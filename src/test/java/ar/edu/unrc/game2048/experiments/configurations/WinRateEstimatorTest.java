package ar.edu.unrc.game2048.experiments.configurations;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by franco on 11/12/16.
 */
public
class WinRateEstimatorTest {
    @Test
    public
    void addSample()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 1, 2);
        estimator.addSample(10.4);
        estimator.addSample(4);
        assertThat(estimator.currentSampleSize(), is(2));

        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.currentSampleSize(), is(4));

        estimator.addSample(2);
        assertThat(estimator.currentSampleSize(), is(5));

        estimator.addSample(1);
        assertThat(estimator.currentSampleSize(), is(5));
        estimator.addSample(1);
        estimator.addSample(1);
        estimator.addSample(1);
        estimator.addSample(1);
        assertThat(estimator.currentSampleSize(), is(5));

        assertThat(estimator.averageWinRate(), is(100d));
    }

    @Test
    public
    void averageMaxValue()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 1, 2);
        estimator.addSample(10.4);
        estimator.addSample(4);
        assertThat(estimator.currentSampleSize(), is(2));

        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.currentSampleSize(), is(4));

        estimator.addSample(2);
        assertThat(estimator.currentSampleSize(), is(5));

        estimator.addSample(1);
        assertThat(estimator.currentSampleSize(), is(5));
        estimator.addSample(1);
        estimator.addSample(1);
        estimator.addSample(1);
        estimator.addSample(1);
        assertThat(estimator.currentSampleSize(), is(5));

        assertThat(estimator.averageMaxValue(), is(1d));
    }

    @Test
    public
    void averageWinRate()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 3, 2);
        estimator.addSample(10.4);
        estimator.addSample(4);
        assertThat(estimator.currentSampleSize(), is(2));

        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.currentSampleSize(), is(4));

        estimator.addSample(2);
        assertThat(estimator.currentSampleSize(), is(5));

        estimator.addSample(2);
        assertThat(estimator.currentSampleSize(), is(5));
        estimator.addSample(2);
        estimator.addSample(3);
        estimator.addSample(2);
        estimator.addSample(3);
        assertThat(estimator.currentSampleSize(), is(5));

        assertThat(estimator.averageWinRate(), is(40d));
    }

    @Test
    public
    void printableAverages()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 3, 2);
        estimator.addSample(10.4);
        estimator.addSample(1.5);
        assertThat(estimator.printableAverages(), is("? %"));

        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.printableAverages(), is("? %"));

        estimator.addSample(2);
        assertThat(estimator.printableAverages(), is("winRate 60 % - maxTile 5,58"));

        estimator.addSample(2);
        estimator.addSample(2);
        estimator.addSample(3);
        estimator.addSample(2);
        estimator.addSample(3);
        assertThat(estimator.printableAverages(), is("winRate 40 % - maxTile 2,4"));

        estimator = new WinRateEstimator(30, 3, 2);
        for ( int i = 0; i < 29; i++ ) {
            estimator.addSample(0);
        }
        estimator.addSample(3.2);
        assertThat(estimator.printableAverages(), is("winRate 3,33 % - maxTile 0,11"));
    }

    @Test
    public
    void reset()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 1, 2);
        estimator.addSample(10.4);
        estimator.addSample(4);
        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.currentSampleSize(), is(4));

        estimator.reset();
        assertThat(estimator.currentSampleSize(), is(0));
    }

}
