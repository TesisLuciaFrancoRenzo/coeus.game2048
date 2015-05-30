/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPrediction;

/**
 *
 * @author franco
 */
public class Prediction implements IPrediction {

    protected final double[] perceptronOutput;

    /**
     *
     */
    protected final Double prediction;

    /**
     *
     * @param prediction       representacion numerica de la salida de las
     *                         neuronas
     * @param perceptronOutput con la salida tal y como se obtuvo del
     *                         perceptron, en forma de vector
     */
    public Prediction(Double prediction, double[] perceptronOutput) {
        this.prediction = prediction;
        this.perceptronOutput = perceptronOutput;
    }

    @Override
    public int compareTo(IPrediction o) {
        if ( o instanceof Prediction ) {
            return prediction.compareTo(((Prediction) o).prediction);
        }
        throw new IllegalArgumentException("o must be a Prediction class");
    }

}
