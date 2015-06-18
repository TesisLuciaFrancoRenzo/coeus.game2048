/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

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
public class IConfiguration2048Test {

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
    public IConfiguration2048Test() {
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
     * Test of denormalizeValueFromPerceptronOutput method, of class
     * IConfiguration2048.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        IConfiguration2048 instance = new IConfiguration2048Impl();
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoardReward method, of class IConfiguration2048.
     */
    @Test
    public void testGetBoardReward() {
        System.out.println("getBoardReward");
        GameBoard board = null;
        int outputNeuron = 0;
        IConfiguration2048 instance = new IConfiguration2048Impl();
        double expResult = 0.0;
        double result = instance.getBoardReward(board, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class IConfiguration2048.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        Game2048 game = null;
        int outputNeuron = 0;
        IConfiguration2048 instance = new IConfiguration2048Impl();
        double expResult = 0.0;
        double result = instance.getFinalReward(game, outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class
     * IConfiguration2048.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        IConfiguration2048 instance = new IConfiguration2048Impl();
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class IConfiguration2048Impl implements IConfiguration2048 {

        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        @Override
        public double getBoardReward(GameBoard board, int outputNeuron) {
            return 0.0;
        }

        @Override
        public double getFinalReward(Game2048 game, int outputNeuron) {
            return 0.0;
        }

        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }

    /**
     *
     */
    public class IConfiguration2048Impl implements IConfiguration2048 {

        /**
         *
         * @param value
         * @return
         */
        @Override
        public double denormalizeValueFromPerceptronOutput(Object value) {
            return 0.0;
        }

        /**
         *
         * @param board
         * @param outputNeuron
         * @return
         */
        @Override
        public double getBoardReward(GameBoard board, int outputNeuron) {
            return 0.0;
        }

        /**
         *
         * @param game
         * @param outputNeuron
         * @return
         */
        @Override
        public double getFinalReward(Game2048 game, int outputNeuron) {
            return 0.0;
        }

        /**
         *
         * @param value
         * @return
         */
        @Override
        public double normalizeValueToPerceptronOutput(Object value) {
            return 0.0;
        }
    }
}
