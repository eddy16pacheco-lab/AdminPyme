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

public class ReporteComprasPDFProfesional extends sql_consulta {
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
    
    public void generarReporteCompras() {
        String filePath = null; // Variable para almacenar la ruta del archivo
        
        try {
            // 1. Diálogo para seleccionar rango de fechas
            Object[] fechas = mostrarDialogoFechas();
            if (fechas == null) return;
            
            String fechaInicio = (String) fechas[0];
            String fechaFin = (String) fechas[1];
            
            // 2. Obtener datos de la base de datos
            List<CompraData> compras = obtenerComprasPorFecha(fechaInicio, fechaFin);
            if (compras.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron compras en el rango de fechas especificado");
                return;
            }
            
            // 3. Diálogo para guardar PDF
            filePath = mostrarDialogoGuardarPDF(fechaInicio, fechaFin);
            if (filePath == null) return;
            
            // 4. Crear el PDF profesional
            String tituloReporte = "Reporte de Compras " + fechaInicio + " al " + fechaFin;
            crearPDFComprasProfesional(filePath, tituloReporte, compras, fechaInicio, fechaFin);
            
            // 5. Preguntar si desea abrir el reporte
            abrirReporteGenerado(filePath, compras.size());
            
        } catch (Exception e) {
            manejarErrorGeneracion(e);
        }
    }

    // Clase interna para almacenar los datos de compra con tipos correctos
    private class CompraData {
        String codigoCompra;
        String proveedor;
        String encargado;
        String codigoProducto;
        String producto;
        double precioUnitario;
        String infoEntrega;
        int totalUnidades;
        double totalCompra;
        String fechaCompra;
        
        CompraData(String codigoCompra, String proveedor, String encargado, String codigoProducto, 
                  String producto, double precioUnitario, String infoEntrega, int totalUnidades, 
                  double totalCompra, String fechaCompra) {
            this.codigoCompra = codigoCompra;
            this.proveedor = proveedor;
            this.encargado = encargado;
            this.codigoProducto = codigoProducto;
            this.producto = producto;
            this.precioUnitario = precioUnitario;
            this.infoEntrega = infoEntrega;
            this.totalUnidades = totalUnidades;
            this.totalCompra = totalCompra;
            this.fechaCompra = fechaCompra;
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
     * 2. Obtener datos de la vista reporte_de_compra con tipos correctos
     */
    private List<CompraData> obtenerComprasPorFecha(String fechaInicio, String fechaFin) {
        List<CompraData> compras = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado: " + e.getMessage());
        }
        
        // Consulta a la vista reporte_de_compra
        query = "SELECT * FROM reporte_de_compra WHERE `Fecha de compra` BETWEEN ? AND ? ORDER BY `Fecha de compra` DESC";
        
        try {
            ps = con.EstablecerConexion().prepareStatement(query);
            
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String codigoCompra = String.valueOf(rs.getInt("Codigo de compra"));
                String proveedor = rs.getString("Proveedor");
                String encargado = rs.getString("Encargado");
                String codigoProducto = String.valueOf(rs.getInt("Codigo de producto"));
                String producto = rs.getString("Producto");
                double precioUnitario = rs.getDouble("Precio unitario");
                String infoEntrega = rs.getString("Informacion de entrega");
                int totalUnidades = rs.getInt("Total de unidades");
                double totalCompra = rs.getDouble("Total de la compra");
                String fechaCompra = "N/A";
                
                if (rs.getDate("Fecha de compra") != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    fechaCompra = dateFormat.format(rs.getDate("Fecha de compra"));
                }
                
                CompraData compra = new CompraData(
                    codigoCompra, proveedor, encargado, codigoProducto, producto, 
                    precioUnitario, infoEntrega, totalUnidades, totalCompra, fechaCompra
                );
                compras.add(compra);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener compras: " + e.getMessage(), e);
        }
        
        return compras;
    }

