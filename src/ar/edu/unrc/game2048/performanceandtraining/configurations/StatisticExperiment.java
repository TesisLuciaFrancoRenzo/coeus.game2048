/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.utils.ThreadResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import static java.lang.Math.round;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class StatisticExperiment<NeuralNetworkClass> {

    private Date dateForFileName;
    private SimpleDateFormat dateFormater;

    private String experimentName;
    private String fileName;

    private int gamesToPlay;
    private TDLambdaLearning learningMethod;
    private double maxScore;
    private double maxTurn;
    private double meanScore;
    private double meanTurn;
    private double minScore;
    private double minTurn;
    private int saveBackupEvery;
    private int simulations;
    private List<Double> tileStatistics;

    private int tileToWin;
    private double winRate;

    /**
     *
     */
    protected LearningExperiment<NeuralNetworkClass> learningExperiment;

    /**
     *
     * @param learningExperiment
     */
    public StatisticExperiment(LearningExperiment<NeuralNetworkClass> learningExperiment) {
        this.learningExperiment = learningExperiment;
    }

    /**
     * @param gamesToPlay the gamesToPlay to set
     */
    public void setGamesToPlayPerThread(int gamesToPlay) {
        this.gamesToPlay = gamesToPlay;
    }

    /**
     * @return the tileStatistics
     */
    public StatisticForCalc getTileStatistics() {
        StatisticForCalc statistic = new StatisticForCalc();
        statistic.setWinRate(winRate);
        statistic.setMaxScore(maxScore);
        statistic.setMeanScore(meanScore);
        statistic.setMinScore(minScore);
        statistic.setMaxTurn(maxTurn);
        statistic.setMeanTurn(meanTurn);
        statistic.setMinTurn(minTurn);
        statistic.setTileStatistics(tileStatistics);
        return statistic;
    }

    /**
     *
     * @param rowStartTitle
     * @param colStartTitle
     * @param sheet
     * @param backupFilesSize
     * @param CellStyleTitle
     */
    public void loadTitle(int rowStartTitle, int colStartTitle, Sheet sheet, int backupFilesSize, CellStyle CellStyleTitle) {
        int total_juegos = saveBackupEvery;
        Row row1 = sheet.getRow(rowStartTitle);
        for ( int file = 1; file <= backupFilesSize; file++ ) {
            Cell cell = row1.createCell(file + colStartTitle, Cell.CELL_TYPE_NUMERIC);
            cell.setCellStyle(CellStyleTitle);
            Integer value = total_juegos * file;
            String valueStr = value.toString();
            String cellV = valueStr;
            if ( valueStr.length() > 3 ) {
                cellV = valueStr.substring(0, valueStr.length() - 3) + "K";
            }
            cell.setCellValue(cellV);
        }
    }

    public void processFile(String fileToProcess, int delayPerMove) throws Exception {

        //preparamos los destinos de las siimulaciones para posterior sumatoria final
        List<ThreadResult> results = new ArrayList(simulations);
        List<Game2048<NeuralNetworkClass>> games = new ArrayList(simulations);
        List<INeuralNetworkInterfaceFor2048<NeuralNetworkClass>> neuralNetworkInterfaces = new ArrayList(simulations);
        List<TDLambdaLearning> tdLambdaLearning = new ArrayList(simulations);

        for ( int i = 0; i < simulations; i++ ) {
            INeuralNetworkInterfaceFor2048<NeuralNetworkClass> neuralNetworkInterfaceClone;
            PerceptronConfiguration2048<NeuralNetworkClass> tempPerceptronConfiguration = null;
            neuralNetworkInterfaceClone = (INeuralNetworkInterfaceFor2048<NeuralNetworkClass>) learningExperiment.getNeuralNetworkInterfaceFor2048().clone();

            NTupleConfiguration2048 tempNTupleConfiguration = null;
            IPerceptronInterface tempPerceptronInterface = null;

            if ( learningExperiment.getNeuralNetworkInterfaceFor2048().getPerceptronConfiguration() != null ) {
                tempPerceptronConfiguration = (PerceptronConfiguration2048<NeuralNetworkClass>) learningExperiment.getNeuralNetworkInterfaceFor2048().getPerceptronConfiguration().clone();
                neuralNetworkInterfaceClone.setPerceptronConfiguration(tempPerceptronConfiguration);
                tempPerceptronInterface = neuralNetworkInterfaceClone.getPerceptronInterface(); //TODO revisar esto
            }
            if ( learningExperiment.getNeuralNetworkInterfaceFor2048().getNTupleConfiguration() != null ) {
                tempNTupleConfiguration = (NTupleConfiguration2048) learningExperiment.getNeuralNetworkInterfaceFor2048().getNTupleConfiguration().clone();
                neuralNetworkInterfaceClone.setNTupleConfiguration(tempNTupleConfiguration);
            }

            if ( tempPerceptronConfiguration != null || tempNTupleConfiguration != null ) {
                //cargamos la red neuronal entrenada
                File perceptronFile = new File(fileToProcess + ".ser");
                if ( !perceptronFile.exists() ) {
                    throw new IllegalArgumentException("perceptron file must exists: " + perceptronFile.getCanonicalPath());
                }
                neuralNetworkInterfaceClone.loadOrCreatePerceptron(perceptronFile, true);
            }

            Game2048<NeuralNetworkClass> game = new Game2048<>(tempPerceptronConfiguration, tempNTupleConfiguration, tileToWin, delayPerMove);

            neuralNetworkInterfaces.add(neuralNetworkInterfaceClone);
            if ( tempPerceptronConfiguration != null ) {
                tdLambdaLearning.add(learningExperiment.instanceOfTdLearninrgImplementation(tempPerceptronInterface)); //TODO revisar esto
            }
            if ( tempNTupleConfiguration != null ) {
                tdLambdaLearning.add(learningExperiment.instanceOfTdLearninrgImplementation(tempNTupleConfiguration.getNTupleSystem())); //TODO revisar esto
            }
            games.add(game);
            results.add(new ThreadResult());
        }

        IntStream
                .range(0, simulations)
                .parallel()
                .forEach(i -> {
                    // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
                    // En caso contrario creamos un perceptron vacio, inicializado al azar
                    for ( results.get(i).setProcesedGames(1); results.get(i).getProcesedGames() <= gamesToPlay; results.get(i).addProcesedGames() ) {
                        games.get(i).resetGame(); //reset
                        while ( !games.get(i).iLoose() && !games.get(i).iWin() ) {
                            if ( tdLambdaLearning.isEmpty() ) {
                                neuralNetworkInterfaces.get(i).playATurn(games.get(i), null).compute();
                            } else {
                                neuralNetworkInterfaces.get(i).playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                            }
                        }
                        //calculamos estadisticas
                        results.get(i).addStatisticForTile(games.get(i).getMaxNumberCode());
                        results.get(i).addScore(games.get(i).getScore());

                        if ( games.get(i).getMaxNumber() >= 2_048 ) {
                            results.get(i).addWin();
                            results.get(i).addLastTurn(games.get(i).getLastTurn());
                        }

                    }
                    games.get(i).dispose();
                });

        winRate = 0;
        maxScore = 0;
        minScore = 0;
        meanScore = 0;
        maxTurn = 0;
        minTurn = 0;
        meanTurn = 0;

        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0d);
        }
        results.stream().forEach((result) -> {
            winRate += result.getWinRate();
            maxScore += result.getMaxScore();
            minScore += result.getMinScore();
            meanScore += result.getMeanScore();
            maxTurn += result.getMaxTurn();
            minTurn += result.getMinTurn();
            meanTurn += result.getMeanTurn();
            for ( int i = 0; i <= 17; i++ ) {
                tileStatistics.set(i, tileStatistics.get(i) + result.getStatisticForTile(i));
            }
        });

        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.set(i, tileStatistics.get(i) / (simulations * 1d));
        }
        winRate = winRate / (simulations * 1d);
        assert winRate < 100;
        maxScore = maxScore / (simulations * 1d);
        minScore = minScore / (simulations * 1d);
        meanScore = meanScore / (simulations * 1d);
        maxTurn = maxTurn / (simulations * 1d);
        minTurn = minTurn / (simulations * 1d);
        meanTurn = meanTurn / (simulations * 1d);

        if ( !results.isEmpty() ) {
            //creamos un archivo de logs para acumular estadisticas
            Date now;
            if ( dateForFileName != null ) {
                now = dateForFileName;
            } else {
                now = new Date();
            }
            File logFile = new File(fileToProcess + "_" + dateFormater.format(now) + "_STATISTICS" + ".txt");

            try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                printStream.print("Gano: " + round(winRate) + "% - Total de partidas: " + gamesToPlay + " (promedios obtenidos con " + simulations + " simulaciones)");
                printStream.println("\nValores alcanzados:");
                for ( int i = 0; i <= 17; i++ ) {
                    printStream.println(tileStatistics.get(i).toString().replaceAll("\\.", ","));
                }
                printStream.println("\nPuntajes alcanzados (valor/veces):");

                printStream.println("Maximo puntaje:" + maxScore);
                printStream.println("Media  puntaje:" + meanScore);
                printStream.println("Minimo puntaje:" + minScore);

                printStream.println("Maximo turno:" + maxTurn);
                printStream.println("Media  turno:" + meanTurn);
                printStream.println("Minimo turno:" + minTurn);
            }
            System.out.println("Finished.");
        }
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
            learningMethod = null;
            if ( learningExperiment != null ) {
                this.tileToWin = learningExperiment.getTileToWin();
                this.experimentName = learningExperiment.getExperimentName();
            }
            initializeStatistics();
            run(experimentPath, delayPerMove);
        } catch ( Exception ex ) {
            Logger.getLogger(StatisticExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the dateForFileName
     */
    protected Date getDateForFileName() {
        return dateForFileName;
    }

    /**
     * @param dateForFileName the dateForFileName to set
     */
    protected void setDateForFileName(Date dateForFileName) {
        this.dateForFileName = dateForFileName;
    }

    /**
     * @return the experimentName
     */
    protected String getExperimentName() {
        return experimentName;
    }

    /**
     * @param experimentName the experimentName to set
     */
    protected void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * @return the fileName
     */
    protected String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the gamesToPlay
     */
    protected int getGamesToPlay() {
        return gamesToPlay;
    }

    /**
     * @return the learningMethod
     */
    protected TDLambdaLearning getLearningMethod() {
        return learningMethod;
    }

    /**
     * @param learningMethod the learningMethod to set
     */
    protected void setLearningMethod(TDLambdaLearning learningMethod) {
        this.learningMethod = learningMethod;
    }

    protected void setSimpleDateFormat(SimpleDateFormat dateFormater) {
        this.dateFormater = dateFormater;
    }

    /**
     * @return the simulations
     */
    protected int getSimulations() {
        return simulations;
    }

    /**
     * @param threads the simulations to set
     */
    public void setSimulations(int threads) {
        this.simulations = threads;
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
    protected void setTileToWin(int tileToWin) {
        this.tileToWin = tileToWin;
    }

    /**
     * Se deben inicializar:
     * <ul>
     * <li>private int delayPerMove;</li>
     * <li>private IPlayingExperiment playingExperiment;</li>
     * <li>private String fileName;</li>
     * </ul>
     * <p>
     * Las siguientes variables se inicializan automaticamente, pero pueden ser
     * modificadas:
     * <ul>
     * <li>private int tileToWin;</li>
     * <li>private String experimentName;</li>
     * <li>private PerceptronConfiguration2048 perceptronConfiguration;</li>
     * <li>private TDLambdaLearning learningMethod;</li>
     * </ul>
     * <p>
     * @throws Exception
     */
    protected abstract void initializeStatistics() throws Exception;

    /**
     *
     * @param experimentPath
     * @param delayPerMove   <p>
     * @throws Exception
     */
    protected void run(String experimentPath, int delayPerMove) throws Exception {
        String dirPath = experimentPath
                + this.learningExperiment.getNeuralNetworkInterfaceFor2048().getLibName() + File.separator
                + experimentName + File.separator;
        File dirPathFile = new File(dirPath);
        if ( !dirPathFile.exists() ) {
            dirPathFile.mkdirs();
        }
        String filePath = dirPath + fileName;
        File randomPerceptronFile = new File(dirPath + this.getExperimentName() + LearningExperiment._RANDOM + ".ser");

        //hacemos estadisticas del perceptron random, si es necesario
        Map<File, StatisticForCalc> resultsRandom = new HashMap<>();
        System.out.print("Starting " + this.getExperimentName() + LearningExperiment._RANDOM + " Statistics... ");
        processFile(dirPath + this.getExperimentName() + LearningExperiment._RANDOM, delayPerMove);
        resultsRandom.put(randomPerceptronFile, getTileStatistics());

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
        Map<File, StatisticForCalc> resultsPerFile = new HashMap<>();
        for ( File f : allFiles ) {
            if ( f.getName().matches(".*\\_BackupN\\-.*\\.ser") ) {
                System.out.print("Starting " + f.getName() + " Statistics... ");
                processFile(dirPath + f.getName().replaceAll("\\.ser$", ""), delayPerMove);
                resultsPerFile.put(f, getTileStatistics());
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

        exportToExcel(filePath, backupFiles, resultsPerFile, resultsRandom, randomPerceptronFile);

    }

    public void exportToExcel(String filePath, List<File> backupFiles, Map<File, StatisticForCalc> resultsPerFile, Map<File, StatisticForCalc> resultsRandom, File randomPerceptronFile) throws IOException, InvalidFormatException {
        InputStream inputXLSX = this.getClass().getResourceAsStream("/ar/edu/unrc/game2048/resources/Estadisticas.xlsx");
        Workbook wb = WorkbookFactory.create(inputXLSX);

        try ( FileOutputStream outputXLSX = new FileOutputStream(filePath + "_" + dateFormater.format(dateForFileName) + "_STATISTICS" + ".xlsx") ) {
            //============= imptimimos en la hoja de tiles ===================

            Sheet sheet = wb.getSheetAt(0);
            int tiles = 17;
            //Estilo par los titulos de las tablas
            int rowStartTitle = 0;
            int colStartTitle = 2;
            // Luego creamos el objeto que se encargará de aplicar el estilo a la celda
            Font fontCellTitle = wb.createFont();
            fontCellTitle.setFontHeightInPoints((short) 10);
            fontCellTitle.setFontName("Arial");
            fontCellTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle CellStyleTitle = wb.createCellStyle();
            CellStyleTitle.setWrapText(true);
            CellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
            CellStyleTitle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            CellStyleTitle.setFont(fontCellTitle);

            // Establecemos el tipo de sombreado de nuestra celda
            CellStyleTitle.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            CellStyleTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            //estilo titulo finalizado

            //Estilo de celdas con los valores de las estadisticas
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);
            /* We are now ready to set borders for this style */
            /* Draw a thin left border */
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            /* Add medium right border */
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            /* Add dashed top border */
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            /* Add dotted bottom border */
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            //estilo celdas finalizado

            //configuraciones basadas en el spreadsheet
            int rowStart = 2;
            int colStart = 3;
            for ( int tile = 0; tile <= tiles; tile++ ) {
                Row row = sheet.getRow(tile + rowStart - 1);
                for ( int file = 0; file < backupFiles.size(); file++ ) {
                    Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                    cell.setCellStyle(cellStyle);
                    Double cellValue = resultsPerFile.get(backupFiles.get(file)).getTileStatistics().get(tile);
                    cell.setCellValue(cellValue);
                }
            }
            if ( !resultsRandom.isEmpty() ) {
                for ( int tile = 0; tile <= tiles; tile++ ) {
                    int file = 0;
                    Row row = sheet.getRow(tile + rowStart - 1);
                    Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                    Double cellValue = resultsRandom.get(randomPerceptronFile).getTileStatistics().get(tile);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(cellValue);
                }
            }

            //============= imptimimos en la hoja de Score ===================
            sheet = wb.getSheetAt(1);
            rowStart = 2;
            loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            Row row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMinScore();
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMinScore();
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }

            rowStart = 3;
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMeanScore();
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMeanScore();
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }

            rowStart = 4;
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMaxScore();
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMaxScore();
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cellValue);
            }

            //============= imptimimos en la hoja de Win ===================
            sheet = wb.getSheetAt(2);
            rowStart = 2;
            loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getWinRate();
                assert cellValue <= 100 && cellValue >= 0;
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getWinRate();
                assert cellValue <= 100 && cellValue >= 0;
                cell.setCellValue(cellValue);
            }

            //============= imptimimos en la hoja de Turns ===================
            sheet = wb.getSheetAt(3);
            rowStart = 2;
            loadTitle(rowStartTitle, colStartTitle, sheet, backupFiles.size(), CellStyleTitle);
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMinTurn();
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMinTurn();
                cell.setCellValue(cellValue);
            }

            rowStart = 3;
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMeanTurn();
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMeanTurn();
                cell.setCellValue(cellValue);
            }

            rowStart = 4;
            row = sheet.getRow(rowStart - 1);
            for ( int file = 0; file < backupFiles.size(); file++ ) {
                Cell cell = row.createCell(file + colStart, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMaxTurn();
                cell.setCellValue(cellValue);
            }
            if ( !resultsRandom.isEmpty() ) {
                int file = 0;
                Cell cell = row.createCell(file + colStart - 1, Cell.CELL_TYPE_NUMERIC);
                cell.setCellStyle(cellStyle);
                Double cellValue = resultsPerFile.get(backupFiles.get(file)).getMaxTurn();
                cell.setCellValue(cellValue);
            }

            wb.write(outputXLSX);
        }
    }

    protected void saveBackupEvery(int saveBackupEvery) {
        this.saveBackupEvery = saveBackupEvery;
    }

}
