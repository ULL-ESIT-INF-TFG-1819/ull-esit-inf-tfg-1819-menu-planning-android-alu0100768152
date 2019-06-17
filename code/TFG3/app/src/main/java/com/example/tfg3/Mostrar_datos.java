package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class Mostrar_datos extends AppCompatActivity {

    private TextView tvEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tvEdad = (TextView) findViewById(R.id.tvEdad);

        //Integer.parseInt() Esto es para convertir un string to int
        //int edad = Integer.parseInt(getIntent().getStringExtra("actividad"));
        /*Bundle datos = getIntent().getExtras();
        String sexo = datos.getString("sexo");
        int edad = Integer.parseInt(datos.getString("edad"));
        int altura = Integer.parseInt(datos.getString("altura"));
        double peso = Double.parseDouble(datos.getString("peso"));
        double actividad = datos.getDouble("actividad");
        String aceiteOliva = datos.getString("aceiteOliva");*/

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select nombre, sexo, edad, altura, peso, actividad, diet, health, aceiteOliva from datosUsuario where id=01", null);



        String nombre = "";
        String sexo = "";
        int edad = 0;
        Double altura = 0.0;
        Double peso = 0.0;
        Double actividad = 0.0;
        String diet = "";
        String health = "";
        String aceiteOliva = "";
        Double kcal_dia = 0.0;
        Double grasas = 0.0;
        Double proteinas = 0.0;
        Double hidratos = 0.0;





       if(fila.moveToFirst()){
            nombre = fila.getString(0);
            sexo = fila.getString(1);
            edad = Integer.parseInt(fila.getString(2));
            altura = Double.valueOf(fila.getString(3)).doubleValue();
            peso = Double.valueOf(fila.getString(4)).doubleValue();
            actividad = Double.valueOf(fila.getString(5)).doubleValue();
            diet = fila.getString(6);
            health = fila.getString(7);
            aceiteOliva = fila.getString(8);

        }

        Cursor paca = BaseDeDatos.rawQuery("select kcal_dia, grasas, proteinas, hidratos from macronutrientes where id=01", null);

        if(paca.moveToFirst()){
            kcal_dia = Double.valueOf(paca.getString(0)).doubleValue();
            grasas = Double.valueOf(paca.getString(1)).doubleValue();
            proteinas = Double.valueOf(paca.getString(2)).doubleValue();
            hidratos = Double.valueOf(paca.getString(3)).doubleValue();
        }

        BaseDeDatos.close();




        tvEdad.setText("Estas son las kcal que debes consumir al día: " + String.format("%.2f", kcal_dia) + " Kcal \n\n" +
                "Grasas: " + String.format("%.2f", grasas) + "g \n\n" +
                "Proteinas: " + String.format("%.2f", proteinas) + "g \n\n " +
                "Hidratos de carbono: " + String.format("%.2f", hidratos) + "g \n\n y tu nombre es:" + nombre);

        /*
        Bundle datos = getIntent().getExtras();
        int actividad= datos.getInt("actividad");
        tvEdad.setText("Esta es tu edad: " + actividad);
        */

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.itemDatos){
            Intent siguiente = new Intent(this, recoger_datos1.class);
            startActivity(siguiente);
        } else if(id == R.id.itemCalorias){
            Intent siguiente = new Intent(this, Mostrar_datos.class);
            startActivity(siguiente);
        }
        return super.onOptionsItemSelected(item);
    }


    public void Siguiente2(View view) {
        Intent siguiente = new Intent(this, DatosAPI.class);

        startActivity(siguiente);

    }

}