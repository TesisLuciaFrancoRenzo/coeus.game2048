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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public class ThreadResult {

    private double maxScore;

    private double maxTurn;
    private double minScore;
    private double minTurn;

    private int procesedGames;
    private final List<Integer> tileStatistics;
    private double totalScore;
    private int winGames = 0;
    double totalTurn;

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
        totalTurn = 0;
        maxScore = 0;
        minScore = Integer.MAX_VALUE;
        maxTurn = 0;
        minTurn = Integer.MAX_VALUE;
    }

    /**
     *
     * @param lastTurn
     */
    public void addLastTurn(int lastTurn) {
        assert lastTurn != 0;
        totalTurn += lastTurn;
        if ( lastTurn > maxTurn ) {
            maxTurn = lastTurn;
        }
        if ( lastTurn < minTurn ) {
            minTurn = lastTurn;
        }
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
     * @return the maxTurn
     */
    public double getMaxTurn() {
        if ( winGames > 0 ) {
            return maxTurn;
        } else {
            return 0;
        }
    }

    /**
     * @return the maxScore
     */
    public double getMeanScore() {
        return totalScore / (procesedGames * 1d);
    }

    /**
     * @return the maxScore
     */
    public double getMeanTurn() {
        if ( winGames > 0 ) {
            return totalTurn / (winGames * 1d);
        } else {
            return 0;
        }
    }

    /**
     * @return the minScore
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * @return the minTurn
     */
    public double getMinTurn() {
        if ( winGames > 0 ) {
            return minTurn;
        } else {
            return 0;
        }
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
        return (winGames * 100d) / (procesedGames * 1d);
    }
}
