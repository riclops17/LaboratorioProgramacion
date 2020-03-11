package com.example.agendadefinitiva;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Modificar extends AppCompatActivity {
    EditText et_nombre, et_apellido , et_telefono;
    Button bt_modificar ,bt_eliminar, bt_llamar;
    int id;
    String nombre;
    String apellido;
    String telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        //obtengo el conjunto de datos q pasamos con la ventana anterior con el bundle
        Bundle b = getIntent().getExtras();
        if(b != null){
            // obtengo los valores q cargue mediante el intent
            id = b.getInt("Id");
            nombre = b.getString("Nombre");
            apellido = b.getString("Apellido");
            telefono = b.getString("Telefono");
        }
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        // hay q setear los datos q obtuvimos
        et_nombre.setText(nombre);
        et_apellido.setText(apellido);
        et_telefono.setText(telefono);


        bt_modificar = (Button) findViewById(R.id.bt_modificar);
        bt_eliminar = (Button) findViewById(R.id.bt_eliminar);
        bt_llamar = (Button) findViewById(R.id.bt_llamar);
        bt_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarContacto(view);

            }
        });

        bt_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar(id);
                onBackPressed();

            }
        });

        bt_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar(id,et_nombre.getText().toString(),et_apellido.getText().toString(),et_telefono.getText().toString());
                onBackPressed();
            }
        });
    }

    public void llamarContacto(View view) {
        Intent nico = new Intent(Intent.ACTION_DIAL);
        nico.setData(Uri.parse("tel:" + et_telefono.getText()));
        startActivity(nico);
    }

    private void Modificar(int Id, String Nombre, String Apellido, String Telefono){
        BaseHelper helper = new BaseHelper(this,"DEMO",null,1,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update Personas set Nombre='"+ Nombre +"',Apellido='" + Apellido + "',Telefono='" + Telefono + "'where Id="+Id;
        db.execSQL(sql);
        db.close();
        Toast.makeText(this, R.string.modificar, Toast.LENGTH_LONG).show();
    }
    private void Eliminar(int Id){
        BaseHelper helper = new BaseHelper(this,"DEMO",null,1,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from Personas where Id="+Id;
        db.execSQL(sql);
        db.close();
        Toast.makeText(this, R.string.eliminar, Toast.LENGTH_LONG).show();
    }
}
