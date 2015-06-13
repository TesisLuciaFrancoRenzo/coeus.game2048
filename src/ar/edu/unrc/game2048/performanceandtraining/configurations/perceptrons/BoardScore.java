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
public class BoardScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    /**
     *
     */
    public int maxCodedBoardnumber;

    /**
     *
     */
    public int maxScore;

    /**
     *
     */
    public int minCodedBoardnumber;

    /**
     *
     */
    public int minScore;

    /**
     *
     */
    public BoardScore() {
        maxCodedBoardnumber = 11; //2048 como maximo
        maxScore = 100_000;
        minScore = 0;
        minCodedBoardnumber = 0;

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 16;
        neuronQuantityInLayer[1] = 16;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[3];
        activationFunctionForEncog[0] = null;
        activationFunctionForEncog[1] = new ActivationSigmoid();
        activationFunctionForEncog[2] = new ActivationSigmoid();

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
     * @param normalizedPerceptronInput
     */
    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        // primera fila
        normalizedPerceptronInput.set(0,
                normInput.normalize(board.tileAt(0, 0).getCode())
        );
        normalizedPerceptronInput.set(1,
                normInput.normalize(board.tileAt(0, 1).getCode())
        );
        normalizedPerceptronInput.set(2,
                normInput.normalize(board.tileAt(0, 2).getCode())
        );
        normalizedPerceptronInput.set(3,
                normInput.normalize(board.tileAt(0, 3).getCode())
        );
        // segunda fila
        normalizedPerceptronInput.set(4,
                normInput.normalize(board.tileAt(1, 0).getCode())
        );
        normalizedPerceptronInput.set(5,
                normInput.normalize(board.tileAt(1, 1).getCode())
        );
        normalizedPerceptronInput.set(6,
                normInput.normalize(board.tileAt(1, 2).getCode())
        );
        normalizedPerceptronInput.set(7,
                normInput.normalize(board.tileAt(1, 3).getCode())
        );
        // tercera fila
        normalizedPerceptronInput.set(8,
                normInput.normalize(board.tileAt(2, 0).getCode())
        );
        normalizedPerceptronInput.set(9,
                normInput.normalize(board.tileAt(2, 1).getCode())
        );
        normalizedPerceptronInput.set(10,
                normInput.normalize(board.tileAt(2, 2).getCode())
        );
        normalizedPerceptronInput.set(11,
                normInput.normalize(board.tileAt(2, 3).getCode())
        );
        // cuarta fila
        normalizedPerceptronInput.set(12,
                normInput.normalize(board.tileAt(3, 0).getCode())
        );
        normalizedPerceptronInput.set(13,
                normInput.normalize(board.tileAt(3, 1).getCode())
        );
        normalizedPerceptronInput.set(14,
                normInput.normalize(board.tileAt(3, 2).getCode())
        );
        normalizedPerceptronInput.set(15,
                normInput.normalize(board.tileAt(3, 3).getCode())
        );
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
//        if ( board.getPartialScore() > this.maxScore ) {
//            throw new IllegalArgumentException("board.getPartialScore() supera el maximo de " + maxScore + " con el valor " + board.getPartialScore());
//        }
//        return normOutput.normalize(board.getPartialScore());
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
//        if ( outputNeuronIndex < 0 || outputNeuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + outputNeuronIndex);
//        }
//        if ( board.getPartialScore() > this.maxScore ) {
//            throw new IllegalArgumentException("board.getPartialScore() supera el maximo de " + maxScore + " con el valor " + board.getPartialScore());
//        }
//        return normOutput.normalize(board.getPartialScore());
//    }

    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Double[] output) {
        return () -> {
            assert output.length == 1;
            return output[0];
        };
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(double value) {
        return normOutput.deNormalize(value);
    }

    @Override
    public double getBoardReward(GameBoard board, int outputNeuron) {
        return board.getPartialScore();
    }

    @Override
    public double getFinalReward(Game2048 game, int outputNeuron) {
        return game.getScore();
    }

    @Override
    public double normalizeValueToPerceptronOutput(double value) {
        return normOutput.normalize(value);
    }

}
