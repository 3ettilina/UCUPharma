package ucupharma;


import Exceptions.*;
import Interfaces.*;
import java.util.LinkedList;

public class TArbolBB<T> implements IArbolBB<T> {

    private IElementoAB<T> raiz;

    /**
     * Separador utilizado entre elemento y elemento al imprimir la lista
     */
    public static final String SEPARADOR_ELEMENTOS_IMPRESOS = "-";

    public TArbolBB() {
        raiz = null;
    }
    
    @Override
    public IElementoAB<T> getRaiz(){
        return this.raiz;
    }

    /**
     * @param unElemento
     * @return
     */
    @Override
    public boolean insertar(IElementoAB<T> unElemento) {
        if (esVacio()) {
            raiz = unElemento;
            return true;
        } else {
            return raiz.insertar(unElemento);
        }
    }

    /**
     * @param unaEtiqueta
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public IElementoAB<T> buscar(Comparable unaEtiqueta) {
        if (esVacio()) {
            return null;
        } else {
            return raiz.buscar(unaEtiqueta);
        }
    }
    

    

    /**
     * @return recorrida en inorden del arbol, null en caso de ser vacío
     */
    @Override
    public String inOrden() {
        if (esVacio()) {
            return null;
        } else {
            return raiz.inOrden();
        }
    }

    /**
     * @return recorrida en preOrden del arbol, null en caso de ser vacío
     */
    /**
     * @return
     */
    public boolean esVacio() {
        return (raiz == null);
    }

    /**
     * @return True si habían elementos en el árbol, false en caso contrario
     */
    public boolean vaciar() {
        if (!esVacio()) {
            raiz = null;
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public LinkedList<Comparable> inorden() {
        LinkedList<Comparable> listaInorden = null;
        if (!esVacio()) {
            listaInorden = new LinkedList<>();
            raiz.inOrden(listaInorden);
        }
        return listaInorden;

    }

    @Override
    public void eliminar(Comparable unaEtiqueta) {
        if (!esVacio()) {
            this.raiz = this.raiz.eliminar(unaEtiqueta);
        }
    }

    public ILista<T> buscarParametro(String param, String aBuscar){
        ILista<T> lista = new Lista<>();
        if(esVacio()){
            return null;
        }
        else{
            this.raiz.buscarParametro(param, aBuscar, lista);
        }
        return lista;
    }
    
    public ILista<T> listarTodos(){
        ILista<T> lista = new Lista<>();
        if(esVacio()){
            return null;
        }
        else{
            this.raiz.listarTodos(lista);
        }
        return lista;
    }
    
    public ILista<ILista<T>> listarAreas(){
        ILista<ILista<T>> lista = new Lista<>();
        if(esVacio()){
            return null;
        }
        else{
            this.raiz.listarAreas(lista);
        }
        return lista;
    }

   
}
