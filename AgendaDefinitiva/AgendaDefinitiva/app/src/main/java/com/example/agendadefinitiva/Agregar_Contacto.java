
package com.example.agendadefinitiva;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.DatePickerDialog;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.Calendar;

public class Agregar_Contacto extends AppCompatActivity {
    EditText et_nombre;
    EditText et_apellido;
    EditText et_telefono;
    TextView et_cumple;
    Button bt_guardar ,bt_mostrar,bt_cumple;
    Calendar calendario;
    int anioActual,diaActual,mesActual;
    DatePickerDialog fecha;
    String diaN="", mesN="", anioN="", fechaN="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_telefono = (EditText) findViewById(R.id.et_telefono);

        bt_guardar = (Button) findViewById(R.id.bt_guardar);
        bt_cumple = (Button) findViewById(R.id.bt_cumple);

        et_cumple = (TextView) findViewById(R.id.et_cumple);

        bt_cumple.setOnClickListener(new View.OnClickListener() {

            public void onClick(View vista){
                calendario = Calendar.getInstance();
                anioActual = calendario.get(Calendar.YEAR);
                mesActual = calendario.get(Calendar.MONTH);
                diaActual = calendario.get(Calendar.DAY_OF_MONTH);
                fecha  = new DatePickerDialog(Agregar_Contacto.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                                mes = mes+1;
                                anioN = String.valueOf(anio);
                                mesN = String.valueOf(mes);
                                diaN = String.valueOf(dia);
                                et_cumple.setText(dia + "/" + mes + "/" + anio);
                                fechaN=dia + "/" + mes + "/" + anio;
                            }
                        }, anioActual, mesActual, diaActual);
                fecha.show();
            }

        });

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar(et_nombre.getText().toString(),et_apellido.getText().toString(),et_telefono.getText().toString(),fechaN);
            }
        });


    }
    private void guardar(String Nombre,String Apellido,String Telefono, String fechaN){
        BaseHelper helper = new BaseHelper(this,"DEMO",null,1,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(!Nombre.isEmpty() && !Apellido.isEmpty() && !Telefono.isEmpty()){
            try {
                // como contenedor de valores o direccionarios
                ContentValues c = new ContentValues();
                c.put("Nombre", Nombre);
                c.put("Apellido", Apellido);
                c.put("Telefono", Telefono);
                c.put("Dia", fechaN);
                db.insert("PERSONAS", null, c);
                db.close();
                Toast.makeText(this, R.string.contactoGuardado, Toast.LENGTH_LONG).show();
                et_telefono.setText("");
                et_nombre.setText("");
                et_apellido.setText("");
                et_cumple.setText("");
            } catch (Exception e) {
                Toast.makeText(this, "error :" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }else {
            Toast.makeText(this, R.string.completarCampos, Toast.LENGTH_SHORT).show();
        }

    }
}
