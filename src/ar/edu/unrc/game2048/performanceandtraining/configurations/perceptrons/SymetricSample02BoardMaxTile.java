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
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author franco
 * @param <NeuralNetworkClass>
 */
public class SymetricSample02BoardMaxTile<NeuralNetworkClass> extends PerceptronConfiguration2048<NeuralNetworkClass> {

    public double maxCode2x3;

    public int maxCodeLines;
    public int maxCodedBoardnumber;

    public double minCode2x3;

    public int minCodeLines;
    public int minCodedBoardnumber;

    private final NormalizedField normInput2x3;
    private final NormalizedField normInputSimpleBoard;

    public SymetricSample02BoardMaxTile() {
        maxCodeLines = 11_111_111; //2048 max
        minCodeLines = 0;
        maxCode2x3 = 111_111_111_111d; //2048
        minCode2x3 = 0d;
        perceptron_hidden_quantity = 10;
        perceptron_input_quantity = 20;
        maxCodedBoardnumber = 11;
        minCodedBoardnumber = 0;
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
        normInputSimpleBoard = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardnumber, minCodedBoardnumber, activationFunctionMax, activationFunctionMin);
        normOutput = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedBoardnumber, minCodedBoardnumber, activationFunctionMax, activationFunctionMin);
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
        // primera fila
        normalizedPerceptronInput.set(4,
                normInputSimpleBoard.normalize(board.tileAt(0, 0).getCode())
        );
        normalizedPerceptronInput.set(5,
                normInputSimpleBoard.normalize(board.tileAt(0, 1).getCode())
        );
        normalizedPerceptronInput.set(6,
                normInputSimpleBoard.normalize(board.tileAt(0, 2).getCode())
        );
        normalizedPerceptronInput.set(7,
                normInputSimpleBoard.normalize(board.tileAt(0, 3).getCode())
        );
        // segunda fila
        normalizedPerceptronInput.set(8,
                normInputSimpleBoard.normalize(board.tileAt(1, 0).getCode())
        );
        normalizedPerceptronInput.set(9,
                normInputSimpleBoard.normalize(board.tileAt(1, 1).getCode())
        );
        normalizedPerceptronInput.set(10,
                normInputSimpleBoard.normalize(board.tileAt(1, 2).getCode())
        );
        normalizedPerceptronInput.set(11,
                normInputSimpleBoard.normalize(board.tileAt(1, 3).getCode())
        );
        // tercera fila
        normalizedPerceptronInput.set(12,
                normInputSimpleBoard.normalize(board.tileAt(2, 0).getCode())
        );
        normalizedPerceptronInput.set(13,
                normInputSimpleBoard.normalize(board.tileAt(2, 1).getCode())
        );
        normalizedPerceptronInput.set(14,
                normInputSimpleBoard.normalize(board.tileAt(2, 2).getCode())
        );
        normalizedPerceptronInput.set(15,
                normInputSimpleBoard.normalize(board.tileAt(2, 3).getCode())
        );
        // cuarta fila
        normalizedPerceptronInput.set(16,
                normInputSimpleBoard.normalize(board.tileAt(3, 0).getCode())
        );
        normalizedPerceptronInput.set(17,
                normInputSimpleBoard.normalize(board.tileAt(3, 1).getCode())
        );
        normalizedPerceptronInput.set(18,
                normInputSimpleBoard.normalize(board.tileAt(3, 2).getCode())
        );
        normalizedPerceptronInput.set(19,
                normInputSimpleBoard.normalize(board.tileAt(3, 3).getCode())
        );

    }

    @Override
    public IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data) {
        return () -> {
            assert data[0] != Double.NaN;
            return (int) Math.round(normOutput.deNormalize(data[0]));
        };
    }

    @Override
    public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex) {
        return 0;
    }
//
//    @Override
//    public double translateThisFinalStateToNormalizedPerceptronOutput(GameBoard<NeuralNetworkClass> board, int neuronIndex) {
//        //TODO testear esto cuando lo hagamos abstracto
//        if ( neuronIndex < 0 || neuronIndex >= perceptron_output_quantity ) {
//            throw new IllegalArgumentException("neuronIndex range for output layer must be [0," + perceptron_output_quantity + "] but was " + neuronIndex);
//        }
//        return normOutput.normalize(board.getMaxTileNumberCode());
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
