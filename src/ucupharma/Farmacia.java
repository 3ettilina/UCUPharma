/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;
import Interfaces.*;

/**
 *
 * @author Bettina
 */
public class Farmacia {
    
    public String nombre;
    static GestorDocumentos documentos;
    static Stock stock;
    
    public Farmacia(String nombre){
        this.nombre = nombre;
        Farmacia.stock = new Stock();
        Farmacia.documentos = new GestorDocumentos();
    }
    
    /**
     * Método para generar una venta.
     * @param clave - Clave del producto a vender.
     * @param cantidad - Cantidad de unidades a vender.
     */
    public void vender(int clave, int cantidad){
        documentos.vender(clave, cantidad);
    }
    
    /**
     * Método que permite devolver cierta cantidad de un producto vendido. Modificando las ventas.
     * @param numDocumento - Número de documento de la venta.
     * @param codigo - Código del producto a buscar.
     * @param cantidad - Cantidad de unidades a devolver.
     */
    public void devolver(int numDocumento, int codigo, int cantidad){
        documentos.devolver(numDocumento, codigo, cantidad);
    }
    
    /**
     * Método que devuelve un reporte de ventas utilizando un rango de fechas.
     * @param fecha_desde - Fecha anterior desde donde se comienza la búsqueda.
     * @param fecha_hasta - Fecha más reciente hasta donde se busca.
     */
    public void reporte(String fecha_desde, String fecha_hasta){
        documentos.reporte(fecha_desde, fecha_hasta);
    }
            
    /**
     * Método que permite aumentar las unidades de un producto en el stock.
     * @param codigo - Código del producto
     * @param cantidad - Cantidad de unidades a aumentar.
     */
    public void altaStock(Comparable codigo, int cantidad){
        stock.altaStock(codigo, cantidad);
    }
    
    /**
     * Método que permite disminuir las unidades de un producto en el stock.
     * @param codigo - Código de producto.
     * @param cantidad - Cantidad de unidades a disminuir.
     */
    public void bajaStock(Comparable codigo, int cantidad){
        stock.altaStock(codigo, cantidad);
    }
    
    /**
     * Método que permite ingresar productos utilizando un archivo.
     * @param rutaArchivo - Ruta absoluta del archivo a utilizar (de tipo .csv)
     * @param modificar - String "Si" o "No" que indica si se van a modificar los datos de los productos existentes en el Stock.
     */
    public void cargarProductos(String rutaArchivo, String modificar){
        stock.cargarProductos(rutaArchivo, modificar);
    }
    
    /**
     * Método que aumenta el stock de uno o varios productos mediante un archivo de tipo csv.
     * @param rutaArchivo - Ruta absoluta de la ubicación del archivo a utilizar.
     */
    public void cargarStock(String rutaArchivo){
        stock.cargarStock(rutaArchivo);
    }
    
    /**
     * Método que busca un producto utilizando un código.
     * @param codigo - código del producto a buscar.
     * @return 
     */
    public IElementoAB<IProducto> buscarPorCodigo(int codigo){
        return stock.buscarPorCodigo(codigo);
    }
    
    /**
     * Método que busca productos por su descripción corta.
     * @param descripcion - Cadena de texto que indica los caracteres a buscar en la descripción corta de los productos.
     * @return lista de productos que contienen esa descripción corta
     */
    public ILista<IProducto> buscarPorDescCorta(String descripcion){
        return stock.buscarPorDescCorta(descripcion);
    }
    
    public ILista<IProducto> buscarPorDescLarga(String descripcion){
        return stock.buscarPorDescCorta(descripcion);
    }
    
    public ILista<ILista<IProducto>> listarAreas(){
        return stock.listarPorArea();
    }
    
    public ILista<IProducto> listarProductos(){
        return stock.listarProductos();
    }
    
    public ILista<IProducto> buscarVencimientos(String año){
        return stock.reporteVencimientos(año);
    }

}
