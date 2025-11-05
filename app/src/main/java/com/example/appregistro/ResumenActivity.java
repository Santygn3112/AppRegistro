package com.example.appregistro; // Asegúrate de que este es tu paquete

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate; // 👈 Importar
import android.content.Intent;
import android.content.res.Configuration; // 👈 Importar
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResumenActivity extends AppCompatActivity {

    TextView tvResumenDatos;
    Button btnVolver, btnModo; // 👈 Declarar vistas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        // 1. Enlazar las vistas
        tvResumenDatos = findViewById(R.id.tv_resumen_datos);
        btnVolver = findViewById(R.id.btn_volver);
        btnModo = findViewById(R.id.btn_modo_claro_oscuro_resumen); // 👈 Enlazar

        // 2. Recuperar y mostrar datos del Intent
        Intent intent = getIntent();
        String usuario = intent.getStringExtra(MainActivity.KEY_USUARIO);
        String email = intent.getStringExtra(MainActivity.KEY_EMAIL);
        String sexo = intent.getStringExtra(MainActivity.KEY_SEXO);

        String resumen =
                "Nombre de usuario: " + usuario +
                "\nCorreo electrónico: " + email +
                "\nSexo: " + sexo;
        tvResumenDatos.setText(resumen);

        // 3. Configurar botón de volver
        btnVolver.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y vuelve a MainActivity
        });

        // 4. Configurar botón de modo claro/oscuro
        btnModo.setOnClickListener(v -> toggleTheme());
    }

    /**
     * Esta función cambia el modo de la app entre claro y oscuro.
     * Es idéntica a la de MainActivity.
     */
    private void toggleTheme() {
        // Obtenemos el modo actual del sistema
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Invertimos el modo
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Si es de noche, cambiamos a modo de día
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            // Si es de día, cambiamos a modo de noche
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}