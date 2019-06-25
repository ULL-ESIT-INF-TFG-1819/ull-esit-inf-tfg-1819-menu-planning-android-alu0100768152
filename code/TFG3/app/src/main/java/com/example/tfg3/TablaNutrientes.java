package com.example.tfg3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class TablaNutrientes extends AppCompatActivity {

    private static final String TAG = "RECETA";
    WebView wvTabla;
    //public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_nutrientes);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        wvTabla = (WebView) findViewById(R.id.wvTabla);

        Bundle datos = getIntent().getExtras();
        int id = Integer.parseInt(datos.getString("id"));

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String shareAs = "";

        Cursor fila = BaseDeDatos.rawQuery("select shareAs from platos where id="+id, null);

        if (fila.moveToFirst()) {

            shareAs = fila.getString(0);

        }

        BaseDeDatos.close();

        //wvTabla.getSettings().setUserAgentString(USER_AGENT);

       // wvTabla.getSettings().setJavaScriptEnabled(true);
        //wvTabla.getSettings().setAppCacheEnabled(true);
        wvTabla.setWebViewClient(new WebViewClient());

        String[] parts = shareAs.split("[:]");
        Log.i(TAG, parts[1]);

        wvTabla.loadUrl("https:" +parts[1]);


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
}
