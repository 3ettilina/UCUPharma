/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import org.junit.Test;
import static org.junit.Assert.*;
import Interfaces.*;

/**
 *
 * @author Bettina Carrizo
 */
public class StockTest {
    IArbolBB<IProducto> arbolPrueba = new TArbolBB<>();
    Farmacia ucu = new Farmacia("UCU");
    public StockTest() {
    }

    @Test
    public void testCargarProductos(){
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        Comparable clave = 111;
        String resExp = "WELLA FORM CR. 55GR.";
        ILista<IProducto> resObt = ucu.buscarPorDescCorta("WE");
        IProducto prodObt = resObt.buscar(clave).getDato();
        String codObt = prodObt.getDescripcionCorta();
        //111;1900-01-01 00:00:00;2016-09-23 16:30:18;1190;WELLA FORM CR. 55GR.;WELLA FORM CREMA 55 GRAMOS;Activo;false;false;2011;Quimioterápico 
        assertEquals(resExp, codObt);
    }
    
    @Test
    public void testCargarStock(){
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        ucu.cargarStock("C:\\farmacia_stock_prueba.csv");
        Comparable clave = 110;
        int resObt = ucu.buscarPorCodigo(110).getDatos().getCantidad();
        int resExp = 4490;
        
        assertEquals(resExp, resObt);
    }
    
    @Test
    public void testReporteVencimientos(){
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        String año = "2022";
        int codigoProd = 110;
        ILista<IProducto> restObt = ucu.buscarVencimientos(año);
        INodo<IProducto> prodObt = restObt.buscar(codigoProd);
        int codigoObt = prodObt.getDato().getCodigo();
        assertEquals(codigoProd, codigoObt);
    }
    
    @Test
    public void testBuscarPorDescCorta(){
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        String descABuscar = "PROD";
        ILista<IProducto> restObt = ucu.buscarPorDescCorta(descABuscar);
        INodo<IProducto> prodObt = restObt.getPrimero();
        String descEncontrada = prodObt.getDato().getDescripcionCorta();
        String descEsperada = "PRODUCTO PRUEBA";
        assertEquals(descEsperada, descEncontrada);
    }
    
    @Test
    public void testAltaStock(){
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        int codigo = 101;
        int cantidad = 20;
        ucu.altaStock(codigo, cantidad);
        int resObt = ucu.buscarPorCodigo(codigo).getDatos().getCantidad();
        int resExp = 20;
        assertEquals(resExp, resObt);
    }
    
}
