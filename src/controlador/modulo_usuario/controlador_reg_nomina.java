package controlador.modulo_usuario;

import consultas_bd.consulta_nomina;
import controlador.controlador_menu;
import controlador.controlador_padre;
import informe_reporte.GeneradorReciboNominaPDF;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.nomina;
import modelo.pago;
import modelo.usuario;
import validaciones.modulo_usuario.validar_nomina;
import vista.formularios.FormularioRegistroNomina;

public class controlador_reg_nomina extends controlador_padre implements ActionListener, KeyListener {

    private nomina modelo;
    private FormularioRegistroNomina vista;
    private consulta_nomina sql = new consulta_nomina();
    private validar_nomina validar = new validar_nomina();
    
    // Constantes para límites y validaciones
    private static final double SALARIO_MINIMO = 100.0;
    private static final double SALARIO_MAXIMO = 100000.0;
    private static final int MAX_REFERENCIA = 50;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public controlador_reg_nomina(nomina modelo, FormularioRegistroNomina vista) {
        this.modelo = modelo;
        this.vista = vista;
        iniciar();
        this.vista.jButton_aceptar.addActionListener(this);
        this.vista.jButton_limpiar.addActionListener(this);
        this.vista.jComboBox_cedula.addActionListener(this);
        this.vista.jComboBox_forma_de_pago.addActionListener(this);
        
        // Agregar KeyListener para validaciones en tiempo real
        this.vista.jTextField_monto.addKeyListener(this);
        this.vista.jTextField_referencia.addKeyListener(this);
    }

    @Override
    public void iniciar() {
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        mostrar();
        configurarValidacionesIniciales();
    }

    @Override
    protected void mostrar() {
        vista.jComboBox_cedula.removeAllItems();
        vista.jComboBox_cedula.addItem("Seleccionar un empleado");
        for (usuario cargar_empleado : sql.cargar_empleados()) {
            vista.jComboBox_cedula.addItem(cargar_empleado.getCedula() + " " + cargar_empleado.getNombre());
        }
        sql.con_pag.select_table_pago(vista.jComboBox_forma_de_pago);
        vista.jTextField_referencia.setVisible(false);
        
        // Establecer fecha actual por defecto
        vista.jDateChooser_fecha.setDate(new Date());
    }
    
    /**
     * Configura tooltips y validaciones iniciales
     */
    private void configurarValidacionesIniciales() {
        vista.jTextField_monto.setToolTipText("Salario entre $" + SALARIO_MINIMO + " y $" + SALARIO_MAXIMO);
        vista.jTextField_referencia.setToolTipText("Máximo " + MAX_REFERENCIA + " caracteres");
    }

    /**
     * Valida todos los campos antes de obtener los datos
     */
    private boolean validarCampos() {
        // Validar empleado seleccionado
        if (vista.jComboBox_cedula.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, 
                "Debe seleccionar un empleado", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jComboBox_cedula.requestFocus();
            return false;
        }
        
        // Validar forma de pago seleccionada
        if (vista.jComboBox_forma_de_pago.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, 
                "Debe seleccionar una forma de pago", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jComboBox_forma_de_pago.requestFocus();
            return false;
        }
        
        // Validar monto
        String montoTexto = vista.jTextField_monto.getText().trim();
        if (montoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El monto es requerido", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jTextField_monto.requestFocus();
            return false;
        }
        
        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto < SALARIO_MINIMO) {
                JOptionPane.showMessageDialog(null, 
                    "El salario no puede ser menor a $" + SALARIO_MINIMO, 
                    "Monto inválido", 
                    JOptionPane.WARNING_MESSAGE);
                vista.jTextField_monto.requestFocus();
                return false;
            }
            
            if (monto > SALARIO_MAXIMO) {
                JOptionPane.showMessageDialog(null, 
                    "El salario no puede exceder $" + SALARIO_MAXIMO, 
                    "Monto inválido", 
                    JOptionPane.WARNING_MESSAGE);
                vista.jTextField_monto.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "El monto debe ser un número válido", 
                "Formato inválido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jTextField_monto.requestFocus();
            return false;
        }
        
