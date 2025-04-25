/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea3;

import javax.swing.JFrame;          //para crear ventanas
import javax.swing.JOptionPane;     //cajas de texto

/**
 *
 * @author chris
 */
public class Tarea3 {

    public static void main(String[] args) {
        String gradoStr = JOptionPane.showInputDialog(null, "ingrese el grado del arbol B:", "Grado del arbol B", JOptionPane.QUESTION_MESSAGE);
        int grado = 0;
        try {
            grado = Integer.parseInt(gradoStr);
            if (grado < 2) {
                JOptionPane.showMessageDialog(null, "el grado debe ser minimo 2.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ingrese un numero valido para el grado.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //VENTANA DEL ARBOL
        ArbolB panelArbol = new ArbolB(grado);
        JFrame ventana = new JFrame("arbol B");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.getContentPane().add(panelArbol);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        while (true) {
            String opcionStr = JOptionPane.showInputDialog(
                    ventana, // menu de operaciones
                    "Seleccione una operacion:\n" +
                            "1. Insertar clave\n" +
                            "2. Eliminar clave\n" +
                            "3. Buscar clave\n" +
                            "4. Salir",
                    "MENU arbol B",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (opcionStr == null) {
                break;
            }

            try {
                int opcion = Integer.parseInt(opcionStr);
                switch (opcion) {
                    case 1:
                        String claveInsertarStr = JOptionPane.showInputDialog(ventana, "ingrese la clave que desea insertar:", "Insertar Clave", JOptionPane.QUESTION_MESSAGE);
                        if (claveInsertarStr != null) {
                            try {
                                int claveInsertar = Integer.parseInt(claveInsertarStr);
                                panelArbol.insertar(claveInsertar);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(ventana, "ingrese un numero valido para la clave.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 2:
                        String claveEliminarStr = JOptionPane.showInputDialog(ventana, "ingrese la clave que desea eliminar:", "Eliminar Clave", JOptionPane.QUESTION_MESSAGE);
                        if (claveEliminarStr != null) {
                            try {
                                int claveEliminar = Integer.parseInt(claveEliminarStr);
                                panelArbol.eliminar(claveEliminar);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(ventana, "ingrese un numero valido para la clave.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 3:
                        String claveBuscarStr = JOptionPane.showInputDialog(ventana, "ingrese la clave que desea buscar:", "Buscar Clave", JOptionPane.QUESTION_MESSAGE);
                        if (claveBuscarStr != null) {
                            try {
                                int claveBuscar = Integer.parseInt(claveBuscarStr);
                                if (panelArbol.buscar(claveBuscar)) {
                                    JOptionPane.showMessageDialog(ventana, "La clave " + claveBuscar + " se encontro en el arbol.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(ventana, "La clave " + claveBuscar + " no se encontro en el arbol.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(ventana, "ingrese un numero valido para la clave.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(ventana, "saliendo del programa.", "Salir", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    default:
                        JOptionPane.showMessageDialog(ventana, "opcion no valida.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, "ingrese un numero valido para la opcion.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}