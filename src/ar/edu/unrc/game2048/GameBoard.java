/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointState;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.inputs.InputNtupleList;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.greedy.StateProbability;
import static java.lang.Math.random;
import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 * @param <NeuralNetworkClass>
 */
public class GameBoard<NeuralNetworkClass> implements IStatePerceptron, IStateNTuple {

    /**
     *
     */
    public static final int MAX_BOARDTILE_CODED_NUMBER = 17;

    /**
     *
     */
    public final static int MIN_BOARDTILE_CODED_NUMBER = 0;

    /**
     *
     */
    public static final int TILE_NUMBER = 4 * 4;

    /**
     *
     * @param t1
     * @param t2
     * @param t3
     * @param t4 <p>
     * @return
     */
    public static int calculateCustomHash(Tile t1,
            Tile t2,
            Tile t3,
            Tile t4) {
        int result = 1;
        result = 31 * result + t1.getGameValue();
        result = 31 * result + t2.getGameValue();
        result = 31 * result + t3.getGameValue();
        result = 31 * result + t4.getGameValue();
        return result;
    }

    /**
     *
     * @param tileArray
     * @param x
     * @param y
     *
     * @return
     */
    public static Tile tileAt(Tile[] tileArray,
            int x,
            int y) {
        return tileArray[x + y * 4];
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
    public GameBoard(Game2048<NeuralNetworkClass> game,
            TileContainer tileContainer) {
        iWin = false;
        canMove = true;
        this.game = game;
        this.tileContainer = tileContainer;
        tiles = new Tile[TILE_NUMBER];
        partialScore = 0;
        maxTileNumberCode = 0;
        maxTileNumberValue = 0;
        //inicializamos el tablero para su traduccion a las entradas de la red neuronal
        if ( game.getNeuralNetworkConfiguration() != null ) {
            if ( game.getNeuralNetworkConfiguration().useNTupleList() ) {
                normalizedPerceptronInput = new InputNtupleList();
            } else {
                normalizedPerceptronInput = new ArrayList<>(game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]);
                for ( int i = 0; i < game.getNeuralNetworkConfiguration().
                        getNeuronQuantityInLayer()[0]; i++ ) {
                    normalizedPerceptronInput.add(null);
                }
            }
        } else {
            normalizedPerceptronInput = null;
        }
    }

    /**
     *
     * @param updateInputs
     */
    public void addTile(boolean updateInputs) {
        if ( !availableSpaceList.isEmpty() ) {
            int index = (int) (random() * availableSpaceList.size()) % availableSpaceList.
                    size();
            Integer tilePos = availableSpaceList.get(index);
            int value = random() < 0.9 ? 1 : 2;
            tiles[tilePos] = this.tileContainer.getTile(value);
        }
        this.updateInternalState(updateInputs);
    }

    /**
     *
     * @return
     */
    public List<Integer> availableSpace() {
        return availableSpaceList;
    }

//    /**
//     *
//     */
//    public void calculateBestSymetricBoard() {
//        if ( game.getnTupleSystemConfiguration() == null || game.getnTupleSystemConfiguration().getNTupleSystem() == null ) {
//            throw new IllegalStateException("Falta un perceptron para ejecutar este método");
//        }
//        List<VerySimpleBoard<NeuralNetworkClass>> allPossibleSimetricBoards = new ArrayList<>(8);
//
//        allPossibleSimetricBoards.add(new VerySimpleBoard(tiles, game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, tiles), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, allPossibleSimetricBoards.get(1).getSimpleTiles()), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, allPossibleSimetricBoards.get(2).getSimpleTiles()), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.horizontalFlipTiles(tiles), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, allPossibleSimetricBoards.get(4).getSimpleTiles()), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, allPossibleSimetricBoards.get(5).getSimpleTiles()), game.getnTupleSystemConfiguration()));
//        allPossibleSimetricBoards.add(new VerySimpleBoard(Game2048.rotateBoardTiles(90, allPossibleSimetricBoards.get(6).getSimpleTiles()), game.getnTupleSystemConfiguration()));
//
//        assert allPossibleSimetricBoards.size() == 8;
//
//        Stream<VerySimpleBoard<NeuralNetworkClass>> stream;
////        stream = allPossibleSimetricBoards.parallelStream();
//        stream = allPossibleSimetricBoards.stream();
//        List<VerySimpleBoard<NeuralNetworkClass>> bestBoardMatches
//                = stream
//                .map(verySimpleBoard -> {
//                    verySimpleBoard.computePrediction();
//                    return verySimpleBoard;
//                })
//                .collect(MaximalListConsumerSimpleBoard<NeuralNetworkClass>::new, MaximalListConsumerSimpleBoard<NeuralNetworkClass>::accept, MaximalListConsumerSimpleBoard<NeuralNetworkClass>::combine)
//                .getList();
//        VerySimpleBoard<NeuralNetworkClass> bestBoard = bestBoardMatches.get(randomBetween(0, bestBoardMatches.size() - 1));
//    }
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
        for ( int i = 0; i < TILE_NUMBER; i++ ) {
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
        return Objects.equals(this.availableSpaceList, other.availableSpaceList);
    }

