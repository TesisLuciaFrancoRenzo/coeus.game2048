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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class NTupleConfiguration2048Test {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    public NTupleConfiguration2048Test() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of clone method, of class NTupleConfiguration2048.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        NTupleConfiguration2048 instance = new NTupleConfiguration2048Impl();
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNTuple method, of class NTupleConfiguration2048.
     */
    @Test
    public void testGetNTuple() {
        System.out.println("getNTuple");
        GameBoard board = null;
        int nTupleIndex = 0;
        NTupleConfiguration2048 instance = new NTupleConfiguration2048Impl();
        SamplePointState[] expResult = null;
        SamplePointState[] result = instance.getNTuple(board, nTupleIndex);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNTupleSystem method, of class NTupleConfiguration2048.
     */
    @Test
    public void testGetNTupleSystem() {
        System.out.println("getNTupleSystem");
        NTupleConfiguration2048 instance = new NTupleConfiguration2048Impl();
        NTupleSystem expResult = null;
        NTupleSystem result = instance.getNTupleSystem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNTupleSystem method, of class NTupleConfiguration2048.
     */
    @Test
    public void testSetNTupleSystem() {
        System.out.println("setNTupleSystem");
        NTupleSystem nTupleSystem = null;
        NTupleConfiguration2048 instance = new NTupleConfiguration2048Impl();
        instance.setNTupleSystem(nTupleSystem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class NTupleConfiguration2048Impl extends NTupleConfiguration2048 {

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getBoardReward(GameBoard board, int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getFinalReward(GameBoard board, int outputNeuron) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
            return null;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     *
     */
    public class NTupleConfiguration2048Impl extends NTupleConfiguration2048 {

        /**
         *
         * @param board
         * @param nTupleIndex
         *
         * @return
         */
        @Override
        public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
            return null;
        }
    }

    /**
     *
     */
    public class NTupleConfiguration2048Impl extends NTupleConfiguration2048 {

        /**
         *
         * @param board
         * @param nTupleIndex
         *
         * @return
         */
        @Override
        public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
            return null;
        }
    }

}
