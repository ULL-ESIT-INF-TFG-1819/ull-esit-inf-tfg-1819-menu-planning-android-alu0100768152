package com.example.tfg3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListaCompra extends AppCompatActivity {

    ArrayList<String> listDatos;

    RecyclerView recyclerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        recyclerId = (RecyclerView) findViewById(R.id.recyclerId);
        recyclerId.setLayoutManager(new LinearLayoutManager(this));


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila2 = BaseDeDatos.rawQuery("select count(*) from lista_ingredientes", null);

        ArrayList<String> ingredientes = new ArrayList<String>();

        int tamBD=0;
        if (fila2.moveToFirst()) {
            tamBD = Integer.parseInt(fila2.getString(0));
        }

        int id_plato;
        String ingrediente;
        double gramos;
        int raciones = 0;

        for(int i=0; i<tamBD; i++){
            Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente, gramos from lista_ingredientes where id="+i, null);

            if (fila.moveToFirst()) {
                id_plato = Integer.parseInt(fila.getString(0));
                ingrediente = fila.getString(1);
                gramos = Double.valueOf(fila.getString(2)).doubleValue();

                Cursor fila3 = BaseDeDatos.rawQuery("select raciones from platos where id="+id_plato, null);

                if (fila3.moveToFirst()) {
                    raciones = Integer.parseInt(fila3.getString(0));
                }

                gramos = gramos/raciones;
                DecimalFormat formato = new DecimalFormat("#.##");
                ingredientes.add(ingrediente+" ("+ formato.format(gramos)+" g)");

            }
        }
        BaseDeDatos.close();

        AdapterListaCompra adapter = new AdapterListaCompra(ingredientes);
        recyclerId.setAdapter(adapter);
    }
}
