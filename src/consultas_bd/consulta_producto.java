package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.categoria;
import modelo.producto;

public class consulta_producto extends sql_consulta {

    public consulta_categoria con_categoria=new consulta_categoria();

    public void obtener_producto(JComboBox Combo) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar un Producto");
        Combo.setModel(modelo);
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(3));
            }
         Combo.setModel(modelo);
        } catch (SQLException e) {
        }
}
       public void obtener_producto_codigo(JComboBox Combo) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar un Producto");
        Combo.setModel(modelo);
        query="SELECT DISTINCT CONCAT(p.codigo_barras,' ',p.nombre_producto) AS 'Producto' FROM detalle_orden_de_compra dodc INNER JOIN producto p ON dodc.producto_id=p.producto_id";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }
         Combo.setModel(modelo);
        } catch (SQLException e) {
        }
}
    public int seleccionar_id_mas_alto(String table, String columna) {
        int max = 0;
        query = "SELECT MAX(" + columna + ") FROM " + table + "";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                max = rs.getInt(1);
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Mensaje de error al \nSeleccionar el valor mas alto" + e);
        }
        return max;
    }
    public void producto_buscar(String busqueda, JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM  producto_buscar v WHERE (producto_id LIKE '" + busqueda + "%' OR nombre_producto LIKE '" + busqueda + "%'); ";
        String[] datos = new String[5];
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
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
            JOptionPane.showMessageDialog(null, "A ocurrido un error en producto buscar: " + e);
        }
    }

    public void seleccionar_producto_categoria(String categoria, JTable tabla, boolean estado) {
        String[] datos = new String[6];
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = estado ? "SELECT * FROM producto_consulta p WHERE p.tipo_categoria='" + categoria + "';" :
                "SELECT * FROM producto_consulta_inactivo p WHERE p.tipo_categoria='" + categoria + "';";
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
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
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return id;
    }

    public void buscar_producto_especificos(String busqueda, JTable tabla, String categoria, boolean estado) {
        String[] datos = new String[5];
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = 
           estado ? 
                "SELECT * FROM producto_consulta p WHERE p.tipo_categoria='" + categoria + "' AND (p.nombre_producto LIKE'" + busqueda + " %' OR p.producto_id LIKE'" + busqueda + "%';"  
              : "SELECT * FROM producto_consulta_inactivo p WHERE p.tipo_categoria='" + categoria + "' AND (p.nombre_producto LIKE'" + busqueda + " %' OR p.producto_id LIKE'" + busqueda + "%');";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
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
            JOptionPane.showMessageDialog(null, "Error en este metodo: " + e);
        }

    }

    public void select_table_producto(JTable tabla,int operacion,String busqueda) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        String[] datos = new String[9];
         
        if(operacion==1){
        query = "SELECT p.producto_id,p.codigo_barras,p.nombre_producto,p.descripcion,c.tipo_categoria,CONCAT(p.capacidad_unidad,' ',p.unidad_medida) AS 'Unidad Med',p.precio_venta,p.precio_compra,p.fecha_vencimiento FROM producto p INNER JOIN categoria c ON p.id_categoria=c.id_categoria";
        }
        if(operacion==2){
        query="SELECT p.producto_id,p.codigo_barras,p.nombre_producto,p.descripcion,c.tipo_categoria,CONCAT(p.capacidad_unidad,' ',p.unidad_medida) AS 'Unidad Med',p.precio_venta,p.precio_compra,p.fecha_vencimiento FROM producto p INNER JOIN categoria c ON p.id_categoria=c.id_categoria WHERE p.codigo_barras LIKE '%"+busqueda+"%' OR p.nombre_producto LIKE '%"+busqueda+"%'";
        }if(operacion==3){
        query="SELECT p.producto_id,p.codigo_barras,p.nombre_producto,p.descripcion,c.tipo_categoria,CONCAT(p.capacidad_unidad,' ',p.unidad_medida) AS 'Unidad Med',p.precio_venta,p.precio_compra,p.fecha_vencimiento FROM producto p INNER JOIN categoria c ON p.id_categoria=c.id_categoria WHERE c.tipo_categoria LIKE '%"+busqueda+"%'"; 
        }
                try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6]= rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8]= rs.getString(9);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

    public void buscar_producto_especificos(String busqueda, JTable tabla,boolean activo) {
        String[] datos = new String[6];
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query =activo ? "SELECT * FROM producto_consulta p WHERE (p.nombre_producto LIKE'" + busqueda + "%' OR p.producto_id LIKE'" + busqueda + "%') ORDER BY producto_id ASC;"
                :"SELECT * FROM producto_consulta_inactivo p WHERE (p.nombre_producto LIKE'" + busqueda + "%' OR p.producto_id LIKE'" + busqueda + "%') ORDER BY producto_id ASC;";
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }

            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "1__Error: " + e);
        }

    }

    public boolean update_table_Producto(producto p) {
        query = "UPDATE producto SET codigo_barras = ?, id_categoria = ?,nombre_producto = ?, descripcion = ? , capacidad_unidad = ?, unidad_medida = ?, precio_compra = ?, precio_venta = ?, fecha_vencimiento = ? WHERE producto_id = ?;";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, p.getCodigo_barra());
            cs.setInt(2, p.getCategoria().getId_categoria());
            cs.setString(3, p.getNombre_prodcuto());
            cs.setString(4, p.getDescripcion());
            cs.setDouble(5, p.getCant_uni_med());
            cs.setString(6, p.getUnidad_medida());
            cs.setDouble(7, p.getPrecio_unitario());
            cs.setDouble(8, p.getPrecio_venta());
            cs.setDate(9, new java.sql.Date(p.getFecha_vencimiento().getTime()));
            cs.setInt(10, p.getId_producto());
            cs.execute();
          JOptionPane.showMessageDialog(null, "Datos del producto modificados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
          con.CerrarConexion();
        return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
        return false;
    }

    public boolean insert_table_Productos(producto p) {
        query = "INSERT INTO producto(codigo_barras,id_categoria,nombre_producto,descripcion,capacidad_unidad,unidad_medida,precio_compra,precio_venta,fecha_vencimiento) VALUES(?,?,?,?,?,?,?,?,?);";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, p.getCodigo_barra());
            cs.setInt(2, p.getCategoria().getId_categoria());
            cs.setString(3, p.getNombre_prodcuto());
            cs.setString(4, p.getDescripcion());
            cs.setDouble(5, p.getCant_uni_med());
            cs.setString(6, p.getUnidad_medida());
            cs.setDouble(7, p.getPrecio_unitario());
            cs.setDouble(8, p.getPrecio_venta());
            cs.setDate(9, new java.sql.Date(p.getFecha_vencimiento().getTime()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Datos del producto guardados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            int error = e.getErrorCode();
            if (error == 1062) {
                JOptionPane.showMessageDialog(null, "Numero del producto ya asignado", "MENSAJE DE ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado al insertar" + e);
            }
        }
        return false;
    }

    // Método alternativo más simple
    public String cambiarEstadoProducto(int productoId, boolean activar) {
        query = activar ? "CALL mover_producto_a_activos(?)" : "CALL mover_producto_a_inactivos(?)";
        String operacion = activar ? "reactivar" : "desactivar";

        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, productoId);

            boolean tieneResultados = cs.execute();

            if (tieneResultados) {
                rs = cs.getResultSet();
                if (rs.next()) {
                    if (rs.getString("resultado").equals("Producto reactivado correctamente")) {
                        JOptionPane.showMessageDialog(null, "Producto Habilitado correctamente");
                    } else if (rs.getString("resultado").equals("Producto movido a inactivos correctamente")) {
                        JOptionPane.showMessageDialog(null, "Producto Inhabilitado correctamente");
                    }

                }

            }

        } catch (SQLException e) {
            return "Error al " + operacion + " producto: " + e.getMessage();
        }

        return "Operación completada";
    }

    public ArrayList<producto> obtener_producto(boolean desicion,String busqueda) {
        ArrayList<producto> lista_producto = new ArrayList<>();
        query =desicion ? "SELECT * FROM producto_consulta" : 
                "SELECT * FROM producto_consulta p WHERE p.producto_id='" + busqueda + "'";
        try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            while(rs.next()){
            producto p=new producto();
            p.setId_producto(rs.getInt(1));
            categoria c=new categoria();
            c.setTipo_categoria(rs.getString(2));
            p.setCategoria(c);
            p.setNombre_prodcuto(rs.getString(3));
            p.setDescripcion(rs.getString(4));
            p.setPrecio_venta(rs.getDouble(5));
            p.setPrecio_unitario(rs.getDouble(6));
            lista_producto.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto: "+e);
        }
        return lista_producto;
    }
    
    public boolean buscar_producto(String busqueda, JList Lista) {
        DefaultListModel modelo = new DefaultListModel();
      
        query = "SELECT * FROM producto_consulta p WHERE p.producto_id LIKE'" + busqueda + "%' OR p.nombre_producto LIKE'" + busqueda + "%'";
        
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement("C° " + rs.getInt(1) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getDouble(6));
            }
            Lista.setModel(modelo);
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    return false;
    }
}


