/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chris
 */
public class ArbolB extends JPanel {

    private int grado;
    private NodoB raiz;
    private static final int ESPACIO_HORIZONTAL = 50;
    private static final int ESPACIO_VERTICAL = 70;
    private static final int RADIO_NODO = 25;

    // clase interna para representar un nodo del arbol B
    private class NodoB {
        public List<Integer> claves;
        public List<NodoB> hijos;
        public boolean esHoja;
        public int x, y; // posicion para la graficacion

        public NodoB() {
            this.claves = new ArrayList<>();
            this.hijos = new ArrayList<>();
            this.esHoja = true;
            this.x = 0;
            this.y = 0;
        }
    }

    // constructor de la clase ArbolB
    public ArbolB(int grado) {
        this.grado = grado;
        this.raiz = new NodoB();
    }

    // funcion para insertar una clave en el arbol B
    public void insertar(int clave) {
        NodoB raizActual = this.raiz;
        if (raizActual.claves.size() == 2 * grado - 1) {
            NodoB nuevaRaiz = new NodoB();
            nuevaRaiz.esHoja = false;
            nuevaRaiz.hijos.add(raizActual);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
        insertarNoLleno(this.raiz, clave); 
        recalcularPosiciones(); // recalcular posiciones despues de la insercion
        repaint(); // redibujar el panel
    }

    // funcion para insertar una clave en un nodo no lleno
    private void insertarNoLleno(NodoB nodo, int clave) {
        int i = nodo.claves.size() - 1;

        if (nodo.esHoja) {
            while (i >= 0 && clave < nodo.claves.get(i)) {
                i--;
            }
            nodo.claves.add(i + 1, clave);
        } else {
            while (i >= 0 && clave < nodo.claves.get(i)) {
                i--;
            }
            i++;
            if (nodo.hijos.get(i).claves.size() == 2 * grado - 1) {
                dividirHijo(nodo, i);
                if (clave > nodo.claves.get(i)) {
                    i++;
                }
            }
            insertarNoLleno(nodo.hijos.get(i), clave);
        }
    }

    // funcion para dividir un hijo lleno de un nodo
    private void dividirHijo(NodoB nodoPadre, int indiceHijo) {
        NodoB hijoLleno = nodoPadre.hijos.get(indiceHijo);
        NodoB nuevoHijo = new NodoB();
        nuevoHijo.esHoja = hijoLleno.esHoja;

        for (int j = 0; j < grado - 1; j++) {
            nuevoHijo.claves.add(hijoLleno.claves.remove(grado));
        }

        if (!hijoLleno.esHoja) {
            for (int j = 0; j < grado; j++) {
                nuevoHijo.hijos.add(hijoLleno.hijos.remove(grado));
            }
        }

        nodoPadre.claves.add(indiceHijo, hijoLleno.claves.remove(grado - 1));
        nodoPadre.hijos.add(indiceHijo + 1, nuevoHijo);
    }

    // funcion para eliminar una clave del arbol B
    public void eliminar(int clave) {
        boolean eliminado = eliminarRecursivo(this.raiz, clave);
        if (this.raiz.claves.isEmpty() && !this.raiz.esHoja) {
            this.raiz = this.raiz.hijos.get(0);
        }
        if (eliminado) {
            recalcularPosiciones(); // recalcular posiciones despues de la eliminacion
            repaint(); // redibujar el panel
        }
    }

    // funcion auxiliar para eliminar una clave
    private boolean eliminarRecursivo(NodoB nodo, int clave) {
        int indice = 0;
        while (indice < nodo.claves.size() && clave > nodo.claves.get(indice)) {
            indice++;
        }

        if (indice < nodo.claves.size() && nodo.claves.get(indice) == clave) {
            if (nodo.esHoja) {
                nodo.claves.remove(indice);
                return true;
            } else {
                NodoB predecesor = obtenerPredecesor(nodo, indice);
                NodoB sucesor = obtenerSucesor(nodo, indice);

                if (predecesor.claves.size() >= grado) {
                    nodo.claves.set(indice, predecesor.claves.remove(predecesor.claves.size() - 1));
                    return true;
                } else if (sucesor.claves.size() >= grado) {
                    nodo.claves.set(indice, sucesor.claves.remove(0));
                    if (sucesor.hijos.size() > 0) {
                        nodo.hijos.get(indice + 1).claves.add(0, sucesor.hijos.remove(0).claves.get(0));
                        if (sucesor.hijos.get(0).hijos.size() > 0) {
                            nodo.hijos.get(indice + 1).hijos.add(0, sucesor.hijos.get(0).hijos.remove(0));
                        }
                    }
                    return true;
                } else {
                    fusionarHijo(nodo, indice);
                    return eliminarRecursivo(nodo.hijos.get(indice), clave);
                }
            }
        } else {
            if (nodo.esHoja) {
                JOptionPane.showMessageDialog(null, "la clave " + clave + " no se encontro en el arbol.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            boolean ultimoHijo = indice == nodo.hijos.size();
            NodoB hijo = nodo.hijos.get(indice);

            if (hijo.claves.size() < grado) {
                llenarHijo(nodo, indice);
            }

            if (ultimoHijo && indice > nodo.hijos.size() - 1) {
                return eliminarRecursivo(nodo.hijos.get(indice - 1), clave);
            } else {
                return eliminarRecursivo(nodo.hijos.get(indice), clave);
            }
        }
    }

    // funcion para obtener el predecesor de una clave en un nodo interno
    private NodoB obtenerPredecesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos.get(indice);
        while (!actual.esHoja) {
            actual = actual.hijos.get(actual.hijos.size() - 1);
        }
        return actual;
    }

    // funcion para obtener el sucesor de una clave en un nodo interno
    private NodoB obtenerSucesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos.get(indice + 1);
        while (!actual.esHoja) {
            actual = actual.hijos.get(0);
        }
        return actual;
    }

    // funcion para llenar un hijo que tiene menos del grado minimo de claves
    private void llenarHijo(NodoB nodoPadre, int indiceHijo) {
        NodoB hijo = nodoPadre.hijos.get(indiceHijo);

        if (indiceHijo != 0 && nodoPadre.hijos.get(indiceHijo - 1).claves.size() >= grado) {
            NodoB hermanoIzquierdo = nodoPadre.hijos.get(indiceHijo - 1);
            hijo.claves.add(0, nodoPadre.claves.get(indiceHijo - 1));
            nodoPadre.claves.set(indiceHijo - 1, hermanoIzquierdo.claves.remove(hermanoIzquierdo.claves.size() - 1));
            if (!hermanoIzquierdo.esHoja) {
                hijo.hijos.add(0, hermanoIzquierdo.hijos.remove(hermanoIzquierdo.hijos.size() - 1));
            }
        } else if (indiceHijo != nodoPadre.hijos.size() - 1 && nodoPadre.hijos.get(indiceHijo + 1).claves.size() >= grado) {
            NodoB hermanoDerecho = nodoPadre.hijos.get(indiceHijo + 1);
            hijo.claves.add(nodoPadre.claves.get(indiceHijo));
            nodoPadre.claves.set(indiceHijo, hermanoDerecho.claves.remove(0));
            if (!hermanoDerecho.esHoja) {
                hijo.hijos.add(hermanoDerecho.hijos.remove(0));
            }
        } else {
            if (indiceHijo != nodoPadre.hijos.size() - 1) {
                fusionarHijo(nodoPadre, indiceHijo);
            } else {
                fusionarHijo(nodoPadre, indiceHijo - 1);
            }
        }
    }

    // funcion para fusionar un hijo con su hermano mas proximo
    private void fusionarHijo(NodoB nodoPadre, int indiceHijo) {
        NodoB hijoIzquierdo = nodoPadre.hijos.get(indiceHijo);
        NodoB hijoDerecho = nodoPadre.hijos.get(indiceHijo + 1);

        hijoIzquierdo.claves.add(nodoPadre.claves.remove(indiceHijo));
        hijoIzquierdo.claves.addAll(hijoDerecho.claves);

        if (!hijoIzquierdo.esHoja) {
            hijoIzquierdo.hijos.addAll(hijoDerecho.hijos);
        }

        nodoPadre.hijos.remove(indiceHijo + 1);
    }

    // funcion para buscar una clave en el arbol B
    public boolean buscar(int clave) {
        return buscarRecursivo(this.raiz, clave);
    }

    // funcion auxiliar para buscar una clave
    private boolean buscarRecursivo(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.claves.size() && clave > nodo.claves.get(i)) {
            i++;
        }

        if (i < nodo.claves.size() && nodo.claves.get(i) == clave) {
            return true;
        }

        if (nodo.esHoja) {
            return false;
        }

        return buscarRecursivo(nodo.hijos.get(i), clave);
    }

