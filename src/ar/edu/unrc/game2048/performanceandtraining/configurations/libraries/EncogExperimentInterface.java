/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.training.FunctionUtils;
import java.io.File;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.obj.SerializeObject;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class EncogExperimentInterface extends INeuralNetworkInterfaceFor2048<BasicNetwork> {

    /**
     *
     */
    protected Function<Double, Double> activationFunctionHidden;

    /**
     *
     */
    protected Function<Double, Double> activationFunctionOutput;

    /**
     *
     */
    protected Function<Double, Double> derivatedActivationFunctionHidden;

    /**
     *
     */
    protected Function<Double, Double> derivatedActivationFunctionOutput;

    /**
     *
     * @param perceptronConfiguration
     */
    public EncogExperimentInterface(PerceptronConfiguration2048<BasicNetwork> perceptronConfiguration) {
        super(perceptronConfiguration);
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    @Override
    public void compareNeuralNetworks(File randomFile, File trainedFile) {
        try {
            BasicNetwork randomNN = (BasicNetwork) SerializeObject.load(randomFile);
            BasicNetwork trainedNN = (BasicNetwork) SerializeObject.load(trainedFile);
            if ( randomNN.equals(trainedNN) ) {
                throw new Exception("No cambio el perceptron para nada!");
            }
        } catch ( Exception ex ) {
            Logger.getLogger(EncogExperimentInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param neuralNetwork
     */
    public void setConfigForTesting(BasicNetwork neuralNetwork) {
        getPerceptronConfiguration().setNeuralNetwork(neuralNetwork);
    }

    @Override
    public String getLibName() {
        return "Encog";
    }

    @Override
    public IPerceptronInterface getPerceptronInterface() {

        if ( getPerceptronConfiguration().activationFunctionHiddenForEncog instanceof ActivationTANH ) {
            this.activationFunctionHidden = FunctionUtils.tanh;
            this.derivatedActivationFunctionHidden = FunctionUtils.derivatedTanh;
        } else if ( getPerceptronConfiguration().activationFunctionHiddenForEncog instanceof ActivationSigmoid ) {
            this.activationFunctionHidden = FunctionUtils.sigmoid;
            this.derivatedActivationFunctionHidden = FunctionUtils.derivatedSigmoid;
        } else {
            throw new IllegalArgumentException("El test esta pensado para utilizar TANH o Simoid como funcion de activacion");
        }

        if ( getPerceptronConfiguration().activationFunctionOutputForEncog instanceof ActivationTANH ) {
            this.activationFunctionOutput = FunctionUtils.tanh;
            this.derivatedActivationFunctionOutput = FunctionUtils.derivatedTanh;
        } else if ( getPerceptronConfiguration().activationFunctionOutputForEncog instanceof ActivationSigmoid ) {
            this.activationFunctionOutput = FunctionUtils.sigmoid;
            this.derivatedActivationFunctionOutput = FunctionUtils.derivatedSigmoid;
        } else {
            throw new IllegalArgumentException("El test esta pensado para utilizar TANH o Simoid como funcion de activacion");
        }

        IPerceptronInterface encogPerceptronInterface = new IPerceptronInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return activationFunctionOutput; //TODO soportar mas capas
                } else {
                    //capas ocultas
                    return activationFunctionHidden;
                }

            }

            @Override
            public double getBias(int layerIndex, int neuronIndex) {
                return getPerceptronConfiguration().getNeuralNetwork().getWeight(layerIndex - 1, getPerceptronConfiguration().getNeuralNetwork().getLayerNeuronCount(layerIndex - 1), neuronIndex);
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
                    throw new IllegalArgumentException("layerIndex out of valid range");
                } else if ( layerIndex == getLayerQuantity() - 1 ) {
                    //ultima capa
                    return derivatedActivationFunctionOutput;
                } else {
                    //capas ocultas
                    return derivatedActivationFunctionHidden;
                }
            }

            @Override
            public int getLayerQuantity() {
                return getPerceptronConfiguration().getNeuralNetwork().getLayerCount();
            }

            @Override
            public int getNeuronQuantityInLayer(int layerIndex) {
                return getPerceptronConfiguration().getNeuralNetwork().getLayerNeuronCount(layerIndex);
            }

            @Override
            public double getWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer) {
                return getPerceptronConfiguration().getNeuralNetwork().getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return true; //FIXME hacer que consulte realmente si tiene bias la capa o no
            }

            @Override
            public void setBias(int layerIndex, int neuronIndex, double correctedBias) {
                getPerceptronConfiguration().getNeuralNetwork().setWeight(layerIndex - 1, getPerceptronConfiguration().getNeuralNetwork().getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
            }

            @Override
            public void setWeight(int layerIndex, int neuronIndex, int neuronIndexPreviousLayer, double correctedWeight) {
                getPerceptronConfiguration().getNeuralNetwork().setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };
        return encogPerceptronInterface;
    }

    /**
     *
     * @param randomized <p>
     * @return
     */
    public BasicNetwork initializeEncogPerceptron(boolean randomized) {
        if ( getPerceptronConfiguration().hiddenLayerQuantity < 1 ) {
            throw new IllegalArgumentException("hiddenLayers < 1");
        }
        BasicNetwork perceptron = new BasicNetwork();
        perceptron.addLayer(new BasicLayer(null, true, getPerceptronConfiguration().perceptron_input_quantity));
        for ( int i = 0; i < getPerceptronConfiguration().hiddenLayerQuantity; i++ ) {
            perceptron.addLayer(new BasicLayer(getPerceptronConfiguration().activationFunctionHiddenForEncog.clone(), true, getPerceptronConfiguration().perceptron_hidden_quantity));
        }
        perceptron.addLayer(new BasicLayer(getPerceptronConfiguration().activationFunctionOutputForEncog.clone(), false, getPerceptronConfiguration().perceptron_output_quantity));
        perceptron.getStructure().finalizeStructure();
        if ( randomized ) {
            perceptron.reset();
        }
        return perceptron;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
        if ( !perceptronFile.exists() ) {
            //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
            getPerceptronConfiguration().setNeuralNetwork(initializeEncogPerceptron(randomizedIfNotExist));
            SerializeObject.save(perceptronFile, getPerceptronConfiguration().getNeuralNetwork());
        } else {
            //si el archivo existe, lo cargamos como perceptron entrenado al juego
            getPerceptronConfiguration().setNeuralNetwork((BasicNetwork) SerializeObject.load(perceptronFile));
        }
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
        SerializeObject.save(perceptronFile, getPerceptronConfiguration().getNeuralNetwork());
    }

}
