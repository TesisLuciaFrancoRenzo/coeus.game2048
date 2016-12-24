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
package ar.edu.unrc.game2048.experiments.statistics.greedy;

import ar.edu.unrc.coeus.interfaces.INeuralNetworkInterface;
import ar.edu.unrc.coeus.tdlearning.learning.TDLambdaLearning;
import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.experiments.configurations.LearningExperiment;
import ar.edu.unrc.game2048.experiments.configurations.librariesinterfaces.GreedyExperimentInterface;

import java.awt.*;
import java.io.File;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GreedyBasic
        extends LearningExperiment {

    /**
     * @param args
     *
     * @throws Exception
     */
    public static
    void main( String[] args )
            throws Exception {
        String filePath;
        if ( args.length == 0 ) {
            filePath = ".." + File.separator + "Perceptrones ENTRENADOS" + File.separator;
        } else {
            filePath = args[0];
        }
        LearningExperiment experiment   = new GreedyBasic();
        boolean            printHistory = false;

        experiment.setStatisticsOnly(true);
        experiment.createLogs(false);
        experiment.setSaveEvery(100_000_000);
        experiment.setSaveBackupEvery(100_000_000);
        //para calcular estadisticas
        experiment.setGamesToPlayPerThreadForStatistics(1_000);
        experiment.setSimulationsForStatistics(8);
        experiment.setRunStatisticsForBackups(false);
        experiment.setExportToExcel(false);
        experiment.start(-1, filePath, true, null, printHistory);

        Toolkit.getDefaultToolkit().beep();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public
    void initialize() {
        if ( getExperimentName() == null ) {
            setExperimentName(getClass());
        }
        setNeuralNetworkName(getExperimentName());
        setTileToWinForTraining(2_048);
        setNeuralNetworkInterfaceFor2048(new GreedyExperimentInterface(null));
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
        return null;
    }

}
