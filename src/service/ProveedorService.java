package service;

import model.catalogo.Proveedor;
import model.articulos.Articulo;
import repository.DataStore;
import java.util.List;
import java.util.Scanner;

/**
 * Clase de servicio que gestiona las operaciones relacionadas con proveedores.
 * Proporciona funcionalidades para registrar proveedores, listarlos,
 * asignar articulos a proveedores y realizar pedidos.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Proveedor
 * @see Articulo
 * @see DataStore
 */
public class ProveedorService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de proveedores.
     * Obtiene la instancia unica del DataStore.
     */
    public ProveedorService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE REGISTRO ====================
    
    /**
     * Registra un nuevo proveedor en el sistema.
     * Solicita NIT, nombre, telefono, email y direccion.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void registrarProveedor(Scanner scanner) {
        System.out.println("\n=== REGISTRAR PROVEEDOR ===");
        
        System.out.print("NIT: ");
        String nit = scanner.nextLine();
        
        if (dataStore.getInventario().BuscarProveedor(nit) != null) {
            System.out.println("Ya existe un proveedor con ese NIT");
            return;
        }
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        Proveedor proveedor = new Proveedor(nit, nombre);
        
        System.out.print("Telefono: ");
        proveedor.setTelefonoContacto(scanner.nextLine());
        
        System.out.print("Email: ");
        proveedor.setEmailContacto(scanner.nextLine());
        
        System.out.print("Direccion: ");
        proveedor.setDireccion(scanner.nextLine());
        
        dataStore.getInventario().AgregarProveedor(proveedor);
        System.out.println("Proveedor registrado exitosamente");
    }

    // ==================== METODOS DE LISTADO ====================
    
    /**
     * Lista todos los proveedores registrados.
     * Muestra NIT, nombre, telefono y cantidad de articulos que suministran.
     */
    public void listarProveedores() {
        System.out.println("\n=== LISTADO DE PROVEEDORES ===");
        List<Proveedor> proveedores = dataStore.getInventario().getListaProveedores();
        
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados");
            return;
        }
        
        for (Proveedor p : proveedores) {
            System.out.println(p.getNitProveedor() + " | " + 
                             p.getNombreProveedor() + " | " +
                             p.getTelefonoContacto() + " | " +
                             "Articulos: " + p.getArticulosSuministrados().size());
        }
    }

    // ==================== METODOS DE ASIGNACION ====================
    
    /**
     * Asigna un articulo a un proveedor.
     * Establece la relacion bidireccional entre ambos.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void asignarArticuloAProveedor(Scanner scanner) {
        System.out.print("NIT del proveedor: ");
        String nit = scanner.nextLine();
        
        Proveedor p = dataStore.getInventario().BuscarProveedor(nit);
        if (p == null) {
            System.out.println("Proveedor no encontrado");
            return;
        }
        
        System.out.print("Codigo del articulo: ");
        String codigo = scanner.nextLine();
        
        Articulo a = dataStore.getInventario().BuscarArticulo(codigo);
        if (a == null) {
            System.out.println("Articulo no encontrado");
            return;
        }
        
        p.agregarArticulo(a);
        a.agregarProveedor(p);
        System.out.println("Articulo asignado al proveedor");
    }

    // ==================== METODOS DE PEDIDOS ====================
    
    /**
     * Realiza un pedido a un proveedor.
     * Simula el proceso de solicitud de articulos.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void realizarPedido(Scanner scanner) {
        System.out.print("NIT del proveedor: ");
        String nit = scanner.nextLine();
        
        Proveedor p = dataStore.getInventario().BuscarProveedor(nit);
        if (p == null) {
            System.out.println("Proveedor no encontrado");
            return;
        }
        
        System.out.println("Articulos que suministra " + p.getNombreProveedor() + ":");
        for (Articulo a : p.getArticulosSuministrados()) {
            System.out.println("  " + a.getCodigoUnico() + " - " + a.getNombre());
        }
        
        System.out.println("Simulando pedido...");
        p.RealizarPedido(p.getArticulosSuministrados());
    }
    
}//fin de la clase