package controlador.modulo_usuario;

import consultas_bd.consulta_usuario;
import controlador.controlador_padre;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import modelo.roles;
import modelo.usuario;
import modelo.ventas;
import validaciones.modulo_usuario.validar_usuario;
import vista.formularios.FormularioRegUsuNuevo;
import vista.gestion.VistaGestionUsuario;

public class controlador_usuario extends controlador_padre implements ActionListener,MouseListener,KeyListener{
   private VistaGestionUsuario vista;
   private usuario modelo;
   private consulta_usuario sql=new consulta_usuario();
   private validar_usuario validaciones=new validar_usuario();

    public controlador_usuario(VistaGestionUsuario vista, usuario modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.JButton3_registrar.addActionListener(this);
        this.vista.jTextField_busqueda.addKeyListener(this);
        this.vista.jComboBox_filtro.addActionListener(this);
        this.vista.jTable_usuarios.addMouseListener(this);
        this.vista.jMenuItem_editar.addActionListener(this);
        this.vista.jButton_actualizar.addActionListener(this);
        this.vista.jButton_limpiar.addActionListener(this);
        this.vista.jMenuItem_inactivar.addActionListener(this);
//        this.vista.jLabel_ojo_activo.addMouseListener(this);
  //      this.vista.jLabel_ojo_inactivo.addMouseListener(this);
    }

// Modificar iniciar()
@Override
public void iniciar() {
        vista.setVisible(false);
        
        mostrarBarraProgreso("Cargando Gestión de Usuarios", "Cargando gestión de usuarios...", vista);
        mostrar();
        ocultar(false);
}
public boolean ocultar(boolean respuesta){
vista.jTextField_nombre_usuario.setVisible(respuesta);
vista.jTextField_correo1.setVisible(respuesta);
vista.jPasswordField1.setVisible(respuesta);
vista.jComboBox_roles_usuarios.setVisible(respuesta);
/*vista.jLabel_ojo_activo.setVisible(respuesta);
vista.jLabel_ojo_inactivo.setVisible(respuesta);*/
return false;
}
    @Override
    protected void mostrarBarraProgreso(String titulo, String mensaje, JInternalFrame vista) {
        super.mostrarBarraProgreso(titulo, mensaje, vista); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
   
    public void seleccionar() {
        int fila = vista.jTable_usuarios.getSelectedRow();
        if (fila >= 0) {
            vista.jButton_actualizar.setEnabled(true);
            vista.jTextField_nombre_usuario.setText(vista.jTable_usuarios.getValueAt(fila, 0).toString());
            vista.jPasswordField1.setText(vista.jTable_usuarios.getValueAt(fila, 1).toString());
            vista.jTextField_correo1.setText(vista.jTable_usuarios.getValueAt(fila, 2).toString());
            vista.jComboBox_roles_usuarios.setSelectedItem(vista.jTable_usuarios.getValueAt(fila, 3).toString());
            vista.jLabel_id_usuario.setText(vista.jTable_usuarios.getValueAt(fila, 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se a seleccionado ninguna fila");
        }
    }

    public boolean obtener_datos() {
        if (validaciones.validar_campo(vista.jTextField_nombre_usuario.getText(), vista.jPasswordField1.getText(),
                vista.jTextField_correo1.getText())) {
            if(vista.jComboBox_roles_usuarios.getSelectedIndex()!=0){
            modelo.setId_persona(Integer.parseInt(vista.jLabel_id_usuario.getText()));
            modelo.setNombre_usuario(vista.jTextField_nombre_usuario.getText());
            modelo.setContraseña_usuario(vista.jPasswordField1.getText());
            modelo.setCorreo_electronico(vista.jTextField_correo1.getText());
            roles Rol = new roles();
            Rol.setId_rol(sql.roles.buscar_id_roles(vista.jComboBox_roles_usuarios.getSelectedItem().toString()));
            modelo.setRol(Rol);
            sql.modificar_usuario(modelo);
            return true;
            }else{
            JOptionPane.showMessageDialog(null,"Seleccionar un rol");
            }
        }
        return false;
    }

    @Override
    protected void mostrar() {
        sql.cargar_usuario(vista.jTable_usuarios,"Inactivo");
        sql.roles.select_table_roles(vista.jComboBox_filtro);
        sql.roles.select_table_roles(vista.jComboBox_roles_usuarios);
        vista.jButton_actualizar.setEnabled(false); 
    }

    @Override
    public void limpiar_todo() {
        vista.jTextField_busqueda.setText("");
        vista.jTextField_nombre_usuario.setText("");
        vista.jPasswordField1.setText("");
        vista.jTextField_correo1.setText("");
        vista.jComboBox_roles_usuarios.setSelectedIndex(0);
        vista.jComboBox_filtro.setSelectedIndex(0);
        vista.JButton3_registrar.setEnabled(true);
        vista.jButton_actualizar.setEnabled(false);
     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.JButton3_registrar) {
            usuario modelo = new usuario();
            FormularioRegUsuNuevo vista = new FormularioRegUsuNuevo();
            controlador_reg_usuario ctrl = new controlador_reg_usuario(modelo, vista);
            this.vista.dispose();
            ctrl.iniciar();
        }
        if (e.getSource() == vista.jComboBox_filtro) {
            if (!(vista.jComboBox_filtro.getSelectedIndex() == 0)) {
                sql.buscar_usuario(vista.jTable_usuarios, vista.jComboBox_filtro.getSelectedItem().toString());
            } else {
                sql.select_table_usuario(vista.jTable_usuarios,"Inactivo");
            }
        }
        if (e.getSource() == vista.jMenuItem_editar) {
            seleccionar();
            ocultar(true);
            vista.JButton3_registrar.setEnabled(false);
        }
        if (e.getSource() == vista.jButton_actualizar) {
            if (obtener_datos()) {
                vista.JButton3_registrar.setEnabled(true);
                limpiar_todo();
                mostrar();
                ocultar(false);
                
       }          
        }
        if(e.getSource()==vista.jMenuItem_inactivar){
            int fila = vista.jTable_usuarios.getSelectedRow();
            sql.inhabilitar_usuario(vista.jTable_usuarios.getValueAt(fila, 5).toString());
            mostrar();
        }
        if(e.getSource()==vista.jButton_limpiar){
        limpiar_todo();
        }
        
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
     if(e.getSource()==vista.jTable_usuarios){
     if(e.getButton()==MouseEvent.BUTTON3){
     vista.menu.show(vista.jTable_usuarios, e.getX(), e.getY());
     }
     }
        if (e.getSource() == vista.jLabel_ojo_activo) {
            vista.jLabel_ojo_inactivo.setVisible(true);
            vista.jLabel_ojo_activo.setVisible(false);
            vista.jPasswordField1.setEchoChar((char) 0);
        }
        if (e.getSource() == vista.jLabel_ojo_inactivo) {
            vista.jLabel_ojo_inactivo.setVisible(false);
            vista.jLabel_ojo_activo.setVisible(true);
            vista.jPasswordField1.setEchoChar('•');

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
    if(e.getSource()==vista.jTextField_busqueda){
    if(vista.jComboBox_filtro.getSelectedIndex()==0){
        sql.select_table_usuario(vista.jTable_usuarios, vista.jTextField_busqueda.getText());
    }else{
     sql.buscar_usuario(vista.jTable_usuarios, vista.jTextField_busqueda.getText(),vista.jComboBox_filtro.getSelectedItem().toString());
    }
    }
 
    }    

}
