package com.example.tfg3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
}
