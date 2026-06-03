package modelo;

import java.util.ArrayList;
import java.util.Date;
import validaciones.modulo_venta.validar_venta;
import validaciones.validaciones.otros.validar_componente;

public class detalle_de_ventas {

    private int id_detalle_venta;
    private ventas n_venta;
    private producto Producto;
    private double monto_venta;
    private int cantidad_vendida;
    private String fecha_y_hora;
    private String estado_venta;
    private Date fecha_coboro;
    private double porcentaje;
    private String tipo_venta;
    
   private validar_componente val=new validar_componente();
    public String getEstado_venta() {
        return estado_venta;
    }

    public String getFecha_y_hora() {
        return fecha_y_hora;
    }

    public void setFecha_y_hora(String fecha_y_hora) {
        this.fecha_y_hora = fecha_y_hora;
    }

    public void setTipo_venta(String tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public void setVal(validar_venta val) {
        this.val = val;
    }

    public String getTipo_venta() {
        return tipo_venta;
    }

    public validar_componente getVal() {
        return val;
    }

    public void setEstado_venta(String estado_venta) {
        this.estado_venta = estado_venta;
    }

    public Date getFecha_coboro() {
        return fecha_coboro;
    }

    public void setFecha_coboro(Date fecha_coboro) {
        this.fecha_coboro = fecha_coboro;
    }


    public int getId_detalle_venta() {
        return id_detalle_venta;
    }

    public producto getProducto() {
        return Producto;
    }

    public void setProducto(producto Producto) {
        this.Producto = Producto;
    }

    public void setId_detalle_venta(int id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    
    public ventas getN_venta() {
        return n_venta;
    }

    public void setN_venta(ventas n_venta) {
        this.n_venta = n_venta;
    }

    public double getMonto_venta() {
        return monto_venta;
    }

    

    public void setMonto_venta(double monto_venta) {
        this.monto_venta = monto_venta;
    }

    public int getCantidad_vendida() {
        return cantidad_vendida;
    }

    public void setCantidad_vendida(int cantidad_vendida) {
        this.cantidad_vendida = cantidad_vendida;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }


    public double caluclar_valor_venta_credito() {
        double total_venta_credito = 0;
        if (this.porcentaje == 0 || this.porcentaje == 1) {
            total_venta_credito = this.monto_venta;
        } else {
            val.limitar_decimales(total_venta_credito = (this.monto_venta * (porcentaje / 100) + this.monto_venta));
        }
        return total_venta_credito;
    }
public double calcular_valor_presupuesto(){
double valor_presupuesto=0;
if(this.porcentaje==0 || this.porcentaje==1){
valor_presupuesto=this.monto_venta;
}
else{
val.limitar_decimales(valor_presupuesto=(this.monto_venta * (porcentaje / 100)));
}
return valor_presupuesto;
}
}
