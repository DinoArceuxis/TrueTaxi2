package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyDashboardActivity extends AppCompatActivity {
    private String username,password,mail,tlf,pago;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dashboard);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        mail=bundle.getString("mail");
        tlf=bundle.getString("tlf");
        pago=bundle.getString("pago");
        if(username.equals("admin") && password.equals("admin"))
        {
            findViewById(R.id.cv_listataxis).setVisibility(View.VISIBLE);
            findViewById(R.id.cv_solicitudes).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.grid_recursos).setPadding(0,70,0,0);
        }
    }

    public void click_misdatos(View view)
    {
        Intent i = new Intent(this,MisDatosActivity.class);
        i.putExtra("username",username);
        i.putExtra("password",password);
        i.putExtra("mail",mail);
        i.putExtra("tlf",tlf);
        i.putExtra("pago",pago);
        startActivity(i);
    }
    public void click_solicitartaxi(View view)
    {
        Intent i = new Intent(this,SolicitarTaxiActivity.class);
        i.putExtra("username",username);
        i.putExtra("password",password);
        i.putExtra("mail",mail);
        i.putExtra("tlf",tlf);
        i.putExtra("pago",pago);
        startActivity(i);
    }
    public void click_missolicitudes(View view)
    {
        Intent i = new Intent(this,MisSolicitudesActivity.class);
        i.putExtra("username",username);
        i.putExtra("password",password);
        i.putExtra("mail",mail);
        i.putExtra("tlf",tlf);
        i.putExtra("pago",pago);
        startActivity(i);
    }
    public void click_logout(View view)
    {
        startActivity(new Intent(MyDashboardActivity.this,ActivitySplashScreen.class));
        finish();
    }
    public void click_listataxis(View view)
    {
        startActivity(new Intent(MyDashboardActivity.this,ListaTaxisActivity.class));
    }
    public void click_solicitudes(View view)
    {
        startActivity(new Intent(MyDashboardActivity.this,SolicitudesActivity.class));
    }
}