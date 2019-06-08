package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class Mostrar_datos extends AppCompatActivity {

    private TextView tvEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);


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

        BaseDeDatos.close();


        altura = altura / 100;


        double kcal_dia = 0;

        if (sexo.equals("hombre")) {
            if (edad < 18) {
                kcal_dia = 17.7 * peso + 651;
            } else if (edad <= 30) {
                kcal_dia = 15.4 * peso - 27 * altura + 717;
            } else if (edad <= 60) {
                kcal_dia = 11.3 * peso + 16 * altura + 901;
            } else if (edad > 60) {
                kcal_dia = 8.8 * peso + 1.128 * altura + 1.071;
            }

        }

        if (sexo.equals("mujer")) {
            if (edad < 18) {
                kcal_dia = 12.2 * peso + 746;
            } else if (edad <= 30) {
                kcal_dia = 13.3 * peso + 334 * altura + 35;
            } else if (edad <= 60) {
                kcal_dia = 8.7 * peso - 25 * altura + 865;
            } else if (edad > 60) {
                kcal_dia = 9.2 * peso * 637 * altura - 302;
            }
        }

        kcal_dia = kcal_dia * actividad;

        double grasas = kcal_dia;

        if (aceiteOliva.equals("si")) {
            grasas = (kcal_dia * 35) / 100;
        } else if (aceiteOliva.equals("no")) {
            grasas = (kcal_dia * 30) / 100;
        }

        double hidratos = kcal_dia - grasas;
        double proteinas = 0;

        grasas = grasas / 9;

        if (actividad == 1 || actividad == 1.55) {
            proteinas = peso * 0.8;
        } else if (actividad == 1.75 && sexo.equals("mujer")) {
            proteinas = peso;
        } else if (actividad == 1.75 && sexo.equals("hombre")) {
            proteinas = peso * 1.2;
        } else if (actividad == 2.10 && sexo.equals("mujer")) {
            proteinas = peso * 1.2;
        } else if (actividad == 2.10 && sexo.equals("hombre")) {
            proteinas = peso * 1.4;
        }

        hidratos = hidratos - (proteinas * 4);
        hidratos = hidratos / 4;

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


    public void Siguiente2(View view) {
        Intent siguiente = new Intent(this, DatosAPI.class);

        startActivity(siguiente);

    }

}