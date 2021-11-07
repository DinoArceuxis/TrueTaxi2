package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private Spinner formas_pago_spinner;
    private EditText Et_username,Et_password,Et_mail,Et_tlf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Et_username=findViewById(R.id.username);
        Et_password=findViewById(R.id.password);
        Et_mail=findViewById(R.id.mail);
        Et_tlf=findViewById(R.id.tlf);

        //INICIO - CONTROL SPINNER FORMAS DE PAGO
        ArrayList<String> formas_pago = new ArrayList<>();
        formas_pago_spinner = findViewById(R.id.pago_spinner);

        formas_pago.add("Tarjeta");
        formas_pago.add("Paypal");
        formas_pago.add("Bitcoin");
        ArrayAdapter adpt = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item,formas_pago);

        formas_pago_spinner.setAdapter(adpt);

        formas_pago_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pago_selecc= (String) formas_pago_spinner.getAdapter().getItem(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //FIN - CONTROL SPINNER FORMAS DE PAGO
    }

    public void registro(View view)
    {
        //FUNCION PARA REGISTRO DEL USUARIO
        String username,password,mail,tlf;
        username=Et_username.getText().toString();
        password=Et_password.getText().toString();
        mail=Et_mail.getText().toString();
        tlf=Et_tlf.getText().toString();

        if(username.length()<1 || password.length()<1 || mail.length()<1 || tlf.length()<1)
        {
            Toast.makeText(RegisterActivity.this, "Todos los campos deben estar completos.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(RegisterActivity.this, "Correo de verificacion enviado a la direccion de email especificada.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }
}