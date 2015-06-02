/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.game2048.TileContainer;
import static junit.framework.Assert.assertEquals;
import org.encog.neural.networks.BasicNetwork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author franco
 */
public class FullNTupleMaxTileTest {

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
    private Game2048<BasicNetwork> game;

    private final Tile[] initialBoard;
    private FullNTupleMaxTile<BasicNetwork> perceptronConfiguration;
    private final Tile[] randomBoard1;

    private final TileContainer tileContainer;

    /**
     *
     */
    public FullNTupleMaxTileTest() {
        tileContainer = new TileContainer(17);
        Tile[] initial = {
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(4),
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(0), tileContainer.getTile(2), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0)
        };
        this.initialBoard = initial;

        Tile[] randomB = {
            tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.getTile(17),
            tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.getTile(4), tileContainer.getTile(4),
            tileContainer.getTile(2), tileContainer.getTile(4), tileContainer.getTile(5), tileContainer.getTile(7),
            tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.getTile(1), tileContainer.getTile(4)
        };
        this.randomBoard1 = randomB;
    }

    /**
     *
     */
    @Before
    public void setUp() {
        perceptronConfiguration = new FullNTupleMaxTile<>();
        game = new Game2048(perceptronConfiguration, 2_048, false, 0, true);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of calculateNormalizedPerceptronInput method, of class BoardMaxTile.
     */
    @Test
    public void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");

        GameBoard<BasicNetwork> board = new GameBoard<>(game, tileContainer);
        board.setTiles(initialBoard);
        board.updateInternalState(true); //dentro de este metodo se utiliza el "calculateNormalizedPerceptronInput"

        //verticales
        double encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        double normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(0), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 2 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(1), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(2), 0.0d);

        encript = 4 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(3), 0.0d);

        //horizontales
        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(4), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(5), 0.0d);

        encript = 0 * 1_000_000 + 2 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(6), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(7), 0.0d);

        //cuadraditos arriba
        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(8), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(9), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(10), 0.0d);

        //cuadraditos centro
        encript = 0 * 1_000_000 + 0 * 10_000 + 2 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(11), 0.0d);

        encript = 0 * 1_000_000 + 2 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(12), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(13), 0.0d);

        //cuadraditos abajo
        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 2;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(14), 0.0d);

        encript = 2 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(15), 0.0d);

        encript = 0 * 1_000_000 + 0 * 10_000 + 0 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(16), 0.0d);

        //custom normalization primer columna
        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(17), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(18), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(19), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(20), 0.0d);

        //custom normalization segunda columna
        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(21), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(22), 0.0d);

        encript = 2d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(23), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(24), 0.0d);

        //custom normalization tercer columna
        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(25), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(26), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(27), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(28), 0.0d);

        //custom normalization tercer columna
        encript = 4d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(29), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(30), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(31), 0.0d);

        encript = 0d / 4d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(32), 0.0d);

        //TODO agregar los testings de la otra parte del tablero codificado (tablero simple)
        //============================================================================================================================
        board = new GameBoard<>(game, tileContainer);
        board.setTiles(randomBoard1);
        board.updateInternalState(true);

        //verticales
        encript = 0 * 1_000_000 + 4 * 10_000 + 2 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(0), 0.0d);

        encript = 4 * 1_000_000 + 6 * 10_000 + 4 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(1), 0.0d);

        encript = 0 * 1_000_000 + 4 * 10_000 + 5 * 100 + 1;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(2), 0.0d);

        encript = 17 * 1_000_000 + 4 * 10_000 + 7 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(3), 0.0d);

        //horizontales
        encript = 0 * 1_000_000 + 4 * 10_000 + 0 * 100 + 17;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(4), 0.0d);

        encript = 4 * 1_000_000 + 6 * 10_000 + 4 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(5), 0.0d);

        encript = 2 * 1_000_000 + 4 * 10_000 + 5 * 100 + 7;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(6), 0.0d);

        encript = 4 * 1_000_000 + 0 * 10_000 + 1 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(7), 0.0d);

        //cuadraditos arriba
        encript = 0 * 1_000_000 + 4 * 10_000 + 6 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(8), 0.0d);

        encript = 4 * 1_000_000 + 6 * 10_000 + 4 * 100 + 0;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(9), 0.0d);

        encript = 0 * 1_000_000 + 4 * 10_000 + 4 * 100 + 17;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(10), 0.0d);

        //cuadraditos centro
        encript = 4 * 1_000_000 + 2 * 10_000 + 4 * 100 + 6;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(11), 0.0d);

        encript = 6 * 1_000_000 + 4 * 10_000 + 5 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(12), 0.0d);

        encript = 4 * 1_000_000 + 5 * 10_000 + 7 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(13), 0.0d);

        //cuadraditos abajo
        encript = 2 * 1_000_000 + 4 * 10_000 + 0 * 100 + 4;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(14), 0.0d);

        encript = 4 * 1_000_000 + 0 * 10_000 + 1 * 100 + 5;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(15), 0.0d);

        encript = 5 * 1_000_000 + 1 * 10_000 + 4 * 100 + 7;
        normalization = ((encript - 0d) / (17171717d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(16), 0.0d);

        //custom normalization primer columna
        encript = 0d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(17), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(18), 0.0d);

        encript = 2d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(19), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(20), 0.0d);

        //custom normalization segunda columna
        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(21), 0.0d);

        encript = 6d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(22), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(23), 0.0d);

        encript = 0d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(24), 0.0d);

        //custom normalization tercer columna
        encript = 0d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(25), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(26), 0.0d);

        encript = 5d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(27), 0.0d);

        encript = 1d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(28), 0.0d);

        //custom normalization tercer columna
        encript = 17d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(29), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(30), 0.0d);

        encript = 7d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(31), 0.0d);

        encript = 4d / 17d;
        normalization = ((encript - 0d) / (1d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(32), 0.0d);

    }

    /**
     * Test of translateRealOutputToNormalizedPerceptronOutputFrom method, of class
     * BoardMaxTile.
     */
    @Test
    public void testTranslateThisFinalStateToPerceptronOutput() {
        System.out.println("translateThisFinalStateToPerceptronOutput");

        GameBoard<BasicNetwork> board = new GameBoard<>(game, tileContainer);
        board.setTiles(initialBoard);
        board.updateInternalState(true);

        double temp = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(temp, board.translateThisFinalStateToPerceptronOutput(0), 0.0d);

        board = new GameBoard<>(game, tileContainer);
        board.setTiles(randomBoard1);
        board.updateInternalState(true);

        temp = ((17d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(temp, board.translateThisFinalStateToPerceptronOutput(0), 0.0d);

    }

}
