package com.example.tfg3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

public class AnadirDespensa extends AppCompatActivity {

    private EditText et_nombre, et_gramos;
    private String ingrediente;
    private String gramos_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_despensa);

        //poner el icono en el action Bar:
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
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


    public void anadir(View view){

        et_nombre = (EditText) findViewById(R.id.Nombre_id);
        et_gramos = (EditText) findViewById(R.id.Gramos_id);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila3 = BaseDeDatos.rawQuery("select count(*) from despensa", null);

        int tamBD=0;
        if (fila3.moveToFirst()) {
            tamBD = Integer.parseInt(fila3.getString(0));
        }

        ContentValues registro = new ContentValues();

        ingrediente = et_nombre.getText().toString();
        gramos_text = et_gramos.getText().toString();

        double gramos = Double.parseDouble(gramos_text);

        if(tamBD == 0){

            registro.put("id", 0);
            registro.put("ingrediente", ingrediente);
            registro.put("gramos", gramos);
            registro.put("tiene", "si");

        }else{
            registro.put("id", tamBD);
            registro.put("ingrediente", ingrediente);
            registro.put("gramos", gramos);
            registro.put("tiene", "si");
        }


        Cursor fila = BaseDeDatos.rawQuery("select id from despensa where id="+tamBD, null);

        if(fila.moveToFirst()) {

            BaseDeDatos.update("despensa", registro, "id=" + tamBD, null);
        }else{
            BaseDeDatos.insert("despensa", null, registro);
        }

        BaseDeDatos.close();

        Intent siguiente = new Intent(this, Despensa.class);
        startActivity(siguiente);

    }
}
