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
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class GeneratorConfig {

    private Double alpha;
    private Double explorationRate;
    private Double gamma;
    private Double lambda;
    private final int number;
    private boolean resetTraces;

    public GeneratorConfig(Double alpha, Double lambda, Double gamma, Double explorationRate, boolean resetTraces, int number) {
        this.alpha = alpha;
        this.lambda = lambda;
        this.gamma = gamma;
        this.explorationRate = explorationRate;
        this.resetTraces = resetTraces;
        this.number = number;
    }

    /**
     * @return the alpha
     */
    public Double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the explorationRate
     */
    public Double getExplorationRate() {
        return explorationRate;
    }

    /**
     * @param explorationRate the explorationRate to set
     */
    public void setExplorationRate(Double explorationRate) {
        this.explorationRate = explorationRate;
    }

    /**
     * @return the gamma
     */
    public Double getGamma() {
        return gamma;
    }

    /**
     * @param gamma the gamma to set
     */
    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    /**
     * @return the lambda
     */
    public Double getLambda() {
        return lambda;
    }

    /**
     * @param lambda the lambda to set
     */
    public void setLambda(Double lambda) {
        this.lambda = lambda;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the resetTraces
     */
    public boolean isResetTraces() {
        return resetTraces;
    }

    /**
     * @param resetTraces the resetTraces to set
     */
    public void setResetTraces(boolean resetTraces) {
        this.resetTraces = resetTraces;
    }
}
