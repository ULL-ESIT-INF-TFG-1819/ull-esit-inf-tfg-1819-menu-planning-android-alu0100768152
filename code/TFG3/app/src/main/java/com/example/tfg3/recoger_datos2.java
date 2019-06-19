package com.example.tfg3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg3.CarpetaAPI.Constantes;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class recoger_datos2 extends AppCompatActivity {

    private RadioButton rb_balanceada, rb_alta_proteina, rb_baja_grasas, rb_baja_carbohidratos, rbAceiteOliva, rbOtroAceite;
    private CheckBox check_vegetariana, check_vegana, check_sin_alcohol, check_reducida_azucar, check_sin_cacahuetes, check_sin_nueces, check_balanceada;

    private static final String TAG = "RECETA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_datos2);

       /* rb_balanceada = (RadioButton) findViewById(R.id.rb_balanceada);
        rb_alta_proteina = (RadioButton) findViewById(R.id.rb_alta_proteina);
        rb_baja_grasas = (RadioButton) findViewById(R.id.rb_baja_grasas);
        rb_baja_carbohidratos = (RadioButton) findViewById(R.id.rb_baja_carbohidratos);*/
        rbAceiteOliva = (RadioButton) findViewById(R.id.rbAceiteOliva);
        rbOtroAceite = (RadioButton) findViewById(R.id.rbOtroAceite);

        check_vegetariana = (CheckBox) findViewById(R.id.check_vegetariana);
        check_vegana = (CheckBox) findViewById(R.id.check_vegana);
        check_sin_alcohol = (CheckBox) findViewById(R.id.check_sin_alcohol);
        check_reducida_azucar = (CheckBox) findViewById(R.id.check_reducida_azucar);
        check_sin_cacahuetes = (CheckBox) findViewById(R.id.check_sin_cacahuetes);
        check_sin_nueces = (CheckBox) findViewById(R.id.check_sin_nueces);
        check_balanceada = (CheckBox) findViewById(R.id.check_balanceada);


        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }




    public void Guardar(View view) {



        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper( this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //BaseDeDatos.delete("datosUsuario", "id=01", null);


        //Los datos que obtengo del layout anterior:
        //Integer.parseInt() Esto es para convertir un string to int
        //int edad = Integer.parseInt(getIntent().getStringExtra("actividad"));
        Bundle datos = getIntent().getExtras();
        String nombre = datos.getString("nombre");
        String sexo = datos.getString("sexo");
        int edad = Integer.parseInt(datos.getString("edad"));
        int alt = Integer.parseInt(datos.getString("altura"));
        double altura = alt * 1.0;

        double peso = Double.parseDouble(datos.getString("peso"));
        double actividad = datos.getDouble("actividad");




        /*String Diet = "nada";

        if (rb_balanceada.isChecked() == true) {
            Diet = "balanced";
        } else if (rb_alta_proteina.isChecked() == true) {
            Diet = "high-protein";
        }else if (rb_baja_grasas.isChecked() == true) {
            Diet = "low-fat";
        }else if (rb_baja_carbohidratos.isChecked() == true) {
            Diet = "low-carb";
        }*/

        String Health = "nada";

        if (check_vegetariana.isChecked() == true) {
            Health = "vegetarian";
        }
        if (check_vegana.isChecked() == true) {
            Health = "vegan";
        }
        if (check_sin_alcohol.isChecked() == true) {
            Health = "alcohol-free";
        }
        if (check_reducida_azucar.isChecked() == true) {
            Health = "sugar-conscious";
        }
        if (check_sin_cacahuetes.isChecked() == true) {
            Health = "peanut-free";
        }
        if (check_sin_nueces.isChecked() == true) {
            Health = "tree-nut-free";
        }
        if (check_balanceada.isChecked() == true) {
            Health = "alcohol-free";
        }

        //Constantes.setHEALTH(Health);

        String aceiteOliva = "nada";

        if(rbAceiteOliva.isChecked() == true){
            aceiteOliva = "si";
        }else if(rbOtroAceite.isChecked() == true){
            aceiteOliva = "no";
        }


        // ----------------------- Calculo de Kcal, grasas, proteinas e hidratos -------------------


        // ------------------------------------------------------------------------------






        if (Health != "nada" && aceiteOliva != "nada" ){

            ContentValues registro = new ContentValues();

            int id = 01;

            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("sexo", sexo);
            registro.put("edad", edad);
            registro.put("altura", altura);
            registro.put("peso", peso);
            registro.put("actividad", actividad);
            //registro.put("diet", Diet);
            registro.put("health", Health);
            registro.put("aceiteOliva", aceiteOliva);
           /* registro.put("kcal_dia", kcal_dia);
            registro.put("grasas", grasas);
            registro.put("proteinas", proteinas);
            registro.put("hidratos", hidratos);*/


            Cursor fila = BaseDeDatos.rawQuery("select id from datosUsuario where id=01", null);

            if(fila.moveToFirst()){

                int cantidad = BaseDeDatos.update("datosUsuario", registro, "id=01", null);

                if(cantidad == 1){
                    BaseDeDatos.close();

                    GuardarMacronutrientes();
                }

            }


            BaseDeDatos.insert("datosUsuario", null, registro);

            BaseDeDatos.close();

            GuardarMacronutrientes();



        } else{
            Toast.makeText(this, "Debes indicar el tipo de dieta a seguir y si utilizas aceite de oliva", Toast.LENGTH_LONG).show();
        }


    }

    public void GuardarMacronutrientes() {
        Intent siguiente = new Intent(this, DatosAPI.class);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper( this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila = BaseDeDatos.rawQuery("select nombre, sexo, edad, altura, peso, actividad, health, aceiteOliva from datosUsuario where id=01", null);


        String nombre = "";
        String sexo = "";
        int edad = 0;
        Double altura = 0.0;
        Double peso = 0.0;
        Double actividad = 0.0;
        //String diet = "";
        String health = "";
        String aceiteOliva = "";

        if(fila.moveToFirst()){
            nombre = fila.getString(0);
            sexo = fila.getString(1);
            edad = Integer.parseInt(fila.getString(2));
            altura = Double.valueOf(fila.getString(3)).doubleValue();
            peso = Double.valueOf(fila.getString(4)).doubleValue();
            actividad = Double.valueOf(fila.getString(5)).doubleValue();
            //diet = fila.getString(6);
            health = fila.getString(6);
            aceiteOliva = fila.getString(7);


        }

       // BaseDeDatos.close();


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



        ContentValues registros = new ContentValues();

        int id = 01;

        registros.put("id", id);
        registros.put("kcal_dia", kcal_dia);
        registros.put("grasas", grasas);
        registros.put("proteinas", proteinas);
        registros.put("hidratos", hidratos);


        Cursor fila2 = BaseDeDatos.rawQuery("select id from macronutrientes where id=01", null);

        if(fila2.moveToFirst()){

            int cantidad = BaseDeDatos.update("macronutrientes", registros, "id=01", null);

            if(cantidad == 1){
                BaseDeDatos.close();

                startActivity(siguiente);

            }

        }

        BaseDeDatos.insert("macronutrientes", null, registros);

        BaseDeDatos.close();

        startActivity(siguiente);


    }


}
