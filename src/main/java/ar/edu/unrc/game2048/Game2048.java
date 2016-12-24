package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.*;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public
class Game2048
        implements IProblemToTrain {

    private final StringBuilder           historyLog;
    private final NTupleConfiguration2048 nTupleSystemConfiguration;
    private final EncogConfiguration2048  neuralNetworkConfiguration;
    private final int                     numberToWin;
    private final boolean                 printHistory;
    private       GameBoard               board;
    private State gameState = State.start;
    private int highest;
    private int lastTurn;
    private int score;

    public
    Game2048(
            EncogConfiguration2048 neuralNetworkConfiguration,
            NTupleConfiguration2048 nTupleSystemConfiguration,
            int numberToWin,
            boolean printHistory
    ) {
        if ( printHistory ) {
            historyLog = new StringBuilder();
        } else {
            historyLog = null;
        }
        this.numberToWin = numberToWin;
        this.neuralNetworkConfiguration = neuralNetworkConfiguration;
        this.nTupleSystemConfiguration = nTupleSystemConfiguration;
        this.printHistory = printHistory;
    }

    public static
    void main( String[] args ) {
        Game2048 game = new Game2048(null, null, 2048, false);
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        String  line;
        do {
            line = scanner.nextLine();
            game.processInput(line);

        } while ( line.compareToIgnoreCase("q") != 0 );
    }

    @Override
    public
    IState computeAfterState(
            IState turnInitialState,
            IAction action
    ) {
        GameBoard futureBoard = (GameBoard) turnInitialState.getCopy();
        switch ( (Action) action ) {
            case left: {
                futureBoard.moveLeft();
                break;
            }
            case right: {
                futureBoard.moveRight();
                break;
            }
            case up: {
                futureBoard.moveUp();
                break;
            }
            case down: {
                futureBoard.moveDown();
                break;
            }
            default: {
                throw new IllegalArgumentException("la acción \"" + action.toString() + "\" no es valida");
            }
        }
        return futureBoard;
    }

    @Override
    public
    IState computeNextTurnStateFromAfterState( IState afterState ) {
        GameBoard finalBoard = (GameBoard) afterState;
        highest = ( finalBoard.getHighestValue() > highest ) ? finalBoard.getHighestValue() : highest;
        score += finalBoard.getPartialScore();
        lastTurn++;
        if ( highest < numberToWin ) {
            finalBoard.clearInterns(true);
            finalBoard.addRandomTile();
            if ( finalBoard.isTerminalState() ) {
                gameState = State.over;
            }
        } else if ( highest == numberToWin ) {
            gameState = State.won;
        }
        return finalBoard;
    }

    @Override
    public
    Double computeNumericRepresentationFor(
            Object[] output,
            IActor actor
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
    double deNormalizeValueFromPerceptronOutput( Object value ) {
        if ( neuralNetworkConfiguration != null ) {
            return neuralNetworkConfiguration.deNormalizeValueFromNeuralNetworkOutput(value);
        } else {
            return nTupleSystemConfiguration.deNormalizeValueFromNeuralNetworkOutput(value);
        }
    }

    private
    void draw() {
        if ( gameState == State.running ) {
            System.out.println(board.toString() + "\nhighest=" + highest + " score=" + score);
        } else {
            if ( gameState == State.won ) {
                System.out.println(board.toString() + "\nGanaste!");

            } else if ( gameState == State.over ) {
                System.out.println(board.toString() + "\nPerdiste!");
            }
        }
        System.out.println("======================================================");
    }

    @Override
    public
    Object[] evaluateBoardWithPerceptron( IState state ) {
        //dependiendo de que tipo de red neuronal utilizamos, evaluamos las entradas y calculamos una salida
        if ( neuralNetworkConfiguration != null && neuralNetworkConfiguration.
                getNeuralNetwork() != null ) {
            if ( neuralNetworkConfiguration.getNeuralNetwork() instanceof BasicNetwork ) { //es sobre la librería encog
                //creamos las entradas de la red neuronal
                double[]  inputs     = new double[neuralNetworkConfiguration.getNeuronQuantityInLayer()[0]];
                IntStream inputLayer = IntStream.range(0, neuralNetworkConfiguration.getNeuronQuantityInLayer()[0]);
                if ( neuralNetworkConfiguration.isConcurrentInputEnabled() ) {
                    inputLayer = inputLayer.parallel();
                } else {
                    inputLayer = inputLayer.sequential();
                }
                inputLayer.forEach(index -> inputs[index] = ( (IStatePerceptron) state ).translateToPerceptronInput(index));

                //cargamos la entrada a la red
                MLData   inputData = new BasicMLData(inputs);
                MLData   output    = neuralNetworkConfiguration.getNeuralNetwork().compute(inputData);
                Double[] out       = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }
        }
        if ( nTupleSystemConfiguration != null && nTupleSystemConfiguration.getNTupleSystem() != null ) {
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
        return highest;
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
    IState initialize( IActor actor ) {
        score = 0;
        lastTurn = 0;
        highest = 0;
        gameState = State.running;
        board = new GameBoard(this);
        board.clearInterns(true);
        board.addRandomTile();
        board.clearInterns(false);
        board.addRandomTile();
        return board;
    }

    public
    boolean isAWin( int value ) {
        return value >= numberToWin;
    }

    public
    boolean isPrintHistory() {
        return printHistory;
    }

    public
    boolean isRunning() {
        return gameState == State.running;
    }

    @Override
    public
    ArrayList< IAction > listAllPossibleActions( IState turnInitialState ) {
        ArrayList< IAction > actions = new ArrayList<>(4);
        GameBoard            state   = (GameBoard) turnInitialState;
        if ( state.canMoveUp() ) { actions.add(Action.up); }
        if ( state.canMoveDown() ) { actions.add(Action.down); }
        if ( state.canMoveLeft() ) { actions.add(Action.left); }
        if ( state.canMoveRight() ) { actions.add(Action.right); }
        return actions;
    }

    /**
     * @param afterState
     *
     * @return lista de todos los posibles siguientes turnos desde {@code afterState}
     */
    public
    List< GreedyStateProbability > listAllPossibleNextTurnStateFromAfterState(
            IState afterState
    ) {
        //noinspection unchecked
        return ( (GameBoard) afterState ).listAllPossibleNextTurnStateFromAfterState();
    }

    @Override
    public
    double normalizeValueToPerceptronOutput( Object value ) {
        if ( nTupleSystemConfiguration != null ) {
            return nTupleSystemConfiguration.normalizeValueToPerceptronOutput(value);
        } else {
            return neuralNetworkConfiguration.normalizeValueToPerceptronOutput(value);
        }
    }

    private
    void processInput( String input ) {
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
    void setCurrentState( IState nextTurnState ) {
        board = (GameBoard) nextTurnState;
    }

    enum State {
        start,
        won,
        running,
        over
    }
}
