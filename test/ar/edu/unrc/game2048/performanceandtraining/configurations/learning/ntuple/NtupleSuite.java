/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple;

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
@Suite.SuiteClasses( {ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple.Experiment_02Test.class, ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple.Experiment_03Test.class, ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple.Experiment_04Test.class, ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple.Experiment_01Test.class} )
public class NtupleSuite {

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
