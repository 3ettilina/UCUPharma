/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;
import Interfaces.*;
import Exceptions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * 
 * @author Bettina
 */
public class Stock {
    
    public IArbolBB<IProducto> arbolStock;
    
    public Stock(){
        this.arbolStock = new TArbolBB();
    }
    
    public IArbolBB<IProducto> getArbolProductos(){
        return arbolStock;
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
    
    public void eliminarProducto(Comparable codigo){
        arbolStock.eliminar(codigo);
    }
    
    /**
     * Método que da de alta productos al stock mediante un archivo de tipop csv.
     * @param rutaArchivo - Ruta absoluta del archivo a utilizar.
     * @param modificar  - Cadena de texto "Si" o "No" que indica si se modificarán los datos de los productos existentes o no.
     * A excepción de las cantidades.
     */
    public void cargarProductos(String rutaArchivo, String modificar){
        try{
            ArrayList<String> productosLeidos = ManejadorArchivos.leerArchivo(rutaArchivo);
            productosLeidos.remove(0);
            Collections.shuffle(productosLeidos);
            for (int i = 0; i < productosLeidos.size(); i++) {
                String[] unProducto = productosLeidos.get(i).split(";");
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
                        prodEncontrado.setAreaAplicacion(unProducto[10]);
                        prodEncontrado.setCantidad(0);
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
        catch(Exception ex){
            ex.getMessage();
        }
        
    }
    
    /**
     * Método que aumenta el stock de uno o varios productos mediante un archivo de tipo csv.
     * @param rutaArchivo - Ruta absoluta de la ubicación del archivo a utilizar.
     */
    public void cargarStock(String rutaArchivo){
        try {
            ArrayList<String> productosLeidos = ManejadorArchivos.leerArchivo(rutaArchivo);
            productosLeidos.remove(0);
            for (int i = 0; i < productosLeidos.size(); i++) {
                String[] unProducto = productosLeidos.get(i).split(";");
                int codigo = Integer.parseInt(unProducto[0]);
                int cant = Integer.parseInt(unProducto[1]);
                IElementoAB<IProducto> nodoProductoEncontrado = arbolStock.buscar(codigo);
                if(nodoProductoEncontrado != null){
                    nodoProductoEncontrado.getDatos().setCantidad(nodoProductoEncontrado.getDatos().getCantidad() + cant);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    /**
     * Método que busca un producto utilizando un código.
     * @param clave - código del producto a buscar.
     * @return - Devuelve un producto encontrado.
     */
    public IElementoAB<IProducto> buscarPorCodigo(int clave){
        return arbolStock.buscar(clave);
    }
    
    /**
     * Método que busca productos por su descripción corta o larga.
     * @param descripcion - Cadena de texto que indica los caracteres a buscar en la descripción corta de los productos.
     * @return Elementos encontrados
     */
    public ILista<IProducto> buscarPorDescCorta(String descripcion){
        ILista<IProducto> listEncontrados = new Lista();
        try {
            IElementoAB<IProducto> raiz = arbolStock.getRaiz();
            
            if (raiz == null){
                throw new NullNodeException("No existen productos en el Stock. Por favor, verifique.");
            }
            else{
                raiz.buscarParametro("descripcion_corta", descripcion, listEncontrados);
            }
        } catch (NullNodeException e) {
            e.getMessage();
        }
        return listEncontrados;
    }
    
    public ILista<IProducto> buscarPorDescLarga(String descripcion){
        ILista<IProducto> listEncontrados = new Lista();
        try {
            IElementoAB<IProducto> raiz = arbolStock.getRaiz();
            
            if (raiz == null){
                throw new NullNodeException("No existen productos en el Stock. Por favor, verifique.");
            }
            else{
                raiz.buscarParametro("descripcion_larga", descripcion, listEncontrados);
            }
        } catch (NullNodeException e) {
            e.getMessage();
        }
        return listEncontrados;
    }
    
    /**
     * Metodo que permite listar todos los productos en stock.
     * @return 
     */
    public ILista<IProducto> listarProductos(){
        IElementoAB<IProducto> raiz = arbolStock.getRaiz();
        ILista<IProducto> productos = new Lista<>();
        
        if (raiz == null){
            System.out.println("No existen productos en el stock.");
        }
        else{
            raiz.listarTodos(productos);
        }
        return productos;
    }
    
    public ILista<ILista<IProducto>> listarPorArea(){
        IElementoAB<IProducto> raiz = arbolStock.getRaiz();
        ILista<ILista<IProducto>> listaAreas = new Lista<>();
        
        try{
            if(raiz != null){
                raiz.listarAreas(listaAreas);
            }
            else{
                throw new NullNodeException("Por alguna razón no hay artículos en Stock. Por favor, realice una carga de stock.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        return listaAreas;
    }
    
    public ILista<IProducto> reporteVencimientos(String año){
        ILista<IProducto> prodsAVencer = new Lista<>();
        IElementoAB<IProducto> nodo = arbolStock.getRaiz();
        try{
            if(nodo != null){
                nodo.buscarParametro("vencimiento", año, prodsAVencer);
            }
            else{
                throw new NullNodeException("Por alguna razón no existen productos en Stock. Por favor, carga un archivo e intentalo nuevamente.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        return prodsAVencer;
    }
    
    public void eliminarArea(String area){
        IElementoAB<IProducto> nodoAct = arbolStock.getRaiz();
        try{
            if(nodoAct != null){
            auxEliminarArea(area, nodoAct);
            }
            else{
                throw new NullNodeException("Por alguna razón no existen productos en Stock. Intenta cargar un archivo primero.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        
    }
    
    public void auxEliminarArea(String area, IElementoAB<IProducto> nodo){
        IElementoAB<IProducto> hIzq = nodo.getHijoIzq();
        IElementoAB<IProducto> hDer = nodo.getHijoDer();
        IProducto prodAct = nodo.getDatos();
        int codigoAct = prodAct.getCodigo();
        String areaAct = prodAct.getAreaAplicacion();
        if(areaAct.equals(area)){
            nodo.eliminar(codigoAct);
        }
        if(hIzq != null){
            auxEliminarArea(area, hIzq);
        }
        if(hDer != null){
            auxEliminarArea(area, hDer);
        }
    }
    
    
    
    
}
