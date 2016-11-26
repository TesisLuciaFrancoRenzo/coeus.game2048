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
package ar.edu.unrc.game2048.performanceandtraining.experiments;

/**
 * Configuración de un generador de Tests.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GeneratorConfig {

    private final double   alpha;
    private final int      annealingAlpha;
    private final Object[] classParameters;
    private final Double   explorationRate;
    private final Double   explorationRateFinalValue;
    private final Integer  explorationRateFinishInterpolation;
    private final Double   explorationRateInitialValue;
    private final Integer  explorationRateStartInterpolation;
    private final double   gamma;
    private final double   lambda;
    private final int      number;
    private final int      repetitions;
    private final boolean  resetTraces;

    /**
     * @param classParameters
     * @param alpha
     * @param annealingAlpha
     * @param lambda
     * @param gamma
     * @param explorationRate
     * @param explorationRateInitialValue
     * @param resetTraces
     * @param explorationRateFinalValue
     * @param number
     * @param explorationRateStartInterpolation
     * @param explorationRateFinishInterpolation
     */
    public
    GeneratorConfig(
            final int repetitions,
            final Object[] classParameters,
            final double alpha,
            final int annealingAlpha,
            final double lambda,
            final Double gamma,
            final Double explorationRate,
            final Double explorationRateInitialValue,
            final Double explorationRateFinalValue,
            final Integer explorationRateStartInterpolation,
            final Integer explorationRateFinishInterpolation,
            final boolean resetTraces,
            final int number
    ) {
        this.repetitions = repetitions;
        this.alpha = alpha;
        this.annealingAlpha = annealingAlpha;
        this.lambda = lambda;
        this.gamma = gamma;
        this.explorationRate = explorationRate;
        this.explorationRateFinishInterpolation = explorationRateFinishInterpolation;
        this.resetTraces = resetTraces;
        this.number = number;
        this.explorationRateInitialValue = explorationRateInitialValue;
        this.explorationRateFinalValue = explorationRateFinalValue;
        this.explorationRateStartInterpolation = explorationRateStartInterpolation;
        this.classParameters = classParameters;
    }

    /**
     * @return alpha
     */
    public
    double getAlpha() {
        return alpha;
    }

    /**
     * @return annealingAlpha
     */
    public
    int getAnnealingAlpha() {
        return annealingAlpha;
    }

    /**
     * @return
     */
    public
    Object[] getClassParameters() {
        return classParameters;
    }

    /**
     * @return explorationRate
     */
    public
    Double getExplorationRate() {
        return explorationRate;
    }

    /**
     * @return the explorationRateFinalValue
     */
    public
    Double getExplorationRateFinalValue() {
        return explorationRateFinalValue;
    }

    /**
     * @return the explorationRateFinishInterpolation
     */
    public
    Integer getExplorationRateFinishInterpolation() {
        return explorationRateFinishInterpolation;
    }

    /**
     * @return the explorationRateInitialValue
     */
    public
    Double getExplorationRateInitialValue() {
        return explorationRateInitialValue;
    }

    /**
     * @return the explorationRateStartInterpolation
     */
    public
    Integer getExplorationRateStartInterpolation() {
        return explorationRateStartInterpolation;
    }

    /**
     * @return gamma
     */
    public
    double getGamma() {
        return gamma;
    }

    /**
     * @return lambda
     */
    public
    double getLambda() {
        return lambda;
    }

    /**
     * @return number
     */
    public
    int getNumber() {
        return number;
    }

    public
    int getRepetitions() {
        return repetitions;
    }

    /**
     * @return resetTraces
     */
    public
    boolean isResetTraces() {
        return resetTraces;
    }

}
