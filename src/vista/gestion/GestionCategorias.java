/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.gestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GestionCategorias extends javax.swing.JInternalFrame {
    // Colores del sistema
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);
    
    // Componentes principales
    public JPanel contentPane;
    public JTabbedPane tabbedPane;
    // Paneles
    public JPanel categoriasPanel;
    public JPanel productosPanel;
    public static JLabel id_usuario;
    // Componentes de Categorías
    public JTable categoriasTable;
    public JTextField nombreCategoriaField;
    public JTextField buscarCategpriraField;
    public JTextArea descripcionCategoriaArea;
    public JButton agregarCategoriaButton;
    public JButton editarCategoriaButton;
    public JButton limpiarCategoriaButton;
    
    // Componentes de Productos
    public JTable productosTable;
    public JTextField nombreProductoField;
    public JTextField precioProductoUniField;
    public JTextField precioProductoVenField;
    public JComboBox<String> categoriaProductoCombo;
    public JTextArea descripcionProductoArea;
    public JButton agregarProductoButton;
    public JButton editarProductoButton=new JButton();
    public JButton limpiarProductoButton;
   // opciones categoria
    public JPopupMenu opciones;
    public JMenuItem JMenuItemseleccionar;
    public JMenuItem JMenuItemInactivar;
    public JMenuItem JMenuItemActivar;

    public GestionCategorias() {
        
        setTitle("ModuloGestión de Categorías");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        // Crear panel principal con gradiente
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
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        // Crear header
        crearHeader();
        
        // Crear pestañas
        crearPestanas();
        
        // Cargar datos iniciales
    }

    private void crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        
        id_usuario=new JLabel();
        JLabel titleLabel = new JLabel("📦 Gestión de Categorías y Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(20, 20, 5, 20));
        
        opciones=new JPopupMenu();
        JMenuItemseleccionar=new JMenuItem("Seleccionar");
        JMenuItemseleccionar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JMenuItemseleccionar.setForeground(Color.BLACK);
        
        JMenuItemInactivar=new JMenuItem("Inactivar");
        JMenuItemInactivar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JMenuItemInactivar.setForeground(Color.BLACK);
       
        JMenuItemActivar = new JMenuItem("Activar");
        JMenuItemActivar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JMenuItemActivar.setForeground(Color.BLACK);

        opciones.add(JMenuItemseleccionar);
        opciones.add(JMenuItemInactivar);
        opciones.add(JMenuItemActivar);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        
        contentPane.add(headerPanel, BorderLayout.NORTH);
    }

    private void crearPestanas() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        // Crear paneles para cada pestaña
        categoriasPanel = crearPanelCategorias();
       // productosPanel = crearPanelProductos();
        // Agregar pestañas
        tabbedPane.addTab("📋 Categorías", categoriasPanel);
       // tabbedPane.addTab("📦 Productos", productosPanel);
        
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
    }

    private JPanel crearPanelCategorias() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel de formulario
        JPanel formPanel = crearFormularioCategorias();
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Panel de tabla
        JPanel tablePanel = crearTablaCategorias();
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearFormularioCategorias() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titulo = new JLabel("Gestión de Categorías");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);
        
        // Nombre de categoría
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nombreLabel, gbc);
        
        nombreCategoriaField = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nombreCategoriaField, gbc);
           
        // Descripción
        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descripcionLabel, gbc);
      
          // Nombre de categoría
        JLabel BuscarLabel = new JLabel("Buscar categoria:");
        BuscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(BuscarLabel, gbc);
        
        buscarCategpriraField= new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(buscarCategpriraField, gbc);
      
        descripcionCategoriaArea = new JTextArea(3, 20);
        descripcionCategoriaArea.setLineWrap(true);
        descripcionCategoriaArea.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(descripcionCategoriaArea);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(scrollDescripcion, gbc);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botonesPanel.setBackground(Color.WHITE);
        
        agregarCategoriaButton = new JButton("Agregar");
        agregarCategoriaButton.setBackground(SUCCESS);
        agregarCategoriaButton.setForeground(Color.WHITE);
        
        editarCategoriaButton = new JButton("Editar");
        editarCategoriaButton.setBackground(SECONDARY);
        editarCategoriaButton.setForeground(Color.WHITE);
        
        
        limpiarCategoriaButton = new JButton("Limpiar");
        limpiarCategoriaButton.setBackground(WARNING);
        limpiarCategoriaButton.setForeground(Color.WHITE);
        
        botonesPanel.add(agregarCategoriaButton);
        botonesPanel.add(editarCategoriaButton);
        botonesPanel.add(limpiarCategoriaButton);
        
        agregarCategoriaButton.setPreferredSize(new Dimension(120, 35));
        editarCategoriaButton.setPreferredSize(new Dimension(100, 35));
        limpiarCategoriaButton.setPreferredSize(new Dimension(100, 35));
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(botonesPanel, gbc);
        
        return panel;
    }

    private JPanel crearTablaCategorias() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titulo = new JLabel("Lista de Categorías");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);
        
        // Tabla de categorías
        String[] columnNames = {"ID", "Nombre", "Descripción", "Estado", "Productos"};
        Object[][] data = {
            {"1", "Electrónicos", "Dispositivos electrónicos y gadgets", "Activo", "15"},
            {"2", "Ropa", "Prendas de vestir", "Activo", "23"},
            {"3", "Hogar", "Artículos para el hogar", "Activo", "12"},
            {"4", "Deportes", "Equipos deportivos", "Inactivo", "8"}
        };
        
        categoriasTable = new JTable(data, columnNames);
        categoriasTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoriasTable.setRowHeight(25);
        categoriasTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        categoriasTable.getTableHeader().setBackground(PRIMARY);
        categoriasTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(categoriasTable);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel de formulario
        JPanel formPanel = crearFormularioProductos();
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Panel de tabla
        JPanel tablePanel = crearTablaProductos();
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearFormularioProductos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titulo = new JLabel("Gestión de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);
        
        // Nombre del producto
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nombreLabel, gbc);
        
        nombreProductoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nombreProductoField, gbc);
        
        // Precio unitario
        JLabel precioLabel = new JLabel("Precio Unitario:");
        precioLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(precioLabel, gbc);
        
        precioProductoUniField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(precioProductoUniField, gbc);
        
        
        // Precio venta
        JLabel precioVenLabel = new JLabel("Precio Venta:");
        precioVenLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(precioVenLabel, gbc);
        
        precioProductoVenField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(precioProductoVenField, gbc);
        
        
        // Categoría
        JLabel categoriaLabel = new JLabel("Categoría:");
        categoriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(categoriaLabel, gbc);
        
        categoriaProductoCombo = new JComboBox();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(categoriaProductoCombo, gbc);
        
        // Descripción
        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(descripcionLabel, gbc);
        
        descripcionProductoArea = new JTextArea(3, 20);
        descripcionProductoArea.setLineWrap(true);
        descripcionProductoArea.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(descripcionProductoArea);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(scrollDescripcion, gbc);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botonesPanel.setBackground(Color.WHITE);
        
        agregarProductoButton = new JButton("Agregar");
        agregarProductoButton.setBackground(SUCCESS);
        agregarProductoButton.setForeground(Color.WHITE);
        
        editarProductoButton = new JButton("Editar");
        editarProductoButton.setBackground(SECONDARY);
        editarProductoButton.setForeground(Color.WHITE);
        
        
        limpiarProductoButton = new JButton("Limpiar");
        limpiarProductoButton.setBackground(WARNING);
        limpiarProductoButton.setForeground(Color.WHITE);
        
        botonesPanel.add(agregarProductoButton);
        botonesPanel.add(editarProductoButton);
        botonesPanel.add(limpiarProductoButton);

        agregarProductoButton.setPreferredSize(new Dimension(120, 35));
        editarProductoButton.setPreferredSize(new Dimension(100, 35));
        limpiarProductoButton.setPreferredSize(new Dimension(100, 35));
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(botonesPanel, gbc);
        
        return panel;
    }

    private JPanel crearTablaProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titulo = new JLabel("Lista de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);
        
        productosTable = new JTable();
        productosTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        productosTable.setRowHeight(25);
        productosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        productosTable.getTableHeader().setBackground(PRIMARY);
        productosTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(productosTable);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    
}