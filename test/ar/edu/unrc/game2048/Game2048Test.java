/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Franco
 */
public class Game2048Test {

    private Game2048 game;
    private final TileContainer tileContainer;

    public Game2048Test() {
        tileContainer = new TileContainer(17);
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
        if ( game != null ) {
            game.dispose();
            game = null;
        }
    }

    /**
     * Test of listAllPossibleActions method, of class Game2048.
     */
    @Test
    public void testListAllPossibleActions() {
        System.out.println("listAllPossibleActions");
        game = new Game2048(null, 2_048, false, 0, true);
        IState state1, state2;

        //inicializamos un tablero terminal
        GameBoard board = new GameBoard(game, tileContainer);
        Tile[] terminalBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(5), tileContainer.getTile(2)
        };
        board.setTiles(terminalBoard);
        board.updateInternalState(true);

        ArrayList<IAction> result = game.listAllPossibleActions(board);
        Assert.assertTrue(result.isEmpty());

        // =========================================== //
        //inicializamos un tablero no terminal
        board = new GameBoard(game, tileContainer);
        Tile[] fullNotTerminalBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(1), tileContainer.getTile(2)
        };
        board.setTiles(fullNotTerminalBoard);
        board.updateInternalState(true);

        Set<IAction> expResult = new HashSet<>();
        expResult.add(Action.down);
        expResult.add(Action.up);

        state1 = game.computeAfterState(board, Action.down); //para comparar luego
        Assert.assertNotNull(state1);

        result = game.listAllPossibleActions(board);
        Assert.assertTrue(result.size() == 2);

        Set<IAction> resultSet = new HashSet<>(game.listAllPossibleActions(board));
        for ( IAction action : resultSet ) {
            Assert.assertTrue(resultSet.contains(action));
        }

        //verificamos que si se llama al afterstate antes de listAllPossibleActions
        // con los mismos tableros, devuelven instancias diferentes, pero con el mismo contenido logico
        state2 = game.computeAfterState(board, Action.down);
        Assert.assertNotSame(state1, state2);
        Assert.assertTrue(((GameBoard) state1).isEqual((GameBoard) state2));

        //verificamos que proximas llamadas a computeafterstate retorne valores ya calculados y no los calcule otra vez
        state1 = game.computeAfterState(board, Action.down);
        Assert.assertNotNull(state1);
        state2 = game.computeAfterState(board, Action.down);
        Assert.assertNotNull(state2);
        Assert.assertEquals(state1, state2);

        state2 = game.computeAfterState(board, Action.up);
        Assert.assertNotSame(state1, state2);

        //verificamos que valores no calculados retornen null
        state1 = game.computeAfterState(board, Action.left);
        Assert.assertNull(state1);
        state1 = game.computeAfterState(board, Action.right);
        Assert.assertNull(state1);

        // =========================================== //
        //inicializamos un tablero con muchos movimientos terminal
        board = new GameBoard(game, tileContainer);
        Tile[] multipleMovesTerminalBoard = {
            tileContainer.getTile(7), tileContainer.getTile(1), tileContainer.getTile(3), tileContainer.getTile(2),
            tileContainer.getTile(1), tileContainer.getTile(0), tileContainer.getTile(5), tileContainer.getTile(1),
            tileContainer.getTile(3), tileContainer.getTile(5), tileContainer.getTile(1), tileContainer.getTile(6),
            tileContainer.getTile(1), tileContainer.getTile(8), tileContainer.getTile(1), tileContainer.getTile(2)
        };
        board.setTiles(multipleMovesTerminalBoard);
        board.updateInternalState(true);

        expResult.clear();
        expResult.add(Action.down);
        expResult.add(Action.up);
        expResult.add(Action.right);
        expResult.add(Action.left);
        result = game.listAllPossibleActions(board);
        Assert.assertTrue(result.size() == 4);
        resultSet = new HashSet<>(game.listAllPossibleActions(board));
        for ( IAction action : resultSet ) {
            Assert.assertTrue(resultSet.contains(action));
        }

        //verificamos que proximas llamadas a computeafterstate retorne valores ya calculados y no los calcule otra vez
        state1 = game.computeAfterState(board, Action.right);
        Assert.assertNotNull(state1);
        state2 = game.computeAfterState(board, Action.right);
        Assert.assertNotNull(state2);
        Assert.assertEquals(state1, state2);

        state2 = game.computeAfterState(board, Action.left);
        Assert.assertNotSame(state1, state2);
    }

}
