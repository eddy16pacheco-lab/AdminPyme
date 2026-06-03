/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package informe_reporte;

import java.util.Date;
import java.util.List;

public class FacturaData {
         private int numeroFactura;
        private Date fecha;
        private String cliente;
        private int cedula;
        private String telefono;
        private String vendedor;
        private List<ProductoFactura> productos;
        private List<PagoFactura> pagos;
        private double totalAPagar;
        private double totalPagado;
        private double saldoPendiente;
        
        // Getters y Setters
        public int getNumeroFactura() { return numeroFactura; }
        public void setNumeroFactura(int numeroFactura) { this.numeroFactura = numeroFactura; }
        
        public Date getFecha() { return fecha; }
        public void setFecha(Date fecha) { this.fecha = fecha; }
        
        public String getCliente() { return cliente; }
        public void setCliente(String cliente) { this.cliente = cliente; }
        
        public int getCedula() { return cedula; }
        public void setCedula(int cedula) { this.cedula = cedula; }
        
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        
        public String getVendedor() { return vendedor; }
        public void setVendedor(String vendedor) { this.vendedor = vendedor; }
        
        public List<ProductoFactura> getProductos() { return productos; }
        public void setProductos(List<ProductoFactura> productos) { this.productos = productos; }
        
        public List<PagoFactura> getPagos() { return pagos; }
        public void setPagos(List<PagoFactura> pagos) { this.pagos = pagos; }
        
        public double getTotalAPagar() { return totalAPagar; }
        public void setTotalAPagar(double totalAPagar) { this.totalAPagar = totalAPagar; }
        
        public double getTotalPagado() { return totalPagado; }
        public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }
        
        public double getSaldoPendiente() { return saldoPendiente; }
        public void setSaldoPendiente(double saldoPendiente) { this.saldoPendiente = saldoPendiente; }
   
}
