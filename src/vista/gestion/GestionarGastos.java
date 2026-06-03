package vista.gestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.beans.PropertyVetoException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.Date;

public class GestionarGastos extends JInternalFrame {

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
    public JPanel resumenPanel;
    public JPanel centerPanel;
    public JPanel buttonPanel;

    public JTextField buscarField;
    public JSpinner fechaDesdeSpinner;
    public JSpinner fechaHastaSpinner;
    public JTable gastosTable;
    public JScrollPane gastosScrollPane;
    public JButton registrarGastoButton;

    public GestionarGastos() {
        setTitle("Gestión de Gastos");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(1366, 768);
        setPreferredSize(new Dimension(1366, 768));

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
        crearCentro();
        crearBotonera();

        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        configurarResponsive();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                try {
                    setMaximum(true);
                } catch (PropertyVetoException ignored) {
                    if (getDesktopPane() != null) {
                        setBounds(0, 0, getDesktopPane().getWidth(), getDesktopPane().getHeight());
                    }
                }
            }
        });
    }

    private void crearHeader() {
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("\uD83D\uDCB8 Gestión de Gastos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        JLabel subtitleLabel = new JLabel("Registro visual de gastos con estilo contable");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
    }

    private JPanel crearCard(String titulo, String valor, Color trendColor) {
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
        JLabel tr = new JLabel(" ");
        tr.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tr.setForeground(trendColor);
        tr.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        card.add(tr, BorderLayout.SOUTH);
        return card;
    }

    private void crearResumen() {
        resumenPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        resumenPanel.setOpaque(false);
        resumenPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        resumenPanel.add(crearCard("Total Gastos", "$0.00", DANGER));
        resumenPanel.add(crearCard("Gastos Hoy", "0", SUCCESS));
        resumenPanel.add(crearCard("Último Gasto", "$0.00", SUCCESS));
        resumenPanel.add(crearCard("Categorías", "0", SUCCESS));
    }

    private void crearCentro() {
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(resumenPanel, BorderLayout.CENTER);

        JPanel filtrosPanel = new JPanel(new GridBagLayout());
        filtrosPanel.setBackground(WHITE);
        filtrosPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Filtrar Gastos",
                        TitledBorder.LEFT,
                        TitledBorder.BELOW_TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        GridBagConstraints fc = new GridBagConstraints();
        fc.insets = new Insets(6, 8, 6, 8);
        fc.fill = GridBagConstraints.HORIZONTAL;
        fc.weightx = 1;

        JLabel buscarLabel = new JLabel("Buscar:");
        buscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 0; fc.gridy = 0; fc.weightx = 0; filtrosPanel.add(buscarLabel, fc);
        buscarField = new JTextField();
        buscarField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buscarField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        fc.gridx = 1; fc.gridy = 0; fc.weightx = 1; filtrosPanel.add(buscarField, fc);

        JLabel desdeLabel = new JLabel("Desde:");
        desdeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 2; fc.gridy = 0; fc.weightx = 0; filtrosPanel.add(desdeLabel, fc);
        fechaDesdeSpinner = new JSpinner(new SpinnerDateModel());
        fechaDesdeSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fechaDesdeSpinner.setValue(new Date());
        JSpinner.DateEditor desdeEditor = new JSpinner.DateEditor(fechaDesdeSpinner, "dd/MM/yyyy");
        fechaDesdeSpinner.setEditor(desdeEditor);
        fc.gridx = 3; fc.gridy = 0; fc.weightx = 0.5; filtrosPanel.add(fechaDesdeSpinner, fc);

        JLabel hastaLabel = new JLabel("Hasta:");
        hastaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 4; fc.gridy = 0; fc.weightx = 0; filtrosPanel.add(hastaLabel, fc);
        fechaHastaSpinner = new JSpinner(new SpinnerDateModel());
        fechaHastaSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fechaHastaSpinner.setValue(new Date());
        JSpinner.DateEditor hastaEditor = new JSpinner.DateEditor(fechaHastaSpinner, "dd/MM/yyyy");
        fechaHastaSpinner.setEditor(hastaEditor);
        fc.gridx = 5; fc.gridy = 0; fc.weightx = 0.5; filtrosPanel.add(fechaHastaSpinner, fc);

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(WHITE);
        tablaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Listado de Gastos",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        gastosTable = new JTable(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Número de gastos", "Encargado", "Monto del Gasto", "Motivo del Gasto",
                        "Categoría del Gasto", "Forma de Pago", "Ref. del Pago", "Fecha del Gasto"
                }
        ));
        gastosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gastosTable.setRowHeight(28);
        gastosTable.setGridColor(LIGHT);
        gastosTable.setShowVerticalLines(false);
        JTableHeader header = gastosTable.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gastosScrollPane = new JScrollPane(gastosTable);
        gastosScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablaPanel.add(filtrosPanel, BorderLayout.NORTH);
        tablaPanel.add(gastosScrollPane, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0; centerPanel.add(top, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1; centerPanel.add(tablaPanel, gbc);
    }

    private void crearBotonera() {
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        registrarGastoButton = new JButton("Registrar Gastos");
        registrarGastoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registrarGastoButton.setBackground(SUCCESS);
        registrarGastoButton.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; buttonPanel.add(registrarGastoButton, gbc);
    }

    private void configurarResponsive() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = getWidth();
                if (w >= 1100) {
                    resumenPanel.setLayout(new GridLayout(1, 4, 12, 12));
                } else {
                    resumenPanel.setLayout(new GridLayout(2, 2, 12, 12));
                }
                resumenPanel.revalidate();
                resumenPanel.repaint();
            }
        });
    }
}