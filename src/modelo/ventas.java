
package modelo;

import java.util.Date;


public class ventas {

    private int id_venta;
    private cliente Cliente;
    private usuario Empleado;
private String fecha_y_hora;

    public String getFecha_y_hora() {
        return fecha_y_hora;
    }

    public void setFecha_y_hora(String fecha_y_hora) {
        this.fecha_y_hora = fecha_y_hora;
    }
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public cliente getCliente() {
        return Cliente;
    }

    public void setCliente(cliente Cliente) {
        this.Cliente = Cliente;
    }

    public usuario getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(usuario Empleado) {
        this.Empleado = Empleado;
    }
    
}
