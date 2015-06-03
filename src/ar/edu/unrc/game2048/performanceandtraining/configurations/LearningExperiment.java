/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.training.ELearningRateAdaptation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Franco
 * @param <NeuralNetworkClass>
 */
public abstract class LearningExperiment<NeuralNetworkClass> {

    /**
     *
     */
    public static final String _RANDOM = "_random";

    /**
     *
     */
    public static final String _TRAINED = "_trained";
    private double[] alpha;
    private int annealingT;
    private String experimentName;
    private int gamesToPlay;
    private int gamesToPlayPerThreadForStatistics;
    private double gamma;
    private boolean initializePerceptronRandomized = true;
    private double lambda;
    private int lastGamePlayedNumber;
    private TDLambdaLearning learningAlgorithm;
    private ELearningRateAdaptation learningRateAdaptation;
    private boolean logsActivated = false;
    private double momentum;
    private INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceFor2048;
    private String perceptronName;
    private boolean runStatisticForRandom = false;
    private boolean runStatisticsForBackups = false;
    private int saveEvery;
    private int simulationsForStatistics;
    private boolean statisticsOnly = false;
    private int tileToWin;

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
     *
     * @param gameNumber
     */
    public void setLastGamePlayedNumber(int gameNumber) {
        this.lastGamePlayedNumber = gameNumber;
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
     * @return the momentum
     */
    public double getMomentum() {
        return momentum;
    }

    /**
     * @param momentum the momentum to set
     */
    public void setMomentum(double momentum) {
        this.momentum = momentum;
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
     * you must initialize:
     * <ul>
     * <li>private double alpha;</li>
     * <li>private double lambda;</li>
     * <li>private int gamesToPlay;</li>
     * <li>private int saveEvery;</li>
     * <li>private int tileToWin;</li>
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
     * @return the runStatisticForRandom
     */
    public boolean isRunStatisticForRandom() {
        return runStatisticForRandom;
    }

    /**
     * @param runStatisticForRandom the runStatisticForRandom to set
     */
    public void setRunStatisticForRandom(boolean runStatisticForRandom) {
        this.runStatisticForRandom = runStatisticForRandom;
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
     */
    public void setLearningRateAdaptationToFixed() {
        this.learningRateAdaptation = ELearningRateAdaptation.fixed;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove
     */
    public void start(String experimentPath, int delayPerMove) {
        File experimentPathFile = new File(experimentPath);
        if ( experimentPathFile.exists() && !experimentPathFile.isDirectory() ) {
            throw new IllegalArgumentException("experimentPath must be a directory");
        }
        if ( !experimentPathFile.exists() ) {
            experimentPathFile.mkdirs();
        }
        try {
            initialize();
            runExperiment(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(LearningExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param game
     * @param printStream
     * @param lastSaveCounter
     * @param randomPerceptronFile
     * @param perceptronFile
     * @param backupNumber
     * @param filePath
     * @param dateFormater
     * @param now
     * @param zeroNumbers          <p>
     * @throws Exception
     */
    public void training(Game2048<NeuralNetworkClass> game, final PrintStream printStream, int lastSaveCounter, File randomPerceptronFile, File perceptronFile, int backupNumber, String filePath, SimpleDateFormat dateFormater, Date now, int zeroNumbers) throws Exception {
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
        for ( int i = lastGamePlayedNumber + 1; i < gamesToPlay + lastGamePlayedNumber; i++ ) { //FIXME menor o igual a gameToPlay? o menor? ver codigo de annealing
            learningAlgorithm.solveAndTrainOnce(game, i);
            int percent = (int) (((i * 1d) / ((gamesToPlay + lastGamePlayedNumber) * 1d)) * 100d);
            System.out.println("Juego número " + i + " (" + percent + "%)    puntaje = " + game.getScore() + "    ficha max = " + game.getMaxNumber() + "    turno alcanzado = " + game.getLastTurn() + "      current alpha = " + Arrays.toString(learningAlgorithm.getCurrentAlpha()));
            if ( printStream != null ) {
                printStream.println(game.getScore() + "\t" + game.getMaxNumber());
            }
            lastSaveCounter++;
            if ( lastSaveCounter >= saveEvery ) {
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFile);
                backupNumber++;
                perceptronFileBackup = new File(filePath + _TRAINED + "_" + dateFormater.format(now) + "_BackupN-" + String.format("%0" + zeroNumbers + "d", backupNumber)
                        + ".ser");
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFileBackup);
                lastSaveCounter = 0;
                System.out.println("============ Perceptron Exportado Exitosamente ============");
                //  neuralNetworkInterfaceFor2048.compareNeuralNetworks(randomPerceptronFile, perceptronFile);
            }
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
     * @param neuralNetworkInterfaceFor2048 the neuralNetworkInterfaceFor2048 to
     *                                      set
     */
    protected void setNeuralNetworkInterfaceFor2048(INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceFor2048) {
        this.neuralNetworkInterfaceFor2048 = neuralNetworkInterfaceFor2048;
    }

    /**
     * @return the saveEvery
     */
    protected int getSaveEvery() {
        return saveEvery;
    }

    /**
     * @param saveEvery the saveEvery to set
     */
    public void setSaveEvery(int saveEvery) {
        this.saveEvery = saveEvery;
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
     * @return the tileToWin
     */
    protected int getTileToWin() {
        return tileToWin;
    }

    /**
     * @param tileToWin the tileToWin to set
     */
    public void setTileToWin(int tileToWin) {
        this.tileToWin = tileToWin;
    }

    /**
     *
     * @param experimentPath
     * @param delayPerMove   <p>
     * @throws Exception
     */
    protected void runExperiment(String experimentPath, int delayPerMove) throws Exception {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy_HH'h'mm'm'ss's'");
        Date now = new Date();

        System.out.println("Starting " + this.getPerceptronName() + " Trainer");

        String dirPath = createPathToDir(experimentPath);
        File dirPathFile = new File(dirPath);
        if ( !dirPathFile.exists() ) {
            dirPathFile.mkdirs();
        }
        String filePath = dirPath + perceptronName;
        File perceptronFile = new File(filePath + _TRAINED + ".ser");
        int zeroNumbers = 1;
        if ( !this.statisticsOnly ) {
            zeroNumbers = Integer.toString(this.gamesToPlay / this.saveEvery).length();
        }
        int backupNumber = 0;
        File perceptronFileBackup = new File(filePath + _TRAINED + "_" + dateFormater.format(now) + "_BackupN-" + String.format("%0" + zeroNumbers + "d", backupNumber)
                + ".ser");
        File randomPerceptronFile = new File(filePath + _RANDOM + ".ser");

        boolean backupRandomPerceptron = false;
        if ( !perceptronFile.exists() ) {
            if ( randomPerceptronFile.exists() ) {
                randomPerceptronFile.delete();
            }
            backupRandomPerceptron = true;
        }

        boolean visible = false;
        if ( delayPerMove > 0 ) {
            visible = true;
        }

        //creamos el juego
        Game2048<NeuralNetworkClass> game = new Game2048<>(neuralNetworkInterfaceFor2048.getPerceptronConfiguration(), tileToWin, visible, delayPerMove, true);

        // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
        // En caso contrario creamos un perceptron vacio, inicializado al azar
        neuralNetworkInterfaceFor2048.loadOrCreatePerceptron(perceptronFile, this.initializePerceptronRandomized);

        //creamos una interfaz de comunicacion entre la red neuronal de encog y el algoritmo de entrenamiento
        if ( backupRandomPerceptron ) {
            //guardamos el perceptron inicial para ahcer estadisticas
            neuralNetworkInterfaceFor2048.savePerceptron(randomPerceptronFile);
        } else {
            if ( !this.statisticsOnly ) {
                //guardamos un backup del perceptron antes de entrenar
                neuralNetworkInterfaceFor2048.savePerceptron(perceptronFileBackup);
            }
        }

        this.setLearningAlgorithm(instanceOfTdLearninrgImplementation(this.getNeuralNetworkInterfaceFor2048().getPerceptronInterface()));
        if ( learningAlgorithm == null && !this.statisticsOnly ) {
            throw new IllegalArgumentException("learningAlgorithm no puede ser null");
        }

        statisticExperiment = new StatisticExperiment(this) {
            @Override
            protected void initializeStatistics() throws Exception {
                this.setGamesToPlayPerThread(gamesToPlayPerThreadForStatistics);
                this.setSimulations(simulationsForStatistics);
                this.setLearningMethod(learningAlgorithm);
            }
        };
        statisticExperiment.setDateForFileName(now);

        System.out.println("Training...");

        //creamos un archivo de logs para acumular estadisticas
        File logFile = new File(filePath + "_" + dateFormater.format(now) + "_LOG" + ".txt");

        if ( !this.statisticsOnly ) {
            //comenzamos a entrenar y guardar estadisticas en el archivo de log
            int lastSaveCounter = 0;
            if ( logsActivated ) {
                try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                    training(game, printStream, lastSaveCounter, randomPerceptronFile, perceptronFile, backupNumber, filePath, dateFormater, now, zeroNumbers);
                }
            } else {
                training(game, null, lastSaveCounter, randomPerceptronFile, perceptronFile, backupNumber, filePath, dateFormater, now, zeroNumbers);
            }

            //guardamos los progresos en un archivo
            neuralNetworkInterfaceFor2048.savePerceptron(perceptronFile);
            //  neuralNetworkInterfaceFor2048.compareNeuralNetworks(randomPerceptronFile, perceptronFile);
        }
        //cerramos el juego
        game.dispose();

        System.out.println("Training Finished.");

        if ( this.simulationsForStatistics > 0 && this.gamesToPlayPerThreadForStatistics > 0 ) {
            //hacemos estadisticas del perceptron random, si es necesario
            Map<File, List<Double>> resultsRandom = new HashMap<>();
            if ( backupRandomPerceptron || isRunStatisticForRandom() ) {
                statisticExperiment.setPerceptronName(this.getExperimentName() + LearningExperiment._RANDOM);
                statisticExperiment.start(experimentPath, 0);
                resultsRandom.put(randomPerceptronFile, statisticExperiment.getTileStatistics());
            }

            if ( isRunStatisticsForBackups() ) {
                //calculamos las estadisticas de los backup si es necesario
                File[] allFiles = (new File(dirPath)).listFiles();
                Arrays.sort(allFiles, (Object o1, Object o2) -> {
                    if ( ((File) o1).lastModified() > ((File) o2).lastModified() ) {
                        return +1;
                    } else if ( ((File) o1).lastModified() < ((File) o2).lastModified() ) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
                List<File> backupFiles = new ArrayList<>();
                Map<File, List<Double>> resultsPerFile = new HashMap<>();
                for ( File f : allFiles ) {
                    if ( f.getName().matches(".*\\_BackupN\\-.*\\.ser") ) {
                        statisticExperiment.setPerceptronName(f.getName().replaceAll("\\.ser$", ""));
                        statisticExperiment.start(experimentPath, 0);
                        resultsPerFile.put(f, statisticExperiment.getTileStatistics());
                        backupFiles.add(f);
                    }
                }
                backupFiles.sort((Object o1, Object o2) -> {
                    if ( ((File) o1).lastModified() > ((File) o2).lastModified() ) {
                        return +1;
                    } else if ( ((File) o1).lastModified() < ((File) o2).lastModified() ) {
                        return -1;
                    } else {
                        return 0;
                    }
                });

                InputStream inputXLSX = this.getClass().getResourceAsStream("/ar/edu/unrc/game2048/resources/Estadisticas.xlsx");
                Workbook wb = WorkbookFactory.create(inputXLSX);
                Sheet sheet = wb.getSheetAt(0);
                int tiles = 17;

                //configuraciones basadas en el spreadsheet
                int rowStart = 2;
                int colStart = 3;
                try ( FileOutputStream outputXLSX = new FileOutputStream(filePath + "_" + dateFormater.format(now) + "_STATISTICS" + ".xlsx") ) {
                    for ( int tile = 0; tile <= tiles; tile++ ) {
                        Row row = sheet.getRow(tile + rowStart - 1);
                        for ( int file = 0; file < backupFiles.size(); file++ ) {
                            Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                            Double cellValue = resultsPerFile.get(backupFiles.get(file)).get(tile);
                            cell.setCellValue(cellValue);
                        }
                    }
                    if ( !resultsRandom.isEmpty() ) {
                        for ( int tile = 0; tile <= tiles; tile++ ) {
                            int file = 0;
                            Row row = sheet.getRow(tile + rowStart - 1);
                            Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                            Double cellValue = resultsRandom.get(randomPerceptronFile).get(tile);
                            cell.setCellValue(cellValue);
                        }
                    }
                    wb.write(outputXLSX);
                }
            } else {
                //calculamos estadisticas para el perceptron entrenado
                statisticExperiment.setPerceptronName(this.getExperimentName() + LearningExperiment._TRAINED);
                statisticExperiment.start(experimentPath, 0);
            }

        }
    }

}
