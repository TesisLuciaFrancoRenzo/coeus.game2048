/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class BasicMaxTileTanHTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public BasicMaxTileTanHTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class BasicMaxTileTanH.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        BasicMaxTileTanH instance = new BasicMaxTileTanH();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoardReward method, of class BasicMaxTileTanH.
     */
    @Test
    public void testGetBoardReward() {
        System.out.println("getBoardReward");
        GameBoard board = null;
        int outputNeuron = 0;
        BasicMaxTileTanH instance = new BasicMaxTileTanH();
        double expResult = 0.0;
        double result = instance.getBoardReward(board, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class BasicMaxTileTanH.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        Game2048 game = null;
        int outputNeuron = 0;
        BasicMaxTileTanH instance = new BasicMaxTileTanH();
        double expResult = 0.0;
        double result = instance.getFinalReward(game, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNTuple method, of class BasicMaxTileTanH.
     */
    @Test
    public void testGetNTuple() {
        System.out.println("getNTuple");
        GameBoard board = null;
        int nTupleIndex = 0;
        BasicMaxTileTanH instance = new BasicMaxTileTanH();
        SamplePointState[] expResult = null;
        SamplePointState[] result = instance.getNTuple(board, nTupleIndex);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class BasicMaxTileTanH.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        BasicMaxTileTanH instance = new BasicMaxTileTanH();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
