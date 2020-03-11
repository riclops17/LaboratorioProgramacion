package com.example.agendadefinitiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Agenda extends AppCompatActivity {

    private Button  agregar, mostrar, cumpleanios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_agenda);

        agregar = (Button) findViewById(R.id.idAgregar);
        mostrar = (Button) findViewById(R.id.idMostrarContacto);
        cumpleanios = (Button) findViewById(R.id.idConsultaCumpleanios);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_Agenda.this,Agregar_Contacto.class));
            }
        });

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_Agenda.this,Listado.class));
            }
        });

        cumpleanios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_Agenda.this,Cumpleanio.class));
            }
        });
    }




}
