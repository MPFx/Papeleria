package model.catalogo;

import java.math.BigDecimal;
import model.articulos.Articulo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa el inventario de la papelería.
 * Gestiona los articulos disponibles y los proveedores registrados.
 * Permite agregar, eliminar, buscar articulos, generar reportes
 * de stock minimo y realizar pedidos a proveedores.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Proveedor
 */
public class Inventario {

    // ==================== ATRIBUTOS ====================
    
    /** Nombre de la tienda o local. */
    private String nombreTienda;
    
    /** Lista de articulos disponibles en el inventario. */
    private List<Articulo> listaArticulos;
    
    /** Lista de proveedores registrados. */
    private List<Proveedor> listaProveedores;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo inventario.
     * Inicializa las listas de articulos y proveedores como vacias.
     * 
     * @param nombreTienda Nombre de la tienda
     */
    public Inventario(String nombreTienda) {
        this.nombreTienda = nombreTienda;
        this.listaArticulos = new ArrayList<>();
        this.listaProveedores = new ArrayList<>();
    }

    // ==================== METODOS DE GESTION DE ARTICULOS ====================
    
    /**
     * Agrega un articulo al inventario.
     * No permite duplicados.
     * 
     * @param articulo Articulo a agregar
     */
    public void AgregarArticulo(Articulo articulo) {
        if (!listaArticulos.contains(articulo)) {
            listaArticulos.add(articulo);
        }
    }

    /**
     * Elimina un articulo del inventario por su codigo unico.
     * 
     * @param codigo Codigo unico del articulo a eliminar
     * @return true si el articulo fue eliminado, false si no existia
     */
    public boolean EliminarArticulo(String codigo) {
        return listaArticulos.removeIf(a -> a.getCodigoUnico().equals(codigo));
    }

    /**
     * Busca un articulo por su codigo unico.
     * 
     * @param codigo Codigo unico del articulo
     * @return Articulo encontrado o null si no existe
     */
    public Articulo BuscarArticulo(String codigo) {
        return listaArticulos.stream()
                .filter(a -> a.getCodigoUnico().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca articulos por el nombre de su categoria.
     * 
     * @param categoria Nombre de la categoria a filtrar
     * @return Lista de articulos que pertenecen a la categoria especificada
     */
    public List<Articulo> BuscarArticulosPorCategoria(String categoria) {
        return listaArticulos.stream()
                .filter(a -> a.getCategoria() != null && 
                            a.getCategoria().getNombreCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    /**
     * Genera un reporte de articulos con stock por debajo del minimo requerido.
     * 
     * @return Lista de articulos que necesitan reabastecimiento
     */
    public List<Articulo> GenerarReporteStockMinimo() {
        return listaArticulos.stream()
                .filter(a -> !a.ValidarStockMinimo())
                .collect(Collectors.toList());
    }

    /**
     * Calcula el valor total del inventario.
     * Suma (precioBase x cantidadStock) de todos los articulos.
     * 
     * @return Valor total del inventario
     */
    public BigDecimal calcularValorTotalInventario() {
        return listaArticulos.stream()
                .map(a -> a.getPrecioBase().multiply(new java.math.BigDecimal(a.getCantidadStock())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    // ==================== METODOS DE GESTION DE PROVEEDORES ====================
    
    /**
     * Agrega un proveedor a la lista de proveedores registrados.
     * No permite duplicados.
     * 
     * @param proveedor Proveedor a agregar
     */
    public void AgregarProveedor(Proveedor proveedor) {
        if (!listaProveedores.contains(proveedor)) {
            listaProveedores.add(proveedor);
        }
    }

    /**
     * Busca un proveedor por su NIT.
     * 
     * @param nit NIT del proveedor
     * @return Proveedor encontrado o null si no existe
     */
    public Proveedor BuscarProveedor(String nit) {
        return listaProveedores.stream()
                .filter(p -> p.getNitProveedor().equals(nit))
                .findFirst()
                .orElse(null);
    }

    /**
     * Realiza un pedido a un proveedor especifico.
     * 
     * @param nit NIT del proveedor
     * @param articulos Lista de articulos a solicitar
     */
    public void RealizarPedidoAProveedor(String nit, List<Articulo> articulos) {
        Proveedor proveedor = BuscarProveedor(nit);
        if (proveedor != null) {
            proveedor.RealizarPedido(articulos);
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Nombre de la tienda */
    public String getNombreTienda() { return nombreTienda; }
    
    /** @param nombreTienda Nuevo nombre de la tienda */
    public void setNombreTienda(String nombreTienda) { this.nombreTienda = nombreTienda; }

    /** @return Lista de articulos del inventario */
    public List<Articulo> getListaArticulos() { return listaArticulos; }
    
    /** @param listaArticulos Nueva lista de articulos */
    public void setListaArticulos(List<Articulo> listaArticulos) { this.listaArticulos = listaArticulos; }

    /** @return Lista de proveedores registrados */
    public List<Proveedor> getListaProveedores() { return listaProveedores; }
    
    /** @param listaProveedores Nueva lista de proveedores */
    public void setListaProveedores(List<Proveedor> listaProveedores) { this.listaProveedores = listaProveedores; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del inventario.
     * Formato: "Inventario: nombreTienda - cantidad articulos"
     * 
     * @return Cadena con el nombre y cantidad de articulos
     */
    @Override
    public String toString() {
        return "Inventario: " + nombreTienda + " - " + listaArticulos.size() + " articulos";
    }
    
}//fin de la clase