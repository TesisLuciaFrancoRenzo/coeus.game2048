/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import java.util.List;
import java.util.function.Function;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author Franco
 */
public abstract class NTupleConfiguration2048 implements Cloneable {

    public Function<Double, Double> activationFunction;
    public List<SamplePointState> allSamplePointStates;
    public Function<Double, Double> derivatedActivationFunction;
    public int[] nTuplesLenght;
    public NormalizedField normOutput;
    private NTupleSystem nTupleSystem;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public abstract SamplePointState[] getNTuple(GameBoard board, int nTupleIndex);

    /**
     * @return the nTupleSystem
     */
    public NTupleSystem getNTupleSystem() {
        return nTupleSystem;
    }

    public void setNTupleSystem(NTupleSystem nTupleSystem) {
        this.nTupleSystem = nTupleSystem;
    }

    public abstract double getCurrentReward(Game2048 game);

    public abstract double getBoardReward(GameBoard board);

    public abstract double getCurrentRewardIf(Game2048 game, GameBoard afterstate);

    public abstract double denormalizeValueFromPerceptronOutput(double value);

    public abstract double normalizeValueToPerceptronOutput(double value);

}
