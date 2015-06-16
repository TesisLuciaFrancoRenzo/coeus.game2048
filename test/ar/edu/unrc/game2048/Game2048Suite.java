/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

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
@Suite.SuiteClasses( {ar.edu.unrc.game2048.IConfiguration2048Test.class, ar.edu.unrc.game2048.TileContainerTest.class, ar.edu.unrc.game2048.PerceptronConfiguration2048Test.class, ar.edu.unrc.game2048.NTupleConfiguration2048Test.class, ar.edu.unrc.game2048.IGameTest.class, ar.edu.unrc.game2048.Game2048Test.class, ar.edu.unrc.game2048.TileTest.class, ar.edu.unrc.game2048.resources.ResourcesSuite.class, ar.edu.unrc.game2048.performanceandtraining.PerformanceandtrainingSuite.class, ar.edu.unrc.game2048.GameBoardTest.class, ar.edu.unrc.game2048.ActionTest.class} )
public class Game2048Suite {

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
