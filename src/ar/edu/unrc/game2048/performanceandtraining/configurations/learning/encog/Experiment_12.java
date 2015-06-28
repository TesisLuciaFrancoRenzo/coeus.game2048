/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.BinaryScore;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Experiment_12 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_12();
        boolean statistics = true;
//        boolean statistics = false;

        experiment.setLambda(0);
        experiment.setGamma(1);
        double[] alpha = {0.0025, 0.0025};
        experiment.setAlpha(alpha);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setExplorationRateToFixed(0.1);
        experiment.setResetEligibilitiTraces(false);
        experiment.setGamesToPlay(25_000);
        experiment.setSaveEvery(500);
        experiment.setSaveBackupEvery(1_000);
        experiment.setInitializePerceptronRandomized(false);

        experiment.createLogs(false);
        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(8);
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
            this.setExperimentName("Experiment_12");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new BinaryScore<>();
        //  config.perceptron_hidden_quantity = config.perceptron_input_quantity * 2;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), getGamma(), isResetEligibilitiTraces());
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }

}
