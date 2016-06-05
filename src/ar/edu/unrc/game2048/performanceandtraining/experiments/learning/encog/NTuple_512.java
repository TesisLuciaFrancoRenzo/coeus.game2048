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

import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces.EncogExperimentInterface;
import ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.PNTuple512;
import ar.edu.unrc.tdlearning.interfaces.IPerceptronInterface;
import static ar.edu.unrc.tdlearning.learning.ELearningStyle.afterState;
import ar.edu.unrc.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.tdlearning.training.ntuple.NTupleSystem;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class NTuple_512 extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new NTuple_512();

//        boolean statistics = true;
        boolean statistics = false;

        boolean[] concurrentLayer = {true, false};
        experiment.setConcurrencyInLayer(concurrentLayer);
        double[] alphas = {0.0025, 0.0025};
        experiment.setAlpha(alphas);
        experiment.setLearningRateAdaptationToFixed();
        experiment.setConcurrencyInComputeBestPosibleAction(true);
        experiment.setLambda(0.7);
        experiment.setGamma(1);
        experiment.setExplorationRateToFixed(0);
        experiment.setResetEligibilitiTraces(false);
        experiment.setGamesToPlay(10_000);
        experiment.setSaveEvery(200);
        experiment.setSaveBackupEvery(500);
        experiment.setInitializePerceptronRandomized(false);

        experiment.createLogs(false);
        //para calcualar estadisticas
        if ( statistics ) {
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

        experiment.start(filePath, 0, true);
    }

    @Override
    public void initialize() throws Exception {
        this.setTileToWinForTraining(512);
        if ( this.getExperimentName() == null ) {
            this.setExperimentName(this.getClass());
        }
        this.setPerceptronName(this.getExperimentName());
        PerceptronConfiguration2048<BasicNetwork> config = new PNTuple512<>();
        this.setNeuralNetworkInterfaceFor2048(new EncogExperimentInterface(config));
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(IPerceptronInterface perceptronInterface) {
        return new TDLambdaLearning(
                perceptronInterface,
                afterState,
                getAlpha(),
                getLambda(),
                getGamma(),
                getConcurrencyInLayer(),
                isResetEligibilitiTraces(),
                false);
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(NTupleSystem nTupleSystem) {
        return null;
    }
}
