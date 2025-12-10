package beans;

import dao.TipoProductoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import modelo.TipoProducto;

@ManagedBean
@ApplicationScoped
public class TipoProductoBean {
    private TipoProducto tipoProd = new TipoProducto();
    private List<TipoProducto> lstTiposProd = new ArrayList<>();
    private final TipoProductoDAO tipoProdDAO = new TipoProductoDAO();
    
    public TipoProductoBean() {
        listarTiposProd();
    }
    
    public void listarTiposProd() {
        tipoProd = new TipoProducto();
        lstTiposProd = tipoProdDAO.listar();
    }
    
    // ... (Métodos para guardar, actualizar, eliminar, etc.)

    // --- Getters y Setters ---

    public TipoProducto getTipoProd() {
        return tipoProd;
    }

    public void setTipoProd(TipoProducto tipoProd) {
        this.tipoProd = tipoProd;
    }

    public List<TipoProducto> getLstTiposProd() {
        return lstTiposProd;
    }

    public void setLstTiposProd(List<TipoProducto> lstTiposProd) {
        this.lstTiposProd = lstTiposProd;
    }
}