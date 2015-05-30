/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import java.util.List;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author franco
 * @param <NeuralNetworkClass>
 */
public abstract class PerceptronConfiguration2048<NeuralNetworkClass> implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    private NeuralNetworkClass neuralNetwork;

    public int hiddenLayerQuantity = 1;
    public int perceptron_hidden_quantity;
    public int perceptron_input_quantity;
    public int perceptron_output_quantity;
    public ActivationFunction activationFunctionHiddenForEncog;
    public ActivationFunction activationFunctionOutputForEncog;
    public double activationFunctionMax;
    public double activationFunctionMin;
    public double randomMoveProbability = 0;
    //fin de la configuracion de la red neuronal

    public NormalizedField normInput;
    public NormalizedField normOutput;

    public abstract void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput);

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

//    public abstract double translateThisFinalStateToNormalizedPerceptronOutput(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex);
    public abstract double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<NeuralNetworkClass> board, int outputNeuronIndex);

    /**
     *
     * @param data
     * @param partialReward el valor a sumar a la prediccion si se esta
     *                      utilizando metodos acumulativos de prediccion en
     *                      TDlearning
     * <p>
     * @return
     */
    public abstract double translatePerceptronOutputToPrediction(double[] data, double partialReward);
}
