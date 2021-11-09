package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menuopciones, menu);
            return true;
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnAdd){
            irAltasDeLista();
            //Toast.makeText(this,"Item seleccionado",Toast.LENGTH_LONG).show();
            return true;
        }
        if (id==R.id.cambiarcontra) {
            Cambiarcontra();
            Toast.makeText(this,"Se seleccionó Cambiar Contraseña",Toast.LENGTH_LONG).show();
            return true;

        }
        if (id==R.id.acercade) {
            Toast.makeText(this,"Se seleccionó la Acerca de",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irAltasDeLista(){
        try {
            Intent i = new Intent(this,AltasActivity.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Cambiarcontra(){
        try {
            Intent i = new Intent(this,CambiarContrasena.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

}