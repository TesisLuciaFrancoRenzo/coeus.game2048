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
package ar.edu.unrc.game2048.performanceandtraining.configurations.libraries;

import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.INeuralNetworkInterfaceFor2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTupleExperimentInterface extends INeuralNetworkInterfaceFor2048<BasicNetwork> {

    /**
     *
     * @param perceptronConfiguration
     */
    public NTupleExperimentInterface(NTupleConfiguration2048 perceptronConfiguration) {
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
        return "NTuple";
    }

    @Override
    public IPerceptronInterface getPerceptronInterface() {
        return null;
    }

    /**
     *
     * @param randomized <p>
     * @return
     */
    public NTupleSystem initializeEncogPerceptron(boolean randomized) {
        NTupleSystem perceptron = new NTupleSystem(this.getNTupleConfiguration().allSamplePointStates, this.getNTupleConfiguration().nTuplesLenght, this.getNTupleConfiguration().activationFunction, this.getNTupleConfiguration().derivatedActivationFunction);
        if ( randomized ) {
            perceptron.reset();
        }
        return perceptron;
    }

    @Override
    public void loadOrCreatePerceptron(File perceptronFile, boolean randomizedIfNotExist) throws Exception {
        this.getNTupleConfiguration().setNTupleSystem(initializeEncogPerceptron(randomizedIfNotExist));
        if ( !perceptronFile.exists() ) {
            //Si el archivo no existe, creamos un perceptron nuevo inicializado al azar
            getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
        } else {
            //si el archivo existe, lo cargamos como perceptron entrenado al juego
            getNTupleConfiguration().getNTupleSystem().load(perceptronFile);
        }
    }

    @Override
    public void savePerceptron(File perceptronFile) throws Exception {
        this.getNTupleConfiguration().getNTupleSystem().save(perceptronFile);
    }

}
