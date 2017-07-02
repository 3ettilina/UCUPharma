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
                Documento venta = new Documento(codigo, prodAVender.getDescripcionCorta(), prodAVender.getAreaAplicacion(), cantidad, total, 1, 0);
                
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
                Documento compra = new Documento(codigo, prodAComprar.getDescripcionCorta(), prodAComprar.getAreaAplicacion(), cantidad, total, 1, 0);
                
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
                throw new NullNodeException("No existe ninguna venta realizada hasta el momento. Por favor, intentelo más tarde.");
            }
        }
        catch (NullNodeException ex){
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
        Date fechaVenta = venta.getFechaDocumento();
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
    
    public ILista<IElementoAB<Documento>> reporteVentasPorProd(int codigo){
        INodo<IArbolBB<Documento>> nodoArbolVentas = listaDocumentos.buscar("Venta");
        IArbolBB<Documento> arbolVentas = nodoArbolVentas.getDato();
        IElementoAB<Documento> elemVenta = arbolVentas.getRaiz();
        ILista<IElementoAB<Documento>> ventasProd = new Lista<>();
        try{
            if(elemVenta != null){
                return auxReporteVentasPorProd(codigo, elemVenta, ventasProd);
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
    
    public ILista<IElementoAB<Documento>> auxReporteVentasPorProd(int codigo, IElementoAB elemVenta, ILista ventas){
        IElementoAB<Documento> hIzq = elemVenta.getHijoIzq();
        IElementoAB<Documento> hDer = elemVenta.getHijoDer();
        IElementoAB<Documento> nodoActual = new TElementoAB(elemVenta.getEtiqueta(), elemVenta.getDatos());
        Documento venta = nodoActual.getDatos();
        int prodEnVenta = venta.getCodigoProd();
        
        if(prodEnVenta == codigo){
            ventas.insertar(new Nodo(nodoActual.getEtiqueta(), nodoActual.getDatos()));
        }
        if(hIzq != null){
            return auxReporteVentasPorProd(codigo, hIzq, ventas);
        }
        if(hDer != null){
            return auxReporteVentasPorProd(codigo, hDer, ventas);
        }
        return ventas;
    }
    
    public ILista<IElementoAB<Documento>> movimientosPorArea(String area){
        IArbolBB<Documento> ventas = listaDocumentos.buscar("Venta").getDato();
        IElementoAB nodoVenta = ventas.getRaiz();
        IArbolBB<Documento> compras = listaDocumentos.buscar("Compra").getDato();
        IElementoAB nodoCompra = compras.getRaiz();
        
        ILista<IElementoAB<Documento>> listaDocsArea = new Lista<>();
        
        try{
            if(nodoVenta != null){
                return auxMovimientosPorArea(area, nodoVenta, listaDocsArea);
            }
            if(nodoCompra != null){
               return auxMovimientosPorArea(area, nodoCompra, listaDocsArea);

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
    
    public ILista<IElementoAB<Documento>> auxMovimientosPorArea(String areaAp, IElementoAB<Documento> nodo, ILista<IElementoAB<Documento>> listaDocsArea){    
        
        Documento doc = nodo.getDatos();
        IElementoAB<Documento> hIzq = nodo.getHijoIzq();
        IElementoAB<Documento> hDer = nodo.getHijoDer();
        if(doc.areaAplicacion.equals(areaAp)){
            listaDocsArea.insertarOrdenado(new Nodo(doc.fechaDocumento, doc));
        }
        if(hIzq != null){
            return auxMovimientosPorArea(areaAp, nodo, listaDocsArea);
        }
        if(hDer != null){
            return auxMovimientosPorArea(areaAp, nodo, listaDocsArea);
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
