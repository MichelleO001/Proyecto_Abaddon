package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;

// Se asume la existencia de ProductoDAO
// import modelo.Producto;

public class DetalleVentaDAO {
    private final Connection conn = ConectarBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    
    private final ProductoDAO productoDAO = new ProductoDAO(); 

    // Columnas: VENTA_ID, PRODUCTO_ID, CANTIDAD_DETALLE, PRECIO_UNITARIO_DETALLE, SUBTOTAL_DETALLE

    /**
     * Guarda un detalle de venta en la base de datos.
     * @param detalle El objeto DetalleVenta a guardar.
     */
    public void guardar(DetalleVenta detalle) {
        try {
            String sql = "INSERT INTO DETALLE_VENTA (VENTA_ID, PRODUCTO_ID, CANTIDAD_DETALLE, PRECIO_UNITARIO_DETALLE, SUBTOTAL_DETALLE) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, detalle.getVentaId());
            ps.setInt(2, detalle.getProductoId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar detalle de venta: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos los detalles de una venta específica.
     * @param ventaId ID de la venta.
     * @return Lista de DetalleVenta.
     */
    public List<DetalleVenta> listarPorVenta(int ventaId) {
        List<DetalleVenta> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM DETALLE_VENTA WHERE VENTA_ID = ? AND ESTADO_DETALLE = 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ventaId);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setId(rs.getInt("ID_DETALLE"));
                detalle.setVentaId(rs.getInt("VENTA_ID"));
                detalle.setProductoId(rs.getInt("PRODUCTO_ID"));
                detalle.setCantidad(rs.getInt("CANTIDAD_DETALLE"));
                detalle.setPrecioUnitario(rs.getDouble("PRECIO_UNITARIO_DETALLE"));
                detalle.setSubtotal(rs.getDouble("SUBTOTAL_DETALLE"));
                
                // Carga del objeto Producto
                detalle.setProducto(productoDAO.buscar(detalle.getProductoId()));
                
                lista.add(detalle);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles de venta: " + e.getMessage());
        }
        return lista;
    }
    
    // ... (Otros métodos como buscar, actualizar, etc.)
}