package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AddCat extends AppCompatActivity {

    private Button finalizar;
    private EditText nomCat;
    private Button tomarFoto;
    final int CAPTURA_IMAGEN = 1;

    private String nombreimg;

    private ImageView imgAddCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);

        tomarFoto = (Button) findViewById(R.id.btn_TomarFoto);
        imgAddCat = (ImageView) findViewById(R.id.imgNewCat);
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURA_IMAGEN);
            }
        });

        finalizar = (Button) findViewById(R.id.btn_FinalizarAddCat);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarAddCat();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURA_IMAGEN && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmapA = (Bitmap) extras.get("data");
            imgAddCat.setImageBitmap(bitmapA);
            try{
                FileOutputStream fos = openFileOutput(crearNombreFoto(), Context.MODE_PRIVATE);
                bitmapA.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }catch (Exception e){
                Toast.makeText(AddCat.this, "No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String crearNombreFoto(){
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        nombreimg = fecha + ".jpg";
        return nombreimg;
    }

    private void finalizarAddCat(){
        nomCat = (EditText) findViewById(R.id.txtNomAddCat);
        String nombreCat = nomCat.getText().toString().trim();

        MyData.catFinCvArray = Arrays.copyOf(MyData.catFinCvArray, MyData.catFinCvArray.length + 1);
        MyData.imgCatFinArray = Arrays.copyOf(MyData.imgCatFinArray, MyData.imgCatFinArray.length + 1);
        MyData.catFinCvArray[MyData.catFinCvArray.length - 1] = nombreCat;
        MyData.imgCatFinArray[MyData.imgCatFinArray.length - 1] = getResources().getIdentifier(nombreimg, "drawable", getPackageName());

        //getFilesDir() + "/" + nombreimg

        Intent in = new Intent(AddCat.this, InicioActivity.class);
        startActivity(in);
    }
}