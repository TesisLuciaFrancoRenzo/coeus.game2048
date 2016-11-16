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

import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.PBoard_32768;
import org.junit.*;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@SuppressWarnings("unchecked")
public
class GameBoardTest {

    private final Tile[]        emptyBoard;
    private final PBoard_32768  nTupleConfiguration;
    private final Tile[]        randomBoard;
    private final TileContainer tileContainer;
    private       Game2048      game;

    /**
     *
     */
    public
    GameBoardTest() {
        tileContainer = new TileContainer(17);
        this.emptyBoard = new Tile[]{tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.
                getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.
                getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.
                getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.
                getTile(0), tileContainer.getTile(0)};

        this.randomBoard = new Tile[]{tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.
                getTile(0), tileContainer.getTile(17), tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(2), tileContainer.getTile(4), tileContainer.
                getTile(5), tileContainer.getTile(7), tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.
                getTile(1), tileContainer.getTile(4)};
        nTupleConfiguration = new PBoard_32768(true);
    }

    /**
     *
     */
    @BeforeClass
    public static
    void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static
    void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public
    void setUp() {
        game = new Game2048(nTupleConfiguration, null, 2_048, 0);
    }

    /**
     *
     */
    @After
    public
    void tearDown() {
    }

    /**
     * Test of tileAt method, of class GameBoard.
     */
    @Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
    public
    void testFailTileAt() {
        System.out.println("tileAt Fail");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        int  x      = 3;
        int  y      = 4;
        Tile result = board.tileAt(x, y);

        board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        x = -1;
        y = 0;
        result = board.tileAt(x, y);
    }

    /**
     * Test of getCopy method, of class GameBoard.
     */
    @Test
    public
    void testGetCopy() {
        System.out.println("getCopy");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(emptyBoard);
        board.updateInternalState(false);

        GameBoard copy = (GameBoard) board.getCopy();

        Assert.assertEquals(board, copy);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] fullBoard = {tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4)};
        board.setTiles(fullBoard);
        board.updateInternalState(true);

        copy = (GameBoard) board.getCopy();

        Assert.assertEquals(board, copy);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] almostFull = {tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.getTile(4), tileContainer.
                getTile(4), tileContainer.getTile(4)};
        board.setTiles(almostFull);
        board.updateInternalState(true);

        copy = (GameBoard) board.getCopy();

        Assert.assertEquals(board, copy);

    }

    /**
     * Test of isEqual method, of class GameBoard.
     */
    @Test
    public
    void testIsEqual() {
        System.out.println("isEqual");

        GameBoard board1 = new GameBoard(game, tileContainer);
        board1.setTiles(emptyBoard);
        board1.updateInternalState(false);

        GameBoard board2 = new GameBoard(game, tileContainer);
        board2.setTiles(emptyBoard);
        board2.updateInternalState(false);

        boolean expResult = true;
        boolean result    = board1.isEqual(board2);
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
    public
    void testIsTerminalState() {
        System.out.println("isTerminalState");
        GameBoard board = new GameBoard(game, tileContainer);
        Tile[] terminalBoard = {tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.
                getTile(3), tileContainer.getTile(2), tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.
                getTile(5), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.
                getTile(1), tileContainer.getTile(6), tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.
                getTile(5), tileContainer.getTile(2)};
        board.setTiles(terminalBoard);
        board.updateInternalState(true);

        boolean expResult = true;
        boolean result    = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game, tileContainer);
        Tile[] fullNotTerminalBoard = {tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.
                getTile(3), tileContainer.getTile(2), tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.
                getTile(5), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.
                getTile(1), tileContainer.getTile(6), tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.
                getTile(1), tileContainer.getTile(2)};
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
        Tile[] winBoard = {tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.
                getTile(3), tileContainer.getTile(2), tileContainer.getTile(1), tileContainer.getTile(0), tileContainer.
                getTile(5), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.
                getTile(1), tileContainer.getTile(6), tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.
                getTile(1), tileContainer.getTile(2)};
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
    public
    void testTileAt() {
        System.out.println("tileAt");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        int  x         = 0;
        int  y         = 0;
        Tile expResult = tileContainer.getTile(0);
        Tile result    = board.tileAt(x, y);
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
    public
    void testUpdateInternalState() {
        System.out.println("updateInternalState");

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(randomBoard);
        board.updateInternalState(true);

        Integer[] expResult = {0, 2, 13};
        Integer[] result    = new Integer[3];
        board.availableSpace().toArray(result);

        Assert.assertArrayEquals(expResult, result);

        // =========================================== //
    }

}
