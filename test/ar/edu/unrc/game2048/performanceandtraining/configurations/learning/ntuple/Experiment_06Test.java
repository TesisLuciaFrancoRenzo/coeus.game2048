/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class Experiment_06Test {

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
    public Experiment_06Test() {
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
     * Test of initialize method, of class Experiment_06.
     * @throws java.lang.Exception
     */
    @Test
    public void testInitialize() throws Exception {
        System.out.println("initialize");
        Experiment_06 instance = new Experiment_06();
        instance.initialize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceOfTdLearninrgImplementation method, of class Experiment_06.
     */
    @Test
    public void testInstanceOfTdLearninrgImplementation_IPerceptronInterface() {
        System.out.println("instanceOfTdLearninrgImplementation");
        IPerceptronInterface perceptronInterface = null;
        Experiment_06 instance = new Experiment_06();
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.instanceOfTdLearninrgImplementation(perceptronInterface);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceOfTdLearninrgImplementation method, of class Experiment_06.
     */
    @Test
    public void testInstanceOfTdLearninrgImplementation_NTupleSystem() {
        System.out.println("instanceOfTdLearninrgImplementation");
        NTupleSystem nTupleSystem = null;
        Experiment_06 instance = new Experiment_06();
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.instanceOfTdLearninrgImplementation(nTupleSystem);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Experiment_06.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        Experiment_06.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
