/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franco
 */
public abstract class VisualExperiment<NeuralNetworkClass> {

    private int delayPerMove = 200;
    private String experimentName;
    private boolean forceStop;
    private PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration;
    private String perceptronName;
    private int tileToWin;
    protected LearningExperiment<NeuralNetworkClass> learningExperiment;

    public VisualExperiment(LearningExperiment<NeuralNetworkClass> learningExperiment) {
        this.learningExperiment = learningExperiment;
    }

    public String getPerceptronFileName() {
        return perceptronName;
    }

    public void start(String experimentPath, int delayPerMove) {
        forceStop = false;
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        try {
            if ( learningExperiment != null ) {
                learningExperiment.initialize();
                this.tileToWin = learningExperiment.getTileToWin();
                this.experimentName = learningExperiment.getExperimentName();
                this.perceptronConfiguration = learningExperiment.getNeuralNetworkInterfaceFor2048().getPerceptronConfiguration();
            } else {
                this.perceptronConfiguration = null;
            }
            initializeVisual();
            run(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(VisualExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    protected void setPerceptronConfiguration(PerceptronConfiguration2048<NeuralNetworkClass> perceptronConfiguration) {
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
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment neuralNetworkInterfaceFor2048;</li>
     * <li>private String perceptronName;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser
     * modificadas:
     * <ul>
     * <li>private int tileToWin;</li>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration;</li>
     * </ul>
     * <p>
     * @throws Exception
     */
    protected abstract void initializeVisual() throws Exception;

    protected void run(String experimentPath, int delayPerMove) throws Exception {
        System.out.println("Starting " + this.getPerceptronName() + " Visual");
        boolean visible = false;
        if ( delayPerMove > 0 ) {
            visible = true;
        }
        Game2048<NeuralNetworkClass> game = new Game2048<>(perceptronConfiguration, tileToWin, visible, delayPerMove, false);
        if ( perceptronConfiguration != null ) {
            //cargamos la red neuronal entrenada
            String dirPath = experimentPath
                    + this.learningExperiment.getNeuralNetworkInterfaceFor2048().getLibName() + File.separator
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
            this.learningExperiment.getNeuralNetworkInterfaceFor2048().loadOrCreatePerceptron(perceptronFile, true);
        }
        while ( !game.iLoose() && !game.iWin() && !forceStop ) {
            this.learningExperiment.getNeuralNetworkInterfaceFor2048().playATurn(game, learningExperiment.getLearningAlgorithm());
        }
        if ( !forceStop ) {
            sleep(5_000);
        }
        game.dispose();
        System.out.println("Finished");
    }

}
