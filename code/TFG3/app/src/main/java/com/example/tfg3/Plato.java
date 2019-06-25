package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Plato extends AppCompatActivity {

    private ListView lv_ingredientes;
    private TextView tvIngredientes, tvGrasas, tvCarbohidratos, tvProteinas, tvCalorias, tvNombre;
    private ImageView imgPlato;
    private static final String TAG = "RECETA";
    private int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //lv_ingredientes = (ListView)findViewById(R.id.lv_ingredientes);
        imgPlato = (ImageView) findViewById(R.id.imgPlato);
        tvIngredientes = (TextView)findViewById(R.id.tvIngredientes);
        tvGrasas = (TextView) findViewById(R.id.tvGrasas);
        tvCarbohidratos = (TextView) findViewById(R.id.tvCarbohidratos);
        tvProteinas = (TextView) findViewById(R.id.tvProteinas);
        tvCalorias = (TextView) findViewById(R.id.tvCalorias);
        tvNombre = (TextView) findViewById(R.id.tvNombre);

        Bundle datos = getIntent().getExtras();
        ID = Integer.parseInt(datos.getString("id"));





        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila3 = BaseDeDatos.rawQuery("select nombre, foto, kcal, grasas, hidratos, proteinas, raciones from platos where id="+ID, null);

        String nombre = "";
        String foto = "";
        Double kcal = 0.0;
        Double grasas = 0.0;
        Double hidratos = 0.0;
        Double proteinas = 0.0;
        int raciones = 0;

        if (fila3.moveToFirst()) {
            nombre = fila3.getString(0);
            foto = fila3.getString(1);
            kcal = Double.valueOf(fila3.getString(2)).doubleValue();
            grasas = Double.valueOf(fila3.getString(3)).doubleValue();
            hidratos = Double.valueOf(fila3.getString(4)).doubleValue();
            proteinas = Double.valueOf(fila3.getString(5)).doubleValue();
            raciones = Integer.parseInt(fila3.getString(6));
        }
        Cursor fila2 = BaseDeDatos.rawQuery("select count(*) from lista_ingredientes", null);

        ArrayList<String> ingredientes = new ArrayList<String>();

        int tamBD=0;
        if (fila2.moveToFirst()) {
            tamBD = Integer.parseInt(fila2.getString(0));
        }

        int count = 0;
        for(int i=0; i<tamBD; i++){
            Cursor fila = BaseDeDatos.rawQuery("select ingrediente from lista_ingredientes where id_plato="+ID+" and id="+i, null);

            if (fila.moveToFirst()) {

                ingredientes.add(fila.getString(0));
                count++;
            }
        }
        BaseDeDatos.close();

        String cadena = "";
        for(int i=0; i<count; i++){
            cadena = cadena + ingredientes.get(i)+" \n";
        }
        tvIngredientes.setText(cadena);

        grasas = grasas/raciones;
        hidratos = hidratos/raciones;
        proteinas = proteinas/raciones;
        kcal = kcal/raciones;

        DecimalFormat formato = new DecimalFormat("#.##");

        tvGrasas.setText(""+formato.format(grasas)+" g");
        tvCarbohidratos.setText(""+formato.format(hidratos)+" g");
        tvProteinas.setText(""+formato.format(proteinas)+" g");
        tvCalorias.setText(""+formato.format(kcal)+" kcal");
        tvNombre.setText(""+nombre);

        Picasso.get()
                .load(foto)
                .into(imgPlato);
        //ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_ingrediente, ingredientes);
        //lv_ingredientes.setAdapter(adapter);


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

    public void boton_tabla(View view) {
        Intent siguiente = new Intent(this, TablaNutrientes.class);
        siguiente.putExtra("id", ""+ID);
        startActivity(siguiente);
    }

    public void boton_preparacion(View view) {
        Intent siguiente = new Intent(this, Preparacion.class);
        siguiente.putExtra("id", ""+ID);
        startActivity(siguiente);
    }
}
