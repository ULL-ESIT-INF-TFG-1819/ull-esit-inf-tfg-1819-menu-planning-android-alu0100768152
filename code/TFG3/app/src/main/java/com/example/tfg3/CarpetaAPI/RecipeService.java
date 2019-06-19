package com.example.tfg3.CarpetaAPI;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {



    @GET("search?from=0&to=100")
    Call<Respuesta> obtenerDatos(@Query("q") String q, @Query("app_id") String app_id, @Query("app_key") String app_key ,@Query("health") String health, @Query("calories") String calories);
}


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