package com.example.pockettrackapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class InfoActivity extends AppCompatActivity {
    private Button btnGoogle, btnSonido;
    private ImageView volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs =
                getSharedPreferences("preferencias", MODE_PRIVATE);

        String lang = prefs.getString("idioma", "es");

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        btnGoogle = findViewById(R.id.btnGoogle);
        btnSonido = findViewById(R.id.btnSonido);
        volver = findViewById(R.id.btnVolver);

        btnGoogle.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        });

        btnSonido.setOnClickListener(v -> {
            Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), sonido);
            r.play();
        });

        volver.setOnClickListener(v -> {
            finish();
        });
    }

}


