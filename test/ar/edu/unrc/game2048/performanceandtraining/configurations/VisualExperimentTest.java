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

import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
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
public class VisualExperimentTest {

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
    public VisualExperimentTest() {
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
     * Test of getDelayPerMove method, of class VisualExperiment.
     */
    @Test
    public void testGetDelayPerMove() {
        System.out.println("getDelayPerMove");
        VisualExperiment instance = null;
        int expResult = 0;
        int result = instance.getDelayPerMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExperimentName method, of class VisualExperiment.
     */
    @Test
    public void testGetExperimentName() {
        System.out.println("getExperimentName");
        VisualExperiment instance = null;
        String expResult = "";
        String result = instance.getExperimentName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronConfiguration method, of class VisualExperiment.
     */
    @Test
    public void testGetPerceptronConfiguration() {
        System.out.println("getPerceptronConfiguration");
        VisualExperiment instance = null;
        PerceptronConfiguration2048 expResult = null;
        PerceptronConfiguration2048 result = instance.getPerceptronConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronFileName method, of class VisualExperiment.
     */
    @Test
    public void testGetPerceptronFileName() {
        System.out.println("getPerceptronFileName");
        VisualExperiment instance = null;
        String expResult = "";
        String result = instance.getPerceptronFileName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronName method, of class VisualExperiment.
     */
    @Test
    public void testGetPerceptronName() {
        System.out.println("getPerceptronName");
        VisualExperiment instance = null;
        String expResult = "";
        String result = instance.getPerceptronName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileToWin method, of class VisualExperiment.
     */
    @Test
    public void testGetTileToWin() {
        System.out.println("getTileToWin");
        VisualExperiment instance = null;
        int expResult = 0;
        int result = instance.getTileToWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getnTupleConfiuguration method, of class VisualExperiment.
     */
    @Test
    public void testGetnTupleConfiuguration() {
        System.out.println("getnTupleConfiuguration");
        VisualExperiment instance = null;
        NTupleConfiguration2048 expResult = null;
        NTupleConfiguration2048 result = instance.getnTupleConfiuguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initializeVisual method, of class VisualExperiment.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInitializeVisual() throws Exception {
        System.out.println("initializeVisual");
        VisualExperiment instance = null;
        instance.initializeVisual();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class VisualExperiment.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        String experimentPath = "";
        int delayPerMove = 0;
        VisualExperiment instance = null;
        instance.run(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDelayPerMove method, of class VisualExperiment.
     */
    @Test
    public void testSetDelayPerMove() {
        System.out.println("setDelayPerMove");
        int delayPerMove = 0;
        VisualExperiment instance = null;
        instance.setDelayPerMove(delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExperimentName method, of class VisualExperiment.
     */
    @Test
    public void testSetExperimentName() {
        System.out.println("setExperimentName");
        String experimentName = "";
        VisualExperiment instance = null;
        instance.setExperimentName(experimentName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPerceptronConfiguration method, of class VisualExperiment.
     */
    @Test
    public void testSetPerceptronConfiguration() {
        System.out.println("setPerceptronConfiguration");
        VisualExperiment instance = null;
        instance.setPerceptronConfiguration(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPerceptronName method, of class VisualExperiment.
     */
    @Test
    public void testSetPerceptronName() {
        System.out.println("setPerceptronName");
        String perceptronName = "";
        VisualExperiment instance = null;
        instance.setPerceptronName(perceptronName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTileToWin method, of class VisualExperiment.
     */
    @Test
    public void testSetTileToWin() {
        System.out.println("setTileToWin");
        int tileToWin = 0;
        VisualExperiment instance = null;
        instance.setTileToWin(tileToWin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setnTupleConfiuguration method, of class VisualExperiment.
     */
    @Test
    public void testSetnTupleConfiuguration() {
        System.out.println("setnTupleConfiuguration");
        NTupleConfiguration2048 nTupleConfiuguration = null;
        VisualExperiment instance = null;
        instance.setnTupleConfiuguration(nTupleConfiuguration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class VisualExperiment.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        String experimentPath = "";
        int delayPerMove = 0;
        VisualExperiment instance = null;
        instance.start(experimentPath, delayPerMove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class VisualExperiment.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        VisualExperiment instance = null;
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     *
     */
    public class VisualExperimentImpl extends VisualExperiment {

        /**
         *
         */
        public VisualExperimentImpl() {
            super(null);
        }

        @Override
        public void initializeVisual() throws Exception {
        }
    }

    /**
     *
     */
    public class VisualExperimentImpl extends VisualExperiment {

        /**
         *
         */
        public VisualExperimentImpl() {
            super(null);
        }

        /**
         *
         * @throws Exception
         */
        @Override
        public void initializeVisual() throws Exception {
        }
    }

    /**
     *
     */
    public class VisualExperimentImpl extends VisualExperiment {

        /**
         *
         */
        public VisualExperimentImpl() {
            super(null);
        }

        /**
         *
         * @throws Exception
         */
        @Override
        public void initializeVisual() throws Exception {
        }
    }

}
