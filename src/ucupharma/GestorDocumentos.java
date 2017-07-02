/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.*;
import Exceptions.*;
import java.util.Date;

/**
 *
 * @author logikom
 */
public class GestorDocumentos {
    
    public ILista<IArbolBB<Documento>> listaDocumentos;
    public IArbolBB<IProducto> arbolStock = Farmacia.stock.getArbolProductos();
    
    public GestorDocumentos(){
        listaDocumentos = new Lista<>();
        inicializarArboles();

    }
    
    private void inicializarArboles(){
        // Creo el árbol de venta y lo inserto en el nodo correspondiente.
        IArbolBB<Documento> arbolVenta = new TArbolBB<>();
        INodo<IArbolBB<Documento>> nodoArbolVenta = new Nodo("Venta", arbolVenta);
        listaDocumentos.insertar(nodoArbolVenta);
        
        // Creo el arbol devolucion y lo inserto en el nodo correspondiente.
        IArbolBB<Documento> arbolDevolucion = new TArbolBB<>();
        INodo<IArbolBB<Documento>> nodoArbolDevolucion = new Nodo("Devolucion", arbolDevolucion);
        listaDocumentos.insertar(nodoArbolDevolucion);
    } 
    
    /**
     * Método para generar una venta.
     * @param codigo - Clave del producto a vender.
     * @param cantidad - Cantidad de unidades a vender.
     */
    public void vender(int codigo, int cantidad){
        IElementoAB<IProducto> nodoProd = arbolStock.buscar(codigo);
        if (nodoProd != null){
            IProducto prodAVender = nodoProd.getDatos();
            if (prodAVender.getCantidad() >= cantidad){
                double total = (prodAVender.getPrecio())*cantidad;
                Documento venta = new Documento(codigo, cantidad, total, 1, 0);
                
                IElementoAB<Documento> elemVenta = new TElementoAB(venta.getId(), venta);
                prodAVender.setCantidad(prodAVender.getCantidad()-cantidad);
                
                INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar("Venta");
                nodoVenta.getDato().insertar(elemVenta);
                System.out.println("Venta realizada satisfactoriamente. El id de su compra es: " + venta.getId() + ". Lo precisará si desea realizar una devolución.");
  
            }
            else{
                System.out.println("La cantidad a vender " + cantidad + " es mayor a la cantidad de items en stock " + prodAVender.getCantidad() + ". Por favor, intentelo nuevamente con la cantidad correcta.");
                
            }
        }
        else{
            System.out.println("El código ingresado no fue encontrado en la base de datos de productos, por favor, verifique.");
            
        }
    }
    
    /**
     * Método que permite devolver cierta cantidad de un producto vendido. Modificando las ventas.
     * @param numDocumento - Número de documento de la venta.
     * @param codigo - Código del producto a buscar.
     * @param cantidad - Cantidad de unidades a devolver.
     */
    public void devolver(int numDocumento, int codigo, int cantidad){
        INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar("Venta");
        IArbolBB<Documento> arbolVenta = nodoVenta.getDato();
        IElementoAB<Documento> elemVenta = arbolVenta.buscar(numDocumento);
        
        IElementoAB<IProducto> elemProd = arbolStock.buscar(codigo);
        
        Date fechaHoy = ManejadorFechas.obtenerFecha();
        
        if(elemProd != null){
            IProducto prod = elemProd.getDatos();
            prod.setCantidad(prod.getCantidad() + cantidad);
            prod.setUltimaActualizacion(fechaHoy);
            
            if(elemVenta != null){
            Documento venta = elemVenta.getDatos();
            int cantVendida = venta.getCantidad();
                if(cantVendida >= cantidad){
                    venta.setCantidad(venta.getCantidad() - cantidad);
                    venta.setFechaModificacion(fechaHoy);
                }
                else{
                    System.out.println("La cantidad que desea devolver supera la vendida en este documento. Verifique.");
                }
            
            }
            else{
                System.out.println("El código de venta no existe en nuestra base de datos. Verifique por favor.");
            }
        }
        else{
            System.out.println("No es posible devolver este producto, ya que UCUPharma no lo comercializa más.");
        }
    }
    
