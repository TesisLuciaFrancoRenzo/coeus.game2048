
package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.interfaces.IActor;
import ar.edu.unrc.coeus.tdlearning.interfaces.IProblemToTrain;
import ar.edu.unrc.coeus.tdlearning.interfaces.IState;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStateNTuple;
import ar.edu.unrc.coeus.tdlearning.interfaces.IStatePerceptron;
import static ar.edu.unrc.game2048.Action.down;
import static ar.edu.unrc.game2048.Action.right;
import static ar.edu.unrc.game2048.Action.up;
import static ar.edu.unrc.game2048.GameBoard.TILE_NUMBER;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.greedy.StateProbability;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_STROKE_CONTROL;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_STROKE_NORMALIZE;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import static java.lang.String.valueOf;
import static java.lang.Thread.sleep;

/**
 * @author Konstantin Bulenkov, lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public final class Game2048<NeuralNetworkClass> extends JPanel implements IGame, IProblemToTrain {

    private static final Color BG_COLOR = new Color(0xbb_ada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILES_MARGIN = 16;
    private static final int TILE_SIZE = 64;

    /**
     *
     * @param args
     */
    @SuppressWarnings( "ResultOfObjectAllocationIgnored" )
    public static void main(String[] args) {
        new Game2048(null, null, 2_048, 1);
    }

    /**
     * Rota un tablero cierto ángulo.
     *
     * @param angle         ángulo de rotación. Puede ser 90 o 270.
     * @param originalTiles tiles antes de la rotación.
     *
     * @return tablero rotado.
     */
    public static Tile[] rotateBoardTiles(final int angle,
            final Tile[] originalTiles) {
        Tile[] rotatedTiles = new Tile[TILE_NUMBER];
        int offsetX = 3, offsetY = 3;
        if ( angle == 90 ) {
            offsetY = 0;
        } else if ( angle == 270 ) {
            offsetX = 0;
        }
        double rad = toRadians(angle);
        int cos = (int) cos(rad);
        int sin = (int) sin(rad);
        for ( int x = 0; x < 4; x++ ) {
            for ( int y = 0; y < 4; y++ ) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                rotatedTiles[newX + newY * 4] = originalTiles[x + y * 4];
            }
        }
        return rotatedTiles;
    }

    private static void ensureSize(java.util.List<Tile> l,
            int s,
            TileContainer tileContainer) {
        while ( l.size() != s ) {
            l.add(tileContainer.getTile(0));
        }
    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }
    private GameBoard<NeuralNetworkClass> board;
    private final int delayPerMove;
    private ArrayList<GameBoard<NeuralNetworkClass>> futurePossibleBoards;
    private final JFrame gameFrame;
    private GameBoard<NeuralNetworkClass> lastInitialStateForPossibleActions;
    private int maxNumber = 0;
    private int maxNumberCode = 0;
    private boolean myLoose = false;
    private int myScore = 0;
    private boolean myWin = false;
    private final NTupleConfiguration2048 nTupleSystemConfiguration;
    private final NeuralNetworkConfiguration2048<NeuralNetworkClass> neuralNetworkConfiguration;
    private final int numberToWin;
    private final boolean repaint;
    private final TileContainer tileContainer;
    private int turnNumber;

    /**
     * El juego finaliza solo si se alcanza la máxima ficha posible o si no quedan movimientos validos para realizar
     *
     * @param perceptronConfiguration
     * @param nTupleSystemConfiguration
     * @param delayPerMove
     */
    public Game2048(
            NeuralNetworkConfiguration2048<NeuralNetworkClass> perceptronConfiguration,
            NTupleConfiguration2048 nTupleSystemConfiguration,
            int delayPerMove) {
        this(perceptronConfiguration, nTupleSystemConfiguration,
                (int) pow(2, 17), delayPerMove);
    }

    /**
     * El juego finaliza solo si se alcanza el tile con el valor {@code numberToWin} o si no quedan movimientos validos
     * para realizar
     *
     * @param perceptronConfiguration
     * @param nTupleSystemConfiguration
     * @param numberToWin               puntaje que alcanzar para ganar el juego
     * @param delayPerMove
     */
    @SuppressWarnings( {"LeakingThisInConstructor",
        "OverridableMethodCallInConstructor"} )
    public Game2048(
            NeuralNetworkConfiguration2048<NeuralNetworkClass> perceptronConfiguration,
            NTupleConfiguration2048 nTupleSystemConfiguration,
            int numberToWin,
            int delayPerMove) {
        this.neuralNetworkConfiguration = perceptronConfiguration;
        this.delayPerMove = delayPerMove;
        gameFrame = new JFrame();
        gameFrame.setTitle("2048 Game");
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setSize(340, 400);
        gameFrame.setResizable(false);
        gameFrame.add(this);
        this.nTupleSystemConfiguration = nTupleSystemConfiguration;

        this.numberToWin = numberToWin;

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                processInput(e.getKeyCode());
            }
        });

        tileContainer = new TileContainer(17);
        resetGame();

        if ( delayPerMove > 0 ) {
            this.repaint = true;
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
        } else {
            this.repaint = false;
            gameFrame.setState(JFrame.ICONIFIED);
            gameFrame.setFocusableWindowState(false);
            gameFrame.setFocusable(false);
            gameFrame.setVisible(false);
        }
    }

    @Override
    public IStatePerceptron computeAfterState(IState turnInitialState,
            IAction action) {
        GameBoard<NeuralNetworkClass> futureBoard = (GameBoard<NeuralNetworkClass>) turnInitialState.
                getCopy();
        switch ( (Action) action ) {
            case left: {
                if ( lastInitialStateForPossibleActions != null && lastInitialStateForPossibleActions.
                        equals(turnInitialState) ) {
                    futureBoard = futurePossibleBoards.get(0);
                } else {
                    futureBoard = left(futureBoard);
                    futureBoard.updateInternalState(true);
                }
                break;
            }
            case right: {
                if ( lastInitialStateForPossibleActions != null && lastInitialStateForPossibleActions.
                        equals(turnInitialState) ) {
                    futureBoard = futurePossibleBoards.get(1);
                } else {
                    rotate(180, futureBoard);
                    futureBoard = left(futureBoard);
                    rotate(180, futureBoard);
                    futureBoard.updateInternalState(true);
                }
                break;
            }
            case up: {
                if ( lastInitialStateForPossibleActions != null && lastInitialStateForPossibleActions.
                        equals(turnInitialState) ) {
                    futureBoard = futurePossibleBoards.get(2);
                } else {
                    rotate(270, futureBoard);
                    futureBoard = left(futureBoard);
                    rotate(90, futureBoard);
                    futureBoard.updateInternalState(true);
                }
                break;
            }
            case down: {
                if ( lastInitialStateForPossibleActions != null && lastInitialStateForPossibleActions.
                        equals(turnInitialState) ) {
                    futureBoard = futurePossibleBoards.get(3);
                } else {
                    rotate(90, futureBoard);
                    futureBoard = left(futureBoard);
                    rotate(270, futureBoard);
                    futureBoard.updateInternalState(true);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("la accion \"" + action.
                        toString() + "\" no es valida");
            }
        }
        return futureBoard;
    }

    @Override
    public IState computeNextTurnStateFromAfterstate(IState s1) {
        GameBoard<NeuralNetworkClass> finalBoard = (GameBoard<NeuralNetworkClass>) s1.
                getCopy();
        if ( finalBoard.isNeedToAddTile() ) {
            finalBoard.addTile(true);
        }
        return finalBoard;
    }

    @Override
    public Double computeNumericRepresentationFor(Object[] output,
            IActor actor) {
        if ( this.getNeuralNetworkConfiguration() != null ) {
            return this.getNeuralNetworkConfiguration().
                    computeNumericRepresentationFor(this, output);
        } else {
            assert output.length == 1;
            return (Double) output[0];
        }
    }

    @Override
    public double denormalizeValueFromPerceptronOutput(Object value) {
        if ( this.getNeuralNetworkConfiguration() != null ) {
            return this.getNeuralNetworkConfiguration().denormalizeValueFromNeuralNetworkOutput(value);
        } else {
            return this.getnTupleSystemConfiguration().denormalizeValueFromNeuralNetworkOutput(value);
        }
    }

    @Override
    public void dispose() {
        gameFrame.dispose();
    }

    @Override
    public Object[] evaluateBoardWithPerceptron(IState state) {
        //dependiendo de que tipo de red neuronal utilizamos, evaluamos las entradas y calculamos una salida
        if ( neuralNetworkConfiguration != null && neuralNetworkConfiguration.
                getNeuralNetwork() != null ) {
            if ( neuralNetworkConfiguration.getNeuralNetwork() instanceof BasicNetwork ) { //es sobre la libreria encog
                //creamos las entradas de la red neuronal
                double[] inputs = new double[getNeuralNetworkConfiguration().
                        getNeuronQuantityInLayer()[0]];
                IntStream inputLayer = IntStream
                        .range(0, getNeuralNetworkConfiguration().
                                getNeuronQuantityInLayer()[0]);
                if ( getNeuralNetworkConfiguration().isConcurrentInputEnabled() ) {
                    inputLayer = inputLayer.parallel();
                } else {
                    inputLayer = inputLayer.sequential();
                }
                inputLayer.forEach(index ->
                        {
                            inputs[index] = ((IStatePerceptron) state).
                                    translateToPerceptronInput(index);
                        });

                //cargamos la entrada a la red
                MLData inputData = new BasicMLData(inputs);
                MLData output = ((BasicNetwork) neuralNetworkConfiguration.
                        getNeuralNetwork()).compute(inputData);
                Double[] out = new Double[output.getData().length];
                for ( int i = 0; i < output.size(); i++ ) {
                    out[i] = output.getData()[i];
                }
                return out;
            }
        }
        if ( nTupleSystemConfiguration != null && nTupleSystemConfiguration.
                getNTupleSystem() != null ) {
            Double[] out = {nTupleSystemConfiguration.getNTupleSystem().
                getComputation((IStateNTuple) state)};
            return out;
        }
        throw new UnsupportedOperationException(
                "only Encog and NTupleSystem is implemented");
    }

    @Override
    public IActor getActorToTrain() {
        return null;
    }

    /**
     * @return the board
     */
    public GameBoard<NeuralNetworkClass> getBoard() {
        return board;
    }

    @Override
    public void setCurrentState(IState nextTurnState) {
        if ( nextTurnState == null ) {
            throw new IllegalArgumentException("nextTurnState can't be null");
        }
        turnNumber++;
        myScore += ((GameBoard<NeuralNetworkClass>) nextTurnState).
                getPartialScore();
        board = (GameBoard<NeuralNetworkClass>) nextTurnState;

        if ( getBoard().isAWin() ) {
            this.myWin = true;
        }
        if ( !myWin && !board.canMove() ) {
            myLoose = true;
        }
        if ( myWin || myLoose ) {
            maxNumber = getBoard().getMaxTileNumberValue();
            maxNumberCode = getBoard().getMaxTileNumberCode();
        }
        if ( repaint ) {
            try {
                repaint();
                if ( this.delayPerMove > 0 ) {
                    sleep(this.delayPerMove);
                }
            } catch ( InterruptedException ex ) {
                Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    @Override
    public double getFinalReward(IState finalState,
            int outputNeuron) {
        if ( neuralNetworkConfiguration != null ) {
            return neuralNetworkConfiguration.
                    getFinalReward((GameBoard) finalState, outputNeuron);
        } else {
            return nTupleSystemConfiguration.getFinalReward(
                    (GameBoard) finalState, outputNeuron);
        }
    }

    /**
     *
     * @return último turno alcanzado.
     */
    public int getLastTurn() {
        return turnNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * @return mayor tile alcanzado codificado como potencia de 2.
     */
    public int getMaxNumberCode() {
        return maxNumberCode;
    }

    /**
     * @return configuración de la red neuronal utilizada.
     */
    public NeuralNetworkConfiguration2048<NeuralNetworkClass> getNeuralNetworkConfiguration() {
        return neuralNetworkConfiguration;
    }

    @Override
    public int getScore() {
        return myScore;
    }
    /**
     * @return the nTupleSystemConfiguration
     */
    public NTupleConfiguration2048 getnTupleSystemConfiguration() {
        return nTupleSystemConfiguration;
    }

    @Override
    public boolean iLoose() {
        return myLoose;
    }

    @Override
    public boolean iWin() {
        return myWin;
    }

    @Override
    public IState initialize(IActor actor) {
        this.resetGame();
        assert this.getBoard().getMaxTileNumberCode() != 0;
        return getBoard().getCopy();
    }

    /**
     * Realiza el movimiento "hacia la izquierda" en el juego.
     *
     * @param board para mover hacia la izquierda
     *
     * @return Afterstate con el tablero luego del movimiento pero antes de agregar un nuevo Tile, el puntaje, y si es
     *         necesario agregar un nuevo tile.
     */
    public GameBoard<NeuralNetworkClass> left(
            GameBoard<NeuralNetworkClass> board) {
        boolean needAddTile = false;
        board.setPartialScore(0);
        for ( int i = 0; i < 4; i++ ) {
            Tile[] line = getLine(i, board);
            Tile[] merged = mergeLine(moveLine(line), board);
            setLine(i, merged, board);
            if ( !needAddTile && !compare(line, merged) ) {
                needAddTile = true;
            }
        }

        if ( needAddTile ) {
            board.setNeedToAddTile(true);
        }

        return board;
    }

    @Override
    public ArrayList<IAction> listAllPossibleActions(IState initialState) {
        ArrayList<IAction> actions = new ArrayList<>(4);
        lastInitialStateForPossibleActions = null;
        GameBoard state = (GameBoard<NeuralNetworkClass>) initialState;
        if ( !initialState.isTerminalState() ) {
            IStatePerceptron afterstate = computeAfterState(state, Action.left);
            if ( !state.isEqual((GameBoard<NeuralNetworkClass>) afterstate) ) {
                actions.add(Action.left);
                futurePossibleBoards.add(0,
                        (GameBoard<NeuralNetworkClass>) afterstate);
            } else {
                futurePossibleBoards.add(0, null);
            }

            afterstate = computeAfterState(state, right);
            if ( !state.isEqual((GameBoard<NeuralNetworkClass>) afterstate) ) {
                actions.add(right);
                futurePossibleBoards.add(1,
                        (GameBoard<NeuralNetworkClass>) afterstate);
            } else {
                futurePossibleBoards.add(1, null);
            }

            afterstate = computeAfterState(state, up);
            if ( !state.isEqual((GameBoard<NeuralNetworkClass>) afterstate) ) {
                actions.add(up);
                futurePossibleBoards.add(2,
                        (GameBoard<NeuralNetworkClass>) afterstate);
            } else {
                futurePossibleBoards.add(2, null);
            }

            afterstate = computeAfterState(state, down);
            if ( !state.isEqual((GameBoard<NeuralNetworkClass>) afterstate) ) {
                actions.add(down);
                futurePossibleBoards.add(3,
                        (GameBoard<NeuralNetworkClass>) afterstate);
            } else {
                futurePossibleBoards.add(3, null);
            }

            lastInitialStateForPossibleActions = state;
        }
        return actions;
    }

    /**
     *
     * @param afterState
     *
     * @return lista de todos los posibles siguientes turnos desde {@code afterState}
     */
    public List<StateProbability> listAllPossibleNextTurnStateFromAfterstate(
            IState afterState) {
        return ((GameBoard<NeuralNetworkClass>) afterState).
                listAllPossibleNextTurnStateFromAfterstate();
    }

    @Override
    public double normalizeValueToPerceptronOutput(Object value) {
        if ( this.getnTupleSystemConfiguration() != null ) {
            return this.getnTupleSystemConfiguration().
                    normalizeValueToPerceptronOutput(value);
        } else {
            return this.getNeuralNetworkConfiguration().
                    normalizeValueToPerceptronOutput(value);
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for ( int y = 0; y < 4; y++ ) {
            for ( int x = 0; x < 4; x++ ) {
                drawTile(g, getBoard().getTiles()[x + y * 4], x, y);
            }
        }
    }

    @Override
    public void processInput(int keyCode) {
        if ( keyCode == VK_ESCAPE ) {
            resetGame();
        }
        if ( !board.canMove() ) {
            myLoose = true;
        }
        if ( !myWin && !myLoose ) {
            IStatePerceptron afterstate = null;
            switch ( keyCode ) {
                case VK_LEFT: {
                    afterstate = computeAfterState(getBoard(), Action.left);
                    break;
                }
                case VK_RIGHT: {
                    afterstate = computeAfterState(getBoard(), Action.right);
                    break;
                }
                case VK_DOWN: {
                    afterstate = computeAfterState(getBoard(), Action.down);
                    break;
                }
                case VK_UP: {
                    afterstate = computeAfterState(getBoard(), Action.up);
                    break;
                }
                default: {
                    throw new IllegalArgumentException(
                            "Cant use the action keyCode = " + keyCode);
                }
            }
            if ( afterstate != null ) {
                turnNumber++;
                myScore += ((GameBoard<NeuralNetworkClass>) afterstate).
                        getPartialScore();
                board = (GameBoard<NeuralNetworkClass>) computeNextTurnStateFromAfterstate(
                        afterstate);
                if ( getBoard().isAWin() ) {
                    this.myWin = true;
                }
            }
        }
        if ( !myWin && !board.canMove() ) {
            myLoose = true;
        }
        if ( myWin || myLoose ) {
            maxNumber = getBoard().getMaxTileNumberValue();
            maxNumberCode = getBoard().getMaxTileNumberCode();
        }
        if ( repaint ) {
            try {
                repaint();
                if ( this.delayPerMove > 0 ) {
                    sleep(this.delayPerMove);
                }
            } catch ( InterruptedException ex ) {
                Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    /**
     * Reinicia el juego.
     */
    public void resetGame() {
        futurePossibleBoards = new ArrayList<>(4);
        myScore = 0;
        maxNumber = 0;
        maxNumberCode = 0;
        turnNumber = 0;
        myWin = false;
        myLoose = false;
        board = new GameBoard<>(this, tileContainer);
        board.clearBoard(tileContainer);
        board.updateInternalState(false);
        board.addTile(false);
        board.addTile(true);
    }

    private boolean compare(Tile[] line1,
            Tile[] line2) {
        if ( line1 == line2 ) {
            return true;
        } else if ( line1.length != line2.length ) {
            return false;
        }

        for ( int i = 0; i < line1.length; i++ ) {
            if ( line1[i].getCode() != line2[i].getCode() ) {
                return false;
            }
        }

        return true;
    }

    private void drawTile(Graphics g2,
            Tile tile,
            int x,
            int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_NORMALIZE);
        int value = tile.getGameValue();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getBackground());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
        g.setColor(tile.getForeground());
        final int size = value < 100 ? 36 : value < 1_000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, BOLD, size);
        g.setFont(font);
        String s = valueOf(value);
        final FontMetrics fm = getFontMetrics(font);
        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
        if ( value != 0 ) {
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2,
                    yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
        }
        if ( myWin || myLoose ) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(FONT_NAME, BOLD, 48));
            if ( myWin ) {
                g.drawString("You won!", 68, 150);
            }
            if ( myLoose ) {
                g.drawString("Game over!", 50, 130);
                g.drawString("You lose!", 64, 200);
            }
            if ( myWin || myLoose ) {
                g.setFont(new Font(FONT_NAME, PLAIN, 16));
                g.setColor(new Color(128, 128, 128, 128));
                g.drawString("Press ESC to play again", 80, getHeight() - 40);
            }
        }
        g.setFont(new Font(FONT_NAME, PLAIN, 18));
        g.drawString("Score: " + myScore, 200, 365);
    }

    private Tile[] getLine(int index,
            GameBoard<NeuralNetworkClass> board) {
        Tile[] result = new Tile[4];
        for ( int i = 0; i < 4; i++ ) {
            result[i] = board.tileAt(i, index);
        }
        return result;
    }

    private Tile[] mergeLine(Tile[] oldLine,
            GameBoard<NeuralNetworkClass> afterstate) {
        LinkedList<Tile> list = new LinkedList<>();
        for ( int i = 0; i < 4 && !oldLine[i].isEmpty(); i++ ) {
            Tile tile = oldLine[i];
            if ( i < 3 && tile.getCode() == oldLine[i + 1].getCode() ) {
                tile = this.tileContainer.getTile(tile.getCode() + 1);
                afterstate.addPartialScore(tile.getGameValue());
                if ( tile.getGameValue() >= numberToWin ) {
                    afterstate.setToWin();
                }
                if ( tile.getGameValue() > numberToWin ) {
                    afterstate.setToWin();
                }
                i++;
            }
            list.add(tile);
        }
        if ( list.isEmpty() ) {
            return oldLine;
        } else {
            ensureSize(list, 4, tileContainer);
            return list.toArray(new Tile[4]);
        }
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<>();

        for ( int i = 0; i < 4; i++ ) {
            if ( !oldLine[i].isEmpty() ) {
                l.addLast(oldLine[i]);
            }
        }

        if ( l.isEmpty() ) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4, tileContainer);
            for ( int i = 0; i < 4; i++ ) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    private void rotate(int angle,
            GameBoard<NeuralNetworkClass> original) {
        arraycopy(rotateBoardTiles(angle, original.getTiles()), 0, original.
                getTiles(), 0, TILE_NUMBER);
    }

    private void setLine(int index,
            Tile[] re,
            GameBoard<NeuralNetworkClass> board) {
        arraycopy(re, 0, board.getTiles(), index * 4, 4);
    }


}
