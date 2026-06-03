package validaciones.modulo_orden_compra;

import java.util.regex.Pattern;
import javax.swing.JLabel;
import validaciones.validaciones.otros.validar_componente;

public class validar_proveedor extends validar_componente{
    
private static final int LONGITUD_MINIMA = 3;
    private final int LONGITUD_MAXIMA = 20;
    private final String REGEX_NOMBRE = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s\\-.]{" + LONGITUD_MINIMA + "," + LONGITUD_MAXIMA + "}$";
     private static final String REGEX_TELEFONO = 
        "^(?:(?:04\\d{9})" +                // Móviles locales (0412...)
        "|(?:02\\d{9})" +                   // Fijos locales (0212...)
        "|(?:58(?:4[14-6]|24|26)\\d{8}))$"; // Internacionales (+58...)

    public boolean validarNombreProveedor(String nombre,JLabel mensaje) {
        if (nombre == null) {
           mensaje.setText("El nombre no puede ser nulo");
        }

        String nombreLimpio = nombre.trim();

        // Validaciones básicas
        if (nombreLimpio.isEmpty()) {
        mensaje.setText("El nombre no puede estar vacío");
        }

       else if (nombreLimpio.length() < LONGITUD_MINIMA || nombreLimpio.length() > LONGITUD_MAXIMA) {
            mensaje.setText("Longitud del nombre debe estar entre " + LONGITUD_MINIMA + " y " + LONGITUD_MAXIMA + " caracteres");
        }

        // Validar caracteres permitidos y formato
       else if (!Pattern.matches(REGEX_NOMBRE, nombreLimpio)) {
           mensaje.setText("El nombre contiene caracteres no válidos o formato incorrecto");
        }

        // Validar que no tenga espacios múltiples consecutivos
       else if (nombreLimpio.contains("  ")) {
            mensaje.setText("El nombre no puede contener espacios múltiples consecutivos");
        }
       else{
       mensaje.setText("");
       }
        return true;
    }
 
    public boolean validarContacto(String telefono,JLabel mensaje){
        if (telefono == null) {
            mensaje.setText("El número no puede ser nulo");
        }

        String numeroLimpio = limpiarNumero(telefono);
        
        if (numeroLimpio.isEmpty()) {
           mensaje.setText("Número inválido (vacío después de limpiar)");
        }

        else if (!Pattern.matches(REGEX_TELEFONO, numeroLimpio)) {
            mensaje.setText("Formato de teléfono inválido");
        }
        else{
        mensaje.setText("");
        }
        return true;
    }

    private String limpiarNumero(String numero) {
        return numero.replaceAll("[^0-9]", "");
    }

    public  boolean validarCorreo(String correo,JLabel mensaje) {
        if (correo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            mensaje.setText("");
            return true;
        } else {
            mensaje.setText("Formato de correo no valido");
            return false;
        }
    }
}
