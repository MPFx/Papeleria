package model.catalogo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import model.articulos.Articulo;

/**
 * Clase que representa una categoria de productos en la papelería.
 * Permite clasificar los articulos y aplicar IVA según corresponda.
 * Cada categoria puede contener multiples articulos.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 */
public class Categoria {

    // ==================== ATRIBUTOS ====================
    
    /** Nombre de la categoria (Ej: LIBROS, CUADERNOS, BOLIGRAFOS). */
    private String nombreCategoria;
    
    /** Descripcion detallada de la categoria. */
    private String descripcionCategoria;
    
    /** Indica si los articulos de esta categoria estan sujetos a IVA. */
    private boolean esSujetoIva;
    
    /** Lista de articulos que pertenecen a esta categoria. */
    private List<Articulo> articulos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva categoria.
     * Inicializa la categoria como sujeta a IVA (true) y la lista de articulos vacia.
     * 
     * @param nombreCategoria Nombre de la categoria
     */
    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.esSujetoIva = true;
        this.articulos = new ArrayList<>();
    }

    // ==================== METODOS DE IVA ====================
    
    /**
     * Aplica el impuesto al valor agregado (IVA) a un precio.
     * En Mexico, el IVA es del 16% (factor 1.16).
     * Si la categoria no esta sujeta a IVA, retorna el precio sin cambios.
     * 
     * @param precio Precio base sin IVA
     * @return Precio con IVA aplicado (o sin el si no aplica)
     */
    public BigDecimal AplicarIva(BigDecimal precio) {
        if (esSujetoIva) {
            return precio.multiply(new BigDecimal("1.16")).setScale(2, RoundingMode.HALF_UP);
        }
        return precio;
    }

    // ==================== METODOS DE GESTION DE ARTICULOS ====================
    
    /**
     * Obtiene la lista de articulos que pertenecen a esta categoria.
     * 
     * @return Lista de articulos de la categoria
     */
    public List<Articulo> ObtenerArticulosPorCategoria() {
        return articulos;
    }

    /**
     * Agrega un articulo a la categoria.
     * No permite duplicados y establece la relacion bidireccional
     * (el articulo tambien referencia a esta categoria).
     * 
     * @param articulo Articulo a agregar a la categoria
     */
    public void agregarArticulo(Articulo articulo) {
        if (!articulos.contains(articulo)) {
            articulos.add(articulo);
            articulo.setCategoria(this);
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Nombre de la categoria */
    public String getNombreCategoria() { return nombreCategoria; }
    
    /** @param nombreCategoria Nuevo nombre de la categoria */
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    /** @return Descripcion de la categoria */
    public String getDescripcionCategoria() { return descripcionCategoria; }
    
    /** @param descripcionCategoria Nueva descripcion */
    public void setDescripcionCategoria(String descripcionCategoria) { this.descripcionCategoria = descripcionCategoria; }

    /** @return true si esta sujeta a IVA */
    public boolean isEsSujetoIva() { return esSujetoIva; }
    
    /** @param esSujetoIva Nueva condicion de IVA */
    public void setEsSujetoIva(boolean esSujetoIva) { this.esSujetoIva = esSujetoIva; }

    /** @return Lista de articulos de la categoria */
    public List<Articulo> getArticulos() { return articulos; }
    
    /** @param articulos Nueva lista de articulos */
    public void setArticulos(List<Articulo> articulos) { this.articulos = articulos; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la categoria.
     * Formato: "nombre (con IVA)" o "nombre (sin IVA)"
     * 
     * @return Cadena con el nombre y estado de IVA
     */
    @Override
    public String toString() {
        return nombreCategoria + (esSujetoIva ? " (con IVA)" : " (sin IVA)");
    }
    
}//fin de la clase