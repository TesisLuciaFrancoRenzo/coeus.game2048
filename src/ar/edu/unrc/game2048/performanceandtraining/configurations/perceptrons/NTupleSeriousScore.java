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
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class NTupleSeriousScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    private final List<SamplePointState> allSamplePointStates;
    private final HashMap<SamplePointState, Integer> mapSamplePointStates;

    private final int numSamples;

    int maxReward = 500_000;
    int minReward = -500_000;

    /**
     *
     */
    public int[] nTuplesLenght;

    /**
     *
     */
    public NTupleSeriousScore() {
        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 52_488;
        neuronQuantityInLayer[1] = 1;
        neuronQuantityInLayer[2] = 1;

        numSamples = 8;
        nTuplesLenght = new int[numSamples];
        for ( int i = 0; i < numSamples; i++ ) {
            nTuplesLenght[i] = 4;
        }

        int maxTile = 9;
        this.allSamplePointStates = new ArrayList<>();
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointStates.add(new Tile(i));
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationLinear();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxReward, minReward, activationFunctionMax, activationFunctionMin);
    }

    private int calculateIndex(int nTupleSampleIndex, SamplePointState[] nTupleSample) {
        int index = 0;
        for ( int j = 0; j < nTuplesLenght[nTupleSampleIndex]; j++ ) {
//            SamplePointState object = ntuple[j];
//            Integer sampleIndex = mapSamplePointStates.get(object);
//            int size = mapSamplePointStates.size();
//            int pow = (int) Math.pow(size, j);
            index += mapSamplePointStates.get(nTupleSample[j])
                    * (int) Math.pow(mapSamplePointStates.size(), j);
        }
        return index;
    }

    /**
     *
     * @param board
     * @param normalizedPerceptronInput <p>
     */
    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        for ( int i = 0; i < numSamples; i++ ) {
            normalizedPerceptronInput.add(calculateIndex(i, getNTuple(board, i)), 1d);
        }
    }

    public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
        switch ( nTupleIndex ) {
            // verticales
            case 0: {
                SamplePointState[] sample
                        = {board.tileAt(0, 0),
                            board.tileAt(0, 1),
                            board.tileAt(0, 2),
                            board.tileAt(0, 3)};
                return sample;
            }
            case 1: {
                SamplePointState[] sample
                        = {board.tileAt(1, 0),
                            board.tileAt(1, 1),
                            board.tileAt(1, 2),
                            board.tileAt(1, 3)};
                return sample;
            }
            case 2: {
                SamplePointState[] sample
                        = {board.tileAt(2, 0),
                            board.tileAt(2, 1),
                            board.tileAt(2, 2),
                            board.tileAt(2, 3)};
                return sample;
            }
            case 3: {
                SamplePointState[] sample
                        = {board.tileAt(3, 0),
                            board.tileAt(3, 1),
                            board.tileAt(3, 2),
                            board.tileAt(3, 3)};
                return sample;
            }
            // horizontales
            case 4: {
                SamplePointState[] sample
                        = {board.tileAt(0, 0),
                            board.tileAt(1, 0),
                            board.tileAt(2, 0),
                            board.tileAt(3, 0)};
                return sample;
            }
            case 5: {
                SamplePointState[] sample
                        = {board.tileAt(0, 1),
                            board.tileAt(1, 1),
                            board.tileAt(2, 1),
                            board.tileAt(3, 1)};
                return sample;
            }
            case 6: {
                SamplePointState[] sample
                        = {board.tileAt(0, 2),
                            board.tileAt(1, 2),
                            board.tileAt(2, 2),
                            board.tileAt(3, 2)};
                return sample;
            }
            case 7: {
                SamplePointState[] sample
                        = {board.tileAt(0, 3),
                            board.tileAt(1, 3),
                            board.tileAt(2, 3),
                            board.tileAt(3, 3)};
                return sample;
            }
            default: {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    @Override
    public Double computeNumericRepresentationFor(Game2048 game, Object[] output) {
        assert output.length == 1;
        return (Double) output[0];
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return normOutput.deNormalize((Double) value);
    }

    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        return board.getPartialScore();
    }

    @Override
    public double getFinalReward(GameBoard board, int outputNeuron) {
        return board.getGame().getScore();
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

    /**
     * Encriptamos el tablero para relacionar patrones y relaciones entre
     * posiciones del tablero de a 4 baldosas
     * <p>
     * @param tileCode1
     * @param tileCode2
     * @param tileCode3
     * @param tileCode4 <p>
     * @return
     */
    private int encryptTiles(int tileCode1, int tileCode2, int tileCode3, int tileCode4) {
        return tileCode1 * 1_000_000 + tileCode2 * 10_000 + tileCode3 * 100 + tileCode4;
    }

    @Override
    public boolean useNTupleList() {
        return true;
    }
}
