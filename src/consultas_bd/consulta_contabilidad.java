package consultas_bd;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class consulta_contabilidad extends sql_consulta{
 private TextArea textArea;
 private DecimalFormat df = new DecimalFormat("#,##0.00");
 
 public void mostrar_libro_diario(JTable tabla, boolean filtrado, Date fecha_ini, Date fecha_fin) {
     String[] columnas={"Numero de asiento","Fecha de asiento","concepto","Numero de cuenta","Nombre de la cuenta","Tipo movimiento","Monto","Descripción"};
        DefaultTableModel  modelo = new DefaultTableModel(columnas,0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = filtrado ? "SELECT * FROM libro_diario d" : 
        "SELECT * FROM libro_diario d WHERE d.fecha_asiento BETWEEN '"+new java.sql.Date(fecha_ini.getTime())+"' AND '"+new java.sql.Date(fecha_fin.getTime())+"';";
        try {
            Object[] datos = new Object[8];
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    public void consulta_movimiento_cuenta(JTable tabla, Date fecha_inicio, Date fecha_final, boolean respuesta) {
String[] columnNames = {
            "Fecha", "Descripción", "Tipo Transacción", "Categoría", "Monto"
        };

       DefaultTableModel modelo = new DefaultTableModel(columnNames,0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = respuesta ? "SELECT r.fecha_transaccion,r.descripcion,r.tipo_movimiento,r.categoria,r.monto,r.estado FROM registro_financiero r ORDER BY r.id_registro DESC"
                : "SELECT r.fecha_transaccion,r.descripcion,r.tipo_movimiento,r.categoria,r.monto,r.estado FROM registro_financiero r  WHERE r.fecha_registro BETWEEN '"+new java.sql.Date(fecha_inicio.getTime())+"' AND '"+new java.sql.Date(fecha_final.getTime()) +"';";
        Object[] datos = new Object[5];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
               modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al cargar los movimiento cuenta: " + e);
        }
    }

    public double saldo_fondos() {
        double saldo_fondos = 0;
        query = "SELECT saldo_actual FROM saldo_fondos";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                saldo_fondos = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el saldo: " + e.getMessage());
        }

        return saldo_fondos;
    }

    public double egresos_mensuales() {
        double egreso = 0;
        query="SELECT SUM(monto) AS Total_Egresos,ROUND(AVG(monto), 2) AS Promedio_Egreso FROM registro_financiero r WHERE tipo_movimiento = 'Egreso' AND estado = 'Completado' AND MONTH(fecha_transaccion)=MONTH(CURDATE()) ORDER BY r.id_registro DESC;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                egreso = rs.getDouble(1);
            }
        } catch (SQLException e) {
        }
        return egreso;
    }

    public double ingresos_mensuales() {
        double ingresos = 0;
        query = "SELECT SUM(dp.monto_cancelado)FROM venta v INNER JOIN descripcion_pago dp ON v.id_venta=dp.id_venta WHERE MONTH(v.fecha)=MONTH(NOW());";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                ingresos = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al seleccionar las ganacias: ");
        }
        return ingresos;
    }

    public int total_transacciones_mensuales(){
    query="SELECT COUNT(r.id_registro) FROM registro_financiero r WHERE MONTH(r.fecha_transaccion)=MONTH(CURDATE())";
    int total_transacciones=0; 
    try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next()){
            total_transacciones=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar las transacciones"+e);
        }
    return total_transacciones;
    }
    public double total_ventas_al_mes(){
    double total_ventas=0;
    query="SELECT SUM(dv.monto_venta) FROM detalle_venta dv INNER JOIN venta v ON dv.id_venta=v.id_venta WHERE MONTH(v.fecha)=MONTH(CURDATE()) AND dv.estado_venta='Aprobado'";
        try {
            st=con.EstablecerConexion().createStatement();
            rs=st.executeQuery(query);
            if(rs.next())
            {
            total_ventas=rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar total de ventas mensuales:" +e);
        }
    
    return total_ventas;
    }
    public JTextArea crearTextAreaFormateado() {
        JTextArea textArea = new JTextArea();
        configurarTextAreaBasico(textArea);
        return textArea;
    }

    /**
     * Configura un JTextArea existente con formato para reportes
     */
    public void configurarTextAreaBasico(JTextArea textArea) {
        if (textArea == null) return;
        
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    /**
     * Carga el Estado de Resultados completo en un JTextArea
     */
    public final Color LIGHT = new Color(236, 240, 241);

    public void cargarEstadoResultadosEnTextArea(JTextArea textArea) {
        if (!validarTextArea(textArea)) {
            return;
        }

        try {
            textArea.setText("");
      
            configurarTextAreaBasico(textArea);
            
            query = "SELECT descripcion, tipo, categoria, Ingresos, Egresos, \"Total General\" " +
                    "FROM vista_ingresos_egresos_completa c WHERE c.fecha=CURDATE()" +
                    "ORDER BY " +
                    "CASE tipo " +
                    "    WHEN 'INGRESO' THEN 1 " +
                    "    WHEN 'EGRESO' THEN 2 " +
                    "    WHEN 'TOTAL' THEN 3 " +
                    "    WHEN 'RESULTADO' THEN 4 " +
 "END, descripcion";

            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();

            generarEncabezadoEstadoResultados(textArea, null);
            procesarDatosEstadoResultados(textArea);
        
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            mostrarErrorTextArea(textArea, "Error al cargar estado de resultados: " + e.getMessage());
        }
    }

    /**
     * Carga Estado de Resultados filtrado por fecha
     */
    public void cargarEstadoResultadosPorFecha(JTextArea textArea, Date fechaDesde) {
        if (!validarTextArea(textArea)) return;
        
        try {
            textArea.setText("");
            configurarTextAreaBasico(textArea);
            
            String sql = "SELECT descripcion, tipo, categoria, Ingresos, Egresos, \"Total General\" " +
                        "FROM vista_ingresos_egresos_completa " +
                        "WHERE fecha=? " +
                        "ORDER BY fecha, " +
                        "CASE tipo " +
                        "    WHEN 'INGRESO' THEN 1 " +
                        "    WHEN 'EGRESO' THEN 2 " +
                        "    WHEN 'TOTAL' THEN 3 " +
                        "    WHEN 'RESULTADO' THEN 4 " +
                        "END";
            
            ps = con.EstablecerConexion().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(fechaDesde.getTime()));
            
            rs = ps.executeQuery();
            
            generarEncabezadoEstadoResultados(textArea, fechaDesde);
            procesarDatosEstadoResultados(textArea);
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            mostrarErrorTextArea(textArea, "Error al cargar datos por fecha: " + e.getMessage());
        }
    }

    /**
     * Carga un resumen financiero compacto
     */
    public void cargarResumenCompacto(JTextArea textArea) {
        if (!validarTextArea(textArea)) return;
        
        try {
            textArea.setText("");
            configurarTextAreaBasico(textArea);
            
            query = "SELECT descripcion, tipo, Ingresos, Egresos, \"Total General\" " +
                    "FROM vista_ingresos_egresos_completa " +
                    "WHERE tipo IN ('TOTAL', 'RESULTADO') " +
                    "ORDER BY " +
                    "CASE tipo " +
                    "    WHEN 'TOTAL' THEN 1 " +
                    "    WHEN 'RESULTADO' THEN 2 " +
                    "END";
            
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            generarResumenCompacto(textArea);
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            mostrarErrorTextArea(textArea, "Error al cargar resumen: " + e.getMessage());
        }
    }

    /**
     * MÉTODOS AUXILIARES PRIVADOS
     */

    private boolean validarTextArea(JTextArea textArea) {
        if (textArea == null) {
            System.err.println("Error: JTextArea no puede ser nulo");
            return false;
        }
        return true;
    }

    private void mostrarErrorTextArea(JTextArea textArea, String mensaje) {
        textArea.setText(mensaje);
        textArea.setFont(new Font("SansSerif", Font.BOLD, 20));
    }

    private void generarEncabezadoEstadoResultados(JTextArea textArea, Date fechaDesde) {
        agregarLineaConFormato(textArea, "═".repeat(70));
        
        String titulo;
        if (fechaDesde != null) {
            titulo = "    ESTADO DE RESULTADOS - " + 
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaDesde);
        } else {
            titulo = "               ESTADO DE RESULTADOS - INGRESOS VS EGRESOS";
        }
        
        agregarLineaConFormato(textArea, titulo);
        agregarLineaConFormato(textArea, "═".repeat(70));
        agregarLineaConFormato(textArea, String.format("%-40s %12s %12s %12s", 
            "DESCRIPCIÓN", "INGRESOS", "EGRESOS", "TOTAL"));
        agregarLineaConFormato(textArea, "-".repeat(70));
    }

    private void procesarDatosEstadoResultados(JTextArea textArea) throws SQLException {
        double totalIngresos = 0;
        double totalEgresos = 0;
        
        while (rs.next()) {
            String descripcion = rs.getString("descripcion");
            String tipo = rs.getString("tipo");
            double ingresos = rs.getDouble("Ingresos");
            double egresos = rs.getDouble("Egresos");
            double total =1000; //rs.getDouble("Total General");
            
            // Acumular totales
            if ("INGRESO".equals(tipo)) {
                totalIngresos += ingresos;
            } else if ("EGRESO".equals(tipo)) {
                totalEgresos += egresos;
            }
            
            String linea = formatearLinea(descripcion, tipo, ingresos, egresos, total);
            agregarLineaConFormato(textArea, linea);
            
            // Agregar separadores para totales
            if ("TOTAL".equals(tipo) && descripcion.contains("INGRESOS")) {
                agregarLineaConFormato(textArea, "-".repeat(70));
            }
        }
        
        // Resultado final
        double resultadoNeto = totalIngresos - totalEgresos;
        agregarLineaConFormato(textArea, "═".repeat(70));
        agregarLineaConFormato(textArea, String.format("%-40s %12s %12s %12s", 
            "RESULTADO NETO FINAL:", "", "", "Bs" + df.format(resultadoNeto)));
        agregarLineaConFormato(textArea, "═".repeat(70));
    }

    private void generarResumenCompacto(JTextArea textArea) throws SQLException {
        agregarLineaConFormato(textArea, "┌──────────────────────────────────────────┐");
        agregarLineaConFormato(textArea, "│         RESUMEN FINANCIERO               │");
        agregarLineaConFormato(textArea, "├──────────────────────────────────────────┤");
        
        while (rs.next()) {
            String descripcion = rs.getString("descripcion");
            double ingresos = rs.getDouble("Ingresos");
            double egresos = rs.getDouble("Egresos");
            double total = rs.getDouble("Total General");
            
            if ("TOTAL INGRESOS".equals(descripcion)) {
                agregarLineaConFormato(textArea, String.format("│ %-20s: %15s │", "Total Ingresos", "Bs" + df.format(ingresos)));
            } else if ("TOTAL EGRESOS".equals(descripcion)) {
                agregarLineaConFormato(textArea, String.format("│ %-20s: %15s │", "Total Egresos", "Bs" + df.format(egresos)));
            } else if ("RESULTADO NETO".equals(descripcion)) {
                agregarLineaConFormato(textArea, "├──────────────────────────────────────────┤");
                String resultado = total >= 0 ? 
                    String.format("│ %-20s: %15s │", "Utilidad Neta", "Bs" + df.format(total)) :
                    String.format("│ %-20s: %15s │", "Pérdida Neta", "Bs" + df.format(Math.abs(total)));
                agregarLineaConFormato(textArea, resultado);
            }
        }
        
        agregarLineaConFormato(textArea, "└──────────────────────────────────────────┘");
    }

    /**
     * Formatea una línea según el tipo de registro
     */
    private String formatearLinea(String descripcion, String tipo, double ingresos, double egresos, double total) {
        String formato = "%-40s %12s %12s %12s";
        
        switch (tipo) {
            case "INGRESO":
                return String.format(formato, 
                    "  " + descripcion, 
                    "Bs" + df.format(ingresos), 
                    "", 
                    "");
                    
            case "EGRESO":
                return String.format(formato, 
                    "  " + descripcion, 
                    "", 
                    "Bs" + df.format(egresos), 
                    "");
                    
            case "TOTAL":
                if (descripcion.contains("INGRESOS")) {
                    return String.format(formato, 
                        "► " + descripcion, 
                        "────────────", 
                        "", 
                        "Bs" + df.format(ingresos));
                } else {
                    return String.format(formato, 
                        "► " + descripcion, 
                        "", 
                        "────────────", 
                        "Bs" + df.format(egresos));
                }
                
            case "RESULTADO":
                return String.format(formato, 
                    "★ " + descripcion, 
                    "", 
                    "", 
                    "Bs" + df.format(total));
                    
            default:
                return String.format(formato, descripcion, 
                    ingresos > 0 ? "Bs" + df.format(ingresos) : "", 
                    egresos > 0 ? "Bs" + df.format(egresos) : "", 
                    "$" + df.format(total));
        }
    }
    
    /**
     * Agrega una línea al JTextArea
     */
    private void agregarLineaConFormato(JTextArea textArea, String linea) {
        textArea.append(linea + "\n");
    }

