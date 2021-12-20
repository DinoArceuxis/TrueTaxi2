package com.example.truetaxi;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ListaTaxisActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ListView lv_taxis;
    private Dialog myDialog;
    private GoogleMap mMap;
    private Marker marker_taxi1;
    private Marker marker_taxi2;
    private Marker marker_taxi3;
    private Marker marker_taxi4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxis);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.taxismap);
        mapFragment.getMapAsync(this);

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
                int iaaa=0;
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

                    if(iaaa==0)
                    {
                        popup_matricula.setText("1234A");
                        popup_estado.setText("Ocupado");
                        popup_ubicacion.setText("Intercambiador de Moncloa, Madrid");
                        popup_destino.setText("");
                        popup_estado.setBackgroundColor(Color.argb(100,255,140,140));

                    }
                    iaaa++;
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng posicion_inicial= new LatLng(40.423539831464275, -3.7122618600260076);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.taxiconpng);

        LatLng taxi1 = new LatLng(40.42391872200169, -3.7113882173269634);
        marker_taxi1 = mMap.addMarker(new MarkerOptions()
            .position(taxi1)
            .title("1234A")
                .snippet("Estado: Ocupado").icon(icon)
            );
        LatLng taxi2=new LatLng(40.41956268999513, -3.6985230139617746);
        marker_taxi2 = mMap.addMarker(new MarkerOptions()
                .position(taxi2)
                        .title("2345B")
                        .snippet("Estado: Ocupado").icon(icon));
        LatLng taxi3=new LatLng(40.40541836860153, -3.8393703457352415);
        marker_taxi3 = mMap.addMarker(new MarkerOptions()
                .position(taxi3)
                        .title("3456C")
                        .snippet("Estado: Libre").icon(icon));
        LatLng taxi4=new LatLng(40.43446684470619, -3.71905670771421);
        marker_taxi4 = mMap.addMarker(new MarkerOptions()
                .position(taxi4)
                        .title("1234A")
                        .snippet("Estado: Ocupado").icon(icon));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion_inicial));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.gradient);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}