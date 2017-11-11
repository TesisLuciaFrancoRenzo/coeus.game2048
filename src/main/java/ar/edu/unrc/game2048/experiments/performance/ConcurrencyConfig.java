/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.experiments.performance;

import org.encog.engine.network.activation.ActivationFunction;

/**
 * @author franc
 */
public
class ConcurrencyConfig {

    private ActivationFunction[] activationFunctionForEncog = null;
    private double[]             alphas                     = null;
    private boolean              concurrencyInEvaluate      = false;
    private boolean[]            concurrencyInLayer         = null;
    private int[]                neuronQuantityInLayer      = null;

    /**
     * @return activationFunctionForEncog
     */
    public
    ActivationFunction[] getActivationFunctionForEncog() {
        return activationFunctionForEncog;
    }

    /**
     * @param activationFunctionForEncog the activationFunctionForEncog to set
     */
    public
    void setActivationFunctionForEncog(
            final ActivationFunction[] activationFunctionForEncog
    ) {
        this.activationFunctionForEncog = activationFunctionForEncog;
    }

    /**
     * @return alphas
     */
    public
    double[] getAlphas() {
        return alphas;
    }

    /**
     * @param alphas a utilizar
     */
    public
    void setAlphas( final double[] alphas ) {
        this.alphas = alphas;
    }

    /**
     * @return concurrencyInLayer
     */
    public
    boolean[] getConcurrencyInLayer() {
        return concurrencyInLayer;
    }

    /**
     * @param concurrencyInEvaluate true si se utiliza concurrencia al evaluar estados.
     */
    public
    void setConcurrencyInEvaluate( final boolean concurrencyInEvaluate ) {
        this.concurrencyInEvaluate = concurrencyInEvaluate;
    }

    /**
     * @return neuronQuantityInLayer
     */
    public
    int[] getNeuronQuantityInLayer() {
        return neuronQuantityInLayer;
    }

    /**
     * @param concurrencyInLayer establece que capas utiliza concurrencia en sus cálculos.
     */
    public
    void setConcurrencyInLayer( final boolean[] concurrencyInLayer ) {
        this.concurrencyInLayer = concurrencyInLayer;
    }

    /**
     * @return concurrencyInEvaluate
     */
    public
    boolean isConcurrencyInEvaluate() {
        return concurrencyInEvaluate;
    }

    /**
     * @param neuronQuantityInLayer cantidad de neuronas en cada capa.
     */
    public
    void setNeuronQuantityInLayer( final int[] neuronQuantityInLayer ) {
        this.neuronQuantityInLayer = neuronQuantityInLayer;
    }

    @SuppressWarnings( "ForLoopReplaceableByForEach" )
    @Override
    public
    String toString() {
        final StringBuilder output = new StringBuilder();

        output.append("evaluate concurrency: ");
        output.append(concurrencyInEvaluate);

        if ( concurrencyInLayer != null ) {
            output.append('\n');
            output.append("training concurrency: ");
            for ( int i = 0; i < concurrencyInLayer.length; i++ ) {
                output.append(concurrencyInLayer[i]).append(", ");
            }
        }
        if ( alphas != null ) {
            output.append('\n');
            output.append("alphas: ");
            for ( int i = 0; i < alphas.length; i++ ) {
                output.append(concurrencyInLayer[i]).append(", ");
            }
        }
        if ( neuronQuantityInLayer != null ) {
            output.append('\n');
            output.append("neuronQuantityInLayer: ");
            for ( int i = 0; i < neuronQuantityInLayer.length; i++ ) {
                output.append(neuronQuantityInLayer[i]).append(", ");
            }
        }
        if ( activationFunctionForEncog != null ) {
            output.append('\n');
            output.append("activationFunctionForEncog: ");
            for ( final ActivationFunction activationFunction : activationFunctionForEncog ) {
                output.append(activationFunction.getClass().getName()).append(", ");
            }
        }
        return output.toString();
    }
}
