/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.NTupleMaxTile;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import ar.edu.unrc.tdlearning.perceptron.ntuple.SamplePointState;
import java.util.List;
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

    /**
     * Test of calculateCustomHash method, of class GameBoard.
     */
    @Test
    public void testCalculateCustomHash() {
        System.out.println("calculateCustomHash");
        Tile t1 = null;
        Tile t2 = null;
        Tile t3 = null;
        Tile t4 = null;
        int expResult = 0;
        int result = GameBoard.calculateCustomHash(t1, t2, t3, t4);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTile method, of class GameBoard.
     */
    @Test
    public void testAddTile() {
        System.out.println("addTile");
        GameBoard instance = null;
        instance.addTile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of availableSpace method, of class GameBoard.
     */
    @Test
    public void testAvailableSpace() {
        System.out.println("availableSpace");
        GameBoard instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.availableSpace();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canMove method, of class GameBoard.
     */
    @Test
    public void testCanMove() {
        System.out.println("canMove");
        GameBoard instance = null;
        boolean expResult = false;
        boolean result = instance.canMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearBoard method, of class GameBoard.
     */
    @Test
    public void testClearBoard() {
        System.out.println("clearBoard");
        TileContainer tileContainer = null;
        GameBoard instance = null;
        instance.clearBoard(tileContainer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class GameBoard.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        GameBoard instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGame method, of class GameBoard.
     */
    @Test
    public void testGetGame() {
        System.out.println("getGame");
        GameBoard instance = null;
        Game2048 expResult = null;
        Game2048 result = instance.getGame();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxTileNumberCode method, of class GameBoard.
     */
    @Test
    public void testGetMaxTileNumberCode() {
        System.out.println("getMaxTileNumberCode");
        GameBoard instance = null;
        int expResult = 0;
        int result = instance.getMaxTileNumberCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxTileNumberValue method, of class GameBoard.
     */
    @Test
    public void testGetMaxTileNumberValue() {
        System.out.println("getMaxTileNumberValue");
        GameBoard instance = null;
        int expResult = 0;
        int result = instance.getMaxTileNumberValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNTuple method, of class GameBoard.
     */
    @Test
    public void testGetNTuple() {
        System.out.println("getNTuple");
        int nTupleIndex = 0;
        GameBoard instance = null;
        SamplePointState[] expResult = null;
        SamplePointState[] result = instance.getNTuple(nTupleIndex);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPartialScore method, of class GameBoard.
     */
    @Test
    public void testGetPartialScore() {
        System.out.println("getPartialScore");
        GameBoard instance = null;
        int expResult = 0;
        int result = instance.getPartialScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPartialScore method, of class GameBoard.
     */
    @Test
    public void testSetPartialScore() {
        System.out.println("setPartialScore");
        int partialScore = 0;
        GameBoard instance = null;
        instance.setPartialScore(partialScore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStateReward method, of class GameBoard.
     */
    @Test
    public void testGetStateReward() {
        System.out.println("getStateReward");
        int outputNeuron = 0;
        GameBoard instance = null;
        double expResult = 0.0;
        double result = instance.getStateReward(outputNeuron);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTiles method, of class GameBoard.
     */
    @Test
    public void testGetTiles() {
        System.out.println("getTiles");
        GameBoard instance = null;
        Tile[] expResult = null;
        Tile[] result = instance.getTiles();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTiles method, of class GameBoard.
     */
    @Test
    public void testSetTiles() {
        System.out.println("setTiles");
        Tile[] tiles = null;
        GameBoard instance = null;
        instance.setTiles(tiles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class GameBoard.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        GameBoard instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAWin method, of class GameBoard.
     */
    @Test
    public void testIsAWin() {
        System.out.println("isAWin");
        GameBoard instance = null;
        boolean expResult = false;
        boolean result = instance.isAWin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFull method, of class GameBoard.
     */
    @Test
    public void testIsFull() {
        System.out.println("isFull");
        GameBoard instance = null;
        boolean expResult = false;
        boolean result = instance.isFull();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNeedToAddTile method, of class GameBoard.
     */
    @Test
    public void testIsNeedToAddTile() {
        System.out.println("isNeedToAddTile");
        GameBoard instance = null;
        boolean expResult = false;
        boolean result = instance.isNeedToAddTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeedToAddTile method, of class GameBoard.
     */
    @Test
    public void testSetNeedToAddTile() {
        System.out.println("setNeedToAddTile");
        boolean needToAddTile = false;
        GameBoard instance = null;
        instance.setNeedToAddTile(needToAddTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllPossibleNextTurnStateFromAfterstate method, of class GameBoard.
     */
    @Test
    public void testListAllPossibleNextTurnStateFromAfterstate() {
        System.out.println("listAllPossibleNextTurnStateFromAfterstate");
        GameBoard instance = null;
        List<StateProbability> expResult = null;
        List<StateProbability> result = instance.listAllPossibleNextTurnStateFromAfterstate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setToWin method, of class GameBoard.
     */
    @Test
    public void testSetToWin() {
        System.out.println("setToWin");
        GameBoard instance = null;
        instance.setToWin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of translateToPerceptronInput method, of class GameBoard.
     */
    @Test
    public void testTranslateToPerceptronInput() {
        System.out.println("translateToPerceptronInput");
        int neuronIndex = 0;
        GameBoard instance = null;
        double expResult = 0.0;
        double result = instance.translateToPerceptronInput(neuronIndex);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPartialScore method, of class GameBoard.
     */
    @Test
    public void testAddPartialScore() {
        System.out.println("addPartialScore");
        int value = 0;
        GameBoard instance = null;
        instance.addPartialScore(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
