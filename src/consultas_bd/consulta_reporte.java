package consultas_bd;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class consulta_reporte extends consulta_pago{
    
    public void detalle_gasto_conslta(JTable tabla){
    modelo=(DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);
    tabla.setModel(modelo);
    query="SELECT * FROM descripicon_pago_consulta ORDER BY id_registro DESC"; 
    Object[] datos=new Object[4];
    try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (Exception e) {
        System.out.println("Error en la vista descripicon_pago_consulta: "+e);
        }
    }
}
