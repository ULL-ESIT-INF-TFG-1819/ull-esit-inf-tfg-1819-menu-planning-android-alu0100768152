package com.example.tfg3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class recoger_datos1 extends AppCompatActivity {


    private EditText etEdad, etAltura, etPeso, etNombre;
    private RadioButton rbHombre, rbMujer, rbAct0, rbAct1, rbAct2, rbAct3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_datos1);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etEdad = (EditText) findViewById(R.id.etEdad);
        rbHombre = (RadioButton) findViewById(R.id.rbHombre);
        rbMujer = (RadioButton) findViewById(R.id.rbMujer);
        etAltura = (EditText) findViewById(R.id.etAltura);
        etPeso = (EditText) findViewById(R.id.etPeso);
        rbAct0 = (RadioButton) findViewById(R.id.rbAct0);
        rbAct1 = (RadioButton) findViewById(R.id.rbAct1);
        rbAct2 = (RadioButton) findViewById(R.id.rbAct2);
        rbAct3 = (RadioButton) findViewById(R.id.rbAct3);


        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    /////////////////////////////// MENU 3 botones ///////////////////////////////

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.itemDatos){
            Intent siguiente = new Intent(this, recoger_datos1.class);
            startActivity(siguiente);
        }

        if(id == R.id.itemCalorias){
            Intent siguiente = new Intent(this, Mostrar_datos.class);
            startActivity(siguiente);
        }

        if(id == R.id.id_buscar){
            Intent siguiente = new Intent(this, DatosAPI.class);
            startActivity(siguiente);
        }

        if(id == R.id.id_menu){
            Intent siguiente = new Intent(this, CrearMenu.class);
            startActivity(siguiente);
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////////


    //Botón para pasar a la siguiente actividad
    public void Siguientee(View view) {
        Intent siguiente = new Intent(this, recoger_datos2.class);
        String sexo = "medio";

        if (rbHombre.isChecked() == true) {
            sexo = "hombre";
        } else if (rbMujer.isChecked() == true) {
            sexo = "mujer";
        }

        double actividad = 0;

        if (rbAct0.isChecked() == true) {
            actividad = 1;
        } else if (rbAct1.isChecked() == true) {
            actividad = 1.55;
        } else if (rbAct2.isChecked() == true) {
            actividad = 1.75;
        } else if (rbAct3.isChecked() == true) {
            actividad = 2.10;
        }

        String nombre = etNombre.getText().toString();
        String edad = etEdad.getText().toString();
        String altura = etAltura.getText().toString();
        String peso = etPeso.getText().toString();


        siguiente.putExtra("nombre", nombre);
        siguiente.putExtra("edad", edad);
        siguiente.putExtra("sexo", sexo);
        siguiente.putExtra("altura", altura);
        siguiente.putExtra("peso", peso);
        siguiente.putExtra("actividad", actividad);

        if (!nombre.isEmpty() && !edad.isEmpty() && !altura.isEmpty() && !peso.isEmpty() && sexo != "medio" && actividad != 0){
            startActivity(siguiente);
        } else {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }

    }


}
