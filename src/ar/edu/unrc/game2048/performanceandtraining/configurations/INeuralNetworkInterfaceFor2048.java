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
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.ELearningStyle;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.io.File;
import java.util.List;

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

    /**
     *
     * @param nTupleConfiguration
     */
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
     * @param createFile
     *
     * @throws Exception
     */
    public abstract void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist, boolean createFile) throws Exception;

    /**
     *
     * @param game
     * @param learningMethod metodo usado para entrenar y evaluar, o null si se
     *                       utiliza una IA al azar
     * <p>
     */
    public void playATurn(Game2048<NeuralNetworkClass> game, TDLambdaLearning learningMethod) {
        // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
        // accion basada en las predicciones del problema
        List<IAction> possibleActions = game.listAllPossibleActions(game.getBoard());
        Action bestAction = (Action) TDLambdaLearning.computeBestPossibleAction(
                game,
                ELearningStyle.afterState,
                game.getBoard(),
                possibleActions,
                null,
                learningMethod.isComputeParallelBestPossibleAction(),
                learningMethod.getBestPossibleActionTimes());

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
                throw new IllegalStateException("Mejor accion no reconocida");
            }
        }
    }

    /**
     *
     * @param perceptronFile <p>
     * @throws Exception
     */
    public abstract void savePerceptron(File perceptronFile) throws Exception;

}
