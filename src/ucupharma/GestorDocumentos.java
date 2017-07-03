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
        IArbolBB<Documento> arbolCompra = new TArbolBB<>();
        INodo<IArbolBB<Documento>> nodoArbolCompra = new Nodo("Compra", arbolCompra);
        listaDocumentos.insertar(nodoArbolCompra);
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
                Documento venta = new Documento(codigo, prodAVender.getDescripcionCorta(), prodAVender.getAreaAplicacion(), cantidad, total, "Venta", 0);
                venta.setPrecioUnitario(prodAVender.getPrecio());
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
    
    public void comprar(int numDocumento, int codigo, int cantidad){
        IElementoAB<IProducto> nodoProd = arbolStock.buscar(codigo);
        if (nodoProd != null){
            IProducto prodAComprar = nodoProd.getDatos();
            if (cantidad >= 0){
                double total = (prodAComprar.getPrecio())*cantidad;
                Documento compra = new Documento(codigo, prodAComprar.getDescripcionCorta(), prodAComprar.getAreaAplicacion(), cantidad, total, "Compra", numDocumento);
                
                IElementoAB<Documento> elemVenta = new TElementoAB(compra.getId(), compra);
                prodAComprar.setCantidad(prodAComprar.getCantidad()+cantidad);
                
                INodo<IArbolBB<Documento>> nodoVenta = listaDocumentos.buscar("Compra");
                nodoVenta.getDato().insertar(elemVenta);
                System.out.println("Compra realizada satisfactoriamente.");
  
            }
            else{
                System.out.println("La cantidad a comprar: " + cantidad + " es menor o igual a cero. Por favor, intentelo nuevamente con la cantidad correcta.");
                
            }
        }
        else{
            System.out.println("El código ingresado no fue encontrado en la base de datos de productos. ¿Desea ingresarlo?");
            
        }
    }
    
    public ILista<Documento> reporte(String fecha_desde, String fecha_hasta){
        Date fDesde = ManejadorFechas.crearFecha(fecha_desde);
        Date fHasta = ManejadorFechas.crearFecha(fecha_hasta);
        INodo<IArbolBB<Documento>> nodoListaDoc = listaDocumentos.buscar("Venta");
        IArbolBB<Documento> arbolVenta = nodoListaDoc.getDato();
        ILista<Documento> lista = new Lista<>();
        
        try{
            if(arbolVenta != null){
                arbolVenta.getRaiz().reporteVentas(fDesde, fHasta, lista);
            }
            else{
                throw new NullNodeException("No existe ninguna venta realizada hasta el momento. Por favor, intentelo más tarde.");
            }
        }
        catch (NullNodeException ex){
            ex.getMessage();
        }
        return lista;
    }
    
    public ILista<Documento> reporteVentasPorProd(String codigo){
        INodo<IArbolBB<Documento>> nodoArbolVentas = listaDocumentos.buscar("Venta");
        IArbolBB<Documento> arbolVentas = nodoArbolVentas.getDato();
        
        ILista<Documento> ventasProd = new Lista<>();
        try{
            if(arbolVentas != null){
                arbolVentas.getRaiz().buscarParametro("codigoProducto", codigo, ventasProd);
            }
            else{
                throw new NullNodeException("No existen ventas de ningún producto registradas hasta el momento.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        
        return ventasProd;
    }
    
    public ILista<Documento> movimientosPorArea(String area){
        IArbolBB<Documento> ventas = listaDocumentos.buscar("Venta").getDato();
        IElementoAB nodoVenta = ventas.getRaiz();
        IArbolBB<Documento> compras = listaDocumentos.buscar("Compra").getDato();
        IElementoAB nodoCompra = compras.getRaiz();
        
        ILista<Documento> listaDocsArea = new Lista<>();
        
        try{
            if(nodoVenta != null){
                nodoVenta.buscarParametro("areaAplicacion", area, listaDocsArea);
            }
            if(nodoCompra != null){
               nodoCompra.buscarParametro("areaAplicacion", area, listaDocsArea);

            }
            if((nodoVenta == null) && (nodoCompra == null)){
                throw new NullNodeException("No existen compras o ventas en la base de datos.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        
        return listaDocsArea;
    }
    
    public Double promedioVentas(int codigo){
        IArbolBB<Documento> arbolVenta = listaDocumentos.buscar("Venta").getDato();
        IElementoAB<Documento> nodoVenta = arbolVenta.getRaiz();
        ILista<String> meses = new Lista<>();
        int cantMeses = meses.cantElementos();
        double total = 0.0;
        double promedio = 0.0;
        try{
            if(nodoVenta != null){
                return auxPromedioVentas(codigo, nodoVenta, total, cantMeses, meses, promedio);
            }
            else{
                throw new NullNodeException("No hay ventas realizadas.");
            }
        }
        catch(NullNodeException ex){
            ex.getMessage();
        }
        return promedio;
    }
    
    public Double auxPromedioVentas(int codigo, IElementoAB<Documento> nodo, double total, int cantMeses, ILista<String> meses, double promedio){
        IElementoAB hIzq = nodo.getHijoIzq();
        IElementoAB hDer = nodo.getHijoDer();
        
        Documento docActual = nodo.getDatos();
        int codProdVendido = docActual.getCodigoProd();
        
        if(codProdVendido == codigo){
            String mes = ManejadorFechas.obtenerMes(docActual.getFechaDocumento());
            INodo<String> mesObtenido = meses.buscar(mes);
            double totalActual = docActual.getTotal();
            if(mesObtenido != null){
                total += totalActual;
                promedio = total/cantMeses;
            }
            else{
                meses.insertar(new Nodo(mes, null));
                cantMeses += 1;
                total += totalActual;
                promedio = total/cantMeses;
            }
            
        }
        if(hIzq != null){
            return auxPromedioVentas(codigo, hIzq, total, cantMeses, meses, promedio);
        }
        if(hDer != null){
            return auxPromedioVentas(codigo, hDer, total, cantMeses, meses, promedio);
        }
        
        return promedio;
    }
    
}
