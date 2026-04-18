package model.articulos;

import java.math.BigDecimal;
import model.catalogo.Proveedor;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.catalogo.Categoria;

/**
 * Clase abstracta que representa un articulo en el inventario de la papelería.
 * Contiene los atributos y comportamientos comunes a todos los tipos de articulos
 * (Libreria, Oficina, Escolar). Gestiona informacion como precio, stock,
 * categoría, proveedores, calificaciones y descuentos.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ArticuloLibreria
 * @see ArticuloOficina
 *see ArticuloEscolar
 * @see Categoria
 * @see Proveedor
 */
public abstract class Articulo {

    // ==================== ATRIBUTOS ====================
    
    /** Codigo unico identificador del articulo. */
    protected String codigoUnico;
    
    /** Nombre del articulo. */
    protected String nombre;
    
    /** Precio base del articulo sin descuentos ni impuestos. */
    protected BigDecimal precioBase;
    
    /** Cantidad actual en stock del articulo. */
    protected int cantidadStock;
    
    /** Categoria a la que pertenece el articulo. */
    protected Categoria categoria;
    
    /** Stock minimo para generar alertas de reabastecimiento. */
    protected int stockMinimo;
    
    /** Porcentaje de descuento aplicable al articulo. */
    protected BigDecimal descuentoAplicable;
    
    /** Fecha de ingreso del articulo al inventario. */
    protected LocalDateTime fechaIngreso;
    
    /** Indica si el articulo esta activo en el inventario. */
    protected boolean estaActivo;
    
    /** Indica si el articulo es importado. */
    protected boolean esImportado;
    
    /** Calificacion promedio del articulo (1-5 estrellas). */
    protected BigDecimal calificacionPromedio;
    
    /** Lista de proveedores que suministran el articulo. */
    protected List<Proveedor> proveedores;
    
    /** Lista de calificaciones individuales para calcular promedio. */
    protected List<Integer> calificaciones;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo articulo.
     * Inicializa stock en 0, stock minimo en 5, descuento en 0,
     * fecha de ingreso actual, activo true, calificacion promedio 0,
     * y listas de proveedores y calificaciones vacias.
     * 
     * @param codigoUnico Codigo unico identificador del articulo
     * @param nombre Nombre del articulo
     * @param precioBase Precio base del articulo
     */
    public Articulo(String codigoUnico, String nombre, BigDecimal precioBase) {
        this.codigoUnico = codigoUnico;
        this.nombre = nombre;
        this.precioBase = precioBase.setScale(2, RoundingMode.HALF_UP);
        this.cantidadStock = 0;
        this.stockMinimo = 5;
        this.descuentoAplicable = BigDecimal.ZERO;
        this.fechaIngreso = LocalDateTime.now();
        this.estaActivo = true;
        this.calificacionPromedio = BigDecimal.ZERO;
        this.proveedores = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula el precio final del articulo aplicando descuentos e IVA.
     * Formula: (precioBase - descuento) + IVA de la categoria.
     * 
     * @return Precio final redondeado a 2 decimales
     */
    public BigDecimal CalcularPrecioFinal() {
        BigDecimal precioConDescuento = precioBase.multiply(
            BigDecimal.ONE.subtract(descuentoAplicable)
        );
        
        if (categoria != null) {
            precioConDescuento = categoria.AplicarIva(precioConDescuento);
        }
        
        return precioConDescuento.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Aplica un descuento al articulo.
     * 
     * @param porcentaje Porcentaje de descuento a aplicar (ej: 0.10 = 10%)
     */
    public void AplicarDescuento(BigDecimal porcentaje) {
        this.descuentoAplicable = porcentaje;
    }

    /**
     * Verifica si el stock actual esta por debajo del minimo requerido.
     * 
     * @return true si el stock es suficiente, false si esta bajo el minimo
     */
    public boolean ValidarStockMinimo() {
        return cantidadStock >= stockMinimo;
    }

    /**
     * Actualiza la calificacion promedio del articulo.
     * Agrega una nueva calificacion y recalcula el promedio.
     * 
     * @param nuevaCalificacion Nueva calificacion (1-5)
     */
    public void ActualizarCalificacion(BigDecimal nuevaCalificacion) {
        calificaciones.add(nuevaCalificacion.intValue());
        double promedio = calificaciones.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        this.calificacionPromedio = new BigDecimal(promedio).setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * Agrega un proveedor a la lista de proveedores del articulo.
     * No permite duplicados.
     * 
     * @param proveedor Proveedor a agregar
     */
    public void agregarProveedor(Proveedor proveedor) {
        if (!proveedores.contains(proveedor)) {
            proveedores.add(proveedor);
        }
    }

    // ==================== METODO ABSTRACTO ====================
    
    /**
     * Obtiene el tipo de articulo como texto.
     * 
     * @return Tipo de articulo (LIBRERIA, OFICINA, ESCOLAR)
     */
    public abstract String getTipoArticulo();

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Codigo unico del articulo */
    public String getCodigoUnico() { return codigoUnico; }
    
    /** @param codigoUnico Nuevo codigo unico */
    public void setCodigoUnico(String codigoUnico) { this.codigoUnico = codigoUnico; }

    /** @return Nombre del articulo */
    public String getNombre() { return nombre; }
    
    /** @param nombre Nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return Precio base */
    public BigDecimal getPrecioBase() { return precioBase; }
    
    /** @param precioBase Nuevo precio base */
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    /** @return Cantidad en stock */
    public int getCantidadStock() { return cantidadStock; }
    
    /** @param cantidadStock Nueva cantidad en stock */
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    /** @return Categoria del articulo */
    public Categoria getCategoria() { return categoria; }
    
    /** @param categoria Nueva categoria */
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    /** @return Stock minimo requerido */
    public int getStockMinimo() { return stockMinimo; }
    
    /** @param stockMinimo Nuevo stock minimo */
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    /** @return Descuento aplicable */
    public BigDecimal getDescuentoAplicable() { return descuentoAplicable; }
    
    /** @param descuentoAplicable Nuevo descuento */
    public void setDescuentoAplicable(BigDecimal descuentoAplicable) { this.descuentoAplicable = descuentoAplicable; }

    /** @return Fecha de ingreso */
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    
    /** @param fechaIngreso Nueva fecha de ingreso */
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    /** @return true si esta activo */
    public boolean isEstaActivo() { return estaActivo; }
    
    /** @param estaActivo Nuevo estado activo */
    public void setEstaActivo(boolean estaActivo) { this.estaActivo = estaActivo; }

    /** @return true si es importado */
    public boolean isEsImportado() { return esImportado; }
    
    /** @param esImportado Nuevo estado de importacion */
    public void setEsImportado(boolean esImportado) { this.esImportado = esImportado; }

    /** @return Calificacion promedio */
    public BigDecimal getCalificacionPromedio() { return calificacionPromedio; }
    
    /** @param calificacionPromedio Nueva calificacion promedio */
    public void setCalificacionPromedio(BigDecimal calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    /** @return Lista de proveedores */
    public List<Proveedor> getProveedores() { return proveedores; }
    
    /** @param proveedores Nueva lista de proveedores */
    public void setProveedores(List<Proveedor> proveedores) { this.proveedores = proveedores; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del articulo.
     * Formato: "codigo - nombre - $precioBase - Stock: cantidadStock"
     * 
     * @return Cadena con la informacion principal del articulo
     */
    @Override
    public String toString() {
        return codigoUnico + " - " + nombre + " - $" + precioBase + " - Stock: " + cantidadStock;
    }
    
}//fin de la clase