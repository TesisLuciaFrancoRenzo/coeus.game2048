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
package ar.edu.unrc.game2048.performanceandtraining.configurations.librariesinterfaces;

import ar.edu.unrc.game2048.Game2048;
import ar.edu.unrc.game2048.GameBoard;
import ar.edu.unrc.game2048.PerceptronConfiguration2048;
import ar.edu.unrc.tdlearning.interfaces.IPerceptronInterface;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.encog.engine.network.activation.ActivationFunction;
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
public class EncogExperimentInterfaceTest {

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
    public EncogExperimentInterfaceTest() {
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
            public Double computeNumericRepresentationFor(Game2048 game, Object[] output) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double denormalizeValueFromPerceptronOutput(Object value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double getBoardReward(GameBoard board, int outputNeuron) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double getFinalReward(GameBoard board, int outputNeuron) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isConcurrentInputEnabled() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double normalizeValueToPerceptronOutput(Object value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean useNTupleList() {
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

        perceptronConfiguration.setActivationFunctionForEncog(new ActivationFunction[3]);
        perceptronConfiguration.getActivationFunctionForEncog()[0] = new ActivationSigmoid();
        perceptronConfiguration.getActivationFunctionForEncog()[1] = new ActivationSigmoid();
        perceptronConfiguration.getActivationFunctionForEncog()[2] = new ActivationSigmoid();
        perceptronConfiguration.activationFunctionMax = 1;
        perceptronConfiguration.activationFunctionMin = 0;

        perceptronConfiguration.setNeuronQuantityInLayer(new int[3]);
        perceptronConfiguration.getNeuronQuantityInLayer()[0] = PERCEPTRON_INPUTS_QUANTITY;
        perceptronConfiguration.getNeuronQuantityInLayer()[1] = PERCEPTRON_HIDDEN_QUANTITY;
        perceptronConfiguration.getNeuronQuantityInLayer()[2] = PERCEPTRON_OUTPUTS_QUANTITY;

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
}
