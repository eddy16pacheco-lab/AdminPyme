package controlador;

import consultas_bd.consulta_detalle_orden_compra;
import consultas_bd.consulta_inventario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.almacen;
import modelo.categoria;
import modelo.detalle_orden_de_compra;
import modelo.entrega;
import modelo.inventario;
import modelo.orden_de_compra;
import modelo.producto;
import modelo.proveedor;
import modelo.ubicacion_fisica;
import validaciones.validaciones.otros.validar_componente;
import vista.VistaInventarioModerno;

public class controlador_inventario extends controlador_padre {

    private inventario modelo_inventario;
    private producto modelo_producto;
    private VistaInventarioModerno vista;
    private consulta_detalle_orden_compra sql = new consulta_detalle_orden_compra();
    private ArrayList<detalle_orden_de_compra> lista_detalle_orden_compra = new ArrayList<>();

    public controlador_inventario(producto modelo_producto, VistaInventarioModerno vista,
            inventario modelo_inventario) {
        this.modelo_producto = modelo_producto;
        this.modelo_inventario = modelo_inventario;
        this.vista = vista;
        iniciar(vista, "Gestion inventario", "Modulo de inventario", "Cargando modulo...");

        // Configurar formato de fecha en el JDateChooser
        configurarFormatoFechaChooser();

        this.vista.jButton_registrar_producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (obtener_datos_producto()) {
                    if (sql.con_pro.insert_table_Productos(modelo_producto)) {
                        mostrar();
                        limpiar_todo();
                    }
                }
            }
        });

        this.vista.jTable_lista_productos_registrado.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    vista.menu_pro.show(vista.jTable_lista_productos_registrado, e.getX(), e.getY());
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

        this.vista.editar_pro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarProductoDeTabla();
            }
        });

        this.vista.jButton_limpiar_producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar_todo();
            }
        });

        this.vista.jButton_actualizar_producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (obtener_datos_producto()) {
                    if (sql.con_pro.update_table_Producto(modelo_producto)) {
                        mostrar();
                        limpiar_todo();
                    }
                }
            }
        });
        this.vista.jComboBox_filtrar_categoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista.jComboBox_filtrar_categoria.getSelectedIndex() != 0) {
                    sql.con_pro.select_table_producto(vista.jTable_lista_productos_registrado, 3,
                            vista.jComboBox_filtrar_categoria.getSelectedItem().toString());
                } else {
                    mostrar();
                }
            }
        });
        this.vista.jButton_limpiar_filtros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar();
            }
        });
        this.vista.jTextField_buscar_producto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!vista.jTextField_buscar_producto.getText().isEmpty()) {
                    sql.con_pro.select_table_producto(vista.jTable_lista_productos_registrado, 2,
                            vista.jTextField_buscar_producto.getText());
                }
            }
        });
        this.vista.jButton_añadir_orc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orden_de_compra orden_compra = obtener_datos_compra();
                lista_detalle_orden_compra.add(obtener_detalle_orden_compra(orden_compra));
                añadir_tabla_compra(lista_detalle_orden_compra);
            }
        });
        this.vista.jButton_registrar_compra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orden_de_compra orden_compra = obtener_datos_compra();
                if (sql.insert_table_detalle_orden_de_compra(lista_detalle_orden_compra, orden_compra)) {
                    limpiar_compra();
                }
            }
        });

        vista.jButton_limpiar_compra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar_compra();
            }
        });

        this.vista.jButton_añadir_producto_inventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenr_datos_inventario();
            }
        });

        this.vista.jComboBox_filtrar_productos_movimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = vista.jComboBox_filtrar_productos_movimiento.getSelectedItem().toString();
                if (vista.jComboBox_filtrar_productos_movimiento.getSelectedIndex() != 0) {
                    sql.cargar_historial_producto(vista.jTable_movimiento, 2, filtro, null, null);
                }else{
sql.cargar_historial_producto(vista.jTable_movimiento, 1, "", null, null);

                }
        
            }
        });
        this.vista.jComboBox_filtrar_movimiento_por_producto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         if(vista.jComboBox_filtrar_movimiento_por_producto.getSelectedIndex()!=0){
String producto=vista.jComboBox_filtrar_movimiento_por_producto.getSelectedItem().toString();
sql.cargar_historial_producto(vista.jTable_movimiento, 3, producto, null, null);
         }else{
         }
