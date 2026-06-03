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

public class ReportePerdidasPDFProfesional extends sql_consulta {
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
    
    public void generarReportePerdidas() {
        String filePath = null; // Variable para almacenar la ruta del archivo
        
        try {
            // 1. Diálogo para seleccionar rango de fechas
            Object[] fechas = mostrarDialogoFechas();
            if (fechas == null) return;
            
            String fechaInicio = (String) fechas[0];
            String fechaFin = (String) fechas[1];
            
            // 2. Obtener datos de las vistas
            List<String[]> resumenPerdidas = obtenerResumenPerdidas();
            List<String[]> perdidasMensuales = obtenerPerdidasMensuales();
            List<String[]> perdidasPorCategoria = obtenerPerdidasPorCategoria();
            List<String[]> creditosVencidos = obtenerCreditosVencidos();
            List<String[]> devoluciones = obtenerDevoluciones();
            
            if (resumenPerdidas.isEmpty() && perdidasMensuales.isEmpty() && 
                perdidasPorCategoria.isEmpty() && creditosVencidos.isEmpty() && 
                devoluciones.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron datos de pérdidas en el rango de fechas especificado");
                return;
            }
            
            // 3. Diálogo para guardar PDF
            filePath = mostrarDialogoGuardarPDF(fechaInicio, fechaFin);
            if (filePath == null) return;
            
            // 4. Crear el PDF profesional
            String tituloReporte = "Reporte de Pérdidas " + fechaInicio + " al " + fechaFin;
            crearPDFPerdidasProfesional(filePath, tituloReporte, resumenPerdidas, 
                                      perdidasMensuales, perdidasPorCategoria, 
                                      creditosVencidos, devoluciones, fechaInicio, fechaFin);
            
            // 5. Preguntar si desea abrir el reporte
            abrirReporteGenerado(filePath, resumenPerdidas.size());
            
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

    /**
     * 2. Obtener datos de las vistas de pérdidas
     */
    private List<String[]> obtenerResumenPerdidas() {
        List<String[]> datos = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        query = "SELECT * FROM vista_resumen_perdidas";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String tipoPerdida = rs.getString("Tipo Pérdida");
                String cantidadCasos = String.valueOf(rs.getInt("Cantidad Casos"));
                String montoTotal = String.format("%,.2f", rs.getDouble("Monto Total"));
                String promedio = String.format("%,.2f", rs.getDouble("Promedio por Caso"));
                
                String[] fila = {tipoPerdida, cantidadCasos, montoTotal, promedio};
                datos.add(fila);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener resumen de pérdidas: " + e.getMessage(), e);
        }
        
        return datos;
    }
    
    private List<String[]> obtenerPerdidasMensuales() {
        List<String[]> datos = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        query = "SELECT * FROM vista_perdidas_mensuales ORDER BY Año DESC, Mes DESC LIMIT 12";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String año = String.valueOf(rs.getInt("Año"));
                String mes = String.valueOf(rs.getInt("Mes"));
                String devoluciones = String.valueOf(rs.getInt("Devoluciones"));
                String montoDevoluciones = String.format("%,.2f", rs.getDouble("Monto Devoluciones"));
                String creditosVencidos = String.valueOf(rs.getInt("Créditos Vencidos"));
                String montoCreditos = String.format("%,.2f", rs.getDouble("Monto Créditos Vencidos"));
                String gastos = String.valueOf(rs.getInt("Gastos"));
                String montoGastos = String.format("%,.2f", rs.getDouble("Monto Gastos"));
                String totalPerdidas = String.format("%,.2f", rs.getDouble("Total Pérdidas"));
                
                String[] fila = {año, mes, devoluciones, montoDevoluciones, creditosVencidos, 
                               montoCreditos, gastos, montoGastos, totalPerdidas};
                datos.add(fila);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener pérdidas mensuales: " + e.getMessage(), e);
        }
        
        return datos;
    }
    
    private List<String[]> obtenerPerdidasPorCategoria() {
        List<String[]> datos = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        query = "SELECT * FROM vista_perdidas_por_categoria";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String categoria = rs.getString(1);
                String devoluciones = String.valueOf(rs.getInt(2));
                String montoDevoluciones = String.format("%,.2f", rs.getDouble(3));
                String creditosVencidos = String.valueOf(rs.getInt(4));
                String montoCreditos = String.format("%,.2f", rs.getDouble(5));
                String productosInactivos = String.valueOf(rs.getInt(6));
                String valorInventario = String.format("%,.2f", rs.getDouble(7));
                
                String[] fila = {categoria, devoluciones, montoDevoluciones, creditosVencidos, 
                               montoCreditos, productosInactivos, valorInventario};
                datos.add(fila);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener pérdidas por categoría: " + e.getMessage(), e);
        }
        
