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
import java.io.File;
import java.io.PrintStream;
import static java.lang.Math.round;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 * @param <NeuralNetworkClass>
 */
public abstract class StatisticExperiment<NeuralNetworkClass> {

    private Date dateForFileName;

    private String experimentName;

    private int gamesToPlay;
    private TDLambdaLearning learningMethod;
    private String perceptronName;
    private int simulations;
    private List<Double> tileStatistics;

    private int tileToWin;

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
    public List<Double> getTileStatistics() {
        return tileStatistics;
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

    /**
     * @return the perceptronName
     */
    protected String getPerceptronName() {
        return perceptronName;
    }

    /**
     * @param perceptronName the perceptronName to set
     */
    protected void setPerceptronName(String perceptronName) {
        this.perceptronName = perceptronName;
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
     * <li>private String perceptronName;</li>
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
        System.out.print("Starting " + this.getPerceptronName() + " Statistics... ");
        String dirPath = experimentPath
                + this.learningExperiment.getNeuralNetworkInterfaceFor2048().getLibName() + File.separator
                + experimentName + File.separator;
        File dirPathFile = new File(dirPath);
        if ( !dirPathFile.exists() ) {
            dirPathFile.mkdirs();
        }
        String filePath = dirPath + perceptronName;

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

            if ( tempPerceptronConfiguration != null || tempPerceptronInterface != null ) {
                //cargamos la red neuronal entrenada
                File perceptronFile = new File(filePath + ".ser");
                if ( !perceptronFile.exists() ) {
                    throw new IllegalArgumentException("perceptron file must exists");
                }
                neuralNetworkInterfaceClone.loadOrCreatePerceptron(perceptronFile, true);
            }

            Game2048<NeuralNetworkClass> game = new Game2048<>(tempPerceptronConfiguration, tempNTupleConfiguration, tileToWin, delayPerMove);

            neuralNetworkInterfaces.add(neuralNetworkInterfaceClone);
            tdLambdaLearning.add(learningExperiment.instanceOfTdLearninrgImplementation(tempPerceptronInterface)); //TODO revisar esto
            games.add(game);
            results.add(new ThreadResult());
        }

        IntStream
                .range(0, simulations)
                .parallel()
                .forEach(i -> {
                    // Si hay un perceptron ya entrenado, lo buscamos en el archivo.
                    // En caso contrario creamos un perceptron vacio, inicializado al azar
                    for ( results.get(i).setProcesedGames(0); results.get(i).getProcesedGames() < gamesToPlay; results.get(i).addProcesedGames() ) {
                        games.get(i).resetGame(); //reset
                        while ( !games.get(i).iLoose() && !games.get(i).iWin() ) {
                            neuralNetworkInterfaces.get(i).playATurn(games.get(i), tdLambdaLearning.get(i)).compute();
                        }
                        //calculamos estadisticas
                        results.get(i).addStatisticForTile(games.get(i).getMaxNumberCode());

                        if ( results.get(i).getMaxScoreAchieved().containsKey(games.get(i).getScore()) ) {
                            Integer number = results.get(i).getMaxScoreAchieved().get(games.get(i).getScore());
                            results.get(i).getMaxScoreAchieved().put(games.get(i).getScore(), number + 1);
                        } else {
                            results.get(i).getMaxScoreAchieved().put(games.get(i).getScore(), 1);
                        }

                        if ( games.get(i).iWin() ) {
                            results.get(i).addWin();
                        }
                    }
                    games.get(i).dispose();
                });

        int winGames = 0;

        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0d);
        }
        Map<Integer, Integer> maxScoreAchieved = new HashMap<>();
        for ( ThreadResult result : results ) {
            winGames += result.getWinGames();
            for ( int i = 0; i <= 17; i++ ) {
                tileStatistics.set(i, tileStatistics.get(i) + result.getStatisticForTile(i));
            }
            result.getMaxScoreAchieved().entrySet().stream().forEach((entry) -> {
                if ( maxScoreAchieved.containsKey(entry.getKey()) ) {
                    Integer oldScore = maxScoreAchieved.get(entry.getKey());
                    maxScoreAchieved.put(entry.getKey(), entry.getValue() + oldScore);
                } else {
                    maxScoreAchieved.put(entry.getKey(), entry.getValue());
                }
            });
        }
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.set(i, tileStatistics.get(i) / (simulations * 1d));
        }

        double winRate = (winGames * 100) / gamesToPlay;

        if ( !results.isEmpty() ) {
            //creamos un archivo de logs para acumular estadisticas
            Date now;
            if ( dateForFileName != null ) {
                now = dateForFileName;
            } else {
                now = new Date();
            }
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy_HH'h'mm'm'ss's'");
            File logFile = new File(filePath + "_" + dateFormater.format(now) + "_STATISTICS" + ".txt");

            try ( PrintStream printStream = new PrintStream(logFile, "UTF-8") ) {
                printStream.print("Gano: " + winGames + " (" + round(winRate) + "%) - Total de partidas: " + gamesToPlay + " (promedios obtenidos con " + simulations + " simulaciones)");
                printStream.println("\nValores alcanzados:");
                for ( int i = 0; i <= 17; i++ ) {
                    printStream.println(tileStatistics.get(i).toString().replaceAll("\\.", ","));
                }
                printStream.println("\nPuntajes alcanzados (valor/veces):");
                maxScoreAchieved.entrySet().stream().sorted(
                        (e1, e2) -> e1.getKey().compareTo(e2.getKey())
                ).forEach((e) -> {
                    printStream.println(e.getKey() + "\t" + e.getValue());
                });
            }
            System.out.println("Finished.");
        }
    }

}
