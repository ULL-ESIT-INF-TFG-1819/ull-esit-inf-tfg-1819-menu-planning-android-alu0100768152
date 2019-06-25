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

        ArrayList<Ingrediente_gramos> listDatos = new ArrayList<Ingrediente_gramos>(); //= new ArrayList<Ingrediente_gramos>()

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
