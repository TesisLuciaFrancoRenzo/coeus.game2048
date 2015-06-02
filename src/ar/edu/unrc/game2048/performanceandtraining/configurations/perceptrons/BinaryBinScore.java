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
public class BinaryBinScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    final static int binaryLenght = 4; //alcanza para escribir el 2048

    public BinaryBinScore() {
        perceptron_hidden_quantity = 32;
        perceptron_input_quantity = 64;
        perceptron_output_quantity = 20; //para poder representar el 500_000 como max score en binaro, que es 111 1010 0010 000
        hiddenLayerQuantity = 1;
        activationFunctionHiddenForEncog = new ActivationSigmoid();
        activationFunctionOutputForEncog = new ActivationSigmoid();
        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, 1, 0, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, 1, 0, activationFunctionMax, activationFunctionMin);
    }

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

    public double toBinary(int reword, int outputNeuronIndex) {
        String bits = Integer.toBinaryString(reword); //TODO optimizar esto! no pedir uno a uno! pedir todo junto y despues trabajr conesto
        if ( outputNeuronIndex > bits.length() - 1 ) {
            return activationFunctionMin;
        } else {
            if ( bits.charAt(outputNeuronIndex) == '0' ) {
                return activationFunctionMin;
            } else {
                return activationFunctionMax;
            }
        }
    }

//    @Override
//    public Prediction translateFinalRewordToPrediction(GameBoard<NeuralNetworkClass> board) {
//        assert board.isTerminalState();
//        return new Prediction(board.getPartialScore() * 1d, null);
//    }
    @Override
    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data) {
        return () -> {
            StringBuilder stringBits = new StringBuilder();
            for ( int i = 0; i < data.length; i++ ) {
                assert data[i] != Double.NaN;
                if ( normOutput.deNormalize(data[i]) >= 0.5 ) {
                    stringBits.append('1');
                } else {
                    stringBits.append('0');
                }
            }
            return Integer.parseInt(stringBits.toString(), 2);
        };
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
        //FIXME cambiar esto para que no pida una a una las neuronas sino que pida todo junto, es mas eficiente
        if ( outputNeuronIndex < 0 || outputNeuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + outputNeuronIndex);
        }
        return toBinary(board.getPartialScore(), outputNeuronIndex);
    }

    @Override
    public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
        //TODO testear esto cuando lo hagamos abstracto
        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
        }
        return normOutput.normalize(board.getPartialScore());
    }

}
