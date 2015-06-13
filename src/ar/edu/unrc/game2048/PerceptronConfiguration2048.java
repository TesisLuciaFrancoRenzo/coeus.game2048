/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class PerceptronConfiguration2048<NeuralNetworkClass> implements Cloneable, IConfiguration2048 {

    /**
     *
     */
    public ActivationFunction[] activationFunctionForEncog;

    /**
     *
     */
    public double activationFunctionMax;

    /**
     *
     */
    public double activationFunctionMin;

    /**
     *
     */
    public NormalizedField normInput;

    /**
     *
     */
    public NormalizedField normOutput;

    /**
     *
     */
    public int[] neuronQuantityInLayer;

    //fin de la configuracion de la red neuronal
    private NeuralNetworkClass neuralNetwork;

    /**
     *
     * @param board
     * @param normalizedPerceptronInput
     */
    public abstract void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param game
     * @param output <p>
     * @return
     */
    public abstract IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Double[] output);

    /**
     * @return the neuralNetwork
     */
    public NeuralNetworkClass getNeuralNetwork() {
        return neuralNetwork;
    }

    /**
     * @param neuralNetwork the neuralNetwork to set
     */
    public void setNeuralNetwork(NeuralNetworkClass neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
