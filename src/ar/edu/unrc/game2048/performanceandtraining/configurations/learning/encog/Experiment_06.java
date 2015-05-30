/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.NTupleScore;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author Franco
 */
public class Experiment_06 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_06();
        experiment.createLogs(false);

//        boolean statistics = true;
        boolean statistics = false;

        experiment.setLambda(0.7);
        experiment.setGamesToPlay(40_000);
        experiment.setLastGamePlayedNumber(0); //recordar AJUSTAR ESTE VALOR
        experiment.setInitialAlpha(0.1); //recomendado por sutton para 16 neuronas, ya que el valor ideal es 1/16 y la grafica queda bien con estos valores al llegar a un millon
        experiment.setAlphaT(800_000);
        experiment.setSaveEvery(500);
        experiment.setInitializePerceptronRandomized(true);

        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(10_000);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticForRandom(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }

        //experiment.setElegibilityTraceLenght(TDLambdaLearning.calculateBestEligibilityTraceLenght(experiment.getLambda()));
        experiment.start(filePath, 0);
    }

    @Override
    public void initialize() throws Exception {
        this.setTileToWin(2_048);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_06");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new NTupleScore<>();
        //config.randomMoveProbability = 0.01;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
        this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(this.getNeuralNetworkInterfaceFor2048().getPerceptronInterface()));
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), true);
    }

}
