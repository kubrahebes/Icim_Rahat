package com.example.teka.icim_rahat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ramazan on 25.08.2017.
 */

public class hakkimizda extends Fragment {
    final int[] sayac = {0};
hakkimizda_class deger;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view;
        view=inflater.inflate(R.layout.hakkimizda, container, false);

        Intent intent=getActivity().getIntent();
         final String ad=intent.getStringExtra("giden");
        String tercih=intent.getStringExtra("tercih");

      //  Toast.makeText(view.getContext(), ad.toString(), Toast.LENGTH_SHORT).show();
        Button ekle=(Button)view.findViewById(R.id.ekle);
        final TextView misyon=(TextView)view.findViewById(R.id.misyon);
        final TextView adres=(TextView)view.findViewById(R.id.adress);
        final TextView iletisim=(TextView)view.findViewById(R.id.iletisim);
        if (tercih.equals("Is Sahibi")){
ekle.setVisibility(View.VISIBLE);
        }else {
            ekle.setVisibility(View.GONE);
        }

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent into=new Intent(view.getContext(),hakkimizda_ekle.class);
                into.putExtra("giden",ad);
                startActivity(into);
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("hakkimizda");
        auth = FirebaseAuth.getInstance();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                   deger = verigetir.getValue(hakkimizda_class.class);

                  if (deger.kulaniciadi.equals(ad)){
                      misyon.setText(deger.hakkimizda);
                      adres.setText(deger.adres);
                      iletisim.setText(deger.iletisim);
                      sayac[0]++;
                  }

                }
            /*    if (sayac.equals(0)){

                    Toast.makeText(this, "Lütfen Kaayıt Ekleyin", Toast.LENGTH_SHORT).show();
                }
*/

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        return view;
    }
}
