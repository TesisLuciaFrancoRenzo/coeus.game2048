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
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public
interface IGame {

    /**
     * Cierra el juego liberando recursos
     */
    void dispose();

    /**
     * devuelve el numero mas alto obtenido en el tablero
     *
     * @return
     */
    int getMaxNumber();

    /**
     * @return puntaje acumulado hasta el punto actual del juego
     */
    int getScore();

    /**
     * @return true si se perdió el juego
     */
    boolean iLoose();

    /**
     * @return true si se gano el juego
     */
    boolean iWin();

    /**
     * Procesa una tecla y el juego reacciona. La tecla "esc" reinicia el juego, y "up" " down" " left" "right" se usan
     * para jugar. Los códigos de dichas teclas son los mismos que se pueden encontrar en la clase KeyEvent.
     *
     * @param keyCode
     */
    void processInput(int keyCode);

}
