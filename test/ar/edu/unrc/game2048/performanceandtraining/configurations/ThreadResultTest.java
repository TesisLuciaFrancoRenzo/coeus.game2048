/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
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

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    public ThreadResultTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of addLastTurn method, of class ThreadResult.
     */
    @Test
    public void testAddLastTurn() {
        System.out.println("addLastTurn");
        int lastTurn = 0;
        ThreadResult instance = new ThreadResult();
        instance.addLastTurn(lastTurn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * Test of getMaxTurn method, of class ThreadResult.
     */
    @Test
    public void testGetMaxTurn() {
        System.out.println("getMaxTurn");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMaxTurn();
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
     * Test of getMeanTurn method, of class ThreadResult.
     */
    @Test
    public void testGetMeanTurn() {
        System.out.println("getMeanTurn");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMeanTurn();
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
     * Test of getMinTurn method, of class ThreadResult.
     */
    @Test
    public void testGetMinTurn() {
        System.out.println("getMinTurn");
        ThreadResult instance = new ThreadResult();
        double expResult = 0.0;
        double result = instance.getMinTurn();
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

}
