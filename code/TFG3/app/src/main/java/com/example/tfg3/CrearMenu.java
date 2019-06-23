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

import com.example.tfg3.CarpetaAPI.ListaRecipeAdapter;
import com.example.tfg3.CarpetaAPI.Recipe;
import com.example.tfg3.CarpetaAPI.RecipeService;
import com.example.tfg3.CarpetaAPI.Respuesta;
import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearMenu extends AppCompatActivity {

    private ArrayList<Recipe> listaPrimeros;
    private ArrayList<Recipe> listaSegundos;

    private Retrofit retrofit;
    private static final String TAG = "RECETA";
    private String app_id = "f8a801f5";
    private String app_key= "70a3be442007e101a7617c95f943525a";
   // private ListaRecipeAdapter listaRecipeAdapter;

    private String health = "";
    private double kcal_dia = 0;
    private double grasas;
    private double proteinas;
    private double hidratos;

    private String semana[] = {"lunes","martes","miercoles","jueves","viernes",
            "sabado","domingo"};


    private int id = 0;
    private int n_semana = 0;
    private int id_ing = 0;

    private int COUNT_p;
    private int COUNT_s;

    private String prim_platos[] = new String[7];
    private String seg_platos[] = new String[7];

    private String ingrediente_principal = "palabra";

    private int pescados = 0;
    private int legumbres = 0;
    private int carnes = 0;
    private int huevos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_menu);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



    }

    public void Consultar(View view) throws IOException, JSONException {

        Intent siguiente = new Intent(this, Plato.class);
        startActivity(siguiente);

       /* String jsonString = IOHelper.stringFromAsset(this, "food.json");

        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray food = new JSONArray(jsonString);

            String result = "";
            for (int i = 0; i < 7413; i++) {
                JSONObject ingredientes = food.getJSONObject(i);
                //new Gson().fromJson(city.toString(), City.class);
                Log.i(TAG, ingredientes.getString("Category")+" "+i);

            }
            //txtJson.setText(result);
            Log.i(TAG, ""+food.length());
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }




        /*Log.i(TAG, food.length());
        FileInputStream fi = openFileInput("food.json");
        BufferedInputStream bi = new BufferedInputStream(fi);
        StringBuffer buffer = new StringBuffer();
        Log.i(TAG, "hola22");

        while (bi.available() != 0){
            char c = (char)bi.read();
            buffer.append(c);
        }

        Log.i(TAG, "hola333");
        bi.close();
        fi.close();

        JSONArray jsonArray = new JSONArray(buffer.toString());

        //StringBuffer foodBuffer = new StringBuffer();

        for( int i=0; i<5; i++){
            String item = jsonArray.getJSONObject(i).getString("Category");
            Log.i(TAG, item);
        }*/


        //metodoprueba();
    }

    public void Lista_compra(View view){
        Intent siguiente = new Intent(this, ListaCompra.class);
        startActivity(siguiente);

    }

    public void despensa(View view){
        Intent siguiente = new Intent(this, Despensa.class);
        startActivity(siguiente);

    }

    public void Buscar(View view) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // BBDD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select health from datosUsuario where id=01", null);
        Cursor fila2 = BaseDeDatos.rawQuery("select kcal_dia, grasas, proteinas, hidratos from macronutrientes where id=01", null);




        if (fila.moveToFirst()) {

            health = fila.getString(0);

        }


        if (fila2.moveToFirst()) {

            kcal_dia = Double.valueOf(fila2.getString(0)).doubleValue();
            grasas = Double.valueOf(fila2.getString(1)).doubleValue();
            proteinas = Double.valueOf(fila2.getString(2)).doubleValue();
            hidratos = Double.valueOf(fila2.getString(3)).doubleValue();

        }

        //Log.i(TAG, health);
        BaseDeDatos.close();

        //grasas = grasas * 0.7;
        //proteinas = proteinas * 0.7;
        //hidratos = hidratos * 0.7;




        kcal_dia = kcal_dia * 0.4;

       /* String primeros [] = new String[20];
        primeros[0] = "salad";
        primeros[1] = "soup";
        primeros[2] = "gazpacho";
        primeros[3] = "vegetables";
        primeros[4] = "first course";

        int randomp = (int) (Math.random() * 5);*/

        String q = "soup";
        //Log.i(TAG, primeros[randomp]);
        obtenerPrimeros("salad soup");

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

    private void obtenerPrimeros(String q) {

        double kcal_primero = (kcal_dia * 0.3) - 40;
        int intkcal_primero =  (int)kcal_primero;

        String calories = "0-"+intkcal_primero;
        RecipeService service = retrofit.create(RecipeService.class);
        Call<Respuesta> RespuestaCall = service.obtenerDatos(q, app_id, app_key, health, calories);
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
                    obtenerSegundos("main dish");//main dish

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
        Call<Respuesta> RespuestaCall = service.obtenerDatos(q, app_id, app_key, health, calories);

        RespuestaCall.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if (response.isSuccessful()) {

                    Respuesta respuesta = response.body();
                    ArrayList<Recipe> listaRecipe = respuesta.getHits();
                    listaSegundos = listaRecipe;
                    COUNT_s = listaRecipe.size();
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


    private void almacenarmenu() {

        int random1 = (int) (Math.random() * COUNT_p);
        int random2 = (int) (Math.random() * COUNT_s);

        //Log.i(TAG, "Numeros: "+random1+" "+random2);
        Recipe p = listaPrimeros.get(random1);
        Recipe s = listaSegundos.get(random2);


        float grasas_prim = p.getRecipe().getTotalNutrients().getFAT().getQuantity();
        float grasas_seg = s.getRecipe().getTotalNutrients().getFAT().getQuantity();

        float proteinas_prim = p.getRecipe().getTotalNutrients().getPROCNT().getQuantity();
        float proteinas_seg = s.getRecipe().getTotalNutrients().getPROCNT().getQuantity();

        float hidratos_prim = p.getRecipe().getTotalNutrients().getCHOCDF().getQuantity();
        float hidratos_seg = s.getRecipe().getTotalNutrients().getCHOCDF().getQuantity();


        //Log.i(TAG, "Grasas Tiene: "+ grasas_prim+" + "+grasas_seg + " y son " + grasas);
        //Log.i(TAG, "proteinas Tiene: "+ proteinas_prim+" + "+proteinas_seg + " y son " + proteinas);
       // Log.i(TAG, "hidratos Tiene: "+ hidratos_prim+" + "+hidratos_seg + " y son " + hidratos);
        if (grasas_prim + grasas_seg <= grasas) {
            if (proteinas_prim + proteinas_seg <= proteinas) {
                if (hidratos_prim + hidratos_seg <= hidratos) {

                    if(n_semana != 0){
                        int var = 0;
                        for(int i = 0; i<n_semana; i++){
                            if(prim_platos[i] == p.getRecipe().getLabel() || seg_platos[i] == s.getRecipe().getLabel()){
                                var = 1;
                            }
                        }
                        if(var == 0){
                            prim_platos[n_semana] = p.getRecipe().getLabel();
                            seg_platos[n_semana] = s.getRecipe().getLabel();
                        }else{
                            almacenarmenu();
                            muerte();

                        }
                    }else{
                        prim_platos[n_semana] = p.getRecipe().getLabel();
                        seg_platos[n_semana] = s.getRecipe().getLabel();
                    }

                    int varLegumbres=0;
                    int varPescados=0;
                    int varCarnes=0;

                    for (int i = 0; i< s.getRecipe().getIngredients().size(); i++) {

                        String lineaIngrediente = s.getRecipe().getIngredients().get(i).getText();
                        String[] parts = lineaIngrediente.split("[ ,\\(\\)\\*\\.-]");

                        for (int j = 0; j < parts.length; j++) {
                            try {
                                boolean estaLegumbre = busqueda_legumbres(parts[j].toUpperCase());

                                if (estaLegumbre == true){
                                    if(legumbres<4){
                                        varLegumbres++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }

                                boolean estaPescado = busqueda_pescados(parts[j].toUpperCase());
                                if (estaPescado == true){
                                    if(pescados<4){
                                        varPescados++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }

                                boolean estaCarne = busqueda_carnes(parts[j].toUpperCase());
                                if (estaCarne == true){
                                    if(carnes<4){
                                        varCarnes++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                Log.i(TAG, "ERROR");
                            }
                        }

                    }



                    for (int i = 0; i< p.getRecipe().getIngredients().size(); i++) {

                        String lineaIngrediente = p.getRecipe().getIngredients().get(i).getText();
                        String[] parts = lineaIngrediente.split("[ ,\\(\\)\\*\\.-]");

                        for (int j = 0; j < parts.length; j++) {
                            try {
                                boolean estaLegumbre = busqueda_legumbres(parts[j].toUpperCase());

                                if (estaLegumbre == true){
                                    if(legumbres<4){
                                        varLegumbres++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }

                                boolean estaPescado = busqueda_pescados(parts[j].toUpperCase());
                                if (estaPescado == true){
                                    if(pescados<4){
                                        varPescados++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }

                                boolean estaCarne = busqueda_carnes(parts[j].toUpperCase());
                                if (estaCarne == true){
                                    if(carnes<4){
                                        varCarnes++;
                                    }else{
                                        almacenarmenu();
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                Log.i(TAG, "ERROR");
                            }
                        }

                    }

                    if(varLegumbres!=0){
                        legumbres++;
                    }
                    if(varPescados!=0){
                        pescados++;
                    }
                    if(varCarnes!=0){
                        carnes++;
                    }

                    double Mgramos = 0;
                    int n = 0;
                    for (int i = 0; i< s.getRecipe().getIngredients().size(); i++) {
                        if (Mgramos < s.getRecipe().getIngredients().get(i).getWeight()){
                            Mgramos = s.getRecipe().getIngredients().get(i).getWeight();
                            n = i;
                        }
                    }
                    String ingPrincipal = s.getRecipe().getIngredients().get(n).getText();
                    String[] parts = ingPrincipal.split("[ ,\\(\\)\\*\\.-]");


                    for (int i = 0; i < parts.length; i++) {

                        //Log.i(TAG, parts[i].toUpperCase());
                        boolean esta = es_igual(parts[i].toUpperCase());

                        /*if (parts[i]=="water"){
                            almacenarmenu();
                        }*/
                        if (esta == true){
                            //Log.i(TAG, parts[i]);
                            if(ingrediente_principal!=parts[i]){
                                ingrediente_principal = parts[i];
                                //Log.i(TAG, parts[i]);
                                i=parts.length;
                            }else{
                                almacenarmenu();
                                break;
                            }
                        }/*else{
                            almacenarmenu();
                        }*/

                        //Log.i(TAG, parts[i]);
                    }


                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();


                    registro.put("id", id);
                    registro.put("dia", semana[n_semana]);
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


                    Cursor fila = BaseDeDatos.rawQuery("select id from platos where id="+id, null);

                    if(fila.moveToFirst()) {

                        BaseDeDatos.update("platos", registro, "id=" + id, null);
                    }else{
                        BaseDeDatos.insert("platos", null, registro);
                    }



                    ContentValues registro2 = new ContentValues();

                    for (int i = 0; i< p.getRecipe().getIngredients().size(); i++) {
                        registro2.put("id", id_ing);
                        registro2.put("id_plato", id);
                        registro2.put("ingrediente", p.getRecipe().getIngredients().get(i).getText());
                        registro2.put("gramos", p.getRecipe().getIngredients().get(i).getWeight());
                        registro2.put("tiene", "no");

                        if(fila.moveToFirst()) {

                            BaseDeDatos.update("lista_ingredientes", registro2, "id=" + id_ing, null);
                        }else{
                            BaseDeDatos.insert("lista_ingredientes", null, registro2);
                        }

                        registro2 = new ContentValues();
                        id_ing = id_ing + 1;
                    }

                    id = id + 1;


                    ContentValues registro3 = new ContentValues();

                    registro3.put("id", id);
                    registro3.put("dia", semana[n_semana]);
                    registro3.put("nombre", s.getRecipe().getLabel());
                    registro3.put("foto", s.getRecipe().getImage());
                    registro3.put("url", s.getRecipe().getUrl());
                    registro3.put("shareAs", s.getRecipe().getShareAs());
                    registro3.put("raciones", s.getRecipe().getYield());
                    registro3.put("calorias", s.getRecipe().getCalories());
                    registro3.put("kcal", s.getRecipe().getTotalNutrients().getENERC_KCAL().getQuantity());
                    registro3.put("grasas", s.getRecipe().getTotalNutrients().getFAT().getQuantity());
                    registro3.put("hidratos", s.getRecipe().getTotalNutrients().getCHOCDF().getQuantity());
                    registro3.put("proteinas", s.getRecipe().getTotalNutrients().getPROCNT().getQuantity());


                    if(fila.moveToFirst()) {

                        BaseDeDatos.update("platos", registro3, "id=" + id, null);
                    }else{
                        BaseDeDatos.insert("platos", null, registro3);
                    }


                    ContentValues registro4 = new ContentValues();

                    for (int i = 0; i< s.getRecipe().getIngredients().size(); i++) {
                        registro4.put("id", id_ing);
                        registro4.put("id_plato", id);
                        registro4.put("ingrediente", s.getRecipe().getIngredients().get(i).getText());
                        registro4.put("gramos", s.getRecipe().getIngredients().get(i).getWeight());
                        registro4.put("tiene", "no");

                        if(fila.moveToFirst()) {

                            BaseDeDatos.update("lista_ingredientes", registro4, "id=" + id_ing, null);
                        }else{
                            BaseDeDatos.insert("lista_ingredientes", null, registro4);
                        }

                        registro4 = new ContentValues();
                        id_ing = id_ing + 1;
                    }

                    id = id + 1;


                    BaseDeDatos.close();

                    if(n_semana == 6){
                        n_semana++;
                        metodoprueba();

                    }else{
                        n_semana = n_semana + 1;
                        almacenarmenu();
                        muerte();
                    }



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

    private boolean es_igual(String palabra){


        String jsonString = IOHelper.stringFromAsset(this, "food.json");

        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray food = new JSONArray(jsonString);

            String result = "";
            for (int i = 0; i < 7413; i++) {
                JSONObject ingredientes = food.getJSONObject(i);
                if(palabra.equals(ingredientes.getString("Category"))){
                    //Log.i(TAG, palabra);
                    return true;
                }
            }
            return false;
            //Log.i(TAG, ""+food.length());
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        //Log.i(TAG, palabra);
        return false;
    }

    private void metodoprueba() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select nombre, shareAs from platos where id=1", null);
        Cursor fila2 = BaseDeDatos.rawQuery("select ingrediente, gramos from lista_ingredientes where id=8 and id_plato=1", null);

        String nombre = "";
        String shareAs = "";
        String ingrediente = "";
        double gramos = 0.0;


        if (fila.moveToFirst()) {

            nombre = fila.getString(0);
            shareAs = fila.getString(1);

        }

        if (fila2.moveToFirst()) {

            ingrediente = fila2.getString(0);
            gramos = Double.valueOf(fila2.getString(1)).doubleValue();

        }
        BaseDeDatos.close();

        Log.i(TAG, "Datos SQLite: nombre: "+nombre+" buscar: "+shareAs+ " ingrediente: "+ingrediente+ " g: "+gramos);

        Intent siguiente = new Intent(this, MenuSemanal.class);
        startActivity(siguiente);

    }


}
