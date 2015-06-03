/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.NTupleMaxTile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class GameBoardTest {

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
    private final Tile[] emptyBoard;

    private Game2048 game;
    private final NTupleMaxTile nTupleConfiguration;

    private final Tile[] randomBoard;
    private final TileContainer tileContainer;

    /**
     *
     */
    public GameBoardTest() {
        tileContainer = new TileContainer(17);
        Tile[] emptyB = {
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0)
        };
        this.emptyBoard = emptyB;

        Tile[] randomB = {
            tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.getTile(17),
            tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(2), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(7),
            tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.getTile(1), tileContainer.getTile(4)
        };
        this.randomBoard = randomB;
        nTupleConfiguration = new NTupleMaxTile();
    }

    /**
     *
     */
    @Before
    public void setUp() {
        game = new Game2048(nTupleConfiguration, 2_048, false, 0, true);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of tileAt method, of class GameBoard.
     */
    @Test( expected = java.lang.ArrayIndexOutOfBoundsException.class )
    public void testFailTileAt() {
        System.out.println("tileAt Fail");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        int x = 3;
        int y = 4;
        Tile result = board.tileAt(x, y);

        board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        x = -1;
        y = 0;
        result = board.tileAt(x, y);
    }

//    /**
//     * Test of isFull method, of class GameBoard.
//     */
//    @Test
//    public void testIsFull() {
//        System.out.println("isFull");
//        GameBoard board = new GameBoard(nTupleConfiguration, tileContainer);
//        Tile[] emptyBoard = {
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0)
//        };
//        board.setTiles(emptyBoard);
//        board.
//        boolean expResult = false;
//        boolean result = board.isFull();
//        Assert.assertEquals(expResult, result);
//
//        Tile[] emptyBoard = {
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
//            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0)
//        };
//        board.setTiles(emptyBoard);
//        boolean expResult = false;
//        boolean result = board.isFull();
//        Assert.assertEquals(expResult, result);
//    }
    /**
     * Test of getCopy method, of class GameBoard.
     */
    @Test
    public void testGetCopy() {
        System.out.println("getCopy");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(emptyBoard);
        board.updateInternalState(false);

        GameBoard copy = board.getCopy(tileContainer);

        Assert.assertEquals(board, copy);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] fullBoard = {
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4)
        };
        board.setTiles(fullBoard);
        board.updateInternalState(true);

        copy = board.getCopy(tileContainer);

        Assert.assertEquals(board, copy);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] almostFull = {
            tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4)
        };
        board.setTiles(almostFull);
        board.updateInternalState(true);

        copy = board.getCopy(tileContainer);

        Assert.assertEquals(board, copy);

    }

    /**
     * Test of isEqual method, of class GameBoard.
     */
    @Test
    public void testIsEqual() {
        System.out.println("isEqual");

        GameBoard board1 = new GameBoard(game, tileContainer);
        board1.setTiles(emptyBoard);
        board1.updateInternalState(false);

        GameBoard board2 = new GameBoard(game, tileContainer);
        board2.setTiles(emptyBoard);
        board2.updateInternalState(false);

        boolean expResult = true;
        boolean result = board1.isEqual(board2);
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board1 = new GameBoard(game, tileContainer);
        board1.setTiles(randomBoard);
        board1.updateInternalState(true);

        board2 = new GameBoard(game, tileContainer);
        board2.setTiles(randomBoard);
        board2.updateInternalState(true);

        expResult = true;
        result = board1.isEqual(board2);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of isTerminalState method, of class GameBoard.
     */
    @Test
    public void testIsTerminalState() {
        System.out.println("isTerminalState");
        GameBoard board = new GameBoard(game, tileContainer);
        Tile[] terminalBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(5), tileContainer.getTile(2)
        };
        board.setTiles(terminalBoard);
        board.updateInternalState(true);

        boolean expResult = true;
        boolean result = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] fullNotTerminalBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(1), tileContainer.getTile(2)
        };
        board.setTiles(fullNotTerminalBoard);
        board.updateInternalState(true);

        expResult = false;
        result = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        expResult = false;
        result = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] winBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(0), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(1), tileContainer.getTile(2)
        };
        board.setTiles(winBoard);
        board.updateInternalState(true);
        board.setToWin(); //simulamos ser el juego, configurando este tablero como ganador

        expResult = true;
        result = board.isTerminalState();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of tileAt method, of class GameBoard.
     */
    @Test
    public void testTileAt() {
        System.out.println("tileAt");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        int x = 0;
        int y = 0;
        Tile expResult = tileContainer.getTile(0);
        Tile result = board.tileAt(x, y);
        Assert.assertEquals(expResult, result);

        x = 3;
        y = 3;
        expResult = tileContainer.getTile(4);
        result = board.tileAt(x, y);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of updateInternalState method, of class GameBoard.
     */
    @Test
    public void testUpdateInternalState() {
        System.out.println("updateInternalState");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        Integer[] expResult = {0, 2, 13};
        Integer[] result = new Integer[3];
        board.availableSpace().toArray(result);

        Assert.assertArrayEquals(expResult, result);

        // =========================================== //
    }

}
