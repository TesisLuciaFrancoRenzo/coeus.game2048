/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class EncogExperimentInterfaceTest {

    public EncogExperimentInterfaceTest() {
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
     * Test of compareNeuralNetworks method, of class EncogExperimentInterface.
     */
    @Test
    public void testCompareNeuralNetworks() {
        System.out.println("compareNeuralNetworks");
        File randomFile = null;
        File trainedFile = null;
        EncogExperimentInterface instance = null;
        instance.compareNeuralNetworks(randomFile, trainedFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfigForTesting method, of class EncogExperimentInterface.
     */
    @Test
    public void testSetConfigForTesting() {
        System.out.println("setConfigForTesting");
        BasicNetwork neuralNetwork = null;
        EncogExperimentInterface instance = null;
        instance.setConfigForTesting(neuralNetwork);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLibName method, of class EncogExperimentInterface.
     */
    @Test
    public void testGetLibName() {
        System.out.println("getLibName");
        EncogExperimentInterface instance = null;
        String expResult = "";
        String result = instance.getLibName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronInterface method, of class EncogExperimentInterface.
     */
    @Test
    public void testGetPerceptronInterface() {
        System.out.println("getPerceptronInterface");
        EncogExperimentInterface instance = null;
        IPerceptronInterface expResult = null;
        IPerceptronInterface result = instance.getPerceptronInterface();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initializeEncogPerceptron method, of class EncogExperimentInterface.
     */
    @Test
    public void testInitializeEncogPerceptron() {
        System.out.println("initializeEncogPerceptron");
        boolean randomized = false;
        EncogExperimentInterface instance = null;
        BasicNetwork expResult = null;
        BasicNetwork result = instance.initializeEncogPerceptron(randomized);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadOrCreatePerceptron method, of class EncogExperimentInterface.
     */
    @Test
    public void testLoadOrCreatePerceptron() throws Exception {
        System.out.println("loadOrCreatePerceptron");
        File perceptronFile = null;
        boolean randomizedIfNotExist = false;
        EncogExperimentInterface instance = null;
        instance.loadOrCreatePerceptron(perceptronFile, randomizedIfNotExist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePerceptron method, of class EncogExperimentInterface.
     */
    @Test
    public void testSavePerceptron() throws Exception {
        System.out.println("savePerceptron");
        File perceptronFile = null;
        EncogExperimentInterface instance = null;
        instance.savePerceptron(perceptronFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
