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

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.tdlearning.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.learning.EExplorationRateAlgorithms;
import ar.edu.unrc.tdlearning.learning.ELearningRateAdaptation;
import ar.edu.unrc.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.training.ntuple.NTupleSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

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
    public static final String RANDOM = "_random";

    /**
     *
     */
    public static final String TRAINED = "_trained";
    private double[] alpha;
    private int annealingT;

    private double avgBestPissibleActionTimes;
    private double avgTrainingTimes;
    private int backupNumber;
    private LinkedList<Double> bestPissibleActionTimes;
    private boolean concurrencyInComputeBestPosibleAction = false;
    private boolean[] concurrencyInLayer;
    private long elapsedTime = 0;
    private String experimentName;
    private EExplorationRateAlgorithms explorationRate;
    private double explorationRateFinalValue;
    private int explorationRateFinishDecrementing;
    private double explorationRateInitialValue;
    private int explorationRateStartDecrementing;
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
    private boolean resetEligibilitiTraces = false;
    private boolean runStatisticsForBackups = false;
    private int saveBackupEvery = 0;
    private int saveEvery = 0;
    private int simulationsForStatistics;
    private boolean statisticsOnly = false;
    private int tileToWinForTraining;
    private LinkedList<Double> trainingTimes;
    private int tileToWinForStatistics = 2_048;

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
    public double getAvgBestPissibleActionTimes() {
        return avgBestPissibleActionTimes;
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
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        this.explorationRate = EExplorationRateAlgorithms.fixed;
        this.explorationRateInitialValue = value;
    }

    /**
     * @return the gamesToPlayPerThreadForStatistics
     */
    public int getGamesToPlayPerThreadForStatistics() {
        return gamesToPlayPerThreadForStatistics;
    }

    /**
     * @param gamesToPlayPerThreadForStatistics the
     *                                          gamesToPlayPerThreadForStatistics
     *                                          to set
     */
    public void setGamesToPlayPerThreadForStatistics(int gamesToPlayPerThreadForStatistics) {
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
    public void setLearningRateAdaptation(ELearningRateAdaptation learningRateAdaptation) {
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
     * <li>private INeuralNetworkInterfaceFor2048
     * neuralNetworkInterfaceFor2048;</li>
     * <li>private TDTrainerMethod trainerMethod;</li>
     * <li>private int gamesToPlayPerThreadForStatistics;</li>
     * <li>private int simulationsForStatistics;</li>
     * <li>private boolean statisticsOnly;</li>
     * <li>private TDLambdaLearning learningAlgorithm</li>
     * <p>
     * </ul>
     * <p>
     * @throws Exception
     */
    public abstract void initialize() throws Exception;

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface);

    /**
     *
     * @param nTupleSystem <p>
     * @return
     */
    public abstract TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem);

    /**
     * @return the resetEligibilitiTraces
     */
    public boolean isResetEligibilitiTraces() {
        return resetEligibilitiTraces;
    }

    /**
     * @param resetEligibilitiTraces the resetEligibilitiTraces to set
     */
    public void setResetEligibilitiTraces(boolean resetEligibilitiTraces) {
        this.resetEligibilitiTraces = resetEligibilitiTraces;
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
    public void setExplorationRate(double initialValue, int startDecrementing, double finalValue, int finishDecrementing) {
        if ( initialValue < 0 || initialValue > 1 ) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if ( finalValue < 0 || finalValue > 1 ) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
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
     * @param experimentPath
     * @param delayPerMove
     * @param createPerceptronFile
     *
     * @throws java.lang.Exception
     */
    public void start(String experimentPath, int delayPerMove, boolean createPerceptronFile) throws Exception {
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        initialize();
        runExperiment(experimentPath, delayPerMove, createPerceptronFile);
    }

    /**
     *
     * @param game
     * @param printStream
     * @param randomPerceptronFile
     * @param perceptronFile
     * @param configFile
     * @param filePath
     * @param dateFormater
     * @param now
     * @param zeroNumbers          <p>
     * @throws Exception
     */
    public void training(Game2048<NeuralNetworkClass> game, final PrintStream printStream, File randomPerceptronFile, File perceptronFile, File configFile, String filePath, SimpleDateFormat dateFormater, Date now, int zeroNumbers) throws Exception {
        File perceptronFileBackup;
        switch ( this.learningRateAdaptation ) {
            case fixed: {
                learningAlgorithm.setLearningRateAdaptationToFixed();
                break;
            }
            case annealing: {
                learningAlgorithm.setLearningRateAdaptationToAnnealing(annealingT);
                break;
            }
        }
        switch ( this.explorationRate ) {
            case fixed: {
                learningAlgorithm.setExplorationRateToFixed(this.explorationRateInitialValue);
                break;
            }
            case linear: {
                learningAlgorithm.setExplorationRate(
                        this.explorationRateInitialValue,
                        this.explorationRateStartDecrementing,
                        this.explorationRateFinalValue,
                        this.explorationRateFinishDecrementing
                );
                break;
            }
        }

        if ( learningAlgorithm.canCollectStatistics() ) {
            bestPissibleActionTimes = new LinkedList<>();
            trainingTimes = new LinkedList<>();
        }

        for ( int i = lastSavedGamePlayedNumber + 1; i <= gamesToPlay; i++ ) {
            long start = System.nanoTime();
            learningAlgorithm.solveAndTrainOnce(game, i);
            elapsedTime += System.nanoTime() - start;

            if ( learningAlgorithm.canCollectStatistics() ) {
                double avg = 0;
                for ( Long sample : learningAlgorithm.getBestPossibleActionTimes() ) {
                    avg += sample;
                }
                avg /= (learningAlgorithm.getBestPossibleActionTimes().size() * 1d);
                bestPissibleActionTimes.add(avg);

                avg = 0;
                for ( Long sample : learningAlgorithm.getTrainingTimes() ) {
                    avg += sample;
                }
                avg /= (learningAlgorithm.getTrainingTimes().size() * 1d);
                trainingTimes.add(avg);
            }

            int percent = (int) (((i * 1d) / (gamesToPlay * 1d)) * 100d);
            System.out.println("Juego número " + i + " (" + percent + "%)    puntaje = " + game.getScore() + "    ficha max = " + game.getMaxNumber() + "    turno alcanzado = " + game.getLastTurn() + "      current alpha = " + Arrays.toString(learningAlgorithm.getCurrentAlpha()));
            if ( printStream != null ) {
                printStream.println(game.getScore() + "\t" + game.getMaxNumber());
            }
            boolean writeConfig = false;
            if ( i % saveEvery == 0 || i % saveBackupEvery == 0 ) {
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFile);
                System.out.println("============ Perceptron Exportado Exitosamente (SAVE) ============");
                writeConfig = true;
            }
            if ( i % saveBackupEvery == 0 ) {
                backupNumber++;
                perceptronFileBackup = new File(filePath + TRAINED + "_" + dateFormater.format(now) + "_BackupN-" + String.format("%0" + zeroNumbers + "d", backupNumber)
                        + ".ser");
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFileBackup);
                System.out.println("============ Perceptron Exportado Exitosamente (BACKUP " + backupNumber + ") ============");
                writeConfig = true;
            }
            if ( writeConfig ) {
                try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8")) ) {
                    out.write(Integer.toString(i) + "\n" + Integer.toString(backupNumber) + "\n" + Long.toString(elapsedTime));
                }
            }
        }
    }

    public void setTileToWinForStatistics(int winValue) {
        tileToWinForStatistics = winValue;
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
     * @param neuralNetworkInterfaceFor2048 the neuralNetworkInterfaceFor2048 to
     *                                      set
     */
    protected void setNeuralNetworkInterfaceFor2048(INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceFor2048) {
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
     * @param experimentPath
     * @param delayPerMove         <p>
     * @param createPerceptronFile
     *
     * @throws Exception
     */
    @SuppressWarnings( "static-access" )
    protected void runExperiment(String experimentPath, int delayPerMove, boolean createPerceptronFile) throws Exception {
        if ( saveEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar el perceptron mediante la variable saveEvery");
        }
        if ( saveBackupEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar backups del perceptron mediante la variable saveBackupEvery");
        }
        long time = 0; //para medir el timepo que demoro en entrenar
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy_HH'h'mm'm'ss's'");
        Date now = new Date();

        System.out.println("Starting " + this.getPerceptronName() + " Trainer");

        String dirPath = createPathToDir(experimentPath);
        if ( createPerceptronFile ) {
            File dirPathFile = new File(dirPath);
            if ( !dirPathFile.exists() ) {
                dirPathFile.mkdirs();
            }
        }
        String filePath = dirPath + perceptronName;
        File perceptronFile = new File(filePath + TRAINED + ".ser");
        File configFile = new File(filePath + CONFIG + ".txt");

        backupNumber = 0;
        lastSavedGamePlayedNumber = 0;
        elapsedTime = 0;
        if ( configFile.exists() ) {
            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), "UTF-8")) ) {
                String line = reader.readLine();
                if ( line == null ) {
                    throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
                }
                this.lastSavedGamePlayedNumber = Integer.parseInt(line);
                line = reader.readLine();
                if ( line == null ) {
                    throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
                }
                this.backupNumber = Integer.parseInt(line);
                line = reader.readLine();
                if ( line == null ) {
                    throw new IllegalArgumentException("el archivo de configuracion no tiene un formato válido");
                }
                this.elapsedTime = Long.parseLong(line);
            }
        }

        int zeroNumbers = 1;
        if ( !this.statisticsOnly ) {
            zeroNumbers = Integer.toString(this.gamesToPlay / this.saveBackupEvery).length();
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
        //creamos el juego
        Game2048<NeuralNetworkClass> game = new Game2048<>(neuralNetworkInterfaceFor2048.getPerceptronConfiguration(), neuralNetworkInterfaceFor2048.getNTupleConfiguration(), tileToWinForTraining, delayPerMove);

        // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
        // En caso contrario creamos un perceptron vacio, inicializado al azar
        neuralNetworkInterfaceFor2048.loadOrCreatePerceptron(perceptronFile, this.initializePerceptronRandomized, createPerceptronFile);

        //creamos una interfaz de comunicacion entre la red neuronal de encog y el algoritmo de entrenamiento
        if ( backupRandomPerceptron ) {
            //guardamos el perceptron inicial para ahcer estadisticas
            neuralNetworkInterfaceFor2048.savePerceptron(randomPerceptronFile);
        }

        if ( this.getNeuralNetworkInterfaceFor2048().getPerceptronInterface() != null ) {
            this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(this.getNeuralNetworkInterfaceFor2048().getPerceptronInterface()));
            this.learningAlgorithm.setComputeParallelBestPossibleAction(concurrencyInComputeBestPosibleAction);
        }
        if ( this.getNeuralNetworkInterfaceFor2048().getNTupleConfiguration() != null ) {
            this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(this.getNeuralNetworkInterfaceFor2048().getNTupleConfiguration().getNTupleSystem()));
            this.learningAlgorithm.setComputeParallelBestPossibleAction(concurrencyInComputeBestPosibleAction);
        }

        if ( learningAlgorithm == null && !this.statisticsOnly ) {
            throw new IllegalArgumentException("learningAlgorithm no puede ser null");
        }

        System.out.println("Training...");

        //creamos un archivo de logs para acumular estadisticas
        File logFile = new File(filePath + "_" + dateFormater.format(now) + "_LOG" + ".txt");

        if ( !this.statisticsOnly ) {
            //comenzamos a entrenar y guardar estadisticas en el archivo de log
            if ( logsActivated ) {
                try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                    training(game, printStream, randomPerceptronFile, perceptronFile, configFile, filePath, dateFormater, now, zeroNumbers);
                }
            } else {
                training(game, null, randomPerceptronFile, perceptronFile, configFile, filePath, dateFormater, now, zeroNumbers);
            }
            if ( learningAlgorithm.canCollectStatistics() ) {
                avgBestPissibleActionTimes = 0d;
                for ( Double sample : this.bestPissibleActionTimes ) {
                    avgBestPissibleActionTimes += sample;
                }
                avgBestPissibleActionTimes /= (this.bestPissibleActionTimes.size() * 1d);

                avgTrainingTimes = 0d;
                for ( Double sample : this.trainingTimes ) {
                    avgTrainingTimes += sample;
                }
                avgTrainingTimes /= (this.trainingTimes.size() * 1d);
            }
            //guardamos los progresos en un archivo
            if ( createPerceptronFile ) {
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFile);
            }
        }
        //cerramos el juego
        game.dispose();

        System.out.println("Training Finished.");

        if ( this.simulationsForStatistics > 0 && this.gamesToPlayPerThreadForStatistics > 0 ) {
            statisticExperiment = new StatisticExperiment(this) {

                @Override
                protected void initializeStatistics() throws Exception {
                    this.setGamesToPlayPerThread(gamesToPlayPerThreadForStatistics);
                    this.saveBackupEvery(saveBackupEvery);
                    this.setSimulations(simulationsForStatistics);
                    this.setLearningMethod(learningAlgorithm);
                    this.setTileToWinForStatistics(tileToWinForStatistics);
                }
            };
            statisticExperiment.setFileName(this.getExperimentName());
            statisticExperiment.start(experimentPath, delayPerMove, createPerceptronFile);
        }
    }
}
