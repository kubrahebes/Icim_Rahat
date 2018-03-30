package com.example.teka.icim_rahat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by teka on 24.7.2017.
 */

public class anasayfa extends Activity {
   ListView liste;
    ArrayList<anasayfa_liste_class>arylist;
Button btn;
    Button imageButton,btnyeniyorum,btnyorum;
     ListView tumyorum;
    EditText yeniyoru;
    LinearLayout liner,liner2;
    ArrayList<String > arrayList;

    private StorageReference mStorageRef;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;


    il_ilce_okul_adi_class value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anasayfa);

        Intent into=getIntent();
        final String ilce=into.getStringExtra("giden");
        String isim=into.getStringExtra("isim");
        String tercih=into.getStringExtra("tercih");

        liste=(ListView) findViewById(R.id.anasayfalist);

        arylist=new ArrayList<anasayfa_liste_class>();
        //arylist.add(new anasayfa_liste_class(ilce));
        //arylist.add(new anasayfa_liste_class("ilce"));


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("okul_adi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    value = verigetir.getValue(il_ilce_okul_adi_class.class);
                    // Toast.makeText(Icim_Rahat.this, deger.okul_ad, Toast.LENGTH_SHORT).show();
                    if (ilce.equals(value.ilce)){
                        Toast.makeText(anasayfa.this, value.okul_ad.toString(), Toast.LENGTH_SHORT).show();

                        arylist.add(new anasayfa_liste_class(value.okul_ad));

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        anasayfa_adapter adapter=new anasayfa_adapter(anasayfa.this,R.layout.anasayfa_list,arylist);
        liste.setAdapter(adapter);









    }
}
