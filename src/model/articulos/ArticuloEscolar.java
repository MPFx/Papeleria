package model.articulos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Clase que representa un articulo escolar en el inventario.
 * Hereda de Articulo e incluye atributos especificos como grado educativo,
 * obligatoriedad y peso en gramos. Permite calcular descuentos por temporada
 * (vuelta a clases en agosto).
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see ArticuloLibreria
 * @see ArticuloOficina
 */
public class ArticuloEscolar extends Articulo {

    // ==================== ATRIBUTOS ====================
    
    /** Grado educativo al que va dirigido el articulo (PRIMARIA, SECUNDARIA, etc.). */
    private String gradoEducativo;
    
    /** Indica si el articulo es obligatorio en la lista escolar. */
    private boolean esObligatorio;
    
    /** Peso en gramos del articulo. */
    private int pesoGramos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un articulo escolar.
     * Inicializa valores por defecto: gradoEducativo "PRIMARIA",
     * esObligatorio false, pesoGramos 200.
     * 
     * @param codigoUnico Codigo unico identificador del articulo
     * @param nombre Nombre del articulo
     * @param precioBase Precio base del articulo
     */
    public ArticuloEscolar(String codigoUnico, String nombre, BigDecimal precioBase) {
        super(codigoUnico, nombre, precioBase);
        this.gradoEducativo = "PRIMARIA";
        this.esObligatorio = false;
        this.pesoGramos = 200;
    }

    // ==================== METODOS ESPECIFICOS ====================
    
    /**
     * Calcula el descuento por temporada para articulos escolares.
     * Aplica 20% de descuento durante el mes de agosto (vuelta a clases).
     * 
     * @return Porcentaje de descuento (0.20 si es agosto, 0 en caso contrario)
     */
    public BigDecimal CalcularDescuentoTemporada() {
        LocalDateTime ahora = LocalDateTime.now();
        Month mesActual = ahora.getMonth();
        
        if (mesActual == Month.AUGUST) {
            return new BigDecimal("0.20");
        }
        return BigDecimal.ZERO;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de articulo como texto.
     * 
     * @return "ESCOLAR"
     */
    @Override
    public String getTipoArticulo() {
        return "ESCOLAR";
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Grado educativo */
    public String getGradoEducativo() { return gradoEducativo; }
    
    /** @param gradoEducativo Nuevo grado educativo */
    public void setGradoEducativo(String gradoEducativo) { this.gradoEducativo = gradoEducativo; }

    /** @return true si es obligatorio */
    public boolean isEsObligatorio() { return esObligatorio; }
    
    /** @param esObligatorio Nueva condicion de obligatoriedad */
    public void setEsObligatorio(boolean esObligatorio) { this.esObligatorio = esObligatorio; }

    /** @return Peso en gramos */
    public int getPesoGramos() { return pesoGramos; }
    
    /** @param pesoGramos Nuevo peso en gramos */
    public void setPesoGramos(int pesoGramos) { this.pesoGramos = pesoGramos; }
    
}//fin de la clase