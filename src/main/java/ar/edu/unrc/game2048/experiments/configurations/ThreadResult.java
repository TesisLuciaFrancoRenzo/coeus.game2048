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
package ar.edu.unrc.game2048.experiments.configurations;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultados obtenidos en una simulación estadística. Se necesitan varias simulaciones de éstas para crear un promedio.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public
class ThreadResult {

    private final List< Integer > tileStatistics;
    private       double          maxScore;
    private       double          maxTurn;
    private       double          minScore;
    private       double          minTurn;
    private int processedGames = 0;
    private       double          totalScore;
    private       double          totalTurn;
    private       int             winGames;

    /**
     * Nuevo resultado de simulación.
     */
    public
    ThreadResult() {
        winGames = 0;
        tileStatistics = new ArrayList<>(18);
        for ( int i = 0; i <= 17; i++ ) {
            tileStatistics.add(0);
        }
        totalScore = 0.0d;
        totalTurn = 0.0d;
        maxScore = 0.0d;
        minScore = Double.MAX_VALUE;
        maxTurn = 0.0d;
        minTurn = Double.MAX_VALUE;
    }

    /**
     * Calcula el mayor y menor turno alcanzado en forma dinámica, mientras se ejecutan las estadísticas.
     *
     * @param lastTurn turno alcanzado en el último juego.
     */
    public
    void addLastTurn( final int lastTurn ) {
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
    public
    void addProcessedGames() {
        processedGames++;
    }

    /**
     * Calcula el mayor y menor puntaje en forma dinámica, mientras se ejecutan las estadísticas.
     *
     * @param score puntaje actual alcanzado en el último juego.
     */
    public
    void addScore( final double score ) {
        totalScore += score;
        if ( score > maxScore ) {
            maxScore = score;
        }
        if ( score < minScore ) {
            minScore = score;
        }
    }

    /**
     * @param tileCode aumenta en 1 el valor del tile alcanzado en el último partido.
     */
    public
    void addStatisticForTile( final int tileCode ) {
        tileStatistics.set(tileCode, tileStatistics.get(tileCode) + 1);
    }

    /**
     * aumenta en 1 el valor de partidas ganadas.
     */
    public
    void addWin() {
        winGames++;
    }

    /**
     * @return máximo puntaje alcanzado hasta ahora.
     */
    public
    double getMaxScore() {
        return maxScore;
    }

    /**
     * @return máximo turno alcanzado hasta ahora.
     */
    public
    double getMaxTurn() {
        return ( winGames > 0 ) ? maxTurn : 0.0d;
    }

    /**
     * @return puntaje medio alcanzado hasta ahora.
     */
    public
    double getMeanScore() {
        return totalScore / ( (double) processedGames * 1.0d );
    }

    /**
     * @return turno medio alcanzado hasta ahora.
     */
    public
    double getMeanTurn() {
        return ( winGames > 0 ) ? ( totalTurn / ( (double) winGames * 1.0d ) ) : 0.0d;
    }

    /**
     * @return puntaje mínimo alcanzado hasta ahora.
     */
    public
    double getMinScore() {
        return minScore;
    }

    /**
     * @return turno mínimo alcanzado hasta ahora.
     */
    public
    double getMinTurn() {
        return ( winGames > 0 ) ? minTurn : 0.0d;
    }

    /**
     * @return cantidad de partidas procesadas en la simulación hasta ahora.
     */
    public
    int getProcessedGames() {
        return processedGames;
    }

    /**
     * @param value nueva cantidad de partidas procesadas en la simulación hasta ahora.
     */
    public
    void setProcessedGames( final int value ) {
        processedGames = value;
    }

    /**
     * @param tileCode cantidad de veces que se alcanzo este valor de tile al terminar las partidas.
     *
     * @return
     */
    public
    Integer getStatisticForTile( final int tileCode ) {
        return tileStatistics.get(tileCode);
    }

    /**
     * @return tasa de partidas ganadoras hasta ahora.
     */
    public
    double getWinRate() {
        return ( (double) winGames * 100.0d ) / ( (double) processedGames * 1.0d );
    }
}
