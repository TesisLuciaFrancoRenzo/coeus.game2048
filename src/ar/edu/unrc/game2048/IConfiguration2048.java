/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

/**
 *
 * @author Franco
 */
public interface IConfiguration2048 {

    /**
     *
     * @param value <p>
     * @return
     */
    public abstract double denormalizeValueFromPerceptronOutput(double value);

    /**
     *
     * @param board        <p>
     * @param outputNeuron <p>
     * @return
     */
    public abstract double getBoardReward(GameBoard board, int outputNeuron);

    /**
     *
     * @param game
     * @param outputNeuron <p>
     * @return
     */
    public abstract double getFinalReward(Game2048 game, int outputNeuron);

    /**
     *
     * @param value <p>
     * @return
     */
    public abstract double normalizeValueToPerceptronOutput(double value);

}