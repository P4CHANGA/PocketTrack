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

import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {

    private Button loginButton;
    private EditText usernameText, passwordText;

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
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginButton = findViewById(R.id.loginButton);
        usernameText = findViewById(R.id.usernameTextLogin);
        passwordText = findViewById(R.id.passwordTextLogin);

        loginButton.setOnClickListener(v -> {
            String username = usernameText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            try {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);

                getSharedPreferences("APP",MODE_PRIVATE)
                        .edit()
                                .putString("NOMBREID",username)
                                        .apply();

                API.login(json, new UtilRest.OnResponseListener() {
                    @Override
                    public void onSuccess(UtilRest.Response r) {

                        String token = r.content;
                        getSharedPreferences("API",MODE_PRIVATE)
                                .edit()
                                .putString("TOKEN",token)
                                        .apply();


                        Toast.makeText(ActivityLogin.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(ActivityLogin.this, CuentasActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onError(UtilRest.Response r) {
                        Toast.makeText(ActivityLogin.this, "Error: " + r.exception, Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(ActivityLogin.this, PreferenciasActivity.class);
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
