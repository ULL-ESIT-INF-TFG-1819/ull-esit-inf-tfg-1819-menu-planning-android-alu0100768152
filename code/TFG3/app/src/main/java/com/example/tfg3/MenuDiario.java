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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg3.CarpetaAPI.Recipe;
import com.example.tfg3.CarpetaAPI.RecipeService;
import com.example.tfg3.CarpetaAPI.Respuesta;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuDiario extends AppCompatActivity {

    private static final String TAG = "RECETA";
    private String app_id = "f8a801f5";
    private String app_key= "70a3be442007e101a7617c95f943525a";
    private ImageView imgPrimero, imgSegundo;
    private TextView nombrePrimero, nombreSegundo;
    private int ID;
    private int ID2;
    private Retrofit retrofit;
    private String health = "";
    private double kcal_dia = 0;
    private double grasas;
    private double proteinas;
    private double hidratos;
    private ArrayList<Recipe> listaPrimeros;
    private ArrayList<Recipe> listaSegundos;
    private int COUNT_p;
    private int COUNT_s;
    private double grasas_seg;
    private double proteinas_seg;
    private double hidratos_seg;
    private double grasas_prim;
    private double proteinas_prim;
    private double hidratos_prim;
    private int pescados = 0;
    private int legumbres = 0;
    private int carnes = 0;
    private int huevos = 0;
    private int ultimo_id = 0;
    private String dia_prim;
    private String dia_seg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diario);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mostrar_platos();

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

    private void mostrar_platos() {
        imgPrimero = (ImageView) findViewById(R.id.imgPrimero);
        imgSegundo = (ImageView) findViewById(R.id.imgSegundo);
        nombrePrimero = (TextView) findViewById(R.id.nombrePrimero);
        nombreSegundo = (TextView) findViewById(R.id.nombreSegundo);

        Bundle datos = getIntent().getExtras();
        ID = Integer.parseInt(datos.getString("id"));
        ID2 = ID +1;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select nombre, foto from platos where id="+ID, null);

        String nombre = "";
        String foto = "";

        if (fila.moveToFirst()) {
            nombre = fila.getString(0);
            foto = fila.getString(1);
        }
        int id2 = ID + 1;
        Cursor fila2 = BaseDeDatos.rawQuery("select nombre, foto from platos where id="+id2, null);

        String nombre2 = "";
        String foto2 = "";

        if (fila2.moveToFirst()) {
            nombre2 = fila2.getString(0);
            foto2 = fila2.getString(1);
        }

        BaseDeDatos.close();

        Picasso.get()
                .load(foto)
                .into(imgPrimero);

        nombrePrimero.setText(nombre);

        Picasso.get()
                .load(foto2)
                .into(imgSegundo);

        nombreSegundo.setText(nombre2);
    }


    public void Bprimero(View view) {
        Intent siguiente = new Intent(this, Plato.class);
        siguiente.putExtra("id", ""+ID);
        startActivity(siguiente);
    }
    public void Bsegundo(View view) {
        Intent siguiente = new Intent(this, Plato.class);
        int id = ID+1;
        siguiente.putExtra("id", ""+id);
        startActivity(siguiente);
    }

    public void recalcular_primero(View view) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // BBDD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select health from datosUsuario where id=01", null);
        Cursor fila2 = BaseDeDatos.rawQuery("select kcal_dia, grasas, proteinas, hidratos from macronutrientes where id=01", null);
        Cursor fila4 = BaseDeDatos.rawQuery("select dia from platos where id="+ID, null);

        if (fila4.moveToFirst()) {
            dia_prim = fila4.getString(0);
        }


        if (fila.moveToFirst()) {

            health = fila.getString(0);
        }

        if (fila2.moveToFirst()) {

            kcal_dia = Double.valueOf(fila2.getString(0)).doubleValue();
            grasas = Double.valueOf(fila2.getString(1)).doubleValue();
            proteinas = Double.valueOf(fila2.getString(2)).doubleValue();
            hidratos = Double.valueOf(fila2.getString(3)).doubleValue();

        }

        //Datos del segundo plato:
        int id2 = ID + 1;
        Cursor fila3 = BaseDeDatos.rawQuery("select raciones, grasas, proteinas, hidratos from platos where id="+id2, null);
        int raciones = 0;
        if (fila3.moveToFirst()) {
            raciones = Integer.parseInt(fila3.getString(0));
            grasas_seg = Double.valueOf(fila3.getString(1)).doubleValue();
            proteinas_seg = Double.valueOf(fila3.getString(2)).doubleValue();
            hidratos_seg = Double.valueOf(fila3.getString(3)).doubleValue();
        }
        grasas_seg = grasas_seg / raciones;
        proteinas_seg = proteinas_seg / raciones;
        hidratos_seg = hidratos_seg / raciones;

        Cursor fila5 = BaseDeDatos.rawQuery("select legumbres, pescados, carnes, huevos, ultimo_id from datos_menu where id=01", null);

        if (fila5.moveToFirst()) {
            legumbres = Integer.parseInt(fila5.getString(0));
            pescados = Integer.parseInt(fila5.getString(1));
            carnes = Integer.parseInt(fila5.getString(2));
            huevos = Integer.parseInt(fila5.getString(3));
            ultimo_id = Integer.parseInt(fila5.getString(4));
        }

        BaseDeDatos.close();

        kcal_dia = kcal_dia * 0.4;

        obtenerPrimeros("salad soup");
    }


    public void recalcular_segundo(View view) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // BBDD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select health from datosUsuario where id=01", null);
        Cursor fila2 = BaseDeDatos.rawQuery("select kcal_dia, grasas, proteinas, hidratos from macronutrientes where id=01", null);
        Cursor fila4 = BaseDeDatos.rawQuery("select dia from platos where id="+ID2, null);

        if (fila4.moveToFirst()) {
            dia_seg = fila4.getString(0);
        }


        if (fila.moveToFirst()) {

            health = fila.getString(0);
        }

        if (fila2.moveToFirst()) {

            kcal_dia = Double.valueOf(fila2.getString(0)).doubleValue();
            grasas = Double.valueOf(fila2.getString(1)).doubleValue();
            proteinas = Double.valueOf(fila2.getString(2)).doubleValue();
            hidratos = Double.valueOf(fila2.getString(3)).doubleValue();

        }

        //Datos del primer plato:

        Cursor fila3 = BaseDeDatos.rawQuery("select raciones, grasas, proteinas, hidratos from platos where id="+ID, null);
        int raciones = 0;
        if (fila3.moveToFirst()) {
            raciones = Integer.parseInt(fila3.getString(0));
            grasas_prim = Double.valueOf(fila3.getString(1)).doubleValue();
            proteinas_prim = Double.valueOf(fila3.getString(2)).doubleValue();
            hidratos_prim = Double.valueOf(fila3.getString(3)).doubleValue();
        }
        grasas_prim = grasas_prim / raciones;
        proteinas_prim = proteinas_prim / raciones;
        hidratos_prim = hidratos_prim / raciones;

        Cursor fila5 = BaseDeDatos.rawQuery("select legumbres, pescados, carnes, huevos, ultimo_id from datos_menu where id=01", null);

        if (fila5.moveToFirst()) {
            legumbres = Integer.parseInt(fila5.getString(0));
            pescados = Integer.parseInt(fila5.getString(1));
            carnes = Integer.parseInt(fila5.getString(2));
            huevos = Integer.parseInt(fila5.getString(3));
            ultimo_id = Integer.parseInt(fila5.getString(4));
        }

        BaseDeDatos.close();

        kcal_dia = kcal_dia * 0.4;

        obtenerSegundos("main dish");
    }

    private void obtenerPrimeros(String q) {

        double kcal_primero = (kcal_dia * 0.3) - 40;
        int intkcal_primero =  (int)kcal_primero;

        String calories = "0-"+intkcal_primero;
        RecipeService service = retrofit.create(RecipeService.class);
        Call<Respuesta> RespuestaCall = service.obtenerDatos("100", q, app_id, app_key, health, calories);
        Log.i(TAG, q);
        RespuestaCall.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if (response.isSuccessful()) {

                    Respuesta respuesta = response.body();
                    ArrayList<Recipe> listaRecipe = respuesta.getHits();
                    listaPrimeros = listaRecipe;
                    COUNT_p = listaRecipe.size();
                    Log.i(TAG, "ENTREE");
                    almacenarmenu();

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

    private void obtenerSegundos(String q) {

        double kcal_segundo = (kcal_dia * 0.7) - 50;
        int intkcal_segundo =  (int)kcal_segundo;

        String calories = "0-"+ intkcal_segundo;
        RecipeService service = retrofit.create(RecipeService.class);
        Call<Respuesta> RespuestaCall = service.obtenerDatos("100", q, app_id, app_key, health, calories);

        RespuestaCall.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if (response.isSuccessful()) {

                    Respuesta respuesta = response.body();
                    ArrayList<Recipe> listaRecipe = respuesta.getHits();
                    listaSegundos = listaRecipe;
                    COUNT_s = listaRecipe.size();
                    almacenarmenu2();


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

    private void almacenarmenu(){

        int random1 = (int) (Math.random() * COUNT_p);

        //Log.i(TAG, "Numeros: "+random1+" "+random2);
        Recipe p = listaPrimeros.get(random1);

        float grasas_prim = (p.getRecipe().getTotalNutrients().getFAT().getQuantity() / p.getRecipe().getYield());

        float proteinas_prim = (p.getRecipe().getTotalNutrients().getPROCNT().getQuantity() / p.getRecipe().getYield());

        float hidratos_prim = (p.getRecipe().getTotalNutrients().getCHOCDF().getQuantity() / p.getRecipe().getYield());

        //Log.i(TAG, "Grasas Tiene: "+ grasas_prim+" + "+grasas_seg + " y son " + grasas);
        //Log.i(TAG, "proteinas Tiene: "+ proteinas_prim+" + "+proteinas_seg + " y son " + proteinas);
        // Log.i(TAG, "hidratos Tiene: "+ hidratos_prim+" + "+hidratos_seg + " y son " + hidratos);
        if (grasas_prim + grasas_seg <= grasas) {
            if (proteinas_prim + proteinas_seg <= proteinas) {
                if (hidratos_prim + hidratos_seg <= hidratos) {


                    for (int i = 0; i< p.getRecipe().getIngredients().size(); i++) {

                        String lineaIngrediente = p.getRecipe().getIngredients().get(i).getText();
                        String[] parts = lineaIngrediente.split("[ ,\\(\\)\\*\\.-]");

                        for (int j = 0; j < parts.length; j++) {
                            try {

                                if (parts[j].toUpperCase().equals("egg") || parts[j].toUpperCase().equals("eggs")){
                                    if(huevos>=4){
                                        almacenarmenu();
                                        muerte();
                                    }
                                }

                                boolean estaLegumbre = busqueda_legumbres(parts[j].toUpperCase());

                                if (estaLegumbre == true){
                                    if(legumbres>=4){
                                        almacenarmenu();
                                        muerte();
                                    }
                                }

                                boolean estaPescado = busqueda_pescados(parts[j].toUpperCase());
                                if (estaPescado == true){
                                    if(pescados>=4){
                                        almacenarmenu();
                                        muerte();
                                    }
                                }

                                boolean estaCarne = busqueda_carnes(parts[j].toUpperCase());
                                if (estaCarne == true){
                                    if(carnes>=4){
                                        almacenarmenu();
                                        muerte();
                                    }
                                }


                            } catch (IOException e) {
                                Log.i(TAG, "ERROR");
                            }
                        }

                    }


                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();


                    registro.put("id", ID);
                    registro.put("dia", dia_prim);
                    registro.put("nombre", p.getRecipe().getLabel());
                    registro.put("foto", p.getRecipe().getImage());
                    registro.put("url", p.getRecipe().getUrl());
                    registro.put("shareAs", p.getRecipe().getShareAs());
                    registro.put("raciones", p.getRecipe().getYield());
                    registro.put("calorias", p.getRecipe().getCalories());
                    registro.put("kcal", p.getRecipe().getTotalNutrients().getENERC_KCAL().getQuantity());
                    registro.put("grasas", p.getRecipe().getTotalNutrients().getFAT().getQuantity());
                    registro.put("hidratos", p.getRecipe().getTotalNutrients().getCHOCDF().getQuantity());
                    registro.put("proteinas", p.getRecipe().getTotalNutrients().getPROCNT().getQuantity());


                    Cursor fila = BaseDeDatos.rawQuery("select id from platos where id="+ID, null);

                    if(fila.moveToFirst()) {

                        BaseDeDatos.update("platos", registro, "id=" + ID, null);
                    }else{
                        BaseDeDatos.insert("platos", null, registro);
                    }


                    Cursor fila3 = BaseDeDatos.rawQuery("select count(*) from lista_ingredientes", null);

                    int tamBD=0;
                    if (fila3.moveToFirst()) {
                        tamBD = Integer.parseInt(fila3.getString(0));
                    }

                    //ContentValues registro3 = new ContentValues();
                    for(int i=0; i<tamBD; i++) {
                        Cursor fila4 = BaseDeDatos.rawQuery("select ingrediente from lista_ingredientes where id_plato=" + ID + " and id=" + i, null);

                        if (fila4.moveToFirst()) {
                            BaseDeDatos.delete("lista_ingredientes", "id="+i, null);
                        }
                    }

                    ContentValues registro2 = new ContentValues();

                    for (int i = 0; i< p.getRecipe().getIngredients().size(); i++) {
                        registro2.put("id", ultimo_id);
                        registro2.put("id_plato", ID);
                        registro2.put("ingrediente", p.getRecipe().getIngredients().get(i).getText());
                        registro2.put("gramos", p.getRecipe().getIngredients().get(i).getWeight());
                        registro2.put("tiene", "no");

                        if(fila.moveToFirst()) {

                            BaseDeDatos.update("lista_ingredientes", registro2, "id=" + ultimo_id, null);
                        }else{
                            BaseDeDatos.insert("lista_ingredientes", null, registro2);
                        }

                        registro2 = new ContentValues();
                        ultimo_id = ultimo_id + 1;
                    }


                    BaseDeDatos.close();

                    mostrar_platos();



                    //  datos.setText("URL: "+ p.getRecipe().getUrl() + "\n\n Raciones: " + p.getRecipe().getYield() + "\n\n Calorías: "
                    //        + p.getRecipe().getCalories() + "\n\n Ingrediente: " + p.getRecipe().getIngredients().get(0).getText() + "\n\n Grasas: " + p.getRecipe().getTotalNutrients().getFAT().getQuantity());


                }else{
                    almacenarmenu();
                    muerte();
                }
            }else{
                almacenarmenu();
                muerte();
            }
        }else{
            almacenarmenu();
            muerte();

        }

    }

    private void almacenarmenu2(){

        int random1 = (int) (Math.random() * COUNT_s);

        //Log.i(TAG, "Numeros: "+random1+" "+random2);
        Recipe s = listaSegundos.get(random1);

        float grasas_seg = (s.getRecipe().getTotalNutrients().getFAT().getQuantity() / s.getRecipe().getYield());

        float proteinas_seg = (s.getRecipe().getTotalNutrients().getPROCNT().getQuantity() / s.getRecipe().getYield());

        float hidratos_seg = (s.getRecipe().getTotalNutrients().getCHOCDF().getQuantity() / s.getRecipe().getYield());

        //Log.i(TAG, "Grasas Tiene: "+ grasas_prim+" + "+grasas_seg + " y son " + grasas);
        //Log.i(TAG, "proteinas Tiene: "+ proteinas_prim+" + "+proteinas_seg + " y son " + proteinas);
        // Log.i(TAG, "hidratos Tiene: "+ hidratos_prim+" + "+hidratos_seg + " y son " + hidratos);
        if (grasas_prim + grasas_seg <= grasas) {
            if (proteinas_prim + proteinas_seg <= proteinas) {
                if (hidratos_prim + hidratos_seg <= hidratos) {


                    for (int i = 0; i< s.getRecipe().getIngredients().size(); i++) {

                        String lineaIngrediente = s.getRecipe().getIngredients().get(i).getText();
                        String[] parts = lineaIngrediente.split("[ ,\\(\\)\\*\\.-]");

                        for (int j = 0; j < parts.length; j++) {
                            try {

                                if (parts[j].toUpperCase().equals("egg") || parts[j].toUpperCase().equals("eggs")){
                                    if(huevos>=4){
                                        almacenarmenu2();
                                        muerte();
                                    }
                                }

                                boolean estaLegumbre = busqueda_legumbres(parts[j].toUpperCase());

                                if (estaLegumbre == true){
                                    if(legumbres>=4){
                                        almacenarmenu2();
                                        muerte();
                                    }
                                }

                                boolean estaPescado = busqueda_pescados(parts[j].toUpperCase());
                                if (estaPescado == true){
                                    if(pescados>=4){
                                        almacenarmenu2();
                                        muerte();
                                    }
                                }

                                boolean estaCarne = busqueda_carnes(parts[j].toUpperCase());
                                if (estaCarne == true){
                                    if(carnes>=4){
                                        almacenarmenu2();
                                        muerte();
                                    }
                                }


                            } catch (IOException e) {
                                Log.i(TAG, "ERROR");
                            }
                        }

                    }


                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();


                    registro.put("id", ID2);
                    registro.put("dia", dia_seg);
                    registro.put("nombre", s.getRecipe().getLabel());
                    registro.put("foto", s.getRecipe().getImage());
                    registro.put("url", s.getRecipe().getUrl());
                    registro.put("shareAs", s.getRecipe().getShareAs());
                    registro.put("raciones", s.getRecipe().getYield());
                    registro.put("calorias", s.getRecipe().getCalories());
                    registro.put("kcal", s.getRecipe().getTotalNutrients().getENERC_KCAL().getQuantity());
                    registro.put("grasas", s.getRecipe().getTotalNutrients().getFAT().getQuantity());
                    registro.put("hidratos", s.getRecipe().getTotalNutrients().getCHOCDF().getQuantity());
                    registro.put("proteinas", s.getRecipe().getTotalNutrients().getPROCNT().getQuantity());


                    Cursor fila = BaseDeDatos.rawQuery("select id from platos where id="+ID2, null);

                    if(fila.moveToFirst()) {

                        BaseDeDatos.update("platos", registro, "id=" + ID2, null);
                    }else{
                        BaseDeDatos.insert("platos", null, registro);
                    }


                    Cursor fila3 = BaseDeDatos.rawQuery("select count(*) from lista_ingredientes", null);

                    int tamBD=0;
                    if (fila3.moveToFirst()) {
                        tamBD = Integer.parseInt(fila3.getString(0));
                    }

                    //ContentValues registro3 = new ContentValues();
                    for(int i=0; i<tamBD; i++) {
                        Cursor fila4 = BaseDeDatos.rawQuery("select ingrediente from lista_ingredientes where id_plato=" + ID2 + " and id=" + i, null);

                        if (fila4.moveToFirst()) {
                            BaseDeDatos.delete("lista_ingredientes", "id="+i, null);
                        }
                    }

                    ContentValues registro2 = new ContentValues();

                    for (int i = 0; i< s.getRecipe().getIngredients().size(); i++) {
                        registro2.put("id", ultimo_id);
                        registro2.put("id_plato", ID2);
                        registro2.put("ingrediente", s.getRecipe().getIngredients().get(i).getText());
                        registro2.put("gramos", s.getRecipe().getIngredients().get(i).getWeight());
                        registro2.put("tiene", "no");

                        if(fila.moveToFirst()) {

                            BaseDeDatos.update("lista_ingredientes", registro2, "id=" + ultimo_id, null);
                        }else{
                            BaseDeDatos.insert("lista_ingredientes", null, registro2);
                        }

                        registro2 = new ContentValues();
                        ultimo_id = ultimo_id + 1;
                    }


                    BaseDeDatos.close();

                    mostrar_platos();



                    //  datos.setText("URL: "+ p.getRecipe().getUrl() + "\n\n Raciones: " + p.getRecipe().getYield() + "\n\n Calorías: "
                    //        + p.getRecipe().getCalories() + "\n\n Ingrediente: " + p.getRecipe().getIngredients().get(0).getText() + "\n\n Grasas: " + p.getRecipe().getTotalNutrients().getFAT().getQuantity());


                }else{
                    almacenarmenu2();
                    muerte();
                }
            }else{
                almacenarmenu2();
                muerte();
            }
        }else{
            almacenarmenu2();
            muerte();

        }

    }

    private void muerte(){

    }

    private boolean busqueda_legumbres(String palabra) throws IOException {

        //List<String> listado = new ArrayList<String>();
        String linea;
        //Log.i(TAG, palabra);
        InputStream is = this.getResources().openRawResource(R.raw.legumes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is!=null){
            // linea=reader.readLine();
            while ((linea=reader.readLine()) != null){
                String[] parts = linea.split("[ ,\\(\\)\\*\\.-]");
                for(int i=0; i< parts.length; i++){
                    //Log.i(TAG, parts[i]);
                    if (palabra.equals(parts[i].toUpperCase())){
                        Log.i(TAG, parts[i]);
                        linea = null;
                        return true;
                    }
                }
            }

        }
        is.close();
        return false;
    }

    private boolean busqueda_pescados(String palabra) throws IOException {

        String linea;
        //Log.i(TAG, palabra);
        InputStream is = this.getResources().openRawResource(R.raw.pescados);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is!=null){
            // linea=reader.readLine();
            while ((linea=reader.readLine()) != null){
                String[] parts = linea.split("[ ,\\(\\)\\*\\.-]");
                for(int i=0; i< parts.length; i++){
                    //Log.i(TAG, parts[i]);
                    if (palabra.equals(parts[i].toUpperCase())){
                        Log.i(TAG, parts[i]);
                        linea = null;
                        return true;
                    }
                }
            }

        }
        is.close();
        return false;
    }

    private boolean busqueda_carnes(String palabra) throws IOException {

        String linea;
        //Log.i(TAG, palabra);
        InputStream is = this.getResources().openRawResource(R.raw.carnes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is!=null){
            // linea=reader.readLine();
            while ((linea=reader.readLine()) != null){
                String[] parts = linea.split("[ ,\\(\\)\\*\\.-]");
                for(int i=0; i< parts.length; i++){
                    //Log.i(TAG, parts[i]);
                    if (palabra.equals(parts[i].toUpperCase())){
                        Log.i(TAG, parts[i]);
                        linea = null;
                        return true;
                    }
                }
            }

        }
        is.close();
        return false;
    }

}
