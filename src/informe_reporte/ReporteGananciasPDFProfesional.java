package informe_reporte;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import consultas_bd.sql_consulta;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.Font;
import com.toedter.calendar.JDateChooser;
import java.net.URL;
import java.awt.Desktop;

public class ReporteGananciasPDFProfesional extends sql_consulta {
    private static final String NOMBRE_EMPRESA = "Inversiones\nLa Frontera del Sabor\nCG";
    
    // Variable para almacenar la ruta de la imagen
    private String rutaLogoEmpresa = "imagen/AdminPyme.jpg";
    
    private com.itextpdf.text.Image obtenerLogoEmpresa() {
        try {
            // Primero intentar con la ruta específica del logo
            if (rutaLogoEmpresa != null && new File(rutaLogoEmpresa).exists()) {
                com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(rutaLogoEmpresa);
                logo.scaleToFit(80, 80); // Tamaño adecuado para el logo
                return logo;
            }
            
            // Si no se encuentra, buscar en rutas alternativas
            String[] rutasComunes = {
                "imagen/AdminPyme.jpg",
                "src/imagen/AdminPyme.jpg",
                "resources/imagen/AdminPyme.jpg",
                "recursos/imagen/AdminPyme.jpg",
                System.getProperty("user.dir") + "/imagen/AdminPyme.jpg",
                "AdminPyme.jpg"
            };
            
            for (String ruta : rutasComunes) {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(archivo.getAbsolutePath());
                    logo.scaleToFit(80, 80);
                    return logo;
                }
            }
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo de la empresa: " + e.getMessage());
        }
        return null;
    }
    
    public void generarReporteGanancias() {
        String filePath = null; // Variable para almacenar la ruta del archivo
        
        try {
            // 1. Diálogo para seleccionar rango de fechas
            Object[] fechas = mostrarDialogoFechas();
            if (fechas == null) return;
            
            String fechaInicio = (String) fechas[0];
            String fechaFin = (String) fechas[1];
            
            // 2. Obtener datos de las vistas de ganancias
            List<String[]> resumenGanancias = obtenerResumenGanancias(fechaInicio, fechaFin);
            List<String[]> gananciasMensuales = obtenerGananciasMensuales(fechaInicio, fechaFin);
            List<String[]> gananciasPorCategoria = obtenerGananciasPorCategoria(fechaInicio, fechaFin);
            List<String[]> ventasTopProductos = obtenerVentasTopProductos(fechaInicio, fechaFin);
            List<String[]> ventasRecientes = obtenerVentasRecientes(fechaInicio, fechaFin);
            
            if (resumenGanancias.isEmpty() && gananciasMensuales.isEmpty() && 
                gananciasPorCategoria.isEmpty() && ventasTopProductos.isEmpty() && 
                ventasRecientes.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron datos de ganancias en el rango de fechas especificado");
                return;
            }
            
            // 3. Diálogo para guardar PDF
            filePath = mostrarDialogoGuardarPDF(fechaInicio, fechaFin);
            if (filePath == null) return;
            
            // 4. Crear el PDF profesional
            String tituloReporte = "Reporte de Ganancias " + fechaInicio + " al " + fechaFin;
            crearPDFGananciasProfesional(filePath, tituloReporte, resumenGanancias, 
                                      gananciasMensuales, gananciasPorCategoria, 
                                      ventasTopProductos, ventasRecientes, fechaInicio, fechaFin);
            
            // 5. Preguntar si desea abrir el reporte
            abrirReporteGenerado(filePath, resumenGanancias.size());
            
        } catch (Exception e) {
            manejarErrorGeneracion(e);
        }
    }

    // Métodos auxiliares para la estructura modular

    /**
     * 1. Diálogo para seleccionar rango de fechas
     */
    private Object[] mostrarDialogoFechas() {
        JPanel fechaPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fechaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JDateChooser dateChooserInicio = new JDateChooser();
        JDateChooser dateChooserFin = new JDateChooser();
        
        dateChooserInicio.setDate(new Date());
        dateChooserFin.setDate(new Date());
        dateChooserInicio.setDateFormatString("yyyy-MM-dd");
        dateChooserFin.setDateFormatString("yyyy-MM-dd");
        
        fechaPanel.add(new JLabel("Fecha Inicio:"));
        fechaPanel.add(dateChooserInicio);
        fechaPanel.add(new JLabel("Fecha Fin:"));
        fechaPanel.add(dateChooserFin);
        
        int result = JOptionPane.showConfirmDialog(null, fechaPanel, 
                "Seleccionar Rango de Fechas", JOptionPane.OK_CANCEL_OPTION);
        
        if (result != JOptionPane.OK_OPTION) {
            return null;
        }
        
        if (dateChooserInicio.getDate() == null || dateChooserFin.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas");
            return null;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = sdf.format(dateChooserInicio.getDate());
        String fechaFin = sdf.format(dateChooserFin.getDate());
        
        return new Object[]{fechaInicio, fechaFin};
    }

private List<String[]> obtenerResumenGanancias(String fechaInicio, String fechaFin) {
    List<String[]> datos = new ArrayList<>();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
    }
    
    // Consulta directa sin vistas
    query = "SELECT \n" +
            "    'Ventas Contado' AS 'Tipo Ganancia',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'Cantidad Transacciones',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',\n" +
            "    AVG(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE NULL END) AS 'Promedio por Transacción'\n" +
            "FROM detalle_venta dv\n" +
            "INNER JOIN venta v ON dv.id_venta = v.id_venta\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT \n" +
            "    'Ventas Crédito' AS 'Tipo Ganancia',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'Cantidad Transacciones',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',\n" +
            "    AVG(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE NULL END) AS 'Promedio por Transacción'\n" +
            "FROM detalle_venta dv\n" +
            "INNER JOIN venta v ON dv.id_venta = v.id_venta\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT \n" +
            "    'Ganancia Neta' AS 'Tipo Ganancia',\n" +
            "    COUNT(DISTINCT dv.id_detalle_venta) AS 'Cantidad Transacciones',\n" +
            "    SUM((dv.monto_venta - (COALESCE(p.precio_compra, 0) * dv.cantidad_vendida))) AS 'Monto Total',\n" +
            "    AVG(dv.monto_venta - (COALESCE(p.precio_compra, 0) * dv.cantidad_vendida)) AS 'Promedio por Transacción'\n" +
            "FROM detalle_venta dv\n" +
            "INNER JOIN producto p ON dv.producto_id = p.producto_id\n" +
            "INNER JOIN venta v ON dv.id_venta = v.id_venta\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "AND p.precio_compra IS NOT NULL";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        
        // Establecer parámetros de fecha (se repiten 3 veces)
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        ps.setString(3, fechaInicio);
        ps.setString(4, fechaFin);
        ps.setString(5, fechaInicio);
        ps.setString(6, fechaFin);
        
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String tipoGanancia = rs.getString("Tipo Ganancia");
            String cantidadTransacciones = String.valueOf(rs.getInt("Cantidad Transacciones"));
            String montoTotal = String.format("%,.2f", rs.getDouble("Monto Total"));
            String promedio = String.format("%,.2f", rs.getDouble("Promedio por Transacción"));
            
            String[] fila = {tipoGanancia, cantidadTransacciones, montoTotal, promedio};
            datos.add(fila);
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener resumen de ganancias: " + e.getMessage(), e);
    }
    
    return datos;
}

