package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddCat extends AppCompatActivity {

    private Button tomarFoto;
    final int CAPTURA_IMAGEN = 1;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURA_IMAGEN && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmapA = (Bitmap) extras.get("data");
            imgAddCat.setImageBitmap(bitmapA);
        }
    }
}