package com.javieravenegas.finanzasencasalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AddRegisterActivity extends AppCompatActivity {

    private Button continuar;
    private ProgressBar pgbar;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_register);

        pgbar= (ProgressBar) findViewById(R.id.pgbAddingRegister);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                contador++;
                pgbar.setProgress(contador);

                if (contador == 100){
                    t.cancel();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddRegisterActivity.this, "Usuario agregado correctamente", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(AddRegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        t.schedule(tt, 0, 100);
    }
}