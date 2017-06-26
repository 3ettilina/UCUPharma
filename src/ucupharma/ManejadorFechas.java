/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author logikom
 */
public class ManejadorFechas {
    
    
    public static Date obtenerFecha(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
    
    public static Date crearFecha(String dato){
     try{
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fecha = formato.parse(dato);
        return fecha;
    } 
     catch (ParseException e){
        System.err.println(e.getMessage());
    }
    return null;
    }
}
