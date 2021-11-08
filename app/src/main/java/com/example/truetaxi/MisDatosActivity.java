package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MisDatosActivity extends AppCompatActivity {
    private EditText et_username,et_password,et_mail,et_tlf,et_pago;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        Bundle bundle = getIntent().getExtras();
        et_username = findViewById(R.id.username);
        et_username.setText(bundle.getString("username"));
        et_password = findViewById(R.id.password);
        et_password.setText(bundle.getString("password"));
        et_mail = findViewById(R.id.mail);
        et_mail.setText("Correo");
        et_tlf = findViewById(R.id.tlf);
        et_tlf.setText("Teléfono");
        et_pago = findViewById(R.id.formapago);
        et_pago.setText("Bitcoin");

        username=et_username.getText().toString();
        password=et_password.getText().toString();
    }
}