package modelo;

public class detalle_orden_de_compra {
private int id_detalle_orden_de_compra;
private producto producto;
private entrega entrega;
private orden_de_compra orden_de_compra;
private int id_orden_entrega;
private int cantidad_empaquetada;
private int cantidad_entregada;
private double precio_unitario;
private double sub_total;
private almacen almacen;
    public int getId_detalle_orden_de_compra() {
        return id_detalle_orden_de_compra;
    }

    public almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(almacen almacen) {
        this.almacen = almacen;
    }

    public void setId_detalle_orden_de_compra(int id_detalle_orden_de_compra) {
        this.id_detalle_orden_de_compra = id_detalle_orden_de_compra;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(entrega entrega) {
        this.entrega = entrega;
    }

    public orden_de_compra getOrden_de_compra() {
        return orden_de_compra;
    }

    public void setOrden_de_compra(orden_de_compra orden_de_compra) {
        this.orden_de_compra = orden_de_compra;
    }

    public int getId_orden_entrega() {
        return id_orden_entrega;
    }

    public void setId_orden_entrega(int id_orden_entrega) {
        this.id_orden_entrega = id_orden_entrega;
    }

    public int getCantidad_empaquetada() {
        return cantidad_empaquetada;
    }

    public void setCantidad_empaquetada(int cantidad_empaquetada) {
        this.cantidad_empaquetada = cantidad_empaquetada;
    }

    public int getCantidad_entregada() {
        return cantidad_entregada;
    }

    public void setCantidad_entregada(int cantidad_entregada) {
        this.cantidad_entregada = cantidad_entregada;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }
  
}
