package com.example.teka.icim_rahat;

import android.app.Activity;
import android.content.Intent;
import android.opengl.EGLDisplay;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ramazan on 25.08.2017.
 */

public class hakkimizda_ekle extends Activity {
   Button ekle;
    EditText hakkimizda,adres,tel;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("hakkimizda");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hakkimizda_ekle);

        Intent intent=getIntent();
        final String ad=intent.getStringExtra("giden");


       ekle=(Button)findViewById(R.id.ekle);
        hakkimizda=(EditText)findViewById(R.id.misyon);
        adres=(EditText)findViewById(R.id.adress);
        tel=(EditText)findViewById(R.id.iletisim);


        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hakkimizda_class yeni=new hakkimizda_class(hakkimizda.getText().toString(),adres.getText().toString(),tel.getText().toString(),ad.toString());
                myRef.push().setValue(yeni);


                hakkimizda.setText("");
                adres.setText("");
                tel.setText("");
              //  Intent into=new Intent(hakkimizda_ekle.this, com.example.teka.icim_rahat.hakkimizda.class);
                //startActivity(into);

            }
        });

    }
}
