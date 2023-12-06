package com.javieravenegas.finanzasencasalogin.models;

public class Finanzas {
    private String uid;
    private String uiduser;
    private String presupuesto;
    private String ingresos;
    private String gastos;
    private long fecha;

    public Finanzas() {
    }

    public Finanzas(String uid, String uiduser, String presupuesto, String ingresos, String gastos, long fecha) {
        this.uid = uid;
        this.uiduser = uiduser;
        this.presupuesto = presupuesto;
        this.ingresos = ingresos;
        this.gastos = gastos;
        this.fecha = fecha;
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

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getIngresos() {
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public String getGastos() {
        return gastos;
    }

    public void setGastos(String gastos) {
        this.gastos = gastos;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
