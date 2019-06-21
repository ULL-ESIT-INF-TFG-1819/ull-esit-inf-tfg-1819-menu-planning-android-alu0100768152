package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;
import com.squareup.picasso.Picasso;

public class MenuDiario extends AppCompatActivity {

    private ImageView imgPrimero, imgSegundo;
    private TextView nombrePrimero, nombreSegundo;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diario);

        imgPrimero = (ImageView) findViewById(R.id.imgPrimero);
        imgSegundo = (ImageView) findViewById(R.id.imgSegundo);
        nombrePrimero = (TextView) findViewById(R.id.nombrePrimero);
        nombreSegundo = (TextView) findViewById(R.id.nombreSegundo);

        Bundle datos = getIntent().getExtras();
        ID = Integer.parseInt(datos.getString("id"));


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select nombre, foto from platos where id="+ID, null);

        String nombre = "";
        String foto = "";

        if (fila.moveToFirst()) {
            nombre = fila.getString(0);
            foto = fila.getString(1);
        }
        int id2 = ID + 1;
        Cursor fila2 = BaseDeDatos.rawQuery("select nombre, foto from platos where id="+id2, null);

        String nombre2 = "";
        String foto2 = "";

        if (fila2.moveToFirst()) {
            nombre2 = fila2.getString(0);
            foto2 = fila2.getString(1);
        }

        BaseDeDatos.close();

        Picasso.get()
                .load(foto)
                .into(imgPrimero);

        nombrePrimero.setText(nombre);

        Picasso.get()
                .load(foto2)
                .into(imgSegundo);

        nombreSegundo.setText(nombre2);

    }


    public void Bprimero(View view) {
        Intent siguiente = new Intent(this, Plato.class);
        siguiente.putExtra("id", ""+ID);
        startActivity(siguiente);
    }
    public void Bsegundo(View view) {
        Intent siguiente = new Intent(this, Plato.class);
        int id = ID+1;
        siguiente.putExtra("id", ""+id);
        startActivity(siguiente);
    }

}