public void exportarLibroDiarioCSV(JTable tabla, boolean filtrado, Date fecha_ini, Date fecha_fin) {
    // Siempre preguntar la ruta
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccione donde guardar el Libro Diario CSV");
    
    // Sugerir nombre por defecto
    String nombreSugerido;
    if (filtrado) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nombreSugerido = "Libro_Diario_" + sdf.format(fecha_ini) + "_a_" + sdf.format(fecha_fin) + ".csv";
    } else {
        nombreSugerido = "Libro_Diario_Completo.csv";
    }
    
    fileChooser.setSelectedFile(new File(nombreSugerido));
    
    // Mostrar diálogo de guardado
    int userSelection = fileChooser.showSaveDialog(null);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        
        // Confirmar si el archivo ya existe
        if (fileToSave.exists()) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "El archivo ya existe. ¿Desea reemplazarlo?",
                "Archivo Existente",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return; // El usuario canceló
            }
        }
        
        // Asegurar extensión .csv
        if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".csv")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
        }
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileToSave, false), true)) {
            TableModel model = tabla.getModel();
            
            // Escribir encabezado informativo
            pw.println("LIBRO DIARIO CONTABLE");
            if (filtrado) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                pw.println("Período: " + sdf.format(fecha_ini) + " al " + sdf.format(fecha_fin));
            } else {
                pw.println("Todos los registros - Período completo");
            }
            pw.println("Exportado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            pw.println(); // Línea en blanco
            
            // Escribir encabezados de columnas
            for (int col = 0; col < model.getColumnCount(); col++) {
                pw.print(escaparCSV(model.getColumnName(col)));
                if (col < model.getColumnCount() - 1) {
                    pw.print(";");
                }
            }
            pw.println();
            
            // Escribir datos
            double totalDebe = 0;
            double totalHaber = 0;
            
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    pw.print(escaparCSV(value != null ? value.toString() : ""));
                    
                    // Acumular totales
                    if (col == 6 && value != null) { // Columna de Monto
                        try {
                            double monto = Double.parseDouble(value.toString());
                            String tipoMovimiento = model.getValueAt(row, 5).toString();
                            if ("DEBE".equalsIgnoreCase(tipoMovimiento)) {
                                totalDebe += monto;
                            } else if ("HABER".equalsIgnoreCase(tipoMovimiento)) {
                                totalHaber += monto;
                            }
                        } catch (NumberFormatException e) {
                            // Ignorar errores de conversión
                        }
                    }
                    
                    if (col < model.getColumnCount() - 1) {
                        pw.print(";");
                    }
                }
                pw.println();
            }
            
            // Escribir totales
            pw.println();
            pw.println(";;;TOTAL DEBE:;;;" + String.format("%.2f", totalDebe).replace('.', ','));
            pw.println(";;;TOTAL HABER:;;;" + String.format("%.2f", totalHaber).replace('.', ','));
            pw.println(";;;DIFERENCIA:;;;" + String.format("%.2f", (totalDebe - totalHaber)).replace('.', ','));
            
            JOptionPane.showMessageDialog(null, 
                "✅ Libro Diario exportado exitosamente!\n\n" +
                "📁 Archivo: " + fileToSave.getName() + "\n" +
                "📊 Registros exportados: " + model.getRowCount() + "\n" +
                "💾 Ubicación: " + fileToSave.getParent() + "\n\n" +
                "El archivo CSV puede ser abierto con Excel.",
                "Exportación Completada", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "❌ Error al exportar a CSV:\n" + e.getMessage(),
                "Error de Exportación", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, 
            "Exportación cancelada por el usuario.",
            "Exportación Cancelada", 
            JOptionPane.WARNING_MESSAGE);
    }
}

private String escaparCSV(String texto) {
    if (texto == null) return "";
    
    // Si contiene punto y coma, comillas o saltos de línea, envolver en comillas
    if (texto.contains(";") || texto.contains("\"") || texto.contains("\n") || texto.contains("\r")) {
        // Escapar comillas duplicándolas
        texto = texto.replace("\"", "\"\"");
        return "\"" + texto + "\"";
    }
    return texto;
}
}
