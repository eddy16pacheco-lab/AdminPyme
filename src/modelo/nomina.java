package modelo;

import java.util.Date;

public class nomina {
   private int id_nomina;
   private usuario usuario;
   private pago pago;
   private String ref_pago;
   private String periodo_pago;
   private double salario_bruto;
   private String estado_pago;
   private Date fecha_pago;

    public int getId_nomina() {
        return id_nomina;
    }

    public void setId_nomina(int id_nomina) {
        this.id_nomina = id_nomina;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public pago getPago() {
        return pago;
    }

    public void setPago(pago pago) {
        this.pago = pago;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public String getRef_pago() {
        return ref_pago;
    }

    public void setRef_pago(String ref_pago) {
        this.ref_pago = ref_pago;
    }

    public String getPeriodo_pago() {
        return periodo_pago;
    }

    public void setPeriodo_pago(String periodo_pago) {
        this.periodo_pago = periodo_pago;
    }

    public double getSalario_bruto() {
        return salario_bruto;
    }

    public void setSalario_bruto(double salario_bruto) {
        this.salario_bruto = salario_bruto;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }
   
}
