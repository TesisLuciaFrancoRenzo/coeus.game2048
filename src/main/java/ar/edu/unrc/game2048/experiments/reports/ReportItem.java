/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.experiments.reports;

import org.jetbrains.annotations.NotNull;

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
        implements Comparable< ReportItem > {

    protected Double  bestValue             = null;
    protected boolean failed                = false;
    protected String  file                  = null;
    protected String  trainingNumber        = null;
    private   File    bestNeuralNetworkFile = null;
    private   String  motive                = null;

    @Override
    public
    int compareTo( @NotNull final ReportItem o ) {
        return bestValue.compareTo(o.bestValue) * -1;
    }

    public
    void createBackupFile() {
        if ( ( bestNeuralNetworkFile != null ) && bestNeuralNetworkFile.exists() ) {
            try {
                final File destiny = new File(bestNeuralNetworkFile.getCanonicalPath().replace(".ser", ".BEST"));
                Files.copy(bestNeuralNetworkFile.toPath(), destiny.toPath(), REPLACE_EXISTING);
                // System.out.println("copy (" + bytes + ") = " + destiny);
            } catch ( final IOException ex ) {
                Logger.getLogger(ReportItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the bestValue
     */
    public
    double getBestValue() {
        return bestValue;
    }

    void setBestValue( final double bestValue ) {
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
    void setFile( final String file ) {
        this.file = file;
    }

    /**
     * @param trainingNumber the trainingNumber to set
     */
    public
    void setTrainingNumber( final String trainingNumber ) {
        this.trainingNumber = trainingNumber;
    }

    void setBestNeuralNetworkSerFile( final File bestNeuralNetworkFile ) {
        this.bestNeuralNetworkFile = bestNeuralNetworkFile;
    }

    @Override
    public
    String toString() {
        return ( ( failed ) ? "Fail: " + motive + '\t' : "" ) + bestValue + '\t' + trainingNumber + '\t' + file;
    }


}
