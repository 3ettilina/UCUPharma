package Interfaces;


import java.util.LinkedList;
import Interfaces.*;
import java.util.Date;
import ucupharma.Documento;

public interface IElementoAB<T> {

    /**
     * Obtiene el valor de la etiqueta del nodo.
     *
     * @return Etiqueta del nodo.
     */
    public Comparable getEtiqueta();

    /**
     * Obtiene el hijo izquierdo del nodo.
     *
     * @return Hijo Izquierdo del nodo.
     */
    public IElementoAB getHijoIzq();

    /**
     * Obtiene el hijo derecho del nodo.
     *
     * @return Hijo derecho del nodo.
     */
    public IElementoAB getHijoDer();

    /**
     * Asigna el hijo izquierdo del nodo.
     *
     * @return Elemento a ser asignado como hijo izquierdo.
     */
    public void setHijoIzq(IElementoAB elemento);

    /**
     * Asigna el hijo derecho del nodo.
     *
     * @return Elemento a ser asignado como hijo derecho.
     */
    public void setHijoDer(IElementoAB elemento);

    /**
     * Busca un elemento dentro del arbol con la etiqueta indicada.
     *
     * @param unaEtiqueta del nodo a buscar
     * @return Elemento encontrado. En caso de no encontrarlo, retorna nulo.
     */
    public IElementoAB buscar(Comparable unaEtiqueta);

    

    /**
     * Inserta un elemento dentro del arbol.
     *
     * @param elemento Elemento a insertar.
     * @return Exito de la operaci�n.
     */
    public boolean insertar(IElementoAB elemento);

  
    /**
     * Imprime en inorden el arbol separado por guiones.
     *
     * @return String conteniendo el InOrden
     */
    public String inOrden();

     /**
     * pone las etiquetas del recorrido en inorden en una linkedlist.
     *
     * @param unaLista
     */
    public void inOrden(LinkedList<Comparable> unaLista);

    /**
     * Retorna los datos contenidos en el elemento.
     *
     * @return
     */
    public T getDatos();
   
    /**
     * Elimina un elemento dada una etiqueta.
     * @param unaEtiqueta
     * @return 
     */
    public IElementoAB<T> eliminar(Comparable unaEtiqueta);

    /**
     * Método que devuelve uno o más elementos, buscando en el árbol correspondiente utilizando el parámetro a buscr (nombre del atributo)
     * y un String del valor que se desea buscar.
     * @param param
     * @param aBuscar
     * @param lis 
     */
    public void buscarParametro(String param, String aBuscar, ILista lis);
    
    /**
     * Método que lista todos los elementos de un árbol
     * @param list 
     */
    public void listarTodos(ILista<T> list);
    
    /**
     * Método que lista los elementos, ordenados por área y por descripción
     * @param list 
     */
    public void listarAreas(ILista<ILista<T>> list);
    
    /**
     * Método que devuelve el reporte de ventas en un rango de fechas dado.
     * @param fecha_desde
     * @param fecha_hasta
     * @param lista 
     */
    public void reporteVentas(Date fecha_desde, Date fecha_hasta, ILista<T> lista);
    
    /**
     * Método que elimina un área de aplicación. Impactando sobre los elementos del árbol.
     * @param area 
     */
    public void eliminarArea(String area);
    
    /**
     * Método que retorna el promedio de ventas de un producto
     * @param codigo
     * @param total
     * @param cantMeses
     * @param cantVentas
     * @param meses
     * @param promedio
     * @return vector con valores para insertar en tabla
     */
    public Object[] promedioVentas(int codigo, double total, int cantMeses, int cantVentas, ILista<String> meses, double promedio);
}
