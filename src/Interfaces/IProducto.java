/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.Date;
/**
 * 
 * @author Bettina
 * @param <T> 
 */
public interface IProducto<T> {
    
    /**
     * Método que permite insertar un precio en un producto.
     */
    public void setPrecio(double precio);
    
    /**
     * Método que permite modificar la ultima actualización de un producto.
     */
    public void setUltimaActualizacion(Date fecha);
  
    /**
     * Método que permite modificar la descripción corta.
     */
    public void setDescCorta(String dCorta);
    
    /**
     * Método que permite modificar la descripción corta.
     */
    public void setDescLarga(String dLarga);
    
    /**
     * Método que permite modificar el estado de un producto.
     */
    public void setEstado(String estado);
    
    /**
     * Método que permite modificar si un producto debe ser refrigerado.
     */
    public void setRefrigerado(Boolean refri);
    
    /**
     * Método que permite modificar si un producto requiere receta.
     */
    public void setRequiereReceta(Boolean receta);
    
    /**
     * Método que permite modificar el vencimiento de un producto
     * @param vencimiento 
     */
    public void setVencimiento(int vencimiento);
    
    /**
     * Método que permite modificar el area de aplicacion de un producto.
     * @param area_de_aplicacion 
     */
    public void setAreaAplicacion(String area_de_aplicacion);
    
    /**
     * Método que permite modificar la cantidad de producto existente.
     * @param cantidad 
     */
    public void setCantidad(int cantidad);
    
    /**
     * Método que retorna el código de un producto.
     * @return Codigo de producto.
     */
    public int getCodigo();
    
    /**
     * Metodo que retorna la fecha de creacion de un producto en formato YYYY/MM/DD hh:mm:ss.
     * @return Fecha de creacion de producto.
     */
    public Date getFechaCreacion();
    
    /**
     * Metodo que retorna la fecha de la ultima actualizacion de un producto en formato YYYY/MM/DD hh:mm:ss.
     * @return Ultima actualizacion de producto.
     */
    public Date getUltimaActualizacion();
    
    /**
     * Método que retorna el precio de un producto.
     * @return Precio de producto
     */
    public double getPrecio();
    
    /**
     * Metodo que retorna la descripcion corta de un producto.
     * @return Descripcion corta de producto.
     */
    public String getDescripcionCorta();
   
    /**
     * Método que retorna la descripcion larga de un producto.
     * @return Descripcion larga de producto
     */
    public String getDescripcionLarga();
    
    /**
     * Metodo que retorna el estado de un producto (Activo, Inactivo, Null).
     * @return Estado de producto
     */
    public String getEstado();
    
    /**
     * Metodo que retorna si un producto es refrigerado ()
     * @return Verdadero o Falso
     */
    public boolean getRefrigerado();
    
    /**
     * Metodo que retorna si un producto lleva receta.
     * @return Verdadero o Falso
     */
    public boolean getRequiereReceta();
    
    /**
     * Metodo que retorna el año de venvimiento de un producto.
     * @return Numero entero que representa el año.
     */
    public int getVencimiento();
    
    /**
     * Método que retorna el area de aplicacion de un producto.
     * @return Area de aplicacion
     */
    public String getAreaAplicacion();
    
    /**
     * Metodo que retorna la cantidad de existencias de un producto.
     * @return 
     */
    public int getCantidad();
    
    
}
