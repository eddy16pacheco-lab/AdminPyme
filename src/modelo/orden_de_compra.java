package modelo;

import java.util.Date;

public class orden_de_compra {

private int id_orden_de_compra;
private String codigo_orden_de_compra;
private proveedor proveedor;
private usuario usuario;
private Date fecha_orden;
private Date fecha_requerida;
    public int getId_orden_de_compra() {
        return id_orden_de_compra;
    }

    public String getCodigo_orden_de_compra() {
        return codigo_orden_de_compra;
    }

    public void setCodigo_orden_de_compra(String codigo_orden_de_compra) {
        this.codigo_orden_de_compra = codigo_orden_de_compra;
    }

    public void setId_orden_de_compra(int id_orden_de_compra) {
        this.id_orden_de_compra = id_orden_de_compra;
    }

    public proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(Date fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public Date getFecha_requerida() {
        return fecha_requerida;
    }

    public void setFecha_requerida(Date fecha_requerida) {
        this.fecha_requerida = fecha_requerida;
    }



}
