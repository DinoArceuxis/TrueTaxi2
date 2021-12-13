package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SolicitarTaxiActivity extends AppCompatActivity {

    private Spinner sp1;
    private EditText etorigen,etfecha,ethora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_taxi);

        etorigen=findViewById(R.id.origen);
        etfecha=findViewById(R.id.fecha);
        ethora=findViewById(R.id.hora);
        etorigen.setText("ETSINF - Boadilla del Monte");
        sp1=findViewById(R.id.destinospinner);
        String [] opciones={"ETSINF - Boadilla del Monte","Calle Puerta del Angel 31","Principe Pio 23","Calle Arrakeen 4","Calle Gran Via 12","Intercambiador de Moncloa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        sp1.setAdapter(adapter);
    }

    public void solicitar_taxi(View view)
    {
        Toast.makeText(SolicitarTaxiActivity.this, "Solicitud de Taxi Enviada.", Toast.LENGTH_LONG).show();
        finish();
    }
}