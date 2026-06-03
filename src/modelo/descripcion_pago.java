package modelo;

public class descripcion_pago {
    private int id_descripicon_pago;
    private ventas venta;
    private cliente Cliente;
    private pago pago;
    private double monto_cancelado;
    private String ref_pago;
    private String fecha_y_hora;

    public int getId_descripicon_pago() {
        return id_descripicon_pago;
    }

    public ventas getVenta() {
        return venta;
    }

    public void setVenta(ventas venta) {
        this.venta = venta;
    }

    public void setId_descripicon_pago(int id_descripicon_pago) {
        this.id_descripicon_pago = id_descripicon_pago;
    }

    public cliente getCliente() {
        return Cliente;
    }

    public void setCliente(cliente Cliente) {
        this.Cliente = Cliente;
    }

    public pago getPago() {
        return pago;
    }

    public void setPago(pago pago) {
        this.pago = pago;
    }

    public double getMonto_cancelado() {
        return monto_cancelado;
    }

    public void setMonto_cancelado(double monto_cancelado) {
        this.monto_cancelado = monto_cancelado;
    }

    public String getRef_pago() {
        return ref_pago;
    }

    public void setRef_pago(String ref_pago) {
        this.ref_pago = ref_pago;
    }

    public String getFecha_y_hora() {
        return fecha_y_hora;
    }

    public void setFecha_y_hora(String fecha_y_hora) {
        this.fecha_y_hora = fecha_y_hora;
    }

   
}
