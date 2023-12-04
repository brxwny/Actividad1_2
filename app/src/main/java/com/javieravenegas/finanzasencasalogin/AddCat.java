package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.javieravenegas.finanzasencasalogin.models.Categoria;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class AddCat extends AppCompatActivity {

    private Button finalizar, tomarFoto, elegirFoto;
    private EditText nomCat, desCat, montoAsig;
    final int CAPTURA_IMAGEN = 1;
    private String nombreimg;
    private ImageView imgAddCat;
    private StorageReference storageReference;
    private String storage_path = "cat/*";
    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;
    private Uri image_url;
    private String photo = "photo";
    private String uidcat, uiduser;
    private String download_uri = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);

        tomarFoto = (Button) findViewById(R.id.btn_TomarFoto);
        elegirFoto = findViewById(R.id.btn_ElegirFoto);
        nomCat = (EditText) findViewById(R.id.txtNomAddCat);
        desCat = findViewById(R.id.txtDesAddCat);
        montoAsig = findViewById(R.id.intMontoAddCat);
        imgAddCat = (ImageView) findViewById(R.id.imgNewCat);
        uidcat = UUID.randomUUID().toString();
        capturarId();
        inicializarFirebase();

        storageReference = FirebaseStorage.getInstance().getReference();
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURA_IMAGEN);
            }
        });

        elegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFoto();
            }
        });

        finalizar = (Button) findViewById(R.id.btn_FinalizarAddCat);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(nomCat.getText().toString().equals("")||desCat.getText().toString().equals("")||montoAsig.getText().toString().equals("")){
                        Toast.makeText(AddCat.this, "Complete todos los datos", Toast.LENGTH_LONG).show();
                    } else if (download_uri.equals("")) {
                        Toast.makeText(AddCat.this, "Seleccione una imagen", Toast.LENGTH_LONG).show();
                    }else {
                        finalizarAddCat();
                    }
                }catch (Exception e){
                    Toast.makeText(AddCat.this, "Error al crear categoría", Toast.LENGTH_LONG).show();
                }
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
                subirPhoto(Uri.fromFile(getFileStreamPath(nombreimg)));
            }catch (Exception e){
                Toast.makeText(AddCat.this, "No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == COD_SEL_IMAGE && resultCode == RESULT_OK ){
            try{
                image_url = data.getData();
                subirPhoto(image_url);
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
        String nombreCat = nomCat.getText().toString().trim();

        Categoria c = new Categoria();
        c.setUid(uidcat);
        c.setUiduser(uiduser);
        c.setNombre(nombreCat);
        c.setDescripcion(desCat.getText().toString().trim());
        c.setMonto(montoAsig.getText().toString().trim());
        c.setPhoto(download_uri);

        databaseReference.child("Categoria").child(c.getUid()).setValue(c);

        //MyData.catFinCvArray = Arrays.copyOf(MyData.catFinCvArray, MyData.catFinCvArray.length + 1);
        //MyData.imgCatFinArray = Arrays.copyOf(MyData.imgCatFinArray, MyData.imgCatFinArray.length + 1);
        //MyData.catFinCvArray[MyData.catFinCvArray.length - 1] = nombreCat;
        //MyData.imgCatFinArray[MyData.imgCatFinArray.length - 1] = getResources().getIdentifier(nombreimg, "drawable", getPackageName());

        Intent in = new Intent(AddCat.this, TusFinanzas.class);
        startActivity(in);
    }

    private void selectFoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, COD_SEL_IMAGE);
    }

    private void subirPhoto(Uri image_url){
        String rute_storage_photo = storage_path + "" + photo + "" + uiduser + "" + uidcat;
        StorageReference reference = storageReference.child(rute_storage_photo);
        reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        download_uri = uri.toString();
                        Toast.makeText(AddCat.this, "Imagen agregada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void capturarId(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        uiduser = sharedPreferences.getString("uid", "");
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}