package controlador;

import consultas_bd.conexion_bd;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import vista.VistaInventarioModerno;

// Imports específicos para iTextPDF (evitar conflictos)
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Imports para Apache POI (Excel .xlsx)
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Controlador para manejar la generación y exportación de reportes
 * Usa la vista de base de datos: reporte_disponibilidad_inventario
 */
public class ControladorReportes {

    private VistaInventarioModerno vista;
    private conexion_bd conexion;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private static final String NOMBRE_EMPRESA = "Inversiones\nLa Frontera del Sabor\nCG";
    private String rutaLogoEmpresa = "imagen/AdminPyme.jpg";

    // Datos del reporte actual para exportación
    private List<String[]> datosReporteActual = new ArrayList<>();
    private String tipoReporteActual = "";

    public ControladorReportes(VistaInventarioModerno vista) {
        this.vista = vista;
        this.conexion = new conexion_bd();
        inicializarListeners();
    }

    private void inicializarListeners() {
        this.vista.jButton_generar_reporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteSeleccionado();
            }
        });

        this.vista.jButton_exportar_pdf_inventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarPDF();
            }
        });

        this.vista.jButton_exportar_excel_inventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarExcel();
            }
        });
    }

    private void generarReporteSeleccionado() {
        String reporteSeleccionado = (String) vista.jComboBox_tipo_reporte.getSelectedItem();

        if (reporteSeleccionado == null || reporteSeleccionado.equals("Seleccionar reporte...")) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, seleccione un tipo de reporte.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (reporteSeleccionado) {
            case "Reporte de Disponibilidad de Productos":
                generarReporteDisponibilidad();
                break;
            case "Reporte de Productos más Vendidos":
                generarReporteProductosMasVendidos();
                break;
            case "Reporte de Ventas Totales por Empleado":
                generarReporteVentasPorEmpleado();
                break;
            case "Reporte de Categorias":
                generarReporteCategorias();
                break;
            case "Contenido de Categorias":
                generarReporteContenidoCategorias();
                break;
            case "Productos Agotados":
                generarReporteProductosAgotados();
                break;
            case "Compra Inventario":
                generarReporteCompraInventario();
                break;
            default:
                JOptionPane.showMessageDialog(vista,
                        "Tipo de reporte no implementado: " + reporteSeleccionado,
                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarReporteDisponibilidad() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte desde la base de datos...\n");
            tipoReporteActual = "Disponibilidad de Productos";

            datosReporteActual = obtenerDatosVistaDisponibilidad();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(183) + "\n" +
                                "                    REPORTE DE DISPONIBILIDAD DE PRODUCTOS\n" +
                                "═".repeat(183) + "\n\n" +
                                "    ⚠️ No se encontraron productos disponibles en el inventario.\n\n" +
                                "═".repeat(183) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de productos encontrados: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ==================== REPORTE DE PRODUCTOS MÁS VENDIDOS ====================

    private void generarReporteProductosMasVendidos() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de productos más vendidos...\n");
            tipoReporteActual = "Productos más Vendidos";

            datosReporteActual = obtenerDatosVistaProductosMasVendidos();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE PRODUCTOS MÁS VENDIDOS\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ⚠️ No se encontraron datos de productos vendidos.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de registros encontrados: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaProductosMasVendidos() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de productos más vendidos
            String query = "SELECT * FROM reporte_producto_mas_vendido";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 1. Capturar nombres de columnas dinámicamente
            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            // 2. Capturar datos
            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // ==================== REPORTE DE VENTAS TOTALES POR EMPLEADO
    // ====================

    private void generarReporteVentasPorEmpleado() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de ventas por empleado...\n");
            tipoReporteActual = "Ventas Totales por Empleado";

            datosReporteActual = obtenerDatosVistaVentasPorEmpleado();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE VENTAS TOTALES POR EMPLEADO\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ⚠️ No se encontraron datos de ventas por empleado.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de registros encontrados: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaVentasPorEmpleado() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de ventas totales por empleado
            String query = "SELECT * FROM ventas_totales_por_empleado";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 1. Capturar nombres de columnas dinámicamente
            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            // 2. Capturar datos
            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // ==================== REPORTE DE CATEGORIAS ====================

    private void generarReporteCategorias() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de categorías...\n");
            tipoReporteActual = "Categorias";

            datosReporteActual = obtenerDatosVistaCategorias();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE CATEGORIAS\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ⚠️ No se encontraron categorías.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de registros encontrados: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaCategorias() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de categorías
            String query = "SELECT * FROM categoria_consultar";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 1. Capturar nombres de columnas dinámicamente
            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            // 2. Capturar datos
            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // ==================== REPORTE DE CONTENIDO DE CATEGORÍAS ====================

    private void generarReporteContenidoCategorias() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de contenido de categorías...\n");
            tipoReporteActual = "Contenido de Categorias";

            datosReporteActual = obtenerDatosVistaContenidoCategorias();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE CONTENIDO DE CATEGORÍAS\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ⚠️ No se encontraron productos.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de registros encontrados: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaContenidoCategorias() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de productos por categoría
            String query = "SELECT * FROM producto_consulta";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // ==================== REPORTE DE PRODUCTOS AGOTADOS ====================

    private void generarReporteProductosAgotados() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de productos agotados...\n");
            tipoReporteActual = "Productos Agotados";

            datosReporteActual = obtenerDatosVistaProductosAgotados();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE PRODUCTOS AGOTADOS\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ✅ No hay productos con stock mínimo o agotados.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de productos en alerta: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaProductosAgotados() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de productos agotados / stock mínimo
            String query = "SELECT * FROM productos_stock_min";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // ==================== REPORTE DE COMPRA DE INVENTARIO ====================

    private void generarReporteCompraInventario() {
        try {
            vista.jTextArea_reporte_inventario.setText("⏳ Cargando reporte de compras...\n");
            tipoReporteActual = "Compra Inventario";

            datosReporteActual = obtenerDatosVistaCompraInventario();

            if (datosReporteActual.isEmpty()) {
                vista.jTextArea_reporte_inventario.setText(
                        "═".repeat(120) + "\n" +
                                "                    REPORTE DE COMPRA DE INVENTARIO\n" +
                                "═".repeat(120) + "\n\n" +
                                "    ⚠️ No se encontraron registros de compras.\n\n" +
                                "═".repeat(120) + "\n");
                return;
            }

            generarTextoReporte(datosReporteActual);

            JOptionPane.showMessageDialog(vista,
                    "✅ Reporte generado exitosamente.\n" +
                            "Total de registros de compra: " + datosReporteActual.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            vista.jTextArea_reporte_inventario.setText("❌ Error al generar el reporte:\n" + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String[]> obtenerDatosVistaCompraInventario() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta a la vista de reporte de compras
            String query = "SELECT * FROM reporte_de_compra";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    // Variable para almacenar los nombres de las columnas dinámicamente
    private String[] nombresColumnasGenerados;

    private List<String[]> obtenerDatosVistaDisponibilidad() {
        List<String[]> datos = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conexion.EstablecerConexion();
            st = con.createStatement();
            // Consulta para obtener todos los datos
            String query = "SELECT * FROM reporte_disponibilidad_inventario";
            rs = st.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 1. Capturar nombres de columnas dinámicamente
            nombresColumnasGenerados = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                // getColumnLabel es preferible a getColumnName para alias
                nombresColumnasGenerados[i - 1] = metaData.getColumnLabel(i);
            }

            // 2. Capturar datos
            while (rs.next()) {
                String[] fila = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    if (valor == null) {
                        fila[i - 1] = "";
                    } else if (valor instanceof java.sql.Date || valor instanceof java.sql.Timestamp) {
                        fila[i - 1] = new SimpleDateFormat("dd/MM/yyyy").format(valor);
                    } else if (valor instanceof Double || valor instanceof Float
                            || valor instanceof java.math.BigDecimal) {
                        try {
                            fila[i - 1] = df.format(valor);
                        } catch (Exception e) {
                            fila[i - 1] = valor.toString();
                        }
                    } else {
                        fila[i - 1] = valor.toString();
                    }
                }
                datos.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al consultar la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            // Inicializar array vacío para evitar NullPointerException
            nombresColumnasGenerados = new String[0];
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    conexion.CerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datos;
    }

    private void generarTextoReporte(List<String[]> datos) {
        StringBuilder sb = new StringBuilder();

        // Usar los nombres capturados de la BD
        String[] columnas = nombresColumnasGenerados;
        if (columnas == null)
            columnas = new String[0];

        int numColumnas = columnas.length;
        int[] anchosColumnas = new int[numColumnas];

        // --- PASO 1: Calcular anchos dinámicamente ---

        // Inicializar con longitud de encabezados
        for (int i = 0; i < numColumnas; i++) {
            anchosColumnas[i] = columnas[i].length();
        }

        // Revisar todos los datos para encontrar el máximo ancho por columna
        for (String[] fila : datos) {
            for (int i = 0; i < numColumnas && i < fila.length; i++) {
                if (fila[i] != null) {
                    int longitudDato = fila[i].length();
                    if (longitudDato > anchosColumnas[i]) {
                        anchosColumnas[i] = longitudDato;
                    }
                }
            }
        }

        // Añadir padding (espacio extra) a cada columna y calcular ancho total
        int anchoTotal = 0;
        for (int i = 0; i < numColumnas; i++) {
            anchosColumnas[i] += 2; // +2 espacios de margen
            anchoTotal += anchosColumnas[i];
        }

        // --- PASO 2: Construir el reporte ---

        // Encabezado del Reporte
        sb.append("═".repeat(anchoTotal)).append("\n");
        String titulo = "REPORTE DE DISPONIBILIDAD DE PRODUCTOS";
        int paddingTitulo = Math.max(0, (anchoTotal - titulo.length()) / 2);
        sb.append(" ".repeat(paddingTitulo)).append(titulo).append("\n");

        String empresa = NOMBRE_EMPRESA.replace("\n", " ");
        int paddingEmpresa = Math.max(0, (anchoTotal - empresa.length()) / 2);
        sb.append(" ".repeat(paddingEmpresa)).append(empresa).append("\n");

        sb.append("═".repeat(anchoTotal)).append("\n");
        sb.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");
        sb.append("Total registros: ").append(datos.size()).append("\n");
        sb.append("═".repeat(anchoTotal)).append("\n\n");

        // Fila de Encabezados de Columnas
        StringBuilder headerFormat = new StringBuilder();
        for (int i = 0; i < numColumnas; i++) {
            // Formato dinámico: %-Ns (alineado izquierda, ancho N)
            headerFormat.append(String.format("%-" + anchosColumnas[i] + "s", columnas[i]));
        }
        sb.append(headerFormat.toString()).append("\n");
        sb.append("-".repeat(anchoTotal)).append("\n");

        // Filas de Datos
        // Variables para totales calculados (precio × cantidad)
        double totalCompra = 0; // Precio de compra × Cantidad en Inv.
        double totalVenta = 0; // Precio de venta × Cantidad Vendida
        int totalCantidadVendida = 0;

        // Índices detectados para cálculo de totales
        int idxPrecioCompra = -1, idxCantidadInv = -1;
        int idxPrecioVenta = -1, idxCantidadVendida = -1;
        int idxEmpleado = -1;

        // Detectar columnas por nombre
        for (int i = 0; i < numColumnas; i++) {
            String colLower = columnas[i].toLowerCase();

            // Detectar Precio de Compra
            if (colLower.contains("precio") && colLower.contains("compra")) {
                idxPrecioCompra = i;
            } else if (colLower.equals("p. de compra") || colLower.contains("costo")) {
                idxPrecioCompra = i;
            }

            // Detectar Cantidad en Inventario
            if (colLower.contains("cantidad") && colLower.contains("inv")) {
                idxCantidadInv = i;
            } else if (colLower.contains("cantidad disponible") || colLower.contains("stock")) {
                idxCantidadInv = i;
            }

            // Detectar Precio de Venta
            if (colLower.contains("precio") && colLower.contains("venta")) {
                idxPrecioVenta = i;
            } else if (colLower.equals("p. de venta")) {
                idxPrecioVenta = i;
            }

            // Detectar Cantidad Vendida
            if (colLower.contains("cantidad") && colLower.contains("vendid")) {
                idxCantidadVendida = i;
            }

            // Detectar Empleado
            if (colLower.contains("empleado") || colLower.contains("vendedor") || colLower.contains("usuario")) {
                idxEmpleado = i;
            }
        }

        // Mapa para acumular ventas por empleado
        Map<String, Integer> ventasPorEmpleado = new HashMap<>();

        for (String[] fila : datos) {
            StringBuilder lineFormat = new StringBuilder();
            for (int i = 0; i < numColumnas; i++) {
                String valor = (i < fila.length && fila[i] != null) ? fila[i] : "";
                lineFormat.append(String.format("%-" + anchosColumnas[i] + "s", valor));
            }
            sb.append(lineFormat.toString()).append("\n");

            // Calcular totales: Precio × Cantidad
            try {
                // Total Compra = Precio de compra × Cantidad en Inventario
                if (idxPrecioCompra != -1 && idxCantidadInv != -1 &&
                        idxPrecioCompra < fila.length && idxCantidadInv < fila.length) {
                    double precioCompra = parseDouble(fila[idxPrecioCompra]);
                    double cantidadInv = parseDouble(fila[idxCantidadInv]);
                    totalCompra += precioCompra * cantidadInv;
                }

                // Total Venta = Precio de venta × Cantidad Vendida
                if (idxPrecioVenta != -1 && idxCantidadVendida != -1 &&
                        idxPrecioVenta < fila.length && idxCantidadVendida < fila.length) {
                    double precioVenta = parseDouble(fila[idxPrecioVenta]);
                    double cantidadVendida = parseDouble(fila[idxCantidadVendida]);
                    totalVenta += precioVenta * cantidadVendida;
                }

                // Total Cantidad Vendida (suma simple)
                if (idxCantidadVendida != -1 && idxCantidadVendida < fila.length) {
                    int cant = parseInt(fila[idxCantidadVendida]);
                    totalCantidadVendida += cant;

                    // Acumular por empleado
                    if (idxEmpleado != -1 && idxEmpleado < fila.length) {
                        String nombreEmpleado = fila[idxEmpleado];
                        if (nombreEmpleado != null && !nombreEmpleado.trim().isEmpty()) {
                            ventasPorEmpleado.put(nombreEmpleado,
                                    ventasPorEmpleado.getOrDefault(nombreEmpleado, 0) + cant);
                        }
                    }
                }
            } catch (Exception e) {
                /* Ignorar errores de parseo en sumas */
            }
        }

        // Totales Finales
        sb.append("-".repeat(anchoTotal)).append("\n");

        String tituloTotales = "TOTALES ESTIMADOS";
        if ("Disponibilidad de Productos".equals(tipoReporteActual)) {
            tituloTotales = "TOTAL DE VALOR";
        }
        sb.append(tituloTotales).append(":\n");

        if (idxPrecioCompra != -1 && idxCantidadInv != -1)
            sb.append("Total Compra: ").append(df.format(totalCompra)).append("   ");
        if (idxPrecioVenta != -1 && idxCantidadVendida != -1)
            sb.append("Total Venta: ").append(df.format(totalVenta)).append("   ");

        // Mostrar desglose por empleado o total general
        if (!ventasPorEmpleado.isEmpty()) {
            sb.append("\n");
            for (Map.Entry<String, Integer> entry : ventasPorEmpleado.entrySet()) {
                String etiqueta = entry.getKey();
                if ("Productos más Vendidos".equals(tipoReporteActual)) {
                    etiqueta = "Cantidad Total Vendida";
                }
                sb.append(etiqueta).append(": ").append(entry.getValue()).append("\n");
            }
        } else if (idxCantidadVendida != -1) {
            sb.append("Total Unid. Vendidas: ").append(totalCantidadVendida);
        }
        sb.append("\n");

        sb.append("═".repeat(anchoTotal)).append("\n");
        sb.append("\n* Reporte dinámico generado por AdminPyme *\n");

        vista.jTextArea_reporte_inventario.setText(sb.toString());
        vista.jTextArea_reporte_inventario.setCaretPosition(0);
    }

    // Método auxiliar simple para parsear doubles del formato local (con comas o
    // puntos)
    private double parseDouble(String texto) {
        try {
            if (texto == null || texto.isEmpty())
                return 0.0;
            // Reemplazar coma por punto para parseo estándar si falla NumberFormat
            return Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String texto) {
        try {
            if (texto == null || texto.isEmpty())
                return 0;
            // Eliminar decimales si es un flotante representado como string
            if (texto.contains("."))
                texto = texto.substring(0, texto.indexOf("."));
            if (texto.contains(","))
                texto = texto.substring(0, texto.indexOf(","));
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ==================== EXPORTACIÓN A PDF ====================

    public void exportarPDF() {
        if (datosReporteActual.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "⚠️ Primero debe generar un reporte antes de exportar.",
                    "Sin datos para exportar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte PDF");
            String nombreArchivo = "Reporte_" + tipoReporteActual.replace(" ", "_") + "_" +
                    new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".pdf";
            fileChooser.setSelectedFile(new File(nombreArchivo));

            int userSelection = fileChooser.showSaveDialog(vista);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                crearPDF(filePath);

                int opcion = JOptionPane.showConfirmDialog(vista,
                        "✅ Reporte PDF generado exitosamente:\n" + filePath +
                                "\n\n¿Desea abrir el archivo ahora?",
                        "Exportación Completada",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    abrirArchivo(filePath);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al exportar a PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void crearPDF(String filePath) throws Exception {
        String[] columnas = nombresColumnasGenerados;
        if (columnas == null || columnas.length == 0)
            columnas = new String[] { "Sin Columnas" };

        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(30, 30, 60, 40);

        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Fuentes (completamente calificadas para evitar conflictos)
        com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY);
        com.itextpdf.text.Font fontSubtitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, BaseColor.GRAY);
        com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.NORMAL);
        com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.BOLD, BaseColor.WHITE);
        com.itextpdf.text.Font fontNegrita = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 9, com.itextpdf.text.Font.BOLD);

        // Encabezado con logo
        PdfPTable tablaEncabezado = new PdfPTable(2);
        tablaEncabezado.setWidthPercentage(100);
        tablaEncabezado.setSpacingAfter(10f);
        tablaEncabezado.setWidths(new float[] { 30f, 70f });

        PdfPCell celdaLogo = new PdfPCell();
        celdaLogo.setBorder(PdfPCell.NO_BORDER);
        celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

        try {
            // Intentar cargar logo desde ruta relativa o src
            File logoFile = new File("src/imagen/AdminPyme.jpg");
            if (!logoFile.exists())
                logoFile = new File("imagen/AdminPyme.jpg");

            if (logoFile.exists()) {
                Image logo = Image.getInstance(logoFile.getAbsolutePath());
                logo.scaleToFit(200, 200); // Tamaño solicitado 200x200
                celdaLogo.addElement(logo);
            } else {
                celdaLogo.addElement(new Paragraph(" "));
            }
        } catch (Exception e) {
            celdaLogo.addElement(new Paragraph(" "));
        }

        PdfPCell celdaEmpresa = new PdfPCell();
        celdaEmpresa.setBorder(PdfPCell.NO_BORDER);
        celdaEmpresa.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph empresa = new Paragraph(NOMBRE_EMPRESA, fontTitulo);
        empresa.setAlignment(Element.ALIGN_CENTER);

        Paragraph titulo = new Paragraph("REPORTE DE " + tipoReporteActual.toUpperCase(), fontSubtitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingBefore(10);

        Paragraph fecha = new Paragraph("Generado: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                fontNormal);
        fecha.setAlignment(Element.ALIGN_CENTER);

        Paragraph totalProductos = new Paragraph("Total de registros: " + datosReporteActual.size(), fontNegrita);
        totalProductos.setAlignment(Element.ALIGN_CENTER);

        celdaEmpresa.addElement(empresa);
        celdaEmpresa.addElement(titulo);
        celdaEmpresa.addElement(fecha);
        celdaEmpresa.addElement(totalProductos);

        tablaEncabezado.addCell(celdaLogo);
        tablaEncabezado.addCell(celdaEmpresa);
        document.add(tablaEncabezado);

        document.add(new Paragraph("\n"));

        // Tabla de datos
        PdfPTable table = new PdfPTable(columnas.length);
        table.setWidthPercentage(100);

        // Anchos relativos personalizados (Pesos)
        float[] anchosRelativos = new float[columnas.length];
        for (int i = 0; i < columnas.length; i++) {
            String col = columnas[i].toLowerCase();
            if (col.contains("producto") || col.contains("descripcion")) {
                anchosRelativos[i] = 4.5f; // ~300px relativo (Muy ancho)
            } else if (col.contains("estado")) {
                anchosRelativos[i] = 1.5f; // ~100px relativo (Ancho)
            } else if (col.contains("cantidad") || col.contains("disponible") || col.contains("cant")) {
                anchosRelativos[i] = 0.8f; // ~60px relativo (Angosto)
            } else if (col.contains("ubicacion") || col.contains("pasillo")) {
                anchosRelativos[i] = 0.8f; // ~60px relativo (Angosto)
            } else if (col.contains("compra") || col.contains("costo")) {
                anchosRelativos[i] = 1.0f; // Reducido para dar espacio
            } else if (col.contains("venta") || col.contains("precio")) {
                anchosRelativos[i] = 1.0f; // Reducido para dar espacio
            } else if (col.contains("codigo")) {
                anchosRelativos[i] = 1.8f; // Estándar
            } else if (col.contains("fecha")) {
                anchosRelativos[i] = 1.2f; // Estándar
            } else {
                anchosRelativos[i] = 1.0f; // Default angosto
            }
        }
        table.setWidths(anchosRelativos);

        BaseColor colorHeader = new BaseColor(44, 62, 80);
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, fontHeader));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(6);
            table.addCell(header);
        }

        // Detección de columnas para cálculo de totales (Precio × Cantidad)
        int idxPrecioCompra = -1, idxCantidadInv = -1;
        int idxPrecioVenta = -1, idxCantidadVendida = -1;
        int idxEmpleado = -1;

        for (int i = 0; i < columnas.length; i++) {
            String colLower = columnas[i].toLowerCase();

            // Detectar Precio de Compra
            if (colLower.contains("precio") && colLower.contains("compra")) {
                idxPrecioCompra = i;
            } else if (colLower.equals("p. de compra") || colLower.contains("costo")) {
                idxPrecioCompra = i;
            }

            // Detectar Cantidad en Inventario
            if (colLower.contains("cantidad") && colLower.contains("inv")) {
                idxCantidadInv = i;
            } else if (colLower.contains("cantidad disponible") || colLower.contains("stock")) {
                idxCantidadInv = i;
            }

            // Detectar Precio de Venta
            if (colLower.contains("precio") && colLower.contains("venta")) {
                idxPrecioVenta = i;
            } else if (colLower.equals("p. de venta")) {
                idxPrecioVenta = i;
            }

            // Detectar Cantidad Vendida
            if (colLower.contains("cantidad") && colLower.contains("vendid")) {
                idxCantidadVendida = i;
            }

            // Detectar Empleado
            if (colLower.contains("empleado") || colLower.contains("vendedor") || colLower.contains("usuario")) {
                idxEmpleado = i;
            }
        }

        double totalCompra = 0;
        double totalVenta = 0;
        int totalCantidadVendida = 0;
        Map<String, Integer> ventasPorEmpleado = new HashMap<>(); // Usar Map
        boolean filaPar = false;
        BaseColor colorFilaPar = new BaseColor(240, 240, 240);

        for (String[] fila : datosReporteActual) {
            BaseColor colorFondo = filaPar ? colorFilaPar : BaseColor.WHITE;

            for (int i = 0; i < columnas.length; i++) {
                String valor = (i < fila.length && fila[i] != null) ? fila[i] : "";
                PdfPCell cell = new PdfPCell(new Phrase(valor, fontNormal));
                cell.setBackgroundColor(colorFondo);
                cell.setPadding(4);

                // Alinear a la derecha si es columna numérica
                if (i == idxPrecioCompra || i == idxPrecioVenta || i == idxCantidadInv || i == idxCantidadVendida) {
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                } else {
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                }
                table.addCell(cell);
            }

            try {
                // Total Compra = Precio de compra × Cantidad en Inventario
                if (idxPrecioCompra != -1 && idxCantidadInv != -1 &&
                        idxPrecioCompra < fila.length && idxCantidadInv < fila.length) {
                    totalCompra += parseDouble(fila[idxPrecioCompra]) * parseDouble(fila[idxCantidadInv]);
                }

                // Total Venta = Precio de venta × Cantidad Vendida
                if (idxPrecioVenta != -1 && idxCantidadVendida != -1 &&
                        idxPrecioVenta < fila.length && idxCantidadVendida < fila.length) {
                    totalVenta += parseDouble(fila[idxPrecioVenta]) * parseDouble(fila[idxCantidadVendida]);
                }

                // Total Cantidad Vendida
                if (idxCantidadVendida != -1 && idxCantidadVendida < fila.length) {
                    int cant = parseInt(fila[idxCantidadVendida]);
                    totalCantidadVendida += cant;

                    // Acumular por empleado
                    if (idxEmpleado != -1 && idxEmpleado < fila.length) {
                        String nombreEmpleado = fila[idxEmpleado];
                        if (nombreEmpleado != null && !nombreEmpleado.trim().isEmpty()) {
                            ventasPorEmpleado.put(nombreEmpleado,
                                    ventasPorEmpleado.getOrDefault(nombreEmpleado, 0) + cant);
                        }
                    }
                }
            } catch (Exception e) {
            }

            filaPar = !filaPar;
        }

        document.add(table);

        // Totales (Solo si se detectaron columnas numéricas)
        if ((idxPrecioCompra != -1 && idxCantidadInv != -1) || (idxPrecioVenta != -1 && idxCantidadVendida != -1)) {
            PdfPTable tablaTotales = new PdfPTable(2);
            tablaTotales.setWidthPercentage(50);
            tablaTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaTotales.setSpacingBefore(15f);

            String tituloTotales = "TOTALES ESTIMADOS";
            if ("Disponibilidad de Productos".equals(tipoReporteActual)) {
                tituloTotales = "TOTAL DE VALOR";
            }
            PdfPCell cellHeaderTot = new PdfPCell(new Phrase(tituloTotales, fontNegrita));
            cellHeaderTot.setColspan(2);
            cellHeaderTot.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaderTot.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tablaTotales.addCell(cellHeaderTot);

            if (idxPrecioCompra != -1 && idxCantidadInv != -1) {
                tablaTotales.addCell(new Phrase("Total Compra:", fontNormal));
                tablaTotales.addCell(new Phrase(df.format(totalCompra), fontNegrita));
            }
            if (idxPrecioVenta != -1 && idxCantidadVendida != -1) {
                tablaTotales.addCell(new Phrase("Total Venta:", fontNormal));
                tablaTotales.addCell(new Phrase(df.format(totalVenta), fontNegrita));
            }

            // Desglose por empleado
            if (!ventasPorEmpleado.isEmpty()) {
                for (Map.Entry<String, Integer> entry : ventasPorEmpleado.entrySet()) {
                    String etiqueta = entry.getKey();
                    if ("Productos más Vendidos".equals(tipoReporteActual)) {
                        etiqueta = "Cantidad Total Vendida";
                    }
                    tablaTotales.addCell(new Phrase(etiqueta + ":", fontNormal));
                    tablaTotales.addCell(new Phrase(String.valueOf(entry.getValue()), fontNegrita));
                }
            } else if (idxCantidadVendida != -1) {
                tablaTotales.addCell(new Phrase("Unidades Vendidas:", fontNormal));
                tablaTotales.addCell(new Phrase(String.valueOf(totalCantidadVendida), fontNegrita));
            }

            document.add(tablaTotales);
        }

        Paragraph piePagina = new Paragraph(
                "\n\n* Reporte generado automáticamente por AdminPyme *",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8,
                        com.itextpdf.text.Font.ITALIC, BaseColor.GRAY));
        piePagina.setAlignment(Element.ALIGN_CENTER);
        document.add(piePagina);

        document.close();
    }

    // ==================== EXPORTACIÓN A EXCEL (.xlsx) ====================

    public void exportarExcel() {
        if (datosReporteActual.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "⚠️ Primero debe generar un reporte antes de exportar.",
                    "Sin datos para exportar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte Excel");
            String nombreArchivo = "Reporte_" + tipoReporteActual.replace(" ", "_") + "_" +
                    new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".xlsx";
            fileChooser.setSelectedFile(new File(nombreArchivo));

            int userSelection = fileChooser.showSaveDialog(vista);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                crearExcelXLSX(filePath);

                int opcion = JOptionPane.showConfirmDialog(vista,
                        "✅ Reporte Excel generado exitosamente:\n" + filePath +
                                "\n\n¿Desea abrir el archivo ahora?",
                        "Exportación Completada",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    abrirArchivo(filePath);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al exportar a Excel: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void crearExcelXLSX(String filePath) throws Exception {
        String[] columnas = nombresColumnasGenerados;
        if (columnas == null || columnas.length == 0)
            columnas = new String[] { "Sin Columnas" };

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reporte de Inventario");

        int rowNum = 0;

        // Estilos
        XSSFCellStyle estiloTitulo = workbook.createCellStyle();
        XSSFFont fuenteTitulo = workbook.createFont();
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short) 16);
        fuenteTitulo.setColor(IndexedColors.DARK_BLUE.getIndex());
        estiloTitulo.setFont(fuenteTitulo);
        estiloTitulo.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle estiloSubtitulo = workbook.createCellStyle();
        XSSFFont fuenteSubtitulo = workbook.createFont();
        fuenteSubtitulo.setBold(true);
        fuenteSubtitulo.setFontHeightInPoints((short) 12);
        estiloSubtitulo.setFont(fuenteSubtitulo);
        estiloSubtitulo.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle estiloHeader = workbook.createCellStyle();
        XSSFFont fuenteHeader = workbook.createFont();
        fuenteHeader.setBold(true);
        fuenteHeader.setColor(IndexedColors.WHITE.getIndex());
        estiloHeader.setFont(fuenteHeader);
        estiloHeader.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloHeader.setAlignment(HorizontalAlignment.CENTER);
        estiloHeader.setBorderBottom(BorderStyle.THIN);
        estiloHeader.setBorderTop(BorderStyle.THIN);
        estiloHeader.setBorderLeft(BorderStyle.THIN);
        estiloHeader.setBorderRight(BorderStyle.THIN);

        XSSFCellStyle estiloDatos = workbook.createCellStyle();
        estiloDatos.setBorderBottom(BorderStyle.THIN);
        estiloDatos.setBorderTop(BorderStyle.THIN);
        estiloDatos.setBorderLeft(BorderStyle.THIN);
        estiloDatos.setBorderRight(BorderStyle.THIN);

        XSSFCellStyle estiloNumerico = workbook.createCellStyle();
        estiloNumerico.setBorderBottom(BorderStyle.THIN);
        estiloNumerico.setBorderTop(BorderStyle.THIN);
        estiloNumerico.setBorderLeft(BorderStyle.THIN);
        estiloNumerico.setBorderRight(BorderStyle.THIN);
        estiloNumerico.setAlignment(HorizontalAlignment.RIGHT);

        XSSFCellStyle estiloDatosAlterno = workbook.createCellStyle();
        estiloDatosAlterno.cloneStyleFrom(estiloDatos);
        estiloDatosAlterno.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloDatosAlterno.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFCellStyle estiloTotales = workbook.createCellStyle();
        XSSFFont fuenteTotales = workbook.createFont();
        fuenteTotales.setBold(true);
        estiloTotales.setFont(fuenteTotales);
        estiloTotales.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        estiloTotales.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloTotales.setBorderBottom(BorderStyle.THIN);
        estiloTotales.setBorderTop(BorderStyle.THIN);
        estiloTotales.setBorderLeft(BorderStyle.THIN);
        estiloTotales.setBorderRight(BorderStyle.THIN);

        // Encabezado
        XSSFRow rowTitulo = sheet.createRow(rowNum++);
        XSSFCell cellTitulo = rowTitulo.createCell(0);
        cellTitulo.setCellValue(NOMBRE_EMPRESA.replace("\n", " "));
        cellTitulo.setCellStyle(estiloTitulo);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnas.length - 1));

        XSSFRow rowSubtitulo = sheet.createRow(rowNum++);
        XSSFCell cellSubtitulo = rowSubtitulo.createCell(0);
        cellSubtitulo.setCellValue("REPORTE DE " + tipoReporteActual.toUpperCase());
        cellSubtitulo.setCellStyle(estiloSubtitulo);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnas.length - 1));

        XSSFRow rowFecha = sheet.createRow(rowNum++);
        XSSFCell cellFecha = rowFecha.createCell(0);
        cellFecha.setCellValue("Generado: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        XSSFRow rowTotal = sheet.createRow(rowNum++);
        XSSFCell cellTotal = rowTotal.createCell(0);
        cellTotal.setCellValue("Total de registros: " + datosReporteActual.size());

        rowNum++;

        // Encabezados de columnas
        XSSFRow headerRow = sheet.createRow(rowNum++);
        for (int i = 0; i < columnas.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(estiloHeader);
        }

        // Detección de columnas para cálculo de totales (Precio × Cantidad)
        int idxPrecioCompra = -1, idxCantidadInv = -1;
        int idxPrecioVenta = -1, idxCantidadVendida = -1;
        int idxEmpleado = -1;

        for (int i = 0; i < columnas.length; i++) {
            String colLower = columnas[i].toLowerCase();

            // Detectar Precio de Compra
            if (colLower.contains("precio") && colLower.contains("compra")) {
                idxPrecioCompra = i;
            } else if (colLower.equals("p. de compra") || colLower.contains("costo")) {
                idxPrecioCompra = i;
            }

            // Detectar Cantidad en Inventario
            if (colLower.contains("cantidad") && colLower.contains("inv")) {
                idxCantidadInv = i;
            } else if (colLower.contains("cantidad disponible") || colLower.contains("stock")) {
                idxCantidadInv = i;
            }

            // Detectar Precio de Venta
            if (colLower.contains("precio") && colLower.contains("venta")) {
                idxPrecioVenta = i;
            } else if (colLower.equals("p. de venta")) {
                idxPrecioVenta = i;
            }

            // Detectar Cantidad Vendida
            if (colLower.contains("cantidad") && colLower.contains("vendid")) {
                idxCantidadVendida = i;
            }

            // Detectar Empleado
            if (colLower.contains("empleado") || colLower.contains("vendedor") || colLower.contains("usuario")) {
                idxEmpleado = i;
            }
        }

        // Datos
        double totalCompra = 0;
        double totalVenta = 0;
        int totalCantidadVendida = 0;
        Map<String, Integer> ventasPorEmpleado = new HashMap<>(); // Usar Map
        boolean filaPar = false;

        for (String[] fila : datosReporteActual) {
            XSSFRow dataRow = sheet.createRow(rowNum++);

            for (int i = 0; i < columnas.length; i++) {
                XSSFCell cell = dataRow.createCell(i);
                String valor = (i < fila.length && fila[i] != null) ? fila[i] : "";

                // Intentar poner valor numérico si es columna numérica detectada
                boolean esNumerico = false;
                if (i == idxPrecioCompra || i == idxPrecioVenta || i == idxCantidadInv || i == idxCantidadVendida) {
                    try {
                        double num = parseDouble(valor);
                        cell.setCellValue(num);
                        cell.setCellStyle(estiloNumerico);
                        esNumerico = true;
                    } catch (Exception e) {
                    }
                }

                if (!esNumerico) {
                    cell.setCellValue(valor);
                    cell.setCellStyle(filaPar ? estiloDatosAlterno : estiloDatos);
                }
            }

            try {
                // Total Compra = Precio de compra × Cantidad en Inventario
                if (idxPrecioCompra != -1 && idxCantidadInv != -1 &&
                        idxPrecioCompra < fila.length && idxCantidadInv < fila.length) {
                    totalCompra += parseDouble(fila[idxPrecioCompra]) * parseDouble(fila[idxCantidadInv]);
                }

                // Total Venta = Precio de venta × Cantidad Vendida
                if (idxPrecioVenta != -1 && idxCantidadVendida != -1 &&
                        idxPrecioVenta < fila.length && idxCantidadVendida < fila.length) {
                    totalVenta += parseDouble(fila[idxPrecioVenta]) * parseDouble(fila[idxCantidadVendida]);
                }

                // Total Cantidad Vendida
                if (idxCantidadVendida != -1 && idxCantidadVendida < fila.length) {
                    int cant = parseInt(fila[idxCantidadVendida]);
                    totalCantidadVendida += cant;

                    // Acumular por empleado
                    if (idxEmpleado != -1 && idxEmpleado < fila.length) {
                        String nombreEmpleado = fila[idxEmpleado];
                        if (nombreEmpleado != null && !nombreEmpleado.trim().isEmpty()) {
                            ventasPorEmpleado.put(nombreEmpleado,
                                    ventasPorEmpleado.getOrDefault(nombreEmpleado, 0) + cant);
                        }
                    }
                }
            } catch (Exception e) {
            }

            filaPar = !filaPar;
        }

        rowNum++;

        // Totales (Solo si hay columnas numéricas detectadas)
        if ((idxPrecioCompra != -1 && idxCantidadInv != -1) || (idxPrecioVenta != -1 && idxCantidadVendida != -1)) {
            XSSFRow rowTotales = sheet.createRow(rowNum++);

            XSSFCell cellLabelTot = rowTotales.createCell(0);
            cellLabelTot.setCellValue("TOTALES:");
            cellLabelTot.setCellStyle(estiloTotales);

            int colOffset = 1;
            if (idxPrecioCompra != -1 && idxCantidadInv != -1) {
                XSSFCell cellLbl = rowTotales.createCell(colOffset++);
                cellLbl.setCellValue("Total Compra:");
                cellLbl.setCellStyle(estiloTotales);
                XSSFCell cellVal = rowTotales.createCell(colOffset++);
                cellVal.setCellValue(totalCompra);
                cellVal.setCellStyle(estiloTotales);
            }
            if (idxPrecioVenta != -1 && idxCantidadVendida != -1) {
                XSSFCell cellLbl = rowTotales.createCell(colOffset++);
                cellLbl.setCellValue("Total Venta:");
                cellLbl.setCellStyle(estiloTotales);
                XSSFCell cellVal = rowTotales.createCell(colOffset++);
                cellVal.setCellValue(totalVenta);
                cellVal.setCellStyle(estiloTotales);
            }

            // Desglose por empleado (uno debajo de otro)
            if (!ventasPorEmpleado.isEmpty()) {
                for (Map.Entry<String, Integer> entry : ventasPorEmpleado.entrySet()) {
                    XSSFRow rowEmp = sheet.createRow(rowNum++);
                    int empOffset = 1;

                    XSSFCell cellLbl = rowEmp.createCell(empOffset++);
                    cellLbl.setCellValue(entry.getKey() + ":");
                    cellLbl.setCellStyle(estiloTotales);

                    XSSFCell cellVal = rowEmp.createCell(empOffset++);
                    cellVal.setCellValue(entry.getValue());
                    cellVal.setCellStyle(estiloTotales);
                }
            } else if (idxCantidadVendida != -1) {
                XSSFCell cellLbl = rowTotales.createCell(colOffset++);
                cellLbl.setCellValue("Unid. Vendidas:");
                cellLbl.setCellStyle(estiloTotales);
                XSSFCell cellVal = rowTotales.createCell(colOffset++);
                cellVal.setCellValue(totalCantidadVendida);
                cellVal.setCellStyle(estiloTotales);
            }
        }

        // Ajustar ancho de columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private void abrirArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(vista,
                        "El archivo no existe: " + rutaArchivo,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivo);
            } else {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + rutaArchivo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo abrir el archivo.\nPuede abrirlo manualmente en:\n" + rutaArchivo,
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
