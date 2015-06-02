/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.random;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.RandomExperimentInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author Franco
 */
public class Experiment_00 extends LearningExperiment<BasicNetwork> {

    /**
     *
     * @param args <p>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath
                    = ".." + File.separator
                    + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }
        LearningExperiment experiment = new Experiment_00();
        experiment.setStatisticsOnly(true);
        experiment.createLogs(false);
        experiment.setTileToWin(2_048);
        //para calcualr estadisticas
        experiment.setGamesToPlayPerThreadForStatistics(1_000);
        experiment.setSimulationsForStatistics(10);

        experiment.start(filePath, 0);
    }

    @Override
    public void initialize() throws Exception {
        this.setExperimentName("Experiment_00");
        this.setPerceptronName(this.getExperimentName());
        this.setNeuralNetworkInterfaceFor2048(new RandomExperimentInterface(null));
    }

    /**
     *
     * @param perceptronInterface
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return null;
    }

}
