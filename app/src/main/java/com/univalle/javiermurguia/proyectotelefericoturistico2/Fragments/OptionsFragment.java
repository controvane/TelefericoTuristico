package com.univalle.javiermurguia.proyectotelefericoturistico2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Activities.CambiarPassActivity;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Activities.LoginActivity;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Activities.MainActivity;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.Marcador;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.MarkerViewModel;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.User;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.UserViewModel;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OptionsFragment extends Fragment {

    private TextView textNombre;
    private Button buttonCambiarPass, buttonCerrarSesion, buttonCerrarOpciones;
    private UserViewModel userViewModel;
    private User user;

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize(View view){
        this.textNombre = view.findViewById(R.id.textViewFragNombre);
        this.buttonCambiarPass = view.findViewById(R.id.buttonFragCambiarPass);
        this.buttonCerrarSesion = view.findViewById(R.id.buttonFragCerrarSesión);
        this.buttonCerrarOpciones = view.findViewById(R.id.buttonFragCerrarOpciones);
        this.buttonCerrarOpciones.setOnClickListener(viewCerrar -> cerrarFragment());
        this.buttonCerrarSesion.setOnClickListener(viewCerrar -> cerrarSesion());
        this.buttonCambiarPass.setOnClickListener(viewCambiar -> cambiarPass());
        this.userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        this.userViewModel.getUser().observe(getViewLifecycleOwner(), user -> loadInfo(user));
    }

    public void loadInfo(User user){
        this.user = user;
        this.textNombre.setText(user.getUserName());
        this.textNombre.setVisibility(View.VISIBLE);
        this.buttonCambiarPass.setVisibility(View.VISIBLE);
        this.buttonCerrarSesion.setVisibility(View.VISIBLE);
        this.buttonCerrarOpciones.setVisibility(View.VISIBLE);
    }

    public void cerrarFragment(){
        this.userViewModel.setUser(new User());
        this.textNombre.setText("");
        this.textNombre.setVisibility(View.INVISIBLE);
        this.buttonCambiarPass.setVisibility(View.INVISIBLE);
        this.buttonCerrarSesion.setVisibility(View.INVISIBLE);
        this.buttonCerrarOpciones.setVisibility(View.INVISIBLE);
    }

    public void cerrarSesion(){
        checkLogout();
        Intent intento = new Intent(getActivity(), LoginActivity.class);
        intento.putExtra("user", this.user);
        startActivity(intento);
        getActivity().finish();
    }

    public void cambiarPass(){
        if(this.user.getUserName().equals("Invitado")){
            Toast.makeText(getActivity(),"Invitado no tiene contraseña para cambiar", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intento = new Intent(getActivity(), CambiarPassActivity.class);
        intento.putExtra("user",this.user);
        startActivity(intento);
    }

    private void checkLogout(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://150.230.90.26/api/user-disconnect";
        Map<String,String> args = new HashMap<String,String>();
        JsonObjectRequest jSonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,new JSONObject(args),
                object -> Log.d("registroLogout","Registro realizado exitosamente"),
                error -> Log.d("aviso","Ooops, hubo un error "+error.getMessage()));
        queue.add(jSonRequest);
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        initialize(view);
        return view;
    }
}