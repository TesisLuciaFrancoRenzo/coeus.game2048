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
package ar.edu.unrc.game2048.performanceandtraining.configurations.performance;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.BinaryScore;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import ar.edu.unrc.utils.StringAndFiles;
import static ar.edu.unrc.utils.StringAndFiles.UTF_8;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ConcurrencyExperiment_01 extends LearningExperiment<BasicNetwork> {

    /**
     *
     */
    public static int GAMES_TO_PLAY;

    /**
     *
     */
    public static int MAX_INNER_LAYERS;

    /**
     *
     */
    public static int MAX_NEURON_QUANTITY;

    /**
     *
     */
    public static int MIN_NEURON_QUANTITY;

    /**
     *
     */
    public static int SAMPLES_PER_EXPERIMENT;

    /**
     *
     */
    public static ConcurrencyConfig currentConfig;

    /**
     *
     */
    public static String filePath;

    /**
     *
     */
    public static StringBuilder outputResults;

    /**
     *
     * @param concurrency
     *
     * @throws Exception
     */
    public static void experimentSet(boolean concurrency) throws Exception {
        for ( int innerLayerQuantity = 1; innerLayerQuantity <= MAX_INNER_LAYERS; innerLayerQuantity++ ) {
            System.out.println("===============================================================================");
            System.out.println("Capas intermedias: " + innerLayerQuantity);
            System.out.println("===============================================================================");
            for ( int innerLayersNeuronQuantity = MIN_NEURON_QUANTITY; innerLayersNeuronQuantity <= MAX_NEURON_QUANTITY; innerLayersNeuronQuantity++ ) {
                // Primer experimento, con 1 capa, en serie
                currentConfig = new ConcurrencyConfig();

                currentConfig.concurrencyInLayer = new boolean[innerLayerQuantity + 2];
                for ( int i = 0; i < innerLayerQuantity + 1; i++ ) {
                    currentConfig.concurrencyInLayer[i] = concurrency;
                }
                currentConfig.concurrencyInLayer[currentConfig.concurrencyInLayer.length - 1] = false;

                currentConfig.alphas = new double[innerLayerQuantity + 1];
                for ( int i = 0; i < innerLayerQuantity + 1; i++ ) {
                    currentConfig.alphas[i] = 0.0025;
                }

                currentConfig.neuronQuantityInLayer = new int[innerLayerQuantity + 2];
                currentConfig.neuronQuantityInLayer[0] = 64;
                for ( int i = 1; i <= innerLayerQuantity; i++ ) {
                    currentConfig.neuronQuantityInLayer[i] = (int) Math.pow(2, innerLayersNeuronQuantity);
                }
                currentConfig.neuronQuantityInLayer[currentConfig.neuronQuantityInLayer.length - 1] = 1;

                currentConfig.activationFunctionForEncog = new ActivationFunction[innerLayerQuantity + 1];
                for ( int i = 0; i < innerLayerQuantity + 1; i++ ) {
                    currentConfig.activationFunctionForEncog[i] = new ActivationTANH();
                }

                System.out.println("========================================================");
                System.out.println("Inicio Experimento:");
                System.out.println(currentConfig.toString());
                System.out.println("========================================================");

                StatisticCalculator trainingStats = new StatisticCalculator(SAMPLES_PER_EXPERIMENT);
                StatisticCalculator bestPossibleStats = new StatisticCalculator(SAMPLES_PER_EXPERIMENT);
                long time;
                for ( int i = 1; i <= SAMPLES_PER_EXPERIMENT; i++ ) {
                    System.out.println("Calculo de muestra N" + i);
                    time = System.currentTimeMillis();
                    startStatistics(trainingStats, bestPossibleStats);
                    time = System.currentTimeMillis() - time;
                    System.out.println("Final de Calculo de muestra N" + i + " - demoró " + time + "ms");
                }
                String resultsTraining = trainingStats.computeBasicStatistics();
                String forSaveTraining = trainingStats.toString();
                String resultsBestPossible = bestPossibleStats.computeBasicStatistics();
                String forSaveBestPossible = bestPossibleStats.toString();

                System.out.println("====================================");
                System.out.println("Fin Experimento:");
                System.out.println(currentConfig.toString());
                System.out.println("** Training DEMORÓ => " + resultsTraining);
                System.out.println("** Best Possible Action DEMORÓ => " + resultsBestPossible);
                System.out.println("====================================");

                outputResults.append("====================================\n");
                outputResults.append(currentConfig.toString()).append("\n");
                outputResults.append("muestras Training => ").append(forSaveTraining).append("\n");
                outputResults.append("resultados Training => ").append(resultsTraining).append("\n");
                outputResults.append("muestras Best Possible Action => ").append(forSaveBestPossible).append("\n");
                outputResults.append("resultados Best Possible Action => ").append(resultsBestPossible).append("\n");
            }
        }
    }

    /**
     *
     * @param args <p>
     */
    public static void main(String[] args) {
        try {
            File output;
            if ( args.length == 0 ) {
                filePath
                        = ".." + File.separator
                        + "Estadisticas" + File.separator;
            } else {
                filePath = args[0];
            }
            output = new File(filePath);
            if ( !output.exists() ) {
                output.mkdirs();
            }

            SAMPLES_PER_EXPERIMENT = 5;
            GAMES_TO_PLAY = 40;
            MAX_INNER_LAYERS = 2;
            MAX_NEURON_QUANTITY = 9;
            MIN_NEURON_QUANTITY = 2;

            outputResults = new StringBuilder();
            experimentSet(false);
            experimentSet(true);

            StringAndFiles.stringToFile(new File(filePath + "Concurrencia_Experimento_01.txt"), outputResults.toString(), UTF_8);
        } catch ( Exception ex ) {
            Logger.getLogger(ConcurrencyExperiment_01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param bestPossibleStats
     * @return tiempo demorado en entrenar en milisegundos
     *
     * @throws Exception
     */
    public static void startStatistics(StatisticCalculator trainingStats, StatisticCalculator bestPossibleStats) throws Exception {
        LearningExperiment experiment = new ConcurrencyExperiment_01();
        experiment.setAlpha(currentConfig.alphas);
        experiment.setConcurrencyInLayer(currentConfig.concurrencyInLayer);
        experiment.setLearningRateAdaptationToAnnealing(500_000);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setResetEligibilitiTraces(false);
        experiment.setGamesToPlay(GAMES_TO_PLAY);
        experiment.setSaveEvery(10_000); //no se guarda nada
        experiment.setSaveBackupEvery(10_000); //no se hacen backup
        experiment.setInitializePerceptronRandomized(false);
        experiment.setComputeBestPosibleActionConcurrently(false);

        experiment.createLogs(false);
        experiment.setStatisticsOnly(false);
        experiment.setRunStatisticForRandom(false);
        experiment.setRunStatisticsForBackups(false);
        experiment.setGamesToPlayPerThreadForStatistics(0);
        experiment.setSimulationsForStatistics(0);
        experiment.start(filePath, 0, false);

        bestPossibleStats.addSample(experiment.getAvgBestPissibleActionTimes());
        trainingStats.addSample(experiment.getAvgTrainingTimes());
    }

    @Override
    public void initialize() throws Exception {
        this.setTileToWin(2_048);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("ConcurrencyTimes");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new BinaryScore<>();
        config.setNeuronQuantityInLayer(currentConfig.neuronQuantityInLayer);
        config.setActivationFunctionForEncog(currentConfig.activationFunctionForEncog);
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), getGamma(), getConcurrencyInLayer(), isResetEligibilitiTraces(), true);
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }

}
