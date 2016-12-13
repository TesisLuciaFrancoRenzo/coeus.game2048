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
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.inputs.InputNTupleList;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.random;
import static java.lang.System.arraycopy;

/**
 * @param
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public
class GameBoard
        implements IStatePerceptron, IStateNTuple {
    /**
     *
     */
    public static final int TILE_NUMBER = 4 * 4;
    private final Game2048      game;
    private final List<Double>  normalizedPerceptronInput;
    private final TileContainer tileContainer;
    private       List<Integer> availableSpaceList;
    private       boolean       canMove;
    private       boolean       iWin;
    private       boolean       isFull;
    private       int           maxTileNumberCode;
    private       int           maxTileNumberValue;
    private       boolean       needToAddTile;
    private       int           partialScore;
    private       Tile[]        tiles;

    /**
     * @param game
     * @param tileContainer
     */
    public
    GameBoard(
            Game2048 game,
            TileContainer tileContainer
    ) {
        iWin = false;
        canMove = true;
        this.game = game;
        this.tileContainer = tileContainer;
        tiles = new Tile[TILE_NUMBER];
        partialScore = 0;
        maxTileNumberCode = 0;
        maxTileNumberValue = 0;
        //inicializamos el tablero para su traducción a las entradas de la red neuronal
        if (game.getNeuralNetworkConfiguration() != null) {
            if (game.getNeuralNetworkConfiguration().useNTupleList()) {
                normalizedPerceptronInput = new InputNTupleList();
            } else {
                normalizedPerceptronInput = new ArrayList<>(game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]);
                for (int i = 0; i < game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]; i++) {
                    normalizedPerceptronInput.add(null);
                }
            }
        } else {
            normalizedPerceptronInput = null;
        }
    }

    /**
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     *
     * @return
     */
    public static
    int calculateCustomHash(
            Tile t1,
            Tile t2,
            Tile t3,
            Tile t4
    ) {
        int result = 1;
        result = 31 * result + t1.getGameValue();
        result = 31 * result + t2.getGameValue();
        result = 31 * result + t3.getGameValue();
        result = 31 * result + t4.getGameValue();
        return result;
    }

    /**
     * @param tileArray
     * @param x
     * @param y
     *
     * @return
     */
    public static
    Tile tileAt(
            Tile[] tileArray,
            int x,
            int y
    ) {
        return tileArray[x + y * 4];
    }

    void addPartialScore(int value) {
        partialScore += value;
    }

    /**
     * @param updateInputs
     */
    public
    void addTile(boolean updateInputs) {
        if (!availableSpaceList.isEmpty()) {
            int     index   = (int) (random() * availableSpaceList.size()) % availableSpaceList.size();
            Integer tilePos = availableSpaceList.get(index);
            int     value   = random() < 0.9 ? 1 : 2;
            tiles[tilePos] = tileContainer.getTile(value);
            if (game.printHistory) {
                //                System.out.println("newRandomTile=" + tilePos + "\t" + value);
                assert game.historyLog != null;
                game.historyLog.append("NT=").append(tilePos).append("\t").append(value).append("\n");
            }
        }
        updateInternalState(updateInputs);
    }

    /**
     * @return
     */
    public
    List<Integer> availableSpace() {
        return availableSpaceList;
    }

    private
    List<Integer> calculateAvailableSpace() {
        List<Integer> list = new ArrayList<>(16);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].isEmpty()) {
                list.add(i);
            }
        }
        return list;
    }

    private
    boolean calculateCanMove() {
        if (!isFull) {
            return true;
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = tileAt(x, y);
                if ((x < 3 && t.getCode() == tileAt(x + 1, y).getCode()) || ((y < 3) && t.getCode() == tileAt(x, y + 1).getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    private
    void calculateMaxTile() {
        maxTileNumberValue = 0;
        maxTileNumberCode = 0;
        for (Tile t : tiles) {
            if (t.getGameValue() > maxTileNumberValue) {
                maxTileNumberValue = t.getGameValue();
                maxTileNumberCode = t.getCode();
            }
        }
    }

    /**
     * @return the canMove
     */
    public
    boolean canMove() {
        return canMove;
    }

    /**
     * @param tileContainer
     */
    public
    void clearBoard(TileContainer tileContainer) {
        for (int i = 0; i < TILE_NUMBER; i++) {
            tiles[i] = tileContainer.getTile(0);
        }
    }

    /**
     * @param obj
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public
    boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final GameBoard other = (GameBoard) obj;
        return iWin == other.iWin &&
               canMove == other.canMove &&
               isFull == other.isFull &&
               maxTileNumberValue == other.maxTileNumberValue &&
               needToAddTile == other.needToAddTile &&
               partialScore == other.partialScore &&
               Arrays.deepEquals(tiles, other.tiles) &&
               Objects.equals(availableSpaceList, other.availableSpaceList);
    }

    /**
     * @return una copia del tablero
     */
    @Override
    public
    IState getCopy() {
        GameBoard copy = new GameBoard(game, tileContainer);
        arraycopy(tiles, 0, copy.tiles, 0, GameBoard.TILE_NUMBER);
        copy.iWin = iWin;
        copy.canMove = canMove;
        copy.isFull = isFull;
        copy.maxTileNumberValue = maxTileNumberValue;
        copy.availableSpaceList = new ArrayList<>(16);
        availableSpaceList.forEach((space) -> copy.availableSpaceList.add(space));
        copy.needToAddTile = needToAddTile;
        copy.partialScore = partialScore;
        return copy;
    }

    /**
     * @return the game
     */
    public
    Game2048 getGame() {
        return game;
    }

    /**
     * @return the maxTileNumberCode
     */
    public
    int getMaxTileNumberCode() {
        return maxTileNumberCode;
    }

    /**
     * @return the maxTileNumberValue
     */
    public
    int getMaxTileNumberValue() {
        return maxTileNumberValue;
    }

    @Override
    public
    SamplePointValue[] getNTuple(int nTupleIndex) {
        return game.getNTupleSystemConfiguration().getNTuple(this, nTupleIndex);
    }

    /**
     * @return the partialScore
     */
    public
    int getPartialScore() {
        return partialScore;
    }

    /**
     * @param partialScore the partialScore to set
     */
    public
    void setPartialScore(int partialScore) {
        this.partialScore = partialScore;
    }

    @Override
    public
    double getStateReward(int outputNeuron) {
        if (game.getNTupleSystemConfiguration() != null) {
            return game.getNTupleSystemConfiguration().getBoardReward(this, outputNeuron);
        } else {
            return game.getNeuralNetworkConfiguration().getBoardReward(this, outputNeuron);
        }
    }

    /**
     * @return the tiles
     */
    public
    Tile[] getTiles() {
        return tiles;
    }

    /**
     * @param tiles the tiles to set
     */
    public
    void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * @return
     */
    @Override
    public
    int hashCode() {
        int hash = 5;
        hash = 97 * hash + (iWin ? 1 : 0);
        hash = 97 * hash + (canMove ? 1 : 0);
        hash = 97 * hash + (isFull ? 1 : 0);
        hash = 97 * hash + maxTileNumberValue;
        hash = 97 * hash + maxTileNumberCode;
        hash = 97 * hash + (needToAddTile ? 1 : 0);
        hash = 97 * hash + partialScore;
        hash = 97 * hash + Arrays.deepHashCode(tiles);
        hash = 97 * hash + Objects.hashCode(availableSpaceList);
        return hash;
    }

    /**
     * @return true si es un tablero ganador
     */
    public
    boolean isAWin() {
        return iWin;
    }

    /**
     * @param gameBoard para comparar
     *                  <p>
     *
     * @return true si los 2 tableros son iguales topológicamente
     */
    public
    boolean isEqual(GameBoard gameBoard) {
        for (int i = 0; i < tiles.length; i++) {
            if (!tiles[i].equals(gameBoard.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return
     */
    public
    boolean isFull() {
        return isFull;
    }

    /**
     * @return the needToAddTile
     */
    public
    boolean isNeedToAddTile() {
        return needToAddTile;
    }

    /**
     * @param needToAddTile the needToAddTile to set
     */
    public
    void setNeedToAddTile(boolean needToAddTile) {
        this.needToAddTile = needToAddTile;
    }

    @Override
    public
    boolean isTerminalState() {
        return iWin || !canMove;
    }

    /**
     * @return
     */
    public
    List<GreedyStateProbability> listAllPossibleNextTurnStateFromAfterState() {
        List<GreedyStateProbability> output = new ArrayList<>(16);
        for (int value = 1; value <= 2; value++) {
            double probability;
            if (value == 1) {
                probability = 0.9;
            } else {
                probability = 0.1;
            }
            for (int index = 0; index < availableSpaceList.size() - 1; index++) {
                @SuppressWarnings("unchecked") GameBoard copy = (GameBoard) getCopy();
                copy.tiles[availableSpaceList.get(index)] = tileContainer.getTile(value);
                copy.updateInternalState(true);
                output.add(new GreedyStateProbability(copy, probability));
            }
        }
        return output;
    }

    /**
     * Establece que este tablero gano el juego
     */
    public
    void setToWin() {
        iWin = true;
    }

    /**
     * @param x
     * @param y
     *
     * @return
     */
    public
    Tile tileAt(
            int x,
            int y
    ) {
        return tiles[x + y * 4];
    }

    @Override
    public
    Double translateToPerceptronInput(int neuronIndex) {
        if (neuronIndex < 0 || neuronIndex >= game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0]) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," +
                                               game.getNeuralNetworkConfiguration().getNeuronQuantityInLayer()[0] +
                                               "] but was " +
                                               neuronIndex);
        }
        return normalizedPerceptronInput.get(neuronIndex);
    }

    /**
     * actualizamos la traducción del tablero como entrada del perceptron, encriptado y normalizado.
     * También se actualiza el calculo de si este es un tablero final o no.
     *
     * @param updateInputs
     */
    public
    void updateInternalState(boolean updateInputs) {
        availableSpaceList = calculateAvailableSpace();
        isFull = availableSpaceList.isEmpty();
        canMove = calculateCanMove();
        calculateMaxTile();
        if (game.getNeuralNetworkConfiguration() != null && updateInputs) {
            //   assert this.getMaxTileNumberCode() != 0;
            game.getNeuralNetworkConfiguration().calculateNormalizedPerceptronInput(this, normalizedPerceptronInput);
        }
    }

}
