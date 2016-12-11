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
package ar.edu.unrc.game2048.performanceandtraining.experiments;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.EncogNTupleLinearSimplified_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.EncogNTupleLinearWithBiasSimplified_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.NTupleBasicLinear;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.NTupleBasicLinearSimplified_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.NTupleBasicTanH;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.NTupleBasicTanHSimplified_512;

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
            final int eligibilityTraceLength,
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
            final String filePath,
            final boolean[] concurrentLayer,
            final boolean replaceEligibilityTraces
    ) {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setEligibilityTraceLength(eligibilityTraceLength);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
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
        experiment.start(numberForShow, filePath, 0, true, null, false);
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
        boolean statistics = false;
        //                boolean   statistics                   = true;
        int       repetitions                  = 1;
        int       maxTrainingThreads           = 8;
        boolean   doBackupStatistics           = true;
        String    experimentDirName            = "NTupleBasicTanH-Lambdas";
        String    experimentName               = "NTupleBasicTanH";
        String    experimentClass              = "NTupleBasicTanH";
        int       gamesToPlay                  = 500_000;
        int       saveEvery                    = 2_000;
        int       saveBackupEvery              = 10_000;
        int       tileToWinForStatistics       = 2048;
        boolean[] concurrentLayer              = {false, false};
        int       gamesToPlayPerThreadForStats = 100;
        boolean   replacingTraces              = true;
        boolean   accumulatingTraces           = true;

        lambdaList.add(0d);
        lambdaList.add(0.3d);
        lambdaList.add(0.6d);
        lambdaList.add(0.8d);

        int eligibilityTraceLengthList = -1;

        //        annealingAlphaList.add(2_000_000); //Sin annealing
        //        annealingAlphaList.add(400_000);
        //        annealingAlphaList.add(600_000);
        annealingAlphaList.add(500_000);

        alphaList.add(0.005d);

        gammaList.add(1d);

        // Exploration rates constantes
        interpolatedExplorationRateInitialValues = null;
        interpolatedExplorationRateFinalValues = null;
        interpolatedExplorationRateStartInterpolation = null;
        interpolatedExplorationRateFinishInterpolation = null;
        fixedExplorationRate.add(0d);

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
            case "NTupleBasicLinear": {
                classConstructor = NTupleBasicLinear.class.getConstructor();
                break;
            }
            case "NTupleBasicTanH": {
                classConstructor = NTupleBasicTanH.class.getConstructor();
                break;
            }
            case "NTupleBasicLinearSimplified_512": {
                classConstructor = NTupleBasicLinearSimplified_512.class.getConstructor();
                break;
            }
            case "EncogNTupleLinearSimplified_512": {
                classConstructor = EncogNTupleLinearSimplified_512.class.getConstructor(EncogNTupleLinearSimplified_512.PARAMETER_TYPE);
                classParameters = new Object[]{false};
                break;
            }
            case "EncogNTupleLinearWithBiasSimplified_512": {
                classConstructor = EncogNTupleLinearWithBiasSimplified_512.class.getConstructor(EncogNTupleLinearWithBiasSimplified_512.PARAMETER_TYPE);
                classParameters = new Object[]{true};
                break;
            }
            case "NTupleBasicTanHSimplified_512": {
                classConstructor = NTupleBasicTanHSimplified_512.class.getConstructor();
                break;
            }
            default: {
                throw new IllegalArgumentException("no se reconoce la clase: " + experimentClass);
            }
        }

        runAllConfigs(
                repetitions,
                maxTrainingThreads,
                experimentDirName,
                experimentName,
                classConstructor,
                classParameters,
                alphaList,
                annealingAlphaList,
                lambdaList,
                eligibilityTraceLengthList,
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
                replacingTraces,
                accumulatingTraces,
                filePath,
                concurrentLayer
        );

        Toolkit.getDefaultToolkit().beep();
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static
    void runAllConfigs(
            final int repetitions,
            final int maxTrainingThreads,
            final String experimentDirName,
            final String experimentName,
            final Constructor<?> experiment,
            final Object[] classParameters,
            final List<Double> alphaList,
            final List<Integer> annealingAlphaList,
            final List<Double> lambdaList,
            final int eligibilityTraceLengthList,
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
            final boolean replacingTraces,
            final boolean accumulatingTraces,
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
                                    if (lambdaList.get(j) == 0) {
                                        number++;
                                        experiments.add(new GeneratorConfig(
                                                a,
                                                classParameters,
                                                alphaList.get(i),
                                                annealingAlphaList.get(m),
                                                lambdaList.get(j),
                                                eligibilityTraceLengthList,
                                                gammaList.get(k),
                                                explorationRateList.get(l),
                                                null,
                                                null,
                                                null,
                                                null,
                                                false,
                                                number
                                        ));
                                    } else {
                                        if (accumulatingTraces) {
                                            number++;
                                            experiments.add(new GeneratorConfig(
                                                    a,
                                                    classParameters,
                                                    alphaList.get(i),
                                                    annealingAlphaList.get(m),
                                                    lambdaList.get(j),
                                                    eligibilityTraceLengthList,
                                                    gammaList.get(k),
                                                    explorationRateList.get(l),
                                                    null,
                                                    null,
                                                    null,
                                                    null,
                                                    false,
                                                    number
                                            ));
                                        }
                                        if (replacingTraces) {
                                            number++;
                                            experiments.add(new GeneratorConfig(
                                                    a,
                                                    classParameters,
                                                    alphaList.get(i),
                                                    annealingAlphaList.get(m),
                                                    lambdaList.get(j),
                                                    eligibilityTraceLengthList,
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
                            } else {
                                for (int n = 0; n < explorationRateInitialValues.size(); n++) {
                                    for (int o = 0; o < explorationRateFinalValues.size(); o++) {
                                        for (int p = 0; p < explorationRateStartInterpolation.size(); p++) {
                                            for (int q = 0; q < explorationRateFinishInterpolation.size(); q++) {
                                                if (lambdaList.get(j) == 0) {
                                                    if (accumulatingTraces) {
                                                        number++;
                                                        experiments.add(new GeneratorConfig(
                                                                a,
                                                                classParameters,
                                                                alphaList.get(i),
                                                                annealingAlphaList.get(m),
                                                                lambdaList.get(j),
                                                                eligibilityTraceLengthList,
                                                                gammaList.get(k),
                                                                null,
                                                                explorationRateInitialValues.get(n),
                                                                explorationRateFinalValues.get(o),
                                                                explorationRateStartInterpolation.get(p),
                                                                explorationRateFinishInterpolation.get(q),
                                                                false,
                                                                number
                                                        ));
                                                    }
                                                } else {
                                                    if (accumulatingTraces) {
                                                        number++;
                                                        experiments.add(new GeneratorConfig(
                                                                a,
                                                                classParameters,
                                                                alphaList.get(i),
                                                                annealingAlphaList.get(m),
                                                                lambdaList.get(j),
                                                                eligibilityTraceLengthList,
                                                                gammaList.get(k),
                                                                null,
                                                                explorationRateInitialValues.get(n),
                                                                explorationRateFinalValues.get(o),
                                                                explorationRateStartInterpolation.get(p),
                                                                explorationRateFinishInterpolation.get(q),
                                                                false,
                                                                number
                                                        ));
                                                    }
                                                    if (replacingTraces) {
                                                        number++;
                                                        experiments.add(new GeneratorConfig(
                                                                a,
                                                                classParameters,
                                                                alphaList.get(i),
                                                                annealingAlphaList.get(m),
                                                                lambdaList.get(j),
                                                                eligibilityTraceLengthList,
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
                    String newFilePath = filePath + experimentDirName + File.separator + expConfig.getRepetitions() + "-alpha_" +
                                         expConfig.getAlpha() +
                                         ((expConfig.getAnnealingAlpha() > 0) ? "-anneal_" + expConfig.getAnnealingAlpha() : "") +
                                         "-lambda_" +
                                         expConfig.getLambda() +
                                         "-gamma_" +
                                         expConfig.getGamma() + explorationRateString + "-replaceTraces_" + expConfig.isReplaceTraces() +
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
                            runStatisticsForBackups, createLogs, expConfig.getLambda(), expConfig.getEligibilityTraceLength(),
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
                            expConfig.getExplorationRateFinishInterpolation(), newFilePath, concurrentLayer, expConfig.isReplaceTraces()
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
