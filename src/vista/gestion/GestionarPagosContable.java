package vista.gestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Date;
import java.beans.PropertyVetoException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GestionarPagosContable extends JInternalFrame {

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

    public JTextField pagosBuscarField;
    public JTable pagosTable;
    public JScrollPane pagosScrollPane;
    public JSpinner pagosFechaDesdeSpinner;
    public JSpinner pagosFechaHastaSpinner;
    public JButton crearPagoButton;

    public GestionarPagosContable() {
        setTitle("Pagos de Productos");
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
        setSize(1366, 768);

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
        JLabel titleLabel = new JLabel("\uD83D\uDCB3 Gestión de Pagos de Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        JLabel subtitleLabel = new JLabel("Interfaz visual estilo contable");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
    }

    private void crearResumen() {
        resumenPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        resumenPanel.setOpaque(false);
        resumenPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        resumenPanel.add(crearCard("Total Pagos", "$0.00", SUCCESS));
        resumenPanel.add(crearCard("Pendiente", "$0.00", DANGER));
        resumenPanel.add(crearCard("Último Pago", "$0.00", SUCCESS));
        resumenPanel.add(crearCard("Pagos Hoy", "0", SUCCESS));
    }

    private JPanel crearCard(String t, String v, Color trendColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        JLabel titulo = new JLabel(t);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titulo.setForeground(DARK);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel valor = new JLabel(v);
        valor.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valor.setForeground(PRIMARY);
        valor.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel tend = new JLabel(" ");
        tend.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tend.setForeground(trendColor);
        tend.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titulo, BorderLayout.NORTH);
        card.add(valor, BorderLayout.CENTER);
        card.add(tend, BorderLayout.SOUTH);
        return card;
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


        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(WHITE);
        tablaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(LIGHT, 1),
                        "Historial de Pagos",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        DARK
                ),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(WHITE);
        GridBagConstraints fc = new GridBagConstraints();
        fc.insets = new Insets(4, 6, 4, 6);
        fc.fill = GridBagConstraints.HORIZONTAL;
        fc.weightx = 1;
        JLabel buscarLabel = new JLabel("Buscar:");
        buscarLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 0; fc.gridy = 0; fc.weightx = 0; filterPanel.add(buscarLabel, fc);
        pagosBuscarField = new JTextField();
        pagosBuscarField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pagosBuscarField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        fc.gridx = 1; fc.gridy = 0; fc.weightx = 1; filterPanel.add(pagosBuscarField, fc);

        JLabel desdeLabel = new JLabel("Desde:");
        desdeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 2; fc.gridy = 0; fc.weightx = 0; filterPanel.add(desdeLabel, fc);
        pagosFechaDesdeSpinner = new JSpinner(new SpinnerDateModel());
        pagosFechaDesdeSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pagosFechaDesdeSpinner.setValue(new Date());
        JSpinner.DateEditor desdeEditor = new JSpinner.DateEditor(pagosFechaDesdeSpinner, "dd/MM/yyyy");
        pagosFechaDesdeSpinner.setEditor(desdeEditor);
        fc.gridx = 3; fc.gridy = 0; fc.weightx = 0.5; filterPanel.add(pagosFechaDesdeSpinner, fc);

        JLabel hastaLabel = new JLabel("Hasta:");
        hastaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fc.gridx = 4; fc.gridy = 0; fc.weightx = 0; filterPanel.add(hastaLabel, fc);
        pagosFechaHastaSpinner = new JSpinner(new SpinnerDateModel());
        pagosFechaHastaSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pagosFechaHastaSpinner.setValue(new Date());
        JSpinner.DateEditor hastaEditor = new JSpinner.DateEditor(pagosFechaHastaSpinner, "dd/MM/yyyy");
        pagosFechaHastaSpinner.setEditor(hastaEditor);
        fc.gridx = 5; fc.gridy = 0; fc.weightx = 0.5; filterPanel.add(pagosFechaHastaSpinner, fc);
        tablaPanel.add(filterPanel, BorderLayout.NORTH);

        pagosTable = new JTable(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Forma", "Referencia", "Monto"}
        ));
        pagosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pagosTable.setRowHeight(28);
        pagosTable.setGridColor(LIGHT);
        pagosTable.setShowVerticalLines(false);
        JTableHeader header = pagosTable.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pagosScrollPane = new JScrollPane(pagosTable);
        pagosScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablaPanel.add(pagosScrollPane, BorderLayout.CENTER);

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

        crearPagoButton = new JButton("Crear nuevo pago");
        crearPagoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        crearPagoButton.setBackground(SUCCESS);
        crearPagoButton.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; buttonPanel.add(crearPagoButton, gbc);
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