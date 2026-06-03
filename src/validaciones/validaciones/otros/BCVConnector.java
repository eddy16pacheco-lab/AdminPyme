package validaciones.validaciones.otros;

import consultas_bd.sql_consulta;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.sql.SQLException;
public class BCVConnector extends sql_consulta{

       {
        disableSSLVerification();
    }
    
    private void disableSSLVerification() {
        try {
            // Crear un trust manager que no valide certificados
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };

            // Instalar el trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Crear hostname verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private double seleccionar_ultimo_valor(){
double valor=0;
query="SELECT valor FROM tasa_dolar ORDER BY id_tasa_dolar DESC";    
try {
    st=con.EstablecerConexion().createStatement();
    rs=st.executeQuery(query);
    if(rs.next()){
    valor=rs.getDouble(1);
    }
    } catch (Exception e) {
    }

return valor;
}
private void registrar_valor(double valor){
  query="INSERT INTO tasa_dolar(valor,fecha) VALUES(?,CURDATE())";
    try {
        ps=con.EstablecerConexion().prepareCall(query);
        ps.setDouble(1, valor);
        ps.execute();
    } catch (SQLException e) {
        
        if(e.getErrorCode()==1644){
        }else{
        System.out.println(""+e);
        }
    }
}
    public double getDollarRate() {
        try {
        Document doc = Jsoup.connect("https://www.bcv.org.ve")
                .timeout(15000)
                .userAgent("Mozilla/5.0")
                .get();
            
            Element dollarElement = doc.select("div#dolar .centrado strong").first();
            String rateText = dollarElement.text().replace(",", ".").trim();
            registrar_valor(Double.parseDouble(rateText));
            return Double.parseDouble(rateText);
            
        } catch (Exception e) {
            System.out.println(""+e);
        }
           return seleccionar_ultimo_valor();
    }
    
}
