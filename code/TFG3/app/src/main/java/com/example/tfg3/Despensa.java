package com.example.tfg3;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Despensa extends AppCompatActivity {

    //ArrayList<String> listDatos;

    RecyclerView recyclerDespensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despensa);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        recyclerDespensa = (RecyclerView) findViewById(R.id.recyclerDespensa);
        recyclerDespensa.setLayoutManager(new LinearLayoutManager(this));




        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila2 = BaseDeDatos.rawQuery("select count(*) from despensa", null);
        int tamDesp=0;
        if (fila2.moveToFirst()) {
            tamDesp = Integer.parseInt(fila2.getString(0));
        }

        Cursor fila3 = BaseDeDatos.rawQuery("select ultimo_id from datos_menu where id=01", null);

        int taming=0;
        if (fila3.moveToFirst()) {
            taming = Integer.parseInt(fila3.getString(0));
        }

        int id_plato;
        String ingrediente;
        double gramos;

        for(int j=0; j<taming; j++){
            Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente, gramos from lista_ingredientes where id="+j + " and tiene='si'", null);

            if (fila.moveToFirst()) {
                id_plato = Integer.parseInt(fila.getString(0));
                ingrediente = fila.getString(1);
                gramos = Double.valueOf(fila.getString(2)).doubleValue();

                Cursor fila4 = BaseDeDatos.rawQuery("select raciones from platos where id="+id_plato+" and hecho='si'", null);

                if (fila4.moveToFirst()) {
                    for (int i = 0; i < tamDesp; i++) {
                        Cursor fila5 = BaseDeDatos.rawQuery("select id from despensa where id=" + i + " and ingrediente=" + "'" + ingrediente + "' and gramos=" + gramos, null); //+ " and tiene=si"

                        int id;
                        if (fila5.moveToFirst()) {
                            id = Integer.parseInt(fila5.getString(0));

                            ContentValues registro = new ContentValues();

                            registro.put("id", id);
                            registro.put("ingrediente", ingrediente);
                            registro.put("gramos", gramos);
                            registro.put("tiene", "no");
                            BaseDeDatos.update("despensa", registro, "id=" + id, null);
                        }

                        //ingredientes.add(ingrediente+" ("+ formato.format(gramos)+" g)");
                    }
                }
            }
        }



        ArrayList<Ingrediente_gramos> listDatos = new ArrayList<Ingrediente_gramos>(); //= new ArrayList<Ingrediente_gramos>()



       // String ingrediente;
       // double gramos;

        for(int i=0; i<tamDesp; i++){
            Cursor fila = BaseDeDatos.rawQuery("select ingrediente, gramos from despensa where id="+i + " and tiene='si'", null); //+ " and tiene=si"

            if (fila.moveToFirst()) {

                ingrediente = fila.getString(0);
                gramos = Double.valueOf(fila.getString(1)).doubleValue();

                DecimalFormat formato = new DecimalFormat("#.##");
                String var_gramos= formato.format(gramos);

                gramos = Double.parseDouble(var_gramos);

                listDatos.add(new Ingrediente_gramos(ingrediente, gramos, i)); //ingrediente+" ("+ formato.format(gramos)+" g)"

            }
        }

        BaseDeDatos.close();


        AdapterDespensa adapter = new AdapterDespensa(listDatos);

        final ArrayList<Ingrediente_gramos> finalListDatos = listDatos;
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent siguiente = new Intent(getApplicationContext(), ModificarDespensa.class);
                siguiente.putExtra("ingrediente", finalListDatos.get(recyclerDespensa.getChildAdapterPosition(view)).getIngrediente());
                siguiente.putExtra("gramos", ""+finalListDatos.get(recyclerDespensa.getChildAdapterPosition(view)).getGramos());
                siguiente.putExtra("id", ""+finalListDatos.get(recyclerDespensa.getChildAdapterPosition(view)).getId());
                startActivity(siguiente);

                //Toast.makeText(getApplicationContext(), "Selección: "+ finalListDatos.get(recyclerDespensa.getChildAdapterPosition(view)).getIngrediente(), Toast.LENGTH_LONG).show();//+listDatos.get(recyclerDespensa.getChildAdapterPosition(view)
            }
        });

        recyclerDespensa.setAdapter(adapter);
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

    public void anadir(View view){
        Intent siguiente = new Intent(this, AnadirDespensa.class);
        startActivity(siguiente);

    }
}
