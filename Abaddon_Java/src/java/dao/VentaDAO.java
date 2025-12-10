package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Venta;

public class VentaDAO {
    private final Connection conn = ConectarBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(); 
    // private final DetalleVentaDAO detalleDAO = new DetalleVentaDAO(); // Se necesitaría

    public int guardarVenta(Venta venta) {
        int idGenerado = -1;
        // Asume que la lógica de DetalleVenta y ProductoDAO se ejecuta después de obtener el ID_VENTA
        try {
            String sql = "INSERT INTO VENTA (CLIENTE_ID, VENDEDOR_ID, TOTAL_VENTA, ESTADO_VENTA) VALUES (?, ?, ?, ?)";
            // Statement.RETURN_GENERATED_KEYS es crucial para obtener el ID_VENTA
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, venta.getClienteId());
            ps.setInt(2, venta.getVendedorId());
            ps.setDouble(3, venta.getTotalVenta());
            ps.setString(4, venta.getEstadoVenta());
            
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
             System.out.println("Error al guardar venta: " + e.getMessage());
        }
        return idGenerado;
    }
    
    public Venta buscar(int id) {
        Venta venta = null;
        try {
            String sql = "SELECT * FROM VENTA WHERE ID_VENTA = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                venta = new Venta();
                venta.setId(rs.getInt("ID_VENTA"));
                venta.setClienteId(rs.getInt("CLIENTE_ID"));
                venta.setVendedorId(rs.getInt("VENDEDOR_ID"));
                venta.setFechaVenta(rs.getTimestamp("FECHA_VENTA"));
                venta.setTotalVenta(rs.getDouble("TOTAL_VENTA"));
                venta.setEstadoVenta(rs.getString("ESTADO_VENTA"));
                
                // Carga de objetos relacionados (lazy loading o eager loading)
                venta.setCliente(usuarioDAO.buscar(venta.getClienteId())); // Se necesitaría un UsuarioDAO.buscar
                venta.setVendedor(usuarioDAO.buscar(venta.getVendedorId()));
                // venta.setDetalles(detalleDAO.listarPorVenta(venta.getId())); // Se necesitaría un DetalleVentaDAO
            }
        } catch (SQLException e) {
             System.out.println("Error al buscar venta: " + e.getMessage());
        }
        return venta;
    }
}