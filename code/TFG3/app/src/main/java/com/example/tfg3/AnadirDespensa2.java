package com.example.tfg3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class AnadirDespensa2 extends AppCompatActivity {


    private EditText et_nombre, et_gramos;
    private String ingrediente;
    private String gramos_text;
    //private double gramos;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_despensa2);
        et_nombre = (EditText) findViewById(R.id.et_nombre_id);
        et_gramos = (EditText) findViewById(R.id.et_gramos_id);


        Bundle datos = getIntent().getExtras();
        id = Integer.parseInt(datos.getString("id"));

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila4 = BaseDeDatos.rawQuery("select ingrediente, gramos from lista_ingredientes where id="+id, null);
        if (fila4.moveToFirst()) {
            ingrediente = fila4.getString(0);
            gramos_text = fila4.getString(1);
        }

        BaseDeDatos.close();

        et_nombre.setText(ingrediente);
        et_gramos.setText(gramos_text);

    }

    public void anadir2(View view){

        ingrediente = et_nombre.getText().toString();
        gramos_text = et_gramos.getText().toString();
        double gramos_var = Double.parseDouble(gramos_text);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila5 = BaseDeDatos.rawQuery("select id_plato from lista_ingredientes where id="+id, null);
        int id_plato = 0;
        if (fila5.moveToFirst()) {
            id_plato = Integer.parseInt(fila5.getString(0));
        }

        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("id_plato", id_plato);
        registro.put("ingrediente", ingrediente);
        registro.put("gramos", gramos_var);
        registro.put("tiene", "si");

        BaseDeDatos.update("lista_ingredientes", registro, "id=" + id, null);


        Cursor fila3 = BaseDeDatos.rawQuery("select count(*) from despensa", null);

        int tamBD=0;
        if (fila3.moveToFirst()) {
            tamBD = Integer.parseInt(fila3.getString(0));
        }

        ContentValues registro2 = new ContentValues();


       // double gramos = Double.parseDouble(gramos_text);

        registro2.put("id", tamBD);
        registro2.put("ingrediente", ingrediente);
        registro2.put("gramos", gramos_var);
        registro2.put("tiene", "si");



        Cursor fila = BaseDeDatos.rawQuery("select id from despensa where id="+tamBD, null);

        if(fila.moveToFirst()) {

            BaseDeDatos.update("despensa", registro2, "id=" + tamBD, null);
        }else{
            BaseDeDatos.insert("despensa", null, registro2);
        }

        BaseDeDatos.close();



        Intent siguiente = new Intent(this, ListaCompra.class);
        startActivity(siguiente);




    }
}
