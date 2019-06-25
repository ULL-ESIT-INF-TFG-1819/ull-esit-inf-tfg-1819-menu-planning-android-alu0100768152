package com.example.tfg3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EstaEnLaDespensa extends AppCompatActivity {

    private ListView lv_despensa;
    private static final String TAG = "RECETA";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esta_en_la_despensa);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        lv_despensa = (ListView) findViewById(R.id.lv_despensa);


        Bundle datos = getIntent().getExtras();
        id = Integer.parseInt(datos.getString("id"));

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila2 = BaseDeDatos.rawQuery("select count(*) from despensa", null);

        ArrayList<String> listDatos = new ArrayList<String>(); //= new ArrayList<Ingrediente_gramos>()

        int tamBD=0;
        if (fila2.moveToFirst()) {
            tamBD = Integer.parseInt(fila2.getString(0));
        }

        String ingrediente;
        double gramos;

        for(int i=0; i<tamBD; i++){
            Cursor fila = BaseDeDatos.rawQuery("select ingrediente, gramos from despensa where id="+i + " and tiene='si'", null); //+ " and tiene=si"

            if (fila.moveToFirst()) {

                ingrediente = fila.getString(0);
                gramos = Double.valueOf(fila.getString(1)).doubleValue();

                DecimalFormat formato = new DecimalFormat("#.##");
                String var_gramos= formato.format(gramos);

                gramos = Double.parseDouble(var_gramos);

                listDatos.add(ingrediente); //ingrediente+" ("+ formato.format(gramos)+" g)"

            }
        }

        BaseDeDatos.close();

        String var_ingredientes [] = new String[listDatos.size()];


        for(int i=0; i<listDatos.size(); i++){
            var_ingredientes [i] = listDatos.get(i);
            //Log.i(TAG, listDatos.get(i));
        }


      /*  ArrayList<String> lista_despensa = new ArrayList<String>();

        for(int i=0; i<listDatos.size(); i++){
            lista_despensa.add(listDatos.get(i).getIngrediente());
        }*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_ingrediente, var_ingredientes);
        lv_despensa.setAdapter(adapter);


        lv_despensa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                String var = ""+ lv_despensa.getItemAtPosition(i);
                comprobar(var);

                Log.i(TAG, ""+lv_despensa.getItemAtPosition(i));

            }
        });
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

    public void comprobar(String ingrediente){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Cursor fila2 = BaseDeDatos.rawQuery("select gramos from despensa where ingrediente='"+ingrediente+"' and tiene='si'", null);

        double gramos=0.0;
        if (fila2.moveToFirst()) {
            gramos = Double.valueOf(fila2.getString(0)).doubleValue();
        }
        Cursor fila3 = BaseDeDatos.rawQuery("select gramos from lista_ingredientes where id="+id, null);
        double gramos_lista=0.0;
        if (fila3.moveToFirst()) {
            gramos_lista = Double.valueOf(fila3.getString(0)).doubleValue();
        }

        if(gramos_lista<=gramos){

            Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente, gramos from lista_ingredientes where id="+id, null);

            int id_plato = 0;
            String ingrediente_var = "";
            double gramos_var = 0.0;
            String tiene = "si";

            if(fila.moveToFirst()) {

                id_plato = Integer.parseInt(fila.getString(0));
                ingrediente_var = fila.getString(1);
                gramos_var = Double.valueOf(fila.getString(2)).doubleValue();
            }


            ContentValues registro = new ContentValues();

            registro.put("id", id);
            registro.put("id_plato", id_plato);
            registro.put("ingrediente", ingrediente_var);
            registro.put("gramos", gramos_var);
            registro.put("tiene", tiene);

            BaseDeDatos.update("lista_ingredientes", registro, "id=" + id, null);

            BaseDeDatos.close();

            Intent siguiente = new Intent(this, ListaCompra.class);
            startActivity(siguiente);
        }else{
            gramos_lista = gramos_lista-gramos;

            Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente from lista_ingredientes where id="+id, null);

            int id_plato = 0;
            String ingrediente_var = "";
            String tiene = "no";

            if(fila.moveToFirst()) {

                id_plato = Integer.parseInt(fila.getString(0));
                ingrediente_var = fila.getString(1);
            }


            ContentValues registro = new ContentValues();

            registro.put("id", id);
            registro.put("id_plato", id_plato);
            registro.put("ingrediente", ingrediente_var);
            registro.put("gramos", gramos_lista);
            registro.put("tiene", tiene);

            BaseDeDatos.update("lista_ingredientes", registro, "id=" + id, null);

            BaseDeDatos.close();

            Intent siguiente = new Intent(this, ListaCompra.class);
            startActivity(siguiente);

        }
    }

    public void Lista_compra(View view){
        Intent siguiente = new Intent(this, AnadirDespensa2.class);
        siguiente.putExtra("id", ""+id);
        startActivity(siguiente);

    }
}
