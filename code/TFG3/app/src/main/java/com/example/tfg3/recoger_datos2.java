package com.example.tfg3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg3.CarpetaAPI.Constantes;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class recoger_datos2 extends AppCompatActivity {

    private RadioButton rb_balanceada, rb_alta_proteina, rb_baja_grasas, rb_baja_carbohidratos, rbAceiteOliva, rbOtroAceite;
    private CheckBox check_vegetariana, check_vegana, check_sin_alcohol, check_reducida_azucar, check_sin_cacahuetes, check_sin_nueces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_datos2);

        rb_balanceada = (RadioButton) findViewById(R.id.rb_balanceada);
        rb_alta_proteina = (RadioButton) findViewById(R.id.rb_alta_proteina);
        rb_baja_grasas = (RadioButton) findViewById(R.id.rb_baja_grasas);
        rb_baja_carbohidratos = (RadioButton) findViewById(R.id.rb_baja_carbohidratos);
        rbAceiteOliva = (RadioButton) findViewById(R.id.rbAceiteOliva);
        rbOtroAceite = (RadioButton) findViewById(R.id.rbOtroAceite);

        check_vegetariana = (CheckBox) findViewById(R.id.check_vegetariana);
        check_vegana = (CheckBox) findViewById(R.id.check_vegana);
        check_sin_alcohol = (CheckBox) findViewById(R.id.check_sin_alcohol);
        check_reducida_azucar = (CheckBox) findViewById(R.id.check_reducida_azucar);
        check_sin_cacahuetes = (CheckBox) findViewById(R.id.check_sin_cacahuetes);
        check_sin_nueces = (CheckBox) findViewById(R.id.check_sin_nueces);


        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }




    public void Guardar(View view) {

        Intent siguiente = new Intent(this, DatosAPI.class);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper( this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        //Los datos que obtengo del layout anterior:
        //Integer.parseInt() Esto es para convertir un string to int
        //int edad = Integer.parseInt(getIntent().getStringExtra("actividad"));
        Bundle datos = getIntent().getExtras();
        String nombre = datos.getString("nombre");
        String sexo = datos.getString("sexo");
        int edad = Integer.parseInt(datos.getString("edad"));
        int altura = Integer.parseInt(datos.getString("altura"));

        double peso = Double.parseDouble(datos.getString("peso"));
        double actividad = datos.getDouble("actividad");




        String Diet = "nada";

        if (rb_balanceada.isChecked() == true) {
            Diet = "balanced";
        } else if (rb_alta_proteina.isChecked() == true) {
            Diet = "high-protein";
        }else if (rb_baja_grasas.isChecked() == true) {
            Diet = "low-fat";
        }else if (rb_baja_carbohidratos.isChecked() == true) {
            Diet = "low-carb";
        }

        String Health = "";

        if (check_vegetariana.isChecked() == true) {
            Health = Health + "vegetarian";
        }
        if (check_vegana.isChecked() == true) {
            Health = Health + "vegan";
        }
        if (check_sin_alcohol.isChecked() == true) {
            Health = Health + "alcohol-free";
        }
        if (check_reducida_azucar.isChecked() == true) {
            Health = Health + "sugar-conscious";
        }
        if (check_sin_cacahuetes.isChecked() == true) {
            Health = Health + "peanut-free";
        }
        if (check_sin_nueces.isChecked() == true) {
            Health = Health + "tree-nut-free";
        }

        //Constantes.setHEALTH(Health);

        String aceiteOliva = "nada";

        if(rbAceiteOliva.isChecked() == true){
            aceiteOliva = "si";
        }else if(rbOtroAceite.isChecked() == true){
            aceiteOliva = "no";
        }

        if (Diet != "nada" && aceiteOliva != "nada" ){

            ContentValues registro = new ContentValues();

            int id = 01;

            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("sexo", sexo);
            registro.put("edad", edad);
            registro.put("altura", altura);
            registro.put("peso", peso);
            registro.put("actividad", actividad);
            registro.put("diet", Diet);
            registro.put("health", Health);
            registro.put("aceiteOliva", aceiteOliva);


            Cursor fila = BaseDeDatos.rawQuery("select id from datosUsuario where id=01", null);

            if(fila.moveToFirst()){

                int cantidad = BaseDeDatos.update("datosUsuario", registro, "id=01", null);

                if(cantidad == 1){
                    BaseDeDatos.close();

                    startActivity(siguiente);
                }

            }


            BaseDeDatos.insert("datosUsuario", null, registro);

            BaseDeDatos.close();

            startActivity(siguiente);

        } else{
            Toast.makeText(this, "Debes indicar el tipo de dieta a seguir y si utilizas aceite de oliva", Toast.LENGTH_LONG).show();
        }


    }


}
