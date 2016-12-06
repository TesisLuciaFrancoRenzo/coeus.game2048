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
package ar.edu.unrc.game2048.performanceandtraining.experiments.learning.encog;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.NeuralNetworkConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.ConfigPerceptronNTupleLinearSimplified_512;
import org.encog.neural.networks.BasicNetwork;

import java.awt.*;
import java.io.File;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class EncogNTupleLinearWithBiasSimplified_512
        extends LearningExperiment<BasicNetwork> {

    /**
     *
     */
    public final static Class<?>[] PARAMETER_TYPE = {Boolean.class};
    private final Boolean hasBias;

    /**
     * @param hasBias
     */
    public
    EncogNTupleLinearWithBiasSimplified_512(final Boolean hasBias) {
        super();
        this.hasBias = hasBias;
    }

    /**
     * @param args
     *
     * @throws Exception
     */
    public static
    void main(String[] args)
            throws Exception {
        String filePath;
        if (args.length == 0) {
            filePath = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }
        LearningExperiment experiment   = new EncogNTupleLinearWithBiasSimplified_512(true);
        boolean            printHistory = false;

        boolean statistics = true;
        //        boolean statistics = false;

        double[] alphas = {0.0025, 0.0025};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();

        experiment.setLambda(0.5);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setGamesToPlay(12_000);
        experiment.setSaveEvery(1_000);
        experiment.setSaveBackupEvery(300);
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPossibleAction(true);
        boolean[] concurrentLayer = {false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);

        experiment.createLogs(false);
        //para calcular estadisticas
        experiment.setTileToWinForStatistics(512);
        if (statistics) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(100);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }
        experiment.setExportToExcel(true);
        experiment.start(-1, filePath, 0, true, null, printHistory);

        Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public
    void initialize() {
        setTileToWinForTraining(512);
        if (getExperimentName() == null) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        NeuralNetworkConfiguration2048<BasicNetwork> config = new ConfigPerceptronNTupleLinearSimplified_512<>(hasBias);
        setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            INeuralNetworkInterface perceptronInterface
    ) {
        return new TDLambdaLearning(perceptronInterface, afterState, getAlpha(), getLambda(), getGamma(), getConcurrencyInLayer(), false);
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            NTupleSystem nTupleSystem
    ) {
        return null;
    }
}
