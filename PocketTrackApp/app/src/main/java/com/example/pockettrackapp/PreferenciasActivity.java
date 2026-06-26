package com.example.pockettrackapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class PreferenciasActivity extends AppCompatActivity {

    private RadioGroup rgIdioma;
    private RadioButton rbEsp;
    private RadioButton rbEng;


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
        setContentView(R.layout.preferencias_activity);

        rgIdioma = findViewById(R.id.rgIdioma);
        rbEsp = findViewById(R.id.rbEsp);
        rbEng = findViewById(R.id.rbEng);

        SharedPreferences preferences = getSharedPreferences("preferencias",MODE_PRIVATE);

        String idiomaActual = preferences.getString("idioma","es");

        if(idiomaActual.equals("es")){
            rbEsp.setChecked(true);
        }else {
            rbEng.setChecked(true);
        }

        rgIdioma.setOnCheckedChangeListener((group, checkedId) -> {
           String nuevoIdioma = "es";

           if(checkedId == R.id.rbEng){
               nuevoIdioma = "en";
           }

           cambiarIdioma(nuevoIdioma);
           confirmarIdioma(nuevoIdioma);
           finish();
        });

    }
    private void cambiarIdioma(String lang){
        SharedPreferences preferences = getSharedPreferences("preferencias", MODE_PRIVATE);

        preferences.edit()
                .putString("idioma",lang)
                .apply();
    }

    private void confirmarIdioma(String lang){
        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
        );
    }
}

