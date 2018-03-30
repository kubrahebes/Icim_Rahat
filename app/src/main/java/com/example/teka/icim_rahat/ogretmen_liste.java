package com.example.teka.icim_rahat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by teka on 27.7.2017.
 */

public class ogretmen_liste extends Fragment {
ArrayList<ogretmen_class>liste;
    ArrayAdapter<String>adapter;
View vi;
 ogretmen_class deger;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vi= inflater.inflate(R.layout.ogretmen_listesi, container, false);

        final ListView listView=(ListView)vi.findViewById(R.id.ogretmen);


        liste=new ArrayList<ogretmen_class>() ;
        Button btnekle=(Button)vi.findViewById(R.id.ekle);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ogretmen_bilgisi");

        auth = FirebaseAuth.getInstance();


        Intent intent=getActivity().getIntent();
      final String   ad=intent.getStringExtra("giden");
        String tercih=intent.getStringExtra("tercih");

        if (tercih.equals("Is Sahibi")){

            btnekle.setVisibility(View.VISIBLE);
        }else {

            btnekle.setVisibility(View.GONE);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               liste=new ArrayList<ogretmen_class>();

                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    deger=verigetir.getValue(com.example.teka.icim_rahat.ogretmen_class.class);
                    if (deger.kullaniciadi.equals(ad)){
                        liste.add(new ogretmen_class(deger.ogretmenisim,deger.mezunolduguokul,deger.istecrubesi,
                                deger.danisman,deger.kullaniciadi));
                        ogretmen_adapter adapter=new ogretmen_adapter(vi.getContext(),R.layout.ogretmen_listesi,liste);
                        listView.setAdapter(adapter);

                    }


                }}

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }

        });
       // Toast.makeText(vi.getContext(),deger.ogretmenisim,Toast.LENGTH_SHORT).show();
       //


        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent into=new Intent(vi.getContext(),ogretmen_bilgisi_ekle.class);
                into.putExtra("giden",ad);
                startActivity(into);

            }
        });

        return vi;

    }


}
