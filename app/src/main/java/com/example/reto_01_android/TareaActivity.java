package com.example.reto_01_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TareaActivity extends AppCompatActivity {

    private TextView nroTarea, nombreTarea;
    private Tarea tareaObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        nroTarea = (TextView) findViewById(R.id.txtNroTarea);

        Intent i = getIntent();
        tareaObj = (Tarea) getIntent().getSerializableExtra("tarea");

        nroTarea.setText(tareaObj.getCodigo().toString());

    }

}
