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
package ar.edu.unrc.game2048.performanceandtraining.experiments.learning;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.experiments.GeneratorConfig;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicLinear_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicTanH_512;

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
public
class TestGeneratorActivationFunctionVsTraces {

    /**
     *
     */
    public static final int NO_ANNEALING = -1;

    private static
    void configAndExecute(
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
            int saveBackupEvery,
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
        experiment.setConcurrencyInComputeBestPossibleAction(true);
        boolean[] concurrentLayer = {false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(512);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setSaveBackupEvery(saveBackupEvery);
        experiment.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        experiment.setExportToExcel(true);
        System.out.println("*=*=*=*=*=*=*=*=*=*=* N" + numberForShow + " Ejecutando " + filePath + " *=*=*=*=*=*=*=*=*=*=*");
        experiment.start(numberForShow, filePath, 0, true, null);
    }

    /**
     * @param ex error a tratar.
     *
     * @return traducción de la traza de errores a texto.
     */
    public static
    String getMsj(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter  pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * @param args
     *
     * @throws Exception
     */
    public static
    void main(String[] args)
            throws Exception {
        String        filePath           = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
        List<Double>  lambdaList         = new ArrayList<>();
        List<Double>  alphaList          = new ArrayList<>();
        List<Integer> annealingAlphaList = new ArrayList<>();
        List<Double>  gammaList          = new ArrayList<>();
        List<Double>  explorationRate    = new ArrayList<>();

        //============================== configuraciones manuales ==================================
        int     repetitions = 1;
        int     maxTrainingThreads = 8;
        int     gamesToPlay = 20_000;
        int     saveEvery = 1_000;
        int     saveBackupEvery = 500;
        boolean resetTracesTest = false;

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
        int     gamesToPlayPerThreadForStatistics;
        int     simulationsForStatistics;

        statisticsOnly = false;
        runStatisticsForBackups = false;
        gamesToPlayPerThreadForStatistics = 0;
        simulationsForStatistics = 0;

        runAllConfigs(repetitions,
                maxTrainingThreads,
                "BasicLinear_512_ActFuncVsTrace",
                BasicLinear_512.class.getConstructor(),
                null,
                alphaList,
                annealingAlphaList,
                lambdaList,
                gammaList,
                statisticsOnly,
                runStatisticsForBackups,
                createLogs,
                gamesToPlay,
                saveEvery,
                saveBackupEvery,
                gamesToPlayPerThreadForStatistics,
                simulationsForStatistics,
                explorationRate, resetTracesTest,
                filePath
        );
        runAllConfigs(repetitions,
                maxTrainingThreads,
                "BasicTanH_512_ActFuncVsTrace",
                BasicTanH_512.class.getConstructor(),
                null,
                alphaList,
                annealingAlphaList,
                lambdaList,
                gammaList,
                statisticsOnly,
                runStatisticsForBackups,
                createLogs,
                gamesToPlay,
                saveEvery,
                saveBackupEvery,
                gamesToPlayPerThreadForStatistics,
                simulationsForStatistics,
                explorationRate, resetTracesTest,
                filePath
        );

        statisticsOnly = true;
        runStatisticsForBackups = true;
        gamesToPlayPerThreadForStatistics = 1_000;
        simulationsForStatistics = 8;

        runAllConfigs(repetitions,
                maxTrainingThreads,
                "BasicLinear_512_ActFuncVsTrace",
                BasicLinear_512.class.getConstructor(),
                null,
                alphaList,
                annealingAlphaList,
                lambdaList,
                gammaList,
                statisticsOnly,
                runStatisticsForBackups,
                createLogs,
                gamesToPlay,
                saveEvery,
                saveBackupEvery,
                gamesToPlayPerThreadForStatistics,
                simulationsForStatistics,
                explorationRate, resetTracesTest,
                filePath
        );
        runAllConfigs(repetitions,
                maxTrainingThreads,
                "BasicTanH_512_ActFuncVsTrace",
                BasicTanH_512.class.getConstructor(),
                null,
                alphaList,
                annealingAlphaList,
                lambdaList,
                gammaList,
                statisticsOnly,
                runStatisticsForBackups,
                createLogs,
                gamesToPlay,
                saveEvery,
                saveBackupEvery,
                gamesToPlayPerThreadForStatistics,
                simulationsForStatistics,
                explorationRate, resetTracesTest,
                filePath
        );
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private static
    void runAllConfigs(
            final int repetitions,
            final int maxTrainingThreads,
            final String experimentName,
            final Constructor<?> experiment,
            final Object[] classParameters,
            final List<Double> alphaList,
            final List<Integer> annealingAlphaList,
            final List<Double> lambdaList,
            final List<Double> gammaList,
            final boolean statisticsOnly,
            final boolean runStatisticsForBackups,
            final boolean createLogs,
            final int gamesToPlay,
            final int saveEvery,
            final int saveBackupEvery,
            final int gamesToPlayPerThreadForStatistics,
            final int simulationsForStatistics,
            final List<Double> explorationRateList,
            final boolean resetTracesTest,
            final String filePath
    ) {
        List<GeneratorConfig> experiments = new ArrayList<>();
        int                   number      = 0;
        for (int a = 0; a < repetitions; a++) {
            for (int i = 0; i < alphaList.size(); i++) {
                for (int j = 0; j < lambdaList.size(); j++) {
                    for (int k = 0; k < gammaList.size(); k++) {
                        for (int l = 0; l < explorationRateList.size(); l++) {
                            for (int m = 0; m < annealingAlphaList.size(); m++) {
                                number++;
                                experiments.add(new GeneratorConfig(a,
                                        classParameters,
                                        alphaList.get(i),
                                        annealingAlphaList.get(m),
                                        lambdaList.get(j),
                                        gammaList.get(k),
                                        explorationRateList.get(l),
                                        null,
                                        null,
                                        null,
                                        null,
                                        false,
                                        number
                                ));
                                if (explorationRateList.get(l) > 0 && lambdaList.get(j) > 0 && resetTracesTest) {
                                    number++;
                                    experiments.add(new GeneratorConfig(a,
                                            classParameters,
                                            alphaList.get(i),
                                            annealingAlphaList.get(m),
                                            lambdaList.get(j),
                                            gammaList.get(k),
                                            explorationRateList.get(l),
                                            null,
                                            null,
                                            null,
                                            null,
                                            true,
                                            number
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }

        Stream<GeneratorConfig> stream;
        if (statisticsOnly) {
            stream = experiments.stream();
        } else {
            stream = experiments.parallelStream();
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(maxTrainingThreads);
        try {
            forkJoinPool.submit(() -> //parallel task here, for example
                    stream.forEach(expConfig -> {
                        try {
                            String newFilePath = filePath +
                                                 "ActivationFunctionVsTraces" +
                                                 File.separator + expConfig.getRepetitions() + "-alpha_" +
                                                 expConfig.getAlpha() +
                                                 ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.getAnnealingAlpha() : "") +
                                                 "-lambda_" +
                                                 expConfig.getLambda() +
                                                 "-gamma_" +
                                                 expConfig.getGamma() +
                                                 "-explorationRate_" +
                                                 expConfig.getExplorationRate() +
                                                 "-resetTraces_" +
                                                 expConfig.isResetTraces() +
                                                 File.separator;
                            File newPath = new File(newFilePath);
                            if (!newPath.exists()) {
                                newPath.mkdirs();
                            }
                            LearningExperiment cloneExperiment = (LearningExperiment) experiment.newInstance();
                            cloneExperiment.setExperimentName(experimentName);
                            configAndExecute(
                                    expConfig.getNumber(),
                                    cloneExperiment,
                                    statisticsOnly,
                                    runStatisticsForBackups,
                                    createLogs,
                                    expConfig.getLambda(),
                                    expConfig.getAlpha(),
                                    expConfig.getGamma(),
                                    gamesToPlay,
                                    saveEvery,
                                    saveBackupEvery,
                                    gamesToPlayPerThreadForStatistics,
                                    simulationsForStatistics,
                                    expConfig.getExplorationRate(),
                                    expConfig.isResetTraces(),
                                    newFilePath
                            );
                        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(TestGeneratorActivationFunctionVsTraces.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    })).get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TestGeneratorActivationFunctionVsTraces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
