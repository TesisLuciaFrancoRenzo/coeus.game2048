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
package ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.coeus.tdlearning.utils.FunctionUtils;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.Tile;

import java.util.ArrayList;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NBasicLinearNoPartialScore_512
        extends NTupleConfiguration2048 {

    /**
     * Configuración para jugar 512, con función de activación Lineal, sin puntaje parcial.
     */
    public
    NBasicLinearNoPartialScore_512() {
        this.activationFunction = FunctionUtils.LINEAR;
        this.derivedActivationFunction = FunctionUtils.LINEAR_DERIVED;
        this.concurrency = false;
        int maxTile = 9;

        nTuplesLength = new int[17];
        for (int i = 0; i < 17; i++) {
            nTuplesLength[i] = 4;
        }

        this.allSamplePointPossibleValues = new ArrayList<>();
        for (int i = 0; i <= maxTile; i++) {
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
    double deNormalizeValueFromNeuralNetworkOutput(Object value) {
        return (double) value;
    }

    @Override
    public
    double getBoardReward(
            GameBoard board,
            int outputNeuron
    ) {
        return 0;
    }

    @Override
    public
    double getFinalReward(
            GameBoard board,
            int outputNeuron
    ) {
        return board.getGame().getScore();
    }

    @Override
    public
    SamplePointValue[] getNTuple(
            GameBoard board,
            int nTupleIndex
    ) {
        switch (nTupleIndex) {
            // verticales
            case 0: {
                return new SamplePointValue[]{board.tileAt(0, 0), board.tileAt(0, 1), board.tileAt(0, 2), board.tileAt(0, 3)};
            }
            case 1: {
                return new SamplePointValue[]{board.tileAt(1, 0), board.tileAt(1, 1), board.tileAt(1, 2), board.tileAt(1, 3)};
            }
            case 2: {
                return new SamplePointValue[]{board.tileAt(2, 0), board.tileAt(2, 1), board.tileAt(2, 2), board.tileAt(2, 3)};
            }
            case 3: {
                return new SamplePointValue[]{board.tileAt(3, 0), board.tileAt(3, 1), board.tileAt(3, 2), board.tileAt(3, 3)};
            }
            // horizontales
            case 4: {
                return new SamplePointValue[]{board.tileAt(0, 0), board.tileAt(1, 0), board.tileAt(2, 0), board.tileAt(3, 0)};
            }
            case 5: {
                return new SamplePointValue[]{board.tileAt(0, 1), board.tileAt(1, 1), board.tileAt(2, 1), board.tileAt(3, 1)};
            }
            case 6: {
                return new SamplePointValue[]{board.tileAt(0, 2), board.tileAt(1, 2), board.tileAt(2, 2), board.tileAt(3, 2)};
            }
            case 7: {
                return new SamplePointValue[]{board.tileAt(0, 3), board.tileAt(1, 3), board.tileAt(2, 3), board.tileAt(3, 3)};
            }
            // cuadrados
            // primera fila de rectangulos
            case 8: {
                return new SamplePointValue[]{board.tileAt(0, 0), board.tileAt(0, 1), board.tileAt(1, 1), board.tileAt(1, 0)};
            }
            case 9: {
                return new SamplePointValue[]{board.tileAt(1, 0), board.tileAt(1, 1), board.tileAt(2, 1), board.tileAt(2, 0)};
            }
            case 10: {
                return new SamplePointValue[]{board.tileAt(2, 0), board.tileAt(2, 1), board.tileAt(3, 1), board.tileAt(3, 0)};
            }
            //segunda fila de rectangulos
            case 11: {
                return new SamplePointValue[]{board.tileAt(0, 1), board.tileAt(0, 2), board.tileAt(1, 2), board.tileAt(1, 1)};
            }
            case 12: {
                return new SamplePointValue[]{board.tileAt(1, 1), board.tileAt(1, 2), board.tileAt(2, 2), board.tileAt(2, 1)};
            }
            case 13: {
                return new SamplePointValue[]{board.tileAt(2, 1), board.tileAt(2, 2), board.tileAt(3, 2), board.tileAt(3, 1)};
            }
            //tercera fila de rectangulos
            case 14: {
                return new SamplePointValue[]{board.tileAt(0, 2), board.tileAt(0, 3), board.tileAt(1, 3), board.tileAt(1, 2)};
            }
            case 15: {
                return new SamplePointValue[]{board.tileAt(1, 2), board.tileAt(1, 3), board.tileAt(2, 3), board.tileAt(2, 2)};
            }
            case 16: {
                return new SamplePointValue[]{board.tileAt(2, 2), board.tileAt(2, 3), board.tileAt(3, 3), board.tileAt(3, 2)};
            }

            default: {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    @Override
    public
    double normalizeValueToPerceptronOutput(Object value) {
        return (double) value;
    }

}
