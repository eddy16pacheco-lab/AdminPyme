package validaciones.modulo_usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
public class validar_usuario extends validar_persona {
    
    // Método para validar el nombre de usuario
    public boolean validar_nombre_usuario(String usuario) {
        // Verificar que el nombre de usuario no esté vacío
        if (usuario == null || usuario.trim().isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacío.");
            return false;
        }

        // Longitud mínima y máxima
        int longitudMinima = 5;
        int longitudMaxima = 15;
        if (usuario.length() < longitudMinima || usuario.length() > longitudMaxima) {
            System.out.println("El nombre de usuario debe tener entre " + longitudMinima + " y " + longitudMaxima + " caracteres.");
            return false;
        }

        // Verificar que solo contenga caracteres alfanuméricos, guiones bajos o guiones
        String regex = "^[a-zA-Z0-9_-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(usuario);

        if (!matcher.matches()) {
            System.out.println("El nombre de usuario solo puede contener letras, números, guiones bajos (_) o guiones (-).");
            return false;
        }

        // Verificar que no comience ni termine con un guión o guión bajo
        if (usuario.startsWith("-") || usuario.startsWith("_")
                || usuario.endsWith("-") || usuario.endsWith("_")) {
            System.out.println("El nombre de usuario no puede comenzar ni terminar con un guión (-) o guión bajo (_).");
            return false;
        }

        // Si pasa todas las validaciones, el nombre de usuario es válido
       return true;
    }
// Agrega estos métodos a tu clase validar_usuario:

public boolean validar_longitud_usuario(String usuario) {
    if (usuario == null) return false;
    return usuario.length() >= 8 && usuario.length() <= 16;
}

public boolean validar_longitud_contrasena(String contrasena) {
    if (contrasena == null) return false;
    return contrasena.length() >= 8 && contrasena.length() <= 16;
}

// Si quieres un método para validación de formato (opcional y simple)
public boolean validar_formato_contrasena_simple(String contrasena) {
    // Solo valida que no tenga espacios
    return contrasena != null && !contrasena.contains(" ");
}

// Método para verificar si la contraseña no está vacía
public boolean validar_contrasena_no_vacia(String contrasena) {
    return contrasena != null && !contrasena.trim().isEmpty();
}
public boolean validar_longitud_nombre(String nombre) {
    if (nombre == null) return false;
    return nombre.length() >= 2 && nombre.length() <= 50;
}

public boolean validar_longitud_apellido(String apellido) {
    if (apellido == null) return false;
    return apellido.length() >= 2 && apellido.length() <= 50;
}

public boolean validar_formato_usuario(String usuario) {
    // Permite letras, números, guiones bajos y puntos
    return usuario.matches("^[a-zA-Z0-9_.]{8,16}$");
}


public boolean validar_cedula_exacta(String cedula) {
    // Verifica que sea exactamente 8 dígitos numéricos
    if (cedula == null || cedula.length()   < 6 || cedula.length() > 9) {
        return false;
    }
    
    // Verifica que sean solo números
    if (!cedula.matches("\\d{7,8}")) {
        return false;
    }
    
    // Aquí puedes agregar el algoritmo de validación de cédula ecuatoriana
    // si es necesario
    return validar_cedula(cedula); // Llama a tu método existente si lo tienes
}

public boolean validar_dominio_correo(String correo) {
    // Lista de dominios comunes permitidos (puedes personalizar)
    String[] dominiosPermitidos = {
        "gmail.com", "hotmail.com", "outlook.com", "yahoo.com", 
        "edu.ec", "gob.ec", "com.ec", "org.ec", "net.ec"
    };
    
    if (correo == null || !correo.contains("@")) {
        return false;
    }
    
    String dominio = correo.substring(correo.indexOf("@") + 1).toLowerCase();
    
    // Validar contra lista de dominios permitidos (opcional)
    for (String dominioPermitido : dominiosPermitidos) {
        if (dominio.equals(dominioPermitido) || dominio.endsWith("." + dominioPermitido)) {
            return true;
        }
    }
    
    // Si no está en la lista, aún puede ser válido
    // Podrías hacer una validación más estricta si lo deseas
    return dominio.matches("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
}
// Agrega estos métodos a tu clase validar_usuario:

/**
 * Valida el formato básico de un correo electrónico
 * Usa una expresión regular estándar para validar formato
 */
public boolean validar_correo(String correo) {
    if (correo == null || correo.trim().isEmpty()) {
        return false;
    }
    
    // Expresión regular mejorada para validar formato de correo
    String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    return correo.trim().matches(regex);
}

/**
 * Valida la longitud del correo (entre 5 y 100 caracteres)
 */
public boolean validar_longitud_correo(String correo) {
    if (correo == null) return false;
    return correo.length() >= 5 && correo.length() <= 100;
}

/**
 * Valida que el correo no tenga espacios
 */
public boolean validar_correo_sin_espacios(String correo) {
    if (correo == null) return false;
    return !correo.contains(" ");
}

/**
 * Valida que el nombre de usuario (antes de @) no esté vacío
 */
public boolean validar_usuario_correo(String correo) {
    if (correo == null || !correo.contains("@")) {
        return false;
    }
    
    String usuario = correo.substring(0, correo.indexOf("@"));
    return !usuario.trim().isEmpty();
}

/**
 * Valida múltiples @ (solo debe haber uno)
 */
public boolean validar_un_arroba(String correo) {
    if (correo == null) return false;
    
    int count = 0;
    for (char c : correo.toCharArray()) {
        if (c == '@') count++;
    }
    return count == 1;
}

/**
 * Valida caracteres especiales no permitidos
 */
public boolean validar_caracteres_correo(String correo) {
    if (correo == null) return false;
    
    // Caracteres permitidos antes del @
    String usuario = correo.contains("@") ? 
        correo.substring(0, correo.indexOf("@")) : correo;
    
    // Permite letras, números, puntos, guiones, guiones bajos, y signo +
    return usuario.matches("^[A-Za-z0-9._%+-]+$");
}

/**
 * Valida el correo completo con todas las reglas
 */
public boolean validar_correo_completo(String correo) {
    if (correo == null || correo.trim().isEmpty()) {
        return false;
    }
    
    // 1. Validar longitud
    if (!validar_longitud_correo(correo)) {
        return false;
    }
    
    // 2. Validar que no tenga espacios
    if (!validar_correo_sin_espacios(correo)) {
        return false;
    }
    
    // 3. Validar que tenga exactamente un @
    if (!validar_un_arroba(correo)) {
        return false;
    }
    
    // 4. Validar que el usuario no esté vacío
    if (!validar_usuario_correo(correo)) {
        return false;
    }
    
    // 5. Validar caracteres permitidos
    if (!validar_caracteres_correo(correo)) {
        return false;
    }
    
    // 6. Validar formato con expresión regular
    if (!validar_correo(correo)) {
        return false;
    }
    
    // 7. Validar dominio
    if (!validar_dominio_correo(correo)) {
        return false;
    }
    
    return true;
}

/**
 * Proporciona un mensaje de error específico para el correo
 */
public String obtener_mensaje_error_correo(String correo) {
    if (correo == null || correo.trim().isEmpty()) {
        return "El correo electrónico no puede estar vacío";
    }
    
    if (!validar_longitud_correo(correo)) {
        return "El correo debe tener entre 5 y 100 caracteres";
    }
    
    if (!validar_correo_sin_espacios(correo)) {
        return "El correo no puede contener espacios";
    }
    
    if (!validar_un_arroba(correo)) {
        return "El correo debe contener exactamente un símbolo @";
    }
    
    if (!validar_usuario_correo(correo)) {
        return "El nombre de usuario del correo no puede estar vacío";
    }
    
    if (!validar_caracteres_correo(correo)) {
        return "El correo contiene caracteres no permitidos";
    }
    
    if (!validar_correo(correo)) {
        return "Formato de correo inválido";
    }
    
    if (!validar_dominio_correo(correo)) {
        return "El dominio del correo no es válido";
    }
    
    return "Correo válido";
}

/**
 * Valida correos con dominios específicos de Ecuador (opcional)
 */
public boolean validar_correo_ecuador(String correo) {
    if (!validar_correo_completo(correo)) {
        return false;
    }
    
    String dominio = correo.substring(correo.indexOf("@") + 1).toLowerCase();
    
    // Dominios ecuatorianos comunes
    String[] dominiosEcuador = {
        ".ec", ".com.ec", ".net.ec", ".org.ec", ".edu.ec", 
        ".gob.ec", ".mil.ec", ".info.ec"
    };
    
    for (String dominioEc : dominiosEcuador) {
        if (dominio.endsWith(dominioEc)) {
            return true;
        }
    }
    
    // También aceptar dominios internacionales
    String[] dominiosInternacionales = {
        ".com", ".org", ".net", ".edu", ".gov", ".mil", 
        ".info", ".biz", ".io", ".co"
    };
    
    for (String dominioInt : dominiosInternacionales) {
        if (dominio.endsWith(dominioInt)) {
            return true;
        }
    }
    
    return false;
}
}
