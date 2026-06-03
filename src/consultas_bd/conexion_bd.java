package consultas_bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexion_bd {

    private String ruta = System.getProperty("user.home");
    private String url = "jdbc:mysql://localhost:3306/db_sistema_adminpyme_final";
    private final String usuario = "root";
    private final String contraseña = "Eddy123.";
    private Connection con;

    public Connection EstablecerConexion() {

        try {
            con = DriverManager.getConnection(url, usuario, contraseña);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A OCURRIDO UN ERROR: " + e);
        }
        return con;
    }

    public void CerrarConexion() {
        try {
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A OCURRIDO UN ERROR: " + e);
        }

    }
}
