package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;




public class Pantalla2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
    }
public void Volver(View v)
{
    finish();

}
    public void Crear(View v)
    {
        Intent i = new Intent(this, AltasActivity.class );
        startActivity(i);

    }
    public void Listar(View v)
    {
        Intent i = new Intent(this, ListaActivity.class );
        startActivity(i);
    }


}