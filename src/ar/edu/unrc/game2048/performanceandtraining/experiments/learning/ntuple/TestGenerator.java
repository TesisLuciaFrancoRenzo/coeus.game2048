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
import ar.edu.unrc.game2048.performanceandtraining.experiments.ArgumentLoader;
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
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TestGenerator {

    /**
     *
     */
    public static final int NO_ANNEALING = -1;
    private static int tileToWinForStatistics;

    /**
     *
     * @param numberForShow
     * @param experiment
     * @param statisticsOnly
     * @param runStatisticsForBackups
     * @param createLogs
     * @param lambda
     * @param alpha
     * @param annealingAlpha
     * @param gamma
     * @param gamesToPlay
     * @param saveEvery
     * @param saveBacupEvery
     * @param gamesToPlayPerThreadForStatistics
     * @param simulationsForStatistics
     * @param explorationRate
     * @param replaceEligibilityTraces
     * @param filePath
     */
    public static void configAndExcecute(
            int numberForShow,
            LearningExperiment experiment,
            boolean statisticsOnly,
            boolean runStatisticsForBackups,
            boolean createLogs,
            double lambda,
            double alpha,
            int annealingAlpha,
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
        experiment.setTileToWinForStatistics(tileToWinForStatistics);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        if ( annealingAlpha == NO_ANNEALING ) {
            experiment.setLearningRateAdaptationToFixed();
        } else {
            experiment.setLearningRateAdaptationToAnnealing(annealingAlpha);
        }
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

    /**
     *
     * @param ex <p>
     * @return
     */
    public static String getMsj(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /**
     *
     * @param args <p>
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
        boolean statistics = true;
//        boolean statistics = false;
        int maxTrainingThreads = 8;
        boolean doBackupStatistics = true;
        tileToWinForStatistics = 512;
        int gamesToPlayPerThreadForStats = 100;
        String experimentName = "BasicLinear_512";
        String experimentClass = "BasicLinear_512";
        int gamesToPlay = 20_000;
        int saveEvery = 1_000;
        int saveBackupEvery = 500;

        lambdaList.add(0.3d);
//        lambdaList.add(0.1d);
//        lambdaList.add(0.2d);

        alphaList.add(0.0025d);
        annealingAlphaList.add(NO_ANNEALING); //Sin annealing
//        annealingAlphaList.add(400_000);
//        annealingAlphaList.add(600_000);

        // gammaList.add(0.9d); No da resultados buenos
        gammaList.add(1d);

        explorationRate.add(0d);
        explorationRate.add(0.001d);
        explorationRate.add(0.005d);
        explorationRate.add(0.01d);
        explorationRate.add(0.1d);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================

        if ( args.length != 0 ) {
            ArgumentLoader arguments = new ArgumentLoader(args);
            statistics = Boolean.parseBoolean(arguments.getArg("statistics"));
            maxTrainingThreads = Integer.parseInt(arguments.getArg("maxTrainingThreads"));
            doBackupStatistics = Boolean.parseBoolean(arguments.getArg("doBackupStatistics"));
            gamesToPlay = Integer.parseInt(arguments.getArg("gamesToPlay"));
            saveEvery = Integer.parseInt(arguments.getArg("saveEvery"));
            saveBackupEvery = Integer.parseInt(arguments.getArg("saveBackupEvery"));
            experimentName = arguments.getArg("experimentName");
            experimentClass = arguments.getArg("experimentClass");

            lambdaList = ArgumentLoader.parseDoubleArray(arguments.getArg("lambdaList"));
            annealingAlphaList = ArgumentLoader.parseIntegerArray(arguments.getArg("annealingAlphaList"));
            alphaList = ArgumentLoader.parseDoubleArray(arguments.getArg("alphaList"));
            gammaList = ArgumentLoader.parseDoubleArray(arguments.getArg("gammaList"));
            explorationRate = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRate"));
        }

        boolean statisticsOnly;
        boolean runStatisticsForBackups;
        int gamesToPlayPerThreadForStatistics;
        int simulationsForStatistics;

        if ( statistics ) {
            statisticsOnly = true;
            runStatisticsForBackups = doBackupStatistics;
            gamesToPlayPerThreadForStatistics = gamesToPlayPerThreadForStats;
            simulationsForStatistics = 8;
        } else {
            statisticsOnly = false;
            runStatisticsForBackups = false;
            gamesToPlayPerThreadForStatistics = 0;
            simulationsForStatistics = 0;
        }

        Constructor<?> classConstructor;
        switch ( experimentClass ) {
            case "BasicLinear": {
                classConstructor = BasicLinear.class.getConstructor();
                break;
            }
            case "BasicTanH": {
                classConstructor = BasicTanH.class.getConstructor();
                break;
            }
            case "BasicLinear_512": {
                classConstructor = BasicLinear_512.class.getConstructor();
                break;
            }
            case "BasicTanH_512": {
                classConstructor = BasicTanH_512.class.getConstructor();
                break;
            }
            default: {
                throw new IllegalArgumentException(
                        "no se reconoce la clase: " + experimentClass);
            }
        }

        runAllConfigs(maxTrainingThreads, experimentName, classConstructor,
                alphaList, annealingAlphaList, lambdaList, gammaList, statisticsOnly,
                runStatisticsForBackups, createLogs, gamesToPlay, saveEvery,
                saveBackupEvery, gamesToPlayPerThreadForStatistics,
                simulationsForStatistics, explorationRate, filePath);
    }

    /**
     *
     * @param maxTrainingThreads
     * @param experimentName
     * @param experiment
     * @param alphaList
     * @param annealingAlphaList
     * @param lambdaList
     * @param gammaList
     * @param statisticsOnly
     * @param runStatisticsForBackups
     * @param createLogs
     * @param gamesToPlay
     * @param saveEvery
     * @param saveBacupEvery
     * @param gamesToPlayPerThreadForStatistics
     * @param simulationsForStatistics
     * @param explorationRateList
     * @param filePath
     */
    public static void runAllConfigs(int maxTrainingThreads,
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
                            experiments.add(new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m), lambdaList.
                                    get(j), gammaList.get(k), explorationRateList.get(l), false, number));
                            if ( explorationRateList.get(l) > 0 && lambdaList.get(j) > 0 ) {
                                number++;
                                experiments.add(
                                        new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                                lambdaList.get(j), gammaList.get(k), explorationRateList.get(l), true,
                                                number));
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
            forkJoinPool.submit(() ->
                    stream.forEach(expConfig ->
                            {
                                try {
                                    String newFilePath = filePath + "AutomaticTests" + File.separator + "alpha_" + expConfig.
                                            getAlpha() + ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.
                                                    getAnnealingAlpha() : "") + "-lambda_" + expConfig.
                                            getLambda() + "-gamma_" + expConfig.getGamma() + "-explorationRate_" + expConfig.
                                            getExplorationRate() + "-resetTraces_" + expConfig.isResetTraces() + File.separator;
                                    File newPath = new File(newFilePath);
                                    if ( !newPath.exists() ) {
                                        newPath.mkdirs();
                                    }
                                    LearningExperiment cloneExperiment = (LearningExperiment) experiment.newInstance();
                                    cloneExperiment.setExperimentName(experimentName);
                                    configAndExcecute(expConfig.getNumber(), cloneExperiment, statisticsOnly,
                                            runStatisticsForBackups, createLogs, expConfig.getLambda(), expConfig.
                                            getAlpha(), expConfig.getAnnealingAlpha(), expConfig.getGamma(), gamesToPlay,
                                            saveEvery, saveBacupEvery, gamesToPlayPerThreadForStatistics,
                                            simulationsForStatistics, expConfig.getExplorationRate(), expConfig.
                                            isResetTraces(), newFilePath);
                                } catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex ) {
                                    Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            })
            ).get();
        } catch ( InterruptedException | ExecutionException ex ) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
