package com.example.reto_01_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TareaActivity extends AppCompatActivity {

    private TextView nombreTarea, descTarea, fechaTarea, impTarea, costoTarea;
    private Tarea tareaObj;
    private Switch hecha;
    private ImageButton btnAtras, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        nombreTarea = (TextView) findViewById(R.id.txtNombreTarea);
        descTarea = (TextView) findViewById(R.id.txtDescTarea);
        fechaTarea = (TextView) findViewById(R.id.txtFechaTarea);
        impTarea = (TextView) findViewById(R.id.txtImpTarea);
        costoTarea = (TextView) findViewById(R.id.txtCostoTarea);

        hecha = (Switch) findViewById(R.id.swichtHecha);
        btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnBorrar = (ImageButton) findViewById(R.id.btnBorrar);

        Intent i = getIntent();
        tareaObj = (Tarea) getIntent().getSerializableExtra("tarea");

        nombreTarea.setText(tareaObj.getNombre().toString());
        descTarea.setText(tareaObj.getDescripcion().toString());
        fechaTarea.setText(tareaObj.getFecha().toString());
        impTarea.setText(tareaObj.getPrioridad().toString());
        costoTarea.setText(tareaObj.getCoste().toString());

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
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
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

}
