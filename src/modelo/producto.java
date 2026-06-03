package modelo;

import java.util.Date;

public class producto {
private int id_producto;
private String codigo_barra;
private categoria Categoria;
private String nombre_prodcuto;
private String descripcion;
private double precio_unitario;
private String unidad_medida;
private double cant_uni_med;
private Date fecha_vencimiento;
private double precio_venta;
private int cantidad;
private String estado;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(categoria Categoria) {
        this.Categoria = Categoria;
    }

    public String getNombre_prodcuto() {
        return nombre_prodcuto;
    }

    public void setNombre_prodcuto(String nombre_prodcuto) {
        this.nombre_prodcuto = nombre_prodcuto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public double getCant_uni_med() {
        return cant_uni_med;
    }

    public void setCant_uni_med(double cant_uni_med) {
        this.cant_uni_med = cant_uni_med;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
