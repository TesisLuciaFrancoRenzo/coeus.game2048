/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author franco
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {ar.edu.unrc.game2048.performanceandtraining.configurations.learning.LearningSuite.class, ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.PerceptronsSuite.class, ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.LibrariesSuite.class, ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048Test.class, ar.edu.unrc.game2048.performanceandtraining.configurations.StatisticForCalcTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.VisualExperimentTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperimentTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.StatisticExperimentTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.ThreadResultTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.NtuplesSuite.class} )
public class ConfigurationsSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