        // Validar período de pago
        if (vista.jComboBox_jornada.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, 
                "Debe seleccionar un período de pago", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jComboBox_jornada.requestFocus();
            return false;
        }
        
        // Validar estado de pago
        if (vista.jComboBox_estado_del_pago.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, 
                "Debe seleccionar un estado de pago", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            vista.jComboBox_estado_del_pago.requestFocus();
            return false;
        }
        
        // Validar fecha
        if (vista.jDateChooser_fecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, 
                "La fecha de pago es requerida", 
                "Campo requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar fecha futura
        if (vista.jDateChooser_fecha.getDate().after(new Date())) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                "La fecha seleccionada es futura. ¿Desea continuar?", 
                "Fecha futura", 
                JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return false;
            }
        }
        
        // Validar referencia para transferencia
        if (vista.jComboBox_forma_de_pago.getSelectedIndex() == 1) {
            String referencia = vista.jTextField_referencia.getText().trim();
            if (referencia.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "La referencia es requerida para pagos por transferencia", 
                    "Campo requerido", 
                    JOptionPane.WARNING_MESSAGE);
                vista.jTextField_referencia.requestFocus();
                return false;
            }
            
            if (referencia.length() > MAX_REFERENCIA) {
                JOptionPane.showMessageDialog(null, 
                    "La referencia no puede exceder " + MAX_REFERENCIA + " caracteres", 
                    "Longitud excedida", 
                    JOptionPane.WARNING_MESSAGE);
                vista.jTextField_referencia.requestFocus();
                return false;
            }
        }
        
        return true;
    }

    protected boolean obtener_datos() {
        if (!validarCampos()) {
            return false;
        }
        
        try {
            if (vista.jComboBox_cedula.getSelectedIndex() != 0 && vista.jComboBox_forma_de_pago.getSelectedIndex() != 0) {
                usuario usuario = new usuario();
                String usu = vista.jComboBox_cedula.getSelectedItem().toString();
                String[] id_usu = usu.split(" ");
                usuario.setId_persona(sql.con_usu.seleccionar_usuario_cedula(id_usu[0]).getId_persona());
                modelo.setUsuario(usuario);
                
                pago pago = new pago();
                String pagos = vista.jComboBox_forma_de_pago.getSelectedItem().toString();
                pago.setId_pago(sql.con_pag.buscar_forma_de_pago(pagos));
                modelo.setPago(pago);
                
                modelo.setPeriodo_pago(vista.jComboBox_jornada.getSelectedItem().toString());
                modelo.setEstado_pago(vista.jComboBox_estado_del_pago.getSelectedItem().toString());
                modelo.setSalario_bruto(Double.parseDouble(vista.jTextField_monto.getText().trim()));
                
                if (!vista.jTextField_referencia.getText().isEmpty()) {
                    modelo.setRef_pago(vista.jTextField_referencia.getText().trim());
                } else {
                    modelo.setRef_pago(null);
                }
                
                modelo.setFecha_pago(vista.jDateChooser_fecha.getDate());
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al procesar los datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }

    @Override
    protected void limpiar_todo() {
        int respuesta = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de que desea limpiar todos los campos? Se perderán los datos ingresados.", 
            "Confirmar limpieza", 
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            vista.jComboBox_cedula.setSelectedIndex(0);
            vista.jComboBox_estado_del_pago.setSelectedIndex(0);
            vista.jComboBox_forma_de_pago.setSelectedIndex(0);
            vista.jComboBox_jornada.setSelectedIndex(0);
            vista.jLabel_cargo.setText("");
            vista.jTextField_monto.setText("");
            vista.jTextField_referencia.setVisible(false);
            vista.jTextField_referencia.setText("");
            vista.jDateChooser_fecha.setDate(new Date()); // Establecer fecha actual
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jComboBox_cedula) {
            if (vista.jComboBox_cedula.getSelectedIndex() != 0) {
                String com = vista.jComboBox_cedula.getSelectedItem().toString();
                String[] cedula = com.split(" ");
                try {
                    for (usuario cargar_empleado : sql.cargar_empleados()) {
                        if (cargar_empleado.getCedula() == Integer.parseInt(cedula[0])) {
                            vista.jLabel_cargo.setText(cargar_empleado.getRol().getTipo_rol());
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Error al cargar información del empleado", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                vista.jLabel_cargo.setText("");
            }
        }
        
        if (e.getSource() == vista.jComboBox_forma_de_pago) {
            int opciones = vista.jComboBox_forma_de_pago.getSelectedIndex();
            if (opciones != 0) {
                if (opciones == 1) { // Transferencia
                    vista.jTextField_referencia.setVisible(true);
                    vista.jTextField_referencia.requestFocus();
                } else {
                    vista.jTextField_referencia.setVisible(false);
                    vista.jTextField_referencia.setText("");
                }
            } else {
                vista.jTextField_referencia.setVisible(false);
                vista.jTextField_referencia.setText("");
            }
        }
        
        if (e.getSource() == vista.jButton_aceptar) {
            if (obtener_datos()) {
                if (sql.registrar_nomina(modelo)) {
                    JOptionPane.showMessageDialog(null, 
                        "Pago de empleado registrado exitosamente", 
                        "Registro exitoso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    try {
                        int id_nomina = sql.cargar_id_nomina();
                        GeneradorReciboNominaPDF grn = new GeneradorReciboNominaPDF();
                        grn.generarReciboNominaPDF(id_nomina);
                        
                        JOptionPane.showMessageDialog(null, 
                            "Recibo de nómina generado exitosamente", 
                            "PDF generado", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        Logger.getLogger(controlador_reg_nomina.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, 
                            "Error al generar el recibo PDF: " + ex.getMessage(), 
                            "Error PDF", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                    
                    limpiar_todo();
                    vista.dispose();
                    controlador_menu.vista_pago_nomina();
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Error al registrar el pago de nómina", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        if (e.getSource() == vista.jButton_limpiar) {
            limpiar_todo();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Validar que solo se ingresen números en el campo monto
        if (e.getSource() == vista.jTextField_monto) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
            
            // Prevenir múltiples puntos decimales
            String texto = vista.jTextField_monto.getText();
            if (c == '.' && texto.contains(".")) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        
        // Limitar longitud del campo referencia
        if (e.getSource() == vista.jTextField_referencia) {
            if (vista.jTextField_referencia.getText().length() >= MAX_REFERENCIA) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Enter para navegar entre campos
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (e.getSource() == vista.jTextField_monto) {
                vista.jComboBox_jornada.requestFocus();
            } else if (e.getSource() == vista.jTextField_referencia) {
                vista.jButton_aceptar.requestFocus();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Validación en tiempo real para el monto
        if (e.getSource() == vista.jTextField_monto) {
            String texto = vista.jTextField_monto.getText();
            if (!texto.isEmpty()) {
                try {
                    double monto = Double.parseDouble(texto);
                    if (monto < SALARIO_MINIMO || monto > SALARIO_MAXIMO) {
                        vista.jTextField_monto.setForeground(Color.RED);
                    } else {
                        vista.jTextField_monto.setForeground(Color.BLACK);
                    }
                } catch (NumberFormatException ex) {
                    vista.jTextField_monto.setForeground(Color.RED);
                }
            }
        }
        
        // Validación en tiempo real para referencia
        if (e.getSource() == vista.jTextField_referencia) {
            String texto = vista.jTextField_referencia.getText();
            if (texto.length() > MAX_REFERENCIA) {
                vista.jTextField_referencia.setText(texto.substring(0, MAX_REFERENCIA));
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    
    /**
     * Método para validar si ya existe un pago para el mismo empleado en el mismo período
     */
    private boolean validarPagoDuplicado(int idUsuario, String periodo, Date fecha) {
        // Implementar lógica para verificar duplicados
        // return sql.existePagoDuplicado(idUsuario, periodo, fecha);
        return false; // Temporal - implementar según tu base de datos
    }
}