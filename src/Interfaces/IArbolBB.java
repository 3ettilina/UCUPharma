package Interfaces;


import java.util.LinkedList;

public interface IArbolBB<T> {
    
    /**
     * Retorna la raiz del arbol
     * @return raiz
     */
    public IElementoAB<T> getRaiz();

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

    /**
     * Método que devuelve una lista con todos los productos vendidos en un rango de fechas dado.
     * @param fecha_desde
     * @param fecha_hasta
     * @param listaVentas
     * @return ILista<T> con Ventas.
     */
    public ILista<T> reporteFechas(String fecha_desde, String fecha_hasta, ILista<T> listaVentas);
    
    /**
     * Método que lista todos los productos
     * @return ILista<T> con los productos dentro.
     */
    public ILista<T> listarTodos();
    
    /**
     * Método que elimina un área de aplicación
     * @param area
     * @return Devuelve un String con el mensaje de confirmación
     */
    public String eliminarArea(String area);
    
    /**
     * Método que retorna el promedio de ventas de un producto dado
     * @param codigo
     * @return 
     */
    public Object[] promedioVentas(int codigo);
}

