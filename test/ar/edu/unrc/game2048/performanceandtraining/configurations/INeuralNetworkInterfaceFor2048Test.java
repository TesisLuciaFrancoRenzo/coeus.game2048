/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import java.io.File;
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
public class INeuralNetworkInterfaceFor2048Test {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public INeuralNetworkInterfaceFor2048Test() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clone method, of class INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        INeuralNetworkInterfaceFor2048 instance = null;
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareNeuralNetworks method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testCompareNeuralNetworks() {
        System.out.println("compareNeuralNetworks");
        File randomFile = null;
        File trainedFile = null;
        INeuralNetworkInterfaceFor2048 instance = null;
        instance.compareNeuralNetworks(randomFile, trainedFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLibName method, of class INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testGetLibName() {
        System.out.println("getLibName");
        INeuralNetworkInterfaceFor2048 instance = null;
        String expResult = "";
        String result = instance.getLibName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNTupleConfiguration method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testGetNTupleConfiguration() {
        System.out.println("getNTupleConfiguration");
        INeuralNetworkInterfaceFor2048 instance = null;
        NTupleConfiguration2048 expResult = null;
        NTupleConfiguration2048 result = instance.getNTupleConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronConfiguration method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testGetPerceptronConfiguration() {
        System.out.println("getPerceptronConfiguration");
        INeuralNetworkInterfaceFor2048 instance = null;
        PerceptronConfiguration2048 expResult = null;
        PerceptronConfiguration2048 result = instance.getPerceptronConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronInterface method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testGetPerceptronInterface() {
        System.out.println("getPerceptronInterface");
        INeuralNetworkInterfaceFor2048 instance = null;
        IPerceptronInterface expResult = null;
        IPerceptronInterface result = instance.getPerceptronInterface();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadOrCreatePerceptron method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testLoadOrCreatePerceptron() throws Exception {
        System.out.println("loadOrCreatePerceptron");
        File perceptronFile = null;
        boolean randomizedIfNotExist = false;
        INeuralNetworkInterfaceFor2048 instance = null;
        instance.loadOrCreatePerceptron(perceptronFile, randomizedIfNotExist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playATurn method, of class INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testPlayATurn() {
        System.out.println("playATurn");
        INeuralNetworkInterfaceFor2048 instance = null;
        IsolatedComputation expResult = null;
        IsolatedComputation result = instance.playATurn(null, null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePerceptron method, of class INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testSavePerceptron() throws Exception {
        System.out.println("savePerceptron");
        File perceptronFile = null;
        INeuralNetworkInterfaceFor2048 instance = null;
        instance.savePerceptron(perceptronFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNTupleConfiguration method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testSetNTupleConfiguration() {
        System.out.println("setNTupleConfiguration");
        NTupleConfiguration2048 nTupleConfiguration = null;
        INeuralNetworkInterfaceFor2048 instance = null;
        instance.setNTupleConfiguration(nTupleConfiguration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPerceptronConfiguration method, of class
     * INeuralNetworkInterfaceFor2048.
     */
    @Test
    public void testSetPerceptronConfiguration() {
        System.out.println("setPerceptronConfiguration");
        INeuralNetworkInterfaceFor2048 instance = null;
        instance.setPerceptronConfiguration(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class INeuralNetworkInterfaceFor2048Impl extends INeuralNetworkInterfaceFor2048 {

        public INeuralNetworkInterfaceFor2048Impl() {
            super(null);
        }

        @Override
        public void compareNeuralNetworks(File randomFile, File trainedFile) {
        }

        @Override
        public String getLibName() {
            return "";
        }

        @Override
        public IPerceptronInterface getPerceptronInterface() {
            return null;
        }

        @Override
        public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
        }

        @Override
        public void savePerceptron(File perceptronFile) throws Exception {
        }
    }

    public class INeuralNetworkInterfaceFor2048Impl extends INeuralNetworkInterfaceFor2048 {

        public INeuralNetworkInterfaceFor2048Impl() {
            super(null);
        }

        public void compareNeuralNetworks(File randomFile, File trainedFile) {
        }

        public String getLibName() {
            return "";
        }

        public IPerceptronInterface getPerceptronInterface() {
            return null;
        }

        public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
        }

        public void savePerceptron(File perceptronFile) throws Exception {
        }
    }

}
