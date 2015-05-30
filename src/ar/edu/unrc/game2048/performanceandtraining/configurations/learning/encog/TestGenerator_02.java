/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author franco
 */
public class TestGenerator_02 {

    public static void main(String[] args) throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath
                    = ".." + File.separator
                    + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }

        List<Double> lambdaList = new LinkedList<>();
        List<Double> alphaList = new LinkedList<>();

        //============================== configuraciones manuales ==================================
        boolean statistics = true;
//        boolean statistics = false;

        int gamesToPlay = 10_000;
        int saveEvery = 1_000;
//
//        lambdaList.add(0d);
//        lambdaList.add(0.1d);
//        lambdaList.add(0.2d);
//        lambdaList.add(0.3d);
        lambdaList.add(0.4d);
        lambdaList.add(0.5d);
        lambdaList.add(0.6d);
        lambdaList.add(0.7d);
//        lambdaList.add(0.8d);
//        lambdaList.add(0.9d);
//        lambdaList.add(1d);
        //-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---alphaList.add(0.001d);
        alphaList.add(0.001d);
        alphaList.add(0.01d);
        alphaList.add(0.025d);
        alphaList.add(0.05d);
        alphaList.add(0.1d);
        alphaList.add(0.2d);
        alphaList.add(0.3d);
        alphaList.add(0.4d);
        alphaList.add(0.5d);
        alphaList.add(0.6d);
        alphaList.add(0.7d);
        alphaList.add(0.8d);
        alphaList.add(0.9d);
        alphaList.add(1d);

        boolean createLogs = false;

        //============================== fin de configuraciones manuales ==================================
        boolean statisticsOnly;
        boolean runStatisticForRandom;
        boolean runStatisticsForBackups;
        int gamesToPlayPerThreadForStatistics;
        int simulationsForStatistics;

        if ( statistics ) {
            statisticsOnly = true;
            runStatisticForRandom = true;
            runStatisticsForBackups = true;
            gamesToPlayPerThreadForStatistics = 10_000;
            simulationsForStatistics = 8;
        } else {
            statisticsOnly = false;
            runStatisticForRandom = false;
            runStatisticsForBackups = false;
            gamesToPlayPerThreadForStatistics = 0;
            simulationsForStatistics = 0;
        }

        runAllConfigs("Experiment_01", new Experiment_01(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_02", new Experiment_02(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_03", new Experiment_03(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_04", new Experiment_04(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_05", new Experiment_05(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_06", new Experiment_06(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_07", new Experiment_07(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
//        runAllConfigs("Experiment_08", new Experiment_08(), alphaList, lambdaList, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);

    }

    public static void runAllConfigs(String experimentName, LearningExperiment experiment, List<Double> alphaList, List<Double> lambdaList, boolean statisticsOnly, boolean runStatisticForRandom, boolean runStatisticsForBackups, boolean createLogs, int gamesToPlay, int saveEvery, int gamesToPlayPerThreadForStatistics, int simulationsForStatistics, String filePath) {
        alphaList.stream().forEach((alpha) -> {
            lambdaList.stream().forEach((lambda) -> {
                experiment.setExperimentName(experimentName + "-alpha_" + alpha + "-lambda_" + lambda);
                configAndExcecute(experiment, statisticsOnly, runStatisticForRandom, runStatisticsForBackups, createLogs, lambda, alpha, gamesToPlay, saveEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, filePath);
            });
        });
    }

    public static void configAndExcecute(LearningExperiment experiment, boolean statisticsOnly, boolean runStatisticForRandom, boolean runStatisticsForBackups, boolean createLogs, double lambda, double alpha, int gamesToPlay, int saveEvery, int gamesToPlayPerThreadForStatistics, int simulationsForStatistics, String filePath) {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticForRandom(runStatisticForRandom);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        //       experiment.setElegibilityTraceLenght(TDLambdaLearning.calculateBestEligibilityTraceLenght(lambda));
//        experiment.setElegibilityTraceLenght(Integer.MAX_VALUE);
        experiment.setAlpha(alpha);
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        experiment.start(filePath, 0);
    }
}
