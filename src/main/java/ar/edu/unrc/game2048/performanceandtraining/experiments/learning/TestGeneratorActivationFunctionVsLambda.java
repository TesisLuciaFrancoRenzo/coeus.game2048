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

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ar.edu.unrc.game2048.performanceandtraining.experiments.TestGenerator.NO_ANNEALING;
import static ar.edu.unrc.game2048.performanceandtraining.experiments.TestGenerator.runAllConfigs;

/**
 * Test específicamente diseñado para comparar eficiencia de funciones de activación.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class TestGeneratorActivationFunctionVsLambda {

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
        String    testDirName            = "ActivationFunctionVsLambda";
        int       repetitions            = 1;
        int       maxTrainingThreads     = 8;
        int       gamesToPlay            = 12_000;
        int       saveEvery              = 1_000;
        int       saveBackupEvery        = 300;
        int       tileToWinForStatistics = 512;
        boolean[] concurrentLayer        = {false, false};
        boolean   replacingTraces        = false;
        boolean   accumulatingTraces     = true;

        boolean statisticsOnly               = false;
        boolean runBackupStatistics          = true;
        int     gamesToPlayPerThreadForStats = 100;
        int     simulationsForStatistics     = 8;

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

        int eligibilityTraceLength = -1;

        alphaList.add(0.0025d);
        annealingAlphaList.add(NO_ANNEALING); //Sin annealing

        gammaList.add(1d);

        interpolatedExplorationRateInitialValues = null;
        interpolatedExplorationRateFinalValues = null;
        interpolatedExplorationRateStartInterpolation = null;
        interpolatedExplorationRateFinishInterpolation = null;
        fixedExplorationRate.add(0d);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================

        runAllConfigs(
                repetitions,
                maxTrainingThreads,
                testDirName, Arrays.asList("NTupleBasicLinearSimplified_512", "NTupleBasicTanHSimplified_512"),
                alphaList,
                annealingAlphaList,
                lambdaList, eligibilityTraceLength,
                gammaList,
                statisticsOnly, runBackupStatistics,
                createLogs,
                gamesToPlay,
                saveEvery,
                saveBackupEvery, gamesToPlayPerThreadForStats,
                tileToWinForStatistics,
                simulationsForStatistics,
                fixedExplorationRate,
                interpolatedExplorationRateInitialValues,
                interpolatedExplorationRateFinalValues,
                interpolatedExplorationRateStartInterpolation,
                interpolatedExplorationRateFinishInterpolation, replacingTraces,
                accumulatingTraces,
                filePath,
                concurrentLayer
        );

        Toolkit.getDefaultToolkit().beep();
    }
}
