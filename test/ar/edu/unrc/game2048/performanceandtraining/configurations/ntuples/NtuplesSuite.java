/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples;

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
@Suite.SuiteClasses( {ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.BasicScoreLinearTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.BasicMaxTileTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.BasicTanHTest.class, ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.BasicSigmoidTest.class} )
public class NtuplesSuite {

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
