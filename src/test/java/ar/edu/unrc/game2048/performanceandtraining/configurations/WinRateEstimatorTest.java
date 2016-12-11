package ar.edu.unrc.game2048.performanceandtraining.configurations;

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

        assertThat(estimator.average(), is(1d));
    }

    @Test
    public
    void average()
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

        assertThat(estimator.average(), is(0.4d));
    }

    @Test
    public
    void printableAverage()
            throws Exception {
        WinRateEstimator estimator = new WinRateEstimator(5, 3, 2);
        estimator.addSample(10.4);
        estimator.addSample(1.5);
        assertThat(estimator.printableAverage(), is("? %"));

        estimator.addSample(5);
        estimator.addSample(9);
        assertThat(estimator.printableAverage(), is("? %"));

        estimator.addSample(2);
        assertThat(estimator.printableAverage(), is("60 %"));

        estimator.addSample(2);
        estimator.addSample(2);
        estimator.addSample(3);
        estimator.addSample(2);
        estimator.addSample(3);
        assertThat(estimator.printableAverage(), is("40 %"));

        estimator = new WinRateEstimator(30, 3, 2);
        for (int i = 0; i < 29; i++) {
            estimator.addSample(0);
        }
        estimator.addSample(3.2);
        assertThat(estimator.printableAverage(), is("3,33 %"));
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
