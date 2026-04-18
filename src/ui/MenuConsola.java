package ui;

import service.*;
import java.util.Scanner;

/**
 * Clase que implementa la interfaz de usuario por consola para el sistema de papelería.
 * Gestiona la interaccion con el usuario, muestra los menus principales y submenus,
 * y procesa las opciones seleccionadas. Coordina los servicios de inventario,
 * proveedores y control.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see InventarioService
 * @see ProveedorService
 * @see ControlService
 */
public class MenuConsola {

    // ==================== ATRIBUTOS ====================
    
    /** Scanner para leer la entrada del usuario. */
    private Scanner scanner;
    
    /** Servicio para operaciones de inventario. */
    private InventarioService inventarioService;
    
    /** Servicio para operaciones de proveedores. */
    private ProveedorService proveedorService;
    
    /** Servicio para operaciones de control y reportes. */
    private ControlService controlService;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del menu de consola.
     * Inicializa el scanner y crea las instancias de los servicios necesarios.
     */
    public MenuConsola() {
        this.scanner = new Scanner(System.in);
        this.inventarioService = new InventarioService();
        this.proveedorService = new ProveedorService();
        this.controlService = new ControlService();
    }

    // ==================== METODO PRINCIPAL ====================
    
    /**
     * Inicia el bucle principal del menu.
     * Muestra el menu principal y procesa las opciones seleccionadas
     * hasta que el usuario decide salir.
     */
    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> menuInventario();
                case 2 -> menuProveedores();
                case 3 -> menuControl();
                case 4 -> {
                    System.out.println("Gracias por usar Papelería Mexico!");
                    salir = true;
                }
                default -> System.out.println("Opcion no valida");
            }
            
            if (!salir) {
                pausa();
            }
        }
        
        scanner.close();
    }

    // ==================== MENU PRINCIPAL ====================
    
    /**
     * Muestra el menu principal del sistema.
     * Opciones: Gestion de Inventario, Gestion de Proveedores,
     * Control y Reportes, Salir.
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n*** PAPELERIA MEXICO ***");
        System.out.println("1. Gestion de Inventario");
        System.out.println("2. Gestion de Proveedores");
        System.out.println("3. Control y Reportes");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    // ==================== SUBMENU INVENTARIO ====================
    
    /**
     * Muestra el submenu de gestion de inventario.
     * Opciones: Agregar articulo, Listar articulos,
     * Buscar articulo, Ajustar stock, Volver.
     */
    private void menuInventario() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n=== INVENTARIO ===");
            System.out.println("1. Agregar articulo");
            System.out.println("2. Listar articulos");
            System.out.println("3. Buscar articulo");
            System.out.println("4. Ajustar stock");
            System.out.println("5. Volver");
            System.out.print("Seleccione: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    inventarioService.agregarArticulo(scanner);
                    break;
                case 2:
                    inventarioService.listarArticulos();
                    break;
                case 3:
                    inventarioService.buscarArticulo(scanner);
                    break;
                case 4:
                    inventarioService.ajustarStock(scanner);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
            
            if (!volver) pausa();
        }
    }

    // ==================== SUBMENU PROVEEDORES ====================
    
    /**
     * Muestra el submenu de gestion de proveedores.
     * Opciones: Registrar proveedor, Listar proveedores,
     * Asignar articulo a proveedor, Realizar pedido, Volver.
     */
    private void menuProveedores() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n=== PROVEEDORES ===");
            System.out.println("1. Registrar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Asignar articulo a proveedor");
            System.out.println("4. Realizar pedido");
            System.out.println("5. Volver");
            System.out.print("Seleccione: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> proveedorService.registrarProveedor(scanner);
                case 2 -> proveedorService.listarProveedores();
                case 3 -> proveedorService.asignarArticuloAProveedor(scanner);
                case 4 -> proveedorService.realizarPedido(scanner);
                case 5 -> volver = true;
                default -> System.out.println("Opcion no valida");
            }
            
            if (!volver) pausa();
        }
    }

    // ==================== SUBMENU CONTROL ====================
    
    /**
     * Muestra el submenu de control y reportes.
     * Opciones: Verificar stocks minimos, Aplicar descuentos,
     * Generar reportes, Validar venta, Volver.
     */
    private void menuControl() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n=== CONTROL Y REPORTES ===");
            System.out.println("1. Verificar stocks minimos");
            System.out.println("2. Aplicar descuentos");
            System.out.println("3. Generar reportes");
            System.out.println("4. Validar venta");
            System.out.println("5. Volver");
            System.out.print("Seleccione: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    controlService.verificarStocksMinimos();
                    break;
                case 2:
                    controlService.aplicarDescuentos(scanner);
                    break;
                case 3:
                    controlService.generarReportes(scanner);
                    break;
                case 4:
                    controlService.validarVenta(scanner);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
            
            if (!volver) pausa();
        }
    }

    // ==================== METODOS AUXILIARES ====================
    
    /**
     * Lee y valida la opcion ingresada por el usuario.
     * 
     * @return Numero entero de la opcion seleccionada, o 0 si no es valida
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Pausa la ejecucion hasta que el usuario presione Enter.
     * Util para que el usuario pueda leer mensajes antes de continuar.
     */
    private void pausa() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
}//fin de la clase