/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.INodo;
import Interfaces.ILista;

/**
 *
 * @author Bettina
 */
public class Lista<T> implements ILista<T>{
    
    private INodo<T> primero;

    public Lista() {
        primero = null;
    }

    public Lista(INodo<T> unNodo) {
        this.primero = unNodo;
    }

    @Override
    public void insertar(INodo<T> unNodo) {
        if (esVacia()) {
            primero = unNodo;
        } else {
            INodo<T> aux = primero;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(unNodo);
        }
    }
    
    public void insertarOrdenado(INodo<T> unNodo){
        if (this.esVacia()){
            this.primero = unNodo;
        }
        else{
            if (this.getPrimero().compareTo(unNodo.getEtiqueta()) >= 0){
                unNodo.setSiguiente(this.getPrimero());
                this.primero = unNodo;
            }
            else{
                INodo<T> nodoPuntero = this.getPrimero();
                while (nodoPuntero != null){
                    if (nodoPuntero.getSiguiente() == null){
                        nodoPuntero.setSiguiente(unNodo);
                        break;
                    }
                    if ((nodoPuntero.getSiguiente().compareTo(unNodo.getEtiqueta())) >= 0){
                        unNodo.setSiguiente(nodoPuntero.getSiguiente());
                        nodoPuntero.setSiguiente(unNodo);
                        break;
                    }
                    nodoPuntero = nodoPuntero.getSiguiente();
                }
                
            }
        }
    }

    @Override
    public INodo<T> buscar(Comparable clave) {
        if (esVacia()) {
            return null;
        } else {
            INodo<T> aux = primero;
            while (aux != null) {
                if (aux.getEtiqueta().equals(clave)) {
                    return aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return null;
    }
    
    
    @Override    
    public boolean eliminar(Comparable clave) {
        if (esVacia()) {
            return false;
        }
        if (primero.getSiguiente() == null) {
            if (primero.getEtiqueta().equals(clave)) {
                primero = null;
                return true;
            }
        }
        INodo<T> aux = primero;
        if (aux.getEtiqueta().compareTo(clave) == 0) {
            //Eliminamos el primer elemento
            INodo temp1 = aux;
            INodo temp = aux.getSiguiente();
            primero = temp;
            return true;
        }
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getEtiqueta().equals(clave)) {
                INodo<T> temp = aux.getSiguiente();
                aux.setSiguiente(temp.getSiguiente());
                return true;

            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    @Override
    public String imprimir() {
        String aux = "";
        if (!esVacia()) {
            INodo<T> temp = primero;
            while (temp != null) {
                temp.imprimirEtiqueta();
                temp = temp.getSiguiente();
            }
        }
        return aux;
    }
    
    

    @Override
    public String imprimir(String separador) {
        String aux = "";
        if (esVacia()) {
            return "";
        } else {
            INodo temp = primero;
            aux = "" + temp.getEtiqueta();
            while (temp.getSiguiente() != null) {
                aux = aux + separador + temp.getSiguiente().getEtiqueta();
                temp = temp.getSiguiente();
            }

        }
        return aux;

    }

    @Override
    public int cantElementos() {
        int contador = 0;
        if (esVacia()) {
            System.out.println("Cantidad de elementos 0.");
            return 0;
        } else {
            INodo aux = primero;
            while (aux != null) {
                contador++;
                aux = aux.getSiguiente();
            }
        }
        return contador;
    }

    @Override
    public boolean esVacia() {
        return primero == null;
    }

    @Override
    public INodo getPrimero() {
        return primero;
    }
}
