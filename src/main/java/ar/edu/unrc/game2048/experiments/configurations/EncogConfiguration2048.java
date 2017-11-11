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
package ar.edu.unrc.game2048.experiments.configurations;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces.EncogExperimentInterface;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizedField;

import java.util.List;
import java.util.Random;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.AFTER_STATE;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class EncogConfiguration2048
        extends LearningExperiment
        implements Cloneable, IConfiguration2048 {

    public static final Class< ? >[] PARAMETER_TYPE = { Boolean.class };

    protected ActivationFunction[] activationFunctionForEncog = null;
    protected double               activationFunctionMax      = 0.0;
    protected double               activationFunctionMin      = 0.0;
    protected boolean              hasBias                    = true;
    protected BasicNetwork         neuralNetwork              = null;
    protected int[]                neuronQuantityInLayer      = null;
    protected NormalizedField      normOutput                 = null;

    /**
     * @param board tablero a calcular
     * @param normalizedPerceptronInput lista de salida, que representa las entradas del perceptron normalizadas
     */
    public abstract
    void calculateNormalizedPerceptronInput(
            GameBoard board,
            List< Double > normalizedPerceptronInput
    );

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    EncogConfiguration2048 clone()
            throws CloneNotSupportedException {
        return (EncogConfiguration2048) super.clone();
    }

    /**
     * @param game problema
     * @param output salida del perceptrón.
     *
     * @return interpretación numérica para el tablero actual del juego, util para comparaciones
     */
    public abstract
    Double computeNumericRepresentationFor(
            Game2048 game,
            Object[] output
    );

    /**
     * @return true si el perceptrón contiene bias
     */
    public
    boolean containBias() {
        return hasBias;
    }

    /**
     * @return the activationFunctionForEncog
     */
    public
    ActivationFunction[] getActivationFunctionForEncog() {
        return activationFunctionForEncog;
    }

    /**
     * @param activationFunctionForEncog the activationFunctionForEncog to set
     */
    public
    void setActivationFunctionForEncog(
            final ActivationFunction[] activationFunctionForEncog
    ) {
        this.activationFunctionForEncog = activationFunctionForEncog;
    }

    /**
     * @return true si se permite concurrencia en la capa de entrada
     */
    public abstract
    boolean isConcurrentInputEnabled();

    /**
     * @param activationFunctionMax valor maximo soportado por la función de activación
     */
    public
    void setActivationFunctionMax( final double activationFunctionMax ) {
        this.activationFunctionMax = activationFunctionMax;
    }

    /**
     * @return the neuralNetwork
     */
    public
    BasicNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    /**
     * @param neuralNetwork the neuralNetwork to set
     */
    public
    void setNeuralNetwork( final BasicNetwork neuralNetwork ) {
        this.neuralNetwork = neuralNetwork;
    }

    /**
     * @return the neuronQuantityInLayer
     */
    public
    int[] getNeuronQuantityInLayer() {
        return neuronQuantityInLayer;
    }

    /**
     * @param neuronQuantityInLayer the neuronQuantityInLayer to set
     */
    public
    void setNeuronQuantityInLayer( final int[] neuronQuantityInLayer ) {
        this.neuronQuantityInLayer = neuronQuantityInLayer;
    }

    @Override
    public
    void initialize() {
        if ( getExperimentName() == null ) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(this));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            final INeuralNetworkInterface perceptronInterface
    ) {
        return new TDLambdaLearning(perceptronInterface,
                AFTER_STATE,
                getAlpha(),
                getLambda(),
                isReplaceEligibilityTraces(),
                getGamma(),
                getConcurrencyInLayer(),
                new Random(),
                isCanCollectStatistics());
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            final NTupleSystem nTupleSystem
    ) {
        return null;
    }

    /**
     * @param activationFunctionMin valor mínimo soportado por la función de activación
     */
    public
    void setActivationFunctionMin( final double activationFunctionMin ) {
        this.activationFunctionMin = activationFunctionMin;
    }

    /**
     * @return true si se usan NTupleList.
     */
    public abstract
    boolean useNTupleList();

}
