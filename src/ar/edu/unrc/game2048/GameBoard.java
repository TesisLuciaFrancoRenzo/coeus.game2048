/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IReward;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import static java.lang.Math.random;
import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author franco pellegrini
 * @param <NeuralNetworkClass>
 */
public class GameBoard<NeuralNetworkClass> implements IState {

    /**
     *
     */
    public static final int maxBoardTileCodedNumber = 17;

    /**
     *
     */
    public final static int minBoardTileCodedNumber = 0;

    /**
     *
     */
    public static final int tileNumber = 4 * 4;

    /**
     *
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @return
     */
    public static int calculateCustomHash(Tile t1, Tile t2, Tile t3, Tile t4) {
        int result = 1;
        result = 31 * result + t1.getGameValue();
        result = 31 * result + t2.getGameValue();
        result = 31 * result + t3.getGameValue();
        result = 31 * result + t4.getGameValue();
        return result;
    }

    private List<Integer> availableSpaceList;
    private boolean canMove;
    private final Game2048<NeuralNetworkClass> game;
    private boolean iWin;
    private boolean isFull;
    private int maxTileNumberCode;
    private int maxTileNumberValue;
    private boolean needToAddTile;
    private final List<Double> normalizedPerceptronInput;
    private int partialScore;
    private final TileContainer tileContainer;
    private Tile[] tiles;

    /**
     *
     * @param game
     * @param tileContainer
     */
    public GameBoard(Game2048<NeuralNetworkClass> game, TileContainer tileContainer) {
        iWin = false;
        canMove = true;
        this.game = game;
        this.tileContainer = tileContainer;
        tiles = new Tile[tileNumber];
        partialScore = 0;
        maxTileNumberCode = 0;
        maxTileNumberValue = 0;
        //inicializamos el tablero para su traduccion a las entradas de la red neuronal
        if ( game.getPerceptronConfiguration() != null ) {
            normalizedPerceptronInput = new ArrayList<>(game.getPerceptronConfiguration().perceptron_input_quantity);
            for ( int i = 0; i < game.getPerceptronConfiguration().perceptron_input_quantity; i++ ) {
                normalizedPerceptronInput.add(null);
            }
        } else {
            normalizedPerceptronInput = null;
        }
    }

    /**
     *
     */
    public void addTile() {
        List<Integer> list = this.availableSpace();
        if ( !list.isEmpty() ) {
            int index = (int) (random() * list.size()) % list.size();
            Integer tilePos = list.get(index);
            int value = random() < 0.9 ? 1 : 2;
            tiles[tilePos] = this.tileContainer.getTile(value);
        }
        this.updateInternalState(true);
    }

    /**
     *
     * @return
     */
    public List<Integer> availableSpace() {
        return availableSpaceList;
    }

    /**
     * @return the canMove
     */
    public boolean canMove() {
        return canMove;
    }

