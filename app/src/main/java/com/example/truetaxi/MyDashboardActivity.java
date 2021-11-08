package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dashboard);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String password = bundle.getString("password");
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
        startActivity(new Intent(MyDashboardActivity.this,MisDatosActivity.class));
    }
    public void click_solicitartaxi(View view)
    {
        startActivity(new Intent(MyDashboardActivity.this,SolicitarTaxiActivity.class));
    }
    public void click_missolicitudes(View view)
    {
        startActivity(new Intent(MyDashboardActivity.this,MisSolicitudesActivity.class));
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