/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations;

/**
 *
 * @author Franco
 */
public class StringIterator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String ejemplo = "hola\ncomo\nestas, poroto";
        StringIterator iterador = new StringIterator(ejemplo, null, ",");
        String renglon;
        while ( (renglon = iterador.readLine()) != null ) {
            System.out.println("renglon = " + renglon);
        }
    }
    private final String comentarios;
    private final String nuevaLinea;
    private int renglon = 0;
    private final String txt;

    /**
     * Itera sobre textos
     * <p/>
     * @param string
     */
    public StringIterator(String string) {
        this.txt = string;
        this.comentarios = null;
        //FIXME esto trae bugs!?
        this.nuevaLinea = "\n";
    }

    /**
     * Itera sobre textos, evitando el texto que continua desde comentarios
     * hasta nueva linea
     * <p/>
     * @param string
     * @param comments
     */
    public StringIterator(String string, String comments) {
        this.txt = string;
        this.comentarios = comments;
        this.nuevaLinea = "\n";
    }

    /**
     * Itera sobre textos, evitando el texto que continua desde comentarios
     * hasta nueva linea, la cual es nuevaLinea
     * <p/>
     * @param string
     * @param comments
     * @param newLine
     */
    public StringIterator(String string, String comments, String newLine) {
        this.txt = string;
        this.comentarios = comments;
        this.nuevaLinea = newLine;
    }

    /**
     *
     * @return
     */
    public String readLine() {
        String salida = null;
        if ( renglon != -1 ) {
            int enter = txt.indexOf(nuevaLinea, renglon);
            if ( enter == -1 ) {
                enter = txt.length();
            }

            salida = txt.substring(renglon, enter);

            renglon = enter + 1;
            if ( renglon > txt.length() ) {
                renglon = -1;
            }

            if ( comentarios != null && !comentarios.isEmpty() ) {
                if ( salida.trim().startsWith(comentarios) ) {
                    salida = readLine();
                }
            }
        }
        return salida;
    }

    /**
     *
     */
    public void reset() {
        renglon = 0;
    }

}
