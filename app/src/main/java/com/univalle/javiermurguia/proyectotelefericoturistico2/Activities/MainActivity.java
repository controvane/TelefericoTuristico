package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.Marker;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.Marcador;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.MarkerViewModel;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.User;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.UserViewModel;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewAlias;
    private UserViewModel userViewModel;
    private LinearLayout.LayoutParams params;
    private FragmentContainerView optionsFragment;
    private User user;
    private ConstraintLayout controles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textViewAlias = findViewById(R.id.textViewAlias);
        this.user= (User) getIntent().getSerializableExtra("user");
        try{
            if(user.isEnabled()){
                this.textViewAlias.setText("Bienvenido "+user.getUserName());
            }
            else {
                this.textViewAlias.setText("");
            }
        }
        catch(Exception ex){
            this.textViewAlias.setText("");
        }
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        this.userViewModel.getUser().observe(this, userView -> hideOptionsFragment(userView));
        this.optionsFragment = findViewById(R.id.opciones);
        this.controles = findViewById(R.id.controles);
        this.params = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,0);
        this.optionsFragment.setLayoutParams(this.params);
        checkLogin();
        this.controles.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1f
                )
        );
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(MainActivity.this, ARActivity.class));
            }
        });
        findViewById(R.id.buttonIrAMap).setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(MainActivity.this, LoadingActivity.class));
            }
        });
        findViewById(R.id.buttonOpciones).setOnClickListener(view -> infoOfUser(this.user));
        this.setTitle("Guia Teleferico Turistico");
    }

    private void hideOptionsFragment(User user){
        if(!user.isShow()){
            this.params.weight = 0f;
            this.optionsFragment.setLayoutParams(this.params);
            this.controles.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f
                    )
            );
        }
    }

    public boolean infoOfUser(User user){
        user.setShow(true);
        if(user.isShow()){
            Log.d("creandoOnClick", "estoy dentro del if del for de infoOfMarker");
            this.params.weight = 1f;
            this.optionsFragment.setLayoutParams(this.params);
            this.controles.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            0f
                    )
            );
            this.userViewModel.setUser(user);
        }
        return true;
    }

    private void checkLogin(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url ="http://150.230.90.26/api/user-connect";
        Map<String,String> args = new HashMap<String,String>();
        JsonObjectRequest jSonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,new JSONObject(args),
                object -> Log.d("registroLogin","Registro realizado exitosamente"),
                error -> Log.d("aviso","Ooops, hubo un error "+error.getMessage()));
        queue.add(jSonRequest);
        return;
    }
}