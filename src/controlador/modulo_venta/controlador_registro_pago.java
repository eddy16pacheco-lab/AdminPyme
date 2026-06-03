package controlador.modulo_venta;

import consultas_bd.consulta_descripcion_pago;
import controlador.controlador_menu;
import controlador.controlador_padre;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdk.jfr.Event;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import modelo.pago;
import validaciones.modulo_venta.validar_descripcion_pago;
import validaciones.modulo_venta.validar_venta;
import validaciones.validaciones.otros.validar_componente;
import vista.formularios.FormularioPagoNuevo;

public class controlador_registro_pago extends controlador_padre implements ActionListener,KeyListener{

private descripcion_pago modelo;
private FormularioPagoNuevo vista;
private consulta_descripcion_pago sql=new consulta_descripcion_pago();
private validar_descripcion_pago validacion=new validar_descripcion_pago();
private ArrayList<detalle_de_ventas>  detalle_venta;    
public controlador_registro_pago(descripcion_pago modelo, FormularioPagoNuevo vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.detalle_venta = sql.con_ven.cargar_informacion_cliente("" + modelo.getVenta().getId_venta());
        this.vista.jComboBox_forma_pago.addKeyListener(this);
        this.vista.jButton_registrar.addActionListener(this);
}

    public double getDolar() {
        return dolar;
    }

    public void setDolar(double dolar) {
        this.dolar = dolar;
    }
    
    @Override
    protected void iniciar() {
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        mostrar();
        ocular(false);
    }
protected String cargar_dolar() throws IOException{
return validacion.obtenerValorDolar();
}
double dolar;
    @Override
    protected void mostrar() {
        sql.con_pago.select_table_pago(vista.jComboBox_forma_pago);
        for (detalle_de_ventas object : detalle_venta) {
            String[] nombre_apellido=object.getN_venta().getCliente().getNombre().split(" ");
            vista.jLabel_codigo_de_venta.setText(""+object.getN_venta().getId_venta());
            vista.jLabel_nombre.setText(""+ nombre_apellido[0]);
            vista.jLabel_apellido.setText(""+ nombre_apellido[1]);
            vista.jLabel_cedula.setText(""+object.getN_venta().getCliente().getCedula());
        }
    try {
        vista.jLabel_dolar.setText(cargar_dolar());
        vista.jLabel_dolar.setText("" + validacion.limpiar_valores(validacion.limitar_decimales(validacion.dolar.getDollarRate())));
        setDolar(Double.parseDouble(vista.jLabel_dolar.getText()));
    } catch (IOException ex) {
        Logger.getLogger(controlador_registro_pago.class.getName()).log(Level.SEVERE, null, ex);
    }
    vista.jLabel_monto_total_venta_dolares.setText(validacion.limitar_decimales(calcular_total_venta()/dolar));
    vista.jLabel_monto_total_venta_bolivares.setText(""+calcular_total_venta());
 
    }
    protected void ocular(boolean respuesta) {
//        vista.jLabel_referencia.setVisible(respuesta);
        vista.jTextField_referencia.setVisible(respuesta);
    }
protected double calcular_total_venta(){
double acumulador=0;

    for (detalle_de_ventas object : detalle_venta) {
        acumulador += object.getMonto_venta();
    }

return acumulador;
}
private ArrayList<descripcion_pago> lista_pago=new ArrayList<>();
    private void agregar_pago() {
        descripcion_pago descripcion_pago = new descripcion_pago();
        pago pago = new pago();
        pago.setId_pago(vista.jComboBox_forma_pago.getSelectedIndex());
        if (!(pago.getId_pago() == 0)) {
            pago.setTipo_de_pago(vista.jComboBox_forma_pago.getSelectedItem().toString());
            if (pago.getId_pago() == 1) {
                descripcion_pago.setRef_pago(JOptionPane.showInputDialog(null, "Digite la referencia del pago"));
            }
            if (!vista.jTextField_monto_cancelar.getText().isEmpty()) {
                double mont_pag = Double.parseDouble(vista.jTextField_monto_cancelar.getText());
                if (pago.getId_pago() == 2) {
                    descripcion_pago.setMonto_cancelado(mont_pag * getDolar());
                }
                if (!(pago.getId_pago() == 2)) {
                    descripcion_pago.setMonto_cancelado(mont_pag);
                } 
                if (pago.getId_pago() == 2 || !(pago.getId_pago() == 2)) {
                    descripcion_pago.setPago(pago);
                    lista_pago.add(descripcion_pago);
                    JOptionPane.showMessageDialog(null, "Pago registrado exitosamente");
                    vista.jTextField_monto_cancelar.setText("");
                    vista.jTextField_monto_cancelar.requestFocus();
                    vista.jComboBox_forma_pago.setSelectedIndex(0);
                    calcular_cambio();
                    double acumular = 0;
                    for (descripcion_pago li_p : lista_pago) {
                        acumular += li_p.getMonto_cancelado();
                    }
                
                }
            } else {
                JOptionPane.showMessageDialog(null, "Digite el monto del pago");
                vista.jTextField_monto_cancelar.requestFocus();
}
         }        else {
            JOptionPane.showMessageDialog(null, "Seleecionar una forma de pago");
        }
    }
 
