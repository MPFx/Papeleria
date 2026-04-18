package repository;

import model.articulos.*;
import model.catalogo.*;
import model.control.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase singleton que actua como almacenamiento central de datos del sistema de papelería.
 * Simula una base de datos en memoria gestionando inventario, categorias,
 * control de stock, descuentos, reportes y validaciones.
 * Contiene datos de prueba precargados para demostracion.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Inventario
 * @see Categoria
 * @see ControlStock
 * @see GestionDescuentos
 * @see Reportes
 * @see Validaciones
 */
public class DataStore {

    // ==================== ATRIBUTOS ====================
    
    /** Instancia unica del singleton DataStore. */
    private static DataStore instance;
    
    /** Inventario principal de la papelería. */
    private Inventario inventario;
    
    /** Mapa de categorias indexadas por nombre. */
    private Map<String, Categoria> categorias;
    
    /** Control de stock para alertas y revisiones. */
    private ControlStock controlStock;
    
    /** Gestion de descuentos y promociones. */
    private GestionDescuentos gestionDescuentos;
    
    /** Generador de reportes. */
    private Reportes reportes;
    
    /** Validaciones del sistema. */
    private Validaciones validaciones;

    // ==================== CONSTRUCTOR PRIVADO ====================
    
    /**
     * Constructor privado para implementar el patron singleton.
     * Inicializa el inventario, mapa de categorias y componentes de control.
     * Carga datos de prueba.
     */
    private DataStore() {
        this.inventario = new Inventario("Papeleria Mexico - Centro");
        this.categorias = new HashMap<>();
        this.controlStock = new ControlStock();
        this.gestionDescuentos = new GestionDescuentos();
        this.reportes = new Reportes();
        this.validaciones = new Validaciones();
        
        inicializarDatosPrueba();
    }

    // ==================== METODOS SINGLETON ====================
    
