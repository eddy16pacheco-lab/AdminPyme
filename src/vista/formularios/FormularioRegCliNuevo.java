package vista.formularios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormularioRegCliNuevo extends JFrame {
    
    // Colores del sistema (mismo estilo que GestionCategorias)
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);
    public static final Color WHITE = new Color(255, 255, 255);
    
    // Componentes principales
    public static JPanel contentPane;
    public static JPanel headerPanel;
    public static JPanel formContainerPanel;
    public static JPanel buttonPanel;
    
    // Componentes del formulario
    public static JTextField jTextField_nombre;
    public static JTextField jTextField_apellido;
    public static JTextField jTextField_cedula;
    public static JTextField jTextField_telefono;
    public static JComboBox<String> jComboBox1;
    public static JComboBox<String> jComboBox_numeros_tlf1;
    public static JButton jButton_registrar1;
    public static JButton jButton_cancelar2;
    
    public FormularioRegCliNuevo() {
        initializeFrame();
        createComponents();
        setupLayout();
        setVisible(true);
    }
    
    private void initializeFrame() {
        setTitle("Registro de Cliente - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 900, 700);
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
        JPanel mainFormPanel = crearFormularioPrincipal();
        
        formContainerPanel.add(mainFormPanel, BorderLayout.CENTER);
        
        // Crear panel de botones
        crearBotones();
    }
    
    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBorder(new EmptyBorder(10, 10, 20, 10));
        
        // Título principal
        JLabel titleLabel = new JLabel("👥 FORMULARIO DE REGISTRO DE CLIENTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Complete todos los campos requeridos para registrar un nuevo cliente en el sistema");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
    }
    
    private JPanel crearFormularioPrincipal() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Header de la sección
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        headerPanel.setPreferredSize(new Dimension(0, 45));
        
        JLabel titleLabel = new JLabel("📋 INFORMACIÓN DEL CLIENTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Panel de campos del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        
        // Campo nombre
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameLabel, gbc);
        
        jTextField_nombre = new JTextField();
        jTextField_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_nombre.setPreferredSize(new Dimension(0, 40));
        jTextField_nombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(jTextField_nombre, gbc);
        
        // Campo apellido
        JLabel lastnameLabel = new JLabel("Apellido:");
        lastnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lastnameLabel, gbc);
        
        jTextField_apellido = new JTextField();
        jTextField_apellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_apellido.setPreferredSize(new Dimension(0, 40));
        jTextField_apellido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(jTextField_apellido, gbc);
        
        // Campo cédula
        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(cedulaLabel, gbc);
        
        jTextField_cedula = new JTextField();
        jTextField_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_cedula.setPreferredSize(new Dimension(0, 40));
        jTextField_cedula.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(jTextField_cedula, gbc);
        
        // Campo teléfono
        JLabel phoneLabel = new JLabel("Teléfono:");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(phoneLabel, gbc);
        
        JPanel phonePanel = new JPanel(new BorderLayout(10, 0));
        phonePanel.setBackground(Color.WHITE);
        
        jComboBox_numeros_tlf1 = new JComboBox<>(new String[]{"0412", "0414", "0416", "0424", "0426", "0422"});
        jComboBox_numeros_tlf1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_numeros_tlf1.setPreferredSize(new Dimension(100, 40));
        jComboBox_numeros_tlf1.setBackground(Color.WHITE);
        jComboBox_numeros_tlf1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        jTextField_telefono = new JTextField();
        jTextField_telefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_telefono.setPreferredSize(new Dimension(0, 40));
        jTextField_telefono.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        phonePanel.add(jComboBox_numeros_tlf1, BorderLayout.WEST);
        phonePanel.add(jTextField_telefono, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(phonePanel, gbc);
        
        // Campo dirección
        JLabel addressLabel = new JLabel("Dirección:");
        addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(addressLabel, gbc);
        
        jComboBox1 = new JComboBox<>(new String[]{
            "Seleccionar Dirección", "Caracas", "Maracaibo", "Valencia", "Barquisimeto", 
            "Maracay", "Maturín", "Puerto La Cruz", "Barcelona", "San Cristóbal"
        });
        jComboBox1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox1.setPreferredSize(new Dimension(0, 40));
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(jComboBox1, gbc);
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(LIGHT);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        JLabel infoLabel = new JLabel("<html><b>💡 Información:</b> Todos los campos son obligatorios. El teléfono debe contener 7 dígitos.</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(DARK);
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(infoPanel, gbc);
        
        // Ensamblar el panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void crearBotones() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Botón registrar
        jButton_registrar1 = new JButton("📝 REGISTRAR CLIENTE");
        jButton_registrar1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_registrar1.setBackground(SUCCESS);
        jButton_registrar1.setForeground(Color.WHITE);
        jButton_registrar1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        jButton_registrar1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_registrar1.setFocusPainted(false);
        
        // Botón limpiar
        jButton_cancelar2 = new JButton("🗑️ LIMPIAR FORMULARIO");
        jButton_cancelar2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_cancelar2.setBackground(WARNING);
        jButton_cancelar2.setForeground(Color.WHITE);
        jButton_cancelar2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        jButton_cancelar2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_cancelar2.setFocusPainted(false);
        
        buttonPanel.add(jButton_registrar1);
        buttonPanel.add(jButton_cancelar2);
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
    

}