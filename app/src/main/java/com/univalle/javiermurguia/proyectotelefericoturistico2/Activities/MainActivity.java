package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textViewAlias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textViewAlias = findViewById(R.id.textViewAlias);
        Intent intento = getIntent();
        try{
            if(intento.getStringExtra("user_name").length() > 0){
                this.textViewAlias.setText("Bienvenido "+intento.getStringExtra("user_name"));
            }
            else {
                this.textViewAlias.setText("");
            }
        }
        catch(NullPointerException ex){
            this.textViewAlias.setText("");
        }

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(MainActivity.this, com.univalle.javiermurguia.proyectotelefericoturistico2.Activities.ARActivity.class));
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
                startActivity(new Intent(MainActivity.this, com.univalle.javiermurguia.proyectotelefericoturistico2.Activities.LoadingActivity.class));
            }
        });
    }
}