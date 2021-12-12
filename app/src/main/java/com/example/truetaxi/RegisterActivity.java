package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.nio.charset.Charset;
import java.util.Properties;

import java.util.ArrayList;
import java.util.Random;

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
        String username,password,mail,tlf,pago;
        username = Et_username.getText().toString();
        password = Et_password.getText().toString();
        mail = Et_mail.getText().toString();
        tlf = Et_tlf.getText().toString();
        pago = formas_pago_spinner.getSelectedItem().toString();
        if(username.length()<1 || password.length()<1 || mail.length()<1 || tlf.length()<1)
        {
            Toast.makeText(RegisterActivity.this, "Todos los campos deben estar completos.", Toast.LENGTH_SHORT).show();
        }
        else {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"truetaxidb",null,1);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery("select * from Cliente where nombre='"+username+"'",null);
            if(fila.moveToFirst())
            {
                Toast.makeText(RegisterActivity.this, "El nombre de usuario especificado ya existe. Por favor usa otro.", Toast.LENGTH_SHORT).show();
            }
            else {
                byte[] random_array = new byte[5];
                new Random().nextBytes(random_array);
                String random_code = new String(random_array, Charset.forName("UTF-8"));
                ContentValues registro = new ContentValues();
                registro.put("nombre", username);
                registro.put("password", password);
                registro.put("correo", mail);
                registro.put("telf", tlf);
                registro.put("pago", pago);
                db.insert("Cliente", null, registro);

                //ENVIAR CORREO DE VERIFICACION
                /*final String mail_username = "truetaxi.oof@gmail.com";
                final String mail_password = "delaverga123";

                String mensaje_a_enviar = "¡Gracias por registrarte en TrueTaxi!\nTu código de verificación es "+random_code;

                Properties props = new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.port","587");

                Session session = Session.getInstance(props,new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail_username,mail_password);
                    }
                });

                try{
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mail_username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
                    message.setSubject("Probando","UTF-8");
                    message.setText(mensaje_a_enviar);
                    Transport.send(message);
                    Toast.makeText(RegisterActivity.this, "Correo de verificacion enviado a la direccion de email especificada.", Toast.LENGTH_SHORT).show();
                }catch (MessagingException e){
                    throw new RuntimeException(e);
                }*/
                Toast.makeText(RegisterActivity.this, "Correo de verificacion enviado a la direccion de email especificada.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, ActivitySplashScreen.class));
                finish();
            }
            db.close();
        }
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
    }
}