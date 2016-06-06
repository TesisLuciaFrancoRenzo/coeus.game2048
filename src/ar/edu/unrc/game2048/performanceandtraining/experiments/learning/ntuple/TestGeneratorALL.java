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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class TestGeneratorALL {

    /**
     *
     * @param experiment
     * @param statisticsOnly
     * @param runStatisticsForBackups
     * @param createLogs
     * @param lambda
     * @param alpha
     * @param gamma
     * @param gamesToPlay
     * @param saveEvery
     * @param saveBacupEvery
     * @param gamesToPlayPerThreadForStatistics
     * @param simulationsForStatistics
     * @param explorationRate
     * @param resetEligibilitiTraces
     * @param filePath
     *
     * @throws java.lang.Exception
     */
    public static void configAndExcecute(
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
            boolean resetEligibilitiTraces,
            String filePath
    ) throws Exception {
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setGamma(gamma);
        double[] alphas = {alpha, alpha};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setExplorationRateToFixed(explorationRate);
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPosibleAction(true);
        boolean[] concurrentLayer = {false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(2_048);
        experiment.setResetEligibilitiTraces(resetEligibilitiTraces);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setSaveBackupEvery(saveBacupEvery);
        experiment.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        System.out.println("***************************************** Ejecutando " + filePath + " *****************************************");
        experiment.start(filePath, 0, true);
    }

    /**
     *
     * @param args <p>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath
                    = ".." + File.separator
                    + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }

        List<Double> lambdaList = new LinkedList<>();
        List<Double> alphaList = new LinkedList<>();
        List<Double> gammaList = new LinkedList<>();
        List<Double> explorationRate = new LinkedList<>();

        //============================== configuraciones manuales ==================================
//        boolean statistics = true;
        boolean statistics = false;

        int gamesToPlay = 2_000_000;
        int saveEvery = 5_000;
        int saveBackupEvery = 25_000;

        lambdaList.add(0.6d);
        lambdaList.add(0.7d);
        lambdaList.add(0.8d);

        alphaList.add(0.0025d);

        gammaList.add(0.9d);
        gammaList.add(1d);

        explorationRate.add(0d);
        explorationRate.add(0.1d);

        boolean createLogs = false;
        //============================== fin de configuraciones manuales ==================================
        boolean statisticsOnly;
        boolean runStatisticsForBackups;
        int gamesToPlayPerThreadForStatistics;
        int simulationsForStatistics;

        if ( statistics ) {
            statisticsOnly = true;
            runStatisticsForBackups = true;
            gamesToPlayPerThreadForStatistics = 1_000;
            simulationsForStatistics = 8;
        } else {
            statisticsOnly = false;
            runStatisticsForBackups = false;
            gamesToPlayPerThreadForStatistics = 0;
            simulationsForStatistics = 0;
        }

        runAllConfigs("BasicLinear", new BasicLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
        runAllConfigs("BasicTanH", new BasicTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
        runAllConfigs("SymetryLinear", new SymetryLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
        runAllConfigs("SymetryTanH", new SymetryTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
    }

    /**
     *
     * @param experimentName
     * @param experiment
     * @param alphaList
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
    public static void runAllConfigs(String experimentName, LearningExperiment experiment, List<Double> alphaList, List<Double> lambdaList, List<Double> gammaList, boolean statisticsOnly, boolean runStatisticsForBackups, boolean createLogs, int gamesToPlay, int saveEvery, int saveBacupEvery, int gamesToPlayPerThreadForStatistics, int simulationsForStatistics, List<Double> explorationRateList, String filePath) {
        alphaList.stream().forEach((alpha) -> {
            lambdaList.stream().forEach((lambda) -> {
                gammaList.stream().forEach((gamma) -> {
                    explorationRateList.stream().forEach((explorationRate) -> {
                        String newFilePath = filePath + "AutomaticTests" + File.separator + experimentName + File.separator;
                        File newPath = new File(newFilePath);
                        if ( !newPath.exists() ) {
                            newPath.mkdirs();
                        }
                        try {
                            experiment.setExperimentName("alpha_" + alpha + "-lambda_" + lambda + "-gamma_" + gamma + "-explorationRate_" + explorationRate + "-resetTraces_" + false);
                            configAndExcecute(experiment, statisticsOnly, runStatisticsForBackups, createLogs, lambda, alpha, gamma, gamesToPlay, saveEvery, saveBacupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, false, newFilePath);
                            if ( explorationRate > 0 ) {
                                experiment.setExperimentName("alpha_" + alpha + "-lambda_" + lambda + "-gamma_" + gamma + "-explorationRate_" + explorationRate + "-resetTraces_" + true);
                                configAndExcecute(experiment, statisticsOnly, runStatisticsForBackups, createLogs, lambda, alpha, gamma, gamesToPlay, saveEvery, saveBacupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, true, newFilePath);
                            }
                        } catch ( Exception ex ) {
                            printErrorInFile(ex, new File(newFilePath + "ERROR_DUMP.txt"));
                        }
                    });
                });
            });
        });
    }

    public final static DateFormat DATE_FILE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy'_'HH'h'-mm'm'-ss's'");
    public final static DateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static void printErrorInFile(Throwable ex, File dumpFile) {
        PrintStream printStream = null;
        try {
            if ( !dumpFile.exists() ) {
                dumpFile.createNewFile();
            }
            printStream = new PrintStream(new FileOutputStream(dumpFile, true), true, "UTF-8");
            String msj = "* " + DATE_FORMATTER.format(new Date()) + "----------------------------------------------------------------------------\n"
                    + getMsj(ex);
            printStream.println(msj);
            System.err.println(msj);
        } catch ( UnsupportedEncodingException | FileNotFoundException ex1 ) {
            Logger.getLogger(TestGeneratorALL.class.getName()).log(Level.SEVERE, null, ex1);
        } catch ( IOException ex1 ) {
            Logger.getLogger(TestGeneratorALL.class.getName()).log(Level.SEVERE, null, ex1);
        } finally {
            if ( printStream != null ) {
                printStream.close();
            }
        }
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
}
