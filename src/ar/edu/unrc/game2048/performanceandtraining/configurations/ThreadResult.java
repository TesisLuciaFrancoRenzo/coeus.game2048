/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author franco pellegrini
 */
public class ThreadResult {

    private Map<Integer, Integer> maxScoreAchieved = new ConcurrentHashMap<>();
    private int procesedGames;
    private final List<Integer> tileStatistics;
    private int winGames = 0;

    /**
     *
     */
    public ThreadResult() {
        winGames = 0;
        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0);
        }
        maxScoreAchieved = new HashMap<>();
    }

    /**
     *
     */
    public void addProcesedGames() {
        procesedGames++;
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
     * @return the maxScoreAchieved
     */
    public Map<Integer, Integer> getMaxScoreAchieved() {
        return maxScoreAchieved;
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
     * @param tileCode
     * @return
     */
    public Integer getStatisticForTile(int tileCode) {
        return tileStatistics.get(tileCode);
    }

    /**
     * @return the winGames
     */
    public int getWinGames() {
        return winGames;
    }
}
