package vista.formularios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormularioRegCli extends JFrame {
    
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
    
    public FormularioRegCli() {
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
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80)); // Reducido de 100 a 80
        headerPanel.setBorder(new EmptyBorder(5, 10, 10, 10)); // Reducido el padding
        
        // Título principal
        JLabel titleLabel = new JLabel("👥 FORMULARIO DE REGISTRO DE CLIENTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Reducido de 24 a 22
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(5, 0, 3, 0)); // Reducido padding
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Complete todos los campos requeridos para registrar un nuevo cliente en el sistema");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Reducido de 14 a 12
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 5, 0)); // Reducido padding
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
    }
    
    private JPanel crearFormularioPrincipal() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15) // Reducido de 20 a 15
        ));
        
        // Header de la sección - AUMENTADO en altura
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(8, 15, 8, 15)); // Aumentado padding vertical
        headerPanel.setPreferredSize(new Dimension(0, 50)); // Aumentado de 45 a 50
        
        JLabel titleLabel = new JLabel("📋 INFORMACIÓN DEL CLIENTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Reducido de 18 a 16
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Panel de campos del formulario - AUMENTADO en altura
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 15, 15, 15)); // Reducido espacio superior de 25 a 20
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8); // Reducido de 10 a 6
        gbc.weightx = 1.0;
        
        // Campo nombre - ALTURA REDUCIDA
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameLabel, gbc);
        
        jTextField_nombre = new JTextField();
        jTextField_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jTextField_nombre.setPreferredSize(new Dimension(0, 35)); // Reducido de 40 a 35
        jTextField_nombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10) // Reducido de 8,12,8,12 a 6,10,6,10
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(jTextField_nombre, gbc);
        
        // Campo apellido - ALTURA REDUCIDA
        JLabel lastnameLabel = new JLabel("Apellido:");
        lastnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lastnameLabel, gbc);
        
        jTextField_apellido = new JTextField();
        jTextField_apellido.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jTextField_apellido.setPreferredSize(new Dimension(0, 35)); // Reducido de 40 a 35
        jTextField_apellido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10) // Reducido de 8,12,8,12 a 6,10,6,10
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(jTextField_apellido, gbc);
        
        // Campo cédula - ALTURA REDUCIDA
        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(cedulaLabel, gbc);
        
        jTextField_cedula = new JTextField();
        jTextField_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jTextField_cedula.setPreferredSize(new Dimension(0, 35)); // Reducido de 40 a 35
        jTextField_cedula.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10) // Reducido de 8,12,8,12 a 6,10,6,10
        ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(jTextField_cedula, gbc);
        
        // Campo teléfono - ALTURA REDUCIDA
        JLabel phoneLabel = new JLabel("Teléfono:");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(phoneLabel, gbc);
        
        JPanel phonePanel = new JPanel(new BorderLayout(8, 0)); // Reducido espacio entre componentes
        phonePanel.setBackground(Color.WHITE);
        
        jComboBox_numeros_tlf1 = new JComboBox<>(new String[]{"0412", "0414", "0416", "0424", "0426", "0422"});
        jComboBox_numeros_tlf1.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jComboBox_numeros_tlf1.setPreferredSize(new Dimension(90, 35)); // Reducido de 100,40 a 90,35
        jComboBox_numeros_tlf1.setBackground(Color.WHITE);
        jComboBox_numeros_tlf1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6) // Reducido de 5,8,5,8 a 4,6,4,6
        ));
        
        jTextField_telefono = new JTextField();
        jTextField_telefono.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jTextField_telefono.setPreferredSize(new Dimension(0, 35)); // Reducido de 40 a 35
        jTextField_telefono.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10) // Reducido de 8,12,8,12 a 6,10,6,10
        ));
        
        phonePanel.add(jComboBox_numeros_tlf1, BorderLayout.WEST);
        phonePanel.add(jTextField_telefono, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(phonePanel, gbc);
        
        // Campo dirección - ALTURA REDUCIDA
        JLabel addressLabel = new JLabel("Dirección:");
        addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(addressLabel, gbc);
        
        jComboBox1 = new JComboBox<>(new String[]{
            "Seleccionar Dirección", "Caracas", "Maracaibo", "Valencia", "Barquisimeto", 
            "Maracay", "Maturín", "Puerto La Cruz", "Barcelona", "San Cristóbal"
        });
        jComboBox1.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Reducido de 14 a 13
        jComboBox1.setPreferredSize(new Dimension(0, 35)); // Reducido de 40 a 35
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // Reducido de 5,8,5,8 a 4,8,4,8
        ));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(jComboBox1, gbc);
        
        // Panel de información - ALTURA REDUCIDA
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(LIGHT);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) // Reducido de 12,15,12,15 a 8,12,8,12
        ));
        
        JLabel infoLabel = new JLabel("<html><b>💡 Información:</b> Todos los campos son obligatorios. El teléfono debe contener 7 dígitos.</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11)); // Reducido de 12 a 11
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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15)); // Reducido espacio
        buttonPanel.setBorder(new EmptyBorder(8, 0, 8, 0)); // Reducido padding
        
        // Botón registrar - ALTURA REDUCIDA
        jButton_registrar1 = new JButton("📝 REGISTRAR CLIENTE");
        jButton_registrar1.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        jButton_registrar1.setBackground(SUCCESS);
        jButton_registrar1.setForeground(Color.WHITE);
        jButton_registrar1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25) // Reducido de 12,30,12,30 a 10,25,10,25
        ));
        jButton_registrar1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_registrar1.setFocusPainted(false);
        
        // Botón limpiar - ALTURA REDUCIDA
        jButton_cancelar2 = new JButton("🗑️ LIMPIAR FORMULARIO");
        jButton_cancelar2.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Reducido de 14 a 13
        jButton_cancelar2.setBackground(WARNING);
        jButton_cancelar2.setForeground(Color.WHITE);
        jButton_cancelar2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25) // Reducido de 12,30,12,30 a 10,25,10,25
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