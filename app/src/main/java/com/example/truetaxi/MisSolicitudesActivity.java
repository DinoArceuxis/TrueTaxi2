package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MisSolicitudesActivity extends AppCompatActivity {
    private ListView lv_solicitudes;
    private Dialog myDialog;
    String matriculataxi,origen,destino,fecha,estado;
    int selected;
    ArrayList<String> personas = new ArrayList<>();
    ArrayList<String> lista_solicitudes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_solicitudes);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MisSolicitudesActivity.this,"My Notification");
        builder.setContentTitle("Solicitud Verificada");
        builder.setContentText("Taxi: 1234A\nOrigen: Calle Puerta del Angel 23, Madrid\nDestino: Intercambiador de Moncloa, Madrid");
        builder.setSmallIcon(R.drawable.ic_baseline_verified_user_24);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(MisSolicitudesActivity.this);
        managerCompat.notify(1,builder.build());

        personas.add("ETSINF - Boadilla del Monte");
        personas.add("Calle Principe Pio 23");
        personas.add("Calle Puerta del Angel 31");
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
                    matriculataxi="Sin Asignar";
                    origen="Principe Pio 23, Madrid";
                    destino="Calle Arrakeen 4, Madrid";
                    fecha="14/12/2021 23:32";
                    estado="Rechazada";
                }
                else if(i==1)
                {
                    matriculataxi="Sin Asignar";
                    origen="Calle Puerta del Angel 31, Madrid";
                    destino="Principe Pio 23, Madrid";
                    fecha="17/12/2021 15:18";
                    estado="Pendiente";
                }
                else if(i==2)
                {
                    matriculataxi="1234A";
                    origen="Calle Puerta del Angel 23, Madrid";
                    destino="Intercambiador de Moncloa, Madrid";
                    fecha="19/12/2021 19:23";
                    estado="Verificada";
                }
                else
                {
                    matriculataxi="Demasiados ejemplos";
                    origen="Demasiados ejemplos";
                    destino="Demasiados ejemplos";
                    fecha="Demasiados ejemplos";
                    estado="Demasiados ejemplos";
                }
                myDialog.setContentView(R.layout.popup4);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
                EditText et_popup_id_taxi,et_popup_ubicacion,et_popup_destino,et_popup_fecha,et_popup_estado;
                et_popup_id_taxi=myDialog.findViewById(R.id.et_popup_id_taxi);
                et_popup_ubicacion=myDialog.findViewById(R.id.et_popup_ubicacion);
                et_popup_destino=myDialog.findViewById(R.id.et_popup_destino);
                et_popup_fecha=myDialog.findViewById(R.id.et_popup_fecha);
                et_popup_estado=myDialog.findViewById(R.id.et_popup_estado);

                et_popup_id_taxi.setText(matriculataxi);
                et_popup_ubicacion.setText(origen);
                et_popup_destino.setText(destino);
                et_popup_fecha.setText(fecha);
                et_popup_estado.setText(estado);
                if(estado=="Verificada")
                {
                    et_popup_estado.setBackgroundColor(Color.argb(100,0,255,0));
                }
                else if(estado=="Rechazada")
                {
                    et_popup_estado.setBackgroundColor(Color.argb(100,255,0,0));
                    Toast.makeText(MisSolicitudesActivity.this, "La solicitud rechazada se eliminará", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}