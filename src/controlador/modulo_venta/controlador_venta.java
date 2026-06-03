package controlador.modulo_venta;

import consultas_bd.consulta_detalle_venta;
import consultas_bd.consulta_venta;
import controlador.controlador_padre;
import controlador.modulo_venta.controlador_registro_cli;
import informe_reporte.GeneradorFacturaCreditoPDF;
import informe_reporte.GeneradorFacturaPDF;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.cliente;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import modelo.detalle_gasto;
import modelo.ventas;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.cliente;
import modelo.descripcion_pago;
import modelo.detalle_de_ventas;
import modelo.detalle_gasto;
import modelo.pago;
import modelo.producto;
import modelo.usuario;
import modelo.ventas;
import validaciones.modulo_venta.validar_venta;
import vista.formularios.FormularioVender;
import vista.formularios.FormularioRegCliNuevo;
import vista.gestion.VistaGestionVentas;
import vista.menu;

public class controlador_venta extends controlador_padre implements ActionListener, KeyListener, MouseListener {

    private ArrayList<detalle_de_ventas> lista_modelo = new ArrayList<>();
    private detalle_de_ventas modelo = new detalle_de_ventas();
    private ArrayList<producto> lista_producto = new ArrayList<>();
    private ArrayList<descripcion_pago> lista_pago = new ArrayList<>();
    private validar_venta validaciones = new validar_venta();
    private consulta_venta sql = new consulta_venta();
    private consulta_detalle_venta sql_venta = new consulta_detalle_venta();
    private FormularioVender vista;
    private double valor_dolar;
    private GeneradorFacturaPDF factura;
    
    // Variables para gestión de productos seleccionados
    private int filaSeleccionadaCompra = -1;
    private int filaSeleccionadaProductos = -1;

    public double getValor_dolar() {
        return valor_dolar;
    }

    public void setValor_dolar(double valor_dolar) {
        this.valor_dolar = valor_dolar;
    }

    public controlador_venta(FormularioVender vista,detalle_de_ventas modelo) {
        this.vista = vista;
        this.modelo=modelo;
        // Listeners existentes
        this.vista.jButton_registrar_venta.addActionListener(this);
        this.vista.jTextField_busqueda.addKeyListener(this);
        this.vista.jTable_lista_productos.addMouseListener(this);
        this.vista.jMenuItem_agregar.addActionListener(this);
        this.vista.jComboBox_forma_de_pago.addKeyListener(this);
        this.vista.jButton_cancelar.addActionListener(this);
        
        // Nuevos listeners para el popup menu de la tabla de compra
        this.vista.jMenuItem_eliminar.addActionListener(this);
        this.vista.jMenuItem_modificar_cantidad.addActionListener(this);
        
        // Listener para la tabla de compra
        this.vista.jTable_lista_de_compra.addMouseListener(this);
        
        // Configurar atajos de teclado
        configurarAtajosTeclado();
    }

