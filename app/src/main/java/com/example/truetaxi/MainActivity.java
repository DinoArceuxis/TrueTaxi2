package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Et_username,Et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Et_username=findViewById(R.id.username);
        Et_password=findViewById(R.id.password);
    }

    public void pressLogin(View view)
    {
        //REALIZA EL LOGIN DEL USUARIO. DEPENDIENDO DE SI ES ADMIN O USUARIO NORMAL APARECERAN DISTINTAS COSAS EN LA SIGUIENTE PANTALLA
        String username = Et_username.getText().toString();
        String password = Et_password.getText().toString();
        if(username.length()<1 || password.length()<1)
        {
            Toast.makeText(MainActivity.this, "Todos los campos deben estar completos.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"truetaxidb",null,1);
            SQLiteDatabase db = admin.getWritableDatabase();
            Cursor fila = db.rawQuery("select * from Cliente where nombre='"+username+"' and password='"+password+"'",null);
            if(fila.moveToFirst() || (username.equals("admin") && password.equals("admin")))
            {
                Intent i = new Intent(this,MyDashboardActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Nombre o contraseña incorrectos. Prueba otra vez.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void pressRegistro(View view)
    {
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}