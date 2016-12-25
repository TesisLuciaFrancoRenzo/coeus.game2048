/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.experiments.performance;

import java.util.ArrayList;

/**
 * Calcula estadísticas sobre experimentos de concurrencia.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class StatisticCalculatorPerformance {

    private final ArrayList< Double > experiment;

    /**
     * @param defaultCapacity solo para inicializar variables internas.
     */
    public
    StatisticCalculatorPerformance( final Integer defaultCapacity ) {
        experiment = new ArrayList<>(defaultCapacity);
    }

    /**
     * Agrega una muestra de tiempo al cálculo.
     *
     * @param milliseconds
     */
    public
    void addSample( final double milliseconds ) {
        experiment.add(milliseconds);
    }

    /**
     * Calcula las estadísticas.
     *
     * @return
     */
    public
    String[] computeBasicStatistics() {
        final String[] output = new String[2];
        if ( experiment.isEmpty() ) {
            throw new IllegalStateException("la cantidad de experimentos no debe ser vacía");
        }
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        double avg = 0;
        for ( final Double sample : experiment ) {
            avg += sample;
            if ( sample < min ) {
                min = sample;
            }
            if ( sample > max ) {
                max = sample;
            }
        }
        avg /= ( experiment.size() * 1.0d );
        output[0] = "Promedio: " + avg + "ms. Mínimo: " + min + "ms. Máximo: " + max + "ms.";
        output[1] = avg + "\t" + min + '\t' + max;
        return output;
    }

    /**
     * @return
     */
    @Override
    public
    String toString() {
        final StringBuilder output = new StringBuilder();
        experiment.forEach(( sample ) -> output.append(sample).append('\t'));
        return output.toString();
    }

}
