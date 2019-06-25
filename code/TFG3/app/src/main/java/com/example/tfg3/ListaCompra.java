package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaCompra extends AppCompatActivity {

    ArrayList<String> listDatos;
    List<Row> rows;

    //RecyclerView recyclerId;
    private ListView listCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

       //recyclerId = (RecyclerView) findViewById(R.id.recyclerId);
        //recyclerId.setLayoutManager(new LinearLayoutManager(this));
        //listCompra = (ListView) findViewById(R.id.listCompra);

        listCompra = (ListView) findViewById(android.R.id.list);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila2 = BaseDeDatos.rawQuery("select ultimo_id from datos_menu where id=01", null);

        //ArrayList<String> ingredientes = new ArrayList<String>();


        int tamBD=0;
        if (fila2.moveToFirst()) {
            tamBD = Integer.parseInt(fila2.getString(0));
        }

        int id_plato;
        String ingrediente;
        double gramos;
        int raciones = 0;

        rows = new ArrayList<Row>();
        Row row = null;

        for(int i=0; i<tamBD; i++){
            Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente, gramos from lista_ingredientes where id="+i + " and tiene='no'", null);

            if (fila.moveToFirst()) {
                id_plato = Integer.parseInt(fila.getString(0));
                ingrediente = fila.getString(1);
                gramos = Double.valueOf(fila.getString(2)).doubleValue();

                Cursor fila3 = BaseDeDatos.rawQuery("select raciones from platos where id="+id_plato, null);

                if (fila3.moveToFirst()) {
                    raciones = Integer.parseInt(fila3.getString(0));
                }
                row = new Row();
                gramos = gramos/raciones;
                DecimalFormat formato = new DecimalFormat("#.##");
                row.setTitle(ingrediente+" ("+ formato.format(gramos)+" g)");
                row.setId(i);
                rows.add(row);
                //ingredientes.add(ingrediente+" ("+ formato.format(gramos)+" g)");

            }
        }
        BaseDeDatos.close();

        listCompra.setAdapter(new AdapterListaCompra(this, rows));

        listCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent siguiente = new Intent(ListaCompra.this, EstaEnLaDespensa.class);
                siguiente.putExtra("id", ""+rows.get(position).getId());
                startActivity(siguiente);
               /* Toast.makeText(ListaCompra.this,
                        rows.get(position).getTitle(), Toast.LENGTH_SHORT)
                        .show();*/
            }
        });

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_lista, ingredientes);
        listCompra.setAdapter(adapter);

        /*listCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (listCompra.getItemAtPosition(position).isChecked() == true) {
            }
        });*/


        //AdapterListaCompra adapter = new AdapterListaCompra(ingredientes);
        //recyclerId.setAdapter(adapter);

    }

    /////////////////////////////// MENU 3 botones ///////////////////////////////

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuoverflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.itemDatos){
            Intent siguiente = new Intent(this, recoger_datos1.class);
            startActivity(siguiente);
        }

        if(id == R.id.itemCalorias){
            Intent siguiente = new Intent(this, Mostrar_datos.class);
            startActivity(siguiente);
        }

        if(id == R.id.id_buscar){
            Intent siguiente = new Intent(this, DatosAPI.class);
            startActivity(siguiente);
        }

        if(id == R.id.id_menu){
            Intent siguiente = new Intent(this, CrearMenu.class);
            startActivity(siguiente);
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////////


}
