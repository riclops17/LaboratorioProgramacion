package com.example.atrapalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        TextView puntajeLabel = (TextView) findViewById(R.id.puntajeLabel);
        TextView puntajeMasAltoLabel =(TextView)findViewById(R.id.puntajeMasAltoLabel);

        int puntaje = getIntent().getIntExtra("PUNTAJE",0);
        puntajeLabel.setText(puntaje + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        int puntajeMasAlto = settings.getInt("PUNTAJE_ALTO",0);
        if(puntaje >puntajeMasAlto){

            puntajeMasAltoLabel.setText("Puntaje mas alto : "+puntaje);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("PUNTAJE_ALTO",puntaje);
            editor.commit();

        }else{
            puntajeMasAltoLabel.setText("Puntaje mas alto : "+puntajeMasAlto);
        }
    }
    public void repetir(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


}
