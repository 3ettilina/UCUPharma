/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.Date;

/**
 *
 * @author logikom
 */
public interface IDocumento {
    
    /**
     * Retorna el código del producto.
     * @return 
     */
    public int getCodigoProd();
    
    public double getPrecioUnitario();
    
    public String getTipoDoc(); 
    
    /**
     * Retorna el monto total de la venta.
     * @return 
     */
    public double getTotal();
    
    /**
     * Retorna la cantidad de items vendidos.
     * @return 
     */
    public int getCantidad();
    
    /**
     * Retorna la fecha de la venta.
     * @return 
     */
    public Date getFechaDocumento();
    
    /**
     * Retorna la fecha de modificación de la venta.
     * Si es que un producto fue devuelto, ésta será distinta de la fecha de venta.
     * @return 
     */
    public Date getFechaModificacion();
    
    /**
     * Retorna el id de la venta.
     * @return 
     */
    public Comparable getId();
    
    /**
     * Modifica la cantidad de items que se dieron de baja del Stock.
     * @param cantidad 
     */
    public void setCantidad(int cantidad);
    
    /**
     * Cambia la fecha de modificacion de la venta.
     * @param fecha 
     */
    public void setFechaModificacion(Date fecha);
    
    /**
     * Cambia el monto total de la venta.
     * @param total 
     */
    public void setTotal(double total);
}
