package vista.formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import consultas_bd.consulta_detalle_orden_compra;
import modelo.detalle_orden_de_compra;
import validaciones.validaciones.otros.validar_componente;

public class GestionCompras extends JInternalFrame{
    // Colores del sistema (mismos que SistemaContable)
    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);
    
    // Componentes principales
    public JPanel contentPane;
    
    // Componentes de la vista única
    public JTable consultaTable;
    public JTextField buscarProveedorField;
    public JDateChooser fechaInicioField;
    public JDateChooser fechaFinField;
    public JButton buscarButton;
    public JButton limpiarButton;
    public JButton exportarButton;
    public JPopupMenu JPopuMenu1=new JPopupMenu();
    public JMenuItem MenuItemDetalle=new JMenuItem("Ver detalle de venta");    

// Componentes de Métricas
    public JLabel totalComprasLabel=crearLabelEstadistica();
    public JLabel proveedoresActivosLabel=crearLabelEstadistica();
    public JLabel productosCompradosLabel=crearLabelEstadistica();
    public JLabel comprasMesLabel=crearLabelEstadistica();
    private consulta_detalle_orden_compra sql=new consulta_detalle_orden_compra();
    // Formatos
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public GestionCompras() {
        setTitle("Gestión de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1366, 768);
        setMinimumSize(new Dimension(1366, 768));
        
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
        
        // Crear contenido principal
        crearContenidoPrincipal();
        
        // Hacer la ventana responsive
        configurarResponsividad();
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
    }
    
    private void crearHeader() {
        JPopuMenu1.add(MenuItemDetalle);
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Título principal
        JLabel titleLabel = new JLabel("🔍 CONSULTA DE COMPRAS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Compras registradas");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setLayout(new BorderLayout());
        titleContainer.add(titleLabel, BorderLayout.CENTER);
        titleContainer.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        
        contentPane.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void crearContenidoPrincipal() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel superior con métricas
        JPanel metricasPanel = crearPanelMetricas();
        
        // Panel central con búsqueda y tabla
        JPanel consultaPanel = crearPanelConsulta();
        
        mainPanel.add(metricasPanel, BorderLayout.NORTH);
        mainPanel.add(consultaPanel, BorderLayout.CENTER);
        
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelMetricas() {
        consulta_detalle_orden_compra.cargar_metrica car=sql.cargar_datos_compra();
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(1, 4, 15, 15));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1), 
            "📊 Métricas de Compras", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), 
            Color.WHITE
        ));
        // Crear tarjetas de métricas
        panel.add(crearTarjetaMetrica("📦 Monto compras Mensuales", ""+validar_componente.limitar_decimales(car.getTotal_compra())+"", totalComprasLabel, PRIMARY));
        panel.add(crearTarjetaMetrica("🏢 Total de compras Mensuales", ""+car.getNumero_de_compras()+"", proveedoresActivosLabel, SECONDARY));
        panel.add(crearTarjetaMetrica("📊 Promedio de compras Mensuales", ""+validar_componente.limitar_decimales(car.getPromedio_compra())+"", productosCompradosLabel, SUCCESS));
       // panel.add(crearTarjetaMetrica("📅 Este Mes", "42", comprasMesLabel, WARNING));
        
        return panel;
    }
    
    private JPanel crearTarjetaMetrica(String titulo, String valor, JLabel label, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        label.setText(valor);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(label, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel crearPanelConsulta() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 10));
        
        // Panel de búsqueda
        JPanel searchPanel = crearPanelBusqueda();
        
        // Tabla de consulta
        JPanel tablePanel = crearPanelTabla();
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1), 
            "🔍 Búsqueda de Compras", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), 
            Color.WHITE
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;
        
        // Fila 1: Campo de búsqueda por proveedor (único campo)
        agregarCampoBusqueda(panel, gbc, 0, "Buscar por proveedores o codigo de compra:", buscarProveedorField = crearTextField());
        
        // Fila 2: Rango de fechas
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        
        JLabel fechaInicioLabel = new JLabel("Fecha Inicio:");
        fechaInicioLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fechaInicioLabel.setForeground(Color.WHITE);
        panel.add(fechaInicioLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        fechaInicioField = crearDateChooser();
        panel.add(fechaInicioField, gbc);
        
        // Fila 3: Fecha fin
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        
        JLabel fechaFinLabel = new JLabel("Fecha Fin:");
        fechaFinLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fechaFinLabel.setForeground(Color.WHITE);
        panel.add(fechaFinLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        fechaFinField = crearDateChooser();
        panel.add(fechaFinField, gbc);
        
        // Panel de botones
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        buscarButton = crearBoton("🔍 Buscar", SUCCESS);
        limpiarButton = crearBoton("🧹 Limpiar", WARNING);
        exportarButton = crearBoton("📊 Exportar", SECONDARY);
        
        buttonPanel.add(buscarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(exportarButton);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private void agregarCampoBusqueda(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
        
        // Espacio vacío para alineación
        gbc.gridx = 2;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        panel.add(Box.createHorizontalStrut(10), gbc);
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        
        consultaTable = crearTablaConsulta();
        JScrollPane tableScroll = new JScrollPane(consultaTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            "📋 Resultados de la Búsqueda",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            Color.WHITE
        ));
        
        panel.add(tableScroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JLabel crearLabelEstadistica() {
        JLabel label = new JLabel();
        label.setOpaque(false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    private JTextField crearTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(0, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JDateChooser crearDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateChooser.setPreferredSize(new Dimension(0, 35));
        return dateChooser;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }
    
    private JTable crearTablaConsulta() {
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setSelectionBackground(SECONDARY);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(LIGHT);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Configurar modelo de tabla para consulta
        String[] columnNames = {"ID", "Proveedor", "Producto", "Cantidad", "Fecha", "Estado", "Usuario"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);
        
        return table;
    }
    
    private void configurarResponsividad() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                actualizarTamanosResponsivos();
            }
        });
    }
    
    private void actualizarTamanosResponsivos() {
        int width = getWidth();
        
        Font titleFont = new Font("Segoe UI", Font.BOLD, Math.max(24, Math.min(32, width / 40)));
        Font subtitleFont = new Font("Segoe UI", Font.PLAIN, Math.max(12, Math.min(16, width / 80)));
        
        Component[] components = contentPane.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String text = label.getText();
                if (text != null) {
                    if (text.contains("CONSULTA")) {
                        label.setFont(titleFont);
                    } else if (text.contains("Sistema de consulta")) {
                        label.setFont(subtitleFont);
                    }
                }
            }
        }
    }
    
    // Métodos públicos para acceso a componentes
    public JTable getConsultaTable() { return consultaTable; }
    public JTextField getBuscarProveedorField() { return buscarProveedorField; }
    public JDateChooser getFechaInicioField() { return fechaInicioField; }
    public JDateChooser getFechaFinField() { return fechaFinField; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getExportarButton() { return exportarButton; }
    
    // Métodos para obtener fechas formateadas
    public String getFechaInicioFormateada() {
        if (fechaInicioField.getDate() != null) {
            return dateFormat.format(fechaInicioField.getDate());
        }
        return null;
    }
    
    public String getFechaFinFormateada() {
        if (fechaFinField.getDate() != null) {
            return dateFormat.format(fechaFinField.getDate());
        }
        return null;
    }
    
    // Método para limpiar todos los filtros
    public void limpiarFiltros() {
        buscarProveedorField.setText("");
        fechaInicioField.setDate(null);
        fechaFinField.setDate(null);
    }
    
    // Métodos para actualizar métricas
    public void setTotalCompras(String valor) { totalComprasLabel.setText(valor); }
    public void setProveedoresActivos(String valor) { proveedoresActivosLabel.setText(valor); }
    public void setProductosComprados(String valor) { productosCompradosLabel.setText(valor); }
    public void setComprasMes(String valor) { comprasMesLabel.setText(valor); }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GestionCompras frame = new GestionCompras();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}