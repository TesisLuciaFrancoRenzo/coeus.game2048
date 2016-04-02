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
import java.util.ArrayList;
import java.util.List;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
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
public class BoardScoreCustomNormalizationTest {

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
    public double maxCodedNumber;

    /**
     *
     */
    public double minCodedNumber;

    private final ActivationTANH activationFunctionForEncog;
    private final double activationFunctionMax;
    private final double activationFunctionMin;

    private BoardScoreCustomNormalization config;
    private Game2048 game;
    private final NormalizedField normInputSimpleBoard;
    private final TileContainer tileContainer;

    /**
     *
     */
    public BoardScoreCustomNormalizationTest() {
        tileContainer = new TileContainer(17);
        activationFunctionForEncog = new ActivationTANH();
        activationFunctionMax = 1d;
        activationFunctionMin = -1d;
        maxCodedNumber = 1;
        minCodedNumber = 0;
        normInputSimpleBoard = new NormalizedField(NormalizationAction.Normalize,
                null, maxCodedNumber, minCodedNumber, activationFunctionMax, activationFunctionMin);
    }

    /**
     *
     */
    @Before
    public void setUp() {
        config = new BoardScoreCustomNormalization();
        game = new Game2048(config, null, 2_048, 0);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getPerceptronInterface method, of class EncogExperiment.
     */
    @Test
    public void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");

        Tile[] example1 = {
            tileContainer.getTile(1), tileContainer.getTile(0), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(2), tileContainer.getTile(1), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.getTile(0), tileContainer.getTile(0),
            tileContainer.getTile(5), tileContainer.getTile(3), tileContainer.getTile(2), tileContainer.getTile(2)
        };

        double[] expectedResults = {
            normInputSimpleBoard.normalize(1 / 6d), normInputSimpleBoard.normalize(0d), normInputSimpleBoard.normalize(0d), normInputSimpleBoard.normalize(0d),
            normInputSimpleBoard.normalize(2 / 6d), normInputSimpleBoard.normalize(1 / 6d), normInputSimpleBoard.normalize(0d), normInputSimpleBoard.normalize(0d),
            normInputSimpleBoard.normalize(4 / 6d), normInputSimpleBoard.normalize(6 / 6d), normInputSimpleBoard.normalize(0d), normInputSimpleBoard.normalize(0d),
            normInputSimpleBoard.normalize(5 / 6d), normInputSimpleBoard.normalize(3 / 6d), normInputSimpleBoard.normalize(2 / 6d), normInputSimpleBoard.normalize(2 / 6d)
        };

        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(example1);
        board.updateInternalState(true);

        List<Double> translation = new ArrayList<>(config.neuronQuantityInLayer.length);
        for ( int i = 0; i < config.neuronQuantityInLayer.length; i++ ) {
            translation.add(null);
        }

        config.calculateNormalizedPerceptronInput(board, translation);

        for ( int i = 0; i < 10; i++ ) {
            Assert.assertEquals("Index =" + i, expectedResults[i], translation.get(i), 0.000000000001);
        }

    }

}
