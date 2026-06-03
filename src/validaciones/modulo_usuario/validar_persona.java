package validaciones.modulo_usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import validaciones.validaciones.otros.validar_componente;

public class validar_persona extends validar_componente{
    
    public boolean validar_nombre(String nombre) {
        if (nombre.matches("^[A-Z]+[a-z]{2,18}$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validar_apellido(String apellido) {
        if (apellido.matches("^[A-Z]+[a-z]{3,18}$")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validar_cedula(String cedula) {
        if (cedula.matches("^[0-9]{7,8}$")) {
            return true;
        } else {
            return false;
        }
    }

}

