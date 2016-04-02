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

import java.util.List;
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
public class StatisticForCalcTest {

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
    public StatisticForCalcTest() {
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
     * Test of getMaxScore method, of class StatisticForCalc.
     */
    @Test
    public void testGetMaxScore() {
        System.out.println("getMaxScore");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMaxScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxTurn method, of class StatisticForCalc.
     */
    @Test
    public void testGetMaxTurn() {
        System.out.println("getMaxTurn");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMaxTurn();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMeanScore method, of class StatisticForCalc.
     */
    @Test
    public void testGetMeanScore() {
        System.out.println("getMeanScore");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMeanScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMeanTurn method, of class StatisticForCalc.
     */
    @Test
    public void testGetMeanTurn() {
        System.out.println("getMeanTurn");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMeanTurn();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinScore method, of class StatisticForCalc.
     */
    @Test
    public void testGetMinScore() {
        System.out.println("getMinScore");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMinScore();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinTurn method, of class StatisticForCalc.
     */
    @Test
    public void testGetMinTurn() {
        System.out.println("getMinTurn");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getMinTurn();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileStatistics method, of class StatisticForCalc.
     */
    @Test
    public void testGetTileStatistics() {
        System.out.println("getTileStatistics");
        StatisticForCalc instance = new StatisticForCalc();
        List<Double> expResult = null;
        List<Double> result = instance.getTileStatistics();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWinRate method, of class StatisticForCalc.
     */
    @Test
    public void testGetWinRate() {
        System.out.println("getWinRate");
        StatisticForCalc instance = new StatisticForCalc();
        double expResult = 0.0;
        double result = instance.getWinRate();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxScore method, of class StatisticForCalc.
     */
    @Test
    public void testSetMaxScore() {
        System.out.println("setMaxScore");
        double maxScore = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMaxScore(maxScore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxTurn method, of class StatisticForCalc.
     */
    @Test
    public void testSetMaxTurn() {
        System.out.println("setMaxTurn");
        double maxTurn = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMaxTurn(maxTurn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMeanScore method, of class StatisticForCalc.
     */
    @Test
    public void testSetMeanScore() {
        System.out.println("setMeanScore");
        double meanScore = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMeanScore(meanScore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMeanTurn method, of class StatisticForCalc.
     */
    @Test
    public void testSetMeanTurn() {
        System.out.println("setMeanTurn");
        double meanTurn = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMeanTurn(meanTurn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinScore method, of class StatisticForCalc.
     */
    @Test
    public void testSetMinScore() {
        System.out.println("setMinScore");
        double minScore = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMinScore(minScore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinTurn method, of class StatisticForCalc.
     */
    @Test
    public void testSetMinTurn() {
        System.out.println("setMinTurn");
        double minTurn = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setMinTurn(minTurn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileStatistics method, of class StatisticForCalc.
     */
    @Test
    public void testSetTileStatistics() {
        System.out.println("setTileStatistics");
        List<Double> tileStatistics = null;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setTileStatistics(tileStatistics);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWinRate method, of class StatisticForCalc.
     */
    @Test
    public void testSetWinRate() {
        System.out.println("setWinRate");
        double winRate = 0.0;
        StatisticForCalc instance = new StatisticForCalc();
        instance.setWinRate(winRate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
