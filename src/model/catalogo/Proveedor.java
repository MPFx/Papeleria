package model.catalogo;

import java.util.ArrayList;
import java.util.List;
import model.articulos.Articulo;

/**
 * Clase que representa un proveedor de articulos para la papelería.
 * Gestiona informacion de contacto, metodo de pago preferido,
 * y los articulos que suministra. Permite realizar pedidos y
 * consultar disponibilidad de productos.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Articulo
 * @see Inventario
 */
public class Proveedor {

    // ==================== ATRIBUTOS ====================
    
    /** NIT (Numero de Identificacion Tributaria) del proveedor. */
    private String nitProveedor;
    
    /** Nombre o razon social del proveedor. */
    private String nombreProveedor;
    
    /** Telefono de contacto del proveedor. */
    private String telefonoContacto;
    
    /** Email de contacto del proveedor. */
    private String emailContacto;
    
    /** Direccion fisica del proveedor. */
    private String direccion;
    
    /** Metodo de pago preferido por el proveedor (TRANSFERENCIA, EFECTIVO, etc.). */
    private String metodoPagoPreferido;
    
    /** Lista de articulos que este proveedor suministra. */
    private List<Articulo> articulosSuministrados;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo proveedor.
     * Inicializa la lista de articulos suministrados como vacia
     * y establece metodo de pago preferido por defecto como "TRANSFERENCIA".
     * 
     * @param nitProveedor NIT del proveedor
     * @param nombreProveedor Nombre o razon social
     */
    public Proveedor(String nitProveedor, String nombreProveedor) {
        this.nitProveedor = nitProveedor;
        this.nombreProveedor = nombreProveedor;
        this.articulosSuministrados = new ArrayList<>();
        this.metodoPagoPreferido = "TRANSFERENCIA";
    }

    // ==================== METODOS DE GESTION ====================
    
    /**
     * Realiza un pedido al proveedor.
     * Simula el proceso de solicitud de articulos y muestra
     * en consola los productos solicitados.
     * 
     * @param articulos Lista de articulos a solicitar
     * @return true si el pedido fue realizado exitosamente
     */
    public boolean RealizarPedido(List<Articulo> articulos) {
        System.out.println("Realizando pedido a " + nombreProveedor);
        for (Articulo a : articulos) {
            System.out.println("  - " + a.getNombre() + " x " + a.getCantidadStock());
        }
        return true;
    }

    /**
     * Consulta la disponibilidad de un articulo con el proveedor.
     * Metodo simulado que retorna una cantidad fija de 100 unidades.
     * 
     * @param codigo Codigo unico del articulo a consultar
     * @return Cantidad disponible del articulo (simulado)
     */
    public int ConsultarDisponibilidad(String codigo) {
        return 100;
    }

    /**
     * Agrega un articulo a la lista de productos suministrados.
     * Establece la relacion bidireccional (el articulo tambien referencia a este proveedor).
     * No permite duplicados.
     * 
     * @param articulo Articulo a agregar
     */
    public void agregarArticulo(Articulo articulo) {
        if (!articulosSuministrados.contains(articulo)) {
            articulosSuministrados.add(articulo);
            articulo.agregarProveedor(this);
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return NIT del proveedor */
    public String getNitProveedor() { return nitProveedor; }
    
    /** @param nitProveedor Nuevo NIT */
    public void setNitProveedor(String nitProveedor) { this.nitProveedor = nitProveedor; }

    /** @return Nombre del proveedor */
    public String getNombreProveedor() { return nombreProveedor; }
    
    /** @param nombreProveedor Nuevo nombre */
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    /** @return Telefono de contacto */
    public String getTelefonoContacto() { return telefonoContacto; }
    
    /** @param telefonoContacto Nuevo telefono */
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    /** @return Email de contacto */
    public String getEmailContacto() { return emailContacto; }
    
    /** @param emailContacto Nuevo email */
    public void setEmailContacto(String emailContacto) { this.emailContacto = emailContacto; }

    /** @return Direccion del proveedor */
    public String getDireccion() { return direccion; }
    
    /** @param direccion Nueva direccion */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /** @return Metodo de pago preferido */
    public String getMetodoPagoPreferido() { return metodoPagoPreferido; }
    
    /** @param metodoPagoPreferido Nuevo metodo de pago */
    public void setMetodoPagoPreferido(String metodoPagoPreferido) { this.metodoPagoPreferido = metodoPagoPreferido; }

    /** @return Lista de articulos suministrados */
    public List<Articulo> getArticulosSuministrados() { return articulosSuministrados; }
    
    /** @param articulosSuministrados Nueva lista de articulos */
    public void setArticulosSuministrados(List<Articulo> articulosSuministrados) { this.articulosSuministrados = articulosSuministrados; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del proveedor.
     * Formato: "nombre - NIT: nit"
     * 
     * @return Cadena con nombre y NIT del proveedor
     */
    @Override
    public String toString() {
        return nombreProveedor + " - NIT: " + nitProveedor;
    }
    
}//fin de la clase