package com.javieravenegas.finanzasencasalogin;

public class DataModelF {
    private String nombre;
    private String imagenUrl;

    public DataModelF(String nombre, String imagenUrl) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
