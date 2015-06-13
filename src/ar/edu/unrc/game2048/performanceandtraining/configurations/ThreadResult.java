/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public class ThreadResult {

    private double maxScore;
    private double minScore;

    private int procesedGames;
    private final List<Integer> tileStatistics;
    private int winGames = 0;
    double totalScore;

    /**
     *
     */
    public ThreadResult() {
        winGames = 0;
        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0);
        }
        totalScore = 0;
        maxScore = 0;
        minScore = Integer.MAX_VALUE;
    }

    /**
     *
     */
    public void addProcesedGames() {
        procesedGames++;
    }

    /**
     *
     * @param score
     */
    public void addScore(double score) {
        totalScore += score;
        if ( score > getMaxScore() ) {
            maxScore = score;
        }
        if ( score < getMinScore() ) {
            minScore = score;
        }
    }

    /**
     *
     * @param tileCode
     */
    public void addStatisticForTile(int tileCode) {
        tileStatistics.set(tileCode, tileStatistics.get(tileCode) + 1);
    }

    /**
     */
    public void addWin() {
        winGames++;
    }

    /**
     * @return the maxScore
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * @return the maxScore
     */
    public double getMeanScore() {
        return totalScore / (procesedGames * 1d);
    }

    /**
     * @return the minScore
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * @return the procesedGames
     */
    public int getProcesedGames() {
        return procesedGames;
    }

    /**
     *
     * @param value
     */
    public void setProcesedGames(int value) {
        procesedGames = value;
    }

    /**
     *
     * @param tileCode <p>
     * @return
     */
    public Integer getStatisticForTile(int tileCode) {
        return tileStatistics.get(tileCode);
    }

    /**
     * @return the winGames
     */
    public double getWinRate() {
        return winGames / (procesedGames * 1d);
    }
}
