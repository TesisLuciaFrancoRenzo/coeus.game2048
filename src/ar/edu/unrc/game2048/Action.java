/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IAction;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini pellegrini
 */
public enum Action implements IAction {

    /**
     * Arriba
     */
    up,
    /**
     * Abajo
     */
    down,
    /**
     * Izquierda
     */
    left,
    /**
     * Derecha
     */
    right,
    /**
     * Restablecer
     */
    reset
}
