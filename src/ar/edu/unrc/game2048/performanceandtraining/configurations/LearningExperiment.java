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
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.coeus.tdlearning.learning.EExplorationRateAlgorithms;
import ar.edu.unrc.coeus.tdlearning.learning.ELearningRateAdaptation;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.TestGenerator;
import static ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple.TestGenerator.getMsj;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class LearningExperiment<NeuralNetworkClass> {

    /**
     *
     */
    public static final String CONFIG = "_config";
    /**
     *
     */
    public static final DateFormat DATE_FILE_FORMATTER = new SimpleDateFormat(
            "dd-MM-yyyy'_'HH'h'-mm'm'-ss's'");
    /**
     *
     */
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss");

    /**
     *
     */
    public static final String ERROR_DUMP = "ERROR_DUMP";
    /**
     *
     */
    public static final String LAST_SAVE_DATA = "_last_save_data";

    /**
     *
     */
    public static final String RANDOM = "_random";

    /**
     *
     */
    public static final String TRAINED = "_trained";

    private static void printErrorInFile(Throwable ex,
            File dumpFile) {
        PrintStream printStream = null;
        try {
            if ( !dumpFile.exists() ) {
                dumpFile.createNewFile();
            }
            printStream = new PrintStream(new FileOutputStream(dumpFile, true),
                    true, "UTF-8");
            String msj = "* " + DATE_FORMATTER.format(new Date()) + "----------------------------------------------------------------------------\n"
                    + getMsj(ex);
            printStream.println(msj);
            System.err.println(msj);
        } catch ( UnsupportedEncodingException | FileNotFoundException ex1 ) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE,
                    null, ex1);
        } catch ( IOException ex1 ) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE,
                    null, ex1);
        } finally {
            if ( printStream != null ) {
                printStream.close();
            }
        }
    }
    private double[] alpha;
    private int annealingT;

    private double avgBestPossibleActionTimes;
    private double avgTrainingTimes;
    private int backupNumber;
    private LinkedList<Double> bestPossibleActionTimes;
    private boolean concurrencyInComputeBestPosibleAction = false;
    private boolean[] concurrencyInLayer;
    private long elapsedTime = 0;
    private String experimentName;
    private EExplorationRateAlgorithms explorationRate;
    private double explorationRateFinalValue;
    private int explorationRateFinishDecrementing;
    private double explorationRateInitialValue;
    private int explorationRateStartDecrementing;
    private boolean exportToExcel = true;
    private int gamesToPlay;
    private int gamesToPlayPerThreadForStatistics;
    private double gamma;
    private boolean initializePerceptronRandomized = false;
    private double lambda;
    private int lastSavedGamePlayedNumber;
    private TDLambdaLearning learningAlgorithm;
    private ELearningRateAdaptation learningRateAdaptation;
    private boolean logsActivated = false;
    private INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceFor2048;
    private String perceptronName;
    private boolean replaceEligibilityTraces = false;
    private boolean runStatisticsForBackups = false;
    private int saveBackupEvery = 0;
    private int saveEvery = 0;
    private int simulationsForStatistics;
    private boolean statisticsOnly = false;
    private int tileToWinForStatistics = 2_048;
    private int tileToWinForTraining;
    private LinkedList<Double> trainingTimes;
    /**
     *
     */
    protected StatisticExperiment statisticExperiment;

    /**
     *
     * @param logsActivated
     */
    public void createLogs(boolean logsActivated) {
        this.logsActivated = logsActivated;
    }

    /**
     *
     * @param experimentPath <p>
     * @return
     */
    public String createPathToDir(String experimentPath) {
        String dirPath = experimentPath
                + neuralNetworkInterfaceFor2048.getLibName() + File.separator
                + experimentName + File.separator;
        return dirPath;
    }

    /**
     * @return the alpha
     */
    public double[] getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double[] alpha) {
        this.alpha = alpha;
    }

    /**
     *
     * @return
     */
    public double getAvgBestPossibleActionTimes() {
        return avgBestPossibleActionTimes;
    }

    /**
     *
     * @return
     */
    public double getAvgTrainingTimes() {
        return avgTrainingTimes;
    }

    /**
     *
     * @param parallel
     */
    public void setConcurrencyInComputeBestPosibleAction(boolean parallel) {
        this.concurrencyInComputeBestPosibleAction = parallel;
    }

    /**
     *
     * @return
     */
    public boolean[] getConcurrencyInLayer() {
        return concurrencyInLayer;
    }

    /**
     *
     * @param concurrencyInLayer
     */
    public void setConcurrencyInLayer(boolean[] concurrencyInLayer) {
        this.concurrencyInLayer = concurrencyInLayer;
    }

    /**
     * @return the experimentName
     */
    public String getExperimentName() {
        return experimentName;
    }


    /**
     * @param experimentName the experimentName to set
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }
    /**
     * @param experimentClass
     */
    public void setExperimentName(Class experimentClass) {
        String className = experimentClass.getName();
        int lastDot = className.lastIndexOf('.');
        if ( lastDot != -1 ) {
            className = className.substring(lastDot + 1);
        }
        this.experimentName = className;
    }

    /**
     *
     * @param value
     */
    public void setExplorationRateToFixed(double value) {
        if ( value < 0 || value > 1 ) {
            throw new IllegalArgumentException(
                    "value debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.fixed;
        this.explorationRateInitialValue = value;
    }

    /**
     * @param exportToExcel the exportToExcel to set
     */
    public void setExportToExcel(boolean exportToExcel) {
        this.exportToExcel = exportToExcel;
    }

    /**
     * @return the gamesToPlayPerThreadForStatistics
     */
    public int getGamesToPlayPerThreadForStatistics() {
        return gamesToPlayPerThreadForStatistics;
    }

    /**
     * @param gamesToPlayPerThreadForStatistics the gamesToPlayPerThreadForStatistics to set
     */
    public void setGamesToPlayPerThreadForStatistics(
            int gamesToPlayPerThreadForStatistics) {
        this.gamesToPlayPerThreadForStatistics = gamesToPlayPerThreadForStatistics;
    }

    /**
     * @return the gamma
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * @param gamma the gamma to set
     */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    /**
     *
     * @param randomized
     */
    public void setInitializePerceptronRandomized(boolean randomized) {
        initializePerceptronRandomized = randomized;
    }

    /**
     * @return the lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * @param lambda the lambda to set
     */
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    /**
     * @return the learningRateAdaptation
     */
    public ELearningRateAdaptation getLearningRateAdaptation() {
        return learningRateAdaptation;
    }

    /**
     * @param learningRateAdaptation the learningRateAdaptation to set
     */
    public void setLearningRateAdaptation(
            ELearningRateAdaptation learningRateAdaptation) {
        this.learningRateAdaptation = learningRateAdaptation;
    }

    /**
     *
     * @param annealingT
     */
    public void setLearningRateAdaptationToAnnealing(int annealingT) {
        this.learningRateAdaptation = ELearningRateAdaptation.annealing;
        this.annealingT = annealingT;
    }

    /**
     * @return the perceptronName
     */
    public String getPerceptronName() {
        return perceptronName;
    }

    /**
     * @param perceptronName the perceptronName to set
     */
    public void setPerceptronName(String perceptronName) {
        this.perceptronName = perceptronName;
    }

    /**
     * @return the saveBackupEvery
     */
    public int getSaveBackupEvery() {
        return saveBackupEvery;
    }

    /**
     * @param saveBackupEvery the saveBackupEvery to set
     */
    public void setSaveBackupEvery(int saveBackupEvery) {
        this.saveBackupEvery = saveBackupEvery;
    }

    /**
     * @return the saveEvery
     */
    public int getSaveEvery() {
        return saveEvery;
    }

    /**
     * @param saveEvery the saveEvery to set
     */
    public void setSaveEvery(int saveEvery) {
        this.saveEvery = saveEvery;
    }

    /**
     *
     * @param winValue
     */
    public void setTileToWinForStatistics(int winValue) {
        tileToWinForStatistics = winValue;
    }

    /**
     * you must initialize:
     * <ul>
     * <li>private double alpha;</li>
     * <li>private double lambda;</li>
     * <li>private int gamesToPlay;</li>
     * <li>private int saveBackupEvery;</li>
     * <li>private int tileToWinForTraining;</li>
     * <li>private String experimentName;</li>
     * <li>private String perceptronName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration; </li>
     * <li>private INeuralNetworkInterfaceFor2048 neuralNetworkInterfaceFor2048;</li>
     * <li>private TDTrainerMethod trainerMethod;</li>
     * <li>private int gamesToPlayPerThreadForStatistics;</li>
     * <li>private int simulationsForStatistics;</li>
     * <li>private boolean statisticsOnly;</li>
     * <li>private TDLambdaLearning learningAlgorithm</li>
     * <p>
     * </ul>
     * <p>
     */
    public abstract void initialize();

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(
            INeuralNetworkInterface perceptronInterface);

    /**
     *
     * @param nTupleSystem <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(
            NTupleSystem nTupleSystem);

    /**
     * @return the replaceEligibilityTraces
     */
    public boolean isReplaceEligibilityTraces() {
        return replaceEligibilityTraces;
    }

    /**
     * @param replaceEligibilityTraces the replaceEligibilityTraces to set
     */
    public void setReplaceEligibilityTraces(boolean replaceEligibilityTraces) {
        this.replaceEligibilityTraces = replaceEligibilityTraces;
    }

    /**
     * @return the runStatisticsForBackups
     */
    public boolean isRunStatisticsForBackups() {
        return runStatisticsForBackups;
    }

    /**
     * @param runStatisticsForBackups the runStatisticsForBackups to set
     */
    public void setRunStatisticsForBackups(boolean runStatisticsForBackups) {
        this.runStatisticsForBackups = runStatisticsForBackups;
    }

    /**
     * @return the statisticsOnly
     */
    public boolean isStatisticsOnly() {
        return statisticsOnly;
    }

    /**
     * @param statisticsOnly the statisticsOnly to set
     */
    public void setStatisticsOnly(boolean statisticsOnly) {
        this.statisticsOnly = statisticsOnly;
    }

    /**
     *
     * @param initialValue
     * @param startDecrementing
     * @param finalValue
     * @param finishDecrementing
     */
    public void setExplorationRate(double initialValue,
            int startDecrementing,
            double finalValue,
            int finishDecrementing) {
        if ( initialValue < 0 || initialValue > 1 ) {
            throw new IllegalArgumentException(
                    "initialValue debe estar en el intervalo [0,1]");
        }
        if ( finalValue < 0 || finalValue > 1 ) {
            throw new IllegalArgumentException(
                    "finalValue debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.linear;
        this.explorationRateInitialValue = initialValue;
        this.explorationRateStartDecrementing = startDecrementing;
        this.explorationRateFinalValue = finalValue;
        this.explorationRateFinishDecrementing = finishDecrementing;
    }

    /**
     *
     */
    public void setLearningRateAdaptationToFixed() {
        this.learningRateAdaptation = ELearningRateAdaptation.fixed;
    }

    /**
     *
     * @param numberForShow
     * @param experimentPath
     * @param delayPerMove
     * @param createPerceptronFile
     * @param errorDumpDir
     */
    public void start(int numberForShow,
            String experimentPath,
            int delayPerMove,
            boolean createPerceptronFile,
            String errorDumpDir) {
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException(
                    "experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        initialize();
        runExperiment(numberForShow, experimentPath, delayPerMove,
                createPerceptronFile, errorDumpDir);
    }

    /**
     *
     * @param game
     * @param printStream
     * @param randomPerceptronFile
     * @param perceptronFile
     * @param lastSaveDataFile
     * @param filePath
     * @param dateFormater
     * @param zeroNumbers          <p>
     * @param numberForShow
     *
     * @throws Exception
     */
    public void training(
            int numberForShow,
            Game2048<NeuralNetworkClass> game,
            final PrintStream printStream,
            File randomPerceptronFile,
            File perceptronFile,
            File lastSaveDataFile,
            String filePath,
            SimpleDateFormat dateFormater,
            int zeroNumbers
    ) throws Exception {
        File perceptronFileBackup;
        switch ( this.learningRateAdaptation ) {
            case fixed: {
                learningAlgorithm.setFixedLearningRate();
                break;
            }
            case annealing: {
                learningAlgorithm.setAnnealingLearningRate(
                        annealingT);
                break;
            }
        }
        switch ( this.explorationRate ) {
            case fixed: {
                learningAlgorithm.setFixedExplorationRate(
                        this.explorationRateInitialValue);
                break;
            }
            case linear: {
                learningAlgorithm.setLinearExplorationRate(
                        this.explorationRateInitialValue,
                        this.explorationRateStartDecrementing,
                        this.explorationRateFinalValue,
                        this.explorationRateFinishDecrementing
                );
                break;
            }
        }

        if ( learningAlgorithm.canCollectStatistics() ) {
            bestPossibleActionTimes = new LinkedList<>();
            trainingTimes = new LinkedList<>();
        }

        for ( int i = lastSavedGamePlayedNumber + 1; i <= gamesToPlay; i++ ) {
            long start = System.nanoTime();
            learningAlgorithm.solveAndTrainOnce(game, i);
            elapsedTime += System.nanoTime() - start;
            if ( learningAlgorithm.canCollectStatistics() ) {
                double avg = 0;
                for ( Long sample : learningAlgorithm.
                        getStatisticsBestPossibleActionTimes() ) {
                    avg += sample;
                }
                avg /= (learningAlgorithm.getStatisticsBestPossibleActionTimes().
                        size() * 1d);
                bestPossibleActionTimes.add(avg);

                avg = 0;
                for ( Long sample : learningAlgorithm.
                        getStatisticsTrainingTimes() ) {
                    avg += sample;
                }
                avg /= (learningAlgorithm.getStatisticsTrainingTimes().size() * 1d);
                trainingTimes.add(avg);
            }

            int percent = (int) (((i * 1d) / (gamesToPlay * 1d)) * 100d);
            if ( numberForShow != -1 ) {
                System.out.println(
                        numberForShow + "- Juego número " + i + " (" + percent + "%)    puntaje = " + game.
                        getScore() + "    ficha max = " + game.getMaxNumber() + "    turno alcanzado = " + game.
                        getLastTurn() + "      current alpha = " + Arrays.
                        toString(learningAlgorithm.getCurrentAlpha()));
            } else {
                System.out.println(
                        "Juego número " + i + " (" + percent + "%)    puntaje = " + game.
                        getScore() + "    ficha max = " + game.getMaxNumber() + "    turno alcanzado = " + game.
                        getLastTurn() + "      current alpha = " + Arrays.
                        toString(learningAlgorithm.getCurrentAlpha()));
            }
            if ( printStream != null ) {
                printStream.
                        println(game.getScore() + "\t" + game.getMaxNumber());
            }
            boolean writeConfig = false;
            if ( i % saveEvery == 0 || i % saveBackupEvery == 0 ) {
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(perceptronFile);
                System.out.println(
                        "============ Perceptron Exportado Exitosamente (SAVE) ============");
                writeConfig = true;
            }
            if ( i % saveBackupEvery == 0 ) {
                backupNumber++;
                perceptronFileBackup = new File(
                        filePath + TRAINED + "_BackupN-" + String.format(
                                "%0" + zeroNumbers + "d", backupNumber)
                        + ".ser");
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(
                        perceptronFileBackup);
                System.out.println(
                        "============ Perceptron Exportado Exitosamente (BACKUP " + backupNumber + ") ============");
                writeConfig = true;
            }
            if ( writeConfig ) {
                try ( BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(
                                lastSaveDataFile), "UTF-8")) ) {
                    out.write(Integer.toString(i) + "\n" + Integer.toString(
                            backupNumber) + "\n" + Long.toString(elapsedTime));
                }
            }
        }
    }

    private void saveConfigFile(File configFile) throws Exception {
        try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(configFile), "UTF-8")) ) {
            out.write("experimentName: " + experimentName + "\n");
            out.write("perceptronName: " + perceptronName + "\n");
            out.write("tileToWinForTraining: " + tileToWinForTraining + "\n");
            out.
                    write("tileToWinForStatistics: " + tileToWinForStatistics + "\n");
            out.write("gamesToPlay: " + gamesToPlay + "\n");
            out.write(
                    "gamesToPlayPerThreadForStatistics: " + gamesToPlayPerThreadForStatistics + "\n");
            out.write("statisticsOnly: " + statisticsOnly + "\n");
            out.write(
                    "simulationsForStatistics: " + simulationsForStatistics + "\n");
            out.write("saveEvery: " + saveEvery + "\n");
            out.write("saveBackupEvery: " + saveBackupEvery + "\n");
            out.write("alpha: " + Arrays.toString(alpha) + "\n");
            out.write("gamma: " + gamma + "\n");
            out.write("lambda: " + lambda + "\n");
            out.write("annealingT: " + annealingT + "\n");
            out.
                    write("learningRateAdaptation: " + learningRateAdaptation + "\n");
            out.write(
                    "initializePerceptronRandomized: " + initializePerceptronRandomized + "\n");
            out.
                    write("replaceEligibilityTraces: " + replaceEligibilityTraces + "\n");
            out.write(
                    "concurrencyInComputeBestPosibleAction: " + concurrencyInComputeBestPosibleAction + "\n");
            out.write("concurrencyInLayer: " + Arrays.toString(
                    concurrencyInLayer) + "\n");
            out.write("explorationRate: " + explorationRate + "\n");
            out.write(
                    "explorationRateFinalValue: " + explorationRateFinalValue + "\n");
            out.write(
                    "explorationRateInitialValue: " + explorationRateInitialValue + "\n");
            out.write(
                    "explorationRateStartDecrementing: " + explorationRateStartDecrementing + "\n");
            out.write(
                    "explorationRateFinishDecrementing: " + explorationRateFinishDecrementing + "\n");
            out.write("\n");
            out.write(
                    "Operating system Name: " + System.getProperty("os.name") + "\n");
            out.write(
                    "Operating system type: " + System.getProperty("os.arch") + "\n");
            out.write("Operating system version: " + System.getProperty(
                    "os.version") + "\n");
            if ( System.getProperty("os.name").matches(".*[Ww]indows.*") ) {
                Process command = Runtime.getRuntime().exec("wmic cpu get name");
                try ( BufferedReader in = new BufferedReader(
                        new InputStreamReader(command.getInputStream())) ) {
                    String line;
                    while ( (line = in.readLine()) != null ) {
                        if ( !line.isEmpty() && !line.contains("Name") ) {
                            out.write("CPU: " + line.trim() + "\n");
                            break;
                        }
                    }
                }
            } else if ( System.getProperty("os.name").matches(".*[Ll]inux.*") ) {
                Process command = Runtime.getRuntime().exec("cat /proc/cpuinfo");
                try ( BufferedReader in = new BufferedReader(
                        new InputStreamReader(command.getInputStream())) ) {
                    String line;
                    while ( (line = in.readLine()) != null ) {
                        if ( !line.isEmpty() && line.matches(
                                ".*model name\\s*:.*") ) {
                            int i = line.indexOf(':');
                            out.write(
                                    "CPU: " + line.substring(i + 1).trim() + "\n");
                            break;
                        }
                    }
                }
            }
            out.write("Available processors (cores): " + Runtime.getRuntime().
                    availableProcessors() + "\n");
            /* This will return Long.MAX_VALUE if there is no preset limit */
            long maxMemory = Runtime.getRuntime().maxMemory();
            /* Maximum amount of memory the JVM will attempt to use */
            out.write(
                    "Maximum memory (bytes): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory) + "\n");
            /* Total memory currently available to the JVM */
            out.write("Total memory available to JVM (bytes): " + Runtime.
                    getRuntime().totalMemory() + "\n");
        }
    }

    /**
     * @return the annealingT
     */
    protected int getAnnealingT() {
        return annealingT;
    }

    /**
     * @param annealingT the annealingT to set
     */
    protected void setAnnealingT(int annealingT) {
        this.annealingT = annealingT;
    }

    /**
     * @return the gamesToPlay
     */
    protected int getGamesToPlay() {
        return gamesToPlay;
    }

    /**
     * @param gamesToPlay the gamesToPlay to set
     */
    public void setGamesToPlay(int gamesToPlay) {
        this.gamesToPlay = gamesToPlay;
    }

    /**
     * @return the learningAlgorithm
     */
    protected TDLambdaLearning getLearningAlgorithm() {
        return learningAlgorithm;
    }

    /**
     * @param learningAlgorithm the learningAlgorithm to set
     */
    protected void setLearningAlgorithm(TDLambdaLearning learningAlgorithm) {
        this.learningAlgorithm = learningAlgorithm;
    }

    /**
     * @return the neuralNetworkInterfaceFor2048
     */
    protected INeuralNetworkInterfaceFor2048<NeuralNetworkClass> getNeuralNetworkInterfaceFor2048() {
        return neuralNetworkInterfaceFor2048;
    }

    /**
     * @param neuralNetworkInterfaceFor2048 the neuralNetworkInterfaceFor2048 to set
     */
    protected void setNeuralNetworkInterfaceFor2048(
            INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceFor2048) {
        this.neuralNetworkInterfaceFor2048 = neuralNetworkInterfaceFor2048;
    }

    /**
     * @return the simulationsForStatistics
     */
    protected int getSimulationsForStatistics() {
        return simulationsForStatistics;
    }

    /**
     * @param simulationsForStatistics the simulationsForStatistics to set
     */
    public void setSimulationsForStatistics(int simulationsForStatistics) {
        this.simulationsForStatistics = simulationsForStatistics;
    }

    /**
     * @return the statisticExperiment
     */
    protected StatisticExperiment getStatisticExperiment() {
        return statisticExperiment;
    }

    /**
     * @return the tileToWinForTraining
     */
    protected int getTileToWinForTraining() {
        return tileToWinForTraining;
    }

    /**
     * @param tileToWinForTraining the tileToWinForTraining to set
     */
    public void setTileToWinForTraining(int tileToWinForTraining) {
        this.tileToWinForTraining = tileToWinForTraining;
    }

    /**
     *
     * @param numberForShow
     * @param experimentPath
     * @param delayPerMove         <p>
     * @param createPerceptronFile
     * @param errorDumpDir
     */
    @SuppressWarnings( "static-access" )
    protected void runExperiment(int numberForShow,
            String experimentPath,
            int delayPerMove,
            boolean createPerceptronFile,
            String errorDumpDir) {
        if ( saveEvery == 0 ) {
            throw new IllegalArgumentException(
                    "se debe configurar cada cuanto guardar el perceptron mediante la variable saveEvery");
        }
        if ( saveBackupEvery == 0 ) {
            throw new IllegalArgumentException(
                    "se debe configurar cada cuanto guardar backups del perceptron mediante la variable saveBackupEvery");
        }
        System.out.println(
                "Starting " + this.getPerceptronName() + ((numberForShow == -1) ? "" : " Trainer Nº " + numberForShow));
        String dirPath = createPathToDir(experimentPath);
        String bugFilePath;
        if ( errorDumpDir == null ) {
            bugFilePath = dirPath + ERROR_DUMP + ".txt";
        } else {
            bugFilePath = errorDumpDir + ERROR_DUMP + ".txt";
        }
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat(
                    "dd-MM-yyyy_HH'h'mm'm'ss's'");
            Date now = new Date();
            if ( createPerceptronFile ) {
                File dirPathFile = new File(dirPath);
                if ( !dirPathFile.exists() ) {
                    dirPathFile.mkdirs();
                }
            }
            String filePath = dirPath + perceptronName;
            File perceptronFile = new File(filePath + TRAINED + ".ser");
            File lastSaveDataFile = new File(filePath + LAST_SAVE_DATA + ".txt");
            File configFile;
            if ( errorDumpDir == null ) {
                configFile = new File(filePath + CONFIG + ".txt");
            } else {
                configFile = new File(errorDumpDir + CONFIG + ".txt");
            }
            backupNumber = 0;
            lastSavedGamePlayedNumber = 0;
            elapsedTime = 0;
            if ( lastSaveDataFile.exists() ) {
                try ( BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(
                                lastSaveDataFile), "UTF-8")) ) {
                    String line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException(
                                "el archivo de configuracion no tiene un formato válido");
                    }
                    this.lastSavedGamePlayedNumber = Integer.parseInt(line);
                    line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException(
                                "el archivo de configuracion no tiene un formato válido");
                    }
                    this.backupNumber = Integer.parseInt(line);
                    line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException(
                                "el archivo de configuracion no tiene un formato válido");
                    }
                    this.elapsedTime = Long.parseLong(line);
                }
            }

            int zeroNumbers = 1;
            if ( !this.statisticsOnly ) {
                zeroNumbers = Integer.toString(
                        this.gamesToPlay / this.saveBackupEvery).length();
            }

            boolean backupRandomPerceptron = false;
            File randomPerceptronFile = null;
            if ( createPerceptronFile ) {
                randomPerceptronFile = new File(filePath + RANDOM + ".ser");
                if ( !perceptronFile.exists() ) {
                    if ( randomPerceptronFile.exists() ) {
                        randomPerceptronFile.delete();
                    }
                    backupRandomPerceptron = true;
                }
            }

            // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
            // En caso contrario creamos un perceptron vacio, inicializado al azar
            neuralNetworkInterfaceFor2048.loadOrCreatePerceptron(perceptronFile,
                    this.initializePerceptronRandomized, createPerceptronFile);
            //FIXME que hacer si esta ROTO? solucionar esto

            //creamos una interfaz de comunicacion entre la red neuronal de encog y el algoritmo de entrenamiento
            if ( backupRandomPerceptron ) {
                //guardamos el perceptron inicial para ahcer estadisticas
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(
                        randomPerceptronFile);
            }

            if ( neuralNetworkInterfaceFor2048.getPerceptronInterface() != null ) {
                this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(
                        neuralNetworkInterfaceFor2048.getPerceptronInterface()));
                this.learningAlgorithm.setComputeParallelBestPossibleAction(
                        concurrencyInComputeBestPosibleAction);
            }
            if ( neuralNetworkInterfaceFor2048.getNTupleConfiguration() != null ) {
                this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(
                        neuralNetworkInterfaceFor2048.getNTupleConfiguration().
                        getNTupleSystem()));
                this.learningAlgorithm.setComputeParallelBestPossibleAction(
                        concurrencyInComputeBestPosibleAction);
            }

            if ( learningAlgorithm == null && !this.statisticsOnly ) {
                throw new IllegalArgumentException(
                        "learningAlgorithm no puede ser null");
            }

            saveConfigFile(configFile);

            System.out.println("Training...");

            //creamos un archivo de logs para acumular estadisticas
            File logFile = new File(
                    filePath + "_" + dateFormater.format(now) + "_LOG" + ".txt");

            //creamos el juego
            Game2048<NeuralNetworkClass> game = new Game2048<>(
                    neuralNetworkInterfaceFor2048.getPerceptronConfiguration(),
                    neuralNetworkInterfaceFor2048.getNTupleConfiguration(),
                    tileToWinForTraining, delayPerMove);

            if ( !this.statisticsOnly ) {
                //comenzamos a entrenar y guardar estadisticas en el archivo de log
                if ( logsActivated ) {
                    try ( PrintStream printStream = new PrintStream(logFile,
                            "UTF-8") ) {
                        training(numberForShow, game, printStream,
                                randomPerceptronFile, perceptronFile,
                                lastSaveDataFile, filePath, dateFormater,
                                zeroNumbers);
                    }
                } else {
                    training(numberForShow, game, null, randomPerceptronFile,
                            perceptronFile, lastSaveDataFile, filePath,
                            dateFormater, zeroNumbers);
                }
                if ( learningAlgorithm.canCollectStatistics() ) {
                    avgBestPossibleActionTimes = 0d;
                    for ( Double sample : this.bestPossibleActionTimes ) {
                        avgBestPossibleActionTimes += sample;
                    }
                    avgBestPossibleActionTimes /= (this.bestPossibleActionTimes.
                            size() * 1d);

                    avgTrainingTimes = 0d;
                    for ( Double sample : this.trainingTimes ) {
                        avgTrainingTimes += sample;
                    }
                    avgTrainingTimes /= (this.trainingTimes.size() * 1d);
                }
                //guardamos los progresos en un archivo
                if ( createPerceptronFile ) {
                    neuralNetworkInterfaceFor2048.saveNeuralNetwork(
                            perceptronFile);
                }
            }
            //cerramos el juego
            game.dispose();

            System.out.println("Training Finished.");

            if ( this.simulationsForStatistics > 0 && this.gamesToPlayPerThreadForStatistics > 0 ) {
                statisticExperiment = new StatisticExperiment(this) {
                    @Override
                    protected void initializeStatistics() throws Exception {
                        this.setGamesToPlayPerThread(
                                gamesToPlayPerThreadForStatistics);
                        this.saveBackupEvery(saveBackupEvery);
                        this.setSimulations(simulationsForStatistics);
                        this.setLearningMethod(learningAlgorithm);
                        this.setTileToWinForStatistics(tileToWinForStatistics);
                        this.setExportToExcel(exportToExcel);
                        this.setRunStatisticsForBackups(runStatisticsForBackups);
                    }
                };
                statisticExperiment.setFileName(this.getExperimentName());
                statisticExperiment.start(experimentPath, delayPerMove,
                        createPerceptronFile);
            }
        } catch ( Exception ex ) {
            printErrorInFile(ex, new File(bugFilePath));
        }
    }

}
