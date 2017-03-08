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
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.experiments.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.game2048.experiments.configurations.NTupleConfiguration2048;

import java.io.File;
import java.io.OutputStream;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class NTupleExperimentInterface
        extends INeuralNetworkInterfaceFor2048 {

    /**
     * Interfaz de experimentos entre la NTuplas y Coeus
     *
     * @param perceptronConfiguration configuración
     */
    public
    NTupleExperimentInterface(
            final NTupleConfiguration2048 perceptronConfiguration
    ) {
        super(perceptronConfiguration);
    }

    /**
     * @return @throws CloneNotSupportedException
     */
    @Override
    public
    NTupleExperimentInterface clone()
            throws CloneNotSupportedException {
        return (NTupleExperimentInterface) super.clone();
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
    NTupleSystem initializeNTupleSystem( final boolean randomized ) {
        final NTupleSystem nTupleSystem = new NTupleSystem(getNTupleConfiguration().getAllSamplePointPossibleValues(),
                getNTupleConfiguration().getNTuplesLength(),
                getNTupleConfiguration().getActivationFunction(),
                getNTupleConfiguration().getDerivedActivationFunction(),
                getNTupleConfiguration().isConcurrency());
        if ( randomized ) {
            nTupleSystem.randomize();
        }
        return nTupleSystem;
    }

    @Override
    public
    void loadOrCreatePerceptron(
            final File perceptronFile,
            final boolean randomizedIfNotExist,
            final boolean createPerceptronFile
    )
            throws Exception {
        getNTupleConfiguration().setNTupleSystem(initializeNTupleSystem(randomizedIfNotExist));
        if ( createPerceptronFile ) {
            if ( perceptronFile.exists() ) {
                //si el archivo existe, lo cargamos como perceptron entrenado al juego
                getNTupleConfiguration().getNTupleSystem().load(perceptronFile);
            } else {
                //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
                getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
            }
        }
    }

    @Override
    public
    void saveNeuralNetwork( final File perceptronFile )
            throws Exception {
        getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
    }

    @Override
    public
    void saveNeuralNetwork( final OutputStream outputStream )
            throws Exception {
        getNTupleConfiguration().getNTupleSystem().save(outputStream);
    }

}
