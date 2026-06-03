package controlador;

import controlador.controlador_padre;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.usuario;
import validaciones.modulo_usuario.validar_acceso_login;

import vista.login1;

public class controlador_login extends controlador_padre implements ActionListener, MouseListener,KeyListener{

    private usuario modelo;
    public static login1 vista;
    private validar_acceso_login vl = new validar_acceso_login();
    // Límites de caracteres
    private final int MAX_CARACTERES_USUARIO = 20;
    private final int MAX_CARACTERES_CONTRASENA = 20;

    public usuario getModelo() {
        return modelo;
    }

    public void setModelo(usuario modelo) {
        this.modelo = modelo;
    }

    public login1 getVista() {
        return vista;
    }

    public void setVista(login1 vista) {
        controlador_login.vista = vista;
    }
    
    public controlador_login (){
        this.vista.jButton_iniciar_sesión.addActionListener(this);
        this.vista.jTextField_usuario.addKeyListener(this);
        this.vista.jPasswordFie_contraseña.addKeyListener(this);
        this.vista.jLabel_men_olv_con.addMouseListener(this);
        this.vista.jSeparator1.addMouseListener(this);
        this.vista.jLabel_salir.addMouseListener(this);
        configurarLimitesCaracteres();
    }

    public controlador_login (usuario modelo, login1 vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.jButton_iniciar_sesión.addActionListener(this);
        this.vista.jTextField_usuario.addKeyListener(this);
        this.vista.jPasswordFie_contraseña.addKeyListener(this);
        this.vista.jLabel_men_olv_con.addMouseListener(this);
        this.vista.jSeparator1.addMouseListener(this);
        this.vista.jLabel_salir.addMouseListener(this);
        configurarLimitesCaracteres();
    }

    private void configurarLimitesCaracteres() {
        // Agregar document filters para limitar caracteres
        javax.swing.text.PlainDocument docUsuario = (javax.swing.text.PlainDocument) vista.jTextField_usuario.getDocument();
        docUsuario.setDocumentFilter(new LimitarCaracteresFilter(MAX_CARACTERES_USUARIO));
        
        javax.swing.text.PlainDocument docContrasena = (javax.swing.text.PlainDocument) vista.jPasswordFie_contraseña.getDocument();
        docContrasena.setDocumentFilter(new LimitarCaracteresFilter(MAX_CARACTERES_CONTRASENA));
    }

    public void iniciar() {
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        actualizarEstadoBoton(); // Estado inicial del botón
    }

    public void cerrar() {
        vista.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton_iniciar_sesión) {
            boolean respuesta = iniciar_sesion(vista.jPasswordFie_contraseña.getText(), vista.jTextField_usuario.getText());
            if (respuesta) {
                cerrar();
            }
        }
    }

    private boolean iniciar_sesion(String c_usu, String n_usu) {
        boolean res = vl.validar_campo(c_usu, n_usu);
        if (res) {
            // Si hay error en validación, no hacer nada
            return false;
        } else {
            modelo.setNombre_usuario(n_usu);
            modelo.setContraseña_usuario(c_usu);
            if (vl.recupera_usuario(modelo)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if(e.getSource()==vista.jLabel_salir){
            if(e.getButton()==MouseEvent.BUTTON1){
                vista.dispose();
            }
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        Color azul = new Color(0, 115, 182);
        vista.jLabel_men_olv_con.setForeground(azul);
        vista.jSeparator1.setForeground(azul);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        Color griz = new Color(0, 0, 0);
        vista.jLabel_men_olv_con.setForeground(griz);
        vista.jSeparator1.setForeground(griz);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Validación de caracteres mientras se escribe
        char c = e.getKeyChar();
        // Puedes agregar validaciones específicas aquí si es necesario
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Navegación con tecla Enter
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == vista.jTextField_usuario) {
                // Desde usuario ir a contraseña
                vista.jPasswordFie_contraseña.requestFocus();
            } else if (e.getSource() == vista.jPasswordFie_contraseña) {
                // Desde contraseña ir al botón de iniciar sesión
                vista.jButton_iniciar_sesión.requestFocus();
            } else if (e.getSource() == vista.jButton_iniciar_sesión) {
                // Si el botón tiene foco y se presiona Enter, ejecutar acción
                if (vista.jButton_iniciar_sesión.isEnabled()) {
                    boolean respuesta = iniciar_sesion(vista.jPasswordFie_contraseña.getText(), vista.jTextField_usuario.getText());
                    if (respuesta) {
                        cerrar();
                    }
                }
            }
            e.consume(); // Prevenir sonido del sistema
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Actualizar estado del botón cuando se modifica algún campo
        actualizarEstadoBoton();
    }

    private void actualizarEstadoBoton() {
        String usuario = vista.jTextField_usuario.getText();
        String contrasena = new String(vista.jPasswordFie_contraseña.getPassword());
        
        // Habilitar botón solo si ambos campos tienen texto y no exceden límites
        boolean camposValidos = !usuario.trim().isEmpty() && 
                               !contrasena.trim().isEmpty() &&
                               usuario.length() <= MAX_CARACTERES_USUARIO &&
                               contrasena.length() <= MAX_CARACTERES_CONTRASENA;
        
        vista.jButton_iniciar_sesión.setEnabled(camposValidos);
    }

    // Clase interna para limitar caracteres
    private class LimitarCaracteresFilter extends javax.swing.text.DocumentFilter {
        private int maxCaracteres;

        public LimitarCaracteresFilter(int maxCaracteres) {
            this.maxCaracteres = maxCaracteres;
        }

        @Override
        public void insertString(javax.swing.text.DocumentFilter.FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) 
                throws javax.swing.text.BadLocationException {
            if (string == null) return;
            
            if ((fb.getDocument().getLength() + string.length()) <= maxCaracteres) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Opcional: mostrar advertencia o sonido
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void replace(javax.swing.text.DocumentFilter.FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) 
                throws javax.swing.text.BadLocationException {
            if (text == null) return;
            
            int currentLength = fb.getDocument().getLength();
            int newLength = currentLength - length + text.length();
            
            if (newLength <= maxCaracteres) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Opcional: mostrar advertencia o sonido
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}