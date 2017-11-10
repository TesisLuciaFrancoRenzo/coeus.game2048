package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.game2048.experiments.configurations.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.inputs.InputNTupleList;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;

import java.util.*;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GameBoard
        implements IStatePerceptron, IStateNTuple {
    private static final Random rand = new Random();
    private static final int    side = 4;
    private final List< Integer > availableSpace;
    private final Game2048        game;
    private final List< Double >  normalizedPerceptronInput;
    private boolean checkingAvailableMoves = false;
    private int     highestValue           = 0;
    private int     partialScore           = 0;
    private Tile[][] tiles;

    public
    GameBoard( final Game2048 game ) {
        super();
        this.game = game;
        tiles = new Tile[side][side];
        availableSpace = new LinkedList<>();
        //inicializamos el tablero para su traducción a las entradas de la red neuronal
        final EncogConfiguration2048 neuralNetworkConfiguration = game.getNeuralNetworkConfiguration();
        if ( neuralNetworkConfiguration != null ) {
            if ( neuralNetworkConfiguration.useNTupleList() ) {
                normalizedPerceptronInput = new InputNTupleList();
            } else {
                normalizedPerceptronInput = new ArrayList<>(neuralNetworkConfiguration.getNeuronQuantityInLayer()[0]);
                final int neuronQuantityInLayerLength = neuralNetworkConfiguration.getNeuronQuantityInLayer()[0];
                for ( int i = 0; i < neuronQuantityInLayerLength; i++ ) {
                    normalizedPerceptronInput.add(null);
                }
            }
        } else {
            normalizedPerceptronInput = null;
        }
    }

    public
    void addRandomTile() {
        if ( !availableSpace.isEmpty() ) {
            final int pos = availableSpace.get(rand.nextInt(availableSpace.size()));
            final int row = pos / side;
            final int col = pos % side;
            final int val = ( rand.nextInt(10) == 0 ) ? 4 : 2;
            assert tiles[row][col] == null;
            tiles[row][col] = new Tile(val);
            updateNormalizedPerceptronInput();
        }
    }

    public
    boolean canMoveDown() {
        checkingAvailableMoves = true;
        final boolean hasMoves = moveDown();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveLeft() {
        checkingAvailableMoves = true;
        final boolean hasMoves = moveLeft();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveRight() {
        checkingAvailableMoves = true;
        final boolean hasMoves = moveRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveUp() {
        checkingAvailableMoves = true;
        final boolean hasMoves = moveUp();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    void clearInterns( final boolean resetMerged ) {
        availableSpace.clear();
        for ( int row = 0; row < side; row++ ) {
            for ( int col = 0; col < side; col++ ) {
                if ( tiles[row][col] != null ) {
                    if ( resetMerged ) { tiles[row][col].setMerged(false); }
                } else {
                    availableSpace.add(( side * row ) + col);
                }
            }
        }
    }

    @Override
    public
    boolean equals( final Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof GameBoard ) ) { return false; }

        final GameBoard gameBoard = (GameBoard) o;

        if ( checkingAvailableMoves != gameBoard.checkingAvailableMoves ) { return false; }
        if ( highestValue != gameBoard.highestValue ) { return false; }
        if ( partialScore != gameBoard.partialScore ) { return false; }
        if ( !game.equals(gameBoard.game) ) { return false; }
        return ( ( normalizedPerceptronInput != null )
                 ? normalizedPerceptronInput.equals(gameBoard.normalizedPerceptronInput)
                 : ( gameBoard.normalizedPerceptronInput == null ) ) && availableSpace.equals(gameBoard.availableSpace) &&
               Arrays.deepEquals(tiles, gameBoard.tiles);
    }

    public
    Collection< Integer > getAvailableSpace() {
        return availableSpace;
    }

    @Override
    public
    IState getCopy() {
        final GameBoard copy = new GameBoard(game);
        copy.highestValue = highestValue;
        copy.partialScore = partialScore;
        for ( int row = 0; row < side; row++ ) {
            for ( int col = 0; col < side; col++ ) {
                if ( tiles[row][col] != null ) {
                    copy.tiles[row][col] = new Tile(tiles[row][col].getValue());
                }
            }
        }
        copy.clearInterns(false);
        copy.updateNormalizedPerceptronInput();
        return copy;
    }

    public
    int getHighestValue() {
        return highestValue;
    }

    @Override
    public
    SamplePointValue[] getNTuple( final int nTupleIndex ) {
        return game.getNTupleSystemConfiguration().getNTuple(this, nTupleIndex);
    }

    public
    int getPartialScore() {
        return partialScore;
    }

    @Override
    public
    double getStateReward( final int outputNeuron ) {
        return partialScore;
    }

    public
    Tile[][] getTiles() {
        return tiles;
    }

    public
    void setTiles( final Tile[][] tiles ) {
        this.tiles = tiles;
    }

    @Override
    public
    int hashCode() {
        int result = game.hashCode();
        result = ( 31 * result ) + ( ( normalizedPerceptronInput != null ) ? normalizedPerceptronInput.hashCode() : 0 );
        result = ( 31 * result ) + availableSpace.hashCode();
        result = ( 31 * result ) + ( checkingAvailableMoves ? 1 : 0 );
        result = ( 31 * result ) + highestValue;
        result = ( 31 * result ) + partialScore;
        result = ( 31 * result ) + Arrays.deepHashCode(tiles);
        return result;
    }

    public
    boolean isAWin() {
        return game.isAWin(highestValue);
    }

    public
    boolean isFull() {
        return availableSpace.isEmpty();
    }

    @Override
    public
    boolean isTerminalState() {
        checkingAvailableMoves = true;
        final boolean noMovesLeft = game.isAWin(highestValue) || ( !moveUp() && !moveDown() && !moveLeft() && !moveRight() );
        checkingAvailableMoves = false;
        return noMovesLeft;
    }

    /**
     * @return
     */
    public
    List< GreedyStateProbability > listAllPossibleNextTurnStateFromAfterState() {
        final List< GreedyStateProbability > output = new ArrayList<>(16);
        for ( int value = 1; value <= 2; value++ ) {
            final double probability = ( value == 1 ) ? 0.9 : 0.1;
            clearInterns(false);
            final int availableSpaceSize = availableSpace.size() - 1;
            for ( int index = 0; index < availableSpaceSize; index++ ) {
                final GameBoard copy = (GameBoard) getCopy();
                final int       pos  = availableSpace.get(index);
                final int       row  = pos / side;
                final int       col  = pos % side;
                copy.tiles[row][col] = new Tile((int) Math.pow(2, value));
                copy.updateNormalizedPerceptronInput();
                output.add(new GreedyStateProbability(copy, probability));
            }
        }
        return output;
    }

    private
    boolean move(
            final int countDownFrom,
            final int yInc,
            final int xInc
    ) {
        highestValue = 0;
        partialScore = 0;

        boolean moved = false;
        for ( int i = 0; i < ( side * side ); i++ ) {
            final int j = Math.abs(countDownFrom - i);

            int r = j / side;
            int c = j % side;

            if ( tiles[r][c] == null ) { continue; }

            int nextR = r + yInc;
            int nextC = c + xInc;

            while ( ( nextR >= 0 ) && ( nextR < side ) && ( nextC >= 0 ) && ( nextC < side ) ) {

                final Tile next = tiles[nextR][nextC];
                final Tile curr = tiles[r][c];

                if ( next == null ) {

                    if ( checkingAvailableMoves ) { return true; }

                    tiles[nextR][nextC] = curr;
                    tiles[r][c] = null;
                    r = nextR;
                    c = nextC;
                    nextR += yInc;
                    nextC += xInc;
                    moved = true;

                } else if ( next.canMergeWith(curr) ) {

                    if ( checkingAvailableMoves ) { return true; }

                    final int value = next.mergeWith(curr);
                    if ( value > highestValue ) {
                        highestValue = value;
                    }
                    partialScore += value;
                    tiles[r][c] = null;
                    moved = true;
                    break;
                } else {
                    break;
                }
            }
        }
        if ( moved ) {
            updateNormalizedPerceptronInput();
        }
        return moved;
    }

    public
    boolean moveDown() {
        return move(( side * side ) - 1, 1, 0);
    }

    public
    boolean moveLeft() {
        return move(0, 0, -1);
    }

    public
    boolean moveRight() {
        return move(( side * side ) - 1, 0, 1);
    }

    public
    boolean moveUp() {
        return move(0, -1, 0);
    }

    public
    Tile tileAt(
            final int row,
            final int col
    ) {
        return tiles[row][col];
    }

    @Override
    public
    String toString() {
        final StringBuilder out = new StringBuilder();
        out.append('\n');
        for ( int r = 0; r < side; r++ ) {
            for ( int c = 0; c < side; c++ ) {
                if ( tiles[r][c] == null ) {
                    out.append("-\t");
                } else {
                    out.append(tiles[r][c].getValue()).append('\t');
                }
            }
            out.append('\n');
        }
        return out.toString();
    }

    @Override
    public
    Double translateToPerceptronInput( final int neuronIndex ) {
        if ( ( neuronIndex < 0 ) || ( neuronIndex >= game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] ) ) {
            throw new IllegalArgumentException(
                    "neuronIndex range for output layer must be [0," + game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] +
                    "] but was " + neuronIndex);
        }
        return normalizedPerceptronInput.get(neuronIndex);
    }

    private
    void updateNormalizedPerceptronInput() {
        final EncogConfiguration2048 neuralNetworkConfiguration = game.getNeuralNetworkConfiguration();
        if ( neuralNetworkConfiguration != null ) {
            //   assert this.getMaxTileNumberCode() != 0;
            neuralNetworkConfiguration.calculateNormalizedPerceptronInput(this, normalizedPerceptronInput);
        }
    }
}
