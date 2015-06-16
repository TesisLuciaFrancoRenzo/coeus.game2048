/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class PerceptronConfiguration2048Test {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public PerceptronConfiguration2048Test() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateNormalizedPerceptronInput method, of class PerceptronConfiguration2048.
     */
    @Test
    public void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");
        PerceptronConfiguration2048 instance = new PerceptronConfiguration2048Impl();
        instance.calculateNormalizedPerceptronInput(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clone method, of class PerceptronConfiguration2048.
     */
    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        PerceptronConfiguration2048 instance = new PerceptronConfiguration2048Impl();
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class PerceptronConfiguration2048.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Game2048 game = null;
        Object[] output = null;
        PerceptronConfiguration2048 instance = new PerceptronConfiguration2048Impl();
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(game, output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeuralNetwork method, of class PerceptronConfiguration2048.
     */
    @Test
    public void testGetNeuralNetwork() {
        System.out.println("getNeuralNetwork");
        PerceptronConfiguration2048 instance = new PerceptronConfiguration2048Impl();
        Object expResult = null;
        Object result = instance.getNeuralNetwork();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeuralNetwork method, of class PerceptronConfiguration2048.
     */
    @Test
    public void testSetNeuralNetwork() {
        System.out.println("setNeuralNetwork");
        Object neuralNetwork = null;
        PerceptronConfiguration2048 instance = new PerceptronConfiguration2048Impl();
        instance.setNeuralNetwork(neuralNetwork);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PerceptronConfiguration2048Impl extends PerceptronConfiguration2048 {

        /**
         *
         * @param board
         * @param normalizedPerceptronInput
         */
        public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        }

        @Override
        public IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Object[] output) {
            return null;
        }
    }

    public class PerceptronConfiguration2048Impl extends PerceptronConfiguration2048 {

        public void calculateNormalizedPerceptronInput(GameBoard<NeuralNetworkClass> board, List<Double> normalizedPerceptronInput) {
        }

        public IsolatedComputation<Double> computeNumericRepresentationFor(Game2048 game, Object[] output) {
            return null;
        }
    }

}
