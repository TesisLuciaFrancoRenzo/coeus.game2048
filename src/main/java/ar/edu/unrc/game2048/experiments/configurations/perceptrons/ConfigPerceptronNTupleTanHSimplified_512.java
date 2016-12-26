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
package ar.edu.unrc.game2048.experiments.configurations.perceptrons;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.game2048.experiments.configurations.EncogConfiguration2048;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Configuración para jugar hasta 512, tablero de tipo NTupla, con función de activación Tangente Hiperbólica, y puntaje
 * parcial.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ConfigPerceptronNTupleTanHSimplified_512
        extends EncogConfiguration2048 {

    private final List< SamplePointValue >             allSamplePointPossibleValues;
    private final boolean                              concurrentInput;
    private final HashMap< SamplePointValue, Integer > mapSamplePointValuesIndex;
    private final int                                  maxTile;
    private final int[]                                nTuplesLength;
    private final int[]                                nTuplesWeightQuantityIndex;
    private final int                                  numSamples;
    private final boolean                              useNTupleList;

    /**
     * @param hasBias
     */
    public
    ConfigPerceptronNTupleTanHSimplified_512( final Boolean hasBias ) {
        this.hasBias = hasBias;
        setTileToWinForTraining(512);

        final int minReward = -20_000;
        final int maxReward = 20_000;
        numSamples = 8;
        maxTile = 9;
        concurrentInput = true;
        useNTupleList = true;

        nTuplesLength = new int[numSamples];
        for ( int i = 0; i < numSamples; i++ ) {
            nTuplesLength[i] = 4;
        }

        allSamplePointPossibleValues = new ArrayList<>();
        mapSamplePointValuesIndex = new HashMap<>();
        allSamplePointPossibleValues.add(null);
        mapSamplePointValuesIndex.put(null, 0);
        for ( int spvIndex = 1; spvIndex <= maxTile; spvIndex++ ) {
            allSamplePointPossibleValues.add(new Tile((int) Math.pow(2, spvIndex)));
            mapSamplePointValuesIndex.put(allSamplePointPossibleValues.get(spvIndex), spvIndex);
        }

        nTuplesWeightQuantityIndex = new int[nTuplesLength.length];
        int lastNTuplesWeightQuantity = 0;
        nTuplesWeightQuantityIndex[0] = lastNTuplesWeightQuantity;
        int lutSize = 0;
        for ( int nTupleIndex = 0; nTupleIndex < nTuplesLength.length; nTupleIndex++ ) {
            final int nTuplesWeightQuantity = (int) Math.pow(mapSamplePointValuesIndex.size(), nTuplesLength[nTupleIndex]);
            lutSize += nTuplesWeightQuantity;
            if ( nTupleIndex > 0 ) {
                nTuplesWeightQuantityIndex[nTupleIndex] = nTuplesWeightQuantityIndex[nTupleIndex - 1] + lastNTuplesWeightQuantity;
            }
            lastNTuplesWeightQuantity = nTuplesWeightQuantity;
        }
        neuronQuantityInLayer = new int[2];
        neuronQuantityInLayer[1] = 1;
        neuronQuantityInLayer[0] = lutSize;

        activationFunctionForEncog = new ActivationFunction[1];
        activationFunctionForEncog[0] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize, null, maxReward, minReward, activationFunctionMax, activationFunctionMin);
    }

    /**
     * @param nTupleIndex
     * @param nTuple
     *
     * @return
     */
    public
    int calculateIndex(
            final int nTupleIndex,
            final SamplePointValue[] nTuple
    ) {
        return nTuplesWeightQuantityIndex[nTupleIndex] +
               NTupleSystem.calculateLocalIndex(nTupleIndex, nTuplesLength, nTuple, mapSamplePointValuesIndex);
    }

    @Override
    public
    void calculateNormalizedPerceptronInput(
            final GameBoard board,
            final List< Double > normalizedPerceptronInput
    ) {
        for ( int i = 0; i < numSamples; i++ ) {
            normalizedPerceptronInput.add(calculateIndex(i, getNTuple(board, i)), 1.0d);
        }
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    ConfigPerceptronNTupleTanHSimplified_512 clone()
            throws CloneNotSupportedException {
        return (ConfigPerceptronNTupleTanHSimplified_512) super.clone();
    }

    @Override
    public
    Double computeNumericRepresentationFor(
            final Game2048 game,
            final Object[] output
    ) {
        assert output.length == 1;
        return (Double) output[0];
    }

    @Override
    public
    double deNormalizeValueFromNeuralNetworkOutput( final Object value ) {
        return normOutput.deNormalize((Double) value);
    }

    /**
     * @return allSamplePointPossibleValues
     */
    public
    List< SamplePointValue > getAllSamplePointPossibleValues() {
        return allSamplePointPossibleValues;
    }

    /**
     * @return mapSamplePointValuesIndex
     */
    public
    HashMap< SamplePointValue, Integer > getMapSamplePointValuesIndex() {
        return mapSamplePointValuesIndex;
    }

    /**
     * @return maxTile
     */
    public
    int getMaxTile() {
        return maxTile;
    }

    /**
     * @param board
     * @param nTupleIndex
     *
     * @return
     */
    public
    SamplePointValue[] getNTuple(
            final GameBoard board,
            final int nTupleIndex
    ) {
        final Tile[][] tiles = board.getTiles();
        switch ( nTupleIndex ) {
            // verticales
            case 0:
                return new SamplePointValue[] { tiles[0][0], tiles[0][1], tiles[0][2], tiles[0][3] };
            case 1:
                return new SamplePointValue[] { tiles[1][0], tiles[1][1], tiles[1][2], tiles[1][3] };
            case 2:
                return new SamplePointValue[] { tiles[2][0], tiles[2][1], tiles[2][2], tiles[2][3] };
            case 3:
                return new SamplePointValue[] { tiles[3][0], tiles[3][1], tiles[3][2], tiles[3][3] };
            // horizontales
            case 4:
                return new SamplePointValue[] { tiles[0][0], tiles[1][0], tiles[2][0], tiles[3][0] };
            case 5:
                return new SamplePointValue[] { tiles[0][1], tiles[1][1], tiles[2][1], tiles[3][1] };
            case 6:
                return new SamplePointValue[] { tiles[0][2], tiles[1][2], tiles[2][2], tiles[3][2] };
            case 7:
                return new SamplePointValue[] { tiles[0][3], tiles[1][3], tiles[2][3], tiles[3][3] };
            default:
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     * @return longitud de las nTuplas.
     */
    public
    int[] getNTuplesLength() {
        return nTuplesLength;
    }

    /**
     * @return nTuplesWeightQuantityIndex
     */
    public
    int[] getNTuplesWeightQuantityIndex() {
        return nTuplesWeightQuantityIndex;
    }

    /**
     * @return numero de muestras.
     */
    public
    int getNumSamples() {
        return numSamples;
    }

    @Override
    public
    boolean isConcurrentInputEnabled() {
        return concurrentInput;
    }

    @Override
    public
    double normalizeValueToPerceptronOutput( final Object value ) {
        return normOutput.normalize((Double) value);
    }

    @Override
    public
    boolean useNTupleList() {
        return useNTupleList;
    }
}
