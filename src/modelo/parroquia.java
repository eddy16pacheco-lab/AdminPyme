package modelo;

public class parroquia {
private int id_parroquia;
private String nombre_parroquia;
private municipio municipio;

    public int getId_parroquia() {
        return id_parroquia;
    }

    public void setId_parroquia(int id_parroquia) {
        this.id_parroquia = id_parroquia;
    }


    public String getNombre_parroquia() {
        return nombre_parroquia;
    }

    public void setNombre_parroquia(String nombre_parroquia) {
        this.nombre_parroquia = nombre_parroquia;
    }

    public municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(municipio municipio) {
        this.municipio = municipio;
    }


}
