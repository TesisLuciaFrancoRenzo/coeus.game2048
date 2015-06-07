/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.test.configurations.experiments;

import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.game2048.performanceandtraining.configurations.libraries.EncogExperimentInterface;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IPerceptronInterface;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class EncogExperimentTest {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    public EncogExperimentTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getPerceptronInterface method, of class EncogExperimentInterface.
     */
    @Test
    public void testGetPerceptronInterface() {
        System.out.println("getPerceptronInterface");

        PerceptronConfiguration2048<BasicNetwork> perceptronConfiguration = new PerceptronConfiguration2048<BasicNetwork>() {

            @Override
            public void calculateNormalizedPerceptronInput(GameBoard<BasicNetwork> board, List<Double> normalizedPerceptronInput) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateRealOutputToNormalizedPerceptronOutputFrom(GameBoard<BasicNetwork> board, int neuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double translateRewordToNormalizedPerceptronOutputFrom(GameBoard<BasicNetwork> board, int outputNeuronIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        BasicNetwork perceptron = new BasicNetwork();
        int PERCEPTRON_INPUTS_QUANTITY = 2;
        int PERCEPTRON_HIDDEN_QUANTITY = 2;
        int PERCEPTRON_OUTPUTS_QUANTITY = 2;

        perceptron.addLayer(new BasicLayer(null, true, PERCEPTRON_INPUTS_QUANTITY));
        perceptron.addLayer(new BasicLayer(new ActivationSigmoid(), true, PERCEPTRON_HIDDEN_QUANTITY));
        perceptron.addLayer(new BasicLayer(new ActivationSigmoid(), false, PERCEPTRON_OUTPUTS_QUANTITY));
        perceptron.getStructure().finalizeStructure();

        double[] weightsA = {
            0.5, 0.4, 0.15, 0.24,
            0.9, 0.25, 0.43, 0.82,
            0.13, 0.39, 0.6, 0.8
        };

        double[] weightsB = {
            0.3, 0.2, 0.1, 0.9,
            0.4, 0.5, 0.6, 0.7,
            0.81, 0.22, 0.11, 0.55
        };

        //configuramos los pesos
        perceptron.setWeight(0, 0, 0, weightsA[0]);
        perceptron.setWeight(0, 0, 1, weightsA[1]);
        perceptron.setWeight(0, 1, 0, weightsA[2]);
        perceptron.setWeight(0, 1, 1, weightsA[3]);

        perceptron.setWeight(1, 0, 0, weightsA[4]);
        perceptron.setWeight(1, 0, 1, weightsA[5]);
        perceptron.setWeight(1, 1, 0, weightsA[6]);
        perceptron.setWeight(1, 1, 1, weightsA[7]);

        //configuramos las bias
        perceptron.setWeight(0, 2, 0, weightsA[8]);
        perceptron.setWeight(0, 2, 1, weightsA[9]);
        perceptron.setWeight(1, 2, 0, weightsA[10]);
        perceptron.setWeight(1, 2, 1, weightsA[11]);

        perceptronConfiguration.activationFunctionOutputForEncog = new ActivationSigmoid();
        perceptronConfiguration.activationFunctionHiddenForEncog = new ActivationSigmoid();
        perceptronConfiguration.activationFunctionMax = 1;
        perceptronConfiguration.activationFunctionMin = 0;
        perceptronConfiguration.perceptron_hidden_quantity = PERCEPTRON_HIDDEN_QUANTITY;
        perceptronConfiguration.perceptron_input_quantity = PERCEPTRON_INPUTS_QUANTITY;
        perceptronConfiguration.perceptron_output_quantity = PERCEPTRON_OUTPUTS_QUANTITY;
        perceptronConfiguration.hiddenLayerQuantity = 1;

        EncogExperimentInterface experiment = new EncogExperimentInterface(perceptronConfiguration);
        experiment.setConfigForTesting(perceptron);
        IPerceptronInterface encogInterface = experiment.getPerceptronInterface();

        double expResult;
        double result;

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe
        double[] inputs = {0.3, 0.4};

        MLData inputData = new BasicMLData(inputs);
        MLData outut = perceptron.compute(inputData);

        double[] expResultArray = {0.80281682071709, 0.8140600608095045};
        double[] resultArray = outut.getData();
        Assert.assertArrayEquals(expResultArray, resultArray, 0.0000000000000001);
        // demostracion teorica con wxMaxima de que el resultado es el esperado
        //"=== INPUT ===";
        //"f(netk1)";fnetk1 : 0.3;
        //"f(netk2)";fnetk2 : 0.4;
        //"===VARIABLES===";
        //"w(k1,j1)";wk1j1 : 0.5;
        //"w(k1,j2)";wk1j2 : 0.4;
        //"w(k2,j1)";wk2j1 : 0.15;
        //"w(k2,j2)";wk2j2 : 0.24;
        //"bias j1";biasj1 : 0.13;
        //"bias j2";biasj2 : 0.39;
        //"w(j1,i1)";wj1i1 : 0.9;
        //"w(j1,i2)";wj1i2 : 0.25;
        //"w(j2,i1)";wj2i1 : 0.43;
        //"w(j2,i2)";wj2i2 : 0.82;
        //"bias i1";biasi1 : 0.6;
        //"bias i2";biasi2 : 0.8;
        //"=== FORMULAS ===";
        //f(net) := 1/(1+%e^(-net));
        //fd(net) := f(net)*(1-f(net));
        //"===net y f(net)===";
        //"netJ1";netJ1 : fnetk1*wk1j1+fnetk2*wk2j1+biasj1;
        //"f(netJ1)";fnetJ1 : f(netJ1);
        //"netJ2";netJ2 : fnetk1*wk1j2+fnetk2*wk2j2+biasj2;
        //"f(netJ2)";fnetJ2 : f(netJ2);
        //"netI1";netI1 : fnetJ1*wj1i1+fnetJ2*wj2i1+biasi1;
        //"netI2";netI2 : fnetJ1*wj1i2+fnetJ2*wj2i2+biasi2;
        //"=== OUTPUT ===";
        //"f(netI1)";fnetI1 : f(netI1);
        //"f(netI2)";fnetI2 : f(netI2);

        //get pesos con interfaz
        expResult = weightsA[0];
        result = encogInterface.getWeight(1, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsA[1];
        result = encogInterface.getWeight(1, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsA[2];
        result = encogInterface.getWeight(1, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsA[3];
        result = encogInterface.getWeight(1, 1, 1);
        assertEquals(expResult, result);

        expResult = weightsA[4];
        result = encogInterface.getWeight(2, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsA[5];
        result = encogInterface.getWeight(2, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsA[6];
        result = encogInterface.getWeight(2, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsA[7];
        result = encogInterface.getWeight(2, 1, 1);
        assertEquals(expResult, result);

        // get bias con interfaz
        expResult = weightsA[8];
        result = encogInterface.getBias(1, 0);
        assertEquals(expResult, result);

        expResult = weightsA[9];
        result = encogInterface.getBias(1, 1);
        assertEquals(expResult, result);

        expResult = weightsA[10];
        result = encogInterface.getBias(2, 0);
        assertEquals(expResult, result);

        expResult = weightsA[11];
        result = encogInterface.getBias(2, 1);
        assertEquals(expResult, result);

        //cambiamos los pesos, y luego verificamos que se cambien correctamente
        //configuramos los pesos
        encogInterface.setWeight(1, 0, 0, weightsB[0]);
        encogInterface.setWeight(1, 1, 0, weightsB[1]);
        encogInterface.setWeight(1, 0, 1, weightsB[2]);
        encogInterface.setWeight(1, 1, 1, weightsB[3]);

        encogInterface.setWeight(2, 0, 0, weightsB[4]);
        encogInterface.setWeight(2, 1, 0, weightsB[5]);
        encogInterface.setWeight(2, 0, 1, weightsB[6]);
        encogInterface.setWeight(2, 1, 1, weightsB[7]);

        //configuramos las bias
        encogInterface.setBias(1, 0, weightsB[8]);
        encogInterface.setBias(1, 1, weightsB[9]);
        encogInterface.setBias(2, 0, weightsB[10]);
        encogInterface.setBias(2, 1, weightsB[11]);

        //get pesos con interfaz
        expResult = weightsB[0];
        result = encogInterface.getWeight(1, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsB[1];
        result = encogInterface.getWeight(1, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsB[2];
        result = encogInterface.getWeight(1, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsB[3];
        result = encogInterface.getWeight(1, 1, 1);
        assertEquals(expResult, result);

        expResult = weightsB[4];
        result = encogInterface.getWeight(2, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsB[5];
        result = encogInterface.getWeight(2, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsB[6];
        result = encogInterface.getWeight(2, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsB[7];
        result = encogInterface.getWeight(2, 1, 1);
        assertEquals(expResult, result);

        // get bias con interfaz
        expResult = weightsB[8];
        result = encogInterface.getBias(1, 0);
        assertEquals(expResult, result);

        expResult = weightsB[9];
        result = encogInterface.getBias(1, 1);
        assertEquals(expResult, result);

        expResult = weightsB[10];
        result = encogInterface.getBias(2, 0);
        assertEquals(expResult, result);

        expResult = weightsB[11];
        result = encogInterface.getBias(2, 1);
        assertEquals(expResult, result);

        //accedemos a los cambios pero con encog y verificamos las modificaciones
        //pesos
        expResult = weightsB[0];
        result = perceptron.getWeight(0, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsB[1];
        result = perceptron.getWeight(0, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsB[2];
        result = perceptron.getWeight(0, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsB[3];
        result = perceptron.getWeight(0, 1, 1);
        assertEquals(expResult, result);

        expResult = weightsB[4];
        result = perceptron.getWeight(1, 0, 0);
        assertEquals(expResult, result);

        expResult = weightsB[5];
        result = perceptron.getWeight(1, 0, 1);
        assertEquals(expResult, result);

        expResult = weightsB[6];
        result = perceptron.getWeight(1, 1, 0);
        assertEquals(expResult, result);

        expResult = weightsB[7];
        result = perceptron.getWeight(1, 1, 1);
        assertEquals(expResult, result);

        //bias
        expResult = weightsB[8];
        result = perceptron.getWeight(0, 2, 0);
        assertEquals(expResult, result);

        expResult = weightsB[9];
        result = perceptron.getWeight(0, 2, 1);
        assertEquals(expResult, result);

        expResult = weightsB[10];
        result = perceptron.getWeight(1, 2, 0);
        assertEquals(expResult, result);

        expResult = weightsB[11];
        result = perceptron.getWeight(1, 2, 1);
        assertEquals(expResult, result);

        // testeamos que la salida es la esperada. Los calculos se han realizado
        // manualmente y corresponden al caso de pureba numero 2 del informe
        double[] inputs2 = {0.8, 1.5};

        inputData = new BasicMLData(inputs2);
        outut = perceptron.compute(inputData);

        double[] expResultArray2 = {0.7164779076006158, 0.8218381521799242};
        double[] resultArray2 = outut.getData();
        Assert.assertArrayEquals(expResultArray2, resultArray2, 0.0000000000000001);
    }
//
//
//    /**
//     * Test of setPerceptronCopyTo method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testSetPerceptronCopyTo() {
//        System.out.println("setPerceptronCopyTo");
//        Game2048 game = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.setPerceptronCopyTo(game);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setPerceptronTo method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testSetPerceptronTo() {
//        System.out.println("setPerceptronTo");
//        Game2048 game = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.setPerceptronTo(game);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loadActivationFunctionsFromConfiguration method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testLoadActivationFunctionsFrom() {
//        System.out.println("loadActivationFunctionsFromConfiguration");
//        PerceptronConfiguration2048 perceptronConfiguration = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.loadActivationFunctionsFromConfiguration(perceptronConfiguration);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loadOrCreatePerceptron method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testLoadOrCreatePerceptron() throws Exception {
//        System.out.println("loadOrCreatePerceptron");
//        Game2048 game = null;
//        File perceptronFile = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.loadOrCreatePerceptron(game, perceptronFile);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of playATurn method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testPlayATurn() {
//        System.out.println("playATurn");
//        Game2048 game = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.playATurn(game);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of savePerceptron method, of class EncogExperimentInterface.
//     */
//    @Test
//    public void testSavePerceptron() throws Exception {
//        System.out.println("savePerceptron");
//        File perceptronFile = null;
//        EncogExperimentInterface instance = new EncogExperimentInterface();
//        instance.savePerceptron(perceptronFile);
//        fail("The test case is a prototype.");
//    }

}
