/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class TileTest {

    public TileTest() {
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
     * Test of equals method, of class Tile.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Tile instance = new Tile();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBackground method, of class Tile.
     */
    @Test
    public void testGetBackground() {
        System.out.println("getBackground");
        Tile instance = new Tile();
        Color expResult = null;
        Color result = instance.getBackground();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCode method, of class Tile.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        Tile instance = new Tile();
        int expResult = 0;
        int result = instance.getCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getForeground method, of class Tile.
     */
    @Test
    public void testGetForeground() {
        System.out.println("getForeground");
        Tile instance = new Tile();
        Color expResult = null;
        Color result = instance.getForeground();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameValue method, of class Tile.
     */
    @Test
    public void testGetGameValue() {
        System.out.println("getGameValue");
        Tile instance = new Tile();
        int expResult = 0;
        int result = instance.getGameValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Tile.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Tile instance = new Tile();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class Tile.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        Tile instance = new Tile();
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
