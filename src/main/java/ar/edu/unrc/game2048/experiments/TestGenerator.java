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

import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ar.edu.unrc.game2048.experiments.GeneratorConfig.runOneConfig;

/**
 * Tests parametrizables mediante parámetros, útil para correr variedades de simulaciones sin recompilar el programa.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TestGenerator {

    /**
     * @param args
     *
     * @throws Exception
     */
    public static
    void main( final String... args )
            throws Exception {
        //============================== fin de configuraciones manuales ==================================

        if ( args.length == 0 ) {
            System.err.println("No se encuentran parámetros de configuración");
            System.exit(-1);
        } else {

            final ArgumentLoader arguments = new ArgumentLoader(args);

            final String         experimentDirName       = arguments.getArg("experimentDirName");
            final List< String > experimentClassNameList = ArgumentLoader.parseStringArray(arguments.getArg("experimentClassNameList"));
            final boolean        createLogs              = Boolean.parseBoolean(arguments.getArg("createLogs"));
            final boolean        canCollectStatistics    = Boolean.parseBoolean(arguments.getArg("canCollectStatistics"));

            final int repetitions        = Integer.parseInt(arguments.getArg("repetitions"));
            final int maxTrainingThreads = Integer.parseInt(arguments.getArg("maxTrainingThreads"));

            final int gamesToPlay                  = Integer.parseInt(arguments.getArg("gamesToPlay"));
            final int gamesToPlayPerThreadForStats = Integer.parseInt(arguments.getArg("gamesToPlayPerThreadForStats"));
            final int saveEvery                    = Integer.parseInt(arguments.getArg("saveEvery"));
            final int saveBackupEvery              = Integer.parseInt(arguments.getArg("saveBackupEvery"));

            final boolean statisticsOnly           = Boolean.parseBoolean(arguments.getArg("statisticsOnly"));
            final int     simulationsForStatistics = Integer.parseInt(arguments.getArg("simulationsForStatistics"));
            final int     tileToWinForStatistics   = Integer.parseInt(arguments.getArg("tileToWinForStatistics"));
            final boolean runBackupStatistics      = Boolean.parseBoolean(arguments.getArg("runBackupStatistics"));

            final List< Double > lambdaList             = ArgumentLoader.parseDoubleArray(arguments.getArg("lambdaList"));
            final int            eligibilityTraceLength = Integer.parseInt(arguments.getArg("eligibilityTraceLength"));
            final boolean        replacingTraces        = Boolean.parseBoolean(arguments.getArg("replacingTraces"));
            final boolean        accumulatingTraces     = Boolean.parseBoolean(arguments.getArg("accumulatingTraces"));

            final List< Integer > annealingAlphaList                             =
                    ArgumentLoader.parseIntegerArray(arguments.getArg("annealingAlphaList"));
            final List< Double >  alphaList                                      = ArgumentLoader.parseDoubleArray(arguments.getArg("alphaList"));
            final List< Double >  gammaList                                      = ArgumentLoader.parseDoubleArray(arguments.getArg("gammaList"));
            final boolean[]       concurrentLayer                                =
                    ArgumentLoader.parseBooleanArray(arguments.getArg("concurrentLayerList"));
            final boolean         computeBestPossibleActionConcurrently          =
                    Boolean.parseBoolean(arguments.getArg("computeBestPossibleActionConcurrently"));
            final List< Double >  whenStartToExplore                             =
                    ArgumentLoader.parseDoubleArray(arguments.getArg("whenStartToExplore"));
            List< Integer >       interpolatedExplorationRateStartInterpolation  = null;
            List< Double >        interpolatedExplorationRateInitialValues       = null;
            List< Integer >       interpolatedExplorationRateFinishInterpolation = null;
            List< Double >        interpolatedExplorationRateFinalValues         = null;
            List< Double >        fixedExplorationRate                           = null;
            try {
                interpolatedExplorationRateInitialValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateInitialValueList"));
                interpolatedExplorationRateFinalValues = ArgumentLoader.parseDoubleArray(arguments.getArg("explorationRateFinalValuesList"));
                interpolatedExplorationRateStartInterpolation =
                        ArgumentLoader.parseIntegerArray(arguments.getArg("explorationRateStartInterpolationList"));
                interpolatedExplorationRateFinishInterpolation =
                        ArgumentLoader.parseIntegerArray(arguments.getArg("explorationRateFinishInterpolationList"));
                if ( ( interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateFinalValues.size() ) ||
                     ( interpolatedExplorationRateFinalValues.size() != interpolatedExplorationRateStartInterpolation.size() ) ||
                     ( interpolatedExplorationRateInitialValues.size() != interpolatedExplorationRateFinishInterpolation.size() ) ) {
                    System.err.println("La cantidad de parámetros de exploration rate no coinciden");
                    System.exit(-1);
                }
            } catch ( final Exception ignored ) {
                try {
                    fixedExplorationRate = ArgumentLoader.parseDoubleArray(arguments.getArg("fixedExplorationRateList"));
                } catch ( final Exception ignored2 ) {
                    System.err.println("No se encuentran parámetros para explorationRate constantes o variables");
                    System.exit(-1);
                }
            }
            long         milliseconds = System.currentTimeMillis();
            final String filePath     = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
            runAllConfigs(repetitions,
                    canCollectStatistics,
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
                    whenStartToExplore,
                    fixedExplorationRate,
                    interpolatedExplorationRateInitialValues,
                    interpolatedExplorationRateFinalValues,
                    interpolatedExplorationRateStartInterpolation,
                    interpolatedExplorationRateFinishInterpolation,
                    replacingTraces,
                    accumulatingTraces,
                    filePath,
                    concurrentLayer,
                    computeBestPossibleActionConcurrently);
            milliseconds = System.currentTimeMillis() - milliseconds;

            final long   hs      = milliseconds / 3_600_000L;
            final long   hsRest  = milliseconds % 3_600_000L;
            final long   min     = hsRest / 60_000L;
            final long   restMin = hsRest % 60_000L;
            final double seg     = restMin / 1_000.0d;

            final DecimalFormat formatter = new DecimalFormat("#.###");
            System.out.println("\ntiempo de ejecución = " + hs + "h " + min + "m " + formatter.format(seg) + "s (total: " + milliseconds + " ms).");
            Toolkit.getDefaultToolkit().beep();
        }
    }


    @SuppressWarnings( "ForLoopReplaceableByForEach" )
    protected static
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
            final List< Double > whenStartToExplore,
            final List< Double > explorationRateList,
            final List< Double > explorationRateInitialValues,
            final List< Double > explorationRateFinalValues,
            final List< Integer > explorationRateStartInterpolation,
            final List< Integer > explorationRateFinishInterpolation,
            final boolean replacingTraces,
            final boolean accumulatingTraces,
            final String filePath,
            final boolean[] concurrentLayer,
            final boolean computeBestPossibleActionConcurrently
    ) {
        final Collection< GeneratorConfig > experiments = new ArrayList<>();
        int                                 number      = 0;
        for ( int a = 0; a < repetitions; a++ ) {
            for ( int className = 0; className < experimentClassNameList.size(); className++ ) {
                for ( int i = 0; i < alphaList.size(); i++ ) {
                    for ( int j = 0; j < lambdaList.size(); j++ ) {
                        for ( int k = 0; k < gammaList.size(); k++ ) {
                            for ( int m = 0; m < annealingAlphaList.size(); m++ ) {
                                for ( int w = 0; w < whenStartToExplore.size(); w++ ) {
                                    if ( explorationRateList != null ) {
                                        for ( int l = 0; l < explorationRateList.size(); l++ ) {
                                            if ( lambdaList.get(j) == 0.0d ) {
                                                number++;
                                                experiments.add(new GeneratorConfig(a,
                                                        canCollectStatistics,
                                                        experimentClassNameList.get(className),
                                                        alphaList.get(i),
                                                        annealingAlphaList.get(m),
                                                        lambdaList.get(j),
                                                        eligibilityTraceLength,
                                                        gammaList.get(k),
                                                        whenStartToExplore.get(w),
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
                                                            whenStartToExplore.get(w),
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
                                                            whenStartToExplore.get(w),
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
                                                        if ( lambdaList.get(j) == 0.0d ) {
                                                            number++;
                                                            experiments.add(new GeneratorConfig(a,
                                                                    canCollectStatistics,
                                                                    experimentClassNameList.get(className),
                                                                    alphaList.get(i),
                                                                    annealingAlphaList.get(m),
                                                                    lambdaList.get(j),
                                                                    eligibilityTraceLength,
                                                                    gammaList.get(k),
                                                                    whenStartToExplore.get(w),
                                                                    null,
                                                                    explorationRateInitialValues.get(n),
                                                                    explorationRateFinalValues.get(o),
                                                                    explorationRateStartInterpolation.get(p),
                                                                    explorationRateFinishInterpolation.get(q),
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
                                                                        whenStartToExplore.get(w),
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
                                                                        whenStartToExplore.get(w),
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
        }

        if ( !statisticsOnly ) {
            try {
                final BlockingQueue< GeneratorConfig > queue = new ArrayBlockingQueue<>(experiments.size());
                queue.addAll(experiments);

                final ExecutorService pool = Executors.newFixedThreadPool(maxTrainingThreads);

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
                                    concurrentLayer,
                                    expConfig,
                                    computeBestPossibleActionConcurrently);
                        }
                    });
                }
                pool.shutdown();
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch ( final InterruptedException e ) {
                Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        experiments.forEach(expConfig -> runOneConfig(experimentDirName,
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
                concurrentLayer,
                expConfig,
                computeBestPossibleActionConcurrently));
    }
}
