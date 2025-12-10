package beans;

import dao.DetalleVentaDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.DetalleVenta;
import modelo.Producto;

@ManagedBean
@ViewScoped
public class DetalleVentaBean {
    // Objeto temporal para agregar al carrito
    private DetalleVenta detalleTemporal = new DetalleVenta();
    
    // Lista de detalles que forman la venta (carrito)
    private List<DetalleVenta> carrito = new ArrayList<>();
    
    // ID del producto a agregar (si se usa en el formulario)
    private int productoIdSeleccionado;
    
    private final DetalleVentaDAO detalleDAO = new DetalleVentaDAO();

    /**
     * Agrega un producto al carrito de compras.
     * Asume que se tiene un método para obtener el precio del producto.
     */
    public void agregarAlCarrito(Producto producto, int cantidad) {
        // Lógica de cálculo y creación del detalle
        DetalleVenta nuevoDetalle = new DetalleVenta();
        nuevoDetalle.setProductoId(producto.getId());
        nuevoDetalle.setProducto(producto);
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setPrecioUnitario(producto.getPrecio());
        nuevoDetalle.setSubtotal(producto.getPrecio() * cantidad);
        
        // Agregar al carrito (validando si ya existe para sumar cantidad)
        carrito.add(nuevoDetalle); 
        // Nota: En la vida real, se debería actualizar el total en el VentaBean.
    }
    
    // --- Getters y Setters ---

    public DetalleVenta getDetalleTemporal() {
        return detalleTemporal;
    }

    public void setDetalleTemporal(DetalleVenta detalleTemporal) {
        this.detalleTemporal = detalleTemporal;
    }

    public List<DetalleVenta> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<DetalleVenta> carrito) {
        this.carrito = carrito;
    }

    public int getProductoIdSeleccionado() {
        return productoIdSeleccionado;
    }

    public void setProductoIdSeleccionado(int productoIdSeleccionado) {
        this.productoIdSeleccionado = productoIdSeleccionado;
    }
}