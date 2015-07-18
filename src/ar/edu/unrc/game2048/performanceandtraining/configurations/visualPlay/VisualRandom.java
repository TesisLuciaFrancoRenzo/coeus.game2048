/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.visualPlay;

import ar.edu.unrc.game2048.performanceandtraining.configurations.LearningExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.VisualExperiment;
import ar.edu.unrc.game2048.performanceandtraining.configurations.learning.random.Experiment_01;
import java.io.File;

/**
 *
 * @author Renzo Bianchini
 */
public class VisualRandom extends VisualExperiment {

    public VisualRandom(LearningExperiment learningExperiment) {
        super(learningExperiment);
    }

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

        game.start(filePath, 250);
    }

    @Override
    protected void initializeVisual() throws Exception {
        setDelayPerMove(250);
    }

}
