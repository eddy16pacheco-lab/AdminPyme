
package validaciones.modulo_venta;

import com.toedter.calendar.JDateChooser;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.detalle_de_ventas;
import javax.swing.JTable;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import validaciones.validaciones.otros.BCVConnector;
import validaciones.modulo_cliente.validar_cliente;

public class validar_venta extends validar_cliente {

    public boolean verificar_existencia(ArrayList<detalle_de_ventas> dv) {
        if (dv.isEmpty()) {

            return true;
        }
        return false;
    }

    public boolean verificar_existencia_del_pago(ArrayList<descripcion_pago> dp) {
        if (dp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista de pago vacia");
            return true;
        }
        return false;
    }

    public long validar_fecha(String mensaje) {
        Date fechaSeleccionada;
        long fecha_sql = 0;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        JPanel panel = new JPanel();
        panel.add(new JLabel("" + mensaje + " :"));
        panel.add(dateChooser);
        int option = JOptionPane.showConfirmDialog(null, panel, "Seleccionar Fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // Verificar si el usuario seleccionó "OK"
        if (option == JOptionPane.OK_OPTION) {
            // Obtener la fecha seleccionada
            fechaSeleccionada = dateChooser.getDate();
            if (fechaSeleccionada != null) {
                JOptionPane.showMessageDialog(null, "Fecha seleccionada: " + fechaSeleccionada, "Fecha", JOptionPane.INFORMATION_MESSAGE);
                fecha_sql = fechaSeleccionada.getTime();
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        return fecha_sql;
    }
    
    public boolean validar_pago_para_registrar_venta(
            ArrayList<descripcion_pago> lista_pagos,ArrayList<detalle_de_ventas> lista_modelos) {
        double valor_venta = 0;
        double valor_pago = 0;

        if (!lista_pagos.isEmpty() && valor_pago >= valor_venta) {
        for (detalle_de_ventas li : lista_modelos) {
            valor_venta += li.getMonto_venta();
        }
        for (descripcion_pago li : lista_pagos) {
            valor_pago += li.getMonto_cancelado();
        return true;
        }
        }
    return false;
    }
// Agregar estos métodos en la clase validar_venta

/**
 * Validar modificaciones de ventas
 */
public boolean validarModificacionVenta(String campo, String valor) {
    switch (campo) {
        case "estado_venta":
            return valor.matches("Aprobado|Pendiente|Devolucion");
            
        case "tipo_venta":
            return valor.matches("Contado|Credito");
            
        case "porcentaje":
            try {
                double porcentaje = Double.parseDouble(valor);
                return porcentaje >= 0 && porcentaje <= 100;
            } catch (NumberFormatException e) {
                return false;
            }
            
        case "cantidad_vendida":
            try {
                int cantidad = Integer.parseInt(valor);
                return cantidad > 0;
            } catch (NumberFormatException e) {
                return false;
            }
            
        case "fecha_cobro":
            return validarFormatoFecha(valor);
            
        default:
            return false;
    }
}

/**
 * Validar formato de fecha
 */
private boolean validarFormatoFecha(String fecha) {
    if (fecha == null || fecha.trim().isEmpty()) {
        return true; // Fecha vacía es válida
    }
    
    // Validar formato YYYY-MM-DD
    return fecha.matches("\\d{4}-\\d{2}-\\d{2}");
}

/**
 * Validar que no se modifiquen ventas ya pagadas
 */
public boolean validarVentaModificable(ArrayList<detalle_de_ventas> ventas, int idVenta) {
    for (detalle_de_ventas venta : ventas) {
        if (venta.getN_venta().getId_venta() == idVenta) {
            // Aquí puedes agregar lógica adicional de validación
            return true;
        }
    }
    return false;
}

/**
 * Mostrar diálogo de confirmación para modificaciones
 */
public boolean confirmarModificaciones(int cantidadVentas, String cambios) {
    int respuesta = JOptionPane.showConfirmDialog(null,
        "¿Está seguro de aplicar los siguientes cambios?\n\n" +
        "Ventas a modificar: " + cantidadVentas + "\n" +
        "Cambios: " + cambios + "\n\n" +
        "Esta acción no se puede deshacer.",
        "Confirmar Modificaciones",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    
    return respuesta == JOptionPane.YES_OPTION;
}
}
