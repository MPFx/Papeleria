package service;

import model.articulos.*;
import model.catalogo.*;
import repository.DataStore;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Clase de servicio que gestiona las operaciones del inventario.
 * Proporciona funcionalidades para agregar articulos, listarlos,
 * buscar por codigo y ajustar stock.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Inventario
 * @see DataStore
 */
public class InventarioService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de inventario.
     * Obtiene la instancia unica del DataStore.
     */
    public InventarioService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE CREACION ====================
    
    /**
     * Agrega un nuevo articulo al inventario.
     * Solicita al usuario los datos segun el tipo de articulo
     * (Libreria, Oficina, Escolar).
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void agregarArticulo(Scanner scanner) {
        System.out.println("\n=== AGREGAR NUEVO ARTICULO ===");
        
        System.out.print("Codigo unico: ");
        String codigo = scanner.nextLine();
        
        Inventario inventario = dataStore.getInventario();
        if (inventario.BuscarArticulo(codigo) != null) {
            System.out.println("Ya existe un articulo con ese codigo");
            return;
        }
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio base: $");
        BigDecimal precio = new BigDecimal(scanner.nextLine());
        
        System.out.println("Tipo de articulo:");
        System.out.println("1. Librería");
        System.out.println("2. Oficina");
        System.out.println("3. Escolar");
        System.out.print("Seleccione: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        Articulo articulo = null;
        
        switch (tipo) {
            case 1:
                articulo = new ArticuloLibreria(codigo, nombre, precio);
                System.out.print("Tipo de papel: ");
                ((ArticuloLibreria) articulo).setTipoPapel(scanner.nextLine());
                break;
            case 2:
                articulo = new ArticuloOficina(codigo, nombre, precio);
                System.out.print("Tamano: ");
                ((ArticuloOficina) articulo).setTamano(scanner.nextLine());
                break;
            case 3:
                articulo = new ArticuloEscolar(codigo, nombre, precio);
                System.out.print("Grado educativo: ");
                ((ArticuloEscolar) articulo).setGradoEducativo(scanner.nextLine());
                break;
        }
        
        if (articulo != null) {
            System.out.print("Stock inicial: ");
            articulo.setCantidadStock(Integer.parseInt(scanner.nextLine()));
            
            System.out.print("Stock minimo: ");
            articulo.setStockMinimo(Integer.parseInt(scanner.nextLine()));
            
            System.out.println("Categorias disponibles:");
            List<Categoria> categorias = dataStore.getAllCategorias();
            for (Categoria c : categorias) {
                System.out.println("  " + c.getNombreCategoria());
            }
            System.out.print("Categoria: ");
            String nomCat = scanner.nextLine().toUpperCase();
            Categoria cat = dataStore.getCategoria(nomCat);
            if (cat != null) {
                articulo.setCategoria(cat);
                cat.agregarArticulo(articulo);
            }
            
            inventario.AgregarArticulo(articulo);
            System.out.println("Articulo agregado exitosamente");
        }
    }

    // ==================== METODOS DE LISTADO ====================
    
    /**
     * Lista todos los articulos del inventario.
     * Muestra codigo, nombre, precio, stock, tipo y estado.
     */
    public void listarArticulos() {
        System.out.println("\n=== LISTADO DE ARTICULOS ===");
        Inventario inventario = dataStore.getInventario();
        List<Articulo> articulos = inventario.getListaArticulos();
        
        if (articulos.isEmpty()) {
            System.out.println("No hay articulos en el inventario");
            return;
        }
        
        for (Articulo a : articulos) {
            System.out.println(a.getCodigoUnico() + " | " + 
                             a.getNombre() + " | " +
                             "$" + a.getPrecioBase() + " | " +
                             "Stock: " + a.getCantidadStock() + " | " +
                             "Tipo: " + a.getTipoArticulo() + " | " +
                             (a.isEstaActivo() ? "Activo" : "Inactivo"));
        }
    }

    // ==================== METODOS DE BUSQUEDA ====================
    
    /**
     * Busca y muestra los detalles completos de un articulo por su codigo.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void buscarArticulo(Scanner scanner) {
        System.out.print("Codigo del articulo: ");
        String codigo = scanner.nextLine();
        
        Articulo a = dataStore.getInventario().BuscarArticulo(codigo);
        if (a == null) {
            System.out.println("Articulo no encontrado");
            return;
        }
        
        System.out.println("\n=== DATOS DEL ARTICULO ===");
        System.out.println("Codigo: " + a.getCodigoUnico());
        System.out.println("Nombre: " + a.getNombre());
        System.out.println("Tipo: " + a.getTipoArticulo());
        System.out.println("Precio base: $" + a.getPrecioBase());
        System.out.println("Precio final: $" + a.CalcularPrecioFinal());
        System.out.println("Stock: " + a.getCantidadStock());
        System.out.println("Stock minimo: " + a.getStockMinimo());
        System.out.println("Categoria: " + (a.getCategoria() != null ? a.getCategoria().getNombreCategoria() : "Sin categoria"));
        System.out.println("Calificacion: " + a.getCalificacionPromedio() + " estrellas");
        System.out.println("Estado: " + (a.isEstaActivo() ? "Activo" : "Inactivo"));
        
        if (!a.getProveedores().isEmpty()) {
            System.out.println("Proveedores:");
            for (Proveedor p : a.getProveedores()) {
                System.out.println("  - " + p.getNombreProveedor());
            }
        }
    }

    // ==================== METODOS DE AJUSTE ====================
    
    /**
     * Ajusta el stock de un articulo existente.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void ajustarStock(Scanner scanner) {
        System.out.print("Codigo del articulo: ");
        String codigo = scanner.nextLine();
        
        Articulo a = dataStore.getInventario().BuscarArticulo(codigo);
        if (a == null) {
            System.out.println("Articulo no encontrado");
            return;
        }
        
        System.out.println("Stock actual: " + a.getCantidadStock());
        System.out.print("Nuevo stock: ");
        int nuevoStock = Integer.parseInt(scanner.nextLine());
        a.setCantidadStock(nuevoStock);
        System.out.println("Stock actualizado");
        
        if (!a.ValidarStockMinimo()) {
            System.out.println("El articulo esta por debajo del stock minimo");
        }
    }
    
}//fin de la clase