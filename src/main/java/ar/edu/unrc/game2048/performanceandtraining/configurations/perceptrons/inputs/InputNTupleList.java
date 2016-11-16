/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unrc.game2048.performanceandtraining.configurations.perceptrons.inputs;

import java.util.*;

/**
 * Implementación de una Lista, útil para almacenar valores binarios, optimizada para no ocupar memoria con los valores
 * 0. Utilizada para las entradas de una red neuronal grande.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class InputNTupleList
        implements List<Double> {

    private final Set<Integer> set;

    /**
     *
     */
    public
    InputNTupleList() {
        set = new HashSet<>();
    }

    /**
     * @param e
     *
     * @return
     */
    @Override
    public
    boolean add(Double e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     * @param element
     */
    @Override
    public
    void add(
            int index,
            Double element
    ) {
        set.add(index);
    }

    /**
     * @param c
     *
     * @return
     */
    @Override
    public
    boolean addAll(Collection<? extends Double> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     * @param c
     *
     * @return
     */
    @Override
    public
    boolean addAll(
            int index,
            Collection<? extends Double> c
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public
    void clear() {
        set.clear();
    }

    /**
     * @param o
     *
     * @return
     */
    @Override
    public
    boolean contains(Object o) {
        return set.contains(o);
    }

    /**
     * @param c
     *
     * @return
     */
    @Override
    public
    boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     *
     * @return
     */
    @Override
    public
    Double get(int index) {
        return (set.contains(index)) ? 1d : 0d;
    }

    /**
     * @return tamaño interno usado.
     */
    public
    int getInternalSetSize() {
        return set.size();
    }

    /**
     * @param o
     *
     * @return
     */
    @Override
    public
    int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return
     */
    @Override
    public
    boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return
     */
    @Override
    public
    Iterator<Double> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param o
     *
     * @return
     */
    @Override
    public
    int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return
     */
    @Override
    public
    ListIterator<Double> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     *
     * @return
     */
    @Override
    public
    ListIterator<Double> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param o
     *
     * @return
     */
    @Override
    public
    boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     *
     * @return
     */
    @Override
    public
    Double remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param c
     *
     * @return
     */
    @Override
    public
    boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param c
     *
     * @return
     */
    @Override
    public
    boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param index
     * @param element
     *
     * @return
     */
    @Override
    public
    Double set(
            int index,
            Double element
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return
     */
    @Override
    public
    int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param fromIndex
     * @param toIndex
     *
     * @return
     */
    @Override
    public
    List<Double> subList(
            int fromIndex,
            int toIndex
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return
     */
    @Override
    public
    Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param <T>
     * @param a
     *
     * @return
     */
    @Override
    public
    <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
