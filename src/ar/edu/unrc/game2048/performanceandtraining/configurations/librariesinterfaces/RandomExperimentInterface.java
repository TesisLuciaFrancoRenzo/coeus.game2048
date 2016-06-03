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

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.tdlearning.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.learning.TDLambdaLearning;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.io.File;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class RandomExperimentInterface extends INeuralNetworkInterfaceFor2048 {

    /**
     *
     * @param perceptronConfiguration
     */
    public RandomExperimentInterface(PerceptronConfiguration2048 perceptronConfiguration) {
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
        return "Random";
    }

    @Override
    public IPerceptronInterface getPerceptronInterface() {
        return null;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist, boolean createPerceptronFile) throws Exception {
    }

    @Override
    public void playATurn(Game2048 game, TDLambdaLearning learningMethod) {
        switch ( TDLambdaLearning.randomBetween(1, 4) ) {
            case 1: {
                game.processInput(VK_LEFT);
                break;
            }
            case 2: {
                game.processInput(VK_RIGHT);
                break;
            }
            case 3: {
                game.processInput(VK_DOWN);
                break;
            }
            case 4: {
                game.processInput(VK_UP);
                break;
            }
            default: {
                throw new IllegalStateException("El numero al azar elegido debe ir entre 1 y 4");
            }
        }
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
