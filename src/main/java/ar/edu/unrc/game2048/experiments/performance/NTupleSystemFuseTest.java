package ar.edu.unrc.game2048.experiments.performance;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.NTupleSystem;
import ar.edu.unrc.game2048.experiments.configurations.ntuples.ConfigNTupleBasicLinear_512;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La colocamos como deprecated, porque los pocos experimentos que realizamos tarda mucho en obtener resultados. Lo que hacemos es entrenar diferentes
 * cerebro, y luego los unimos, los clonamos y volvemos a entrenar.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
@Deprecated
public
class NTupleSystemFuseTest {

    public static
    void main( final String[] args )
            throws IOException, ClassNotFoundException {
        final ConfigNTupleBasicLinear_512 nTupleConfiguration = new ConfigNTupleBasicLinear_512();
        final int                         numFiles            = 7;
        final List< NTupleSystem >        nTupleSystems       = new ArrayList<>(numFiles);
        final String                      dir                 =
                "c:\\Users\\franc\\Trabajo\\Programas\\Gradle-Projects\\Tesis\\Perceptrones ENTRENADOS\\AutomaticTests\\";
        final String                      dirEnd              = "\\NTuple\\ConfigNTupleBasicLinear_512\\BasicLinear_512_trained.ser";
        for ( int i = 0; i < numFiles; i++ ) {
            nTupleSystems.add(new NTupleSystem(nTupleConfiguration.getAllSamplePointPossibleValues(),
                    nTupleConfiguration.getNTuplesLength(),
                    nTupleConfiguration.getActivationFunction(),
                    nTupleConfiguration.getDerivedActivationFunction(),
                    false));
            nTupleSystems.get(i)
                    .load(new File(dir + i + "-alpha_0.005-anneal_2000000-lambda_0.0-gamma_1.0-explorationRate_0.1_0.01_0_500000-resetTraces_false" +
                                   dirEnd));
        }
        final File fileOut = new File(dir + "BasicLinear_fused.ser");
        NTupleSystem.fuseLut(nTupleSystems);
        nTupleSystems.get(0).save(fileOut);
    }
}
