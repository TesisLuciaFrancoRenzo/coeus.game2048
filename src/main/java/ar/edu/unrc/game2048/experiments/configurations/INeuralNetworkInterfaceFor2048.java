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
package ar.edu.unrc.game2048.experiments.configurations;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.interfaces.IAction;
import ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.game2048.Action;
import ar.edu.unrc.game2048.Game2048;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

/**
 * Representación de la configuración de las redes neuronales, ya sea NTupla o Perceptrón.
 *
 * @param
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class INeuralNetworkInterfaceFor2048
        implements Cloneable {

    private final Random                  random                  = new Random();
    /**
     *
     */
    protected     EncogConfiguration2048  perceptronConfiguration = null;
    private       NTupleConfiguration2048 nTupleConfiguration     = null;

    /**
     * @param perceptronConfiguration configuración del Perceptrón.
     */
    protected
    INeuralNetworkInterfaceFor2048( final EncogConfiguration2048 perceptronConfiguration ) {
        super();
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     * @param nTupleConfiguration configuración de la NTupla.
     */
    protected
    INeuralNetworkInterfaceFor2048( final NTupleConfiguration2048 nTupleConfiguration ) {
        super();
        this.nTupleConfiguration = nTupleConfiguration;
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    INeuralNetworkInterfaceFor2048 clone()
            throws CloneNotSupportedException {
        return (INeuralNetworkInterfaceFor2048) super.clone();
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
    void setNTupleConfiguration( final NTupleConfiguration2048 nTupleConfiguration ) {
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
    EncogConfiguration2048 getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration configuración del Perceptrón.
     */
    public
    void setPerceptronConfiguration( final EncogConfiguration2048 perceptronConfiguration ) {
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
            final Game2048 game,
            final TDLambdaLearning learningMethod
    ) {
        // evaluamos cada acción aplicada al estado inicial y elegimos la mejor
        // acción basada en las predicciones del problema
        final List< IAction > possibleActions = game.listAllPossibleActions(game.getBoard());
        final Action bestAction = (Action) TDLambdaLearning.computeBestPossibleAction(game, ELearningStyle.AFTER_STATE,
                game.getBoard(), possibleActions, null, learningMethod.isComputeParallelBestPossibleAction(), random, null).getAction();
        switch ( bestAction ) {
            case LEFT:
                game.getBoard().moveLeft();
                break;
            case RIGHT:
                game.getBoard().moveRight();
                break;
            case DOWN:
                game.getBoard().moveDown();
                break;
            case UP:
                game.getBoard().moveUp();
                break;
            default:
                throw new IllegalStateException("Mejor acción no reconocida");
        }
        game.setCurrentState(game.computeNextTurnStateFromAfterState(game.getBoard()));
        if ( game.isPrintHistory() ) {
            game.getHistoryLog().append("M=").append(bestAction).append('\n');
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
    void saveNeuralNetwork( final File neuralNetworkFile )
            throws Exception;


    /**
     * Guarda la red neuronal en un archivo
     *
     * @throws Exception
     */
    public abstract
    void saveNeuralNetwork( final OutputStream outputStream )
            throws Exception;

}
