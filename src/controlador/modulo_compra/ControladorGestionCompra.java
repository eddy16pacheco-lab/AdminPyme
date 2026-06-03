package controlador.modulo_compra;

import consultas_bd.consulta_detalle_orden_compra;
import controlador.controlador_padre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.detalle_orden_de_compra;
import modelo.orden_de_compra;
import vista.formularios.FormularioGestionCompras;
import vista.formularios.GestionCompras;

public class ControladorGestionCompra extends controlador_padre implements ActionListener,KeyListener,MouseListener{
    
    private detalle_orden_de_compra modelo;
    private GestionCompras vista;
private consulta_detalle_orden_compra sql=new consulta_detalle_orden_compra();
    public ControladorGestionCompra(detalle_orden_de_compra modelo, GestionCompras vista) {
        this.modelo = modelo;
        this.vista = vista;
        iniciar(vista, "Gestion de Compra","Modulo de compras","Cargando modulo de compras...");
        vista.exportarButton.setText("Registrar Compra");
        vista.exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vista.setIcon(true);
                    orden_de_compra compra = new orden_de_compra();
                    FormularioGestionCompras vista2 = new FormularioGestionCompras();
                    ControladorCompra ctrl = new ControladorCompra(vista2, compra);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(ControladorGestionCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        vista.limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           limpiar_todo();
            }
        });
        vista.buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           if(vista.fechaFinField.getDate() != null && vista.fechaInicioField !=null){
          sql.con_ord_com.orden_de_compra_consultar(vista.consultaTable, 2, vista.fechaInicioField.getDate(), vista.fechaFinField.getDate(),"");
           }
           else if(vista.fechaInicioField ==null){
               JOptionPane.showMessageDialog(null, "La fecha de inicio no puede estar vacio");
           }
           else if(vista.fechaFinField ==null){
               JOptionPane.showMessageDialog(null, "La fecha de culminacion no puede estar vacio");
           }
            }
        });
        vista.buscarProveedorField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
           if(!vista.buscarProveedorField.getText().isEmpty()){
           sql.con_ord_com.orden_de_compra_consultar(vista.consultaTable, 3, null,null, vista.buscarProveedorField.getText());
           }else{
           sql.con_ord_com.orden_de_compra_consultar(vista.consultaTable, 1,null,null,"");
           }
            }
        });
    }

    @Override
    protected void mostrar() {
    sql.con_ord_com.orden_de_compra_consultar(vista.consultaTable,1,null,null,"");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    protected void limpiar_todo() {
    vista.limpiarFiltros();
    mostrar();
    }
    
    
}
