/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public class TileContainer {

    private final Tile[] tiles;

    /**
     *
     * @param maxCodedValue
     */
    public TileContainer(int maxCodedValue) {
        tiles = new Tile[maxCodedValue + 1];
        for ( int i = 0; i <= maxCodedValue; i++ ) {
            tiles[i] = new Tile(i);
        }
    }

    /**
     *
     * @param code <p>
     * @return
     */
    public Tile getTile(int code) {
        return tiles[code];
    }

}
