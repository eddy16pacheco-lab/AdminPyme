package consultas_bd;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.inventario;

public class consulta_movimiento_inventario extends sql_consulta{

public void select_consulta_movimiento_inventario(JTable tabla,int n_consulta,String filtro,Date fechaIni,Date fechaFin){
String[] columns = {"Producto", "Cantidad", "Precio", "Tipo movimiento", "Fecha","Responsable"};  
DefaultTableModel modelo = new DefaultTableModel(columns, 0);
    modelo.setRowCount(0);
    tabla.setModel(modelo);
    if (n_consulta == 1) {
        query = "SELECT * FROM movimiento_inventario_consulta m ORDER BY m.id_movimiento_inventario DESC";
    }
    if (n_consulta == 2) {
        query = "SELECT * FROM movimiento_inventario_consulta m WHERE m.tipo_movimiento='" + filtro + "' ORDER BY m.id_movimiento_inventario DESC";
    }
    if (n_consulta == 3) {
        query = "SELECT * FROM movimiento_inventario_consulta m WHERE m.fecha_movimiento BETWEEN '" + new java.sql.Date(fechaIni.getTime()) + "' AND '" + new java.sql.Date(fechaFin.getTime()) + "' ORDER BY m.id_movimiento_inventario DESC";
    }
    if (n_consulta == 4) {
        query = "SELECT * FROM movimiento_inventario_consulta m WHERE m.fecha_movimiento BETWEEN '" + new java.sql.Date(fechaIni.getTime()) + "' AND '" + new java.sql.Date(fechaFin.getTime()) + "' AND m.tipo_movimiento='" + filtro + "' ORDER BY m.id_movimiento_inventario DESC";
    }
    try {
        String[] datos = new String[7];
        st = con.EstablecerConexion().createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            datos[0]=rs.getString(1);
            datos[1] = rs.getString(2);
            datos[2] = rs.getString(3);
            datos[3] = rs.getString(4);
            datos[4] = rs.getString(5);
            datos[5] = rs.getString(7);
         //   datos[6] = rs.getString(7);
            modelo.addRow(datos);
        }
        tabla.setModel(modelo);
        con.CerrarConexion();
    } catch (SQLException e) {
JOptionPane.showMessageDialog(null,"Error: "+e);
    }
}

//metodo para buscar por rango de fecha
public void select_consulta_movimiento_inventario(JTable tabla,Date fecha){
modelo=(DefaultTableModel) tabla.getModel();
modelo.setRowCount(0);
tabla.setModel(modelo);
query="SELECT * FROM movimiento_inventario_consulta WHERE fecha_movimiento='"+new java.sql.Date(fecha.getTime())+"'";
    try {
        String[] datos = new String[7];
        st = con.EstablecerConexion().createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            datos[0]=rs.getString(2);
            datos[1] = rs.getString(3);
            datos[2] = rs.getString(4);
            datos[3] = rs.getString(5);
            datos[4] = rs.getString(6);
            datos[5] = rs.getString(7);
            datos[6] = rs.getString(8);
         //   datos[6] = rs.getString(7);
            modelo.addRow(datos);
        }
        tabla.setModel(modelo);
        con.CerrarConexion();
    } catch (SQLException e) {
JOptionPane.showMessageDialog(null,"Error: "+e);
    }
}


}

