package controlador.modulo_compra;

import consultas_bd.consulta_provedor;
import controlador.controlador_padre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import modelo.proveedor;
import validaciones.modulo_orden_compra.validar_proveedor;
import vista.gestion.GestionarProveedores;

public class controlador_proveedor extends controlador_padre implements ActionListener, MouseListener, KeyListener {
    private proveedor modelo;
    private GestionarProveedores vista;
    private consulta_provedor sql = new consulta_provedor();
    private validar_proveedor validacion = new validar_proveedor();

    // Constantes para límites de campos
    private static final int MAX_NOMBRE = 100;
    private static final int MAX_TELEFONO = 7;
    private static final int MAX_CORREO = 100;
    private static final int MAX_DIRECCION = 200;

    public controlador_proveedor(proveedor modelo, GestionarProveedores vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        // Configurar límites de caracteres
        configurarLimitesCampos();
        
        this.vista.jButton_registrar.addActionListener(this);
        this.vista.jButton_limpiar.addActionListener(this);
        this.vista.jButton_modificar.addActionListener(this);
        this.vista.jTextField_buscar_proveedor.addKeyListener(this);
        this.vista.jTable_table_proveedores.addMouseListener(this);
        this.vista.JMenuItemSeleccionar.addActionListener(this);
    }

    // Configurar límites de caracteres para los campos
    private void configurarLimitesCampos() {
        // Limitar nombre
        vista.jTextField_nombre.setDocument(new LimitedDocument(MAX_NOMBRE));
        
        // Limitar teléfono (solo números)
        vista.jTextField_telefono.setDocument(new NumericLimitedDocument(MAX_TELEFONO));
        
        // Limitar correo
        vista.jTextField_correo.setDocument(new LimitedDocument(MAX_CORREO));
        
        // Limitar dirección
        vista.jTextField_ubicacion.setDocument(new LimitedDocument(MAX_DIRECCION));
        
        // Limitar campo de búsqueda
        vista.jTextField_buscar_proveedor.setDocument(new LimitedDocument(50));
    }

    @Override
    public void iniciar() {
        vista.setVisible(false);
        mostrarBarraProgreso("Cargando Gestión de Proveedores", "Cargando gestión de proveedores...", vista);
        mostrar("activo");
    }

