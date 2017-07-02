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
    
    public Date fechaDocumento;
    public Date fechaModificacion;
    public int tipoDocumento;
    public static int ultimoId = 1;
    public Comparable id;
    public int codigoProducto;
    public String descripcionCorta;
    public String areaAplicacion;
    public int cantidad;
    public double total;
    
    public Documento(){
    }
    
    /**
     * Metodo constructor de un objeto Venta
     * @param codProducto
     * @param descCorta
     * @param areaAp
     * @param cantidad
     * @param total 
     * @param tipo_documento 
     * @param nroDoc 
     */
    public Documento(int codProducto, String descCorta, String areaAp, int cantidad, double total, int tipo_documento, int nroDoc){
        this.fechaDocumento = ManejadorFechas.obtenerFecha();
        this.fechaModificacion = ManejadorFechas.obtenerFecha();
        this.tipoDocumento = tipo_documento;
        setNroDocumento(nroDoc);
        this.codigoProducto = codProducto;
        this.descripcionCorta = descCorta;
        this.areaAplicacion = areaAp;
        this.cantidad = cantidad;
        this.total = total;
        
    }
    
    @Override
    public String toString(){
        String imprimir = "Id venta: " + this.id + ". Codigo Producto: "+ this.codigoProducto + ". Cantidad: " + this.cantidad + ". Total: " + this.total;
        imprimir += ". Fecha venta: " + this.fechaDocumento + "Fecha Modificación: " + this.fechaModificacion;
        return imprimir;
    }
    
    
    private void setNroDocumento(int nroDoc){
        if(this.tipoDocumento == 1){
            this.id = ultimoId + 1;
            ultimoId += 1;
        }
        else{
            this.id = nroDoc;
        }
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
    public Date getFechaDocumento(){
        return this.fechaDocumento;
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
