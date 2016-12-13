package ar.edu.unrc.game2048.experiments.configurations;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by franco on 11/12/16.
 */
public
class WinRateEstimator {

    private final DecimalFormat df;
    private final int           sampleSize;
    private final int           winValue;
    private       Queue<Double> queue;
    private       double        sum;

    public
    WinRateEstimator(
            int sampleSize,
            int winValue,
            int outputDecimals
    ) {
        this.sampleSize = sampleSize;
        this.winValue = winValue;
        if (outputDecimals < 1) {
            throw new IllegalArgumentException("outputDecimals must be >= 1");
        }
        StringBuilder pattern = new StringBuilder(outputDecimals);
        for (int i = 0; i < outputDecimals; i++) {
            pattern.append('#');
        }
        df = new DecimalFormat("#." + pattern.toString());
        queue = new LinkedList<>();
        sum = 0d;
    }

    public
    void addSample(double sample) {
        final double value = (sample >= winValue) ? 1d : 0d;
        queue.add(value);
        sum += value;
        if (queue.size() > sampleSize) {
            sum -= queue.remove();
        }
    }

    public
    double average() {
        return sum / (queue.size() * 1d);
    }

    public
    int currentSampleSize() {
        return queue.size();
    }

    public
    String printableAverage() {
        if (queue.size() < sampleSize) {
            return "? %";
        } else {
            return df.format((sum * 100d) / sampleSize) + " %";
        }
    }

    public
    void reset() {
        queue.clear();
        sum = 0d;
    }

}
