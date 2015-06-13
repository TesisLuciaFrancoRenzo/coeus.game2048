/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.SymetricSample01Score;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Experiment_10 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_10();
//        boolean statistics = true;
        boolean statistics = false;
        experiment.setLearningRateAdaptationToAnnealing(20_000);
        double[] alpha = {0.1, 0.02};
        experiment.setAlpha(alpha);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setReplaceEligibilitiTraces(true);
        experiment.setResetEligibilitiTraces(true);
        experiment.setGamesToPlay(40_000);
        experiment.setLastGamePlayedNumber(0); //recordar AJUSTAR ESTE VALOR
        experiment.setSaveEvery(1_000);
        experiment.setInitializePerceptronRandomized(true);

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
            this.setExperimentName("Experiment_10");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new SymetricSample01Score<>();
        config.perceptron_hidden_quantity = 32;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), getGamma(), isResetEligibilitiTraces(), isReplaceEligibilitiTraces());
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }

}
