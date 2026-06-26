package com.example.pockettrackapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.os.LocaleListCompat;

import com.example.pockettrackapp.api.API;
import com.example.pockettrackapp.api.UtilRest;

public class CrearGastosActivity extends AppCompatActivity {
    EditText etNombre, etCantidad;
    Button crearButton;

    private final String CANAL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs =
                getSharedPreferences("preferencias", MODE_PRIVATE);

        String lang = prefs.getString("idioma", "es");

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_gastos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNombre = findViewById(R.id.etNombreGasto);
        etCantidad = findViewById(R.id.etCantidadGasto);
        crearButton = findViewById(R.id.btnCrearGasto);

        crearButton.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String cantidad = etCantidad.getText().toString().trim();

            if (nombre.isEmpty() || cantidad.isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double cantidadPars = Double.parseDouble(cantidad);

            long idCuenta = getSharedPreferences("APP", MODE_PRIVATE)
                    .getLong("ID_CUENTA", -1);


            String token = getSharedPreferences("API",MODE_PRIVATE)
                    .getString("TOKEN",null);
            UtilRest.addHeader("Authorization","Bearer " + token);

            API.crearGasto(cantidadPars, nombre ,idCuenta, new UtilRest.OnResponseListener() {
                @Override
                public void onSuccess(UtilRest.Response r) {
                    long idCuenta = getSharedPreferences("APP", MODE_PRIVATE)
                            .getLong("ID_CUENTA", -1);
                    String token = getSharedPreferences("API",MODE_PRIVATE)
                            .getString("TOKEN",null);
                    UtilRest.addHeader("Authorization","Bearer " + token);
                    API.modCantidadCuenta(idCuenta, cantidadPars, new UtilRest.OnResponseListener() {
                        @Override
                        public void onSuccess(UtilRest.Response r) {

                        }

                        @Override
                        public void onError(UtilRest.Response r) {

                        }
                    });

                    mostrarNotificacion();
                    Intent intent = new Intent(CrearGastosActivity.this, CuentasActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(UtilRest.Response r) {

                }
            });

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
            Intent intent = new Intent(CrearGastosActivity.this, PreferenciasActivity.class);
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

    private void mostrarNotificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL_ID);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);

        builder.setContentTitle("PocketTrack");
        builder.setContentText("Gasto creado");
        builder.setSmallIcon(android.R.drawable.star_big_on);

        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(CANAL_ID, "Titulo del canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(canal);
        }

        Notification notification = builder.build();
        notificationManager.notify(Integer.parseInt(CANAL_ID), notification);
    }
}
