package controlador.modulo_usuario;

import consultas_bd.consulta_nomina;
import controlador.controlador_padre;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.nomina;
import modelo.usuario;
import vista.formularios.FormularioRegistroNomina;
import vista.gestion.GestionarNomina;
public class controlador_nomina extends controlador_padre implements ActionListener,MouseListener,KeyListener{

    private nomina modelo;
    private consulta_nomina  sql = new consulta_nomina();
    private GestionarNomina vista;

    public controlador_nomina(nomina modelo, GestionarNomina vista) {
        this.modelo = modelo;
        this.vista = vista;
   iniciar(vista,"Cargando Gestión de Nómina", "Cargando gestión de nómina...", "Gestion nomina");
        this.vista.jButton_crear_nomina.addActionListener(this);
        this.vista.jTable_lista_nomina.addMouseListener(this);
        this.vista.jComboBox_empleado.addActionListener(this);
        this.vista.jButton_buscar.addActionListener(this);
        this.vista.jButton_limpiar.addActionListener(this);
    }
    
    
// 
    @Override
    protected void mostrar() {
        vista.jComboBox_empleado.removeAllItems();
        vista.jComboBox_empleado.addItem("Todos los empleados");
        for (usuario cargar_empleado : sql.cargar_empleados()) {
            vista.jComboBox_empleado.addItem(cargar_empleado.getNombre() + " V-" + cargar_empleado.getCedula()); 
        }
       sql.mostrar_nomina(vista.jTable_lista_nomina,true,null,null);
     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton_crear_nomina) {
            this.vista.dispose();
            nomina nomina = new nomina();
            FormularioRegistroNomina vista_reg = new FormularioRegistroNomina();
            controlador_reg_nomina ctrl = new controlador_reg_nomina(nomina, vista_reg);
        }
        
        if(e.getSource()==vista.jComboBox_empleado){
            if(vista.jComboBox_empleado.getSelectedIndex()!=0){
          String busqueda=vista.jComboBox_empleado.getSelectedItem().toString();
        sql.buscar_nomina(vista.jTable_lista_nomina,busqueda);
            }else{
             sql.mostrar_nomina(vista.jTable_lista_nomina,true,null,null);
            }
        }
        
        if(e.getSource()==vista.jButton_buscar){
      if(vista.jDateChooser_fecha_fin.getDate() != null && vista.jDateChooser_fecha_inicio.getDate() != null){
          sql.mostrar_nomina(vista.jTable_lista_nomina, false, vista.jDateChooser_fecha_inicio.getDate(), vista.jDateChooser_fecha_fin.getDate());
      }else if(vista.jDateChooser_fecha_fin.getDate()==null){
JOptionPane.showMessageDialog(null, "La fecha final no puede estar vacia");
      }
else if(vista.jDateChooser_fecha_inicio.getDate()==null){
JOptionPane.showMessageDialog(null, "La fecha de inicio no puede estar vacia");
}else{
            JOptionPane.showMessageDialog(null, "Seleccionar Fechas");
        }
}
      
        if(e.getSource()==vista.jButton_limpiar){
        limpiar_todo();
        }
        
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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
 
    }

    @Override
    protected void limpiar_todo() {
    vista.jComboBox_empleado.setSelectedIndex(0);
    vista.jDateChooser_fecha_inicio.getDateEditor().setDate(null); 
    vista.jDateChooser_fecha_fin.getDateEditor().setDate(null);
    }
    
}
