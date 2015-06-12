/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import java.util.List;

/**
 *
 * @author Franco
 */
public class StatisticForCalc {

    private double maxScore;
    private double meanScore;
    private double minScore;
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