    public boolean obtener_datos() {
        // Validar campos vacíos
        if (!validacion.validar_campo(vista.jTextField_nombre.getText(), 
                                    vista.jTextField_correo.getText(), 
                                    vista.jTextField_telefono.getText())) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar formato de teléfono
        if (!validarTelefono(vista.jTextField_telefono.getText())) {
            JOptionPane.showMessageDialog(vista, "El teléfono debe contener solo números y tener entre 7 y 15 dígitos", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_telefono.requestFocus();
            return false;
        }

        // Validar formato de correo
        if (!validarCorreo(vista.jTextField_correo.getText())) {
            JOptionPane.showMessageDialog(vista, "El formato del correo electrónico no es válido", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_correo.requestFocus();
            return false;
        }

        // Validar longitud mínima del nombre
        if (vista.jTextField_nombre.getText().trim().length() < 2) {
            JOptionPane.showMessageDialog(vista, "El nombre debe tener al menos 2 caracteres", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_nombre.requestFocus();
            return false;
        }

        try {
            if (!vista.jLabel_id_provedoor.getText().isEmpty()) {
                modelo.setId_proveedor(Integer.parseInt(vista.jLabel_id_provedoor.getText()));
            }
            
            modelo.setNombre(vista.jTextField_nombre.getText().trim());
            modelo.setTelefono(vista.jComboBox_numeros_tlf.getSelectedItem().toString() + "-" + vista.jTextField_telefono.getText());
            modelo.setCorreo_electronico(vista.jTextField_correo.getText().trim());
            modelo.setDireccion(vista.jTextField_ubicacion.getText().trim());
            modelo.setEstado("activo");
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Error en el formato del ID del proveedor", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Validar formato de teléfono
    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        
        // Remover espacios y validar que solo contenga números
        String telefonoLimpio = telefono.replaceAll("\\s+", "");
        if (!telefonoLimpio.matches("\\d+")) return false;
        
        // Validar longitud del teléfono (sin incluir el código de área del combo)
        return telefonoLimpio.length() == 7;
    }

    // Validar formato de correo electrónico
    private boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) return false;
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return correo.matches(emailRegex);
    }

    @Override
    protected void agregar() {
        if (obtener_datos()) {
            if (sql.insert_table_proveedor(modelo)) {
                JOptionPane.showMessageDialog(vista, "Proveedor registrado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar_todo();
                mostrar("activo");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar el proveedor", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void actualizar() {
        if (obtener_datos()) {
            if (sql.update_table_proveedor(modelo)) {
                JOptionPane.showMessageDialog(vista, "Proveedor actualizado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar_todo();
                mostrar("activo");
                vista.jButton_modificar.setEnabled(false);
                vista.jButton_registrar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el proveedor", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void seleecionar() {
        int fila = vista.jTable_table_proveedores.getSelectedRow();
        if (fila >= 0) {
            try {
                vista.jLabel_id_provedoor.setText(vista.jTable_table_proveedores.getValueAt(fila, 0).toString());
                vista.jTextField_nombre.setText(vista.jTable_table_proveedores.getValueAt(fila, 1).toString());
                
                String telefono_selec = vista.jTable_table_proveedores.getValueAt(fila, 2).toString();
                String[] telefono = telefono_selec.split("-");
                
                if (telefono.length >= 2) {
                    vista.jComboBox_numeros_tlf.setSelectedItem(telefono[0]);
                    vista.jTextField_telefono.setText(telefono[1]);
                }
                
                vista.jTextField_correo.setText(vista.jTable_table_proveedores.getValueAt(fila, 3).toString());
                vista.jTextField_ubicacion.setText(vista.jTable_table_proveedores.getValueAt(fila, 4).toString());
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error al cargar los datos del proveedor", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void mostrar(String estado) {
        sql.select_table_proveedor(vista.jTable_table_proveedores, estado, true, "");
        vista.updateUI();
        vista.jButton_modificar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton_modificar) {
            actualizar();
        } else if (e.getSource() == vista.jButton_registrar) {
            agregar();
        } else if (e.getSource() == vista.jButton_limpiar) {
            limpiar_todo();
        } else if (e.getSource() == vista.jButton_activo) {
            mostrar("activo");
            vista.jButton_activo.setVisible(false);
            vista.jButton_inhabilitado.setVisible(true);
        } else if (e.getSource() == vista.JMenuItemSeleccionar) {
            seleecionar();
            vista.jButton_modificar.setEnabled(true);
            vista.jButton_registrar.setEnabled(false); // Deshabilitar registrar cuando se está modificando
        }
    }

    @Override
    protected void limpiar_todo() {
        vista.jLabel_id_provedoor.setText("");
        vista.jTextField_nombre.setText("");
        vista.jTextField_correo.setText("");
        vista.jTextField_telefono.setText("");
        vista.jTextField_ubicacion.setText("");
        vista.jButton_modificar.setEnabled(false);
        vista.jButton_registrar.setEnabled(true);
        vista.jTextField_nombre.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == vista.jTable_table_proveedores) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                vista.JPopuMenu1.show(vista.jTable_table_proveedores, e.getX(), e.getY());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.jTextField_buscar_proveedor) {
            String textoBusqueda = vista.jTextField_buscar_proveedor.getText().trim();
            // Validar que no exceda el límite de búsqueda
            if (textoBusqueda.length() <= 50) {
                sql.select_table_proveedor(vista.jTable_table_proveedores, "", false, textoBusqueda);
            }
        }
    }

    // Métodos de interfaz no utilizados (mantener vacíos)
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    // Clases internas para limitar caracteres
    private class LimitedDocument extends PlainDocument {
        private int maxLength;

        public LimitedDocument(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            
            if ((getLength() + str.length()) <= maxLength) {
                super.insertString(offs, str, a);
            }
        }
    }

    private class NumericLimitedDocument extends LimitedDocument {
        public NumericLimitedDocument(int maxLength) {
            super(maxLength);
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            
            // Solo permitir números
            if (str.matches("\\d+")) {
                super.insertString(offs, str, a);
            }
        }
    }
}