    /**
     *
     * @return una copia del tablero
     */
    @Override
    public IState getCopy() {
        GameBoard<NeuralNetworkClass> copy = new GameBoard<>(getGame(),
                tileContainer);
        arraycopy(getTiles(), 0, copy.getTiles(), 0, GameBoard.TILE_NUMBER);
        copy.iWin = iWin;
        copy.canMove = canMove;
        copy.isFull = isFull;
        copy.maxTileNumberValue = maxTileNumberValue;
        copy.availableSpaceList = new ArrayList<>(16);
        availableSpaceList.stream().forEach((space) ->
                {
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

    @Override
    public SamplePointState[] getNTuple(int nTupleIndex) {
        return game.getnTupleSystemConfiguration().getNTuple(this, nTupleIndex);
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
    public double getStateReward(int outputNeuron) {
        if ( game.getnTupleSystemConfiguration() != null ) {
            return game.getnTupleSystemConfiguration().getBoardReward(this,
                    outputNeuron);
        } else {
            return game.getNeuralNetworkConfiguration().getBoardReward(this,
                    outputNeuron);
        }
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
     *
     * @return
     */
    public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate() {
        List<StateProbability> output = new ArrayList<>(16);
        for ( int value = 1; value <= 2; value++ ) {
            double probability;
            if ( value == 1 ) {
                probability = 0.9;
            } else {
                probability = 0.1;
            }
            for ( int index = 0; index < availableSpaceList.size() - 1; index++ ) {
                GameBoard<NeuralNetworkClass> copy = (GameBoard<NeuralNetworkClass>) this.
                        getCopy();
                copy.tiles[availableSpaceList.get(index)] = this.tileContainer.
                        getTile(value);
                copy.updateInternalState(true);
                output.add(new StateProbability(copy, probability));
            }
        }
        return output;
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
    public Tile tileAt(int x,
            int y) {
        return tiles[x + y * 4];
    }

    @Override
    public Double translateToPerceptronInput(int neuronIndex) {
        if ( neuronIndex < 0 || neuronIndex >= getGame().getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] ) {
            throw new IllegalArgumentException(
                    "neuronIndex range for output layer must be [0," + getGame().getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] + "] but was " + neuronIndex);
        }
        return normalizedPerceptronInput.get(neuronIndex);
    }

    /**
     * actualizamos la traduccion del tablero como entrada del perceptron, encriptado y normalizado.
     * Tambien se actualiza el calculo de si este es un tablero fianl o no.
     * <p>
     * @param updateInputs
     */
    public void updateInternalState(boolean updateInputs) {
        availableSpaceList = calculateAvailableSpace();
        isFull = availableSpaceList.isEmpty();
        canMove = calculateCanMove();
        calulateMaxTile();
        if ( getGame().getNeuralNetworkConfiguration() != null && updateInputs ) {
            //   assert this.getMaxTileNumberCode() != 0;
            getGame().getNeuralNetworkConfiguration().
                    calculateNormalizedPerceptronInput(this,
                            normalizedPerceptronInput);
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
                if ( (x < 3 && t.getCode() == this.tileAt(x + 1, y).getCode())
                        || ((y < 3) && t.getCode() == this.tileAt(x, y + 1).
                        getCode()) ) {
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
