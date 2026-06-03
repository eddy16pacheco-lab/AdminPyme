package informe_reporte;

public class PagoFactura {
      private String tipoPago;
        private String referencia;
        private double monto;
        
        // Getters y Setters
        public String getTipoPago() { return tipoPago; }
        public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }
        
        public String getReferencia() { return referencia; }
        public void setReferencia(String referencia) { this.referencia = referencia; }
        
        public double getMonto() { return monto; }
        public void setMonto(double monto) { this.monto = monto; }
      
}
