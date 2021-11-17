package com.example.reto_01_android;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ListView lista;
    private String[] tareas;
    private ArrayList<Tarea> tareasList;
    private Tarea tareaObj, tareaHecha;
    private String hecha = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        tareaHecha = (Tarea) getIntent().getSerializableExtra("objTarea");
        hecha = getIntent().getStringExtra("hecha");
        if (tareaHecha != null){
            setTareaHecha(tareaHecha);
            //Toast.makeText(getApplicationContext(),tareaHecha.getNombre(),Toast.LENGTH_LONG).show();
        }

        lista = (ListView) findViewById(R.id.listaMarco);
        crearListaTareas(getTareasList());
    }

    public void crearListaTareas(ArrayList<Tarea> tareasList){
//        tareas = new String[tareasList.size()];
//        for (int x=0; x<tareasList.size(); x++){
//            Tarea obj = tareasList.get(x);
//            if(obj.getHecha().equals("no"))
//                tareas[x] = "      " + (x+1) + " - " + obj.getNombre();
//            else if(obj.getHecha().equals("si"))
//                tareas[x] = "✔️ " + (x+1) + " - " + obj.getNombre();
//        }
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_selectable_list_item, android.R.id.text1, tareas);
        AdaptadorListItems adapter = new AdaptadorListItems(this, tareasList);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    //String codigo = adapter.getItem(position);
                    int codigo = adapter.getPosition(adapter.getItem(position));
                    //Toast.makeText(getApplicationContext(),"Código del item position: " + codigo,Toast.LENGTH_SHORT).show();
                    irTarea(getTarea(codigo));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ArrayList<Tarea> getTareasList() {
        try {
            tareasList = new ArrayList<>();
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from tareas", null);
            fila.moveToFirst();
            while(!fila.isAfterLast()){
                tareaObj = new Tarea(fila.getString(0), fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6));
                tareasList.add(tareaObj);
                fila.moveToNext();
            }
            bd.close();
            return tareasList;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tareasList;
    }

    public Tarea getTarea(int codigo){
        Tarea obj = tareasList.get(codigo);
        return obj;
        /*
        for (int x=0; x<tareasList.size(); x++){
            Tarea obj = tareasList.get(x);
            if (obj.getCodigo().equals(codigo)){
                return obj;
            }
        }
        return null;
        */
    }

    public void setTareaHecha(Tarea tarea){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int codigo = Integer.parseInt(tarea.getCodigo());
        String nombre = tarea.getNombre();
        String descripcion = tarea.getDescripcion();
        String fecha = tarea.getFecha();
        String prioridad = tarea.getPrioridad();
        String coste = tarea.getCoste();
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("nombre", nombre);
        registro.put("descripcion", descripcion);
        registro.put("fecha", fecha);
        registro.put("prioridad", prioridad);
        registro.put("coste", coste);
        registro.put("hecha", hecha);
        //int cant = bd.update("tareas", registro, "codigo=" + codigo,null);
        bd.update("tareas", registro, "codigo=" + codigo,null);
        bd.close();
        /*if (cant == 1)
            Toast.makeText(this, "Tarea hecha!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una tarea con ese código!", Toast.LENGTH_SHORT).show();*/
    }

    public void verTareasHechas(View v){
        ArrayList<Tarea> tareasHechasLIst = new ArrayList<>();
        for (Tarea tarea: tareasList){
            if (tarea.getHecha().equals("si")){
                tareasHechasLIst.add(tarea);
            }
        }
        crearListaTareas(tareasHechasLIst);
    }

    public void verTareasPendientes(View v){
        ArrayList<Tarea> tareasPendLIst = new ArrayList<>();
        for (Tarea tarea: tareasList){
            if (tarea.getHecha().equals("no")){
                tareasPendLIst.add(tarea);
            }
        }
        crearListaTareas(tareasPendLIst);
    }

    public void verTareasTodas(View v){
        crearListaTareas(getTareasList());
    }

    public void borrarTabla(View v) {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("drop table if exists tareas");
            bd.execSQL("create table tareas(codigo int primary key,nombre text,descripcion text,fecha text,prioridad text,coste text,hecha text)");
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

    public void irHome() {
        try {
            Intent i = new Intent(this, Home.class );
            startActivity(i);
            finish();
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    @MainThread
    public void onBackPressed(){
        irHome();
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
        if (id == R.id.btnHome){
            irHome();
            return true;
        }
        if (id == R.id.btnAdd){
            irAltasDeLista();
            return true;
        }
        if (id==R.id.cambiarcontra) {
            CambiarContra();
            return true;
        }
        if (id==R.id.acercade) {
            AcercaDe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void CambiarContra(){
        try {
            Intent i = new Intent(this,CambiarContrasena.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void AcercaDe(){
        try {
            Intent i = new Intent(this,AcercaDe.class );
            startActivity(i);
        } catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

//  ------------------------   CLASE ADAPTADOR LISTVIEW   ------------------------
    class AdaptadorListItems extends ArrayAdapter<Tarea> {

        AppCompatActivity appCompatActivity;
        ArrayList<Tarea> tareaLista;

        AdaptadorListItems(AppCompatActivity context, ArrayList<Tarea> tareaLista) {
            super(context, R.layout.list_item, tareaLista);
            appCompatActivity = context;
            this.tareaLista = tareaLista;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.list_item, null);

            TextView textView1 = item.findViewById(R.id.textView);

            textView1.setText(tareaLista.get(position).getNombre());

            ImageView imageView1 = item.findViewById(R.id.imageView);
            ImageView imageView2 = item.findViewById(R.id.imageView2);

            if (tareaLista.get(position).getHecha().equals("si")){
                imageView1.setImageResource(R.mipmap.hecha);
            }
            if (tareaLista.get(position).getPrioridad().equals("Baja")){
                imageView2.setImageResource(R.mipmap.imptarea1);
            } else if (tareaLista.get(position).getPrioridad().equals("Media")){
                imageView2.setImageResource(R.mipmap.imptarea2);
            } else if (tareaLista.get(position).getPrioridad().equals("Alta")){
                imageView2.setImageResource(R.mipmap.imptarea3);
            } else if (tareaLista.get(position).getPrioridad().equals("Urgente")){
                imageView2.setImageResource(R.mipmap.imptarea4);
            }
            return(item);
        }
    }



}