package com.javieravenegas.finanzasencasalogin;

public class DataModelG {
    private String nombre;
    private String monto;

    public DataModelG(String nombre, String monto) {
        this.nombre = nombre;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
