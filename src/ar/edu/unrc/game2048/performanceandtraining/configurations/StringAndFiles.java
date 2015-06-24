/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author franco
 */
public class StringAndFiles {

    /**
     *
     */
    public static final String ISO_8859_1 = "ISO-8859-1";   	//ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
    /**
     *
     */
    public static final String US_ASCII = "US-ASCII"; 	//Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
    /**
     *
     */
    public static final String UTF_16 = "UTF-16"; //Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark
    /**
     *
     */
    public static final String UTF_16BE = "UTF-16BE"; 	//Sixteen-bit UCS Transformation Format, big-endian byte order
    /**
     *
     */
    public static final String UTF_16LE = "UTF-16LE"; 	//Sixteen-bit UCS Transformation Format, little-endian byte order
    /**
     *
     */
    public static final String UTF_8 = "UTF-8"; 	//Eight-bit UCS Transformation Format

    /**
     *
     * @param file
     * @param codification <p>
     * @return <p>
     * @throws FileNotFoundException
     * @throws IOException
     */
    static public String fileToString(File file, String codification) throws FileNotFoundException, IOException {
        StringBuilder salida = new StringBuilder();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), codification)) ) {
            String linea = reader.readLine();
            while ( linea != null ) {
                // Aquí lo que tengamos que hacer con la línea puede ser esto
                salida.append(linea);
                linea = reader.readLine();
                if ( linea != null ) {
                    salida.append("\n");
                }
            }
        }
        return salida.toString();
    }

    /**
     *
     * @param remoteFile
     * @param codification <p>
     * @return <p>
     * @throws MalformedURLException
     * @throws IOException
     * @throws ConnectException
     */
    static public String onlineFileToString(String remoteFile, String codification) throws MalformedURLException, IOException, ConnectException {
        StringBuilder salida = new StringBuilder();
        URL url = new URL(remoteFile);
        URLConnection urlConn = url.openConnection();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), codification)) ) {
            String linea = reader.readLine();
            while ( linea != null ) {
                // Aquí lo que tengamos que hacer con la línea puede ser esto
                salida.append(linea);
                linea = reader.readLine();
                if ( linea != null ) {
                    salida.append("\n");
                }
            }
        }
        return salida.toString();
    }

    /**
     *
     * @param file
     * @param text
     * @param codification <p>
     * @throws IOException
     */
    static public void stringToFile(File file, String text, String codification) throws IOException {
        if ( !file.exists() ) {
            file.createNewFile();
        }
        try ( BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), codification)) ) {
            out.write(text);
        }
    }

}
