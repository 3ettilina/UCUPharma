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
    }
    
    
    /**
     * Método para generar una venta.
     * @param clave - Clave del producto a vender.
     * @param cantidad - Cantidad de unidades a vender.
     */
    public void vender(int clave, int cantidad){
        IElementoAB<IProducto> nodoProd = arbolStock.buscar(clave);
        if (nodoProd != null){
            IProducto prodAVender = nodoProd.getDatos();
            if (prodAVender.getCantidad() >= cantidad){
                double total = (prodAVender.getPrecio())*cantidad;
                Documento venta = new Documento(clave, cantidad, total);
                IElementoAB<Documento> elemVenta = new TElementoAB(ManejadorFechas.obtenerFecha(), venta);
                prodAVender.setCantidad(prodAVender.getCantidad()-cantidad);
                
                if(!listaDocumentos.esVacia()){
                    INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar("Venta");
                    if(nodoVenta != null){
                        nodoVenta.getDato().insertar(elemVenta);
                        System.out.println("Venta realizada satisfactoriamente. El id de su compra es: " + venta.id + ". Lo precisará si desea realizar una devolución.");
                    }
                }
                else{
                    IArbolBB<Documento> arbolVenta = new TArbolBB<>();
                    arbolVenta.insertar(elemVenta);
                    INodo<IArbolBB<Documento>> nodoArbolVenta = new Nodo("Venta", arbolVenta);
                    listaDocumentos.insertar(nodoArbolVenta);
                }
                
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
        IElementoAB<IProducto> nodoProd = arbolStock.buscar(codigo);
        INodo<Documento> nodoProductoEncontrado = listaDocumentos.buscar(numDocumento); //Se busca la fecha en los comparables de los Nodos<ItemVenta>
        if(nodoProductoEncontrado != null){ //Si se encuentra un Nodo que contenga esa fecha como comparable
            Documento ventaEncontrada = nodoProductoEncontrado.getDato(); //Se guarda en una variable el ItemVenta que contiene ese Nodo
            if (codigo == ventaEncontrada.getCodigoProd()){ //Si el código ingresado por parámetro es igual al código del producto encontrado en esa venta.
                if (ventaEncontrada.getCantidad() > cantidad){
                    ventaEncontrada.setCantidad(ventaEncontrada.getCantidad() - cantidad); //Se modifica la cantidad de items vendidos.
                    ventaEncontrada.setFechaModificacion(ManejadorFechas.obtenerFecha());
                
                    INodo<IProducto> nodoProductoEnStock = stock.listaStock.buscar(codigo); //Se busca ese producto (mediante el código ingresado) en la lista de stock de la farmacia.
                    IProducto productoEnStock = nodoProductoEnStock.getDato(); //Se accede al Item dentro del Nodo encontrado.
                    productoEnStock.setCantidad(productoEnStock.getCantidad() + cantidad); //Se modifica la cantidad de stock de ese Item en la farmacia.
                    productoEnStock.setUltimaActualizacion(ManejadorFechas.obtenerFecha());
                    
                }
                else{
                    System.out.println("La cantidad a devolver (" + cantidad + ") es mayor a la cantidad de items vendidos (" + ventaEncontrada.getCantidad() + "). Por favor, verifique.");
                   
                }
                
            }
            else{
                System.out.println("No se encontró el código de producto ingresado. Por favor, verifique.");
               
            }
        }
        else{
            System.out.println("No se encontró venta realizada con el número de documento." + "\nPor favor, verifique.");
            
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
