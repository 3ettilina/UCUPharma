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
 * @author Bettina
 */
public class Stock {
    
    public IArbolBB<IProducto> arbolStock = new TArbolBB();
    
    public Stock(){
    }
    
    /**
     * Método ingresa cantidades a los productos existentes.
     * @param codigo - Código del producto a buscar.
     * @param stock  - Cantidad de items a ingresar.
     */
    public void altaStock(Comparable codigo, int stock){
        try {
            IElementoAB<IProducto> prod = arbolStock.buscar(codigo);
            if (prod != null){
                IProducto prodEncontrado = prod.getDatos();
                int cActual = prodEncontrado.getCantidad();
                prodEncontrado.setCantidad(cActual + stock);
                System.out.println("Se ha(n) agregado exitosamente " + stock + "item(s) al producto " + prodEncontrado.getDescripcionCorta());
            }
            else{
                throw new IdNotFoundException("El código ingresado no se encuentra en la lista de productos. Por favor, verifíquelo.");
            }
            
        }
        catch (IdNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    /**
     * Método que quita unidades a los productos.
     * @param codigo - Código de producto a buscar.
     * @param stock - Cantidad de unidades a quitar.
     */
    public void bajaStock(Comparable codigo, int stock){
        try {
            IElementoAB<IProducto> prod = arbolStock.buscar(codigo);
            if (prod != null){
                IProducto prodEncontrado = prod.getDatos();
                int cActual = prodEncontrado.getCantidad();
                prodEncontrado.setCantidad(cActual - stock);
                System.out.println("Se ha(n) quitado exitosamente " + stock + "item(s) del producto " + prodEncontrado.getDescripcionCorta());
            }
            else{
                throw new IdNotFoundException("El código ingresado no se encuentra en la lista de productos. Por favor, verifíquelo.");
            }
            
        }
        catch (IdNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    /**
     * Método que da de alta productos al stock mediante un archivo de tipop csv.
     * @param rutaArchivo - Ruta absoluta del archivo a utilizar.
     * @param modificar  - Cadena de texto "Si" o "No" que indica si se modificarán los datos de los productos existentes o no.
     * A excepción de las cantidades.
     */
    public void cargarProductos(String rutaArchivo, String modificar){
        
        if("SI".equals(modificar.toUpperCase()) || "NO".equals(modificar.toUpperCase())){
            String[] productosLeidos = ManejadorArchivos.leerArchivo(rutaArchivo);
            for (int i = 1; i < productosLeidos.length; i++) {
                String[] unProducto = productosLeidos[i].split(";");
                int codigoProducto = Integer.parseInt(unProducto[0]);
                IElementoAB<IProducto> nodoProductoEncontrado = arbolStock.buscar(codigoProducto);
                if (nodoProductoEncontrado != null){
                    IProducto prodEncontrado = nodoProductoEncontrado.getDatos();
                    if ("SI".equals(modificar.toUpperCase())){
                        prodEncontrado.setUltimaActualizacion(ManejadorFechas.obtenerFecha());
                        prodEncontrado.setPrecio(Double.parseDouble(unProducto[3]));
                        prodEncontrado.setDescCorta(unProducto[4]);
                        prodEncontrado.setDescLarga(unProducto[5]);
                        prodEncontrado.setEstado(unProducto[6]);
                        prodEncontrado.setRefrigerado(Boolean.parseBoolean(unProducto[7]));
                        prodEncontrado.setRequiereReceta(Boolean.parseBoolean(unProducto[8]));
                        prodEncontrado.setVencimiento(Integer.parseInt(unProducto[9]));
                    }
                }
                else{
                    int codigo = Integer.parseInt(unProducto[0]);
                    Date fecha_creacion = ManejadorFechas.crearFecha(unProducto[1]);
                    Date ult_actualizacion = ManejadorFechas.crearFecha(unProducto[2]);
                    double precio = Double.parseDouble(unProducto[3]);
                    String descCorta = unProducto[4];
                    String descLarga = unProducto[5];
                    String estado = unProducto[6];
                    boolean refrigerado = Boolean.parseBoolean(unProducto[7]);
                    boolean receta = Boolean.parseBoolean(unProducto[8]);
                    int vencimiento = Integer.parseInt(unProducto[9]);
                    String areaAplicacion = unProducto[10];
                    int cantidad = 0;
                    
                    IProducto productoNuevo = new Producto(codigo, fecha_creacion, ult_actualizacion, precio, descCorta, descLarga, estado, refrigerado, receta, vencimiento, areaAplicacion, cantidad);
                    IElementoAB<IProducto> productoAIngresar = new TElementoAB(codigo, productoNuevo);
                    arbolStock.insertar(productoAIngresar);
                }
            }
        }
        else{
            System.out.println("No ha especificado si desea ('SI') o no ('NO') modificar los productos existentes. Por favor, verifique");

        }
    }
    
    /**
     * Método que aumenta el stock de uno o varios productos mediante un archivo de tipo csv.
     * @param rutaArchivo - Ruta absoluta de la ubicación del archivo a utilizar.
     */
    public void cargarStock(String rutaArchivo){
        try {
            String[] productosLeidos = ManejadorArchivos.leerArchivo(rutaArchivo);
            for (int i = 1; i < productosLeidos.length; i++) {
                String[] unProducto = productosLeidos[i].split(";");
                Comparable codigo = unProducto[0];
                int cant = Integer.parseInt(unProducto[1]);
                IElementoAB<IProducto> nodoProductoEncontrado = arbolStock.buscar(codigo);
                if(nodoProductoEncontrado != null){
                    IProducto prodEncontrado = nodoProductoEncontrado.getDatos();
                    prodEncontrado.setCantidad(prodEncontrado.getCantidad() + cant);
                    prodEncontrado.setUltimaActualizacion(ManejadorFechas.obtenerFecha());
                }
                else{
                    throw new IdNotFoundException("El producto: " + codigo + " no existe en el stock. Por favor, verifique.");
                }
            }
        } catch (IdNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    /**
     * Método que busca un producto utilizando un código.
     * @param clave - código del producto a buscar.
     * @return - Devuelve un producto encontrado.
     */
    public IElementoAB<IProducto> buscarPorCodigo(Comparable clave){
        return arbolStock.buscar(clave);
    }
    
    /**
     * Método que busca productos por su descripción corta.
     * @param descripcion - Cadena de texto que indica los caracteres a buscar en la descripción corta de los productos.
     */
    public void buscarPorDescripcionCorta(String descripcion){
        int productosEncontrados = 0;
        if (arbolStock == null){
            System.out.println("No existen productos en el Stock. Por favor, verifique.");
        }
        else{
            IElementoAB<IProducto> nodoPuntero = arbolStock;
            while (nodoPuntero != null){
                IProducto producto = nodoPuntero.getDatos();
                if (producto.getDescripcionCorta().contains(descripcion)){
                    System.out.println(producto);
                    productosEncontrados += 1;
                }
                
                nodoPuntero = nodoPuntero.getSiguiente();
            }
            
            if (productosEncontrados == 0){
                System.out.println("No se encontraron productos que contengan esa descripción corta.");
            }
            
        }
    }
    
    /**
     * Método que busca productos utilizando la descripción larga.
     * @param descripcion - Cadena de caracteres a buscar.
     */
    public void buscarPorDescripcionLarga(String descripcion){
        int productosEncontrados = 0;
        if (listaStock.esVacia()){
            System.out.println("No existen productos en el Stock. Por favor, verifique.");
        }
        else{
            INodo<IProducto> nodoPuntero = listaStock.getPrimero();
            while (nodoPuntero != null){
                IProducto producto = nodoPuntero.getDato();
                if (producto.getDescripcionLarga().contains(descripcion)){
                    System.out.println(producto);
                    productosEncontrados += 1;
                }
                
                nodoPuntero = nodoPuntero.getSiguiente();
            }
            
            if (productosEncontrados == 0){
                System.out.println("No se encontraron productos que contengan esa descripción larga.");
            }
            
        }
    }
    
    /**
     * Metodo que permite listar todos los productos en stock.
     */
    public void listarProductos(){
        INodo<IProducto> nodoPuntero = listaStock.getPrimero();
        if (listaStock.esVacia()){
            System.out.println("No existen productos en el stock.");
        }
        while (nodoPuntero != null){
            IProducto producto = nodoPuntero.getDato();
            System.out.println(producto);
            System.out.println("\n");
            nodoPuntero = nodoPuntero.getSiguiente();
        }
    }
    
}
