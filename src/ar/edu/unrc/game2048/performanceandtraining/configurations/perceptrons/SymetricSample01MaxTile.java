/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
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
public class SymetricSample01MaxTile<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    /**
     *
     */
    public double maxCode2x3;

    /**
     *
     */
    public int maxCodeLines;

    /**
     *
     */
    public int maxCodedBoardOutputnumber;

    /**
     *
     */
    public double minCode2x3;

    /**
     *
     */
    public int minCodeLines;

    /**
     *
     */
    public int minCodedBoardOutputnumber;
    private final NormalizedField normInput2x3;

    /**
     *
     */
    public SymetricSample01MaxTile() {
        maxCodeLines = 11_111_111; //2048 max
        minCodeLines = 0;
        maxCode2x3 = 111_111_111_111d; //2048
        minCode2x3 = 0d;
        maxCodedBoardOutputnumber = 11; //2048 max
        minCodedBoardOutputnumber = 0;
        activationFunctionMax = 1;
        activationFunctionMin = 0;

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 4;
        neuronQuantityInLayer[1] = 8;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationSigmoid();
        activationFunctionForEncog[1] = new ActivationSigmoid();

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodeLines, minCodeLines, activationFunctionMax, activationFunctionMin);
        normInput2x3 = new NormalizedField(NormalizationAction.Normalize,
                null, maxCode2x3, minCode2x3, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardOutputnumber, minCodedBoardOutputnumber, activationFunctionMax, activationFunctionMin);
    }

    /**
     *
     * @param board
     * @param normalizedPerceptronInput
     * @return 
     */
    @Override
    public IsolatedComputation calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        return () -> {
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
            return null;
        };

    }
//
//    @Override
//    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data) {
//        return () -> {
//            assert data[0] != Double.NaN;
//            return (int) Math.round(normOutput.deNormalize(data[0]));
//        };
//    }
//
//    /**
//     *
//     * @param board
//     * @param neuronIndex <p>
//     * @return
//     */
//    @Override
//    public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
//
//        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
//        }
//        return normOutput.normalize(board.getMaxTileNumberCode());
//    }
//
//    /**
//     *
//     * @param board
//     * @param outputNeuronIndex <p>
//     * @return
//     */
//    @Override
//    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
//        return 0;
//    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Object[] output) {
        return () -> {
            assert output.length == 1;
            return (Double) output[0];
        };
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        return normOutput.deNormalize((Double) value);
    }

    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        return 0d;
    }

    @Override
    public double getFinalReward(Game2048 game, int outputNeuron) {
        return game.getMaxNumberCode();
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
