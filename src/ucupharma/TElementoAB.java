package ucupharma;


import Interfaces.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TElementoAB<T> implements IElementoAB<T> {

    private final Comparable etiqueta;
    private IElementoAB hijoIzq;
    private IElementoAB hijoDer;
    private final T datos;

    /**
     * @param unaEtiqueta
     * @param unosDatos
     */
    @SuppressWarnings("unchecked")
    public TElementoAB(Comparable unaEtiqueta, T unosDatos) {
        etiqueta = unaEtiqueta;
        datos = unosDatos;
    }

    @Override
    public IElementoAB getHijoIzq() {
        return hijoIzq;
    }

    @Override
    public IElementoAB getHijoDer() {
        return hijoDer;
    }

    /**
     * @param unElemento
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean insertar(IElementoAB unElemento) {
        if (unElemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().insertar(unElemento);
            } else {
                hijoIzq = unElemento;
                return true;
            }
        } else if (unElemento.getEtiqueta().compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                return getHijoDer().insertar(unElemento);
            } else {
                hijoDer = unElemento;
                return true;
            }
        } else {
            // ya existe un elemento con la misma etiqueta.-
            return false;
        }
    }

    /**
     * @param unaEtiqueta
     * @return
     */
    @Override
    public IElementoAB buscar(Comparable unaEtiqueta) {

        if (unaEtiqueta.equals(etiqueta)) {
            return this;
        } else if (unaEtiqueta.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().buscar(unaEtiqueta);
            } else {
                return null;
            }
        } else if (hijoDer != null) {
            return getHijoDer().buscar(unaEtiqueta);
        } else {
            return null;
        }
    }
   
    @Override
    public void buscarParametro(String param, String aBuscar, ILista lista){
        try{
            Class datoClass = this.datos.getClass();
            Field paramABuscar = datoClass.getDeclaredField(param);
            paramABuscar.setAccessible(true);
            
            String vAct = paramABuscar.get(this.datos).toString();
            
            if(vAct.toUpperCase().contains(aBuscar.toUpperCase())){
                lista.insertarOrdenado(new Nodo(this.etiqueta, this.datos));
            }
            if(this.hijoIzq != null){
                hijoIzq.buscarParametro(param, aBuscar, lista);
            }
            if(this.hijoDer != null){
                hijoDer.buscarParametro(param, aBuscar, lista);
            }
            
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     *
     * @param lista
     */
    @Override
    public void listarTodos(ILista<T> lista){
        lista.insertar(new Nodo(this.etiqueta, this.datos));
        if(this.hijoIzq != null){
            hijoIzq.listarTodos(lista);
        }
        if(this.hijoDer != null){
            hijoDer.listarTodos(lista);
        }
    }

    /**
     *
     * @param lista
     */
    @Override
    public void listarAreas(ILista<ILista<T>> lista){
        try{
            ILista<IProducto> listaArea = new Lista<>();
            
            Class datoClass = this.datos.getClass();
            Field areaApli = datoClass.getDeclaredField("areaAplicacion");
            areaApli.setAccessible(true);
            String areaProd = areaApli.get(this.datos).toString();
            
            Field desc = datoClass.getDeclaredField("descripcion_corta");
            desc.setAccessible(true);
            String descProd = desc.get(this.datos).toString();
            
            INodo<ILista<T>> nodo = lista.buscar(areaProd);
            if(nodo != null){
                if(nodo.getEtiqueta().equals(areaProd)){
                    nodo.getDato().insertarOrdenado(new Nodo(descProd, this.datos));
                }
            }
            else{
                listaArea.insertarOrdenado(new Nodo(descProd, this.datos));
                lista.insertarOrdenado(new Nodo(areaProd,listaArea));
            }
            if(this.hijoIzq != null){
                hijoIzq.listarAreas(lista);
            }
            if(this.hijoDer != null){
                hijoDer.listarAreas(lista);
            }
        } 
        catch (NoSuchFieldException ex) {
        Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SecurityException ex) {
        Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalArgumentException ex) {
        Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
        Logger.getLogger(TElementoAB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    /**
     * @return recorrida en inorden del subArbol que cuelga del elemento actual
     */
    @Override
    public String inOrden() {
        StringBuilder tempStr = new StringBuilder();
        if (hijoIzq != null) {
            tempStr.append(getHijoIzq().inOrden());
            tempStr.append(TArbolBB.SEPARADOR_ELEMENTOS_IMPRESOS);
        }
        tempStr.append(imprimir());
        if (hijoDer != null) {
            tempStr.append(TArbolBB.SEPARADOR_ELEMENTOS_IMPRESOS);
            tempStr.append(getHijoDer().inOrden());
        }

        return tempStr.toString();
    }

    @Override
    public void inOrden(LinkedList<Comparable> unaLista) {
        if (hijoIzq != null) {
            hijoIzq.inOrden(unaLista);

        }
        unaLista.add(this.getEtiqueta());
        if (hijoDer != null) {
            hijoDer.inOrden(unaLista);
        }

    }

    @Override
    public Comparable getEtiqueta() {
        return etiqueta;
    }

    /**
     * @return
     */
    public String imprimir() {
        return (etiqueta.toString());
    }

    @Override
    public T getDatos() {
        return datos;
    }

    @Override
    public void setHijoIzq(IElementoAB elemento) {
        this.hijoIzq = elemento;

    }

    @Override
    public void setHijoDer(IElementoAB elemento) {
        this.hijoDer = elemento;
    }

  

   @Override
    public IElementoAB eliminar(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.getEtiqueta()) < 0) {
            if (this.hijoIzq != null) {
                this.hijoIzq = hijoIzq.eliminar(unaEtiqueta);
            }
            return this;
        }

        if (unaEtiqueta.compareTo(this.getEtiqueta()) > 0) {
            if (this.hijoDer != null) {
                this.hijoDer = hijoDer.eliminar(unaEtiqueta);

            }
            return this;
        }

        return quitaElNodo();
    }
 private IElementoAB quitaElNodo() {
        if (hijoIzq == null) {    // solo tiene un hijo o es hoja
            return hijoDer;
        }

        if (hijoDer == null) { // solo tiene un hijo o es hoja
            return hijoIzq;
        }
        // tiene los dos hijos, buscamos el lexicograficamente anterior
        IElementoAB elHijo = hijoIzq;
        IElementoAB elPadre = this;

        while (elHijo.getHijoDer() != null) {
            elPadre = elHijo;
            elHijo = elHijo.getHijoDer();
        }

        if (elPadre != this) {
            elPadre.setHijoDer(elHijo.getHijoIzq());
            elHijo.setHijoIzq(hijoIzq);
        }

        elHijo.setHijoDer(hijoDer);
        setHijoIzq(null);  // para que no queden referencias y ayudar al collector
        setHijoDer(null);
        return elHijo;
    }

}
