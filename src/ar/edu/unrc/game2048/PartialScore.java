/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048;

import ar.edu.unrc.tdlearning.perceptron.interfaces.IReward;

/**
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public class PartialScore implements IReward {

    private int partialScore;

    /**
     *
     * @param partialScore
     */
    public PartialScore(int partialScore) {
        this.partialScore = partialScore;

    }

    @Override
    public IReward add(IReward reward) {
        partialScore += ((PartialScore) reward).partialScore;
        return this;
    }

    /**
     * @return the partialScore
     */
    protected int getPartialScore() {
        return partialScore;
    }
}
