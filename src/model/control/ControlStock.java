package model.control;

import model.articulos.Articulo;
import model.catalogo.Inventario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que gestiona el control de stock del inventario.
 * Permite verificar articulos con stock minimo, generar alertas,
 * calcular rotacion de inventario y enviar notificaciones por email.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Inventario
 */
public class ControlStock {

    // ==================== ATRIBUTOS ====================
    
    /** Lista de articulos que se encuentran por debajo del stock minimo. */
    private List<Articulo> articulosBajoStock;
    
    /** Fecha y hora de la ultima revision de stock. */
    private LocalDateTime fechaUltimaRevision;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del control de stock.
     * Inicializa la fecha de ultima revision con la fecha y hora actual.
     */
    public ControlStock() {
        this.fechaUltimaRevision = LocalDateTime.now();
    }

    // ==================== METODOS DE VERIFICACION ====================
    
    /**
     * Verifica los articulos que tienen stock por debajo del minimo requerido.
     * Actualiza la lista de articulos bajo stock y la fecha de revision.
     * 
     * @param inventario Inventario a verificar
     * @return Lista de articulos con stock insuficiente
     */
    public List<Articulo> VerificarStocksMinimos(Inventario inventario) {
        this.articulosBajoStock = inventario.GenerarReporteStockMinimo();
        this.fechaUltimaRevision = LocalDateTime.now();
        return articulosBajoStock;
    }

    /**
     * Genera un mensaje de alerta para un articulo con stock bajo.
     * 
     * @param articulo Articulo con stock insuficiente
     * @return Mensaje de alerta formateado
     */
    public String GenerarAlertaStock(Articulo articulo) {
        return "ALERTA: El articulo " + articulo.getNombre() + 
               " tiene stock bajo (" + articulo.getCantidadStock() + 
               " unidades). Minimo requerido: " + articulo.getStockMinimo();
    }

    /**
     * Calcula la rotacion de stock de un articulo en un periodo determinado.
     * Simulacion basada en ventas hipoteticas (30% del stock actual).
     * 
     * @param articulo Articulo a analizar
     * @param periodo Periodo en dias (no utilizado en simulacion)
     * @return Indice de rotacion de stock
     */
    public BigDecimal CalcularRotacionStock(Articulo articulo, int periodo) {
        double rotacion = articulo.getCantidadStock() * 0.3;
        return new BigDecimal(rotacion).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Envia alertas por email para todos los articulos con stock bajo.
     * Simula el envio mostrando mensajes en consola.
     * 
     * @param inventario Inventario a verificar
     */
    public void enviarAlertasPorEmail(Inventario inventario) {
        List<Articulo> bajoStock = VerificarStocksMinimos(inventario);
        for (Articulo a : bajoStock) {
            System.out.println("Enviando alerta por email: " + GenerarAlertaStock(a));
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Lista de articulos con stock bajo */
    public List<Articulo> getArticulosBajoStock() { return articulosBajoStock; }
    
    /** @param articulosBajoStock Nueva lista de articulos bajo stock */
    public void setArticulosBajoStock(List<Articulo> articulosBajoStock) { this.articulosBajoStock = articulosBajoStock; }

    /** @return Fecha de la ultima revision */
    public LocalDateTime getFechaUltimaRevision() { return fechaUltimaRevision; }
    
    /** @param fechaUltimaRevision Nueva fecha de revision */
    public void setFechaUltimaRevision(LocalDateTime fechaUltimaRevision) { this.fechaUltimaRevision = fechaUltimaRevision; }
    
}//fin de la clase