    /**
     * 3. Diálogo para guardar PDF
     */
    private String mostrarDialogoGuardarPDF(String fechaInicio, String fechaFin) {
        String tituloReporte = "Reporte de Compras " + fechaInicio + " al " + fechaFin;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte PDF de Compras");
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
     * 4. Crear el PDF profesional de compras
     */
    private void crearPDFComprasProfesional(String filePath, String tituloReporte, 
                                          List<CompraData> datosCompras, String fechaInicio, String fechaFin) {
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(40, 40, 80, 40);
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new BackgroundEventHandlerCompras());
            
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
            
            Paragraph estadisticas = new Paragraph("TOTAL DE COMPRAS REGISTRADAS: " + datosCompras.size(), fontNormal);
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
            
            // Tabla de compras
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(20f);
            
            float[] anchosColumnas = {6f, 10f, 8f, 6f, 12f, 8f, 12f, 8f, 10f, 8f};
            table.setWidths(anchosColumnas);
            
            String[] columnas = {
                "CÓD. COMPRA", "PROVEEDOR", "ENCARGADO", "CÓD. PRODUCTO", 
                "PRODUCTO", "PRECIO UNIT.", "INFO. ENTREGA", "TOTAL UNID.", 
                "TOTAL COMPRA", "FECHA COMPRA"
            };
            
            BaseColor colorHeader = new BaseColor(70, 130, 180); // Azul steel
            for (String columna : columnas) {
                PdfPCell header = new PdfPCell(new Phrase(columna, fontHeader));
                header.setBackgroundColor(colorHeader);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setPadding(6);
                header.setBorderWidth(1);
                header.setBorderColor(BaseColor.WHITE);
                table.addCell(header);
            }
            
            double totalGeneral = 0;
            boolean filaPar = false;
            BaseColor colorFilaPar = new BaseColor(240, 240, 240);
            BaseColor colorFilaImpar = new BaseColor(255, 255, 255);
            
            for (CompraData compra : datosCompras) {
                BaseColor colorFondo = filaPar ? colorFilaPar : colorFilaImpar;
                
                // Formatear los valores para mostrar
                String precioUnitarioFormateado = String.format("%,.2f", compra.precioUnitario);
                String totalCompraFormateado = String.format("%,.2f", compra.totalCompra);
                
                // Agregar celdas a la tabla
                String[] valoresCelda = {
                    compra.codigoCompra,
                    compra.proveedor,
                    compra.encargado,
                    compra.codigoProducto,
                    compra.producto,
                    precioUnitarioFormateado,
                    compra.infoEntrega,
                    String.valueOf(compra.totalUnidades),
                    totalCompraFormateado,
                    compra.fechaCompra
                };
                
                for (int i = 0; i < valoresCelda.length; i++) {
                    String texto = valoresCelda[i] != null ? valoresCelda[i] : "N/A";
                    PdfPCell cell = new PdfPCell(new Phrase(texto, fontPequeño));
                    
                    // Alinear números a la derecha
                    if (i == 5 || i == 7 || i == 8) { // Columnas de precios y cantidades
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    
                    cell.setBackgroundColor(colorFondo);
                    cell.setPadding(4);
                    cell.setBorderWidth(0.5f);
                    cell.setBorderColor(new BaseColor(200, 200, 200));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                }
                
                // Sumar al total general - CORREGIDO: usar el valor original double
                totalGeneral += compra.totalCompra;
                
                filaPar = !filaPar;
            }
            
            document.add(table);
            
            // Resumen - Formato con 2 decimales fijos
            PdfPTable tablaResumen = new PdfPTable(2);
            tablaResumen.setWidthPercentage(50);
            tablaResumen.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaResumen.setSpacingBefore(10f);
            
            Font fontResumenTitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontResumenValor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
            
            PdfPCell cellTitulo = new PdfPCell(new Phrase("TOTAL GENERAL DE COMPRAS:", fontResumenTitulo));
            cellTitulo.setBorder(PdfPCell.NO_BORDER);
            cellTitulo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitulo.setPadding(5);
            
            // Formatear total general con 2 decimales fijos
            String totalGeneralFormateado = String.format("%,.2f", totalGeneral);
            PdfPCell cellValor = new PdfPCell(new Phrase("Bs. " + totalGeneralFormateado, fontResumenValor));
            cellValor.setBorder(PdfPCell.NO_BORDER);
            cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellValor.setPadding(5);
            cellValor.setBackgroundColor(new BaseColor(220, 220, 220));
            
            tablaResumen.addCell(cellTitulo);
            tablaResumen.addCell(cellValor);
            
            document.add(tablaResumen);
            
            // Pie de página
            Paragraph piePagina = new Paragraph("\n\n* Reporte de compras generado automáticamente por el Sistema Automatizado AdminPyme\nPara gestionar ventas*", 
                                               new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
            piePagina.setAlignment(Element.ALIGN_CENTER);
            document.add(piePagina);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear PDF de compras: " + e.getMessage(), e);
        } finally {
            document.close();
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
    private void abrirReporteGenerado(String rutaPDF, int totalCompras) {
        int opcion = JOptionPane.showConfirmDialog(null, 
            "Reporte PDF generado exitosamente:\n" + rutaPDF +
            "\n\nTotal de compras registradas: " + totalCompras +
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
    
    // Clase interna para el manejo del fondo (sin imagen de fondo)
    class BackgroundEventHandlerCompras extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // No se agrega imagen de fondo según lo solicitado
        }
    }
}