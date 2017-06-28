/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import Interfaces.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 *
 * @author Bettina
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Farmacia UcuPharma = new Farmacia("UcuPharma");
        UCUPharmaMainForm form = new UCUPharmaMainForm();
        //home(UcuPharma);
    }
        /**
         * Menu principal
         * @param UcuPharma
         * @throws IOException 
         */
        public static void home(Farmacia UcuPharma) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Bienvenido a UCUPharma, sistema de control de Stock integral.");
        System.out.println("\n\n");
        System.out.println("A continuación se muestran las opciones. Ingrese los valores numéricos para acceder a ellas:\n");
        System.out.println("1 - Cargar/Modificar productos desde un archivo.");
        System.out.println("2 - Ingresar producto nuevo de forma manual.");
        System.out.println("3 - Agregar stock a un producto desde un archivo.");
        System.out.println("4 - Agregar stock a un producto manualmente.");
        System.out.println("5 - Bajar stock a un producto manualmente.");
        System.out.println("6 - Realizar una venta.");
        System.out.println("7 - Realizar una reintegro de stock.");
        System.out.println("8 - Buscar un producto por su código.");
        System.out.println("9 - Buscar productos por su descripción corta.");
        System.out.println("10 - Buscar productos por su descripción larga.");
        System.out.println("11 - Listar productos ordenados por su nombre.");
        System.out.println("12 - Mostrar las ventas realizadas entre un rango de fechas.");
        System.out.println("13 - Salir");
        String opcion = br.readLine();
        switch(opcion){
            
            case "1":{
                System.out.println("\nIngrese la ruta del archivo: ");
                String ruta = br.readLine();
                System.out.println("Ingrese SI, si desea modificar los productos existentes. De lo contrario ingrese NO");
                String modificar = br.readLine();
                UcuPharma.cargarProductos(ruta, modificar);
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "2":{
                System.out.println("\nIngrese el código de producto a crear: ");
                int codigo = Integer.parseInt(br.readLine());
                System.out.println("\nIngrese el precio unitario: ");
                double precio = Double.parseDouble(br.readLine());
                System.out.println("\nIngrese la descripcion corta:");
                String descCorta = br.readLine();
                System.out.println("\nIngrese la descripcion larga:");
                String descLarga = br.readLine();
                System.out.println("\nIngrese el estado - Activo, Inactivo o nada");
                String estado = br.readLine(); 
                
                System.out.println("\nIngrese si requiere refrigeracion (True o False)");
                String refri = br.readLine();
                boolean refrigerado = Boolean.parseBoolean(refri);
                System.out.println("\nIngrese si requiere receta - (True o False)");
                String receta = br.readLine();
                boolean reqReceta = Boolean.parseBoolean(receta);
                System.out.println("\nIngrese la fecha de vencimiento");
                String fVenc = br.readLine();
                int fechaVenc = Integer.parseInt(fVenc);
                System.out.println("\nIngrese el area de aplicacion");
                String area = br.readLine();
                Date fechaCreacion = ManejadorFechas.obtenerFecha();
                Date fechaModificacion = fechaCreacion;
                IProducto nProducto = new Producto(codigo, fechaCreacion, fechaModificacion, precio, descCorta, descLarga, estado, refrigerado, reqReceta, fechaVenc, area, 0);
                IElementoAB<IProducto> nodoProducto = new TElementoAB(codigo, nProducto);
                UcuPharma.stock.arbolStock.insertar(nodoProducto);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "3":{
                System.out.println("\nIngrese la ruta del archivo: ");
                String ruta = br.readLine();
                UcuPharma.cargarStock(ruta);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "4":{
                System.out.println("\nIngrese el codigo del producto: ");
                String clave = br.readLine();
                int codigo = Integer.parseInt(clave);
                System.out.println("\nIngrese la cantidad de unidades a agregar:");
                int cant = Integer.parseInt(br.readLine());
                UcuPharma.altaStock(codigo,cant);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "5":{
                System.out.println("\nIngrese el codigo del producto: ");
                String clave = br.readLine();
                int codigo = Integer.parseInt(clave);
                System.out.println("\nIngrese la cantidad de unidades a quitar (unidades positivas):");
                int cant = Integer.parseInt(br.readLine());
                UcuPharma.bajaStock(codigo,cant);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "6":{
                System.out.println("\nIngrese el codigo del producto: ");
                String clave = br.readLine();
                int codigo = Integer.parseInt(clave);
                System.out.println("\nIngrese la cantidad a vender: ");
                int cant = Integer.parseInt(br.readLine());
                UcuPharma.vender(codigo, cant);
                System.out.println("\n\nProducto vendido con éxito. Presione cualquier tecla para volver al menu.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "7":{
                System.out.println("\nIngrese el id de la venta: ");
                int id = Integer.parseInt(br.readLine());
                System.out.println("\nIngrese el codigo del producto: ");
                int codigo = Integer.parseInt(br.readLine());
                System.out.println("\nIngrese la cantidad a devolver: ");
                int cant = Integer.parseInt(br.readLine());
                UcuPharma.devolver(id, codigo, cant);
                System.out.println("\n\nProducto devuelto con éxito. Presione cualquier tecla para volver al menu.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            
            case "8":{
                System.out.println("\nIngrese el codigo del producto: ");
                int codigo = Integer.parseInt(br.readLine());
                IElementoAB<IProducto> nodoProducto = UcuPharma.buscarPorCodigo(codigo);
                System.out.println(nodoProducto.getDatos());
                System.out.println("\n\nProducto encontrado con éxito. Presione cualquier tecla para volver al menú.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "9":{
                System.out.println("\nIngrese la descripción corta a buscar: ");
                String descripcion = br.readLine();
                Boolean tipoDesc = true;
                UcuPharma.buscarPorDescripcion(descripcion, tipoDesc);
                System.out.println("\n\nProducto encontrado con éxito. Presione cualquier tecla para volver al menú.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "10":{
                System.out.println("\nIngrese la descripción larga a buscar: ");
                String descripcion = br.readLine();
                Boolean tipoDesc = false;
                UcuPharma.buscarPorDescripcion(descripcion, tipoDesc);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "11":{
                UcuPharma.listarProductos();
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char pausa = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "12":{
                System.out.println("\nIngrese el codigo de la venta: ");
                int id = Integer.parseInt(br.readLine());
                System.out.println("\n Ingrese el código del producto a devolver: ");
                int codigo = Integer.parseInt(br.readLine());
                System.out.println("\nIngrese la cantidad a devolver: ");
                int cant = Integer.parseInt(br.readLine());
                UcuPharma.devolver(id, codigo, cant);
                System.out.println("\n\nProducto ingresado con éxito. Presione cualquier tecla para continuar.");
                char stop = (char) System.in.read();
                home(UcuPharma);
                break;
            }
            case "13":{
                break;
            }
            default:{
                System.out.println("La opción ingresada no es válida.");
                char stop = (char) System.in.read();
                home(UcuPharma);
            }
        }
    }
    
}