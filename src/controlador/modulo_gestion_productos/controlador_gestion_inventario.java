package controlador.modulo_gestion_productos;

import consultas_bd.consulta_inventario;
import controlador.controlador_padre;
import controlador.modulo_compra.ControladorCompra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.inventario;
import modelo.orden_de_compra;
import modelo.producto;
import vista.formularios.FormularioGestionCompras;
import vista.gestion.GestionarInventario;

public class controlador_gestion_inventario extends controlador_padre{

    private inventario modelo_inventario;
    private producto modelo_producto;
    private GestionarInventario vista;
    private consulta_inventario sql = new consulta_inventario();

    public controlador_gestion_inventario(inventario modelo_inventario, producto modelo_producto, GestionarInventario vista) {  
        this.modelo_inventario = modelo_inventario;
        this.modelo_producto = modelo_producto;
        this.vista = vista;
        iniciar(vista, "Gestion inventario", "Modulo de inventario", "Cargando modulo...");
        
        vista.tipoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista.tipoCombo.getSelectedIndex() != 0) {
                    String filtro = vista.tipoCombo.getSelectedItem().toString();
                    sql.con_movi.select_consulta_movimiento_inventario(vista.movementsTable, 2, filtro, null, null);
                }else{
                sql.con_movi.select_consulta_movimiento_inventario(vista.movementsTable,1,null,null,null);
                }
            }
        });
        vista.movementsFilterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaIni=vista.desdeField.getDate();
                Date fechaFin=vista.hastaField.getDate();
                if(fechaIni != null && fechaFin !=null){
           sql.con_movi.select_consulta_movimiento_inventario(vista.movementsTable, 3, "", fechaIni, fechaFin);
                }else{
                sql.con_movi.select_consulta_movimiento_inventario(vista.movementsTable,1,null,null,null);
                }
            }
        });
        vista.menuStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarStockMinimoConInfo(modelo_inventario);
                mostrar();
            }
        });
        vista.productsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            
            }

            @Override
            public void mousePressed(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1){
            agregar_producto();
            }
                if(e.getButton()==MouseEvent.BUTTON3){
           vista.menu1.show(vista.productsTable, e.getX(), e.getY());
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
        });
        vista.stockCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           if(vista.stockCombo.getSelectedIndex()==1){
           sql.inventario_productos(vista.productsTable, 2, "", "");
           }else{
           sql.inventario_productos(vista.productsTable, 1,"", "");
           }
            }
        });
        vista.categoryCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String categoria=vista.categoryCombo.getSelectedItem().toString();
             if(vista.categoryCombo.getSelectedIndex()!=0){
           sql.inventario_productos(vista.productsTable, 4, "",categoria);
             }else{
           sql.inventario_productos(vista.productsTable, 1,"", "");  
             }
            }
        });
        vista.newProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vista.setIcon(true);
                    FormularioGestionCompras vista=new FormularioGestionCompras();
                      orden_de_compra compraModelo=new orden_de_compra();
                      ControladorCompra ctrl=new ControladorCompra(vista, compraModelo);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(controlador_gestion_inventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    protected void mostrar() {
        sql.inventario_productos(vista.productsTable, 1, "", "");
        sql.stock_bajo(vista.table_stock_bajo);
        sql.productos_mas_vendidos(vista.topProductsTabl);
        sql.con_movi.select_consulta_movimiento_inventario(vista.movementsTable,1,null,null,null);
        sql.con_producto.con_categoria.carcar_categorias(vista.categoryCombo);
//vista.movementsFilterBtn.setVisible(false);
     //   vista.newProductBtn.setVisible(true);
        vista.newProductBtn.setText("Agregar producto al inventario");
        
       
    }
    private void agregar_producto(){
    int fila=vista.productsTable.getSelectedRow();
        if (fila >= 0) {
            modelo_producto.setId_producto(Integer.parseInt(vista.productsTable.getValueAt(fila, 0).toString()));
            modelo_producto.setNombre_prodcuto(vista.productsTable.getValueAt(fila, 1).toString());
            modelo_producto.setCantidad(Integer.parseInt(vista.productsTable.getValueAt(fila, 5).toString()));
            modelo_inventario.setProducto(modelo_producto);
            modelo_inventario.setStock_min(Integer.parseInt(vista.productsTable.getValueAt(fila, 6).toString()));
        }
    }

    /**
     * Método alternativo que muestra el stock actual antes de actualizar
     */
    public void actualizarStockMinimoConInfo(inventario producto) {
     int stockActual=producto.getProducto().getCantidad();
     int stockMinimoActual=producto.getStock_min();
        String mensaje = String.format(
            "Producto: %s\n" +
            "Stock actual: %d\n" +
            "Stock mínimo actual: %d\n\n" +
            "Ingrese el nuevo valor para el stock mínimo:",
            producto.getProducto().getNombre_prodcuto(), producto.getProducto().getCantidad(), producto.getStock_min()
        );
        
        boolean entradaValida = false;
        
        while (!entradaValida) {
            String input = JOptionPane.showInputDialog(
                null,
                mensaje,
                "Actualizar Stock Mínimo",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                return;
            }
            
            if (input.matches("\\d+")) {
                int nuevoStockMinimo = Integer.parseInt(input);
                
                // Validación adicional: no puede ser mayor al stock actual (opcional)
                if (nuevoStockMinimo > stockActual) {
                    int confirmar = JOptionPane.showConfirmDialog(
                        null,
                        "El stock mínimo (" + nuevoStockMinimo + ") es mayor que el stock actual (" + stockActual + ").\n" +
                        "Esto podría generar alertas innecesarias.\n\n" +
                        "¿Desea continuar?",
                        "Confirmar Stock Mínimo Alto",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (confirmar != JOptionPane.YES_OPTION) {
                        continue;
                    }
                }
                
                boolean actualizado = sql.actualizarStockMinimoBD(modelo_producto.getId_producto(), nuevoStockMinimo);
                
                if (actualizado) {
                    JOptionPane.showMessageDialog(
                        null,
                        String.format(
                            "Stock mínimo actualizado exitosamente:\n" +
                            "Producto: %s\n" +
                            "Stock mínimo anterior: %d\n" +
                            "Stock mínimo nuevo: %d",
producto.getProducto().getNombre_prodcuto(), producto.getStock_min(), nuevoStockMinimo                        ),
                        "Actualización Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    entradaValida = true;
                }
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Por favor ingrese un número válido.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }   

}


