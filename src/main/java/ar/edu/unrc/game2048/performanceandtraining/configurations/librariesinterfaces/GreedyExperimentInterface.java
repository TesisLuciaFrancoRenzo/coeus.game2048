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
package ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.NeuralNetworkConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.greedy.StateProbability;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

/**
 * Interfaz de experimentos entre la IA greedy y Coeus
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@SuppressWarnings("unchecked")
public
class GreedyExperimentInterface
        extends INeuralNetworkInterfaceFor2048 {

    /**
     * @param perceptronConfiguration configuración
     */
    public
    GreedyExperimentInterface(
            NeuralNetworkConfiguration2048 perceptronConfiguration
    ) {
        super(perceptronConfiguration);
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    Object clone()
            throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public
    String getLibName() {
        return "Greedy";
    }

    @Override
    public
    INeuralNetworkInterface getNeuralNetworkInterface() {
        return null;
    }

    @Override
    public
    void loadOrCreatePerceptron(
            File perceptronFile,
            boolean randomizedIfNotExist,
            boolean createPerceptronFile
    )
            throws Exception {
    }

    @Override
    public
    void playATurn(
            Game2048 game,
            TDLambdaLearning learningMethod
    ) {
        GameBoard         actualBoard     = game.getBoard();
        List<Action>      bestActions     = new ArrayList<>(4);
        double            bestReward      = -100d;
        ArrayList<Action> possibleActions = (ArrayList<Action>) game.listAllPossibleActions(actualBoard);
        for (Action action : possibleActions) {
            GameBoard              afterState            = (GameBoard) game.computeAfterState(actualBoard, action);
            List<StateProbability> allPossibleNextStates = game.listAllPossibleNextTurnStateFromAfterState(afterState);
            Double reward = allPossibleNextStates.stream().mapToDouble((nextStateProb) -> {
                if (((GameBoard) nextStateProb.getNextTurnState()).isAWin() || ((GameBoard) nextStateProb.getNextTurnState()).isFull()) {
                    return ((GameBoard) nextStateProb.getNextTurnState()).getPartialScore() * 4 * nextStateProb.getProbability();
                } else {
                    ArrayList<Action> possibleNextActions = game.listAllPossibleActions(actualBoard);
                    return possibleNextActions.stream().mapToDouble((nextAction) -> {
                        GameBoard nextAfterState = (GameBoard) game.computeAfterState(nextStateProb.getNextTurnState(), nextAction);
                        return nextAfterState.getPartialScore();
                    }).sum() * nextStateProb.getProbability();
                }
            }).sum() + afterState.getPartialScore();
            if (reward == bestReward) {
                bestActions.add(action);
            } else if (reward > bestReward) {
                bestReward = reward;
                bestActions.clear();
                bestActions.add(action);
            }
        }

        if (!bestActions.isEmpty()) {
            Action bestAction = bestActions.get(TDLambdaLearning.randomBetween(0, bestActions.size() - 1));
            switch (bestAction) {
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
                    throw new IllegalArgumentException("no se encontró mejor acción.");
                }
            }
        } else {
            throw new IllegalArgumentException("no se encontró mejor acción.");
        }

    }

    @Override
    public
    void saveNeuralNetwork(File perceptronFile)
            throws Exception {
    }

}
