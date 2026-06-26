package com.example.pockettrackapp.api;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    private static final OkHttpClient client = new OkHttpClient();
    private final static String AUTH_URL = "http://54.144.47.131/auth/";
    private final static String POCKET_URL = "http://54.144.47.131/api/pocket/";


    public static void register(JSONObject post, UtilRest.OnResponseListener listener){
        UtilRest.runQuery(UtilRest.QueryType.POST,AUTH_URL + "signup", post.toString(),listener );
    }

    public static void crearCuenta(String divisa, double cantidad, String nombre, long usuarioId, UtilRest.OnResponseListener listener){
        String url = POCKET_URL + "crearCuenta/"
                + divisa + "/"
                + cantidad + "/"
                + nombre + "/"
                + usuarioId;

        UtilRest.runQuery(UtilRest.QueryType.POST,url, "",listener);
    }

    public static void crearGasto(double cantidad,String nombre,long cuentaId, UtilRest.OnResponseListener listener){
        String url = POCKET_URL + "crearGasto/"
                + nombre + "/"
                + cantidad + "/"
                + cuentaId;

        UtilRest.runQuery(UtilRest.QueryType.POST,url, "",listener);
    }

    public static void modCantidadCuenta(long cuentaId, double cantidad, UtilRest.OnResponseListener listener){
        if (cuentaId < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        String url = POCKET_URL + "cantidadCuenta/"
                + cuentaId + "/"
                + cantidad;

        UtilRest.runQuery(
                UtilRest.QueryType.PATCH,
                url,
                "",
                listener
        );

    }

    public static void login(JSONObject post, UtilRest.OnResponseListener listener){
        UtilRest.runQuery(UtilRest.QueryType.POST,AUTH_URL + "login", post.toString(),listener );
    }

    public static void getCuentas(Long idUsuario, UtilRest.OnResponseListener listener) {
        if (idUsuario < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilRest.runQuery(UtilRest.QueryType.GET, POCKET_URL + "obtenerCuentas/" + idUsuario, listener);
    }

    public static void getIdPorNombre(String nombreUsuario,UtilRest.OnResponseListener listener){
        if(nombreUsuario == null || nombreUsuario.trim().isEmpty()){
            throw new IllegalArgumentException("Nombre de usuario no valido");
        }

        UtilRest.runQuery(UtilRest.QueryType.GET, POCKET_URL + "obtenerIdUsuarioPorUsername/" + nombreUsuario,listener);
    }

    public static void getIdPorNombreCuenta(String nombreCuenta,UtilRest.OnResponseListener listener){
        if(nombreCuenta == null || nombreCuenta.trim().isEmpty()){
            throw new IllegalArgumentException("Nombre de cuenta no valido");
        }

        UtilRest.runQuery(UtilRest.QueryType.GET, POCKET_URL + "obtenerIdCuentaPorNombre/" + nombreCuenta,listener);
    }

    public static void getGastos(Long idCuentas, UtilRest.OnResponseListener listener) {
        if (idCuentas < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilRest.runQuery(UtilRest.QueryType.GET, POCKET_URL + "obtenerGastos/" + idCuentas, listener);
    }

    public static void borrarCuentasYGastos(Long idUsuario, UtilRest.OnResponseListener listener) {
        if (idUsuario < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilRest.runQuery(UtilRest.QueryType.DELETE, POCKET_URL + "eliminarCuentasGastos/" + idUsuario, listener);
    }
}
