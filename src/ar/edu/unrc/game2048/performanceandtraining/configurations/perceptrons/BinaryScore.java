/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.Tile;
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
public class BinaryScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    static final int binaryLenght = 4; //alcanza para escribir el 2048

    public int maxScore;
    public int minScore;

    public BinaryScore() {

        maxScore = 16_384; //ver teoria
        minScore = 0;
        perceptron_hidden_quantity = 64;
        perceptron_input_quantity = 64;
        perceptron_output_quantity = 1;
        hiddenLayerQuantity = 1;
        activationFunctionHiddenForEncog = new ActivationSigmoid();
        activationFunctionOutputForEncog = new ActivationSigmoid();
        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }
//
//    @Override
//    public Prediction translateFinalRewordToPrediction(GameBoard<NeuralNetworkClass> board) {
//        assert board.isTerminalState();
//        return new Prediction(board.getPartialScore() * 1d, null);
//    }

    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
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
        assert currentNeuron == perceptron_input_quantity;
    }

    @Override
    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data) {
        return () -> {
            assert data[0] != Double.NaN;
            return (int) Math.round(normOutput.deNormalize(data[0]));
        };
    }

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
        if ( board.getPartialScore() > this.maxScore ) {
            throw new IllegalArgumentException("board.getPartialScore() supera el maximo de " + maxScore + " con el valor " + board.getPartialScore());
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
}
