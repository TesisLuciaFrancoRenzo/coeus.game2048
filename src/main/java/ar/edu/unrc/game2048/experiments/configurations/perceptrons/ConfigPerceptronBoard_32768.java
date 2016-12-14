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

import ar.edu.unrc.game2048.EncogConfiguration2048;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

import java.util.List;

/**
 * Configuración para jugar hasta 32768, tablero de tipo directo (una neurona = un Tile), con función de activación
 * Tangente Hiperbólica, y puntaje parcial.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ConfigPerceptronBoard_32768
        extends EncogConfiguration2048 {

    private final boolean concurrentInput;

    /**
     * @param hasBias
     */
    public
    ConfigPerceptronBoard_32768( final Boolean hasBias ) {
        this.hasBias = hasBias;
        setTileToWinForTraining(32768);

        int maxCodedBoardNumber = 15;
        int minCodedBoardNumber = 0;
        int maxScore            = 500_000;
        int minScore            = -500_000;

        neuronQuantityInLayer = new int[4];
        neuronQuantityInLayer[0] = 16;
        neuronQuantityInLayer[1] = 5_000;
        neuronQuantityInLayer[2] = 5_000;
        neuronQuantityInLayer[3] = 1;

        concurrentInput = false;

        activationFunctionForEncog = new ActivationFunction[3];

        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationTANH();
        activationFunctionForEncog[2] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                                        null,
                                        maxCodedBoardNumber,
                                        minCodedBoardNumber,
                                        activationFunctionMax,
                                        activationFunctionMin
        );
        normOutput = new NormalizedField(NormalizationAction.Normalize, null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    @Override
    public
    void calculateNormalizedPerceptronInput(
            GameBoard board,
            List< Double > normalizedPerceptronInput
    ) {
        // primera fila
        normalizedPerceptronInput.set(0, normInput.normalize(board.tileAt(0, 0).getCode()));
        normalizedPerceptronInput.set(1, normInput.normalize(board.tileAt(0, 1).getCode()));
        normalizedPerceptronInput.set(2, normInput.normalize(board.tileAt(0, 2).getCode()));
        normalizedPerceptronInput.set(3, normInput.normalize(board.tileAt(0, 3).getCode()));
        // segunda fila
        normalizedPerceptronInput.set(4, normInput.normalize(board.tileAt(1, 0).getCode()));
        normalizedPerceptronInput.set(5, normInput.normalize(board.tileAt(1, 1).getCode()));
        normalizedPerceptronInput.set(6, normInput.normalize(board.tileAt(1, 2).getCode()));
        normalizedPerceptronInput.set(7, normInput.normalize(board.tileAt(1, 3).getCode()));
        // tercera fila
        normalizedPerceptronInput.set(8, normInput.normalize(board.tileAt(2, 0).getCode()));
        normalizedPerceptronInput.set(9, normInput.normalize(board.tileAt(2, 1).getCode()));
        normalizedPerceptronInput.set(10, normInput.normalize(board.tileAt(2, 2).getCode()));
        normalizedPerceptronInput.set(11, normInput.normalize(board.tileAt(2, 3).getCode()));
        // cuarta fila
        normalizedPerceptronInput.set(12, normInput.normalize(board.tileAt(3, 0).getCode()));
        normalizedPerceptronInput.set(13, normInput.normalize(board.tileAt(3, 1).getCode()));
        normalizedPerceptronInput.set(14, normInput.normalize(board.tileAt(3, 2).getCode()));
        normalizedPerceptronInput.set(15, normInput.normalize(board.tileAt(3, 3).getCode()));
    }

    /**
     * @return @throws CloneNotSupportedException
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
    double deNormalizeValueFromNeuralNetworkOutput( Object value ) {
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
    double normalizeValueToPerceptronOutput( Object value ) {
        return normOutput.normalize((Double) value);
    }

    @Override
    public
    boolean useNTupleList() {
        return false;
    }
}
