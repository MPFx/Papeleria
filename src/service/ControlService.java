package service;

import model.articulos.Articulo;
import model.catalogo.Inventario;
import model.catalogo.Categoria;
import model.control.*;
import repository.DataStore;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Clase de servicio que gestiona las operaciones de control del sistema.
 * Proporciona funcionalidades para verificar stocks minimos, aplicar descuentos,
 * generar reportes y validar ventas.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ControlStock
 * @see GestionDescuentos
 * @see Reportes
 * @see Validaciones
 * @see DataStore
 */
public class ControlService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de control.
     * Obtiene la instancia unica del DataStore.
     */
    public ControlService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== CONTROL DE STOCK ====================
    
    /**
     * Verifica y muestra los articulos que tienen stock por debajo del minimo.
     */
    public void verificarStocksMinimos() {
        System.out.println("\n=== VERIFICACION DE STOCKS MINIMOS ===");
        ControlStock control = dataStore.getControlStock();
        Inventario inventario = dataStore.getInventario();
        
        List<Articulo> bajoStock = control.VerificarStocksMinimos(inventario);
        
        if (bajoStock.isEmpty()) {
            System.out.println("Todos los articulos estan por encima de su stock minimo");
        } else {
            System.out.println("Articulos con stock bajo:");
            for (Articulo a : bajoStock) {
                System.out.println(control.GenerarAlertaStock(a));
            }
        }
        
        System.out.println("Ultima revision: " + control.getFechaUltimaRevision());
    }

    // ==================== GESTION DE DESCUENTOS ====================
    
    /**
     * Aplica descuentos a articulos o categorias.
     * Permite seleccionar entre descuento por categoria,
     * descuento especial por articulo o activar temporada promocional.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void aplicarDescuentos(Scanner scanner) {
        System.out.println("\n=== APLICAR DESCUENTOS ===");
        GestionDescuentos descuentos = dataStore.getGestionDescuentos();
        Inventario inventario = dataStore.getInventario();
        
        System.out.println("Tipo de descuento:");
        System.out.println("1. Por categoria");
        System.out.println("2. Especial por articulo");
        System.out.println("3. Activar temporada");
        System.out.print("Seleccione: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                System.out.print("Nombre de la categoria: ");
                String nomCat = scanner.nextLine().toUpperCase();
                Categoria cat = dataStore.getCategoria(nomCat);
                if (cat != null) {
                    System.out.print("Porcentaje de descuento (ej: 0.10 para 10%): ");
                    BigDecimal porcentaje = new BigDecimal(scanner.nextLine());
                    descuentos.AplicarDescuentoCategoria(cat, porcentaje);
                    System.out.println("Descuento aplicado a categoria " + nomCat);
                }
                break;
                
            case 2:
                System.out.print("Codigo del articulo: ");
                String codigo = scanner.nextLine();
                Articulo a = inventario.BuscarArticulo(codigo);
                if (a != null) {
                    BigDecimal desc = descuentos.AplicarDescuentoEspecial(a);
                    if (desc.compareTo(BigDecimal.ZERO) > 0) {
                        System.out.println("Descuento especial del " + desc + "% aplicado");
                    } else {
                        System.out.println("No aplica descuento especial");
                    }
                }
                break;
                
            case 3:
                System.out.println("Temporadas disponibles:");
                System.out.println("- VUELTA A CLASES");
                System.out.println("- NAVIDAD");
                System.out.print("Temporada: ");
                String temporada = scanner.nextLine().toUpperCase();
                descuentos.activarTemporada(temporada);
                System.out.println("Temporada activada: " + temporada);
                break;
        }
    }

    // ==================== REPORTES ====================
    
    /**
     * Genera reportes del sistema.
     * Permite seleccionar entre reporte de inventario completo,
     * reporte valorizado o reporte de proveedores.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void generarReportes(Scanner scanner) {
        System.out.println("\n=== GENERAR REPORTES ===");
        Reportes reportes = dataStore.getReportes();
        Inventario inventario = dataStore.getInventario();
        
        System.out.println("Tipo de reporte:");
        System.out.println("1. Inventario completo");
        System.out.println("2. Valorizado");
        System.out.println("3. Proveedores");
        System.out.print("Seleccione: ");
        
        int opcion = Integer.parseInt(scanner.nextLine());
        
        switch (opcion) {
            case 1:
                System.out.println(reportes.GenerarReporteInventario(inventario));
                break;
            case 2:
                BigDecimal valor = reportes.GenerarReporteValorizado(inventario);
                System.out.println("Valor total del inventario: $" + valor);
                break;
            case 3:
                String reporteProv = reportes.GenerarReporteProveedores(
                    inventario.getListaProveedores());
                System.out.println(reporteProv);
                break;
        }
        
        System.out.print("Exportar a CSV? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            reportes.ExportarACSV("reporte");
        }
    }

    // ==================== VALIDACIONES ====================
    
    /**
     * Valida si una venta es posible para un articulo y cantidad.
     * Verifica existencia, estado activo y stock suficiente.
     * 
     * @param scanner Scanner para entrada de datos
     */
    public void validarVenta(Scanner scanner) {
        System.out.println("\n=== VALIDAR VENTA ===");
        Validaciones validaciones = dataStore.getValidaciones();
        Inventario inventario = dataStore.getInventario();
        
        System.out.print("Codigo del articulo: ");
        String codigo = scanner.nextLine();
        
        Articulo a = inventario.BuscarArticulo(codigo);
        if (a == null) {
            System.out.println("Articulo no encontrado");
            return;
        }
        
        System.out.print("Cantidad a vender: ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        
        String resultado = validaciones.ValidarArticuloParaVenta(a, cantidad);
        if (resultado.equals("OK")) {
            System.out.println("Venta valida. Total: $" + 
                a.getPrecioBase().multiply(new BigDecimal(cantidad)));
        } else {
            System.out.println("Error: " + resultado);
        }
    }
    
}//fin de la clase