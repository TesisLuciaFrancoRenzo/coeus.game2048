/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.experiments.reports;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author franc
 */
public
class ReportCollector {

    private static File WORKING_DIRECTORY;

    private static Map< String, ReportItem > items;

    private static
    double extractNumber( final String line ) {
        final int index = line.indexOf(':');
        assert index != -1;
        return Double.parseDouble(line.substring(index + 1).trim().replaceFirst(",", "."));
    }

    /**
     * @param args the command line arguments
     *
     * @throws IOException
     */
    public static
    void main( final String... args )
            throws IOException {
        WORKING_DIRECTORY = ( args.length == 0 ) ? workingDir() : new File(args[0]);
        items = new HashMap<>();
        assert WORKING_DIRECTORY != null;
        workFiles(WORKING_DIRECTORY.listFiles());
        final List< ReportItem > finalReportItems = new ArrayList<>(items.values());

        Collections.sort(finalReportItems);
        Collections.sort(finalReportItems);

        final File output = new File("./BestResults.txt");
        try ( PrintStream printStream = new PrintStream(output, "UTF-8") ) {
            finalReportItems.
                    forEach(( reportItem ) -> {
                        reportItem.createBackupFile();
                        printStream.println(reportItem);
                    });
        }
    }

    private static
    void parseFile( final File file ) {
        //cargamos el archivo ya guardado
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")) ) {
            for ( String line = br.readLine(); line != null; line = br.readLine() ) {
                if ( line.matches("Win rate: [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?") ) {
                    final ReportItem reportItem = new ReportItem();
                    reportItem.setFile(file.getCanonicalPath().replace(WORKING_DIRECTORY.getCanonicalPath(), ""));
                    try {
                        final File neuralNetworkFile = new File(file.getCanonicalPath().replace("_STATISTICS.txt", ".ser"));
                        if ( neuralNetworkFile.exists() ) {
                            reportItem.setBestNeuralNetworkSerFile(neuralNetworkFile);
                        }
                    } catch ( final IOException ex ) {
                        Logger.getLogger(ReportCollector.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    final String indexToFind = "_trained_BackupN-";
                    int          index       = reportItem.getFile().indexOf(indexToFind);

                    if ( index < 0 ) {
                        System.err.println(" no se encuentra _trained_BackupN- en el nombre del archivo");
                    } else {
                        final String key = reportItem.getFile().substring(0, index);

                        index += indexToFind.length();
                        final int index2 = reportItem.getFile().indexOf('_', index);
                        reportItem.setTrainingNumber(reportItem.getFile().substring(index, index2));
                        reportItem.setBestValue(extractNumber(line));

                        if ( items.containsKey(key) ) {
                            final ReportItem oldReportItem = items.get(key);
                            if ( reportItem.getBestValue() > oldReportItem.getBestValue() ) {
                                items.put(key, reportItem);
                            }
                        } else {
                            items.put(key, reportItem);
                        }
                    }
                }
            }
        } catch ( final IOException ex ) {
            Logger.getLogger(ReportCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static
    void workFiles( @NotNull final File... files ) {
        for ( final File file : files ) {
            if ( file.isDirectory() ) {
                workFiles(file.listFiles()); // Calls same method again.
            } else if ( file.getName().matches(".+_trained_BackupN-\\d+_STATISTICS\\.txt") ) {
                parseFile(file);
            }
        }
    }

    private static
    File workingDir() {
        if ( WORKING_DIRECTORY == null ) {
            try {
                final String Recurso = ReportCollector.class.getSimpleName() + ".class";
                final URL    url     = ReportCollector.class.getResource(Recurso);
                File         f;

                switch ( url.getProtocol() ) {
                    case "file":
                        f = new File(url.toURI());
                        do {
                            f = f.getParentFile();
                        } while ( !f.isDirectory() );
                        WORKING_DIRECTORY = f.getParentFile();
                        break;
                    case "jar":
                        String s = url.toString();
                        s = s.substring(4);
                        final String expected = "!/" + Recurso;
                        s = s.substring(0, s.length() - expected.length());
                        f = new File(new URL(s).toURI());
                        do {
                            f = f.getParentFile();
                        } while ( !f.isDirectory() );
                        WORKING_DIRECTORY = f.getParentFile();
                        break;
                    default:
                        throw new IllegalArgumentException("not supported format");
                }
            } catch ( URISyntaxException | MalformedURLException | IllegalArgumentException e ) {
                WORKING_DIRECTORY = new File(".");
            }
        }
        return WORKING_DIRECTORY;
    }

}
