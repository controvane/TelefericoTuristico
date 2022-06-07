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
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class CreateUserActivity extends AppCompatActivity {

    EditText textCorreo, textNombre, textApellido, textPassword,textAlias;
    Button buttonCrearUsuario;
    Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        this.textCorreo = findViewById(R.id.editTextCrearEmail);
        this.textNombre = findViewById(R.id.editTextCrearNombre);
        this.textApellido = findViewById(R.id.editTextCrearApellido);
        this.textPassword = findViewById(R.id.editTextCrearPass);
        this.textAlias = findViewById(R.id.editTextCrearAlias);
        this.buttonCrearUsuario = findViewById(R.id.buttonCrearUsuario);

        this.buttonCrearUsuario.setOnClickListener(view -> createNewUser());
        this.setTitle("Crear Nuevo Usuario");
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    protected void createNewUser(){
        RequestQueue queue = Volley.newRequestQueue(CreateUserActivity.this);
        String url ="http://150.230.90.26/api/register";
        Map<String,String> newUserArgs = new HashMap<String,String>();
        if(!isValid(this.textCorreo.getText().toString())){
            Toast.makeText(this,"Correo invalido",Toast.LENGTH_LONG).show();
            return;
        }
        if(this.textPassword.getText().toString().length() < 6){
            Toast.makeText(this,"Contraseña muy corta",Toast.LENGTH_LONG).show();
            return;
        }
        if(this.textNombre.getText().toString().length() < 1){
            Toast.makeText(this,"Nombre invalido",Toast.LENGTH_LONG).show();
            return;
        }
        if(this.textApellido.getText().toString().length() < 1){
            Toast.makeText(this,"Apellido Invalido",Toast.LENGTH_LONG).show();
            return;
        }
        if(this.textAlias.getText().toString().length() < 1){
            Toast.makeText(this,"Alias Invalido",Toast.LENGTH_LONG).show();
            return;
        }
        newUserArgs.put("email",this.textCorreo.getText().toString());
        newUserArgs.put("first_name",this.textNombre.getText().toString());
        newUserArgs.put("last_name",this.textApellido.getText().toString());
        newUserArgs.put("password",this.textPassword.getText().toString());
        newUserArgs.put("user_name",this.textAlias.getText().toString());
        JsonObjectRequest jSonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,new JSONObject(newUserArgs),
                object -> fillApiContent(object),
                error -> Log.d("aviso","Ooops, hubo un error "+error.getMessage()));
        queue.add(jSonRequest);
        return;
    }

    private void fillApiContent(JSONObject object){
        this.intento = new Intent(this.getApplicationContext(), LoginActivity.class);
        try {
            if(object.getBoolean("enabled")){
                Toast.makeText(this,"usuario creado con exito",Toast.LENGTH_LONG).show();
                startActivity(this.intento);
                this.finish();
                return;
            }
            Toast.makeText(this,"usuario no se pudo crear",Toast.LENGTH_LONG).show();
        }catch (JSONException ex){
            Toast.makeText(this,"error al crear el usuario",Toast.LENGTH_LONG).show();
        }
    }
}