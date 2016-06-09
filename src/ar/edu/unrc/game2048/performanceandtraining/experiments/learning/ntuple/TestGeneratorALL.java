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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
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
        experiment.setTileToWinForStatistics(2_048);
        experiment.setResetEligibilitiTraces(resetEligibilitiTraces);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setSaveBackupEvery(saveBacupEvery);
        experiment.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        experiment.setExportToExcel(true);
        System.out.println("***************************************** Ejecutando " + filePath + " *****************************************");
        experiment.start(filePath, 0, true);
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
        int pcNumber = 0;
        boolean statistics = false;
        int maxTrainingThreads = 8;
        if ( args.length == 3 ) {
            pcNumber = Integer.parseInt(args[0]);
            statistics = Boolean.parseBoolean(args[1]);
            maxTrainingThreads = Integer.parseInt(args[2]);
        }
        List<Double> lambdaList = new LinkedList<>();
        List<Double> alphaList = new LinkedList<>();
        List<Double> gammaList = new LinkedList<>();
        List<Double> explorationRate = new LinkedList<>();

        //============================== configuraciones manuales ==================================
        if ( args.length == 0 ) {
//            statistics = true;
            statistics = false;
        }

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

        switch ( pcNumber ) {
            case 0: {
                runAllConfigs(maxTrainingThreads, "BasicLinear", new BasicLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                runAllConfigs(maxTrainingThreads, "BasicTanH", new BasicTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                runAllConfigs(maxTrainingThreads, "SymetryLinear", new SymetryLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                runAllConfigs(maxTrainingThreads, "SymetryTanH", new SymetryTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                break;
            }
            case 1: {
                runAllConfigs(maxTrainingThreads, "BasicLinear", new BasicLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                break;
            }
            case 2: {
                runAllConfigs(maxTrainingThreads, "BasicTanH", new BasicTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                break;
            }
            case 3: {
                runAllConfigs(maxTrainingThreads, "SymetryLinear", new SymetryLinear(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                break;
            }
            case 4: {
                runAllConfigs(maxTrainingThreads, "SymetryTanH", new SymetryTanH(), alphaList, lambdaList, gammaList, statisticsOnly, runStatisticsForBackups, createLogs, gamesToPlay, saveEvery, saveBackupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, explorationRate, filePath);
                break;
            }
            default: {
                throw new IllegalArgumentException("wrong pcNumber = " + pcNumber);
            }
        }
    }

    /**
     *
     * @param maxTrainingThreads
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
    public static void runAllConfigs(int maxTrainingThreads, String experimentName, LearningExperiment experiment, List<Double> alphaList, List<Double> lambdaList, List<Double> gammaList, boolean statisticsOnly, boolean runStatisticsForBackups, boolean createLogs, int gamesToPlay, int saveEvery, int saveBacupEvery, int gamesToPlayPerThreadForStatistics, int simulationsForStatistics, List<Double> explorationRateList, String filePath) {
        List<GeneratorConfig> experiments = new ArrayList<>();
        alphaList.stream().forEach((alpha) -> {
            lambdaList.stream().forEach((lambda) -> {
                gammaList.stream().forEach((gamma) -> {
                    explorationRateList.stream().forEach((explorationRate) -> {
                        experiments.add(new GeneratorConfig(alpha, lambda, gamma, explorationRate, false));
                        if ( explorationRate > 0 ) {
                            experiments.add(new GeneratorConfig(alpha, lambda, gamma, explorationRate, true));
                        }
                    });
                });
            });
        });

        Stream<GeneratorConfig> stream;
        if ( statisticsOnly ) {
            stream = experiments.stream();
        } else {
            stream = experiments.parallelStream();
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(maxTrainingThreads);
        try {
            forkJoinPool.submit(()
                    -> //parallel task here, for example
                    stream.forEach(exp -> {
                        try {
                            String newFilePath = filePath + "AutomaticTests" + File.separator + "alpha_" + exp.getAlpha() + "-lambda_" + exp.getLambda() + "-gamma_" + exp.getGamma() + "-explorationRate_" + exp.getExplorationRate() + "-resetTraces_" + exp.isResetTraces() + File.separator;
                            File newPath = new File(newFilePath);
                            if ( !newPath.exists() ) {
                                newPath.mkdirs();
                            }
                            LearningExperiment cloneExperiment = (LearningExperiment) experiment.clone();
                            cloneExperiment.setExperimentName(experimentName);
                            configAndExcecute(cloneExperiment, statisticsOnly, runStatisticsForBackups, createLogs, exp.getLambda(), exp.getAlpha(), exp.getGamma(), gamesToPlay, saveEvery, saveBacupEvery, gamesToPlayPerThreadForStatistics, simulationsForStatistics, exp.getExplorationRate(), exp.isResetTraces(), newFilePath);
                        } catch ( CloneNotSupportedException ex ) {
                            Logger.getLogger(TestGeneratorALL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    })
            ).get();
        } catch ( InterruptedException | ExecutionException ex ) {
            Logger.getLogger(TestGeneratorALL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class GeneratorConfig {

        private Double alpha;
        private Double explorationRate;
        private Double gamma;
        private Double lambda;
        private boolean resetTraces;

        private GeneratorConfig(Double alpha, Double lambda, Double gamma, Double explorationRate, boolean resetTraces) {
            this.alpha = alpha;
            this.lambda = lambda;
            this.gamma = gamma;
            this.explorationRate = explorationRate;
            this.resetTraces = resetTraces;
        }

        /**
         * @return the alpha
         */
        public Double getAlpha() {
            return alpha;
        }

        /**
         * @param alpha the alpha to set
         */
        public void setAlpha(Double alpha) {
            this.alpha = alpha;
        }

        /**
         * @return the explorationRate
         */
        public Double getExplorationRate() {
            return explorationRate;
        }

        /**
         * @param explorationRate the explorationRate to set
         */
        public void setExplorationRate(Double explorationRate) {
            this.explorationRate = explorationRate;
        }

        /**
         * @return the gamma
         */
        public Double getGamma() {
            return gamma;
        }

        /**
         * @param gamma the gamma to set
         */
        public void setGamma(Double gamma) {
            this.gamma = gamma;
        }

        /**
         * @return the lambda
         */
        public Double getLambda() {
            return lambda;
        }

        /**
         * @param lambda the lambda to set
         */
        public void setLambda(Double lambda) {
            this.lambda = lambda;
        }

        /**
         * @return the resetTraces
         */
        public boolean isResetTraces() {
            return resetTraces;
        }

        /**
         * @param resetTraces the resetTraces to set
         */
        public void setResetTraces(boolean resetTraces) {
            this.resetTraces = resetTraces;
        }
    }
}
