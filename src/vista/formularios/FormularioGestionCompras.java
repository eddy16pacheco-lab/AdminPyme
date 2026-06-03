package vista.formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FormularioGestionCompras extends JFrame {
    
    // Colores del sistema
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);
    public static final Color WHITE = new Color(255, 255, 255);
    
    // Componentes principales
    public JPanel contentPane;
    public JPanel headerPanel;
    public JPanel formContainerPanel;
    public JPanel buttonPanel;
    
    // Componentes del formulario
    public JTextField jTextField_codigo_compra;
    public JComboBox<String> jComboBox_proveedor;
    public JComboBox<String> jComboBox_producto;
    public JTextField jTextField_cantidad_empaquetada;
    public JTextField jTextField_cantidad_entregada;
    public JTextField jTextField_precio;
    public JTextField jTextField_total_bs;
    public JTextField jTextField_total_usd;
    public JComboBox<String> jComboBox_unidad;
    public JButton jButton_agregar;
    public JButton jButton_limpiar;
    public JButton jButton_finalizar;
    public JTable jTable_detalle;
    public JLabel jLabel_total_compra;
    public JLabel jLabel_total_dolares;
    public JLabel jLabel_multiplicacion;
    public JLabel id_usuario=new JLabel("1");
    
    public FormularioGestionCompras() {
        initializeFrame();
        createComponents();
        setupLayout();
        setVisible(true);
    }
    
    private void initializeFrame() {
        setTitle("Formulario de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setBounds(100, 100, 1600, 1000);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, 0, getHeight(), SECONDARY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }
    
    private void createComponents() {
        // Crear header
        crearHeader();
        
        // Panel contenedor del formulario
        formContainerPanel = new JPanel();
        formContainerPanel.setOpaque(false);
        formContainerPanel.setLayout(new BorderLayout(10, 10));
        
        // Crear secciones del formulario
        JPanel compraFormPanel = crearSeccionCompra();
        JPanel detallePanel = crearSeccionDetalle();
        
        // Usar BoxLayout horizontal para mejor distribución
        JPanel mainFormPanel = new JPanel();
        mainFormPanel.setOpaque(false);
        mainFormPanel.setLayout(new BoxLayout(mainFormPanel, BoxLayout.X_AXIS));
        mainFormPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Agregar componentes con espacio entre ellos
        mainFormPanel.add(compraFormPanel);
        mainFormPanel.add(Box.createHorizontalStrut(20)); // Espacio entre paneles
        mainFormPanel.add(detallePanel);
        
        formContainerPanel.add(mainFormPanel, BorderLayout.CENTER);
        
        // Crear panel de botones
        crearBotones();
    }
    
    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setBorder(new EmptyBorder(5, 5, 10, 5));
        
        // Título principal
        JLabel titleLabel = new JLabel("🛒 REGISTRO DE COMPRAS DE MERCANCÍA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Registre nuevas compras de mercancías");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
    }
    
    private JPanel crearSeccionCompra() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(500, 700));
        
        // Header de la sección
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(8, 12, 8, 12));
        headerPanel.setPreferredSize(new Dimension(0, 45));
        
        JLabel titleLabel = new JLabel("📋 DATOS DE LA COMPRA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Panel de campos del formulario con JScrollPane
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(15, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;
        
        // Campo código de compra
        JLabel codigoLabel = new JLabel("Código de Compra:");
        codigoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(codigoLabel, gbc);
        
        jTextField_codigo_compra = new JTextField();
        jTextField_codigo_compra.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_codigo_compra.setPreferredSize(new Dimension(0, 35));
        jTextField_codigo_compra.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        jTextField_codigo_compra.setBackground(LIGHT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(jTextField_codigo_compra, gbc);
        
        // Campo proveedor
        JLabel proveedorLabel = new JLabel("Proveedor:");
        proveedorLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(proveedorLabel, gbc);
        
        jComboBox_proveedor = new JComboBox<>();
        jComboBox_proveedor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_proveedor.setPreferredSize(new Dimension(0, 35));
        jComboBox_proveedor.setBackground(Color.WHITE);
        jComboBox_proveedor.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(jComboBox_proveedor, gbc);
        
        // Campo producto
        JLabel productoLabel = new JLabel("Producto:");
        productoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(productoLabel, gbc);
        
        jComboBox_producto = new JComboBox<>();
        jComboBox_producto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_producto.setPreferredSize(new Dimension(0, 35));
        jComboBox_producto.setBackground(Color.WHITE);
        jComboBox_producto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(jComboBox_producto, gbc);
        
        // Panel para cantidad empaquetada y entregada
        JPanel cantidadPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        cantidadPanel.setBackground(Color.WHITE);
        
        // Campo cantidad empaquetada
        JPanel empaquetadaPanel = new JPanel(new BorderLayout());
        empaquetadaPanel.setBackground(Color.WHITE);
        
        JLabel empaquetadaLabel = new JLabel("Cant. Empaquetada:");
        empaquetadaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        empaquetadaPanel.add(empaquetadaLabel, BorderLayout.NORTH);
        
        jTextField_cantidad_empaquetada = new JTextField();
        jTextField_cantidad_empaquetada.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_cantidad_empaquetada.setPreferredSize(new Dimension(0, 35));
        jTextField_cantidad_empaquetada.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        empaquetadaPanel.add(jTextField_cantidad_empaquetada, BorderLayout.CENTER);
        
        // Campo cantidad entregada
        JPanel entregadaPanel = new JPanel(new BorderLayout());
        entregadaPanel.setBackground(Color.WHITE);
        
        JLabel entregadaLabel = new JLabel("Cant. Entregada:");
        entregadaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        entregadaPanel.add(entregadaLabel, BorderLayout.NORTH);
        
        jTextField_cantidad_entregada = new JTextField();
        jTextField_cantidad_entregada.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_cantidad_entregada.setPreferredSize(new Dimension(0, 35));
        jTextField_cantidad_entregada.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        entregadaPanel.add(jTextField_cantidad_entregada, BorderLayout.CENTER);
        
        cantidadPanel.add(empaquetadaPanel);
        cantidadPanel.add(entregadaPanel);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(cantidadPanel, gbc);
        
        // Label para mostrar la multiplicación
        jLabel_multiplicacion = new JLabel("Total Unidades: 0");
        jLabel_multiplicacion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel_multiplicacion.setForeground(SECONDARY);
        jLabel_multiplicacion.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_multiplicacion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        jLabel_multiplicacion.setBackground(LIGHT);
        jLabel_multiplicacion.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(jLabel_multiplicacion, gbc);
        
        // Campo unidad
        JLabel unidadLabel = new JLabel("Unidad de Medida:");
        unidadLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(unidadLabel, gbc);
        
        jComboBox_unidad = new JComboBox<>(new String[]{"Unidades", "Kg", "Lt", "Cajas", "Paquetes", "Bultos", "Bolsas"});
        jComboBox_unidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_unidad.setPreferredSize(new Dimension(0, 35));
        jComboBox_unidad.setBackground(Color.WHITE);
        jComboBox_unidad.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(jComboBox_unidad, gbc);
        
        // Campo precio unitario
        JLabel precioLabel = new JLabel("Precio Unitario ($):");
        precioLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(precioLabel, gbc);
        
        jTextField_precio = new JTextField();
        jTextField_precio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_precio.setPreferredSize(new Dimension(0, 35));
        jTextField_precio.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(jTextField_precio, gbc);
        
        // Panel para totales (Bolívares y Dólares)
        JPanel totalesPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        totalesPanel.setBackground(Color.WHITE);
        
        // Campo total en bolívares
        JPanel totalBsPanel = new JPanel(new BorderLayout());
        totalBsPanel.setBackground(Color.WHITE);
        
        JLabel totalBsLabel = new JLabel("Total (Bs):");
        totalBsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalBsPanel.add(totalBsLabel, BorderLayout.NORTH);
        
        jTextField_total_bs = new JTextField();
        jTextField_total_bs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_total_bs.setPreferredSize(new Dimension(0, 35));
        jTextField_total_bs.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS, 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        jTextField_total_bs.setBackground(LIGHT);
        jTextField_total_bs.setEditable(false);
        totalBsPanel.add(jTextField_total_bs, BorderLayout.CENTER);
        
        // Campo total en dólares
        JPanel totalUsdPanel = new JPanel(new BorderLayout());
        totalUsdPanel.setBackground(Color.WHITE);
        
        JLabel totalUsdLabel = new JLabel("Total (USD):");
        totalUsdLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalUsdPanel.add(totalUsdLabel, BorderLayout.NORTH);
        
        jTextField_total_usd = new JTextField();
        jTextField_total_usd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_total_usd.setPreferredSize(new Dimension(0, 35));
        jTextField_total_usd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 2), // Verde oscuro
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        jTextField_total_usd.setBackground(LIGHT);
        jTextField_total_usd.setEditable(false);
        totalUsdPanel.add(jTextField_total_usd, BorderLayout.CENTER);
        
        totalesPanel.add(totalBsPanel);
        totalesPanel.add(totalUsdPanel);
        
        gbc.gridx = 0;
        gbc.gridy = 12;
        formPanel.add(totalesPanel, gbc);
        
        // Botones de acción
        JPanel actionButtonsPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        actionButtonsPanel.setBackground(Color.WHITE);
        
        jButton_agregar = new JButton("➕ AGREGAR A LISTA");
        jButton_agregar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_agregar.setBackground(SUCCESS);
        jButton_agregar.setForeground(Color.WHITE);
        jButton_agregar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        jButton_agregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_agregar.setFocusPainted(false);
        
        actionButtonsPanel.add(jButton_agregar);
        
        gbc.gridx = 0;
        gbc.gridy = 13;
        formPanel.add(actionButtonsPanel, gbc);
        
        // Ensamblar el panel
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Agregar JScrollPane al formulario para asegurar que todos los componentes sean visibles
        JScrollPane scrollForm = new JScrollPane(formPanel);
        scrollForm.setBorder(BorderFactory.createEmptyBorder());
        scrollForm.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollForm, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearSeccionDetalle() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(800, 700));
        
        // Header de la sección
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(8, 12, 8, 12));
        headerPanel.setPreferredSize(new Dimension(0, 45));
        
        JLabel titleLabel = new JLabel("📊 DETALLE DE LA COMPRA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Tabla de detalle con nuevas columnas
        String[] columnNames = {"Producto", "Cant. Emp.", "Cant. Ent.", "Total Unid.", "Precio Unit.", "Sub Total"};
        Object[][] data = {};
        
        jTable_detalle = new JTable(data, columnNames);
        jTable_detalle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable_detalle.setRowHeight(25);
        jTable_detalle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable_detalle.getTableHeader().setBackground(DARK);
        jTable_detalle.getTableHeader().setForeground(WHITE);
        jTable_detalle.setSelectionBackground(SECONDARY);
        jTable_detalle.setSelectionForeground(Color.WHITE);
        jTable_detalle.setGridColor(LIGHT);
        
        JScrollPane scrollTable = new JScrollPane(jTable_detalle);
        scrollTable.setBorder(BorderFactory.createLineBorder(SECONDARY, 1));
        scrollTable.setPreferredSize(new Dimension(0, 500));
        
        // Panel de totales (bolívares y dólares)
        JPanel totalesPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        totalesPanel.setBackground(LIGHT);
        totalesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        totalesPanel.setPreferredSize(new Dimension(0, 80));
        
        // Fila para bolívares
        JPanel panelBs = new JPanel(new BorderLayout());
        panelBs.setBackground(LIGHT);
        
        JLabel totalBsLabel = new JLabel("TOTAL GENERAL EN BOLÍVARES:");
        totalBsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalBsLabel.setForeground(DARK);
        
        jLabel_total_compra = new JLabel("Bs 0,00");
        jLabel_total_compra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_total_compra.setForeground(SUCCESS);
        jLabel_total_compra.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panelBs.add(totalBsLabel, BorderLayout.WEST);
        panelBs.add(jLabel_total_compra, BorderLayout.CENTER);
        
        // Fila para dólares
        JPanel panelDolares = new JPanel(new BorderLayout());
        panelDolares.setBackground(LIGHT);
        
        JLabel totalDolaresLabel = new JLabel("TOTAL GENERAL EN DÓLARES:");
        totalDolaresLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalDolaresLabel.setForeground(DARK);
        
        jLabel_total_dolares = new JLabel("$ 0.00");
        jLabel_total_dolares.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_total_dolares.setForeground(new Color(0, 100, 0)); // Verde oscuro
        jLabel_total_dolares.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panelDolares.add(totalDolaresLabel, BorderLayout.WEST);
        panelDolares.add(jLabel_total_dolares, BorderLayout.CENTER);
        
        totalesPanel.add(panelBs);
        totalesPanel.add(panelDolares);
        
        // Ensamblar el panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollTable, BorderLayout.CENTER);
        panel.add(totalesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void crearBotones() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Botón finalizar compra
        jButton_finalizar = new JButton("✅ FINALIZAR COMPRA");
        jButton_finalizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_finalizar.setBackground(SUCCESS);
        jButton_finalizar.setForeground(Color.WHITE);
        jButton_finalizar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        jButton_finalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_finalizar.setFocusPainted(false);
        
        // Botón limpiar
        jButton_limpiar = new JButton("🗑️ LIMPIAR TODO");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);
        jButton_limpiar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        jButton_limpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_limpiar.setFocusPainted(false);
        
        buttonPanel.add(jButton_finalizar);
        buttonPanel.add(jButton_limpiar);
    }
    
    // Métodos para cargar datos en los combobox
    public void cargarProveedores(List<String> proveedores) {
        jComboBox_proveedor.removeAllItems();
        jComboBox_proveedor.addItem("Seleccionar proveedor");
        for (String proveedor : proveedores) {
            jComboBox_proveedor.addItem(proveedor);
        }
    }
    
    public void cargarProductos(List<String> productos) {
        jComboBox_producto.removeAllItems();
        jComboBox_producto.addItem("Seleccionar producto");
        for (String producto : productos) {
            jComboBox_producto.addItem(producto);
        }
    }
    
    // Métodos para obtener datos del formulario
    public String getCodigoCompra() {
        return jTextField_codigo_compra.getText().trim();
    }
    
    public String getProveedorSeleccionado() {
        return (String) jComboBox_proveedor.getSelectedItem();
    }
    
    public String getProductoSeleccionado() {
        return (String) jComboBox_producto.getSelectedItem();
    }
    
    public int getCantidadEmpaquetada() {
        try {
            return Integer.parseInt(jTextField_cantidad_empaquetada.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public int getCantidadEntregada() {
        try {
            return Integer.parseInt(jTextField_cantidad_entregada.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public double getPrecioUnitario() {
        try {
            return Double.parseDouble(jTextField_precio.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public String getUnidadMedida() {
        return (String) jComboBox_unidad.getSelectedItem();
    }
    
    // Métodos para actualizar la vista
    public void setCodigoCompra(String codigo) {
        jTextField_codigo_compra.setText(codigo);
    }
    
    public void actualizarTotalUnidades(int totalUnidades) {
        jLabel_multiplicacion.setText("Total Unidades: " + totalUnidades);
    }
    
    public void actualizarTotalProducto(double totalBs, double totalUsd) {
        jTextField_total_bs.setText(String.format("Bs %.2f", totalBs));
        jTextField_total_usd.setText(String.format("$ %.2f", totalUsd));
    }
    
    public void actualizarTotalCompra(double totalGeneral) {
        jLabel_total_compra.setText(String.format("Bs %.2f", totalGeneral));
    }
    
    // Nuevo método para actualizar el total en dólares
    public void actualizarTotalDolares(double totalDolares) {
        jLabel_total_dolares.setText(String.format("$ %.2f", totalDolares));
    }
    
    public void agregarFilaTabla(Object[] fila) {
        DefaultTableModel model = (DefaultTableModel) jTable_detalle.getModel();
        model.addRow(fila);
    }
    
    public void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) jTable_detalle.getModel();
        model.setRowCount(0);
    }
    
    public void limpiarCamposProducto() {
        jComboBox_producto.setSelectedIndex(0);
        jTextField_cantidad_empaquetada.setText("");
        jTextField_cantidad_entregada.setText("");
        jTextField_precio.setText("");
        jTextField_total_bs.setText("Bs 0.00");
        jTextField_total_usd.setText("$ 0.00");
        jLabel_multiplicacion.setText("Total Unidades: 0");
    }
    
    public void limpiarFormulario() {
        jTextField_codigo_compra.setText("");
        jComboBox_proveedor.setSelectedIndex(0);
        jComboBox_unidad.setSelectedIndex(0);
        limpiarCamposProducto();
        limpiarTabla();
        actualizarTotalCompra(0.0);
        actualizarTotalDolares(0.0);
    }
    
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    public boolean confirmarAccion(String mensaje, String titulo) {
        int confirm = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }
    
    private void setupLayout() {
        // Agregar componentes al frame
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(formContainerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // Hacer responsive
        pack();
        setMinimumSize(new Dimension(900, 700));
    }
    
    // Getters para los botones (para que el controlador pueda agregar listeners)
    public JButton getBotonAgregar() { return jButton_agregar; }
    public JButton getBotonLimpiar() { return jButton_limpiar; }
    public JButton getBotonFinalizar() { return jButton_finalizar; }
    
    // Getters para los campos (para que el controlador pueda agregar listeners)
    public JTextField getCampoCodigoCompra() { return jTextField_codigo_compra; }
    public JTextField getCampoCantidadEmpaquetada() { return jTextField_cantidad_empaquetada; }
    public JTextField getCampoCantidadEntregada() { return jTextField_cantidad_entregada; }
    public JTextField getCampoPrecio() { return jTextField_precio; }
    public JComboBox<String> getComboProducto() { return jComboBox_producto; }
}