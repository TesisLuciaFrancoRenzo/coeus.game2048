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
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.game2048.EncogConfiguration2048;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.experiments.configurations.INeuralNetworkInterfaceFor2048;

import java.io.File;
import java.io.OutputStream;

/**
 * Interfaz de experimentos entre IA random y Coeus
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class RandomExperimentInterface
        extends INeuralNetworkInterfaceFor2048 {

    /**
     * @param perceptronConfiguration configuración
     */
    @SuppressWarnings( "unchecked" )
    public
    RandomExperimentInterface(
            EncogConfiguration2048 perceptronConfiguration
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
        return "Random";
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
        switch ( TDLambdaLearning.randomBetween(1, 4) ) {
            case 1: {
                game.getBoard().moveLeft();
                break;
            }
            case 2: {
                game.getBoard().moveRight();
                break;
            }
            case 3: {
                game.getBoard().canMoveDown();
                break;
            }
            case 4: {
                game.getBoard().moveUp();
                break;
            }
            default: {
                throw new IllegalStateException("Mejor acción no reconocida");
            }
        }
        game.setCurrentState(game.computeNextTurnStateFromAfterState(game.getBoard()));
    }

    @Override
    public
    void saveNeuralNetwork( File perceptronFile )
            throws Exception {
    }

    @Override
    public
    void saveNeuralNetwork( OutputStream outputStream )
            throws Exception {

    }

}