sql.cargar_historial_producto(vista.jTable_movimiento, 1, "", null, null);
            }
        });

        // Inicializar el controlador de reportes
        new ControladorReportes(vista);
    }

    private void limpiar_compra() {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_producto_compra.getModel();
        modelo.setRowCount(0);
        lista_detalle_orden_compra.clear();
        vista.jTextField_codigo_compra.setText("");
        vista.jComboBox_forma_de_entrega.setSelectedIndex(0);
        vista.jComboBox_Producto.setSelectedIndex(0);
        vista.jComboBox_proveedor.setSelectedIndex(0);
        vista.jTextField_cantidad_entrega.setText("");
        vista.jTextField_cantidad_forma_entega.setText("");
        vista.jTextField_precio_unitario.setText("");
        vista.jDateChooser_fecha_orden_compra.getDateEditor().setDate(null);
        vista.jLabel_total_compra.setText("Total: Bs0.00");
    }

    private void limpiar_inventario(){
 vista.jComboBox_producto_inventario.setSelectedIndex(0);
vista.jComboBox_almacen_inventario.setSelectedIndex(0);
vista.jComboBox_almacen_inventario.setSelectedIndex(0);
vista.jTextField_cantidad_entrega.setText("");
vista.jTextField_stock_minimo.setText("");
}
    private void añadir_tabla_compra(ArrayList<detalle_orden_de_compra> lista_orden) {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_producto_compra.getModel();
        modelo.setRowCount(0);
        vista.jTable_lista_producto_compra.setModel(modelo);
        Object[] datos = new Object[7];
        double total_compra = 0;
        for (detalle_orden_de_compra dodc : lista_orden) {
            total_compra += dodc.getCantidad_empaquetada() * dodc.getCantidad_entregada() * dodc.getPrecio_unitario();
            datos[0] = dodc.getProducto().getNombre_prodcuto() + " " + dodc.getProducto().getCodigo_barra();
            datos[1] = dodc.getEntrega().getForma_entrega();
            datos[2] = dodc.getCantidad_empaquetada();
            datos[3] = dodc.getCantidad_entregada();
            datos[4] = dodc.getCantidad_empaquetada() * dodc.getCantidad_entregada();
            datos[5] = dodc.getPrecio_unitario();
            datos[6] = validar_componente.limitar_decimales(
                    dodc.getCantidad_empaquetada() * dodc.getCantidad_entregada() * dodc.getPrecio_unitario());
            modelo.addRow(datos);
        }
        vista.jLabel_total_compra.setText("Total: Bs" + limitar_decimales(total_compra));
        vista.jTable_lista_producto_compra.setModel(modelo);
    }

    @Override
    protected void mostrar() {
        sql.con_pro.con_categoria.carcar_categorias(vista.jComboBox_categoria);
        sql.con_pro.select_table_producto(vista.jTable_lista_productos_registrado, 1, "");
        sql.con_pro.con_categoria.carcar_categorias(vista.jComboBox_filtrar_categoria);
        sql.con_ord_com.proveedor.cargar_proveedor(vista.jComboBox_proveedor);
        sql.con_pro.obtener_producto(vista.jComboBox_Producto);
        sql.obtener_forma_entrega(vista.jComboBox_forma_de_entrega);
        sql.coon_alm.obtener_lista_almacen(vista.jComboBox_almacen_destinado);

        // Items adicionales unificados
        sql.cargar_vista_inventario(vista.jTable_lista_productos_inventario);
        sql.con_pro.obtener_producto_codigo(vista.jComboBox_producto_inventario);
        sql.coon_alm.obtener_lista_almacen(vista.jComboBox_almacen_inventario);
        sql.cargar_historial_producto(vista.jTable_movimiento, 1, "", null, null);
        sql.con_pro.obtener_producto_codigo(vista.jComboBox_filtrar_movimiento_por_producto);

        // Procesar fechas para cambiar el formato
        procesarFechasEnTabla();

        // Configurar el renderizador de fecha
        configurarRenderizadorFechaTabla();
    }

    // Método agregado de la unificación
    private boolean obtenr_datos_inventario() {
        String nombre_producto = vista.jComboBox_producto_inventario.getSelectedItem().toString();
        String[] nombre = nombre_producto.split(" ");
        producto id_producto = sql.buscar_producto_nombre_CodigoB(nombre[0]);
        int id_almacen = sql.coon_alm.obtener_almacen(vista.jComboBox_almacen_inventario.getSelectedItem().toString())
                .getId_almacen();
        String pasillo = vista.jComboBox_pasillo.getSelectedItem().toString();
        String estante = vista.jComboBox_estante_inven.getSelectedItem().toString();
        ubicacion_fisica id_ubicacion = sql.coon_alm.obtener_id_ubicacion_fisica(pasillo, estante, "" + id_almacen);
        // System.out.println(""+id_ubicacion.getUbicacion_fisica());
        String cantidad = vista.jTextField_cantidad_inventario.getText();
        String stock_min = vista.jTextField_stock_minimo.getText();
        modelo_inventario.setProducto(id_producto);
        modelo_inventario.setUbicacion_fisica(id_ubicacion);
        int cantidad_ = 0;
        int stock_min_ = 0;
        try {
            cantidad_ = Integer.parseInt(cantidad);
            stock_min_ = Integer.parseInt(stock_min);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese formato de numero validos");
        }
        modelo_inventario.setCantidad(cantidad_);
        modelo_inventario.setStock_min(stock_min_);
        if (sql.insert_inventario(modelo_inventario)) {
            JOptionPane.showMessageDialog(null, "Producto Agregado Exitosamente Al Inventario");
    limpiar_inventario();
        }
        return false;
    }

    /**
     * Procesar fechas en la tabla después de cargar datos
     */
    private void procesarFechasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_productos_registrado.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object valorFecha = modelo.getValueAt(i, 8);
            if (valorFecha != null && !valorFecha.toString().isEmpty()) {
                try {
                    // Intentar parsear la fecha del formato SQL
                    Date fecha = parsearFechaDesdeString(valorFecha.toString());
                    if (fecha != null) {
                        // Formatear al nuevo formato
                        String fechaFormateada = formatearFecha(fecha);
                        modelo.setValueAt(fechaFormateada, i, 8);
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar fecha en fila " + i + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Configurar el renderizador de celdas para la columna de fecha en la tabla
     */
    private void configurarRenderizadorFechaTabla() {
        if (vista.jTable_lista_productos_registrado != null
                && vista.jTable_lista_productos_registrado.getColumnCount() > 8) {
            // Crear un renderizador personalizado para la columna de fecha
            javax.swing.table.DefaultTableCellRenderer fechaRenderer = new javax.swing.table.DefaultTableCellRenderer() {
                private SimpleDateFormat sdf = new SimpleDateFormat("d MMM. yyyy", new Locale("es", "ES"));

                @Override
                public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                            column);

                    if (value instanceof Date) {
                        // Formatear fecha
                        setText(sdf.format((Date) value));
                        setHorizontalAlignment(javax.swing.JLabel.CENTER);
                    } else if (value != null) {
                        // Intentar parsear si es String
                        try {
                            Date fecha = parsearFechaDesdeString(value.toString());
                            setText(sdf.format(fecha));
                            setHorizontalAlignment(javax.swing.JLabel.CENTER);
                        } catch (Exception e) {
                            setText(value.toString());
                        }
                    } else {
                        setText("");
                    }

                    return c;
                }
            };

            // Aplicar el renderizador a la columna de fecha (columna 8)
            vista.jTable_lista_productos_registrado.getColumnModel().getColumn(8).setCellRenderer(fechaRenderer);
        }
    }

    /**
     * Método para parsear diferentes formatos de fecha
     */
    private Date parsearFechaDesdeString(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return null;
        }

        List<String> formatos = Arrays.asList(
                "yyyy-MM-dd", // Formato original: 2025-12-05
                "dd/MM/yyyy", // Formato alternativo: 05/12/2025
                "MM/dd/yyyy", // Formato americano: 12/05/2025
                "dd MMM. yyyy", // Formato deseado: 5 dic. 2025
                "dd MMM yyyy", // Formato sin punto: 5 dic 2025
                "d MMM. yyyy", // Formato con día sin cero: 5 dic. 2025
                "d MMM yyyy" // Formato con día sin cero y sin punto
        );

        for (String formato : formatos) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formato, new Locale("es", "ES"));
                sdf.setLenient(false);
                return sdf.parse(fechaStr);
            } catch (ParseException e) {
                // Continuar con el siguiente formato
            }
        }

        // Si ningún formato funciona, intentar parsear de forma flexible
        try {
            // Intentar detectar el formato automáticamente
            String[] partes = fechaStr.split("[-./\\s]+");
            if (partes.length == 3) {
                // Intentar determinar el formato basado en las partes
                String fechaNormalizada = partes[0] + "-" + partes[1] + "-" + partes[2];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(fechaNormalizada);
            }
        } catch (Exception e) {
            // Si falla, lanzar excepción
        }

        return null;
    }

    /**
     * Método para formatear fecha al formato "5 dic. 2025"
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat("d MMM. yyyy", new Locale("es", "ES"));
        return sdf.format(fecha);
    }

    /**
     * Método para formatear fecha al formato SQL para la base de datos
     */
    private String formatearFechaParaSQL(Date fecha) {
        if (fecha == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    /**
     * Método para formatear fecha para mostrar en la tabla
     */
    public String formatearFechaParaTabla(Date fecha) {
        return formatearFecha(fecha);
    }

    /**
     * Método para configurar el formato de fecha en el JDateChooser
     */
    private void configurarFormatoFechaChooser() {
        if (vista.jDateChooser_fecha_vencimiento != null) {
            // Establecer el formato deseado
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM. yyyy", new Locale("es", "ES"));

            // Configurar el formato del JDateChooser
            vista.jDateChooser_fecha_vencimiento.setDateFormatString("d MMM. yyyy");

            // Configurar el locale para español
            vista.jDateChooser_fecha_vencimiento.setLocale(new Locale("es", "ES"));
        }
    }

    private boolean obtener_datos_producto() {
        try {
            // 1. Obtener y validar datos básicos
            String codigo_barra = vista.jTextField_codigo_barra.getText().trim();
            if (codigo_barra.isEmpty()) {
                mostrarError("El código de barras es obligatorio");
                return false;
            }

            String nombre = vista.jTextField_nombre.getText().trim();
            if (nombre.isEmpty()) {
                mostrarError("El nombre es obligatorio");
                return false;
            }

            // 2. Validar y convertir precios
            double precio_compra = 0;
            double precio_venta = 0;
            double capacidad_medida = 0;

            try {
                precio_compra = Double.parseDouble(vista.jTextField_precio_compra.getText().trim());
                precio_venta = Double.parseDouble(vista.jTextField_precio_venta.getText().trim());
                capacidad_medida = Double.parseDouble(vista.jTextField_valor_unidad.getText().trim());

                if (precio_compra <= 0 || precio_venta <= 0 || capacidad_medida <= 0) {
                    mostrarError("Los valores numéricos deben ser mayores a 0");
                    return false;
                }

                if (precio_venta < precio_compra) {
                    mostrarError("El precio de venta no puede ser menor al precio de compra");
                    return false;
                }
            } catch (NumberFormatException e) {
                mostrarError("Formato numérico inválido en precios o capacidad");
                return false;
            }

            // 3. Validar categoría
            String categoriaNombre = vista.jComboBox_categoria.getSelectedItem().toString();
            int id_categoria = sql.con_pro.buscar_id_categoria(categoriaNombre);

            if (id_categoria <= 0) {
                mostrarError("Categoría no válida");
                return false;
            }

            // 4. Validar fecha (opcional dependiendo del requerimiento)
            Date fecha = vista.jDateChooser_fecha_vencimiento.getDate();

            // 5. Asignar valores al modelo
            modelo_producto.setCodigo_barra(codigo_barra);
            modelo_producto.setNombre_prodcuto(nombre);
            modelo_producto.setDescripcion(vista.jTextField_descripcion.getText().trim());
            modelo_producto.setPrecio_unitario(precio_compra);
            modelo_producto.setPrecio_venta(precio_venta);
            modelo_producto.setCant_uni_med(capacidad_medida);
            modelo_producto.setUnidad_medida(vista.jComboBox_unidad_medida.getSelectedItem().toString());
            modelo_producto.setFecha_vencimiento(fecha);

            // Si estás actualizando un producto, asegúrate de mantener el ID
            if (vista.jButton_registrar_producto.getText().equals("Actualizar Producto")) {
                // El ID ya debería estar establecido en seleccionarProductoDeTabla()
            }

            categoria categoriaObj = new categoria();
            categoriaObj.setId_categoria(id_categoria);
            modelo_producto.setCategoria(categoriaObj);

            return true; // Datos válidos

        } catch (Exception e) {
            mostrarError("Error inesperado al obtener datos: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para mostrar errores
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    private orden_de_compra obtener_datos_compra() {
        orden_de_compra orden_compra = new orden_de_compra();

        // 1. Validar código de orden
        String codigoOrden = vista.jTextField_codigo_compra.getText().trim();
        if (codigoOrden.isEmpty()) {
            mostrarError("El código de orden de compra es obligatorio");
            return null;
        }
        orden_compra.setCodigo_orden_de_compra(codigoOrden);

        // 2. Validar fecha de orden
        Date fechaOrden = vista.jDateChooser_fecha_orden_compra.getDate();
        if (fechaOrden == null) {
            mostrarError("La fecha de orden es obligatoria");
            return null;
        }
        orden_compra.setFecha_orden(fechaOrden);

        // 3. Validar y obtener proveedor
        if (vista.jComboBox_proveedor.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un proveedor");
            return null;
        }

        String nombre_proveedor = vista.jComboBox_proveedor.getSelectedItem().toString();
        proveedor prov = sql.con_ord_com.proveedor.seleccionar_proveedor(nombre_proveedor);

        if (prov == null) {
            mostrarError("No se pudo obtener el proveedor seleccionado");
            return null;
        }
        orden_compra.setProveedor(prov);

        // 4. Establecer estado inicial (si aplica)
        // orden_compra.setEstado("PENDIENTE"); // Descomentar si existe este campo

        return orden_compra;
    }

    private detalle_orden_de_compra obtener_detalle_orden_compra(orden_de_compra ordc) {
        if (ordc == null) {
            mostrarError("La orden de compra no puede ser nula");
            return null;
        }

        detalle_orden_de_compra dtoc = new detalle_orden_de_compra();
        dtoc.setOrden_de_compra(ordc);

        // 1. Validar y obtener producto
        if (vista.jComboBox_Producto.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un producto");
            return null;
        }

        String nombre_producto = vista.jComboBox_Producto.getSelectedItem().toString();
        producto prod = sql.buscar_producto_nombre(nombre_producto);

        if (prod == null) {
            mostrarError("No se pudo obtener el producto seleccionado");
            return null;
        }
        dtoc.setProducto(prod);

        // 2. Validar y obtener almacén
        if (vista.jComboBox_almacen_destinado.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un almacén de destino");
            return null;
        }

        String almacen_selec = vista.jComboBox_almacen_destinado.getSelectedItem().toString();

        almacen almacenObj = sql.coon_alm.obtener_almacen(almacen_selec);
        if (almacenObj == null) {
            mostrarError("No se pudo obtener el almacén seleccionado");
            return null;
        }
        dtoc.setAlmacen(almacenObj);

        // 3. Validar y obtener forma de entrega
        if (vista.jComboBox_forma_de_entrega.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar una forma de entrega");
            return null;
        }

        String forma_entrega = vista.jComboBox_forma_de_entrega.getSelectedItem().toString();
        entrega entregaObj = sql.obtener_forma_entrega(forma_entrega);

        if (entregaObj == null) {
            mostrarError("No se pudo obtener la forma de entrega seleccionada");
            return null;
        }
        dtoc.setEntrega(entregaObj);

        // 4. Validar y convertir cantidades numéricas
        try {
            String cantidadEntregadaStr = vista.jTextField_cantidad_entrega.getText().trim();
            String cantidadEmpaquetadaStr = vista.jTextField_cantidad_forma_entega.getText().trim();
            String precioUnitario = vista.jTextField_precio_unitario.getText().trim();
            if (cantidadEntregadaStr.isEmpty() || cantidadEmpaquetadaStr.isEmpty()) {
                mostrarError("Las cantidades son obligatorias");
                return null;
            }

            int cantidadEntregada = Integer.parseInt(cantidadEntregadaStr);
            int cantidadEmpaquetada = Integer.parseInt(cantidadEmpaquetadaStr);
            double precio_unitario = Double.parseDouble(precioUnitario);
            if (cantidadEntregada <= 0 || cantidadEmpaquetada <= 0) {
                mostrarError("Las cantidades deben ser mayores a 0");
                return null;
            }

            dtoc.setCantidad_entregada(cantidadEntregada);
            dtoc.setCantidad_empaquetada(cantidadEmpaquetada);
            dtoc.setPrecio_unitario(precio_unitario);
            // 5. Calcular y establecer precio total (si aplica)
            // double precioTotal = cantidadEntregada * prod.getPrecio_unitario();
            // dtoc.setPrecio_total(precioTotal);

        } catch (NumberFormatException e) {
            mostrarError("Formato inválido en las cantidades. Deben ser valores numéricos");
            return null;
        }

        return dtoc;
    }

    @Override
    protected void limpiar_todo() {
        try {
            // Limpiar campos de texto
            vista.jTextField_codigo_barra.setText("");
            vista.jTextField_nombre.setText("");
            vista.jTextField_descripcion.setText("");
            vista.jTextField_precio_compra.setText("");
            vista.jTextField_precio_venta.setText("");
            vista.jTextField_valor_unidad.setText("");

            // Establecer valores por defecto en combobox
            if (vista.jComboBox_categoria.getItemCount() > 0) {
                vista.jComboBox_categoria.setSelectedIndex(0);
            }

            if (vista.jComboBox_unidad_medida.getItemCount() > 0) {
                vista.jComboBox_unidad_medida.setSelectedIndex(0);
            }

            // Limpiar fecha (establecer null o fecha actual según requerimiento)
            vista.jDateChooser_fecha_vencimiento.setDate(null);

            // Enfocar el primer campo para mejor UX
            vista.jTextField_codigo_barra.requestFocusInWindow();

            // Restaurar texto del botón a "Registrar Producto"
            vista.jButton_registrar_producto.setText("Registrar Producto");

        } catch (Exception e) {
            System.err.println("Error al limpiar componentes: " + e.getMessage());
            // Podrías mostrar un mensaje más amigable al usuario
        }
    }

    public void seleccionarProductoDeTabla() {
        try {
            int filaSeleccionada = vista.jTable_lista_productos_registrado.getSelectedRow();

            if (filaSeleccionada >= 0) {
                // Obtener el modelo de la tabla
                DefaultTableModel modelo = (DefaultTableModel) vista.jTable_lista_productos_registrado.getModel();

                // Limpiar campos antes de cargar nuevos datos
                limpiar_todo();

                // Obtener datos de cada columna
                int idProducto = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
                vista.jTextField_codigo_barra.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
                vista.jTextField_nombre.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
                vista.jTextField_descripcion.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
                vista.jComboBox_categoria.setSelectedItem(modelo.getValueAt(filaSeleccionada, 4).toString());

                String unidadMedida = modelo.getValueAt(filaSeleccionada, 5).toString();
                String[] unidadM = unidadMedida.split(" ");
                vista.jTextField_valor_unidad.setText(unidadM[0]);
                vista.jComboBox_unidad_medida.setSelectedItem(unidadM[1]);

                vista.jTextField_precio_venta.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
                vista.jTextField_precio_compra.setText(modelo.getValueAt(filaSeleccionada, 7).toString());

                String fechaVencimientoStr = null;

                // Verificar si hay fecha de vencimiento (puede ser null)
                Object fechaObj = modelo.getValueAt(filaSeleccionada, 8);
                if (fechaObj != null) {
                    fechaVencimientoStr = fechaObj.toString();
                }

                // Cargar datos en los campos del formulario
                modelo_producto.setId_producto(idProducto);

                // Cargar fecha de vencimiento si existe - USANDO EL NUEVO MÉTODO
                if (fechaVencimientoStr != null && !fechaVencimientoStr.trim().isEmpty()) {
                    try {
                        // Usar el nuevo método para parsear la fecha
                        Date fecha = parsearFechaDesdeString(fechaVencimientoStr);
                        vista.jDateChooser_fecha_vencimiento.setDate(fecha);
                    } catch (Exception ex) {
                        System.err.println("Error al parsear fecha: " + ex.getMessage());
                        vista.jDateChooser_fecha_vencimiento.setDate(null);
                    }
                } else {
                    vista.jDateChooser_fecha_vencimiento.setDate(null);
                }

            } else {
                mostrarMensajeAdvertencia("No hay fila seleccionada");
            }

        } catch (Exception ex) {
            mostrarError("Error al seleccionar producto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostrarMensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private String limpiarNumero(String texto) {
        if (texto == null) {
            return "0";
        }
        // Remover caracteres no numéricos excepto punto decimal
        return texto.replaceAll("[^\\d.]", "");
    }

}