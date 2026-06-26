package com.example.pockettrackapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

public class CuentasActivity extends AppCompatActivity {

    ListView listView;
    List<Cuenta> cuentaList = new ArrayList<>();
    CuentaAdapter cuentaAdapter;

    private ImageView addCuentas;
    private ImageView delCuentas;


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
        setContentView(R.layout.cuentas_usuario_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listaCuentas);
        addCuentas = findViewById(R.id.addCuenta);
        delCuentas = findViewById(R.id.delCuenta);
        cuentaAdapter = new CuentaAdapter(this, cuentaList);

        listView.setAdapter(cuentaAdapter);

        String nombreUsario = getSharedPreferences("APP",MODE_PRIVATE)
                .getString("NOMBREID",null);


        idPorNombre(nombreUsario);


        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Cuenta cuentaSeleccionada = cuentaList.get(position);
            Intent intent = new Intent(CuentasActivity.this, GastosActivity.class);
            intent.putExtra("NOMBRE",cuentaSeleccionada.nombre);
            startActivity(intent);

        });

        addCuentas.setOnClickListener(v -> {
            Intent intent = new Intent(CuentasActivity.this, CrearCuentaActivity.class);
            startActivity(intent);
        });

        delCuentas.setOnClickListener(v -> {
            long idUsuarioBorrar = getSharedPreferences("APP", MODE_PRIVATE)
                    .getLong("ID_USUARIO", -1);

            borrarCuentaYGastos(idUsuarioBorrar);
        });
    }

    private void cargarCuentas(long idUsuario){
        String token = getSharedPreferences("API",MODE_PRIVATE)
                .getString("TOKEN",null);

        Log.e("TOKEN", "Token guardado: " + token);
        UtilRest.addHeader("Authorization","Bearer " + token);
        API.getCuentas(idUsuario, new UtilRest.OnResponseListener() {
            @Override
            public void onSuccess(UtilRest.Response r) {

                try{

                    JSONArray array = new JSONArray(r.content);
                    cuentaList.clear();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Cuenta c = new Cuenta();

                        c.nombre = object.getString("nombre");
                        c.divisa = object.getString("divisa");
                        c.cantidad = object.getDouble("cantidad");

                        cuentaList.add(c);

                        getSharedPreferences("APP",MODE_PRIVATE)
                                .edit()
                                .putString("CUENTANOMBRE",c.nombre = object.getString("nombre"))
                                .apply();
                    }

                    runOnUiThread(() -> cuentaAdapter.notifyDataSetChanged());
                } catch (JSONException e) {
                    Log.e("CUENTAS", "Error parseando JSON: " + e.getMessage());
                    Log.e("CUENTAS", "Contenido recibido: '" + r.content + "'");
                }
            }

            @Override
            public void onError(UtilRest.Response r) {
                Toast.makeText(CuentasActivity.this, "Error parseando JSON", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void idPorNombre(String username){
        String token = getSharedPreferences("API",MODE_PRIVATE)
                .getString("TOKEN",null);
        UtilRest.addHeader("Authorization","Bearer " + token);
        API.getIdPorNombre(username, new UtilRest.OnResponseListener() {
            @Override
            public void onSuccess(UtilRest.Response r) {

                try {
                    long idUsuario = Long.parseLong(r.content);
                    getSharedPreferences("APP", MODE_PRIVATE)
                            .edit()
                            .putLong("ID_USUARIO", idUsuario)
                            .apply();

                    cargarCuentas(idUsuario);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(UtilRest.Response r) {

            }
        });

    }

    public void borrarCuentaYGastos(Long idUsuario){

        String token = getSharedPreferences("API",MODE_PRIVATE)
                .getString("TOKEN",null);
        UtilRest.addHeader("Authorization","Bearer " + token);
        API.borrarCuentasYGastos(idUsuario, new UtilRest.OnResponseListener() {
            @Override
            public void onSuccess(UtilRest.Response r) {
                Toast.makeText(CuentasActivity.this, "Cuentas y Gastos eliminados", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(UtilRest.Response r) {
                Toast.makeText(CuentasActivity.this, "Error parseando JSON", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        long idUsuario = getSharedPreferences("APP", MODE_PRIVATE)
                .getLong("ID_USUARIO", -1);

        if (idUsuario != -1) {
            cargarCuentas(idUsuario);
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
            Intent intent = new Intent(CuentasActivity.this, PreferenciasActivity.class);
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
