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
package ar.edu.unrc.game2048.experiments.statistics.greedy;

import ar.edu.unrc.coeus.tdlearning.interfaces.IState;

/**
 * Probabilidad de elección, para IA greedy, de un tablero.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GreedyStateProbability {

    private IState nextTurnState;
    private double probability;

    /**
     * @param nextTurnState posible siguiente estado luego de calcular acciones no determinísticas.
     * @param probability   probabilidad de que ocurra {@code nextTurnState} como estado efectivo en el siguiente turno
     */
    public
    GreedyStateProbability(
            final IState nextTurnState,
            final double probability
    ) {
        this.nextTurnState = nextTurnState;
        this.probability = probability;
    }

    /**
     * @return nextTurnState
     */
    public
    IState getNextTurnState() {
        return nextTurnState;
    }

    /**
     * @param nextTurnState posible siguiente estado luego de calcular acciones no determinísticas.
     */
    public
    void setNextTurnState( final IState nextTurnState ) {
        this.nextTurnState = nextTurnState;
    }

    /**
     * @return probabilidad de que ocurra {@code nextTurnState} como estado efectivo en el siguiente turno
     */
    public
    double getProbability() {
        return probability;
    }

    /**
     * @param probability probabilidad de que se alcance el estado {@code nextTurnState}
     */
    public
    void setProbability( final double probability ) {
        if ( probability < 0 && probability > 1 ) {
            throw new IllegalArgumentException("probability debe estar en el rango 0<=probability<=1");
        }
        this.probability = probability;
    }
}
