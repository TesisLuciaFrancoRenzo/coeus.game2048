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
 * @author franco
 * @param <NeuralNetworkClass>
 */
public abstract class PerceptronConfiguration2048<NeuralNetworkClass> implements Cloneable {

    public ActivationFunction activationFunctionHiddenForEncog;
    public double activationFunctionMax;
    public double activationFunctionMin;
    public ActivationFunction activationFunctionOutputForEncog;

    public int hiddenLayerQuantity = 1;
    public NormalizedField normInput;
    public NormalizedField normOutput;
    public int perceptron_hidden_quantity;
    public int perceptron_input_quantity;
    public int perceptron_output_quantity;
    public double randomMoveProbability = 0;
    //fin de la configuracion de la red neuronal

    private NeuralNetworkClass neuralNetwork;

    public abstract void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

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

    /**
     *
     * @param data <p>
     * @return
     */
    public abstract IsolatedComputation<Integer> translatePerceptronOutputToPrediction(double[] data);

    public abstract double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex);

    public abstract double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex);
}
