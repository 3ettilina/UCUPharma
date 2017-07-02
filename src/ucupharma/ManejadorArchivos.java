/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucupharma;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Bettina
 */
public class ManejadorArchivos {
    /**
    * @param nombreCompletoArchivo
    * @param listaLineasArchivo
    * lista con las lineas del archivo
    * @throws IOException
    */
    public static void escribirArchivo(String nombreCompletoArchivo,
        String[] listaLineasArchivo) {
        FileWriter fw;
        try {
            fw = new FileWriter(nombreCompletoArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < listaLineasArchivo.length; i++) {
                    String lineaActual = listaLineasArchivo[i];
                    bw.write(lineaActual);
                    bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo "
                            + nombreCompletoArchivo);
            e.printStackTrace();
        }
    }

    public static ArrayList<String> leerArchivo(String nombreCompletoArchivo) {
        FileInputStream fr;
        ArrayList<String> listaLineasArchivo = new ArrayList<>();
        try {
            fr = new FileInputStream(nombreCompletoArchivo);
            InputStreamReader fich = new InputStreamReader(fr, "UTF-8");
            BufferedReader br = new BufferedReader(fich);
            String lineaActual = br.readLine();
            while (lineaActual != null) {
                    listaLineasArchivo.add(lineaActual);
                    lineaActual = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo "
                            + nombreCompletoArchivo);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo "
                            + nombreCompletoArchivo);
            e.printStackTrace();
        }
        System.out.println("Archivo leido satisfactoriamente");

        return listaLineasArchivo;
    }
}
