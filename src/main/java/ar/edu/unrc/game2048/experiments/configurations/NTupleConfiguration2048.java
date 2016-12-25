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
import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces.NTupleExperimentInterface;
import org.encog.util.arrayutil.NormalizedField;

import java.util.List;
import java.util.function.Function;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class NTupleConfiguration2048
        extends LearningExperiment
        implements Cloneable, IConfiguration2048 {
    protected Function< Double, Double > activationFunction;
    protected List< SamplePointValue >   allSamplePointPossibleValues;
    protected boolean                    concurrency;
    protected Function< Double, Double > derivedActivationFunction;
    protected int[]                      nTuplesLength;
    protected NormalizedField            normOutput;
    private   NTupleSystem               nTupleSystem;

    /**
     * @return
     *
     * @throws CloneNotSupportedException
     */
    @Override
    public
    NTupleConfiguration2048 clone()
            throws CloneNotSupportedException {
        return (NTupleConfiguration2048) super.clone();
    }

    /**
     * @return
     */
    public
    Function< Double, Double > getActivationFunction() {
        return activationFunction;
    }

    /**
     * @return
     */
    public
    List< SamplePointValue > getAllSamplePointPossibleValues() {
        return allSamplePointPossibleValues;
    }

    /**
     * @return
     */
    public
    Function< Double, Double > getDerivedActivationFunction() {
        return derivedActivationFunction;
    }

    /**
     * @param board
     * @param nTupleIndex
     *
     * @return
     */
    public abstract
    SamplePointValue[] getNTuple(
            GameBoard board,
            int nTupleIndex
    );

    /**
     * @return the nTupleSystem
     */
    public
    NTupleSystem getNTupleSystem() {
        return nTupleSystem;
    }

    /**
     * @param nTupleSystem
     */
    public
    void setNTupleSystem( final NTupleSystem nTupleSystem ) {
        this.nTupleSystem = nTupleSystem;
    }

    /**
     * @return
     */
    public
    int[] getNTuplesLength() {
        return nTuplesLength;
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
        if ( getExperimentName() == null ) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        setNeuralNetworkInterfaceFor2048(new NTupleExperimentInterface(this));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            final NTupleSystem nTupleSystem
    ) {
        return new TDLambdaLearning(nTupleSystem, afterState, ( getAlpha() != null ) ? getAlpha()[0] : null,
                getLambda(),
                getEligibilityTraceLength(),
                isReplaceEligibilityTraces(),
                getGamma(),
                getConcurrencyInLayer(), isCanCollectStatistics());
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            final INeuralNetworkInterface perceptronInterface
    ) {
        return null;
    }

    /**
     * @return
     */
    public
    boolean isConcurrency() {
        return concurrency;
    }
}
