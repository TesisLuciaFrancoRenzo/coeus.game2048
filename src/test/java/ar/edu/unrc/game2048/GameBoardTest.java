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

import ar.edu.unrc.game2048.experiments.configurations.ntuples.ConfigNTupleBasicLinear_32768;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@SuppressWarnings( "UnusedAssignment" )
public
class GameBoardTest {

    private final Tile[][]                      emptyBoard;
    private final ConfigNTupleBasicLinear_32768 nTupleConfiguration;
    private final Tile[][]                      randomBoard;
    private Game2048 game = null;

    /**
     *
     */
    public
    GameBoardTest() {
        emptyBoard = new Tile[][] {
                { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, { null, null, null, null }
        };

        randomBoard = new Tile[][] {
                { null, new Tile(16), null, new Tile(131072) },
                { new Tile(16), new Tile(64), new Tile(16), new Tile(16) },
                { new Tile(4), new Tile(16), new Tile(32), new Tile(128) },
                { new Tile(16), null, new Tile(2), new Tile(16) }
        };
        nTupleConfiguration = new ConfigNTupleBasicLinear_32768();
    }

    /**
     *
     */
    @Before
    public
    void setUp() {
        game = new Game2048(null, nTupleConfiguration, 2_048, false);
    }

    /**
     * Test of tileAt method, of class GameBoard.
     */
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public
    void testFailTileAt() {
        System.out.println("tileAt Fail");

        GameBoard board = new GameBoard(game);
        board.setTiles(randomBoard);
        board.clearInterns(true);

        int  x      = 3;
        int  y      = 4;
        Tile result = board.tileAt(x, y);

        board = new GameBoard(game);
        board.setTiles(randomBoard);
        board.clearInterns(true);

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

        GameBoard board = new GameBoard(game);
        board.setTiles(emptyBoard);
        board.clearInterns(true);

        GameBoard copy = (GameBoard) board.getCopy();

        assertThat(board.equals(copy), is(true));

        // =========================================== //
        board = new GameBoard(game);
        final Tile[][] fullBoard = {
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) }
        };
        board.setTiles(fullBoard);
        board.clearInterns(true);

        copy = (GameBoard) board.getCopy();

        assertThat(board.equals(copy), is(true));

        // =========================================== //
        board = new GameBoard(game);
        final Tile[][] almostFull = {
                { null, new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) },
                { new Tile(16), new Tile(16), new Tile(16), new Tile(16) }
        };
        board.setTiles(almostFull);
        board.clearInterns(true);

        copy = (GameBoard) board.getCopy();

        assertThat(board.equals(copy), is(true));

    }

    /**
     * Test of isEqual method, of class GameBoard.
     */
    @Test
    public
    void testIsEqual() {
        System.out.println("isEqual");

        GameBoard board1 = new GameBoard(game);
        board1.setTiles(emptyBoard);
        board1.clearInterns(true);

        GameBoard board2 = new GameBoard(game);
        board2.setTiles(emptyBoard);
        board2.clearInterns(true);

        assertThat(board1.equals(board2), is(true));

        // =========================================== //
        board1 = new GameBoard(game);
        board1.setTiles(randomBoard);
        board1.clearInterns(true);

        board2 = new GameBoard(game);
        board2.setTiles(randomBoard);
        board2.clearInterns(true);

        assertThat(board1.equals(board2), is(true));
    }

    /**
     * Test of isTerminalState method, of class GameBoard.
     */
    @Test
    public
    void testIsTerminalState() {
        System.out.println("isTerminalState");
        GameBoard board = new GameBoard(game);
        final Tile[][] terminalBoard = {
                { new Tile(128), new Tile(2), new Tile(8), new Tile(4) },
                { new Tile(2), new Tile(16), new Tile(32), new Tile(2) },
                { new Tile(8), new Tile(32), new Tile(2), new Tile(64) },
                { new Tile(2), new Tile(256), new Tile(32), new Tile(4) }
        };
        board.setTiles(terminalBoard);
        board.clearInterns(true);

        boolean expResult = true;
        boolean result    = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game);
        final Tile[][] fullNotTerminalBoard = {
                { new Tile(128), new Tile(2), new Tile(8), new Tile(4) },
                { new Tile(2), new Tile(16), new Tile(32), new Tile(2) },
                { new Tile(8), new Tile(32), new Tile(2), new Tile(64) },
                { new Tile(2), new Tile(256), new Tile(2), new Tile(4) }
        };
        board.setTiles(fullNotTerminalBoard);
        board.clearInterns(true);

        expResult = false;
        result = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game);
        board.setTiles(randomBoard);
        board.clearInterns(true);

        expResult = false;
        result = board.isTerminalState();
        Assert.assertEquals(expResult, result);

        // =========================================== //
        board = new GameBoard(game);
        final Tile[][] winBoard = {
                { new Tile(128), new Tile(2), new Tile(8), new Tile(4) },
                { new Tile(2), new Tile(2048), new Tile(32), new Tile(2) },
                { new Tile(8), new Tile(32), new Tile(2), new Tile(64) },
                { new Tile(2), new Tile(256), new Tile(8), new Tile(4) }
        };
        board.setTiles(winBoard);
        board.clearInterns(true);
        //        board.setToWin(); //simulamos ser el juego, configurando este tablero como ganador

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

        final GameBoard board = new GameBoard(game);
        board.setTiles(randomBoard);
        board.clearInterns(true);

        int  x         = 0;
        int  y         = 0;
        Tile expResult = null;
        Tile result    = board.tileAt(x, y);
        Assert.assertEquals(expResult, result);

        x = 3;
        y = 3;
        expResult = new Tile(16);
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

        final GameBoard board = new GameBoard(game);
        board.setTiles(randomBoard);
        board.clearInterns(true);

        final Integer[] result    = new Integer[3];
        board.getAvailableSpace().toArray(result);

        final Integer[] expResult = { 0, 2, 13 };
        Assert.assertArrayEquals(expResult, result);

        // =========================================== //
    }

}
