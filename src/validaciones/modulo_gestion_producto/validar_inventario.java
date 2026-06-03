package validaciones.modulo_gestion_producto;

import consultas_bd.consulta_detalle_orden_compra;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.inventario;
import modelo.producto;
import validaciones.validaciones.otros.validar_componente;

public class validar_inventario extends validar_componente{
private consulta_detalle_orden_compra co=new consulta_detalle_orden_compra();
  
public boolean validar_cantidad_exitente(inventario i) {
        String input;
        boolean valido = false;
        while (!valido) {
            try {
                input = JOptionPane.showInputDialog(null, "Total de unidades " + co.total_disponibilidad_exitente(i.getProducto()) + "\nDigite la disponibilidad del producto:  ", "Mensaje de registro",
                        JOptionPane.QUESTION_MESSAGE);
                // Validar que no se haya cancelado el diálogo
                if (input == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada");
                    break;
                }
                // Convertir a número
                i.setCantidad(Integer.parseInt(input));

                // Validar que sea mayor a 0
                if (i.getCantidad() <= 0) {
                    JOptionPane.showMessageDialog(null, "Error: la disponibilidad debe ser mayor a 0",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } 
                else if(co.total_disponibilidad_exitente(i.getProducto())==0){
                    JOptionPane.showMessageDialog(null, "Error: la disponibilidad del producto actualmente es de 0",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }else if(i.getCantidad() > co.total_disponibilidad_exitente(i.getProducto())){
                    JOptionPane.showMessageDialog(null, "Error: la disponibilidad del producto es mayor a la cantidad actual",
                            "Error", JOptionPane.ERROR_MESSAGE);}
                else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar una cantidad válida",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return valido;
    }
        
private boolean obtener_valor_max(String mensaje,inventario i){
        String input;
        boolean valido = false;
        while (!valido) {
            try {
                input = JOptionPane.showInputDialog(null, ""+mensaje+" : ", "Mensaje de registro",JOptionPane.QUESTION_MESSAGE);
                // Validar que no se haya cancelado el diálogo
                if (input == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada");
                    break;
                }
                // Convertir a número
                i.setStock_max(Integer.parseInt(input));

                // Validar que sea mayor a 0
                if (i.getStock_max() <= 0) {
                    JOptionPane.showMessageDialog(null, "Error: la disponibilidad debe ser mayor a 0",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar una cantidad válida",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return valido;
    }
 private boolean obtener_valor_min(String mensaje,inventario i){
        String input;
        boolean valido = false;
        while (!valido) {
            try {
                input = JOptionPane.showInputDialog(null, ""+mensaje+" : ", "Mensaje de registro",JOptionPane.QUESTION_MESSAGE);
                // Validar que no se haya cancelado el diálogo
                if (input == null) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada");
                    break;
                }
                // Convertir a número
                i.setStock_min(Integer.parseInt(input));

                // Validar que sea mayor a 0
                if (i.getStock_min() <= 0) {
                    JOptionPane.showMessageDialog(null, "Error: la disponibilidad debe ser mayor a 0",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar una cantidad válida",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return valido;
    }

public boolean validar_stock(inventario inventario) {
        boolean respuesta = false;
        if ((obtener_valor_min("Digite cantidad stock minimo", inventario))) {
            if (obtener_valor_max("Digite cantidad stock maximo", inventario)) {
                while (inventario.getStock_min() >= inventario.getStock_max()) {
                    JOptionPane.showMessageDialog(null, "la cantidad minima no puede\n ser igual o mas baja que la cantidad maxima");
                    inventario.setStock_min(-1);
                    inventario.setStock_max(-1);
                    if ((obtener_valor_min("Digite cantidad stock minimo", inventario))) {
                        if (obtener_valor_max("Digite cantidad stock maximo", inventario)) {
                        respuesta=true;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                    if (!(inventario.getStock_min() >= inventario.getStock_max())) {
                        respuesta = true;
                        break;
                    }
                }
                    respuesta=true;
            } else {
        }
            if (inventario.getStock_max() == -1) {
         return  respuesta = false;
                        }
        } 
        return respuesta;
    }

   
}
