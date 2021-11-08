package com.example.truetaxi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Cliente(id_cliente int primary key, nombre text, password text, correo text, telf text, pago text)");
        db.execSQL("create table Taxi(id_taxi int primary key, matricula text, estado text, ubicacion text, destino text)");
        db.execSQL("create table Solicitud(id_solicitud int primary key, origen text, destino text, fecha text, hora text, id_cliente int, id_taxi int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
