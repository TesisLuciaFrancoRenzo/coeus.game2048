/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
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
public class TestGenerator_02Test {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public TestGenerator_02Test() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of configAndExcecute method, of class TestGenerator_02.
     */
    @Test
    public void testConfigAndExcecute() {
        System.out.println("configAndExcecute");
        LearningExperiment experiment = null;
        boolean statisticsOnly = false;
        boolean runStatisticForRandom = false;
        boolean runStatisticsForBackups = false;
        boolean createLogs = false;
        double lambda = 0.0;
        double alpha = 0.0;
        int gamesToPlay = 0;
        int saveEvery = 0;
        int gamesToPlayPerThreadForStatistics = 0;
        int simulationsForStatistics = 0;
        String filePath = "";
        TestGenerator_02.configAndExcecute(experiment, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, lambda, alpha, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class TestGenerator_02.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        TestGenerator_02.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of runAllConfigs method, of class TestGenerator_02.
     */
    @Test
    public void testRunAllConfigs() {
        System.out.println("runAllConfigs");
        String experimentName = "";
        LearningExperiment experiment = null;
        List<Double> alphaList = null;
        List<Double> lambdaList = null;
        boolean statisticsOnly = false;
        boolean runStatisticForRandom = false;
        boolean runStatisticsForBackups = false;
        boolean createLogs = false;
        int gamesToPlay = 0;
        int saveEvery = 0;
        int gamesToPlayPerThreadForStatistics = 0;
        int simulationsForStatistics = 0;
        String filePath = "";
        TestGenerator_02.runAllConfigs(experimentName, experiment, alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
