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
package ar.edu.unrc.game2048;

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
public class IGameTest {

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
    public IGameTest() {
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

    /**
     *
     */
    public class IGameImpl implements IGame {

        @Override
        public void dispose() {
        }

        @Override
        public int getMaxNumber() {
            return 0;
        }

        @Override
        public int getScore() {
            return 0;
        }

        @Override
        public boolean iLoose() {
            return false;
        }

        @Override
        public boolean iWin() {
            return false;
        }

        @Override
        public void processInput(int keyCode) {
        }
    }

    /**
     *
     */
    public class IGameImpl implements IGame {

        /**
         *
         */
        @Override
        public void dispose() {
        }

        /**
         *
         * @return
         */
        @Override
        public int getMaxNumber() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public int getScore() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iLoose() {
            return false;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iWin() {
            return false;
        }

        /**
         *
         * @param keyCode
         */
        @Override
        public void processInput(int keyCode) {
        }
    }

    /**
     *
     */
    public class IGameImpl implements IGame {

        /**
         *
         */
        @Override
        public void dispose() {
        }

        /**
         *
         * @return
         */
        @Override
        public int getMaxNumber() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public int getScore() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iLoose() {
            return false;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iWin() {
            return false;
        }

        /**
         *
         * @param keyCode
         */
        @Override
        public void processInput(int keyCode) {
        }
    }

    /**
     *
     */
    public class IGameImpl implements IGame {

        /**
         *
         */
        @Override
        public void dispose() {
        }

        /**
         *
         * @return
         */
        @Override
        public int getMaxNumber() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public int getScore() {
            return 0;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iLoose() {
            return false;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean iWin() {
            return false;
        }

        /**
         *
         * @param keyCode
         */
        @Override
        public void processInput(int keyCode) {
        }
    }

}
