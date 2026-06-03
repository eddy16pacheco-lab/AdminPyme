/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones.modulo_gestion_producto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class validaciones_producto {
    
     public static int extraerPrimerNumeroRegex(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return -1;
        }
        
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cadena);
        
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        
        return -1;
    }
    // Método para validar el ID del producto
    public boolean validarIdProducto(String idProducto, JLabel labelError) {
        // Limpiar mensaje anterior
        limpiarMensajeError(labelError);
        
        // Verificar que no esté vacío
        if (idProducto == null || idProducto.trim().isEmpty()) {
            mostrarError(labelError, "El ID del producto es requerido");
            return false;
        }
        
        // Validar que sea un número entero positivo
        if (!validar_entero(idProducto, labelError)) {
            mostrarError(labelError, "El ID debe ser un número entero válido");
            return false;
        }
        
        // Validar que sea mayor a cero
        int id = Integer.parseInt(idProducto);
        if (id <= 0) {
            mostrarError(labelError, "El ID debe ser un número mayor a cero");
            return false;
        }
        
        // Validar longitud máxima
        int longitudMaxima = 10;
        if (idProducto.length() > longitudMaxima) {
            mostrarError(labelError, "El ID no puede exceder los " + longitudMaxima + " dígitos");
            return false;
        }
        
        return true;
    }

    // Método para validar el nombre del producto
    public boolean validarNombreProducto(String producto, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (producto == null || producto.trim().isEmpty()) {
            mostrarError(labelError, "Por favor, ingresa un nombre para el producto");
            return false;
        }
        
        int longitudMaxima = 20;
        if (producto.length() > longitudMaxima) {
            mostrarError(labelError, "El nombre es demasiado largo. Máximo " + longitudMaxima + " caracteres");
            return false;
        }
        
        // Validar que no tenga más de 3 números consecutivos
        if (tieneMasDeTresNumerosConsecutivos(producto)) {
            mostrarError(labelError, "El nombre no puede tener más de 3 números consecutivos");
            return false;
        }
        
        // Validar que no tenga símbolos o caracteres especiales (excepto espacios)
        String regex = "^[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(producto);

        if (!matcher.matches()) {
            mostrarError(labelError, "Solo se permiten letras, números y espacios");
            return false;
        }
        
        return true;
    }

    // Método para validar descripción
    public boolean validarDescripcion(String descripcion, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            mostrarError(labelError, "Por favor, ingresa una descripción para el producto");
            return false;
        }
        
        int longitudMaxima = 100;
        if (descripcion.length() > longitudMaxima) {
            mostrarError(labelError, "La descripción es demasiado larga. Máximo " + longitudMaxima + " caracteres");
            return false;
        }
        
        // Validar que no tenga más de 3 números consecutivos
        if (tieneMasDeTresNumerosConsecutivos(descripcion)) {
            mostrarError(labelError, "La descripción no puede tener más de 3 números consecutivos");
            return false;
        }
        
        // Validar que no tenga símbolos o caracteres especiales (excepto espacios, comas y puntos)
        String regex = "^[a-zA-Z0-9\\s.,]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(descripcion);

        if (!matcher.matches()) {
            mostrarError(labelError, "Solo se permiten letras, números, espacios, comas y puntos");
            return false;
        }
        
        return true;
    }

    public boolean validar_decimales(String numero, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (numero == null || numero.trim().isEmpty()) {
            mostrarError(labelError, "Este campo no puede estar vacío");
            return false;
        }

        String regex = "^[-+]?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        if (!matcher.matches()) {
            mostrarError(labelError, "Por favor, ingresa un número válido. Ejemplo: 15.50");
            return false;
        }
        
        return true;
    }

    public boolean validar_entero(String numero, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (numero == null || numero.trim().isEmpty()) {
            mostrarError(labelError, "Este campo no puede estar vacío");
            return false;
        }

        String regex = "^[-+]?\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        if (!matcher.matches()) {
            mostrarError(labelError, "Por favor, ingresa un número entero válido. Ejemplo: 10");
            return false;
        }

        return true;
    }

    public boolean validarPrecioUnitario(String precioUnitario, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (!validar_decimales(precioUnitario, labelError)) {
            return false;
        }
        
        double precio = Double.parseDouble(precioUnitario);
        if (precio <= 0) {
            mostrarError(labelError, "El precio debe ser mayor a cero");
            return false;
        }
        
        return true;
    }

    public boolean validarPrecioVenta(String precioVenta, String precioCompra, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (!validar_decimales(precioVenta, labelError)) {
            return false;
        }
        
        double precioV = Double.parseDouble(precioVenta);
        if (precioV <= 0) {
            mostrarError(labelError, "El precio de venta debe ser mayor a cero");
            return false;
        }
        
        // Validar que el precio de venta no sea menor al precio de compra
        if (precioCompra != null && !precioCompra.trim().isEmpty()) {
            try {
                double precioC = Double.parseDouble(precioCompra);
                if (precioV < precioC) {
                    mostrarError(labelError, "El precio de venta no puede ser menor al precio de compra");
                    return false;
                }
            } catch (NumberFormatException e) {
                // Si no se puede parsear el precio de compra, continuar sin esta validación
            }
        }
        
        return true;
    }

    public boolean validarCategoriaSeleccionada(String categoria, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (categoria == null || categoria.trim().isEmpty() || "Seleccionar".equals(categoria)) {
            // Mostrar JOptionPane para categoría
            JOptionPane.showMessageDialog(null, 
                "Por favor, selecciona una categoría válida", 
                "Categoría requerida", 
                JOptionPane.WARNING_MESSAGE);
            
            // También mostrar en el JLabel si se proporciona
            if (labelError != null) {
                mostrarError(labelError, "Por favor, selecciona una categoría");
            }
            return false;
        }
        
        return true;
    }

    // Método para validar que no tenga más de 3 números consecutivos
    private boolean tieneMasDeTresNumerosConsecutivos(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        
        // Buscar secuencias de 4 o más números consecutivos
        Pattern pattern = Pattern.compile("\\d{4,}");
        Matcher matcher = pattern.matcher(texto);
        
        return matcher.find();
    }

    // Métodos para mostrar y limpiar errores en JLabel
    private void mostrarError(JLabel labelError, String mensaje) {
        if (labelError != null) {
            labelError.setText(mensaje);
            labelError.setVisible(true);
        }
    }

    private void limpiarMensajeError(JLabel labelError) {
        if (labelError != null) {
            labelError.setText("");
            labelError.setVisible(false);
        }
    }

    // Método para crear limitador de caracteres con JLabel
    public KeyAdapter crearLimitadorCaracteres(JTextField textField, int limite, JLabel labelError) {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = textField.getText();
                
                // Si ya alcanzó el límite, consume el evento
                if (texto.length() >= limite) {
                    e.consume();
                    mostrarError(labelError, "Límite alcanzado: máximo " + limite + " caracteres");
                    
                    // Programar para limpiar el mensaje después de 3 segundos
                    new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                limpiarMensajeError(labelError);
                            }
                        }, 
                        3000
                    );
                } else {
                    limpiarMensajeError(labelError);
                }
            }
        };
    }

    // Método para validar longitud
    public boolean validarLongitud(String texto, int limite, JLabel labelError) {
        limpiarMensajeError(labelError);
        
        if (texto == null) return true;
        
        if (texto.length() > limite) {
            mostrarError(labelError, "Límite excedido: máximo " + limite + " caracteres permitidos");
            return false;
        }
        
        return true;
    }

    // Métodos principales de validación actualizados con JLabel
    public boolean validarDatosCompletosProducto(String idProducto, String nombre, String descripcion, String precioUnitario, 
                                                String precioVenta, String categoriaSeleccionada, JLabel labelErrorGeneral) {
        
        boolean idValido = validarIdProducto(idProducto, labelErrorGeneral);
        boolean nombreValido = validarNombreProducto(nombre, labelErrorGeneral);
        boolean descripcionValida = validarDescripcion(descripcion, labelErrorGeneral);
        boolean precioUnitarioValido = validarPrecioUnitario(precioUnitario, labelErrorGeneral);
        boolean precioVentaValido = validarPrecioVenta(precioVenta, precioUnitario, labelErrorGeneral);
        boolean categoriaValida = validarCategoriaSeleccionada(categoriaSeleccionada, labelErrorGeneral);
        
        if (idValido && nombreValido && descripcionValida && precioUnitarioValido && precioVentaValido && categoriaValida) {
            limpiarMensajeError(labelErrorGeneral);
            return true;
        }
        
        return false;
    }

    // Método sobrecargado sin ID
    public boolean validarDatosCompletosProducto(String nombre, String descripcion, String precioUnitario, 
                                                String precioVenta, String categoriaSeleccionada, JLabel labelErrorGeneral) {
        
        boolean nombreValido = validarNombreProducto(nombre, labelErrorGeneral);
        boolean descripcionValida = validarDescripcion(descripcion, labelErrorGeneral);
        boolean precioUnitarioValido = validarPrecioUnitario(precioUnitario, labelErrorGeneral);
        boolean precioVentaValido = validarPrecioVenta(precioVenta, precioUnitario, labelErrorGeneral);
        boolean categoriaValida = validarCategoriaSeleccionada(categoriaSeleccionada, labelErrorGeneral);
        
        if (nombreValido && descripcionValida && precioUnitarioValido && precioVentaValido && categoriaValida) {
            limpiarMensajeError(labelErrorGeneral);
            return true;
        }
        
        return false;
    }
}