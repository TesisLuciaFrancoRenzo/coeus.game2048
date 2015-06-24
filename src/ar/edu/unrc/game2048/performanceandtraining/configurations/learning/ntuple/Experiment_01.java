/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.ntuple;

import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.NTupleExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.BasicScoreLinear;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Experiment_01 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_01();

//        boolean statistics = true;
        boolean statistics = false;
        double[] alphas = {0.0025};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setLambda(0);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        //  experiment.setResetEligibilitiTraces(true);
        experiment.setGamesToPlay(10_000);
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
        this.setTileToWin(32_768);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_01");
        }
        this.setPerceptronName(this.getExperimentName());
        NTupleConfiguration2048 config = new BasicScoreLinear();
        this.setNeuralNetworkInterfaceFor2048(new NTupleExperimentInterface(config));
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return null;
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return new TDLambdaLearningAfterstate(nTupleSystem, (getAlpha() != null) ? getAlpha()[0] : null, getLambda(), getGamma(), isResetEligibilitiTraces());
    }

}
