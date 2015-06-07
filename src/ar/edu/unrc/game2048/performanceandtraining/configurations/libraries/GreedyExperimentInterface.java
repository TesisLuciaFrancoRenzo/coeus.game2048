/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.StateProbability;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class GreedyExperimentInterface extends INeuralNetworkInterfaceFor2048 {

    /**
     *
     * @param perceptronConfiguration
     */
    public GreedyExperimentInterface(PerceptronConfiguration2048 perceptronConfiguration) {
        super(perceptronConfiguration);
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    @Override
    public void compareNeuralNetworks(File randomFile, File trainedFile) {
    }

    @Override
    public String getLibName() {
        return "Greedy";
    }

    @Override
    public IPerceptronInterface getPerceptronInterface() {
        return null;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
    }

    @Override
    public IsolatedComputation playATurn(Game2048 game, TDLambdaLearning learningMethod) {
        return () -> {
            GameBoard actualBoard = game.getBoard();
            List<Action> bestActions = new ArrayList<>(4);
            double bestReward = -100d;
            ArrayList<Action> possibleActions = game.listAllPossibleActions(actualBoard);
            for ( Action action : possibleActions ) {
                GameBoard afterState = (GameBoard) game.computeAfterState(actualBoard, action);
                List<StateProbability> allPosibleNextStates = game.listAllPossibleNextTurnStateFromAfterstate(afterState);
                Double reward = allPosibleNextStates.stream().mapToDouble((nextStateProb) -> {
                    if ( ((GameBoard) nextStateProb.getNextTurnState()).isAWin() || ((GameBoard) nextStateProb.getNextTurnState()).isFull() ) {
                        return ((GameBoard) nextStateProb.getNextTurnState()).getPartialScore() * 4 * nextStateProb.getProbability(); //TODO revisar que no cause problemas
                    } else {
                        ArrayList<Action> possibleNextActions = game.listAllPossibleActions(actualBoard);
                        return possibleNextActions.stream().mapToDouble((nextAction) -> {
                            GameBoard nextAfterState = (GameBoard) game.computeAfterState(nextStateProb.getNextTurnState(), nextAction);
                            return nextAfterState.getPartialScore();
                        }).sum() * nextStateProb.getProbability();
                    }
                }).sum() + afterState.getPartialScore();
                if ( reward == bestReward ) {
                    bestActions.add(action);
                } else if ( reward > bestReward ) {
                    bestReward = reward;
                    bestActions.clear();
                    bestActions.add(action);
                }
            }

            if ( !bestActions.isEmpty() ) {
                Action bestAction = bestActions.get(TDLambdaLearning.randomBetween(0, bestActions.size() - 1));
                switch ( bestAction ) {
                    case left: {
                        game.processInput(VK_LEFT);
                        break;
                    }
                    case right: {
                        game.processInput(VK_RIGHT);
                        break;
                    }
                    case down: {
                        game.processInput(VK_DOWN);
                        break;
                    }
                    case up: {
                        game.processInput(VK_UP);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("no se encontro mejor accion.");
                    }
                }
            } else {
                throw new IllegalArgumentException("no se encontro mejor accion.");
            }
            return null;
        };

    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
