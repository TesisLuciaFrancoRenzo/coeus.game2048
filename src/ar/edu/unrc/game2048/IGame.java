/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

/**
 *
 * @author franco pellegrini
 */
public interface IGame {

    /**
     * Procesa una tecla y el juego reacciona. La tecla "esc" reinicia el juego,
     * y "up" " down" " left" "right" se usan para jugar. Los codigos de dichas
     * teclas son los mismos que se pueden encontrar en la clase KeyEvent.
     * <p>
     * @param keyCode
     */
    public void processInput(int keyCode);

    /**
     * @return true si se gano el juego
     */
    public boolean iWin();

    /**
     * @return true si se perdio el juego
     */
    public boolean iLoose();

    /**
     *
     * @return puntaje acumulado hasta el punto actual del juego
     */
    public int getScore();

    /**
     * devuelve el numero mas alto obtenido en el tablero
     * <p>
     * @return
     */
    public int getMaxNumber();

    /**
     * Cierra el juego liberando recursos
     */
    public void dispose();

}
