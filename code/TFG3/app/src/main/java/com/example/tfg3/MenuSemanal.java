package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private ImageView imgLunes1, imgLunes2, imgMartes1;
    private TextView datos;

    private Retrofit retrofit;
    private static final String TAG = "RECETA";
    private String app_id = "f8a801f5";
    private String app_key= "70a3be442007e101a7617c95f943525a";
    private ListaRecipeAdapter listaRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semanal);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        imgLunes1 = (ImageView) findViewById(R.id.imgLunes1);
        imgLunes2 = (ImageView) findViewById(R.id.imgLunes2);
        imgMartes1 = (ImageView) findViewById(R.id.imgMartes1);
        datos = (TextView) findViewById(R.id.datos);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

        //Bundle datos = getIntent().getExtras();
        //ArrayList<Recipe> listaRecipe = (ArrayList<Recipe>) getIntent().getSerializableExtra("listaRecipe");
        //ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("miLista");
        //imagen1.setImageURI("https://www.edamam.com/web-img/03e/03e118d9a968764719b775890f102d1c.jpg");
        //Recipe p = listaRecipe.get(0);

    }


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

    private void obtenerDatos() {


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select diet, health from datosUsuario where id=01", null);


        String diet = "";
        String health = "";


        if (fila.moveToFirst()) {

            diet = fila.getString(0);
            health = fila.getString(1);


        }

        //Log.i(TAG, health);
        BaseDeDatos.close();

        String q = "first course";
        RecipeService service = retrofit.create(RecipeService.class);
        Call<Respuesta> RespuestaCall = service.obtenerDatos(q, app_id, app_key, health, diet);

        RespuestaCall.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if (response.isSuccessful()) {

                    Respuesta respuesta = response.body();
                    ArrayList<Recipe> listaRecipe = respuesta.getHits();

                    Recipe p = listaRecipe.get(0);
                    Recipe j = listaRecipe.get(1);

                    Picasso.get()
                            .load(p.getRecipe().getImage())
                            .into(imgLunes2);

                    Picasso.get()
                            .load(j.getRecipe().getImage())
                            .into(imgLunes1);

                    datos.setText("URL: "+ p.getRecipe().getUrl() + "\n\n Raciones: " + p.getRecipe().getYield() + "\n\n Calorías: "
                            + p.getRecipe().getCalories() + "\n\n Ingrediente: " + p.getRecipe().getIngredients().get(0).getText() + "\n\n Grasas: " + p.getRecipe().getTotalNutrients().getFAT().getQuantity());

                    //listaRecipeAdapter.adicionarListaRecipe(listaRecipe);

                    //lista_Recipe = listaRecipe;


                    //Constantes.setListaRecetas(listaRecipe);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }
}
