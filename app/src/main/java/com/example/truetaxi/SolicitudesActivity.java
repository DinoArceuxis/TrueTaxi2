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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class SolicitudesActivity extends AppCompatActivity {
    private ListView lv_solicitudes;
    private Dialog myDialog;
    String nombreuser,matriculataxi,origen,destino,fecha;
    int selected;
    ArrayList<String> personas = new ArrayList<>();
    ArrayList<String> lista_solicitudes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        personas.add("Pedro Cepeda Goñi");
        personas.add("Jorge Fernandez Conde");
        personas.add("Gonzalo Peña Ruperez");
        personas.add("Santiago de la Vega Urgal");
        lv_solicitudes=(ListView) findViewById(R.id.lv_solicitudes);
        for(int i=0;i<personas.size();i++)
        {
            lista_solicitudes.add("Solicitud "+(i+1)+" - "+personas.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_solicitudes);
        lv_solicitudes.setAdapter(adapter);
        myDialog = new Dialog(this);

        lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected=i;
                if(i==0)
                {
                    nombreuser="Pedro Cepeda Goñi";
                    matriculataxi="1234A";
                    origen="ETSINF - Boadilla del Monte";
                    destino="Calle Principe Pio 23";
                    fecha="19/12/2021 19:23";
                }
                else if(i==1)
                {
                    nombreuser="Jorge Fernandez Conde";
                    matriculataxi="2345B";
                    origen="Calle Puerta del Angel 31";
                    destino="Principe Pio 23";
                    fecha="17/12/2021 15:18";
                }
                else if(i==2)
                {
                    nombreuser="Gonzalo Peña Ruperez";
                    matriculataxi="3456C";
                    origen="Principe Pio 23";
                    destino="Calle Arrakeen 4";
                    fecha="14/12/2021 23:32";
                }
                else if(i==3)
                {
                    nombreuser="Santiago de la Vega Urgal";
                    matriculataxi="4567D";
                    origen="Calle Gran Via 12";
                    destino="Intercambiador de Moncloa";
                    fecha="13/12/2021 09:21";
                }
                else
                {
                    nombreuser="Demasiados ejemplos";
                    matriculataxi="Demasiados ejemplos";
                    origen="Demasiados ejemplos";
                    destino="Demasiados ejemplos";
                    fecha="Demasiados ejemplos";
                }
                myDialog.setContentView(R.layout.popup2);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
                EditText et_popup_id_cliente,et_popup_id_taxi,et_popup_ubicacion,et_popup_destino,et_popup_fecha;
                et_popup_id_cliente=myDialog.findViewById(R.id.et_popup_id_cliente);
                et_popup_id_taxi=myDialog.findViewById(R.id.et_popup_id_taxi);
                et_popup_ubicacion=myDialog.findViewById(R.id.et_popup_ubicacion);
                et_popup_destino=myDialog.findViewById(R.id.et_popup_destino);
                et_popup_fecha=myDialog.findViewById(R.id.et_popup_fecha);

                et_popup_id_cliente.setText(nombreuser);
                et_popup_id_taxi.setText(matriculataxi);
                et_popup_ubicacion.setText(origen);
                et_popup_destino.setText(destino);
                et_popup_fecha.setText(fecha);
            }
        });
    }
    public void download_logs(View view)
    {
        Toast.makeText(SolicitudesActivity.this, "Descarga iniciada.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SolicitudesActivity.this, ProgressBarActivity.class));
        finish();
    }
    public void verificar(View view)
    {
        double random=Math.random();
        if(random>0.5) {
            personas.remove(selected);
            ArrayList<String> lista_solicitudes2 = new ArrayList<>();
            for (int i = 0; i < personas.size(); i++) {
                lista_solicitudes2.add("Solicitud " + (i + 1) + " - " + personas.get(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_solicitudes2);
            lv_solicitudes.setAdapter(adapter);
            Toast.makeText(SolicitudesActivity.this, "Solicitud de taxi verificada y aceptada.", Toast.LENGTH_SHORT).show();
            myDialog.hide();
        }
        else{
            myDialog.hide();
            myDialog.setContentView(R.layout.popup3);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }
    public void denegar(View view)
    {
        personas.remove(selected);
        ArrayList<String> lista_solicitudes2 = new ArrayList<>();
        for(int i=0;i<personas.size();i++)
        {
            lista_solicitudes2.add("Solicitud "+(i+1)+" - "+personas.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_solicitudes2);
        lv_solicitudes.setAdapter(adapter);
        Toast.makeText(SolicitudesActivity.this, "Solicitud de taxi denegada y eliminada.", Toast.LENGTH_SHORT).show();
        myDialog.hide();
    }
    public void reintentar(View view)
    {
        personas.remove(selected);
        ArrayList<String> lista_solicitudes2 = new ArrayList<>();
        for (int i = 0; i < personas.size(); i++) {
            lista_solicitudes2.add("Solicitud " + (i + 1) + " - " + personas.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_solicitudes2);
        lv_solicitudes.setAdapter(adapter);
        Toast.makeText(SolicitudesActivity.this, "Solicitud de taxi verificada y aceptada.", Toast.LENGTH_SHORT).show();
        myDialog.hide();
    }
}