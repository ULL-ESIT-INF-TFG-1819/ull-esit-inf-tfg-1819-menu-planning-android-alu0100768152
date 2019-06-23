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

public class AnadirDespensa extends AppCompatActivity {

    private EditText et_nombre, et_gramos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_despensa);
    }

    public void anadir(View view){

        et_nombre = (EditText) findViewById(R.id.mod_nombre);
        et_gramos = (EditText) findViewById(R.id.mod_gramos);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila3 = BaseDeDatos.rawQuery("select count(*) from despensa", null);

        int tamBD=0;
        if (fila3.moveToFirst()) {
            tamBD = Integer.parseInt(fila3.getString(0));
        }

        ContentValues registro = new ContentValues();

        String ingrediente = et_nombre.getText().toString();
        String gramos_text = et_gramos.getText().toString();

        double gramos = Double.parseDouble(gramos_text);

        if(tamBD == 0){

            registro.put("id", 0);
            registro.put("ingrediente", ingrediente);
            registro.put("gramos", gramos);
            registro.put("tiene", "si");

        }else{
            registro.put("id", tamBD);
            registro.put("ingrediente", ingrediente);
            registro.put("gramos", gramos);
            registro.put("tiene", "si");
        }


        Cursor fila = BaseDeDatos.rawQuery("select id from despensa where id="+tamBD, null);

        if(fila.moveToFirst()) {

            BaseDeDatos.update("despensa", registro, "id=" + tamBD, null);
        }else{
            BaseDeDatos.insert("despensa", null, registro);
        }

        BaseDeDatos.close();

        Intent siguiente = new Intent(this, Despensa.class);
        startActivity(siguiente);

    }
}
