/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.Tile;
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
public class BinaryScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    static final int binaryLenght = 4; //alcanza para escribir el 2048

    /**
     *
     */
    public int maxScore;

    /**
     *
     */
    public int minScore;

    /**
     *
     */
    public BinaryScore() {

        maxScore = 100_000;
        minScore = 0;

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 64;
        neuronQuantityInLayer[1] = 64;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationSigmoid();
        activationFunctionForEncog[1] = new ActivationSigmoid();

        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    /**
     *
     * @param board
     * @param normalizedPerceptronInput
     */
    @Override
    public IsolatedComputation calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        return () -> {
            Tile[] tiles = board.getTiles();
            int currentNeuron = 0;
            for ( Tile tile : tiles ) {
                String bits = Integer.toBinaryString(tile.getCode());
                for ( int k = 0; k < binaryLenght - bits.length(); k++ ) {
                    normalizedPerceptronInput.set(currentNeuron, activationFunctionMin);
                    currentNeuron++;
                }
                for ( int j = 0; j < bits.length(); j++ ) {
                    if ( bits.charAt(j) == '0' ) {
                        normalizedPerceptronInput.set(currentNeuron, activationFunctionMin);
                    } else {
                        normalizedPerceptronInput.set(currentNeuron, activationFunctionMax);
                    }
                    currentNeuron++;
                }
            }
            assert currentNeuron == this.neuronQuantityInLayer[0];
            return null;
        };
    }

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
        return board.getPartialScore();
    }

    @Override
    public double getFinalReward(Game2048 game, int outputNeuron) {
        return game.getScore();
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
//     * Para el ultimo turno
//     * <p>
//     * @param board
//     * @param neuronIndex
//     * <p>
//     * @return
//     */
//    @Override
//    public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
//
//        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
//        }
//        return normOutput.normalize(board.getPartialScore());
//    }
//
//    /**
//     *
//     * @param board
//     * @param outputNeuronIndex
//     * <p>
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
    public double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

}
