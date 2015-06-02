/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.BoardScoreCustomNormalization;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author Franco
 */
public class Experiment_02 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_02();

//        boolean statistics = true;
        boolean statistics = false;
        experiment.setLearningRateAdaptationToAnnealing(500_000);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setMomentum(0.8);
        experiment.setGamesToPlay(10_000);
        experiment.setLastGamePlayedNumber(0); //recordar AJUSTAR ESTE VALOR
        experiment.setSaveEvery(500);
        experiment.setInitializePerceptronRandomized(true);

        experiment.createLogs(false);

        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(10);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticForRandom(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }

        experiment.start(filePath, 0);
    }

    @Override
    public void initialize() throws Exception {
        this.setTileToWin(2_048);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_02");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new BoardScoreCustomNormalization<>();
        //   config.perceptron_hidden_quantity = config.perceptron_input_quantity * 2;
        config.hiddenLayerQuantity = 2;
        //  config.randomMoveProbability = 0.01;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), true, getGamma(), getMomentum());
    }

}
