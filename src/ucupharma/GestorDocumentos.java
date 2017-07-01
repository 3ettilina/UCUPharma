/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.*;
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
     * @param clave - Clave del producto a vender.
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
            if (prodAComprar.getCantidad() >= cantidad){
                double total = (prodAComprar.getPrecio())*cantidad;
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
     * Método que devuelve un reporte de ventas utilizando un rango de fechas.
     * @param fecha_desde - Fecha anterior desde donde se comienza la búsqueda.
     * @param fecha_hasta - Fecha más reciente hasta donde se busca.
     */
    public void reporte(String fecha_desde, String fecha_hasta){
        Date fechaDesde = ManejadorFechas.obtenerFecha();
        Date fechaHasta = ManejadorFechas.obtenerFecha();
        
        int cantidadTotal = 0;
        double precioTotal = 0.0;
        
        INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar(fechaDesde);
        if (nodoVenta != null){
            while (nodoVenta.getSiguiente() != null){
                Documento venta = nodoVenta.getDatos();
                if (venta.getFechaVenta().after(fechaDesde) && venta.getFechaVenta().before(fechaHasta)){
                    cantidadTotal += venta.cantidad;
                    precioTotal += venta.total;
                    System.out.println(venta);
                }
                nodoVenta = nodoVenta.getSiguiente();
            }
            
            System.out.println("Entre " + fechaDesde + " y " + fechaHasta + " se han vendido: ");
            System.out.println("Cantidad de productos: " + cantidadTotal);
            System.out.println("Valor total: " + precioTotal);
        }
        else{
            System.out.println("No se han encontrado ventas en las fechas indicadas.");
        }
    }
}
