package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button botonInvitado, botonUsuario;
    EditText textoCorreo, textoContrasenia;
    Intent intento;
    Boolean userChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userChecker = false;
        this.botonInvitado = findViewById(R.id.buttonInvitado);
        this.botonUsuario = findViewById(R.id.buttonUsuario);
        this.textoCorreo = findViewById(R.id.editTextCorreo);
        this.textoContrasenia = findViewById(R.id.editTextPassword);

        this.intento = new Intent(this.getApplicationContext(), MainActivity.class);

        this.botonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    pressBotonUsuario();
                }
                catch(JSONException ex){
                    Log.d("FalloLogin","Se Jorobó el Login");
                }

            }
        });

        this.botonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBotonInvitado();
            }
        });
    }

    protected void pressBotonInvitado(){
        startActivity(this.intento);
        this.finish();
    }

    protected void pressBotonUsuario() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url ="http://10.166.14.114:5000/api/login";
        JSONObject content = new JSONObject();
        content.put("email",this.textoCorreo.getText());
        content.put("password",this.textoContrasenia.getText());
        JsonObjectRequest jSonRequest = new JsonObjectRequest(Request.Method.POST, url,content, object -> fillApiContent(object), error -> Log.d("aviso","Ooops, hubo un error"));
        queue.add(jSonRequest);
        if(this.userChecker){
            this.intento.putExtra("email",this.textoCorreo.getText());
            this.intento.putExtra("pass",this.textoContrasenia.getText());
            startActivity(this.intento);
            this.finish();
            return;
        }
        Toast.makeText(this,"email o contraseña erroneos",Toast.LENGTH_LONG).show();
        return;
    }

    private void fillApiContent(JSONObject object){
        try {
            object.getJSONObject("error");
            this.userChecker = false;
        }catch (JSONException ex){
            this.userChecker = true;
        }
    }
}