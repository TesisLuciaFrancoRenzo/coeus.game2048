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
package ar.edu.unrc.game2048.performanceandtraining.experiments.learning.random;

import ar.edu.unrc.coeus.interfaces.IPerceptronInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces.RandomExperimentInterface;
import java.io.File;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Basic extends LearningExperiment<BasicNetwork> {

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
        LearningExperiment experiment = new Basic();
        experiment.setStatisticsOnly(true);
        experiment.createLogs(false);
        experiment.setSaveEvery(100_000_000);
        experiment.setSaveBackupEvery(100_000_000);
        //para calcualr estadisticas
        experiment.setGamesToPlayPerThreadForStatistics(1_000);
        experiment.setSimulationsForStatistics(8);
        experiment.setRunStatisticsForBackups(false);
        experiment.setExportToExcel(false);
        experiment.start(-1, filePath, 0, true, null);
    }

    @Override
    public void initialize() {
        if ( this.getExperimentName() == null ) {
            this.setExperimentName(this.getClass());
        }
        this.setPerceptronName(this.getExperimentName());
        this.setTileToWinForTraining(2_048);
        this.setNeuralNetworkInterfaceFor2048(
                new RandomExperimentInterface(null));
    }

    /**
     *
     * @param perceptronInterface <p>
     * @return
     */
    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(
            IPerceptronInterface perceptronInterface) {
        return null;
    }

    @Override
    public TDLambdaLearning instanceOfTdLearninrgImplementation(
            NTupleSystem nTupleSystem) {
        return null;
    }

}
