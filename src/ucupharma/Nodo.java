/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.INodo;

/**
 * 
 * @author Bettina
 * @param <T> Tipo de dato contenido en Nodo
 */
public class Nodo<T> implements INodo<T>{
    private final Comparable etiqueta;
    private T dato;
    private INodo<T> siguiente = null;

    public Nodo(Comparable etiqueta, T dato) {
        this.etiqueta = etiqueta;
        this.dato = dato;
    }
    
    @Override
    public T getDato() {
        return this.dato;
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;

    }
    
    @Override
    public Comparable getEtiqueta() {
        return this.etiqueta;
    }

    @Override
    public void setSiguiente(INodo<T> nodo) {
        this.siguiente = nodo;

    }

    @Override
    public INodo<T> getSiguiente() {
        return this.siguiente;
    }

    @Override
    public void imprimir() {
        System.out.println(dato.toString());
    }

    @Override
    public void imprimirEtiqueta() {
        System.out.println(this.etiqueta);
    }

    public INodo<T> clonar() {
        return new Nodo(this.etiqueta, this.dato);
    }

    @Override
    public boolean equals(INodo unNodo) {
        return this.dato.equals(unNodo.getDato());
    }

    @Override
    public int compareTo(Comparable etiqueta) {
        return this.etiqueta.compareTo(etiqueta);
    }
}
