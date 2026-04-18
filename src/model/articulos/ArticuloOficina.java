package model.articulos;

import java.math.BigDecimal;

/**
 * Clase que representa un articulo de oficina en el inventario.
 * Hereda de Articulo e incluye atributos especificos como tamano,
 * tipo de empaque y peso en gramos. Permite calcular descuentos
 * por volumen de compra.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see ArticuloLibreria
 * @see ArticuloEscolar
 */
public class ArticuloOficina extends Articulo {

    // ==================== ATRIBUTOS ====================
    
    /** Tamano del articulo (CARTA, OFICIO, A4, etc.). */
    private String tamano;
    
    /** Tipo de empaque del producto (CAJA, PAQUETE, UNIDAD). */
    private String tipoEmpaque;
    
    /** Peso en gramos del articulo. */
    private int pesoGramos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un articulo de oficina.
     * Inicializa valores por defecto: tamano "CARTA",
     * tipoEmpaque "CAJA", pesoGramos 500.
     * 
     * @param codigoUnico Codigo unico identificador del articulo
     * @param nombre Nombre del articulo
     * @param precioBase Precio base del articulo
     */
    public ArticuloOficina(String codigoUnico, String nombre, BigDecimal precioBase) {
        super(codigoUnico, nombre, precioBase);
        this.tamano = "CARTA";
        this.tipoEmpaque = "CAJA";
        this.pesoGramos = 500;
    }

    // ==================== METODOS ESPECIFICOS ====================
    
    /**
     * Calcula el descuento por volumen para articulos de oficina.
     * Aplica 15% de descuento si el stock es mayor a 50 unidades.
     * 
     * @return Porcentaje de descuento (0.15 si aplica, 0 en caso contrario)
     */
    public BigDecimal CalcularDescuentoPorVolumen() {
        if (cantidadStock > 50) {
            return new BigDecimal("0.15");
        }
        return BigDecimal.ZERO;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de articulo como texto.
     * 
     * @return "OFICINA"
     */
    @Override
    public String getTipoArticulo() {
        return "OFICINA";
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Tamano del articulo */
    public String getTamano() { return tamano; }
    
    /** @param tamano Nuevo tamano */
    public void setTamano(String tamano) { this.tamano = tamano; }

    /** @return Tipo de empaque */
    public String getTipoEmpaque() { return tipoEmpaque; }
    
    /** @param tipoEmpaque Nuevo tipo de empaque */
    public void setTipoEmpaque(String tipoEmpaque) { this.tipoEmpaque = tipoEmpaque; }

    /** @return Peso en gramos */
    public int getPesoGramos() { return pesoGramos; }
    
    /** @param pesoGramos Nuevo peso en gramos */
    public void setPesoGramos(int pesoGramos) { this.pesoGramos = pesoGramos; }
    
}//fin de la clase