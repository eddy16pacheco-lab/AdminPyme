package consultas_bd;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

public  class sql_consulta {
    protected DefaultComboBoxModel C_modelo;
    protected DefaultTableModel modelo;
    protected DefaultListModel L_modelo;
    public conexion_bd con = new conexion_bd();
    public Statement st;
    public ResultSet rs;
//variable para insertar
    protected CallableStatement cs;
    protected PreparedStatement ps;
    public String query;
}
