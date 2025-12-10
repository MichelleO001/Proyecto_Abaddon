package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO {
    private final Connection conn = ConectarBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Columnas: ID_USUARIO, NOMBRE_USUARIO, EMAIL_USUARIO, PASSWORD_USUARIO, ROL_USUARIO, ESTADO_USUARIO

    public Usuario autenticar(String email, String password) {
        Usuario user = null;
        try {
            // Nota: En un sistema real, la contraseña debe compararse mediante un hash (ej: BCrypt)
            String sql = "SELECT * FROM USUARIOS WHERE EMAIL_USUARIO = ? AND PASSWORD_USUARIO = ? AND ESTADO_USUARIO = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("ID_USUARIO"));
                user.setNombre(rs.getString("NOMBRE_USUARIO"));
                user.setEmail(rs.getString("EMAIL_USUARIO"));
                user.setRol(rs.getString("ROL_USUARIO"));
                user.setActivo(rs.getBoolean("ESTADO_USUARIO"));
            }
        } catch (SQLException e) {
             System.out.println("Error en autenticación: " + e.getMessage());
        }
        return user;
    }
    
    public List<Usuario> listarClientes() {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM USUARIOS WHERE ROL_USUARIO = 'CLIENTE' AND ESTADO_USUARIO = 1";
            // ... (Lógica de mapeo de resultados similar a listar())
        } catch (Exception e) {
        }
        return lista;
    }

    // ... (Métodos listar, buscar, guardar, etc.)

    Usuario buscar(int clienteId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}