private List<String[]> obtenerGananciasMensuales(String fechaInicio, String fechaFin) {
    List<String[]> datos = new ArrayList<>();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
    }
    
    query = "SELECT \n" +
            "    YEAR(v.fecha) AS 'Año',\n" +
            "    MONTH(v.fecha) AS 'Mes',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'Ventas Contado',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Contado',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'Ventas Crédito',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Crédito',\n" +
            "    SUM(CASE WHEN dv.estado_venta = 'Aprobado' THEN (dv.monto_venta - COALESCE(p.precio_compra, 0) * dv.cantidad_vendida) ELSE 0 END) AS 'Ganancia Neta',\n" +
            "    SUM(CASE WHEN dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'Total Ganancias'\n" +
            "FROM venta v\n" +
            "INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta\n" +
            "INNER JOIN producto p ON dv.producto_id = p.producto_id\n" +
            "WHERE v.fecha BETWEEN ? AND ?\n" +
            "GROUP BY YEAR(v.fecha), MONTH(v.fecha)\n" +
            "ORDER BY YEAR(v.fecha) DESC, MONTH(v.fecha) DESC";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String año = String.valueOf(rs.getInt("Año"));
            String mes = String.valueOf(rs.getInt("Mes"));
            String ventasContado = String.valueOf(rs.getInt("Ventas Contado"));
            String montoContado = String.format("%,.2f", rs.getDouble("Monto Contado"));
            String ventasCredito = String.valueOf(rs.getInt("Ventas Crédito"));
            String montoCredito = String.format("%,.2f", rs.getDouble("Monto Crédito"));
            String gananciaNeta = String.format("%,.2f", rs.getDouble("Ganancia Neta"));
            String totalGanancias = String.format("%,.2f", rs.getDouble("Total Ganancias"));
            
            String[] fila = {año, mes, ventasContado, montoContado, ventasCredito, 
                           montoCredito, gananciaNeta, totalGanancias};
            datos.add(fila);
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener ganancias mensuales: " + e.getMessage(), e);
    }
    
    return datos;
}

