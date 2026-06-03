package controlador.modulo_venta;

import controlador.*;
import consultas_bd.consulta_cliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.cliente;
import modelo.parroquia;
import vista.formularios.FormularioRegCliNuevo;

public class controlador_registro_cli extends controlador_padre implements ActionListener{
    private FormularioRegCliNuevo vista;
    private cliente modelo;
    private consulta_cliente sql = new consulta_cliente();
    
    public controlador_registro_cli(FormularioRegCliNuevo vista, cliente modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.jButton_registrar1.addActionListener(this);
        this.vista.jButton_cancelar2.addActionListener(this);
    }

    @Override
    protected void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        sql.ubicacion.select_cargar_ubicaciones(vista.jComboBox1);
    }

    @Override
    public void agregar(){
        // Validar campos antes de proceder
        if (!validarCampos()) {
            return;
        }
        
        modelo.setNombre(vista.jTextField_nombre.getText().trim());
        modelo.setApellido(vista.jTextField_apellido.getText().trim());
        modelo.setCedula(Integer.parseInt(vista.jTextField_cedula.getText().trim()));
        sql.insert_table_Persona(modelo);
        modelo.setId_persona(sql.select_id_persona());
        parroquia ubicacion = new parroquia();
        ubicacion.setId_parroquia(vista.jComboBox1.getSelectedIndex());
        modelo.setUbicacion(ubicacion);
        
        // Obtener número telefónico completo
        String codigoArea = (String) vista.jComboBox_numeros_tlf1.getSelectedItem();
        String numero = vista.jTextField_telefono.getText().trim();
        modelo.setTelefeno(codigoArea + numero);
        
        sql.insert_table_Cliente(modelo);
        JOptionPane.showMessageDialog(vista, 
            "Cliente registrado exitosamente", 
            "Registro Exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
        vista.dispose();
    }

    @Override
    protected void limpiar_todo() {
        vista.jTextField_nombre.setText("");
        vista.jTextField_apellido.setText("");
        vista.jTextField_cedula.setText("");
        vista.jTextField_telefono.setText("");
        vista.jComboBox1.setSelectedIndex(0);
        vista.jComboBox_numeros_tlf1.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.jButton_registrar1){
            agregar();
        }
        if(e.getSource() == vista.jButton_cancelar2){
            limpiar_todo();
        }
    }

    // Método para validar todos los campos
    private boolean validarCampos() {
        // Validar nombre
        String nombre = vista.jTextField_nombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("El campo Nombre es obligatorio");
            vista.jTextField_nombre.requestFocus();
            return false;
        }
        if (nombre.length() < 2 || nombre.length() > 50) {
            mostrarError("El nombre debe tener entre 2 y 50 caracteres");
            vista.jTextField_nombre.requestFocus();
            return false;
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            mostrarError("El nombre solo puede contener letras y espacios");
            vista.jTextField_nombre.requestFocus();
            return false;
        }

        // Validar apellido
        String apellido = vista.jTextField_apellido.getText().trim();
        if (apellido.isEmpty()) {
            mostrarError("El campo Apellido es obligatorio");
            vista.jTextField_apellido.requestFocus();
            return false;
        }
        if (apellido.length() < 2 || apellido.length() > 50) {
            mostrarError("El apellido debe tener entre 2 y 50 caracteres");
            vista.jTextField_apellido.requestFocus();
            return false;
        }
        if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            mostrarError("El apellido solo puede contener letras y espacios");
            vista.jTextField_apellido.requestFocus();
            return false;
        }

        // Validar cédula
        String cedulaTexto = vista.jTextField_cedula.getText().trim();
        if (cedulaTexto.isEmpty()) {
            mostrarError("El campo Cédula es obligatorio");
            vista.jTextField_cedula.requestFocus();
            return false;
        }
        
        try {
            long cedula = Long.parseLong(cedulaTexto);
            if (!validarCedulaVenezolana(cedulaTexto)) {
                mostrarError("La cédula no es válida. Debe tener entre 6 y 8 dígitos");
                vista.jTextField_cedula.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("La cédula debe contener solo números");
            vista.jTextField_cedula.requestFocus();
            return false;
        }

        // Validar teléfono
        String telefono = vista.jTextField_telefono.getText().trim();
        if (telefono.isEmpty()) {
            mostrarError("El campo Teléfono es obligatorio");
            vista.jTextField_telefono.requestFocus();
            return false;
        }
        
        if (!validarTelefonoVenezolano(telefono)) {
            mostrarError("El teléfono debe contener exactamente 7 dígitos numericos");
            vista.jTextField_telefono.requestFocus();
            return false;
        }

        // Validar dirección
        if (vista.jComboBox1.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar una dirección válida");
            vista.jComboBox1.requestFocus();
            return false;
        }

        return true;
    }

    // Método para validar cédula venezolana
    private boolean validarCedulaVenezolana(String cedula) {
        // Eliminar cualquier espacio o caracter no numérico
        cedula = cedula.replaceAll("[^0-9]", "");
        
        // Validar longitud (cédulas venezolanas típicamente tienen entre 6 y 8 dígitos)
        if (cedula.length() < 6 || cedula.length() > 8) {
            return false;
        }
        
        // Validar que todos los caracteres sean dígitos
        if (!cedula.matches("\\d+")) {
            return false;
        }
        
        // Validar que no sea una secuencia de números repetidos (ej: 000000, 111111, etc.)
        if (cedula.matches("(\\d)\\1{5,}")) {
            return false;
        }
        
        return true;
    }

    // Método para validar teléfono venezolano
    private boolean validarTelefonoVenezolano(String telefono) {
        // Eliminar cualquier espacio o caracter no numérico
        telefono = telefono.replaceAll("[^0-9]", "");
        
        // Validar longitud exacta de 7 dígitos (sin el código de área)
        if (telefono.length() != 7) {
            return false;
        }
        
        // Validar que todos los caracteres sean dígitos
        if (!telefono.matches("\\d+")) {
            return false;
        }
        
        // Validar que el primer dígito no sea 0
        if (telefono.startsWith("0")) {
            return false;
        }
        
        return true;
    }

    // Método para mostrar mensajes de error
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, 
            mensaje, 
            "Error de Validación", 
            JOptionPane.ERROR_MESSAGE);
    }
}