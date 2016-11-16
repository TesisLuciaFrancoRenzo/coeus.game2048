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
package ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.NTupleConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces.NTupleExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.NBasicLinear_512;
import org.encog.neural.networks.BasicNetwork;

import java.io.File;

import static ar.edu.unrc.coeus.tdlearning.learning.ELearningStyle.afterState;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class BasicLinear_512
        extends LearningExperiment<BasicNetwork> {

    /**
     * @param args <p>
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
        LearningExperiment experiment = new BasicLinear_512();

        //        boolean statistics = true;
        boolean statistics = false;

        double[] alphas = {0.0025, 0.0025};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();

        experiment.setLambda(0.2);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setReplaceEligibilityTraces(false);
        experiment.setGamesToPlay(30_000);
        experiment.setSaveEvery(500);
        experiment.setSaveBackupEvery(1_000);
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPossibleAction(true);
        boolean[] concurrentLayer = {false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);

        experiment.createLogs(false);
        //para calcualar estadisticas
        experiment.setTileToWinForStatistics(512);
        if (statistics) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }
        experiment.setExportToExcel(true);
        experiment.start(-1, filePath, 0, true, null);
    }

    @Override
    public
    void initialize() {
        this.setTileToWinForTraining(512);
        if (this.getExperimentName() == null) {
            this.setExperimentName(this.getClass());
        }
        this.setNeuralNetworkName(this.getExperimentName());
        NTupleConfiguration2048 config = new NBasicLinear_512();
        this.setNeuralNetworkInterfaceFor2048(new NTupleExperimentInterface(config));
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            INeuralNetworkInterface perceptronInterface
    ) {
        return null;
    }

    @Override
    public
    TDLambdaLearning instanceOfTdLearningImplementation(
            NTupleSystem nTupleSystem
    ) {
        return new TDLambdaLearning(
                nTupleSystem,
                afterState,
                (getAlpha() != null) ? getAlpha()[0] : null,
                getLambda(),
                getGamma(),
                getConcurrencyInLayer(),
                isReplaceEligibilityTraces(),
                false
        );
    }

}