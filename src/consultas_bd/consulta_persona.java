package consultas_bd;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.persona;

public class consulta_persona extends sql_consulta{

    public boolean update_table_Persona(persona p) {
        query = "UPDATE persona SET cedula=?,nombre=?,apellido=? WHERE id_persona=?;";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, p.getCedula());
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            cs.setInt(4, p.getId_persona());
            cs.execute();
            con.CerrarConexion();
           return true;
        } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "Error al modificar: " + e);
        }
        return false;
    }

    public boolean insert_table_Persona(persona p) {
        query = "INSERT INTO persona(cedula,nombre,apellido)VALUES(?,?,?);";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, p.getCedula());
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            cs.execute();
            //    JOptionPane.showMessageDialog(null,"Reguistro de persona exitoso");
            con.CerrarConexion();
        return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
        return false;
    }

    public void delete_table_persona(persona p) {
        query = "DELETE FROM persona WHERE cedula=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, p.getCedula());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Persona eliminada correctamente");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: ");
        }
    }
    
    public int select_id_persona() {
        int id_persona = 0;
        query = "SELECT MAX(id_persona) FROM persona p;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                id_persona = rs.getInt(1);
            }
            con.CerrarConexion();
        } catch (Exception e) {
            System.out.println(""+e);
        }
        return id_persona;
    }
    
    public int select_id_persona(String id){
    int id_p=0;
query="SELECT p.id_persona FROM cliente c INNER JOIN persona p ON c.id_persona=p.id_persona WHERE c.id_cliente='"+id+"'";    
    try {
        st=con.EstablecerConexion().createStatement();
        rs=st.executeQuery(query);
        if(rs.next()){
        id_p=rs.getInt(1);
        }
        } catch (SQLException e) {
            System.out.println("Error: "+e);
        }
        return id_p; 
    }

}
