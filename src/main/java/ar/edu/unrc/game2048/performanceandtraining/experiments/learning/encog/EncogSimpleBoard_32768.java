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
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.ConfigPerceptronBoard_32768;
import org.encog.neural.networks.BasicNetwork;

import java.awt.*;
import java.io.File;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class EncogSimpleBoard_32768
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
    EncogSimpleBoard_32768(final Boolean hasBias) {
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
        LearningExperiment experiment   = new EncogSimpleBoard_32768(false);
        boolean            printHistory = false;

        boolean[] concurrentLayer = {false, true, true, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        double[] alphas = {0.0025, 0.0025, 0.0025, 0.0025};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setConcurrencyInComputeBestPossibleAction(true);
        experiment.setLambda(0.4);
        experiment.setReplaceEligibilityTraces(false);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setGamesToPlay(10_000);
        experiment.setSaveEvery(200);
        experiment.setSaveBackupEvery(500);
        experiment.setInitializePerceptronRandomized(false);

        experiment.createLogs(false);
        //para calcular estadisticas
        experiment.setStatisticsOnly(false);
        experiment.setTileToWinForStatistics(2_048);
        experiment.setRunStatisticsForBackups(true);
        experiment.setGamesToPlayPerThreadForStatistics(100);
        experiment.setSimulationsForStatistics(8);

        experiment.setExportToExcel(true);
        experiment.start(-1, filePath, 0, true, null, printHistory);

        Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public
    void initialize() {
        setTileToWinForTraining(32_768);
        if (getExperimentName() == null) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        NeuralNetworkConfiguration2048<BasicNetwork> config = new ConfigPerceptronBoard_32768<>(hasBias);
        setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            INeuralNetworkInterface perceptronInterface
    ) {
        return new TDLambdaLearning(perceptronInterface,
                afterState,
                getAlpha(), getLambda(), isReplaceEligibilityTraces(),
                getGamma(),
                getConcurrencyInLayer(),
                false
        );
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            NTupleSystem nTupleSystem
    ) {
        return null;
    }
}
