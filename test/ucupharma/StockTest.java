/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import org.junit.Test;
import static org.junit.Assert.*;
import ucupharma.*;
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
        Comparable clave = 100013;
        String resExp = "WELLA SP.310GR.";
        ILista<IProducto> resObt = ucu.buscarPorDescCorta("WE");
        IProducto prodObt = resObt.buscar(clave).getDato();
        String codObt = prodObt.getDescripcionCorta();
        //100011;1900-01-01 00:00:00;2016-09-23 16:30:18;1190;WELLA FORM CR. 55GR.;WELLA FORM CREMA 55 GRAMOS;Activo;false;false;2011;Quimioterápico 
        assertEquals(resExp, codObt);
    }
    
    
}
