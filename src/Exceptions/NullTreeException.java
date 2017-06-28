/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author logikom
 */
public class NullTreeException extends Exception{
    
    public NullTreeException(){
        
    }
    
    public NullTreeException(String mensaje){
        super(mensaje);
    }
}
