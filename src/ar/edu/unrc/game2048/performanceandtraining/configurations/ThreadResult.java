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
 * Resultados obtenidos en una simulación estadística. Se necesitan varias simulaciones de éstas para crear un promedio.
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
     * Nuevo resultado de simulación.
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
     * Calcula el mayor y menor turno alcanzado en forma dinámica, mientras se ejecutan las estadísticas.
     *
     * @param lastTurn turno alcanzado en el último juego.
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
     * Aumenta la cantidad de juegos procesados.
     */
    public void addProcesedGames() {
        procesedGames++;
    }

    /**
     * Calcula el mayor y menor puntaje en forma dinámica, mientras se ejecutan las estadísticas.
     *
     * @param score puntaje actual alcanzado en el último juego.
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
     * @param tileCode aumenta en 1 el valor del tile alcanzado en el último partido.
     */
    public void addStatisticForTile(int tileCode) {
        tileStatistics.set(tileCode, tileStatistics.get(tileCode) + 1);
    }

    /**
     * aumenta en 1 el valor de partidas ganadas.
     */
    public void addWin() {
        winGames++;
    }

    /**
     * @return máximo puntaje alcanzado hasta ahora.
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * @return máximo turno alcanzado hasta ahora.
     */
    public double getMaxTurn() {
        if ( winGames > 0 ) {
            return maxTurn;
        } else {
            return 0;
        }
    }

    /**
     * @return puntaje medio alcanzado hasta ahora.
     */
    public double getMeanScore() {
        return totalScore / (procesedGames * 1d);
    }

    /**
     * @return turno medio alcanzado hasta ahora.
     */
    public double getMeanTurn() {
        if ( winGames > 0 ) {
            return totalTurn / (winGames * 1d);
        } else {
            return 0;
        }
    }

    /**
     * @return puntaje mínimo alcanzado hasta ahora.
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * @return turno mínimo alcanzado hasta ahora.
     */
    public double getMinTurn() {
        if ( winGames > 0 ) {
            return minTurn;
        } else {
            return 0;
        }
    }

    /**
     * @return cantidad de partidas procesadas en la simulación hasta ahora.
     */
    public int getProcesedGames() {
        return procesedGames;
    }

    /**
     *
     * @param value nueva cantidad de partidas procesadas en la simulación hasta ahora.
     */
    public void setProcesedGames(int value) {
        procesedGames = value;
    }

    /**
     *
     * @param tileCode cantidad de veces que se alcanzo este valor de tile al terminar las partidas.
     *
     * @return
     */
    public Integer getStatisticForTile(int tileCode) {
        return tileStatistics.get(tileCode);
    }

    /**
     * @return tasa de partidas ganadoras hasta ahora.
     */
    public double getWinRate() {
        return (winGames * 100d) / (procesedGames * 1d);
    }
}
