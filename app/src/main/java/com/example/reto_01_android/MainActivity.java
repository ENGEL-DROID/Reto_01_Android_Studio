package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // GIT TOKEN: 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
    // NEW TOKEN: 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


    private EditText Usuario;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario=(EditText) findViewById(R.id.Usuario);
        Password=(EditText) findViewById(R.id.Password);
    }

    public void CrearCuenta(View v) {
        String usu=Usuario.getText().toString();
        String pass=Password.getText().toString();
        SharedPreferences preferencias=getSharedPreferences("agenda",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString(usu, pass);
        editor.commit();
        Toast.makeText(this,"Datos grabados", Toast.LENGTH_LONG).show();
    }
    public void RecuperarContra(View v) {
        String usu=Usuario.getText().toString();
        SharedPreferences prefe=getSharedPreferences("agenda",
                Context.MODE_PRIVATE);
        String d=prefe.getString(usu, "");
        if (d.length()==0) {
            Toast.makeText(this,"No existe dicho nombre en la agenda",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Password.setText(d);
        }
    }

    public void Entrar(View v)
    {
       // String usu=Usuario.getText().toString();
        //String pass=Password.getText().toString();
        //SharedPreferences prefe=getSharedPreferences("agenda",
        //        Context.MODE_PRIVATE);
        //String d=prefe.getString(usu,"");
        //String d1=prefe.getString("",pass);
        //if(d.length()==0 && d1.length()==0)
        //{
            Intent i = new Intent(this, Pantalla2.class );
            startActivity(i);
        //}
        //else
        //{
          //  Toast.makeText(this,"No existe dicha cuenta en la agenda",
           //         Toast.LENGTH_LONG).show();
        //}

    }

}