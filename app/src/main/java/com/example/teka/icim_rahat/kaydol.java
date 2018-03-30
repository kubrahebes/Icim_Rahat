package com.example.teka.icim_rahat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

/**
 * Created by teka on 14.7.2017.
 */

public class kaydol extends Activity {
EditText KullaniciA,Sifre;
    Button Kaydol;
RadioButton veli,issahibi;
    RadioGroup group;

    DatabaseReference myRef;
    FirebaseDatabase database;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaydol);


KullaniciA=(EditText)findViewById(R.id.kullanici);

        Sifre=(EditText)findViewById(R.id.sifre);

        Kaydol=(Button)findViewById(R.id.kaydol);

        veli=(RadioButton)findViewById(R.id.tercihveli);

        issahibi=(RadioButton)findViewById(R.id.tercihissahibi);

        group=(RadioGroup)findViewById(R.id.tercih);

        database = FirebaseDatabase.getInstance();
        myRef  = database.getReference("kullanicilar");
        //kaydol butonuna tıklandıgında

        Kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secilen=group.getCheckedRadioButtonId();
                String secilentext="";
                if (secilen==R.id.tercihveli){
                    secilentext=veli.getText().toString();
                    Intent into=new Intent(kaydol.this,il_liste.class);
                    into.putExtra("giden",KullaniciA.getText());
                    into.putExtra("tercih",secilentext);
                    startActivity(into);
                }
                else {
                    secilentext=issahibi.getText().toString();
                    Intent into=new Intent(kaydol.this,Icim_Rahat.class);
                    into.putExtra("giden",KullaniciA.getText());
                    into.putExtra("tercih",secilentext);
                    startActivity(into);
                }

                kullanici kullaniciekle=new kullanici(KullaniciA.getText().toString(),Sifre.getText().toString(),secilentext);
                          myRef.push().setValue(kullaniciekle);



            }
        });




    }


}

