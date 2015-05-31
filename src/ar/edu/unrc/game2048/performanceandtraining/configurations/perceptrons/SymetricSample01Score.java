/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import java.util.List;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author franco
 * @param <NeuralNetworkClass>
 */
public class SymetricSample01Score<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {
    public double maxCode2x3;

    public int maxCodeLines;
    public int maxScore;

    public double minCode2x3;
    public int minCodeLines;

    public int minScore;
    private final NormalizedField normInput2x3;

    public SymetricSample01Score() {
        maxCodeLines = 11_111_111; //2048 max
        minCodeLines = 0;
        maxCode2x3 = 111_111_111_111d; //2048
        minCode2x3 = 0d;
        maxScore = 500_000; //ver teoria
        minScore = 0;
        perceptron_hidden_quantity = 4;
        perceptron_input_quantity = 4;
        perceptron_output_quantity = 1;
        hiddenLayerQuantity = 1;
        activationFunctionHiddenForEncog = new ActivationTANH();
        activationFunctionOutputForEncog = new ActivationTANH();
        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodeLines, minCodeLines, activationFunctionMax, activationFunctionMin);
        normInput2x3 = new NormalizedField(NormalizationAction.Normalize,
                null, maxCode2x3, minCode2x3, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        //ultimas 2 columnas
        normalizedPerceptronInput.set(0,
                normInput.normalize(encryptTiles(
                        board.tileAt(2, 0).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(2, 2).getCode(),
                        board.tileAt(2, 3).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(1,
                normInput.normalize(encryptTiles(
                        board.tileAt(3, 0).getCode(),
                        board.tileAt(3, 1).getCode(),
                        board.tileAt(3, 2).getCode(),
                        board.tileAt(3, 3).getCode()
                )
                )
        );
        //primeros 2 rectangulos de 2x3
        normalizedPerceptronInput.set(2,
                normInput2x3.normalize(encryptTiles(
                        board.tileAt(0, 0).getCode(),
                        board.tileAt(0, 1).getCode(),
                        board.tileAt(0, 2).getCode(),
                        board.tileAt(1, 0).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(1, 2).getCode()
                )
                )
        );
        normalizedPerceptronInput.set(3,
                normInput2x3.normalize(encryptTiles(
                        board.tileAt(1, 0).getCode(),
                        board.tileAt(1, 1).getCode(),
                        board.tileAt(1, 2).getCode(),
                        board.tileAt(2, 0).getCode(),
                        board.tileAt(2, 1).getCode(),
                        board.tileAt(2, 2).getCode()
                )
                )
        );
        
    }

    @Override
    public double translatePerceptronOutputToPrediction(double[] data, double partialReward) {
        assert data[0] != Double.NaN;
        return Math.round(normOutput.deNormalize(data[0])) + partialReward;
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
        if ( outputNeuronIndex < 0 || outputNeuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + outputNeuronIndex);
        }
        return normOutput.normalize(board.getPartialScore());
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
    private double encryptTiles(int tileCode1, int tileCode2, int tileCode3, int tileCode4, int tileCode5, int tileCode6) {
        return tileCode6 * 10_000_000_000d + tileCode5 * 100_000_000d + tileCode4 * 1_000_000d + tileCode3 * 10_000d + tileCode2 * 100d + tileCode1;
    }
}
