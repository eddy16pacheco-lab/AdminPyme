package consultas_bd;

import java.awt.List;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.cliente;
import modelo.detalle_de_ventas;
import modelo.ventas;

public class consulta_detalle_venta extends sql_consulta {

    public consulta_descripcion_pago descripicom_pago = new consulta_descripcion_pago();

 
 
    public void insert_table_venta(ventas venta) {
        query = "INSERT INTO venta(id_usuario,id_cliente,fecha)VALUES(?,?,NOW())";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setInt(1, venta.getEmpleado().getId_persona());
            ps.setInt(2, venta.getCliente().getId_cliente());
            ps.execute();
            con.CerrarConexion();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public boolean buscar_cliente(cliente c) {
        cliente cliente = new cliente();
        query = "SELECT c.id_cliente,p.nombre,p.apellido,p.cedula FROM cliente c,persona p WHERE c.id_persona=p.id_persona AND p.cedula='" + c.getCedula() + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                c.setId_cliente(rs.getInt(1));
                con.CerrarConexion();
                return true;

            } else {
                JOptionPane.showMessageDialog(null, "El cliente no esta registrado");
                con.CerrarConexion();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: ");
        }
        return false;
    }

    public int obtener_ultima_venta() {
        int id_venta = 0;
        query = "SELECT MAX(id_venta) FROM venta;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                id_venta = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return id_venta;
    }
    
