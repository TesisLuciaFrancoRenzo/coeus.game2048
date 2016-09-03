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
import ar.edu.unrc.game2048.performanceandtraining.experiments.ArgumentLoader;
import ar.edu.unrc.game2048.performanceandtraining.experiments.GeneratorConfig;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.NTupleLinear_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicLinear;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicLinearNoPartialScore_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicLinearSimplified_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicLinear_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.BasicTanH;
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
 * Tests parametrizables mediante parámetros, útil para correr variedades de simulaciones sin recompilar todo el
 * programa.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TestGenerator {

    /**
     *
     */
    public static final int NO_ANNEALING = -1;

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
     * @param tileToWinForStatistics
     * @param simulationsForStatistics
     * @param explorationRate
     * @param explorationRateInitialValue
     * @param explorationRateFinalValue
     * @param explorationRateStartInterpolation
     * @param explorationRateFinishInterpolation
     * @param replaceEligibilityTraces
     * @param filePath
     * @param concurrentLayer
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
            int tileToWinForStatistics,
            int simulationsForStatistics,
            Double explorationRate,
            Double explorationRateInitialValue,
            Double explorationRateFinalValue,
            Integer explorationRateStartInterpolation,
            Integer explorationRateFinishInterpolation,
            boolean replaceEligibilityTraces,
            String filePath,
            boolean[] concurrentLayer
    ) {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setGamma(gamma);
        double[] alphas = {alpha, alpha};
        experiment.setAlpha(alphas);
        if ( explorationRate != null ) {
            experiment.setExplorationRateToFixed(explorationRate);
        } else {
            experiment.setExplorationRate(explorationRateInitialValue,
                    explorationRateStartInterpolation,
                    explorationRateFinalValue,
                    explorationRateFinishInterpolation);
        }
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPosibleAction(true);
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
        List<Double> fixedExplorationRateFixed = new ArrayList<>();
        List<Double> interpolatedExplorationRateFinalValues = new ArrayList<>();
        List<Integer> interpolatedExplorationRateFinishInterpolation = new ArrayList<>();
        List<Double> interpolatedExplorationRateInitialValues = new ArrayList<>();
        List<Integer> interpolatedExplorationRateStartInterpolation = new ArrayList<>();

        int gamesToPlayPerThreadForStats = 1_000; //OJO que no se puede configurar por parametro

        //============================== configuraciones manuales ==================================
//        boolean statistics = true;
        boolean statistics = false;
        int maxTrainingThreads = 8;
        boolean doBackupStatistics = true;
        String experimentName = "BasicLinearNoPartialScore_512";
        String experimentClass = "BasicLinearNoPartialScore_512";
        int gamesToPlay = 1;
        int saveEvery = 1_000;
        int saveBackupEvery = 500;
        int tileToWinForStatistics = 2_048;
        boolean[] concurrentLayer = {false, false};

        lambdaList.add(0d);
//        lambdaList.add(0.1d);
//        lambdaList.add(0.2d);

        alphaList.add(0.005d);

        annealingAlphaList.add(2_000_000); //Sin annealing
//        annealingAlphaList.add(400_000);
//        annealingAlphaList.add(600_000);

        gammaList.add(1d);

        fixedExplorationRateFixed = null;
//        explorationRate.add(0d);
//        explorationRate.add(0.1d);

        interpolatedExplorationRateInitialValues.add(0.1d);
        interpolatedExplorationRateFinalValues.add(0.01d);
        interpolatedExplorationRateFinalValues.add(0.005d);
        interpolatedExplorationRateStartInterpolation.add(0);
        interpolatedExplorationRateFinishInterpolation.add(500_000);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================

        if ( args.length != 0 ) {
            ArgumentLoader arguments = new ArgumentLoader(args);
            statistics = Boolean.parseBoolean(arguments.getArg("statistics"));
            maxTrainingThreads = Integer.parseInt(arguments.getArg("maxTrainingThreads"));
            tileToWinForStatistics = Integer.parseInt(arguments.getArg("tileToWinForStatistics"));
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
            concurrentLayer = ArgumentLoader.parseBooleanArray(arguments.getArg("concurrentLayer"));
            try {
                interpolatedExplorationRateInitialValues = ArgumentLoader.parseDoubleArray(arguments.getArg(
                        "explorationRateInitialValue"));
                interpolatedExplorationRateFinalValues = ArgumentLoader.parseDoubleArray(arguments.getArg(
                        "explorationRateFinalValues"));
                interpolatedExplorationRateStartInterpolation = ArgumentLoader.parseIntegerArray(arguments.getArg(
                        "explorationRateStartInterpolation"));
                interpolatedExplorationRateFinishInterpolation = ArgumentLoader.parseIntegerArray(arguments.getArg(
                        "explorationRateFinishInterpolation"));
                if ( interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateFinalValues.size() || interpolatedExplorationRateFinalValues.
                        size() != interpolatedExplorationRateStartInterpolation.size() || interpolatedExplorationRateInitialValues.
                        size() != interpolatedExplorationRateFinishInterpolation.
                        size() ) {
                    System.err.println("La cantidad de parametros de exploration rate no coinciden");
                    System.exit(-1);
                }
                fixedExplorationRateFixed = null;
            } catch ( Exception e ) {
                interpolatedExplorationRateInitialValues = null;
                interpolatedExplorationRateFinalValues = null;
                interpolatedExplorationRateStartInterpolation = null;
                interpolatedExplorationRateFinishInterpolation = null;
                fixedExplorationRateFixed = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRate"));
            }
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
            case "BasicLinearSimplified_512": {
                classConstructor = BasicLinearSimplified_512.class.getConstructor();
                break;
            }
            case "PNTupleLinear_512": {
                classConstructor = NTupleLinear_512.class.getConstructor();
                break;
            }
            case "BasicLinearNoPartialScore_512": {
                classConstructor = BasicLinearNoPartialScore_512.class.getConstructor();
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
                saveBackupEvery, gamesToPlayPerThreadForStatistics, tileToWinForStatistics,
                simulationsForStatistics, fixedExplorationRateFixed, interpolatedExplorationRateInitialValues,
                interpolatedExplorationRateFinalValues,
                interpolatedExplorationRateStartInterpolation, interpolatedExplorationRateFinishInterpolation, filePath,
                concurrentLayer);
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
            int tileToWinForStatistics,
            int simulationsForStatistics,
            List<Double> explorationRateList,
            List<Double> explorationRateInitialValues,
            List<Double> explorationRateFinalValues,
            List<Integer> explorationRateStartInterpolation,
            List<Integer> explorationRateFinishInterpolation,
            String filePath,
            boolean[] concurrentLayer) {
        List<GeneratorConfig> experiments = new ArrayList<>();
        int number = 0;
        for ( int i = 0; i < alphaList.size(); i++ ) {
            for ( int j = 0; j < lambdaList.size(); j++ ) {
                for ( int k = 0; k < gammaList.size(); k++ ) {
                    for ( int m = 0; m < annealingAlphaList.size(); m++ ) {
                        if ( explorationRateList != null ) {
                            for ( int l = 0; l < explorationRateList.size(); l++ ) {
                                number++;
                                experiments.add(new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                        lambdaList.get(j), gammaList.get(k), explorationRateList.get(l), null, null,
                                        null, null, false, number));
                                if ( explorationRateList.get(l) > 0 && lambdaList.get(j) > 0 ) {
                                    number++;
                                    experiments.add(
                                            new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                                    lambdaList.get(j), gammaList.get(k), explorationRateList.get(l),
                                                    null, null, null, null, true, number));
                                }
                            }
                        } else {
                            for ( int n = 0; n < explorationRateInitialValues.size(); n++ ) {
                                for ( int o = 0; o < explorationRateFinalValues.size(); o++ ) {
                                    for ( int p = 0; p < explorationRateStartInterpolation.size(); p++ ) {
                                        for ( int q = 0; q < explorationRateFinishInterpolation.size(); q++ ) {
                                            number++;
                                            experiments.add(
                                                    new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                                            lambdaList.get(j), gammaList.get(k), null,
                                                            explorationRateInitialValues.get(n),
                                                            explorationRateFinalValues.get(o),
                                                            explorationRateStartInterpolation.get(p),
                                                            explorationRateFinishInterpolation.get(q), false, number));
                                            number++;
                                            experiments.add(
                                                    new GeneratorConfig(alphaList.get(i), annealingAlphaList.get(m),
                                                            lambdaList.get(j), gammaList.get(k), null,
                                                            explorationRateInitialValues.get(n),
                                                            explorationRateFinalValues.get(o),
                                                            explorationRateStartInterpolation.get(p),
                                                            explorationRateFinishInterpolation.get(q), true, number));
                                        }
                                    }
                                }
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

        ForkJoinPool forkJoinPool = new ForkJoinPool(maxTrainingThreads); //TODO arreglar problema de maxThreads internos
        try {
            forkJoinPool.submit(() ->
                    stream.forEach(expConfig ->
                            {
                                try {
                                    String explorationRateString;
                                    if ( explorationRateList != null ) {
                                        explorationRateString = "-explorationRate_" + expConfig.getExplorationRate();
                                    } else {
                                        explorationRateString = "-explorationRate_" + expConfig.
                                                getExplorationRateInitialValue()
                                                + "_" + expConfig.getExplorationRateFinalValue() + "_" + expConfig.
                                                getExplorationRateStartInterpolation() + "_" + expConfig.
                                                getExplorationRateFinishInterpolation();
                                    }
                                    String newFilePath = filePath + "AutomaticTests" + File.separator + "alpha_" + expConfig.
                                            getAlpha() + ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.
                                                    getAnnealingAlpha() : "") + "-lambda_" + expConfig.
                                            getLambda() + "-gamma_" + expConfig.getGamma() + explorationRateString
                                            + "-resetTraces_" + expConfig.isResetTraces() + File.separator;
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
                                            tileToWinForStatistics,
                                            simulationsForStatistics,
                                            expConfig.getExplorationRate(),
                                            expConfig.getExplorationRateInitialValue(),
                                            expConfig.getExplorationRateFinalValue(),
                                            expConfig.getExplorationRateStartInterpolation(),
                                            expConfig.getExplorationRateFinishInterpolation(),
                                            expConfig.isResetTraces(), newFilePath, concurrentLayer);
                                } catch ( ClassCastException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex ) {
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
