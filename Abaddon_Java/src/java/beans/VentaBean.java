package beans;

import dao.VentaDAO;
// import dao.DetalleVentaDAO; // Se necesitaría
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped; // Usar ViewScoped o SessionScoped para procesos de compra
import modelo.Venta;

@ManagedBean
@ViewScoped
public class VentaBean {
    private Venta venta = new Venta();
    private List<Venta> lstVentas = new ArrayList<>();
    private final VentaDAO ventaDAO = new VentaDAO();
    // private final DetalleVentaDAO detalleDAO = new DetalleVentaDAO(); // Se necesitaría
    
    public void iniciarNuevaVenta(int clienteId, int vendedorId) {
        venta = new Venta();
        venta.setClienteId(clienteId);
        venta.setVendedorId(vendedorId);
        venta.setEstadoVenta("PENDIENTE");
        venta.setTotalVenta(0.00);
        // Aquí se inicializaría la lista de detalles
    }
    
    public String finalizarVenta() {
        // 1. Calcular Total de la Venta
        // 2. Guardar la cabecera de la Venta para obtener el ID
        int ventaId = ventaDAO.guardarVenta(venta);
        
        if (ventaId > 0) {
            // 3. Guardar todos los detalles asociados al ventaId
            // for (DetalleVenta detalle : venta.getDetalles()) { detalle.setVentaId(ventaId); detalleDAO.guardar(detalle); }
            
            // 4. Actualizar stock de productos
            // 5. Manejar la navegación o mostrar mensaje de éxito
            return "confirmacionVenta?faces-redirect=true";
        } else {
            // Manejar error de guardado
            return "errorVenta";
        }
    }
    
    // ... (Métodos para agregar/quitar detalles, listar ventas, etc.)

    // --- Getters y Setters ---

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Venta> getLstVentas() {
        return lstVentas;
    }

    public void setLstVentas(List<Venta> lstVentas) {
        this.lstVentas = lstVentas;
    }
}