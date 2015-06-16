/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class NTupleConfiguration2048Test {

    public NTupleConfiguration2048Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clone method, of class NTupleConfiguration2048.
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

    public class NTupleConfiguration2048Impl extends NTupleConfiguration2048 {

        public SamplePointState[] getNTuple(GameBoard board, int nTupleIndex) {
            return null;
        }
    }

}
