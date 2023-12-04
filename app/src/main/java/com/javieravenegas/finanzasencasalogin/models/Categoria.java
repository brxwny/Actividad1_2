package com.javieravenegas.finanzasencasalogin.models;

public class Categoria {
    private String uid;
    private String uiduser;
    private String nombre;
    private String descripcion;
    private String monto;
    private String photo;

    public Categoria() {
    }
    public Categoria(String uid, String uiduser, String nombre, String descripcion, String monto, String photo) {
        this.uid = uid;
        this.uiduser = uiduser;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
