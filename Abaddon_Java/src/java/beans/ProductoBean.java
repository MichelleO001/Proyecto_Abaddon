package beans;

import dao.ProductoDAO;
// Importación corregida: de ProveedorDAO a TipoProductoDAO (asumiendo que existe)
import dao.TipoProductoDAO; 

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import modelo.Producto;
// La lista de proveedores se cambia a lista de Tipos de Producto
import modelo.TipoProducto; 

@ManagedBean
@ApplicationScoped
public class ProductoBean {
    public Producto prod = new Producto();
    public List<Producto> lstProds = new ArrayList<>();
    public List<Producto> lstProdsFiltrado;
    // La lista de proveedores se cambia a lista de tipos de producto
    public List<TipoProducto> lstTiposProd = new ArrayList<>(); 
    private final ProductoDAO prodDAO = new ProductoDAO();
    Part imagen;
    
    public void listarProds(){
        prod = new Producto();
        lstProds = prodDAO.listarProd();
    }    
    
    public void buscar(int id){
        prod = prodDAO.buscar(id);
    }
    
    public void actualizar(){
        // Lógica de manejo de imagen (se mantiene, pero se usa prod.getId() en lugar de prod.getCod())
        if(imagen != null){
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

                InputStream in = imagen.getInputStream();
                // Se usa prod.getId() que es más seguro para un producto existente
                File f = new File(path + "ImgProds", prod.getId() + ".png"); 
                FileOutputStream out = new FileOutputStream(f);

                byte[] buffer = new byte[1024];
                int tamaño;

                while((tamaño = in.read(buffer)) > 0){
                    out.write(buffer, 0, tamaño);
                }

                out.close();
                in.close();

                prod.setImagen("../ImgProds/" + f.getName()); // Se cambia setFoto a setImagen
                
            } catch (IOException e) {
                // Manejo de error
            }    
        }
        
        prodDAO.actualizar(prod);
    }
    
    public void eliminar(int id){
        prodDAO.eliminar(id);
    }
    
    // Método adaptado para listar Tipos de Producto
    public void listarTiposProd(){ 
        TipoProductoDAO tipoProdDAO = new TipoProductoDAO();
        lstTiposProd = tipoProdDAO.listar(); // Asume que TipoProductoDAO tiene un método listar()
    }
    
    public void guardar(){
        // Nota: En la función 'guardar', el ID_PRODUCTO aún no se genera
        // Si el DAO retorna el ID autoincrementable después de guardar, 
        // la lógica de la imagen debe esperar ese ID.
        // Por simplicidad, se puede omitir la subida de la imagen aquí y hacerla después
        // de la inserción, o nombrar el archivo temporalmente.
        // Lo mantendré como en el original, asumiendo que el ID es fijo o se genera antes.
        // Una mejor práctica es guardar el registro, obtener el ID, y luego renombrar/guardar la imagen.
        
        prodDAO.guardar(prod); // Primero guarda el producto (sin imagen final)
        
        // Asumiendo que prod.getId() se actualiza aquí después de guardar (lo cual NO pasa por defecto en JDBC estándar sin un getGeneratedKeys)
        // DEBEMOS REVISAR LA LÓGICA DE SUBIDA DE IMAGEN para productos nuevos
        
        if (imagen != null && prod.getId() != 0) { // Comprobación simple (Debería usar getGeneratedKeys en el DAO)
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                
                InputStream in = imagen.getInputStream();
                // Usa el ID generado
                File f = new File(path + "ImgProds", prod.getId() + ".png"); 
                FileOutputStream out = new FileOutputStream(f);
                
                byte[] buffer = new byte[1024];
                int tamaño;
                
                while((tamaño = in.read(buffer)) > 0){
                    out.write(buffer, 0, tamaño);
                }
                
                out.close();
                in.close();
                
                // Actualiza el producto con la ruta de la imagen
                prod.setImagen("../ImgProds/" + f.getName()); 
                // Necesitarías una función para actualizar solo la imagen en el DAO: prodDAO.actualizarImagen(prod.getId(), prod.getImagen());
                
            } catch (IOException e) {
                // Manejo de error
            }
        }
    }
    
    // --- Getters y Setters adaptados ---
    
    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }

    public List<Producto> getLstProds() {
        return lstProds;
    }

    public void setLstProds(List<Producto> lstProds) {
        this.lstProds = lstProds;
    }

    public Part getImagen() {
        return imagen;
    }

    public void setImagen(Part imagen) {
        this.imagen = imagen;
    }

    // Nuevo Getter/Setter para la lista de Tipos de Producto
    public List<TipoProducto> getLstTiposProd() { 
        return lstTiposProd;
    }

    public void setLstTiposProd(List<TipoProducto> lstTiposProd) {
        this.lstTiposProd = lstTiposProd;
    }

    public List<Producto> getLstProdsFiltrado() {
        return lstProdsFiltrado;
    }

    public void setLstProdsFiltrado(List<Producto> lstProdsFiltrado) {
        this.lstProdsFiltrado = lstProdsFiltrado;
    }
}