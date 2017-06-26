/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;
import java.util.*;
import Interfaces.IDocumento;

/**
 *
 * @author Bettina
 */
public class Documento implements IDocumento{
    
    public Date fechaVenta;
    public Date fechaModificacion;
    public static int ultimoId = 1;
    public Comparable id;
    public int codigoProducto;
    public int cantidad;
    public double total;
    
    public Documento(){
    }
    
    /**
     * Metodo constructor de un objeto Venta
     * @param codProducto
     * @param cantidad
     * @param total 
     */
    public Documento(int codProducto, int cantidad, double total){
        this.fechaVenta = ManejadorFechas.obtenerFecha();
        this.fechaModificacion = ManejadorFechas.obtenerFecha();
        this.id = Documento.ultimoId;
        Documento.ultimoId += 1;
        this.codigoProducto = codProducto;
        this.cantidad = cantidad;
        this.total = total;
        
    }
    
    @Override
    public String toString(){
        String imprimir = "Id venta: " + this.id + ". Codigo Producto: "+ this.codigoProducto + ". Cantidad: " + this.cantidad + ". Total: " + this.total;
        imprimir += ". Fecha venta: " + this.fechaVenta + "Fecha Modificación: " + this.fechaModificacion;
        return imprimir;
    }
    
    @Override
    public int getCodigoProd(){
        return this.codigoProducto;
    }
    
    @Override
    public double getTotal(){
        return this.total;
    }
    
    @Override
    public int getCantidad(){
        return this.cantidad;
    }
    
    @Override
    public Date getFechaVenta(){
        return this.fechaVenta;
    }
    
    @Override
    public Date getFechaModificacion(){
        return this.fechaModificacion;
    }
    
    @Override
    public Comparable getId(){
        return this.id;
    }
    
    @Override
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    
    @Override
    public void setFechaModificacion(Date fecha){
        this.fechaModificacion = fecha;
    }
    
    @Override
    public void setTotal(double total){
        this.total = total;
    }
    
    
}
