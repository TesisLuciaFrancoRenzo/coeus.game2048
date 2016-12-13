package ar.edu.unrc.game2048.experiments.configurations.perceptrons;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.Tile;
import ar.edu.unrc.game2048.TileContainer;
import ar.edu.unrc.game2048.experiments.configurations.ntuples.ConfigNTupleBasicTanHSimplified_512;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.inputs.InputNTupleList;
import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author franc
 */
public
class PNTuple512Test {

    /**
     *
     */
    public
    PNTuple512Test() {
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
    }

    /**
     *
     */
    @After
    public
    void tearDown() {
    }

    /**
     * Test of calculateNormalizedPerceptronInput method, of class ConfigPerceptronNTupleTanHSimplified_512.
     */
    @Test
    public
    void testCalculateNormalizedPerceptronInput() {
        System.out.println("calculateNormalizedPerceptronInput");

        ConfigPerceptronNTupleTanHSimplified_512 nTupleConfiguration = new ConfigPerceptronNTupleTanHSimplified_512(true);
        TileContainer                            tileContainer       = new TileContainer(nTupleConfiguration.getMaxTile());

        Game2048  game  = new Game2048(nTupleConfiguration, null, (int) Math.pow(2, nTupleConfiguration.getMaxTile()), 0, false);
        GameBoard board = new GameBoard(game, tileContainer);
        board.setTiles(new Tile[]{tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.
                getTile(0), tileContainer.getTile(9), tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(2), tileContainer.getTile(4), tileContainer.
                getTile(5), tileContainer.getTile(7), tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.
                getTile(1), tileContainer.getTile(4)});

        InputNTupleList normalizedPerceptronInput = new InputNTupleList();
        nTupleConfiguration.calculateNormalizedPerceptronInput(board, normalizedPerceptronInput);

        //----------------------
        ConfigNTupleBasicTanHSimplified_512 nTupleConfiguration2 = new ConfigNTupleBasicTanHSimplified_512();
        Game2048                            game2                = new Game2048(
                null,
                nTupleConfiguration2,
                (int) Math.pow(2, nTupleConfiguration2.getMaxTile()),
                0,
                false
        );
        GameBoard                           board2               = new GameBoard(game2, tileContainer);
        board2.setTiles(new Tile[]{tileContainer.getTile(0), tileContainer.getTile(4), tileContainer.
                getTile(0), tileContainer.getTile(9), tileContainer.getTile(4), tileContainer.getTile(6), tileContainer.
                getTile(4), tileContainer.getTile(4), tileContainer.getTile(2), tileContainer.getTile(4), tileContainer.
                getTile(5), tileContainer.getTile(7), tileContainer.getTile(4), tileContainer.getTile(0), tileContainer.
                getTile(1), tileContainer.getTile(4)});

        NTupleSystem nTupleSystem = new NTupleSystem(nTupleConfiguration2.getAllSamplePointPossibleValues(), nTupleConfiguration2.getNTuplesLength(),
                nTupleConfiguration2.getActivationFunction(),
                nTupleConfiguration2.getDerivedActivationFunction(),
                false
        );
        int[] indexes = nTupleSystem.getComplexComputation(board2).getIndexes();
        assertThat(normalizedPerceptronInput.getInternalSetSize(), is(indexes.length));
        for (int i : indexes) {
            assertThat(normalizedPerceptronInput.get(i), is(1d));
        }
    }
}
