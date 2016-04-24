/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.performance;

import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class StatisticCalculator {

    private ArrayList<Double> experiment;

    /**
     * @param defaultCapacity solo para inicializar variables internas
     */
    public StatisticCalculator(Integer defaultCapacity) {
        experiment = new ArrayList<>(defaultCapacity);
    }

    public void addSample(double miliseconds) {
        experiment.add(miliseconds);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        experiment.stream().forEach((sample) -> {
            output.append(sample).append("\t");
        });
        return output.toString();
    }

    public String computeBasicStatistics() {
        if ( experiment.isEmpty() ) {
            throw new IllegalStateException("la cantidad de experimentos no debe ser vacia");
        }
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        double avg = 0;
        for ( Double sample : experiment ) {
            avg += sample;
            if ( sample < min ) {
                min = sample;
            }
            if ( sample > max ) {
                max = sample;
            }
        }
        avg = avg / (experiment.size() * 1d);
        return "Promedio: " + avg + "ms. Minimo: " + min + "ms. Máximo: " + max + "ms.";
    }
}
