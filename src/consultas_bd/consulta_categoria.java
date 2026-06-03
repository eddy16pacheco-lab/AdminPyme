package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.categoria;


public class consulta_categoria extends sql_consulta {

     public void buscar_categoria(JTable tabla, String busqueda) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM categoria_consultar c WHERE c.tipo_categoria LIKE '%" + busqueda + "%' ";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            String[] datos = new String[3];
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a mostrar la tabla categoria: " + e);
        }
    }

    public int buscar_id_categoria(String categoria) {
        int id = 0;
        query = "SELECT id_categoria FROM categoria WHERE tipo_categoria='" + categoria + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
            }
        } catch (SQLException e) {
            System.out.println("" + e);
        }
        return id;
    }

    public void select_table_categoria(JTable tabla,boolean respuesta,String busqueda) {
        String[] columnNames = {"Codigo de categoria", "Nombre", "Descripción", "Estado", "Productos"};
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = respuesta ? "SELECT * FROM categoria_consultar":
                "SELECT * FROM categoria_consultar c WHERE c.id_categoria LIKE '"+busqueda+"%' OR c.tipo_categoria LIKE '"+busqueda+"%'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            String[] datos = new String[5];
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
            JOptionPane.showMessageDialog(null, "Error a mostrar la tabla categoria: " + e);
        }
    }

    public boolean insert_table_Categoria(categoria ca) {
        query = "INSERT INTO categoria(tipo_categoria,descripcion,id_usuario) VALUES(?,?,?)";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, ca.getTipo_categoria());
            cs.setString(2, ca.getDescripcion());
            cs.setInt(3, ca.getAdministrador().getId_persona());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Categoría Registrada\nExitosamente");
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado: " + e);
        }
        return false;
    }

    public boolean update_table_Categoria(categoria ca, int id_categoria) {
        query = "UPDATE categoria SET tipo_categoria=?,id_usuario=? WHERE id_categoria=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, ca.getTipo_categoria());
            cs.setInt(2, ca.getAdministrador().getId_persona());
            cs.setInt(3, id_categoria);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Categoria Actualizada\nExitosamente");
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
        return false;
    }
    
        public void carcar_categorias(JComboBox combo) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar categoria");
        combo.setModel(modelo);
        query = "SELECT tipo_categoria FROM categoria ORDER BY id_categoria ASC;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }
            combo.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }


}