    public void calcular_cambio() {
    try {
        double total_cuenta = Double.parseDouble(vista.jLabel_monto_total_venta_bolivares.getText());
        double total_pago = lista_pago.stream().mapToDouble(descripcion_pago::getMonto_cancelado).sum();
        
        vista.jLabel_monto_total_del_pago_bolivares.setText(""+validar_componente.limitar_decimales(total_pago));
        vista.jLabel_monto_total_del_pago_dolares.setText("" + validar_componente.limitar_decimales(total_pago / getDolar()));
        double diferencia = total_pago - total_cuenta;
        double tasaDolar = getDolar(); // Obtenemos la tasa del dólar
       
        if (diferencia > 0) {
            // Cambio a favor del cliente
            double cambioBolivares = diferencia;
            double cambioDolares = diferencia / tasaDolar;
          
            vista.jLabel_monto_total_del_pago_operacion.setText(String.format("%.2f", cambioBolivares));
            vista.jLabel_monto_total_del_pago_operacion_$.setText(String.format("%.2f", cambioDolares));
            vista.jLabel_monto_total_del_pago_operacion.setForeground(new Color(0, 128, 0));
        } else if (diferencia < 0) {
            // Falta pagar
            double faltaBolivares = Math.abs(diferencia);
            double faltaDolares = Math.abs(diferencia) / tasaDolar;
            vista.jLabel_monto_total_del_pago_operacion.setText(String.format("%.2f", faltaBolivares));
            vista.jLabel_monto_total_del_pago_operacion_$.setText(String.format("%.2f", faltaDolares));
            vista.jLabel_monto_total_del_pago_operacion.setForeground(new Color(255, 107, 82));
            
        } else {
            // Exacto
            vista.jLabel_monto_total_del_pago_operacion.setText("0.00");
            vista.jLabel_monto_total_del_pago_operacion_$.setText("0.00");
            vista.jLabel_monto_total_del_pago_operacion.setForeground(new Color(187, 187, 187));
        }
        
    } catch (NumberFormatException e) {
        System.err.println("Error en el formato del monto: " + e.getMessage());
        vista.jLabel_monto_total_del_pago_operacion.setText("Error");
        vista.jLabel_monto_total_del_pago_operacion_$.setText("Error");
    }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton_registrar) {
            sql.insert_table_descripcion_pago(lista_pago, detalle_venta.get(0).getN_venta());
            sql.con_ven.actualizarEstadoVentaAprobado(detalle_venta.get(0).getN_venta().getId_venta());
            controlador_menu.abrir_gestion_de_ventas();
            vista.dispose();
    }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
 if(e.getSource()==vista.jComboBox_forma_pago){
 if(e.getKeyChar()==e.VK_ENTER){
 agregar_pago();
 calcular_cambio();    
 }
 }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
