package consultas_bd;

import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.orden_de_compra;
import modelo.proveedor;
import modelo.usuario;

public class consulta_orden_compra extends sql_consulta{
     public consulta_provedor proveedor=new consulta_provedor();
    public void orden_de_compra_consultar(JTable tabla,int opc_con,Date fechaIni,Date fechaFin,String busqueda) {
    String[] Columname={"Codigo de Compra","Proveedor","Ubicacion","Telefono","Encargado","Fecha de Orden","Numero de Entregas"};
    DefaultTableModel  modelo = new  DefaultTableModel(Columname,0);
if(opc_con==1){
query = "SELECT * FROM vista_orden_de_compra;";
}
if(opc_con==2){
query = "SELECT * FROM vista_orden_de_compra vc WHERE vc.fecha_orden BETWEEN '"+new java.sql.Date(fechaIni.getTime())+"' AND '"+new java.sql.Date(fechaFin.getTime())+"';";
}
if(opc_con==3){
query = "SELECT * FROM vista_orden_de_compra vc WHERE vc.nombre LIKE '%"+busqueda+"%' OR vc.`Codigo de compra` LIKE '%"+busqueda+"%';";
}
if(opc_con==4){
query = "SELECT * FROM vista_orden_de_compra;";
}

        tabla.setModel(modelo);
        String[] datos = new String[7];
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
            JOptionPane.showMessageDialog(null, "A ocurrido un error en orden de compara consultar: " + e);
        }
    }
  
    public boolean insert_table_orden_compra(orden_de_compra orden_de_compra){
    query ="INSERT INTO orden_de_compra(codigo_orden_compra,id_provedor,fecha_orden) VALUES(?,?,?)";
        try {
            ps=con.EstablecerConexion().prepareCall(query);
            ps.setString(1, orden_de_compra.getCodigo_orden_de_compra());
            ps.setInt(2,orden_de_compra.getProveedor().getId_proveedor());
            ps.setDate(3,new java.sql.Date(orden_de_compra.getFecha_orden().getTime()));
            ps.execute();
            con.CerrarConexion();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar la compra: "+e);
        }
    return false;
    }

    public orden_de_compra cargar_orden_de_compra(String id_orden_de_compra) {
        orden_de_compra orden_de_compra = new orden_de_compra();
        usuario usuario = new usuario();
        proveedor proveedor = new proveedor();
        query = "SELECT * FROM orden_de_compra_vista  WHERE `Codigo de compra` = '"+ id_orden_de_compra+"'";        
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                orden_de_compra.setId_orden_de_compra(rs.getInt(1));
                proveedor.setNombre(rs.getString(2));
                proveedor.setDireccion(rs.getString(3));
                proveedor.setTelefono(rs.getString(4));
                usuario.setNombre_usuario(rs.getString(5));
//                orden_de_compra.setFecha(rs.getDate(6));
                orden_de_compra.setProveedor(proveedor);
                orden_de_compra.setUsuario(usuario); 
            }else{
            JOptionPane.showMessageDialog(null,"Compra no encontrada");
            }
            } catch (SQLException e) {
            System.out.println("Error al cargar el orden_de_compra " + id_orden_de_compra + ": " + e);
        }
        
        return orden_de_compra;
    }
    
    public int obtener_ultimo_id_compra(){
    int id=0;
    query="SELECT MAX(id_orden_de_compra) FROM orden_de_compra;";
    try {
          st=con.EstablecerConexion().createStatement();
          rs=st.executeQuery(query);
          if(rs.next()){
          id=rs.getInt(1);
          }
        } catch (SQLException e) {
        System.out.println("Error al cargar el ultimo id compra"+e);
        }
    return id;
    }
}
