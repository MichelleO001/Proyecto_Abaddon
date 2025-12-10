package beans;

import dao.UsuarioDAO; // Necesitas importar el DAO
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped; // Usar SessionScoped para mantener la sesión del usuario
import javax.faces.context.FacesContext;
import modelo.Usuario;

@ManagedBean
@SessionScoped // Crucial para mantener la sesión del usuario logueado
public class LoginBean {
    // Usamos el objeto Usuario del modelo definido
    private Usuario usuario = new Usuario();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Invocamos el DAO

    // --- Getters y Setters de usuario (Necesarios para el formulario de login) ---

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // --- Autenticación ---

    public void autenticar() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            // Asumiendo que UsuarioDAO.autenticar ya implementa la lógica SQL
            // y que la contraseña se encripta o se maneja correctamente en el DAO
            
            // Nota: En una arquitectura real, la encriptación se maneja en el DAO o Servicio. 
            // Aquí se pasa el email y la contraseña sin encriptar al DAO para que este la verifique.
            Usuario userLogeado = usuarioDAO.autenticar(usuario.getEmail(), usuario.getPassword());

            if (userLogeado != null) {
                // Almacenar datos del usuario en sesión
                context.getExternalContext().getSessionMap().put("usuarioLogueado", userLogeado);
                context.getExternalContext().getSessionMap().put("rol", userLogeado.getRol());
                
                String rootPath = context.getExternalContext().getRequestContextPath();
                
                // Redirección basada en el Rol (ADMINISTRADOR, EMPLEADO, CLIENTE)
                switch (userLogeado.getRol()) {
                    case "ADMINISTRADOR":
                        // Redirigir al Dashboard de Administrador
                        context.getExternalContext().redirect(rootPath + "/faces/dashboard/admin.xhtml");
                        break;
                    case "EMPLEADO":
                        // Redirigir al Dashboard de Empleado (Vendedor)
                        context.getExternalContext().redirect(rootPath + "/faces/dashboard/empleado.xhtml");
                        break;
                    case "CLIENTE":
                        // Redirigir al Catálogo de Productos para el Cliente
                        context.getExternalContext().redirect(rootPath + "/faces/dashboard/cliente.xhtml");
                        break;
                    default:
                        // Rol no reconocido, enviar a no autorizado
                        context.getExternalContext().redirect(rootPath + "/faces/noaccess.xhtml");
                        break;
                }
            } else {
                // Credenciales no válidas
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Acceso", "Email y/o Contraseña no válidos."));
            }
        } catch (IOException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error de Redirección: " + e.getMessage()));
        }
    }
    
    // --- Cierre de Sesión ---

    public void cerrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
        try {
            String rootPath = context.getExternalContext().getRequestContextPath();
            context.getExternalContext().redirect(rootPath + "/faces/index.xhtml"); // Volver a la página de login/inicio
        } catch (IOException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error al cerrar sesión."));
        }
    }
    
    // --- Verificación de Sesión (Función genérica) ---

    public void verifSesion(String rolRequerido) {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario user = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogueado");
        
        if (user == null || !user.getRol().equals(rolRequerido)) {
            try {
                String rootPath = context.getExternalContext().getRequestContextPath();
                // Si no hay sesión o el rol no coincide, redirigir
                context.getExternalContext().redirect(rootPath + "/faces/noaccess.xhtml"); 
            } catch (IOException ex) {
                // Manejar error de redirección
            }
        }
    }
    
    // Métodos de verificación específicos para usar en plantillas
    public void verifAdmin() { verifSesion("ADMINISTRADOR"); }
    public void verifEmpleado() { verifSesion("EMPLEADO"); }
    public void verifCliente() { verifSesion("CLIENTE"); }
    
    // Retorna el nombre del usuario logueado para mostrar en el Dashboard
    public String getNombreUsuario() {
         Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogueado");
         return user != null ? user.getNombre() : "Invitado";
    }
}