private List<String[]> obtenerGananciasPorCategoria(String fechaInicio, String fechaFin) {
    List<String[]> datos = new ArrayList<>();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
    }
    
    query = "SELECT \n" +
            "    c.tipo_categoria AS 'Categoria',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'VentasContado',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Contado' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'MontoContado',\n" +
            "    COUNT(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN 1 END) AS 'VentasCredito',\n" +
            "    SUM(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Aprobado' THEN dv.monto_venta ELSE 0 END) AS 'MontoCredito',\n" +
            "    SUM(CASE WHEN dv.estado_venta = 'Aprobado' THEN (dv.monto_venta - COALESCE(p.precio_compra, 0) * dv.cantidad_vendida) ELSE 0 END) AS 'GananciaNeta',\n" +
            "    CASE \n" +
            "        WHEN SUM(dv.monto_venta) > 0 THEN \n" +
            "            (SUM(CASE WHEN dv.estado_venta = 'Aprobado' THEN (dv.monto_venta - COALESCE(p.precio_compra, 0) * dv.cantidad_vendida) ELSE 0 END) / SUM(dv.monto_venta)) * 100\n" +
            "        ELSE 0 \n" +
            "    END AS 'MargenGanancia'\n" +
            "FROM categoria c\n" +
            "INNER JOIN producto p ON c.id_categoria = p.id_categoria\n" +
            "INNER JOIN detalle_venta dv ON p.producto_id = dv.producto_id\n" +
            "INNER JOIN venta v ON dv.id_venta = v.id_venta\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "GROUP BY c.id_categoria, c.tipo_categoria\n" +
            "ORDER BY SUM(dv.monto_venta) DESC";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String categoria = rs.getString("Categoria");
            String ventasContado = String.valueOf(rs.getInt("VentasContado"));
            String montoContado = String.format("%,.2f", rs.getDouble("MontoContado"));
            String ventasCredito = String.valueOf(rs.getInt("VentasCredito"));
            String montoCredito = String.format("%,.2f", rs.getDouble("MontoCredito"));
            String gananciaNeta = String.format("%,.2f", rs.getDouble("GananciaNeta"));
            String margenGanancia = String.format("%,.2f", rs.getDouble("MargenGanancia"));
            
            String[] fila = {categoria, ventasContado, montoContado, ventasCredito, 
                           montoCredito, gananciaNeta, margenGanancia};
            datos.add(fila);
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener ganancias por categoría: " + e.getMessage(), e);
    }
    
    return datos;
}

private List<String[]> obtenerVentasTopProductos(String fechaInicio, String fechaFin) {
    List<String[]> datos = new ArrayList<>();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
    }
    
    query = "SELECT \n" +
            "    p.producto_id AS 'ID Producto',\n" +
            "    p.nombre_producto AS 'Producto',\n" +
            "    c.tipo_categoria AS 'Categoría',\n" +
            "    SUM(dv.cantidad_vendida) AS 'Cantidad Vendida',\n" +
            "    p.precio_venta AS 'Precio Venta',\n" +
            "    SUM(dv.monto_venta) AS 'Total Vendido',\n" +
            "    SUM((dv.monto_venta - COALESCE(p.precio_compra, 0) * dv.cantidad_vendida)) AS 'Ganancia Producto',\n" +
            "    CASE \n" +
            "        WHEN SUM(dv.monto_venta) > 0 THEN \n" +
            "            (SUM((dv.monto_venta - COALESCE(p.precio_compra, 0) * dv.cantidad_vendida)) / SUM(dv.monto_venta)) * 100\n" +
            "        ELSE 0 \n" +
            "    END AS 'Margen Ganancia'\n" +
            "FROM producto p\n" +
            "INNER JOIN categoria c ON p.id_categoria = c.id_categoria\n" +
            "INNER JOIN detalle_venta dv ON p.producto_id = dv.producto_id\n" +
            "INNER JOIN venta v ON dv.id_venta = v.id_venta\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "GROUP BY p.producto_id, p.nombre_producto, c.tipo_categoria, p.precio_venta\n" +
            "ORDER BY SUM(dv.monto_venta) DESC\n" +
            "LIMIT 15";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String idProducto = String.valueOf(rs.getInt("ID Producto"));
            String producto = rs.getString("Producto");
            String categoria = rs.getString("Categoría");
            String cantidadVendida = String.valueOf(rs.getInt("Cantidad Vendida"));
            String precioVenta = String.format("%,.2f", rs.getDouble("Precio Venta"));
            String totalVendido = String.format("%,.2f", rs.getDouble("Total Vendido"));
            String gananciaProducto = String.format("%,.2f", rs.getDouble("Ganancia Producto"));
            String margen = String.format("%,.2f", rs.getDouble("Margen Ganancia"));
            
            String[] fila = {idProducto, producto, categoria, cantidadVendida, precioVenta, 
                           totalVendido, gananciaProducto, margen};
            datos.add(fila);
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener ventas top productos: " + e.getMessage(), e);
    }
    
    return datos;
}

