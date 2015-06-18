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
