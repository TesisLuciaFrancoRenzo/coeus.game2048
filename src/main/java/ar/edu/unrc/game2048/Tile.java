package ar.edu.unrc.game2048;

import ar.edu.unrc.coeus.tdlearning.training.ntuple.SamplePointValue;

/**
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class Tile
        implements SamplePointValue {
    private boolean merged;
    private int     value;

    public
    Tile( final int val ) {
        value = val;
    }

    boolean canMergeWith( final Tile other ) {
        return !merged && ( other != null ) && !other.merged && ( value == other.value );
    }

    @Override
    public
    boolean equals( final Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof Tile ) ) { return false; }

        final Tile tile = (Tile) o;

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

    int mergeWith( final Tile other ) {
        if ( canMergeWith(other) ) {
            value <<= 1;
            merged = true;
            return value;
        }
        return -1;
    }

    void setMerged( final boolean m ) {
        merged = m;
    }

    @Override
    public
    String toString() {
        return Integer.toString(value);
    }
}

