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
package ar.edu.unrc.game2048.performanceandtraining.experiments.performance;

import ar.edu.unrc.coeus.interfaces.IPerceptronInterface;
import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.PBinary;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class ConcurrencyExperiment_Basic extends LearningExperiment<BasicNetwork> {

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
    public static StringBuilder outputForGraphicsResults;

    /**
     *
     */
    public static StringBuilder outputResults;

    /**
     *
     * @param trainConcurrency,   boolean concurrencyInEvaluate
     * @param evaluateConcurrency
     *
     * @throws Exception
     */
    public static void experimentSet(boolean trainConcurrency,
            boolean evaluateConcurrency) throws Exception {
        for ( int innerLayerQuantity = 1; innerLayerQuantity <= MAX_INNER_LAYERS;
                innerLayerQuantity++ ) {
            System.out.println(
                    "===============================================================================");
            System.out.println("Capas intermedias: " + innerLayerQuantity);
            outputForGraphicsResults.append("============ Capas intermedias: ").
                    append(innerLayerQuantity)
                    .append(" - Training Concurrancy: ").
                    append(trainConcurrency)
                    .append(" - evaluating Concurrancy: ").append(
                    evaluateConcurrency)
                    .append(" =============\n");
            System.out.println(
                    "===============================================================================");

            StringBuilder trainingOutput = new StringBuilder();
            StringBuilder evaluateOutput = new StringBuilder();
            for ( int innerLayersNeuronQuantity = MIN_NEURON_QUANTITY;
                    innerLayersNeuronQuantity <= MAX_NEURON_QUANTITY;
                    innerLayersNeuronQuantity++ ) {
                // Primer experimento, con 1 capa, en serie
                currentConfig = new ConcurrencyConfig();
                currentConfig.concurrencyInEvaluate = evaluateConcurrency;

                currentConfig.concurrencyInLayer = new boolean[innerLayerQuantity + 2];
                for ( int i = 0; i < innerLayerQuantity + 1; i++ ) {
                    currentConfig.concurrencyInLayer[i] = trainConcurrency;
                }
                currentConfig.concurrencyInLayer[currentConfig.concurrencyInLayer.length - 1] = false;

                currentConfig.alphas = new double[innerLayerQuantity + 2];
                for ( int i = 0; i < innerLayerQuantity + 2; i++ ) {
                    currentConfig.alphas[i] = 0.0025;
                }

                currentConfig.neuronQuantityInLayer = new int[innerLayerQuantity + 2];
                currentConfig.neuronQuantityInLayer[0] = 64;
                for ( int i = 1; i <= innerLayerQuantity; i++ ) {
                    currentConfig.neuronQuantityInLayer[i] = (int) Math.pow(2,
                            innerLayersNeuronQuantity);
                }
                currentConfig.neuronQuantityInLayer[currentConfig.neuronQuantityInLayer.length - 1] = 1;

                currentConfig.activationFunctionForEncog = new ActivationFunction[innerLayerQuantity + 1];
                for ( int i = 0; i < innerLayerQuantity + 1; i++ ) {
                    currentConfig.activationFunctionForEncog[i] = new ActivationTANH();
                }

                System.out.println(
                        "========================================================");
                System.out.println("Inicio Experimento:");
                System.out.println(currentConfig.toString());
                System.out.println(
                        "========================================================");

                StatisticCalculator trainingStats = new StatisticCalculator(
                        SAMPLES_PER_EXPERIMENT);
                StatisticCalculator bestPossibleStats = new StatisticCalculator(
                        SAMPLES_PER_EXPERIMENT);
                long time;
                for ( int i = 1; i <= SAMPLES_PER_EXPERIMENT; i++ ) {
                    System.out.println("Calculo de muestra N" + i);
                    time = System.currentTimeMillis();
                    startStatistics(trainingStats, bestPossibleStats);
                    time = System.currentTimeMillis() - time;
                    System.out.println(
                            "Final de Calculo de muestra N" + i + " - demoró " + time + "ms");
                }
                String[] resultsTraining = trainingStats.
                        computeBasicStatistics();
                String forSaveTraining = trainingStats.toString();
                String[] resultsBestPossible = bestPossibleStats.
                        computeBasicStatistics();
                String forSaveBestPossible = bestPossibleStats.toString();

                System.out.println("====================================");
                System.out.println("Fin Experimento:");
                System.out.println(currentConfig.toString());
                System.out.
                        println("** Training DEMORÓ => " + resultsTraining[0]);
                System.out.println(
                        "** Best Possible Action DEMORÓ => " + resultsBestPossible[0]);
                System.out.println("====================================");

                outputResults.append("====================================\n");
                outputResults.append(currentConfig.toString()).append("\n");
                outputResults.append("muestras Training =>\t").append(
                        forSaveTraining).append("\n");
                outputResults.append("resultados Training =>\t").append(
                        resultsTraining[0]).append("\n");
                outputResults.append("muestras Best Possible Action =>\t").
                        append(forSaveBestPossible).append("\n");
                outputResults.append("resultados Best Possible Action =>\t").
                        append(resultsBestPossible[0]).append("\n");

                trainingOutput.append(innerLayersNeuronQuantity).append("\t").
                        append(resultsTraining[1]).append("\n");
                evaluateOutput.append(innerLayersNeuronQuantity).append("\t").
                        append(resultsBestPossible[1]).append("\n");
            }

            outputForGraphicsResults.append(
                    "Training: (neuron / avg time / min time / max time)").
                    append("\n");
            outputForGraphicsResults.append(trainingOutput).append("\n");

            outputForGraphicsResults.append(
                    "Evaluating: (neuron / avg time / min time / max time)").
                    append("\n");
            outputForGraphicsResults.append(evaluateOutput).append("\n").append(
                    "\n");
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

            SAMPLES_PER_EXPERIMENT = 10;
            GAMES_TO_PLAY = 20;
            MAX_INNER_LAYERS = 1;
            MAX_NEURON_QUANTITY = 12;
            MIN_NEURON_QUANTITY = 2;

            outputResults = new StringBuilder();
            outputForGraphicsResults = new StringBuilder();
            StringBuilder config = new StringBuilder();
            printConfig(config);
            System.out.println(config);

            printConfig(outputResults);
            printConfig(outputForGraphicsResults);

            long time = System.currentTimeMillis();
            experimentSet(false, true);
            experimentSet(true, true);

            time = System.currentTimeMillis() - time;
            System.out.println("Demoró = " + time + " ms.");
            outputResults.append("\n\n==========\nDemoró ").append(time).append(
                    " ms.");
            outputForGraphicsResults.append("\n\n==========\nDemoró ").append(
                    time).append(" ms.");

            try ( BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(
                            filePath + "Concurrencia_Experimento_01.txt")),
                            "UTF-8")) ) {
                out.write(outputResults.toString());
            }
            try ( BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(
                            filePath + "Concurrencia_Experimento_01_Calc.txt")),
                            "UTF-8")) ) {
                out.write(outputForGraphicsResults.toString());
            }
        } catch ( Exception ex ) {
            Logger.getLogger(ConcurrencyExperiment_Basic.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param trainingStats
     * @param bestPossibleStats
     *
     * @throws Exception
     */
    public static void startStatistics(StatisticCalculator trainingStats,
            StatisticCalculator bestPossibleStats) throws Exception {
        LearningExperiment experiment = new ConcurrencyExperiment_Basic();
        experiment.setAlpha(currentConfig.alphas);
        experiment.setConcurrencyInLayer(currentConfig.concurrencyInLayer);
        experiment.setConcurrencyInComputeBestPosibleAction(
                currentConfig.concurrencyInEvaluate);
        experiment.setLearningRateAdaptationToAnnealing(500_000);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setReplaceEligibilityTraces(false);
        experiment.setGamesToPlay(GAMES_TO_PLAY);
        experiment.setSaveEvery(10_000); //no se guarda nada
        experiment.setSaveBackupEvery(10_000); //no se hacen backup
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPosibleAction(false);

        experiment.createLogs(false);
        experiment.setStatisticsOnly(false);
        experiment.setRunStatisticsForBackups(false);
        experiment.setGamesToPlayPerThreadForStatistics(0);
        experiment.setSimulationsForStatistics(0);
        experiment.start(-1, filePath, 0, false, filePath);

        bestPossibleStats.addSample(experiment.getAvgBestPossibleActionTimes());
        trainingStats.addSample(experiment.getAvgTrainingTimes());
    }

    private static void printConfig(StringBuilder outputResults) {
        outputResults.append("====================================").
                append("\n");
        outputResults.append("SAMPLES_PER_EXPERIMENT = ").append(
                SAMPLES_PER_EXPERIMENT).append("\n");
        outputResults.append("GAMES_TO_PLAY = ").append(GAMES_TO_PLAY).append(
                "\n");
        outputResults.append("MAX_INNER_LAYERS = ").append(MAX_INNER_LAYERS).
                append("\n");
        outputResults.append("MAX_NEURON_QUANTITY = ").append(
                MAX_NEURON_QUANTITY).append(" (").append(Math.pow(2,
                MAX_NEURON_QUANTITY)).append(")").append("\n");
        outputResults.append("MIN_NEURON_QUANTITY = ").append(
                MIN_NEURON_QUANTITY).append(" (").append(Math.pow(2,
                MIN_NEURON_QUANTITY)).append(")").append("\n");
        outputResults.append("====================================").
                append("\n");
    }

    @Override
    public void initialize() {
        this.setTileToWinForTraining(2_048);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("ConcurrencyTimes");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new PBinary<>();
        config.setNeuronQuantityInLayer(currentConfig.neuronQuantityInLayer);
        config.setActivationFunctionForEncog(
                currentConfig.activationFunctionForEncog);
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(
                config));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(
            IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearning(perceptronInterface, afterState, getAlpha(),
                getLambda(), getGamma(), getConcurrencyInLayer(),
                isReplaceEligibilityTraces(), true);
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(
            NTupleSystem nTupleSystem) {
        return null;
    }

}
