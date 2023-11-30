package com.javieravenegas.finanzasencasalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javieravenegas.finanzasencasalogin.models.Usuario;

public class Configuraciones extends AppCompatActivity {

    private EditText txtNombreConfig, txtApellidoConfig, intEdadConfig, txtCorreoConfig, txtPassConfig;
    private RadioButton rbFemenino, rbMasculino, rbOtro;
    private Button btnSaveCambios, btnClose;
    private String usuarioUid;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        btnSaveCambios = findViewById(R.id.btnSaveConfig);
        btnClose = findViewById(R.id.btnCerrarSesion);
        txtNombreConfig = findViewById(R.id.txtNombreConfig);
        txtApellidoConfig = findViewById(R.id.txtApellidoConfig);
        intEdadConfig = findViewById(R.id.intEdadConfig);
        txtCorreoConfig = findViewById(R.id.txtCorreoConfig);
        txtPassConfig = findViewById(R.id.txtPassConfig);
        rbFemenino = findViewById(R.id.rbFemeninoConfig);
        rbMasculino = findViewById(R.id.rbMasculinoConfig);
        rbOtro = findViewById(R.id.rbOtroConfig);
        capturarId();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuario").child(usuarioUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    txtNombreConfig.setText(usuario.getNombre());
                    txtApellidoConfig.setText(usuario.getApellido());
                    intEdadConfig.setText(usuario.getEdad());
                    txtCorreoConfig.setText(usuario.getCorreo());
                    txtPassConfig.setText(usuario.getPass());

                    switch (usuario.getGenero()) {
                        case "Femenino":
                            rbFemenino.setChecked(true);
                            break;
                        case "Masculino":
                            rbMasculino.setChecked(true);
                            break;
                        case "Otro":
                            rbOtro.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Configuraciones.this, "Hubo un error al obtener la información", Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDatos();
                Intent intent = new Intent(Configuraciones.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizarDatos(){
        try {
            String nombre = txtNombreConfig.getText().toString();
            String apellido = txtApellidoConfig.getText().toString();
            String edad = intEdadConfig.getText().toString();
            String correo = txtCorreoConfig.getText().toString();
            String pass = txtPassConfig.getText().toString();

            String genero = "";
            if (rbFemenino.isChecked()) {
                genero = "Femenino";
            } else if (rbMasculino.isChecked()) {
                genero = "Masculino";
            } else if (rbOtro.isChecked()) {
                genero = "Otro";
            }

            databaseReference.child("Usuario").child(usuarioUid).child("nombre").setValue(nombre);
            databaseReference.child("Usuario").child(usuarioUid).child("apellido").setValue(apellido);
            databaseReference.child("Usuario").child(usuarioUid).child("edad").setValue(edad);
            databaseReference.child("Usuario").child(usuarioUid).child("correo").setValue(correo);
            databaseReference.child("Usuario").child(usuarioUid).child("pass").setValue(pass);
            databaseReference.child("Usuario").child(usuarioUid).child("genero").setValue(genero);

            Toast.makeText(Configuraciones.this, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(Configuraciones.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarDatos(){
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(Configuraciones.this, "Sesión cerrada exitosamente", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(Configuraciones.this, "Error al cerrar sesión", Toast.LENGTH_LONG).show();
        }
    }

    private void capturarId(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        usuarioUid = sharedPreferences.getString("uid", "");
    }
}