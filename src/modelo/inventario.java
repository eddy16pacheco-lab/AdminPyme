package modelo;

public class inventario {

    private int id_inventario;
    private producto producto;
    private usuario usuario;
    private int cantidad;
    private int stock_min;
    private int stock_max;
    private String fecha_actualizacion;
    private ubicacion_fisica ubicacion_fisica;
    public int getId_inventario() {
        return id_inventario;
    }

    public ubicacion_fisica getUbicacion_fisica() {
        return ubicacion_fisica;
    }

    public void setUbicacion_fisica(ubicacion_fisica ubicacion_fisica) {
        this.ubicacion_fisica = ubicacion_fisica;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getStock_min() {
        return stock_min;
    }

    public void setStock_min(int stock_min) {
        this.stock_min = stock_min;
    }

    public int getStock_max() {
        return stock_max;
    }

    public void setStock_max(int stock_max) {
        this.stock_max = stock_max;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

}
