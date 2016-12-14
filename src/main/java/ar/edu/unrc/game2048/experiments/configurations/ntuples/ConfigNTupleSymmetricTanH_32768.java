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
package ar.edu.unrc.game2048.experiments.configurations.ntuples;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.coeus.tdlearning.utils.FunctionUtils;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.Tile;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.util.ArrayList;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ConfigNTupleSymmetricTanH_32768
        extends NTupleConfiguration2048 {

    private static final int maxReward = 500_000;
    private static final int minReward = -500_000;

    /**
     * Configuración para jugar hasta 32.768 con tablero simétrico, con función de activación Tangente Hiperbólica, y
     * puntaje parcial.
     */
    public
    ConfigNTupleSymmetricTanH_32768() {
        setTileToWinForTraining(32768);

        activationFunction = FunctionUtils.TANH;
        derivedActivationFunction = FunctionUtils.TANH_DERIVED;
        concurrency = false;
        double activationFunctionMax = 1;
        double activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize, null, maxReward, minReward, activationFunctionMax, activationFunctionMin);

        nTuplesLength = new int[4];
        nTuplesLength[0] = 6;
        nTuplesLength[1] = 6;
        nTuplesLength[2] = 4;
        nTuplesLength[3] = 4;

        int maxTile = 15;
        allSamplePointPossibleValues = new ArrayList<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointPossibleValues.add(new Tile(i));
        }
    }

    /**
     * @return
     *
     * @throws CloneNotSupportedException
     */
    @Override
    public
    Object clone()
            throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public
    double deNormalizeValueFromNeuralNetworkOutput( Object value ) {
        return normOutput.deNormalize((double) value);
    }

    @Override
    public
    double getBoardReward(
            GameBoard board,
            int outputNeuron
    ) {
        return board.getPartialScore();
    }

    @Override
    public
    SamplePointValue[] getNTuple(
            GameBoard board,
            int nTupleIndex
    ) {
        switch ( nTupleIndex ) {
            // rectángulos
            case 0: {
                return new SamplePointValue[] {
                        board.tileAt(0, 0), board.tileAt(0, 1), board.tileAt(0, 2), board.tileAt(1, 0), board.tileAt(1, 1), board.tileAt(1, 2)
                };
            }
            case 1: {
                return new SamplePointValue[] {
                        board.tileAt(1, 0), board.tileAt(1, 1), board.tileAt(1, 2), board.tileAt(2, 0), board.tileAt(2, 1), board.tileAt(2, 2)
                };
            }
            // verticales
            case 2: {
                return new SamplePointValue[] { board.tileAt(2, 0), board.tileAt(2, 1), board.tileAt(2, 2), board.tileAt(2, 3) };
            }
            case 3: {
                return new SamplePointValue[] { board.tileAt(3, 0), board.tileAt(3, 1), board.tileAt(3, 2), board.tileAt(3, 3) };
            }
            default: {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    @Override
    public
    double normalizeValueToPerceptronOutput( Object value ) {
        if ( (Double) value > maxReward ) {
            throw new IllegalArgumentException("value no puede ser mayor a maxReward=" + maxReward);
        }
        return normOutput.normalize((Double) value);
    }
}
