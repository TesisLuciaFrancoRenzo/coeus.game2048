/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.experiments.reports;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author franc
 */
class ReportItem
        implements Comparable<ReportItem> {

    protected Double bestValue;
    protected boolean failed = false;
    protected String file;
    protected String trainingNumber;
    private   File   bestNeuralNetworkFile;
    private   String motive;

    @Override
    public
    int compareTo(ReportItem o) {
        return bestValue.compareTo(o.bestValue) * -1;
    }

    public
    void createBackupFile() {
        if (bestNeuralNetworkFile != null && bestNeuralNetworkFile.exists()) {
            try {
                File destiny = new File(bestNeuralNetworkFile.getCanonicalPath().replace(".ser", ".BEST"));
                Files.copy(bestNeuralNetworkFile.toPath(), destiny.toPath(), REPLACE_EXISTING);
                // System.out.println("copy (" + bytes + ") = " + destiny);
            } catch (IOException ex) {
                Logger.getLogger(ReportItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void failed(String motive) {
        failed = true;
        this.motive = motive;
    }

    public
    File getBestNeuralNetworkFile() {
        return bestNeuralNetworkFile;
    }

    /**
     * @return the bestValue
     */
    public
    double getBestValue() {
        return bestValue;
    }

    void setBestValue(double bestValue) {
        this.bestValue = bestValue;
    }

    /**
     * @return the file
     */
    public
    String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public
    void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the trainingNumber
     */
    public
    String getTrainingNumber() {
        return trainingNumber;
    }

    /**
     * @param trainingNumber the trainingNumber to set
     */
    public
    void setTrainingNumber(String trainingNumber) {
        this.trainingNumber = trainingNumber;
    }

    /**
     * @return the failed
     */
    public
    boolean isFailed() {
        return failed;
    }

    void setBestNeuralNetworkSerFile(File bestNeuralNetworkFile) {
        this.bestNeuralNetworkFile = bestNeuralNetworkFile;
    }

    @Override
    public
    String toString() {
        return ((failed) ? "Fail: " + motive + "\t" : "") + bestValue + "\t" + trainingNumber + "\t" + file;
    }


}
