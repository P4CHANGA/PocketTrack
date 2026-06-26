package com.example.pockettrackapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CuentaAdapter extends ArrayAdapter<Cuenta> {

    private Context contexto;
    private List<Cuenta> misCuentas;


    public CuentaAdapter(@NonNull Context context, @NonNull List<Cuenta> objects) {
        super(context, 0, objects);

        this.contexto = context;
        this.misCuentas = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater miInflador = LayoutInflater.from(getContext());
            convertView = miInflador.inflate(R.layout.fila_cuenta, parent, false);
        }

            Cuenta cuenta = misCuentas.get(position);

            TextView txtNombre = convertView.findViewById(R.id.txtNombre);
            TextView txtCantidad = convertView.findViewById(R.id.txtCantidad);
            TextView txtTipo = convertView.findViewById(R.id.txtDivisa);

            txtNombre.setText(cuenta.nombre);
            txtCantidad.setText(String.valueOf(cuenta.cantidad));
            txtTipo.setText(cuenta.divisa);

            return convertView;
        }
    }

