package model.control;

import model.articulos.Articulo;
import model.catalogo.Inventario;
import model.catalogo.Proveedor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase que genera reportes del sistema de inventario.
 * Permite generar reportes de inventario, reportes valorizados,
 * reportes de proveedores y exportar a formato CSV.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Inventario
 * @see Proveedor
 */
public class Reportes {

    // ==================== ATRIBUTOS ====================
    
    /** Fecha y hora de generacion del reporte. */
    private LocalDateTime fechaGeneracion;
    
    /** Tipo de reporte generado. */
    private String tipoReporte;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor de la clase Reportes.
     * Inicializa la fecha de generacion con la fecha y hora actual.
     */
    public Reportes() {
        this.fechaGeneracion = LocalDateTime.now();
    }

    // ==================== METODOS DE GENERACION ====================
    
    /**
     * Genera un reporte completo del inventario.
     * Incluye nombre de tienda, fecha, total de articulos,
     * valor total y listado detallado por articulo.
     * 
     * @param inventario Inventario a reportar
     * @return Reporte formateado como String
     */
    public String GenerarReporteInventario(Inventario inventario) {
        StringBuilder reporte = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        reporte.append("\n REPORTE DE INVENTARIO \n");
        reporte.append("================================\n");
        reporte.append("Tienda: ").append(inventario.getNombreTienda()).append("\n");
        reporte.append("Fecha: ").append(fechaGeneracion.format(formatter)).append("\n");
        reporte.append("================================\n\n");
        
        reporte.append("RESUMEN:\n");
        reporte.append("Total articulos: ").append(inventario.getListaArticulos().size()).append("\n");
        reporte.append("Valor total: $").append(inventario.calcularValorTotalInventario()).append("\n\n");
        
        reporte.append("ARTICULOS POR CATEGORIA:\n");
        for (Articulo a : inventario.getListaArticulos()) {
            reporte.append(String.format("  %-10s %-30s $%-8.2f Stock: %d\n",
                a.getCodigoUnico(),
                a.getNombre(),
                a.getPrecioBase(),
                a.getCantidadStock()));
        }
        
        return reporte.toString();
    }

    /**
     * Genera un reporte valorizado del inventario.
     * 
     * @param inventario Inventario a valorizar
     * @return Valor total del inventario
     */
    public BigDecimal GenerarReporteValorizado(Inventario inventario) {
        return inventario.calcularValorTotalInventario();
    }

    /**
     * Genera un reporte de todos los proveedores registrados.
     * Incluye nombre, NIT, contacto y cantidad de articulos que suministra.
     * 
     * @param proveedores Lista de proveedores
     * @return Reporte formateado como String
     */
    public String GenerarReporteProveedores(List<Proveedor> proveedores) {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("\n REPORTE DE PROVEEDORES \n");
        reporte.append("================================\n");
        
        for (Proveedor p : proveedores) {
            reporte.append("Proveedor: ").append(p.getNombreProveedor()).append("\n");
            reporte.append("  NIT: ").append(p.getNitProveedor()).append("\n");
            reporte.append("  Contacto: ").append(p.getTelefonoContacto()).append("\n");
            reporte.append("  Articulos que suministra: ").append(p.getArticulosSuministrados().size()).append("\n");
            reporte.append("  ------------------------------\n");
        }
        
        return reporte.toString();
    }

    /**
     * Exporta un reporte a formato CSV.
     * Simula la exportacion mostrando el nombre del archivo.
     * 
     * @param reporte Contenido del reporte a exportar
     * @return Nombre del archivo generado
     */
    public String ExportarACSV(String reporte) {
        String nombreArchivo = "reporte_" + fechaGeneracion.toLocalDate() + ".csv";
        System.out.println("Exportando a CSV: " + nombreArchivo);
        return nombreArchivo;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @param tipoReporte Nuevo tipo de reporte */
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }
    
    /** @return Fecha de generacion del reporte */
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    
    /** @return Tipo de reporte */
    public String getTipoReporte() { return tipoReporte; }
    
}//fin de la clase