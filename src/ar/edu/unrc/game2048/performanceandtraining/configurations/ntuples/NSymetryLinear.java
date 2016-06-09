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

import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.tdlearning.learning.FunctionUtils;
import ar.edu.unrc.tdlearning.training.ntuple.SamplePointState;
import java.util.ArrayList;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NSymetryLinear extends NTupleConfiguration2048 {

    /**
     *
     */
    public NSymetryLinear() {
        this.activationFunction = FunctionUtils.LINEAR;
        this.derivatedActivationFunction = FunctionUtils.LINEAR_DERIVATED;
        this.concurrency = false;
        int maxTile = 15;

        nTuplesLenght = new int[4];
        nTuplesLenght[0] = 6;
        nTuplesLenght[1] = 6;
        nTuplesLenght[2] = 4;
        nTuplesLenght[3] = 4;

        this.allSamplePointStates = new ArrayList<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointStates.add(new Tile(i));
        }
    }

    /**
     *
     * @param value <p>
     * @return
     */
    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return (double) value;
    }

    /**
     *
     * @param board
     * @param outputNeuron <p>
     * @return
     */
    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        return board.getPartialScore();
    }

    @Override
    public double getFinalReward(GameBoard board, int outputNeuron) {
        return board.getGame().getScore();
    }

    /**
     *
     * @param board
     * @param nTupleIndex <p>
     * @return
     */
    @Override
    public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
        switch ( nTupleIndex ) {
            // rectangulos
            case 0: {
                SamplePointState[] sample
                        = {board.tileAt(0, 0),
                            board.tileAt(0, 1),
                            board.tileAt(0, 2),
                            board.tileAt(1, 0),
                            board.tileAt(1, 1),
                            board.tileAt(1, 2)};
                return sample;
            }
            case 1: {
                SamplePointState[] sample
                        = {board.tileAt(1, 0),
                            board.tileAt(1, 1),
                            board.tileAt(1, 2),
                            board.tileAt(2, 0),
                            board.tileAt(2, 1),
                            board.tileAt(2, 2)};
                return sample;
            }
            // verticales
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
            default: {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    /**
     *
     * @param value <p>
     * @return
     */
    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return (double) value;
    }
}