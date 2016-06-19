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

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class VisualExperiment<NeuralNetworkClass> {

    private int delayPerMove = 200;
    private String experimentName;
    private boolean forceStop;
    private NTupleConfiguration2048 nTupleConfiuguration;
    private PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration;
    private String perceptronName;
    private int tileToWin;

    /**
     *
     */
    protected LearningExperiment<NeuralNetworkClass> learningExperiment;

    /**
     *
     * @param learningExperiment
     */
    public VisualExperiment(
            LearningExperiment<NeuralNetworkClass> learningExperiment) {
        this.learningExperiment = learningExperiment;
    }

    /**
     *
     * @return
     */
    public String getPerceptronFileName() {
        return perceptronName;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove
     * @param createPerceptronFile
     */
    public void start(String experimentPath,
            int delayPerMove,
            boolean createPerceptronFile) {
        forceStop = false;
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException(
                    "experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        try {
            if ( learningExperiment != null ) {
                learningExperiment.initialize();
                this.tileToWin = learningExperiment.getTileToWinForTraining();
                this.experimentName = learningExperiment.getExperimentName();
                this.perceptronConfiguration = learningExperiment.
                        getNeuralNetworkInterfaceFor2048().
                        getPerceptronConfiguration();
                this.setnTupleConfiuguration(learningExperiment.
                        getNeuralNetworkInterfaceFor2048().
                        getNTupleConfiguration());
            } else {
                this.perceptronConfiguration = null;
            }
            initializeVisual();
            run(experimentPath, delayPerMove, createPerceptronFile);
        } catch ( Exception ex ) {
            Logger.getLogger(VisualExperiment.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     *
     */
    public void stop() {
        forceStop = true;
    }

    /**
     * @return the delayPerMove
     */
    protected int getDelayPerMove() {
        return delayPerMove;
    }

    /**
     * @param delayPerMove the delayPerMove to set
     */
    protected void setDelayPerMove(int delayPerMove) {
        this.delayPerMove = delayPerMove;
    }

    /**
     * @return the experimentName
     */
    protected String getExperimentName() {
        return experimentName;
    }

    /**
     * @param experimentName the experimentName to set
     */
    protected void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * @return the perceptronConfiguration
     */
    protected PerceptronConfiguration2048<NeuralNetworkClass> getPerceptronConfiguration() {
        return perceptronConfiguration;
    }

    /**
     * @param perceptronConfiguration the perceptronConfiguration to set
     */
    protected void setPerceptronConfiguration(
            PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration) {
        this.perceptronConfiguration = perceptronConfiguration;
    }

    /**
     * @return the perceptronName
     */
    protected String getPerceptronName() {
        return perceptronName;
    }

    /**
     * @param perceptronName the perceptronName to set
     */
    protected void setPerceptronName(String perceptronName) {
        this.perceptronName = perceptronName;
    }

    /**
     * @return the tileToWin
     */
    protected int getTileToWin() {
        return tileToWin;
    }

    /**
     * @param tileToWin the tileToWin to set
     */
    protected void setTileToWin(int tileToWin) {
        this.tileToWin = tileToWin;
    }

    /**
     * @return the nTupleConfiuguration
     */
    protected NTupleConfiguration2048 getnTupleConfiuguration() {
        return nTupleConfiuguration;
    }

    /**
     * @param nTupleConfiuguration the nTupleConfiuguration to set
     */
    protected void setnTupleConfiuguration(
            NTupleConfiguration2048 nTupleConfiuguration) {
        this.nTupleConfiuguration = nTupleConfiuguration;
    }

    /**
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment neuralNetworkInterfaceFor2048;</li>
     * <li>private String perceptronName;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser modificadas:
     * <ul>
     * <li>private int tileToWin;</li>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration;</li>
     * </ul>
     * <p>
     * @throws Exception
     */
    protected abstract void initializeVisual() throws Exception;

    /**
     *
     * @param experimentPath
     * @param delayPerMove         <p>
     * @param createPerceptronFile
     *
     * @throws Exception
     */
    protected void run(String experimentPath,
            int delayPerMove,
            boolean createPerceptronFile) throws Exception {
        System.out.println("Starting " + this.getPerceptronName() + " Visual");
        Game2048<NeuralNetworkClass> game = new Game2048<>(
                perceptronConfiguration, nTupleConfiuguration, tileToWin,
                delayPerMove);
        if ( perceptronConfiguration != null || nTupleConfiuguration != null ) {
            //cargamos la red neuronal entrenada
            String dirPath = experimentPath
                    + this.learningExperiment.getNeuralNetworkInterfaceFor2048().
                    getLibName() + File.separator
                    + experimentName + File.separator;
            File dirPathFile = new File(dirPath);
            if ( !dirPathFile.exists() ) {
                dirPathFile.mkdirs();
            }
            String filePath = dirPath + perceptronName;
            File perceptronFile = new File(filePath + ".ser");
            if ( !perceptronFile.exists() ) {
                throw new IllegalArgumentException("perceptron file must exists");
            }
            this.learningExperiment.getNeuralNetworkInterfaceFor2048().
                    loadOrCreatePerceptron(perceptronFile, true,
                            createPerceptronFile);
        }
        while ( !game.iLoose() && !game.iWin() && !forceStop ) {
            this.learningExperiment.getNeuralNetworkInterfaceFor2048().
                    playATurn(game, learningExperiment.getLearningAlgorithm());
        }
        if ( !forceStop ) {
            sleep(5_000);
        }
        game.dispose();
        System.out.println("Finished");
    }

}
