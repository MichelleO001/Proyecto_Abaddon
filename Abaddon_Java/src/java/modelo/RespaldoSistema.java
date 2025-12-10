package modelo;

import java.util.Date;

public class RespaldoSistema {
    private int codRespaldo; 
    private Date fechaGeneracion; 
    private String nombre; 
    private String nombreArchivo;
    private String rutaArchivo; 
    private double tamanio; 
    private String restaurado; 
    private Date fechaRestauracion; 
    private Integer adminId;
    private int estadoSistemaCod; 
    private Integer configuracionSistemaId; 
    private int tipoRespaldoId; 
    private boolean activo; 
    
    public int getCodRespaldo() {
        return codRespaldo;
    }

    public void setCodRespaldo(int codRespaldo) {
        this.codRespaldo = codRespaldo;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public String getRestaurado() {
        return restaurado;
    }

    public void setRestaurado(String restaurado) {
        this.restaurado = restaurado;
    }

    public Date getFechaRestauracion() {
        return fechaRestauracion;
    }

    public void setFechaRestauracion(Date fechaRestauracion) {
        this.fechaRestauracion = fechaRestauracion;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public int getEstadoSistemaCod() {
        return estadoSistemaCod;
    }

    public void setEstadoSistemaCod(int estadoSistemaCod) {
        this.estadoSistemaCod = estadoSistemaCod;
    }

    public Integer getConfiguracionSistemaId() {
        return configuracionSistemaId;
    }

    public void setConfiguracionSistemaId(Integer configuracionSistemaId) {
        this.configuracionSistemaId = configuracionSistemaId;
    }

    public int getTipoRespaldoId() {
        return tipoRespaldoId;
    }

    public void setTipoRespaldoId(int tipoRespaldoId) {
        this.tipoRespaldoId = tipoRespaldoId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}