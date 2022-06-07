package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class CambiarPassActivity extends AppCompatActivity {

    private EditText textPass;
    private Button buttonPass;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);

        this.user = (User) getIntent().getSerializableExtra("user");
        this.textPass = findViewById(R.id.editTextCambiarPass);
        this.buttonPass = findViewById(R.id.buttonChangePass);
        this.buttonPass.setOnClickListener(view -> {
            try {
                pressBotonChangePass();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    protected void pressBotonChangePass() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(CambiarPassActivity.this);
        String url ="http://150.230.90.26/api/changepassword";
        Map<String,String> loginArgs = new HashMap<String,String>();
        loginArgs.put("email",user.getEmail());
        loginArgs.put("password",this.textPass.getText().toString());
        if(this.textPass.getText().toString().length() < 6){
            Toast.makeText(this,"Contraseña demasiado corta",Toast.LENGTH_LONG).show();
            return;
        }
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
            Toast.makeText(this,"Contraseña cambiada correctamente",Toast.LENGTH_LONG).show();
            this.finish();
        }catch (Exception ex){
            Toast.makeText(this,"usuario no encontrado",Toast.LENGTH_LONG).show();
        }
        return;
    }
}