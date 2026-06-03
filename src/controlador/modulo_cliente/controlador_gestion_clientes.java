package controlador.modulo_cliente;

import controlador.*;
import consultas_bd.consulta_cliente;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import modelo.cliente;
import modelo.parroquia;
import validaciones.modulo_cliente.validar_cliente;
import vista.consultas.DetalleClienteNuevo;
import vista.gestion.VistaGestionCliente;

public class controlador_gestion_clientes extends controlador_padre implements ActionListener,MouseListener,KeyListener{

    private cliente modelo;
    private VistaGestionCliente vista;
    private consulta_cliente sql=new consulta_cliente();
    private validar_cliente validacion=new validar_cliente();
    
    // Constantes para límites de caracteres
    private static final int MAX_CEDULA = 8;
    private static final int MAX_NOMBRE = 50;
    private static final int MAX_APELLIDO = 50;
    private static final int MAX_TELEFONO = 7; // Sin el código de área
    private static final int MAX_BUSQUEDA = 100;

    public controlador_gestion_clientes(cliente modelo, VistaGestionCliente vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.jButton_agregar.addActionListener(this);
        this.vista.jButton_modificar.addActionListener(this);
        this.vista.jButton_limpiar.addActionListener(this);
        this.vista.jMenuItem_inhabilitar.addActionListener(this);
        this.vista.jMenuItem_activar.addActionListener(this);
        this.vista.jMenuItem_editar.addActionListener(this);
        this.vista.jButton_activos.addActionListener(this);
        this.vista.jButton_inactivos.addActionListener(this);
        this.vista.jTextField_busqueda.addKeyListener(this);
        this.vista.jMenuItem_consulta_cliente.addActionListener(this);
        this.vista.jTable_lista_cliente.addMouseListener(this);
        this.vista.jTextField_cedula.addKeyListener(this);
        this.vista.jTextField_nombre.addKeyListener(this);
        this.vista.jTextField_apellido.addKeyListener(this);
        this.vista.jTextField_telefono.addKeyListener(this);
    }

    // Modificar iniciar()
    @Override
    public void iniciar() {
        vista.setVisible(false);
        mostrarBarraProgreso("Cargando Gestión de Clientes", "Cargando gestión de clientes...", vista);
        mostrar();
    }

    @Override
    protected void mostrar() {
        sql.select_table_cliente(vista.jTable_lista_cliente, "Activo",true,"");
        sql.ubicacion.select_cargar_ubicaciones(vista.jComboBox_ubicacion);
        vista.jButton_modificar.setEnabled(false);
        vista.jButton_activos.setVisible(false);
        vista.jButton_inactivos.setVisible(true);
    }

    public boolean agregar_() {
        if (validacion.validar_campo(vista.jTextField_nombre.getText(), vista.jTextField_apellido.getText(),
                vista.jTextField_telefono.getText()) && vista.jComboBox_ubicacion.getSelectedIndex()!=0) {
            String telefono = vista.jComboBox_numeros_tlf.getSelectedItem().toString() + "" + vista.jTextField_telefono.getText();
            modelo.setNombre(vista.jTextField_nombre.getText().trim());
            modelo.setApellido(vista.jTextField_apellido.getText().trim());
            modelo.setCedula(Integer.parseInt(vista.jTextField_cedula.getText()));
            parroquia ubicacion = new parroquia();
            ubicacion.setId_parroquia(vista.jComboBox_ubicacion.getSelectedIndex());
            modelo.setUbicacion(ubicacion);
            modelo.setTelefeno(telefono);
        return true;
        }// Validar que no haya campos vacíos
        else if (!validarCamposCompletos()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
        }
        
        // Validar cédula
                else if (!validarCedula(vista.jTextField_cedula.getText())) {
            JOptionPane.showMessageDialog(vista, "La cédula debe tener exactamente " + MAX_CEDULA + " dígitos", "Cédula Inválida", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_cedula.requestFocus();
        }
        
        // Validar teléfono
       else if (!validarTelefono(vista.jTextField_telefono.getText())) {
            JOptionPane.showMessageDialog(vista, "El teléfono debe tener exactamente " + MAX_TELEFONO + " dígitos", "Teléfono Inválido", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_telefono.requestFocus();
        }
        
        // Validar ubicación
      else  if (vista.jComboBox_ubicacion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una ubicación válida", "Ubicación Requerida", JOptionPane.WARNING_MESSAGE);
            vista.jComboBox_ubicacion.requestFocus();
      }
    return false;
    }

    // Métodos de validación adicionales
    private boolean validarCamposCompletos() {
        return !vista.jTextField_nombre.getText().trim().isEmpty() &&
               !vista.jTextField_apellido.getText().trim().isEmpty() &&
               !vista.jTextField_cedula.getText().trim().isEmpty() &&
               !vista.jTextField_telefono.getText().trim().isEmpty();
    }

    private boolean validarCedula(String cedula) {
        return cedula.matches("\\d{" + MAX_CEDULA + "}");
    }

    private boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{" + MAX_TELEFONO + "}");
    }

    // Método para limitar caracteres en cualquier campo
    private void limitarCaracteres(javax.swing.JTextField campo, int maxLength) {
        String texto = campo.getText();
        if (texto.length() > maxLength) {
            campo.setText(texto.substring(0, maxLength));
            campo.setCaretPosition(maxLength);
        }
    }

    // Método para validar solo números
    private void validarSoloNumeros(javax.swing.JTextField campo) {
        String texto = campo.getText();
        if (!texto.matches("\\d*")) {
            campo.setText(texto.replaceAll("[^\\d]", ""));
        }
    }

    // Método para validar solo letras y espacios
    private void validarSoloLetras(javax.swing.JTextField campo) {
        String texto = campo.getText();
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) {
            campo.setText(texto.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]", ""));
        }
    }
   
