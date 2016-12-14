package ar.edu.unrc.game2048.experiments.configurations;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by franco on 11/12/16.
 */
public
class WinRateEstimator {

    private final DecimalFormat formatter;
    private final int           gameWinValue;
    private final int           sampleSize;
    private       Queue<Double> queue;
    private       double        sumMaxValue;
    private       double        sumWinRate;

    public
    WinRateEstimator(
            int sampleSize,
            int gameWinValue,
            int outputDecimals
    ) {
        this.sampleSize = sampleSize;
        this.gameWinValue = gameWinValue;
        if (outputDecimals < 1) {
            throw new IllegalArgumentException("outputDecimals must be >= 1");
        }
        StringBuilder pattern = new StringBuilder(outputDecimals);
        for (int i = 0; i < outputDecimals; i++) {
            pattern.append('#');
        }
        formatter = new DecimalFormat("#." + pattern.toString());
        queue = new LinkedList<>();
        sumWinRate = 0d;
        sumMaxValue = 0d;
    }

    public
    void addSample(double sample) {
        queue.add(sample);
        sumMaxValue += sample;
        sumWinRate += (sample >= gameWinValue) ? 1d : 0d;
        if (queue.size() > sampleSize) {
            Double old = queue.remove();
            sumWinRate -= (old >= gameWinValue) ? 1d : 0d;
            sumMaxValue -= old;
        }
    }

    public
    double averageMaxValue() {
        if (queue.size() < sampleSize) {
            return 0;
        } else {
            return sumMaxValue / (sampleSize * 1d);
        }
    }

    public
    double averageWinRate() {
        if (queue.size() < sampleSize) {
            return 0;
        } else {
            return (sumWinRate * 100d) / (sampleSize * 1d);
        }
    }

    public
    int currentSampleSize() {
        return queue.size();
    }

    public
    String printableAverages() {
        if (queue.size() < sampleSize) {
            return "? %";
        } else {
            return "winRate " + formatter.format(averageWinRate()) + " % - maxTile " + formatter.format(averageMaxValue());
        }
    }

    public
    void reset() {
        queue.clear();
        sumWinRate = 0d;
        sumMaxValue = 0d;
    }

}
