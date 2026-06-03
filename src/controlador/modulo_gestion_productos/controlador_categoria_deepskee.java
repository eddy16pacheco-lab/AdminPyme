package controlador.modulo_gestion_productos;

import consultas_bd.consulta_categoria;
import controlador.controlador_padre;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.categoria;
import modelo.usuario;
import validaciones.modulo_gestion_producto.validar_categoria;
import vista.gestion.GestionCategorias;
import vista.menu;

public class controlador_categoria_deepskee extends controlador_padre implements ActionListener, MouseListener, KeyListener {

    private categoria modelo;
    private GestionCategorias vista;
    private validar_categoria vl = new validar_categoria();
    private consulta_categoria sql = new consulta_categoria();

    public controlador_categoria_deepskee(categoria modelo, GestionCategorias vista) {
        this.modelo = modelo;
        this.vista = vista;
        //botones categoria
        this.vista.agregarCategoriaButton.addActionListener(this);
        this.vista.limpiarCategoriaButton.addActionListener(this);
        this.vista.editarCategoriaButton.addActionListener(this);
        this.vista.JMenuItemseleccionar.addActionListener(this);
        //botones productos
//campos
        this.vista.nombreCategoriaField.addMouseListener(this);
        this.vista.buscarCategpriraField.addKeyListener(this);
//tablas
        this.vista.categoriasTable.addMouseListener(this);
    }

// Modificar iniciar()
    @Override
    public void iniciar() {
        vista.setVisible(true);
        vista.JMenuItemActivar.setVisible(false);
        vista.JMenuItemInactivar.setVisible(false);
        mostrar();
    }

    @Override

    protected void mostrar() {
        sql.select_table_categoria(vista.categoriasTable,true,"");
        vista.editarCategoriaButton.setEnabled(true);
        vista.agregarCategoriaButton.setEnabled(true);
        //   vista.jLabel_id_usuario.setText(menu.jLabel_usu.getText());
    }

    public boolean obtener_datos() {
        if (vl.validar_campo(vista.nombreCategoriaField.getText())) {
            modelo.setTipo_categoria(vista.nombreCategoriaField.getText());
            modelo.setDescripcion(vista.descripcionCategoriaArea.getText());
            usuario administrador = new usuario();
            administrador.setId_persona(Integer.parseInt(vista.id_usuario.getText()));
            modelo.setAdministrador(administrador);
            return true;
        }
        return false;
    }

    protected void seleccionar() {
        int fila = vista.categoriasTable.getSelectedRow();
        if (fila >= 0) {
            modelo.setId_categoria(Integer.parseInt(vista.categoriasTable.getValueAt(fila, 0).toString()));
            vista.nombreCategoriaField.setText(vista.categoriasTable.getValueAt(fila, 1).toString());
            vista.descripcionCategoriaArea.setText(vista.categoriasTable.getValueAt(fila, 2).toString());
            vista.editarCategoriaButton.setEnabled(true);
            vista.agregarCategoriaButton.setEnabled(false);
            vista.editarProductoButton.requestFocus();
        }
    }

    @Override
    protected void limpiar_todo() {
        vista.nombreCategoriaField.setText("");
        vista.descripcionCategoriaArea.setText("");
        modelo = new categoria();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.agregarCategoriaButton) {
            if (obtener_datos()) {
                sql.insert_table_Categoria(modelo);
                limpiar_todo();
                mostrar();
            }
        }
        if (e.getSource() == vista.limpiarCategoriaButton) {
            limpiar_todo();
        }
        if (e.getSource() == vista.editarCategoriaButton) {
            int fila = vista.categoriasTable.getSelectedRow();
            if (fila >= 0) {
                if (obtener_datos()) {
                    if (sql.update_table_Categoria(modelo, modelo.getId_categoria())) {
                        mostrar();
                        limpiar_todo();
                        vista.agregarCategoriaButton.setEnabled(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Para poder modificar debe seleccionar una categoría");
                vista.editarCategoriaButton.setEnabled(false);
                vista.agregarCategoriaButton.setEnabled(true);
            }

        }
        if (e.getSource() == vista.JMenuItemseleccionar) {
            seleccionar();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == vista.categoriasTable) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                vista.opciones.show(vista.categoriasTable, e.getX(), e.getY());
            }
        }
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
    if(e.getSource()==vista.buscarCategpriraField){
    sql.select_table_categoria(vista.categoriasTable,false,vista.buscarCategpriraField.getText());
    }
    }

}
