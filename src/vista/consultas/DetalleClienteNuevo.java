package vista.consultas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetalleClienteNuevo extends JFrame {
    
    // Colores del sistema (públicos y estáticos)
    public static final Color PRIMARY = new Color(110, 131, 183);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color GRAY = new Color(149, 165, 166);
    
    // Componentes principales (públicos y estáticos)
    public static JPanel contentPane;
    public static JPanel jPanel2;
    public static JPanel jPanel1;
    public static JPanel jPanel3;
    public static JPanel jPanel4;
    public static JPanel jPanel6;
    
    // Labels de imagen y título (públicos y estáticos)
    public static JLabel jLabel12;
    public static JLabel jLabel_id_cliente;
    public static JLabel jLabel15;
    
    // Labels de información básica (públicos y estáticos)
    public static JLabel jLabel21;
    public static JLabel jLabel_nombre;
    public static JLabel jLabel23;
    public static JLabel jLabel_apellido;
    public static JLabel jLabel25;
    public static JLabel jLabel_cedula;
    
    // Labels de información de contacto (públicos y estáticos)
    public static JLabel jLabel16;
    public static JLabel jLabel_telefono;
    public static JLabel jLabel17;
    public static JLabel jLabel_ubicacion;
    
    // Componentes de historial (públicos y estáticos)
    public static JTabbedPane jTabbedPane1;
    public static JScrollPane jScrollPane2;
    public static JScrollPane jScrollPane3;
    public static JList<String> jList_compra;
    public static JList<String> jList_pago;
    
    public DetalleClienteNuevo() {
        initializeFrame();
        createComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeFrame() {
        setTitle("Detalle del Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        
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
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
    }
    
    private void createComponents() {
        // Panel principal
        jPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Crear efecto de sombra suave
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                
                // Fondo blanco con bordes redondeados
                g2d.setColor(WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 20, 20);
            }
        };
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new GridBagLayout());
        
        // Imagen de usuario (placeholder)
        jLabel12 = new JLabel();
        jLabel12.setPreferredSize(new Dimension(200, 200));
        jLabel12.setBorder(BorderFactory.createLineBorder(SECONDARY, 2));
        jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel12.setText("[Imagen]");
        jLabel12.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel12.setForeground(GRAY);
        
        // Títulos
        jLabel_id_cliente = new JLabel("Cliente");
        jLabel_id_cliente.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel_id_cliente.setForeground(DARK);
        jLabel_id_cliente.setHorizontalAlignment(SwingConstants.LEFT);
        
        jLabel15 = new JLabel("Cliente");
        jLabel15.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel15.setForeground(DARK);
        jLabel15.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Panel de información básica
        jPanel3 = createInfoPanel("Información básica");
        jPanel3.setLayout(new GridBagLayout());
        
        jLabel21 = new JLabel("Nombre:");
        jLabel21.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel21.setForeground(DARK);
        
        jLabel_nombre = new JLabel("Nombre:");
        jLabel_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel_nombre.setForeground(DARK);
        
        jLabel23 = new JLabel("Apellido:");
        jLabel23.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel23.setForeground(DARK);
        
        jLabel_apellido = new JLabel("Apellido");
        jLabel_apellido.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel_apellido.setForeground(DARK);
        
        jLabel25 = new JLabel("Cédula:");
        jLabel25.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel25.setForeground(DARK);
        
        jLabel_cedula = new JLabel("Cedula");
        jLabel_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel_cedula.setForeground(DARK);
        
        // Panel de información de contacto
        jPanel1 = createInfoPanel("Información de contacto");
        jPanel1.setLayout(new GridBagLayout());
        
        jLabel16 = new JLabel("Número telefónico:");
        jLabel16.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel16.setForeground(DARK);
        
        jLabel_telefono = new JLabel("Nombre:");
        jLabel_telefono.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel_telefono.setForeground(DARK);
        
        jLabel17 = new JLabel("Ubicación:");
        jLabel17.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel17.setForeground(DARK);
        
        jLabel_ubicacion = new JLabel("Apellido:");
        jLabel_ubicacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel_ubicacion.setForeground(DARK);
        
        // Panel de historial con pestañas
        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jTabbedPane1.setForeground(DARK);
        jTabbedPane1.setBackground(WHITE);
        jTabbedPane1.setBorder(BorderFactory.createLineBorder(SECONDARY, 2));
        
        // Panel de historial de compras
        jPanel4 = new JPanel(new BorderLayout());
        jPanel4.setBackground(WHITE);
        
        jList_compra = new JList<>();
        jList_compra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jList_compra.setBackground(WHITE);
        jList_compra.setForeground(DARK);
        
        jScrollPane2 = new JScrollPane(jList_compra);
        jScrollPane2.setBorder(BorderFactory.createLineBorder(GRAY, 1));
        jScrollPane2.setBackground(WHITE);
        
        jPanel4.add(jScrollPane2, BorderLayout.CENTER);
        
        // Panel de historial de pagos
        jPanel6 = new JPanel(new BorderLayout());
        jPanel6.setBackground(WHITE);
        
        jList_pago = new JList<>();
        jList_pago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jList_pago.setBackground(WHITE);
        jList_pago.setForeground(DARK);
        
        jScrollPane3 = new JScrollPane(jList_pago);
        jScrollPane3.setBorder(BorderFactory.createLineBorder(GRAY, 1));
        jScrollPane3.setBackground(WHITE);
        
        jPanel6.add(jScrollPane3, BorderLayout.CENTER);
        
        // Agregar pestañas
        jTabbedPane1.addTab("Historial de compra", jPanel4);
        jTabbedPane1.addTab("Historial de pago", jPanel6);
    }
    
    private JPanel createInfoPanel(String title) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo con bordes redondeados
                g2d.setColor(WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Borde decorativo
                g2d.setColor(SECONDARY);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY, 2), 
            title, 
            TitledBorder.DEFAULT_JUSTIFICATION, 
            TitledBorder.DEFAULT_POSITION, 
            new Font("Segoe UI", Font.BOLD, 20), 
            DARK
        ));
        return panel;
    }
    
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Panel superior con imagen y títulos
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        
        // Imagen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(jLabel12, gbc);
        
        // Títulos
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        topPanel.add(jLabel_id_cliente, gbc);
        
        gbc.gridy = 1;
        topPanel.add(jLabel15, gbc);
        
        // Panel de información básica
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        topPanel.add(jPanel3, gbc);
        
        // Agregar componentes al panel de información básica
        setupBasicInfoPanel();
        
        // Panel de información de contacto
        setupContactInfoPanel();
        
        // Panel principal
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        jPanel2.add(topPanel, gbc);
        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel2.add(jPanel1, gbc);
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        jPanel2.add(jTabbedPane1, gbc);
        
        contentPane.add(jPanel2, BorderLayout.CENTER);
    }
    
    private void setupBasicInfoPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1: Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel3.add(jLabel21, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        jPanel3.add(jLabel_nombre, gbc);
        
        // Fila 2: Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        jPanel3.add(jLabel23, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        jPanel3.add(jLabel_apellido, gbc);
        
        // Fila 3: Cédula
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        jPanel3.add(jLabel25, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel3.add(jLabel_cedula, gbc);
    }
    
    private void setupContactInfoPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1: Teléfono
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel1.add(jLabel16, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        jPanel1.add(jLabel_telefono, gbc);
        
        // Fila 2: Ubicación
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        jPanel1.add(jLabel17, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        jPanel1.add(jLabel_ubicacion, gbc);
    }
    
    private void setupEventListeners() {
        // Agregar listeners para las listas
        jList_compra.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Lógica cuando se selecciona un elemento de compra
            }
        });
        
        jList_pago.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Lógica cuando se selecciona un elemento de pago
            }
        });
        
        // Listener para cambios de pestaña
        jTabbedPane1.addChangeListener(e -> {
            int selectedIndex = jTabbedPane1.getSelectedIndex();
            // Lógica cuando se cambia de pestaña
        });
    }
    
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(() -> {
            try {
                DetalleClienteNuevo frame = new DetalleClienteNuevo();
                frame.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}