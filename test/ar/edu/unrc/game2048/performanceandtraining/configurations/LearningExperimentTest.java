/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.tdlearning.perceptron.learning.ELearningRateAdaptation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class LearningExperimentTest {

    public LearningExperimentTest() {
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
     * Test of createLogs method, of class LearningExperiment.
     */
    @Test
    public void testCreateLogs() {
        System.out.println("createLogs");
        boolean logsActivated = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.createLogs(logsActivated);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPathToDir method, of class LearningExperiment.
     */
    @Test
    public void testCreatePathToDir() {
        System.out.println("createPathToDir");
        String experimentPath = "";
        LearningExperiment instance = new LearningExperimentImpl();
        String expResult = "";
        String result = instance.createPathToDir(experimentPath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAlpha method, of class LearningExperiment.
     */
    @Test
    public void testGetAlpha() {
        System.out.println("getAlpha");
        LearningExperiment instance = new LearningExperimentImpl();
        double[] expResult = null;
        double[] result = instance.getAlpha();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAlpha method, of class LearningExperiment.
     */
    @Test
    public void testSetAlpha() {
        System.out.println("setAlpha");
        double[] alpha = null;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setAlpha(alpha);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExperimentName method, of class LearningExperiment.
     */
    @Test
    public void testGetExperimentName() {
        System.out.println("getExperimentName");
        LearningExperiment instance = new LearningExperimentImpl();
        String expResult = "";
        String result = instance.getExperimentName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExperimentName method, of class LearningExperiment.
     */
    @Test
    public void testSetExperimentName() {
        System.out.println("setExperimentName");
        String experimentName = "";
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setExperimentName(experimentName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExplorationRateToFixed method, of class LearningExperiment.
     */
    @Test
    public void testSetExplorationRateToFixed() {
        System.out.println("setExplorationRateToFixed");
        double value = 0.0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setExplorationRateToFixed(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGamesToPlayPerThreadForStatistics method, of class LearningExperiment.
     */
    @Test
    public void testGetGamesToPlayPerThreadForStatistics() {
        System.out.println("getGamesToPlayPerThreadForStatistics");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getGamesToPlayPerThreadForStatistics();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGamesToPlayPerThreadForStatistics method, of class LearningExperiment.
     */
    @Test
    public void testSetGamesToPlayPerThreadForStatistics() {
        System.out.println("setGamesToPlayPerThreadForStatistics");
        int gamesToPlayPerThreadForStatistics = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGamma method, of class LearningExperiment.
     */
    @Test
    public void testGetGamma() {
        System.out.println("getGamma");
        LearningExperiment instance = new LearningExperimentImpl();
        double expResult = 0.0;
        double result = instance.getGamma();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGamma method, of class LearningExperiment.
     */
    @Test
    public void testSetGamma() {
        System.out.println("setGamma");
        double gamma = 0.0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setGamma(gamma);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInitializePerceptronRandomized method, of class LearningExperiment.
     */
    @Test
    public void testSetInitializePerceptronRandomized() {
        System.out.println("setInitializePerceptronRandomized");
        boolean randomized = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setInitializePerceptronRandomized(randomized);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLambda method, of class LearningExperiment.
     */
    @Test
    public void testGetLambda() {
        System.out.println("getLambda");
        LearningExperiment instance = new LearningExperimentImpl();
        double expResult = 0.0;
        double result = instance.getLambda();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLambda method, of class LearningExperiment.
     */
    @Test
    public void testSetLambda() {
        System.out.println("setLambda");
        double lambda = 0.0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLambda(lambda);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastGamePlayedNumber method, of class LearningExperiment.
     */
    @Test
    public void testSetLastGamePlayedNumber() {
        System.out.println("setLastGamePlayedNumber");
        int gameNumber = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLastGamePlayedNumber(gameNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLearningRateAdaptation method, of class LearningExperiment.
     */
    @Test
    public void testGetLearningRateAdaptation() {
        System.out.println("getLearningRateAdaptation");
        LearningExperiment instance = new LearningExperimentImpl();
        ELearningRateAdaptation expResult = null;
        ELearningRateAdaptation result = instance.getLearningRateAdaptation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningRateAdaptation method, of class LearningExperiment.
     */
    @Test
    public void testSetLearningRateAdaptation() {
        System.out.println("setLearningRateAdaptation");
        ELearningRateAdaptation learningRateAdaptation = null;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLearningRateAdaptation(learningRateAdaptation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningRateAdaptationToAnnealing method, of class LearningExperiment.
     */
    @Test
    public void testSetLearningRateAdaptationToAnnealing() {
        System.out.println("setLearningRateAdaptationToAnnealing");
        int annealingT = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLearningRateAdaptationToAnnealing(annealingT);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronName method, of class LearningExperiment.
     */
    @Test
    public void testGetPerceptronName() {
        System.out.println("getPerceptronName");
        LearningExperiment instance = new LearningExperimentImpl();
        String expResult = "";
        String result = instance.getPerceptronName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPerceptronName method, of class LearningExperiment.
     */
    @Test
    public void testSetPerceptronName() {
        System.out.println("setPerceptronName");
        String perceptronName = "";
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setPerceptronName(perceptronName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class LearningExperiment.
     */
    @Test
    public void testInitialize() throws Exception {
        System.out.println("initialize");
        LearningExperiment instance = new LearningExperimentImpl();
        instance.initialize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceOfTdLearninrgImplementation method, of class LearningExperiment.
     */
    @Test
    public void testInstanceOfTdLearninrgImplementation_IPerceptronInterface() {
        System.out.println("instanceOfTdLearninrgImplementation");
        IPerceptronInterface perceptronInterface = null;
        LearningExperiment instance = new LearningExperimentImpl();
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.instanceOfTdLearninrgImplementation(perceptronInterface);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of instanceOfTdLearninrgImplementation method, of class LearningExperiment.
     */
    @Test
    public void testInstanceOfTdLearninrgImplementation_NTupleSystem() {
        System.out.println("instanceOfTdLearninrgImplementation");
        NTupleSystem nTupleSystem = null;
        LearningExperiment instance = new LearningExperimentImpl();
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.instanceOfTdLearninrgImplementation(nTupleSystem);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isResetEligibilitiTraces method, of class LearningExperiment.
     */
    @Test
    public void testIsResetEligibilitiTraces() {
        System.out.println("isResetEligibilitiTraces");
        LearningExperiment instance = new LearningExperimentImpl();
        boolean expResult = false;
        boolean result = instance.isResetEligibilitiTraces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResetEligibilitiTraces method, of class LearningExperiment.
     */
    @Test
    public void testSetResetEligibilitiTraces() {
        System.out.println("setResetEligibilitiTraces");
        boolean resetEligibilitiTraces = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setResetEligibilitiTraces(resetEligibilitiTraces);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRunStatisticForRandom method, of class LearningExperiment.
     */
    @Test
    public void testIsRunStatisticForRandom() {
        System.out.println("isRunStatisticForRandom");
        LearningExperiment instance = new LearningExperimentImpl();
        boolean expResult = false;
        boolean result = instance.isRunStatisticForRandom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRunStatisticForRandom method, of class LearningExperiment.
     */
    @Test
    public void testSetRunStatisticForRandom() {
        System.out.println("setRunStatisticForRandom");
        boolean runStatisticForRandom = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setRunStatisticForRandom(runStatisticForRandom);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRunStatisticsForBackups method, of class LearningExperiment.
     */
    @Test
    public void testIsRunStatisticsForBackups() {
        System.out.println("isRunStatisticsForBackups");
        LearningExperiment instance = new LearningExperimentImpl();
        boolean expResult = false;
        boolean result = instance.isRunStatisticsForBackups();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRunStatisticsForBackups method, of class LearningExperiment.
     */
    @Test
    public void testSetRunStatisticsForBackups() {
        System.out.println("setRunStatisticsForBackups");
        boolean runStatisticsForBackups = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setRunStatisticsForBackups(runStatisticsForBackups);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStatisticsOnly method, of class LearningExperiment.
     */
    @Test
    public void testIsStatisticsOnly() {
        System.out.println("isStatisticsOnly");
        LearningExperiment instance = new LearningExperimentImpl();
        boolean expResult = false;
        boolean result = instance.isStatisticsOnly();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStatisticsOnly method, of class LearningExperiment.
     */
    @Test
    public void testSetStatisticsOnly() {
        System.out.println("setStatisticsOnly");
        boolean statisticsOnly = false;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setStatisticsOnly(statisticsOnly);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExplorationRate method, of class LearningExperiment.
     */
    @Test
    public void testSetExplorationRate() {
        System.out.println("setExplorationRate");
        double initialValue = 0.0;
        int startDecrementing = 0;
        double finalValue = 0.0;
        int finishDecrementing = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setExplorationRate(initialValue, startDecrementing, finalValue, finishDecrementing);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningRateAdaptationToFixed method, of class LearningExperiment.
     */
    @Test
    public void testSetLearningRateAdaptationToFixed() {
        System.out.println("setLearningRateAdaptationToFixed");
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLearningRateAdaptationToFixed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class LearningExperiment.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        String experimentPath = "";
        int delayPerMove = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.start(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of training method, of class LearningExperiment.
     */
    @Test
    public void testTraining() throws Exception {
        System.out.println("training");
        LearningExperiment instance = new LearningExperimentImpl();
        instance.training(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnnealingT method, of class LearningExperiment.
     */
    @Test
    public void testGetAnnealingT() {
        System.out.println("getAnnealingT");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getAnnealingT();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnnealingT method, of class LearningExperiment.
     */
    @Test
    public void testSetAnnealingT() {
        System.out.println("setAnnealingT");
        int annealingT = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setAnnealingT(annealingT);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGamesToPlay method, of class LearningExperiment.
     */
    @Test
    public void testGetGamesToPlay() {
        System.out.println("getGamesToPlay");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getGamesToPlay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGamesToPlay method, of class LearningExperiment.
     */
    @Test
    public void testSetGamesToPlay() {
        System.out.println("setGamesToPlay");
        int gamesToPlay = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setGamesToPlay(gamesToPlay);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLearningAlgorithm method, of class LearningExperiment.
     */
    @Test
    public void testGetLearningAlgorithm() {
        System.out.println("getLearningAlgorithm");
        LearningExperiment instance = new LearningExperimentImpl();
        TDLambdaLearning expResult = null;
        TDLambdaLearning result = instance.getLearningAlgorithm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLearningAlgorithm method, of class LearningExperiment.
     */
    @Test
    public void testSetLearningAlgorithm() {
        System.out.println("setLearningAlgorithm");
        TDLambdaLearning learningAlgorithm = null;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setLearningAlgorithm(learningAlgorithm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeuralNetworkInterfaceFor2048 method, of class LearningExperiment.
     */
    @Test
    public void testGetNeuralNetworkInterfaceFor2048() {
        System.out.println("getNeuralNetworkInterfaceFor2048");
        LearningExperiment instance = new LearningExperimentImpl();
        INeuralNetworkInterfaceFor2048 expResult = null;
        INeuralNetworkInterfaceFor2048 result = instance.getNeuralNetworkInterfaceFor2048();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeuralNetworkInterfaceFor2048 method, of class LearningExperiment.
     */
    @Test
    public void testSetNeuralNetworkInterfaceFor2048() {
        System.out.println("setNeuralNetworkInterfaceFor2048");
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setNeuralNetworkInterfaceFor2048(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSaveEvery method, of class LearningExperiment.
     */
    @Test
    public void testGetSaveEvery() {
        System.out.println("getSaveEvery");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getSaveEvery();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSaveEvery method, of class LearningExperiment.
     */
    @Test
    public void testSetSaveEvery() {
        System.out.println("setSaveEvery");
        int saveEvery = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setSaveEvery(saveEvery);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSimulationsForStatistics method, of class LearningExperiment.
     */
    @Test
    public void testGetSimulationsForStatistics() {
        System.out.println("getSimulationsForStatistics");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getSimulationsForStatistics();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSimulationsForStatistics method, of class LearningExperiment.
     */
    @Test
    public void testSetSimulationsForStatistics() {
        System.out.println("setSimulationsForStatistics");
        int simulationsForStatistics = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setSimulationsForStatistics(simulationsForStatistics);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatisticExperiment method, of class LearningExperiment.
     */
    @Test
    public void testGetStatisticExperiment() {
        System.out.println("getStatisticExperiment");
        LearningExperiment instance = new LearningExperimentImpl();
        StatisticExperiment expResult = null;
        StatisticExperiment result = instance.getStatisticExperiment();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileToWin method, of class LearningExperiment.
     */
    @Test
    public void testGetTileToWin() {
        System.out.println("getTileToWin");
        LearningExperiment instance = new LearningExperimentImpl();
        int expResult = 0;
        int result = instance.getTileToWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileToWin method, of class LearningExperiment.
     */
    @Test
    public void testSetTileToWin() {
        System.out.println("setTileToWin");
        int tileToWin = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.setTileToWin(tileToWin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of runExperiment method, of class LearningExperiment.
     */
    @Test
    public void testRunExperiment() throws Exception {
        System.out.println("runExperiment");
        String experimentPath = "";
        int delayPerMove = 0;
        LearningExperiment instance = new LearningExperimentImpl();
        instance.runExperiment(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class LearningExperimentImpl extends LearningExperiment {

        public void initialize() throws Exception {
        }

        public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
            return null;
        }

        public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
            return null;
        }
    }

}
