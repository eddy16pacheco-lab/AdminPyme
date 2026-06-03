package controlador.modulo_usuario;

import consultas_bd.consulta_cliente;
import consultas_bd.consulta_persona;
import consultas_bd.consulta_usuario;
import controlador.controlador_menu;
import controlador.controlador_padre;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.roles;
import modelo.usuario;
import validaciones.modulo_usuario.validar_usuario;
import vista.formularios.FormularioRegUsuNuevo;

public class controlador_reg_usuario extends controlador_padre implements ActionListener,MouseListener,KeyListener{
private usuario modelo;
private FormularioRegUsuNuevo vista;
private validar_usuario validar=new validar_usuario();
private consulta_usuario sql=new consulta_usuario();
    public controlador_reg_usuario(usuario modelo, FormularioRegUsuNuevo vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.jButton_registrar1.addActionListener(this);
        this.vista.jButton_cancelar2.addActionListener(this);
        this.vista.jLabel_ojo_activo.addMouseListener(this);
        this.vista.jLabel_ojo_inactivo.addMouseListener(this);
        this.vista.jTextField_cedula.addKeyListener(this);
            iniciar();
    }

    @Override
    protected void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        mostrar();
    }

    @Override
    protected void mostrar() {
    sql.roles.select_table_roles(vista.jComboBox_roles);
    }
    public boolean agregar_(){
    // Obtener valores de los campos
    String nombre = vista.jTextField_nombre.getText().trim();
    String apellido = vista.jTextField_apellido.getText().trim();
    String cedula = vista.jTextField_cedula.getText().trim();
    String usuario = vista.jTextField__nom_user.getText().trim();
    String contrasena = new String(vista.jPasswordField_con_user.getPassword()).trim();
    String correo = vista.jTextField_correo.getText().trim();
    
    // Validación básica de campos vacíos
    if (!validar.validar_campo(nombre, apellido, cedula, usuario, contrasena, correo)) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
        return false;
    }
    
    // Validación de longitud y formato del nombre
    if (!validar.validar_nombre(nombre)) {
        JOptionPane.showMessageDialog(null, "Nombre no válido");
        return false;
    }
    
    if (!validar.validar_longitud_nombre(nombre)) {
        JOptionPane.showMessageDialog(null, "El nombre debe tener entre 2 y 50 caracteres");
        return false;
    }
    
    // Validación de longitud y formato del apellido
    if (!validar.validar_apellido(apellido)) {
        JOptionPane.showMessageDialog(null, "Apellido no válido");
        return false;
    }
    
    if (!validar.validar_longitud_apellido(apellido)) {
        JOptionPane.showMessageDialog(null, "El apellido debe tener entre 2 y 50 caracteres");
        return false;
    }
    
    // Validación de cédula
    if (!validar.validar_cedula_exacta(cedula)) {
        JOptionPane.showMessageDialog(null, "Cédula no válida. Debe tener 7 y 8 dígitos numéricos");
        return false;
    }
    
    // Validación de longitud del nombre de usuario
    if (!validar.validar_longitud_usuario(usuario)) {
        JOptionPane.showMessageDialog(null, "El nombre de usuario debe tener entre 8 y 16 caracteres");
        return false;
    }
    
    // Validación de formato del nombre de usuario
    if (!validar.validar_formato_usuario(usuario)) {
        JOptionPane.showMessageDialog(null, "El nombre de usuario solo puede contener letras, números, guiones bajos (_) y puntos (.)");
        return false;
    }
    
    // Validación de nombre de usuario (si ya tienes un método)
    if (!validar.validar_nombre_usuario(usuario)) {
        JOptionPane.showMessageDialog(null, "Nombre de usuario no válido");
        return false;
    }
    
    // Validación de longitud de contraseña
    if (!validar.validar_longitud_contrasena(contrasena)) {
        JOptionPane.showMessageDialog(null, "La contraseña debe tener entre 8 y 16 caracteres");
        return false;
    }
    
   
    // Validación de rol
    if(vista.jComboBox_roles.getSelectedIndex() == 0){  
        JOptionPane.showMessageDialog(null,"Seleccionar un rol");
        return false;
    }
    if(sql.cedulaExite(cedula)){
    JOptionPane.showMessageDialog(null, "La cedula ya esta registrada");
        return false;
    }
        if (!validar.validar_correo_completo(correo)) {
        // Mostrar mensaje de error específico
        String mensajeError = validar.obtener_mensaje_error_correo(correo);
        JOptionPane.showMessageDialog(null, 
            "Correo electrónico inválido:\n" + mensajeError + 
            "\n\nEjemplos válidos:\n" +
            "- usuario@dominio.com\n" +
            "- nombre.apellido@empresa.com.ec\n" +
            "- usuario123@hotmail.com");
        return false;
    }
    if(sql.correoExiste(correo)){
    JOptionPane.showMessageDialog(null, "EL Correo ya esta registrado ");
        return false;
    }
    // Si todas las validaciones pasan, proceder con el registro
    modelo.setNombre(nombre);
    modelo.setApellido(apellido);
    modelo.setCedula(Integer.parseInt(cedula));
    sql.insert_table_Persona(modelo);
    modelo.setId_persona(sql.select_id_persona());
    modelo.setNombre_usuario(usuario);
    modelo.setContraseña_usuario(contrasena);
    modelo.setCorreo_electronico(correo);
    roles rol = new roles();
    rol.setTipo_rol(vista.jComboBox_roles.getSelectedItem().toString());
    rol.setId_rol(sql.roles.buscar_id_roles(rol.getTipo_rol()));
    modelo.setRol(rol);
    sql.registrar_usuario(modelo);
    
    return true;
}
  
 
    @Override
    protected void limpiar_todo() {
        vista.jTextField_nombre.setText("");
        vista.jTextField_apellido.setText("");
        vista.jTextField_cedula.setText("");
        vista.jTextField__nom_user.setText("");
        vista.jPasswordField_con_user.setText("");
        vista.jTextField_correo.setText("");
        vista.jComboBox_roles.setSelectedIndex(0);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource()==vista.jButton_registrar1){
    if(agregar_()){
    limpiar_todo();
    vista.dispose();
    controlador_menu.vista_ges_de_productos();
    }
    }
    if(e.getSource()==vista.jButton_cancelar2){
    limpiar_todo();
    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==vista.jLabel_ojo_activo){
        vista.jLabel_ojo_inactivo.setVisible(true);
        vista.jLabel_ojo_activo.setVisible(false);
        vista.jPasswordField_con_user.setEchoChar((char) 0);
        }
        if(e.getSource()==vista.jLabel_ojo_inactivo){
        vista.jLabel_ojo_inactivo.setVisible(false);
        vista.jLabel_ojo_activo.setVisible(true);
        vista.jPasswordField_con_user.setEchoChar('•');
       
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

    }
}
