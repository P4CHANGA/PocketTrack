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

public class GastoAdapter extends ArrayAdapter<Gastos> {

    private Context contexto;
    private List<Gastos> misGastos;


    public GastoAdapter(@NonNull Context context, @NonNull List<Gastos> objects) {
        super(context, 0, objects);

        this.contexto = context;
        this.misGastos = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater miInflador = LayoutInflater.from(getContext());
            convertView = miInflador.inflate(R.layout.fila_gasto, parent, false);
        }


        Gastos gastos = misGastos.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtNombreGasto);
        TextView txtCantidad = convertView.findViewById(R.id.txtCantidadGasto);

        txtNombre.setText(gastos.nombre);
        txtCantidad.setText(String.valueOf(gastos.cantidad));
        return convertView;
    }
}
