package com.example.tfg3.SQLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table datosUsuario(id int primary key, nombre text, sexo text, edad int, altura int, peso real, actividad real, diet text, health text, aceiteOliva text)");
        BaseDeDatos.execSQL("create table macronutrientes(id int primary key, kcal_dia real, grasas real, proteinas real, hidratos real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
