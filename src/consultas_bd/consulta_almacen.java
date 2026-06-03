package consultas_bd;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import modelo.almacen;
import modelo.ubicacion_fisica;

public class consulta_almacen extends sql_consulta{
public ubicacion_fisica obtener_id_ubicacion_fisica(String pasillo,String estante,String id_almacen){
ubicacion_fisica ubicacion=new ubicacion_fisica();
    query="SELECT u.ubicacion_id FROM ubicaciones_fisicas u WHERE u.pasillo='"+pasillo+"' AND u.estante='"+estante+"' AND u.almacen_id='"+id_almacen+"';";
    try {
        st=con.EstablecerConexion().createStatement();
        rs=st.executeQuery(query);
        if(rs.next()){
        ubicacion.setUbicacion_fisica(rs.getInt(1));
        }
    } catch (SQLException e) {
    }
    return ubicacion;
}
    public almacen obtener_almacen(String nombre) {
        almacen almacen = new almacen();
        query = "SELECT * FROM almacenes WHERE nombre='" + nombre + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                almacen.setId_almacen(rs.getInt(1));
                almacen.setCodigo(rs.getString(2));
                almacen.setNombre(rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return almacen;
    }

    public void obtener_lista_almacen(JComboBox combo) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar un Almacen");
        combo.setModel(modelo);
        query = "SELECT * FROM almacenes";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo.addElement(rs.getString(3));
            }
            combo.setModel(modelo);
        } catch (SQLException e) {
            System.out.println("Error al consultar almacen" + e);
        }
    }
}
