package validaciones.modulo_cliente;

import consultas_bd.consulta_cliente;
import controlador.controlador_padre;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import modelo.cliente;
import modelo.ventas;
import validaciones.validaciones.otros.validar_componente;

public class validar_cliente extends validar_componente {
private consulta_cliente co=new consulta_cliente();
    public boolean validar_cliente(String ced){
        ventas v;
        v = co.select_table_venta_cliente(ced);
        if (v.getCliente().getId_persona()>=1) {
            return true;
        }
     return false;
  }    
    
    public boolean validar_telefeno(String telefeno) {
        if (telefeno.matches("^(041[246]|042[46])\\d{7}$")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"Telefeno no valido");
            return false;
        }
    }

}
