package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarTaxiActivity extends AppCompatActivity {
    private EditText et_matricula,et_estado,et_ubicacion,et_destino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_taxi);

        et_matricula = findViewById(R.id.matricula);
        et_estado = findViewById(R.id.estado);
        et_ubicacion = findViewById(R.id.ubicacion);
        et_destino = findViewById(R.id.destino);
    }

    public void registrar_taxi(View view) {
        String matricula, estado, ubicacion, destino;
        matricula = et_matricula.getText().toString();
        estado = et_estado.getText().toString();
        ubicacion = et_ubicacion.getText().toString();
        destino = et_destino.getText().toString();

        if (matricula.length() < 1) {
            Toast.makeText(RegistrarTaxiActivity.this, "El campo matricula no puede estar vacío.", Toast.LENGTH_SHORT).show();
        } else {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "truetaxidb", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery("select * from Taxi where matricula='" + matricula + "'", null);
            if (fila.moveToFirst()) {
                Toast.makeText(RegistrarTaxiActivity.this, "La matricula ya existe. Por favor usa otra.", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues registro = new ContentValues();
                registro.put("matricula", matricula);
                registro.put("estado", estado);
                registro.put("ubicacion", ubicacion);
                registro.put("destino", destino);
                db.insert("Taxi", null, registro);
            }
            Toast.makeText(RegistrarTaxiActivity.this, "Taxi '"+matricula+"' registrado con exito.", Toast.LENGTH_SHORT).show();
            db.close();
        }
    }
}