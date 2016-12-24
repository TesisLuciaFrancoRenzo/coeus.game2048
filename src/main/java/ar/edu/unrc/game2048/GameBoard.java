package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.inputs.InputNTupleList;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;

import java.util.*;

/**
 * Created by franco on 24/12/16.
 */
public
class GameBoard
        implements IStatePerceptron, IStateNTuple {
    private final static Random rand = new Random();
    private final static int    side = 4;
    private final Game2048        game;
    private final List< Double >  normalizedPerceptronInput;
    private       List< Integer > availableSpace;
    private       boolean         checkingAvailableMoves;
    private       int             highestValue;
    private       int             partialScore;
    private       Tile[][]        tiles;

    public
    GameBoard( Game2048 game ) {
        this.game = game;
        this.tiles = new Tile[side][side];
        availableSpace = new LinkedList<>();
        //inicializamos el tablero para su traducción a las entradas de la red neuronal
        if ( game.getNeuralNetworkConfiguration() != null ) {
            if ( game.getNeuralNetworkConfiguration().useNTupleList() ) {
                normalizedPerceptronInput = new InputNTupleList();
            } else {
                normalizedPerceptronInput = new ArrayList<>(game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]);
                for ( int i = 0; i < game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]; i++ ) {
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
            int pos = availableSpace.get(rand.nextInt(availableSpace.size()));
            int row, col;
            row = pos / side;
            col = pos % side;
            int val = rand.nextInt(10) == 0 ? 4 : 2;
            assert tiles[row][col] == null;
            tiles[row][col] = new Tile(val);
            updateNormalizedPerceptronInput();
        }
    }

    public
    boolean canMoveDown() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveDown();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveLeft() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveLeft();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveRight() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    boolean canMoveUp() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveUp();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    public
    void clearInterns( boolean resetMerged ) {
        availableSpace.clear();
        for ( int row = 0; row < side; row++ ) {
            for ( int col = 0; col < side; col++ ) {
                if ( tiles[row][col] != null ) {
                    if ( resetMerged ) { tiles[row][col].setMerged(false); }
                } else {
                    availableSpace.add(side * row + col);
                }
            }
        }
    }

    @Override
    public
    boolean equals( Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof GameBoard ) ) { return false; }

        GameBoard gameBoard = (GameBoard) o;

        if ( checkingAvailableMoves != gameBoard.checkingAvailableMoves ) { return false; }
        if ( highestValue != gameBoard.highestValue ) { return false; }
        if ( partialScore != gameBoard.partialScore ) { return false; }
        if ( !game.equals(gameBoard.game) ) { return false; }
        if ( normalizedPerceptronInput != null
             ? !normalizedPerceptronInput.equals(gameBoard.normalizedPerceptronInput)
             : gameBoard.normalizedPerceptronInput != null ) { return false; }
        if ( !availableSpace.equals(gameBoard.availableSpace) ) { return false; }
        return Arrays.deepEquals(tiles, gameBoard.tiles);
    }

    public
    List< Integer > getAvailableSpace() {
        return availableSpace;
    }

    @Override
    public
    IState getCopy() {
        GameBoard copy = new GameBoard(game);
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
        copy.updateNormalizedPerceptronInput(); //FIXME optimize?
        return copy;
    }

    public
    int getHighestValue() {
        return highestValue;
    }

    @Override
    public
    SamplePointValue[] getNTuple( int nTupleIndex ) {
        return game.getNTupleSystemConfiguration().getNTuple(this, nTupleIndex);
    }

    public
    int getPartialScore() {
        return partialScore;
    }

    @Override
    public
    double getStateReward( int outputNeuron ) {
        return partialScore;
    }

    public
    Tile[][] getTiles() {
        return tiles;
    }

    public
    void setTiles( Tile[][] tiles ) {
        this.tiles = tiles;
    }

    @Override
    public
    int hashCode() {
        int result = game.hashCode();
        result = 31 * result + ( normalizedPerceptronInput != null ? normalizedPerceptronInput.hashCode() : 0 );
        result = 31 * result + availableSpace.hashCode();
        result = 31 * result + ( checkingAvailableMoves ? 1 : 0 );
        result = 31 * result + highestValue;
        result = 31 * result + partialScore;
        result = 31 * result + Arrays.deepHashCode(tiles);
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
        boolean hasMoves = !game.isAWin(highestValue) && ( moveUp() || moveDown() || moveLeft() || moveRight() );
        checkingAvailableMoves = false;
        return !hasMoves;
    }

    /**
     * @return
     */
    public
    List< GreedyStateProbability > listAllPossibleNextTurnStateFromAfterState() {
        List< GreedyStateProbability > output = new ArrayList<>(16);
        for ( int value = 1; value <= 2; value++ ) {
            double probability;
            if ( value == 1 ) {
                probability = 0.9;
            } else {
                probability = 0.1;
            }
            this.clearInterns(false);
            for ( int index = 0; index < availableSpace.size() - 1; index++ ) {
                @SuppressWarnings( "unchecked" ) GameBoard copy = (GameBoard) getCopy();
                int                                        pos  = availableSpace.get(index);
                int                                        row, col;
                row = pos / side;
                col = pos % side;
                copy.tiles[row][col] = new Tile((int) Math.pow(2, value));
                copy.updateNormalizedPerceptronInput();
                output.add(new GreedyStateProbability(copy, probability));
            }
        }
        return output;
    }

    private
    boolean move(
            int countDownFrom,
            int yInc,
            int xInc
    ) {
        boolean moved = false;
        highestValue = 0;
        partialScore = 0;

        for ( int i = 0; i < side * side; i++ ) {
            int j = Math.abs(countDownFrom - i);

            int r = j / side;
            int c = j % side;

            if ( tiles[r][c] == null ) { continue; }

            int nextR = r + yInc;
            int nextC = c + xInc;

            while ( nextR >= 0 && nextR < side && nextC >= 0 && nextC < side ) {

                Tile next = tiles[nextR][nextC];
                Tile curr = tiles[r][c];

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

                    int value = next.mergeWith(curr);
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
        return move(side * side - 1, 1, 0);
    }

    public
    boolean moveLeft() {
        return move(0, 0, -1);
    }

    public
    boolean moveRight() {
        return move(side * side - 1, 0, 1);
    }

    public
    boolean moveUp() {
        return move(0, -1, 0);
    }

    public
    Tile tileAt(
            int row,
            int col
    ) {
        return tiles[row][col];
    }

    @Override
    public
    String toString() {
        StringBuilder out = new StringBuilder();
        out.append("\n");
        for ( int r = 0; r < side; r++ ) {
            for ( int c = 0; c < side; c++ ) {
                if ( tiles[r][c] == null ) {
                    out.append("-\t");
                } else {
                    out.append(tiles[r][c].getValue() + "\t");
                }
            }
            out.append("\n");
        }
        return out.toString();
    }

    @Override
    public
    Double translateToPerceptronInput( int neuronIndex ) {
        if ( neuronIndex < 0 || neuronIndex >= game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] ) {
            throw new IllegalArgumentException(
                    "neuronIndex range for output layer must be [0," + game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] +
                    "] but was " + neuronIndex);
        }
        return normalizedPerceptronInput.get(neuronIndex);
    }

    public
    void updateNormalizedPerceptronInput() {
        if ( game.getNeuralNetworkConfiguration() != null ) {
            //   assert this.getMaxTileNumberCode() != 0;
            game.getNeuralNetworkConfiguration().calculateNormalizedPerceptronInput(this, normalizedPerceptronInput);
        }
    }
}
