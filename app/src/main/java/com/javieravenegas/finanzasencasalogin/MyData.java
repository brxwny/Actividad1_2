package com.javieravenegas.finanzasencasalogin;

public class MyData {
    static String[] nombreInArray = {"Tus finanzas", "Tu ahorro", "Tus gastos", "Tus ingresos", "Configuraciones"};
    static Integer[] drawableInArray = {R.drawable.finanzas_img, R.drawable.ahorro_img, R.drawable.gastos_img, R.drawable.ingresos_img, R.drawable.config_img};

    static Class<?>[] interactInArray = {TusFinanzas.class, AddInversion.class, TusGastos.class, TusIngresos.class, Configuraciones.class};

    //static  String[] catFinCvArray = {"Libros", "Agua", "Gas", "Luz", "Streaming", "Vegetales"};

    //static Integer[] imgCatFinArray = {R.drawable.libros, R.drawable.agua, R.drawable.gas, R.drawable.luz, R.drawable.streaming, R.drawable.vegetales};
}
