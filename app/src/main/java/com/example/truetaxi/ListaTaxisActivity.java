package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaTaxisActivity extends AppCompatActivity {
    private ListView lv_taxis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxis);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"truetaxidb",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ArrayList<String> lista_taxis = new ArrayList<>();
        lv_taxis=(ListView) findViewById(R.id.lv_taxis);
        Cursor fila = db.rawQuery("select * from Taxi",null);
        if(!fila.moveToFirst())
        {
            ContentValues registro = new ContentValues();
            registro.put("matricula", "1234A");
            registro.put("estado", "Libre");
            registro.put("ubicacion", "ETSINF Boadilla");
            registro.put("destino", "Plaza de España 32");
            db.insert("Taxi", null, registro);
        }
        if(fila.moveToFirst()){
            do{
                lista_taxis.add("Taxi "+fila.getInt(0) + " - " + fila.getString(1));
            }while(fila.moveToNext());
        }
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_taxis);
        lv_taxis.setAdapter(adapter);
    }

    public void registro_taxis(View view)
    {
        startActivity(new Intent(ListaTaxisActivity.this,RegistrarTaxiActivity.class));
    }

}