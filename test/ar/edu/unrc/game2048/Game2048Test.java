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

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IState;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IStatePerceptron;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Game2048Test {

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

    private Game2048 game;
    private final TileContainer tileContainer;

    /**
     *
     */
    public Game2048Test() {
        tileContainer = new TileContainer(17);
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
        if ( game != null ) {
            game.dispose();
            game = null;
        }
    }

    /**
     * Test of computeAfterState method, of class Game2048.
     */
    @Test
    public void testComputeAfterState() {
        System.out.println("computeAfterState");
        IState turnInitialState = null;
        IAction action = null;
        Game2048 instance = null;
        IStatePerceptron expResult = null;
        IStatePerceptron result = instance.computeAfterState(turnInitialState, action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNextTurnStateFromAfterstate method, of class Game2048.
     */
    @Test
    public void testComputeNextTurnStateFromAfterstate() {
        System.out.println("computeNextTurnStateFromAfterstate");
        IState s1 = null;
        Game2048 instance = null;
        IStatePerceptron expResult = null;
        IStatePerceptron result = instance.computeNextTurnStateFromAfterstate(s1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeNumericRepresentationFor method, of class Game2048.
     */
    @Test
    public void testComputeNumericRepresentationFor() {
        System.out.println("computeNumericRepresentationFor");
        Object[] output = null;
        Game2048 instance = null;
        IsolatedComputation<Double> expResult = null;
        IsolatedComputation<Double> result = instance.computeNumericRepresentationFor(output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of denormalizeValueFromPerceptronOutput method, of class Game2048.
     */
    @Test
    public void testDenormalizeValueFromPerceptronOutput() {
        System.out.println("denormalizeValueFromPerceptronOutput");
        Object value = null;
        Game2048 instance = null;
        double expResult = 0.0;
        double result = instance.denormalizeValueFromPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dispose method, of class Game2048.
     */
    @Test
    public void testDispose() {
        System.out.println("dispose");
        Game2048 instance = null;
        instance.dispose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluateBoardWithPerceptron method, of class Game2048.
     */
    @Test
    public void testEvaluateBoardWithPerceptron() {
        System.out.println("evaluateBoardWithPerceptron");
        IState state = null;
        Game2048 instance = null;
        IsolatedComputation expResult = null;
        IsolatedComputation result = instance.evaluateBoardWithPerceptron(state);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class Game2048.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        Game2048 instance = null;
        GameBoard expResult = null;
        GameBoard result = instance.getBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalReward method, of class Game2048.
     */
    @Test
    public void testGetFinalReward() {
        System.out.println("getFinalReward");
        int outputNeuron = 0;
        Game2048 instance = null;
        double expResult = 0.0;
        double result = instance.getFinalReward(outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastTurn method, of class Game2048.
     */
    @Test
    public void testGetLastTurn() {
        System.out.println("getLastTurn");
        Game2048 instance = null;
        int expResult = 0;
        int result = instance.getLastTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxNumber method, of class Game2048.
     */
    @Test
    public void testGetMaxNumber() {
        System.out.println("getMaxNumber");
        Game2048 instance = null;
        int expResult = 0;
        int result = instance.getMaxNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxNumberCode method, of class Game2048.
     */
    @Test
    public void testGetMaxNumberCode() {
        System.out.println("getMaxNumberCode");
        Game2048 instance = null;
        int expResult = 0;
        int result = instance.getMaxNumberCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerceptronConfiguration method, of class Game2048.
     */
    @Test
    public void testGetPerceptronConfiguration() {
        System.out.println("getPerceptronConfiguration");
        Game2048 instance = null;
        PerceptronConfiguration2048 expResult = null;
        PerceptronConfiguration2048 result = instance.getPerceptronConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getScore method, of class Game2048.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        Game2048 instance = null;
        int expResult = 0;
        int result = instance.getScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getnTupleSystemConfiguration method, of class Game2048.
     */
    @Test
    public void testGetnTupleSystemConfiguration() {
        System.out.println("getnTupleSystemConfiguration");
        Game2048 instance = null;
        NTupleConfiguration2048 expResult = null;
        NTupleConfiguration2048 result = instance.getnTupleSystemConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iLoose method, of class Game2048.
     */
    @Test
    public void testILoose() {
        System.out.println("iLoose");
        Game2048 instance = null;
        boolean expResult = false;
        boolean result = instance.iLoose();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iWin method, of class Game2048.
     */
    @Test
    public void testIWin() {
        System.out.println("iWin");
        Game2048 instance = null;
        boolean expResult = false;
        boolean result = instance.iWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class Game2048.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        Game2048 instance = null;
        IState expResult = null;
        IState result = instance.initialize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of left method, of class Game2048.
     */
    @Test
    public void testLeft() {
        System.out.println("left");
        Game2048 instance = null;
        GameBoard expResult = null;
        GameBoard result = instance.left(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllPossibleActions method, of class Game2048.
     */
    @Test
    public void testListAllPossibleActions() {
        System.out.println("listAllPossibleActions");
        game = new Game2048(null, 2_048, false, 0, true);
        IStatePerceptron state1;
        IState state2;

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

    /**
     * Test of listAllPossibleNextTurnStateFromAfterstate method, of class
     * Game2048.
     */
    @Test
    public void testListAllPossibleNextTurnStateFromAfterstate() {
        System.out.println("listAllPossibleNextTurnStateFromAfterstate");
        IState afterState = null;
        Game2048 instance = null;
        List<StateProbability> expResult = null;
        List<StateProbability> result = instance.listAllPossibleNextTurnStateFromAfterstate(afterState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Game2048.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Game2048.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeValueToPerceptronOutput method, of class Game2048.
     */
    @Test
    public void testNormalizeValueToPerceptronOutput() {
        System.out.println("normalizeValueToPerceptronOutput");
        Object value = null;
        Game2048 instance = null;
        double expResult = 0.0;
        double result = instance.normalizeValueToPerceptronOutput(value);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paint method, of class Game2048.
     */
    @Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        Game2048 instance = null;
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processInput method, of class Game2048.
     */
    @Test
    public void testProcessInput() {
        System.out.println("processInput");
        int keyCode = 0;
        Game2048 instance = null;
        instance.processInput(keyCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetGame method, of class Game2048.
     */
    @Test
    public void testResetGame() {
        System.out.println("resetGame");
        Game2048 instance = null;
        instance.resetGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentState method, of class Game2048.
     */
    @Test
    public void testSetCurrentState() {
        System.out.println("setCurrentState");
        IState nextTurnState = null;
        Game2048 instance = null;
        instance.setCurrentState(nextTurnState);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
