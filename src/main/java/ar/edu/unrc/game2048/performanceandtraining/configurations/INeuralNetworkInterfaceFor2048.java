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

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.NeuralNetworkConfiguration2048;

import java.io.File;
import java.util.List;

import static java.awt.event.KeyEvent.*;

/**
 * Representación de la configuración de las redes neuronales, ya sea NTupla o Perceptrón.
 *
 * @param <NeuralNetworkClass>
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class INeuralNetworkInterfaceFor2048<NeuralNetworkClass>
        implements Cloneable {

    /**
     *
     */
    protected NeuralNetworkConfiguration2048<NeuralNetworkClass> perceptronConfiguration;
    private   NTupleConfiguration2048                            nTupleConfiguration;

    /**
     * @param perceptronConfiguration configuración del Perceptrón.
     */
    public
    INeuralNetworkInterfaceFor2048(
            NeuralNetworkConfiguration2048<NeuralNetworkClass> perceptronConfiguration
    ) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     * @param nTupleConfiguration configuración de la NTupla.
     */
    public
    INeuralNetworkInterfaceFor2048(
            NTupleConfiguration2048 nTupleConfiguration
    ) {
        this.nTupleConfiguration = nTupleConfiguration;
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

    /**
     * @return nombre de la librería utilizada para la red neuronal.
     */
    public abstract
    String getLibName();

    /**
     * @return configuración de la NTupla.
     */
    public
    NTupleConfiguration2048 getNTupleConfiguration() {
        return nTupleConfiguration;
    }

    /**
     * @param nTupleConfiguration configuración de la NTupla.
     */
    public
    void setNTupleConfiguration(
            NTupleConfiguration2048 nTupleConfiguration
    ) {
        this.nTupleConfiguration = nTupleConfiguration;
    }

    /**
     * @return interfaz a la red neuronal.
     */
    public abstract
    INeuralNetworkInterface getNeuralNetworkInterface();

    /**
     * @return configuración del Perceptrón.
     */
    public
    NeuralNetworkConfiguration2048<NeuralNetworkClass> getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration configuración del Perceptrón.
     */
    public
    void setPerceptronConfiguration(
            NeuralNetworkConfiguration2048<NeuralNetworkClass> perceptronConfiguration
    ) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     * Carga desde un archivo una red neuronal, o crea una nueva (con valores al azar o con valores por defecto).
     *
     * @param neuralNetworkFile    archivo con la red neuronal.
     * @param randomizedIfNotExist true si debe inicializar al azar los pesos y bias al crear una nueva red neuronal.
     * @param createFile           true si debe crear una nueva red neuronal.
     *
     * @throws Exception
     */
    public abstract
    void loadOrCreatePerceptron(
            File neuralNetworkFile,
            boolean randomizedIfNotExist,
            boolean createFile
    )
            throws Exception;

    /**
     * Juega un turno de una partida, utilizando la red neuronal.
     *
     * @param game           juego a jugar
     * @param learningMethod método usado para entrenar y evaluar, o null si se utiliza una IA al azar
     */
    public
    void playATurn(
            Game2048<NeuralNetworkClass> game,
            TDLambdaLearning learningMethod
    ) {
        // evaluamos cada accion aplicada al estado inicial y elegimos la mejor
        // accion basada en las predicciones del problema
        List<IAction> possibleActions = game.listAllPossibleActions(game.
                                                                                getBoard());
        Action bestAction = (Action) TDLambdaLearning.computeBestPossibleAction(
                game,
                ELearningStyle.afterState,
                game.getBoard(),
                possibleActions,
                null,
                learningMethod.isComputeParallelBestPossibleAction(),
                learningMethod.getStatisticsBestPossibleActionTimes()
        );

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
                throw new IllegalStateException("Mejor accion no reconocida");
            }
        }
    }

    /**
     * Guarda la red neuronal en un archivo
     *
     * @param neuralNetworkFile red neuronal a salvar en archivo.
     *
     * @throws Exception
     */
    public abstract
    void saveNeuralNetwork(File neuralNetworkFile)
            throws Exception;

}
