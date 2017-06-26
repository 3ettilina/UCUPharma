/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author Bettina
 */
public interface ILista<T> {
    
    /**
     * Metodo encargado de agregar un nodo al final de la lista.
     *
     * @param nodo - Nodo a agregar
     */
    public void insertar(INodo<T> nodo);

    /**
     * Metodo encargado de buscar un nodo utilizando la clave obtenida por parametro.
     *
     * @param clave - Clave del nodo a buscar.
     * @return El nodo encontrado. En caso de no encontrarlo, retornar null.
     */
    public INodo<T> buscar(Comparable clave);

    /**
     * Metodo encargado de eliminar un nodo utilizando la clave obtenida por parametro .
     *
     * @param clave Clave del nodo a eliminar.
     * @return True en caso de que la eliminacion haya sido efectuada con exito.
     */
    public boolean eliminar(Comparable clave);

    /**
     * Metodo encargado de imprimir en consola las claves de los nodos
     * contenidos en la lista.
     */
    public String imprimir();

    /**
     * Retorna un String con las claves separadas por el separador pasado por
     * parametro.
     *
     * @param separador Separa las claves
     * @return
     */
    public String imprimir(String separador);

    /**
     * Retorna la cantidad de elementos de la lista. En caso de que la lista
     * este vacia, retorna 0.
     *
     * @return Cantidad de elementos de la lista.
     */
    public int cantElementos();

    /**
     * Indica si la lista contiene o no elementos.
     *
     * @return Si tiene elementos o no.
     */
    public boolean esVacia();

    /**
     * Retorna el primer nodo de la lista.
     *
     * @return Primer nodo de la lista.
     */
    public INodo getPrimero();

    
    /**
     * Inserta un elemento en una lista de forma ordenada.
     * @param productoAIngresar 
     */
    public void insertarOrdenado(INodo<T> productoAIngresar);
    
}
