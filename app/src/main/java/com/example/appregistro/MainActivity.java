package com.example.appregistro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etUsuario, etEmail, etPassword, etConfirmarPassword;
    RadioGroup rgSexo;
    CheckBox cbTerminos;
    Button btnRegistrarse, btnModo;
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_SEXO = "sexo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar las vistas
        enlazarVistas();

        // Configurar el botón de registro
        btnRegistrarse.setOnClickListener(v -> procesarRegistro());

        // Configurar el botón de modo claro/oscuro
        btnModo.setOnClickListener(v -> toggleTheme());
    }

    private void toggleTheme() {
        // Obtenemos el modo actual del sistema
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Invertimos el modo
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void enlazarVistas() {
        etUsuario = findViewById(R.id.et_usuario);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmarPassword = findViewById(R.id.et_confirmar_password);
        rgSexo = findViewById(R.id.rg_sexo);
        cbTerminos = findViewById(R.id.cb_terminos);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnModo = findViewById(R.id.btn_modo_claro_oscuro);
    }

    private void procesarRegistro() {
        if (validarFormulario()) {
            String usuario = Objects.requireNonNull(etUsuario.getText()).toString();
            String email = Objects.requireNonNull(etEmail.getText()).toString();

            int selectedSexoId = rgSexo.getCheckedRadioButtonId();
            RadioButton rbSexoSeleccionado = findViewById(selectedSexoId);
            String sexo = rbSexoSeleccionado.getText().toString();

            Intent intent = new Intent(MainActivity.this, ResumenActivity.class);
            intent.putExtra(KEY_USUARIO, usuario);
            intent.putExtra(KEY_EMAIL, email);
            intent.putExtra(KEY_SEXO, sexo);

            startActivity(intent);
        }
    }

    /**
     * Comprueba todas las reglas de validación.
     * Muestra un Toast con el primer error que encuentra.
     * @return true si todo es válido, false si hay algún error.
     */
    private boolean validarFormulario() {
        String usuario = Objects.requireNonNull(etUsuario.getText()).toString().trim();
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString();
        String confirmarPassword = Objects.requireNonNull(etConfirmarPassword.getText()).toString();

        // Campos de texto rellenos
        if (usuario.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarToast(getString(R.string.error_campos_vacios));
            return false;
        }

        // Formato de correo
        if (!email.contains("@") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrarToast(getString(R.string.error_email_formato));
            return false;
        }

        // Longitud de contraseña
        if (password.length() < 7) {
            mostrarToast(getString(R.string.error_password_corta));
            return false;
        }

        // Coincidencia de contraseñas
        if (!password.equals(confirmarPassword)) {
            mostrarToast(getString(R.string.error_password_no_coincide));
            return false;
        }

        // Sexo seleccionado
        if (rgSexo.getCheckedRadioButtonId() == -1) {
            mostrarToast(getString(R.string.error_sexo_requerido));
            return false;
        }

        // Términos aceptados
        if (!cbTerminos.isChecked()) {
            mostrarToast(getString(R.string.error_terminos_requeridos));
            return false;
        }

        return true;
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}