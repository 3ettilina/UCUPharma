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
    
    
    public String nuevoProd(int codigo, String descCorta, String descLarga, int cantidad, double precio, String estado, Boolean refri, Boolean receta, int vencimiento, String areaApl){
        return stock.nuevoProducto(codigo, descCorta, descLarga, cantidad, precio, estado, refri, receta, vencimiento, areaApl);
    }
    /**
     * Método para generar una venta.
     * @param clave - Clave del producto a vender.
     * @param cantidad - Cantidad de unidades a vender.
     */
    public String vender(int clave, int cantidad){
        return documentos.vender(clave, cantidad);
    }
    
    public String comprar(int numDoc, int clave, int cantidad){
        return documentos.comprar(numDoc, clave, cantidad);
    }
    
    /**
     * Método que permite devolver cierta cantidad de un producto vendido. Modificando las ventas.
     * @param numDocumento - Número de documento de la venta.
     * @param codigo - Código del producto a buscar.
     * @param cantidad - Cantidad de unidades a devolver.
     */
    public String devolver(int numDocumento, int codigo, int cantidad){
        return documentos.devolver(numDocumento, codigo, cantidad);
    }
    
    /**
     * Método que devuelve un reporte de ventas utilizando un rango de fechas.
     * @param fecha_desde - Fecha anterior desde donde se comienza la búsqueda.
     * @param fecha_hasta - Fecha más reciente hasta donde se busca.
     * @return 
     */
    public ILista<Documento> reporte(String fecha_desde, String fecha_hasta){
        return documentos.reporte(fecha_desde, fecha_hasta);
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
    
    public String eliminarProd(int codigo){
        return stock.eliminarProducto(codigo);
    }
    
    /**
     * Método que elimina un área de aplicación. Impactando sobre la estructura de productos.
     * @param area
     * @return 
     */
    public String eliminarArea(String area){
        return stock.eliminarArea(area);
    }
    
    /**
     * Método que devuelve el promedio de ventas mensuales de un producto
     * @param codigo
     * @return 
     */
    public Object[] promedioVentas(int codigo){
        return documentos.promedioVentas(codigo);
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
    
    public ILista<Documento> ventasPorProd(String codigo){
        return documentos.reporteVentasPorProd(codigo);
    }
    
    public ILista<Documento> movsPorArea(String area){
        return documentos.movimientosPorArea(area);
    }
    

}
