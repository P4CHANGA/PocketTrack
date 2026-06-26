package com.example.pockettrackapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.LocaleListCompat;

import com.example.pockettrackapp.api.API;
import com.example.pockettrackapp.api.UtilRest;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityRegister extends AppCompatActivity {
    private Button signUpButton;
    private EditText usernameText, passwordText, emailText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs =
                getSharedPreferences("preferencias", MODE_PRIVATE);

        String lang = prefs.getString("idioma", "es");

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        emailText = findViewById(R.id.emailText);
        signUpButton = findViewById(R.id.registerButton);

        signUpButton.setOnClickListener(v -> {
            String username = usernameText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();
            String email = emailText.getText().toString().trim();
            try {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);
                json.put("email", email);

                JSONArray roles = new JSONArray();
                roles.put(1);
                json.put("roles", roles);

                API.register(json, new UtilRest.OnResponseListener() {
                    @Override
                    public void onSuccess(UtilRest.Response r) {
                        Toast.makeText(ActivityRegister.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(UtilRest.Response r) {
                        Toast.makeText(ActivityRegister.this, "Error: " + r.exception, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.preferencias) {
            Intent intent = new Intent(ActivityRegister.this, PreferenciasActivity.class);
            startActivity(intent);
            return true;
        }



        if (id == R.id.info_activity) {
            startActivity(new Intent(this, InfoActivity.class));
            return true;
        }

        if (id == R.id.salir) {
            new AlertDialog.Builder(this)
                    .setTitle("Salir")
                    .setMessage("¿Desea cerrar la aplicación?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        finishAffinity();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