    public void consultar_listado_ventas_fecha(JTable table,boolean respuesta,Date fechaIni,Date fechaFin){
    modelo=(DefaultTableModel) table.getModel();
    modelo.setRowCount(0);
    table.setModel(modelo);
    query= respuesta ? "SELECT * FROM listado_de_ventas" : 
            "SELECT * FROM listado_de_ventas l WHERE l.`Venta de la fecha` BETWEEN '"+new java.sql.Date(fechaIni.getTime())+"' AND '"+new java.sql.Date(fechaFin.getTime())+"'"
            + "ORDER BY l.`Venta de la fecha` ASC ";
    String[] datos=new String[6];
    try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
        while (rs.next()) {
            datos[0] = rs.getString(1);
            datos[1] = rs.getString(2);
            datos[2] = rs.getString(3);
            datos[3] = rs.getString(4);
            datos[4] = rs.getString(5);
            datos[5] = rs.getString(6);
            modelo.addRow(datos);
            }
         table.setModel(modelo);
        } catch (Exception e) {
            System.out.println(""+e);
        }
    }
 
    public void consultar_listado_ventas(JTable table,String busqueda,int respuesta,String fechaIni,String fechaFin){
    String[] columns={     "N. de Venta", "Información del cliente", "Responsable", "Can. Productos Vendidos",
                        "Total de la venta", "Fecha de la venta"};
        DefaultTableModel modelo=new DefaultTableModel(columns,0);
        modelo.setRowCount(0);
        table.setModel(modelo);
        if(respuesta==1){
        query = "SELECT * FROM listado_de_ventas";
        }
        if (respuesta == 2) {
           query= "SELECT * FROM listado_de_ventas l WHERE l.`Informacion del cliente` LIKE'%" + busqueda + "%' OR l.`Numero de venta` LIKE'%" + busqueda + "%'"
                    + "AND `Venta de la fecha` BETWEEN '" + fechaIni + "' AND '" + fechaFin + "'";
        }
        if(respuesta==3){
           query= "SELECT * FROM listado_de_ventas l WHERE l.`Venta de la fecha` BETWEEN '" + fechaIni + "' AND '" + fechaFin + "'";
        }
        if(respuesta==4){
           query= "SELECT * FROM listado_de_ventas l WHERE l.`Informacion del cliente` LIKE'%" + busqueda + "%' OR l.`Numero de venta` LIKE'%" + busqueda + "%' AND l.`Venta de la fecha` BETWEEN '" + fechaIni + "' AND '" + fechaFin + "'";  
        }
        if(respuesta==5){
        query="SELECT * FROM listado_de_ventas l WHERE l.`Informacion del cliente` LIKE'%" + busqueda + "%' OR l.`Numero de venta` LIKE'%" + busqueda + "%'";
        }
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
            table.setModel(modelo);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public void buscar_listado_ventas(JTable table, detalle_de_ventas dv_busqueda) {
        modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0);
        table.setModel(modelo);

        // Construir la consulta dinámicamente según los criterios proporcionados
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM listado_de_ventas l WHERE 1=1");

        // Lista para almacenar los parámetros
        ArrayList<Object> parametros = new ArrayList<>();

        // Búsqueda por número de venta (si no está vacío)
        if (dv_busqueda.getN_venta() != null) {
            queryBuilder.append(" AND l.`Numero de venta` LIKE ?");
            parametros.add(dv_busqueda.getN_venta().getId_venta() + "%");
        }

        // Búsqueda por fecha (si no está vacía)
        if (dv_busqueda.getFecha_y_hora() != null && !dv_busqueda.getFecha_y_hora().isEmpty()) {
            queryBuilder.append(" AND l.`Venta realizada` = ?");
            parametros.add(dv_busqueda.getFecha_y_hora() + "%");
        }
    
    // Búsqueda por tipo de venta (si no está vacío)
    if (dv_busqueda.getTipo_venta() != null && !dv_busqueda.getTipo_venta().isEmpty()) {
        queryBuilder.append(" AND l.tipo_venta = ?");
        parametros.add(dv_busqueda.getTipo_venta());
    }
    
    String query = queryBuilder.toString();
    String[] datos = new String[12];
    
    try {
        // Usar PreparedStatement para evitar SQL injection
        ps = con.EstablecerConexion().prepareStatement(query);
        
        // Establecer los parámetros
        for (int i = 0; i < parametros.size(); i++) {
            ps.setObject(i + 1, parametros.get(i));
        }
        
        rs = ps.executeQuery();
        while (rs.next()) {            
            datos[0] = rs.getString(1);
            datos[1] = rs.getString(2);
            datos[2] = rs.getString(3);
            datos[3] = rs.getString(4);
            datos[4] = rs.getString(5);
            datos[5] = rs.getString(6);
            datos[6] = rs.getString(7);
            datos[7] = rs.getString(8);
            datos[8] = rs.getString(9);
            datos[9] = rs.getString(10);
            datos[10] = rs.getString(11);
            datos[11] = rs.getString(12);
                modelo.addRow(datos);
        }
        table.setModel(modelo);
        
        // Cerrar recursos
        rs.close();
        ps.close();
        
    } catch (Exception e) {
        System.out.println("Error en la búsqueda: " + e);
        // Considera usar un logger en lugar de System.out
    }
}
  
    public String[] obtenerVentasSemana() {
        String[] resultados = new String[4];
        
        // Inicializar con valores por defecto
        resultados[0] = "0";
        resultados[1] = "0";
        resultados[2] = "0.0";
        resultados[3] = "0.0";
        
       query = "SELECT " +
                     "COUNT(CASE WHEN dv.tipo_venta = 'Credito' THEN 1 END) AS ventas_credito, " +
                     "COUNT(CASE WHEN dv.tipo_venta = 'Contado' THEN 1 END) AS ventas_contado, " +
                     "COALESCE(SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END), 0) AS monto_credito, " +
                     "COALESCE(SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END), 0) AS monto_contado " +
                     "FROM venta v " +
                     "INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta " +
                     "WHERE YEARWEEK(NOW()) = YEARWEEK(NOW()) " +
                     "";
        
        try {
            st  = con.EstablecerConexion().createStatement(); 
            // Convertir LocalDate a java.sql.Date
          
            rs = st.executeQuery(query);
            
            if (rs.next()) {
                resultados[0] = String.valueOf(rs.getInt("ventas_credito"));
                resultados[1] = String.valueOf(rs.getInt("ventas_contado"));
                resultados[2] = String.format("%.2f", rs.getDouble("monto_credito"));
                resultados[3] = String.format("%.2f", rs.getDouble("monto_contado"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar ventas semanales: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultados;
    }
    /**
 * Método para obtener el detalle completo de una venta por su número
 * @param numeroVenta Número de venta a consultar
 * @return String con todos los detalles formateados
 */
    public String obtenerDetalleVentaPorNumero(String numeroVenta) {
        StringBuilder detalle = new StringBuilder();

        try {
            query = "SELECT * FROM listado_detalle_de_ventas WHERE `Numero de venta` = ?";
            ps = con.EstablecerConexion().prepareStatement(query);
            ps.setString(1, numeroVenta);
            rs = ps.executeQuery();

            // Encabezado
            detalle.append("=== DETALLE DE VENTA #").append(numeroVenta).append(" ===\n\n");

            boolean tieneDatos = false;
            double totalGeneral = 0;

            while (rs.next()) {
                tieneDatos = true;

                // Información del producto
                detalle.append("Producto: ").append(rs.getString("Producto")).append("\n");
                detalle.append("Código Producto: ").append(rs.getInt("Codigo de producto")).append("\n");
                detalle.append("Precio Venta: ").append(rs.getDouble("precio_venta")).append(" Bs\n");
                detalle.append("Cantidad: ").append(rs.getInt("Cantidad vendida")).append("\n");
                detalle.append("Subtotal: ").append(rs.getDouble("sub total")).append(" Bs\n");

                // Porcentaje (si aplica)
                String porcentaje = rs.getString("Porcentaje");
                if (porcentaje != null && !porcentaje.equals("0%")) {
                    detalle.append("Porcentaje: ").append(porcentaje).append("\n");
                }

                // Total de la venta para este producto
                double totalVenta = rs.getDouble("Total de la venta");
                detalle.append("Total Producto: ").append(totalVenta).append(" Bs\n");
                totalGeneral += totalVenta;

                // Información adicional
                detalle.append("Tipo Venta: ").append(rs.getString("tipo_venta")).append("\n");
                detalle.append("Estado: ").append(rs.getString("estado_venta")).append("\n");

                String fechaCobro = rs.getString("Fecha de cobro");
                if (fechaCobro != null && !fechaCobro.isEmpty()) {
                    detalle.append("Fecha Cobro: ").append(fechaCobro).append("\n");
                }

                detalle.append("Venta Realizada: ").append(rs.getString("Venta realizada")).append("\n");
                detalle.append("----------------------------------------\n");
            }

            if (tieneDatos) {
                detalle.append("\nTOTAL GENERAL: ").append(totalGeneral).append(" Bs\n");
            } else {
                detalle.append("No se encontraron detalles para la venta #").append(numeroVenta);
            }

            rs.close();
            ps.close();
            con.CerrarConexion();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al consultar la base de datos: " + e.getMessage();
        }

        return detalle.toString();
    }

    public consultar_detalle_venta_men detalle_venta() {
    consultar_detalle_venta_men det=new consultar_detalle_venta_men();
        query = "SELECT (SELECT COUNT(v.id_venta) FROM venta v WHERE MONTH(v.fecha) = MONTH(CURDATE())) AS 'Numero de ventas',SUM(dv.monto_venta),AVG(dv.monto_venta),v.fecha FROM  venta v INNER JOIN detalle_venta dv ON v.id_venta=dv.id_venta WHERE MONTH(v.fecha) = MONTH(CURDATE());";
        try {
          st=con.EstablecerConexion().createStatement();
          rs=st.executeQuery(query);
          while(rs.next()){
          det.setNumero_venta(rs.getInt(1));
          det.setMonto_venta(rs.getInt(2));
          det.setPromedio(rs.getDouble(3));
          }
        } catch (SQLException e) {
            System.out.println("Error al consultar_detalle_venta: "+e);
        }
        return det;
    }
  
    public class consultar_detalle_venta_men{
    private int numero_venta;
    private double monto_venta;
    private double promedio;

        public int getNumero_venta() {
            return numero_venta;
        }

        public void setNumero_venta(int numero_venta) {
            this.numero_venta = numero_venta;
        }

        public double getMonto_venta() {
            return monto_venta;
        }

        public void setMonto_venta(double monto_venta) {
            this.monto_venta = monto_venta;
        }

        public double getPromedio() {
            return promedio;
        }

        public void setPromedio(double promedio) {
            this.promedio = promedio;
        }
    
    }
  public boolean insertarDetalleVenta(ArrayList<detalle_de_ventas> lista_dv, ventas v){

        if ("Contado".equalsIgnoreCase(lista_dv.get(0).getTipo_venta())) {
            // Inserción para venta de contado (excluye fecha_cobro y porcentaje)
            query = "INSERT INTO detalle_venta (producto_id, id_venta, cantidad_vendida, monto_venta, tipo_venta, estado_venta) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
        } else if ("Credito".equalsIgnoreCase(lista_dv.get(0).getTipo_venta())) {
            // Inserción para venta de crédito (incluye todas las columnas)
            query = "INSERT INTO detalle_venta (producto_id, id_venta, cantidad_vendida, monto_venta, tipo_venta, estado_venta, fecha_cobro, porcentaje) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            throw new IllegalArgumentException("Tipo de venta no válido: " + lista_dv.get(0).getTipo_venta());
        }

        try {
            ps = con.EstablecerConexion().prepareStatement(query);
// Parámetros comunes
            for (detalle_de_ventas lv : lista_dv) {

                ps.setInt(1, lv.getProducto().getId_producto());
                ps.setInt(2, v.getId_venta());
                ps.setInt(3, lv.getCantidad_vendida());
                ps.setDouble(4, lv.getMonto_venta());
                ps.setString(5, lv.getTipo_venta());
                ps.setString(6, lv.getEstado_venta());

                // Parámetros específicos para crédito
                if ("Credito".equalsIgnoreCase(lista_dv.get(0).getTipo_venta())) {
                    ps.setDate(7, new java.sql.Date(lv.getFecha_coboro().getTime()));
                    ps.setDouble(8, lv.getPorcentaje());
                }
                ps.addBatch();

            }
            ps.executeBatch();
return true;
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error: "+e);
        }
        return false;
    }

}
