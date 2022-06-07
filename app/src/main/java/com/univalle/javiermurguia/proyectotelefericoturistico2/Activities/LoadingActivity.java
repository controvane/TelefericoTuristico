package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.Marcador;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private List<Marcador> marcadores;
    private TextView cargando;
    private Thread hiloJson;
    private Thread hiloTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.marcadores = new ArrayList<>();
        this.cargando = findViewById(R.id.textViewCargando);
        // este Thread es para poder cargar el Json sin Colgar la app
        this.hiloJson = new Thread(() -> {
            cargarLista();
        });
        //Este es para mostrar una animación de carga sin colgar la aplicación
        this.hiloTexto = new Thread(() -> {
            for(int i = 100; i < Double.POSITIVE_INFINITY; i+= 300){
                //esta parte nos permite escuchar cuando se interrumpa el hilo para poder cortar el ciclo
                if(Thread.interrupted()){
                    break;
                }
                correrHandlerTexto(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.hiloTexto.start();
        this.hiloJson.start();
    }

    //esta animación solo crea al handler para que se pueda loopear la animación de carga
    private void correrHandlerTexto(int i){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cambiarTexto();
            }
        },i);
    }

    //esta funcion crea la animación de carga
    private void cambiarTexto(){
        Log.d("flag", "estoy cambiando el texto");
        //Este bloque try catch es una solución muy a lo bruto, es importante verificar porque no puede traer el text view cargando
        try{
            int estado = LoadingActivity.this.cargando.getText().toString().length();
            if(estado < 9){
                LoadingActivity.this.cargando.setText("Cargando .");
                return;
            }else if(estado < 11){
                LoadingActivity.this.cargando.setText("Cargando . .");
                return;
            }else if(estado < 13){
                LoadingActivity.this.cargando.setText("Cargando . . .");
                return;
            }else{
                LoadingActivity.this.cargando.setText("Cargando");
                return;
            }
        }
        catch (Exception ex){
            return;
        }
    }

    //esta funcion nos envia a la actividad de los mapas
    private void enviarAMaps(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("flag", "creando el intentd para ir a map");
                if(LoadingActivity.this.marcadores.size() <= 0){
                    cargarLista();
                    return;
                }
                Intent intento = new Intent(LoadingActivity.this, MapsActivity.class);
                intento.putExtra("Marcadores", (Serializable) LoadingActivity.this.marcadores);
                Log.d("flag", "yendo a map");
                LoadingActivity.this.hiloTexto.interrupt();
                startActivity(intento);
                LoadingActivity.this.finish();
            }
        });
    }

    //Con esta funcion accedemos al JSON con la información de los marcadores
    private void cargarLista(){
        Log.d("flagCargarLista","creando handler de cargar lista");
        RequestQueue queue = Volley.newRequestQueue(LoadingActivity.this);
        String url ="http://150.230.90.26/api/routes";
        if (LoadingActivity.this.marcadores.size() <= 0) {
            JSONArray content = new JSONArray();
            Log.d("flagCargarLista", "haciendo request de JSON");
            JsonArrayRequest jSonRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    content,
                    object -> fillApiContent(object),
                    error -> Log.d("aviso","Ooops, hubo un error "+ error.getMessage()));
            queue.add(jSonRequest);

        }else if(LoadingActivity.this.marcadores.size() > 0){
            Log.d("aviso","ya descargue las ubicaciones");
        }
    }

    //Con esta funcion se carga la lista de marcadores que se enviara a la Activity con el mapa
    private void fillApiContent(JSONArray lineas){
        JSONObject linea;
        JSONObject estacion;
        JSONArray estaciones;
        JSONObject coords;
        try {
            Log.d("flagCargarLista", "Llenando lista");
            for (int i = 0;i < lineas.length(); i++) {
                linea = lineas.getJSONObject(i);
                estaciones = linea.getJSONArray("stations");
                for(int j = 0; j < estaciones.length(); j++){
                    estacion = estaciones.getJSONObject(j);
                    coords = estacion.getJSONObject("coords");
                    this.marcadores.add(new Marcador(
                            estacion.getString("name"),
                            estacion.getString("description"),
                            linea.getString("route"),
                            estacion.getBoolean("draggable"),
                            coords.getDouble("lat"),
                            coords.getDouble("lng")));
                }
            }
        }catch (JSONException ex){
            Log.d("aviso","Hubo un error al extraer datos del Json, Ooops!");
        }
        enviarAMaps();
    }
}