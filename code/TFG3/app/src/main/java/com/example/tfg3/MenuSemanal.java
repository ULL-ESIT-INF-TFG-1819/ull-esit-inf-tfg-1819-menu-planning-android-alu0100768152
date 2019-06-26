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
import android.widget.CheckBox;
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

    private ImageView imgLunes1, imgLunes2, imgMartes1, imgMartes2, imgMiercoles1, imgMiercoles2, imgJueves1, imgJueves2, imgViernes1, imgViernes2, imgSabado1, imgSabado2, imgDomingo1, imgDomingo2, sombra1, sombra2, sombra3, sombra4, sombra5, sombra6, sombra7;
    private TextView datos;
    //private CheckBox check1, check2, check3, check4, check5, check6, check7;
    private static final String TAG = "RECETA";
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
        sombra1 = (ImageView) findViewById(R.id.sombra1);
        sombra2 = (ImageView) findViewById(R.id.sombra2);
        sombra3 = (ImageView) findViewById(R.id.sombra3);
        sombra4 = (ImageView) findViewById(R.id.sombra4);
        sombra5 = (ImageView) findViewById(R.id.sombra5);
        sombra6 = (ImageView) findViewById(R.id.sombra6);
        sombra7 = (ImageView) findViewById(R.id.sombra7);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();


        String imagenes[] = new String[14];
        String hecho[] = new String[14];

        for(int i=0; i<=13; i++){
            Cursor fila = BaseDeDatos.rawQuery("select foto, hecho from platos where id="+i, null);


            if (fila.moveToFirst()) {

                imagenes[i] = fila.getString(0);
                hecho[i] = fila.getString(1);

            }
        }

        BaseDeDatos.close();

        if(hecho[0].equals("si")){

            if(sombra1.getVisibility() == View.INVISIBLE){
                sombra1.setVisibility(View.VISIBLE);
            }

        }else{
            sombra1.setVisibility(View.INVISIBLE);
        }
        Picasso.get()
                .load(imagenes[0])
                .into(imgLunes1);

        Picasso.get()
                .load(imagenes[1])
                .into(imgLunes2);

        if(hecho[2].equals("si")){

            if(sombra2.getVisibility() == View.INVISIBLE){
                sombra2.setVisibility(View.VISIBLE);
            }

        }else{
            sombra2.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(imagenes[2])
                .into(imgMartes1);

        Picasso.get()
                .load(imagenes[3])
                .into(imgMartes2);

        if(hecho[4].equals("si")){

            if(sombra3.getVisibility() == View.INVISIBLE){
                sombra3.setVisibility(View.VISIBLE);
            }

        }else{
            sombra3.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(imagenes[4])
                .into(imgMiercoles1);

        Picasso.get()
                .load(imagenes[5])
                .into(imgMiercoles2);

        if(hecho[6].equals("si")){

            if(sombra4.getVisibility() == View.INVISIBLE){
                sombra4.setVisibility(View.VISIBLE);
            }

        }else{
            sombra4.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(imagenes[6])
                .into(imgJueves1);

        Picasso.get()
                .load(imagenes[7])
                .into(imgJueves2);


        if(hecho[8].equals("si")){

            if(sombra5.getVisibility() == View.INVISIBLE){
                sombra5.setVisibility(View.VISIBLE);
            }

        }else{
            sombra5.setVisibility(View.INVISIBLE);
        }
        Picasso.get()
                .load(imagenes[8])
                .into(imgViernes1);

        Picasso.get()
                .load(imagenes[9])
                .into(imgViernes2);

        if(hecho[10].equals("si")){

            if(sombra6.getVisibility() == View.INVISIBLE){
                sombra6.setVisibility(View.VISIBLE);
            }

        }else{
            sombra6.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(imagenes[10])
                .into(imgSabado1);

        Picasso.get()
                .load(imagenes[11])
                .into(imgSabado2);

        if(hecho[12].equals("si")){

            if(sombra7.getVisibility() == View.INVISIBLE){
                sombra7.setVisibility(View.VISIBLE);
            }

        }else{
            sombra7.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(imagenes[12])
                .into(imgDomingo1);

        Picasso.get()
                .load(imagenes[13])
                .into(imgDomingo2);



    }
    public void hecho1(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=0", null);
        BaseDeDatos.update("platos", registro, "id=1", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho2(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=2", null);
        BaseDeDatos.update("platos", registro, "id=3", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho3(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=4", null);
        BaseDeDatos.update("platos", registro, "id=5", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho4(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=6", null);
        BaseDeDatos.update("platos", registro, "id=7", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho5(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=8", null);
        BaseDeDatos.update("platos", registro, "id=9", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho6(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=10", null);
        BaseDeDatos.update("platos", registro, "id=11", null);

        BaseDeDatos.close();
        menuSemanal();
    }

    public void hecho7(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        Log.i(TAG, "ESTOY DENTRO DEL CHECK");
        registro.put("hecho", "si");
        BaseDeDatos.update("platos", registro, "id=12", null);
        BaseDeDatos.update("platos", registro, "id=13", null);

        BaseDeDatos.close();
        menuSemanal();
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
