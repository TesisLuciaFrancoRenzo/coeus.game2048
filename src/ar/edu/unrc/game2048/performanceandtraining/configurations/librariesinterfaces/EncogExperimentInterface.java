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
import ar.edu.unrc.game2048.NeuralNetworkConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.obj.SerializeObject;

/**
 * Interfaz de experimentos entre la librería Encog y Coeus
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class EncogExperimentInterface extends INeuralNetworkInterfaceFor2048<BasicNetwork> {

    /**
     * función de activación
     */
    protected List<Function<Double, Double>> activationFunction;

    /**
     * derivada de la función de activación
     */
    protected List<Function<Double, Double>> derivatedActivationFunction;

    /**
     *
     * @param perceptronConfiguration configuración
     */
    public EncogExperimentInterface(
            NeuralNetworkConfiguration2048<BasicNetwork> perceptronConfiguration) {
        super(perceptronConfiguration);
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLibName() {
        return "Encog";
    }

    /**
     * Utilizado con propósitos de testing.
     *
     * @param neuralNetwork
     */
    public void setNeuralNetworkForTesting(BasicNetwork neuralNetwork) {
        getPerceptronConfiguration().setNeuralNetwork(neuralNetwork);
    }

    @Override
    public INeuralNetworkInterface getNeuralNetworkInterface() {
        activationFunction = new ArrayList<>(getPerceptronConfiguration().
                getActivationFunctionForEncog().length);
        derivatedActivationFunction = new ArrayList<>(
                getPerceptronConfiguration().getActivationFunctionForEncog().length);

        for ( ActivationFunction activationFunctionForEncog
                : getPerceptronConfiguration().getActivationFunctionForEncog() ) {
            if ( activationFunctionForEncog instanceof ActivationTANH ) {
                this.activationFunction.add(FunctionUtils.TANH);
                this.derivatedActivationFunction.add(FunctionUtils.TANH_DERIVATED);
            } else if ( activationFunctionForEncog instanceof ActivationSigmoid ) {
                this.activationFunction.add(FunctionUtils.SIGMOID);
                this.derivatedActivationFunction.add(FunctionUtils.SIGMOID_DERIVATED);
            } else if ( activationFunctionForEncog instanceof ActivationLinear ) {
                this.activationFunction.add(FunctionUtils.LINEAR);
                this.derivatedActivationFunction.add(FunctionUtils.LINEAR_DERIVATED);
            } else {
                throw new IllegalArgumentException(
                        "El test esta pensado para utilizar TANH, Simoid o Linear como funcion de activacion");
            }
        }

        INeuralNetworkInterface encogPerceptronInterface = new INeuralNetworkInterface() {

            @Override
            public Function<Double, Double> getActivationFunction(int layerIndex) {
                return activationFunction.get(layerIndex - 1);
            }

            @Override
            public double getBias(int layerIndex,
                    int neuronIndex) {
                if ( perceptronConfiguration.containBias() ) {
                    return getPerceptronConfiguration().getNeuralNetwork().
                            getWeight(layerIndex - 1, getPerceptronConfiguration().getNeuralNetwork().
                                    getLayerNeuronCount(layerIndex - 1), neuronIndex);
                } else {
                    throw new IllegalStateException("No hay bias en esta red neuronal");
                }
            }

            @Override
            public Function<Double, Double> getDerivatedActivationFunction(int layerIndex) {
                return derivatedActivationFunction.get(layerIndex - 1);
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
            public double getWeight(int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer) {
                return getPerceptronConfiguration().getNeuralNetwork().getWeight(layerIndex - 1,
                        neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public boolean hasBias(int layerIndex) {
                return perceptronConfiguration.containBias();
            }

            @Override
            public void setBias(int layerIndex,
                    int neuronIndex,
                    double correctedBias) {
                if ( perceptronConfiguration.containBias() ) {
                    getPerceptronConfiguration().getNeuralNetwork().setWeight(layerIndex - 1,
                            getPerceptronConfiguration().
                            getNeuralNetwork().getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
                } else {
                    throw new IllegalStateException("No hay bias en esta red neuronal");
                }
            }

            @Override
            public void setWeight(int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer,
                    double correctedWeight) {
                getPerceptronConfiguration().getNeuralNetwork().setWeight(layerIndex - 1, neuronIndexPreviousLayer,
                        neuronIndex, correctedWeight);
            }
        };
        return encogPerceptronInterface;
    }

    /**
     * Inicializa red neuronal con Encog.
     *
     * @param randomized true si debe ser iniciado con pesos y bias con valores al azar.
     *
     * @return red neuronal inicializada.
     */
    public BasicNetwork initializeEncogPerceptron(boolean randomized) {
        if ( getPerceptronConfiguration().getNeuronQuantityInLayer() == null || getPerceptronConfiguration().
                getNeuronQuantityInLayer().length < 2 ) {
            throw new IllegalArgumentException(
                    "la cantidad de capas es de minimo 2 para un perceptrón (incluyendo entrada y salida)");
        }
        BasicNetwork perceptron = new BasicNetwork();
        ActivationFunction function;
        perceptron.addLayer(new BasicLayer(null, getPerceptronConfiguration().containBias(),
                getPerceptronConfiguration().getNeuronQuantityInLayer()[0]));
        for ( int i = 1; i < getPerceptronConfiguration().getNeuronQuantityInLayer().length - 1; i++ ) {
            function = getPerceptronConfiguration().getActivationFunctionForEncog()[i - 1].clone();
            perceptron.addLayer(new BasicLayer(function, getPerceptronConfiguration().containBias(),
                    getPerceptronConfiguration().getNeuronQuantityInLayer()[i]));
        }
        //getPerceptronConfiguration().getNeuronQuantityInLayer().length - 2 porque el for finaliza en getPerceptronConfiguration().getNeuronQuantityInLayer().length - 1
        function = getPerceptronConfiguration().getActivationFunctionForEncog()[getPerceptronConfiguration().
                getNeuronQuantityInLayer().length - 2].clone();
        perceptron.addLayer(new BasicLayer(function, getPerceptronConfiguration().containBias(),
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
                getPerceptronConfiguration().setNeuralNetwork(initializeEncogPerceptron(randomizedIfNotExist));
                SerializeObject.save(perceptronFile, getPerceptronConfiguration().getNeuralNetwork());
            } else {
                //si el archivo existe, lo cargamos como perceptron entrenado al juego
                getPerceptronConfiguration().setNeuralNetwork((BasicNetwork) SerializeObject.load(perceptronFile));
            }
        } else {
            getPerceptronConfiguration().setNeuralNetwork(initializeEncogPerceptron(randomizedIfNotExist));
        }
    }

    @Override
    public void saveNeuralNetwork(File perceptronFile) throws Exception {
        SerializeObject.save(perceptronFile, getPerceptronConfiguration().getNeuralNetwork());
    }

}
