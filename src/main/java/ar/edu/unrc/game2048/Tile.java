package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;

/**
 * Created by franco on 24/12/16.
 */

public
class Tile
        implements SamplePointValue {
    private boolean merged;
    private int     value;

    public
    Tile( int val ) {
        value = val;
    }

    boolean canMergeWith( Tile other ) {
        return !merged && other != null && !other.merged && value == other.getValue();
    }

    @Override
    public
    boolean equals( Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof Tile ) ) { return false; }

        Tile tile = (Tile) o;

        return value == tile.value;
    }

    int getValue() {
        return value;
    }

    @Override
    public
    int hashCode() {
        return value;
    }

    int mergeWith( Tile other ) {
        if ( canMergeWith(other) ) {
            value *= 2;
            merged = true;
            return value;
        }
        return -1;
    }

    void setMerged( boolean m ) {
        merged = m;
    }

    @Override
    public
    String toString() {
        return Integer.toString(value);
    }
}

