package com.example.tfg3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg3.CarpetaAPI.Constantes;

public class MainActivity extends AppCompatActivity {

    public Constantes CONSTANTE = new Constantes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void ModificarDatos(View view) {
        Intent siguiente = new Intent(this, recoger_datos1.class);
        startActivity(siguiente);
    }


    public void Entrar(View view) {
        Intent siguiente = new Intent(this, DatosAPI.class);
        startActivity(siguiente);
    }
    public void otro(View view) {
        Intent siguiente = new Intent(this, MenuSemanal.class);
        startActivity(siguiente);
    }

}
