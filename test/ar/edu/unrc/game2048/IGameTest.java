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
import org.junit.Test;

/**
 *
 * @author franco
 */
public class IGameTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public IGameTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of dispose method, of class IGame.
     */
    @Test
    public void testDispose() {
        System.out.println("dispose");
        IGame instance = new IGameImpl();
        instance.dispose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxNumber method, of class IGame.
     */
    @Test
    public void testGetMaxNumber() {
        System.out.println("getMaxNumber");
        IGame instance = new IGameImpl();
        int expResult = 0;
        int result = instance.getMaxNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getScore method, of class IGame.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        IGame instance = new IGameImpl();
        int expResult = 0;
        int result = instance.getScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iLoose method, of class IGame.
     */
    @Test
    public void testILoose() {
        System.out.println("iLoose");
        IGame instance = new IGameImpl();
        boolean expResult = false;
        boolean result = instance.iLoose();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iWin method, of class IGame.
     */
    @Test
    public void testIWin() {
        System.out.println("iWin");
        IGame instance = new IGameImpl();
        boolean expResult = false;
        boolean result = instance.iWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processInput method, of class IGame.
     */
    @Test
    public void testProcessInput() {
        System.out.println("processInput");
        int keyCode = 0;
        IGame instance = new IGameImpl();
        instance.processInput(keyCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IGameImpl implements IGame {

        @Override
        public void processInput(int keyCode) {
        }

        @Override
        public boolean iWin() {
            return false;
        }

        @Override
        public boolean iLoose() {
            return false;
        }

        @Override
        public int getScore() {
            return 0;
        }

        @Override
        public int getMaxNumber() {
            return 0;
        }

        @Override
        public void dispose() {
        }
    }

    public class IGameImpl implements IGame {

        public void processInput(int keyCode) {
        }

        public boolean iWin() {
            return false;
        }

        public boolean iLoose() {
            return false;
        }

        public int getScore() {
            return 0;
        }

        public int getMaxNumber() {
            return 0;
        }

        public void dispose() {
        }
    }

}
