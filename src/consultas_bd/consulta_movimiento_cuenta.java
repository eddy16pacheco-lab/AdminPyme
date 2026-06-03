package consultas_bd;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class consulta_movimiento_cuenta extends sql_consulta{
 public void consulta_movimiento_cuenta(JTable tabla,Date fecha_inicio,Date fecha_final,boolean respuesta){
 
  modelo=(DefaultTableModel) tabla.getModel();
 modelo.setRowCount(0);
 tabla.setModel(modelo);
     query = respuesta ? "SELECT * FROM registro_financiero ORDER BY id_registro DESC" : 
      "SELECT * FROM registro_financiero WHERE fecha_transaccion BETWEEN '" + new java.sql.Date(fecha_inicio.getTime()) + "' AND '" + new java.sql.Date(fecha_final.getTime()) + "' ;";
     Object[] datos = new Object[6];
     try {
         st = con.EstablecerConexion().createStatement();
         rs = st.executeQuery(query);
         while (rs.next()) {
             datos[0] = rs.getString(2);
             datos[1] = rs.getString(6);
             datos[2] = rs.getString(4);
             datos[3] = rs.getString(5);
             datos[4] = rs.getString(7);
             datos[5] = rs.getString(19);
             modelo.addRow(datos);
         }
         tabla.setModel(modelo);
         con.CerrarConexion();
     } catch (SQLException e) {
         System.out.println("Error al cargar los movimiento cuenta: " + e);
     }
 }  
}
