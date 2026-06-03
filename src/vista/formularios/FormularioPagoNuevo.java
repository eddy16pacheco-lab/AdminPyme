package vista.formularios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.border.TitledBorder;

public class FormularioPagoNuevo extends JFrame {

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
    public static JLabel jLabel_dolar;
    public static JLabel jLabel_nombre;
    public static JLabel jLabel_apellido;
    public static JLabel jLabel_cedula;
    public static JLabel jLabel_codigo_de_venta;
    public static JLabel jLabel_monto_total_venta_bolivares;
    public static JLabel jLabel_monto_total_venta_dolares;
    public static JLabel jLabel_monto_total_del_pago_bolivares;
    public static JLabel jLabel_monto_total_del_pago_dolares;
    public static JLabel jLabel_monto_total_del_pago_operacion;
    public static JLabel jLabel_monto_total_del_pago_operacion_$;

    public static JButton jButton_registrar;
    public static JButton Jbutoon_limpiar;

    public static JComboBox<String> jComboBox_forma_pago;

    public static JTextField jTextField_monto_cancelar;
    public static JTextField jTextField_referencia;

    public FormularioPagoNuevo() {
        initializeFrame();
        createComponents();
        setupLayout();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Sistema de Gestión - Registro de Pagos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);

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
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
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
        
        // Crear formulario principal
        JPanel mainFormPanel = crearFormularioPrincipal();
        formContainerPanel.add(mainFormPanel, BorderLayout.CENTER);
        
