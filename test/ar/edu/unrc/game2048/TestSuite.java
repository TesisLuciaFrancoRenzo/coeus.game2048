package ar.edu.unrc.game2048;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterfaceTest;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.BoardMaxTileTest;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.BoardScoreCustomNormalizationTest;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.FullNTupleMaxTileTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {GameBoardTest.class, EncogExperimentInterfaceTest.class, Game2048Test.class, BoardScoreCustomNormalizationTest.class, BoardMaxTileTest.class, FullNTupleMaxTileTest.class} )
public class TestSuite {

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
