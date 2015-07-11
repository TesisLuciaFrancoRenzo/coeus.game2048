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
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class BoardScoreCustomNormalization<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    /**
     *
     */
    public int maxEncriptedTile; //ver teoria

    /**
     *
     */
    public int maxScore;

    /**
     *
     */
    public int minEncriptedTile;

    /**
     *
     */
    public int minScore;

    /**
     *
     */
    public BoardScoreCustomNormalization() {

        maxScore = 500_000;
        minScore = -500_000;
        maxEncriptedTile = 1; //ver teoria
        minEncriptedTile = 0;

        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 16;
        neuronQuantityInLayer[1] = 16;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationTANH();
        activationFunctionForEncog[1] = new ActivationTANH();

        activationFunctionMax = 1;
        activationFunctionMin = -1;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxEncriptedTile, minEncriptedTile, activationFunctionMax, activationFunctionMin);
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
    public IsolatedComputation calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        return () -> {
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

    /**
     *
     * @param game
     * @param output <p>
     * @return
     */
    @Override
    public IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Object[] output) {
        return () -> {
            assert output.length == 1;
            return (Double) output[0];
        };
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object[] value, int outputNeuronIndex) {
        return normOutput.deNormalize((Double) value[outputNeuronIndex]);
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
    public double normalizeValueToPerceptronOutput(Object value) {
        return normOutput.normalize((Double) value);
    }

    private Double encryptTile(GameBoard<NeuralNetworkClass> board, int boardTileCode) {
        assert board.getMaxTileNumberCode() != 0;
        return boardTileCode / (board.getMaxTileNumberCode() * 1d);
    }

}
