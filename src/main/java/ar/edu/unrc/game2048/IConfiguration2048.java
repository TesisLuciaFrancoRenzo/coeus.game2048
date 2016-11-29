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
package ar.edu.unrc.game2048;

/**
 * Estructura que debe tener una configuración para un experimento.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
interface IConfiguration2048 {

    /**
     * @param value salida de la red neuronal.
     *
     * @return {@code value} desnormalizado.
     */
    double deNormalizeValueFromNeuralNetworkOutput(Object value);

    /**
     * @param board        tablero.
     * @param outputNeuron neurona de salida.
     *
     * @return recompensa que se obtiene en dicho tablero, luego de un movimiento.
     */
    double getBoardReward(
            GameBoard board,
            int outputNeuron
    );

    /**
     * @param value valor a normalizar.
     *
     * @return {@code value} normalizado con el estilo de las neuronas de salida.
     */
    double normalizeValueToPerceptronOutput(Object value);

}
