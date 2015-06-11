/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
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
 * @param <NeuralNetworkClass>
 */
public abstract class INeuralNetworkInterfaceFor2048<NeuralNetworkClass> implements Cloneable {

    private NTupleConfiguration2048 nTupleConfiguration;

    private PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration;

    /**
     *
     * @param perceptronConfiguration
     */
    public INeuralNetworkInterfaceFor2048(PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     *
     * @param nTupleConfiguration
     */
    public INeuralNetworkInterfaceFor2048(NTupleConfiguration2048 nTupleConfiguration) {
        this.nTupleConfiguration = nTupleConfiguration;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param randomFile
     * @param trainedFile
     */
    public abstract void compareNeuralNetworks(File randomFile, File trainedFile);

    /**
     *
     * @return
     */
    public abstract String getLibName();

    /**
     * @return the nTupleConfiguration
     */
    public NTupleConfiguration2048 getNTupleConfiguration() {
        return nTupleConfiguration;
    }

    public void setNTupleConfiguration(NTupleConfiguration2048 nTupleConfiguration) {
        this.nTupleConfiguration = nTupleConfiguration;
    }

    /**
     * @return the perceptronConfiguration
     */
    public PerceptronConfiguration2048<NeuralNetworkClass> getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration the perceptronConfiguration to set
     */
    public void setPerceptronConfiguration(PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     *
     * @return
     */
    public abstract IPerceptronInterface getPerceptronInterface();

    /**
     * @param perceptronFile       <p>
     * @param randomizedIfNotExist <p>
     * @throws Exception
     */
    public abstract void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception;

    /**
     *
     * @param game
     * @param learningMethod metodo usado para entrenar y evaluar, o null si se
     *                       utiliza una IA al azar
     * <p>
     * @return
     */
    public IsolatedComputation playATurn(Game2048<NeuralNetworkClass> game, TDLambdaLearning learningMethod) {
        return () -> {
            // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
            // accion basada en las predicciones del problema
            Action bestAction = (Action) learningMethod.computeBestPossibleAction(game, game.getBoard()).compute();

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
            }
            return null;
        };
    }

    /**
     *
     * @param perceptronFile <p>
     * @throws Exception
     */
    public abstract void savePerceptron(File perceptronFile) throws Exception;

}
