/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.gestion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaGestionUsuario extends javax.swing.JInternalFrame {
    // Colores idénticos a FormularioRegistroNomina
    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);
        // Variables declaration
    public JButton JButton3_registrar;
    public JButton jButton_actualizar;
    public JButton jButton_limpiar;
    public JComboBox jComboBox_filtro;
    public JComboBox<String> jComboBox_roles_usuarios;
    public static JLabel jLabel_id_usuario=new JLabel();
    public JLabel jLabel_nombre_usuario;
    public JLabel jLabel_ojo_activo;
    public JLabel jLabel_ojo_inactivo;
    public JMenuItem jMenuItem_editar;
    public JMenuItem jMenuItem_inactivar;
    public JPanel jPanel1;
    public JPanel jPanel2;
    public JPasswordField jPasswordField1;
    public JPopupMenu jPopupMenu1;
    public JScrollPane jScrollPane1;
    public JTable jTable_usuarios;
    public JTextField jTextField_busqueda;
    public JTextField jTextField_correo1;
    public JTextField jTextField_nombre_usuario;

    public JPopupMenu menu = new JPopupMenu();
    
    public VistaGestionUsuario() {
        initComponents();
        aplicarEstilos();
    }

    private void aplicarEstilos() {
        // Aplicar fondo gradiente al panel principal
        jPanel1.setLayout(new BorderLayout());
        jPanel1.removeAll();
        
        // Crear panel con gradiente
        JPanel gradientPanel = new JPanel() {
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
        gradientPanel.setLayout(null);
        
        // Recrear los componentes con el nuevo diseño
        crearHeader(gradientPanel);
        crearPanelBusqueda(gradientPanel);
        crearFormularioUsuario(gradientPanel);
        crearTablaUsuarios(gradientPanel);
        crearBotones(gradientPanel);
        
        jPanel1.add(gradientPanel, BorderLayout.CENTER);
    }

    private void crearHeader(JPanel parent) {
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBounds(50, 10, 1400, 80);
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("👥 GESTIÓN DE USUARIOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

        JLabel subtitleLabel = new JLabel("Administre los usuarios del sistema");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        parent.add(headerPanel);
        jMenuItem_editar=new JMenuItem("Editar");
        jMenuItem_inactivar=new JMenuItem("Inactivar");
        menu.add(jMenuItem_inactivar);
        menu.add(jMenuItem_editar);
    }

    private void crearPanelBusqueda(JPanel parent) {
        // Panel de búsqueda
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(LIGHT);
        searchPanel.setBounds(50, 100, 1400, 70);
        searchPanel.setLayout(new GridLayout(1, 4, 10, 0));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Campo de búsqueda
        JLabel buscarLabel = new JLabel("🔍 Buscar:");
        buscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchPanel.add(buscarLabel);

        jTextField_busqueda = new JTextField();
        jTextField_busqueda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_busqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        searchPanel.add(jTextField_busqueda);

        // Combo de filtro
        JLabel filtroLabel = new JLabel("📊 Filtro:");
        filtroLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchPanel.add(filtroLabel);

        jComboBox_filtro = new JComboBox<>(new String[]{"Seleccionar un rol", "Administrador", "Usuario", "Invitado"});
        jComboBox_filtro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_filtro.setBackground(Color.WHITE);
        jComboBox_filtro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        searchPanel.add(jComboBox_filtro);

        parent.add(searchPanel);
    }

    private void crearFormularioUsuario(JPanel parent) {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(50, 190, 1400, 150);
        formPanel.setLayout(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Nombre de usuario
        JPanel nombrePanel = new JPanel(new BorderLayout());
        nombrePanel.setBackground(Color.WHITE);
        JLabel nombreLabel = new JLabel("👤 Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nombrePanel.add(nombreLabel, BorderLayout.NORTH);
        
        jTextField_nombre_usuario = new JTextField();
        jTextField_nombre_usuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_nombre_usuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        nombrePanel.add(jTextField_nombre_usuario, BorderLayout.CENTER);
        formPanel.add(nombrePanel);

        // Correo usuario
        JPanel correoPanel = new JPanel(new BorderLayout());
        correoPanel.setBackground(Color.WHITE);
        JLabel correoLabel = new JLabel("📧 Correo:");
        correoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        correoPanel.add(correoLabel, BorderLayout.NORTH);
        
        jTextField_correo1 = new JTextField();
        jTextField_correo1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextField_correo1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        correoPanel.add(jTextField_correo1, BorderLayout.CENTER);
        formPanel.add(correoPanel);

        // Contraseña
        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);
        JLabel passLabel = new JLabel("🔒 Contraseña:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passPanel.add(passLabel, BorderLayout.NORTH);
        
        jPasswordField1 = new JPasswordField();
        jPasswordField1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jPasswordField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        passPanel.add(jPasswordField1, BorderLayout.CENTER);
        formPanel.add(passPanel);

        // Rol usuario
        JPanel rolPanel = new JPanel(new BorderLayout());
        rolPanel.setBackground(Color.WHITE);
        JLabel rolLabel = new JLabel("🎭 Rol:");
        rolLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        rolPanel.add(rolLabel, BorderLayout.NORTH);
        
        jComboBox_roles_usuarios = new JComboBox<>(new String[]{"Seleccionar un rol", "Administrador", "Usuario", "Invitado"});
        jComboBox_roles_usuarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jComboBox_roles_usuarios.setBackground(Color.WHITE);
        jComboBox_roles_usuarios.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        rolPanel.add(jComboBox_roles_usuarios, BorderLayout.CENTER);
        formPanel.add(rolPanel);

        parent.add(formPanel);
    }

    private void crearTablaUsuarios(JPanel parent) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBounds(50, 360, 1400, 300);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("📊 LISTADO DE USUARIOS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(PRIMARY);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        tablePanel.add(titulo, BorderLayout.NORTH);

        // Tabla de usuarios
        String[] columnNames = {"Usuario", "Contraseña", "Correo", "Tipo de rol", "Estado", "id usuario"};
  String[][] data=new String[1][1];

        jTable_usuarios = new JTable(data, columnNames);
        jTable_usuarios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTable_usuarios.setRowHeight(30);
        jTable_usuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable_usuarios.getTableHeader().setBackground(PRIMARY);
        jTable_usuarios.getTableHeader().setForeground(Color.WHITE);

        jTable_usuarios.getColumnModel().getColumn(5).setMinWidth(0);
        jTable_usuarios.getColumnModel().getColumn(5).setMaxWidth(0);

        jScrollPane1 = new JScrollPane(jTable_usuarios);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(LIGHT, 1));
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        parent.add(tablePanel);
    }

    private void crearBotones(JPanel parent) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(50, 680, 1400, 80);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botón limpiar
        jButton_limpiar = new JButton("🗑️ LIMPIAR");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);
        jButton_limpiar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton_limpiar.setFocusPainted(false);

        // Botón registrar
        JButton3_registrar = new JButton("💾 REGISTRAR");
        JButton3_registrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JButton3_registrar.setBackground(SUCCESS);
        JButton3_registrar.setForeground(Color.WHITE);
        JButton3_registrar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        JButton3_registrar.setFocusPainted(false);

        // Botón actualizar
        jButton_actualizar = new JButton("✏️ ACTUALIZAR");
        jButton_actualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jButton_actualizar.setBackground(SECONDARY);
        jButton_actualizar.setForeground(Color.WHITE);
        jButton_actualizar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        jButton_actualizar.setFocusPainted(false);

        buttonPanel.add(jButton_limpiar);
        buttonPanel.add(JButton3_registrar);
        buttonPanel.add(jButton_actualizar);

        parent.add(buttonPanel);
    }

    // Mantener el método initComponents original pero vacío ya que creamos todo manualmente
    private void initComponents() {
        // Inicializar variables
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_usuarios = new javax.swing.JTable();
        JButton3_registrar = new javax.swing.JButton();
        jButton_actualizar = new javax.swing.JButton();
        jButton_limpiar = new javax.swing.JButton();
        jTextField_busqueda = new javax.swing.JTextField();
        jTextField_nombre_usuario = new javax.swing.JTextField();
        jTextField_correo1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jComboBox_filtro = new javax.swing.JComboBox<>();
        jComboBox_roles_usuarios = new javax.swing.JComboBox<>();
        
        // Configuración básica del JInternalFrame
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestión de Usuarios");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );

        pack();
    }

}
