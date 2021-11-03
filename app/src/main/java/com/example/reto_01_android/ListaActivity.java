package com.example.reto_01_android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ListView lista;
    private String[] tareas;
    private ArrayList<Tarea> tareasList;
    private Tarea tareaObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lista = (ListView) findViewById(R.id.listaMarco);
        getTareasList();
        tareas = new String[tareasList.size()];
        for (int x=0; x<tareasList.size(); x++){
            Tarea obj = tareasList.get(x);
            tareas[x] = obj.getCodigo();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, android.R.id.text1, tareas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String codigo = adapter.getItem(position);
                irTarea(getTarea(codigo));
            }
        });
    }

    public void getTareasList() {
        try {
            tareasList = new ArrayList<>();
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from tareas", null);
            fila.moveToFirst();
            while(!fila.isAfterLast()){
                tareaObj = new Tarea(fila.getString(0), fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5));
                tareasList.add(tareaObj);
                fila.moveToNext();
            }
            bd.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public Tarea getTarea(String codigo){
        for (int x=0; x<tareasList.size(); x++){
            Tarea obj = tareasList.get(x);
            if (obj.getCodigo().equals(codigo)){
                return obj;
            }
        }
        return null;
    }

    public void borrarTabla(View v) {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            //bd.execSQL("drop table if exists tareas");
            bd.execSQL("create table tareas(codigo int primary key,nombre text,descripcion text,fecha text,prioridad text,coste text)");
            bd.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void irAltasDeLista(){
        try {
            Intent i = new Intent(this,AltasActivity.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void irTarea(Tarea tarea){
        try {
            Intent i = new Intent(this,TareaActivity.class );
            i.putExtra("tarea", tarea);
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
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
        }
        return super.onOptionsItemSelected(item);
    }


}