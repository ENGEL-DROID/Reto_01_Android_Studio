package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CambiarContrasena extends AppCompatActivity {


    private EditText Passwordnew;
    private EditText Passwordold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        Passwordold=(EditText) findViewById(R.id.Passwordold);
        Passwordnew=(EditText) findViewById(R.id.Passwordnew);
    }



    public void Cambiarcontra(View v){

        String PasswordOld= Passwordold.getText().toString();
        String PasswordNew= Passwordnew.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("agenda",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String pass=preferencias.getString("pass", "");
        editor.commit();

        if(pass==PasswordOld)
        {
            String passwordNEW= preferencias.getString("pass", PasswordNew);
            try {
                Intent i = new Intent(this,MainActivity.class );
                startActivity(i);
            } catch (Exception e){
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }else
            Toast.makeText(this,"No existe dicha Contraseña en la agenda",Toast.LENGTH_LONG).show();
    }

    public void Volver(View v){
        finish();
    }
}