        return datos;
    }
    
    private List<String[]> obtenerCreditosVencidos() {
        List<String[]> datos = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        query = "SELECT * FROM vista_perdidas_creditos_vencidos ORDER BY `Dias Vencidos` DESC LIMIT 20";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String idVenta = String.valueOf(rs.getInt("ID Venta Crédito"));
                String producto = rs.getString("Producto");
                String cliente = rs.getString("Cliente");
                String cedula = String.valueOf(rs.getInt("Cédula"));
                String totalAdeudado = String.format("%,.2f", rs.getDouble("Total Adeudado"));
                String fechaVencimiento = rs.getDate("Fecha Vencimiento") != null ? 
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("Fecha Vencimiento")) : "N/A";
                String diasVencidos = String.valueOf(rs.getInt("Dias Vencidos"));
                String nivelRiesgo = rs.getString("Nivel Riesgo");
                
                String[] fila = {idVenta, producto, cliente, cedula, totalAdeudado, 
                               fechaVencimiento, diasVencidos, nivelRiesgo};
                datos.add(fila);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener créditos vencidos: " + e.getMessage(), e);
        }
        
        return datos;
    }
    
    private List<String[]> obtenerDevoluciones() {
        List<String[]> datos = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        query = "SELECT * FROM vista_perdidas_devoluciones ORDER BY `Fecha Venta` DESC LIMIT 20";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String idDevolucion = String.valueOf(rs.getInt("ID Devolución"));
                String producto = rs.getString("Producto");
                String categoria = rs.getString(4);
                String cliente = rs.getString("Cliente");
                String cantidadDevuelta = String.valueOf(rs.getInt("Cantidad Devuelta"));
                String totalPerdida = String.format("%,.2f", rs.getDouble("Total Pérdida"));
                String fechaVenta = rs.getTimestamp("Fecha Venta") != null ? 
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("Fecha Venta")) : "N/A";
                String vendedor = rs.getString("Vendedor");
                
                String[] fila = {idDevolucion, producto, categoria, cliente, cantidadDevuelta, 
                               totalPerdida, fechaVenta, vendedor};
                datos.add(fila);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener devoluciones: " + e.getMessage(), e);
        }
        
        return datos;
    }

    /**
     * 3. Diálogo para guardar PDF
     */
    private String mostrarDialogoGuardarPDF(String fechaInicio, String fechaFin) {
        String tituloReporte = "Reporte de Perdidas " + fechaInicio + " al " + fechaFin;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte PDF de Pérdidas");
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
     * 4. Crear el PDF profesional de pérdidas con gráficas
     */
    private void crearPDFPerdidasProfesional(String filePath, String tituloReporte, 
                                           List<String[]> resumenPerdidas, 
                                           List<String[]> perdidasMensuales,
                                           List<String[]> perdidasPorCategoria,
                                           List<String[]> creditosVencidos,
                                           List<String[]> devoluciones,
                                           String fechaInicio, String fechaFin) {
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(40, 40, 80, 40);
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new BackgroundEventHandlerPerdidas());
            
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
            
            Paragraph estadisticas = new Paragraph("ANÁLISIS COMPLETO DE PÉRDIDAS Y DEVOLUCIONES", fontNormal);
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
            agregarResumenEjecutivo(document, resumenPerdidas, fontNegrita, fontNormal);
            
            // Resumen Visual
            agregarResumenVisual(document, resumenPerdidas, fontNegrita);
            
            // Gráfica circular de distribución de pérdidas
            agregarGraficaPastel(document, resumenPerdidas, fontNegrita);
            
            // Análisis por categoría
            agregarAnalisisCategoria(document, perdidasPorCategoria, fontNegrita, fontNormal);
            
            // Créditos vencidos críticos
            agregarCreditosVencidos(document, creditosVencidos, fontNegrita, fontNormal, fontPequeño);
            
            // Devoluciones recientes
            agregarDevolucionesRecientes(document, devoluciones, fontNegrita, fontNormal, fontPequeño);
            
            // Tendencias mensuales
            agregarTendenciasMensuales(document, perdidasMensuales, fontNegrita, fontNormal);
            
            // Recomendaciones
            agregarRecomendaciones(document, fontNegrita, fontNormal);
            
            // Pie de página
            Paragraph piePagina = new Paragraph("\n\n* Reporte de pérdidas generado automáticamente por el Sistema Automatizado AdminPyme\nPara análisis financiero y toma de decisiones*", 
                                               new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
            piePagina.setAlignment(Element.ALIGN_CENTER);
            document.add(piePagina);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear PDF de pérdidas: " + e.getMessage(), e);
        } finally {
            document.close();
        }
    }
    
    private void agregarResumenEjecutivo(Document document, List<String[]> resumenPerdidas, 
                                       Font fontNegrita, Font fontNormal) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RESUMEN EJECUTIVO DE PÉRDIDAS", fontNegrita);
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
        String[] columnas = {"TIPO DE PÉRDIDA", "CASOS", "MONTO TOTAL", "PROMEDIO"};
        BaseColor colorHeader = new BaseColor(70, 130, 180);
        
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
        for (String[] fila : resumenPerdidas) {
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
        Paragraph total = new Paragraph("PÉRDIDA TOTAL GENERAL: Bs. " + String.format("%,.2f", totalGeneral), 
                                      new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED));
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingAfter(15f);
        document.add(total);
    }
    
    private void agregarResumenVisual(Document document, List<String[]> resumenPerdidas, 
                                   Font fontNegrita) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RESUMEN VISUAL DE PÉRDIDAS", fontNegrita);
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
        for (String[] fila : resumenPerdidas) {
            try {
                total += Double.parseDouble(fila[2].replace(",", ""));
            } catch (NumberFormatException e) {
                // Ignorar si no se puede parsear
            }
        }
        
        // Crear celdas visuales para cada tipo de pérdida
        for (String[] fila : resumenPerdidas) {
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
                if (fila[0].contains("Devolucion")) icono = "↻";
                else if (fila[0].contains("Crédito")) icono = "⚠";
                else if (fila[0].contains("Gasto")) icono = "💰";
                else if (fila[0].contains("Inactivo")) icono = "📦";
                else icono = "●";
                
                contenido.add(new Paragraph(icono, 
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY)));
                contenido.add(new Paragraph(fila[0], 
                    new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.DARK_GRAY)));
                contenido.add(new Paragraph(String.format("%.1f%%", porcentaje), 
                    new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED)));
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
    
    private void agregarGraficaPastel(Document document, List<String[]> resumenPerdidas, 
                                   Font fontNegrita) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("DISTRIBUCIÓN DE PÉRDIDAS - GRÁFICA CIRCULAR", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Calcular total para porcentajes
        double total = 0;
        List<Double> montos = new ArrayList<>();
        List<String> tipos = new ArrayList<>();
        List<BaseColor> colores = new ArrayList<>();
        
        // Definir colores para cada tipo de pérdida
        colores.add(new BaseColor(70, 130, 180));   // Azul - Devoluciones
        colores.add(new BaseColor(178, 34, 34));    // Rojo - Créditos Vencidos
        colores.add(new BaseColor(34, 139, 34));    // Verde - Gastos Operativos
        colores.add(new BaseColor(218, 165, 32));   // Dorado - Productos Inactivos
        colores.add(new BaseColor(148, 0, 211));    // Violeta - Extra si hay más categorías
        
        for (String[] fila : resumenPerdidas) {
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
        graficaCircular.add(new Paragraph("DISTRIBUCIÓN DE PÉRDIDAS", 
            new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY)));
        graficaCircular.add(new Paragraph(" "));
        
        // Crear representación visual de la gráfica circular
        if (total > 0) {
            // Simular gráfica circular con caracteres Unicode
            String[] sectores = {"▓▓▓▓", "▒▒▒▒", "░░░░", "||||", "++++"};
            
            StringBuilder graficaTexto = new StringBuilder();
            for (int i = 0; i < montos.size() && i < sectores.length; i++) {
                double porcentaje = (montos.get(i) / total) * 100;
                String tipoAbreviado = abreviarTipoPerdida(tipos.get(i));
                
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
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.RED)));
        }
        
        celdaLeyenda.addElement(leyenda);
        
        // Agregar celdas a la tabla
        tablaGrafica.addCell(celdaGrafica);
        tablaGrafica.addCell(celdaLeyenda);
        
        document.add(tablaGrafica);
        
        // Agregar nota explicativa
        Paragraph nota = new Paragraph(
            "Nota: El tamaño de cada sector es proporcional al monto de pérdida de cada categoría.",
            new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC, BaseColor.GRAY));
        nota.setAlignment(Element.ALIGN_CENTER);
        document.add(nota);
    }
    
    // Método auxiliar para abreviar los tipos de pérdida
    private String abreviarTipoPerdida(String tipoCompleto) {
        if (tipoCompleto.contains("Devolucion")) return "Devol.";
        if (tipoCompleto.contains("Crédito")) return "Créd.Venc.";
        if (tipoCompleto.contains("Gasto")) return "Gastos Op.";
        if (tipoCompleto.contains("Inactivo")) return "Prod.Inac.";
        return tipoCompleto.length() > 10 ? tipoCompleto.substring(0, 10) + "." : tipoCompleto;
    }
    
    private void agregarAnalisisCategoria(Document document, List<String[]> perdidasPorCategoria, 
                                        Font fontNegrita, Font fontNormal) throws DocumentException {
        if (perdidasPorCategoria.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("ANÁLISIS DE PÉRDIDAS POR CATEGORÍA", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaCategoria = new PdfPTable(4);
        tablaCategoria.setWidthPercentage(100);
        tablaCategoria.setSpacingBefore(10f);
        tablaCategoria.setSpacingAfter(15f);
        
        float[] anchosColumnas = {25f, 15f, 30f, 30f};
        tablaCategoria.setWidths(anchosColumnas);
        
        // Encabezados
        String[] columnas = {"CATEGORÍA", "DEVOLUCIONES", "CRÉDITOS VENCIDOS", "PRODUCTOS INACTIVOS"};
        BaseColor colorHeader = new BaseColor(70, 130, 180);
        
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            header.setBorderWidth(1);
            header.setBorderColor(BaseColor.WHITE);
            tablaCategoria.addCell(header);
        }
        
        // Datos
        for (String[] fila : perdidasPorCategoria) {
            for (int i = 0; i < 4; i++) { // Solo las primeras 4 columnas relevantes
                String texto = i < fila.length ? fila[i] : "N/A";
                PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(3);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(new BaseColor(200, 200, 200));
                tablaCategoria.addCell(cell);
            }
        }
        
        document.add(tablaCategoria);
    }
    
    private void agregarCreditosVencidos(Document document, List<String[]> creditosVencidos, 
                                       Font fontNegrita, Font fontNormal, Font fontPequeño) throws DocumentException {
        if (creditosVencidos.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("CRÉDITOS VENCIDOS - TOP 20", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Crear tabla para créditos vencidos
        PdfPTable tablaCreditos = new PdfPTable(5);
        tablaCreditos.setWidthPercentage(100);
        tablaCreditos.setSpacingBefore(10f);
        tablaCreditos.setSpacingAfter(15f);
        
        float[] anchosColumnas = {15f, 25f, 20f, 20f, 20f};
        tablaCreditos.setWidths(anchosColumnas);
        
        // Encabezados
        String[] columnas = {"ID VENTA", "PRODUCTO", "CLIENTE", "TOTAL ADEUDADO", "DÍAS VENCIDOS"};
        BaseColor colorHeader = new BaseColor(178, 34, 34); // Rojo para créditos vencidos
        
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            header.setBorderWidth(1);
            header.setBorderColor(BaseColor.WHITE);
            tablaCreditos.addCell(header);
        }
        
        // Datos
        double totalAdeudado = 0;
        for (String[] fila : creditosVencidos) {
            for (int i = 0; i < 5; i++) {
                if (i < fila.length) {
                    String texto = fila[i];
                    PdfPCell cell = new PdfPCell(new Phrase(texto, fontPequeño));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(3);
                    cell.setBorderWidth(0.5f);
                    cell.setBorderColor(new BaseColor(200, 200, 200));
                    
                    // Resaltar créditos con más de 30 días vencidos
                    if (i == 4 && fila.length > 6) {
                        try {
                            int diasVencidos = Integer.parseInt(fila[6]);
                            if (diasVencidos > 30) {
                                cell.setBackgroundColor(new BaseColor(255, 200, 200)); // Rojo claro
                            } else if (diasVencidos > 15) {
                                cell.setBackgroundColor(new BaseColor(255, 235, 200)); // Amarillo claro
                            }
                        } catch (NumberFormatException e) {
                            // Ignorar si no se puede parsear
                        }
                    }
                    
                    tablaCreditos.addCell(cell);
                    
                    // Sumar total adeudado
                    if (i == 4) {
                        try {
                            totalAdeudado += Double.parseDouble(fila[4].replace(",", ""));
                        } catch (NumberFormatException e) {
                            // Ignorar si no se puede parsear
                        }
                    }
                }
            }
        }
        
        document.add(tablaCreditos);
        
        // Total de créditos vencidos
        Paragraph totalCreditos = new Paragraph("TOTAL CRÉDITOS VENCIDOS: Bs. " + String.format("%,.2f", totalAdeudado), 
                                              new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.RED));
        totalCreditos.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalCreditos);
    }
    
    private void agregarDevolucionesRecientes(Document document, List<String[]> devoluciones, 
                                            Font fontNegrita, Font fontNormal, Font fontPequeño) throws DocumentException {
        if (devoluciones.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("DEVOLUCIONES RECIENTES", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaDevoluciones = new PdfPTable(5);
        tablaDevoluciones.setWidthPercentage(100);
        tablaDevoluciones.setSpacingBefore(10f);
        tablaDevoluciones.setSpacingAfter(15f);
        
        float[] anchosColumnas = {15f, 25f, 20f, 20f, 20f};
        tablaDevoluciones.setWidths(anchosColumnas);
        
        // Encabezados
        String[] columnas = {"ID", "PRODUCTO", "CLIENTE", "CANTIDAD", "PÉRDIDA"};
        BaseColor colorHeader = new BaseColor(218, 165, 32); // Dorado para devoluciones
        
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            header.setBorderWidth(1);
            header.setBorderColor(BaseColor.WHITE);
            tablaDevoluciones.addCell(header);
        }
        
        // Datos
        double totalDevoluciones = 0;
        for (String[] fila : devoluciones) {
            for (int i = 0; i < 5; i++) {
                if (i < fila.length) {
                    String texto = fila[i];
                    PdfPCell cell = new PdfPCell(new Phrase(texto, fontPequeño));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(3);
                    cell.setBorderWidth(0.5f);
                    cell.setBorderColor(new BaseColor(200, 200, 200));
                    tablaDevoluciones.addCell(cell);
                    
                    // Sumar total de devoluciones
                    if (i == 4) {
                        try {
                            totalDevoluciones += Double.parseDouble(fila[5].replace(",", ""));
                        } catch (NumberFormatException e) {
                            // Ignorar si no se puede parsear
                        }
                    }
                }
            }
        }
        
        document.add(tablaDevoluciones);
        
        // Total de devoluciones
        Paragraph totalDev = new Paragraph("TOTAL DEVOLUCIONES: Bs. " + String.format("%,.2f", totalDevoluciones), 
                                         new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, new BaseColor(218, 165, 32)));
        totalDev.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalDev);
    }
    
    private void agregarTendenciasMensuales(Document document, List<String[]> perdidasMensuales, 
                                          Font fontNegrita, Font fontNormal) throws DocumentException {
        if (perdidasMensuales.isEmpty()) return;
        
        Paragraph tituloSeccion = new Paragraph("TENDENCIAS MENSUALES DE PÉRDIDAS", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        PdfPTable tablaMensual = new PdfPTable(6);
        tablaMensual.setWidthPercentage(100);
        tablaMensual.setSpacingBefore(10f);
        tablaMensual.setSpacingAfter(15f);
        
        float[] anchosColumnas = {10f, 10f, 15f, 20f, 20f, 25f};
        tablaMensual.setWidths(anchosColumnas);
        
        // Encabezados
        String[] columnas = {"AÑO", "MES", "DEVOLUCIONES", "CRÉDITOS VENC.", "GASTOS", "TOTAL PÉRDIDAS"};
        BaseColor colorHeader = new BaseColor(70, 130, 180);
        
        for (String columna : columnas) {
            PdfPCell header = new PdfPCell(new Phrase(columna, new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.WHITE)));
            header.setBackgroundColor(colorHeader);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(4);
            header.setBorderWidth(1);
            header.setBorderColor(BaseColor.WHITE);
            tablaMensual.addCell(header);
        }
        
        // Datos
        for (String[] fila : perdidasMensuales) {
            for (int i = 0; i < 6; i++) {
                if (i < fila.length) {
                    String texto = fila[i];
                    PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(3);
                    cell.setBorderWidth(0.5f);
                    cell.setBorderColor(new BaseColor(200, 200, 200));
                    tablaMensual.addCell(cell);
                }
            }
        }
        
        document.add(tablaMensual);
    }
    
    private void agregarRecomendaciones(Document document, Font fontNegrita, Font fontNormal) throws DocumentException {
        Paragraph tituloSeccion = new Paragraph("RECOMENDACIONES Y ACCIONES SUGERIDAS", fontNegrita);
        tituloSeccion.setSpacingBefore(15f);
        tituloSeccion.setSpacingAfter(10f);
        document.add(tituloSeccion);
        
        // Crear lista de recomendaciones
        List<String> recomendaciones = new ArrayList<>();
        recomendaciones.add("• Revisar políticas de crédito para clientes con historial de morosidad");
        recomendaciones.add("• Implementar seguimiento más estricto de cuentas por cobrar vencidas");
        recomendaciones.add("• Analizar causas recurrentes de devoluciones por categoría de producto");
        recomendaciones.add("• Optimizar niveles de inventario para productos con alta rotación");
        recomendaciones.add("• Establecer protocolos de control de calidad para reducir devoluciones");
        recomendaciones.add("• Capacitar al personal en gestión de créditos y cobranzas");
        recomendaciones.add("• Revisar periodicamente los productos inactivos en inventario");
        
        for (String recomendacion : recomendaciones) {
            Paragraph item = new Paragraph(recomendacion, fontNormal);
            item.setSpacingAfter(3f);
            document.add(item);
        }
    }

    /**
     * 5. Manejo de errores
     */
    private void manejarErrorGeneracion(Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al generar el reporte: " + e.getMessage(),
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
    /**
     * Método para preguntar y abrir el reporte PDF generado
     */
    private void abrirReporteGenerado(String rutaPDF, int totalRegistros) {
        int opcion = JOptionPane.showConfirmDialog(null, 
            "Reporte PDF de Pérdidas generado exitosamente:\n" + rutaPDF +
            "\n\nTotal de categorías analizadas: " + totalRegistros +
            "\n\n¿Desea abrir el reporte ahora?",
            "Reporte Generado - Abrir PDF", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            abrirArchivoPDF(rutaPDF);
        }
    }
    
    /**
     * Método para abrir el archivo PDF con el programa predeterminado del sistema
     */
    private void abrirArchivoPDF(String rutaArchivo) {
        try {
            File archivoPDF = new File(rutaArchivo);
            
            // Verificar si el archivo existe
            if (!archivoPDF.exists()) {
                JOptionPane.showMessageDialog(null,
                    "El archivo PDF no se encuentra en la ruta especificada:\n" + rutaArchivo,
                    "Archivo No Encontrado",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Intentar abrir con Desktop
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(archivoPDF);
                    JOptionPane.showMessageDialog(null,
                        "Reporte abierto correctamente en el visor de PDF predeterminado.",
                        "Reporte Abierto",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    throw new Exception("La acción OPEN no está soportada en este sistema.");
                }
            } else {
                throw new Exception("Desktop no está soportado en este sistema.");
            }
            
        } catch (Exception e) {
            // Si falla Desktop, intentar métodos alternativos
            try {
                // Método alternativo para Windows
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + rutaArchivo);
                } 
                // Método alternativo para Mac
                else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec("open " + rutaArchivo);
                } 
                // Método alternativo para Linux/Unix
                else {
                    Runtime.getRuntime().exec("xdg-open " + rutaArchivo);
                }
                
                JOptionPane.showMessageDialog(null,
                    "Reporte abierto correctamente.",
                    "Reporte Abierto",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception ex) {
                // Si todo falla, mostrar opción manual
                JOptionPane.showMessageDialog(null,
                    "No se pudo abrir el reporte automáticamente.\n\n" +
                    "Puede abrirlo manualmente en:\n" + rutaArchivo +
                    "\n\nError: " + e.getMessage(),
                    "Error al Abrir Reporte",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // Clase interna para el manejo del fondo
    class BackgroundEventHandlerPerdidas extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // No se agrega imagen de fondo según lo solicitado
        }
    }
}