    public void comprar(int codigo, int cantidad){
        IElementoAB<IProducto> nodoProd = arbolStock.buscar(codigo);
        if (nodoProd != null){
            IProducto prodAComprar = nodoProd.getDatos();
            if (cantidad >= 0){
                double total = (prodAComprar.getPrecio())*cantidad;
                Documento venta = new Documento(codigo, cantidad, total, 1, 0);
                
                IElementoAB<Documento> elemVenta = new TElementoAB(venta.getId(), venta);
                prodAComprar.setCantidad(prodAComprar.getCantidad()+cantidad);
                
                INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar("Venta");
                nodoVenta.getDato().insertar(elemVenta);
                System.out.println("Venta realizada satisfactoriamente. El id de su compra es: " + venta.getId() + ". Lo precisará si desea realizar una devolución.");
  
            }
            else{
                System.out.println("La cantidad a comprar: " + cantidad + " es menor o igual a cero. Por favor, intentelo nuevamente con la cantidad correcta.");
                
            }
        }
        else{
            System.out.println("El código ingresado no fue encontrado en la base de datos de productos. ¿Desea ingresarlo?");
            
        }
    }
    
    public ILista<IElementoAB<Documento>> reporte(String fecha_desde, String fecha_hasta){
        Date fechaDesde = ManejadorFechas.crearFecha(fecha_desde);
        Date fechaHasta = ManejadorFechas.crearFecha(fecha_hasta);
        
        INodo<IArbolBB<Documento>> nodoListaDoc = listaDocumentos.buscar("Venta");
        IArbolBB<Documento> arbolVenta = nodoListaDoc.getDato();
        IElementoAB<Documento> nodoVenta = arbolVenta.getRaiz();
        
        ILista<IElementoAB<Documento>> ventasEncontradas = new Lista<>();
        
        try{
            if(nodoVenta != null){
                return auxReporte(nodoVenta, fechaDesde, fechaHasta, ventasEncontradas);
            }
            else{
                throw new NullTreeException("No existe ninguna venta realizada hasta el momento. Por favor, intentelo más tarde.");
            }
        }
        catch (NullTreeException ex){
            ex.getMessage();
        }
        return ventasEncontradas;
    }
    
    
    /**
     * Método que devuelve un reporte de ventas utilizando un rango de fechas.
     * @param nodoVenta
     * @param fecha_desde - Fecha anterior desde donde se comienza la búsqueda.
     * @param fecha_hasta - Fecha más reciente hasta donde se busca.
     * @param ventasEncontradas
     * @return lista de ventas encontradas en el rango de fechas indicado.
     */
    public ILista<IElementoAB<Documento>> auxReporte(IElementoAB nodoVenta, Date fecha_desde, Date fecha_hasta, ILista ventasEncontradas){
        IElementoAB<Documento> hIzq = nodoVenta.getHijoIzq();
        IElementoAB<Documento> hDer = nodoVenta.getHijoDer();
        IElementoAB<Documento> elemVenta = new TElementoAB(nodoVenta.getEtiqueta(), nodoVenta);
        Documento venta = elemVenta.getDatos();
        Date fechaVenta = venta.getFechaVenta();
        if(fechaVenta.compareTo(fecha_desde) >= 0 && fechaVenta.compareTo(fecha_hasta) <= 0){
            ventasEncontradas.insertar(new Nodo(elemVenta.getEtiqueta(), elemVenta.getDatos()));
        }
        if(hIzq != null){
            return auxReporte(hIzq, fecha_desde, fecha_hasta, ventasEncontradas);
        }
        if(hDer != null){
            return auxReporte(hDer, fecha_desde, fecha_hasta, ventasEncontradas);
        }
        
        return ventasEncontradas;
    }
    
    
    
    
}
