package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaTaxisActivity extends AppCompatActivity {
    private ListView lv_taxis;
    private Dialog myDialog;
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
                lista_taxis.add("Matricula: "+fila.getString(1));
            }while(fila.moveToNext());
        }
        fila.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_taxis);
        lv_taxis.setAdapter(adapter);
        myDialog = new Dialog(this);
        lv_taxis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String matriculaAux = adapter.getItem(position);
                String matricula = matriculaAux.substring(11);

                myDialog.setContentView(R.layout.popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(ListaTaxisActivity.this,"truetaxidb",null,1);
                SQLiteDatabase db2 = admin2.getWritableDatabase();
                Cursor fila2 = db2.rawQuery("select * from Taxi where matricula='"+matricula+"'",null);
                if(fila2.moveToFirst())
                {
                    EditText popup_matricula,popup_estado,popup_ubicacion,popup_destino;
                    popup_matricula = myDialog.findViewById(R.id.et_popup_matricula);
                    popup_estado = myDialog.findViewById(R.id.et_popup_estado);
                    popup_ubicacion = myDialog.findViewById(R.id.et_popup_ubicacion);
                    popup_destino = myDialog.findViewById(R.id.et_popup_destino);

                    popup_matricula.setText(fila2.getString(1));
                    popup_estado.setText(fila2.getString(2));
                    popup_ubicacion.setText(fila2.getString(3));
                    popup_destino.setText(fila2.getString(4));

                    if(fila2.getString(2).equals("Libre"))
                    {
                        popup_estado.setBackgroundColor(Color.argb(100,140,255,140));
                    }
                    else if(fila2.getString(2).equals("Ocupado"))
                    {
                        popup_estado.setBackgroundColor(Color.argb(100,255,140,140));
                    }
                }
                else
                {
                    Toast.makeText(ListaTaxisActivity.this, "Error en taxi con matricula "+matricula, Toast.LENGTH_SHORT).show();
                }
                fila2.close();
                db2.close();
            }
        });

        lv_taxis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String matriculaAux = (String) adapterView.getItemAtPosition(i);
                String matricula = matriculaAux.substring(11);

                myDialog.setContentView(R.layout.hold_popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(ListaTaxisActivity.this,"truetaxidb",null,1);
                SQLiteDatabase db2 = admin2.getWritableDatabase();
                Cursor fila = db2.rawQuery("select * from Taxi where matricula='"+matricula+"'",null);
                if(fila.moveToFirst())
                {
                    EditText popup_matricula,popup_estado,popup_ubicacion,popup_destino;
                    popup_matricula = myDialog.findViewById(R.id.et_popup_matricula);
                    popup_estado = myDialog.findViewById(R.id.et_popup_estado);

                    popup_matricula.setText(fila.getString(1));
                    popup_estado.setText(fila.getString(2));
                    if(fila.getString(2).equals("Libre"))
                    {
                        popup_estado.setBackgroundColor(Color.argb(100,140,255,140));
                    }
                    else if(fila.getString(2).equals("Ocupado"))
                    {
                        popup_estado.setBackgroundColor(Color.argb(100,255,140,140));
                    }

                }
                else
                {
                    Toast.makeText(ListaTaxisActivity.this, "Error en taxi con matricula "+matricula, Toast.LENGTH_SHORT).show();
                }
                fila.close();
                db2.close();

                //Toast.makeText(getApplicationContext(), "LONGPRESS matricula "+matricula, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    public void registro_taxis(View view)
    {
        startActivity(new Intent(ListaTaxisActivity.this,RegistrarTaxiActivity.class));
    }


}