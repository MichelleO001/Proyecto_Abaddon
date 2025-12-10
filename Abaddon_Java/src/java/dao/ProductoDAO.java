package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;
// Se asume la existencia de TipoProductoDAO y ConectarBD
// import modelo.TipoProducto; 

public class ProductoDAO {
    // Asume que ConectarBD existe y funciona
    private final Connection conn = ConectarBD.conectar(); 
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Se cambia ProveedorDAO por TipoProductoDAO
    private final TipoProductoDAO tipoProductoDAO = new TipoProductoDAO(); 
    
    // Columnas de la tabla PRODUCTOS en ABADDON:
    // ID_PRODUCTO, NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, PRECIO_PRODUCTO, STOCK_PRODUCTO, TIPO_PRODUCTO_ID, IMAGEN_PRODUCTO
    
    public List<Producto> listarProd(){
        List<Producto> lstProds = null;
        
        try {
            // Consulta SQL adaptada a las columnas de la tabla PRODUCTOS
            String sql = "SELECT * FROM PRODUCTOS WHERE ESTADO_PRODUCTO = 1"; 
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            lstProds = new ArrayList<>();
            
            while(rs.next()){
                Producto prod = new Producto();
                // Adaptación de nombres de columnas
                prod.setId(rs.getInt("ID_PRODUCTO"));
                prod.setNombre(rs.getString("NOMBRE_PRODUCTO"));
                prod.setDescripcion(rs.getString("DESCRIPCION_PRODUCTO"));
                prod.setPrecio(rs.getDouble("PRECIO_PRODUCTO")); // Usar getDouble
                prod.setStock(rs.getInt("STOCK_PRODUCTO"));
                prod.setImagen(rs.getString("IMAGEN_PRODUCTO"));
                
                // Adaptación de la FK: de id_prov a TIPO_PRODUCTO_ID
                prod.setTipoProductoId(rs.getInt("TIPO_PRODUCTO_ID")); 
                // Asume que tipoProductoDAO tiene un método buscar(int id)
                prod.setTipoProducto(tipoProductoDAO.buscar(rs.getInt("TIPO_PRODUCTO_ID"))); 
                
                lstProds.add(prod);
            }
            
        } catch (SQLException e) {
             System.out.println("Error al listar productos: " + e.getMessage());
        }
        
        return lstProds;
    }
    
    public void guardar(Producto prod){
        try {
            // SQL adaptado: 7 campos para INSERT (excluyendo estado, fechas y ID autoincrementable)
            // NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, PRECIO_PRODUCTO, STOCK_PRODUCTO, TIPO_PRODUCTO_ID, IMAGEN_PRODUCTO, ESTADO_PRODUCTO (por defecto 1)
            String sql = "INSERT INTO PRODUCTOS (NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, PRECIO_PRODUCTO, STOCK_PRODUCTO, TIPO_PRODUCTO_ID, IMAGEN_PRODUCTO) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            
            // Se eliminó 'cod' y 'fven'
            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio()); 
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getTipoProductoId());
            ps.setString(6, prod.getImagen());
            
            ps.executeUpdate();
        } catch (SQLException e) {
             System.out.println("Error al guardar producto: " + e.getMessage());
        }
    }
    
    public Producto buscar(int id){
        Producto prod = null;
        
        try {
            String sql = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                prod = new Producto();
                // Adaptación de nombres de columnas
                prod.setId(rs.getInt("ID_PRODUCTO"));
                prod.setNombre(rs.getString("NOMBRE_PRODUCTO"));
                prod.setDescripcion(rs.getString("DESCRIPCION_PRODUCTO"));
                prod.setPrecio(rs.getDouble("PRECIO_PRODUCTO"));
                prod.setStock(rs.getInt("STOCK_PRODUCTO"));
                prod.setImagen(rs.getString("IMAGEN_PRODUCTO"));
                prod.setTipoProductoId(rs.getInt("TIPO_PRODUCTO_ID"));
                prod.setTipoProducto(tipoProductoDAO.buscar(rs.getInt("TIPO_PRODUCTO_ID")));
            }        
        } catch (SQLException e) {
             System.out.println("Error al buscar producto: " + e.getMessage());
        }
        
        return prod;
    }
    
    public void actualizar(Producto prod){
        try {
            // SQL adaptado: Se eliminaron 'cod' y 'fven'. Se actualizarán todos los campos principales.
            String sql = "UPDATE PRODUCTOS SET NOMBRE_PRODUCTO = ?, DESCRIPCION_PRODUCTO = ?, PRECIO_PRODUCTO = ?, STOCK_PRODUCTO = ?, "
                    + "TIPO_PRODUCTO_ID = ?, IMAGEN_PRODUCTO = ? WHERE ID_PRODUCTO = ?";
            
            ps = conn.prepareStatement(sql);
            
            // Asumo que si 'cod' existe en el Modelo, lo usaremos para el nombre temporalmente, pero aquí lo omitimos ya que no es columna DB.
            ps.setString(1, prod.getNombre()); 
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio()); 
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getTipoProductoId()); // TIPO_PRODUCTO_ID
            ps.setString(6, prod.getImagen());
            ps.setInt(7, prod.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }
    
    public void eliminar(int id){
        try {
            // En sistemas profesionales, en lugar de DELETE, se actualiza el ESTADO_PRODUCTO a 0 (eliminación lógica)
            String sql = "UPDATE PRODUCTOS SET ESTADO_PRODUCTO = 0 WHERE ID_PRODUCTO = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException e) {
             System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
}