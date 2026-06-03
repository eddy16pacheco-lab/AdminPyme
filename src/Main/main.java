package Main;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import controlador.controlador_menu;
import controlador.controlador_login;
import controlador.modulo_gestion_productos.controlador_categoria_deepskee;
import controlador.modulo_usuario.controlador_nomina;
import controlador.modulo_venta.controlador_gestion_ventas;
import javax.swing.UIManager;
import modelo.categoria;
import modelo.detalle_de_ventas;
import modelo.nomina;
import modelo.usuario;
import vista.gestion.GestionCategorias;
import vista.consultas.VistaContable;
import vista.login1;


public class main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 50);
        UIManager.put("Button.arc", 150);
      
        
        /* categoria modelo = new categoria();
        GestionCategorias vistaCategoria = new GestionCategorias();
        controlador_categoria_deepskee ctrl = new controlador_categoria_deepskee(modelo, vistaCategoria);
        ctrl.iniciar();*/

       
       usuario usuario=new usuario();
        login1 vista=new login1();
        controlador_login ctrl=new controlador_login(usuario, vista);
        ctrl.iniciar();
    }
}