    /**
     * Configura atajos de teclado para acciones frecuentes
     */
    private void configurarAtajosTeclado() {
        // Atajo para agregar pago (Ctrl+P)
        vista.jTextField_monto_pago.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "agregarPago");
        vista.jTextField_monto_pago.getActionMap().put("agregarPago", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar_pago();
            }
        });
        
        // Atajo para registrar venta (Ctrl+R)
        vista.jButton_registrar_venta.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "registrarVenta");
        vista.jButton_registrar_venta.getActionMap().put("registrarVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registrar_venta();
                } catch (Exception ex) {
                    Logger.getLogger(controlador_venta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // Atajo para limpiar todo (Ctrl+L)
        vista.jButton_cancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), "limpiarTodo");
        vista.jButton_cancelar.getActionMap().put("limpiarTodo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar_todo();
            }
        });
        
        // Atajo para buscar producto (Ctrl+B)
        vista.jTextField_busqueda.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK), "focusBusqueda");
        vista.jTextField_busqueda.getActionMap().put("focusBusqueda", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.jTextField_busqueda.requestFocus();
            }
        });
    }

    private void mostrarBarraProgreso() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Cargando Módulo de Ventas");
        dialog.setModal(false);
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Cargando, por favor espere...");
        progressBar.setStringPainted(true);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> progressBar.setString("Obteniendo tipo de cambio..."));
                String valorDolar = "" + validaciones.limpiar_valores(validaciones.limitar_decimales(validaciones.dolar.getDollarRate()));
                SwingUtilities.invokeLater(() -> vista.jLabel_valor_dolar.setText(valorDolar));
                setValor_dolar(Double.parseDouble(valorDolar));
                Thread.sleep(800);
                
                SwingUtilities.invokeLater(() -> progressBar.setString("Cargando métodos de pago..."));
                sql.pago.select_table_pago(vista.jComboBox_forma_de_pago);
                Thread.sleep(600);
                
                SwingUtilities.invokeLater(() -> progressBar.setString("Configurando usuario..."));
                SwingUtilities.invokeLater(() -> vista.jLabel_id_usuario.setText(menu.jLabel_usu.getText()));
                Thread.sleep(400);
                
                SwingUtilities.invokeLater(() -> progressBar.setString("Preparando interfaz..."));
                SwingUtilities.invokeLater(() -> vista.jTextField_busqueda.setText(""));
                Thread.sleep(300);
                
                Thread.sleep(300);
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    vista.setVisible(true);
                });
                
            } catch (Exception ex) {
                Logger.getLogger(controlador_venta.class.getName()).log(Level.SEVERE, "Error en carga", ex);
                SwingUtilities.invokeLater(() -> {
                    dialog.dispose();
                    vista.setVisible(true);
                });
            }
        }).start();
    }

    @Override
    public void iniciar() {
        vista.setVisible(false);
        mostrarBarraProgreso();
        mostrar();

        try {
            vista.jLabel_valor_dolar.setText("" + validaciones.limpiar_valores(validaciones.limitar_decimales(validaciones.dolar.getDollarRate())));
            setValor_dolar(Double.parseDouble(vista.jLabel_valor_dolar.getText()));
        } catch (Exception ex) {
            Logger.getLogger(controlador_venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void mostrar() {
        vista.jScrollPane_tabla_busqueda.setVisible(false);
        sql.pago.select_table_pago(vista.jComboBox_forma_de_pago);
        vista.jLabel_id_usuario.setText(menu.jLabel_usu.getText());
        vista.jTextField_busqueda.setText("");
        actualizarResumenVenta();
    }

    /**
     * Actualiza el resumen de la venta en la interfaz
     */
    private void actualizarResumenVenta() {
        int totalProductos = lista_producto.size();
        int totalItems = 0;
        double totalVentaBs = 0;
        
        for (producto p : lista_producto) {
            totalItems += p.getCantidad();
            totalVentaBs += (p.getCantidad() * p.getPrecio_venta());
        }
        
        vista.jLabel_monto_en_bolivares_mon_tot_ven.setText(validaciones.limitar_decimales(totalVentaBs));
vista.Jlabel_monto_dolares_total_de_ven.setText(validaciones.limitar_decimales(totalVentaBs/getValor_dolar()));
//     vista.Jlabel_monto_dolares_total_de_ven1.setText(validaciones.limitar_decimales(totalVentaBs / getValor_dolar()));
        
        calcular_cambio();
    }

    private boolean llenar_tabla_compra(producto p) {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_de_compra.getModel();
        String[] datos = new String[7];
        datos[0] = "" + p.getCodigo_barra();
        datos[1] = p.getNombre_prodcuto();
        datos[2] = p.getDescripcion();
        datos[3] = "" + p.getPrecio_venta();
        datos[4] = "" + p.getCantidad();
        datos[5] = "" + validaciones.limitar_decimales(p.getPrecio_venta() * p.getCantidad());
        
        if (validaciones.validar_tabla(vista.jTable_lista_de_compra, "" + p.getId_producto(), 0)) {
            JOptionPane.showMessageDialog(null, "Producto ya agregado");
            return false;
        } else {
            modelo.addRow(datos);
            vista.jTable_lista_de_compra.setModel(modelo);
            return true;
        }
    }

    /**
     * Método para eliminar producto de la lista de compra
     */
    private void eliminarProducto() {
        if (filaSeleccionadaCompra >= 0 && filaSeleccionadaCompra < lista_producto.size()) {
            int confirmacion = JOptionPane.showConfirmDialog(null, 
                "¿Está seguro de eliminar este producto de la venta?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Obtener ID del producto a eliminar
                int idProducto = Integer.parseInt(vista.jTable_lista_de_compra.getValueAt(filaSeleccionadaCompra, 0).toString());
                
                // Eliminar de las listas
                lista_producto.remove(filaSeleccionadaCompra);
                lista_modelo.remove(filaSeleccionadaCompra);
                
                // Eliminar de la tabla
                DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_de_compra.getModel();
                modelo.removeRow(filaSeleccionadaCompra);
                
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                actualizarResumenVenta();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar");
        }
    }

    /**
     * Método para modificar cantidad de producto
     */
    private void modificarCantidad() {
        if (filaSeleccionadaCompra >= 0 && filaSeleccionadaCompra < lista_producto.size()) {
            producto p = lista_producto.get(filaSeleccionadaCompra);
            detalle_de_ventas dv = lista_modelo.get(filaSeleccionadaCompra);
            
            Integer nuevaCantidad = validaciones.validar_numero("Digite la nueva cantidad para: " + p.getNombre_prodcuto());
            
            if (nuevaCantidad != null && nuevaCantidad > 0) {
                // Actualizar cantidad en el producto
                p.setCantidad(nuevaCantidad);
                
                // Actualizar detalle de venta
                dv.setCantidad_vendida(nuevaCantidad);
                dv.setMonto_venta(nuevaCantidad * p.getPrecio_venta());
                
                // Actualizar tabla
                DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_de_compra.getModel();
                modelo.setValueAt("" + nuevaCantidad, filaSeleccionadaCompra, 4);
                modelo.setValueAt("" + validaciones.limitar_decimales(nuevaCantidad * p.getPrecio_venta()), filaSeleccionadaCompra, 5);
                modelo.setValueAt("" + validaciones.limitar_decimales((nuevaCantidad * p.getPrecio_venta()) / getValor_dolar()), filaSeleccionadaCompra, 6);
                
                JOptionPane.showMessageDialog(null, "Cantidad actualizada correctamente");
                actualizarResumenVenta();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para modificar");
        }
    }

 private void agregar_pago() {
    descripcion_pago descripcion_pago = new descripcion_pago();
    pago pago = new pago();
    pago.setId_pago(vista.jComboBox_forma_de_pago.getSelectedIndex());
    
    if (!(pago.getId_pago() == 0)) {
        pago.setTipo_de_pago(vista.jComboBox_forma_de_pago.getSelectedItem().toString());
        
        if (pago.getId_pago() == 1) {
            String referencia = JOptionPane.showInputDialog(null, "Digite la referencia del pago");
            if (referencia == null || referencia.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La referencia del pago es obligatoria");
                return;
            }
            descripcion_pago.setRef_pago(referencia);
        }
        
        if (!vista.jTextField_monto_pago.getText().isEmpty()) {
            try {
                String montoTexto = vista.jTextField_monto_pago.getText().trim();
                
                // VALIDACIÓN DE LONGITUD DE CARACTERES
                // Permitir hasta 15 caracteres (incluyendo punto decimal)
                if (montoTexto.length() > 15) {
                    JOptionPane.showMessageDialog(null, 
                        "El monto de pago no puede exceder los 15 caracteres\n" +
                        "Caracteres ingresados: " + montoTexto.length(),
                        "Longitud Excedida",
                        JOptionPane.WARNING_MESSAGE);
                    vista.jTextField_monto_pago.requestFocus();
                    return;
                }
                
                // Validar que solo contiene números y un punto decimal
                if (!montoTexto.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    JOptionPane.showMessageDialog(null, 
                        "Formato de monto inválido.\n" +
                        "Debe contener solo números y hasta 2 decimales.\n" +
                        "Ejemplos válidos: 100, 100.5, 100.50",
                        "Formato Inválido",
                        JOptionPane.WARNING_MESSAGE);
                    vista.jTextField_monto_pago.requestFocus();
                    return;
                }
                
                double mont_pag = Double.parseDouble(montoTexto);
                
                if (mont_pag <= 0) {
                    JOptionPane.showMessageDialog(null, 
                        "El monto debe ser mayor a cero",
                        "Monto Inválido",
                        JOptionPane.WARNING_MESSAGE);
                    vista.jTextField_monto_pago.requestFocus();
                    return;
                }
                
                // Validar monto máximo (ajustar según tu sistema)
                double montoMaximo = 1000000.00; // 1 millón como ejemplo
                if (mont_pag > montoMaximo) {
                    JOptionPane.showMessageDialog(null, 
                        "El monto excede el límite permitido.\n" +
                        "Monto máximo: " + validaciones.limitar_decimales(montoMaximo),
                        "Monto Excedido",
                        JOptionPane.WARNING_MESSAGE);
                    vista.jTextField_monto_pago.requestFocus();
                    return;
                }
                
                if (pago.getId_pago() == 2) {
                    descripcion_pago.setMonto_cancelado(mont_pag * getValor_dolar());
                } else {
                    descripcion_pago.setMonto_cancelado(mont_pag);
                }
                
                descripcion_pago.setPago(pago);
                lista_pago.add(descripcion_pago);
                
                String mensajePago = crearMensajePago(descripcion_pago, mont_pag);
                JOptionPane.showMessageDialog(null, mensajePago, "PAGO AGREGADO EXITOSAMENTE", JOptionPane.INFORMATION_MESSAGE);
                
                vista.jTextField_monto_pago.setText("");
                vista.jTextField_monto_pago.requestFocus();
                vista.jComboBox_forma_de_pago.setSelectedIndex(0);
                calcular_cambio();
                
                double acumular = 0;
                for (descripcion_pago li_p : lista_pago) {
                    acumular += li_p.getMonto_cancelado();
                }
                vista.jLabel_monto_en_bolivares_mon_tot_pago.setText(validaciones.limitar_decimales(acumular));
                vista.Jlabel_monto_dolares_total_de_pago.setText(validaciones.limitar_decimales(acumular / getValor_dolar()));
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, 
                    "El monto de pago debe contener solamente caracteres numéricos\n" +
                    "Formato válido: 123.45 o 123",
                    "Error en Formato",
                    JOptionPane.ERROR_MESSAGE);
                vista.jTextField_monto_pago.requestFocus();
            }
                           
        } else {
            JOptionPane.showMessageDialog(null, 
                "Digite el monto del pago",
                "Campo Vacío",
                JOptionPane.WARNING_MESSAGE);
            vista.jTextField_monto_pago.requestFocus();
        }
    } else {
        JOptionPane.showMessageDialog(null, 
            "Seleccione una forma de pago",
            "Forma de Pago no Seleccionada",
            JOptionPane.WARNING_MESSAGE);
    }
}
  
 private String crearMensajePago(descripcion_pago descripcionPago, double montoOriginal) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("✅ PAGO AGREGADO EXITOSAMENTE\n");
        mensaje.append("══════════════════════════════\n\n");
        mensaje.append("📋 INFORMACIÓN DEL PAGO:\n");
        mensaje.append("────────────────────────────\n");
        mensaje.append("• Forma de pago: ").append(descripcionPago.getPago().getTipo_de_pago()).append("\n");
        
        if (descripcionPago.getPago().getId_pago() == 1 && descripcionPago.getRef_pago() != null) {
            mensaje.append("• Referencia: ").append(descripcionPago.getRef_pago()).append("\n");
        }
        
        mensaje.append("\n💰 MONTOS REGISTRADOS:\n");
        mensaje.append("────────────────────────────\n");
        
        if (descripcionPago.getPago().getId_pago() == 2) {
            mensaje.append("• Monto ingresado: $.").append(validaciones.limitar_decimales(montoOriginal)).append("\n");
            mensaje.append("• Tipo de cambio: Bs. ").append(validaciones.limitar_decimales(getValor_dolar())).append(" por $1\n");
            mensaje.append("• Monto convertido: Bs. ").append(validaciones.limitar_decimales(descripcionPago.getMonto_cancelado())).append("\n");
        } else {
            mensaje.append("• Monto registrado: Bs. ").append(validaciones.limitar_decimales(descripcionPago.getMonto_cancelado())).append("\n");
            if (descripcionPago.getPago().getId_pago() != 2) {
                mensaje.append("• Equivalente en $: $.").append(validaciones.limitar_decimales(descripcionPago.getMonto_cancelado() / getValor_dolar())).append("\n");
            }
        }
        
        double totalPagos = 0;
        for (descripcion_pago pago : lista_pago) {
            totalPagos += pago.getMonto_cancelado();
        }
        
        mensaje.append("\n📊 RESUMEN ACUMULADO:\n");
        mensaje.append("────────────────────────────\n");
        mensaje.append("• Total pagos registrados: ").append(lista_pago.size()).append("\n");
        mensaje.append("• Monto total en Bs: ").append(validaciones.limitar_decimales(totalPagos)).append("\n");
        mensaje.append("• Monto total en $: $.").append(validaciones.limitar_decimales(totalPagos / getValor_dolar())).append("\n");
        
        return mensaje.toString();
    }
  
    public void calcular_cambio() {
        double total_cuenta = 0;
        double total_pago = 0;
        Color color;

        for (producto li_p : lista_producto) {
            total_cuenta += (li_p.getCantidad() * li_p.getPrecio_venta());
            
        }
        vista.jLabel_monto_en_bolivares_mon_tot_ven.setText(total_cuenta+" Bs");
        for (descripcion_pago li_pa : lista_pago) {
            total_pago += li_pa.getMonto_cancelado();
        }

        if (total_pago > total_cuenta) {
            color = new Color(73, 165, 193);
            double cambio = total_pago - total_cuenta;
            vista.jLabel_monto_en_bolivares_mon_tot_cambio.setText(validaciones.limitar_decimales(cambio)+" Bs");
            vista.Jlabel_monto_dolares_total_de_cambio.setText(validaciones.limitar_decimales(cambio / getValor_dolar())+ "$");
            vista.jPanel_cambio.setBackground(color);
        } else if (total_pago < total_cuenta) {
            color = new Color(255, 92, 92);
            double falta = total_cuenta - total_pago;
            vista.jLabel_monto_en_bolivares_mon_tot_cambio.setText(validaciones.limitar_decimales(falta)+" Bs");
            vista.Jlabel_monto_dolares_total_de_cambio.setText("$ "+validaciones.limitar_decimales(falta / getValor_dolar())+ " $");
            vista.jPanel_cambio.setBackground(color);
        } else {
            color = new Color(92, 255, 108);
            vista.jLabel_monto_en_bolivares_mon_tot_cambio.setText("0.00 Bs");
            vista.Jlabel_monto_dolares_total_de_cambio.setText("0.00 $");
            vista.jPanel_cambio.setBackground(color);
        }
    }

    @Override
    protected void agregar() {
        if (filaSeleccionadaProductos >= 0) {
            producto p = new producto();
            p.setCodigo_barra(vista.jTable_lista_productos.getValueAt(filaSeleccionadaProductos, 0).toString());
            p.setNombre_prodcuto(vista.jTable_lista_productos.getValueAt(filaSeleccionadaProductos, 1).toString());
            p.setDescripcion(vista.jTable_lista_productos.getValueAt(filaSeleccionadaProductos, 2).toString());
            p.setCantidad(validaciones.validar_numero("Digite la cantidad"));
            p.setPrecio_venta(Double.parseDouble(vista.jTable_lista_productos.getValueAt(filaSeleccionadaProductos, 3).toString()));
            
            if (p.getCantidad() > 0) {
                lista_producto.add(p);
                detalle_de_ventas dv = new detalle_de_ventas();
                dv.setProducto(p);
                dv.setMonto_venta(p.getCantidad() * p.getPrecio_venta());
                dv.setCantidad_vendida(p.getCantidad());
                lista_modelo.add(dv);
                
                llenar_tabla_compra(p);
                vista.jTextField_monto_pago.requestFocus();
                actualizarResumenVenta();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la lista");
        }
    }

    @Override
    protected void limpiar_todo() {
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de limpiar toda la venta?\nSe perderán todos los productos y pagos agregados.", 
            "Confirmar Limpieza", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            lista_pago.clear();
            lista_producto.clear();
            lista_modelo.clear();
            
            DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_de_compra.getModel();
            modelo.setRowCount(0);
            
            DefaultTableModel modelo2 = (DefaultTableModel) vista.jTable_lista_productos.getModel();
            modelo2.setRowCount(0);
            
           vista.jScrollPane_tabla_busqueda.setVisible(false);

vista.Jlabel_monto_dolares_total_de_cambio.setText("0.00");
            vista.Jlabel_monto_dolares_total_de_pago.setText("0.00");
  //          vista.Jlabel_monto_dolares_total_de_ven1.setText("0.00");
            vista.jLabel_monto_en_bolivares_mon_tot_cambio.setText("0.00");
            vista.jLabel_monto_en_bolivares_mon_tot_pago.setText("0.00");
            vista.jLabel_monto_en_bolivares_mon_tot_ven.setText("0.00");
            vista.jTextField_monto_pago.setText("");
            vista.jTextField_busqueda.setText("");
            vista.jComboBox_forma_de_pago.setSelectedIndex(0);
            vista.jPanel_cambio.setBackground(new Color(187,187,187));
        }
    }

    public boolean registrar_venta() throws Exception {
        if (!lista_producto.isEmpty()) {
            ventas venta = new ventas();
            cliente cliente = new cliente();
            String[] opciones = {"Contado", "Credito"};
            String op = (String) JOptionPane.showInputDialog(null, "Seleccionar el tipo de venta", "MENSAJE DE OPCIONES", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            
            if (op == null) {
                return false; // Usuario canceló la operación
            }
            
            if (op.equals("Contado")) {
                if (validaciones.validar_pago_para_registrar_venta(lista_pago, lista_modelo)) {
                    modelo.setTipo_venta("Contado");
                    modelo.setEstado_venta("Aprobado");
                    for (detalle_de_ventas li : lista_modelo) {
                        li.setEstado_venta(modelo.getEstado_venta());
                        li.setTipo_venta("Contado");
                    }
                    
                    Integer cedula = validaciones.validar_numero("Digite la cédula del cliente");
                    if (cedula == null) {
                        JOptionPane.showMessageDialog(null, "Cédula no válida");
                        return false;
                    }

                    cliente.setCedula(cedula);
                    if (sql_venta.buscar_cliente(cliente)) {
                        venta.setCliente(cliente);
                        usuario empleado = new usuario();
                        empleado.setId_persona(Integer.parseInt(vista.jLabel_id_usuario.getText()));
                        venta.setEmpleado(empleado);
                        sql_venta.insert_table_venta(venta);
                        venta.setId_venta(sql_venta.obtener_ultima_venta());
                        sql_venta.descripicom_pago.insert_table_descripcion_pago(lista_pago, venta);      
                        if (sql_venta.insertarDetalleVenta(lista_modelo, venta)) {
                            
                           JOptionPane.showMessageDialog(null, 
                                "✅ VENTA REGISTRADA EXITOSAMENTE\n" +
                                "Número de venta: " + venta.getId_venta() + "\n" +
                                "Factura generada correctamente");
                            
                            limpiar_todo();
                            factura = new GeneradorFacturaPDF();
                            factura.setDolar(getValor_dolar());
                            factura.generarFacturaPDF(venta.getId_venta());
                            
                            return true;
                        }
                    } else {
                        cliente c = new cliente();
                       FormularioRegCliNuevo vista_cli = new FormularioRegCliNuevo();
                        controlador_registro_cli ctrl = new controlador_registro_cli(vista_cli, c);
                        ctrl.iniciar();
                        vista_cli.jTextField_cedula.setText("" + cliente.getCedula());
                        this.vista.jLabel_cedula1.setText("" + cliente.getCedula());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Para registrar la venta, el monto a pagar\ndebe ser mayor o igual que el total de la venta");
                    vista.jTextField_monto_pago.requestFocus();
                }
            } else {
                // Venta a crédito
                cliente.setCedula(validaciones.validar_numero("Digite la cédula del cliente"));
                if (sql_venta.buscar_cliente(cliente)) {
                    modelo.setFecha_coboro(validaciones.validar_fecha());
                    modelo.setPorcentaje(validaciones.validarNumeroDecimal("Digite cantidad del porcentaje"));
                    for (detalle_de_ventas li : lista_modelo) {
                        li.setEstado_venta("Pendiente");
                        li.setTipo_venta("Credito");
                        li.setFecha_coboro(modelo.getFecha_coboro());
                        li.setPorcentaje(modelo.getPorcentaje());
                    }
                    venta.setCliente(cliente);
                    usuario empleado = new usuario();
                    empleado.setId_persona(Integer.parseInt(vista.jLabel_id_usuario.getText()));
                    venta.setEmpleado(empleado);
                    sql_venta.insert_table_venta(venta);
                    venta.setId_venta(sql_venta.obtener_ultima_venta());
                    GeneradorFacturaCreditoPDF factura_credito = new GeneradorFacturaCreditoPDF();
                    if (sql_venta.insertarDetalleVenta(lista_modelo, venta)) {

                           JOptionPane.showMessageDialog(null, 
                                "✅ VENTA A CREDITO REGISTRADA EXITOSAMENTE\n" +
                                "Número de venta: " + venta.getId_venta() + "\n" +
                                "Factura generada correctamente");
                                                    factura_credito.setDolar(getValor_dolar());
                        factura_credito.generarFacturaCreditoPDF(venta.getId_venta());

                        limpiar_todo();
                    }
                } else {
                    cliente c = new cliente();
                    FormularioRegCliNuevo vista_cli = new FormularioRegCliNuevo();
                    controlador_registro_cli ctrl = new controlador_registro_cli(vista_cli, c);
                    ctrl.iniciar();
                    vista_cli.jTextField_cedula.setText("" + cliente.getCedula());
                    this.vista.jLabel_cedula1.setText("" + cliente.getCedula());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La lista de productos no puede estar vacía");
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton_registrar_venta) {
            try {
                registrar_venta();
            } catch (Exception ex) {
                Logger.getLogger(controlador_venta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == vista.jMenuItem_agregar) {
            agregar();
            calcular_cambio();
        } else if (e.getSource() == vista.jButton_cancelar) {
            limpiar_todo();
        } else if (e.getSource() == vista.jMenuItem_eliminar) {
            eliminarProducto();
        } else if (e.getSource() == vista.jMenuItem_modificar_cantidad) {
            modificarCantidad();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == vista.jComboBox_forma_de_pago) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                agregar_pago();
            }
        }
        
        // Atajo para eliminar producto (Supr)
        if (e.getSource() == vista.jTable_lista_de_compra && e.getKeyCode() == KeyEvent.VK_DELETE) {
            eliminarProducto();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.jTextField_busqueda) {
            if (!(vista.jTextField_busqueda.getText().isEmpty())) {
                if(sql.select_table_producto_busqueda(vista.jTable_lista_productos, vista.jTextField_busqueda.getText()))
                {
                vista.jScrollPane_tabla_busqueda.setVisible(true);
                }else{
                vista.jScrollPane_tabla_busqueda.setVisible(false);
                }
            }else{
                vista.jScrollPane_tabla_busqueda.setVisible(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.jTable_lista_de_compra) {
            filaSeleccionadaCompra = vista.jTable_lista_de_compra.getSelectedRow();
        } else if (e.getSource() == vista.jTable_lista_productos) {
            filaSeleccionadaProductos = vista.jTable_lista_productos.getSelectedRow();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == vista.jTable_lista_productos) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                filaSeleccionadaProductos = vista.jTable_lista_productos.getSelectedRow();
                if (filaSeleccionadaProductos >= 0) {
     //               vista.jPopupMenu1.show(vista.jTable_lista_productos, e.getX(), e.getY());
                }
            }
        } else if (e.getSource() == vista.jTable_lista_de_compra) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                filaSeleccionadaCompra = vista.jTable_lista_de_compra.getSelectedRow();
                if (filaSeleccionadaCompra >= 0) {
//                    vista.jPopupMenu2.show(vista.jTable_lista_de_compra, e.getX(), e.getY());
                }
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
    public void cerrar() {
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de cerrar el módulo de ventas?\nSe perderán los datos no guardados.", 
            "Confirmar Cierre", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            this.vista.dispose();
        }
    }
}