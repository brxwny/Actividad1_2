package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarseActivity extends AppCompatActivity {

    private Button addNewRegister;
    private EditText nombre, apellido, edad, correo, pass, confirmpass;
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
        correo = findViewById(R.id.txtRegisterCorreo);
        pass = findViewById(R.id.txtRegisterPass2);
        confirmpass = findViewById(R.id.txtRegisterPassConfirm);
        termycond = findViewById(R.id.ckbCondiciones);
        addNewRegister = (Button)findViewById(R.id.btnAccept);

        addNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rgSelect = rgGenero.getCheckedRadioButtonId();
                try {
                    if(nombre.getText().toString().equals("")|| apellido.getText().toString().equals("")|| edad.getText().toString().equals("")|| correo.getText().toString().equals("")||pass.getText().toString().equals("")||confirmpass.getText().toString().equals("")|| rgSelect == -1){
                        Toast.makeText(RegistrarseActivity.this, "Complete todos los datos", Toast.LENGTH_LONG).show();
                    } else if (!termycond.isChecked()) {
                        Toast.makeText(RegistrarseActivity.this, "Acepte los términos y condiciones", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(RegistrarseActivity.this, AddRegisterActivity.class);
                        startActivity(i);
                    }
                }catch (Exception e){
                    Toast.makeText(RegistrarseActivity.this, "Algo salió mal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}