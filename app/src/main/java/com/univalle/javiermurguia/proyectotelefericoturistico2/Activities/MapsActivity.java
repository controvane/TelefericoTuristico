package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.Marcador;
import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.MarkerViewModel;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;
import com.univalle.javiermurguia.proyectotelefericoturistico2.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Los Atributos Protected estan as?? para pasar informaciona la clase FollowerRunnable
    protected GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Marcador> marcadores;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected FusedLocationProviderClient myLocationProviderClient;
    protected PlacesClient placesClient;
    private MarkerViewModel markerViewModel;
    private FragmentContainerView infoFragment;
    private LinearLayout.LayoutParams params;
    private List<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Todo este bloque de codigo es para poder pedir permiso al usuario si es que dedsea que la aplicaci??n acceda a su gps
        if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            new AlertDialog.Builder(MapsActivity.this)
                    .setTitle("Permiso de de Geolocalizacion")
                    .setMessage("Esta aplicacion requiere usar geolocalizacion, a continuaci??n se le pedira su confirmaci??n")
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }).create().show();
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intento = getIntent();
        this.polylines = new ArrayList<>();
        this.marcadores = (List<Marcador>) intento.getSerializableExtra("Marcadores");
        this.markerViewModel = new ViewModelProvider(this).get(MarkerViewModel.class);
        this.markerViewModel.getSelectedItem().observe(this, marcador -> hideInfoFragment(marcador));
        this.infoFragment = findViewById(R.id.info);
        this.params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0);
        this.infoFragment.setLayoutParams(this.params);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.setTitle("Reccorrido");
    }

    private void hideInfoFragment(Marcador marker){
        if(marker.getNombre().equals("")){
            this.params.weight = 0f;
            this.infoFragment.setLayoutParams(this.params);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //cargamos todos los puntos de interes como marcadores al mapa
        /*for (Marcador m: this.marcadores){
            this.mMap.addMarker(new MarkerOptions().position(new LatLng(m.getLatitud(),m.getLongitud())).title(m.getNombre()));
        }*/
        //Para inicializar el mapa vamos a la Univalle, es como el predeterminado
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(
                        this.marcadores.get(0).getLatitud(),
                        this.marcadores.get(0).getLongitud()),
                17));
        this.mMap.setOnMarkerClickListener(marker -> infoOfMarker(marker));
        getMyLocation();
        mMap.setOnCameraMoveListener(() -> checkDrawLines());
        mMap.setOnCameraIdleListener(() -> checkDrawLines());
        checkDrawLines();
    }

    public void checkDrawLines(){
        if(this.polylines.size() > 0){
            for(Polyline poly : this.polylines){
                poly.remove();
            }
        }
        drawLines();
    }

    // revisar si esto funciona
    public void drawLines(){
        Map<String,List<Marcador>> lineas = new HashMap<String, List<Marcador>>();
        for(Marcador marker : this.marcadores){
            if(!lineas.containsKey(marker.getLinea())){
                List<Marcador> listaLineas = new ArrayList<>();
                listaLineas.add(marker);
                lineas.put(marker.getLinea(), listaLineas);
                continue;
            }
            lineas.get(marker.getLinea()).add(marker);
        }
        this.polylines.clear();
        for (String key : lineas.keySet()){
            PolylineOptions latlang = new PolylineOptions();
            for(Marcador marker: lineas.get(key)){
                latlang.add(new LatLng(marker.getLatitud(),marker.getLongitud()));
            }
            if(lineas.get(key).get(0).getLinea().equals("blanca")){
               this.polylines.add(mMap.addPolyline(latlang.width(5).color(Color.WHITE).geodesic(false)));
            }
            else if(lineas.get(key).get(0).getLinea().equals("Linea Naranja")){
                this.polylines.add(mMap.addPolyline(latlang.width(5).color(Color.parseColor("#DA8C25")).geodesic(false)));
            }
            else if(lineas.get(key).get(0).getLinea().equals("Linea Caf??")){
                this.polylines.add(mMap.addPolyline(latlang.width(5).color(Color.parseColor("#331900")).geodesic(false)));
            }
        }
    }



    //Esta funci??n permite cargar el fragment de informaci??n, tecnicamente el fragment ya esta ahi, pero vuelve todo visible desde el otro lado
    public boolean infoOfMarker(Marker marker){
        Log.d("creandoOnClick", "estoy dentro del infoOfMarker");
        for(Marcador m : this.marcadores){
            Log.d("creandoOnClick", "estoy dentro del for de infoOfMarker");
            if(marker.getTitle().equals(m.getNombre())){
                Log.d("creandoOnClick", "estoy dentro del if del for de infoOfMarker");
                this.params.weight = 1.75f;
                this.infoFragment.setLayoutParams(this.params);
                this.markerViewModel.setData(m);
                break;
            }
        }
        return true;
    }

    //La otra parte para pedir los permisos de aplicaci??n, esta anula la pregunta inicial para verificar si se concedieron los permisos una vez concedidos
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
                return;
            }

        }
    }

    //Esta funci??n y la siguiente clase son para poder seguir al usuario mientras se mueve
    private void getMyLocation(){
        this.mMap.getUiSettings().setAllGesturesEnabled(false);
        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
        this.placesClient = Places.createClient(MapsActivity.this);
        this.myLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        try{
            this.mMap.setMyLocationEnabled(true);
            this.mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        catch(SecurityException ex){
            Log.d("errorUbicacion",ex.getMessage()+" error de seguridad en get my Location");
        }
        catch(NullPointerException ex){
            Log.d("errorUbicacion",ex.getMessage()+" error de null pointer en get my Location");
        }
        new Thread(new FollowerRunnable()).start();
    }

    private class FollowerRunnable implements Runnable{

        @Override
        public void run() {
            follower();
        }

        private void follower(){
            try{
                Log.d("perseguir","estoy dentro del try de el follower");
                Task<Location> locationResult = MapsActivity.this.myLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(MapsActivity.this, task -> logicGetMyLocation(task));
            }
            catch(SecurityException ex){
                Log.d("errorUbicacion",ex.getMessage()+" error de seguridad en follower");
            }
            catch(NullPointerException ex){
                Log.d("errorUbicacion",ex.getMessage()+" error de null pointer en follower");
            }
        }

        private void logicGetMyLocation(Task<Location> task){
            Log.d("perseguir","estoy dentro de logicGetMyLocation");
            Location location;
            if(task.isSuccessful()){
                location = task.getResult();
                if(location != null){
                    MapsActivity.this.mMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude())));
                }
            }else {
                Log.d("errorUbicacion", "La ubicaci??n actual es nula. Usando predeterminada.");
                Log.e("errorUbicacion", "Exception: %s", task.getException());
                mMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(new LatLng(
                                MapsActivity.this.marcadores.get(0).getLatitud(),
                                MapsActivity.this.marcadores.get(0).getLongitud()
                        ), 17));
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        }
    }
}