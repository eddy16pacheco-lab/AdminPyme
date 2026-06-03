package vista.formularios;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class FormularioRegistroGastoNuevo extends JFrame {

    public static final Color PRIMARY = new Color(110, 131, 183);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(231, 76, 60);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color LIGHT = new Color(236, 240, 241);
    public static final Color DARK = new Color(52, 73, 94);

    public static JPanel jPanel1;
    public static JPanel jPanel4;
    public static JPanel jPanel6;
    public static JPanel jPanel3;
    public static JPanel jPanel7;
    public static JPanel jPanel8;
    public static JLabel id_usuario = new JLabel();
    public static JButton jButton_limpiar;
    public static JButton jButton_aceptar;

    public static JLabel jLabel1;
    public static JLabel jLabel2;
    public static JLabel jLabel6;
    public static JLabel jLabel4;
    public static JLabel jLabel3;
    public static JLabel jLabel7;
    public static JLabel jLabel9;
    public static JLabel jLabel_ref_pago;
    public static JLabel jLabel10;
    public static JLabel jLabel14;
    public static JLabel jLabel5;

    public static JComboBox<String> jComboBox_forma_de_pago;
    public static JComboBox<String> jComboBox_categoria_gastos;
    public static JDateChooser jDateChooser_fecha;

    public static JTextField jTextField_referencia;
    public static JTextField jTextField2;
    public static JTextField jTextField_monto_gasto;

    public FormularioRegistroGastoNuevo() {
        setTitle("Registrar Gastos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, 0, getHeight(), SECONDARY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        getContentPane().setLayout(new BorderLayout());

        jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2d.setColor(SECONDARY);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            }
        };
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new GridBagLayout());

        jPanel3 = new JPanel();
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new GridBagLayout());
        jLabel6 = new JLabel();
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/imagen/gastos-de-capital.png")));
        jLabel1 = new JLabel("Registrar Gastos");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(DARK);
        jLabel2 = new JLabel();
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/imagen/presupuesto.png")));

        GridBagConstraints h = new GridBagConstraints();
        h.insets = new Insets(8, 8, 8, 8);
        h.gridx = 0; h.gridy = 0; h.anchor = GridBagConstraints.WEST; jPanel3.add(jLabel6, h);
        h.gridx = 1; h.gridy = 0; h.weightx = 1.0; h.fill = GridBagConstraints.HORIZONTAL; h.anchor = GridBagConstraints.CENTER; jPanel3.add(jLabel1, h);
        h.gridx = 2; h.gridy = 0; h.weightx = 0; h.fill = GridBagConstraints.NONE; h.anchor = GridBagConstraints.EAST; jPanel3.add(jLabel2, h);

        jPanel6 = new JPanel();
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new GridBagLayout());

        jLabel7 = new JLabel("Fecha del Gasto:");
        jLabel7.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jDateChooser_fecha = new JDateChooser();
        jDateChooser_fecha.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        jDateChooser_fecha.setPreferredSize(new Dimension(340, 40));

        jLabel9 = new JLabel("Monto del Gasto:");
        jLabel9.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jTextField_monto_gasto = new JTextField();
        jTextField_monto_gasto.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        jLabel10 = new JLabel("Categoria del Gasto:");
        jLabel10.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jComboBox_categoria_gastos = new JComboBox<>(new String[] { "Pagado", "Pendiente", "Cancelado" });
        jComboBox_categoria_gastos.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        jLabel14 = new JLabel("Motivo del Gasto:");
        jLabel14.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jTextField2 = new JTextField();
        jTextField2.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 10, 6, 10);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; jPanel6.add(jLabel7, c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; jPanel6.add(jDateChooser_fecha, c);
        c.gridx = 0; c.gridy = 1; c.fill = GridBagConstraints.NONE; c.weightx = 0; jPanel6.add(jLabel9, c);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; jPanel6.add(jTextField_monto_gasto, c);
        c.gridx = 0; c.gridy = 2; c.fill = GridBagConstraints.NONE; c.weightx = 0; jPanel6.add(jLabel10, c);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL; jPanel6.add(jComboBox_categoria_gastos, c);
        c.gridx = 0; c.gridy = 3; c.fill = GridBagConstraints.NONE; c.weightx = 0; jPanel6.add(jLabel14, c);
        c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; jPanel6.add(jTextField2, c);

        jPanel7 = new JPanel();
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new GridBagLayout());

        jPanel8 = new JPanel();
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new GridBagLayout());
        jLabel4 = new JLabel("Forma de Pago");
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel3 = new JLabel();
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/imagen/dar-dinero.png")));

        GridBagConstraints p8 = new GridBagConstraints();
        p8.insets = new Insets(4, 4, 4, 4);
        p8.gridx = 0; p8.gridy = 0; p8.anchor = GridBagConstraints.CENTER; jPanel8.add(jLabel4, p8);
        p8.gridx = 1; p8.gridy = 0; p8.anchor = GridBagConstraints.CENTER; jPanel8.add(jLabel3, p8);

        jComboBox_forma_de_pago = new JComboBox<>();
        jComboBox_forma_de_pago.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        GridBagConstraints p7 = new GridBagConstraints();
        p7.insets = new Insets(6, 10, 6, 10);
        p7.gridx = 0; p7.gridy = 0; p7.fill = GridBagConstraints.HORIZONTAL; p7.weightx = 1.0; jPanel7.add(jPanel8, p7);
        p7.gridx = 0; p7.gridy = 1; p7.fill = GridBagConstraints.HORIZONTAL; p7.weightx = 1.0; jPanel7.add(jComboBox_forma_de_pago, p7);

        jLabel_ref_pago = new JLabel("Ref. del pago:");
        jLabel_ref_pago.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jTextField_referencia = new JTextField();
        jTextField_referencia.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        GridBagConstraints p7b = new GridBagConstraints();
        p7b.insets = new Insets(6, 10, 6, 10);
        p7b.gridx = 0; p7b.gridy = 2; p7b.fill = GridBagConstraints.HORIZONTAL; p7b.weightx = 1.0; jPanel7.add(jLabel_ref_pago, p7b);
        p7b.gridx = 0; p7b.gridy = 3; p7b.fill = GridBagConstraints.HORIZONTAL; p7b.weightx = 1.0; jPanel7.add(jTextField_referencia, p7b);

        jLabel5 = new JLabel();
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/imagen/gastos (3).png")));

        jPanel4 = new JPanel();
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new GridBagLayout());
        jButton_aceptar = new JButton("Registrar");
        jButton_aceptar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton_limpiar = new JButton("Limpiar");
        jButton_limpiar.setFont(new Font("Segoe UI", Font.BOLD, 16));

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(0, 8, 0, 8);
        f.gridx = 0; f.gridy = 0; jPanel4.add(jButton_aceptar, f);
        f.gridx = 1; f.gridy = 0; jPanel4.add(jButton_limpiar, f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; jPanel1.add(jPanel3, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0; jPanel1.add(jPanel6, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 0.6; gbc.weighty = 1.0; jPanel1.add(jPanel7, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; jPanel1.add(jLabel5, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; jPanel1.add(jPanel4, gbc);

        getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        EventQueue.invokeLater(() -> new FormularioRegistroGastoNuevo().setVisible(true));
    }
}