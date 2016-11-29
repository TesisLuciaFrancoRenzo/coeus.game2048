package ar.edu.unrc.game2048.performanceandtraining.experiments.learning.ntuple;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.performanceandtraining.configurations.ntuples.ConfigNTupleBasicLinear_512;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 26/11/2016.
 */
public
class NTupleSystemFuseTest {

    public static
    void main(String[] args)
            throws IOException, ClassNotFoundException {
        ConfigNTupleBasicLinear_512 nTupleConfiguration = new ConfigNTupleBasicLinear_512();
        int                         numFiles            = 7;
        List<NTupleSystem>          nTupleSystems       = new ArrayList<>(numFiles);
        String                      dir                 = "c:\\Users\\franc\\Trabajo\\Programas\\Gradle-Projects\\Tesis\\Perceptrones ENTRENADOS\\AutomaticTests\\";
        String                      dirEnd              = "\\NTuple\\ConfigNTupleBasicLinear_512\\BasicLinear_512_trained.ser";
        for (int i = 0; i < numFiles; i++) {
            nTupleSystems.add(new NTupleSystem(
                    nTupleConfiguration.getAllSamplePointPossibleValues(),
                    nTupleConfiguration.getNTuplesLength(),
                    nTupleConfiguration.getActivationFunction(),
                    nTupleConfiguration.getDerivedActivationFunction(),
                    false
            ));
            nTupleSystems.get(i)
                         .load(new File(dir +
                                        i +
                                        "-alpha_0.005-anneal_2000000-lambda_0.0-gamma_1.0-explorationRate_0.1_0.01_0_500000-resetTraces_false" +
                                        dirEnd));
        }
        File fileOut = new File(dir + "BasicLinear_fused.ser");
        NTupleSystem.fuseLut(nTupleSystems);
        nTupleSystems.get(0).save(fileOut);
    }
}
