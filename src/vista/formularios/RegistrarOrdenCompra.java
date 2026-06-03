/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarOrdenCompra extends JInternalFrame {
    // Componentes públicos
    public JComboBox<String> proveedorComboBox;
    public JTextField fechaOrdenField;
    public JTextField fechaEntregaField;
    public JComboBox<String> departamentoComboBox;
    public JTextField solicitanteField;
    public JTextArea comentariosArea;
    public JPanel itemsContainer;
    public JLabel totalLabel;
    public JButton addItemButton;
    public JButton registrarButton;
    public JButton limpiarButton;
    public double total = 0.0;

    public RegistrarOrdenCompra() {
        initComponents();
        setTitle("Registrar Orden de Compra");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(900, 700);
        setLocation(50, 50);
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Registrar Nueva Orden de Compra");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Panel del formulario con scroll
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        // Panel de campos del formulario
        JPanel camposPanel = new JPanel(new GridBagLayout());
        camposPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Proveedor
        gbc.gridx = 0; gbc.gridy = 0;
        camposPanel.add(createLabel("Proveedor"), gbc);
        gbc.gridx = 1;
        proveedorComboBox = new JComboBox<>(new String[]{"", "Proveedor A", "Proveedor B", "Proveedor C"});
        proveedorComboBox.setBackground(Color.WHITE);
        camposPanel.add(proveedorComboBox, gbc);

        // Fecha de Orden
        gbc.gridx = 0; gbc.gridy = 1;
        camposPanel.add(createLabel("Fecha de Orden"), gbc);
        gbc.gridx = 1;
        fechaOrdenField = new JTextField();
        fechaOrdenField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        camposPanel.add(fechaOrdenField, gbc);

        // Fecha de Entrega
        gbc.gridx = 0; gbc.gridy = 2;
        camposPanel.add(createLabel("Fecha de Entrega Esperada"), gbc);
        gbc.gridx = 1;
        fechaEntregaField = new JTextField();
        camposPanel.add(fechaEntregaField, gbc);

        // Departamento
        gbc.gridx = 0; gbc.gridy = 3;
        camposPanel.add(createLabel("Departamento Solicitante"), gbc);
        gbc.gridx = 1;
        departamentoComboBox = new JComboBox<>(new String[]{"", "Ventas", "Marketing", "Operaciones", "TI"});
        departamentoComboBox.setBackground(Color.WHITE);
        camposPanel.add(departamentoComboBox, gbc);

        // Solicitante
        gbc.gridx = 0; gbc.gridy = 4;
        camposPanel.add(createLabel("Persona Solicitante"), gbc);
        gbc.gridx = 1;
        solicitanteField = new JTextField();
        camposPanel.add(solicitanteField, gbc);

        // Comentarios
        gbc.gridx = 0; gbc.gridy = 5;
        camposPanel.add(createLabel("Comentarios"), gbc);
        gbc.gridx = 1;
        comentariosArea = new JTextArea(4, 20);
        comentariosArea.setLineWrap(true);
        comentariosArea.setWrapStyleWord(true);
        JScrollPane comentariosScroll = new JScrollPane(comentariosArea);
        camposPanel.add(comentariosScroll, gbc);

        // Sección de Artículos
        JLabel articulosLabel = new JLabel("Artículos");
        articulosLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        articulosLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        // Container para items
        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setBackground(Color.WHITE);
        itemsContainer.setBorder(new LineBorder(Color.LIGHT_GRAY));

        // Agregar primera fila de artículo
        addItemRow();

        // Botón para agregar artículo
        addItemButton = new JButton("Agregar Artículo");
        addItemButton.setBackground(new Color(52, 152, 219));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setFocusPainted(false);

        // Sección de total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalPanel.add(totalLabel);

        // Botones de acción
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonesPanel.setBackground(Color.WHITE);
        registrarButton = new JButton("Registrar Orden");
        registrarButton.setBackground(new Color(46, 204, 113));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFocusPainted(false);

        limpiarButton = new JButton("Limpiar Formulario");
        limpiarButton.setBackground(new Color(243, 156, 18));
        limpiarButton.setForeground(Color.WHITE);
        limpiarButton.setFocusPainted(false);

        botonesPanel.add(limpiarButton);
        botonesPanel.add(registrarButton);

        // Ensamblar el formulario
        formPanel.add(camposPanel);
        formPanel.add(articulosLabel);
        formPanel.add(itemsContainer);
        formPanel.add(addItemButton);
        formPanel.add(totalPanel);
        formPanel.add(botonesPanel);

        // Agregar todo al panel principal
        mainPanel.add(titulo, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Agregar al internal frame
        add(mainPanel);

        // Configurar eventos
        configurarEventos();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }

    public void addItemRow() {
        JPanel itemRow = new JPanel();
        itemRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemRow.setBackground(Color.WHITE);

        JTextField descripcionField = new JTextField(20);
        descripcionField.setPreferredSize(new Dimension(200, 30));
        descripcionField.putClientProperty("fieldType", "descripcion");

        JTextField cantidadField = new JTextField(10);
        cantidadField.setPreferredSize(new Dimension(80, 30));
        cantidadField.putClientProperty("fieldType", "cantidad");

        JTextField precioField = new JTextField(10);
        precioField.setPreferredSize(new Dimension(100, 30));
        precioField.putClientProperty("fieldType", "precio");

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBackground(new Color(231, 76, 60));
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setFocusPainted(false);

        // Agregar componentes a la fila
        itemRow.add(new JLabel("Descripción:"));
        itemRow.add(descripcionField);
        itemRow.add(new JLabel("Cantidad:"));
        itemRow.add(cantidadField);
        itemRow.add(new JLabel("Precio:"));
        itemRow.add(precioField);
        itemRow.add(eliminarButton);

        itemsContainer.add(itemRow);
        itemsContainer.revalidate();
        itemsContainer.repaint();

        // Configurar eventos para los campos
        cantidadField.addActionListener(e -> calcularTotal());
        precioField.addActionListener(e -> calcularTotal());

        // Configurar evento para el botón eliminar
        eliminarButton.addActionListener(e -> {
            if (itemsContainer.getComponentCount() > 1) {
                itemsContainer.remove(itemRow);
                itemsContainer.revalidate();
                itemsContainer.repaint();
                calcularTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Debe haber al menos un artículo en la orden.");
            }
        });
    }

    public void calcularTotal() {
        total = 0.0;
        Component[] components = itemsContainer.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel row = (JPanel) comp;
                Component[] fields = row.getComponents();
                
                double cantidad = 0;
                double precio = 0;
                
                for (Component field : fields) {
                    if (field instanceof JTextField) {
                        JTextField txtField = (JTextField) field;
                        String fieldType = (String) txtField.getClientProperty("fieldType");
                        
                        try {
                            if ("cantidad".equals(fieldType)) {
                                cantidad = Double.parseDouble(txtField.getText());
                            } else if ("precio".equals(fieldType)) {
                                precio = Double.parseDouble(txtField.getText());
                            }
                        } catch (NumberFormatException e) {
                            // Ignorar errores de formato
                        }
                    }
                }
                
                total += cantidad * precio;
            }
        }
        
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    private void configurarEventos() {
        addItemButton.addActionListener(e -> addItemRow());

        limpiarButton.addActionListener(e -> {
            proveedorComboBox.setSelectedIndex(0);
            fechaOrdenField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            fechaEntregaField.setText("");
            departamentoComboBox.setSelectedIndex(0);
            solicitanteField.setText("");
            comentariosArea.setText("");
            
            // Limpiar items excepto el primero
            while (itemsContainer.getComponentCount() > 1) {
                itemsContainer.remove(itemsContainer.getComponentCount() - 1);
            }
            
            // Limpiar campos del primer item
            Component firstRow = itemsContainer.getComponent(0);
            if (firstRow instanceof JPanel) {
                Component[] fields = ((JPanel) firstRow).getComponents();
                for (Component field : fields) {
                    if (field instanceof JTextField) {
                        ((JTextField) field).setText("");
                    }
                }
            }
            
            total = 0.0;
            totalLabel.setText("Total: $0.00");
            itemsContainer.revalidate();
            itemsContainer.repaint();
        });

        registrarButton.addActionListener(e -> {
            // Validaciones básicas
            if (proveedorComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor.");
                return;
            }
            
            if (solicitanteField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del solicitante.");
                return;
            }
            
            // Validar que haya al menos un artículo con datos
            boolean articulosValidos = validarArticulos();
            if (!articulosValidos) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos de los artículos.");
                return;
            }
            
            JOptionPane.showMessageDialog(this, "Orden de compra registrada exitosamente.");
        });
    }

    private boolean validarArticulos() {
        Component[] components = itemsContainer.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel row = (JPanel) comp;
                Component[] fields = row.getComponents();
                
                boolean descripcionValida = false;
                boolean cantidadValida = false;
                boolean precioValido = false;
                
                for (Component field : fields) {
                    if (field instanceof JTextField) {
                        JTextField txtField = (JTextField) field;
                        String fieldType = (String) txtField.getClientProperty("fieldType");
                        String text = txtField.getText().trim();
                        
                        if ("descripcion".equals(fieldType) && !text.isEmpty()) {
                            descripcionValida = true;
                        } else if ("cantidad".equals(fieldType) && !text.isEmpty()) {
                            try {
                                Double.parseDouble(text);
                                cantidadValida = true;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        } else if ("precio".equals(fieldType) && !text.isEmpty()) {
                            try {
                                Double.parseDouble(text);
                                precioValido = true;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        }
                    }
                }
                
                if (!descripcionValida || !cantidadValida || !precioValido) {
                    return false;
                }
            }
        }
        
        return true;
    }
}