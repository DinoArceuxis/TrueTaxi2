package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class SolicitarTaxiActivity extends AppCompatActivity implements OnMapReadyCallback {

    //private Spinner sp1;
    private EditText etorigen;
    private TextView tvfecha,tvhora;
    private GoogleMap mMap;
    int hour,minute;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_taxi);

        etorigen=findViewById(R.id.origen);
        tvfecha=findViewById(R.id.fecha);
        tvhora=findViewById(R.id.hora);
        etorigen.setText("Calle Puerta del Angel 24, Madrid");

        tvfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SolicitarTaxiActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth,mDateSetListener,
                year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50,255,255,255)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                Toast.makeText(SolicitarTaxiActivity.this, "Fecha seleccionada: "+day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
                tvfecha.setText(day+"/"+month+"/"+year);
            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.destino);
        mapFragment.getMapAsync(this);
    }

    public void solicitar_taxi(View view)
    {
        Toast.makeText(SolicitarTaxiActivity.this, "Solicitud de Taxi Enviada.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng a= new LatLng(40.423539831464275, -3.7122618600260076);
        LatLng taxi1 = new LatLng(40.41994600824458, -3.701661770946019);
        LatLng taxi2=new LatLng(40.41408669312602, -3.7240545117345114);
        //mMap.addMarker(new MarkerOptions().position(a).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(a));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                LatLng clicked= new LatLng(latLng.latitude,latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(clicked).title("Destino Seleccionado"));
                Toast.makeText(SolicitarTaxiActivity.this, "Destino seleccionado.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void timePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour=selectedHour;
                minute=selectedMinute;
                tvhora.setText(hour+":"+minute);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Selecciona una hora");
        timePickerDialog.show();
    }
}