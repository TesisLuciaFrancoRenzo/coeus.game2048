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
package ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.experiments.GeneratorConfig;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Test específicamente diseñado para comparar eficiencia de funciones de activación.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TestGeneratorActivationFunctionVsTraces {

    /**
     *
     */
    public static final int NO_ANNEALING = -1;


    /**
     * @param ex error a tratar.
     *
     * @return traducción de la traza de errores a texto.
     */
    public static String getMsj(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /**
     *
     * @param args
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath
                = ".." + File.separator
                + "Perceptrones ENTRENADOS" + File.separator;
        List<Double> lambdaList = new ArrayList<>();
        List<Double> alphaList = new ArrayList<>();
        List<Integer> annealingAlphaList = new ArrayList<>();
        List<Double> gammaList = new ArrayList<>();
        List<Double> explorationRate = new ArrayList<>();

        //============================== configuraciones manuales ==================================
        int maxTrainingThreads = 8;
        int gamesToPlay = 20_000;
        int saveEvery = 1_000;
        int saveBackupEvery = 500;

        lambdaList.add(0d);
        lambdaList.add(0.1d);
        lambdaList.add(0.2d);
        lambdaList.add(0.3d);
        lambdaList.add(0.4d);
        lambdaList.add(0.5d);
        lambdaList.add(0.6d);
        lambdaList.add(0.7d);
        lambdaList.add(0.8d);
        lambdaList.add(0.9d);
        lambdaList.add(1d);

        alphaList.add(0.0025d);
        annealingAlphaList.add(NO_ANNEALING); //Sin annealing

        gammaList.add(1d);

        explorationRate.add(0d);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================

        boolean statisticsOnly;
        boolean runStatisticsForBackups;
        int gamesToPlayPerThreadForStatistics;
        int simulationsForStatistics;

        statisticsOnly = false;
        runStatisticsForBackups = false;
        gamesToPlayPerThreadForStatistics = 0;
        simulationsForStatistics = 0;

        runAllConfigs(maxTrainingThreads, "BasicLinear_512_ActFuncVsTrace",
                BasicLinear_512.class.getConstructor(), alphaList, annealingAlphaList, lambdaList, gammaList,
                statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery,
                gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
        runAllConfigs(maxTrainingThreads, "BasicTanH_512_ActFuncVsTrace", BasicTanH_512.class.getConstructor(),
                alphaList, annealingAlphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups,
                createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics,
                simulationsForStatistics, explorationRate, filePath);

        statisticsOnly = true;
        runStatisticsForBackups = true;
        gamesToPlayPerThreadForStatistics = 1_000;
        simulationsForStatistics = 8;

        runAllConfigs(maxTrainingThreads, "BasicLinear_512_ActFuncVsTrace",
                BasicLinear_512.class.getConstructor(), alphaList, annealingAlphaList, lambdaList, gammaList,
                statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery,
                gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
        runAllConfigs(maxTrainingThreads, "BasicTanH_512_ActFuncVsTrace",
                BasicTanH_512.class.getConstructor(), alphaList, annealingAlphaList, lambdaList, gammaList,
                statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery,
                gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
    }
    private static void configAndExcecute(
            int numberForShow,
            LearningExperiment experiment,
            boolean statisticsOnly,
            boolean runStatisticsForBackups,
            boolean createLogs,
            double lambda,
            double alpha,
            double gamma,
            int gamesToPlay,
            int saveEvery,
            int saveBacupEvery,
            int gamesToPlayPerThreadForStatistics,
            int simulationsForStatistics,
            double explorationRate,
            boolean replaceEligibilityTraces,
            String filePath
    ) {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setGamma(gamma);
        double[] alphas = {alpha, alpha};
        experiment.setAlpha(alphas);
        experiment.setExplorationRateToFixed(explorationRate);
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPosibleAction(true);
        boolean[] concurrentLayer = {false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(512);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setSaveBackupEvery(saveBacupEvery);
        experiment.setGamesToPlayPerThreadForStatistics(
                gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        experiment.setExportToExcel(true);
        System.out.println(
                "*=*=*=*=*=*=*=*=*=*=* N" + numberForShow + " Ejecutando " + filePath + " *=*=*=*=*=*=*=*=*=*=*");
        experiment.start(numberForShow, filePath, 0, true, null);
    }

    private static void runAllConfigs(int maxTrainingThreads,
            String experimentName,
            Constructor<?> experiment,
            List<Double> alphaList,
            List<Integer> annealingAlphaList,
            List<Double> lambdaList,
            List<Double> gammaList,
            boolean statisticsOnly,
            boolean runStatisticsForBackups,
            boolean createLogs,
            int gamesToPlay,
            int saveEvery,
            int saveBacupEvery,
            int gamesToPlayPerThreadForStatistics,
            int simulationsForStatistics,
            List<Double> explorationRateList,
            String filePath) {
        List<GeneratorConfig> experiments = new ArrayList<>();
        int number = 0;
        for ( int i = 0; i < alphaList.size(); i++ ) {
            for ( int j = 0; j < lambdaList.size(); j++ ) {
                for ( int k = 0; k < gammaList.size(); k++ ) {
                    for ( int l = 0; l < explorationRateList.size(); l++ ) {
                        for ( int m = 0; m < annealingAlphaList.size(); m++ ) {
                            number++;
                            experiments.add(new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                    lambdaList.get(j), gammaList.get(k), explorationRateList.get(l), false, number));
                            if ( explorationRateList.get(l) > 0 && lambdaList.get(j) > 0 ) {
                                number++;
                                experiments.add(
                                        new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m), lambdaList.get(
                                                j),
                                                gammaList.get(k), explorationRateList.get(l), true, number));
                            }
                        }
                    }
                }
            }
        }

        Stream<GeneratorConfig> stream;
        if ( statisticsOnly ) {
            stream = experiments.stream();
        } else {
            stream = experiments.parallelStream();
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(maxTrainingThreads);
        try {
            forkJoinPool.submit(() -> //parallel task here, for example
                    stream.forEach(expConfig ->
                            {
                                try {
                                    String newFilePath = filePath + "ActivationFunctionVsTraces" + File.separator + "alpha_" + expConfig.
                                            getAlpha() + ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.
                                                    getAnnealingAlpha() : "") + "-lambda_" + expConfig.
                                            getLambda() + "-gamma_" + expConfig.getGamma() + "-explorationRate_" + expConfig.
                                            getExplorationRate() + "-resetTraces_" + expConfig.isResetTraces() + File.separator;
                                    File newPath = new File(newFilePath);
                                    if ( !newPath.exists() ) {
                                        newPath.mkdirs();
                                    }
                                    LearningExperiment cloneExperiment = (LearningExperiment) experiment.
                                            newInstance();
                                    cloneExperiment.setExperimentName(
                                            experimentName);
                                    configAndExcecute(expConfig.getNumber(),
                                            cloneExperiment, statisticsOnly,
                                            runStatisticsForBackups, createLogs,
                                            expConfig.getLambda(), expConfig.
                                            getAlpha(), expConfig.getGamma(),
                                            gamesToPlay, saveEvery,
                                            saveBacupEvery,
                                            gamesToPlayPerThreadForStatistics,
                                            simulationsForStatistics, expConfig.
                                            getExplorationRate(), expConfig.
                                            isResetTraces(), newFilePath);
                                } catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex ) {
                                    Logger.getLogger(
                                            TestGeneratorActivationFunctionVsTraces.class.
                                            getName()).log(Level.SEVERE, null,
                                                    ex);
                                }
                            })
            ).get();
        } catch ( InterruptedException | ExecutionException ex ) {
            Logger.getLogger(TestGeneratorActivationFunctionVsTraces.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
    }
}
