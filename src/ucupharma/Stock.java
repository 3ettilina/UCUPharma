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
     * Método que busca productos por su descripción corta o larga.
     * @param descripcion - Cadena de texto que indica los caracteres a buscar en la descripción corta de los productos.
     * @param descCorta - Indica si el usuario quiere buscar por descripcion corta o larga
     * @return Elementos encontrados
     */
    public ILista<IElementoAB<IProducto>> buscarPorDesc(String descripcion, Boolean descCorta){
        ILista<IElementoAB<IProducto>> listEncontrados = new Lista();
        try {
            IElementoAB<IProducto> raiz = arbolStock.getRaiz();
            
            if (raiz == null){
                throw new NullNodeException("No existen productos en el Stock. Por favor, verifique.");
            }
            else{
                return auxBuscarPorDesc(raiz, descripcion, descCorta, listEncontrados);
            }
            
        } catch (NullNodeException e) {
            e.getMessage();
        }
        return listEncontrados;
    }
    
    public ILista<IElementoAB<IProducto>> auxBuscarPorDesc(IElementoAB<IProducto> elemProd, String desc, Boolean tDescCorta, ILista prodEncontrados){
        IElementoAB<IProducto> hIzq = elemProd.getHijoIzq();
        IElementoAB<IProducto> hDer = elemProd.getHijoDer();
        if(tDescCorta){
            IProducto prod = elemProd.getDatos();
            if(desc.contains(prod.getDescripcionCorta())){
                prodEncontrados.insertarOrdenado(new Nodo(elemProd.getEtiqueta(), elemProd.getDatos()));
            }
            if(hIzq != null){
                return auxBuscarPorDesc(hIzq, desc, tDescCorta, prodEncontrados);
            }
            if(hDer != null){
                return auxBuscarPorDesc(hDer, desc, tDescCorta, prodEncontrados);
            }
        }
        if (! tDescCorta){
            IProducto prod = elemProd.getDatos();
            if(desc.contains(prod.getDescripcionLarga())){
                prodEncontrados.insertarOrdenado(new Nodo(elemProd.getEtiqueta(), elemProd.getDatos()));
            }
            if(hIzq != null){
                return auxBuscarPorDesc(hIzq, desc, tDescCorta, prodEncontrados);
            }
            if(hDer != null){
                return auxBuscarPorDesc(hDer, desc, tDescCorta, prodEncontrados);
            }
        }
        return prodEncontrados;
    }
    
    
    /**
     * Metodo que permite listar todos los productos en stock.
     */
    public ILista<IProducto> listarProductos(){
        IElementoAB<IProducto> raiz = arbolStock.getRaiz();
        ILista<IProducto> productos = new Lista<>();
        
        if (raiz == null){
            System.out.println("No existen productos en el stock.");
        }
        else{
            auxListarProductos(raiz, productos);
        }
        return productos;
    }
    
    public ILista<IProducto> auxListarProductos(IElementoAB<IProducto> nodo, ILista<IProducto> prods){
        
        IElementoAB<IProducto> hIzq = nodo.getHijoIzq();
        IElementoAB<IProducto> hDer = nodo.getHijoDer();
        
        INodo<IProducto> prodAInsertar = new Nodo(nodo.getEtiqueta(), nodo.getDatos());
        prods.insertar(prodAInsertar);
        if (hIzq != null){
            return auxListarProductos(hIzq, prods);
        }
        if (hDer != null){
            return auxListarProductos(hDer, prods);
        }
        return prods;
    }
    
    public ILista<IArbolBB<IProducto>> listarPorArea(){
        IElementoAB<IProducto> elemProd = arbolStock.getRaiz();
        ILista<IArbolBB<IProducto>> listaAreas = new Lista<>();
        
        try{
            if(elemProd != null){
                auxListarPorArea(elemProd, listaAreas);
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
    
    public ILista<IArbolBB<IProducto>> auxListarPorArea(IElementoAB<IProducto> nodoAct, ILista listaAreas){
        IElementoAB<IProducto> hIzq = nodoAct.getHijoIzq();
        IElementoAB<IProducto> hDer = nodoAct.getHijoDer();
        
        IProducto prod = nodoAct.getDatos();
        String areaProd = prod.getAreaAplicacion();
        
        INodo<IArbolBB<IProducto>> area = listaAreas.buscar(areaProd);
        
        if(area != null){
            IArbolBB<IProducto> arbolArea = area.getDato();
            arbolArea.insertar(nodoAct);
        }
        else{
            IArbolBB<IProducto> arbolArea = new TArbolBB();
            arbolArea.insertar(nodoAct);
            listaAreas.insertar(new Nodo(areaProd, arbolArea));
        }
        
        if(hIzq != null){
            return auxListarPorArea(hIzq,listaAreas);
        }
        
        if(hDer != null){
            return auxListarPorArea(hDer,listaAreas);
        }
        
        
        return listaAreas;
    }
    
    
}
