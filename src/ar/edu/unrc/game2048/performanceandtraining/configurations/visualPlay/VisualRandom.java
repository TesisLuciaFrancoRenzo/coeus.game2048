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
package ar.edu.unrc.game2048.performanceandtraining.configurations.visualPlay;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.VisualExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.random.Experiment_01;
import java.io.File;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class VisualRandom extends VisualExperiment {

    /**
     *
     * @param args
     *
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
        LearningExperiment experiment = new Experiment_01();
        VisualRandom game = new VisualRandom(experiment);

        game.start(filePath, 250, false);
    }

    /**
     *
     * @param learningExperiment
     */
    public VisualRandom(LearningExperiment learningExperiment) {
        super(learningExperiment);
    }

    @Override
    protected void initializeVisual() throws Exception {
        setDelayPerMove(250);
    }

}
