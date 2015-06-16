/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class FullNTupleScoreTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public FullNTupleScoreTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateNormalizedPerceptronInput method, of class FullNTupleScore.
     */
    @Test
    public void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");
        FullNTupleScore instance = new FullNTupleScore();
        instance.calculateNormalizedPerceptronInput(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class FullNTupleScore.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Game2048 game = null;
        Object[] output = null;
        FullNTupleScore instance = new FullNTupleScore();
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(game, output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class FullNTupleScore.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        FullNTupleScore instance = new FullNTupleScore();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoardReward method, of class FullNTupleScore.
     */
    @Test
    public void testGetBoardReward() {
        System.out.println("getBoardReward");
        GameBoard board = null;
        int outputNeuron = 0;
        FullNTupleScore instance = new FullNTupleScore();
        double expResult = 0.0;
        double result = instance.getBoardReward(board, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class FullNTupleScore.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        Game2048 game = null;
        int outputNeuron = 0;
        FullNTupleScore instance = new FullNTupleScore();
        double expResult = 0.0;
        double result = instance.getFinalReward(game, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class FullNTupleScore.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        FullNTupleScore instance = new FullNTupleScore();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
