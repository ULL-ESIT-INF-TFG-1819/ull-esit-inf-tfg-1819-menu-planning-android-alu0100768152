package com.example.tfg3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class Preparacion extends AppCompatActivity {

    WebView wvPreparacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparacion);

        wvPreparacion = (WebView) findViewById(R.id.wvPreparacion);

        Bundle datos = getIntent().getExtras();
        int id = Integer.parseInt(datos.getString("id"));

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String url = "";

        Cursor fila = BaseDeDatos.rawQuery("select url from platos where id="+id, null);

        if (fila.moveToFirst()) {

            url = fila.getString(0);

        }

        BaseDeDatos.close();

        //wvTabla.getSettings().setUserAgentString(USER_AGENT);

        // wvTabla.getSettings().setJavaScriptEnabled(true);
        //wvTabla.getSettings().setAppCacheEnabled(true);
        wvPreparacion.setWebChromeClient(new WebChromeClient());

        String[] parts = url.split("[:]");
        //Log.i(TAG, parts[1]);

        wvPreparacion.loadUrl("https:" +parts[1]);
    }
}
