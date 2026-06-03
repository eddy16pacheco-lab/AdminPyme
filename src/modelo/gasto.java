package modelo;

import java.util.Date;

public class gasto {
private int id_gasto;
private usuario usuario;
private Date fecha_gasto;
private double monto_gasto;
private String motivo_del_gasto;
private String categoria;
private String comprobante;

    public int getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(int id_gasto) {
        this.id_gasto = id_gasto;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_gasto() {
        return fecha_gasto;
    }

    public void setFecha_gasto(Date fecha_gasto) {
        this.fecha_gasto = fecha_gasto;
    }

    public double getMonto_gasto() {
        return monto_gasto;
    }

    public void setMonto_gasto(double monto_gasto) {
        this.monto_gasto = monto_gasto;
    }

    public String getMotivo_del_gasto() {
        return motivo_del_gasto;
    }

    public void setMotivo_del_gasto(String motivo_del_gasto) {
        this.motivo_del_gasto = motivo_del_gasto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

}
