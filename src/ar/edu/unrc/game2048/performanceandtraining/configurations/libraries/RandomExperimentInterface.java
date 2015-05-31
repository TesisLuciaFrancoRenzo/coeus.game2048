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
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.io.File;
import static java.lang.Math.random;

/**
 *
 * @author Franco
 */
public class RandomExperimentInterface extends INeuralNetworkInterfaceFor2048 {

    /**
     * calcula un numero al azar entre los limites dados, inclusive estos.
     * <p>
     * @param a numero desde
     * @param b numero hasta
     * <p>
     * @return aleatorio entre a y b
     * <p>
     */
    public static int randomBetween(int a, int b) {
        if ( a > b ) {
            throw new IllegalArgumentException("error: b debe ser mayor que a");
        }
        int tirada = a + (int) ((double) (b - a + 1) * random());
        return tirada;
    }

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
    public void playATurn(Game2048 game, TDLambdaLearning learningMethod) {
        switch ( randomBetween(1, 4) ) {
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
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
    }

}
