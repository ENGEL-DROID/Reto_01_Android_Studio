package com.example.reto_01_android;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Calendar;

public class TareaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView nombreTarea, descTarea, fechaTarea, costoTarea;
    private Spinner impTarea;
    private String[] prioridades = new String[]{"Baja", "Media", "Alta", "Urgente"};
    private Tarea tareaObj;
    private Switch hecha;
    private ImageButton btnAtras, btnBorrar, btnEdit, btnSave;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);
        initDatePicker();

        nombreTarea = (TextView) findViewById(R.id.txtNombreTarea);
        descTarea = (TextView) findViewById(R.id.txtDescTarea);
        fechaTarea = (TextView) findViewById(R.id.txtFechaTarea);
        costoTarea = (TextView) findViewById(R.id.txtCostoTarea);

        impTarea = findViewById(R.id.txtImpTarea);
        impTarea.setOnItemSelectedListener(this);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.spinner_item_dark, prioridades);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item_dark, prioridades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        impTarea.setAdapter(adapter);

        nombreTarea.setEnabled(false);
        descTarea.setEnabled(false);
        fechaTarea.setEnabled(false);
        impTarea.setEnabled(false);
        costoTarea.setEnabled(false);

        hecha = (Switch) findViewById(R.id.swichtHecha);
        btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnBorrar = (ImageButton) findViewById(R.id.btnBorrar);

        btnSave.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        tareaObj = (Tarea) getIntent().getSerializableExtra("tarea");

        nombreTarea.setText(tareaObj.getNombre().toString());
        descTarea.setText(tareaObj.getDescripcion().toString());
        fechaTarea.setText(tareaObj.getFecha().toString());
        costoTarea.setText(tareaObj.getCoste().toString() + "€");

        switch(tareaObj.getPrioridad()) {
            case "Baja" :
                impTarea.setSelection(0);
                break;
            case "Media" :
                impTarea.setSelection(1);
                break;
            case "Alta" :
                impTarea.setSelection(2);
                break;
            case "Urgente" :
                impTarea.setSelection(3);
                break;
            default :
                impTarea.setSelection(0);
        }

        if (tareaObj.getHecha().equals("si")){
            hecha.setChecked(true);
        }
    }

    public void setBtnAtras(View v){
        try {
            Intent i = new Intent(this, ListaActivity.class);
            i.putExtra("objTarea", tareaObj);
            if (hecha.isChecked()){
                i.putExtra("hecha", "si");
            } else {
                i.putExtra("hecha", "no");
            }
            startActivity(i);
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    @MainThread
    public void onBackPressed(){
        setBtnAtras(null);
    }

    public void editarTarea(View v){
        nombreTarea.setEnabled(true);
        descTarea.setEnabled(true);
        fechaTarea.setEnabled(true);
        impTarea.setEnabled(true);
        costoTarea.setEnabled(true);
        nombreTarea.setTextColor(Color.MAGENTA);
        descTarea.setTextColor(Color.MAGENTA);
        fechaTarea.setTextColor(Color.MAGENTA);
        costoTarea.setTextColor(Color.MAGENTA);
        btnEdit.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        btnAtras.setVisibility(View.INVISIBLE);
        btnBorrar.setVisibility(View.INVISIBLE);
        hecha.setVisibility(View.INVISIBLE);
    }

    public void saveTarea(View v){
        modificarTarea(tareaObj);
        nombreTarea.setEnabled(false);
        descTarea.setEnabled(false);
        fechaTarea.setEnabled(false);
        impTarea.setEnabled(false);
        costoTarea.setEnabled(false);
        nombreTarea.setTextColor(Color.DKGRAY);
        descTarea.setTextColor(Color.DKGRAY);
        fechaTarea.setTextColor(Color.DKGRAY);
        costoTarea.setTextColor(Color.DKGRAY);
        btnEdit.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.INVISIBLE);
        btnAtras.setVisibility(View.VISIBLE);
        btnBorrar.setVisibility(View.VISIBLE);
        hecha.setVisibility(View.VISIBLE);
    }

    public void modificarTarea(Tarea tarea){
        tarea.setNombre(nombreTarea.getText().toString());
        tarea.setDescripcion(descTarea.getText().toString());
        tarea.setFecha(fechaTarea.getText().toString());
        tarea.setPrioridad(impTarea.getSelectedItem().toString());
        tarea.setCoste(costoTarea.getText().toString());
    }

    public void setBorrarTarea(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = tareaObj.getCodigo();
        int cant = bd.delete("tareas", "codigo=" + codigo, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "Tarea Eliminada!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una tarea con dicho código", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ListaActivity.class);
        startActivity(i);
    }

    public void ventanaConfirmar(View v){
        AlertDialog dialogo = new AlertDialog.Builder(this).setPositiveButton("      Sí, eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hicieron click en el botón positivo, así que la acción está confirmada
                setBorrarTarea();
            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hicieron click en el botón negativo, no confirmaron
                // Simplemente descartamos el diálogo
                dialog.dismiss();
            }
        })
        .setTitle("Confirmar") // El título
        .setMessage("¿Deseas eliminar ésta Tarea?") // El mensaje
        .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog!
        dialogo.show();
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
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fechaTarea.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

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
