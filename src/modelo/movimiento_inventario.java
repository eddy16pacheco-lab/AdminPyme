package modelo;

import java.util.Date;

public class movimiento_inventario {
private int id_movimiento_inventario;
private  usuario usuario;
private producto producto;
private int cantidad;
private String tipo_movimiento; 
private Date fecha_movimiento;
private String tiempo_movimiento;
private double precio_unitario;
private String observacion;

    public int getId_movimiento_inventario() {
        return id_movimiento_inventario;
    }

    public void setId_movimiento_inventario(int id_movimiento_inventario) {
        this.id_movimiento_inventario = id_movimiento_inventario;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getTiempo_movimiento() {
        return tiempo_movimiento;
    }

    public void setTiempo_movimiento(String tiempo_movimiento) {
        this.tiempo_movimiento = tiempo_movimiento;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


}
