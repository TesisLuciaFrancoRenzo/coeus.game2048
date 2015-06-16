/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

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
@Suite.SuiteClasses( {BoardMaxTileTest.class, NTupleScoreTest.class, BinaryScoreTest.class, FullNTupleScoreTest.class, SymetricSample01MaxTileTest.class, BoardScoreCustomNormalizationTest.class, SymetricSample01ScoreTest.class, FullNTupleMaxTileTest.class, NTupleMaxTileTest.class, NTupleScoreLinealTest.class, SymetricSample02BoardMaxTileTest.class, BoardMaxTileCustomNormalizationTest.class, BoardScoreTest.class})
public class PerceptronsSuite {

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
