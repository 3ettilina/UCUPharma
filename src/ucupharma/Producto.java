/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.IProducto;
import java.util.Date;

/**
 *
 * @author Bettina
 */
public class Producto implements IProducto {
    
    public int id;
    public Date fecha_creacion;
    public Date ultima_actualizacion;
    public double precio;
    public String descripcion_corta;
    public String descripcion_larga;
    public String estado;
    public boolean refrigerado;
    public boolean receta;
    public int vencimiento;
    public String areaAplicacion;
    public int cantidad;
    
    /**
     * Constructor de Producto sin parametros.
     */
    public Producto(){
        
    }
    
    /**
     * Constructor de Producto con parametros.
     * @param codigo
     * @param creacion
     * @param actualizacion
     * @param precio
     * @param desc_corta
     * @param desc_larga
     * @param estado
     * @param refrigerado
     * @param receta 
     * @param vencimiento 
     * @param area_de_aplicacion 
     * @param cantidad 
     */
    public Producto(int codigo, Date creacion, Date actualizacion, double precio, String desc_corta, String desc_larga, String estado, boolean refrigerado, boolean receta, int vencimiento, String area_de_aplicacion, int cantidad){
        this.id = codigo;
        this.fecha_creacion = creacion;
        this.ultima_actualizacion = actualizacion;
        this.precio = precio;
        this.descripcion_corta = desc_corta;
        this.descripcion_larga = desc_larga;
        this.estado = estado;
        this.refrigerado = refrigerado;
        this.receta = receta;
        this.vencimiento = vencimiento;
        this.areaAplicacion = area_de_aplicacion;
        this.cantidad = cantidad;
    }
    
    
    @Override
    public String toString(){
        String imprimir = "Código: " + this.id + ". Descripcion: " + this.descripcion_corta + ". Cantidad: " + this.cantidad + ". Precio: "+ this.precio + ". Area de Aplicación: " + this.areaAplicacion + ". \n Estado: " + this.estado;
        imprimir += ". Refrigerado: "+ this.refrigerado + ". Requiere receta: " + this.receta + ". Fecha creación: " + this.fecha_creacion + ". Ultima actualización: " + this.ultima_actualizacion + ". Vencimiento: " + this.vencimiento;
        return imprimir;
    }
    
    public void setCodigo(int nCodigo){
        this.id = nCodigo;
    }
    
    @Override 
    public void setUltimaActualizacion(Date fecha){
        this.ultima_actualizacion = fecha;
    }
    @Override
    public void setPrecio(double nPrecio){
        this.precio = nPrecio;
    }
    @Override
    public void setDescCorta(String nDescCorta){
        this.descripcion_corta = nDescCorta;
    }
    
    @Override
    public void setDescLarga(String nDescLarga){
        this.descripcion_larga = nDescLarga;
    }
    
    @Override
    public void setEstado(String nEstado){
        this.estado = nEstado;
    }
    
    @Override
    public void setRefrigerado(Boolean nRefrigerado){
        this.refrigerado = nRefrigerado;
    }
    
    @Override
    public void setRequiereReceta(Boolean nReceta){
        this.receta = nReceta;
    }
    
    @Override
    public void setVencimiento(int vencimiento){
        this.vencimiento = vencimiento;
    }
    
    @Override
    public void setAreaAplicacion(String area_de_aplicacion){
        this.areaAplicacion = area_de_aplicacion;
    }
    
    @Override
    public void setCantidad(int nCantidad){
        this.cantidad = nCantidad;
    }
    
    @Override
    public int getCodigo(){
        return this.id;
    }
    
    @Override
    public Date getFechaCreacion(){
        return this.fecha_creacion;
    }
    
    @Override
    public Date getUltimaActualizacion(){
        return this.ultima_actualizacion;
    }
    
    @Override
    public double getPrecio(){
        return this.precio;
    }
    
    @Override
    public String getDescripcionCorta(){
        return this.descripcion_corta;
    }
    
    @Override
    public String getDescripcionLarga(){
        return this.descripcion_larga;
    }
    
    @Override
    public String getEstado(){
        return this.estado;
    }
    
    @Override
    public boolean getRefrigerado(){
        return this.refrigerado;
    }
    
    @Override
    public boolean getRequiereReceta(){
        return this.receta;
    }
    
    @Override
    public int getVencimiento(){
        return this.vencimiento;
    }
    
    @Override
    public String getAreaAplicacion(){
        return this.areaAplicacion;
    }
    
    @Override
    public int getCantidad(){
        return this.cantidad;
    }
    
    
}
