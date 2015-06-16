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
import org.junit.Test;

/**
 *
 * @author franco
 */
public class ThreadResultTest {

    public ThreadResultTest() {
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
     * Test of addProcesedGames method, of class ThreadResult.
     */
    @Test
    public void testAddProcesedGames() {
        System.out.println("addProcesedGames");
        ThreadResult instance = new ThreadResult();
        instance.addProcesedGames();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addScore method, of class ThreadResult.
     */
    @Test
    public void testAddScore() {
        System.out.println("addScore");
        double score = 0.0;
        ThreadResult instance = new ThreadResult();
        instance.addScore(score);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addStatisticForTile method, of class ThreadResult.
     */
    @Test
    public void testAddStatisticForTile() {
        System.out.println("addStatisticForTile");
        int tileCode = 0;
        ThreadResult instance = new ThreadResult();
        instance.addStatisticForTile(tileCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addWin method, of class ThreadResult.
     */
    @Test
    public void testAddWin() {
        System.out.println("addWin");
        ThreadResult instance = new ThreadResult();
        instance.addWin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxScore method, of class ThreadResult.
     */
    @Test
    public void testGetMaxScore() {
        System.out.println("getMaxScore");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMaxScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMeanScore method, of class ThreadResult.
     */
    @Test
    public void testGetMeanScore() {
        System.out.println("getMeanScore");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMeanScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinScore method, of class ThreadResult.
     */
    @Test
    public void testGetMinScore() {
        System.out.println("getMinScore");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMinScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProcesedGames method, of class ThreadResult.
     */
    @Test
    public void testGetProcesedGames() {
        System.out.println("getProcesedGames");
        ThreadResult instance = new ThreadResult();
        int expResult = 0;
        int result = instance.getProcesedGames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProcesedGames method, of class ThreadResult.
     */
    @Test
    public void testSetProcesedGames() {
        System.out.println("setProcesedGames");
        int value = 0;
        ThreadResult instance = new ThreadResult();
        instance.setProcesedGames(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatisticForTile method, of class ThreadResult.
     */
    @Test
    public void testGetStatisticForTile() {
        System.out.println("getStatisticForTile");
        int tileCode = 0;
        ThreadResult instance = new ThreadResult();
        Integer expResult = null;
        Integer result = instance.getStatisticForTile(tileCode);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWinRate method, of class ThreadResult.
     */
    @Test
    public void testGetWinRate() {
        System.out.println("getWinRate");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getWinRate();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
