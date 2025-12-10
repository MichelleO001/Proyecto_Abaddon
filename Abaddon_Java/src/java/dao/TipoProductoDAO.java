package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoProducto;

public class TipoProductoDAO {
    private final Connection conn = ConectarBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Columnas: ID_TIPO_PROD, NOMBRE_TIPO_PROD, DESCRIPCION_TIPO_PROD, ESTADO_TIPO_PROD

    public List<TipoProducto> listar() {
        List<TipoProducto> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM TIPO_PRODUCTO WHERE ESTADO_TIPO_PROD = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoProducto tp = new TipoProducto();
                tp.setId(rs.getInt("ID_TIPO_PROD"));
                tp.setNombre(rs.getString("NOMBRE_TIPO_PROD"));
                tp.setDescripcion(rs.getString("DESCRIPCION_TIPO_PROD"));
                tp.setActivo(rs.getBoolean("ESTADO_TIPO_PROD"));
                tp.setFechaCreacion(rs.getTimestamp("FECHA_CREACION_TIPO_PROD"));
                lista.add(tp);
            }
        } catch (SQLException e) {
             System.out.println("Error al listar tipos de producto: " + e.getMessage());
        }
        return lista;
    }
    
    public TipoProducto buscar(int id) {
        TipoProducto tp = null;
        try {
            String sql = "SELECT * FROM TIPO_PRODUCTO WHERE ID_TIPO_PROD = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                tp = new TipoProducto();
                tp.setId(rs.getInt("ID_TIPO_PROD"));
                tp.setNombre(rs.getString("NOMBRE_TIPO_PROD"));
                tp.setDescripcion(rs.getString("DESCRIPCION_TIPO_PROD"));
                tp.setActivo(rs.getBoolean("ESTADO_TIPO_PROD"));
            }
        } catch (SQLException e) {
             System.out.println("Error al buscar tipo de producto: " + e.getMessage());
        }
        return tp;
    }

    // ... (Métodos guardar, actualizar, eliminar/desactivar se implementarían aquí)
}