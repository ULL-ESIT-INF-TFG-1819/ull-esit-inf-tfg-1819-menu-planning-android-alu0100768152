package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tfg3.CarpetaAPI.ListaRecipeAdapter;
import com.example.tfg3.CarpetaAPI.Recipe;
import com.example.tfg3.CarpetaAPI.RecipeService;
import com.example.tfg3.CarpetaAPI.Respuesta;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatosAPI extends AppCompatActivity {


    private Retrofit retrofit;
    private static final String TAG = "RECETA";


    private RecyclerView recyclerView;
    private ListaRecipeAdapter listaRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_api);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaRecipeAdapter = new ListaRecipeAdapter(this);
        recyclerView.setAdapter(listaRecipeAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();
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

   /* private String pasoDatos(){
        String var = "chicken";
        return var;
    }*/

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


        RecipeService service = retrofit.create(RecipeService.class);
        Call<Respuesta> RespuestaCall = service.obtenerDatos(health, diet);

        RespuestaCall.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if (response.isSuccessful()) {

                    Respuesta respuesta = response.body();
                    ArrayList<Recipe> listaRecipe = respuesta.getHits();

                    listaRecipeAdapter.adicionarListaRecipe(listaRecipe);



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