private List<String[]> obtenerVentasRecientes(String fechaInicio, String fechaFin) {
    List<String[]> datos = new ArrayList<>();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
    }
    
    query = "SELECT \n" +
            "    v.id_venta AS 'ID Venta',\n" +
            "    p.nombre_producto AS 'Producto',\n" +
            "    c.tipo_categoria AS 'Categoria',\n" +
            "    CONCAT(per.nombre, ' ', per.apellido) AS 'Cliente',\n" +
            "    dv.cantidad_vendida AS 'Cantidad Vendida',\n" +
            "    dv.monto_venta AS 'Total Venta',\n" +
            "    v.fecha AS 'Fecha Venta',\n" +
            "    u.nombre_usuario AS 'Vendedor',\n" +
            "    dv.tipo_venta AS 'Tipo Venta'\n" +
            "FROM venta v\n" +
            "INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta\n" +
            "INNER JOIN producto p ON dv.producto_id = p.producto_id\n" +
            "INNER JOIN categoria c ON p.id_categoria = c.id_categoria\n" +
            "INNER JOIN cliente cl ON v.id_cliente = cl.id_cliente\n" +
            "INNER JOIN persona per ON cl.id_persona = per.id_persona\n" +
            "INNER JOIN usuario u ON v.id_usuario = u.id_usuario\n" +
            "WHERE dv.estado_venta = 'Aprobado'\n" +
            "AND v.fecha BETWEEN ? AND ?\n" +
            "ORDER BY v.fecha DESC, v.id_venta DESC\n" +
            "LIMIT 20";
    
    try {
        ps = con.EstablecerConexion().prepareStatement(query);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String idVenta = String.valueOf(rs.getInt("ID Venta"));
            String producto = rs.getString("Producto");
            String categoria = rs.getString("Categoria");
            String cliente = rs.getString("Cliente");
            String cantidadVendida = String.valueOf(rs.getInt("Cantidad Vendida"));
            String totalVenta = String.format("%,.2f", rs.getDouble("Total Venta"));
            String fechaVenta = rs.getDate("Fecha Venta") != null ? 
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getDate("Fecha Venta")) : "N/A";
            String vendedor = rs.getString("Vendedor");
            String tipoVenta = rs.getString("Tipo Venta");
            
            String[] fila = {idVenta, producto, categoria, cliente, cantidadVendida, 
                           totalVenta, fechaVenta, vendedor, tipoVenta};
            datos.add(fila);
        }
        
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener ventas recientes: " + e.getMessage(), e);
    }
    
    return datos;
}
    /**
     * 3. Diálogo para guardar PDF
     */
    private String mostrarDialogoGuardarPDF(String fechaInicio, String fechaFin) {
        String tituloReporte = "Reporte de Ganancias " + fechaInicio + " al " + fechaFin;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte PDF de Ganancias");
        fileChooser.setSelectedFile(new File(tituloReporte.replace(" ", "_") + ".pdf"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            return filePath;
        }
        return null;
    }

    /**
     * 4. Crear el PDF profesional de ganancias con gráficas
     */
    private void crearPDFGananciasProfesional(String filePath, String tituloReporte, 
                                           List<String[]> resumenGanancias, 
                                           List<String[]> gananciasMensuales,
                                           List<String[]> gananciasPorCategoria,
                                           List<String[]> ventasTopProductos,
                                           List<String[]> ventasRecientes,
                                           String fechaInicio, String fechaFin) {
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(40, 40, 80, 40);
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new BackgroundEventHandlerGanancias());
            
            document.open();
            
            // Fuentes
            Font fontTituloPrincipal = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.GRAY);
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font fontPequeño = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
            
            // Crear tabla para encabezado con logo
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setSpacingAfter(10f);
            
            // Celda para el logo
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER);
            celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            com.itextpdf.text.Image logo = obtenerLogoEmpresa();
            if (logo != null) {
                celdaLogo.addElement(logo);
            } else {
                // Si no hay logo, agregar espacio en blanco
                celdaLogo.addElement(new Paragraph(" "));
            }
            
            // Celda para la información de la empresa
            PdfPCell celdaEmpresa = new PdfPCell();
            celdaEmpresa.setBorder(PdfPCell.NO_BORDER);
            celdaEmpresa.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaEmpresa.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            // Información de la empresa
            Paragraph empresa = new Paragraph(NOMBRE_EMPRESA, fontTituloPrincipal);
            empresa.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph linea = new Paragraph("_________________________________________________________");
            linea.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph titulo = new Paragraph(tituloReporte.toUpperCase(), fontSubtitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5);
            
            Paragraph periodo = new Paragraph("PERÍODO: " + fechaInicio + " AL " + fechaFin, fontNegrita);
            periodo.setAlignment(Element.ALIGN_CENTER);
            periodo.setSpacingAfter(3);
            
            Paragraph estadisticas = new Paragraph("ANÁLISIS COMPLETO DE GANANCIAS Y RENTABILIDAD", fontNormal);
            estadisticas.setAlignment(Element.ALIGN_CENTER);
            estadisticas.setSpacingAfter(10);
            
            celdaEmpresa.addElement(empresa);
            celdaEmpresa.addElement(linea);
            celdaEmpresa.addElement(titulo);
            celdaEmpresa.addElement(periodo);
            celdaEmpresa.addElement(estadisticas);
            
            // Agregar celdas a la tabla
            tablaEncabezado.addCell(celdaLogo);
            tablaEncabezado.addCell(celdaEmpresa);
            
            document.add(tablaEncabezado);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss");
            String fechaHora = sdf.format(new Date());
            
            Paragraph fechaGeneracion = new Paragraph("Generado el " + fechaHora, fontPequeño);
            fechaGeneracion.setAlignment(Element.ALIGN_RIGHT);
            fechaGeneracion.setSpacingAfter(15);
            document.add(fechaGeneracion);
            
            // Resumen Ejecutivo
            agregarResumenEjecutivo(document, resumenGanancias, fontNegrita, fontNormal);
            
            // Resumen Visual
            agregarResumenVisual(document, resumenGanancias, fontNegrita);
            
            // Gráfica circular de distribución de ganancias
            agregarGraficaPastel(document, resumenGanancias, fontNegrita);
            
            // Análisis por categoría
            agregarAnalisisCategoria(document, gananciasPorCategoria, fontNegrita, fontNormal);
            
            // Top productos más rentables
            agregarTopProductos(document, ventasTopProductos, fontNegrita, fontNormal, fontPequeño);
            
            // Ventas recientes
            agregarVentasRecientes(document, ventasRecientes, fontNegrita, fontNormal, fontPequeño);
            
            // Tendencias mensuales
            agregarTendenciasMensuales(document, gananciasMensuales, fontNegrita, fontNormal);
            
            // Recomendaciones
            agregarRecomendaciones(document, fontNegrita, fontNormal);
            
            // Pie de página
            Paragraph piePagina = new Paragraph("\n\n* Reporte de ganancias generado automáticamente por el Sistema Automatizado AdminPyme\nPara análisis financiero y toma de decisiones*", 
                                               new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
            piePagina.setAlignment(Element.ALIGN_CENTER);
            document.add(piePagina);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear PDF de ganancias: " + e.getMessage(), e);
        } finally {
            document.close();
        }
    }
    
    private void agregarResumenEjecutivo(Document document, List<String[]> resumenGanancias, 
                                       Font fontNegrita, Font fontNormal) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RESUMEN EJECUTIVO DE GANANCIAS", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Crear tabla de resumen
        PdfPTable tablaResumen = new PdfPTable(4);
        tablaResumen.setWidthPercentage(100);
        tablaResumen.setSpacingBefore(10f);
        tablaResumen.setSpacingAfter(20f);
        
        float[] anchosColumnas = {30f, 20f, 25f, 25f};
        tablaResumen.setWidths(anchosColumnas);
        
        // Encabezados
        String[] columnas = {"TIPO DE GANANCIA", "TRANSACCIONES", "MONTO TOTAL", "PROMEDIO"};
        BaseColor colorHeader = new BaseColor(34, 139, 34); // Verde para ganancias
        
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(6);
            header.setBorderWidth(1);
            header.setBorderColor(BaseColor.WHITE);
            tablaResumen.addCell(header);
        }
        
        // Datos
        double totalGeneral = 0;
        for (String[] fila : resumenGanancias) {
            for (int i = 0; i < fila.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(fila[i], fontNormal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(4);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                tablaResumen.addCell(cell);
                
                if (i == 2) { // Columna de monto total
                    try {
                        totalGeneral += Double.parseDouble(fila[i].replace(",", ""));
                    } catch (NumberFormatException e) {
                        // Ignorar si no se puede parsear
                    }
                }
            }
        }
        
        document.add(tablaResumen);
        
        // Total general
        Paragraph total = new Paragraph("GANANCIA TOTAL GENERAL: Bs. " + String.format("%,.2f", totalGeneral), 
                                      new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(34, 139, 34)));
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingAfter(15f);
        document.add(total);
    }
    
    private void agregarResumenVisual(Document document, List<String[]> resumenGanancias, 
                                   Font fontNegrita) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RESUMEN VISUAL DE GANANCIAS", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Crear tabla de resumen visual
        PdfPTable tablaVisual = new PdfPTable(3);
        tablaVisual.setWidthPercentage(100);
        tablaVisual.setSpacingBefore(10f);
        tablaVisual.setSpacingAfter(15f);
        
        // Calcular total
        double total = 0;
        for (String[] fila : resumenGanancias) {
            try {
                total += Double.parseDouble(fila[2].replace(",", ""));
            } catch (NumberFormatException e) {
                // Ignorar si no se puede parsear
            }
        }
        
        // Crear celdas visuales para cada tipo de ganancia
        for (String[] fila : resumenGanancias) {
            PdfPCell celdaVisual = new PdfPCell();
            celdaVisual.setBorder(PdfPCell.NO_BORDER);
            celdaVisual.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaVisual.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaVisual.setPadding(10);
            
            try {
                double monto = Double.parseDouble(fila[2].replace(",", ""));
                double porcentaje = total > 0 ? (monto / total) * 100 : 0;
                
                // Crear contenido visual
                Paragraph contenido = new Paragraph();
                contenido.setAlignment(Element.ALIGN_CENTER);
                
                // Icono representativo
                String icono = "";
                if (fila[0].contains("Contado")) icono = "💰";
                else if (fila[0].contains("Crédito")) icono = "📈";
                else if (fila[0].contains("Neta")) icono = "💵";
                else icono = "●";
                
                contenido.add(new Paragraph(icono, 
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(34, 139, 34))));
                contenido.add(new Paragraph(fila[0], 
                    new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.DARK_GRAY)));
                contenido.add(new Paragraph(String.format("%.1f%%", porcentaje), 
                    new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(34, 139, 34))));
                contenido.add(new Paragraph("Bs. " + fila[2], 
                    new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK)));
                
                celdaVisual.addElement(contenido);
                
            } catch (NumberFormatException e) {
                celdaVisual.addElement(new Paragraph("Dato no disponible", 
                    new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.GRAY)));
            }
            
            tablaVisual.addCell(celdaVisual);
        }
        
        document.add(tablaVisual);
    }
    
    private void agregarGraficaPastel(Document document, List<String[]> resumenGanancias, 
                                   Font fontNegrita) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("DISTRIBUCIÓN DE GANANCIAS - GRÁFICA CIRCULAR", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Calcular total para porcentajes
        double total = 0;
        List<Double> montos = new ArrayList<>();
        List<String> tipos = new ArrayList<>();
        List<BaseColor> colores = new ArrayList<>();
        
        // Definir colores para cada tipo de ganancia
        colores.add(new BaseColor(34, 139, 34));   // Verde - Ventas Contado
        colores.add(new BaseColor(70, 130, 180));  // Azul - Ventas Crédito
        colores.add(new BaseColor(218, 165, 32));  // Dorado - Ganancia Neta
        colores.add(new BaseColor(148, 0, 211));   // Violeta - Extra si hay más categorías
        
        for (String[] fila : resumenGanancias) {
            try {
                double monto = Double.parseDouble(fila[2].replace(",", ""));
                total += monto;
                montos.add(monto);
                tipos.add(fila[0]);
            } catch (NumberFormatException e) {
                // Ignorar si no se puede parsear
            }
        }
        
        // Crear tabla para la gráfica y leyenda
        PdfPTable tablaGrafica = new PdfPTable(2);
        tablaGrafica.setWidthPercentage(100);
        tablaGrafica.setSpacingBefore(10f);
        tablaGrafica.setSpacingAfter(15f);
        
        // Celda para la gráfica de texto
        PdfPCell celdaGrafica = new PdfPCell();
        celdaGrafica.setBorder(PdfPCell.NO_BORDER);
        celdaGrafica.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaGrafica.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        // Crear representación de gráfica circular con texto
        Paragraph graficaCircular = new Paragraph();
        graficaCircular.setAlignment(Element.ALIGN_CENTER);
        
        // Agregar título de la gráfica
        graficaCircular.add(new Paragraph("DISTRIBUCIÓN DE GANANCIAS", 
            new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY)));
        graficaCircular.add(new Paragraph(" "));
        
        // Crear representación visual de la gráfica circular
        if (total > 0) {
            // Simular gráfica circular con caracteres Unicode
            String[] sectores = {"▓▓▓▓", "▒▒▒▒", "░░░░", "||||"};
            
            StringBuilder graficaTexto = new StringBuilder();
            for (int i = 0; i < montos.size() && i < sectores.length; i++) {
                double porcentaje = (montos.get(i) / total) * 100;
                String tipoAbreviado = abreviarTipoGanancia(tipos.get(i));
                
                // Agregar sector con color representativo
                graficaTexto.append(sectores[i])
                           .append(" ")
                           .append(String.format("%.1f%%", porcentaje))
                           .append(" ")
                           .append(tipoAbreviado)
                           .append("\n");
            }
            
            graficaCircular.add(new Paragraph(graficaTexto.toString(),
                new Font(Font.FontFamily.COURIER, 9, Font.BOLD, BaseColor.BLACK)));
        }
        
        celdaGrafica.addElement(graficaCircular);
        
        // Celda para la leyenda
        PdfPCell celdaLeyenda = new PdfPCell();
        celdaLeyenda.setBorder(PdfPCell.NO_BORDER);
        celdaLeyenda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaLeyenda.setVerticalAlignment(Element.ALIGN_TOP);
        
        // Crear leyenda detallada
        Paragraph leyenda = new Paragraph("LEYENDA DETALLADA:\n", 
            new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.DARK_GRAY));
        leyenda.setSpacingAfter(10f);
        
        if (total > 0) {
            for (int i = 0; i < montos.size(); i++) {
                double porcentaje = (montos.get(i) / total) * 100;
                double monto = montos.get(i);
                
                // Crear ítem de leyenda con color
                Paragraph itemLeyenda = new Paragraph();
                
                // Agregar cuadro de color
                Chunk cuadroColor = new Chunk("■ ", 
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, 
                            i < colores.size() ? colores.get(i) : BaseColor.BLACK));
                itemLeyenda.add(cuadroColor);
                
                // Agregar texto de la leyenda
                String textoLeyenda = String.format("%s: %6.1f%% (Bs. %,.2f)", 
                    tipos.get(i), porcentaje, monto);
                itemLeyenda.add(new Phrase(textoLeyenda, 
                    new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL)));
                
                leyenda.add(itemLeyenda);
            }
            
            // Agregar total general
            leyenda.add(new Paragraph("\nTOTAL GENERAL: Bs. " + String.format("%,.2f", total),
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, new BaseColor(34, 139, 34))));
        }
        
        celdaLeyenda.addElement(leyenda);
        
        // Agregar celdas a la tabla
        tablaGrafica.addCell(celdaGrafica);
        tablaGrafica.addCell(celdaLeyenda);
        
        document.add(tablaGrafica);
        
        // Agregar nota explicativa
        Paragraph nota = new Paragraph(
            "Nota: El tamaño de cada sector es proporcional al monto de ganancia de cada categoría.",
            new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC, BaseColor.GRAY));
        nota.setAlignment(Element.ALIGN_CENTER);
        document.add(nota);
    }
    
    // Método auxiliar para abreviar los tipos de ganancia
    private String abreviarTipoGanancia(String tipoCompleto) {
        if (tipoCompleto.contains("Contado")) return "Vent.Cont.";
        if (tipoCompleto.contains("Crédito")) return "Vent.Créd.";
        if (tipoCompleto.contains("Neta")) return "Gan.Neta";
        return tipoCompleto.length() > 10 ? tipoCompleto.substring(0, 10) + "." : tipoCompleto;
    }
    
    private void agregarAnalisisCategoria(Document document, List<String[]> gananciasPorCategoria, 
                                        Font fontNegrita, Font fontNormal) throws DocumentException {
        if (gananciasPorCategoria.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("ANÁLISIS DE GANANCIAS POR CATEGORÍA", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaCategoria = new PdfPTable(7);
        tablaCategoria.setWidthPercentage(100);
        tablaCategoria.setSpacingBefore(10f);
        tablaCategoria.setSpacingAfter(15f);
        
        float[] anchosColumnas = {25f, 12f, 15f, 12f, 15f, 15f, 12f};
        tablaCategoria.setWidths(anchosColumnas);
        
        String[] encabezados = {"CATEGORÍA", "V.CONT.", "MONTO CONT.", "V.CRÉD.", "MONTO CRÉD.", "GAN. NETA", "MARGEN %"};
        BaseColor colorHeader = new BaseColor(70, 130, 180); // Azul acero
        
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell(new Phrase(encabezado, 
                new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            tablaCategoria.addCell(header);
        }
        
        for (String[] fila : gananciasPorCategoria) {
            for (int i = 0; i < fila.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(fila[i], fontNormal));
                cell.setHorizontalAlignment(i == 0 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
                cell.setPadding(3);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                
                // Resaltar valores altos de margen
                if (i == 6) { // Columna de margen
                    try {
                        double margen = Double.parseDouble(fila[i].replace(",", ""));
                        if (margen > 30) {
                            cell.setBackgroundColor(new BaseColor(220, 255, 220)); // Verde claro
                        } else if (margen < 10) {
                            cell.setBackgroundColor(new BaseColor(255, 220, 220)); // Rojo claro
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar si no se puede parsear
                    }
                }
                
                tablaCategoria.addCell(cell);
            }
        }
        
        document.add(tablaCategoria);
    }
    
    private void agregarTopProductos(Document document, List<String[]> ventasTopProductos, 
                                   Font fontNegrita, Font fontNormal, Font fontPequeño) throws DocumentException {
        if (ventasTopProductos.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("TOP 15 PRODUCTOS MÁS RENTABLES", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaTopProductos = new PdfPTable(8);
        tablaTopProductos.setWidthPercentage(100);
        tablaTopProductos.setSpacingBefore(10f);
        tablaTopProductos.setSpacingAfter(15f);
        
        float[] anchosColumnas = {8f, 25f, 15f, 10f, 12f, 12f, 12f, 10f};
        tablaTopProductos.setWidths(anchosColumnas);
        
        String[] encabezados = {"ID", "PRODUCTO", "CATEGORÍA", "CANT.", "PRECIO", "TOTAL", "GANANCIA", "MARGEN%"};
        BaseColor colorHeader = new BaseColor(218, 165, 32); // Dorado
        
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell(new Phrase(encabezado, 
                new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            tablaTopProductos.addCell(header);
        }
        
        for (String[] fila : ventasTopProductos) {
            for (int i = 0; i < fila.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(fila[i], fontNormal));
                cell.setHorizontalAlignment(i == 1 || i == 2 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
                cell.setPadding(3);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                
                // Resaltar productos con alto margen
                if (i == 7) { // Columna de margen
                    try {
                        double margen = Double.parseDouble(fila[i].replace(",", ""));
                        if (margen > 40) {
                            cell.setBackgroundColor(new BaseColor(220, 255, 220)); // Verde claro
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar si no se puede parsear
                    }
                }
                
                tablaTopProductos.addCell(cell);
            }
        }
        
        document.add(tablaTopProductos);
    }
    
    private void agregarVentasRecientes(Document document, List<String[]> ventasRecientes, 
                                      Font fontNegrita, Font fontNormal, Font fontPequeño) throws DocumentException {
        if (ventasRecientes.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("VENTAS RECIENTES (ÚLTIMAS 20)", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaVentas = new PdfPTable(9);
        tablaVentas.setWidthPercentage(100);
        tablaVentas.setSpacingBefore(10f);
        tablaVentas.setSpacingAfter(15f);
        
        float[] anchosColumnas = {8f, 20f, 15f, 15f, 8f, 12f, 15f, 12f, 10f};
        tablaVentas.setWidths(anchosColumnas);
        
        String[] encabezados = {"ID", "PRODUCTO", "CATEGORÍA", "CLIENTE", "CANT.", "TOTAL", "FECHA", "VENDEDOR", "TIPO"};
        BaseColor colorHeader = new BaseColor(148, 0, 211); // Violeta
        
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell(new Phrase(encabezado, 
                new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(3);
            tablaVentas.addCell(header);
        }
        
        for (String[] fila : ventasRecientes) {
            for (int i = 0; i < fila.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(fila[i], fontPequeño));
                cell.setHorizontalAlignment(i == 1 || i == 2 || i == 3 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
                cell.setPadding(2);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                
                // Resaltar ventas al contado
                if (i == 8 && "Contado".equals(fila[i])) {
                    cell.setBackgroundColor(new BaseColor(220, 255, 220)); // Verde claro
                }
                
                tablaVentas.addCell(cell);
            }
        }
        
        document.add(tablaVentas);
    }
    
    private void agregarTendenciasMensuales(Document document, List<String[]> gananciasMensuales, 
                                          Font fontNegrita, Font fontNormal) throws DocumentException {
        if (gananciasMensuales.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("TENDENCIAS MENSUALES DE GANANCIAS", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaMensual = new PdfPTable(8);
        tablaMensual.setWidthPercentage(100);
        tablaMensual.setSpacingBefore(10f);
        tablaMensual.setSpacingAfter(15f);
        
        float[] anchosColumnas = {8f, 8f, 10f, 12f, 10f, 12f, 15f, 15f};
        tablaMensual.setWidths(anchosColumnas);
        
        String[] encabezados = {"AÑO", "MES", "V.CONT.", "MONTO CONT.", "V.CRÉD.", "MONTO CRÉD.", "GAN. NETA", "TOTAL GAN."};
        BaseColor colorHeader = new BaseColor(139, 0, 0); // Rojo oscuro
        
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell(new Phrase(encabezado, 
                new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            tablaMensual.addCell(header);
        }
        
        for (String[] fila : gananciasMensuales) {
            for (int i = 0; i < fila.length; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(fila[i], fontNormal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(3);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                
                // Resaltar meses con alta ganancia
                if (i == 7) { // Columna de total ganancias
                    try {
                        double total = Double.parseDouble(fila[i].replace(",", ""));
                        if (total > 5000) {
                            cell.setBackgroundColor(new BaseColor(220, 255, 220)); // Verde claro
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar si no se puede parsear
                    }
                }
                
                tablaMensual.addCell(cell);
            }
        }
        
        document.add(tablaMensual);
    }
    
    private void agregarRecomendaciones(Document document, Font fontNegrita, Font fontNormal) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RECOMENDACIONES Y ANÁLISIS", fontNegrita);
        tituloSeccion.setSpacingBefore(20f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Crear tabla de recomendaciones
        PdfPTable tablaRecomendaciones = new PdfPTable(2);
        tablaRecomendaciones.setWidthPercentage(100);
        tablaRecomendaciones.setSpacingBefore(10f);
        tablaRecomendaciones.setSpacingAfter(15f);
        
        // Recomendaciones generales
        String[] recomendaciones = {
            "📊 Optimizar inventario basado en productos más rentables",
            "💰 Incrementar promociones en categorías de alto margen",
            "📈 Revisar estrategias de precios para productos con bajo margen",
            "⏰ Reducir tiempos de entrega para ventas al crédito",
            "🎯 Capacitar vendedores en productos de alta rentabilidad",
            "📱 Implementar campañas de marketing para categorías destacadas",
            "💡 Analizar posibilidad de aumentar precios en productos populares",
            "🔄 Revisar políticas de crédito para mejorar flujo de caja"
        };
        
        for (String recomendacion : recomendaciones) {
            PdfPCell celda = new PdfPCell(new Phrase(recomendacion, fontNormal));
            celda.setBorder(PdfPCell.NO_BORDER);
            celda.setPadding(5);
            celda.setBackgroundColor(new BaseColor(245, 245, 245));
            tablaRecomendaciones.addCell(celda);
        }
        
        document.add(tablaRecomendaciones);
    }

    /**
     * 5. Preguntar si desea abrir el reporte
     */
    private void abrirReporteGenerado(String filePath, int cantidadRegistros) {
        String mensaje = String.format(
            "¡Reporte de ganancias generado exitosamente!\n\n" +
            "Archivo: %s\n" +
            "Registros procesados: %d\n\n" +
            "¿Desea abrir el reporte ahora?",
            new File(filePath).getName(), cantidadRegistros
        );
        
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, 
                "Reporte Generado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo abrir el archivo automáticamente. " +
                    "Puede abrirlo manualmente desde: " + filePath);
            }
        }
    }

    /**
     * Manejo de errores
     */
    private void manejarErrorGeneracion(Exception e) {
        String mensajeError = "Error al generar el reporte de ganancias:\n" + e.getMessage();
        System.err.println(mensajeError);
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(null, mensajeError, 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Clase para manejar el fondo y eventos de página del PDF de ganancias
 */
class BackgroundEventHandlerGanancias extends PdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContentUnder();
            
            // Fondo degradado suave
            com.itextpdf.text.Rectangle rect = document.getPageSize();
            canvas.setColorFill(new BaseColor(250, 250, 250));
            canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
            canvas.fill();
            
            // Marca de agua sutil
            canvas.beginText();
            canvas.setColorFill(new BaseColor(240, 240, 240));
            canvas.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 60);
            canvas.showTextAligned(Element.ALIGN_CENTER, "GANANCIAS", 
                rect.getWidth() / 2, rect.getHeight() / 2, 45);
            canvas.endText();
            
            // Pie de página
            canvas.beginText();
            canvas.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 8);
            canvas.setColorFill(BaseColor.GRAY);
            canvas.showTextAligned(Element.ALIGN_CENTER, 
                "Página " + writer.getPageNumber() + " - Reporte Confidencial de Ganancias", 
                rect.getWidth() / 2, rect.getBottom() + 20, 0);
            canvas.endText();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}