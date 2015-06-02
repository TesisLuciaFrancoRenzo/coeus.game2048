/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author franco
 * @param <NeuralNetworkClass>
 */
public class NTupleScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    public int maxCodedBoardnumber = 11_111_111; //2048 maximo
    public int maxScore = 500_000; //ver teoria
    public int minCodedBoardnumber = 0;
    public int minScore = 0;

    public NTupleScore() {
        perceptron_hidden_quantity = 17;
        perceptron_input_quantity = 17;
        perceptron_output_quantity = 1;
        hiddenLayerQuantity = 1;
        activationFunctionHiddenForEncog = new ActivationSigmoid();
        activationFunctionOutputForEncog = new ActivationSigmoid();
        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardnumber, minCodedBoardnumber, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

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
        //segunda fila de rectangulos
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
    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data) {
        return () -> {
            assert data[0] != Double.NaN;
            return (int) Math.round(normOutput.deNormalize(data[0]));
        };
    }

//    @Override
//    public Prediction translateFinalRewordToPrediction(GameBoard<NeuralNetworkClass> board) {
//        assert board.isTerminalState();
//        return new Prediction(board.getPartialScore() * 1d, null);
//    }
    @Override
    public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
        //TODO testear esto cuando lo hagamos abstracto
        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
        }
        return normOutput.normalize(board.getPartialScore());
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
        if ( outputNeuronIndex < 0 || outputNeuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + outputNeuronIndex);
        }
        return normOutput.normalize(board.getPartialScore());
    }

//    @Override
//    public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
//        //TODO testear esto cuando lo hagamos abstracto
//        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
//        }
//        if ( board.getGame().getScore() > maxScore ) {
//            throw new IllegalArgumentException("game score can't exceede " + maxScore + "] points, but was " + board.getGame().getScore());
//        }
//        return normOutput.normalize(board.getAsFinalScore());
//    }
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
