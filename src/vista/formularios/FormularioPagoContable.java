package vista.formularios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FormularioPagoContable extends JFrame {

    public final Color PRIMARY = new Color(44, 62, 80);
    public final Color SECONDARY = new Color(52, 152, 219);
    public final Color SUCCESS = new Color(39, 174, 96);
    public final Color DANGER = new Color(231, 76, 60);
    public final Color WARNING = new Color(243, 156, 18);
    public final Color LIGHT = new Color(236, 240, 241);
    public final Color DARK = new Color(52, 73, 94);
    public final Color WHITE = new Color(255, 255, 255);

    public JPanel contentPane;
    public JPanel headerPanel;
    public JPanel formContainerPanel;
    public JPanel resumenPanel;
    public JPanel buttonPanel;
    public JScrollPane centerScrollPane;
    

    public JLabel jLabel_dolar;
    public JLabel jLabel_nombre;
    public JLabel jLabel_apellido;
    public JLabel jLabel_cedula;
    public JLabel jLabel_codigo_de_venta;
    public JLabel jLabel_monto_total_venta_bolivares;
    public JLabel jLabel_monto_total_venta_dolares;
    public JLabel jLabel_monto_total_del_pago_bolivares;
    public JLabel jLabel_monto_total_del_pago_dolares;
    public JLabel jLabel_monto_total_del_pago_operacion;
    public JLabel jLabel_monto_total_del_pago_operacion_$;

    public JButton jButton_registrar;
    public JButton jButton_limpiar;
    public JButton jButton_cancelar;

    public JComboBox<String> jComboBox_forma_pago;

    public JTextField jTextField_monto_cancelar;
    public JTextField jTextField_referencia;

    public FormularioPagoContable() {
        setTitle("Registro de Pago");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1366, 768);
        setPreferredSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        setResizable(true);
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
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);
        crearHeader();
        crearResumen();
        crearFormulario();
        crearBotonera();
        contentPane.add(headerPanel, BorderLayout.NORTH);
        centerScrollPane = new JScrollPane(formContainerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        centerScrollPane.setViewportBorder(null);
        centerScrollPane.setWheelScrollingEnabled(true);
        centerScrollPane.getVerticalScrollBar().setUnitIncrement(24);
        centerScrollPane.getVerticalScrollBar().setBlockIncrement(48);
        centerScrollPane.getViewport().setOpaque(true);
        centerScrollPane.getViewport().setBackground(WHITE);
        contentPane.add(centerScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        configurarResponsive();
        pack();
        setSize(1366, 768);
        setAlwaysOnTop(true);
        ajustarALaBarraDeTareas();
    }

    private void configurarResponsive() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentesAlTamaño();
            }
        });
    }

    private void ajustarComponentesAlTamaño() {
        int width = getWidth();
        int height = getHeight();
        
        // Ajustar tamaño del header
        headerPanel.setPreferredSize(new Dimension(width, height / 8));
        
        // Ajustar panel de resumen según el tamaño de la ventana
        if (width < 1024) {
            // Pantallas pequeñas - resumen horizontal
            resumenPanel.setLayout(new GridLayout(1, 4, 12, 12));
            resumenPanel.setPreferredSize(new Dimension(0, height / 6));
        } else {
            // Pantallas grandes - resumen vertical
            resumenPanel.setLayout(new GridLayout(4, 1, 12, 12));
            resumenPanel.setPreferredSize(new Dimension(width / 4, 0));
        }
        
        // Ajustar fuentes según el tamaño
        ajustarFuentesResponsive(width);
        
        // Ajustar altura de los paneles del formulario
        ajustarAlturaPanelesFormulario(height);
        
        // Revalidar y repintar
        resumenPanel.revalidate();
        resumenPanel.repaint();
        formContainerPanel.revalidate();
        formContainerPanel.repaint();
    }

    private void ajustarFuentesResponsive(int width) {
        Font fontTitulo, fontNormal, fontBoton;
        
        if (width < 768) {
            fontTitulo = new Font("Segoe UI", Font.BOLD, 16);
            fontNormal = new Font("Segoe UI", Font.PLAIN, 12);
            fontBoton = new Font("Segoe UI", Font.BOLD, 12);
        } else if (width < 1024) {
            fontTitulo = new Font("Segoe UI", Font.BOLD, 18);
            fontNormal = new Font("Segoe UI", Font.PLAIN, 13);
            fontBoton = new Font("Segoe UI", Font.BOLD, 13);
        } else {
            fontTitulo = new Font("Segoe UI", Font.BOLD, 20);
            fontNormal = new Font("Segoe UI", Font.PLAIN, 14);
            fontBoton = new Font("Segoe UI", Font.BOLD, 14);
        }
        
        // Aplicar fuentes a los componentes
        aplicarFuentesComponentes(fontTitulo, fontNormal, fontBoton);
    }

    private void aplicarFuentesComponentes(Font fontTitulo, Font fontNormal, Font fontBoton) {
        // Aplicar a botones
        jButton_registrar.setFont(fontBoton);
        jButton_limpiar.setFont(fontBoton);
        jButton_cancelar.setFont(fontBoton);
        
        // Aplicar a labels
        Component[] components = formContainerPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                aplicarFuentesPanel((JPanel) comp, fontTitulo, fontNormal);
            }
        }
    }

    private void aplicarFuentesPanel(JPanel panel, Font fontTitulo, Font fontNormal) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getFont().getStyle() == Font.BOLD) {
                    label.setFont(fontTitulo);
                } else {
                    label.setFont(fontNormal);
                }
            } else if (comp instanceof JTextField) {
                ((JTextField) comp).setFont(fontNormal);
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).setFont(fontNormal);
            } else if (comp instanceof JPanel) {
                aplicarFuentesPanel((JPanel) comp, fontTitulo, fontNormal);
            }
        }
    }

    private void ajustarAlturaPanelesFormulario(int height) {
        // Ajustar altura preferida de los paneles según la altura total
        int panelHeight = Math.max(height / 5, 180);
        
        Component[] components = formContainerPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                comp.setPreferredSize(new Dimension(0, panelHeight));
            }
        }
    }

    private void ajustarALaBarraDeTareas() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
        Rectangle bounds = gc.getBounds();
        int x = bounds.x + insets.left;
        int y = bounds.y + insets.top;
        setLocation(x, y);
    }

    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("\uD83D\uDCB0 Registro de Pagos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        JLabel subtitleLabel = new JLabel("Gestión visual de pagos con estilo contable");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
    }

    private JPanel tarjetaResumen(String titulo, String valor, String tendencia, Color colorTendencia) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(DARK);
        t.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 22));
        v.setForeground(PRIMARY);
        v.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel tr = new JLabel(tendencia);
        tr.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tr.setForeground(colorTendencia);
        tr.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        card.add(tr, BorderLayout.SOUTH);
        return card;
    }

    private void crearResumen() {
        resumenPanel = new JPanel(new GridLayout(4, 1, 12, 12));
        resumenPanel.setOpaque(false);
        resumenPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        resumenPanel.add(tarjetaResumen("Venta Total", "$0.00", "+0%", SUCCESS));
        resumenPanel.add(tarjetaResumen("Pagado", "$0.00", "+0%", SUCCESS));
        resumenPanel.add(tarjetaResumen("Pendiente", "$0.00", "-0%", DANGER));
        resumenPanel.add(tarjetaResumen("Saldo", "$0.00", "+0%", SUCCESS));
        resumenPanel.setPreferredSize(new Dimension(360, 0));
        contentPane.add(resumenPanel, BorderLayout.WEST);
    }

    private void crearFormulario() {
        formContainerPanel = new JPanel(new GridBagLayout());
        formContainerPanel.setBackground(WHITE);
        formContainerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        JPanel infoClientePanel = new JPanel(new GridBagLayout());
        infoClientePanel.setBackground(WHITE);
        infoClientePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Información del Cliente",
                        TitledBorder.CENTER,
                        TitledBorder.BELOW_TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(8, 10, 8, 10);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.anchor = GridBagConstraints.WEST;
        c1.weightx = 1;
        infoClientePanel.setPreferredSize(new Dimension(0, 220));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_nombre = new JLabel();
        jLabel_nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c1.gridx = 0; c1.gridy = 0; infoClientePanel.add(nombreLabel, c1);
        c1.gridx = 1; c1.gridy = 0; infoClientePanel.add(jLabel_nombre, c1);

        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_apellido = new JLabel();
        jLabel_apellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c1.gridx = 0; c1.gridy = 1; infoClientePanel.add(apellidoLabel, c1);
        c1.gridx = 1; c1.gridy = 1; infoClientePanel.add(jLabel_apellido, c1);

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_cedula = new JLabel();
        jLabel_cedula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c1.gridx = 0; c1.gridy = 2; infoClientePanel.add(cedulaLabel, c1);
        c1.gridx = 1; c1.gridy = 2; infoClientePanel.add(jLabel_cedula, c1);

        JLabel codigoLabel = new JLabel("Código de venta:");
        codigoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_codigo_de_venta = new JLabel();
        jLabel_codigo_de_venta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c1.gridx = 0; c1.gridy = 3; infoClientePanel.add(codigoLabel, c1);
        c1.gridx = 1; c1.gridy = 3; infoClientePanel.add(jLabel_codigo_de_venta, c1);

        JPanel tasasPanel = new JPanel(new GridBagLayout());
        tasasPanel.setBackground(WHITE);
        tasasPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Tasas y Montos",
                        TitledBorder.CENTER,
                        TitledBorder.BELOW_TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(8, 10, 8, 10);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;
        c2.weightx = 1;
        tasasPanel.setPreferredSize(new Dimension(0, 220));

        JLabel dolarLabel = new JLabel("Tasa del dólar:");
        dolarLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_dolar = new JLabel();
        jLabel_dolar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        c2.gridx = 0; c2.gridy = 0; tasasPanel.add(dolarLabel, c2);
        c2.gridx = 1; c2.gridy = 0; tasasPanel.add(jLabel_dolar, c2);

        JLabel ventaBsLabel = new JLabel("Monto venta Bs:");
        ventaBsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_venta_bolivares = new JLabel();
        jLabel_monto_total_venta_bolivares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 1; tasasPanel.add(ventaBsLabel, c2);
        c2.gridx = 1; c2.gridy = 1; tasasPanel.add(jLabel_monto_total_venta_bolivares, c2);

        JLabel ventaUsdLabel = new JLabel("Monto venta $:");
        ventaUsdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_venta_dolares = new JLabel();
        jLabel_monto_total_venta_dolares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 2; tasasPanel.add(ventaUsdLabel, c2);
        c2.gridx = 1; c2.gridy = 2; tasasPanel.add(jLabel_monto_total_venta_dolares, c2);

        JLabel pagoBsLabel = new JLabel("Pago Bs:");
        pagoBsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_del_pago_bolivares = new JLabel();
        jLabel_monto_total_del_pago_bolivares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 3; tasasPanel.add(pagoBsLabel, c2);
        c2.gridx = 1; c2.gridy = 3; tasasPanel.add(jLabel_monto_total_del_pago_bolivares, c2);

        JLabel pagoUsdLabel = new JLabel("Pago $:");
        pagoUsdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_del_pago_dolares = new JLabel();
        jLabel_monto_total_del_pago_dolares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 4; tasasPanel.add(pagoUsdLabel, c2);
        c2.gridx = 1; c2.gridy = 4; tasasPanel.add(jLabel_monto_total_del_pago_dolares, c2);

        JLabel operLabel = new JLabel("Total operación:");
        operLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_del_pago_operacion = new JLabel();
        jLabel_monto_total_del_pago_operacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 5; tasasPanel.add(operLabel, c2);
        c2.gridx = 1; c2.gridy = 5; tasasPanel.add(jLabel_monto_total_del_pago_operacion, c2);

        JLabel operUsdLabel = new JLabel("Total operación $:");
        operUsdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel_monto_total_del_pago_operacion_$ = new JLabel();
        jLabel_monto_total_del_pago_operacion_$.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c2.gridx = 0; c2.gridy = 6; tasasPanel.add(operUsdLabel, c2);
        c2.gridx = 1; c2.gridy = 6; tasasPanel.add(jLabel_monto_total_del_pago_operacion_$, c2);

        JPanel entradaPanel = new JPanel(new GridBagLayout());
        entradaPanel.setBackground(WHITE);
        entradaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Registrar Pago",
                        TitledBorder.CENTER,
                        TitledBorder.BELOW_TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        GridBagConstraints c3 = new GridBagConstraints();
        c3.insets = new Insets(8, 10, 8, 10);
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.anchor = GridBagConstraints.WEST;
        c3.weightx = 1;
        entradaPanel.setPreferredSize(new Dimension(0, 220));

        JLabel montoLabel = new JLabel("Monto a cancelar:");
        montoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTextField_monto_cancelar = new JTextField();
        jTextField_monto_cancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_monto_cancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        c3.gridx = 0; c3.gridy = 0; entradaPanel.add(montoLabel, c3);
        c3.gridx = 1; c3.gridy = 0; entradaPanel.add(jTextField_monto_cancelar, c3);

        JLabel formaLabel = new JLabel("Forma de pago:");
        formaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jComboBox_forma_pago = new JComboBox<>(new String[]{
                "Seleccionar", "Transferencia", "Efectivo", "Tarjeta", "Pago Móvil", "Zelle"
        });
        jComboBox_forma_pago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jComboBox_forma_pago.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        c3.gridx = 0; c3.gridy = 1; entradaPanel.add(formaLabel, c3);
        c3.gridx = 1; c3.gridy = 1; entradaPanel.add(jComboBox_forma_pago, c3);

        JLabel refLabel = new JLabel("Referencia:");
        refLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTextField_referencia = new JTextField();
        jTextField_referencia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField_referencia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        c3.gridx = 0; c3.gridy = 2; entradaPanel.add(refLabel, c3);
        c3.gridx = 1; c3.gridy = 2; entradaPanel.add(jTextField_referencia, c3);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0; formContainerPanel.add(infoClientePanel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 0; formContainerPanel.add(tasasPanel, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 1; formContainerPanel.add(entradaPanel, gbc);
    }

    private void crearBotonera() {
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        jButton_registrar = new JButton("Registrar");
        jButton_registrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_registrar.setBackground(SUCCESS);
        jButton_registrar.setForeground(Color.WHITE);

        jButton_limpiar = new JButton("Limpiar");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_limpiar.setBackground(WARNING);
        jButton_limpiar.setForeground(Color.WHITE);

        jButton_cancelar = new JButton("Cancelar");
        jButton_cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jButton_cancelar.setBackground(DANGER);
        jButton_cancelar.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(jButton_registrar, gbc);
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(jButton_limpiar, gbc);
        gbc.gridx = 2; gbc.gridy = 0; buttonPanel.add(jButton_cancelar, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioPagoContable().setVisible(true);
        });
    }
}