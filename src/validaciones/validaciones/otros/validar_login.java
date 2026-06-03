package validaciones.validaciones.otros;

import consultas_bd.conexion_bd;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class validar_login {

    //objeto que tiene la conexion a la base de datos
    conexion_bd con = new conexion_bd();

    /*este metodo necesita llevar dos parametros 
    1)para el nombre de usuario
    2)para la contraseña de usuario*/
    public boolean inicio_de_sesion(String user_name, String user_password) {
        /*consulta para traer los datos donde el user_name sea igual al user_name del parametro*/
        String select = "SELECT user_name,user_password FROM usuario WHERE user_name='" + user_name + "'";
        Statement st;
        try {
            st = con.EstablecerConexion().createStatement();
            ResultSet rs = st.executeQuery(select);
            //en el caso rs.next() traiga una consulta sigmifica que el usuario exite
            if (rs.next()) {
                String usuario = rs.getString(1);
                String contraseña = rs.getString(2);
                /*condicion para saber si la contraseña es igual a la contraseña que pasaron a travez 
                del parametro user_password*/
                if (user_password.equals(contraseña)) {
                    //si el usuario es correcto y la contraseña es correcta devuelve true  
                    return true;

                } else {
                    //si el usuario es correcto y la contrasela es incorrecta devuelve false
                    JOptionPane.showMessageDialog(null, "Contraseña invalida");
                    return false;
                }
            } //en el caso rs.next() no traiga una consulta sigmifica que el usuario no exite
            else {
                JOptionPane.showMessageDialog(null, "Usuario invalido");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("" + e);
        }
        return false;
    }
}
