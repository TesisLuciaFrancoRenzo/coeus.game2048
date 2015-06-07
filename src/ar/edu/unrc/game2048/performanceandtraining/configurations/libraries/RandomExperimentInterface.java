/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IsolatedComputation;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
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
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
    }

    @Override
    public IsolatedComputation playATurn(Game2048 game, TDLambdaLearning learningMethod) {
        return () -> {
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
            }
            return null;
        };
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
