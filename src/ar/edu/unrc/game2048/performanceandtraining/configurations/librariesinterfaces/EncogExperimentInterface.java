/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.utils.FunctionUtils;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
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
    protected List<Function<Double, Double>> activationFunction;

    /**
     *
     */
    protected List<Function<Double, Double>> derivatedActivationFunction;

    /**
     *
     * @param perceptronConfiguration
     */
    public EncogExperimentInterface(
            PerceptronConfiguration2048<BasicNetwork> perceptronConfiguration) {
        super(perceptronConfiguration);
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    @Override
    public void compareNeuralNetworks(File randomFile,
            File trainedFile) {
        try {
            BasicNetwork randomNN = (BasicNetwork) SerializeObject.load(
                    randomFile);
            BasicNetwork trainedNN = (BasicNetwork) SerializeObject.load(
                    trainedFile);
            if ( randomNN.equals(trainedNN) ) {
                throw new Exception("No cambio el perceptron para nada!");
            }
        } catch ( Exception ex ) {
            Logger.getLogger(EncogExperimentInterface.class.getName()).log(
                    Level.SEVERE, null, ex);
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
    public INeuralNetworkInterface getPerceptronInterface() {
        activationFunction = new ArrayList<>(getPerceptronConfiguration().
                getActivationFunctionForEncog().length);
        derivatedActivationFunction = new ArrayList<>(
                getPerceptronConfiguration().getActivationFunctionForEncog().length);

        for ( ActivationFunction activationFunctionForEncog
                : getPerceptronConfiguration().getActivationFunctionForEncog() ) {
            if ( activationFunctionForEncog instanceof ActivationTANH ) {
                this.activationFunction.add(FunctionUtils.TANH);
                this.derivatedActivationFunction.add(
                        FunctionUtils.TANH_DERIVATED);
            } else if ( activationFunctionForEncog instanceof ActivationSigmoid ) {
                this.activationFunction.add(FunctionUtils.SIGMOID);
                this.derivatedActivationFunction.add(
                        FunctionUtils.SIGMOID_DERIVATED);
            } else if ( activationFunctionForEncog instanceof ActivationLinear ) {
                this.activationFunction.add(FunctionUtils.LINEAR);
                this.derivatedActivationFunction.add(
                        FunctionUtils.LINEAR_DERIVATED);
            } else {
                throw new IllegalArgumentException(
                        "El test esta pensado para utilizar TANH, Simoid o Linear como funcion de activacion");
            }
        }

        INeuralNetworkInterface encogPerceptronInterface = new INeuralNetworkInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
//                if ( layerIndex <= 0 || layerIndex >= getLayerQuantity() ) {
//                    throw new IllegalArgumentException("layerIndex out of valid range");
//                }
                return activationFunction.get(layerIndex - 1);
            }

            @Override
            public double getBias(int layerIndex,
                    int neuronIndex) {
                return getPerceptronConfiguration().getNeuralNetwork().
                        getWeight(layerIndex - 1, getPerceptronConfiguration().
                                getNeuralNetwork().getLayerNeuronCount(
                                        layerIndex - 1), neuronIndex);
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(
                    int layerIndex) {
                return derivatedActivationFunction.get(layerIndex - 1);
            }

            @Override
            public int getLayerQuantity() {
                return getPerceptronConfiguration().getNeuralNetwork().
                        getLayerCount();
            }

            @Override
            public int getNeuronQuantityInLayer(int layerIndex) {
                return getPerceptronConfiguration().getNeuralNetwork().
                        getLayerNeuronCount(layerIndex);
            }

            @Override
            public double getWeight(int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer) {
                return getPerceptronConfiguration().getNeuralNetwork().
                        getWeight(layerIndex - 1, neuronIndexPreviousLayer,
                                neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return true; //TODO hacer que consulte realmente si tiene bias la capa o no
            }

            @Override
            public void setBias(int layerIndex,
                    int neuronIndex,
                    double correctedBias) {
                getPerceptronConfiguration().getNeuralNetwork().setWeight(
                        layerIndex - 1, getPerceptronConfiguration().
                        getNeuralNetwork().getLayerNeuronCount(layerIndex - 1),
                        neuronIndex, correctedBias);
            }

            @Override
            public void setWeight(int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer,
                    double correctedWeight) {
                getPerceptronConfiguration().getNeuralNetwork().setWeight(
                        layerIndex - 1, neuronIndexPreviousLayer, neuronIndex,
                        correctedWeight);
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
        if ( getPerceptronConfiguration().getNeuronQuantityInLayer() == null || getPerceptronConfiguration().
                getNeuronQuantityInLayer().length < 2 ) {
            throw new IllegalArgumentException(
                    "la cantidad de capas es de minimo 2 para un perceptrón (incluyendo entrada y salida)");
        }
        BasicNetwork perceptron = new BasicNetwork();
        ActivationFunction function;
        perceptron.addLayer(new BasicLayer(null, true,
                getPerceptronConfiguration().getNeuronQuantityInLayer()[0]));
        for ( int i = 1; i < getPerceptronConfiguration().
                getNeuronQuantityInLayer().length - 1; i++ ) {
            function = getPerceptronConfiguration().
                    getActivationFunctionForEncog()[i - 1].clone();
            perceptron.addLayer(new BasicLayer(function, true,
                    getPerceptronConfiguration().getNeuronQuantityInLayer()[i]));
        }
        //getPerceptronConfiguration().getNeuronQuantityInLayer().length - 2 porque el for finaliza en getPerceptronConfiguration().getNeuronQuantityInLayer().length - 1
        function = getPerceptronConfiguration().getActivationFunctionForEncog()[getPerceptronConfiguration().
                getNeuronQuantityInLayer().length - 2].clone();
        perceptron.addLayer(new BasicLayer(function, false,
                getPerceptronConfiguration().getNeuronQuantityInLayer()[getPerceptronConfiguration().
                getNeuronQuantityInLayer().length - 1]));
        perceptron.getStructure().finalizeStructure();
        if ( randomized ) {
            perceptron.reset();
        }
        return perceptron;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile,
            boolean randomizedIfNotExist,
            boolean createFile) throws Exception {
        if ( createFile ) {
            if ( !perceptronFile.exists() ) {
                //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
                getPerceptronConfiguration().setNeuralNetwork(
                        initializeEncogPerceptron(randomizedIfNotExist));
                SerializeObject.save(perceptronFile,
                        getPerceptronConfiguration().getNeuralNetwork());
            } else {
                //si el archivo existe, lo cargamos como perceptron entrenado al juego
                getPerceptronConfiguration().setNeuralNetwork(
                        (BasicNetwork) SerializeObject.load(perceptronFile));
            }
        } else {
            getPerceptronConfiguration().setNeuralNetwork(
                    initializeEncogPerceptron(randomizedIfNotExist));
        }
    }

    @Override
    public void saveNeuralNetwork(File perceptronFile) throws Exception {
        SerializeObject.save(perceptronFile, getPerceptronConfiguration().
                getNeuralNetwork());
    }

}
