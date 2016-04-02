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
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class StatisticForCalc {

    private double maxScore;
    private double maxTurn;
    private double meanScore;
    private double meanTurn;
    private double minScore;
    private double minTurn;
    private List<Double> tileStatistics;
    private double winRate;

    /**
     * @return the maxScore
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * @param maxScore the maxScore to set
     */
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * @return the maxTurn
     */
    public double getMaxTurn() {
        return maxTurn;
    }

    /**
     * @param maxTurn the maxTurn to set
     */
    public void setMaxTurn(double maxTurn) {
        this.maxTurn = maxTurn;
    }

    /**
     * @return the meanScore
     */
    public double getMeanScore() {
        return meanScore;
    }

    /**
     * @param meanScore the meanScore to set
     */
    public void setMeanScore(double meanScore) {
        this.meanScore = meanScore;
    }

    /**
     * @return the meanTurn
     */
    public double getMeanTurn() {
        return meanTurn;
    }

    /**
     * @param meanTurn the meanTurn to set
     */
    public void setMeanTurn(double meanTurn) {
        this.meanTurn = meanTurn;
    }

    /**
     * @return the minScore
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * @param minScore the minScore to set
     */
    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    /**
     * @return the minTurn
     */
    public double getMinTurn() {
        return minTurn;
    }

    /**
     * @param minTurn the minTurn to set
     */
    public void setMinTurn(double minTurn) {
        this.minTurn = minTurn;
    }

    /**
     * @return the tileStatistics
     */
    public List<Double> getTileStatistics() {
        return tileStatistics;
    }

    /**
     * @param tileStatistics the tileStatistics to set
     */
    public void setTileStatistics(List<Double> tileStatistics) {
        this.tileStatistics = tileStatistics;
    }

    /**
     * @return the winRate
     */
    public double getWinRate() {
        return winRate;
    }

    /**
     * @param winRate the winRate to set
     */
    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
}
