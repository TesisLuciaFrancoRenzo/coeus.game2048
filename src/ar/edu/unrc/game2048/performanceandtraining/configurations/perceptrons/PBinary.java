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
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class PBinary<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    private final static int BINARY_LENGHT = 4; //alcanza para escribir el 2048
    private final boolean concurrenInput;
    private final int maxScore;
    private final int minScore;

    /**
     *
     */
    public PBinary() {
        maxScore = 500_000;
        minScore = -500_000;

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 64;
        neuronQuantityInLayer[1] = 128;
        neuronQuantityInLayer[2] = 1;

        concurrenInput = false;

        this.activationFunctionForEncog = new ActivationFunction[2];
        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax,
                activationFunctionMin);
    }

    /**
     *
     * @param board
     * @param normalizedPerceptronInput <p>
     */
    @Override
    public void calculateNormalizedPerceptronInput(
            GameBoard<NeuralNetworkClass> board,
            List<Double> normalizedPerceptronInput) {
        Tile[] tiles = board.getTiles();
        int currentNeuron = 0;
        for ( Tile tile : tiles ) {
            String bits = Integer.toBinaryString(tile.getCode());
            for ( int k = 0; k < BINARY_LENGHT - bits.length(); k++ ) {
                normalizedPerceptronInput.set(currentNeuron,
                        activationFunctionMin);
                currentNeuron++;
            }
            for ( int j = 0; j < bits.length(); j++ ) {
                if ( bits.charAt(j) == '0' ) {
                    normalizedPerceptronInput.set(currentNeuron,
                            activationFunctionMin);
                } else {
                    normalizedPerceptronInput.set(currentNeuron,
                            activationFunctionMax);
                }
                currentNeuron++;
            }
        }
        assert currentNeuron == this.getNeuronQuantityInLayer()[0];
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
     *
     * @return
     */
    @Override
    public boolean isConcurrentInputEnabled() {
        return concurrenInput;
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean useNTupleList() {
        return false;
    }

}
