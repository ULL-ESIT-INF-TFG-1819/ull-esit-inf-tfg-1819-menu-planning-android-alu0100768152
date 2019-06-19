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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tfg3.CarpetaAPI.ListaRecipeAdapter;
import com.example.tfg3.CarpetaAPI.Recipe;
import com.example.tfg3.CarpetaAPI.RecipeService;
import com.example.tfg3.CarpetaAPI.Respuesta;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuSemanal extends AppCompatActivity {

    private ImageView imgLunes1, imgLunes2, imgMartes1, imgMartes2, imgMiercoles1, imgMiercoles2, imgJueves1, imgJueves2, imgViernes1, imgViernes2, imgSabado1, imgSabado2, imgDomingo1, imgDomingo2;
    private TextView datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semanal);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        menuSemanal();

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
        } else if(id == R.id.itemCalorias){
            Intent siguiente = new Intent(this, Mostrar_datos.class);
            startActivity(siguiente);
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////////



    private void menuSemanal() {

        imgLunes1 = (ImageView) findViewById(R.id.imgLunes1);
        imgLunes2 = (ImageView) findViewById(R.id.imgLunes2);
        imgMartes1 = (ImageView) findViewById(R.id.imgMartes1);
        imgMartes2 = (ImageView) findViewById(R.id.imgMartes2);
        imgMiercoles1 = (ImageView) findViewById(R.id.imgMiercoles1);
        imgMiercoles2 = (ImageView) findViewById(R.id.imgMiercoles2);
        imgJueves1 = (ImageView) findViewById(R.id.imgJueves1);
        imgJueves2 = (ImageView) findViewById(R.id.imgJueves2);
        imgViernes1 = (ImageView) findViewById(R.id.imgViernes1);
        imgViernes2 = (ImageView) findViewById(R.id.imgViernes2);
        imgSabado1 = (ImageView) findViewById(R.id.imgSabado1);
        imgSabado2 = (ImageView) findViewById(R.id.imgSabado2);
        imgDomingo1 = (ImageView) findViewById(R.id.imgDomingo1);
        imgDomingo2 = (ImageView) findViewById(R.id.imgDomingo2);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String imagenes[] = new String[14];

        for(int i=0; i<=13; i++){
            Cursor fila = BaseDeDatos.rawQuery("select foto from platos where id="+i, null);

            if (fila.moveToFirst()) {

                imagenes[i] = fila.getString(0);

            }
        }

        BaseDeDatos.close();

        Picasso.get()
                .load(imagenes[0])
                .into(imgLunes1);

        Picasso.get()
                .load(imagenes[1])
                .into(imgLunes2);

        Picasso.get()
                .load(imagenes[2])
                .into(imgMartes1);

        Picasso.get()
                .load(imagenes[3])
                .into(imgMartes2);

        Picasso.get()
                .load(imagenes[4])
                .into(imgMiercoles1);

        Picasso.get()
                .load(imagenes[5])
                .into(imgMiercoles2);

        Picasso.get()
                .load(imagenes[6])
                .into(imgJueves1);

        Picasso.get()
                .load(imagenes[7])
                .into(imgJueves2);

        Picasso.get()
                .load(imagenes[8])
                .into(imgViernes1);

        Picasso.get()
                .load(imagenes[9])
                .into(imgViernes2);

        Picasso.get()
                .load(imagenes[10])
                .into(imgSabado1);

        Picasso.get()
                .load(imagenes[11])
                .into(imgSabado2);

        Picasso.get()
                .load(imagenes[12])
                .into(imgDomingo1);

        Picasso.get()
                .load(imagenes[13])
                .into(imgDomingo2);

    }

    public void lunes(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "0");
        startActivity(siguiente);
    }
    public void martes(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "2");
        startActivity(siguiente);
    }
    public void miercoles(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "4");
        startActivity(siguiente);
    }
    public void jueves(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "6");
        startActivity(siguiente);
    }
    public void viernes(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "8");
        startActivity(siguiente);
    }
    public void sabado(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "10");
        startActivity(siguiente);
    }
    public void domingo(View view) {
        Intent siguiente = new Intent(this, MenuDiario.class);
        siguiente.putExtra("id", "12");
        startActivity(siguiente);
    }


}
