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
 * Codificacion Ntupla + tablero simple
 * <p>
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public class FullNTupleScore<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    /**
     *
     */
    public int maxCodedBoardnumber = 11_111_111; //2048 maximo

    /**
     *
     */
    public int maxCodedNumber = 1;

    /**
     *
     */
    public int maxScore = 100_000;

    /**
     *
     */
    public int minCodedBoardnumber = 0;

    /**
     *
     */
    public int minCodedNumber = 0;

    /**
     *
     */
    public int minScore = 0;
    private final NormalizedField normInputSimpleBoard;

    /**
     *
     */
    public FullNTupleScore() {
        this.neuronQuantityInLayer = new int[3];
        neuronQuantityInLayer[0] = 33;
        neuronQuantityInLayer[1] = 33;
        neuronQuantityInLayer[2] = 1;

        this.activationFunctionForEncog = new ActivationFunction[2];

        activationFunctionForEncog[0] = new ActivationSigmoid();
        activationFunctionForEncog[1] = new ActivationSigmoid();

        activationFunctionMax = 1;
        activationFunctionMin = 0;

        normInput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardnumber, minCodedBoardnumber, activationFunctionMax, activationFunctionMin);

        normInputSimpleBoard = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedNumber, minCodedNumber, activationFunctionMax, activationFunctionMin);

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
            // verticales
            normalizedPerceptronInput.set(0,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 0).getCode(),
                                    board.tileAt(0, 1).getCode(),
                                    board.tileAt(0, 2).getCode(),
                                    board.tileAt(0, 3).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(1,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(1, 0).getCode(),
                                    board.tileAt(1, 1).getCode(),
                                    board.tileAt(1, 2).getCode(),
                                    board.tileAt(1, 3).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(2,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(2, 0).getCode(),
                                    board.tileAt(2, 1).getCode(),
                                    board.tileAt(2, 2).getCode(),
                                    board.tileAt(2, 3).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(3,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(3, 0).getCode(),
                                    board.tileAt(3, 1).getCode(),
                                    board.tileAt(3, 2).getCode(),
                                    board.tileAt(3, 3).getCode()
                            )
                    )
            );
            // horizontales
            normalizedPerceptronInput.set(4,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 0).getCode(),
                                    board.tileAt(1, 0).getCode(),
                                    board.tileAt(2, 0).getCode(),
                                    board.tileAt(3, 0).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(5,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 1).getCode(),
                                    board.tileAt(1, 1).getCode(),
                                    board.tileAt(2, 1).getCode(),
                                    board.tileAt(3, 1).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(6,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 2).getCode(),
                                    board.tileAt(1, 2).getCode(),
                                    board.tileAt(2, 2).getCode(),
                                    board.tileAt(3, 2).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(7,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 3).getCode(),
                                    board.tileAt(1, 3).getCode(),
                                    board.tileAt(2, 3).getCode(),
                                    board.tileAt(3, 3).getCode()
                            )
                    )
            );
            // cuadrados
            normalizedPerceptronInput.set(8,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 0).getCode(),
                                    board.tileAt(0, 1).getCode(),
                                    board.tileAt(1, 1).getCode(),
                                    board.tileAt(1, 0).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(9,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(1, 0).getCode(),
                                    board.tileAt(1, 1).getCode(),
                                    board.tileAt(2, 1).getCode(),
                                    board.tileAt(2, 0).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(10,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(2, 0).getCode(),
                                    board.tileAt(2, 1).getCode(),
                                    board.tileAt(3, 1).getCode(),
                                    board.tileAt(3, 0).getCode()
                            )
                    )
            );
            //segunda fila de rectangulos
            normalizedPerceptronInput.set(11,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 1).getCode(),
                                    board.tileAt(0, 2).getCode(),
                                    board.tileAt(1, 2).getCode(),
                                    board.tileAt(1, 1).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(12,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(1, 1).getCode(),
                                    board.tileAt(1, 2).getCode(),
                                    board.tileAt(2, 2).getCode(),
                                    board.tileAt(2, 1).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(13,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(2, 1).getCode(),
                                    board.tileAt(2, 2).getCode(),
                                    board.tileAt(3, 2).getCode(),
                                    board.tileAt(3, 1).getCode()
                            )
                    )
            );
            //segunda fila de rectangulos
            normalizedPerceptronInput.set(14,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(0, 2).getCode(),
                                    board.tileAt(0, 3).getCode(),
                                    board.tileAt(1, 3).getCode(),
                                    board.tileAt(1, 2).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(15,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(1, 2).getCode(),
                                    board.tileAt(1, 3).getCode(),
                                    board.tileAt(2, 3).getCode(),
                                    board.tileAt(2, 2).getCode()
                            )
                    )
            );
            normalizedPerceptronInput.set(16,
                    normInput.normalize(encryptNTupleTiles(
                                    board.tileAt(2, 2).getCode(),
                                    board.tileAt(2, 3).getCode(),
                                    board.tileAt(3, 3).getCode(),
                                    board.tileAt(3, 2).getCode()
                            )
                    )
            );

            // primera fila
            normalizedPerceptronInput.set(17,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(0, 0).getCode()))
            );
            normalizedPerceptronInput.set(18,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(0, 1).getCode()))
            );
            normalizedPerceptronInput.set(19,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(0, 2).getCode()))
            );
            normalizedPerceptronInput.set(20,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(0, 3).getCode()))
            );
            // segunda fila
            normalizedPerceptronInput.set(21,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(1, 0).getCode()))
            );
            normalizedPerceptronInput.set(22,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(1, 1).getCode()))
            );
            normalizedPerceptronInput.set(23,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(1, 2).getCode()))
            );
            normalizedPerceptronInput.set(24,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(1, 3).getCode()))
            );
            // tercera fila
            normalizedPerceptronInput.set(25,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(2, 0).getCode()))
            );
            normalizedPerceptronInput.set(26,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(2, 1).getCode()))
            );
            normalizedPerceptronInput.set(27,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(2, 2).getCode()))
            );
            normalizedPerceptronInput.set(28,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(2, 3).getCode()))
            );
            // cuarta fila
            normalizedPerceptronInput.set(29,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(3, 0).getCode()))
            );
            normalizedPerceptronInput.set(30,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(3, 1).getCode()))
            );
            normalizedPerceptronInput.set(31,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(3, 2).getCode()))
            );
            normalizedPerceptronInput.set(32,
                    normInputSimpleBoard.normalize(encryptSimpleBoardTile(board, board.tileAt(3, 3).getCode()))
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
    private int encryptNTupleTiles(int tileCode1, int tileCode2, int tileCode3, int tileCode4) {
        return tileCode1 * 1_000_000 + tileCode2 * 10_000 + tileCode3 * 100 + tileCode4;
    }

    private Double encryptSimpleBoardTile(GameBoard board, int boardTileCode) {
        return boardTileCode / (board.getMaxTileNumberCode() * 1d);
    }

}
