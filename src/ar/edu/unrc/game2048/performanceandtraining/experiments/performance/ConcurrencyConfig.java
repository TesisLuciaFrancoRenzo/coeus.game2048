/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.experiments.performance;

import org.encog.engine.network.activation.ActivationFunction;

/**
 *
 * @author franc
 */
public class ConcurrencyConfig {

    /**
     *
     */
    public ActivationFunction[] activationFunctionForEncog;

    /**
     *
     */
    public double[] alphas;

    /**
     *
     */
    public boolean[] concurrencyInLayer;

    /**
     *
     */
    public int[] neuronQuantityInLayer;
    boolean concurrencyInEvaluate;

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("evaluate concurrency: ");
        output.append(concurrencyInEvaluate);

        if ( concurrencyInLayer != null ) {
            output.append("\n");
            output.append("training concurrency: ");
            for ( int i = 0; i < concurrencyInLayer.length; i++ ) {
                output.append(concurrencyInLayer[i]).append(", ");
            }
        }
        if ( alphas != null ) {
            output.append("\n");
            output.append("alphas: ");
            for ( int i = 0; i < alphas.length; i++ ) {
                output.append(concurrencyInLayer[i]).append(", ");
            }
        }
        if ( neuronQuantityInLayer != null ) {
            output.append("\n");
            output.append("neuronQuantityInLayer: ");
            for ( int i = 0; i < neuronQuantityInLayer.length; i++ ) {
                output.append(neuronQuantityInLayer[i]).append(", ");
            }
        }
        if ( activationFunctionForEncog != null ) {
            output.append("\n");
            output.append("activationFunctionForEncog: ");
            for ( ActivationFunction activationFunction : activationFunctionForEncog ) {
                output.append(activationFunction.getClass().getName()).append(", ");
            }
        }
        return output.toString();
    }
}
