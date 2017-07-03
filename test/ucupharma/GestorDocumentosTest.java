/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.ILista;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author UCU
 */
public class GestorDocumentosTest {
    
    Farmacia ucu = new Farmacia("UCU");
    
    
    public GestorDocumentosTest() {
    }

    @Test
    public void testVender() {
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        ucu.cargarStock("C:\\farmacia_stock_prueba.csv");
        int codigo = 111;
        int cantidad = 20;
        ucu.vender(codigo, cantidad);
        int resObt = ucu.buscarPorCodigo(codigo).getDatos().getCantidad();
        int resExp = 80;
        
        assertEquals(resExp, resObt);
    }
    
    @Test
    public void testComprar() {
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        ucu.cargarStock("C:\\farmacia_stock_prueba.csv");
        int numDoc = 2565;
        int codigo = 110;
        int cantidad = 10;
        ucu.comprar(numDoc, codigo, cantidad);
        int resObt = ucu.buscarPorCodigo(codigo).getDatos().getCantidad();
        int resExp = 4500;
        
        assertEquals(resExp, resObt);
    }
    
    @Test
    public void testDevolver() {
        ucu.cargarProductos("C:\\farmacia_articles_prueba.csv", "SI");
        ucu.cargarStock("C:\\farmacia_stock_prueba.csv");
        int numDoc = 1;
        int codigo = 111;
        int cantidad = 10;
        ucu.comprar(numDoc, codigo, cantidad);
        int resObt = ucu.buscarPorCodigo(codigo).getDatos().getCantidad();
        int resExp = 110;
        
        assertEquals(resExp, resObt);
    }
    
}
