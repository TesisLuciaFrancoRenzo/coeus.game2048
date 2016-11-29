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
import ar.edu.unrc.game2048.NeuralNetworkConfiguration2048;
import ar.edu.unrc.game2048.Tile;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.util.List;

/**
 * @param <NeuralNetworkClass>
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class PBinary_2048<NeuralNetworkClass>
        extends NeuralNetworkConfiguration2048<NeuralNetworkClass> {

    private final static int BINARY_LENGTH = 4; //alcanza para escribir el 2048
    private final boolean concurrentInput;

    /**
     * Configuración para jugar hasta 2048, tablero de tipo binario, con función de activación Tangente Hiperbólica, y
     * puntaje parcial.
     */
    public
    PBinary_2048() {
        int maxScore = 500_000;
        int minScore = -500_000;

        neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 64;
        neuronQuantityInLayer[1] = 128;
        neuronQuantityInLayer[2] = 1;

        concurrentInput = false;

        activationFunctionForEncog = new ActivationFunction[2];
        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normOutput = new NormalizedField(NormalizationAction.Normalize, null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    @Override
    public
    void calculateNormalizedPerceptronInput(
            GameBoard<NeuralNetworkClass> board,
            List<Double> normalizedPerceptronInput
    ) {
        Tile[] tiles         = board.getTiles();
        int    currentNeuron = 0;
        for (Tile tile : tiles) {
            String bits = Integer.toBinaryString(tile.getCode());
            for (int k = 0; k < BINARY_LENGTH - bits.length(); k++) {
                normalizedPerceptronInput.set(currentNeuron, activationFunctionMin);
                currentNeuron++;
            }
            for (int j = 0; j < bits.length(); j++) {
                if (bits.charAt(j) == '0') {
                    normalizedPerceptronInput.set(currentNeuron, activationFunctionMin);
                } else {
                    normalizedPerceptronInput.set(currentNeuron, activationFunctionMax);
                }
                currentNeuron++;
            }
        }
        assert currentNeuron == getNeuronQuantityInLayer()[0];
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
    Double computeNumericRepresentationFor(
            Game2048 game,
            Object[] output
    ) {
        assert output.length == 1;
        return (Double) output[0];
    }

    @Override
    public
    double deNormalizeValueFromNeuralNetworkOutput(Object value) {
        return normOutput.deNormalize((Double) value);
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
    boolean isConcurrentInputEnabled() {
        return concurrentInput;
    }

    @Override
    public
    double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

    @Override
    public
    boolean useNTupleList() {
        return false;
    }

}
