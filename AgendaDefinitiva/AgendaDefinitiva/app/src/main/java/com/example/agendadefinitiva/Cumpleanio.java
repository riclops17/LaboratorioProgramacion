package com.example.agendadefinitiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Cumpleanio extends AppCompatActivity {
    // referenciar el listview para q cuadno yo toque un elemento pueda obtener lo q esta adentro
    ListView listView;
    ArrayList<String> listado;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarListado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumpleanio);
        listView = (ListView) findViewById(R.id.lvCumpleanio);

        CargarListado();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int clave = Integer.parseInt(listado.get(i).split(" ")[0]);
                String nombre = listado.get(i).split(" ")[1];
                String apellido = listado.get(i).split(" ")[2];
                String telefono = listado.get(i).split(" ")[3];
                Intent intent = new Intent(Cumpleanio.this,Modificar.class);
                //enviamos parametros al intent modificar
                intent.putExtra("Id",clave);
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Apellido",apellido);
                intent.putExtra("Telefono",telefono);
                startActivity(intent);


            }
        });
    }

    private void CargarListado(){
        listado = ListaPersonas();
        //adapter me va servir para tranformar los datos y dejar en una vista como yo quiero
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listado);
        listView.setAdapter(adapter);
    }
    private ArrayList<String>ListaPersonas(){
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this,"DEMO",null,1,null);
        SQLiteDatabase db = helper.getReadableDatabase();
        //Aca buscamos el dia de hoy
        Calendar calendario = Calendar.getInstance();
        String diaN=String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
        String mesN=String.valueOf(calendario.get(Calendar.MONTH)+1);//Se le suma 1 al mes porque android los cuenta a partir de 0... o sea... enero seria el mes 0
        //Aca se hace bien el formato del dia mes para que sea de longitud 4... por ejemplo 1/1 seria 01/01
        if(diaN.length()<2){
            diaN = "0"+diaN;
        }
        if(mesN.length()<2){
            mesN = "0"+mesN;
        }
        //Este es el string de busqueda
        String fechaHoy = diaN+"/"+mesN;
        ///////////////////////

        String sql = "select Id,Nombre,Apellido,Telefono,Dia from Personas where Dia like '"+fechaHoy+"%'";

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{                                                                            // agregado
                String linea = c.getInt(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4);
                datos.add(linea);
            }while (c.moveToNext());
        }
        db.close();
        return datos;
    }


}
