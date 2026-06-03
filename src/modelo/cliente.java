package modelo;

public class cliente extends persona {
    private int id_cliente;
    private String telefeno;
    private parroquia ubicacion;
private String estado;
    public String getTelefeno() {
        return telefeno;
    }

    public void setTelefeno(String telefeno) {
        this.telefeno = telefeno;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public parroquia getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(parroquia ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

 
    
}
