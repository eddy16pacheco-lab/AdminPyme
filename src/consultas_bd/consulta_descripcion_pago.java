package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.descripcion_pago;
import modelo.pago;
import modelo.ventas;

public class consulta_descripcion_pago extends sql_consulta {
public consulta_pago con_pago=new consulta_pago();
public consulta_venta con_ven=new consulta_venta();
 
public void insert_table_descripcion_pago(ArrayList<descripcion_pago> l_dpa,ventas venta) {
        String insert = "INSERT INTO descripcion_pago(id_pago,id_venta,monto_cancelado,ref_pago)VALUES(?,?,?,?);";
        try {
            ps = con.EstablecerConexion().prepareCall(insert);
            for (descripcion_pago dpa : l_dpa) {
                ps.setInt(1, dpa.getPago().getId_pago());
                ps.setInt(2, venta.getId_venta());
                ps.setDouble(3, dpa.getMonto_cancelado());
                ps.setString(4, dpa.getRef_pago());
                ps.addBatch();
            }
            ps.executeBatch();
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
    }
}
