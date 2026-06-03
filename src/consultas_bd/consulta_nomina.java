package consultas_bd;

import java.lang.reflect.Array;
import modelo.nomina;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.pago;
import modelo.roles;
import modelo.usuario;

public class consulta_nomina extends sql_consulta {

    public consulta_usuario con_usu=new consulta_usuario();
    public consulta_pago con_pag=new consulta_pago();
    public boolean registrar_nomina(nomina nomina) {
        query = "INSERT INTO nomina(id_usuario,id_pago,ref_pago,fecha_pago,salario_bruto,periodo_pago,estado_pago) "
                + "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setInt(1, nomina.getUsuario().getId_persona());
            ps.setInt(2, nomina.getPago().getId_pago());
            ps.setString(3, nomina.getRef_pago());
            ps.setDate(4, new java.sql.Date(nomina.getFecha_pago().getTime()));
            ps.setDouble(5, nomina.getSalario_bruto());
            ps.setString(6, nomina.getPeriodo_pago());
            ps.setString(7, nomina.getEstado_pago());
            ps.execute();
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
         JOptionPane.showMessageDialog(null,""+e.getMessage());
        }
        return false;
    }

    public boolean modificar_nomina(nomina nomina) {
        query = "UPDATE nomina SET id_usuario=?,id_pago=?,ref_pago=?,fecha_pago=?,salario_bruto=?,periodo_pago=?,estado_pago=?"
                + "WHERE id_nomina=? ";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, nomina.getUsuario().getId_persona());
            cs.setInt(2, nomina.getPago().getId_pago());
            cs.setString(3, nomina.getRef_pago());
            cs.setDate(4, new java.sql.Date(nomina.getFecha_pago().getTime()));
            cs.setDouble(5, nomina.getSalario_bruto());
            cs.setString(6, nomina.getPeriodo_pago());
            cs.setString(7, nomina.getEstado_pago());
            cs.setInt(8, nomina.getId_nomina());
            cs.execute();
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            System.out.println("" + e);
        }

        return false;
    }

    public void mostrar_nomina(JTable tabla,boolean respuesta,Date fechaIni,Date fechaFin) {
           // Configurar tabla
        String[] columnNames = {
            "Número de Pago", "Empleado", "Periodo de Pago", 
            "Salario Bruto", "Forma de Pago", "Estado del Pago", 
            "Ref del Pago", "Fecha del Pago"
        };
DefaultTableModel  modelo=new DefaultTableModel(columnNames,0); 
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = respuesta ? "SELECT * FROM nomina_consulta":"SELECT * FROM nomina_consulta c WHERE c.`Fecha del Pago` BETWEEN '"+new java.sql.Date(fechaIni.getTime())+"' AND '"+new java.sql.Date(fechaFin.getTime())+"'";
        Object[] datos=new Object[9];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    }
    
    public void buscar_nomina(JTable tabla,String busqueda){
           modelo=(DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM nomina_consulta n WHERE n.Empleado='"+busqueda+"'";
        Object[] datos=new Object[9];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    
    }
  
    public ArrayList<usuario> cargar_empleados() {
        ArrayList<usuario> lista_empleado = new ArrayList<>();
        query = "SELECT persona,cedula,tipo_rol FROM usuario_consulta";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                usuario usuario = new usuario();
                roles rol = new roles();
                usuario.setNombre(rs.getString(1));
                usuario.setCedula(rs.getInt(2));
                rol.setTipo_rol(rs.getString(3));
                usuario.setRol(rol);
                lista_empleado.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al caragar_empleados: "+e);
        }
    return lista_empleado;
    }

  public int cargar_id_nomina(){
  int id_nomina=0;
      query="SELECT MAX(id_nomina) FROM nomina";
      try {
          st=con.EstablecerConexion().createStatement();
          rs=st.executeQuery(query);
          if(rs.next()){
          id_nomina=rs.getInt(1);
          }
      } catch (SQLException e) {
          System.out.println("Error al cargar la ultima nomina; "+e);
      }
      return id_nomina;
  }
}
