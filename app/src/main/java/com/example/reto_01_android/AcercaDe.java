package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
    }

    public void gitEngel(View v) {
        String url = "https://github.com/ENGEL-DROID";
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    public void gitInigo(View v) {
        String url = "https://github.com/Inigocampo";
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }


    public void Volver(View v){
        finish();
    }
}
