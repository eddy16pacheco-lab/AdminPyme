package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.proveedor;

public class consulta_provedor extends sql_consulta {

    public consula_ubicaciones ubicacion = new consula_ubicaciones();

    public boolean insert_table_proveedor(proveedor pr) {
        query = "INSERT INTO provedor(nombre,telefono,correo_electronico,direccion) VALUES (?,?,?,?);";
        if(pr==null){
        }else{
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setString(1, pr.getNombre());
            ps.setString(2, pr.getTelefono());
            ps.setString(3, pr.getCorreo_electronico());
            ps.setString(4, pr.getDireccion());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar en prooveedor: " + e);
        }
        }
    return false;
    }

   public void select_table_proveedor(JTable tabla,String condicion,boolean respuesta,String busqueda) {
     String[] columnsname={"Código de proveedor","Nombre","Teléfono","Correo","Ubicación"};
        DefaultTableModel  modelo =new DefaultTableModel(columnsname,0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        String[] datos = new String[5];
        query = respuesta ? "SELECT id_provedor,nombre,LEFT(telefono, 4),RIGHT(telefono, 7),correo_electronico,direccion FROM provedor WHERE estado='"+condicion+"'"
                : "SELECT id_provedor,nombre,LEFT(telefono, 4),RIGHT(telefono, 7),correo_electronico,direccion FROM provedor WHERE (nombre LIKE '"+busqueda+"%' OR id_provedor LIKE '"+busqueda+"%')";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3)+"-"+rs.getString(4);
                datos[3] = rs.getString(5);
                datos[4] = rs.getString(6);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta de proovedor: " + e);
        }
    }
 
   public void cargar_proveedor(JComboBox Combox){
       DefaultComboBoxModel modelo = new DefaultComboBoxModel();
       modelo.addElement("Seleccionar Proveedor");
       query="SELECT * FROM provedor";
       try {
           st=con.EstablecerConexion().createStatement();
           rs=st.executeQuery(query);
       while(rs.next()){
       modelo.addElement(rs.getString(2));
       }
       Combox.setModel(modelo);
       } catch (SQLException e) {
           System.out.println("Error al cargar proveedor"+e);
       }
   }
    public boolean  update_table_proveedor(proveedor pro){
    query="UPDATE  provedor SET nombre=?,direccion=?,telefono=?,correo_electronico=?,estado=? WHERE id_provedor=?;";
    try {
        cs=con.EstablecerConexion().prepareCall(query);
        cs.setString(1, pro.getNombre());
        cs.setString(2, pro.getDireccion());
        cs.setString(3, pro.getTelefono());
        cs.setString(4, pro.getCorreo_electronico());
        cs.setString(5, pro.getEstado());
        cs.setInt(6, pro.getId_proveedor());   
        cs.execute();
        JOptionPane.showMessageDialog(null,"Datos de prooveedor actualizado");
        con.CerrarConexion();
        return true;
    } catch (SQLException e) {
    JOptionPane.showMessageDialog(null,"Error al actualizar proveedor: "+e);
    }
    return false;
}

    public void estado_proveedor(proveedor pro,String mensaje){
query="UPDATE  provedor SET estado=? WHERE id_provedor=?;";
    try {
        cs = con.EstablecerConexion().prepareCall(query);
        cs.setString(1, pro.getEstado());
        cs.setInt(2, pro.getId_proveedor());
        cs.execute();
        JOptionPane.showMessageDialog(null,"Datos de prooveedor "+mensaje+"");
    } catch (SQLException e) {
    JOptionPane.showMessageDialog(null,"Error al inhabilitar proveedor: "+e);
    }
}
    
    public int buscar_proveedor(String nombre_proveedor){
    int id_proveedor=0;
    query="SELECT id_provedor FROM provedor WHERE nombre='"+nombre_proveedor+"'";
        try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next()){
            id_proveedor=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los datos del proveedor "+nombre_proveedor+": "+e);
        }
        return id_proveedor;
    }
    
    public ArrayList<proveedor> cargar_proveedores(boolean respuesta,String nombre) {
        ArrayList<proveedor> lista_proveedores = new ArrayList<>();
        query = respuesta ? "SELECT * FROM provedor" :"SELECT * FROM provedor WHERE nombre='"+nombre+"'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                proveedor proveedor = new proveedor();
                proveedor.setId_proveedor(rs.getInt(1));
                proveedor.setNombre(rs.getString(2));
                proveedor.setTelefono(rs.getString(3));
                proveedor.setCorreo_electronico(rs.getString(4));
                proveedor.setDireccion(rs.getString(5));
                lista_proveedores.add(proveedor);
                
            }
            
        } catch (Exception e) {
            System.out.println("Error al cargar_proveedores: "+e);
        }
    return lista_proveedores;
    }

    public proveedor seleccionar_proveedor(String nombre){
    proveedor proveedor=new proveedor();
        query="SELECT * FROM provedor WHERE nombre='"+nombre+"'";
        try {
          st=con.EstablecerConexion().createStatement();
          rs=st.executeQuery(query);
          if(rs.next()){
          proveedor.setId_proveedor(rs.getInt(1));
          }else{
              System.out.println("No hay nada");
          }
        } catch (SQLException e) {
            System.out.println("Error al seleccionar_proveedor: "+e);
        }
    return proveedor;
    }
}
