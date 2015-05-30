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
public class BoardScoreCustomNormalization<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    public int maxScore;
    public int minScore;
    public int maxEncriptedTile; //ver teoria
    public int minEncriptedTile;

    public BoardScoreCustomNormalization() {

        maxScore = 500_000; //ver teoria
        minScore = 0;
        maxEncriptedTile = 1; //ver teoria
        minEncriptedTile = 0;
        perceptron_hidden_quantity = 16;
        perceptron_input_quantity = 16;
        perceptron_output_quantity = 1;
        hiddenLayerQuantity = 1;
        activationFunctionHiddenForEncog = new ActivationTANH();
        activationFunctionOutputForEncog = new ActivationTANH();
        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxEncriptedTile, minEncriptedTile, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxScore, minScore, activationFunctionMax, activationFunctionMin);
    }

    @Override
    public double translatePerceptronOutputToPrediction(double[] data, double partialReward) {
        assert data[0] != Double.NaN;
        return Math.round(normOutput.deNormalize(data[0])) + partialReward;
    }

    private Double encryptTile(GameBoard<NeuralNetworkClass> board, int boardTileCode) {
        assert board.getMaxTileNumberCode() != 0;
        return boardTileCode / (board.getMaxTileNumberCode() * 1d);
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
        if ( outputNeuronIndex < 0 || outputNeuronIndex >= perceptron_output_quantity ) {
            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + outputNeuronIndex);
        }
        return normOutput.normalize(board.getPartialScore());
    }

    @Override
    public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        // primera fila
        normalizedPerceptronInput.set(0,
                normInput.normalize(encryptTile(board, board.tileAt(0, 0).getCode()))
        );
        normalizedPerceptronInput.set(1,
                normInput.normalize(encryptTile(board, board.tileAt(1, 0).getCode()))
        );
        normalizedPerceptronInput.set(2,
                normInput.normalize(encryptTile(board, board.tileAt(2, 0).getCode()))
        );
        normalizedPerceptronInput.set(3,
                normInput.normalize(encryptTile(board, board.tileAt(3, 0).getCode()))
        );
        // segunda fila
        normalizedPerceptronInput.set(4,
                normInput.normalize(encryptTile(board, board.tileAt(0, 1).getCode()))
        );
        normalizedPerceptronInput.set(5,
                normInput.normalize(encryptTile(board, board.tileAt(1, 1).getCode()))
        );
        normalizedPerceptronInput.set(6,
                normInput.normalize(encryptTile(board, board.tileAt(2, 1).getCode()))
        );
        normalizedPerceptronInput.set(7,
                normInput.normalize(encryptTile(board, board.tileAt(3, 1).getCode()))
        );
        // tercera fila
        normalizedPerceptronInput.set(8,
                normInput.normalize(encryptTile(board, board.tileAt(0, 2).getCode()))
        );
        normalizedPerceptronInput.set(9,
                normInput.normalize(encryptTile(board, board.tileAt(1, 2).getCode()))
        );
        normalizedPerceptronInput.set(10,
                normInput.normalize(encryptTile(board, board.tileAt(2, 2).getCode()))
        );
        normalizedPerceptronInput.set(11,
                normInput.normalize(encryptTile(board, board.tileAt(3, 2).getCode()))
        );
        // cuarta fila
        normalizedPerceptronInput.set(12,
                normInput.normalize(encryptTile(board, board.tileAt(0, 3).getCode()))
        );
        normalizedPerceptronInput.set(13,
                normInput.normalize(encryptTile(board, board.tileAt(1, 3).getCode()))
        );
        normalizedPerceptronInput.set(14,
                normInput.normalize(encryptTile(board, board.tileAt(2, 3).getCode()))
        );
        normalizedPerceptronInput.set(15,
                normInput.normalize(encryptTile(board, board.tileAt(3, 3).getCode()))
        );
    }

//    @Override
//    public double translateThisFinalStateToNormalizedPerceptronOutput(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
//        //TODO testear esto cuando lo hagamos abstracto
//        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
//        }
//        if ( board.getGame().getScore() > maxScore ) {
//            throw new IllegalArgumentException("game score can't exceede " + maxScore + "] points, but was " + board.getGame().getScore());
//        }
//        return normOutput.normalize(board.getAsFinalScore()); //TODO revisar esot.. realmente tiene e,l puntaje final? ya que si se trabaja sobre tableros evaluados y no usados no deberia tener el puntaje
//    }
}
