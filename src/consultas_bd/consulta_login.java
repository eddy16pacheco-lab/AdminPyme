package consultas_bd;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.roles;
import modelo.usuario;

public class consulta_login extends sql_consulta{
    public usuario obtener_usuario(String u) {
        usuario usu = new usuario();
        query = "SELECT DISTINCT u.id_usuario,u.nombre_usuario,u.contraseña_usuario,r.tipo_rol FROM usuario u,roles r WHERE u.nombre_usuario='" + u + "' AND  u.id_rol=r.id_rol;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                usu.setId_persona(rs.getInt(1));
                usu.setNombre_usuario(rs.getString(2));
                usu.setContraseña_usuario(rs.getString(3));
                roles rol = new roles();
                rol.setTipo_rol(rs.getString(4));
                usu.setRol(rol);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un evento inseperado: " + e);
        }
        return usu;
    }
   
}
