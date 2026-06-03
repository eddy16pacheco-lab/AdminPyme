package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.detalle_orden_de_compra;
import modelo.entrega;
import modelo.inventario;
import modelo.orden_de_compra;
import modelo.producto;

public class consulta_detalle_orden_compra extends sql_consulta {

    public consulta_producto con_pro = new consulta_producto();
    public consulta_orden_compra con_ord_com = new consulta_orden_compra();
public consulta_almacen coon_alm=new consulta_almacen();
 public void cargar_historial_producto(JTable tabla,int caso,String filtro,Date fechaIni,Date fechaFin){
 DefaultTableModel modelo=(DefaultTableModel)tabla.getModel();
 modelo.setRowCount(0);
 tabla.setModel(modelo);
     if(caso==1){
 query="SELECT * FROM vista_historial_producto";
 }
 if(caso==2){
 query="SELECT * FROM vista_historial_producto WHERE tipo_movimiento='"+filtro+"'";
 }
 if(caso==3){
     query="SELECT * FROM vista_historial_producto d WHERE d.`Datos del producto`='"+filtro+"'";
 }try {
         st=con.EstablecerConexion().createStatement();
         rs=st.executeQuery(query);
         Object[] datos=new Object[7];
         while(rs.next()){
             datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
             datos[3]=rs.getString(4);
             datos[4]=rs.getString(5);
             datos[5]=rs.getString(6);
             datos[6]=rs.getString(7);
             modelo.addRow(datos);
         }
         tabla.setModel(modelo);
     } catch (SQLException e) {
     System.out.println("Error al consultar historial del producto: "+e);
     }
 }
public void cargar_vista_inventario(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT CONCAT(p.codigo_barras,' ',p.nombre_producto,' ',p.descripcion) AS 'Producto',a.nombre,uf.pasillo,uf.estante,p.precio_compra,i.cantidad_dis,i.cantidad_min_stock FROM inventario i INNER JOIN producto p ON i.producto_id=p.producto_id INNER JOIN ubicaciones_fisicas uf ON i.ubicacion_id=uf.ubicacion_id INNER JOIN almacenes a ON uf.almacen_id=a.almacen_id";
        Object[] datos = new Object[7];
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
                datos[6] = rs.getString(7);
                modelo.addRow(datos);
            }
            
            tabla.setModel(modelo);
    } catch (SQLException e) {
        System.out.println("Error cargar inventario: "+e);
    }
}
public int total_disponibilidad_exitente(producto p) {
        query = "SELECT DISTINCT (dp.cantidad_empaquetada*dp.cantidad_entregada-i.cantidad) FROM detalle_orden_de_compra dp,producto p, inventario i WHERE  dp.producto_id=p.producto_id AND i.producto_id=p.producto_id AND i.producto_id='" + p.getId_producto() + "';";
        int cantidad = 0;
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
            con.CerrarConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar disponibilidad exitosa" + e);
        }
        return cantidad;
    }
   public producto buscar_producto_nombre_CodigoB(String codigoB) {
        producto producto = new producto();
        query = "SELECT * FROM producto p WHERE p.codigo_barras='"+codigoB+ "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                producto.setId_producto(rs.getInt(1));
                producto.setCodigo_barra(rs.getString(2));
                producto.setNombre_prodcuto(rs.getString(4));
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el total de disponibilidad: " + e);
        }
        return producto;
    }
    public producto buscar_producto_nombre(String nombre) {
        producto producto = new producto();
        query = "SELECT * FROM producto p WHERE p.nombre_producto='"+nombre+ "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                producto.setId_producto(rs.getInt(1));
                producto.setCodigo_barra(rs.getString(2));
                producto.setNombre_prodcuto(rs.getString(4));
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el total de disponibilidad: " + e);
        }
        return producto;
    }
public void obtener_forma_entrega(JComboBox combo){
    DefaultComboBoxModel modelo=new DefaultComboBoxModel();
    modelo.addElement("Seleccionar Forma de Entrega");
    combo.setModel(modelo);
    query = "SELECT * FROM entrega ORDER BY id_entrega ASC";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(2));
            }
            combo.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en a la hora de seleecinar la entrega: " + e);
        }
}
    public ArrayList<entrega> forma_entrega() {
        ArrayList<entrega> lista = new ArrayList<>();
        String query = "SELECT * FROM entrega ORDER BY id_entrega ASC";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                entrega entrega = new entrega();
                entrega.setForma_entrega(rs.getString(2));
                lista.add(entrega);
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en a la hora de seleecinar la entrega: " + e);
        }
        return lista;
    }

    public boolean insert_table_movimiento_inventario(ArrayList<detalle_orden_de_compra> dodc, int id_usuario) {
        query = "INSERT INTO movimiento_inventario(id_usuario,producto_id,precio_unitario,cantidad,tipo_movimiento,fecha_movimiento,tiempo_movimiento)"
                + "VALUES(? , ? , ?,?,'agregado',CURDATE(),CURTIME());";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            for (detalle_orden_de_compra object : dodc) {
                ps.setInt(1, id_usuario);
                ps.setInt(2, object.getProducto().getId_producto());
                ps.setDouble(3, object.getPrecio_unitario());
                ps.setInt(4, (object.getCantidad_empaquetada() * object.getCantidad_entregada()));
                ps.addBatch();
            }
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar en movimiento inventario: " + e.getMessage());
        }
        return false;
    }
