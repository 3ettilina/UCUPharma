package Interfaces;


import java.util.LinkedList;

public interface IArbolBB<T> {

    /**
     * Inserta un elemento en el arbol. En caso de ya existir un elemento con la
     * clave indicada en "unElemento", retorna falso.
     *
     * @param unElemento Elemento a insertar
     * @return Exito de la operaci�n
     */

    public boolean insertar(IElementoAB<T> unElemento);

 

    /**
     * Busca un elemento dentro del �rbol.
     *
     *
     * @param unaEtiqueta Etiqueta identificadora del elemento a buscar.
     * .
     * @return Elemento encontrado. En caso de no encontrarlo, retorna nulo.
     */
    public IElementoAB<T> buscar(Comparable unaEtiqueta);

   
       /**
     * Elimina un elemento dada una etiqueta.
     * @param unaEtiqueta 
     */
    public void eliminar(Comparable unaEtiqueta);

    
    
    /**
     * Imprime en InOrden los elementos del �rbol, separados por guiones.
     *
     * @return String conteniendo el preorden separado por guiones.
     */
    public String inOrden();
    
    /**
     *
     * 
     * @return una LinkedList con las etiquetas del recorrido.
     * 
     */
    public LinkedList<Comparable> inorden( );

  
	
	
}

