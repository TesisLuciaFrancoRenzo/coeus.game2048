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
package ar.edu.unrc.game2048.performanceandtraining.configurations.learning.encog;

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.NTupleScore;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.perceptron.learning.TDLambdaLearningAfterstate;
import ar.edu.unrc.tdlearning.perceptron.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Experiment_03 extends LearningExperiment<BasicNetwork> {

    /**
     *
     * @param args <p>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath
                    = ".." + File.separator
                    + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }
        LearningExperiment experiment = new Experiment_03();

//        boolean statistics = true;
        boolean statistics = false;

        boolean[] concurrentLayer = {false, false, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setLearningRateAdaptationToAnnealing(500_000);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0.1);
        experiment.setResetEligibilitiTraces(true);
        experiment.setGamesToPlay(100_000);
        experiment.setSaveEvery(500);
        experiment.setSaveBackupEvery(500);
        experiment.setInitializePerceptronRandomized(true);

        experiment.createLogs(false);
        //para calcualar estadisticas
        if ( statistics ) {
            experiment.setStatisticsOnly(true);
            experiment.setRunStatisticForRandom(true);
            experiment.setRunStatisticsForBackups(true);
            experiment.setGamesToPlayPerThreadForStatistics(1_000);
            experiment.setSimulationsForStatistics(8);
        } else {
            experiment.setStatisticsOnly(false);
            experiment.setRunStatisticForRandom(false);
            experiment.setRunStatisticsForBackups(false);
            experiment.setGamesToPlayPerThreadForStatistics(0);
            experiment.setSimulationsForStatistics(0);
        }

        experiment.start(filePath, 0, true);
    }

    @Override
    public void initialize() throws Exception {
        this.setTileToWin(2_048);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName("Experiment_03");
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new NTupleScore<>();
        // config.randomMoveProbability = 0.01;
        //config.perceptron_hidden_quantity = config.perceptron_input_quantity;
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearningAfterstate(perceptronInterface, getAlpha(), getLambda(), getGamma(), getConcurrencyInLayer(), isResetEligibilitiTraces());
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }
}
