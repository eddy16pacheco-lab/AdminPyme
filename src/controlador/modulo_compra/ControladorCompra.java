package controlador.modulo_compra;

import consultas_bd.consulta_detalle_orden_compra;
import controlador.controlador_padre;
import modelo.*;
import vista.formularios.FormularioGestionCompras;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import validaciones.validaciones.otros.validar_componente;
import vista.formularios.GestionCompras;
import vista.menu;

public class ControladorCompra extends controlador_padre {

    private FormularioGestionCompras vista;
    
    private orden_de_compra compraModelo;
    private ArrayList<detalle_orden_de_compra> detalle_com = new ArrayList<>();
    private consulta_detalle_orden_compra sql = new consulta_detalle_orden_compra();
    private int pro_id;
    private Double dolar = validar_componente.dolar.getDollarRate();
    
    // Tasa de cambio para cálculos
    private double tasaCambio = validar_componente.dolar.getDollarRate(); // Valor por defecto, se actualiza con getDollarRate()
    
    // Constantes para longitud máxima de campos
    private static final int MAX_CODIGO_COMPRA = 20;
    private static final int MAX_CANTIDAD = 6;
    private static final int MAX_PRECIO = 10;
    
    public ControladorCompra(FormularioGestionCompras vista, orden_de_compra compraModelo) {
        this.vista = vista;
        this.compraModelo = compraModelo;
        iniciar(vista, "Modulo de Compra");
        
        // Actualizar tasa de cambio con el valor real
        this.tasaCambio = dolar != null ? dolar : 36.50;
        
      
        vista.jButton_agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_detalle_compra();
                carcar_tabla(vista.jTable_detalle);
                actualizarTotalesGenerales();
            }
        });
        
        vista.jComboBox_producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista.jComboBox_producto.getSelectedIndex() != 0) {
                    String id_pro = validar_componente.obtenerPrimerGrupoNumerico(vista.jComboBox_producto.getSelectedItem().toString());
                    producto prod = new producto();
                    for (producto p : sql.con_pro.obtener_producto(false, id_pro)) {
                        prod = p;                        
                    }
                    vista.jTextField_precio.setText("" + prod.getPrecio_unitario());
                    pro_id = prod.getId_producto();
                    calcular_detalle(); // Recalcular al cambiar producto
                }
            }
        });

        vista.jButton_finalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registrar()) {
                    JOptionPane.showMessageDialog(null, "Compra registrada exitosamente");
                   vista.dispose();
                   
                }
            }
        });
        
        vista.jButton_limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiarFormulario();
                detalle_com.clear();
                carcar_tabla(vista.jTable_detalle);
                actualizarTotalesGenerales();
            }
        });
        
        vista.jTextField_cantidad_empaquetada.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Validar que solo se ingresen números
                char c = e.getKeyChar();
                
                // Permitir solo dígitos y teclas de control
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorCaracterInvalido("Cantidad empaquetada", "valores numericos");
                    return;
                }
                
                // Validar longitud máxima
                String text = vista.jTextField_cantidad_empaquetada.getText();
                if (text.length() >= MAX_CANTIDAD && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorLongitud("Cantidad empaquetada", MAX_CANTIDAD);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!vista.jTextField_cantidad_entregada.getText().isEmpty() && !vista.jTextField_cantidad_empaquetada.getText().isEmpty()){
                    vista.jLabel_multiplicacion.setText("Total de unidades: "+ calcular_cantidad(Integer.parseInt(vista.jTextField_cantidad_empaquetada.getText()), Integer.parseInt(vista.jTextField_cantidad_entregada.getText())));
                } else {
                    vista.jLabel_multiplicacion.setText("Total de unidades: 0");
                }
                calcular_detalle();
            }
        });
        
        vista.jTextField_cantidad_entregada.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Validar que solo se ingresen números
                char c = e.getKeyChar();
                
                // Permitir solo dígitos y teclas de control
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorCaracterInvalido("Cantidad entregada", "valores numericos");
                    return;
                }
                
                // Validar longitud máxima
                String text = vista.jTextField_cantidad_entregada.getText();
                if (text.length() >= MAX_CANTIDAD && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorLongitud("Cantidad entregada", MAX_CANTIDAD);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!vista.jTextField_cantidad_entregada.getText().isEmpty() && !vista.jTextField_cantidad_empaquetada.getText().isEmpty()){
                    int cant_emp = Integer.parseInt(vista.jTextField_cantidad_empaquetada.getText());
                    int cant_ent = Integer.parseInt(vista.jTextField_cantidad_entregada.getText());
                    vista.jLabel_multiplicacion.setText("Total de unidades: "+ calcular_cantidad(cant_emp, cant_ent));
                    calcular_detalle();
                } else {
                    vista.jLabel_multiplicacion.setText("Total de unidades: 0");
                }
            }
        });
        
        vista.jTextField_precio.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Validar que solo se ingresen números y punto decimal
                char c = e.getKeyChar();
                String text = vista.jTextField_precio.getText();
                
                // Permitir números, punto decimal y teclas de control
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorCaracterInvalido("Precio", "solo números y punto decimal");
                    return;
                }
                
                // Validar solo un punto decimal
                if (c == '.' && text.contains(".")) {
                    e.consume();
                    mostrarErrorCaracterInvalido("Precio", "solo un punto decimal permitido");
                    return;
                }
                
                // Validar que el punto decimal no sea el primer carácter
                if (c == '.' && text.isEmpty()) {
                    e.consume();
                    mostrarErrorCaracterInvalido("Precio", "el precio no puede empezar con punto decimal");
                    return;
                }
                
                // Validar longitud máxima
                if (text.length() >= MAX_PRECIO && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorLongitud("Precio", MAX_PRECIO);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                calcular_detalle();
            }
        });
        
        vista.jTextField_codigo_compra.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Validar longitud máxima para código de compra
                String text = vista.jTextField_codigo_compra.getText();
                char c = e.getKeyChar();
                
                if (text.length() >= MAX_CODIGO_COMPRA && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    mostrarErrorLongitud("Código de compra", MAX_CODIGO_COMPRA);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    // Método para setear la tasa de cambio
    public void setTasaCambio(double tasa) {
        this.tasaCambio = tasa;
        // Recalcular totales si hay datos
        if (!detalle_com.isEmpty()) {
            actualizarTotalesGenerales();
        }
    }
    
    public double getTasaCambio() {
        return tasaCambio;
    }
 
    private void mostrarErrorLongitud(String campo, int maxLongitud) {
        JOptionPane.showMessageDialog(vista,
            "¡Límite de caracteres alcanzado!\n" +
            "El campo '" + campo + "' no puede tener más de " + maxLongitud + " caracteres.",
            "Límite de Caracteres Excedido",
            JOptionPane.WARNING_MESSAGE);
    }
    
    private void mostrarErrorCaracterInvalido(String campo, String tipoPermitido) {
        JOptionPane.showMessageDialog(vista,
            "¡Carácter no permitido!\n" +
            "El campo " + campo + " solo acepta " + tipoPermitido + ".",
            "Carácter No Válido",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private boolean validarLongitudCampos() {
        // Validar código de compra
        if (vista.getCodigoCompra().length() > MAX_CODIGO_COMPRA) {
            vista.mostrarMensaje("El código de compra no puede tener más de " + 
                MAX_CODIGO_COMPRA + " caracteres", 
                "Error de longitud", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_codigo_compra.requestFocus();
            return false;
        }
        
        // Validar campos numéricos
        String cantEmp = vista.jTextField_cantidad_empaquetada.getText();
        String cantEnt = vista.jTextField_cantidad_entregada.getText();
        String precio = vista.jTextField_precio.getText();
        
        if (!cantEmp.isEmpty() && cantEmp.length() > MAX_CANTIDAD) {
            vista.mostrarMensaje("La cantidad empaquetada no puede tener más de " + 
                MAX_CANTIDAD + " dígitos", 
                "Error de longitud", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_cantidad_empaquetada.requestFocus();
            return false;
        }
        
        if (!cantEnt.isEmpty() && cantEnt.length() > MAX_CANTIDAD) {
            vista.mostrarMensaje("La cantidad entregada no puede tener más de " + 
                MAX_CANTIDAD + " dígitos", 
                "Error de longitud", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_cantidad_entregada.requestFocus();
            return false;
        }
        
        if (!precio.isEmpty() && precio.length() > MAX_PRECIO) {
            vista.mostrarMensaje("El precio no puede tener más de " + 
                MAX_PRECIO + " caracteres", 
                "Error de longitud", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_precio.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean validarCamposNumericos() {
        // Validar que los campos numéricos contengan solo números válidos
        try {
            // Validar cantidad empaquetada
            if (!vista.jTextField_cantidad_empaquetada.getText().isEmpty()) {
                int cantEmp = Integer.parseInt(vista.jTextField_cantidad_empaquetada.getText());
                if (cantEmp <= 0) {
                    vista.mostrarMensaje("La cantidad empaquetada debe ser mayor a cero", 
                        "Error en cantidad", JOptionPane.ERROR_MESSAGE);
                    vista.jTextField_cantidad_empaquetada.requestFocus();
                    return false;
                }
            }
            
            // Validar cantidad entregada
            if (!vista.jTextField_cantidad_entregada.getText().isEmpty()) {
                int cantEnt = Integer.parseInt(vista.jTextField_cantidad_entregada.getText());
                if (cantEnt <= 0) {
                    vista.mostrarMensaje("La cantidad entregada debe ser mayor a cero", 
                        "Error en cantidad", JOptionPane.ERROR_MESSAGE);
                    vista.jTextField_cantidad_entregada.requestFocus();
                    return false;
                }
            }
            
            // Validar precio
            if (!vista.jTextField_precio.getText().isEmpty()) {
                double precio = Double.parseDouble(vista.jTextField_precio.getText());
                if (precio <= 0) {
                    vista.mostrarMensaje("El precio debe ser mayor a cero", 
                        "Error en precio", JOptionPane.ERROR_MESSAGE);
                    vista.jTextField_precio.requestFocus();
                    return false;
                }
            }
            
            return true;
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Por favor, ingrese valores numéricos válidos en los campos correspondientes", 
                "Error en campos numéricos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean validarCaracteresEspeciales() {
        // Validar que no haya espacios en blanco en los campos numéricos
        String cantEmp = vista.jTextField_cantidad_empaquetada.getText();
        String cantEnt = vista.jTextField_cantidad_entregada.getText();
        String precio = vista.jTextField_precio.getText();
        
        if (cantEmp.contains(" ")) {
            vista.mostrarMensaje("La cantidad empaquetada no puede contener espacios en blanco", 
                "Espacios no permitidos", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_cantidad_empaquetada.requestFocus();
            return false;
        }
        
        if (cantEnt.contains(" ")) {
            vista.mostrarMensaje("La cantidad entregada no puede contener espacios en blanco", 
                "Espacios no permitidos", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_cantidad_entregada.requestFocus();
            return false;
        }
        
        if (precio.contains(" ")) {
            vista.mostrarMensaje("El precio no puede contener espacios en blanco", 
                "Espacios no permitidos", JOptionPane.ERROR_MESSAGE);
            vista.jTextField_precio.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private orden_de_compra crear_compra() {
        orden_de_compra orden_compra = new orden_de_compra();
        if (!vista.jTextField_codigo_compra.getText().isEmpty() && vista.jComboBox_proveedor.getSelectedIndex() != 0) {
            orden_compra.setCodigo_orden_de_compra(vista.getCodigoCompra());
            proveedor proveedor = new proveedor();
            usuario usuario = new usuario();
            proveedor.setId_proveedor(sql.con_ord_com.proveedor.buscar_proveedor(vista.getProveedorSeleccionado()));
            usuario.setId_persona(id_usuario);
            orden_compra.setProveedor(proveedor);
            orden_compra.setUsuario(usuario);
        }
        return orden_compra;
    }
    
    private void carcar_tabla(JTable tabla) {
        String[] columnNames = {"Producto", "Cant. Emp.", "Cant. Ent.", "Total Unid.", "Precio Unit.", "Sub Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);
        tabla.setModel(modelo);
        Object[] datos = new Object[6];
        for (detalle_orden_de_compra lista : detalle_com) {
            datos[0] ="C° "+lista.getProducto().getId_producto()+" "+lista.getProducto().getNombre_prodcuto()+" "+lista.getProducto().getDescripcion();
            datos[1] = lista.getCantidad_entregada();
            datos[2] = lista.getCantidad_empaquetada();
            datos[3] = calcular_cantidad(lista.getCantidad_empaquetada(), lista.getCantidad_entregada());
            datos[4] = lista.getProducto().getPrecio_unitario();
            datos[5] = validar_componente.limitar_decimales(calcular_cantidad(lista.getCantidad_empaquetada(), lista.getCantidad_entregada()) * lista.getProducto().getPrecio_unitario());
            modelo.addRow(datos);
        }
        tabla.setModel(modelo);
    }

    private boolean registrar() {
        // Validar longitudes antes de registrar
        if (!validarLongitudCampos()) {
            return false;
        }
        
        // Validar campos numéricos
        if (!validarCamposNumericos()) {
            return false;
        }
        
        // Validar caracteres especiales
        if (!validarCaracteresEspeciales()) {
            return false;
        }
        
        // Validar que haya detalles en la compra
        if (detalle_com.isEmpty()) {
            vista.mostrarMensaje("Debe agregar al menos un producto al detalle de la compra", 
                "Detalle vacío", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Mostrar resumen de la compra antes de registrar
        if (!mostrarResumenCompra()) {
            return false; // El usuario canceló
        }
        if(vista.jComboBox_unidad.getSelectedIndex()==0){
        return false;
        }
        orden_de_compra orden_compra = crear_compra();
        if (sql.insert_table_detalle_orden_de_compra(detalle_com, orden_compra)) {
            // Limpiar formulario después del registro exitoso
            vista.limpiarFormulario();
            detalle_com.clear();
            actualizarTotalesGenerales();
            return true;
        }
        return false;
    }
    
    // Método para mostrar resumen de la compra antes de registrar
    private boolean mostrarResumenCompra() {
        double totalBolivares = calcularTotalCompraBolivares();
        double totalDolares = totalBolivares / tasaCambio;
        
        int confirm = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de registrar la compra?\n\n" +
            "Resumen de la compra:\n" +
            "• Productos: " + detalle_com.size() + "\n" +
            "• Total en Bolívares: Bs " + String.format("%.2f", totalBolivares) + "\n" +
            "• Total en Dólares: $ " + String.format("%.2f", totalDolares) + "\n" +
            "• Tasa de cambio: Bs " + String.format("%.2f", tasaCambio) + " por $1\n\n" +
            "¿Desea continuar con el registro?",
            "Confirmar Registro de Compra",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        return confirm == JOptionPane.YES_OPTION;
    }
    
    // Método para calcular el total de la compra en bolívares
    private double calcularTotalCompraBolivares() {
        double total = 0.0;
        for (detalle_orden_de_compra detalle : detalle_com) {
            int totalUnidades = calcular_cantidad(detalle.getCantidad_empaquetada(), detalle.getCantidad_entregada());
            total += totalUnidades * detalle.getProducto().getPrecio_unitario();
        }
        return total;
    }
    
    // Método para actualizar los totales generales en la vista
    private void actualizarTotalesGenerales() {
        double totalBolivares = calcularTotalCompraBolivares();
        double totalDolares = totalBolivares / tasaCambio;
        
        // Actualizar los labels de total general
        vista.actualizarTotalCompra(totalBolivares);
        vista.actualizarTotalDolares(totalDolares);
    }
    
    private boolean crear_detalle_compra() {
        // Validar longitudes antes de agregar detalle
        if (!validarLongitudCampos()) {
            return false;
        }
        
        // Validar campos numéricos
        if (!validarCamposNumericos()) {
            return false;
        }
        
        // Validar caracteres especiales
        if (!validarCaracteresEspeciales()) {
            return false;
        }
        
        // Validar que se haya seleccionado un producto
        if (vista.jComboBox_producto.getSelectedIndex() == 0) {
            vista.mostrarMensaje("Debe seleccionar un producto", 
                "Producto no seleccionado", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que las cantidades sean mayores a cero
        if (vista.getCantidadEmpaquetada() <= 0 || vista.getCantidadEntregada() <= 0) {
            vista.mostrarMensaje("Las cantidades deben ser mayores a cero", 
                "Cantidades inválidas", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        detalle_orden_de_compra det_com = new detalle_orden_de_compra();
        producto pro = sql.obtener_producto(pro_id);
        entrega entrega = sql.obtener_forma_entrega(vista.getUnidadMedida());
        det_com.setProducto(pro);
        det_com.setEntrega(entrega);
        det_com.setCantidad_entregada(vista.getCantidadEntregada());
        det_com.setCantidad_empaquetada(vista.getCantidadEmpaquetada());
        calcular_detalle();
        detalle_com.add(det_com);
        
        // Limpiar campos del producto después de agregar
        vista.limpiarCamposProducto();
        
        vista.mostrarMensaje("Producto agregado correctamente al detalle de compra", 
            "Producto Agregado", JOptionPane.INFORMATION_MESSAGE);
        
        return true;
    }
    
    private void calcular_detalle() {
        if (!vista.jTextField_cantidad_empaquetada.getText().isEmpty() && 
            !vista.jTextField_cantidad_entregada.getText().isEmpty() && 
            !vista.jTextField_precio.getText().isEmpty()) {
            
            try {
                int total_unidad = calcular_cantidad(
                    Integer.parseInt(vista.jTextField_cantidad_empaquetada.getText()), 
                    Integer.parseInt(vista.jTextField_cantidad_entregada.getText())
                );
                double precio = Double.parseDouble(vista.jTextField_precio.getText());
                double totalBs = total_unidad * precio;
                double totalUsd = totalBs / tasaCambio;
                
                // Actualizar ambos campos de total
                vista.actualizarTotalProducto(totalBs, totalUsd);
                
            } catch (NumberFormatException e) {
                vista.actualizarTotalProducto(0.0, 0.0);
            }
        } else {
            vista.actualizarTotalProducto(0.0, 0.0);
        }
    }

    private int calcular_cantidad(int cant_emp, int can_ent) {
        return cant_emp * can_ent;
    }
    
    @Override
    protected void mostrar() {
        vista.jComboBox_proveedor.removeAllItems();
        vista.jComboBox_proveedor.addItem("Seleccionar Proveedor");
        for (proveedor object : sql.con_ord_com.proveedor.cargar_proveedores(true, "")) {
            vista.jComboBox_proveedor.addItem(object.getNombre());
        }
        
        vista.jComboBox_unidad.removeAllItems();
        vista.jComboBox_unidad.addItem("Seleccionar forma de entrega");
        for (entrega object : sql.forma_entrega()) {
            vista.jComboBox_unidad.addItem(object.getForma_entrega());
        }
        
        vista.jComboBox_producto.removeAllItems();
        vista.jComboBox_producto.addItem("Seleccionar Producto");
        for(producto producto: sql.con_pro.obtener_producto(true, "")){
            vista.jComboBox_producto.addItem("C° "+producto.getId_producto()+" "+producto.getNombre_prodcuto()+" "+producto.getDescripcion()+" Bs "+producto.getPrecio_unitario());
        }
        
        vista.id_usuario.setText(menu.jLabel_usu.getText());
       id_usuario=Integer.parseInt(vista.id_usuario.getText());
        System.out.println(""+id_usuario);
        // Inicializar totales en cero
        actualizarTotalesGenerales();
        vista.actualizarTotalProducto(0.0, 0.0);
    }
    private int id_usuario;
}