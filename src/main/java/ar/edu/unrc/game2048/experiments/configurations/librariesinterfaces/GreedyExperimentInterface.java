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
package ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.experiments.configurations.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.game2048.experiments.statistics.greedy.GreedyStateProbability;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Interfaz de experimentos entre la IA greedy y Coeus
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GreedyExperimentInterface
        extends INeuralNetworkInterfaceFor2048 {

    /**
     * @param perceptronConfiguration configuración
     */
    public
    GreedyExperimentInterface(
            final EncogConfiguration2048 perceptronConfiguration
    ) {
        super(perceptronConfiguration);
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    GreedyExperimentInterface clone()
            throws CloneNotSupportedException {
        return (GreedyExperimentInterface) super.clone();
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
            final File perceptronFile,
            final boolean randomizedIfNotExist,
            final boolean createPerceptronFile
    )
            throws Exception {
    }

    @Override
    public
    void playATurn(
            final Game2048 game,
            final TDLambdaLearning learningMethod
    ) {
        final GameBoard           actualBoard     = game.getBoard();
        final List< Action >      bestActions     = new ArrayList<>(4);
        double                    bestReward      = -100.0d;
        final Iterable< IAction > possibleActions = game.listAllPossibleActions(actualBoard);
        for ( final IAction action : possibleActions ) {
            final GameBoard                      afterState            = (GameBoard) game.computeAfterState(actualBoard, action);
            final List< GreedyStateProbability > allPossibleNextStates = game.listAllPossibleNextTurnStateFromAfterState(afterState);
            final Double reward = allPossibleNextStates.stream().mapToDouble(( nextStateProb ) -> {
                if ( ( (GameBoard) nextStateProb.getNextTurnState() ).isAWin() || ( (GameBoard) nextStateProb.getNextTurnState() ).isFull() ) {
                    return ( (GameBoard) nextStateProb.getNextTurnState() ).getPartialScore() * 4 * nextStateProb.getProbability();
                } else {
                    Collection< IAction > possibleNextActions = game.listAllPossibleActions(actualBoard);
                    return possibleNextActions.stream().mapToDouble(( nextAction ) -> {
                        GameBoard nextAfterState = (GameBoard) game.computeAfterState(nextStateProb.getNextTurnState(), nextAction);
                        return nextAfterState.getPartialScore();
                    }).sum() * nextStateProb.getProbability();
                }
            }).sum() + afterState.getPartialScore();
            if ( reward == bestReward ) {
                bestActions.add((Action) action);
            } else if ( reward > bestReward ) {
                bestReward = reward;
                bestActions.clear();
                bestActions.add((Action) action);
            }
        }

        if ( bestActions.isEmpty() ) {
            throw new IllegalArgumentException("no se encontró mejor acción.");
        } else {
            final Action bestAction = bestActions.get(TDLambdaLearning.randomBetween(0, bestActions.size() - 1));
            switch ( bestAction ) {
                case left:
                    game.getBoard().moveLeft();
                    break;
                case right:
                    game.getBoard().moveRight();
                    break;
                case down:
                    game.getBoard().canMoveDown();
                    break;
                case up:
                    game.getBoard().moveUp();
                    break;
                default:
                    throw new IllegalStateException("Mejor acción no reconocida");
            }
            game.setCurrentState(game.computeNextTurnStateFromAfterState(game.getBoard()));
        }

    }

    @Override
    public
    void saveNeuralNetwork( final File perceptronFile )
            throws Exception {
    }

    @Override
    public
    void saveNeuralNetwork( final OutputStream outputStream )
            throws Exception {

    }

}
