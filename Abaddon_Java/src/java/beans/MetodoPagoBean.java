package beans;

import dao.MetodoPagoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import modelo.MetodoPago;

@ManagedBean
@ApplicationScoped
public final class MetodoPagoBean {
    private MetodoPago metodoPago = new MetodoPago();
    private List<MetodoPago> lstMetodosPago = new ArrayList<>();
    private final MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
    
    public MetodoPagoBean() {
        listarMetodosPago();
    }
    
    public void listarMetodosPago() {
        metodoPago = new MetodoPago();
        lstMetodosPago = metodoPagoDAO.listar();
    }
   
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<MetodoPago> getLstMetodosPago() {
        return lstMetodosPago;
    }

    public void setLstMetodosPago(List<MetodoPago> lstMetodosPago) {
        this.lstMetodosPago = lstMetodosPago;
    }
}