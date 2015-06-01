/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.FullNTupleScore;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.training.ELearningRateAdaptation;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author Franco
 */
public class Experiment_03 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Experiment_03();
        experiment.createLogs(false);

//        boolean statistics = true;
        boolean statistics = false;
        experiment.setLearningRateAdaptationToFixed();
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setMomentum(0.5);
        experiment.setGamesToPlay(20_000);//van 50.000
        //experiment.setLastGamePlayedNumber(0); //recordar AJUSTAR ESTE VALOR //TODO volver a agregar un sistema que soporte continuar un entrenamiento a medias para lso alfas dinamicos
        experiment.setSaveEvery(1_000);

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

        experiment.start(filePath, 0);
    }

    @Override
    public void initialize() throws Exception {
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_03");
        }
        this.setPerceptronName(this.getExperimentName());
        this.setTileToWin(2_048);
        PerceptronConfiguration2048<BasicNetwork> config = new FullNTupleScore<>();
        config.randomMoveProbability = 0.01;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), true, getGamma(), getMomentum());
    }

}
