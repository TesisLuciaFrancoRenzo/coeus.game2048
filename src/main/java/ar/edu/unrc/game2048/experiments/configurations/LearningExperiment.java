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
package ar.edu.unrc.game2048.experiments.configurations;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.EExplorationRateAlgorithms;
import ar.edu.unrc.coeus.tdlearning.learning.ELearningRateAdaptation;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.coeus.utils.StatisticCalculator;
import ar.edu.unrc.game2048.Game2048;

import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ar.edu.unrc.game2048.experiments.TestGenerator.getMsj;

/**
 * Experimento especializado para aprendizaje de redes neuronales.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public abstract
class LearningExperiment {

    /**
     * Nombre para el archivo salvado con mejor entrenamiento hasta el momento
     */
    public static final String                     BEST_TRAINED                           = "_best_trained";
    /**
     * Extensión de las configuraciones de entrenamiento del experimento.
     */
    public static final String                     CONFIG                                 = "_config";
    public static final String                     DATE_FILE_FORMATTER_PATTERN            = "dd-MM-yyyy_HH'h'mm'm'ss's'";
    public static final String                     DATE_FORMATTER_PATTERN                 = "dd/MM/yyyy HH:mm:ss";
    /**
     * Nombre del archivo para las salidas de errores.
     */
    public static final String                     ERROR_DUMP                             = "ERROR_DUMP";
    public static final String                     HISTORY_DUMP                           = "HISTORY_DUMP";
    /**
     * Nombre del archivo con los datos de la ultima red neuronal guardada en disco.
     */
    public static final String                     LAST_SAVE_DATA                         = "_last_save_data";
    /**
     * Nombre que se le agrega a los archivos de redes neuronales inicializados sin entrenamiento, para comparar al
     * final del experimento con la versión entrenada.
     */
    public static final String                     RANDOM                                 = "_random";
    /**
     * Nombre que se le agrega a los archivos de redes neuronales con entrenamiento.
     */
    public static final String                     TRAINED                                = "_trained";
    /**
     * Formato para fechas.
     */
    private final       DateFormat                 dateFormat                             = new SimpleDateFormat(DATE_FORMATTER_PATTERN);
    private final       DecimalFormat              decimalFormat                          = new DecimalFormat("#.###");
    /**
     * Formato para fechas en los nombres de archivos.
     */
    private final       DateFormat                 simpleDateFormat                       = new SimpleDateFormat(DATE_FILE_FORMATTER_PATTERN);
    /**
     * Experimento encargad de las estadísticas.
     */
    protected           StatisticExperiment        statisticExperiment                    = null;
    private             double[]                   alpha                                  = null;
    private             int                        annealingT                             = 0;
    private             int                        backupNumber                           = 0;
    private             int                        bestGame                               = 0;
    private             double                     bestMaxTile                            = 0.0;
    private             StatisticCalculator        bestPossibleActionTimes                = null;
    private             double                     bestWinRate                            = 0.0;
    private             boolean                    canCollectStatistics                   = false;
    private             boolean                    concurrencyInComputeBestPossibleAction = false;
    private             boolean[]                  concurrencyInLayer                     = null;
    private             long                       elapsedTimeMilliseconds                = 0L;
    private             int                        eligibilityTraceLength                 = -1;
    private             String                     experimentName                         = null;
    private             EExplorationRateAlgorithms explorationRate                        = null;
    private             double                     explorationRateFinalValue              = 0.0;
    private             int                        explorationRateFinishDecrementing      = 0;
    private             double                     explorationRateInitialValue            = 0.0;
    private             int                        explorationRateStartDecrementing       = 0;
    private             boolean                    exportToExcel                          = true;
    private             int                        gamesToPlay                            = 0;
    private             int                        gamesToPlayPerThreadForStatistics      = 0;
    private             double                     gamma                                  = 0.0;
    private             boolean                    initializePerceptronRandomized         = false;
    private             double                     lambda                                 = 0.0;
    private             int                        lastSavedGamePlayedNumber              = 0;
    private             TDLambdaLearning           learningAlgorithm                      = null;
    private             ELearningRateAdaptation        learningRateAdaptation                 = null;
    private             boolean                        logsActivated                          = false;
    private             StatisticCalculator            maxTileEstimator                       = null;
    private             StatisticCalculator            maxTurnAvg                             = null;
    private             INeuralNetworkInterfaceFor2048 neuralNetworkInterfaceFor2048          = null;
    private             String                         neuralNetworkName                      = null;
    private             boolean                        replaceEligibilityTraces               = false;
    private             boolean                        runStatisticsForBackups                = false;
    private             int                            sampleSizeForEstimation                = 2_000;
    private             int                            saveBackupEvery                        = 0;
    private             int                            saveEvery                              = 0;
    private             int                            simulationsForStatistics               = 0;
    private             boolean                        statisticsOnly                         = false;
    private             int                            tileToWinForStatistics                 = 2_048;
    private             int                            tileToWinForTraining                   = 0;
    private             StatisticCalculator            trainingTimes                          = null;
    private             double                         whenStartToExplore                     = 1.0d;
    private             StatisticCalculator            winRateEstimator                       = null;

    private static
    void printErrorInFile(
            final Throwable ex,
            final File dumpFile
    ) {
        PrintStream printStream = null;
        try {
            if ( !dumpFile.exists() ) {
                dumpFile.createNewFile();
            }
            final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_PATTERN);
            printStream = new PrintStream(new FileOutputStream(dumpFile, true), true, "UTF-8");
            final String msj =
                    "* " + dateFormat.format(new Date()) + "----------------------------------------------------------------------------\n" +
                    getMsj(ex);
            printStream.println(msj);
            System.err.println(msj);
        } catch ( final IOException ex1 ) {
            Logger.getLogger(LearningExperiment.class.getName()).log(Level.SEVERE, null, ex1);
        } finally {
            if ( printStream != null ) {
                printStream.close();
            }
        }
    }

    public
    boolean canExploreThisTurn( final long currentTurn ) {
        return ( whenStartToExplore >= 1 ) ||
               ( ( maxTurnAvg.getItemCounter() > 0 ) && ( currentTurn > ( maxTurnAvg.getAverage() * whenStartToExplore ) ) );
    }

    /**
     * @param logsActivated true si se deben crear logs detallados.
     */
    public
    void createLogs( final boolean logsActivated ) {
        this.logsActivated = logsActivated;
    }

    /**
     * Crea ruta a los directorios adecuados para guardar los resultados de los experimentos.
     *
     * @param experimentPath directorio base.
     *
     * @return ruta de los directorios para usar en los resultados de los experimentos.
     */
    public
    String createPathToDir( final String experimentPath ) {
        return experimentPath + neuralNetworkInterfaceFor2048.getLibName() + File.separator + experimentName + File.separator;
    }

    /**
     * @return constante alfa
     */
    public
    double[] getAlpha() {
        return alpha;
    }

    /**
     * @param alpha constante alfa
     */
    public
    void setAlpha( final double[] alpha ) {
        this.alpha = alpha;
    }

    /**
     * @return partido en el que el annealing alcanza la mitad del valor inicial.
     */
    protected
    int getAnnealingT() {
        return annealingT;
    }

    /**
     * @param annealingT partido en el que el annealing alcanza la mitad del valor inicial.
     */
    protected
    void setAnnealingT( final int annealingT ) {
        this.annealingT = annealingT;
    }

    public
    double getBestPossibleActionTimesAverage() {
        return bestPossibleActionTimes.getAverage();
    }

    /**
     * @return Arreglo con valores indicando true en el numero de capa que se permite cálculos concurrentes.
     */
    public
    boolean[] getConcurrencyInLayer() {
        return concurrencyInLayer;
    }

    /**
     * @param concurrencyInLayer Arreglo con valores indicando true en el numero de capa que se permite cálculos concurrentes.
     */
    public
    void setConcurrencyInLayer( final boolean[] concurrencyInLayer ) {
        this.concurrencyInLayer = concurrencyInLayer;
    }

    public
    int getEligibilityTraceLength() {
        return eligibilityTraceLength;
    }

    public
    void setEligibilityTraceLength( final int eligibilityTraceLength ) {
        this.eligibilityTraceLength = eligibilityTraceLength;
    }

    /**
     * @return Nombre del experimento.
     */
    public
    String getExperimentName() {
        return experimentName;
    }

    /**
     * @param experimentName nombre del experimento.
     */
    public
    void setExperimentName( final String experimentName ) {
        this.experimentName = experimentName;
    }

    /**
     * @return cantidad de partidos a jugar en el experimento.
     */
    protected
    int getGamesToPlay() {
        return gamesToPlay;
    }

    /**
     * @param gamesToPlay cantidad de partidos a jugar en el experimento.
     */
    public
    void setGamesToPlay( final int gamesToPlay ) {
        this.gamesToPlay = gamesToPlay;
    }

    /**
     * @return cantidad de juegos a jugar por procesador en el cálculo de estadísticas.
     */
    public
    int getGamesToPlayPerThreadForStatistics() {
        return gamesToPlayPerThreadForStatistics;
    }

    /**
     * @param gamesToPlayPerThreadForStatistics cantidad de juegos a jugar por procesador en el cálculo de estadísticas.
     */
    public
    void setGamesToPlayPerThreadForStatistics(
            final int gamesToPlayPerThreadForStatistics
    ) {
        this.gamesToPlayPerThreadForStatistics = gamesToPlayPerThreadForStatistics;
    }

    /**
     * @return gamma.
     */
    public
    double getGamma() {
        return gamma;
    }

    /**
     * @param gamma nueva gamma.
     */
    public
    void setGamma( final double gamma ) {
        this.gamma = gamma;
    }

    /**
     * @return lambda.
     */
    public
    double getLambda() {
        return lambda;
    }

    /**
     * @param lambda nueva lambda.
     */
    public
    void setLambda( final double lambda ) {
        this.lambda = lambda;
    }

    /**
     * @return algoritmo de entrenamiento utilizado en el experimento.
     */
    protected
    TDLambdaLearning getLearningAlgorithm() {
        return learningAlgorithm;
    }

    /**
     * @param learningAlgorithm algoritmo de entrenamiento utilizado en el experimento.
     */
    protected
    void setLearningAlgorithm( final TDLambdaLearning learningAlgorithm ) {
        this.learningAlgorithm = learningAlgorithm;
    }

    /**
     * @return Tipo de adaptación de tasa de aprendizaje utilizado.
     */
    public
    ELearningRateAdaptation getLearningRateAdaptation() {
        return learningRateAdaptation;
    }

    /**
     * @param learningRateAdaptation Tipo de adaptación de tasa de aprendizaje utilizado.
     */
    public
    void setLearningRateAdaptation(
            final ELearningRateAdaptation learningRateAdaptation
    ) {
        this.learningRateAdaptation = learningRateAdaptation;
    }

    /**
     * @return interfaz utilizada para comunicar la red neuronal con el algoritmo de entrenamiento.
     */
    protected
    INeuralNetworkInterfaceFor2048 getNeuralNetworkInterfaceFor2048() {
        return neuralNetworkInterfaceFor2048;
    }

    /**
     * @param neuralNetworkInterfaceFor2048 interfaz utilizada para comunicar la red neuronal con el algoritmo de entrenamiento.
     */
    protected
    void setNeuralNetworkInterfaceFor2048(
            final INeuralNetworkInterfaceFor2048 neuralNetworkInterfaceFor2048
    ) {
        this.neuralNetworkInterfaceFor2048 = neuralNetworkInterfaceFor2048;
    }

    /**
     * @return nombre de la red neuronal.
     */
    public
    String getNeuralNetworkName() {
        return neuralNetworkName;
    }

    /**
     * @param neuralNetworkName establece nombre de la red neuronal.
     */
    public
    void setNeuralNetworkName( final String neuralNetworkName ) {
        this.neuralNetworkName = neuralNetworkName;
    }

    public
    int getSampleSizeForEstimation() {
        return sampleSizeForEstimation;
    }

    public
    void setSampleSizeForEstimation( final int sampleSizeForEstimation ) {
        this.sampleSizeForEstimation = sampleSizeForEstimation;
    }

    /**
     * @return intervalo que establece cada cuántas partidas se debe realizar un una copia de la red neuronal actual.
     */
    public
    int getSaveBackupEvery() {
        return saveBackupEvery;
    }

    /**
     * @param saveBackupEvery intervalo que establece cada cuántas partidas se debe realizar un una copia de la red neuronal actual.
     */
    public
    void setSaveBackupEvery( final int saveBackupEvery ) {
        this.saveBackupEvery = saveBackupEvery;
    }

    /**
     * @return intervalo que establece cada cuántas partidas se debe guardar en un archivo, el estado de la red neuronal actual.
     */
    public
    int getSaveEvery() {
        return saveEvery;
    }

    /**
     * @param saveEvery intervalo que establece cada cuántas partidas se debe guardar en un archivo, el estado de la red neuronal actual.
     */
    public
    void setSaveEvery( final int saveEvery ) {
        this.saveEvery = saveEvery;
    }

    /**
     * @return cantidad de simulaciones que se realizan para calcular estadísticas, por procesador.
     */
    protected
    int getSimulationsForStatistics() {
        return simulationsForStatistics;
    }

    /**
     * @param simulationsForStatistics cantidad de simulaciones que se realizan para calcular estadísticas, por procesador.
     */
    public
    void setSimulationsForStatistics( final int simulationsForStatistics ) {
        this.simulationsForStatistics = simulationsForStatistics;
    }

    /**
     * @return Experimento de estadísticas asociado.
     */
    protected
    StatisticExperiment getStatisticExperiment() {
        return statisticExperiment;
    }

    /**
     * @return numero que se considera ganador del juego, para entrenamientos.
     */
    protected
    int getTileToWinForTraining() {
        return tileToWinForTraining;
    }

    /**
     * @param tileToWinForTraining numero que se considera ganador del juego, para entrenamientos.
     */
    public
    void setTileToWinForTraining( final int tileToWinForTraining ) {
        this.tileToWinForTraining = tileToWinForTraining;
    }

    public
    double getTrainingTimesAverage() {
        return trainingTimes.getAverage();
    }

    public
    double getWhenStartToExplore() {
        return whenStartToExplore;
    }

    /**
     * Numero entre 0.0d y 1.0d que multiplica el promedio de los turnos actuales, para determinar desde que turno se comienza a explorar.
     *
     * @param whenStartToExplore
     */
    public
    void setWhenStartToExplore( final double whenStartToExplore ) {
        this.whenStartToExplore = whenStartToExplore;
    }

    /**
     * Valores que se deben inicializar del experimento, por ejemplo:
     * <ul>
     * <li>private double alpha;</li>
     * <li>private double lambda;</li>
     * <li>private int gamesToPlay;</li>
     * <li>private int saveBackupEvery;</li>
     * <li>private int tileToWinForTraining;</li>
     * <li>private String experimentName;</li>
     * <li>private String neuralNetworkName;</li>
     * <li>private EncogConfiguration2048 perceptronConfiguration; </li>
     * <li>private INeuralNetworkInterfaceFor2048 neuralNetworkInterfaceFor2048;</li>
     * <li>private TDTrainerMethod trainerMethod;</li>
     * <li>private int gamesToPlayPerThreadForStatistics;</li>
     * <li>private int simulationsForStatistics;</li>
     * <li>private boolean statisticsOnly;</li>
     * <li>private TDLambdaLearning learningAlgorithm</li>
     * </ul>
     * etc.
     */
    public abstract
    void initialize();

    /**
     * @param neuralNetworkInterface interfaz que conecta la red neuronal con el algoritmo de TDLearning
     *
     * @return Instancia de el método {@code TDLambdaLearning} a utilizar basado en la interfaz {@code neuralNetworkInterface}
     */
    public abstract
    TDLambdaLearning instanceOfTdLearningImplementation(
            INeuralNetworkInterface neuralNetworkInterface
    );

    /**
     * @param nTupleSystem interfaz que conecta la NTupla con el algoritmo de TDLearning
     *
     * @return Instancia de el método {@code TDLambdaLearning} a utilizar basado en la interfaz {@code nTupleSystem}
     */
    public abstract
    TDLambdaLearning instanceOfTdLearningImplementation(
            NTupleSystem nTupleSystem
    );

    public
    boolean isCanCollectStatistics() {
        return canCollectStatistics;
    }

    public
    void setCanCollectStatistics( final boolean canCollectStatistics ) {
        this.canCollectStatistics = canCollectStatistics;
    }

    public
    boolean isReplaceEligibilityTraces() {
        return replaceEligibilityTraces;
    }

    public
    void setReplaceEligibilityTraces( final boolean replaceEligibilityTraces ) {
        this.replaceEligibilityTraces = replaceEligibilityTraces;
    }

    /**
     * @return true si se debe realizar estadísticas sobre las copias de las redes neuronales, realizadas a través del tiempo de entrenamiento total,
     * para notar su progreso.
     */
    public
    boolean isRunStatisticsForBackups() {
        return runStatisticsForBackups;
    }

    /**
     * @param runStatisticsForBackups true si se debe realizar estadísticas sobre las copias de las redes neuronales, realizadas a través del tiempo
     *                                de entrenamiento total, para notar su progreso.
     */
    public
    void setRunStatisticsForBackups( final boolean runStatisticsForBackups ) {
        this.runStatisticsForBackups = runStatisticsForBackups;
    }

    /**
     * @return true si solo se están ejecutando experimentos de cálculos de estadísticas.
     */
    public
    boolean isStatisticsOnly() {
        return statisticsOnly;
    }

    /**
     * @param statisticsOnly true si solo se están ejecutando experimentos de cálculos de estadísticas.
     */
    public
    void setStatisticsOnly( final boolean statisticsOnly ) {
        this.statisticsOnly = statisticsOnly;
    }

    /**
     * Inicia el experimento.
     *
     * @param numberForShow        Numero para mostrar en la terminal al mostrar los valores alcanzado. Útil para ejecutar muchos experimentos en
     *                             paralelo e identificar los resultados parciales en la terminal.
     * @param experimentPath       Ruta del directorio donde se guardan los resultados.
     * @param createPerceptronFile true si se deben crear las redes neuronales si no existen.
     * @param errorDumpDir         directorio donde se vuelcan los archivos de errores.
     */
    protected
    void runExperiment(
            final int numberForShow,
            final String experimentPath,
            final boolean createPerceptronFile,
            final String errorDumpDir,
            final boolean printHistory
    ) {
        if ( saveEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar el perceptron mediante la variable saveEvery");
        }
        if ( saveBackupEvery == 0 ) {
            throw new IllegalArgumentException("se debe configurar cada cuanto guardar backups del perceptron mediante la variable saveBackupEvery");
        }
        System.out.println("Starting " + neuralNetworkName + ( ( numberForShow == -1 ) ? "" : " Trainer Nº " + numberForShow ));
        final String dirPath     = createPathToDir(experimentPath);
        final String bugFilePath = ( ( errorDumpDir == null ? dirPath : errorDumpDir ) + ERROR_DUMP + ".txt" );
        final File   historyFile = printHistory ? new File(dirPath + HISTORY_DUMP + ".txt") : null;
        maxTileEstimator = new StatisticCalculator(sampleSizeForEstimation, 2);
        winRateEstimator = new StatisticCalculator(sampleSizeForEstimation, 2);
        maxTurnAvg = new StatisticCalculator(sampleSizeForEstimation, 2);

        try {
            final Date now = new Date();
            if ( createPerceptronFile ) {
                final File dirPathFile = new File(dirPath);
                if ( !dirPathFile.exists() ) {
                    dirPathFile.mkdirs();
                }
            }
            final String filePath   = dirPath + neuralNetworkName;
            final File   configFile = new File(( errorDumpDir == null ? filePath : errorDumpDir ) + CONFIG + ".txt");
            backupNumber = 0;
            lastSavedGamePlayedNumber = 0;
            elapsedTimeMilliseconds = 0;
            final File lastSaveDataFile = new File(filePath + LAST_SAVE_DATA + ".txt");
            if ( lastSaveDataFile.exists() ) {
                try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(lastSaveDataFile), "UTF-8")) ) {
                    String line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                    }
                    lastSavedGamePlayedNumber = Integer.parseInt(line);
                    line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                    }
                    backupNumber = Integer.parseInt(line);
                    line = reader.readLine();
                    if ( line == null ) {
                        throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                    }
                    elapsedTimeMilliseconds = Long.parseLong(line);
                    try {
                        line = reader.readLine();
                        if ( line == null ) {
                            throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                        }
                        bestGame = Integer.parseInt(line);
                        line = reader.readLine();
                        if ( line == null ) {
                            throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                        }
                        bestWinRate = Double.parseDouble(line);
                    } catch ( final Exception ignored ) {
                        bestGame = 0;
                        bestWinRate = 0;
                    }
                    try {
                        line = reader.readLine();
                        if ( line == null ) {
                            throw new IllegalArgumentException("el archivo de configuración no tiene un formato válido");
                        }
                        bestMaxTile = Double.parseDouble(line);
                    } catch ( final Exception ignored ) {
                        bestMaxTile = 0;
                    }
                }
            }

            int zeroNumbers = 1;
            if ( !statisticsOnly ) {
                zeroNumbers = Integer.toString(gamesToPlay / saveBackupEvery).length();
            }

            boolean    backupRandomPerceptron = false;
            File       randomPerceptronFile   = null;
            final File perceptronFile         = new File(filePath + TRAINED + ".ser");
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
            // En caso contrario creamos un perceptron vacío, inicializado al azar
            neuralNetworkInterfaceFor2048.loadOrCreatePerceptron(perceptronFile, initializePerceptronRandomized, createPerceptronFile);
            //FIXME que hacer si esta ROTO? solucionar esto

            //creamos una interfaz de comunicación entre la red neuronal de encog y el algoritmo de entrenamiento
            if ( backupRandomPerceptron ) {
                //guardamos el perceptron inicial para hacer estadisticas
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(randomPerceptronFile);
            }

            if ( neuralNetworkInterfaceFor2048.getNeuralNetworkInterface() != null ) {
                learningAlgorithm = instanceOfTdLearningImplementation(neuralNetworkInterfaceFor2048.getNeuralNetworkInterface());
                learningAlgorithm.setComputeParallelBestPossibleAction(concurrencyInComputeBestPossibleAction);
            }
            if ( neuralNetworkInterfaceFor2048.getNTupleConfiguration() != null ) {
                learningAlgorithm = instanceOfTdLearningImplementation(neuralNetworkInterfaceFor2048.getNTupleConfiguration().getNTupleSystem());
                learningAlgorithm.setComputeParallelBestPossibleAction(concurrencyInComputeBestPossibleAction);
            }

            if ( ( learningAlgorithm == null ) && !statisticsOnly ) {
                throw new IllegalArgumentException("learningAlgorithm no puede ser null");
            }

            saveConfigFile(configFile);

            System.out.println("Training...");

            //creamos un archivo de logs para acumular estadisticas
            final File logFile = new File(filePath + '_' + simpleDateFormat.format(now) + "_LOG" + ".txt");

            //creamos el juego
            final Game2048 game = new Game2048(neuralNetworkInterfaceFor2048.getPerceptronConfiguration(),
                    neuralNetworkInterfaceFor2048.getNTupleConfiguration(),
                    tileToWinForTraining,
                    printHistory);

            if ( !statisticsOnly ) {
                //comenzamos a entrenar y guardar estadisticas en el archivo de log
                if ( logsActivated ) {
                    try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                        training(numberForShow, game, printStream, perceptronFile, lastSaveDataFile, filePath, zeroNumbers, historyFile);
                    }
                } else {
                    training(numberForShow, game, null, perceptronFile, lastSaveDataFile, filePath, zeroNumbers, historyFile);
                }
            }

            System.out.println("Training Finished.");

            if ( ( simulationsForStatistics > 0 ) && ( gamesToPlayPerThreadForStatistics > 0 ) ) {
                statisticExperiment = new StatisticExperiment(this) {
                    @Override
                    protected
                    void initializeStatistics() {
                        setGamesToPlayPerThread(gamesToPlayPerThreadForStatistics);
                        saveBackupEvery(saveBackupEvery);
                        setSimulations(simulationsForStatistics);
                        setLearningMethod(learningAlgorithm);
                        setTileToWinForStatistics(tileToWinForStatistics);
                        setExportToExcel(exportToExcel);
                        setRunStatisticsForBackups(runStatisticsForBackups);
                    }
                };
                statisticExperiment.setFileName(experimentName);
                statisticExperiment.start(experimentPath, createPerceptronFile, printHistory);
            }
        } catch ( final Exception ex ) {
            printErrorInFile(ex, new File(bugFilePath));
        }
    }

    @SuppressWarnings( "HardcodedFileSeparator" )
    private
    void saveConfigFile( final File configFile )
            throws Exception {
        try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8")) ) {
            out.write("experimentName: " + experimentName + '\n');
            out.write("perceptronName: " + neuralNetworkName + '\n');
            out.write("tileToWinForTraining: " + tileToWinForTraining + '\n');
            out.write("tileToWinForStatistics: " + tileToWinForStatistics + '\n');
            out.write("hasBias: " + ( ( neuralNetworkInterfaceFor2048.getPerceptronConfiguration() != null )
                                      ? String.valueOf(neuralNetworkInterfaceFor2048.getPerceptronConfiguration().containBias())
                                      : "false" ) + '\n');
            out.write("gamesToPlay: " + gamesToPlay + '\n');
            out.write("gamesToPlayPerThreadForStatistics: " + gamesToPlayPerThreadForStatistics + '\n');
            out.write("statisticsOnly: " + statisticsOnly + '\n');
            out.write("simulationsForStatistics: " + simulationsForStatistics + '\n');
            out.write("saveEvery: " + saveEvery + '\n');
            out.write("saveBackupEvery: " + saveBackupEvery + '\n');
            out.write("alpha: " + Arrays.toString(alpha) + '\n');
            out.write("gamma: " + gamma + '\n');
            out.write("lambda: " + lambda + '\n');
            out.write("eligibilityTraceLength: " + eligibilityTraceLength + '\n');
            out.write("replaceEligibilityTraces: " + replaceEligibilityTraces + '\n');
            out.write("annealingT: " + annealingT + '\n');
            out.write("learningRateAdaptation: " + learningRateAdaptation + '\n');
            out.write("initializePerceptronRandomized: " + initializePerceptronRandomized + '\n');
            out.write("concurrencyInComputeBestPossibleAction: " + concurrencyInComputeBestPossibleAction + '\n');
            out.write("concurrencyInLayer: " + Arrays.toString(concurrencyInLayer) + '\n');
            out.write("whenStartToExplore: " + whenStartToExplore + '\n');
            out.write("explorationRate: " + explorationRate + '\n');
            out.write("explorationRateFinalValue: " + explorationRateFinalValue + '\n');
            out.write("explorationRateInitialValue: " + explorationRateInitialValue + '\n');
            out.write("explorationRateStartDecrementing: " + explorationRateStartDecrementing + '\n');
            out.write("explorationRateFinishDecrementing: " + explorationRateFinishDecrementing + '\n');
            out.write("\n");
            out.write("Operating system Name: " + System.getProperty("os.name") + '\n');
            out.write("Operating system type: " + System.getProperty("os.arch") + '\n');
            out.write("Operating system version: " + System.getProperty("os.version") + '\n');
            if ( System.getProperty("os.name").matches(".*[Ww]indows.*") ) {
                final Process command = Runtime.getRuntime().exec("wmic cpu get name");
                try ( BufferedReader in = new BufferedReader(new InputStreamReader(command.getInputStream(), "UTF-8")) ) {
                    String line;
                    while ( ( line = in.readLine() ) != null ) {
                        if ( !line.isEmpty() && !line.contains("Name") ) {
                            out.write("CPU: " + line.trim() + '\n');
                            break;
                        }
                    }
                }
            } else if ( System.getProperty("os.name").matches(".*[Ll]inux.*") ) {
                final Process command = Runtime.getRuntime().exec("cat /proc/cpuinfo");
                try ( BufferedReader in = new BufferedReader(new InputStreamReader(command.getInputStream(), "UTF-8")) ) {
                    String line;
                    while ( ( line = in.readLine() ) != null ) {
                        if ( !line.isEmpty() && line.matches(".*model name\\s*:.*") ) {
                            final int i = line.indexOf(':');
                            out.write("CPU: " + line.substring(i + 1).trim() + '\n');
                            break;
                        }
                    }
                }
            }
            out.write("Available processors (cores): " + Runtime.getRuntime().availableProcessors() + '\n');
            /* This will return Long.MAX_VALUE if there is no preset limit */
            final long maxMemory = Runtime.getRuntime().maxMemory();
            /* Maximum amount of memory the JVM will attempt to use */
            out.write("Maximum memory (bytes): " + ( maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory ) + '\n');
            /* Total memory currently available to the JVM */
            out.write("Total memory available to JVM (bytes): " + Runtime.getRuntime().totalMemory() + '\n');
        }
    }

    /**
     * @param parallel true si se debe activar concurrencia en los cálculos de elegir la mejor acción posible.
     */
    public
    void setConcurrencyInComputeBestPossibleAction( final boolean parallel ) {
        concurrencyInComputeBestPossibleAction = parallel;
    }

    /**
     * Establece el nombre del experimento basado en el nombre de la clase {@code experimentClass}.
     *
     * @param experimentClass clase de la cual extraer el nombre del experimento.
     */
    public
    void setExperimentName( final Class experimentClass ) {
        String    className = experimentClass.getName();
        final int lastDot   = className.lastIndexOf('.');
        if ( lastDot != -1 ) {
            className = className.substring(lastDot + 1);
        }
        experimentName = className;
    }

    /**
     * Establece tasa de exploración con una función de atenuación lineal.
     *
     * @param initialValue       valor inicial.
     * @param startDecrementing  partido en el cual se va a empezar a atenuar {@code initialValue}.
     * @param finalValue         valor final luego de la atenuación.
     * @param finishDecrementing partido en el cual se deja de atenuar {@code initialValue} y alcanza el valor {@code finalValue}.
     */
    public
    void setExplorationRate(
            final double initialValue,
            final int startDecrementing,
            final double finalValue,
            final int finishDecrementing
    ) {
        if ( ( initialValue < 0 ) || ( initialValue > 1 ) ) {
            throw new IllegalArgumentException("initialValue debe estar en el intervalo [0,1]");
        }
        if ( ( finalValue < 0 ) || ( finalValue > 1 ) ) {
            throw new IllegalArgumentException("finalValue debe estar en el intervalo [0,1]");
        }
        explorationRate = EExplorationRateAlgorithms.LINEAR;
        explorationRateInitialValue = initialValue;
        explorationRateStartDecrementing = startDecrementing;
        explorationRateFinalValue = finalValue;
        explorationRateFinishDecrementing = finishDecrementing;
    }

    /**
     * @param value valor de la tasa de exploración fija, entre 0 y 1 inclusive.
     */
    public
    void setExplorationRateToFixed( final double value ) {
        if ( ( value < 0 ) || ( value > 1 ) ) {
            throw new IllegalArgumentException("value debe estar en el intervalo [0,1]");
        }
        explorationRate = EExplorationRateAlgorithms.FIXED;
        explorationRateInitialValue = value;
    }

    /**
     * @param exportToExcel true si se debe exportar resultados a una hoja de cálculo.
     */
    public
    void setExportToExcel( final boolean exportToExcel ) {
        this.exportToExcel = exportToExcel;
    }

    /**
     * @param randomized true si se debe inicializar las redes neuronales de forma al azar.
     */
    public
    void setInitializePerceptronRandomized( final boolean randomized ) {
        initializePerceptronRandomized = randomized;
    }

    /**
     * Establece una tasa de aprendizaje con annealing de valor {@code annealingT}.
     *
     * @param annealingT
     */
    public
    void setLearningRateAdaptationToAnnealing( final int annealingT ) {
        learningRateAdaptation = ELearningRateAdaptation.ANNEALING;
        this.annealingT = annealingT;
    }

    /**
     * Establece una tasa de aprendizaje fija, sin atenuación.
     */
    public
    void setLearningRateAdaptationToFixed() {
        learningRateAdaptation = ELearningRateAdaptation.FIXED;
    }

    /**
     * @param winValue valor utilizado para finalizar el juego como que se ganó, para el cálculo de estadísticas y el winRate.
     */
    public
    void setTileToWinForStatistics( final int winValue ) {
        tileToWinForStatistics = winValue;
    }

    /**
     * Inicia el experimento.
     *
     * @param numberForShow        Numero para mostrar en la terminal al mostrar los valores alcanzado. Útil para ejecutar muchos experimentos en
     *                             paralelo e identificar los resultados parciales en la terminal.
     * @param experimentPath       Ruta del directorio donde se guardan los resultados.
     * @param createPerceptronFile true si se deben crear las redes neuronales si no existen.
     * @param errorDumpDir         directorio donde se vuelcan los archivos de errores.
     */
    public
    void start(
            final int numberForShow,
            final String experimentPath,
            final boolean createPerceptronFile,
            final String errorDumpDir,
            final boolean printHistory
    ) {
        final File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        initialize();
        runExperiment(numberForShow, experimentPath, createPerceptronFile, errorDumpDir, printHistory);
    }

    /**
     * @param numberForShow    Numero para mostrar en la terminal al mostrar los valores alcanzado. Útil para ejecutar muchos experimentos en paralelo
     *                         e identificar los resultados parciales en la terminal.
     * @param game             juego a entrenar
     * @param printStream      salida de textos de debug e información.
     * @param perceptronFile   archivo de la red neuronal con entrenamiento.
     * @param lastSaveDataFile archivo donde se guardan los datos del ultimo entrenamiento, para poder continuar en caso de interrupciones.
     * @param filePath         ruta en donde se guardan los resultados.
     * @param zeroNumbers      cantidad de ceros a la izquierda en los números de los archivos de backup.
     *
     * @throws Exception
     */
    protected
    void training(
            final int numberForShow,
            final Game2048 game,
            final PrintStream printStream,
            final File perceptronFile,
            final File lastSaveDataFile,
            final String filePath,
            final int zeroNumbers,
            final File historyFile
    )
            throws Exception {
        switch ( learningRateAdaptation ) {
            case FIXED:
                learningAlgorithm.setFixedLearningRate();
                break;
            case ANNEALING:
                learningAlgorithm.setAnnealingLearningRate(annealingT);
                break;
        }
        switch ( explorationRate ) {
            case FIXED:
                learningAlgorithm.setFixedExplorationRate(explorationRateInitialValue);
                break;
            case LINEAR:
                learningAlgorithm.setLinearExplorationRate(explorationRateInitialValue,
                        explorationRateStartDecrementing,
                        explorationRateFinalValue,
                        explorationRateFinishDecrementing);
                break;
        }

        if ( learningAlgorithm.canCollectStatistics() ) {
            bestPossibleActionTimes = new StatisticCalculator();
            trainingTimes = new StatisticCalculator();
        }

        ByteArrayOutputStream     bestSavedGameCache = new ByteArrayOutputStream();
        boolean                   needToSaveBestGame = false;
        final StatisticCalculator timePerTurn        = new StatisticCalculator();

        for ( int i = lastSavedGamePlayedNumber + 1; i <= gamesToPlay; i++ ) {
            final long   start           = System.currentTimeMillis();
            final Double lastTimePerTurn = learningAlgorithm.solveAndTrainOnce(game, i);
            elapsedTimeMilliseconds += System.currentTimeMillis() - start;
            if ( game.isPrintHistory() ) {
                try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(historyFile, true), "UTF-8")) ) {
                    out.append("\n========== NEW GAME (")
                            .append(Integer.toString(i))
                            .append(")==========\n")
                            .append(game.getHistoryLog())
                            .append("\n")
                            .append("score=")
                            .append(Integer.toString(game.getScore()))
                            .append("\n")
                            .append("turn=")
                            .append(Integer.toString(game.getLastTurn()))
                            .append("\n");
                }
            }
            if ( learningAlgorithm.canCollectStatistics() ) {
                bestPossibleActionTimes.addSample(learningAlgorithm.getBestPossibleActionTimesAverage());
                trainingTimes.addSample(learningAlgorithm.getTrainingTimesAverage());
                timePerTurn.addSample(lastTimePerTurn);
            }

            final int percent = (int) ( ( ( i * 1.0d ) / ( gamesToPlay * 1.0d ) ) * 100.0d );
            winRateEstimator.addSample(( game.getMaxNumber() >= tileToWinForStatistics ) ? 100 : 0);
            maxTileEstimator.addSample(game.getMaxNumber());
            maxTurnAvg.addSample(game.getLastTurn());

            final StringBuilder msj = new StringBuilder().append(( needToSaveBestGame ) ? "!! " : "");
            if ( numberForShow == -1 ) {
                msj.append("Juego número ");
            } else {
                msj.append(numberForShow).append("- Juego número ");
            }
            msj.append(i)
                    .append(" (")
                    .append(percent)
                    .append("%)\tpuntaje = ")
                    .append(game.getScore())
                    .append("\tficha max = ")
                    .append(game.getMaxNumber())
                    .append(" (")
                    .append("winRate ")
                    .append(winRateEstimator.printableFullCapacityAverage())
                    .append(" % - maxTile ")
                    .append(maxTileEstimator.printableFullCapacityAverage())
                    .append(')')
                    .append("\tRandomChoices = ")
                    .append(learningAlgorithm.getRandomChoicesCounter())
                    .append("\tturno alcanzado = ")
                    .append(game.getLastTurn()).append(" (avg=").append(maxTurnAvg.printableAverage()).append(')')
                    .append("\tcurrentExplorationRate = ")
                    .append(learningAlgorithm.getCurrentExplorationRate())
                    .append("\tcurrent alpha = ")
                    .append(learningAlgorithm.getCurrentAlpha()[0]);

            System.out.println(msj);

            Double averageMaxValue = maxTileEstimator.getFullCapacityAverage();
            Double averageWinRate  = winRateEstimator.getFullCapacityAverage();
            if ( averageMaxValue == null ) { averageMaxValue = 0.0d; }
            if ( averageWinRate == null ) { averageWinRate = 0.0d; }
            if ( ( averageWinRate > bestWinRate ) ||
                 ( ( averageWinRate >= 100.0d ) && ( averageWinRate >= bestWinRate ) && ( averageMaxValue > bestMaxTile ) ) ) {
                bestMaxTile = averageMaxValue;
                bestGame = i;
                bestWinRate = averageWinRate;
                bestSavedGameCache = new ByteArrayOutputStream();
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(bestSavedGameCache);
                needToSaveBestGame = true;
                System.out.println("** ¡NUEVO RECORD! winRate de " + bestWinRate + "% - maxTile " + bestMaxTile + " **");
            }

            if ( printStream != null ) {
                printStream.println(game.getScore() + "\t" + game.getMaxNumber());
            }
            boolean writeConfig = false;
            if ( ( ( i % saveEvery ) == 0 ) || ( ( i % saveBackupEvery ) == 0 ) ) {
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(perceptronFile);
                if ( needToSaveBestGame && ( bestSavedGameCache.size() > 0 ) ) {
                    try ( FileOutputStream f2 = new FileOutputStream(filePath + BEST_TRAINED + ".ser", false) ) {
                        bestSavedGameCache.writeTo(f2);
                    }
                    needToSaveBestGame = false;
                }
                System.out.println("============ Perceptron Exportado Exitosamente (SAVE) ============");
                writeConfig = true;
            }
            if ( ( i % saveBackupEvery ) == 0 ) {
                backupNumber++;
                final File perceptronFileBackup =
                        new File(filePath + TRAINED + "_BackupN-" + String.format("%0" + zeroNumbers + 'd', backupNumber) + ".ser");
                neuralNetworkInterfaceFor2048.saveNeuralNetwork(perceptronFileBackup);
                System.out.println("============ Perceptron Exportado Exitosamente (BACKUP " + backupNumber + ") ============");
                writeConfig = true;
            }
            if ( writeConfig ) {
                try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lastSaveDataFile), "UTF-8")) ) {
                    final long   hs      = elapsedTimeMilliseconds / 3_600_000L;
                    final long   hsRest  = elapsedTimeMilliseconds % 3_600_000L;
                    final long   min     = hsRest / 60_000L;
                    final long   restMin = hsRest % 60_000L;
                    final double seg     = restMin / 1_000.0d;
                    out.write(Integer.toString(i) + '\n' + Integer.toString(backupNumber) + '\n' + Long.toString(elapsedTimeMilliseconds) + '\n' +
                              Integer.toString(bestGame) + '\n' + Double.toString(bestWinRate) + '\n' + Double.toString(bestMaxTile) +
                              "\n\ntiempo de ejecución = " + hs + "h " + min + "m " + decimalFormat.format(seg) + "s (total: " +
                              elapsedTimeMilliseconds + " ms).");
                }
            }
        }
        if ( learningAlgorithm.canCollectStatistics() ) {
            System.out.println(new StringBuilder().append("\nEstadísticas Recolectadas:\ntimePerTurn = ")
                    .append(timePerTurn.getAverage())
                    .append(" ms.\n")
                    .append("bestPossibleActionTimes = ")
                    .append(bestPossibleActionTimes.getAverage())
                    .append(" ms.\n")
                    .append("trainingTimes = ")
                    .append(trainingTimes.getAverage())
                    .append(" ms.\n"));
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
