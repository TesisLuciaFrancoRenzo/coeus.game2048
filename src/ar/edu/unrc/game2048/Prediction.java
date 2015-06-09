/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IPrediction;
import ar.edu.unrc.tdlearning.perceptron.interfaces.IReward;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class Prediction implements IPrediction {

    /**
     *
     */
    protected Double prediction;

    /**
     *
     * @param prediction representacion numerica de la salida de las neuronas
     */
    public Prediction(Double prediction) {
        this.prediction = prediction;
    }

    @Override
    public void addReword(IReward reward) {
        if ( reward instanceof PartialScore ) {
            prediction += ((PartialScore) reward).getPartialScore();
        } else {
            throw new IllegalArgumentException("reward must be a PartialScore class");
        }
    }

    @Override
    public int compareTo(IPrediction o) {
        if ( o instanceof Prediction ) {
            return prediction.compareTo(((Prediction) o).prediction);
        }
        throw new IllegalArgumentException("o must be a Prediction class");
    }

    @Override
    public void multiplyBy(double probability) {
        prediction *= probability;
    }

}
