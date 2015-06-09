/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
