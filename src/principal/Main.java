package principal;

import ui.MenuConsola;

/**
 * Clase principal que contiene el punto de entrada del sistema de papelería.
 * Inicia la aplicacion y muestra el menu de consola al usuario.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see MenuConsola
 */
public class Main {
    
    /**
     * Metodo principal que inicia el sistema de papelería.
     * Muestra mensaje de bienvenida y crea una instancia del menu de consola.
     * 
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("*** PAPELERIA MEXICO ***");
        System.out.println("Sistema de Gestion de Inventario");
        System.out.println("Version 2.0 - Todo para tu oficina y escuela!");
        System.out.println("Iniciando aplicacion...\n");
        
        MenuConsola menu = new MenuConsola();
        menu.iniciar();
    }
    
}//fin de la clase