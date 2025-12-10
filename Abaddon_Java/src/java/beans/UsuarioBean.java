package beans;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped; // Usar SessionScoped para el usuario logueado
import javax.faces.bean.ManagedBean;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
    private Usuario usuario = new Usuario();
    private List<Usuario> lstUsuarios = new ArrayList<>();
    private final UsuarioDAO userDAO = new UsuarioDAO();
    
    public String autenticar() {
        Usuario loggedInUser = userDAO.autenticar(usuario.getEmail(), usuario.getPassword());
        
        if (loggedInUser != null) {
            this.usuario = loggedInUser; // Almacena el usuario autenticado en el bean de sesión
            // Devuelve la página de destino basada en el rol
            if ("ADMINISTRADOR".equals(usuario.getRol())) {
                return "dashboardAdmin?faces-redirect=true";
            } else if ("EMPLEADO".equals(usuario.getRol())) {
                return "dashboardEmpleado?faces-redirect=true";
            } else {
                return "catalogo?faces-redirect=true"; // Cliente
            }
        } else {
            // Mensaje de error (se puede integrar con PrimeFaces Growl)
            return "errorAuth"; // Navegación a la página de error
        }
    }
    
    // ... (Métodos para gestionar CRUD de usuarios)

    // --- Getters y Setters ---

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLstUsuarios() {
        return lstUsuarios;
    }

    public void setLstUsuarios(List<Usuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }
}