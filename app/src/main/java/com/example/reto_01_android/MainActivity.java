package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Usuario;
    private EditText Password;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usuario=(EditText) findViewById(R.id.Usuario);
        Password=(EditText) findViewById(R.id.Password);
        checkBox=findViewById(R.id.checkBox);

    }

    public void irAltas(View v){
        try {
        Intent i = new Intent(this,AltasActivity.class );
        startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void irLista(View v){
        try {
            Intent i = new Intent(this,ListaActivity.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // ----------------------  ÍÑIGO  -------------------------------

    public void Guardar(View v) {
        String usu = Usuario.getText().toString();
        String pass = Password.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("agenda",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(usu, pass);
        editor.commit();
        Toast.makeText(this,"Datos grabados", Toast.LENGTH_LONG).show();


    }
    public void Entrar(View v)
    {
        String usu=Usuario.getText().toString();
        String pass=Password.getText().toString();

        if(checkBox.isChecked()==true)
        {
            SharedPreferences prefe=getSharedPreferences("agenda",
                    Context.MODE_PRIVATE);
            String d=prefe.getString(usu, "");
            if (d.length()==0) {
                Toast.makeText(this,"No existe dicho nombre en la agenda",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Password.setText(d);
                Intent i = new Intent(this, Pantalla2.class );
                startActivity(i);
            }
        }


    }




}