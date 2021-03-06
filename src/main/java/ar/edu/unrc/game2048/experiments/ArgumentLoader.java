/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.game2048.experiments;

import java.util.*;

/**
 * Parsea argumentos de un programa (main) en una estructura amigable para su uso. Los argumentos deben tener el estilo:
 * <p>
 * arg1=2 arg44=[0.0,55.8]
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class ArgumentLoader {

    private final Map< String, String > map;

    /**
     * @param args argumentos a parsear.
     */
    public
    ArgumentLoader( final String... args ) {
        super();
        map = new HashMap<>(args.length);
        for ( final String arg : args ) {
            final int index = arg.indexOf('=');
            if ( index == -1 ) {
                throw new IllegalArgumentException("No se reconoce el argumento: " + arg);
            }
            final String id    = arg.substring(0, index).trim();
            final String value = arg.substring(index + 1).trim();
            map.put(id, value);
        }
    }

    /**
     * Parsea arreglos de Boolean.
     *
     * @param arrayString formato del estilo [true,false,false]
     *
     * @return lista de Boolean.
     */
    public static
    boolean[] parseBooleanArray( final String arrayString ) {

        if ( ( arrayString == null ) || !arrayString.startsWith("[") || !arrayString.endsWith("]") ) {
            throw new IllegalArgumentException("arrayString format unknown: " + arrayString);
        }

        final String[]  list = arrayString.substring(1, arrayString.length() - 1).split(",");
        final boolean[] out  = new boolean[list.length];
        int             i    = 0;
        for ( final String bool : list ) {
            out[i] = Boolean.parseBoolean(bool.trim());
            i++;
        }
        return out;
    }

    /**
     * Parsea arreglos de Double.
     *
     * @param arrayString formato del estilo [0.0,0.1,0.2,5547]
     *
     * @return lista de Double.
     */
    public static
    List< Double > parseDoubleArray( final String arrayString ) {

        if ( ( arrayString == null ) || !arrayString.startsWith("[") || !arrayString.endsWith("]") ) {
            throw new IllegalArgumentException("arrayString format unknown: " + arrayString);
        }

        final String[]       list = arrayString.substring(1, arrayString.length() - 1).split(",");
        final List< Double > out  = new ArrayList<>(list.length);
        for ( final String number : list ) {
            out.add(Double.parseDouble(number.trim()));
        }
        return out;
    }

    /**
     * Parsea arreglos de Integer.
     *
     * @param arrayString formato del estilo [0,1,2,5547]
     *
     * @return lista de Integer.
     */
    public static
    List< Integer > parseIntegerArray( final String arrayString ) {

        if ( ( arrayString == null ) || !arrayString.startsWith("[") || !arrayString.endsWith("]") ) {
            throw new IllegalArgumentException("arrayString format unknown: " + arrayString);
        }

        final String[]        list = arrayString.substring(1, arrayString.length() - 1).split(",");
        final List< Integer > out  = new ArrayList<>(list.length);
        for ( final String number : list ) {
            out.add(Integer.parseInt(number.trim()));
        }
        return out;
    }

    /**
     * Parsea arreglos de Strings.
     *
     * @param arrayString formato del estilo [clase1,clase-2,clase_3]
     *
     * @return lista de Strings.
     */
    public static
    List< String > parseStringArray( final String arrayString ) {

        if ( ( arrayString == null ) || !arrayString.startsWith("[") || !arrayString.endsWith("]") ) {
            throw new IllegalArgumentException("arrayString format unknown: " + arrayString);
        }
        return Arrays.asList(arrayString.substring(1, arrayString.length() - 1).split(","));
    }

    /**
     * @param id identificador del argumento.
     *
     * @return valor que contiene argumento.
     */
    public
    String getArg( final String id ) {
        final String value = map.get(id);
        if ( value == null ) {
            throw new IllegalArgumentException("No se reconoce el argumento: " + id);
        }
        return map.get(id);
    }

}
