package model.control;

import model.articulos.Articulo;
import model.articulos.ArticuloEscolar;
import model.articulos.ArticuloLibreria;
import model.articulos.ArticuloOficina;
import model.catalogo.Categoria;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la aplicacion de descuentos en el sistema.
 * Permite aplicar descuentos por categoria, descuentos especiales
 * por tipo de articulo, descuentos por cantidad y activar temporadas
 * promocionales.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Categoria
 */
public class GestionDescuentos {

    // ==================== ATRIBUTOS ====================
    
    /** Lista de descuentos actualmente activos. */
    private List<String> descuentosActivos;
    
    /** Temporada de descuentos actual (NINGUNA, VUELTA A CLASES, NAVIDAD). */
    private String temporadaDescuentos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor de la gestion de descuentos.
     * Inicializa la lista de descuentos activos como vacia
     * y la temporada como "NINGUNA".
     */
    public GestionDescuentos() {
        this.descuentosActivos = new ArrayList<>();
        this.temporadaDescuentos = "NINGUNA";
    }

    // ==================== METODOS DE DESCUENTOS ====================
    
    /**
     * Aplica un descuento a todos los articulos de una categoria.
     * 
     * @param categoria Categoria a la que aplicar el descuento
     * @param porcentaje Porcentaje de descuento (ej: 0.15 = 15%)
     */
    public void AplicarDescuentoCategoria(Categoria categoria, BigDecimal porcentaje) {
        for (Articulo a : categoria.getArticulos()) {
            a.AplicarDescuento(porcentaje);
        }
        descuentosActivos.add("Descuento del " + porcentaje + "% en categoria " + categoria.getNombreCategoria());
    }

    /**
     * Aplica un descuento especial a un articulo segun su tipo.
     * Utiliza los metodos especificos de cada subclase:
     * - ArticuloLibreria: descuento por peso
     * - ArticuloOficina: descuento por volumen
     * - ArticuloEscolar: descuento por temporada
     * 
     * @param articulo Articulo a evaluar
     * @return Porcentaje de descuento aplicado
     */
    public BigDecimal AplicarDescuentoEspecial(Articulo articulo) {
        BigDecimal descuento = BigDecimal.ZERO;
        
        if (articulo instanceof ArticuloLibreria) {
            descuento = ((ArticuloLibreria) articulo).CalcularDescuentoEspecial();
        } else if (articulo instanceof ArticuloOficina) {
            descuento = ((ArticuloOficina) articulo).CalcularDescuentoPorVolumen();
        } else if (articulo instanceof ArticuloEscolar) {
            descuento = ((ArticuloEscolar) articulo).CalcularDescuentoTemporada();
        }
        
        if (descuento.compareTo(BigDecimal.ZERO) > 0) {
            articulo.AplicarDescuento(descuento);
            descuentosActivos.add("Descuento especial del " + descuento + "% en " + articulo.getNombre());
        }
        
        return descuento;
    }

    /**
     * Calcula el descuento por cantidad de unidades compradas.
     * Escala de descuentos:
     * - 10+ unidades: 5%
     * - 20+ unidades: 10%
     * - 50+ unidades: 15%
     * - 100+ unidades: 20%
     * 
     * @param cantidad Cantidad de unidades
     * @return Porcentaje de descuento aplicable
     */
    public BigDecimal CalcularDescuentoPorCantidad(int cantidad) {
        if (cantidad >= 100) {
            return new BigDecimal("0.20");
        } else if (cantidad >= 50) {
            return new BigDecimal("0.15");
        } else if (cantidad >= 20) {
            return new BigDecimal("0.10");
        } else if (cantidad >= 10) {
            return new BigDecimal("0.05");
        }
        return BigDecimal.ZERO;
    }

    /**
     * Activa una temporada de descuentos.
     * Aplica descuentos predefinidos segun la temporada:
     * - VUELTA A CLASES: 15% en categoria ESCOLAR
     * - NAVIDAD: 10% en categoria OFICINA
     * 
     * @param temporada Nombre de la temporada
     */
    public void activarTemporada(String temporada) {
        this.temporadaDescuentos = temporada;
        switch (temporada) {
            case "VUELTA A CLASES":
                AplicarDescuentoCategoria(new Categoria("ESCOLAR"), new BigDecimal("0.15"));
                break;
            case "NAVIDAD":
                AplicarDescuentoCategoria(new Categoria("OFICINA"), new BigDecimal("0.10"));
                break;
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Lista de descuentos activos */
    public List<String> getDescuentosActivos() { return descuentosActivos; }
    
    /** @param descuentosActivos Nueva lista de descuentos */
    public void setDescuentosActivos(List<String> descuentosActivos) { this.descuentosActivos = descuentosActivos; }

    /** @return Temporada de descuentos actual */
    public String getTemporadaDescuentos() { return temporadaDescuentos; }
    
    /** @param temporadaDescuentos Nueva temporada */
    public void setTemporadaDescuentos(String temporadaDescuentos) { this.temporadaDescuentos = temporadaDescuentos; }
    
}//fin de la clase