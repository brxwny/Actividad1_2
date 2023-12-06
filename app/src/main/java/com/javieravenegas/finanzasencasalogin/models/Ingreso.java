package com.javieravenegas.finanzasencasalogin.models;

public class Ingreso {
    private String uid;
    private String uiduser;
    private long fecha;
    private String nombre;
    private String descripcion;
    private String monto;
    private String uidcat;

    public Ingreso() {
    }

    public Ingreso(String uid, String uiduser, long fecha, String nombre, String descripcion, String monto, String uidcat) {
        this.uid = uid;
        this.uiduser = uiduser;
        this.fecha = fecha;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.uidcat = uidcat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUiduser() {
        return uiduser;
    }

    public void setUiduser(String uiduser) {
        this.uiduser = uiduser;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUidcat() {
        return uidcat;
    }

    public void setUidcat(String uidcat) {
        this.uidcat = uidcat;
    }
}
