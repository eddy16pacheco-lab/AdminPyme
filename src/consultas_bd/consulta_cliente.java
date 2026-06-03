package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.categoria;
import modelo.cliente;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import modelo.pago;
import modelo.producto;
import modelo.parroquia;
import modelo.usuario;
import modelo.ventas;

public class consulta_cliente extends consulta_persona{

    public consula_ubicaciones ubicacion = new consula_ubicaciones();
    
    public void delete_table_cliente(cliente c) {
        query = "DELETE FROM cliente WHERE id_cliente=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, c.getCedula());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

    public cliente seleccionar_cliente_por_cedula(String cedula) {
        cliente cliente = new cliente();
        query = "SELECT DISTINCT p.nombre,p.apellido,p.cedula,c.telefono FROM persona p,cliente c,venta v WHERE p.id_persona=c.id_persona AND v.id_cliente=c.id_cliente AND p.cedula='" + cedula + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                cliente.setNombre(rs.getString(1));
                cliente.setApellido(rs.getString(2));
                cliente.setCedula(rs.getInt(3));
                cliente.setTelefeno(rs.getString(4));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return cliente;
    }

    public boolean update_table_Cliente(cliente c) {
       query = "UPDATE cliente SET telefono=?,id_parroquia=? WHERE id_cliente=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setString(1, c.getTelefeno());
            cs.setInt(2, c.getUbicacion().getId_parroquia());
            cs.setInt(3, c.getId_cliente());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del cliente: " + e);
        }  
        return false;
    }

    public boolean insert_table_Cliente(cliente c) {
        c.setId_persona(select_id_persona());
        query = "INSERT INTO cliente (id_persona,telefono,id_parroquia) VALUES (?,?,?)";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, c.getId_persona());
            cs.setString(2, c.getTelefeno());
            cs.setInt(3, c.getUbicacion().getId_parroquia());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Cliente registrado");
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado mira me: " + e);
        }
return false;
    }

    //si el cliente ya esta reguistrado en el sistema
    public ventas select_table_venta_cliente(String ce) {
        ventas v = new ventas();
        cliente c = new cliente();
        usuario u = new usuario();
        query = "SELECT c.id_cliente,v.id_venta,v.id_usuario FROM cliente c,persona p,venta v WHERE (v.id_cliente=c.id_cliente AND c.id_persona=p.id_persona) AND p.cedula='" + ce + "';";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId_persona(Integer.parseInt(rs.getString(1)));
                v.setCliente(c);
                v.setId_venta(rs.getInt(2));
                u.setId_persona(rs.getInt(3));
                v.setEmpleado(u);
            } else {
                c.setCedula(0);
                v.setCliente(c);
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado en ventas clientes: " + e);
        }

        return v;
    }

    public void select_table_cliente_busqueda(JList tabla, String bus) {
        DefaultListModel modelo = new DefaultListModel<>();
        tabla.setModel(modelo);
        query = "SELECT CONCAT(p.id_persona,' ',p.cedula,' ',p.nombre,' ',p.apellido) FROM persona p WHERE (p.cedula LIKE '" + bus + "%' || p.nombre LIKE '" + bus + "%') ;";
        String datos;
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos = rs.getString(1);
                modelo.addElement(datos);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }

