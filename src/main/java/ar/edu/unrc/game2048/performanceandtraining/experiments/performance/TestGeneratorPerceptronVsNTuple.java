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
package ar.edu.unrc.game2048.performanceandtraining.experiments.performance;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog.EncogNTupleLinearWithBiasSimplified_512;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.NTupleBasicLinearSimplified_512;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.unrc.game2048.performanceandtraining.experiments.TestGenerator.NO_ANNEALING;
import static ar.edu.unrc.game2048.performanceandtraining.experiments.TestGenerator.runAllConfigs;

/**
 * Test específicamente diseñado para comparar eficiencia de funciones de activación.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TestGeneratorPerceptronVsNTuple {

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
        String    testDirName            = "PerceptronVsNTuple";
        int       repetitions            = 4;
        int       maxTrainingThreads     = 8;
        int       gamesToPlay            = 12_000;
        int       saveEvery              = 1000;
        int       saveBackupEvery        = 300;
        int       tileToWinForStatistics = 512;
        boolean   resetTracesTest        = true;
        boolean   noResetTracesTest      = false;

        lambdaList.add(0.3d);
        alphaList.add(0.0025d);
        annealingAlphaList.add(NO_ANNEALING); //Sin annealing

        gammaList.add(1d);

        interpolatedExplorationRateInitialValues = null;
        interpolatedExplorationRateFinalValues = null;
        interpolatedExplorationRateStartInterpolation = null;
        interpolatedExplorationRateFinishInterpolation = null;
        fixedExplorationRate.add(0.1d);

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

        for (int i = 0; i < repetitions; i++) {
            runAllConfigs(
                    1,
                    maxTrainingThreads,
                    testDirName,
                    repetitions + "_NTupleBasicLinearSimplified_512",
                    NTupleBasicLinearSimplified_512.class.getConstructor(),
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
                    tileToWinForStatistics,
                    simulationsForStatistics,
                    fixedExplorationRate,
                    interpolatedExplorationRateInitialValues,
                    interpolatedExplorationRateFinalValues,
                    interpolatedExplorationRateStartInterpolation,
                    interpolatedExplorationRateFinishInterpolation,
                    resetTracesTest,
                    noResetTracesTest,
                    filePath, new boolean[]{false, false}
            );
        }
        for (int i = 0; i < repetitions; i++) {
            runAllConfigs(
                    1,
                    maxTrainingThreads,
                    testDirName,
                    repetitions + "_EncogNTupleLinearWithBiasSimplified_512",
                    EncogNTupleLinearWithBiasSimplified_512.class.getConstructor(EncogNTupleLinearWithBiasSimplified_512.PARAMETER_TYPE),
                    new Object[]{true},
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
                    noResetTracesTest,
                    filePath, new boolean[]{true, false}
            );
        }
        statisticsOnly = true;
        runStatisticsForBackups = true;
        gamesToPlayPerThreadForStatistics = 100;
        simulationsForStatistics = 8;

        for (int i = 0; i < repetitions; i++) {
            runAllConfigs(
                    1,
                    maxTrainingThreads,
                    testDirName,
                    repetitions + "_NTupleBasicLinearSimplified_512",
                    NTupleBasicLinearSimplified_512.class.getConstructor(),
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
                    tileToWinForStatistics,
                    simulationsForStatistics,
                    fixedExplorationRate,
                    interpolatedExplorationRateInitialValues,
                    interpolatedExplorationRateFinalValues,
                    interpolatedExplorationRateStartInterpolation,
                    interpolatedExplorationRateFinishInterpolation,
                    resetTracesTest,
                    noResetTracesTest,
                    filePath, new boolean[]{false, false}
            );
        }
        for (int i = 0; i < repetitions; i++) {
            runAllConfigs(
                    1,
                    maxTrainingThreads,
                    testDirName,
                    repetitions + "_EncogNTupleLinearWithBiasSimplified_512",
                    EncogNTupleLinearWithBiasSimplified_512.class.getConstructor(EncogNTupleLinearWithBiasSimplified_512.PARAMETER_TYPE),
                    new Object[]{true},
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
                    noResetTracesTest,
                    filePath, new boolean[]{true, false}
            );
        }

        Toolkit.getDefaultToolkit().beep();
    }
}
