package com.example.pockettrackapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.LocaleListCompat;

import com.example.pockettrackapp.api.API;
import com.example.pockettrackapp.api.UtilRest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GastosActivity extends AppCompatActivity {

    ListView listView;
    List<Gastos> gastosList = new ArrayList<>();
    GastoAdapter gastoAdapter;

   private ImageView crearButton;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs =
                getSharedPreferences("preferencias", MODE_PRIVATE);

        String lang = prefs.getString("idioma", "es");

        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto_cuentas_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listaGastos);
        crearButton = findViewById(R.id.addGastos);
        gastoAdapter = new GastoAdapter(this,gastosList);

        listView.setAdapter(gastoAdapter);
        String nombre = getIntent().getStringExtra("NOMBRE");

        idPorNombre(nombre);

        crearButton.setOnClickListener(v ->{
            Intent intent = new Intent(GastosActivity.this, CrearGastosActivity.class);
            startActivity(intent);
        });


    }

    public void idPorNombre(String nombreCuenta){
        String token = getSharedPreferences("API",MODE_PRIVATE)
                .getString("TOKEN",null);
        UtilRest.addHeader("Authorization","Bearer " + token);
        API.getIdPorNombreCuenta(nombreCuenta, new UtilRest.OnResponseListener() {
            @Override
            public void onSuccess(UtilRest.Response r) {
                try{
                    long idCuenta = Long.parseLong(r.content);
                    getSharedPreferences("APP", MODE_PRIVATE)
                            .edit()
                            .putLong("ID_CUENTA", idCuenta)
                            .apply();

                    cargarGastos(idCuenta);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(UtilRest.Response r) {

            }
        });
    }

    public void cargarGastos(long idCuenta){
        String token = getSharedPreferences("API",MODE_PRIVATE)
                .getString("TOKEN",null);
        UtilRest.addHeader("Authorization","Bearer " + token);
        API.getGastos(idCuenta, new UtilRest.OnResponseListener() {
            @Override
            public void onSuccess(UtilRest.Response r) {
                try{
                    JSONArray array = new JSONArray(r.content);
                    gastosList.clear();

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Gastos g = new Gastos();

                        g.nombre = object.getString("nombre");
                        g.cantidad = object.getDouble("cantidad");

                        gastosList.add(g);

                        runOnUiThread(() -> gastoAdapter.notifyDataSetChanged());
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(UtilRest.Response r) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        long idCuenta = getSharedPreferences("APP", MODE_PRIVATE)
                .getLong("ID_CUENTA", -1);

        if (idCuenta != -1) {
            cargarGastos(idCuenta);
        }
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
            Intent intent = new Intent(GastosActivity.this, PreferenciasActivity.class);
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