public boolean insert_inventario(inventario inventario){
query="INSERT INTO inventario(producto_id,cantidad_dis,cantidad_min_stock,ubicacion_id) VALUES (? , ? , ? , ?)";
    try {
        ps=con.EstablecerConexion().prepareCall(query);
        ps.setInt(1, inventario.getProducto().getId_producto());
        ps.setInt(2, inventario.getCantidad());
        ps.setInt(3, inventario.getStock_min());
        ps.setInt(4, 2);
        ps.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al registrar en inventario "+e);
    }
    return false;
}
    public boolean insert_table_inventario(ArrayList<detalle_orden_de_compra> dodc, int id_usuario) {
        query = "INSERT INTO inventario(producto_id,id_usuario,cantidad,fecha_actualizacion,tiempo_actualizacion)"
                + "VALUES (? , ? , ? ,CURDATE() , CURTIME())";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            for (detalle_orden_de_compra com : dodc) {
                ps.setInt(1, com.getProducto().getId_producto());
                ps.setInt(2, id_usuario);
                ps.setInt(3, (com.getCantidad_empaquetada() * com.getCantidad_entregada()));
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar en inventario" + e.getMessage());
        }
        return false;
    }

    public boolean insert_table_detalle_orden_de_compra(ArrayList<detalle_orden_de_compra> detalle_orden_de_compra, orden_de_compra orden_compra) {
        if (con_ord_com.insert_table_orden_compra(orden_compra)) {
            query = "INSERT INTO detalle_orden_de_compra(id_orden_de_compra,id_entrega,producto_id,cantidad_entregada,precio_unitario,cantidad_empaquetada,almacen_id) VALUES(? , ? , ? , ? , ? , ? , ?);";
            orden_compra.setId_orden_de_compra(con_ord_com.obtener_ultimo_id_compra());
            try {
                cs = con.EstablecerConexion().prepareCall(query);
                for (detalle_orden_de_compra object : detalle_orden_de_compra) {
                    cs.setInt(1, con_ord_com.obtener_ultimo_id_compra());
                    cs.setInt(2, object.getEntrega().getId_entrega());
                    cs.setInt(3, object.getProducto().getId_producto());
                    cs.setInt(4, object.getCantidad_entregada());
                    cs.setInt(5, object.getCantidad_empaquetada());
                    cs.setDouble(6, object.getPrecio_unitario());
                    cs.setInt(7, object.getAlmacen().getId_almacen());
                    cs.addBatch();
                }
                cs.executeBatch();
                JOptionPane.showMessageDialog(null, "Compra registrada exitosamente");
                con.CerrarConexion();
                return true;
            } catch (SQLException e) {

                System.out.println("Error al registrar el orden de compra:" + e);
            }
        } else {
            return false;
        }
        return false;
    }

    public entrega obtener_forma_entrega(String forma_entrega) {
        entrega entrega = new entrega();
        query = "SELECT id_entrega,forma_entrega FROM entrega WHERE forma_entrega='" + forma_entrega + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                entrega.setId_entrega(rs.getInt(1));
                entrega.setForma_entrega(rs.getString(2));
            }
            con.CerrarConexion();
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return entrega;
    }

    public producto obtener_producto(int id) {
        producto producto = new producto();
        query = "SELECT * FROM producto WHERE producto_id='" + id + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                producto.setId_producto(rs.getInt(1));
                producto.setNombre_prodcuto(rs.getString(3) + " " + rs.getString(4));
                producto.setPrecio_unitario(rs.getDouble(5));
            }
        } catch (SQLException e) {
        }
        return producto;
    }
    
    public cargar_metrica cargar_datos_compra(){
    cargar_metrica cargar=new cargar_metrica();
 query="SELECT SUM((dodc.cantidad_entregada * dodc.cantidad_entregada) * p.precio_compra) AS 'Total de la compra el mes',COUNT(odc.id_orden_de_compra) AS 'Total de compras',AVG((dodc.cantidad_entregada * dodc.cantidad_entregada) * p.precio_compra) AS 'Promedio de ventas' FROM orden_de_compra odc INNER JOIN detalle_orden_de_compra dodc ON odc.id_orden_de_compra=dodc.id_orden_de_compra INNER JOIN producto p ON dodc.producto_id=p.producto_id WHERE MONTH(odc.fecha_orden)=MONTH(CURDATE())";
    try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next()){
            cargar.setTotal_compra(rs.getDouble(1));
            cargar.setNumero_de_compras(rs.getInt(2));
            cargar.setPromedio_compra(rs.getDouble(3));
            }
            
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"Error cargar datos de compra: "+e);
        }
    return cargar;
    }
            
            
            public class cargar_metrica{
    private double total_compra;
    private int numero_de_compras;
    private double promedio_compra;

        public double getTotal_compra() {
            return total_compra;
        }

        public void setTotal_compra(double total_compra) {
            this.total_compra = total_compra;
        }

        public int getNumero_de_compras() {
            return numero_de_compras;
        }

        public void setNumero_de_compras(int numero_de_compras) {
            this.numero_de_compras = numero_de_compras;
        }

        public double getPromedio_compra() {
            return promedio_compra;
        }

        public void setPromedio_compra(double promedio_compra) {
            this.promedio_compra = promedio_compra;
        }
    
    }
}
