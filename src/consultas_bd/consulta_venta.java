package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.cliente;
import modelo.detalle_de_ventas;
import modelo.parroquia;
import modelo.usuario;
import modelo.ventas;

public class consulta_venta extends sql_consulta {
public consulta_cliente con_cli=new consulta_cliente();
    public consulta_pago pago = new consulta_pago();

 /*   public void insert_table_ventas(ventas v){
 query="INSERT INTO venta(id_usuario,id_cliente,fecha) VALUES(?,?,NOW())";
    try {
        cs=con.EstablecerConexion().prepareCall(query);
        cs.setInt(1, v.getEmpleado().getId_persona());
        cs.setInt(2, v.getCliente().getId_cliente());
        cs.execute();
        con.CerrarConexion();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"A ocurrido un error inesperado en \n"
                + "consulta_venta.insert_table_ventas: "+e);
    }

}*/

    public void select_table_producto_ventas(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT ¨* FROM detalle_venta_lista_producto_venta";
        String datos[] = new String[4];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }

    public boolean select_table_producto_busqueda(JTable tabla, String bus) {
        modelo = (DefaultTableModel)tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM lista_productos_inventario p WHERE (p.nombre_producto LIKE'" + bus + "%' || p.codigo_barras LIKE'" + bus + "%') ORDER BY p.producto_id ASC;";
        String datos[] = new String[5];
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
                return true;
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
        return false;
    }

    public void select_venta_credito(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM venta_creditos";
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
        System.out.println(""+e);
    }
        }
 
    public int select_maximo_id_venta(){
    query="SELECT MAX(id_venta) FROM venta";
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
   
    public String seleccionar_fecha(String id_detalle_venta) {
        String fecha = null;
        query = "SELECT dv.fecha_cobro FROM detalle_venta dv WHERE dv.id_detalle_venta='" + id_detalle_venta + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                fecha = rs.getString(1);
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println("" + e);
        }
        return fecha;
        
    }
 
    public ventas seleccionar_ventas(){
    ventas venta=new ventas();
        query="SELECT * FROM venta_consulta c ORDER BY c.id_venta DESC ";
        try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next()){
                venta.setId_venta(rs.getInt(1));
                usuario usuario=new usuario();
                usuario.setNombre_usuario(rs.getString(2));
                venta.setEmpleado(usuario);
            }else{
            
            }
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    return venta;
    }
     
    public ventas seleccionar_ventas(String id_venta){
    ventas venta=new ventas();
        query="SELECT * FROM venta_consulta c ORDER BY c.id_venta DESC ";
        try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next()){
                venta.setId_venta(rs.getInt(1));
                usuario usuario=new usuario();
                usuario.setNombre_usuario(rs.getString(2));
                venta.setEmpleado(usuario);
            }else{
            
            }
        } catch (SQLException e) {
            System.out.println(""+e);
        }
    return venta;
    }

    public ArrayList<detalle_de_ventas> cargar_informacion_cliente(String id_venta) {
    ArrayList<detalle_de_ventas> lista_ventas=new ArrayList<>();
        query = "SELECT * FROM vista_consulta_cliente_ventas v WHERE v.id_venta='" + id_venta + "'";
        cliente cliente = new cliente();
        ventas ventas = new ventas();
        parroquia parroquia = new parroquia();
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                detalle_de_ventas detalle_venta = new detalle_de_ventas();
                ventas.setId_venta(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setCedula(rs.getInt(3));
                cliente.setUbicacion(parroquia);
                ventas.setCliente(cliente);
                ventas.setFecha_y_hora(rs.getString(6));
                detalle_venta.setN_venta(ventas);
                detalle_venta.setTipo_venta(rs.getString(7));
                detalle_venta.setEstado_venta(rs.getString(8));
                detalle_venta.setMonto_venta(rs.getDouble(9));
                lista_ventas.add(detalle_venta);
            }
    } catch (SQLException e) {
        System.out.println("Error al cargar informacion del cliente: "+e);
    }
return lista_ventas;
}
    
    /**
     * Método para actualizar el estado de una venta de "Pendiente" a "Aprobado"
     * @param idVenta El ID de la venta a actualizar
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarEstadoVentaAprobado(int idVenta) {
    boolean actualizado=false;
        query = "UPDATE detalle_venta SET estado_venta = 'Aprobado' WHERE id_venta = ? AND estado_venta = 'Pendiente'";
        
        try {
            // Obtener la conexión a la base de datos (ajusta según tu configuración)
            
            // Query para actualizar el estado
            
            ps = con.EstablecerConexion().prepareStatement(query);
            ps.setInt(1, idVenta);
            
            // Ejecutar la actualización
            int filasAfectadas = ps.executeUpdate();
            
            // Verificar si se actualizó al menos una fila
            if (filasAfectadas > 0) {
                actualizado = true;
                System.out.println("Numero de Venta: " + idVenta + " actualizada a estado 'Aprobado'");
            } else {
                System.out.println("No se pudo actualizar la venta. Verifique que exista y esté en estado 'Pendiente'");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la venta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (ps != null) ps.close();
                if (con != null) con.CerrarConexion();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return actualizado;
    }
    
    /**
     * Método sobrecargado para actualizar múltiples ventas
     * @param idsVentas Array con los IDs de las ventas a actualizar
     * @return Número de ventas actualizadas exitosamente
     */
    public int actualizarEstadosVentasAprobado(int[] idsVentas) {
        int ventasActualizadas = 0;
        
        for (int idVenta : idsVentas) {
            if (actualizarEstadoVentaAprobado(idVenta)) {
                ventasActualizadas++;
            }
        }
        
        System.out.println("Total de ventas actualizadas: " + ventasActualizadas + " de " + idsVentas.length);
        return ventasActualizadas;
    }
    // Agregar estos métodos en la clase consulta_venta

/**
 * Método para actualizar el estado de una venta
 */
public boolean actualizarEstadoVenta(int idVenta, String nuevoEstado) {
    boolean actualizado = false;
    query = "UPDATE detalle_venta SET estado_venta = ? WHERE id_venta = ?";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        ps.setString(1, nuevoEstado);
        ps.setInt(2, idVenta);
        
        int filasAfectadas = ps.executeUpdate();
        actualizado = filasAfectadas > 0;
        
        if (actualizado) {
            System.out.println("Estado de venta " + idVenta + " actualizado a: " + nuevoEstado);
        }
        
    } catch (SQLException e) {
        System.err.println("Error al actualizar estado de venta: " + e.getMessage());
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.CerrarConexion();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
    
    return actualizado;
}


}

