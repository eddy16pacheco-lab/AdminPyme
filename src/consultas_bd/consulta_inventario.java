package consultas_bd;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.categoria;
import modelo.inventario;
import modelo.modelo_inventario;
import modelo.producto;

public class consulta_inventario extends sql_consulta {

    public consulta_producto con_producto = new consulta_producto();
    public consulta_movimiento_inventario con_movi = new consulta_movimiento_inventario();

    public producto cargar_producto(int id) {

        query = "SELECT * FROM cargar_producto c WHERE c.producto_id='" + id + "'";
        producto producto = new producto();
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                producto.setId_producto(rs.getInt(1));
                producto.setNombre_prodcuto(rs.getString(2));
                categoria c = new categoria();
                c.setTipo_categoria(rs.getString(3));
                producto.setCategoria(c);
                producto.setPrecio_unitario(rs.getDouble(4));
                producto.setPrecio_venta(rs.getDouble(5));
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al cargar el producto: " + e);
        }
        return producto;
    }

    public void update_a_la_hora_de_insertar(inventario i) {
        query = "UPDATE inventario SET cantidad = cantidad + ? WHERE producto_id = ? AND cantidad >= ?";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setInt(1, i.getCantidad());
            ps.setInt(2, i.getProducto().getId_producto());
            ps.setInt(3, i.getCantidad());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Regstro exitoso");
            con.CerrarConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al nsertar para actualizar en el inventario" + e);
        }
    }

    public void select_inventario_producto(JList tabla, String busqueda) {
        DefaultListModel L_modelo = new DefaultListModel();
        String datos;
        query = "SELECT DISTINCT CONCAT(p.producto_id,' ',p.nombre_producto,' ',p.descripcion) FROM producto p,categoria c WHERE p.id_categoria=c.id_categoria AND (p.nombre_producto LIKE '" + busqueda + "%' OR p.producto_id LIKE '" + busqueda + "%') ORDER BY p.producto_id ASC;;";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos = rs.getString(1);
                L_modelo.addElement(datos);
            }
            tabla.setModel(L_modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }

    public void inventario_productos(JTable tabla, int respuesta, String busqueda, String categoria) {
        String[] columns = {"Código", "Nombre", "Categoría", "Precio Com", "Precop Ven", "Stock", "Stock Mín."};
        DefaultTableModel modelo = new DefaultTableModel(columns, 0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        if (respuesta == 1) {
            query = "SELECT * FROM inventario_productos_";
        }
        if (respuesta == 2) {
            query = "SELECT * FROM inventario_productos_ i WHERE i.cantidad  < i.cantidad_min_stock ";
        }
        if (respuesta == 3) {
            query = "SELECT * FROM inventario_productos_ i WHERE i.Producto LIKE '%" + busqueda + "%'";
        }
        if (respuesta == 4) {
            query = "SELECT * FROM inventario_productos_ i WHERE i.tipo_categoria='" + categoria + "'";
        }
        if (respuesta == 5) {
            query = "SELECT * FROM inventario_productos_ i WHERE i.Producto LIKE '%" + busqueda + "%' i.tipo_categoria='" + categoria + "'";
        }
        try {
            String[] datos = new String[7];

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
                modelo.addRow(datos);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar inventario_productos: " + e);
        }
    }

    public void productos_mas_vendidos(JTable tabla) {
        String[] columns = {"C°", "Producto", "Ventas Totales", "Categoría", "Ingresos"};
        DefaultTableModel Tabla_modelo = new DefaultTableModel(columns, 0);
        tabla.setModel(Tabla_modelo);
        query = "SELECT p.producto_id AS 'C°',p.nombre_producto AS 'Producto',SUM(dv.cantidad_vendida) AS 'Ventas Totales',c.tipo_categoria AS 'Categoría',SUM(dv.monto_venta) AS 'Ingresos' FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id_categoria INNER JOIN detalle_venta dv ON p.producto_id = dv.producto_id INNER JOIN venta v ON dv.id_venta = v.id_venta WHERE dv.estado_venta = 'Aprobado'GROUP BY p.producto_id, p.nombre_producto, c.tipo_categoria ORDER BY SUM(dv.cantidad_vendida) DESC;";
        String[] datos = new String[5];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                Tabla_modelo.addRow(datos);
            }
            tabla.setModel(Tabla_modelo);
        } catch (SQLException e) {
            System.out.println("Error al cargar los productos mas vendidos" + e);
        }
    }

    public void stock_bajo(JTable tabla) {
        String[] columns = {"Nombre", "Categoría", "Stock", "Stock Mín."};
        DefaultTableModel modelo = new DefaultTableModel(columns, 0);
        modelo.setRowCount(0);
        tabla.setModel(modelo);
        query = "SELECT * FROM inventario_productos_ i WHERE i.cantidad <= i.cantidad_min_stock";
        String[] datos = new String[7];
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(7);
                modelo.addRow(datos);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar inventario_productos: " + e);
        }
    }

    public void buscar_categoria_inventario_productos(JTable tabla, String categoria) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        query = "SELECT * FROM inventario_productos_ i WHERE i.tipo_categoria='" + categoria + "'";
        String[] datos = new String[7];
        try {
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
                modelo.addRow(datos);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar inventario_productos: " + e);
        }
    }

    public void insert_table_inventario(inventario i) {
        query = "INSERT INTO inventario(id_usuario,producto_id,cantidad,cantidad_min_stock,cantidad_max_stock,fecha_actualizacion,tiempo_actualizacion) VALUES(?,?,?,?,?,NOW(),CURTIME());";
        try {
            ps = con.EstablecerConexion().prepareCall(query);
            ps.setInt(1, i.getUsuario().getId_persona());
            ps.setInt(2, i.getProducto().getId_producto());
            ps.setInt(3, i.getCantidad());
            ps.setInt(4, i.getStock_min());
            ps.setInt(5, i.getStock_max());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Producto registrado");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado" + e);
        }
    }

    public void buscar_producto_inventario(JList tabla, String busqueda) {
        DefaultListModel modelo = new DefaultListModel<>();
        tabla.setModel(modelo);
        String datos;
        query = "SELECT DISTINCT CONCAT('C°',p.producto_id,' Nombre: ',p.nombre_producto,' ',p.descripcion) FROM producto p WHERE (p.nombre_producto LIKE '" + busqueda + "%' OR p.producto_id LIKE '" + busqueda + "%');";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                datos = rs.getString(1);
                modelo.addElement(datos);
            }
            tabla.setModel(modelo);
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "a ocurrido un error inseperado: " + e);
        }
    }

    public boolean verificar_producto(int id) {
        query = "SELECT producto_id FROM inventario WHERE producto_id='" + id + "'";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar producto: " + e);
        }
        return false;
    }

    public void delete_inventario(producto p) {
        query = "DELETE  FROM inventario WHERE producto_id=?";
        try {
            cs = con.EstablecerConexion().prepareCall(query);
            cs.setInt(1, p.getId_producto());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Producto eliminado");
            con.CerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error inseperado: " + e);
        }
    }

    public ArrayList<modelo_inventario> productos_con_stock_minimo() {
        ArrayList<modelo_inventario> lista_inventario = new ArrayList<>();
        query = "SELECT * FROM productos_stock_min";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                modelo_inventario inventario = new modelo_inventario();
                producto producto = new producto();
                producto.setId_producto(rs.getInt(1));
                producto.setNombre_prodcuto(rs.getString(2));
                producto.setDescripcion(rs.getString(3));
                categoria categoria = new categoria();
                categoria.setTipo_categoria(rs.getString(4));
                producto.setCategoria(categoria);
                producto.setPrecio_venta(rs.getDouble(5));
                inventario.setLista_producto(producto);
                inventario.setCantidad(rs.getInt(6));
                inventario.setStock_min(7);
                lista_inventario.add(inventario);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar los productos con stokc minimo: " + e);
        }
        return lista_inventario;
    }

    public String[] cargar_resumen_inventario() {
        String[] resumen = new String[4];
        query = "SELECT * FROM vista_resumen_inventario";
        try {
            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                resumen[0] = "" + rs.getInt(1);
                resumen[1] = rs.getString(2);
                resumen[2] = rs.getString(3);
                resumen[3] = rs.getString(4);
            }
            con.CerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al cargar resumen: " + e);
        }

        return resumen;
    }

    public int validarCantidadInventario(int productoId, int cantidadARegistrar) {
        int total_def = 0;

        try {
            // Calcular el total máximo permitido (suma de todas las compras)
            query = "SELECT SUM(dodc.cantidad_empaquetada * dodc.cantidad_entregada) as total_comprado "
                    + "FROM detalle_orden_de_compra dodc "
                    + "WHERE dodc.producto_id = '" + productoId + "'";

            st = con.EstablecerConexion().createStatement();
            rs = st.executeQuery(query);

            int totalComprado = 0;
            if (rs.next()) {
                totalComprado = rs.getInt("total_comprado");
                // Si es NULL (no hay compras), se considera 0
                if (rs.wasNull()) {
                    totalComprado = -1;
                }
            }

            // Validar que no exceda el total comprado
            if (cantidadARegistrar <= totalComprado) {
                total_def = totalComprado - cantidadARegistrar;
            } else {
            }

            con.CerrarConexion();
        } catch (SQLException e) {
        }

        return total_def;
    }
    

    
    
    
    public void actualizarStockMinimo(int idInventario) {
        // Solicitar el nuevo valor del stock mínimo
        String input = JOptionPane.showInputDialog(
            null,
            "Ingrese el nuevo valor para el stock mínimo:",
            "Actualizar Stock Mínimo",
            JOptionPane.QUESTION_MESSAGE
        );
        
        // Si el usuario cancela o cierra la ventana
        if (input == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return;
        }
        
        // Validar que el input no esté vacío
        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Debe ingresar un valor numérico.",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Validar que sea un número entero positivo
        try {
            int nuevoStockMinimo = Integer.parseInt(input.trim());
            
            if (nuevoStockMinimo < 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "El stock mínimo no puede ser negativo.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Actualizar en la base de datos
            boolean actualizado = actualizarStockMinimoBD(idInventario, nuevoStockMinimo);
            
            if (actualizado) {
                JOptionPane.showMessageDialog(
                    null,
                    "Stock mínimo actualizado exitosamente a: " + nuevoStockMinimo,
                    "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo actualizar el stock mínimo.",
                    "Error en la Base de Datos",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Por favor ingrese un número válido (entero).",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Método mejorado con validación más estricta usando expresión regular
     */
    public void actualizarStockMinimoValidado(int idInventario) {
        boolean entradaValida = false;
        
        while (!entradaValida) {
            String input = JOptionPane.showInputDialog(
                null,
                "Ingrese el nuevo valor para el stock mínimo (solo números enteros positivos):",
                "Actualizar Stock Mínimo",
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Si el usuario cancela
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
                return;
            }
            
            // Validar con expresión regular para números enteros positivos
            if (input.matches("\\d+")) {
                try {
                    int nuevoStockMinimo = Integer.parseInt(input);
                    
                    // Validar rango razonable (opcional)
                    if (nuevoStockMinimo > 1000) {
                        int confirmar = JOptionPane.showConfirmDialog(
                            null,
                            "El valor ingresado (" + nuevoStockMinimo + ") parece alto.\n" +
                            "¿Está seguro de continuar?",
                            "Confirmar Valor Alto",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                        );
                        
                        if (confirmar != JOptionPane.YES_OPTION) {
                            continue; // Volver a solicitar el valor
                        }
                    }
                    
                    // Actualizar en la base de datos
                    boolean actualizado = actualizarStockMinimoBD(idInventario, nuevoStockMinimo);
                    
                    if (actualizado) {
                        JOptionPane.showMessageDialog(
                            null,
                            "✓ Stock mínimo actualizado exitosamente a: " + nuevoStockMinimo,
                            "Actualización Exitosa",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        entradaValida = true;
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "✗ No se pudo actualizar el stock mínimo.\n" +
                            "Verifique que el ID de inventario exista.",
                            "Error en la Base de Datos",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                } catch (NumberFormatException e) {
                    // Esto no debería ocurrir si pasó la validación regex, pero por seguridad
                    JOptionPane.showMessageDialog(
                        null,
                        "Error interno al procesar el número.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Entrada inválida. Solo se permiten números enteros positivos.\n" +
                    "Ejemplos válidos: 5, 10, 25",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Método que actualiza en la base de datos
     */
    public boolean actualizarStockMinimoBD(int idInventario, int nuevoStockMinimo) {
        query = "UPDATE inventario SET cantidad_min_stock = ? WHERE producto_id = ?";
        
        try 
            {
            ps = con.EstablecerConexion().prepareStatement(query);
            ps.setInt(1, nuevoStockMinimo);
            ps.setInt(2, idInventario);
            
            int filasAfectadas = ps.executeUpdate();
            
            // Devolver true si se actualizó al menos una fila
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Error de base de datos: " + e.getMessage(),
                "Error SQL",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
}