    private void seleccionar(){
        int filas = vista.jTable_lista_cliente.getSelectedRow();
        if(filas >= 0){
            try {
                modelo.setId_persona(sql.select_id_persona(vista.jTable_lista_cliente.getValueAt(vista.jTable_lista_cliente.getSelectedRow(), 0).toString()));
                modelo.setId_cliente(Integer.parseInt(vista.jTable_lista_cliente.getValueAt(vista.jTable_lista_cliente.getSelectedRow(), 0).toString()));
                vista.jTextField_nombre.setText(vista.jTable_lista_cliente.getValueAt(filas, 1).toString());
                vista.jTextField_apellido.setText(vista.jTable_lista_cliente.getValueAt(filas, 2).toString());
                String[] cedula;
                String cedula_selec = vista.jTable_lista_cliente.getValueAt(filas, 3).toString();
                cedula = cedula_selec.split("-");
                vista.jTextField_cedula.setText(cedula.length > 1 ? cedula[1] : cedula_selec);
                String[] numero;
                String numero_selec = vista.jTable_lista_cliente.getValueAt(filas, 4).toString();
                numero = numero_selec.split("-");
                if(numero.length > 1) {
                    vista.jComboBox_numeros_tlf.setSelectedItem(numero[0]);
                    vista.jTextField_telefono.setText(numero[1]);
                } else {
                    vista.jTextField_telefono.setText(numero_selec);
                }
                vista.jComboBox_ubicacion.setSelectedItem(vista.jTable_lista_cliente.getValueAt(filas, 5).toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al cargar los datos del cliente: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jMenuItem_consulta_cliente) {
            DetalleClienteNuevo vista = new DetalleClienteNuevo();
            controlador_detalle_cliente ctrl = new controlador_detalle_cliente(modelo, vista);
            ctrl.iniciar();
        }
        if (e.getSource() == vista.jButton_agregar) {
            if(agregar_()){
            if(sql.insert_table_Persona(modelo)){
                if(sql.insert_table_Cliente(modelo)){
                    limpiar_todo();
                    mostrar();
                    JOptionPane.showMessageDialog(vista, "Cliente agregado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            }
            
        }
        if (e.getSource() == vista.jButton_limpiar) {
            limpiar_todo();
        }
        if (e.getSource() == vista.jButton_activos) {
            vista.jButton_activos.setVisible(false);
            vista.jButton_inactivos.setVisible(true);
            sql.select_table_cliente(vista.jTable_lista_cliente,"Activo",true,"");
        }
        if (e.getSource() == vista.jButton_inactivos) {
            vista.jButton_inactivos.setVisible(false);
            vista.jButton_activos.setVisible(true);
            sql.select_table_cliente(vista.jTable_lista_cliente, "Inactivo",true,"");
        }
        if (e.getSource() == vista.jMenuItem_inhabilitar) {
            int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Está seguro de que desea inhabilitar este cliente?", 
                "Confirmar Inhabilitación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modelo.setEstado("Inactivo");
                sql.inhabilitar_o_activar_cliente(modelo);
                mostrar();
                limpiar_todo();
                JOptionPane.showMessageDialog(vista, "Cliente inhabilitado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (e.getSource() == vista.jMenuItem_activar) {
            int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Está seguro de que desea activar este cliente?", 
                "Confirmar Activación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modelo.setEstado("Activo");
                sql.inhabilitar_o_activar_cliente(modelo);
                sql.select_table_cliente(vista.jTable_lista_cliente, "Inactivo",true,"");
                limpiar_todo();
                JOptionPane.showMessageDialog(vista, "Cliente activado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(e.getSource() == vista.jMenuItem_editar){
            seleccionar();
            vista.jButton_agregar.setEnabled(false);
            vista.jButton_modificar.setEnabled(true);
        }
        if (e.getSource() == vista.jButton_modificar) {
            // Validar que se haya seleccionado un cliente para modificar
            if (modelo.getId_cliente() == 0) {
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione un cliente para modificar", 
                    "Cliente No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
           if(agregar_()){
            if(sql.update_table_Persona(modelo)){
                if (sql.update_table_Cliente(modelo)) {
                    limpiar_todo();
                    mostrar();
                    vista.jButton_agregar.setEnabled(true);
                    vista.jButton_modificar.setEnabled(false);
                    JOptionPane.showMessageDialog(vista, "Cliente modificado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
           } 
         }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == vista.jTable_lista_cliente){
            if(e.getButton() == MouseEvent.BUTTON1){
                try {
                    modelo.setId_cliente(Integer.parseInt(vista.jTable_lista_cliente.getValueAt(vista.jTable_lista_cliente.getSelectedRow(), 0).toString()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error al seleccionar el cliente", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(e.getButton() == MouseEvent.BUTTON3){
                if (vista.jButton_activos.isVisible()) {
                    vista.jMenuItem_inhabilitar.setVisible(false);
                    vista.jMenuItem_activar.setVisible(true);
                }
                if (vista.jButton_inactivos.isVisible()) {
                    vista.jMenuItem_activar.setVisible(false);
                    vista.jMenuItem_inhabilitar.setVisible(true);
                }
                vista.jPopupMenu1.show(vista.jTable_lista_cliente, e.getX(),e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == vista.jTextField_busqueda){
            limitarCaracteres(vista.jTextField_busqueda, MAX_BUSQUEDA);
            sql.select_table_cliente(vista.jTable_lista_cliente, "Activo", false, vista.jTextField_busqueda.getText());
        }
        if(e.getSource() == vista.jTextField_cedula){
            validarSoloNumeros(vista.jTextField_cedula);
            limitarCaracteres(vista.jTextField_cedula, MAX_CEDULA);
            limitarCaracteresConContador(vista.jTextField_cedula, MAX_CEDULA, vista.jLabel_cedula, "numero");
        }
        if(e.getSource() == vista.jTextField_nombre){
            validarSoloLetras(vista.jTextField_nombre);
            limitarCaracteres(vista.jTextField_nombre, MAX_NOMBRE);
        }
        if(e.getSource() == vista.jTextField_apellido){
            validarSoloLetras(vista.jTextField_apellido);
            limitarCaracteres(vista.jTextField_apellido, MAX_APELLIDO);
        }
        if(e.getSource() == vista.jTextField_telefono){
            validarSoloNumeros(vista.jTextField_telefono);
            limitarCaracteres(vista.jTextField_telefono, MAX_TELEFONO);
        }
    }

    @Override
    protected void limpiar_todo() {
        vista.jTextField_nombre.setText("");
        vista.jTextField_apellido.setText("");
        vista.jTextField_cedula.setText("");
        vista.jTextField_telefono.setText("");
        vista.jTextField_busqueda.setText("");
        vista.jComboBox_ubicacion.setSelectedIndex(0);
        
        // Resetear el modelo
        modelo = new cliente(); // O como se inicialice tu modelo
        vista.jButton_agregar.setEnabled(true);
        vista.jButton_modificar.setEnabled(false);
    }
}