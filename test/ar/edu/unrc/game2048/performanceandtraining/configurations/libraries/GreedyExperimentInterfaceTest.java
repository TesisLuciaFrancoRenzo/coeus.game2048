/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class GreedyExperimentInterfaceTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public GreedyExperimentInterfaceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compareNeuralNetworks method, of class GreedyExperimentInterface.
     */
    @Test
    public void testCompareNeuralNetworks() {
        System.out.println("compareNeuralNetworks");
        File randomFile = null;
        File trainedFile = null;
        GreedyExperimentInterface instance = null;
        instance.compareNeuralNetworks(randomFile, trainedFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLibName method, of class GreedyExperimentInterface.
     */
    @Test
    public void testGetLibName() {
        System.out.println("getLibName");
        GreedyExperimentInterface instance = null;
        String expResult = "";
        String result = instance.getLibName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronInterface method, of class GreedyExperimentInterface.
     */
    @Test
    public void testGetPerceptronInterface() {
        System.out.println("getPerceptronInterface");
        GreedyExperimentInterface instance = null;
        IPerceptronInterface expResult = null;
        IPerceptronInterface result = instance.getPerceptronInterface();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadOrCreatePerceptron method, of class GreedyExperimentInterface.
     */
    @Test
    public void testLoadOrCreatePerceptron() throws Exception {
        System.out.println("loadOrCreatePerceptron");
        File perceptronFile = null;
        boolean randomizedIfNotExist = false;
        GreedyExperimentInterface instance = null;
        instance.loadOrCreatePerceptron(perceptronFile, randomizedIfNotExist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playATurn method, of class GreedyExperimentInterface.
     */
    @Test
    public void testPlayATurn() {
        System.out.println("playATurn");
        Game2048 game = null;
        TDLambdaLearning learningMethod = null;
        GreedyExperimentInterface instance = null;
        IsolatedComputation expResult = null;
        IsolatedComputation result = instance.playATurn(game, learningMethod);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePerceptron method, of class GreedyExperimentInterface.
     */
    @Test
    public void testSavePerceptron() throws Exception {
        System.out.println("savePerceptron");
        File perceptronFile = null;
        GreedyExperimentInterface instance = null;
        instance.savePerceptron(perceptronFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
