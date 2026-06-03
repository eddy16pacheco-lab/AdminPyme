/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import vista.gestion.GestionarInventario;
import vista.VistaInventarioModerno;
import javax.swing.*;
import java.awt.*;

/**
 * Clase principal para probar las vistas
 */
public class Main {

    public static void main(String[] args) {
        // Usar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    
    private static void createAndShowGUI() {
        // Frame principal
        JFrame mainFrame = new JFrame("Demo - Sistema de Inventario");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400, 900);
        mainFrame.setLocationRelativeTo(null);
        
        // DesktopPane para InternalFrames
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 242, 245));
        mainFrame.setContentPane(desktopPane);
        
        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuVistas = new JMenu("Vistas");
        menuVistas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JMenuItem itemVistaOriginal = new JMenuItem("Abrir Vista Inventario Moderno");
        JMenuItem itemGestionSimple = new JMenuItem("Abrir Gestión Simple");
        JMenuItem itemCerrarTodas = new JMenuItem("Cerrar Todas");
        
        // Acción para VistaInventarioModerno
        itemVistaOriginal.addActionListener(e -> {
            // Evitar abrir múltiples instancias
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame.getTitle().contains("Sistema de Gestión de Inventario")) {
                    frame.toFront();
                    return;
                }
            }
            
            VistaInventarioModerno vista = new VistaInventarioModerno();
            vista.setLocation(30, 30);
            desktopPane.add(vista);
            vista.setVisible(true);
            try {
                vista.setSelected(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Acción para GestionarInventario
        itemGestionSimple.addActionListener(e -> {
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame.getTitle().contains("Sistema de Gestión de Inventario")) {
                    frame.toFront();
                    return;
                }
            }
            
            GestionarInventario gestion = new GestionarInventario();
            gestion.setLocation(80, 80);
            desktopPane.add(gestion);
            gestion.setVisible(true);
            try {
                gestion.setSelected(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Acción para cerrar todas
        itemCerrarTodas.addActionListener(e -> {
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                frame.dispose();
            }
        });
        
        menuVistas.add(itemVistaOriginal);
        menuVistas.add(itemGestionSimple);
        menuVistas.addSeparator();
        menuVistas.add(itemCerrarTodas);
        
        menuBar.add(menuVistas);
        mainFrame.setJMenuBar(menuBar);
        
        // Panel de control rápido
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(PRIMARY);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnVistaCompleta = createControlButton("Abrir Vista Completa", SUCCESS);
        btnVistaCompleta.addActionListener(itemVistaOriginal.getActionListeners()[0]);
        
        JButton btnGestionSimple = createControlButton("Abrir Gestión Simple", SECONDARY);
        btnGestionSimple.addActionListener(itemGestionSimple.getActionListeners()[0]);
        
        JButton btnSalir = createControlButton("Salir", DANGER);
        btnSalir.addActionListener(e -> System.exit(0));
        
        controlPanel.add(btnVistaCompleta);
        controlPanel.add(btnGestionSimple);
        controlPanel.add(btnSalir);
        
        mainFrame.add(controlPanel, BorderLayout.NORTH);
        
        // Mensaje de bienvenida
        JLabel welcomeLabel = new JLabel(
            "<html><div style='text-align: center; padding: 50px;'>" +
            "<h1 style='color: #2C3E50;'>Sistema de Gestión de Inventario</h1>" +
            "<p style='color: #7F8C8D; font-size: 16px;'>Seleccione una vista para comenzar</p>" +
            "<p style='color: #95A5A6; font-size: 14px; margin-top: 30px;'>" +
            "<strong>Vista Moderna:</strong> 5 pestañas con todas las funcionalidades<br>" +
            "<strong>Gestión Simple:</strong> 3 pestañas con interfaz simplificada</p>" +
            "</div></html>", 
            SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        desktopPane.add(welcomeLabel, JLayeredPane.DEFAULT_LAYER);
        welcomeLabel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        
        mainFrame.setVisible(true);
        
        // Abrir automáticamente la vista moderna
        SwingUtilities.invokeLater(() -> itemVistaOriginal.doClick());
    }
    
    private static Color PRIMARY = new Color(44, 62, 80);
    private static Color SECONDARY = new Color(52, 152, 219);
    private static Color SUCCESS = new Color(39, 174, 96);
    private static Color DANGER = new Color(231, 76, 60);
    
    private static JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
}