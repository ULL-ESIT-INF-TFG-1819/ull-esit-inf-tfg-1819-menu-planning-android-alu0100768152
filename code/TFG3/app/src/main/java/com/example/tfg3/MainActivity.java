package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg3.CarpetaAPI.Constantes;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {

    public Constantes CONSTANTE = new Constantes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    public void Entrar(View view) {


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id from datosUsuario where id=01", null);

        if(fila.moveToFirst()){
            Intent siguiente = new Intent(this, CrearMenu.class);
            startActivity(siguiente);
        }else{
            Intent siguiente = new Intent(this, recoger_datos1.class);
            startActivity(siguiente);
        }


    }

}
