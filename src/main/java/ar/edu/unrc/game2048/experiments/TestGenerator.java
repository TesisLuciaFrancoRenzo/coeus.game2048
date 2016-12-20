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
package ar.edu.unrc.game2048.experiments;

import ar.edu.unrc.game2048.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.LearningExperiment;
import ar.edu.unrc.game2048.experiments.configurations.ntuples.*;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronBinary_2048;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronBoard_32768;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronNTupleLinearSimplified_512;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronNTupleTanHSimplified_512;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            final boolean canCollectStatistics,
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
            final boolean replaceEligibilityTraces,
            final boolean concurrencyInComputeBestPossibleAction
    ) {
        experiment.setCanCollectStatistics(canCollectStatistics);
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setEligibilityTraceLength(eligibilityTraceLength);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        experiment.setGamma(gamma);
        double[] alphas = { alpha, alpha };
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
        experiment.setConcurrencyInComputeBestPossibleAction(concurrencyInComputeBestPossibleAction);
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(tileToWinForStatistics);
        if ( annealingAlpha == NO_ANNEALING ) {
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
    String getMsj( Throwable ex ) {
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
    @SuppressWarnings( "UnusedAssignment" )
    public static
    void main( String[] args )
            throws Exception {
        String          filePath                                       = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
        List< Double >  lambdaList                                     = new ArrayList<>();
        List< String >  experimentClassNameList                        = new ArrayList<>();
        List< Double >  alphaList                                      = new ArrayList<>();
        List< Integer > annealingAlphaList                             = new ArrayList<>();
        List< Double >  gammaList                                      = new ArrayList<>();
        List< Double >  fixedExplorationRate                           = new ArrayList<>();
        List< Double >  interpolatedExplorationRateFinalValues         = new ArrayList<>();
        List< Integer > interpolatedExplorationRateFinishInterpolation = new ArrayList<>();
        List< Double >  interpolatedExplorationRateInitialValues       = new ArrayList<>();
        List< Integer > interpolatedExplorationRateStartInterpolation  = new ArrayList<>();
        String          experimentDirName;
        int             repetitions;
        int             maxTrainingThreads;
        int             simulationsForStatistics;
        boolean         runBackupStatistics;
        boolean         statisticsOnly;
        int             gamesToPlay;
        int             saveEvery;
        int             saveBackupEvery;
        int             tileToWinForStatistics;
        boolean[]       concurrentLayer;
        int             gamesToPlayPerThreadForStats;
        boolean         replacingTraces;
        boolean         accumulatingTraces;
        int             eligibilityTraceLength;
        boolean         createLogs;
        boolean         canCollectStatistics;
        boolean         concurrencyInComputeBestPossibleAction;
        //============================== fin de configuraciones manuales ==================================

        if ( args.length != 0 ) {

            lambdaList.clear();
            experimentClassNameList.clear();
            alphaList.clear();
            annealingAlphaList.clear();
            gammaList.clear();
            fixedExplorationRate.clear();
            interpolatedExplorationRateFinalValues.clear();
            interpolatedExplorationRateFinishInterpolation.clear();
            interpolatedExplorationRateInitialValues.clear();
            interpolatedExplorationRateStartInterpolation.clear();

            ArgumentLoader arguments = new ArgumentLoader(args);

            experimentDirName = arguments.getArg("experimentDirName");
            experimentClassNameList = ArgumentLoader.parseStringArray(arguments.getArg("experimentClassNameList"));
            createLogs = Boolean.parseBoolean(arguments.getArg("createLogs"));
            canCollectStatistics = Boolean.parseBoolean(arguments.getArg("canCollectStatistics"));

            repetitions = Integer.parseInt(arguments.getArg("repetitions"));
            maxTrainingThreads = Integer.parseInt(arguments.getArg("maxTrainingThreads"));

            gamesToPlay = Integer.parseInt(arguments.getArg("gamesToPlay"));
            gamesToPlayPerThreadForStats = Integer.parseInt(arguments.getArg("gamesToPlayPerThreadForStats"));
            saveEvery = Integer.parseInt(arguments.getArg("saveEvery"));
            saveBackupEvery = Integer.parseInt(arguments.getArg("saveBackupEvery"));

            statisticsOnly = Boolean.parseBoolean(arguments.getArg("statisticsOnly"));
            simulationsForStatistics = Integer.parseInt(arguments.getArg("simulationsForStatistics"));
            tileToWinForStatistics = Integer.parseInt(arguments.getArg("tileToWinForStatistics"));
            runBackupStatistics = Boolean.parseBoolean(arguments.getArg("runBackupStatistics"));

            lambdaList = ArgumentLoader.parseDoubleArray(arguments.getArg("lambdaList"));
            eligibilityTraceLength = Integer.parseInt(arguments.getArg("eligibilityTraceLength"));
            replacingTraces = Boolean.parseBoolean(arguments.getArg("replacingTraces"));
            accumulatingTraces = Boolean.parseBoolean(arguments.getArg("accumulatingTraces"));

            annealingAlphaList = ArgumentLoader.parseIntegerArray(arguments.getArg("annealingAlphaList"));
            alphaList = ArgumentLoader.parseDoubleArray(arguments.getArg("alphaList"));
            gammaList = ArgumentLoader.parseDoubleArray(arguments.getArg("gammaList"));
            concurrentLayer = ArgumentLoader.parseBooleanArray(arguments.getArg("concurrentLayerList"));
            concurrencyInComputeBestPossibleAction = Boolean.parseBoolean(arguments.getArg("concurrencyInComputeBestPossibleAction"));
            try {
                interpolatedExplorationRateInitialValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateInitialValueList"));
                interpolatedExplorationRateFinalValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateFinalValuesList"));
                interpolatedExplorationRateStartInterpolation =
                        ArgumentLoader.parseIntegerArray(arguments.getArg("explorationRateStartInterpolationList"));
                interpolatedExplorationRateFinishInterpolation =
                        ArgumentLoader.parseIntegerArray(arguments.getArg("explorationRateFinishInterpolationList"));
                if ( interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateFinalValues.size() ||
                     interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateStartInterpolation.size() ||
                     interpolatedExplorationRateInitialValues.size() != interpolatedExplorationRateFinishInterpolation.size() ) {
                    System.err.println("La cantidad de parámetros de exploration rate no coinciden");
                    System.exit(-1);
                }
                fixedExplorationRate = null;
            } catch ( Exception e ) {
                try {
                    interpolatedExplorationRateInitialValues = null;
                    interpolatedExplorationRateFinalValues = null;
                    interpolatedExplorationRateStartInterpolation = null;
                    interpolatedExplorationRateFinishInterpolation = null;
                    fixedExplorationRate = ArgumentLoader.parseDoubleArray(arguments.getArg("fixedExplorationRateList"));
                } catch ( Exception e2 ) {
                    System.err.println("No se encuentran parámetros para explorationRate constantes o variables");
                    System.exit(-1);
                }
            }

            runAllConfigs(repetitions, canCollectStatistics,
                    maxTrainingThreads,
                    experimentDirName,
                    experimentClassNameList,
                    alphaList,
                    annealingAlphaList,
                    lambdaList,
                    eligibilityTraceLength,
                    gammaList,
                    statisticsOnly,
                    runBackupStatistics,
                    createLogs,
                    gamesToPlay,
                    saveEvery,
                    saveBackupEvery,
                    gamesToPlayPerThreadForStats,
                    tileToWinForStatistics,
                    simulationsForStatistics,
                    fixedExplorationRate,
                    interpolatedExplorationRateInitialValues,
                    interpolatedExplorationRateFinalValues,
                    interpolatedExplorationRateStartInterpolation,
                    interpolatedExplorationRateFinishInterpolation,
                    replacingTraces,
                    accumulatingTraces, filePath, concurrentLayer, concurrencyInComputeBestPossibleAction);

            Toolkit.getDefaultToolkit().beep();
        } else {
            System.err.println("No se encuentran parámetros de configuración"); //TODO detallar
            System.exit(-1);
        }
    }

    @SuppressWarnings( "ForLoopReplaceableByForEach" )
    public static
    void runAllConfigs(
            final int repetitions,
            final boolean canCollectStatistics,
            final int maxTrainingThreads,
            final String experimentDirName,
            final List< String > experimentClassNameList,
            final List< Double > alphaList,
            final List< Integer > annealingAlphaList,
            final List< Double > lambdaList,
            final int eligibilityTraceLength,
            final List< Double > gammaList,
            final boolean statisticsOnly,
            final boolean runStatisticsForBackups,
            final boolean createLogs,
            final int gamesToPlay,
            final int saveEvery,
            final int saveBackupEvery,
            final int gamesToPlayPerThreadForStatistics,
            final int tileToWinForStatistics,
            final int simulationsForStatistics,
            final List< Double > explorationRateList,
            final List< Double > explorationRateInitialValues,
            final List< Double > explorationRateFinalValues,
            final List< Integer > explorationRateStartInterpolation,
            final List< Integer > explorationRateFinishInterpolation,
            final boolean replacingTraces,
            final boolean accumulatingTraces,
            final String filePath,
            final boolean[] concurrentLayer,
            final boolean concurrencyInComputeBestPossibleAction
    ) {
        List< GeneratorConfig > experiments = new ArrayList<>();
        int                     number      = 0;
        for ( int a = 0; a < repetitions; a++ ) {
            for ( int className = 0; className < experimentClassNameList.size(); className++ ) {
                for ( int i = 0; i < alphaList.size(); i++ ) {
                    for ( int j = 0; j < lambdaList.size(); j++ ) {
                        for ( int k = 0; k < gammaList.size(); k++ ) {
                            for ( int m = 0; m < annealingAlphaList.size(); m++ ) {
                                if ( explorationRateList != null ) {
                                    for ( int l = 0; l < explorationRateList.size(); l++ ) {
                                        if ( lambdaList.get(j) == 0 ) {
                                            number++;
                                            experiments.add(new GeneratorConfig(a,
                                                    canCollectStatistics,
                                                    experimentClassNameList.get(className),
                                                    alphaList.get(i),
                                                    annealingAlphaList.get(m),
                                                    lambdaList.get(j),
                                                    eligibilityTraceLength,
                                                    gammaList.get(k),
                                                    explorationRateList.get(l),
                                                    null,
                                                    null,
                                                    null,
                                                    null,
                                                    false,
                                                    number));
                                        } else {
                                            if ( accumulatingTraces ) {
                                                number++;
                                                experiments.add(new GeneratorConfig(a,
                                                        canCollectStatistics,
                                                        experimentClassNameList.get(className),
                                                        alphaList.get(i),
                                                        annealingAlphaList.get(m),
                                                        lambdaList.get(j),
                                                        eligibilityTraceLength,
                                                        gammaList.get(k),
                                                        explorationRateList.get(l),
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        false,
                                                        number));
                                            }
                                            if ( replacingTraces ) {
                                                number++;
                                                experiments.add(new GeneratorConfig(a,
                                                        canCollectStatistics,
                                                        experimentClassNameList.get(className),
                                                        alphaList.get(i),
                                                        annealingAlphaList.get(m),
                                                        lambdaList.get(j),
                                                        eligibilityTraceLength,
                                                        gammaList.get(k),
                                                        explorationRateList.get(l),
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        true,
                                                        number));
                                            }
                                        }
                                    }
                                } else {
                                    for ( int n = 0; n < explorationRateInitialValues.size(); n++ ) {
                                        for ( int o = 0; o < explorationRateFinalValues.size(); o++ ) {
                                            for ( int p = 0; p < explorationRateStartInterpolation.size(); p++ ) {
                                                for ( int q = 0; q < explorationRateFinishInterpolation.size(); q++ ) {
                                                    if ( lambdaList.get(j) == 0 ) {
                                                        if ( accumulatingTraces ) {
                                                            number++;
                                                            experiments.add(new GeneratorConfig(a,
                                                                    canCollectStatistics,
                                                                    experimentClassNameList.get(className),
                                                                    alphaList.get(i),
                                                                    annealingAlphaList.get(m),
                                                                    lambdaList.get(j),
                                                                    eligibilityTraceLength,
                                                                    gammaList.get(k),
                                                                    null,
                                                                    explorationRateInitialValues.get(n),
                                                                    explorationRateFinalValues.get(o),
                                                                    explorationRateStartInterpolation.get(p),
                                                                    explorationRateFinishInterpolation.get(q),
                                                                    false,
                                                                    number));
                                                        }
                                                    } else {
                                                        if ( accumulatingTraces ) {
                                                            number++;
                                                            experiments.add(new GeneratorConfig(a,
                                                                    canCollectStatistics,
                                                                    experimentClassNameList.get(className),
                                                                    alphaList.get(i),
                                                                    annealingAlphaList.get(m),
                                                                    lambdaList.get(j),
                                                                    eligibilityTraceLength,
                                                                    gammaList.get(k),
                                                                    null,
                                                                    explorationRateInitialValues.get(n),
                                                                    explorationRateFinalValues.get(o),
                                                                    explorationRateStartInterpolation.get(p),
                                                                    explorationRateFinishInterpolation.get(q),
                                                                    false,
                                                                    number));
                                                        }
                                                        if ( replacingTraces ) {
                                                            number++;
                                                            experiments.add(new GeneratorConfig(a,
                                                                    canCollectStatistics,
                                                                    experimentClassNameList.get(className),
                                                                    alphaList.get(i),
                                                                    annealingAlphaList.get(m),
                                                                    lambdaList.get(j),
                                                                    eligibilityTraceLength,
                                                                    gammaList.get(k),
                                                                    null,
                                                                    explorationRateInitialValues.get(n),
                                                                    explorationRateFinalValues.get(o),
                                                                    explorationRateStartInterpolation.get(p),
                                                                    explorationRateFinishInterpolation.get(q),
                                                                    true,
                                                                    number));
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
        }

        if ( !statisticsOnly ) {
            try {
                final BlockingQueue< GeneratorConfig > queue = new ArrayBlockingQueue<>(experiments.size());
                for ( GeneratorConfig config : experiments ) {
                    queue.add(config);
                }

                ExecutorService pool = Executors.newFixedThreadPool(maxTrainingThreads);

                for ( int i = 1; i <= maxTrainingThreads; i++ ) {
                    pool.execute(() -> {
                        GeneratorConfig expConfig;
                        while ( ( expConfig = queue.poll() ) != null ) {
                            runOneConfig(experimentDirName,
                                    statisticsOnly,
                                    false,
                                    createLogs,
                                    gamesToPlay,
                                    saveEvery,
                                    saveBackupEvery,
                                    0,
                                    tileToWinForStatistics,
                                    0,
                                    explorationRateList,
                                    filePath,
                                    concurrentLayer, expConfig, concurrencyInComputeBestPossibleAction);
                        }
                    });
                }
                pool.shutdown();
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch ( InterruptedException e ) {
                Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        experiments.forEach(expConfig -> {
            runOneConfig(experimentDirName,
                    true,
                    runStatisticsForBackups,
                    createLogs,
                    gamesToPlay,
                    saveEvery,
                    saveBackupEvery,
                    gamesToPlayPerThreadForStatistics,
                    tileToWinForStatistics,
                    simulationsForStatistics,
                    explorationRateList,
                    filePath,
                    concurrentLayer, expConfig, concurrencyInComputeBestPossibleAction);
        });
    }

    private static
    void runOneConfig(
            String experimentDirName,
            boolean statisticsOnly,
            boolean runStatisticsForBackups,
            boolean createLogs,
            int gamesToPlay,
            int saveEvery,
            int saveBackupEvery,
            int gamesToPlayPerThreadForStatistics,
            int tileToWinForStatistics,
            int simulationsForStatistics,
            List< Double > explorationRateList,
            String filePath,
            boolean[] concurrentLayer,
            GeneratorConfig expConfig,
            final boolean concurrencyInComputeBestPossibleAction
    ) {
        try {
            String explorationRateString;
            if ( explorationRateList != null ) {
                explorationRateString = "-explorationRate_" + expConfig.getExplorationRate();
            } else {
                explorationRateString = new StringBuilder().append("-explorationRate_")
                        .append(expConfig.getExplorationRateInitialValue())
                        .append('_')
                        .append(expConfig.getExplorationRateFinalValue())
                        .append('_')
                        .append(expConfig.getExplorationRateStartInterpolation())
                        .append('_')
                        .append(expConfig.getExplorationRateFinishInterpolation())
                        .toString();
            }
            String newFilePath = new StringBuilder().append(filePath)
                    .append(experimentDirName)
                    .append(File.separator)
                    .append(expConfig.getRepetitions())
                    .append("-alpha_")
                    .append(expConfig.getAlpha())
                    .append(( expConfig.getAnnealingAlpha() > 0 ) ? "-anneal_" + expConfig.getAnnealingAlpha() : "")
                    .append("-lambda_")
                    .append(expConfig.getLambda())
                    .append("-gamma_")
                    .append(expConfig.getGamma())
                    .append(explorationRateString)
                    .append("-replaceTraces_")
                    .append(expConfig.isReplaceTraces())
                    .append(File.separator)
                    .toString();
            File newPath = new File(newFilePath);
            if ( !newPath.exists() ) {
                newPath.mkdirs();
            }

            Constructor< ? > classConstructor;
            Object[]         classParameters = null;
            switch ( expConfig.getClassName() ) {
                case "ConfigNTupleBasicLinear_512": {
                    classConstructor = ConfigNTupleBasicLinear_512.class.getConstructor();
                    break;
                }
                case "ConfigNTupleBasicLinear_32768": {
                    classConstructor = ConfigNTupleBasicLinear_32768.class.getConstructor();
                    break;
                }
                case "ConfigNTupleBasicLinearSimplified_512": {
                    classConstructor = ConfigNTupleBasicLinearSimplified_512.class.getConstructor();
                    break;
                }
                case "ConfigNTupleBasicSigmoid_32768": {
                    classConstructor = ConfigNTupleBasicSigmoid_32768.class.getConstructor();
                    break;
                }
                case "ConfigNTupleBasicTanH_32768": {
                    classConstructor = ConfigNTupleBasicTanH_32768.class.getConstructor();
                    break;
                }
                case "ConfigNTupleBasicTanHSimplified_512": {
                    classConstructor = ConfigNTupleBasicTanHSimplified_512.class.getConstructor();
                    break;
                }
                case "ConfigNTupleSymmetricLinear_32768": {
                    classConstructor = ConfigNTupleSymmetricLinear_32768.class.getConstructor();
                    break;
                }
                case "ConfigNTupleSymmetricTanH_32768": {
                    classConstructor = ConfigNTupleSymmetricTanH_32768.class.getConstructor();
                    break;
                }
                case "ConfigPerceptronBinary_2048": {
                    classConstructor = ConfigPerceptronBinary_2048.class.getConstructor();
                    break;
                }
                case "ConfigPerceptronBoard_noBias_32768": {
                    classConstructor = ConfigPerceptronBoard_32768.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { false };
                    break;
                }
                case "ConfigPerceptronBoard_withBias_32768": {
                    classConstructor = ConfigPerceptronBoard_32768.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { true };
                    break;
                }
                case "ConfigPerceptronNTupleLinearSimplified_noBias_512": {
                    classConstructor = ConfigPerceptronNTupleLinearSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { false };
                    break;
                }
                case "ConfigPerceptronNTupleLinearSimplified_withBias_512": {
                    classConstructor = ConfigPerceptronNTupleLinearSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { true };
                    break;
                }
                case "ConfigPerceptronNTupleTanHSimplified_noBias_512": {
                    classConstructor = ConfigPerceptronNTupleTanHSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { false };
                    break;
                }
                case "ConfigPerceptronNTupleTanHSimplified_withBias_512": {
                    classConstructor = ConfigPerceptronNTupleTanHSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { true };
                    break;
                }

                default: {
                    throw new IllegalArgumentException("no se reconoce la clase: " + expConfig.getClassName());
                }
            }

            LearningExperiment cloneExperiment;
            if ( classParameters != null ) {
                cloneExperiment = (LearningExperiment) classConstructor.newInstance(classParameters);
            } else {
                cloneExperiment = (LearningExperiment) classConstructor.newInstance();
            }
            cloneExperiment.setExperimentName(expConfig.getClassName());
            configAndExecute(expConfig.isCanCollectStatistics(),
                    expConfig.getNumber(),
                    cloneExperiment,
                    statisticsOnly,
                    runStatisticsForBackups,
                    createLogs,
                    expConfig.getLambda(),
                    expConfig.getEligibilityTraceLength(),
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
                    newFilePath,
                    concurrentLayer, expConfig.isReplaceTraces(), concurrencyInComputeBestPossibleAction);
        } catch ( NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex ) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