        // Crear panel de botones
        crearBotones();
    }

    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 120));
        headerPanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        // Título principal
        JLabel titleLabel = new JLabel("💳 FORMULARIO DE REGISTRO DE PAGOS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 5, 0));

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Complete la información para registrar un nuevo pago en el sistema");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));

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

        // Panel de información superior
        JPanel topInfoPanel = crearPanelInformacionSuperior();
        panel.add(topInfoPanel, BorderLayout.NORTH);

        // Panel central con secciones
        JPanel centerPanel = crearPanelCentral();
        panel.add(centerPanel, BorderLayout.CENTER);

        // Panel de entrada de datos
        JPanel inputPanel = crearPanelEntradaDatos();
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelInformacionSuperior() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Panel de información general
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Tasa del dólar
        JLabel tasaLabel = new JLabel("💰 Tasa del Dólar:");
        tasaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(tasaLabel, gbc);

        jLabel_dolar = new JLabel("0.00 Bs");
        jLabel_dolar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_dolar.setForeground(SECONDARY);
        gbc.gridx = 1; gbc.gridy = 0;
        infoPanel.add(jLabel_dolar, gbc);

        // Código de venta
        JLabel codigoLabel = new JLabel("📋 Código de Venta:");
        codigoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(codigoLabel, gbc);

        jLabel_codigo_de_venta = new JLabel("N/A");
        jLabel_codigo_de_venta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_codigo_de_venta.setForeground(SECONDARY);
        gbc.gridx = 1; gbc.gridy = 1;
        infoPanel.add(jLabel_codigo_de_venta, gbc);

        // Panel de información del cliente
        JPanel clientePanel = new JPanel(new GridBagLayout());
        clientePanel.setBackground(Color.WHITE);
        clientePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            "👤 Información del Cliente",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY
        ));

        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.insets = new Insets(8, 10, 8, 10);
        cgbc.anchor = GridBagConstraints.WEST;

        // Nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cgbc.gridx = 0; cgbc.gridy = 0;
        clientePanel.add(nombreLabel, cgbc);

        jLabel_nombre = new JLabel("N/A");
        jLabel_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cgbc.gridx = 1; cgbc.gridy = 0;
        clientePanel.add(jLabel_nombre, cgbc);

        // Apellido
        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cgbc.gridx = 0; cgbc.gridy = 1;
        clientePanel.add(apellidoLabel, cgbc);

        jLabel_apellido = new JLabel("N/A");
        jLabel_apellido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cgbc.gridx = 1; cgbc.gridy = 1;
        clientePanel.add(jLabel_apellido, cgbc);

        // Cédula
        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cgbc.gridx = 0; cgbc.gridy = 2;
        clientePanel.add(cedulaLabel, cgbc);

        jLabel_cedula = new JLabel("N/A");
        jLabel_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cgbc.gridx = 1; cgbc.gridy = 2;
        clientePanel.add(jLabel_cedula, cgbc);

        panel.add(infoPanel);
        panel.add(clientePanel);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Panel Monto Total Venta
        JPanel ventaPanel = crearPanelMonto("💵 MONTO TOTAL VENTA", 
            jLabel_monto_total_venta_bolivares = crearLabelMonto("0.00 Bs"),
            jLabel_monto_total_venta_dolares = crearLabelMonto("0.00 $")
        );

        // Panel Monto Total Pago
        JPanel pagoPanel = crearPanelMonto("💰 MONTO TOTAL PAGO",
            jLabel_monto_total_del_pago_bolivares = crearLabelMonto("0.00 Bs"),
            jLabel_monto_total_del_pago_dolares = crearLabelMonto("0.00 $")
        );

        // Panel Monto Operación
        JPanel operacionPanel = crearPanelMonto("📊 MONTO OPERACIÓN",
            jLabel_monto_total_del_pago_operacion = crearLabelMonto("0.00 Bs"),
            jLabel_monto_total_del_pago_operacion_$ = crearLabelMonto("0.00 $")
        );

        panel.add(ventaPanel);
        panel.add(pagoPanel);
        panel.add(operacionPanel);

        return panel;
    }

    private JPanel crearPanelMonto(String titulo, JLabel labelBs, JLabel labelDolares) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Header
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Panel de montos
        JPanel montosPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        montosPanel.setOpaque(false);

        // Bolívares
        JPanel bsPanel = new JPanel(new BorderLayout());
        bsPanel.setOpaque(false);
        JLabel bsLabel = new JLabel("Bolívares:");
        bsLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelBs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelBs.setForeground(DARK);
        bsPanel.add(bsLabel, BorderLayout.NORTH);
        bsPanel.add(labelBs, BorderLayout.CENTER);

        // Dólares
        JPanel usdPanel = new JPanel(new BorderLayout());
        usdPanel.setOpaque(false);
        JLabel usdLabel = new JLabel("Dólares:");
        usdLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelDolares.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelDolares.setForeground(DARK);
        usdPanel.add(usdLabel, BorderLayout.NORTH);
        usdPanel.add(labelDolares, BorderLayout.CENTER);

        montosPanel.add(bsPanel);
        montosPanel.add(usdPanel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(montosPanel, BorderLayout.CENTER);

        return panel;
    }

    private JLabel crearLabelMonto(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(DARK);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JPanel crearPanelEntradaDatos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Monto a Cancelar
        JLabel montoLabel = new JLabel("💵 Monto a Cancelar:");
        montoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(montoLabel, gbc);

        jTextField_monto_cancelar = new JTextField();
        jTextField_monto_cancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_monto_cancelar.setPreferredSize(new Dimension(0, 40));
        jTextField_monto_cancelar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(jTextField_monto_cancelar, gbc);

        // Forma de Pago
        JLabel formaPagoLabel = new JLabel("💳 Forma de Pago:");
        formaPagoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(formaPagoLabel, gbc);

        jComboBox_forma_pago = new JComboBox<>(new String[] {
            "Seleccionar forma de pago", "Transferencia", "Efectivo", "Tarjeta de Crédito", 
            "Tarjeta de Débito", "Pago Móvil", "Zelle", "PayPal"
        });
        jComboBox_forma_pago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_forma_pago.setPreferredSize(new Dimension(0, 40));
        jComboBox_forma_pago.setBackground(Color.WHITE);
        jComboBox_forma_pago.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(jComboBox_forma_pago, gbc);

        // Referencia
        JLabel referenciaLabel = new JLabel("📝 Referencia:");
        referenciaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(referenciaLabel, gbc);

        jTextField_referencia = new JTextField();
        jTextField_referencia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_referencia.setPreferredSize(new Dimension(0, 40));
        jTextField_referencia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(jTextField_referencia, gbc);

        return panel;
    }

    private void crearBotones() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Botón registrar
        jButton_registrar = new JButton("💾 REGISTRAR PAGO");
        jButton_registrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton_registrar.setBackground(SUCCESS);
        jButton_registrar.setForeground(Color.WHITE);
        jButton_registrar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        jButton_registrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_registrar.setFocusPainted(false);

        // Botón limpiar
        Jbutoon_limpiar = new JButton("🗑️ LIMPIAR FORMULARIO");
        Jbutoon_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        Jbutoon_limpiar.setBackground(WARNING);
        Jbutoon_limpiar.setForeground(Color.WHITE);
        Jbutoon_limpiar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        Jbutoon_limpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Jbutoon_limpiar.setFocusPainted(false);

        buttonPanel.add(jButton_registrar);
        buttonPanel.add(Jbutoon_limpiar);
    }

    private void setupLayout() {
        // Agregar componentes al frame
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(formContainerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Hacer responsive
        pack();
        setMinimumSize(new Dimension(1200, 800));
    }


}