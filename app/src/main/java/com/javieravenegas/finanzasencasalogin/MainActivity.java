package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javieravenegas.finanzasencasalogin.models.Usuario;

public class MainActivity extends AppCompatActivity {

    private ImageButton twt, google, face;
    private EditText correo, pass;
    private Button registrarse, iniciar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrarse = (Button) findViewById(R.id.btnRegistrarse);
        iniciar = (Button)findViewById(R.id.btnLogin);
        correo = (EditText)findViewById(R.id.txtCorreo);
        pass = findViewById(R.id.txtPassword);
        twt = findViewById(R.id.btntwt);
        google = findViewById(R.id.btngoogle);
        face = findViewById(R.id.btnface);

        inicializarFirebase();

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrarseActivity.class);
                startActivity(i);
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                try{
                    if(correo.getText().toString().equals("")||pass.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Ingrese sus credenciales", Toast.LENGTH_LONG).show();
                    }else{
                        verificarCredenciales(correo.getText().toString().trim(), pass.getText().toString().trim());
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Algo salió mal", Toast.LENGTH_LONG).show();
                }
            }
        });

        twt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddInversion.class);
                startActivity(i);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddInversion.class);
                startActivity(i);
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddInversion.class);
                startActivity(i);
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void verificarCredenciales(final String correo, final String pass) {
        databaseReference.child("Usuario").orderByChild("correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = snapshot.getValue(Usuario.class);
                        if (usuario != null && usuario.getPass().equals(pass)) {
                            String key = usuario.getUid();
                            capturarId(key);

                            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                            intent.putExtra("correo", correo);
                            startActivity(intent);
                            return;
                        }
                    }
                    Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error en la base de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void capturarId(String uid){
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid", uid);
            editor.apply();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Error al capturar su id de usuario", Toast.LENGTH_LONG).show();
        }
    }
}