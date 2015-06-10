/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PartialScore;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import ar.edu.unrc.tdlearning.perceptron.training.FunctionUtils;
import java.util.ArrayList;

/**
 *
 * @author Franco
 */
public class BasicMaxTileLinear extends NTupleConfiguration2048 {

    int maxReward = 11; // tile 2048
    int minReward = 0;

    /**
     *
     */
    public BasicMaxTileLinear() {
        this.activationFunction = FunctionUtils.linear;
        this.derivatedActivationFunction = FunctionUtils.derivatedLinear;

        nTuplesLenght = new int[17];
        for ( int i = 0; i < 17; i++ ) {
            nTuplesLenght[i] = 4;
        }

        int maxTile = 11;
        this.allSamplePointStates = new ArrayList<>();
        for ( int i = 0; i <= maxTile; i++ ) {
            allSamplePointStates.add(new Tile(i));
        }
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
            // cuadrados
            // primera fila de rectangulos
            case 8: {
                SamplePointState[] sample
                        = {board.tileAt(0, 0),
                            board.tileAt(0, 1),
                            board.tileAt(1, 1),
                            board.tileAt(1, 0)};
                return sample;
            }
            case 9: {
                SamplePointState[] sample
                        = {board.tileAt(1, 0),
                            board.tileAt(1, 1),
                            board.tileAt(2, 1),
                            board.tileAt(2, 0)};
                return sample;
            }
            case 10: {
                SamplePointState[] sample
                        = {board.tileAt(2, 0),
                            board.tileAt(2, 1),
                            board.tileAt(3, 1),
                            board.tileAt(3, 0)};
                return sample;
            }
            //segunda fila de rectangulos
            case 11: {
                SamplePointState[] sample
                        = {board.tileAt(0, 1),
                            board.tileAt(0, 2),
                            board.tileAt(1, 2),
                            board.tileAt(1, 1)};
                return sample;
            }
            case 12: {
                SamplePointState[] sample
                        = {board.tileAt(1, 1),
                            board.tileAt(1, 2),
                            board.tileAt(2, 2),
                            board.tileAt(2, 1)};
                return sample;
            }
            case 13: {
                SamplePointState[] sample
                        = {board.tileAt(2, 1),
                            board.tileAt(2, 2),
                            board.tileAt(3, 2),
                            board.tileAt(3, 1)};
                return sample;
            }
            //tercera fila de rectangulos
            case 14: {
                SamplePointState[] sample
                        = {board.tileAt(0, 2),
                            board.tileAt(0, 3),
                            board.tileAt(1, 3),
                            board.tileAt(1, 2)};
                return sample;
            }
            case 15: {
                SamplePointState[] sample
                        = {board.tileAt(1, 2),
                            board.tileAt(1, 3),
                            board.tileAt(2, 3),
                            board.tileAt(2, 2)};
                return sample;
            }
            case 16: {
                SamplePointState[] sample
                        = {board.tileAt(2, 2),
                            board.tileAt(2, 3),
                            board.tileAt(3, 3),
                            board.tileAt(3, 2)};
                return sample;
            }

            default: {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
    }

    @Override
    public PartialScore getCurrentReward(Game2048 game) {
        if ( game.iLoose() || game.iWin() ) {
            return new PartialScore(game.getMaxNumberCode());
        } else {
            return new PartialScore(0);
        }
    }

    @Override
    public PartialScore getBoardReward(GameBoard board) {
        return new PartialScore(0);
    }

    /**
     *
     * @param data <p>
     * @return
     */
    @Override
    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double data) {
        return () -> {
            //assert data != Double.NaN;
            return (int) Math.round(data);
        };
    }

    @Override
    public double translateRewardToNormalizedPerceptronOutput(GameBoard board) { //TODO si esta correcto el cambio hacer que se pida al GAME y no al BOARD la reward
        if ( !board.isTerminalState() ) {
            return 0;
        } else {
            int reward = board.getMaxTileNumberCode();
            if ( reward > maxReward ) {
                throw new IllegalArgumentException("score supera el maximo de " + maxReward + " con el valor " + reward);
            }
            return reward;
        }
    }

}
