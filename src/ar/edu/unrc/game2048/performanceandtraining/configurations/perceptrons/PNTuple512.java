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

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointState;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class PNTuple512<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    private final List<SamplePointState> allSamplePointStates;
    private final boolean concurrenInput;
    private final HashMap<SamplePointState, Integer> mapSamplePointStates;
    private final int maxReward = 20_000;
    private final int maxTile;
    private final int minReward = -20_000;
    private final int[] nTuplesLenght;
    private final int[] nTuplesWeightQuantityIndex;
    private final int numSamples;
    private final boolean useNTupleList;

    /**
     *
     */
    public PNTuple512() {
        numSamples = 8;
        maxTile = 9;
        concurrenInput = true;
        useNTupleList = true;

        nTuplesLenght = new int[numSamples];
        for ( int i = 0; i < numSamples; i++ ) {
            nTuplesLenght[i] = 4;
        }

        this.allSamplePointStates = new ArrayList<>();
        this.mapSamplePointStates = new HashMap<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointStates.add(new Tile(i));
            mapSamplePointStates.put(allSamplePointStates.get(i), i);
        }

        nTuplesWeightQuantityIndex = new int[nTuplesLenght.length];
        int lasNTuplesWeightQuantity = 0;
        nTuplesWeightQuantityIndex[0] = lasNTuplesWeightQuantity;
        int lutSize = 0;
        for ( int i = 0; i < nTuplesLenght.length; i++ ) {
            int nTuplesWeightQuantity = (int) Math.pow(mapSamplePointStates.
                    size(), nTuplesLenght[i]);
            lutSize += nTuplesWeightQuantity;
            if ( i > 0 ) {
                nTuplesWeightQuantityIndex[i] = nTuplesWeightQuantityIndex[i - 1] + lasNTuplesWeightQuantity;
            }
            lasNTuplesWeightQuantity = nTuplesWeightQuantity;
        }
        this.neuronQuantityInLayer = new int[2];
        neuronQuantityInLayer[1] = 1;
        neuronQuantityInLayer[0] = lutSize;

        this.activationFunctionForEncog = new ActivationFunction[1];
        activationFunctionForEncog[0] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxReward, minReward, activationFunctionMax,
                activationFunctionMin);
    }

    /**
     *
     * @param nTupleSampleIndex
     * @param nTupleSample
     *
     * @return
     */
    public int calculateIndex(int nTupleSampleIndex,
            SamplePointState[] nTupleSample) {
        int localIndex = 0;
        for ( int j = 0; j < getnTuplesLenght()[nTupleSampleIndex]; j++ ) {
//            SamplePointState object = ntuple[j];
//            Integer sampleIndex = mapSamplePointStates.get(object);
//            int size = mapSamplePointStates.size();
//            int pow = (int) Math.pow(size, j);
            localIndex += getMapSamplePointStates().get(nTupleSample[j])
                    * (int) Math.pow(getMapSamplePointStates().size(), j);
        }
        return getnTuplesWeightQuantityIndex()[nTupleSampleIndex] + localIndex;
    }

    @Override
    public void calculateNormalizedPerceptronInput(
            GameBoard<NeuralNetworkClass> board,
            List<Double> normalizedPerceptronInput) {
        for ( int i = 0; i < getNumSamples(); i++ ) {
            normalizedPerceptronInput.
                    add(calculateIndex(i, getNTuple(board, i)), 1d);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double computeNumericRepresentationFor(Game2048 game,
            Object[] output) {
        assert output.length == 1;
        return (Double) output[0];
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return normOutput.deNormalize((Double) value);
    }

    /**
     * @return the allSamplePointStates
     */
    public List<SamplePointState> getAllSamplePointStates() {
        return allSamplePointStates;
    }

    @Override
    public double getBoardReward(GameBoard board,
            int outputNeuron) {
        return board.getPartialScore();
    }

    @Override
    public double getFinalReward(GameBoard board,
            int outputNeuron) {
        return board.getGame().getScore();
    }

    /**
     * @return the mapSamplePointStates
     */
    public HashMap<SamplePointState, Integer> getMapSamplePointStates() {
        return mapSamplePointStates;
    }

    /**
     * @return the maxTile
     */
    public int getMaxTile() {
        return maxTile;
    }

    public SamplePointState[] getNTuple(GameBoard board,
            int nTupleIndex) {
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

    /**
     * @return numero de muestras.
     */
    public int getNumSamples() {
        return numSamples;
    }

    /**
     * @return longitud de las ntuplas.
     */
    public int[] getnTuplesLenght() {
        return nTuplesLenght;
    }

    /**
     * @return nTuplesWeightQuantityIndex
     */
    public int[] getnTuplesWeightQuantityIndex() {
        return nTuplesWeightQuantityIndex;
    }

    @Override
    public boolean isConcurrentInputEnabled() {
        return concurrenInput;
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

    @Override
    public boolean useNTupleList() {
        return useNTupleList;
    }
}
