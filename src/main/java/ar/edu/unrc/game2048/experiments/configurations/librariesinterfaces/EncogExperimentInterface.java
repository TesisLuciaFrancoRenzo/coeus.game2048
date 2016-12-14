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
package ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.utils.FunctionUtils;
import ar.edu.unrc.game2048.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.INeuralNetworkInterfaceFor2048;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.obj.SerializeObject;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Interfaz de experimentos entre la librería Encog y Coeus
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class EncogExperimentInterface
        extends INeuralNetworkInterfaceFor2048 {

    /**
     * función de activación
     */
    protected List< Function< Double, Double > > activationFunction;

    /**
     * derivada de la función de activación
     */
    protected List< Function< Double, Double > > derivedActivationFunction;

    /**
     * @param perceptronConfiguration configuración
     */
    public
    EncogExperimentInterface(
            EncogConfiguration2048 perceptronConfiguration
    ) {
        super(perceptronConfiguration);
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    Object clone()
            throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public
    String getLibName() {
        return "Encog";
    }

    @Override
    public
    INeuralNetworkInterface getNeuralNetworkInterface() {
        activationFunction = new ArrayList<>(perceptronConfiguration.getActivationFunctionForEncog().length);
        derivedActivationFunction = new ArrayList<>(perceptronConfiguration.getActivationFunctionForEncog().length);

        for ( ActivationFunction activationFunctionForEncog : perceptronConfiguration.getActivationFunctionForEncog() ) {
            if ( activationFunctionForEncog instanceof ActivationTANH ) {
                activationFunction.add(FunctionUtils.TANH);
                derivedActivationFunction.add(FunctionUtils.TANH_DERIVED);
            } else if ( activationFunctionForEncog instanceof ActivationSigmoid ) {
                activationFunction.add(FunctionUtils.SIGMOID);
                derivedActivationFunction.add(FunctionUtils.SIGMOID_DERIVED);
            } else if ( activationFunctionForEncog instanceof ActivationLinear ) {
                activationFunction.add(FunctionUtils.LINEAR);
                derivedActivationFunction.add(FunctionUtils.LINEAR_DERIVED);
            } else {
                throw new IllegalArgumentException("El test esta pensado para utilizar TANH, Sigmoid o Linear como función de activación");
            }
        }

        return new INeuralNetworkInterface() {

            @Override
            public
            Function< Double, Double > getActivationFunction( int layerIndex ) {
                return activationFunction.get(layerIndex - 1);
            }

            @Override
            public
            double getBias(
                    int layerIndex,
                    int neuronIndex
            ) {
                if ( hasBias(layerIndex) ) {
                    final BasicNetwork neuralNetwork = perceptronConfiguration.getNeuralNetwork();
                    return neuralNetwork.getWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex);
                } else {
                    throw new IllegalStateException("No hay bias en la capa " + layerIndex);
                }
            }

            @Override
            public
            Function< Double, Double > getDerivedActivationFunction( int layerIndex ) {
                return derivedActivationFunction.get(layerIndex - 1);
            }

            @Override
            public
            int getLayerQuantity() {
                return perceptronConfiguration.getNeuralNetwork().getLayerCount();
            }

            @Override
            public
            int getNeuronQuantityInLayer( int layerIndex ) {
                return perceptronConfiguration.getNeuralNetwork().getLayerNeuronCount(layerIndex);
            }

            @Override
            public
            double getWeight(
                    int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer
            ) {
                return perceptronConfiguration.getNeuralNetwork().getWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex);
            }

            @Override
            public
            boolean hasBias( int layerIndex ) {
                return perceptronConfiguration.getNeuralNetwork().isLayerBiased(layerIndex - 1);
            }

            @Override
            public
            void setBias(
                    int layerIndex,
                    int neuronIndex,
                    double correctedBias
            ) {
                if ( hasBias(layerIndex) ) {
                    final BasicNetwork neuralNetwork = perceptronConfiguration.getNeuralNetwork();
                    neuralNetwork.setWeight(layerIndex - 1, neuralNetwork.getLayerNeuronCount(layerIndex - 1), neuronIndex, correctedBias);
                } else {
                    throw new IllegalStateException("No hay bias en la capa " + layerIndex);
                }
            }

            @Override
            public
            void setWeight(
                    int layerIndex,
                    int neuronIndex,
                    int neuronIndexPreviousLayer,
                    double correctedWeight
            ) {
                perceptronConfiguration.getNeuralNetwork().setWeight(layerIndex - 1, neuronIndexPreviousLayer, neuronIndex, correctedWeight);
            }
        };
    }

    /**
     * Inicializa red neuronal con Encog.
     *
     * @param randomized true si debe ser iniciado con pesos y bias con valores al azar.
     *
     * @return red neuronal inicializada.
     */
    public
    BasicNetwork initializeEncogPerceptron( boolean randomized ) {
        if ( perceptronConfiguration.getNeuronQuantityInLayer() == null || perceptronConfiguration.getNeuronQuantityInLayer().length < 2 ) {
            throw new IllegalArgumentException("la cantidad de capas es de mínimo 2 para un perceptrón (incluyendo entrada y salida)");
        }
        BasicNetwork perceptron = new BasicNetwork();
        ActivationFunction function;
        perceptron.addLayer(new BasicLayer(null, perceptronConfiguration.containBias(), perceptronConfiguration.getNeuronQuantityInLayer()[0]));
        for ( int i = 1; i < perceptronConfiguration.getNeuronQuantityInLayer().length - 1; i++ ) {
            function = perceptronConfiguration.getActivationFunctionForEncog()[i - 1].clone();
            perceptron.addLayer(new BasicLayer(function, perceptronConfiguration.containBias(), perceptronConfiguration.getNeuronQuantityInLayer()[i]
            ));
        }
        //perceptronConfiguration.getNeuronQuantityInLayer().length - 2 porque el for finaliza en perceptronConfiguration.getNeuronQuantityInLayer
        // ().length - 1
        function = perceptronConfiguration.getActivationFunctionForEncog()[perceptronConfiguration.getNeuronQuantityInLayer().length - 2].clone();
        perceptron.addLayer(new BasicLayer(function,
                                           false,
                                           perceptronConfiguration.getNeuronQuantityInLayer()[
                                                   perceptronConfiguration.getNeuronQuantityInLayer().length - 1]
        ));
        perceptron.getStructure().finalizeStructure();
        if ( randomized ) {
            perceptron.reset();
        }
        return perceptron;
    }

    @Override
    public
    void loadOrCreatePerceptron(
            File perceptronFile,
            boolean randomizedIfNotExist,
            boolean createFile
    )
            throws Exception {
        if ( createFile ) {
            if ( !perceptronFile.exists() ) {
                //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
                perceptronConfiguration.setNeuralNetwork(initializeEncogPerceptron(randomizedIfNotExist));
                SerializeObject.save(perceptronFile, perceptronConfiguration.getNeuralNetwork());
            } else {
                //si el archivo existe, lo cargamos como perceptron entrenado al juego
                perceptronConfiguration.setNeuralNetwork((BasicNetwork) SerializeObject.load(perceptronFile));
            }
        } else {
            perceptronConfiguration.setNeuralNetwork(initializeEncogPerceptron(randomizedIfNotExist));
        }
    }

    @Override
    public
    void saveNeuralNetwork( File perceptronFile )
            throws Exception {
        SerializeObject.save(perceptronFile, perceptronConfiguration.getNeuralNetwork());
    }

    @Override
    public
    void saveNeuralNetwork( OutputStream outputStream )
            throws Exception {
        //TODO implementar cuando sea necesario
    }

    /**
     * Utilizado con propósitos de testing.
     *
     * @param neuralNetwork
     */
    public
    void setNeuralNetworkForTesting( BasicNetwork neuralNetwork ) {
        perceptronConfiguration.setNeuralNetwork(neuralNetwork);
    }

}
