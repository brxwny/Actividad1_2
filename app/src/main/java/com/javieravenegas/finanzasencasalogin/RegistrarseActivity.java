package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javieravenegas.finanzasencasalogin.models.Finanzas;
import com.javieravenegas.finanzasencasalogin.models.Usuario;

import java.util.UUID;

public class RegistrarseActivity extends AppCompatActivity {

    private Button addNewRegister;
    private EditText nombre, apellido, edad, correo, pass, confirmpass;
    private String genero;
    private RadioButton rbFem, rbMasc;
    private RadioGroup rgGenero;
    private CheckBox termycond;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        edad = findViewById(R.id.intEdad);
        rgGenero = findViewById(R.id.radioGroup);
        rbFem = findViewById(R.id.rbFemenino);
        rbMasc = findViewById(R.id.rbMasculino);
        correo = findViewById(R.id.txtRegisterCorreo);
        pass = findViewById(R.id.txtRegisterPass2);
        confirmpass = findViewById(R.id.txtRegisterPassConfirm);
        termycond = findViewById(R.id.ckbCondiciones);
        addNewRegister = (Button)findViewById(R.id.btnAccept);
        inicializarFirebase();

        addNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rgSelect = rgGenero.getCheckedRadioButtonId();

                if(rbFem.isChecked()){
                    genero = "Femenino";
                }else if(rbMasc.isChecked()){
                    genero = "Masculino";
                }else{
                    genero = "Otro";
                }

                try {
                    if(nombre.getText().toString().equals("")|| apellido.getText().toString().equals("")|| edad.getText().toString().equals("")|| correo.getText().toString().equals("")||pass.getText().toString().equals("")||confirmpass.getText().toString().equals("")|| rgSelect == -1){
                        Toast.makeText(RegistrarseActivity.this, "Complete todos los datos", Toast.LENGTH_LONG).show();
                    } else if (!termycond.isChecked()) {
                        Toast.makeText(RegistrarseActivity.this, "Acepte los términos y condiciones", Toast.LENGTH_LONG).show();
                    } else if (pass.getText().toString().equals(confirmpass.getText().toString())) {

                        Usuario u = new Usuario();
                        u.setUid(UUID.randomUUID().toString());
                        u.setNombre(nombre.getText().toString().trim());
                        u.setApellido(apellido.getText().toString().trim());
                        u.setEdad(edad.getText().toString().trim());
                        u.setGenero(genero);
                        u.setCorreo(correo.getText().toString().trim());
                        u.setPass(pass.getText().toString().trim());

                        databaseReference.child("Usuario").child(u.getUid()).setValue(u);

                        Finanzas f = new Finanzas();
                        f.setUid(UUID.randomUUID().toString());
                        f.setUiduser(u.getUid());
                        f.setPresupuesto("");
                        f.setIngresos("");
                        f.setGastos("");
                        f.setFecha(System.currentTimeMillis());
                        databaseReference.child("Finanzas").child(f.getUiduser()).setValue(f);

                        Intent i = new Intent(RegistrarseActivity.this, AddRegisterActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(RegistrarseActivity.this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(RegistrarseActivity.this, "Algo salió mal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}