    /**
     *
     * @param tileContainer
     */
    public void clearBoard(TileContainer tileContainer) {
        for ( int i = 0; i < tileNumber; i++ ) {
            tiles[i] = tileContainer.getTile(0);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !Objects.equals(getClass(), obj.getClass()) ) {
            return false;
        }
        final GameBoard<NeuralNetworkClass> other = (GameBoard<NeuralNetworkClass>) obj;
        if ( this.iWin != other.iWin ) {
            return false;
        }
        if ( this.canMove != other.canMove ) {
            return false;
        }
        if ( this.isFull != other.isFull ) {
            return false;
        }
        if ( this.maxTileNumberValue != other.maxTileNumberValue ) {
            return false;
        }
        if ( this.needToAddTile != other.needToAddTile ) {
            return false;
        }
//        if ( !Objects.equals(this.normalizedPerceptronInput, other.normalizedPerceptronInput) ) {
//            return false;
//        }
        if ( this.getPartialScore() != other.getPartialScore() ) {
            return false;
        }
        if ( !Arrays.deepEquals(this.tiles, other.tiles) ) {
            return false;
        }
        if ( !Objects.equals(this.availableSpaceList, other.availableSpaceList) ) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param tileContainer para reciclar Tiles
     * <p>
     * @return una copia del tablero
     */
    public GameBoard<NeuralNetworkClass> getCopy(TileContainer tileContainer) {
        GameBoard<NeuralNetworkClass> copy = new GameBoard<>(getGame(), tileContainer);
        arraycopy(getTiles(), 0, copy.getTiles(), 0, GameBoard.tileNumber);
        copy.iWin = iWin;
        copy.canMove = canMove;
        copy.isFull = isFull;
        copy.maxTileNumberValue = maxTileNumberValue;
        copy.availableSpaceList = new ArrayList<>(16);
        availableSpaceList.stream().forEach((space) -> {
            copy.availableSpaceList.add(space);
        });
        copy.needToAddTile = needToAddTile;
        copy.partialScore = partialScore;
        return copy;
    }

    /**
     * @return the game
     */
    public Game2048<NeuralNetworkClass> getGame() {
        return game;
    }

    /**
     * @return the maxTileNumberCode
     */
    public int getMaxTileNumberCode() {
        return maxTileNumberCode;
    }

    /**
     * @return the maxTileNumberValue
     */
    public int getMaxTileNumberValue() {
        return maxTileNumberValue;
    }

    /**
     * @return the partialScore
     */
    public int getPartialScore() {
        return partialScore;
    }

    /**
     * @param partialScore the partialScore to set
     */
    public void setPartialScore(int partialScore) {
        this.partialScore = partialScore;
    }

    @Override
    public IReward getReward() {
        return new PartialScore(this.getPartialScore());
    }

    /**
     * @return the tiles
     */
    public Tile[] getTiles() {
        return tiles;
    }

    /**
     * @param tiles the tiles to set
     */
    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.iWin ? 1 : 0);
        hash = 97 * hash + (this.canMove ? 1 : 0);
        hash = 97 * hash + (this.isFull ? 1 : 0);
        hash = 97 * hash + this.maxTileNumberValue;
        hash = 97 * hash + this.maxTileNumberCode;
        hash = 97 * hash + (this.needToAddTile ? 1 : 0);
        //  hash = 97 * hash + Objects.hashCode(this.normalizedPerceptronInput);
        hash = 97 * hash + this.getPartialScore();
        hash = 97 * hash + Arrays.deepHashCode(this.tiles);
        hash = 97 * hash + Objects.hashCode(this.availableSpaceList);
        return hash;
    }

    /**
     *
     * @return true si es un tablero ganador
     */
    public boolean isAWin() {
        return iWin;
    }

    /**
     *
     * @param gameBoard para comparar
     * <p>
     * @return true si los 2 tableros son iguales topologicamente
     */
    public boolean isEqual(GameBoard<NeuralNetworkClass> gameBoard) {
        for ( int i = 0; i < tiles.length; i++ ) {
            if ( !this.getTiles()[i].equals(gameBoard.getTiles()[i]) ) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean isFull() {
        return this.isFull;
    }

    /**
     * @return the needToAddTile
     */
    public boolean isNeedToAddTile() {
        return needToAddTile;
    }

    /**
     * @param needToAddTile the needToAddTile to set
     */
    public void setNeedToAddTile(boolean needToAddTile) {
        this.needToAddTile = needToAddTile;
    }

    @Override
    public boolean isTerminalState() {
        return iWin || !canMove;
    }

    /**
     * Establece que este tablero gano el juego
     */
    public void setToWin() {
        iWin = true;
    }

    /**
     *
     * @param x
     * @param y <p>
     * @return
     */
    public Tile tileAt(int x, int y) {
        return getTiles()[x + y * 4];
    }

//    @Override
//    public IPrediction translateFinalStateToPrediction() {
//        return getGame().getPerceptronConfiguration().translateFinalRewordToPrediction(this); //TODO borrar?
//    }
    @Override
    public double translateRealOutputToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
        return getGame().getPerceptronConfiguration().translateRealOutputToNormalizedPerceptronOutputFrom(this, outputNeuronIndex);
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(int outputNeuronIndex) {
        return getGame().getPerceptronConfiguration().translateRewordToNormalizedPerceptronOutputFrom(this, outputNeuronIndex);
    }

//    @Override
//    public double translateThisFinalStateToPerceptronOutput(int neuronIndex) {
//        return getGame().getPerceptronConfiguration().translateRealOutputToNormalizedPerceptronOutputFrom(this, neuronIndex);
//    }
    @Override
    public double translateToPerceptronInput(int neuronIndex) {
        //TODO testear esto cuando lo hagamos abstracto
        if ( neuronIndex < 0 || neuronIndex >= getGame().getPerceptronConfiguration().perceptron_input_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + getGame().getPerceptronConfiguration().perceptron_input_quantity + "] but was " + neuronIndex);
        }
        return normalizedPerceptronInput.get(neuronIndex);
    }

    /**
     * actualizamos la traduccion del tablero como entrada del perceptron,
     * encriptado y normalizado. Tambien se actualiza el calculo de si este es
     * un tablero fianl o no.
     * <p>
     * @param updateNormalizedInputs
     */
    public void updateInternalState(boolean updateNormalizedInputs) {
        availableSpaceList = calculateAvailableSpace();
        isFull = availableSpaceList.isEmpty();
        canMove = calculateCanMove();
        calulateMaxTile();
        if ( getGame().getPerceptronConfiguration() != null && updateNormalizedInputs ) {
            //   assert this.getMaxTileNumberCode() != 0;
            getGame().getPerceptronConfiguration().calculateNormalizedPerceptronInput(this, normalizedPerceptronInput);
        }

    }

    private List<Integer> calculateAvailableSpace() {
        List<Integer> list = new ArrayList<>(16);
        for ( int i = 0; i < tiles.length; i++ ) {
            if ( tiles[i].isEmpty() ) {
                list.add(i);
            }
        }
        return list;
    }

    private boolean calculateCanMove() {
        if ( !this.isFull() ) {
            return true;
        }
        for ( int x = 0; x < 4; x++ ) {
            for ( int y = 0; y < 4; y++ ) {
                Tile t = this.tileAt(x, y);
                if ( (x < 3 && t.getCode() == this.tileAt(x + 1, y).getCode()) //TODO optimizar con equal??
                        || ((y < 3) && t.getCode() == this.tileAt(x, y + 1).getCode()) ) {
                    return true;
                }
            }
        }
        return false;
    }

    private void calulateMaxTile() {
        this.maxTileNumberValue = 0;
        this.maxTileNumberCode = 0;
        for ( Tile t : tiles ) {
            if ( t.getGameValue() > this.maxTileNumberValue ) {
                this.maxTileNumberValue = t.getGameValue();
                this.maxTileNumberCode = t.getCode();
            }
        }
    }

    void addPartialScore(int value) {
        partialScore += value;
    }

}
