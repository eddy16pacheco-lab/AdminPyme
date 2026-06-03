package controlador.modulo_cliente;

import controlador.*;
import consultas_bd.consulta_cliente;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import modelo.cliente;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import validaciones.modulo_venta.validar_venta;
import vista.consultas.DetalleClienteNuevo;

public class controlador_detalle_cliente extends controlador_padre {
    private cliente modelo;
    private DetalleClienteNuevo vista;
    private consulta_cliente sql=new consulta_cliente();
    private ArrayList<detalle_de_ventas> l_venta=new ArrayList<>();
    private ArrayList<descripcion_pago> l_pago=new ArrayList<>();
    private validar_venta validaciones=new validar_venta();
    public controlador_detalle_cliente(cliente modelo, DetalleClienteNuevo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {

        if (mostra()) {
            vista.setVisible(true);
            vista.setLocationRelativeTo(null);
        }
    }

    private boolean mostra() {
       /* vista.jLabel_nombre.setText(modelo.getNombre());
        vista.jLabel_apellido.setText(modelo.getApellido());
        vista.jLabel_cedula.setText("" + modelo.getCedula());
        vista.jLabel_telefono.setText(modelo.getTelefeno());
        vista.jLabel_ubicacion.setText(modelo.getUbicacion().getNombre_parroquia());*/
        
        vista.jList_compra.setModel(sql.cargarHistorialVentasCliente(1));
      return true;
        }

    public void llenar() {
        DefaultListModel modelo_comp = new DefaultListModel();
        DefaultListModel modelo_pago = new DefaultListModel();
        modelo_comp.addElement("Productos");
        for (detalle_de_ventas lit : l_venta) {
            String[] fecha=lit.getFecha_y_hora().split(" ");
            modelo_comp.addElement("Nombre: "+lit.getProducto().getNombre_prodcuto());
            modelo_comp.addElement("Descripcion: "+lit.getProducto().getDescripcion());
            modelo_comp.addElement("Precio: "+lit.getProducto().getPrecio_venta());
            modelo_comp.addElement("Cantidad comprada: "+lit.getProducto().getCantidad());
            modelo_comp.addElement("Sub total: "+lit.getMonto_venta());
            modelo_comp.addElement("Fecha: "+fecha[0]+"");
            modelo_comp.addElement("Hora: "+validaciones.convertirHoraInglesaAEspanolaSimple(validaciones.cortarSegundosConSplit(fecha[1]))+"");
            modelo_comp.addElement("----------------------------------------------------------------------------------------------------");
        }
        for (descripcion_pago lp : l_pago) {
            String[] fecha = lp.getFecha_y_hora().split(" ");
            if(lp.getRef_pago()==null){
                modelo_pago.addElement("Forma de pago: " + lp.getPago().getTipo_de_pago());
                modelo_pago.addElement("Monto del pago: " + lp.getMonto_cancelado());
                modelo_pago.addElement("Fecha: " + fecha[0] + "");
                modelo_pago.addElement("Hora: " + validaciones.convertirHoraInglesaAEspanolaSimple(validaciones.cortarSegundosConSplit(fecha[1])) + "");
                modelo_pago.addElement("----------------------------------------------------------------------------------------------------");
            }else{
                modelo_pago.addElement("Forma de pago: " + lp.getPago().getTipo_de_pago());
                modelo_pago.addElement("Monto del pago: " + lp.getMonto_cancelado());
                modelo_pago.addElement("Numero de Referencia: "+lp.getRef_pago());
                modelo_pago.addElement("Fecha: " + fecha[0] + "");
                modelo_pago.addElement("Hora: " + validaciones.convertirHoraInglesaAEspanolaSimple(validaciones.cortarSegundosConSplit(fecha[1])) + "");
                modelo_pago.addElement("----------------------------------------------------------------------------------------------------");
  
            }
            
        }
        vista.jList_compra.setModel(modelo_comp);
        vista.jList_pago.setModel(modelo_pago);
    }
}
