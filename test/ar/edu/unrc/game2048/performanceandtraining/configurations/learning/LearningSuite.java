/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning;

import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog.EncogSuite;
import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.greedy.GreedySuite;
import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple.NtupleSuite;
import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.random.RandomSuite;
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
@Suite.SuiteClasses( {NtupleSuite.class, RandomSuite.class, GreedySuite.class, EncogSuite.class})
public class LearningSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

}
