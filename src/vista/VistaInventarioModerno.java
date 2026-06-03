/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class VistaInventarioModerno extends JInternalFrame {
    // Colores del tema (igual que GestionarInventario)
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);

    // Panel principal y pestañas
    private JPanel mainPanel;
    private JTabbedPane jTabbedPane1;

    // Variables de componentes (manteniendo los nombres originales)
    // Panel Productos
    public JTable jTable_lista_productos_registrado;
    public JTextField jTextField_nombre;
    public JTextField jTextField_codigo_barra;
    public JTextField jTextField_descripcion;
    public JComboBox<String> jComboBox_categoria;
    public JTextField jTextField_valor_unidad;
    public JComboBox<String> jComboBox_unidad_medida;
    public JTextField jTextField_precio_compra;
    public JTextField jTextField_precio_venta;
    public JDateChooser jDateChooser_fecha_vencimiento;
    public JButton jButton_registrar_producto;
    // Agrega estas declaraciones junto con las otras variables públicas:

    public JComboBox<String> jComboBox_iva;
    // Variables para búsqueda y filtros en Productos
    public JTextField jTextField_buscar_producto;
    public JButton jButton_buscar_producto;
    public JComboBox<String> jComboBox_filtrar_categoria;
    public JButton jButton_limpiar_filtros;
    public JButton jButton_limpiar_producto;
    public JButton jButton_actualizar_producto;
    // Panel Inventario
    public JComboBox<String> jComboBox_filtar_producto_inventario;
    public JComboBox<String> jComboBox_filtar_stock;
    public JComboBox<String> jComboBox_pasillo;
    public JComboBox<String> jComboBox_estante_inven;
    public JComboBox<String> jComboBox_almacen_inventario;
    public JComboBox<String> jComboBox_producto_inventario;
    public JButton jButton_añadir_producto_inventario;
    public JTable jTable_lista_productos_inventario;
    public JComboBox<String> jComboBox_filtar_proveedor_inventario;
    // NUEVOS CAMPOS PARA INVENTARIO
    public JTextField jTextField_cantidad_inventario;
    public JTextField jTextField_stock_minimo;

    // Panel Órdenes de compra
    public JTextField jTextField_codigo_compra;
    public JComboBox<String> jComboBox_proveedor;
    public JDateChooser jDateChooser_fecha_orden_compra;
    public JComboBox<String> jComboBox_almacen_destinado;
    public JTable jTable_lista_producto_compra;
    public JComboBox<String> jComboBox_Producto;
    public JComboBox<String> jComboBox_forma_de_entrega;
    public JTextField jTextField8;
    public JTextField jTextField_cantidad_entrega;
    public JTextField jTextField_precio_unitario;
    public JTextField jTextField_cantidad_forma_entega;
    public JButton jButton_añadir_orc;
    public JButton jButton_limpiar_compra;
    public JButton jButton_registrar_compra;
    public JLabel jLabel_total_compra; // NUEVO: JLabel para el total

    // Panel Movimientos
    public JComboBox<String> jComboBox_filtrar_productos_movimiento;
    public JTable jTable_movimiento;
    public JComboBox<String> jComboBox_filtrar_movimento_almacenes;
    public JComboBox<String> jComboBox_filtrar_movimiento_por_producto;
    public JButton jButton8;
    public JDateChooser jDateChooser_fecha_desde; // Para filtrar por fecha desde
    public JDateChooser jDateChooser_fecha_hasta; // Para filtrar por fecha hasta

    // Panel Reportes
    public JPanel jPanel5;

    // Panel Reporte Inventario Disponible
    public JTextArea jTextArea_reporte_inventario;
    public JButton jButton_exportar_pdf_inventario;
    public JButton jButton_exportar_excel_inventario;
    public JButton jButton_actualizar_reporte_inventario;
    public JComboBox<String> jComboBox_tipo_reporte; // ComboBox para seleccionar tipo de reporte
    public JButton jButton_generar_reporte; // Botón para generar el reporte seleccionado
    public JMenuItem editar_pro = new JMenuItem("Editar Producto");
    public JPopupMenu menu_pro = new JPopupMenu();

    public VistaInventarioModerno() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sistema de Gestión de Inventario");
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1300, 850);

        // Configurar para que se abra maximizada
        try {
            setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        // Panel principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT);

        // Header
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        // Panel central con pestañas
        jTabbedPane1 = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane1.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Crear paneles de cada pestaña
        jTabbedPane1.addTab("Productos", createProductosPanel());
        jTabbedPane1.addTab("Inventario", createInventarioPanel());
        jTabbedPane1.addTab("Órdenes de Compra", createOrdenesCompraPanel());
        jTabbedPane1.addTab("Reportes", createReportesPanel());
        jTabbedPane1.addTab("Movimientos", createMovimientosPanel());

        // Estilo de pestañas
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setBackgroundAt(i, LIGHT);
        }

        mainPanel.add(jTabbedPane1, BorderLayout.CENTER);
        setContentPane(mainPanel);

        menu_pro.add(editar_pro);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY,
                        getWidth(), 0, SECONDARY);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 100));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Sistema de Gestión de Inventario");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Gestión completa de productos, inventario y movimientos");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.CENTER);

        return header;
    }

    // PANEL PRODUCTOS
    private JPanel createProductosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel principal con scroll
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 15));
        mainContentPanel.setBackground(LIGHT);

        // ========== PANEL DE BÚSQUEDA Y FILTROS ==========
        JPanel searchFilterPanel = createCardPanel("");
        searchFilterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;
        gbcSearch.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Campo de búsqueda
        gbcSearch.gridx = 0;
        gbcSearch.gridy = 0;
        gbcSearch.gridwidth = 1;
        gbcSearch.anchor = GridBagConstraints.LINE_END;
        searchFilterPanel.add(createStyledLabel("Buscar Producto:"), gbcSearch);

        gbcSearch.gridx = 1;
        gbcSearch.gridwidth = 2;
        gbcSearch.weightx = 1.0;
        gbcSearch.anchor = GridBagConstraints.LINE_START;
        jTextField_buscar_producto = createStyledTextField();
        jTextField_buscar_producto.setPreferredSize(new Dimension(300, 32));
        jTextField_buscar_producto.putClientProperty("JTextField.placeholderText", "Código o nombre del producto...");
        searchFilterPanel.add(jTextField_buscar_producto, gbcSearch);

        // Botón de búsqueda
        gbcSearch.gridx = 3;
        gbcSearch.gridwidth = 1;
        gbcSearch.weightx = 0;
        gbcSearch.anchor = GridBagConstraints.LINE_START;
        jButton_buscar_producto = createStyledButton("Buscar", PRIMARY);
        jButton_buscar_producto.setPreferredSize(new Dimension(120, 32));
        searchFilterPanel.add(jButton_buscar_producto, gbcSearch);

        // Filtro por categoría
        gbcSearch.gridx = 4;
        gbcSearch.gridwidth = 1;
        gbcSearch.weightx = 0;
        gbcSearch.anchor = GridBagConstraints.LINE_END;
        searchFilterPanel.add(createStyledLabel("Filtrar por Categoría:"), gbcSearch);

        gbcSearch.gridx = 5;
        gbcSearch.gridwidth = 2;
        gbcSearch.weightx = 1.0;
        gbcSearch.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtrar_categoria = createStyledComboBox(
                new String[] { "Todas", "Electrónicos", "Ropa", "Hogar", "Alimentos" });
        jComboBox_filtrar_categoria.setPreferredSize(new Dimension(200, 32));
        searchFilterPanel.add(jComboBox_filtrar_categoria, gbcSearch);

        // Botón de limpiar filtros
        gbcSearch.gridx = 7;
        gbcSearch.gridwidth = 1;
        gbcSearch.weightx = 0;
        gbcSearch.anchor = GridBagConstraints.LINE_START;
        jButton_limpiar_filtros = createStyledButton("Limpiar Filtros", WARNING);
        jButton_limpiar_filtros.setPreferredSize(new Dimension(150, 32));
        searchFilterPanel.add(jButton_limpiar_filtros, gbcSearch);

        // ========== PANEL DE FORMULARIO SUPERIOR ==========
        JPanel formPanel = createCardPanel("");
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Código de barra y Categoría
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Código de barra:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_codigo_barra = createStyledTextField();
        jTextField_codigo_barra.setPreferredSize(new Dimension(250, 32));
        formPanel.add(jTextField_codigo_barra, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Categoría:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_categoria = createStyledComboBox(
                new String[] { "Seleccionar...", "Electrónicos", "Ropa", "Hogar", "Alimentos" });
        jComboBox_categoria.setPreferredSize(new Dimension(250, 32));
        formPanel.add(jComboBox_categoria, gbc);

        // Fila 1: Nombre y Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_nombre = createStyledTextField();
        jTextField_nombre.setPreferredSize(new Dimension(250, 32));
        formPanel.add(jTextField_nombre, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Descripción:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_descripcion = createStyledTextField();
        jTextField_descripcion.setPreferredSize(new Dimension(250, 32));
        formPanel.add(jTextField_descripcion, gbc);

        // Fila 2: Valor unidad y Unidad medida
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Valor de la unidad:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_valor_unidad = createStyledTextField();
        jTextField_valor_unidad.setPreferredSize(new Dimension(180, 32));
        formPanel.add(jTextField_valor_unidad, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Unidad Med:"), gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_unidad_medida = createStyledComboBox(new String[] { "UN", "KG", "LT", "GR", "ML" });
        jComboBox_unidad_medida.setPreferredSize(new Dimension(120, 32));
        formPanel.add(jComboBox_unidad_medida, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Precio Compra:"), gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_precio_compra = createStyledTextField();
        jTextField_precio_compra.setPreferredSize(new Dimension(180, 32));
        formPanel.add(jTextField_precio_compra, gbc);

        // Fila 3: Precio venta y Fecha vencimiento
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Precio venta:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_precio_venta = createStyledTextField();
        jTextField_precio_venta.setPreferredSize(new Dimension(180, 32));
        formPanel.add(jTextField_precio_venta, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("Fecha de vencimiento:"), gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jDateChooser_fecha_vencimiento = new JDateChooser();
        jDateChooser_fecha_vencimiento.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jDateChooser_fecha_vencimiento.setPreferredSize(new Dimension(180, 32));
        formPanel.add(jDateChooser_fecha_vencimiento, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(createStyledLabel("IVA:"), gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_iva = createStyledComboBox(new String[] { "Tiene IVA", "Excento de IVA" });
        jComboBox_iva.setPreferredSize(new Dimension(180, 32));
        formPanel.add(jComboBox_iva, gbc);

        // Fila 4: Botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Botón Limpiar
        jButton_limpiar_producto = createStyledButton("Limpiar", WARNING);
        jButton_limpiar_producto.setPreferredSize(new Dimension(120, 38));
        buttonPanel.add(jButton_limpiar_producto);

        // Botón Actualizar
        jButton_actualizar_producto = createStyledButton("Actualizar Producto", SECONDARY);
        jButton_actualizar_producto.setPreferredSize(new Dimension(180, 38));
        buttonPanel.add(jButton_actualizar_producto);

        // Botón Registrar
        jButton_registrar_producto = createStyledButton("Registrar Producto", SUCCESS);
        jButton_registrar_producto.setPreferredSize(new Dimension(180, 38));
        buttonPanel.add(jButton_registrar_producto);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);

        // ========== PANEL DE TABLA INFERIOR ==========
        JPanel tablePanel = createCardPanel("Productos Registrados");
        tablePanel.setLayout(new BorderLayout());

        String[] columns = { "id_producto", "Código Barra", "Nombre", "Descripción", "Categoría", "Uni Med",
                "Precio Venta", "Precio Compra", "Fecha Expiración" };
        Object[][] data = {};

        jTable_lista_productos_registrado = createStyledTable(columns, data);
        configurarTablaProductos();

        JScrollPane scrollPane = new JScrollPane(jTable_lista_productos_registrado);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // ========== ORGANIZAR PANELES ==========
        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setBackground(LIGHT);
        topPanel.add(searchFilterPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        mainContentPanel.add(topPanel, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        JScrollPane mainScroll = new JScrollPane(mainContentPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(mainScroll, BorderLayout.CENTER);

        return panel;
    }

    // PANEL INVENTARIO
    private JPanel createInventarioPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel principal con scroll
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 15));
        mainContentPanel.setBackground(LIGHT);

        // ========== PANEL SUPERIOR: AGREGAR PRODUCTO ==========
        JPanel addPanel = createCardPanel("");
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Producto y Almacén
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Producto:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_producto_inventario = createStyledComboBox(new String[] { "Seleccionar producto..." });
        jComboBox_producto_inventario.setPreferredSize(new Dimension(250, 32));
        addPanel.add(jComboBox_producto_inventario, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Almacén:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_almacen_inventario = createStyledComboBox(new String[] { "Almacén Principal", "Almacén Secundario" });
        jComboBox_almacen_inventario.setPreferredSize(new Dimension(250, 32));
        addPanel.add(jComboBox_almacen_inventario, gbc);

        // Fila 1: Pasillo y Estante
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Seleccionar Pasillo:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_pasillo = createStyledComboBox(new String[] { "A", "B", "C", "D" });
        jComboBox_pasillo.setPreferredSize(new Dimension(250, 32));
        addPanel.add(jComboBox_pasillo, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Estante:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_estante_inven = createStyledComboBox(new String[] { "1", "2", "3", "4" });
        jComboBox_estante_inven.setPreferredSize(new Dimension(250, 32));
        addPanel.add(jComboBox_estante_inven, gbc);

        // Fila 2: Cantidad y Stock Mínimo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_cantidad_inventario = createStyledTextField();
        jTextField_cantidad_inventario.setPreferredSize(new Dimension(250, 32));
        jTextField_cantidad_inventario.putClientProperty("JTextField.placeholderText", "Ingrese la cantidad");
        addPanel.add(jTextField_cantidad_inventario, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        addPanel.add(createStyledLabel("Stock Mínimo:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jTextField_stock_minimo = createStyledTextField();
        jTextField_stock_minimo.setPreferredSize(new Dimension(250, 32));
        jTextField_stock_minimo.putClientProperty("JTextField.placeholderText", "Mínimo requerido");
        addPanel.add(jTextField_stock_minimo, gbc);

        // Fila 3: Botón
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        jButton_añadir_producto_inventario = createStyledButton("Añadir Producto al Inventario", SECONDARY);
        jButton_añadir_producto_inventario.setPreferredSize(new Dimension(280, 38));
        addPanel.add(jButton_añadir_producto_inventario, gbc);

        // ========== PANEL MEDIO: FILTROS ==========
        JPanel filterPanel = createCardPanel("Filtros de Búsqueda");
        filterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcFilter = new GridBagConstraints();
        gbcFilter.fill = GridBagConstraints.HORIZONTAL;
        gbcFilter.insets = new Insets(8, 8, 8, 8);

        // Fila de filtros
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        gbcFilter.gridwidth = 1;
        gbcFilter.anchor = GridBagConstraints.LINE_END;
        // filterPanel.add(createStyledLabel("Stock:"), gbcFilter);

        gbcFilter.gridx = 1;
        gbcFilter.gridwidth = 1;
        gbcFilter.weightx = 0.5;
        gbcFilter.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtar_stock = createStyledComboBox(
                new String[] { "Todos", "Stock Bajo", "Stock Normal", "Sin Stock" });
        jComboBox_filtar_stock.setPreferredSize(new Dimension(200, 32));
        filterPanel.add(jComboBox_filtar_stock, gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridwidth = 1;
        gbcFilter.weightx = 0;
        gbcFilter.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Filtro por Producto:"), gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridwidth = 1;
        gbcFilter.weightx = 0.5;
        gbcFilter.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtar_producto_inventario = createStyledComboBox(
                new String[] { "Todos", "Electrónicos", "Ropa", "Hogar" });
        jComboBox_filtar_producto_inventario.setPreferredSize(new Dimension(200, 32));
        filterPanel.add(jComboBox_filtar_producto_inventario, gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridwidth = 1;
        gbcFilter.weightx = 0;
        gbcFilter.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Filtro por Proveedor:"), gbcFilter);

        gbcFilter.gridx = 5;
        gbcFilter.gridwidth = 1;
        gbcFilter.weightx = 0.5;
        gbcFilter.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtar_proveedor_inventario = createStyledComboBox(
                new String[] { "Todos", "Proveedor A", "Proveedor B" });
        jComboBox_filtar_proveedor_inventario.setPreferredSize(new Dimension(200, 32));
        filterPanel.add(jComboBox_filtar_proveedor_inventario, gbcFilter);

        // ========== PANEL INFERIOR: TABLA ==========
        JPanel tablePanel = createCardPanel("Productos en Inventario");
        tablePanel.setLayout(new BorderLayout());

        String[] columns = { "Producto", "Almacén", "Ubicación", "Precio Unit", "Stock", "Stock Mín", "Proveedor" };
        Object[][] data = {};

        jTable_lista_productos_inventario = createStyledTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(jTable_lista_productos_inventario);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // ========== ORGANIZAR PANELES ==========
        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setBackground(LIGHT);
        topPanel.add(addPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);

        mainContentPanel.add(topPanel, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        JScrollPane mainScroll = new JScrollPane(mainContentPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(mainScroll, BorderLayout.CENTER);

        return panel;
    }

    // PANEL ÓRDENES DE COMPRA
    private JPanel createOrdenesCompraPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel principal con split pane
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerSize(5);
        mainSplitPane.setDividerLocation(500);
        mainSplitPane.setContinuousLayout(true);

        // ========== PANEL IZQUIERDO: FORMULARIO ==========
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(LIGHT);

        // Panel de datos de orden
        JPanel orderPanel = createCardPanel("");
        orderPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcOrder = new GridBagConstraints();
        gbcOrder.fill = GridBagConstraints.HORIZONTAL;
        gbcOrder.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Código de compra
        gbcOrder.gridx = 0;
        gbcOrder.gridy = 0;
        gbcOrder.gridwidth = 1;
        gbcOrder.anchor = GridBagConstraints.LINE_END;
        orderPanel.add(createStyledLabel("Código de Compra:"), gbcOrder);

        gbcOrder.gridx = 1;
        gbcOrder.gridwidth = 3;
        gbcOrder.weightx = 1.0;
        gbcOrder.anchor = GridBagConstraints.LINE_START;
        jTextField_codigo_compra = createStyledTextField();
        jTextField_codigo_compra.setPreferredSize(new Dimension(300, 32));
        orderPanel.add(jTextField_codigo_compra, gbcOrder);

        // Fila 1: Proveedores
        gbcOrder.gridx = 0;
        gbcOrder.gridy = 1;
        gbcOrder.gridwidth = 1;
        gbcOrder.weightx = 0;
        gbcOrder.anchor = GridBagConstraints.LINE_END;
        orderPanel.add(createStyledLabel("Proveedores:"), gbcOrder);

        gbcOrder.gridx = 1;
        gbcOrder.gridwidth = 3;
        gbcOrder.weightx = 1.0;
        gbcOrder.anchor = GridBagConstraints.LINE_START;
        jComboBox_proveedor = createStyledComboBox(new String[] { "Seleccionar proveedor..." });
        jComboBox_proveedor.setPreferredSize(new Dimension(300, 32));
        orderPanel.add(jComboBox_proveedor, gbcOrder);

        // Fila 2: Fecha de compra
        gbcOrder.gridx = 0;
        gbcOrder.gridy = 2;
        gbcOrder.gridwidth = 1;
        gbcOrder.weightx = 0;
        gbcOrder.anchor = GridBagConstraints.LINE_END;
        orderPanel.add(createStyledLabel("Fecha de compra:"), gbcOrder);

        gbcOrder.gridx = 1;
        gbcOrder.gridwidth = 3;
        gbcOrder.weightx = 1.0;
        gbcOrder.anchor = GridBagConstraints.LINE_START;
        jDateChooser_fecha_orden_compra = new JDateChooser();
        jDateChooser_fecha_orden_compra.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jDateChooser_fecha_orden_compra.setPreferredSize(new Dimension(300, 32));
        orderPanel.add(jDateChooser_fecha_orden_compra, gbcOrder);

        // Fila 3: Almacén Destinado
        gbcOrder.gridx = 0;
        gbcOrder.gridy = 3;
        gbcOrder.gridwidth = 1;
        gbcOrder.weightx = 0;
        gbcOrder.anchor = GridBagConstraints.LINE_END;
        orderPanel.add(createStyledLabel("Almacén Destinado:"), gbcOrder);

        gbcOrder.gridx = 1;
        gbcOrder.gridwidth = 3;
        gbcOrder.weightx = 1.0;
        gbcOrder.anchor = GridBagConstraints.LINE_START;
        jComboBox_almacen_destinado = createStyledComboBox(new String[] { "Almacén Principal", "Almacén Secundario" });
        jComboBox_almacen_destinado.setPreferredSize(new Dimension(300, 32));
        orderPanel.add(jComboBox_almacen_destinado, gbcOrder);

        // Panel de detalles del producto - SIN SCROLLPANEL
        JPanel detailPanel = createCardPanel("Detalles del Producto");
        detailPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcDetail = new GridBagConstraints();
        gbcDetail.fill = GridBagConstraints.HORIZONTAL;
        gbcDetail.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Producto y Cantidad entregada
        gbcDetail.gridx = 0;
        gbcDetail.gridy = 0;
        gbcDetail.gridwidth = 1;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        detailPanel.add(createStyledLabel("Producto:"), gbcDetail);

        gbcDetail.gridx = 1;
        gbcDetail.gridwidth = 2;
        gbcDetail.weightx = 1.0;
        gbcDetail.anchor = GridBagConstraints.LINE_START;
        jComboBox_Producto = createStyledComboBox(new String[] { "Seleccionar producto..." });
        jComboBox_Producto.setPreferredSize(new Dimension(250, 32));
        detailPanel.add(jComboBox_Producto, gbcDetail);

        gbcDetail.gridx = 3;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        detailPanel.add(createStyledLabel("Cantidad entregada:"), gbcDetail);

        gbcDetail.gridx = 4;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0.5;
        gbcDetail.anchor = GridBagConstraints.LINE_START;
        jTextField_cantidad_entrega = createStyledTextField();
        jTextField_cantidad_entrega.setPreferredSize(new Dimension(120, 32));
        detailPanel.add(jTextField_cantidad_entrega, gbcDetail);

        // Fila 1: Forma de entrega y Cantidad por forma
        gbcDetail.gridx = 0;
        gbcDetail.gridy = 1;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        detailPanel.add(createStyledLabel("Forma de entrega:"), gbcDetail);

        gbcDetail.gridx = 1;
        gbcDetail.gridwidth = 2;
        gbcDetail.weightx = 1.0;
        gbcDetail.anchor = GridBagConstraints.LINE_START;
        jComboBox_forma_de_entrega = createStyledComboBox(new String[] { "Unidades", "Cajas", "Paquetes" });
        jComboBox_forma_de_entrega.setPreferredSize(new Dimension(250, 32));
        detailPanel.add(jComboBox_forma_de_entrega, gbcDetail);

        gbcDetail.gridx = 3;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        detailPanel.add(createStyledLabel("Cant. por forma:"), gbcDetail);

        gbcDetail.gridx = 4;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0.5;
        gbcDetail.anchor = GridBagConstraints.LINE_START;
        jTextField_cantidad_forma_entega = createStyledTextField();
        jTextField_cantidad_forma_entega.setPreferredSize(new Dimension(120, 32));
        detailPanel.add(jTextField_cantidad_forma_entega, gbcDetail);

        // Fila 2: Precio unitario
        gbcDetail.gridx = 0;
        gbcDetail.gridy = 2;
        gbcDetail.gridwidth = 1;
        gbcDetail.weightx = 0;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        detailPanel.add(createStyledLabel("Precio unitario:"), gbcDetail);

        gbcDetail.gridx = 1;
        gbcDetail.gridwidth = 2;
        gbcDetail.weightx = 1.0;
        gbcDetail.anchor = GridBagConstraints.LINE_START;
        jTextField_precio_unitario = createStyledTextField();
        jTextField_precio_unitario.setPreferredSize(new Dimension(250, 32));
        detailPanel.add(jTextField_precio_unitario, gbcDetail);

        // Fila 3: Botón Añadir
        gbcDetail.gridx = 3;
        gbcDetail.gridy = 3;
        gbcDetail.gridwidth = 2;
        gbcDetail.weightx = 0;
        gbcDetail.anchor = GridBagConstraints.LINE_END;
        gbcDetail.fill = GridBagConstraints.NONE;
        jButton_añadir_orc = createStyledButton("Añadir a la lista", SECONDARY);
        jButton_añadir_orc.setPreferredSize(new Dimension(180, 38));
        detailPanel.add(jButton_añadir_orc, gbcDetail);

        // Panel de botones en la parte inferior izquierda
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        jButton_limpiar_compra = createStyledButton("Limpiar Compra", WARNING);
        jButton_limpiar_compra.setPreferredSize(new Dimension(180, 38));

        jButton_registrar_compra = createStyledButton("Registrar Compra", SUCCESS);
        jButton_registrar_compra.setPreferredSize(new Dimension(180, 38));

        buttonPanel.add(jButton_limpiar_compra);
        buttonPanel.add(jButton_registrar_compra);

        // Organizar panel izquierdo
        JPanel leftContent = new JPanel(new BorderLayout(10, 10));
        leftContent.setBackground(LIGHT);
        leftContent.add(orderPanel, BorderLayout.NORTH);
        leftContent.add(detailPanel, BorderLayout.CENTER);
        leftContent.add(buttonPanel, BorderLayout.SOUTH);

        // ========== PANEL DERECHO: TABLA CON TOTAL ==========
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(LIGHT);

        // Panel de la tabla
        JPanel tablePanel = createCardPanel("Listado de Compra");
        tablePanel.setLayout(new BorderLayout());

        String[] columns = { "Producto", "Forma de entrega", "Cant. por forma", "Cant. entregada", "Total unidades",
                "Precio unit.", "Sub total" };
        Object[][] data = {};

        jTable_lista_producto_compra = createStyledTable(columns, data);
        JScrollPane tableScroll = new JScrollPane(jTable_lista_producto_compra);

        tablePanel.add(tableScroll, BorderLayout.CENTER);

        // Panel para el total en la parte inferior derecha
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Crear el JLabel para mostrar el total
        jLabel_total_compra = new JLabel("Total: $0.00");
        jLabel_total_compra.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel_total_compra.setForeground(SUCCESS);
        totalPanel.add(jLabel_total_compra);

        // Organizar el panel derecho
        rightPanel.add(tablePanel, BorderLayout.CENTER);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);

        // Configurar split pane
        mainSplitPane.setLeftComponent(leftContent);
        mainSplitPane.setRightComponent(rightPanel);

        panel.add(mainSplitPane, BorderLayout.CENTER);

        return panel;
    }

    // PANEL REPORTES
    private JPanel createReportesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 15));
        mainContentPanel.setBackground(LIGHT);

        // ========== PANEL DE ESTADÍSTICAS ==========
        JPanel statsPanel = createCardPanel("Estadísticas del Inventario");
        statsPanel.setLayout(new GridLayout(1, 4, 15, 15));


        // ========== PANEL DE REPORTE DE INVENTARIO DISPONIBLE ==========
        JPanel inventoryReportPanel = createCardPanel("Reporte de Inventario Disponible");
        inventoryReportPanel.setLayout(new BorderLayout(10, 10));

        // Panel de botones de acción
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionButtonsPanel.setBackground(Color.WHITE);

        // ComboBox para seleccionar tipo de reporte (400px de ancho)
        jComboBox_tipo_reporte = createStyledComboBox(new String[] {
                "Seleccionar reporte...",
                "Reporte de Disponibilidad de Productos",
                "Reporte de Productos más Vendidos",
                "Reporte de Ventas Totales por Empleado",
                "Reporte de Categorias",
                "Contenido de Categorias",
                "Productos Agotados",
                "Compra Inventario"
        });
        jComboBox_tipo_reporte.setPreferredSize(new Dimension(400, 38));
        actionButtonsPanel.add(jComboBox_tipo_reporte);

        // Botón Generar Reporte
        jButton_generar_reporte = createStyledButton("📋 Generar Reporte", PRIMARY);
        jButton_generar_reporte.setPreferredSize(new Dimension(180, 38));
        actionButtonsPanel.add(jButton_generar_reporte);

        // Separador visual
        JLabel separador = new JLabel("   |   ");
        separador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        separador.setForeground(DARK);
        actionButtonsPanel.add(separador);

        // Botón Exportar PDF
        jButton_exportar_pdf_inventario = createStyledButton("📄 Exportar a PDF", DANGER);
        jButton_exportar_pdf_inventario.setPreferredSize(new Dimension(160, 38));
        actionButtonsPanel.add(jButton_exportar_pdf_inventario);

        // Botón Exportar Excel
        jButton_exportar_excel_inventario = createStyledButton("📊 Exportar a Excel", SUCCESS);
        jButton_exportar_excel_inventario.setPreferredSize(new Dimension(170, 38));
        actionButtonsPanel.add(jButton_exportar_excel_inventario);

        // JTextArea para mostrar el reporte
        jTextArea_reporte_inventario = new JTextArea();
        jTextArea_reporte_inventario.setFont(new Font("Monospaced", Font.PLAIN, 11));
        jTextArea_reporte_inventario.setEditable(false);
        jTextArea_reporte_inventario.setLineWrap(false);
        jTextArea_reporte_inventario.setWrapStyleWord(false);
        jTextArea_reporte_inventario.setBackground(new Color(250, 250, 250));
        jTextArea_reporte_inventario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        jTextArea_reporte_inventario.setText(
                "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n"
                        +
                        "                              SISTEMA DE REPORTES DE INVENTARIO\n" +
                        "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n\n"
                        +
                        "    📋 INSTRUCCIONES PARA GENERAR REPORTES:\n\n"
                        +
                        "    1. Seleccione el tipo de reporte en el menú desplegable (ComboBox)\n" +
                        "    2. Haga clic en el botón '📋 Generar Reporte'\n" +
                        "    3. El reporte se mostrará en esta área de texto\n\n" +
                        "    📊 REPORTES DISPONIBLES:\n\n" +
                        "        • Reporte de Disponibilidad de Productos\n" +
                        "          Muestra todos los productos con inventario disponible,\n" +
                        "          incluyendo: Código, Nombre, Capacidad, Precios, Cantidad,\n" +
                        "          Ubicación, Fecha de Vencimiento y Estado.\n\n" +
                        "        • Reporte de Productos más Vendidos\n" +
                        "          Muestra los productos con mayor cantidad de ventas,\n" +
                        "          útil para análisis de demanda y gestión de stock.\n\n" +
                        "    💾 OPCIONES DE EXPORTACIÓN:\n\n" +
                        "        📄 Exportar a PDF - Para impresión y presentaciones formales\n" +
                        "        📊 Exportar a Excel - Para análisis y manipulación de datos\n\n" +
                        "    ⚠️ NOTA: Primero debe generar un reporte antes de exportar.\n\n" +
                        "═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n");

        JScrollPane scrollPaneReporte = new JScrollPane(jTextArea_reporte_inventario);
        scrollPaneReporte.setPreferredSize(new Dimension(800, 400));
        scrollPaneReporte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneReporte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        inventoryReportPanel.add(actionButtonsPanel, BorderLayout.NORTH);
        inventoryReportPanel.add(scrollPaneReporte, BorderLayout.CENTER);

        // ========== ORGANIZAR PANELES ==========
        mainContentPanel.add(statsPanel, BorderLayout.NORTH);
        mainContentPanel.add(inventoryReportPanel, BorderLayout.CENTER);

        JScrollPane mainScroll = new JScrollPane(mainContentPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(mainScroll, BorderLayout.CENTER);

        return panel;
    }

    // PANEL MOVIMIENTOS
    private JPanel createMovimientosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 15));
        mainContentPanel.setBackground(LIGHT);

        // ========== PANEL DE FILTROS ==========
        JPanel filterPanel = createCardPanel("");
        filterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Fila 0: Tipo de movimiento
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Filtrar Tipo de movimiento:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtrar_productos_movimiento = createStyledComboBox(new String[] { "TODOS","ENTRADA","SALIDA","TRANSFERENCIA","CREACION" });
        jComboBox_filtrar_productos_movimiento.setPreferredSize(new Dimension(200, 32));
        filterPanel.add(jComboBox_filtrar_productos_movimiento, gbc);

        // Fila 1: Tipo de Almacén
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Filtrar por Tipo de Almacén:"), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtrar_movimento_almacenes = createStyledComboBox(new String[] { "Todos", "Principal", "Secundario" });
        jComboBox_filtrar_movimento_almacenes.setPreferredSize(new Dimension(200, 32));
        filterPanel.add(jComboBox_filtrar_movimento_almacenes, gbc);

        // Fila 1: Producto
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Producto:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jComboBox_filtrar_movimiento_por_producto = createStyledComboBox(new String[] { "Todos", "Electrónicos", "Ropa", "Hogar" });
        jComboBox_filtrar_movimiento_por_producto.setPreferredSize(new Dimension(180, 32));
        filterPanel.add(jComboBox_filtrar_movimiento_por_producto, gbc);

        // Fila 2: Fecha Desde
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Fecha Desde:"), gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jDateChooser_fecha_desde = new JDateChooser();
        jDateChooser_fecha_desde.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jDateChooser_fecha_desde.setPreferredSize(new Dimension(180, 32));
        filterPanel.add(jDateChooser_fecha_desde, gbc);

        // Fila 3: Fecha Hasta
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(createStyledLabel("Fecha Hasta:"), gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        jDateChooser_fecha_hasta = new JDateChooser();
        jDateChooser_fecha_hasta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jDateChooser_fecha_hasta.setPreferredSize(new Dimension(180, 32));
        filterPanel.add(jDateChooser_fecha_hasta, gbc);

        // Fila 4: Botón Filtrar
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        jButton8 = createStyledButton("Filtrar Movimientos", SECONDARY);
        jButton8.setPreferredSize(new Dimension(180, 38));
        filterPanel.add(jButton8, gbc);

        // ========== PANEL DE TABLA ==========
        JPanel tablePanel = createCardPanel("Historial de Movimientos");
        tablePanel.setLayout(new BorderLayout());

        String[] columns = { "Producto", "Almacén", "Ubicación", "Cant. Anterior", "Cant. Nueva", "Tipo Movimiento",
                "Descripción", "Fecha" };
        Object[][] data = {};

        jTable_movimiento = createStyledTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(jTable_movimiento);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // ========== ORGANIZAR PANELES ==========
        mainContentPanel.add(filterPanel, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        JScrollPane mainScroll = new JScrollPane(mainContentPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(mainScroll, BorderLayout.CENTER);

        return panel;
    }

    // ========== MÉTODOS AUXILIARES PARA ESTILO ==========

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(DARK);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return combo;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private JPanel createCardPanel(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titleLabel.setForeground(PRIMARY);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            card.add(titleLabel, BorderLayout.NORTH);
        }

        return card;
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 0, 0, 0, color),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(PRIMARY);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);

        return card;
    }

    private JTable createStyledTable(String[] columns, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(LIGHT);
        table.getTableHeader().setForeground(DARK);
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(new Color(222, 226, 230));
        table.setSelectionBackground(SECONDARY);
        table.setSelectionForeground(Color.WHITE);
        table.setIntercellSpacing(new Dimension(0, 0));

        return table;
    }

    /**
     * Configura la tabla de productos: oculta columnas no visibles y ajusta anchos
     */
    private void configurarTablaProductos() {
        if (jTable_lista_productos_registrado != null) {
            // Ocultar columna 0 (id_producto) - ancho 0
            jTable_lista_productos_registrado.getColumnModel().getColumn(0).setMinWidth(0);
            jTable_lista_productos_registrado.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable_lista_productos_registrado.getColumnModel().getColumn(0).setPreferredWidth(0);

            // Configurar anchos para las demás columnas
            int[] anchosColumnas = { 120, 200, 250, 100, 80, 100, 100, 120 };

            for (int i = 1; i < anchosColumnas.length + 1
                    && i < jTable_lista_productos_registrado.getColumnCount(); i++) {
                jTable_lista_productos_registrado.getColumnModel().getColumn(i)
                        .setPreferredWidth(anchosColumnas[i - 1]);
            }

            // Configurar alineaciones
            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            javax.swing.table.DefaultTableCellRenderer rightRenderer = new javax.swing.table.DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

            // Columna 4 (Uni Med) - centrada
            jTable_lista_productos_registrado.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

            // Columnas 5 y 6 (Precios) - derecha
            jTable_lista_productos_registrado.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
            jTable_lista_productos_registrado.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

            // Columna 7 (Fecha) - centrada
            jTable_lista_productos_registrado.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        }
    }
}