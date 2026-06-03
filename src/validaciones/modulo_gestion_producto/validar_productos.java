package validaciones.modulo_gestion_producto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import validaciones.validaciones.otros.validar_componente;

public class validar_productos extends validar_componente {

    // Método para validar el nombre del producto
    public boolean validarNombreProducto(String producto) {
        // Verificar que el nombre no esté vacío
        if (producto == null || producto.trim().isEmpty()) {
            System.out.println("El nombre del producto no puede estar vacío.");
            return false;
        }
        int longitudMaxima = 20;
        if (producto.length() > longitudMaxima) {
            System.out.println("El nombre del producto no puede exceder los " + longitudMaxima + " caracteres.");
            return false;
        }
        // Verificar que el nombre solo contenga caracteres alfanuméricos y espacios
        String regex = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(producto);

        if (!matcher.matches()) {
            System.out.println("El nombre del producto solo puede contener caracteres alfanuméricos y espacios.");
            return false;
        }
        // Si pasa todas las validaciones, el nombre es válido
        System.out.println("El nombre del producto es válido.");
        return true;
    }

    public boolean validar_decimales(String numero) {
        // Verificar que la cadena no esté vacía
        if (numero == null || numero.trim().isEmpty()) {
            System.out.println("La cadena no puede estar vacía.");
            return false;
        }

        // Expresión regular para validar números decimales
        String regex = "^[-+]?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        if (!matcher.matches()) {
            System.out.println("La cadena no representa un número decimal válido.");
            return false;
        }
        // Si pasa la validación, es un número decimal válido
        System.out.println("La cadena es un número decimal válido.");
        return true;
    }

    public boolean validar_entero(String numero) {
        // Verificar que la cadena no esté vacía
        if (numero == null || numero.trim().isEmpty()) {
            System.out.println("La cadena no puede estar vacía.");
            return false;
        }

        // Expresión regular para validar números enteros
        String regex = "^[-+]?\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        if (!matcher.matches()) {
            System.out.println("La cadena no representa un número entero válido.");
            return false;
        }

        // Si pasa la validación, es un número entero válido
        System.out.println("La cadena es un número entero válido.");
        return true;
    }
    
}
