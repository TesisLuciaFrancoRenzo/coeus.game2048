/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class SymetricSample01MaxTileTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public SymetricSample01MaxTileTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateNormalizedPerceptronInput method, of class
     * SymetricSample01MaxTile.
     */
    @Test
    public void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        //      instance.calculateNormalizedPerceptronInput(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class
     * SymetricSample01MaxTile.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Game2048 game = null;
        Object[] output = null;
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(game, output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class
     * SymetricSample01MaxTile.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoardReward method, of class SymetricSample01MaxTile.
     */
    @Test
    public void testGetBoardReward() {
        System.out.println("getBoardReward");
        GameBoard board = null;
        int outputNeuron = 0;
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        double expResult = 0.0;
        double result = instance.getBoardReward(board, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class SymetricSample01MaxTile.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        Game2048 game = null;
        int outputNeuron = 0;
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        double expResult = 0.0;
        double result = instance.getFinalReward(game, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class
     * SymetricSample01MaxTile.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        SymetricSample01MaxTile instance = new SymetricSample01MaxTile();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
