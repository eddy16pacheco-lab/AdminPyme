package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.roles;

public class consulta_roles extends sql_consulta{
   public void insert_table_roles(roles r) {
        query = "INSERT INTO roles(nombre_rol,descripcion)VALUES(?,?);";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, r.getDescripcion_rol());
            cs.setString(2, r.getDescripcion_rol());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Reguistro de persona exitoso");
    con.CerrarConexion();
        } catch (SQLException e) {
    JOptionPane.showMessageDialog(null,"A ocurrido un error inesperado"+e);
    
    }
   }
   public int buscar_id_roles(String nombre_rol){
   query="SELECT id_rol FROM roles WHERE tipo_rol='"+nombre_rol+"'";
  int id=0;
   try {
       st=con.EstablecerConexion().createStatement();
       rs=st.executeQuery(query);
          if(rs.next()){
          id=rs.getInt(1);
          }
   con.CerrarConexion();
   } catch (SQLException e) {
           System.out.println(""+e);
       }
   return id;
   }
    public void select_table_roles(JComboBox combo) {
        query = "SELECT * FROM roles ";
        C_modelo=new DefaultComboBoxModel();
        C_modelo.addElement("Seleccionar un rol");
        combo.setModel(C_modelo);
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
               C_modelo.addElement(rs.getString(2));
            }
            combo.setModel(C_modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

}
