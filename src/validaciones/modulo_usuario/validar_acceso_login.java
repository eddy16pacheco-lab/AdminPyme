package validaciones.modulo_usuario;

import consultas_bd.consulta_login;
import consultas_bd.consulta_usuario;
import controlador.controlador_menu;
import javax.swing.JOptionPane;
import modelo.usuario;
import validaciones.validaciones.otros.validar_componente;
import vista.menu;
import vista.menu;

public class validar_acceso_login extends validar_componente {
  
    private consulta_usuario sql = new consulta_usuario();
//para verificar el acceso de los usuarios del sistema    
private controlador_menu ctrl;
    public boolean recupera_usuario(usuario usu) {
        usuario v_usu;
        v_usu = sql.seleccionar_usuario(usu.getNombre_usuario());
      if(v_usu.getEstado()!=null){
           if(v_usu.getEstado().equals("Activo")){
        if (usu.getNombre_usuario().equals(v_usu.getNombre_usuario())) {
            if (usu.getContraseña_usuario().equals(v_usu.getContraseña_usuario())) {
                if (v_usu.getRol().getId_rol() == 1) {
                    if (validar_estado(v_usu)) {
                        System.out.println("El usuario es un administrador:"
                                +v_usu.getId_persona());
                        ctrl = new controlador_menu();
                        ctrl.iniciar();
                        menu.jLabel_usu.setText("" + v_usu.getId_persona());
                        controlador_menu.inaviltar_opciones(true);

                    }
                }
                if (v_usu.getRol().getId_rol() == 3) {
                    ctrl = new controlador_menu();
                    ctrl.iniciar();
                    menu.jLabel_usu.setText("" + v_usu.getId_persona());
                    controlador_menu.inaviltar_opciones_supervisor();
                }
                if (v_usu.getRol().getId_rol() == 2) {
                    controlador_menu ctrl = new controlador_menu();
                    ctrl.iniciar();
                    menu.jLabel_usu.setText("" + v_usu.getId_persona());
                    controlador_menu.inaviltar_opciones(false);
                }
return true;
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña invalida");
                return false;
            }
        
        } else {
            JOptionPane.showMessageDialog(null, "usuario no existe");
            return false;

        }
        }         
        else{
        JOptionPane.showMessageDialog(null, "El usuario "+usu.getNombre_usuario()+" esta inactivo y no puede iniciar sesión");
           return false;          
        }
   
      }else{
      JOptionPane.showMessageDialog(null, "Usuario no registrado");
      }
    return false;
    }
 
    private boolean validar_estado(usuario usu) {
        if (usu.getEstado().equals("Activo")) {
        return true;
        }
        if (usu.getEstado().equals("Bloqueado")) {
            JOptionPane.showMessageDialog(null, "El usuario: " + usu.getNombre_usuario() + " esta bloqueado\n por lo tanto no puede iniciar sesion");
        return false;
        }
       return false;
}
}
