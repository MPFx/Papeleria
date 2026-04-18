package model.control;

import model.articulos.Articulo;
import model.catalogo.Proveedor;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase que contiene metodos de validacion para el sistema.
 * Permite validar stock antes de venta, precios positivos,
 * proveedores duplicados, articulos activos, codigos unicos
 * y condicion completa de articulos para venta.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Proveedor
 */
public class Validaciones {

    // ==================== METODOS DE VALIDACION ====================
    
    /**
     * Valida si hay suficiente stock de un articulo antes de una venta.
     * 
     * @param articulo Articulo a validar
     * @param cantidad Cantidad que se desea vender
     * @return true si hay stock suficiente y el articulo existe
     */
    public boolean ValidarStockAntesVenta(Articulo articulo, int cantidad) {
        if (articulo == null) return false;
        if (!articulo.isEstaActivo()) return false;
        return articulo.getCantidadStock() >= cantidad;
    }

    /**
     * Valida que un precio sea positivo y no nulo.
     * 
     * @param precio Precio a validar
     * @return true si el precio es mayor a 0
     */
    public boolean ValidarPrecioPositivo(BigDecimal precio) {
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Valida si ya existe un proveedor con el mismo NIT en la lista.
     * 
     * @param proveedores Lista de proveedores existentes
     * @param nit NIT a verificar
     * @return true si ya existe un proveedor con ese NIT
     */
    public boolean ValidarProveedorDuplicado(List<Proveedor> proveedores, String nit) {
        return proveedores.stream().anyMatch(p -> p.getNitProveedor().equals(nit));
    }

    /**
     * Valida si un articulo existe y esta activo.
     * 
     * @param articulo Articulo a validar
     * @return true si el articulo no es null y esta activo
     */
    public boolean ValidarArticuloActivo(Articulo articulo) {
        return articulo != null && articulo.isEstaActivo();
    }

    /**
     * Valida si un codigo unico ya existe en la lista de articulos.
     * 
     * @param articulos Lista de articulos existentes
     * @param codigo Codigo a verificar
     * @return true si el codigo NO existe (es valido para nuevo articulo)
     */
    public boolean ValidarCodigoUnico(List<Articulo> articulos, String codigo) {
        return articulos.stream().noneMatch(a -> a.getCodigoUnico().equals(codigo));
    }

    /**
     * Valida todas las condiciones para permitir la venta de un articulo.
     * Retorna un mensaje descriptivo del error o "OK" si es valido.
     * 
     * @param articulo Articulo a validar
     * @param cantidad Cantidad que se desea vender
     * @return Mensaje de error o "OK" si todo es correcto
     */
    public String ValidarArticuloParaVenta(Articulo articulo, int cantidad) {
        if (articulo == null) {
            return "Articulo no existe";
        }
        if (!articulo.isEstaActivo()) {
            return "Articulo inactivo";
        }
        if (!ValidarStockAntesVenta(articulo, cantidad)) {
            return "Stock insuficiente. Disponible: " + articulo.getCantidadStock();
        }
        return "OK";
    }
    
}//fin de la clase