    /**
     * Obtiene la instancia unica del DataStore.
     * Si no existe, la crea por primera vez.
     * 
     * @return Instancia unica del DataStore
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ==================== INICIALIZACION DE DATOS ====================
    
    /**
     * Inicializa datos de prueba para demostracion.
     * Crea categorias (Libreria, Oficina, Escolar), proveedores,
     * y articulos de ejemplo con sus relaciones.
     */
    private void inicializarDatosPrueba() {
        // Crear categorias
        Categoria catLibreria = new Categoria("LIBRERIA");
        catLibreria.setDescripcionCategoria("Libros, revistas y material de lectura");
        categorias.put(catLibreria.getNombreCategoria(), catLibreria);

        Categoria catOficina = new Categoria("OFICINA");
        catOficina.setDescripcionCategoria("Articulos de oficina y papelería");
        categorias.put(catOficina.getNombreCategoria(), catOficina);

        Categoria catEscolar = new Categoria("ESCOLAR");
        catEscolar.setDescripcionCategoria("Utiles escolares");
        categorias.put(catEscolar.getNombreCategoria(), catEscolar);

        // Crear proveedores
        Proveedor prov1 = new Proveedor("123456789-1", "Distribuidora de Papel SA");
        prov1.setTelefonoContacto("555-1234");
        prov1.setEmailContacto("ventas@distribuidora.com");
        prov1.setDireccion("Av. Reforma 123, CDMX");
        inventario.AgregarProveedor(prov1);

        Proveedor prov2 = new Proveedor("987654321-2", "Utiles Escolares de Mexico");
        prov2.setTelefonoContacto("555-5678");
        prov2.setEmailContacto("pedidos@utiles.com");
        inventario.AgregarProveedor(prov2);

        // Crear articulos de librería
        ArticuloLibreria libro = new ArticuloLibreria("LIB001", "El Principito", new BigDecimal("250.00"));
        libro.setCantidadStock(15);
        libro.setStockMinimo(5);
        libro.setCategoria(catLibreria);
        libro.setTipoPapel("BOND");
        libro.agregarProveedor(prov1);
        inventario.AgregarArticulo(libro);
        catLibreria.agregarArticulo(libro);
        prov1.agregarArticulo(libro);

        ArticuloLibreria cuaderno = new ArticuloLibreria("LIB002", "Cuaderno Profesional", new BigDecimal("85.00"));
        cuaderno.setCantidadStock(50);
        cuaderno.setStockMinimo(20);
        cuaderno.setCategoria(catLibreria);
        cuaderno.setTipoPapel("CUADRICULADO");
        cuaderno.agregarProveedor(prov1);
        inventario.AgregarArticulo(cuaderno);
        catLibreria.agregarArticulo(cuaderno);

        // Crear articulos de oficina
        ArticuloOficina resma = new ArticuloOficina("OFI001", "Resma Papel Carta", new BigDecimal("120.00"));
        resma.setCantidadStock(30);
        resma.setStockMinimo(10);
        resma.setCategoria(catOficina);
        resma.setTamano("CARTA");
        resma.setTipoEmpaque("RESMA");
        resma.agregarProveedor(prov1);
        inventario.AgregarArticulo(resma);
        catOficina.agregarArticulo(resma);

        ArticuloOficina folder = new ArticuloOficina("OFI002", "Folder Tamano Oficio", new BigDecimal("35.00"));
        folder.setCantidadStock(100);
        folder.setStockMinimo(25);
        folder.setCategoria(catOficina);
        folder.agregarProveedor(prov1);
        inventario.AgregarArticulo(folder);
        catOficina.agregarArticulo(folder);

        // Crear articulos escolares
        ArticuloEscolar lapiz = new ArticuloEscolar("ESC001", "Lapiz Mongol", new BigDecimal("8.50"));
        lapiz.setCantidadStock(200);
        lapiz.setStockMinimo(50);
        lapiz.setCategoria(catEscolar);
        lapiz.setGradoEducativo("PRIMARIA");
        lapiz.setEsObligatorio(true);
        lapiz.agregarProveedor(prov2);
        inventario.AgregarArticulo(lapiz);
        catEscolar.agregarArticulo(lapiz);

        ArticuloEscolar colores = new ArticuloEscolar("ESC002", "Caja de Colores 12pzs", new BigDecimal("45.00"));
        colores.setCantidadStock(75);
        colores.setStockMinimo(20);
        colores.setCategoria(catEscolar);
        colores.setGradoEducativo("PREESCOLAR");
        colores.agregarProveedor(prov2);
        inventario.AgregarArticulo(colores);
        catEscolar.agregarArticulo(colores);
    }

    // ==================== GETTERS ====================
    
    /** @return Inventario principal */
    public Inventario getInventario() { return inventario; }
    
    /** @return Mapa de categorias */
    public Map<String, Categoria> getCategorias() { return categorias; }
    
    /** @return Control de stock */
    public ControlStock getControlStock() { return controlStock; }
    
    /** @return Gestion de descuentos */
    public GestionDescuentos getGestionDescuentos() { return gestionDescuentos; }
    
    /** @return Generador de reportes */
    public Reportes getReportes() { return reportes; }
    
    /** @return Validaciones del sistema */
    public Validaciones getValidaciones() { return validaciones; }

    // ==================== METODOS CRUD PARA CATEGORIAS ====================
    
    /** @return Lista de todas las categorias */
    public List<Categoria> getAllCategorias() {
        return new ArrayList<>(categorias.values());
    }

    /**
     * Obtiene una categoria por su nombre.
     * 
     * @param nombre Nombre de la categoria
     * @return Categoria encontrada o null
     */
    public Categoria getCategoria(String nombre) {
        return categorias.get(nombre.toUpperCase());
    }

    /** @param categoria Categoria a agregar */
    public void addCategoria(Categoria categoria) {
        categorias.put(categoria.getNombreCategoria().toUpperCase(), categoria);
    }
    
}//fin de la clase