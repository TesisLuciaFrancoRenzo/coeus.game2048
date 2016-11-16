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
 * Optimización para reutilizar tiles y no crear y borrar instancias.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public
class TileContainer {

    private final Tile[] tiles;

    /**
     * @param maxCodedValue código de tile en base 2 del numero mas alto que se va a utilizar.
     */
    public
    TileContainer(int maxCodedValue) {
        tiles = new Tile[maxCodedValue + 1];
        for (int i = 0; i <= maxCodedValue; i++) {
            tiles[i] = new Tile(i);
        }
    }

    /**
     * @param code código en base 2 del tile que se necesita.
     *
     * @return
     */
    public
    Tile getTile(int code) {
        return tiles[code];
    }

}
