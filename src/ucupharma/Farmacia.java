/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;
import Interfaces.IProducto;
import Interfaces.INodo;
import Interfaces.ILista;
import java.util.*;

/**
 *
 * @author Bettina
 */
public class Farmacia {
    
    public String nombre;
    public ILista<Documento> listaVentas;
    public Stock stock = new Stock();
    
    public Farmacia(String nombre){
        this.nombre = nombre;
        this.listaVentas = new Lista<>();
    }
    
    /**
     * Método para generar una venta.
     * @param clave - Clave del producto a vender.
     * @param cantidad - Cantidad de unidades a vender.
     */
    public void vender(int clave, int cantidad){
        ILista<IProducto> prodStock = stock.listaStock;
        INodo<IProducto> nodoProductoEncontrado = prodStock.buscar(clave);
        if (nodoProductoEncontrado != null){
            IProducto prodAVender = nodoProductoEncontrado.getDato();
            if (prodAVender.getCantidad() >= cantidad){
                double total = (prodAVender.getPrecio())*cantidad;
                Documento venta = new Documento(clave, cantidad, total);
                INodo<Documento> nodoVenta = new Nodo(ManejadorFechas.obtenerFecha(), venta);
                prodAVender.setCantidad(prodAVender.getCantidad()-cantidad);
                listaVentas.insertarOrdenado(nodoVenta);
                System.out.println("Venta realizada satisfactoriamente.");
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
        INodo<Documento> nodoProductoEncontrado = listaVentas.buscar(numDocumento); //Se busca la fecha en los comparables de los Nodos<ItemVenta>
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
        
        INodo<Documento> nodoVenta = listaVentas.buscar(fechaDesde);
        if (nodoVenta != null){
            while (nodoVenta.getSiguiente() != null){
                Documento venta = nodoVenta.getDato();
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
    public INodo<IProducto> buscarPorCodigo(int codigo){
        return stock.buscarPorCodigo(codigo);
    }
    
    /**
     * Método que busca productos por su descripción corta.
     * @param descripcion - Cadena de texto que indica los caracteres a buscar en la descripción corta de los productos.
     */
    public void buscarPorDescripcionCorta(String descripcion){
        stock.buscarPorDescripcionCorta(descripcion);
    }
    
    /**
     * Método que busca productos utilizando la descripción larga.
     * @param descripcion - Cadena de caracteres a buscar.
     */
    public void buscarPorDescripcionLarga(String descripcion){
        stock.buscarPorDescripcionLarga(descripcion);
    }
    
    public void listarProductos(){
        stock.listarProductos();
    }

}
