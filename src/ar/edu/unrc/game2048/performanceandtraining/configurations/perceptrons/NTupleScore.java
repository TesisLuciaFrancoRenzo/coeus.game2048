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
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class NTupleScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    /**
     *
     */
    public int maxCodedBoardnumber = 11_111_111; //2048 maximo

    /**
     *
     */
    public int maxScore = 100_000;

    /**
     *
     */
    public int minCodedBoardnumber = 0;

    /**
     *
     */
    public int minScore = 0;

    /**
     *
     */
    public NTupleScore() {
        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 17;
        neuronQuantityInLayer[1] = 17;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationSigmoid();
        activationFunctionForEncog[1] = new ActivationSigmoid();

        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardnumber, minCodedBoardnumber, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    /**
     *
     * @param board
     * @param normalizedPerceptronInput <p>
     * @return
     */
    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        // verticales
        normalizedPerceptronInput.set(0,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 0).getCode(),
                        board.tileAt(0, 1).getCode(),
                        board.tileAt(0, 2).getCode(),
                        board.tileAt(0, 3).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(1,
                normInput.normalize(encryptTiles(
                        board.tileAt(1, 0).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(1, 3).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(2,
                normInput.normalize(encryptTiles(
                        board.tileAt(2, 0).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(2, 3).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(3,
                normInput.normalize(encryptTiles(
                        board.tileAt(3, 0).getCode(),
                        board.tileAt(3, 1).getCode(),
                        board.tileAt(3, 2).getCode(),
                        board.tileAt(3, 3).getCode()
                )
                )
        );
        // horizontales
        normalizedPerceptronInput.set(4,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 0).getCode(),
                        board.tileAt(1, 0).getCode(),
                        board.tileAt(2, 0).getCode(),
                        board.tileAt(3, 0).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(5,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 1).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(3, 1).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(6,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 2).getCode(),
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(3, 2).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(7,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 3).getCode(),
                        board.tileAt(1, 3).getCode(),
                        board.tileAt(2, 3).getCode(),
                        board.tileAt(3, 3).getCode()
                )
                )
        );
        // cuadrados
        normalizedPerceptronInput.set(8,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 0).getCode(),
                        board.tileAt(0, 1).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(1, 0).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(9,
                normInput.normalize(encryptTiles(
                        board.tileAt(1, 0).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(2, 0).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(10,
                normInput.normalize(encryptTiles(
                        board.tileAt(2, 0).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(3, 1).getCode(),
                        board.tileAt(3, 0).getCode()
                )
                )
        );
        //segunda fila de rectangulos
        normalizedPerceptronInput.set(11,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 1).getCode(),
                        board.tileAt(0, 2).getCode(),
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(1, 1).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(12,
                normInput.normalize(encryptTiles(
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(2, 1).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(13,
                normInput.normalize(encryptTiles(
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(3, 2).getCode(),
                        board.tileAt(3, 1).getCode()
                )
                )
        );
        //tercera fila de rectangulos
        normalizedPerceptronInput.set(14,
                normInput.normalize(encryptTiles(
                        board.tileAt(0, 2).getCode(),
                        board.tileAt(0, 3).getCode(),
                        board.tileAt(1, 3).getCode(),
                        board.tileAt(1, 2).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(15,
                normInput.normalize(encryptTiles(
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(1, 3).getCode(),
                        board.tileAt(2, 3).getCode(),
                        board.tileAt(2, 2).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(16,
                normInput.normalize(encryptTiles(
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(2, 3).getCode(),
                        board.tileAt(3, 3).getCode(),
                        board.tileAt(3, 2).getCode()
                )
                )
        );
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

}
