package com.example.reto_01_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    // GIT TOKEN: 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
    // NEW TOKEN: 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

    private EditText txtNombre, txtDescripcion, txtFecha, txtCoste;
    private Button btnRegistrar, btnCancelar;
    private CheckBox chHecha;
    private Spinner spinPrioridad;
    private String nombre, descripcion, fecha, coste, prioridad;
    private boolean hecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFecha = findViewById(R.id.txtFecha);
        txtCoste = findViewById(R.id.txtCoste);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnCancelar = findViewById(R.id.btnCancelar);
        chHecha = findViewById(R.id.chHecha);
        spinPrioridad = findViewById(R.id.spinPrioridad);
    }

    public void registrar(View v){
        nombre = txtNombre.getText().toString();
        descripcion = txtDescripcion.getText().toString();
        fecha = txtFecha.getText().toString();
        coste = txtCoste.getText().toString();
        prioridad = spinPrioridad.getSelectedItem().toString();
        hecha = chHecha.isSelected();
    }




}