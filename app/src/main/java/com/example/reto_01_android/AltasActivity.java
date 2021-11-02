package com.example.reto_01_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AltasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText txtNombre, txtDescripcion, txtFecha, txtCoste;
    private Button btnRegistrar, btnCancelar;
    private Spinner spinPrioridad;
    private String nombre, descripcion, fecha, coste, prioridad;
    private String[] prioridades = new String[]{"Baja", "Media", "Alta", "Urgente"};
    private DatePickerDialog datePickerDialog;
    private int codigo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);
        initDatePicker();

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFecha = findViewById(R.id.txtFecha);
        txtCoste = findViewById(R.id.txtCoste);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnCancelar = findViewById(R.id.btnCancelar);

        spinPrioridad = findViewById(R.id.spinPrioridad);
        spinPrioridad.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, prioridades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrioridad.setAdapter(adapter);

        txtFecha.setText(getTodaysDate());
    }

    public void irMain(View v){
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
        txtCoste.setText("");
        Intent i = new Intent(this,MainActivity.class );
        startActivity(i);
    }

    // ----------------------------  SQLite -------------------------------
    public void alta(View v) {
        boolean control = true;
        int color = Color.MAGENTA;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        nombre = txtNombre.getText().toString();
        if(nombre.length() < 1){
            txtNombre.setHint("Ingrese un Nombre por favor! ");
            txtNombre.setHintTextColor(color);
            control = false;
        }
        descripcion = txtDescripcion.getText().toString();
        if(descripcion.length() < 1){
            txtDescripcion.setHint("Ingrese una Descripción por favor! ");
            txtDescripcion.setHintTextColor(color);
            control = false;
        }
        coste = txtCoste.getText().toString();
        if(txtCoste.length() < 1){
            txtCoste.setHint("Ingrese un Coste por favor! ");
            txtCoste.setHintTextColor(color);
            control = false;
        }
        if(control){
            codigo++;
            fecha = txtFecha.getText().toString();
            prioridad = spinPrioridad.getSelectedItem().toString();
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("descripcion", descripcion);
            registro.put("fecha", fecha);
            registro.put("prioridad", prioridad);
            registro.put("coste", coste);
            bd.insert("tareas", null, registro);
            bd.close();
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtFecha.setText("");
            txtCoste.setText("");
            Toast.makeText(this, "Tarea guardada con éxito! ",Toast.LENGTH_SHORT).show();
            irMain(v);
        } else {
            Toast.makeText(this, "Ingrese datos por favor! ",Toast.LENGTH_SHORT).show();
        }
    }

    // ----------------------------  Spinner Prioridades -------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast msg = Toast.makeText(this, "Se ha seleccionado un item", Toast.LENGTH_SHORT);
       // msg.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // ----------------------------  DatePicker -------------------------------
    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                txtFecha.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month){
        if (month == 1) return "ENE";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "ABR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AGO";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DIC";
        return "ENE";
    }

    public void openDatePicker(View v){
        datePickerDialog.show();
    }

}