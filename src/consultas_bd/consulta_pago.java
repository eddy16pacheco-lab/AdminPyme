package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class consulta_pago extends sql_consulta {

    public void select_table_pago(JComboBox combo) {
        query = "SELECT tipo_de_pago FROM pago ORDER BY id_pago ASC";
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar pago");
        combo.setModel(modelo);
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }
            combo.setModel(modelo);
            con.CerrarConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }

    public void descripicon_pago_consulta(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM descripicon_pago_consulta";
        String[] datos = new String[4];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            System.out.println("" + e);
        }
    }

    public int buscar_forma_de_pago(String forma_de_pago) {
        int id_pago = 0;
        query = "SELECT id_pago FROM pago WHERE tipo_de_pago='" + forma_de_pago + "' ";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                id_pago = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al busca forma de pago: " + e);
        }
        return id_pago;
    }

}
