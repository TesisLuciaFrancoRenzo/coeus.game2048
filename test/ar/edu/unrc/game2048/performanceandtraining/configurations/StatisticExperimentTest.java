/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class StatisticExperimentTest {

    public StatisticExperimentTest() {
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
     * Test of setGamesToPlayPerThread method, of class StatisticExperiment.
     */
    @Test
    public void testSetGamesToPlayPerThread() {
        System.out.println("setGamesToPlayPerThread");
        int gamesToPlay = 0;
        StatisticExperiment instance = null;
        instance.setGamesToPlayPerThread(gamesToPlay);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileStatistics method, of class StatisticExperiment.
     */
    @Test
    public void testGetTileStatistics() {
        System.out.println("getTileStatistics");
        StatisticExperiment instance = null;
        StatisticForCalc expResult = null;
        StatisticForCalc result = instance.getTileStatistics();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class StatisticExperiment.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        String experimentPath = "";
        int delayPerMove = 0;
        StatisticExperiment instance = null;
        instance.start(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateForFileName method, of class StatisticExperiment.
     */
    @Test
    public void testGetDateForFileName() {
        System.out.println("getDateForFileName");
        StatisticExperiment instance = null;
        Date expResult = null;
        Date result = instance.getDateForFileName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDateForFileName method, of class StatisticExperiment.
     */
    @Test
    public void testSetDateForFileName() {
        System.out.println("setDateForFileName");
        Date dateForFileName = null;
        StatisticExperiment instance = null;
        instance.setDateForFileName(dateForFileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExperimentName method, of class StatisticExperiment.
     */
    @Test
    public void testGetExperimentName() {
        System.out.println("getExperimentName");
        StatisticExperiment instance = null;
        String expResult = "";
        String result = instance.getExperimentName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExperimentName method, of class StatisticExperiment.
     */
    @Test
    public void testSetExperimentName() {
        System.out.println("setExperimentName");
        String experimentName = "";
        StatisticExperiment instance = null;
        instance.setExperimentName(experimentName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGamesToPlay method, of class StatisticExperiment.
     */
    @Test
    public void testGetGamesToPlay() {
        System.out.println("getGamesToPlay");
        StatisticExperiment instance = null;
        int expResult = 0;
        int result = instance.getGamesToPlay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLearningMethod method, of class StatisticExperiment.
     */
    @Test
    public void testGetLearningMethod() {
        System.out.println("getLearningMethod");
        StatisticExperiment instance = null;
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.getLearningMethod();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningMethod method, of class StatisticExperiment.
     */
    @Test
    public void testSetLearningMethod() {
        System.out.println("setLearningMethod");
        TDLambdaLearning learningMethod = null;
        StatisticExperiment instance = null;
        instance.setLearningMethod(learningMethod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronName method, of class StatisticExperiment.
     */
    @Test
    public void testGetPerceptronName() {
        System.out.println("getPerceptronName");
        StatisticExperiment instance = null;
        String expResult = "";
        String result = instance.getPerceptronName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPerceptronName method, of class StatisticExperiment.
     */
    @Test
    public void testSetPerceptronName() {
        System.out.println("setPerceptronName");
        String perceptronName = "";
        StatisticExperiment instance = null;
        instance.setPerceptronName(perceptronName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSimulations method, of class StatisticExperiment.
     */
    @Test
    public void testGetSimulations() {
        System.out.println("getSimulations");
        StatisticExperiment instance = null;
        int expResult = 0;
        int result = instance.getSimulations();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSimulations method, of class StatisticExperiment.
     */
    @Test
    public void testSetSimulations() {
        System.out.println("setSimulations");
        int threads = 0;
        StatisticExperiment instance = null;
        instance.setSimulations(threads);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileToWin method, of class StatisticExperiment.
     */
    @Test
    public void testGetTileToWin() {
        System.out.println("getTileToWin");
        StatisticExperiment instance = null;
        int expResult = 0;
        int result = instance.getTileToWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileToWin method, of class StatisticExperiment.
     */
    @Test
    public void testSetTileToWin() {
        System.out.println("setTileToWin");
        int tileToWin = 0;
        StatisticExperiment instance = null;
        instance.setTileToWin(tileToWin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initializeStatistics method, of class StatisticExperiment.
     */
    @Test
    public void testInitializeStatistics() throws Exception {
        System.out.println("initializeStatistics");
        StatisticExperiment instance = null;
        instance.initializeStatistics();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class StatisticExperiment.
     */
    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        String experimentPath = "";
        int delayPerMove = 0;
        StatisticExperiment instance = null;
        instance.run(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class StatisticExperimentImpl extends StatisticExperiment {

        public StatisticExperimentImpl() {
            super(null);
        }

        public void initializeStatistics() throws Exception {
        }
    }

}
