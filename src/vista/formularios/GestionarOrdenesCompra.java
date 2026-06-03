/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionarOrdenesCompra extends JInternalFrame {
    // Componentes públicos
    public JTable ordersTable;
    public JTextField searchField;
    public JButton searchButton;
    public JButton verButton;
    public JButton editarButton;
    public JButton eliminarButton;
    public DefaultTableModel tableModel;

    public GestionarOrdenesCompra() {
        initComponents();
        setTitle("Gestionar Órdenes de Compra");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(1000, 600);
        setLocation(100, 100);
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Gestionar Órdenes de Compra");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        searchField = new JTextField();
        searchField.putClientProperty("JTextField.placeholderText", "Buscar órdenes...");

        searchButton = new JButton("Buscar");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Tabla de órdenes
        String[] columnNames = {"ID", "Proveedor", "Fecha Orden", "Total", "Estado", "Acciones"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        ordersTable = new JTable(tableModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setRowHeight(30);
        ordersTable.getTableHeader().setReorderingAllowed(false);

        // Personalizar el renderizador para la columna de estado
        ordersTable.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());

        // Personalizar el renderizador para la columna de acciones
        ordersTable.getColumnModel().getColumn(5).setCellRenderer(new AccionesCellRenderer());
        ordersTable.getColumnModel().getColumn(5).setCellEditor(new AccionesCellEditor());

        JScrollPane tableScroll = new JScrollPane(ordersTable);

        // Cargar datos de ejemplo
        cargarDatosEjemplo();

        // Ensamblar todo
        mainPanel.add(titulo, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(tableScroll, BorderLayout.SOUTH);

        // Agregar al internal frame
        add(mainPanel);

        // Configurar eventos
        configurarEventos();
    }

    private void cargarDatosEjemplo() {
        // Limpiar tabla
        tableModel.setRowCount(0);

        // Datos de ejemplo
        Object[][] datos = {
            {"OC-001", "Proveedor A", "2023-05-15", 1250.75, "completada", "Acciones"},
            {"OC-002", "Proveedor B", "2023-05-18", 850.50, "aprobada", "Acciones"},
            {"OC-003", "Proveedor C", "2023-05-20", 2100.00, "pendiente", "Acciones"},
            {"OC-004", "Proveedor A", "2023-05-22", 750.25, "cancelada", "Acciones"}
        };

        for (Object[] fila : datos) {
            tableModel.addRow(fila);
        }
    }

    private void configurarEventos() {
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                boolean found = false;
                for (int j = 0; j < tableModel.getColumnCount() - 1; j++) { // Excluir columna de acciones
                    Object value = tableModel.getValueAt(i, j);
                    if (value != null && value.toString().toLowerCase().contains(searchTerm)) {
                        found = true;
                        break;
                    }
                }
                
                // En una implementación real, aquí filtrarías la tabla
                if (!found && !searchTerm.isEmpty()) {
                    // Ocultar fila (en implementación real usarías un TableRowSorter)
                    JOptionPane.showMessageDialog(this, 
                        "Búsqueda: '" + searchTerm + "' - En una implementación completa se filtraría la tabla.");
                    break;
                }
            }
        });
    }

    // Renderizador personalizado para la columna de estado
    class EstadoCellRenderer extends JLabel implements javax.swing.table.TableCellRenderer {
        public EstadoCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String estado = value != null ? value.toString() : "";
            setText(estado.substring(0, 1).toUpperCase() + estado.substring(1));
            
            // Colores según el estado
            switch (estado) {
                case "pendiente":
                    setBackground(new Color(255, 243, 205));
                    setForeground(new Color(133, 100, 4));
                    break;
                case "aprobada":
                    setBackground(new Color(209, 236, 241));
                    setForeground(new Color(12, 84, 96));
                    break;
                case "completada":
                    setBackground(new Color(212, 237, 218));
                    setForeground(new Color(21, 87, 36));
                    break;
                case "cancelada":
                    setBackground(new Color(248, 215, 218));
                    setForeground(new Color(114, 28, 36));
                    break;
                default:
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
            }
            
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }

    // Renderizador personalizado para la columna de acciones
    class AccionesCellRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton verBtn, editarBtn, eliminarBtn;

        public AccionesCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            setOpaque(true);

            verBtn = new JButton("Ver");
            verBtn.setBackground(new Color(52, 152, 219));
            verBtn.setForeground(Color.WHITE);
            verBtn.setFocusPainted(false);
            verBtn.setPreferredSize(new Dimension(60, 25));

            editarBtn = new JButton("Editar");
            editarBtn.setBackground(new Color(243, 156, 18));
            editarBtn.setForeground(Color.WHITE);
            editarBtn.setFocusPainted(false);
            editarBtn.setPreferredSize(new Dimension(70, 25));

            eliminarBtn = new JButton("Eliminar");
            eliminarBtn.setBackground(new Color(231, 76, 60));
            eliminarBtn.setForeground(Color.WHITE);
            eliminarBtn.setFocusPainted(false);
            eliminarBtn.setPreferredSize(new Dimension(80, 25));

            add(verBtn);
            add(editarBtn);
            add(eliminarBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }
    }

    // Editor personalizado para la columna de acciones
    class AccionesCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private JPanel panel;
        private JButton verBtn, editarBtn, eliminarBtn;

        public AccionesCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(true);

            verBtn = new JButton("Ver");
            verBtn.setBackground(new Color(52, 152, 219));
            verBtn.setForeground(Color.WHITE);
            verBtn.setFocusPainted(false);
            verBtn.setPreferredSize(new Dimension(60, 25));

            editarBtn = new JButton("Editar");
            editarBtn.setBackground(new Color(243, 156, 18));
            editarBtn.setForeground(Color.WHITE);
            editarBtn.setFocusPainted(false);
            editarBtn.setPreferredSize(new Dimension(70, 25));

            eliminarBtn = new JButton("Eliminar");
            eliminarBtn.setBackground(new Color(231, 76, 60));
            eliminarBtn.setForeground(Color.WHITE);
            eliminarBtn.setFocusPainted(false);
            eliminarBtn.setPreferredSize(new Dimension(80, 25));

            // Configurar eventos
            verBtn.addActionListener(e -> {
                int row = ordersTable.getSelectedRow();
                if (row != -1) {
                    String id = tableModel.getValueAt(row, 0).toString();
                    JOptionPane.showMessageDialog(GestionarOrdenesCompra.this, 
                        "Viendo orden: " + id);
                }
                fireEditingStopped();
            });

            editarBtn.addActionListener(e -> {
                int row = ordersTable.getSelectedRow();
                if (row != -1) {
                    String id = tableModel.getValueAt(row, 0).toString();
                    JOptionPane.showMessageDialog(GestionarOrdenesCompra.this, 
                        "Editando orden: " + id);
                }
                fireEditingStopped();
            });

            eliminarBtn.addActionListener(e -> {
                int row = ordersTable.getSelectedRow();
                if (row != -1) {
                    String id = tableModel.getValueAt(row, 0).toString();
                    int confirm = JOptionPane.showConfirmDialog(GestionarOrdenesCompra.this,
                        "¿Está seguro de eliminar la orden " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(row);
                        JOptionPane.showMessageDialog(GestionarOrdenesCompra.this,
                            "Orden " + id + " eliminada correctamente.");
                    }
                }
                fireEditingStopped();
            });

            panel.add(verBtn);
            panel.add(editarBtn);
            panel.add(eliminarBtn);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Acciones";
        }
    }
}