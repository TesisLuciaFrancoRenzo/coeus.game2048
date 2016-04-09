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
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class BoardMaxTileTest {

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
    private BoardMaxTile<BasicNetwork> perceptronConfiguration;
    private final Tile[] randomBoard1;

    private final TileContainer tileContainer;

    /**
     *
     */
    public BoardMaxTileTest() {
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
        perceptronConfiguration = new BoardMaxTile<>();
        game = new Game2048(perceptronConfiguration, null, 2_048, 0);
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

        assertEquals(-1d, board.translateToPerceptronInput(0), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(1), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(2), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(3), 0.0d);

        assertEquals(-1d, board.translateToPerceptronInput(4), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(5), 0.0d);

        double normalization = ((2d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(6), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(7), 0.0d);

        assertEquals(-1d, board.translateToPerceptronInput(8), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(9), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(10), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(11), 0.0d);

        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(12), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(13), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(14), 0.0d);
        assertEquals(-1d, board.translateToPerceptronInput(15), 0.0d);

        board = new GameBoard<>(game, tileContainer);
        board.setTiles(randomBoard1);
        board.updateInternalState(true); //dentro de este metodo se utiliza el "calculateNormalizedPerceptronInput"

        normalization = ((0d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(0), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(1), 0.0d);
        normalization = ((2d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(2), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(3), 0.0d);

        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(4), 0.0d);
        normalization = ((6d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(5), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(6), 0.0d);
        normalization = ((0d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(7), 0.0d);

        normalization = ((0d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(8), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(9), 0.0d);
        normalization = ((5d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(10), 0.0d);
        normalization = ((1d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(11), 0.0d);

        normalization = ((17d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(12), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(13), 0.0d);
        normalization = ((7d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(14), 0.0d);
        normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.translateToPerceptronInput(15), 0.0d);
    }

    /**
     * Test of translateRealOutputToNormalizedPerceptronOutputFrom method, of
     * class BoardMaxTile.
     */
    @Test
    public void testTranslateThisFinalStateToPerceptronOutput() {
        System.out.println("translateThisFinalStateToPerceptronOutput");

        GameBoard<BasicNetwork> board = new GameBoard<>(game, tileContainer);
        board.setTiles(initialBoard);
        board.updateInternalState(true);

        double normalization = ((4d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.getMaxTileNumberValue(), 0.0d);

        board = new GameBoard<>(game, tileContainer);
        board.setTiles(randomBoard1);
        board.updateInternalState(true);

        normalization = ((17d - 0d) / (17d - 0d)) * (1d - -1d) + -1d;
        assertEquals(normalization, board.getMaxTileNumberValue(), 0.0d);

    }

}
