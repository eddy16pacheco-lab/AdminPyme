package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class consula_ubicaciones extends sql_consulta{
       public void select_cargar_ubicaciones(JComboBox modelo) {
       DefaultComboBoxModel C_modelo = new DefaultComboBoxModel();
       C_modelo.addElement("Seleccionar ubicación");
       modelo.setModel(C_modelo);
        String select = "SELECT DISTINCT CONCAT(p.nombre_parroquia,' ',m.nombre_municipio) FROM parroquia p,municipio m "
                + "WHERE p.id_municipio=m.id_municipio ORDER BY p.id_parroquia ASC;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                C_modelo.addElement(rs.getString(1));
            }
            modelo.addItem(C_modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"Error al cargar las ubicaciones: "+e);
        }
    }
 public void select_table_ubicaciones(JComboBox modelo) {
        C_modelo = (DefaultComboBoxModel) modelo.getModel();
        String select = "SELECT * FROM ubicacion";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                C_modelo.addElement(rs.getString(1) + " " + rs.getString(2));
            }
            modelo.setModel(C_modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error a cargar el combox\nde ubicaciones: " + e);
        }
    }

 
}
