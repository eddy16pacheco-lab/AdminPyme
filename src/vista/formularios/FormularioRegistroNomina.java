/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.formularios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Eddy
 */
public class FormularioRegistroNomina extends JFrame{


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

    // Variables declaration
    public javax.swing.JButton jButton_aceptar;
    public javax.swing.JButton jButton_limpiar;
    public javax.swing.JComboBox<String> jComboBox_cedula;
    public javax.swing.JComboBox<String> jComboBox_estado_del_pago;
    public javax.swing.JComboBox<String> jComboBox_forma_de_pago;
    public javax.swing.JComboBox<String> jComboBox_jornada;
    public com.toedter.calendar.JDateChooser jDateChooser_fecha;
    public javax.swing.JLabel jLabel_cargo;
    public javax.swing.JTextField jTextField_monto;
    public javax.swing.JTextField jTextField_referencia;

    public FormularioRegistroNomina() {
        initializeFrame();
        createComponents();
        setupLayout();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Sistema de Gestión - Registro de Nómina");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 900, 800);
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
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        // Título principal
        JLabel titleLabel = new JLabel("💰 REGISTRO DE NÓMINA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 5, 0));

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Complete la información para registrar el pago de nómina del empleado");
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

        // Panel de campos del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Sección: Selección del empleado
        JLabel empleadoSectionLabel = new JLabel("👤 INFORMACIÓN DEL EMPLEADO");
        empleadoSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        empleadoSectionLabel.setForeground(PRIMARY);
        empleadoSectionLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(empleadoSectionLabel, gbc);

        // Cédula del trabajador
        JLabel cedulaLabel = new JLabel("🆔 Cédula del Trabajador:");
        cedulaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(cedulaLabel, gbc);

        jComboBox_cedula = new JComboBox<>(new String[] { 
            "Seleccionar empleado", "V-12345678", "V-87654321", "V-11223344", "V-44332211" 
        });
        jComboBox_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_cedula.setPreferredSize(new Dimension(0, 40));
        jComboBox_cedula.setBackground(Color.WHITE);
        jComboBox_cedula.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(jComboBox_cedula, gbc);

        // Cargo
        JLabel cargoLabel = new JLabel("💼 Cargo:");
        cargoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(cargoLabel, gbc);

        jLabel_cargo = new JLabel("No seleccionado");
        jLabel_cargo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel_cargo.setForeground(DARK);
        jLabel_cargo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(jLabel_cargo, gbc);

        // Sección: Información del pago
        JLabel pagoSectionLabel = new JLabel("💳 INFORMACIÓN DEL PAGO");
        pagoSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pagoSectionLabel.setForeground(PRIMARY);
        pagoSectionLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(pagoSectionLabel, gbc);

        // Jornada
        JLabel jornadaLabel = new JLabel("📅 Jornada:");
        jornadaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(jornadaLabel, gbc);

        jComboBox_jornada = new JComboBox<>(new String[] { 
            "Seleccionar jornada", "Diario", "Semanal", "Quincenal", "Mensual" 
        });
        jComboBox_jornada.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_jornada.setPreferredSize(new Dimension(0, 40));
        jComboBox_jornada.setBackground(Color.WHITE);
        jComboBox_jornada.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(jComboBox_jornada, gbc);

        // Estado del pago
        JLabel estadoPagoLabel = new JLabel("📊 Estado del Pago:");
        estadoPagoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(estadoPagoLabel, gbc);

        jComboBox_estado_del_pago = new JComboBox<>(new String[] { 
            "Seleccionar estado del pago", "Pendiente", "Completado", "Anulado", "Conciliado" 
        });
        jComboBox_estado_del_pago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_estado_del_pago.setPreferredSize(new Dimension(0, 40));
        jComboBox_estado_del_pago.setBackground(Color.WHITE);
        jComboBox_estado_del_pago.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(jComboBox_estado_del_pago, gbc);

        // Monto a pagar
        JLabel montoLabel = new JLabel("💰 Monto a Pagar:");
        montoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(montoLabel, gbc);

        jTextField_monto = new JTextField();
        jTextField_monto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_monto.setPreferredSize(new Dimension(0, 40));
        jTextField_monto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(jTextField_monto, gbc);

        // Sección: Detalles adicionales
        JLabel detallesSectionLabel = new JLabel("📋 DETALLES ADICIONALES");
        detallesSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        detallesSectionLabel.setForeground(PRIMARY);
        detallesSectionLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(detallesSectionLabel, gbc);

        // Fecha del pago
        JLabel fechaLabel = new JLabel("📅 Fecha del Pago:");
        fechaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        formPanel.add(fechaLabel, gbc);

        jDateChooser_fecha = new JDateChooser();
        jDateChooser_fecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jDateChooser_fecha.setPreferredSize(new Dimension(0, 40));
        jDateChooser_fecha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(jDateChooser_fecha, gbc);

        // Forma de pago
        JLabel formaPagoLabel = new JLabel("💳 Forma de Pago:");
        formaPagoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(formaPagoLabel, gbc);

        jComboBox_forma_de_pago = new JComboBox<>(new String[] { 
            "Seleccionar forma de pago", "Transferencia", "Efectivo", "Cheque", "Depósito" 
        });
        jComboBox_forma_de_pago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_forma_de_pago.setPreferredSize(new Dimension(0, 40));
        jComboBox_forma_de_pago.setBackground(Color.WHITE);
        jComboBox_forma_de_pago.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(jComboBox_forma_de_pago, gbc);

        // Referencia del pago
        JLabel referenciaLabel = new JLabel("📝 Referencia del Pago:");
        referenciaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(referenciaLabel, gbc);

        jTextField_referencia = new JTextField();
        jTextField_referencia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_referencia.setPreferredSize(new Dimension(0, 40));
        jTextField_referencia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        gbc.gridx = 1;
        gbc.gridy = 10;
        formPanel.add(jTextField_referencia, gbc);

        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(LIGHT);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel infoLabel = new JLabel("<html><b>💡 Información:</b> Todos los campos son obligatorios. Verifique la información antes de registrar el pago.</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(DARK);

        infoPanel.add(infoLabel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        formPanel.add(infoPanel, gbc);

        // Ensamblar el panel
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private void crearBotones() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Botón limpiar
        jButton_limpiar = new JButton("🗑️ LIMPIAR FORMULARIO");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);
        jButton_limpiar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING.darker(), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        jButton_limpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_limpiar.setFocusPainted(false);

        // Botón registrar
        jButton_aceptar = new JButton("💾 REGISTRAR PAGO");
        jButton_aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_aceptar.setBackground(SUCCESS);
        jButton_aceptar.setForeground(Color.WHITE);
        jButton_aceptar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS.darker(), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        jButton_aceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton_aceptar.setFocusPainted(false);

        buttonPanel.add(jButton_limpiar);
        buttonPanel.add(jButton_aceptar);
    }

    private void setupLayout() {
        // Agregar componentes al frame
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(formContainerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Hacer responsive
        pack();
        setMinimumSize(new Dimension(900, 800));
    }
}    

