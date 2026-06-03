/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.gestion;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.toedter.calendar.JDateChooser;
import consultas_bd.consulta_inventario;
import consultas_bd.consulta_movimiento_inventario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionarInventario extends JInternalFrame {
    // Colores del tema
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);
    
    // Componentes públicos
    public JPanel mainPanel;
    public JButton[] tabButtons;
    public JPanel tabContentPanel;
    
    // Componentes del Dashboard
    public JPanel dashboardPanel;
    public JPanel statsPanel;
    public JPanel topProductsPanel;
    public JPanel lowStockPanel;
    
    // Componentes de Productos
    public JMenuItem menuStock = new JMenuItem("Modificar Stock Minimo");
    public JPopupMenu menu1=new JPopupMenu();
    public JPanel productsPanel;
    public JPanel productsFiltersPanel;
    public JPanel productsTablePanel;
    public JLabel catLabel;
    public JComboBox<String> categoryCombo;
    public JLabel stockLabel;
    public JComboBox<String> stockCombo;
    public JLabel searchLabel;
    public JTextField searchField;
    public JButton filterBtn;
    public JButton newProductBtn;
    public JTable productsTable;
    
    // Componentes de Movimientos
    public JPanel movementsPanel;
    public JPanel movementsFiltersPanel;
    public JPanel movementsTablePanel;
    public JLabel desdeLabel;
    public JDateChooser desdeField;
    public JLabel hastaLabel;
    public JDateChooser hastaField;
    public JLabel tipoLabel;
    public JComboBox<String> tipoCombo;
    public JButton movementsFilterBtn;
    public JTable movementsTable;
   
    public GestionarInventario() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Sistema de Gestión de Inventario");
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        
        // Panel principal con BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        
        // Header
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        
        // Centro con pestañas y contenido
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(createTabButtons(), BorderLayout.NORTH);
        
        // Crear el panel de contenido de pestañas
        tabContentPanel = createTabContent();
        centerPanel.add(tabContentPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        
        // Activar primer tab después de que todo esté inicializado
        SwingUtilities.invokeLater(() -> switchTab(0));
        
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY, 
                    getWidth(), 0, SECONDARY
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 100));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("📦 Sistema de Gestión de Inventario");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Control y administración completa de productos");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);
        
        header.add(textPanel, BorderLayout.CENTER);
        menu1.add(menuStock);
        return header;
    }
    
    private JPanel createTabButtons() {
        JPanel tabPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        
        String[] tabNames = {"Datos de inventario", "Productos", "Movimientos"};
        tabButtons = new JButton[tabNames.length];
        
        for (int i = 0; i < tabNames.length; i++) {
            tabButtons[i] = createTabButton(tabNames[i]);
            final int tabIndex = i;
            tabButtons[i].addActionListener(e -> switchTab(tabIndex));
            tabPanel.add(tabButtons[i]);
        }
        
        return tabPanel;
    }
    
    private JButton createTabButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(DARK);
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private JPanel createTabContent() {
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Agregar los diferentes paneles de contenido
        contentPanel.add(createDashboardPanel(), "Estado del inventario");
        contentPanel.add(createProductsPanel(), "Productos");
        contentPanel.add(createMovementsPanel(), "Movimientos");
        
        return contentPanel;
    }
    
    public void switchTab(int tabIndex) {
        // Resetear todos los botones
        for (JButton button : tabButtons) {
            button.setBackground(Color.WHITE);
            button.setForeground(DARK);
        }
        
        // Activar botón seleccionado
        tabButtons[tabIndex].setBackground(SECONDARY);
        tabButtons[tabIndex].setForeground(Color.WHITE);
        
        // Cambiar contenido
        if (tabContentPanel != null) {
            CardLayout cl = (CardLayout) tabContentPanel.getLayout();
            String[] tabNames = {"Estado del inventario", "Productos", "Movimientos"};
            cl.show(tabContentPanel, tabNames[tabIndex]);
        }
    }
    
    // PANEL DASHBOARD
    public JPanel createDashboardPanel() {
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(new Color(248, 249, 250));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(248, 249, 250));
        
        // Estadísticas
        contentPanel.add(createStatsPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Panel para productos más vendidos y stock bajo en horizontal
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setBackground(new Color(248, 249, 250));
        
        // Productos más vendidos
        bottomPanel.add(createTopProductsPanel());
        
        // Productos con stock bajo
        bottomPanel.add(createLowStockPanel());
        
        contentPanel.add(bottomPanel);
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);
        
        return dashboardPanel;
    }
    
    public consulta_inventario sql=new consulta_inventario();
    public String[] datos=sql.cargar_resumen_inventario();
    public JPanel createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(248, 249, 250));
        
        String[] stats = {
            "Total de Productos|BS "+datos[0]+"| ",
            "Valor Total Inventario|BS "+datos[1]+"| ", 
            "Productos Bajos en Stock|"+datos[2]+"| ",
            "Total de categorias|"+datos[3]+"| "
        };
        
        for (String stat : stats) {
            String[] parts = stat.split("\\|");
            statsPanel.add(createStatCard(parts[0], parts[1], parts[2]));
        }
        
        return statsPanel;
    }
    
    public JPanel createStatCard(String title, String value, String trend) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(4, 0, 0, 0, SECONDARY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(PRIMARY);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel trendLabel = new JLabel(trend);
        trendLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        trendLabel.setForeground(trend.contains("+") ? SUCCESS : DANGER);
        trendLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(trendLabel);
        
        return card;
    }
      public JTable topProductsTabl;
    public JPanel createTopProductsPanel() {
        topProductsPanel = createCardPanel("Productos Más Vendidos");
        topProductsPanel.setLayout(new BorderLayout());
        
        // Columnas para la tabla de productos más vendidos
        String[] columns = {"C°", "Producto", "Ventas Totales", "Categoría", "Ingresos"};
        Object[][] data = {
            {"1", "Laptop HP 15\"", 45, "Electrónicos", "BS 38,250.00"},
            {"2", "Smartphone Samsung Galaxy", 38, "Electrónicos", "BS 24,700.00"},
            {"3", "Tablet iPad 10\"", 32, "Electrónicos", "BS 14,400.00"},
            {"4", "Zapatos Deportivos", 28, "Ropa", "BS 2,100.00"},
            {"5", "Camiseta Básica", 25, "Ropa", "BS 375.00"},
            {"6", "Mouse Inalámbrico", 18, "Electrónicos", "BS 450.00"},
            {"7", "Auriculares Bluetooth", 15, "Electrónicos", "BS 1,200.00"},
            {"8", "Monitor 27\"", 12, "Electrónicos", "BS 7,200.00"}
        };
        
        topProductsTabl=new JTable();
        JScrollPane scrollPane = new JScrollPane(topProductsTabl);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        
        topProductsPanel.add(scrollPane, BorderLayout.CENTER);
        
        return topProductsPanel;
    }
    
    public JTable table_stock_bajo;
    public JPanel createLowStockPanel() {
        lowStockPanel = createCardPanel("Productos con Stock Bajo");
        lowStockPanel.setLayout(new BorderLayout());
        
        String[] columns = {"Producto", "Categoría", "Stock Actual", "Stock Mínimo", "Estado"};
        Object[][] data = {
            {"Mouse Inalámbrico", "Electrónicos", 2, 5, "Stock Bajo"},
            {"Cargador USB-C", "Electrónicos", 3, 10, "Stock Bajo"}, 
            {"Auriculares Bluetooth", "Electrónicos", 1, 5, "Stock Bajo"},
            {"Monitor 27\"", "Electrónicos", 0, 3, "Sin Stock"},
            {"Teclado Mecánico", "Electrónicos", 4, 8, "Stock Bajo"}
        };
        
        table_stock_bajo = createTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(table_stock_bajo);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        
        lowStockPanel.add(scrollPane, BorderLayout.CENTER);
        
        return lowStockPanel;
    }
    
    // PANEL PRODUCTOS
    public JPanel createProductsPanel() {
        productsPanel = new JPanel(new BorderLayout());
        productsPanel.setBackground(new Color(248, 249, 250));
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));
        
        // Filtros
        contentPanel.add(createProductsFiltersPanel(), BorderLayout.NORTH);
        
        // Lista de productos
        contentPanel.add(createProductsTablePanel(), BorderLayout.CENTER);
        
        productsPanel.add(contentPanel, BorderLayout.CENTER);
        return productsPanel;
    }
    
    public JPanel createProductsFiltersPanel() {
        productsFiltersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productsFiltersPanel.setBackground(new Color(248, 249, 250));
        productsFiltersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Categoría
        catLabel = new JLabel("Categoría:");
        catLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        categoryCombo = new JComboBox<>(new String[]{"Todas", "Electrónicos", "Ropa", "Hogar", "Deportes"});
        
        // Estado Stock
        stockLabel = new JLabel("Estado Stock:");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        stockCombo = new JComboBox<>(new String[]{"Todos", "Stock Bajo"});
        
        // Búsqueda
        searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));
        
        // Botones
        filterBtn = createButton("Filtrar", SECONDARY);
        newProductBtn = createButton("Nuevo Producto", SUCCESS);
        
        productsFiltersPanel.add(catLabel);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        productsFiltersPanel.add(categoryCombo);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        productsFiltersPanel.add(stockLabel);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        productsFiltersPanel.add(stockCombo);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        productsFiltersPanel.add(searchLabel);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        productsFiltersPanel.add(searchField);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        productsFiltersPanel.add(filterBtn);
        productsFiltersPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        productsFiltersPanel.add(newProductBtn);
        
        return productsFiltersPanel;
    }
    
    public JPanel createProductsTablePanel() {
        productsTablePanel = createCardPanel("Lista de Productos");
        productsTablePanel.setLayout(new BorderLayout());
        
        String[] columns = {"Código", "Nombre", "Categoría", "Precio", "Stock", "Stock Mín.", "Estado", "Acciones"};
        Object[][] data = {
            {"PROD001", "Laptop HP 15\"", "Electrónicos", "$850.00", 15, 5, "Normal", "Editar | Eliminar"},
            {"PROD002", "Smartphone Samsung Galaxy", "Electrónicos", "$650.00", 22, 10, "Normal", "Editar | Eliminar"},
            {"PROD003", "Tablet iPad 10\"", "Electrónicos", "$450.00", 8, 5, "Stock Bajo", "Editar | Eliminar"},
            {"PROD004", "Camiseta Básica", "Ropa", "$15.00", 45, 20, "Normal", "Editar | Eliminar"},
            {"PROD005", "Zapatos Deportivos", "Ropa", "$75.00", 12, 10, "Normal", "Editar | Eliminar"},
            {"PROD006", "Mouse Inalámbrico", "Electrónicos", "$25.00", 2, 5, "Stock Bajo", "Editar | Eliminar"}
        };
        
        productsTable = createTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        
        productsTablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return productsTablePanel;
    }
    
    // PANEL MOVIMIENTOS
    public JPanel createMovementsPanel() {
        movementsPanel = new JPanel(new BorderLayout());
        movementsPanel.setBackground(new Color(248, 249, 250));
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));
        
        // Filtros
        contentPanel.add(createMovementsFiltersPanel(), BorderLayout.NORTH);
        
        // Lista de movimientos
        contentPanel.add(createMovementsTablePanel(), BorderLayout.CENTER);
        
        movementsPanel.add(contentPanel, BorderLayout.CENTER);
        return movementsPanel;
    }
    
    public JPanel createMovementsFiltersPanel() {
        movementsFiltersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        movementsFiltersPanel.setBackground(new Color(248, 249, 250));
        movementsFiltersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Fechas
        desdeLabel = new JLabel("Desde:");
        desdeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        desdeField = new JDateChooser();
        desdeField.setPreferredSize(new Dimension(120, 30));
        
        hastaLabel = new JLabel("Hasta:");
        hastaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        hastaField = new JDateChooser();
        hastaField.setPreferredSize(new Dimension(120, 30));
        
        // Tipo Movimiento
        tipoLabel = new JLabel("Tipo Movimiento:");
        tipoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        tipoCombo = new JComboBox<>(new String[]{"Todos","creacion","eliminacion","modificacion","agregado","vendido"});
        
        // Botones
        movementsFilterBtn = createButton("Filtrar", SECONDARY);
        
        movementsFiltersPanel.add(desdeLabel);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        movementsFiltersPanel.add(desdeField);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        movementsFiltersPanel.add(hastaLabel);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        movementsFiltersPanel.add(hastaField);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        movementsFiltersPanel.add(tipoLabel);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        movementsFiltersPanel.add(tipoCombo);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        movementsFiltersPanel.add(movementsFilterBtn);
        movementsFiltersPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        
        return movementsFiltersPanel;
    }
    
    public JPanel createMovementsTablePanel() {
        movementsTablePanel = createCardPanel("Movimientos de Inventario");
        movementsTablePanel.setLayout(new BorderLayout());
        
        String[] columns = {"Fecha", "Producto", "Tipo", "Cantidad", "Motivo", "Acciones"};
        Object[][] data = {
            {"2024-01-15", "Laptop HP 15\"", "Entrada", 10, "Compra a proveedor", "Eliminar"},
            {"2024-01-16", "Smartphone Samsung Galaxy", "Salida", 5, "Venta a cliente", "Eliminar"},
            {"2024-01-17", "Tablet iPad 10\"", "Ajuste", 2, "Ajuste por inventario físico", "Eliminar"},
            {"2024-01-18", "Camiseta Básica", "Entrada", 25, "Compra a proveedor", "Eliminar"}
        };
        
        movementsTable = createTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(movementsTable);
        
        movementsTablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return movementsTablePanel;
    }
    
    // MÉTODOS AUXILIARES
    public JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    public JPanel createCardPanel(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, SECONDARY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        card.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    public JTable createTable(String[] columns, Object[][] data) {
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
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(222, 226, 230));
        
        return table;
    }
}