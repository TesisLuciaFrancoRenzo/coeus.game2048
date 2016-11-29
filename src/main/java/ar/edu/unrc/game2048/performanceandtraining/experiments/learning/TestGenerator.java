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
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.NTupleLinearWithBias_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.NTupleLinear_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.*;

import java.awt.*;
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
public
class TestGenerator {

    /**
     *
     */
    public static final int NO_ANNEALING = -1;

    /**
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
     * @param saveBackupEvery
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
    public static
    void configAndExecute(
            final int numberForShow,
            final LearningExperiment experiment,
            final boolean statisticsOnly,
            final boolean runStatisticsForBackups,
            final boolean createLogs,
            final double lambda,
            final double alpha,
            final int annealingAlpha,
            final double gamma,
            final int gamesToPlay,
            final int saveEvery,
            final int saveBackupEvery,
            final int gamesToPlayPerThreadForStatistics,
            final int tileToWinForStatistics,
            final int simulationsForStatistics,
            final Double explorationRate,
            final Double explorationRateInitialValue,
            final Double explorationRateFinalValue,
            final Integer explorationRateStartInterpolation,
            final Integer explorationRateFinishInterpolation,
            final boolean replaceEligibilityTraces,
            final String filePath,
            final boolean[] concurrentLayer
    ) {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setGamma(gamma);
        double[] alphas = {alpha, alpha};
        experiment.setAlpha(alphas);
        if (explorationRate != null) {
            experiment.setExplorationRateToFixed(explorationRate);
        } else {
            experiment.setExplorationRate(explorationRateInitialValue,
                    explorationRateStartInterpolation,
                    explorationRateFinalValue,
                    explorationRateFinishInterpolation
            );
        }
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPossibleAction(true);
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(tileToWinForStatistics);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        if (annealingAlpha == NO_ANNEALING) {
            experiment.setLearningRateAdaptationToFixed();
        } else {
            experiment.setLearningRateAdaptationToAnnealing(annealingAlpha);
        }
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
    @SuppressWarnings("UnusedAssignment")
    public static
    void main(String[] args)
            throws Exception {
        String        filePath                                       = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
        List<Double>  lambdaList                                     = new ArrayList<>();
        List<Double>  alphaList                                      = new ArrayList<>();
        List<Integer> annealingAlphaList                             = new ArrayList<>();
        List<Double>  gammaList                                      = new ArrayList<>();
        List<Double>  fixedExplorationRate                           = new ArrayList<>();
        List<Double>  interpolatedExplorationRateFinalValues         = new ArrayList<>();
        List<Integer> interpolatedExplorationRateFinishInterpolation = new ArrayList<>();
        List<Double>  interpolatedExplorationRateInitialValues       = new ArrayList<>();
        List<Integer> interpolatedExplorationRateStartInterpolation  = new ArrayList<>();

        //============================== configuraciones manuales ==================================
        //        boolean statistics = false;
        boolean   statistics                   = true;
        int       repetitions                  = 1;
        int       maxTrainingThreads           = 8;
        boolean   doBackupStatistics           = true;
        String    experimentName               = "BasicLinear_512";
        String    experimentClass              = "BasicLinear_512";
        int       gamesToPlay                  = 12_000;
        int       saveEvery                    = 1000;
        int       saveBackupEvery              = 300;
        int       tileToWinForStatistics       = 512;
        boolean[] concurrentLayer              = {false, false};
        int       gamesToPlayPerThreadForStats = 100;
        boolean   resetTracesTest              = true;

        lambdaList.add(0.3d);

        //        annealingAlphaList.add(2_000_000); //Sin annealing
        //        annealingAlphaList.add(400_000);
        //        annealingAlphaList.add(600_000);
        annealingAlphaList.add(NO_ANNEALING);

        alphaList.add(0.0025d);

        gammaList.add(1d);

        // Exploration rates constantes
        interpolatedExplorationRateInitialValues = null;
        interpolatedExplorationRateFinalValues = null;
        interpolatedExplorationRateStartInterpolation = null;
        interpolatedExplorationRateFinishInterpolation = null;
        fixedExplorationRate.add(0.2d);
        fixedExplorationRate.add(0.1d);
        fixedExplorationRate.add(0.01d);
        fixedExplorationRate.add(0.001d);

        // Exploration rates variables
        //        fixedExplorationRate = null;
        //        interpolatedExplorationRateInitialValues.add(0.1d);
        //        interpolatedExplorationRateFinalValues.add(0.01d);
        //        interpolatedExplorationRateStartInterpolation.add(0);
        //        interpolatedExplorationRateFinishInterpolation.add(500_000);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================

        if (args.length != 0) {
            ArgumentLoader arguments = new ArgumentLoader(args);
            statistics = Boolean.parseBoolean(arguments.getArg("statistics"));
            maxTrainingThreads = Integer.parseInt(arguments.getArg("maxTrainingThreads"));
            tileToWinForStatistics = Integer.parseInt(arguments.getArg("tileToWinForStatistics"));
            doBackupStatistics = Boolean.parseBoolean(arguments.getArg("doBackupStatistics"));
            gamesToPlay = Integer.parseInt(arguments.getArg("gamesToPlay"));
            gamesToPlayPerThreadForStats = Integer.parseInt(arguments.getArg("gamesToPlayPerThreadForStats"));
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
                interpolatedExplorationRateInitialValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateInitialValue"));
                interpolatedExplorationRateFinalValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateFinalValues"));
                interpolatedExplorationRateStartInterpolation = ArgumentLoader.parseIntegerArray(arguments.getArg("explorationRateStartInterpolation"));
                interpolatedExplorationRateFinishInterpolation = ArgumentLoader.parseIntegerArray(arguments.getArg(
                        "explorationRateFinishInterpolation"));
                if (interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateFinalValues.size() ||
                    interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateStartInterpolation.size() ||
                    interpolatedExplorationRateInitialValues.size() != interpolatedExplorationRateFinishInterpolation.size()) {
                    System.err.println("La cantidad de parámetros de exploration rate no coinciden");
                    System.exit(-1);
                }
                fixedExplorationRate = null;
            } catch (Exception e) {
                interpolatedExplorationRateInitialValues = null;
                interpolatedExplorationRateFinalValues = null;
                interpolatedExplorationRateStartInterpolation = null;
                interpolatedExplorationRateFinishInterpolation = null;
                fixedExplorationRate = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRate"));
            }
        }

        boolean statisticsOnly;
        boolean runStatisticsForBackups;
        int     gamesToPlayPerThreadForStatistics;
        int     simulationsForStatistics;

        if (statistics) {
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
        Object[]       classParameters = null;
        switch (experimentClass) {
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
            case "NTupleLinear_512": {
                classConstructor = NTupleLinear_512.class.getConstructor(NTupleLinear_512.PARAMETER_TYPE);
                classParameters = new Object[]{false};
                break;
            }
            case "NTupleLinearWithBias_512": {
                classConstructor = NTupleLinearWithBias_512.class.getConstructor(NTupleLinearWithBias_512.PARAMETER_TYPE);
                classParameters = new Object[]{true};
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
                throw new IllegalArgumentException("no se reconoce la clase: " + experimentClass);
            }
        }

        runAllConfigs(
                repetitions,
                maxTrainingThreads,
                experimentName,
                classConstructor,
                classParameters,
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
                tileToWinForStatistics,
                simulationsForStatistics,
                fixedExplorationRate,
                interpolatedExplorationRateInitialValues,
                interpolatedExplorationRateFinalValues,
                interpolatedExplorationRateStartInterpolation,
                interpolatedExplorationRateFinishInterpolation,
                resetTracesTest,
                filePath,
                concurrentLayer
        );

        Toolkit.getDefaultToolkit().beep();
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
            final int tileToWinForStatistics,
            final int simulationsForStatistics,
            final List<Double> explorationRateList,
            final List<Double> explorationRateInitialValues,
            final List<Double> explorationRateFinalValues,
            final List<Integer> explorationRateStartInterpolation,
            final List<Integer> explorationRateFinishInterpolation,
            final boolean resetTracesTest,
            final String filePath,
            final boolean[] concurrentLayer
    ) {
        List<GeneratorConfig> experiments = new ArrayList<>();
        int                   number      = 0;
        for (int a = 0; a < repetitions; a++) {
            for (int i = 0; i < alphaList.size(); i++) {
                for (int j = 0; j < lambdaList.size(); j++) {
                    for (int k = 0; k < gammaList.size(); k++) {
                        for (int m = 0; m < annealingAlphaList.size(); m++) {
                            if (explorationRateList != null) {
                                for (int l = 0; l < explorationRateList.size(); l++) {
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
                            } else {
                                for (int n = 0; n < explorationRateInitialValues.size(); n++) {
                                    for (int o = 0; o < explorationRateFinalValues.size(); o++) {
                                        for (int p = 0; p < explorationRateStartInterpolation.size(); p++) {
                                            for (int q = 0; q < explorationRateFinishInterpolation.size(); q++) {
                                                number++;
                                                experiments.add(new GeneratorConfig(a,
                                                        classParameters,
                                                        alphaList.get(i),
                                                        annealingAlphaList.get(m),
                                                        lambdaList.get(j),
                                                        gammaList.get(k),
                                                        null,
                                                        explorationRateInitialValues.get(n),
                                                        explorationRateFinalValues.get(o),
                                                        explorationRateStartInterpolation.get(p),
                                                        explorationRateFinishInterpolation.get(q),
                                                        false,
                                                        number
                                                ));
                                                if (resetTracesTest) {
                                                    number++;
                                                    experiments.add(new GeneratorConfig(a,
                                                            classParameters,
                                                            alphaList.get(i),
                                                            annealingAlphaList.get(m),
                                                            lambdaList.get(j),
                                                            gammaList.get(k),
                                                            null,
                                                            explorationRateInitialValues.get(n),
                                                            explorationRateFinalValues.get(o),
                                                            explorationRateStartInterpolation.get(p),
                                                            explorationRateFinishInterpolation.get(q),
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

        ForkJoinPool forkJoinPool = new ForkJoinPool(maxTrainingThreads); //TODO arreglar problema de maxThreads internos
        try {
            forkJoinPool.submit(() -> stream.forEach(expConfig -> {
                try {
                    String explorationRateString;
                    if (explorationRateList != null) {
                        explorationRateString = "-explorationRate_" + expConfig.getExplorationRate();
                    } else {
                        explorationRateString = "-explorationRate_" +
                                                expConfig.getExplorationRateInitialValue() +
                                                '_' +
                                                expConfig.getExplorationRateFinalValue() +
                                                '_' +
                                                expConfig.getExplorationRateStartInterpolation() +
                                                '_' +
                                                expConfig.getExplorationRateFinishInterpolation();
                    }
                    String newFilePath = filePath + "AutomaticTests" + File.separator + expConfig.getRepetitions() + "-alpha_" +
                                         expConfig.getAlpha() +
                                         ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.getAnnealingAlpha() : "") +
                                         "-lambda_" +
                                         expConfig.getLambda() +
                                         "-gamma_" +
                                         expConfig.getGamma() +
                                         explorationRateString +
                                         "-resetTraces_" +
                                         expConfig.isResetTraces() +
                                         File.separator;
                    File newPath = new File(newFilePath);
                    if (!newPath.exists()) {
                        newPath.mkdirs();
                    }
                    LearningExperiment<?> cloneExperiment;
                    if (expConfig.getClassParameters() != null) {
                        cloneExperiment = (LearningExperiment<?>) experiment.newInstance(expConfig.getClassParameters());
                    } else {
                        cloneExperiment = (LearningExperiment<?>) experiment.newInstance();
                    }
                    cloneExperiment.setExperimentName(experimentName);
                    configAndExecute(expConfig.getNumber(),
                            cloneExperiment,
                            statisticsOnly,
                            runStatisticsForBackups,
                            createLogs,
                            expConfig.getLambda(),
                            expConfig.getAlpha(),
                            expConfig.getAnnealingAlpha(),
                            expConfig.getGamma(),
                            gamesToPlay,
                            saveEvery,
                            saveBackupEvery,
                            gamesToPlayPerThreadForStatistics,
                            tileToWinForStatistics,
                            simulationsForStatistics,
                            expConfig.getExplorationRate(),
                            expConfig.getExplorationRateInitialValue(),
                            expConfig.getExplorationRateFinalValue(),
                            expConfig.getExplorationRateStartInterpolation(),
                            expConfig.getExplorationRateFinishInterpolation(),
                            expConfig.isResetTraces(),
                            newFilePath,
                            concurrentLayer
                    );
                } catch (ClassCastException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            })).get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
