package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.User;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button botonInvitado, botonUsuario, botonNuevoUsuario;
    EditText textoCorreo, textoContrasenia;
    Intent intento;
    Boolean userChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Pedimos permiso para poder usar la camara
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    50); }

        this.userChecker = false;
        this.botonInvitado = findViewById(R.id.buttonInvitado);
        this.botonUsuario = findViewById(R.id.buttonUsuario);
        this.botonNuevoUsuario = findViewById(R.id.buttonNuevoUsuario);
        this.textoCorreo = findViewById(R.id.editTextCorreo);
        this.textoContrasenia = findViewById(R.id.editTextPassword);

        this.botonUsuario.setOnClickListener(view -> {
            try{
                pressBotonUsuario();
            }
            catch(JSONException ex){
                Log.d("FalloLogin","Se Jorobó el Login");
            }

        });

        this.botonInvitado.setOnClickListener(view -> pressBotonInvitado());
        this.botonNuevoUsuario.setOnClickListener(view -> pressCrearUsuario());
    }

    protected void pressBotonInvitado(){
        User user = new User("Invitado");
        this.intento = new Intent(this.getApplicationContext(), MainActivity.class);
        this.intento.putExtra("user",user);
        startActivity(this.intento);
        this.finish();
    }

    protected void pressCrearUsuario(){
        this.intento = new Intent(this.getApplicationContext(), CreateUserActivity.class);
        startActivity(this.intento);
        this.finish();
    }

    protected void pressBotonUsuario() throws JSONException {
        this.intento = new Intent(this.getApplicationContext(), MainActivity.class);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url ="http://150.230.90.26/api/login";
        Map<String,String> loginArgs = new HashMap<String,String>();
        loginArgs.put("email",this.textoCorreo.getText().toString());
        loginArgs.put("password",this.textoContrasenia.getText().toString());
        JsonObjectRequest jSonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,new JSONObject(loginArgs),
                object -> fillApiContent(object),
                error -> Log.d("aviso","Ooops, hubo un error "+error.getMessage()));
        queue.add(jSonRequest);
        return;
    }

    private void fillApiContent(JSONObject object){
        try {
            if(object.getBoolean("enabled")){
                this.userChecker = true;
                User user = new User(
                        object.getString("email"),
                        object.getBoolean("enabled"),
                        object.getString("first_name"),
                        object.getString("id_token"),
                        object.getString("last_name"),
                        object.getString("rol"),
                        object.getString("url_avatar"),
                        object.getString("user_name"),
                        object.getString("uuid"));
                this.intento.putExtra("user",user);
                startActivity(this.intento);
                this.finish();
                return;
            }
            this.userChecker = false;
            Toast.makeText(this,"email o contraseña erroneos",Toast.LENGTH_LONG).show();
        }catch (JSONException ex){
            this.userChecker = false;
            Toast.makeText(this,"usuario no encontrado",Toast.LENGTH_LONG).show();
        }
    }
}