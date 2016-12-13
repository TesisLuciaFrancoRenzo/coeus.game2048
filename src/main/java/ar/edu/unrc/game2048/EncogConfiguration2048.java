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
package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.experiments.configurations.LearningExperiment;
import ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces.EncogExperimentInterface;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizedField;

import java.util.List;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class EncogConfiguration2048
        extends LearningExperiment
        implements Cloneable, IConfiguration2048 {

    public final static Class<?>[] PARAMETER_TYPE = {Boolean.class};

    protected ActivationFunction[] activationFunctionForEncog;
    protected double               activationFunctionMax;
    protected double               activationFunctionMin;
    protected boolean hasBias = true;
    protected BasicNetwork neuralNetwork;
    protected int[] neuronQuantityInLayer;
    protected NormalizedField normInput;
    protected NormalizedField normOutput;
    protected int tileToWin = -1;

    /**
     * @param board
     * @param normalizedPerceptronInput
     */
    public abstract
    void calculateNormalizedPerceptronInput(
            GameBoard board,
            List<Double> normalizedPerceptronInput
    );

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    Object clone()
            throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param game
     * @param output
     *
     * @return
     */
    public abstract
    Double computeNumericRepresentationFor(
            Game2048 game,
            Object[] output
    );

    /**
     * @return
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
            ActivationFunction[] activationFunctionForEncog
    ) {
        this.activationFunctionForEncog = activationFunctionForEncog;
    }

    /**
     * @return
     */
    public
    double getActivationFunctionMax() {
        return activationFunctionMax;
    }

    /**
     * @param activationFunctionMax
     */
    public
    void setActivationFunctionMax(double activationFunctionMax) {
        this.activationFunctionMax = activationFunctionMax;
    }

    /**
     * @return
     */
    public
    double getActivationFunctionMin() {
        return activationFunctionMin;
    }

    /**
     * @param activationFunctionMin
     */
    public
    void setActivationFunctionMin(double activationFunctionMin) {
        this.activationFunctionMin = activationFunctionMin;
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
    void setNeuralNetwork(BasicNetwork neuralNetwork) {
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
    void setNeuronQuantityInLayer(int[] neuronQuantityInLayer) {
        this.neuronQuantityInLayer = neuronQuantityInLayer;
    }

    /**
     * @return
     */
    public
    NormalizedField getNormInput() {
        return normInput;
    }

    /**
     * @return
     */
    public
    NormalizedField getNormOutput() {
        return normOutput;
    }

    @Override
    public
    void initialize() {
        if (getExperimentName() == null) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(this));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            INeuralNetworkInterface perceptronInterface
    ) {
        return new TDLambdaLearning(perceptronInterface,
                afterState,
                getAlpha(),
                getLambda(),
                isReplaceEligibilityTraces(),
                getGamma(),
                getConcurrencyInLayer(),
                false
        );
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            NTupleSystem nTupleSystem
    ) {
        return null;
    }

    /**
     * @return
     */
    public abstract
    boolean isConcurrentInputEnabled();

    /**
     * @return
     */
    public
    boolean isHasBias() {
        return hasBias;
    }

    /**
     * @param hasBias
     */
    public
    void setHasBias(boolean hasBias) {
        this.hasBias = hasBias;
    }

    /**
     * @return
     */
    public abstract
    boolean useNTupleList();

}
