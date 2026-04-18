package model.articulos;

import java.math.BigDecimal;

/**
 * Clase que representa un articulo de libreria en el inventario.
 * Hereda de Articulo e incluye atributos especificos como tipo de papel,
 * color de tinta y peso en gramos. Permite calcular descuentos especiales
 * para papeles pesados.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see ArticuloOficina
 * @see ArticuloEscolar
 */
public class ArticuloLibreria extends Articulo {

    // ==================== ATRIBUTOS ====================
    
    /** Tipo de papel (Blanco, Reciclado, Bond, etc.). */
    private String tipoPapel;
    
    /** Color de tinta del producto. */
    private String colorTinta;
    
    /** Peso en gramos del articulo. */
    private int pesoGramos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un articulo de libreria.
     * Inicializa valores por defecto: tipoPapel "BLANCO",
     * colorTinta "NEGRO", pesoGramos 75.
     * 
     * @param codigoUnico Codigo unico identificador del articulo
     * @param nombre Nombre del articulo
     * @param precioBase Precio base del articulo
     */
    public ArticuloLibreria(String codigoUnico, String nombre, BigDecimal precioBase) {
        super(codigoUnico, nombre, precioBase);
        this.tipoPapel = "BLANCO";
        this.colorTinta = "NEGRO";
        this.pesoGramos = 75;
    }

    // ==================== METODOS ESPECIFICOS ====================
    
    /**
     * Calcula el descuento especial para articulos de libreria.
     * Aplica 10% de descuento si el peso es mayor a 100 gramos.
     * 
     * @return Porcentaje de descuento (0.10 si aplica, 0 en caso contrario)
     */
    public BigDecimal CalcularDescuentoEspecial() {
        if (pesoGramos > 100) {
            return new BigDecimal("0.10");
        }
        return BigDecimal.ZERO;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de articulo como texto.
     * 
     * @return "LIBRERIA"
     */
    @Override
    public String getTipoArticulo() {
        return "LIBRERIA";
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Tipo de papel */
    public String getTipoPapel() { return tipoPapel; }
    
    /** @param tipoPapel Nuevo tipo de papel */
    public void setTipoPapel(String tipoPapel) { this.tipoPapel = tipoPapel; }

    /** @return Color de tinta */
    public String getColorTinta() { return colorTinta; }
    
    /** @param colorTinta Nuevo color de tinta */
    public void setColorTinta(String colorTinta) { this.colorTinta = colorTinta; }

    /** @return Peso en gramos */
    public int getPesoGramos() { return pesoGramos; }
    
    /** @param pesoGramos Nuevo peso en gramos */
    public void setPesoGramos(int pesoGramos) { this.pesoGramos = pesoGramos; }
    
}//fin de la clase