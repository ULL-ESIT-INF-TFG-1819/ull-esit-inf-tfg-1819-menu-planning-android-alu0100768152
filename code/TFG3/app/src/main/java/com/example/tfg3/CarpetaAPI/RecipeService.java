package com.example.tfg3.CarpetaAPI;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {

  /*  public void Gfvr() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select diet, health from datosUsuario where id=01", null);


        String diet = "";
        String health = "";


        if (fila.moveToFirst()) {

            diet = fila.getString(0);
            health = fila.getString(1);


        }

        BaseDeDatos.close();
    }*/

    @GET("search?q=&app_id=f8a801f5&app_key=70a3be442007e101a7617c95f943525a&from=0&to=20&health=alcohol-free")
    Call<Respuesta> obtenerDatos(@Query("health") String health, @Query("diet") String diet);
}
