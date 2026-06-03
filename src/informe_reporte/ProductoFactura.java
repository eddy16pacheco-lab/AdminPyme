/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package informe_reporte;

/**
 *
 * @author Eddy
 */
public class ProductoFactura {
         private String nombre;
        private String descripcion;
        private int cantidad;
        private double precioUnitario;
        private double subtotal;
        private String tipoVenta;
        private String estado;
        private double totalProducto;
        
        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        
        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
        
        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
        
        public String getTipoVenta() { return tipoVenta; }
        public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
        
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        
        public double getTotalProducto() { return totalProducto; }
        public void setTotalProducto(double totalProducto) { this.totalProducto = totalProducto; }
   
}
