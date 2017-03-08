package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.*;
import ar.edu.unrc.game2048.experiments.configurations.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.NTupleConfiguration2048;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class Game2048
        implements IProblemToTrain {

    private final StringBuilder           historyLog;
    private final NTupleConfiguration2048 nTupleSystemConfiguration;
    private final EncogConfiguration2048  neuralNetworkConfiguration;
    private final int                     numberToWin;
    private final boolean                 printHistory;
    private GameBoard board     = null;
    private State     gameState = State.START;
    private int       lastTurn  = 0;
    private int       maxNumber = 0;
    private int       score     = 0;

    public
    Game2048(
            final EncogConfiguration2048 neuralNetworkConfiguration,
            final NTupleConfiguration2048 nTupleSystemConfiguration,
            final int numberToWin,
            final boolean printHistory
    ) {
        super();
        historyLog = printHistory ? new StringBuilder() : null;
        this.numberToWin = numberToWin;
        this.neuralNetworkConfiguration = neuralNetworkConfiguration;
        this.nTupleSystemConfiguration = nTupleSystemConfiguration;
        this.printHistory = printHistory;
    }

    public static
    void main( final String... args ) {
        final Game2048 game = new Game2048(null, null, 2048, false);
        System.out.println("Press \"ENTER\" to continue...");
        try ( Scanner scanner = new Scanner(System.in, "UTF-8") ) {
            String line;
            do {
                line = scanner.nextLine();
                game.processInput(line);
            } while ( line.compareToIgnoreCase("q") != 0 );
        }
    }

    @Override
    public
    boolean canExploreThisTurn( final long currentTurn ) {
        return ( neuralNetworkConfiguration != null )
               ? neuralNetworkConfiguration.canExploreThisTurn(currentTurn)
               : nTupleSystemConfiguration.canExploreThisTurn(currentTurn);
    }

    @Override
    public
    IState computeAfterState(
            final IState turnInitialState,
            final IAction action
    ) {
        final GameBoard futureBoard = (GameBoard) turnInitialState.getCopy();
        switch ( (Action) action ) {
            case LEFT:
                futureBoard.moveLeft();
                break;
            case RIGHT:
                futureBoard.moveRight();
                break;
            case UP:
                futureBoard.moveUp();
                break;
            case DOWN:
                futureBoard.moveDown();
                break;
            default:
                throw new IllegalArgumentException("la acción \"" + action + "\" no es valida");
        }
        return futureBoard;
    }

    @Override
    public
    IState computeNextTurnStateFromAfterState( final IState afterState ) {
        final GameBoard finalBoard = (GameBoard) afterState.getCopy(); //FIXME optimizar?
        maxNumber = ( finalBoard.getHighestValue() > maxNumber ) ? finalBoard.getHighestValue() : maxNumber;
        score += finalBoard.getPartialScore();
        lastTurn++;
        if ( maxNumber < numberToWin ) {
            finalBoard.clearInterns(true);
            finalBoard.addRandomTile();
            if ( finalBoard.isTerminalState() ) {
                gameState = State.OVER;
            }
        } else if ( maxNumber == numberToWin ) {
            gameState = State.WON;
        }
        return finalBoard;
    }

    @Override
    public
    Double computeNumericRepresentationFor(
            final Object[] output,
            final IActor actor
    ) {
        if ( neuralNetworkConfiguration != null ) {
            return neuralNetworkConfiguration.computeNumericRepresentationFor(this, output);
        } else {
            assert output.length == 1;
            return (Double) output[0];
        }
    }

    @Override
    public
    double deNormalizeValueFromPerceptronOutput( final Object value ) {
        return ( neuralNetworkConfiguration != null )
               ? neuralNetworkConfiguration.deNormalizeValueFromNeuralNetworkOutput(value)
               : nTupleSystemConfiguration.deNormalizeValueFromNeuralNetworkOutput(value);
    }

    private
    void draw() {
        if ( gameState == State.RUNNING ) {
            System.out.println(board + "\nmaxNumber=" + maxNumber + " score=" + score);
        } else {
            if ( gameState == State.WON ) {
                System.out.println(board + "\nGanaste!");
            } else if ( gameState == State.OVER ) {
                System.out.println(board + "\nPerdiste!");
            }
        }
        System.out.println("======================================================");
    }

    @Override
    public
    Object[] evaluateBoardWithPerceptron( final IState state ) {
        //dependiendo de que tipo de red neuronal utilizamos, evaluamos las entradas y calculamos una salida
        if ( ( neuralNetworkConfiguration != null ) && ( neuralNetworkConfiguration.getNeuralNetwork() != null ) ) {
            if ( neuralNetworkConfiguration.getNeuralNetwork() instanceof BasicNetwork ) { //es sobre la librería encog
                //creamos las entradas de la red neuronal
                final double[] inputs     = new double[neuralNetworkConfiguration.getNeuronQuantityInLayer()[0]];
                IntStream      inputLayer = IntStream.range(0, neuralNetworkConfiguration.getNeuronQuantityInLayer()[0]);
                inputLayer = neuralNetworkConfiguration.isConcurrentInputEnabled() ? inputLayer.parallel() : inputLayer.sequential();
                inputLayer.forEach(index -> inputs[index] = ( (IStatePerceptron) state ).translateToPerceptronInput(index));

                //cargamos la entrada a la red
                final MLData   inputData  = new BasicMLData(inputs);
                final MLData   output     = neuralNetworkConfiguration.getNeuralNetwork().compute(inputData);
                final Double[] out        = new Double[output.getData().length];
                final int      outputSize = output.size();
                for ( int i = 0; i < outputSize; i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }
        }
        if ( ( nTupleSystemConfiguration != null ) && ( nTupleSystemConfiguration.getNTupleSystem() != null ) ) {
            return new Double[] { nTupleSystemConfiguration.getNTupleSystem().getComputation((IStateNTuple) state) };
        }
        throw new UnsupportedOperationException("only Encog and NTupleSystem is implemented");
    }

    @Override
    public
    IActor getActorToTrain() {
        return null;
    }

    public
    GameBoard getBoard() {
        return board;
    }

    public
    StringBuilder getHistoryLog() {
        return historyLog;
    }

    public
    int getLastTurn() {
        return lastTurn;
    }

    public
    int getMaxNumber() {
        return maxNumber;
    }

    public
    NTupleConfiguration2048 getNTupleSystemConfiguration() {
        return nTupleSystemConfiguration;
    }

    public
    EncogConfiguration2048 getNeuralNetworkConfiguration() {
        return neuralNetworkConfiguration;
    }

    public
    int getScore() {
        return score;
    }

    @Override
    public
    IState initialize( final IActor actor ) {
        score = 0;
        lastTurn = 0;
        maxNumber = 0;
        gameState = State.RUNNING;
        board = new GameBoard(this);
        board.clearInterns(true);
        board.addRandomTile();
        board.clearInterns(false);
        board.addRandomTile();
        return board;
    }

    public
    boolean isAWin( final int value ) {
        return value >= numberToWin;
    }

    public
    boolean isPrintHistory() {
        return printHistory;
    }

    public
    boolean isRunning() {
        return gameState == State.RUNNING;
    }

    @Override
    public
    List< IAction > listAllPossibleActions( final IState turnInitialState ) {
        final List< IAction > actions = new ArrayList<>(4);
        final GameBoard       state   = (GameBoard) turnInitialState;
        if ( state.canMoveUp() ) { actions.add(Action.UP); }
        if ( state.canMoveDown() ) { actions.add(Action.DOWN); }
        if ( state.canMoveLeft() ) { actions.add(Action.LEFT); }
        if ( state.canMoveRight() ) { actions.add(Action.RIGHT); }
        return actions;
    }

    /**
     * @param afterState
     *
     * @return lista de todos los posibles siguientes turnos desde {@code afterState}
     */
    public
    List< GreedyStateProbability > listAllPossibleNextTurnStateFromAfterState(
            final IState afterState
    ) {
        return ( (GameBoard) afterState ).listAllPossibleNextTurnStateFromAfterState();
    }

    @Override
    public
    double normalizeValueToPerceptronOutput( final Object value ) {
        return ( nTupleSystemConfiguration != null )
               ? nTupleSystemConfiguration.normalizeValueToPerceptronOutput(value)
               : neuralNetworkConfiguration.normalizeValueToPerceptronOutput(value);
    }

    private
    void processInput( final String input ) {
        switch ( input ) {
            case "w":
                if ( board.canMoveUp() ) {
                    board.moveUp();
                    setCurrentState(computeNextTurnStateFromAfterState(board));
                }
                break;
            case "s":
                if ( board.canMoveDown() ) {
                    board.moveDown();
                    setCurrentState(computeNextTurnStateFromAfterState(board));
                }
                break;
            case "a":
                if ( board.canMoveLeft() ) {
                    board.moveLeft();
                    setCurrentState(computeNextTurnStateFromAfterState(board));
                }
                break;
            case "d":
                if ( board.canMoveRight() ) {
                    board.moveRight();
                    setCurrentState(computeNextTurnStateFromAfterState(board));
                }
                break;
            case "n":
                initialize(null);
                break;
            default:
                break;
        }
        draw();
    }


    @Override
    public
    void setCurrentState( final IState nextTurnState ) {
        board = (GameBoard) nextTurnState;
    }

    enum State {
        START,
        WON,
        RUNNING,
        OVER
    }
}