//metodo para seleccionar a un cliente por su cedula de persona
    public int select_table_persona(String cedula) {
        cliente cliente = new cliente();
        query = "SELECT DISTINCT id_persona FROM persona WHERE cedula LIKE '" + cedula + "%';";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                cliente.setId_persona(Integer.parseInt(rs.getString(1)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado esta aqui: " + e);
        }
        return cliente.getId_persona();
    }

    public cliente select_cliente_venta(String id_persona) {
        cliente c = new cliente();
        query = "SELECT DISTINCT c.id_cliente,p.nombre,p.apellido,p.cedula FROM cliente c,persona p, venta v WHERE v.id_cliente=c.id_cliente AND c.id_persona=p.id_persona AND c.id_cliente='" + id_persona + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                c.setId_persona(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido(rs.getString(3));
                c.setCedula(rs.getInt(4));
                con.CerrarConexion();
            }
        } catch (SQLException e) {
        }
        return c;
    }

    public void select_table_cliente(JTable tabla,String estado,boolean respuesta,String busqueda) {
   String[] columnNames = {
            "N° Cliente", "Nombre", "Apellido", "Cédula", "Teléfono", "Ubicación"
        };
        DefaultTableModel modelo = new DefaultTableModel(columnNames,0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query =respuesta ? "SELECT * FROM cliente_consultar c WHERE c.estado='"+estado+"' ORDER BY c.`Numero de cliente` ASC" :
                "SELECT * FROM cliente_consultar c WHERE c.estado='"+estado+"' AND (c.nombre LIKE '"+busqueda+"%' OR c.cedula LIKE '"+busqueda+"%' OR c.`Numero de cliente` LIKE '"+busqueda+"') ORDER BY c.`Numero de cliente` ASC";
        String[] datos = new String[6];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = "V-"+rs.getString(4);
                datos[4] = rs.getString(5)+"-"+rs.getString(6);
                datos[5] = rs.getString(7);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar la table cliente: " + e);
        }
    }
   
    public void select_table_cliente_por_estado(JTable tabla,String estado) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM cliente_consultar_estado c WHERE c.estado_venta='"+estado+"'";
        String[] datos = new String[6];
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
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar la table cliente: " + e);
        }
    }

    public void buscar_table_cliente(JTable tabla, String busqueda) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM cliente_consultar c WHERE (c.id_cliente LIKE '" + busqueda + "%' OR c.cedula LIKE'" + busqueda + "%' OR c.nombre LIKE '" + busqueda + "%') ORDER BY id_cliente ASC";
        String[] datos = new String[6];
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
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar la table cliente: " + e);
        }
    }

    public void inhabilitar_o_activar_cliente(cliente c){
    query="UPDATE cliente SET estado=? WHERE id_cliente=?;";
        try {
            cs=con.EstablecerConexion().prepareCall(query);
            cs.setString(1, c.getEstado());
            cs.setInt(2,c.getId_cliente());
            cs.execute();
            JOptionPane.showMessageDialog(null,"Cliente activado");
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    }
 
    public cliente select_cliente_ventas(cliente c) {
        String select = "SELECT c.id_cliente FROM cliente c,persona p WHERE c.id_persona=p.id_persona AND p.cedula=" + c.getCedula() + ";";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                c.setId_persona(rs.getInt(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
        return c;
    }

    public producto select_producto(int id) {
        producto producto = new producto();
        String select = "SELECT DISTINCT p.producto_id,p.nombre_producto,p.descripcion,p.precio_venta,c.id_categoria,c.tipo_categoria FROM producto p, categoria c WHERE p.id_categoria=c.id_categoria AND p.producto_id='" + id + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                producto.setId_producto(rs.getInt(1));
                producto.setNombre_prodcuto(rs.getString(2));
                producto.setDescripcion(rs.getString(3));
                producto.setPrecio_venta(rs.getDouble(4));
                categoria categoria = new categoria();
                categoria.setId_categoria(rs.getInt(5));
                categoria.setTipo_categoria(rs.getString(6));
                producto.setCategoria(categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar un producto: " + e);
        }
        return producto;
    }
     public DefaultListModel<String> cargarHistorialVentasCliente(int idCliente) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        String sql = "SELECT " +
                     "    v.id_venta, " +
                     "    DATE_FORMAT(v.fecha, '%d/%m/%Y') as fecha, " +
                     "    p.nombre_producto, " +
                     "    dv.cantidad_vendida, " +
                     "    dv.monto_venta, " +
                     "    (dv.cantidad_vendida * dv.monto_venta) as total, " +
                     "    dv.tipo_venta, " +
                     "    dv.estado_venta " +
                     "FROM vista_historial_ventas_cliente vh " +
                     "INNER JOIN venta v ON vh.id_venta = v.id_venta " +
                     "INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta " +
                     "INNER JOIN producto p ON dv.producto_id = p.producto_id " +
                     "WHERE v.id_cliente = ? " +
                     "ORDER BY v.fecha DESC, v.id_venta DESC";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String item = String.format(
                    "Venta #%d | %s | %s | %d und | BS: %.2f | %s | %s",
                    rs.getInt("id_venta"),
                    rs.getString("fecha"),
                    rs.getString("nombre_producto"),
                    rs.getInt("cantidad_vendida"),
                    rs.getDouble("total"),
                    rs.getString("tipo_venta"),
                    rs.getString("estado_venta")
                );
                listModel.addElement(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            listModel.addElement("Error al cargar historial de ventas");
        }
        
        return listModel;
    }   
}
