package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.roles;
import modelo.usuario;

public class consulta_usuario extends consulta_persona {
public consulta_roles roles=new consulta_roles();
 // Agrega este método en tu clase consulta_usuario
public boolean correoExiste(String correo) {
     query = "SELECT COUNT(*) FROM usuario WHERE correo_electronico = ?";
    
    try {
         ps =con.EstablecerConexion().prepareCall(query);
         ps.setString(1, correo);
         rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Cerrar recursos (rs, ps, con)
    }
    
    return false;
}

public boolean cedulaExite(String cedula){
query="SELECT COUNT(*) FROM persona WHERE cedula='"+cedula+"'";
    try {
        st=con.EstablecerConexion().createStatement();
        rs=st.executeQuery(query);
        if(rs.next()){
        return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar cedula: "+e);
    }
return false;
}
    public void registrar_usuario(usuario u) {
        query = "INSERT INTO usuario(id_persona,nombre_usuario,contraseña_usuario,correo_electronico,id_rol)VALUES(?,?,?,?,?);";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, u.getId_persona());
            cs.setString(2, u.getNombre_usuario());
            cs.setString(3, u.getContraseña_usuario());
            cs.setString(4, u.getCorreo_electronico());
            cs.setInt(5, u.getRol().getId_rol());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Usuario registrado");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
    }

    public void modificar_usuario(usuario u) {
        query = "UPDATE usuario SET nombre_usuario=?,contraseña_usuario=?,correo_electronico=?,id_rol=? WHERE id_usuario=?;";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, u.getNombre_usuario());
            cs.setString(2, u.getContraseña_usuario());
            cs.setString(3, u.getCorreo_electronico());
            cs.setInt(4, u.getRol().getId_rol());
            cs.setInt(5, u.getId_persona());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
    }

    public void eliminar_usuario(usuario u) {
        query = "DELETE FROM usuario WHERE id_usuario=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, u.getId_persona());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }
  
    public boolean buscar_usuario(String u) {
        usuario usu = new usuario();
        query = "SELECT DISTINCT u.id_usuario,u.nombre_usuario,u.`contraseña_usuario`,r.tipo_rol FROM usuario u,roles r WHERE u.nombre_usuario='" + u + "' AND  u.id_rol=r.id_rol;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                usu.setId_persona(rs.getInt(1));
                usu.setNombre_usuario(rs.getString(2));
                usu.setContraseña_usuario(rs.getString(3));
                roles rol = new roles();
                rol.setTipo_rol(rs.getString(3));
                usu.setRol(rol);
                con.CerrarConexion();
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un evento inseperado: " + e);
        }
        return false;
    }

    public void buscar_usuario(JTable tabla, String busqueda, String filtro) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        query = "SELECT * FROM usuario_consulta u WHERE u.nombre_usuario LIKE'" + busqueda + "%' AND u.tipo_rol='" + filtro + "'";
        tabla.setModel(modelo);
        String[] datos = new String[7];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

    public void buscar_usuario(JTable tabla, String filtro) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        query = "SELECT * FROM usuario_consulta u WHERE u.tipo_rol='" + filtro + "'";
        tabla.setModel(modelo);
        String[] datos = new String[7];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

    public usuario seleccionar_usuario_cedula(String ced) {
        usuario usua = new usuario();
        query = "SELECT * FROM usuario_consulta WHERE cedula='" + ced + "';";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                usua.setNombre_usuario(rs.getString(1));
                usua.setApellido(rs.getString(2));
                usua.setCorreo_electronico(rs.getString(3));
                roles rol = new roles();
                rol.setTipo_rol(rs.getString(4));
                usua.setRol(rol);
                usua.setEstado(rs.getString(5));
                usua.setId_persona(rs.getInt(6));
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al seleccionar el cliente: " + e);
        }
        return usua;
    }

    public usuario seleccionar_usuario(String usu) {
        usuario usua = new usuario();
        query = "SELECT id_usuario,nombre_usuario,contraseña_usuario,id_rol,estado FROM  usuario u WHERE nombre_usuario='" + usu + "';";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {  
                usua.setId_persona(rs.getInt(1));
                usua.setNombre_usuario(rs.getString(2));
                usua.setContraseña_usuario(rs.getString(3));
                roles rol = new roles();
                rol.setId_rol(rs.getInt(4));
                usua.setRol(rol);
                usua.setEstado(rs.getString(5));    
                }
            
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al seleccionar el cliente: " + e);
        }
        return usua;
    }

    public JTable cargar_usuario(JTable tabla,String estado) {
        String[] columnNames = {"Usuario", "Contraseña", "Correo", "Tipo de rol", "Estado", "id usuario"};
       DefaultTableModel modelo = new DefaultTableModel(columnNames,0);
        modelo.setRowCount(0);
        query = "SELECT * FROM usuario_consulta WHERE estado!='"+estado+"'";
        tabla.setModel(modelo);
        String[] datos = new String[7];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5]= rs.getString(6);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
        return tabla;
    }
    
    public void select_table_usuario(JTable tabla, String busqueda) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        query = "SELECT * FROM usuario_consulta u WHERE u.nombre_usuario LIKE'" + busqueda + "%'";
        tabla.setModel(modelo);
        String[] datos = new String[7];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }
 
    public void inhabilitar_usuario(String id_usuario){
    query="UPDATE usuario SET estado='Inactivo' WHERE id_usuario='"+id_usuario+"'";
        try {
            ps=con.EstablecerConexion().prepareCall(query);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Usuario Inhabilitado");
        } catch (SQLException e) {
            System.out.println("Error al inhabilitar usuario: "+e);
        }
    }
}