    // funcion para calcular las posiciones de los nodos para la graficacion
    private void recalcularPosiciones() {
        if (raiz != null) {
            calcularPosicionNodo(raiz, getWidth() / 2, ESPACIO_VERTICAL, ESPACIO_HORIZONTAL);
        }
    }

    private int calcularPosicionNodo(NodoB nodo, int x, int y, int espacioH) {
        nodo.x = x;
        nodo.y = y;
        int anchoSubarbol = 0;
        if (!nodo.esHoja) {
            int numHijos = nodo.hijos.size();
            int espacioTotal = (numHijos - 1) * espacioH;
            anchoSubarbol = calcularAnchoSubarbol(nodo);
            int xInicial = x - anchoSubarbol / 2;
            for (NodoB hijo : nodo.hijos) {
                xInicial += calcularPosicionNodo(hijo, xInicial, y + ESPACIO_VERTICAL, espacioH * (2 * grado -1)) + espacioH;
            }
        }
        return anchoSubarbol;
    }

    private int calcularAnchoSubarbol(NodoB nodo) {
        if (nodo.esHoja) {
            return nodo.claves.size() * (2 * RADIO_NODO);
        }
        int ancho = 0;
        for (NodoB hijo : nodo.hijos) {
            ancho += calcularAnchoSubarbol(hijo) + ESPACIO_HORIZONTAL;
        }
        return Math.max(0, ancho - ESPACIO_HORIZONTAL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        dibujarArbol(g2d, raiz);
    }

    //DIBUJAR ARBOL
    private void dibujarArbol(Graphics2D g2d, NodoB nodo) {
        if (nodo != null) {
            int numClaves = nodo.claves.size();
            int xBase = nodo.x - (numClaves * (2 * RADIO_NODO) - RADIO_NODO) / 2;

            // dibujar el nodo y las claves
            for (int i = 0; i < numClaves; i++) {
                int x = xBase + i * (2 * RADIO_NODO);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillOval(x - RADIO_NODO, nodo.y - RADIO_NODO, 2 * RADIO_NODO, 2 * RADIO_NODO);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x - RADIO_NODO, nodo.y - RADIO_NODO, 2 * RADIO_NODO, 2 * RADIO_NODO);
                String texto = String.valueOf(nodo.claves.get(i));
                FontMetrics fm = g2d.getFontMetrics();
                int anchoTexto = fm.stringWidth(texto);
                int altoTexto = fm.getHeight();
                g2d.drawString(texto, x - anchoTexto / 2, nodo.y + altoTexto / 4);
            }

            // dibujar las lineas hacia los hijos
            if (!nodo.esHoja) {
                for (NodoB hijo : nodo.hijos) {
                    if (hijo != null) {
                        g2d.setColor(Color.GRAY);
                        g2d.drawLine(nodo.x, nodo.y + RADIO_NODO, hijo.x, hijo.y - RADIO_NODO);
                        dibujarArbol(g2d, hijo);
                    }
                }
            }
        }
    }
}