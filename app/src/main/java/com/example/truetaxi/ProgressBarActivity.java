package com.example.truetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProgressBarActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView tv1;
    private int progress=0;
    int counter=0;
    private Handler mhandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        mProgressBar=(ProgressBar) findViewById(R.id.progressbarr);
        tv1 = (TextView) findViewById(R.id.textloading);
        tv1.setText("Descarga en proceso");
        tv1.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(progress<100)
                {
                    progress++;

                    android.os.SystemClock.sleep(50);
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(progress);
                        }
                    });
                }
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("Descarga Completa!");
                        Toast.makeText(ProgressBarActivity.this, "Descarga completada!", Toast.LENGTH_SHORT).show();
                        generateNoteOnSD(ProgressBarActivity.this,"log","Prueba");
                        startActivity(new Intent(ProgressBarActivity.this, SolicitudesActivity.class));
                        finish();
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(progress<100)
                {
                    android.os.SystemClock.sleep(500);
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(counter==1) tv1.setText("Descarga en proceso.");
                            if(counter==2) tv1.setText("Descarga en proceso..");
                            if(counter==3) {tv1.setText("Descarga en proceso...");counter=0;}
                            counter++;
                        }
                    });
                }
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("Descarga Completa!");
                    }
                });
            }
        }).start();
    }

    public void cancelar(View view)
    {
        Toast.makeText(ProgressBarActivity.this, "Descarga cancelada.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProgressBarActivity.this, SolicitudesActivity.class));
        finish();
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        //Toast.makeText(context, "se mete", Toast.LENGTH_SHORT).show();
        try {
            File root = new File(Environment.getExternalStorageDirectory().getPath(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //TODO: SE ESTA METIENDO AQUI
            e.printStackTrace();
        }
    }
}