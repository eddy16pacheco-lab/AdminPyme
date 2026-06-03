package consultas_bd;

import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.gasto;

public class consulta_gastos extends sql_consulta {

    public consulta_usuario con_usu=new consulta_usuario();

    public boolean registrar_gasto(gasto gasto) {
        query = "INSERT INTO gastos(id_usuario,fecha_gasto,monto_gasto,motivo_del_gasto,categoria,comprobante) "
                + "VALUES(?,?,?,?,?,?)";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setInt(1, gasto.getUsuario().getId_persona());
            ps.setDate(2, new java.sql.Date(gasto.getFecha_gasto().getTime()));
            ps.setDouble(3, gasto.getMonto_gasto());
            ps.setString(4, gasto.getMotivo_del_gasto());
            ps.setString(5, gasto.getCategoria());
            ps.setString(6, gasto.getComprobante());
            ps.execute();
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            System.out.println("" + e);
        }
        return false;
    }

    public boolean modificar_gasto(gasto gasto) {
        query = "UPDATE gastos SET id_usuario=?,fecha_gasto=?,monto_gasto=?,motivo_del_gasto=?,categoria=?,comprobante=? WHERE id_gastos=? ";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, gasto.getUsuario().getId_persona());
            cs.setDate(2, new java.sql.Date(gasto.getFecha_gasto().getTime()));
            cs.setDouble(3, gasto.getMonto_gasto());
            cs.setString(4, gasto.getMotivo_del_gasto());
            cs.setString(5, gasto.getCategoria());
            cs.setString(6, gasto.getComprobante());
            cs.setInt(7, gasto.getId_gasto());
            cs.execute();
            con.CerrarConexion();
        } catch (SQLException e) {
        }
        return false;
    }

    public void mostrar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM gastos_consulta";
        Object[] datos = new Object[8];
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
                datos[7] = rs.getString(8);
                modelo.addRow(datos);
            }
            con.CerrarConexion();
            tabla.setModel(modelo);
        } catch (SQLException e) {
            System.out.println("" + e);
        }
    }
}
