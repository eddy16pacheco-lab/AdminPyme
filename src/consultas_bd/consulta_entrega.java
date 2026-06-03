package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.entrega;

public class consulta_entrega extends sql_consulta{
    public void select_table_entrega(JComboBox combo) {
        C_modelo = (DefaultComboBoxModel) combo.getModel();
        String select = "SELECT * FROM entrega";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                C_modelo.addElement(rs.getString(1) + " " + rs.getString(2));
            }
            combo.setModel(C_modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }
public void insert_table_entrega(entrega en){
 String insert="INSERT INTO entrega(forma_entrega) VALUES(?)";
    try {
        cs=con.EstablecerConexion().prepareCall(insert);
        cs.setString(1, en.getForma_entrega());
        cs.execute();
        JOptionPane.showMessageDialog(null,"forma de entrega reguistrada");
        con.CerrarConexion();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"A ocurrido un error inesperado"+e);
    }

}


}
