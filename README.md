# Explicacion de la aplicacion

Esta aplicacion es una implementacion grafica de un arbol B que permite visualizar y manipular la estructura del arbol mediante operaciones basicas

## Clase Principal

Esta clase maneja la interfaz de usuario y la logica principal del programa:

Entrada del Grado: Solicita al usuario el grado del arbol B (minimo 2) mediante un cuadro de dialogo
Creacion de Ventana: Crea una ventana JFrame que contiene un panel de tipo ArbolB
Menu de Operaciones: Presenta un menu con 4 opciones:

Insertar clave
Eliminar clave
Buscar clave
Salir del programa

Para cada operacion, muestra cajas de texto para ingresar los valores necesarios y llama a los metodos correspondientes de la clase ArbolB

## Clase ArbolB

Esta clase extiende JPanel y contiene toda la logica del arbol B y su representacion grafica

## Funcionalidades Principales:

### Operaciones Basicas:

insertar(clave): agrega una nueva clave al arbol, dividiendo nodos cuando es necesario
eliminar(clave): elimina una clave del arbol, fusionando nodos cuando es necesario
buscar(clave): verifica si una clave existe en el arbol

### Metodos Auxiliares:

dividirHijo(): divide un nodo lleno cuando se inserta una nueva clave
fusionarHijo(): combina nodos cuando quedan con menos claves de las necesarias
llenarHijo(): balancea el arbol distribuyendo claves entre nodos hermanos

### Representacion Grafica:

recalcularPosiciones(): calcula las coordenadas de cada nodo para el dibujo
paintComponent(): dibuja el arbol completo con nodos y conexiones
dibujarArbol(): renderiza cada nodo con sus claves y las lineas a sus hijos

La visualizacion se actualiza automaticamente despues de cada operacion
