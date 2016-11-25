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
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import org.encog.neural.networks.BasicNetwork;

import java.io.File;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NTupleExperimentInterface
        extends INeuralNetworkInterfaceFor2048<BasicNetwork> {

    /**
     * Interfaz de experimentos entre la NTuplas y Coeus
     *
     * @param perceptronConfiguration configuración
     */
    public
    NTupleExperimentInterface(
            NTupleConfiguration2048 perceptronConfiguration
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
        return "NTuple";
    }

    @Override
    public
    INeuralNetworkInterface getNeuralNetworkInterface() {
        return null;
    }

    /**
     * Inicializa NTupla, con valores de pesos al azar, o con 0.
     *
     * @param randomized true si se inicializan pesos con valores al azar.
     *
     * @return una NTupla inicializada.
     */
    public
    NTupleSystem initializeNTupleSystem(boolean randomized) {
        NTupleSystem nTupleSystem = new NTupleSystem(
                getNTupleConfiguration().getAllSamplePointPossibleValues(),
                getNTupleConfiguration().getNTuplesLength(),
                getNTupleConfiguration().getActivationFunction(),
                getNTupleConfiguration().getDerivedActivationFunction(),
                getNTupleConfiguration().isConcurrency()
        );
        if (randomized) {
            nTupleSystem.randomize();
        }
        return nTupleSystem;
    }

    @Override
    public
    void loadOrCreatePerceptron(
            File perceptronFile,
            boolean randomizedIfNotExist,
            boolean createPerceptronFile
    )
            throws Exception {
        getNTupleConfiguration().setNTupleSystem(initializeNTupleSystem(randomizedIfNotExist));
        if (createPerceptronFile) {
            if (!perceptronFile.exists()) {
                //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
                getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
            } else {
                //si el archivo existe, lo cargamos como perceptron entrenado al juego
                getNTupleConfiguration().getNTupleSystem().load(perceptronFile);
            }
        }
    }

    @Override
    public
    void saveNeuralNetwork(File perceptronFile)
            throws Exception {
        getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
    }

}
