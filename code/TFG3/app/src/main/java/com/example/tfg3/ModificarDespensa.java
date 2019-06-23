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

import java.util.ArrayList;

public class ModificarDespensa extends AppCompatActivity {

    private EditText mod_nombre, mod_gramos;

    private String ingrediente;
    private String gramos;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_despensa);

        mod_nombre = (EditText) findViewById(R.id.mod_nombre);
        mod_gramos = (EditText) findViewById(R.id.mod_gramos);

        Bundle datos = getIntent().getExtras();
        ingrediente = datos.getString("ingrediente");
        gramos = datos.getString("gramos");
        id = datos.getString("id");


        mod_nombre.setText(ingrediente);
        mod_gramos.setText(gramos);

    }

    public void modificar(View view){

        mod_nombre = (EditText) findViewById(R.id.mod_nombre);
        mod_gramos = (EditText) findViewById(R.id.mod_gramos);

        String ingrediente_var = mod_nombre.getText().toString();
        String gramos_text = mod_gramos.getText().toString();

        double gramos_var = Double.parseDouble(gramos_text);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("ingrediente", ingrediente_var);
        registro.put("gramos", gramos_var);
        registro.put("tiene", "si");

        BaseDeDatos.update("despensa", registro, "id=" + id, null);

        BaseDeDatos.close();

        Intent siguiente = new Intent(this, Despensa.class);
        startActivity(siguiente);

    }

    public void borrar(View view){

        double gramos_var = Double.parseDouble(gramos);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("ingrediente", ingrediente);
        registro.put("gramos", gramos_var);
        registro.put("tiene", "no");

        BaseDeDatos.update("despensa", registro, "id=" + id, null);

        BaseDeDatos.close();

        Intent siguiente = new Intent(this, Despensa.class);
        startActivity(siguiente);

    }
}
