package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.MetodoPago;

public class MetodoPagoDAO {
    private final Connection conn = ConectarBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Columnas: ID_METODO_PAGO, NOMBRE_METODO_PAGO, DESCRIPCION_METODO_PAGO, ESTADO_METODO_PAGO

    public List<MetodoPago> listar() {
        List<MetodoPago> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM METODO_PAGO WHERE ESTADO_METODO_PAGO = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("ID_METODO_PAGO"));
                mp.setNombre(rs.getString("NOMBRE_METODO_PAGO"));
                mp.setDescripcion(rs.getString("DESCRIPCION_METODO_PAGO"));
                mp.setActivo(rs.getBoolean("ESTADO_METODO_PAGO"));
                lista.add(mp);
            }
        } catch (SQLException e) {
             System.out.println("Error al listar métodos de pago: " + e.getMessage());
        }
        return lista;
    }
    
    // ... (Métodos guardar, actualizar, buscar, etc.)
}