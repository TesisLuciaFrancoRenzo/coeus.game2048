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

import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import java.util.List;
import java.util.function.Function;
import org.encog.util.arrayutil.NormalizedField;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract class NTupleConfiguration2048 implements Cloneable, IConfiguration2048 {

    /**
     *
     */
    public Function<Double, Double> activationFunction;

    /**
     *
     */
    public List<SamplePointState> allSamplePointStates;

    /**
     *
     */
    public Function<Double, Double> derivatedActivationFunction;

    /**
     *
     */
    public int[] nTuplesLenght;

    /**
     *
     */
    public NormalizedField normOutput;
    private NTupleSystem nTupleSystem;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param board
     * @param nTupleIndex <p>
     * @return
     */
    public abstract SamplePointState[] getNTuple(GameBoard board, int nTupleIndex);

    /**
     * @return the nTupleSystem
     */
    public NTupleSystem getNTupleSystem() {
        return nTupleSystem;
    }

    /**
     *
     * @param nTupleSystem
     */
    public void setNTupleSystem(NTupleSystem nTupleSystem) {
        this.nTupleSystem = nTupleSystem;
    }

}
