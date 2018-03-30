package com.example.teka.icim_rahat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by teka on 14.7.2017.
 */

public class il_liste extends Activity {
    il_isteclass value;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
   // private FirebaseAuth.AuthStateListener mAuthListener;
    public List<String> list_parent,cankiri,denizli,duzce,adiyaman,antep;
    public adapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
   public List<String> ankara_liste,antalya_liste,aydin_liste,artvin;
    public List<String> urfa_list,afyon_liste,bursa,balikesir,bolu,canakkale;
    public int last_position = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.il_liste);
        Intent intent=getIntent();
       final String ad=intent.getStringExtra("giden");
       // Toast.makeText(this, ad, Toast.LENGTH_SHORT).show();
         final String tercih=intent.getStringExtra("tercih");
        //ilçe seçildiginde anasayfaya gitme
        expandlist_view = (ExpandableListView)findViewById(R.id.exliste);
        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
         Intent into=new Intent(il_liste.this,anasayfa.class);
       into.putExtra("giden",(String)expand_adapter.getChild(groupPosition, childPosition));
        into.putExtra("isim",ad);
        into.putExtra("tercih",tercih);
        startActivity(into);

       return false;
     }
        });
        //tek bir ilin ilçelerini gösterme
        expandlist_view.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if(last_position != -1 && last_position != groupPosition)
                {
                    expandlist_view.collapseGroup(last_position);
                }
                last_position = groupPosition;

            }
        });
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("iller");
        cankiri=new ArrayList<String >();
        denizli=new ArrayList<String >();
        urfa_list=new ArrayList<String >();
        duzce=new ArrayList<String >();
        antep=new ArrayList<String >();
        ankara_liste=new ArrayList<String >();
        antalya_liste=new ArrayList<String >();
        aydin_liste=new ArrayList<String >();
        balikesir=new ArrayList<String >();
        bolu=new ArrayList<String >();
        bursa=new ArrayList<String >();
        canakkale=new ArrayList<String >();
        afyon_liste=new ArrayList<String >();
       adiyaman=new ArrayList<String>();
        artvin=new ArrayList<String >();

//listeleri doldurma
        hazirla();

//adaptor ayarlama
        expand_adapter = new adapter(list_parent,il_liste.this,list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz

    }

    public void hazirla() {

        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk
        cankiri = new ArrayList<String>();
        denizli = new ArrayList<String>();
        urfa_list = new ArrayList<String>();
        duzce = new ArrayList<String>();
        antep = new ArrayList<String>();
        ankara_liste = new ArrayList<String>();
        antalya_liste = new ArrayList<String>();
        aydin_liste = new ArrayList<String>();
        balikesir = new ArrayList<String>();
        bolu = new ArrayList<String>();
        bursa = new ArrayList<String>();
        canakkale = new ArrayList<String>();
        afyon_liste = new ArrayList<String>();
        adiyaman = new ArrayList<String>();
         artvin=new ArrayList<String >();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot hazirla : dataSnapshot.getChildren()) {
                value = hazirla.getValue(il_isteclass.class);
                    if (value.il!=null) {
    if (value.il.equals("Şanlıurfa")) {
        list_parent.add(value.il);
        urfa_list.add(value.ilce1);
        urfa_list.add(value.ilce2);
        urfa_list.add(value.ilce3);
        urfa_list.add(value.ilce4);
        urfa_list.add(value.ilce5);
        urfa_list.add(value.ilce6);
        urfa_list.add(value.ilce7);
        urfa_list.add(value.ilce8);
    } else if (value.il.equals("Gaziantep")) {

        list_parent.add(value.il);
        antep.add(value.ilce1);
        antep.add(value.ilce2);
        antep.add(value.ilce3);
        antep.add(value.ilce4);
        antep.add(value.ilce5);
        antep.add(value.ilce6);
        antep.add(value.ilce7);
        antep.add(value.ilce8);

    } else if (value.il.equals("Adıyaman")) {
        list_parent.add(value.il);
        adiyaman.add(value.ilce1);
        adiyaman.add(value.ilce2);
        adiyaman.add(value.ilce3);
        adiyaman.add(value.ilce4);
        adiyaman.add(value.ilce5);
        adiyaman.add(value.ilce6);
        adiyaman.add(value.ilce7);
        adiyaman.add(value.ilce8);
    } else if (value.il.equals("Düzce")) {
        list_parent.add(value.il);
        duzce.add(value.ilce1);
        duzce.add(value.ilce2);
        duzce.add(value.ilce3);
        duzce.add(value.ilce4);
        duzce.add(value.ilce5);
        duzce.add(value.ilce6);
        duzce.add(value.ilce7);
        duzce.add(value.ilce8);
    } else if (value.il.equals("Denizli")) {
        list_parent.add(value.il);
        denizli.add(value.ilce1);
        denizli.add(value.ilce2);
        denizli.add(value.ilce3);
        denizli.add(value.ilce4);
        denizli.add(value.ilce5);
        denizli.add(value.ilce6);
        denizli.add(value.ilce7);
        denizli.add(value.ilce8);
    } else if (value.il.equals("Çankırı")) {
        list_parent.add(value.il);
        cankiri.add(value.ilce1);
        cankiri.add(value.ilce2);
        cankiri.add(value.ilce3);
        cankiri.add(value.ilce4);
        cankiri.add(value.ilce5);
        cankiri.add(value.ilce6);
        cankiri.add(value.ilce7);
        cankiri.add(value.ilce8);
    } else if (value.il.equals("Çanakkale")) {
        list_parent.add(value.il);
        canakkale.add(value.ilce1);
        canakkale.add(value.ilce2);
        canakkale.add(value.ilce3);
        canakkale.add(value.ilce4);
        canakkale.add(value.ilce5);
        canakkale.add(value.ilce6);
        canakkale.add(value.ilce7);
        canakkale.add(value.ilce8);
    } else if (value.il.equals("Bolu")) {
        list_parent.add(value.il);
        bolu.add(value.ilce1);
        bolu.add(value.ilce2);
        bolu.add(value.ilce3);
        bolu.add(value.ilce4);
        bolu.add(value.ilce5);
        bolu.add(value.ilce6);
        bolu.add(value.ilce7);
        bolu.add(value.ilce8);
    } else if (value.il.equals("Balıkesir")) {
        list_parent.add(value.il);
        balikesir.add(value.ilce1);
        balikesir.add(value.ilce2);
        balikesir.add(value.ilce3);
        balikesir.add(value.ilce4);
        balikesir.add(value.ilce5);
        balikesir.add(value.ilce6);
        balikesir.add(value.ilce7);
        balikesir.add(value.ilce8);
    } else if (value.il.equals("Bursa")) {
        list_parent.add(value.il);
        bursa.add(value.ilce1);
        bursa.add(value.ilce2);
        bursa.add(value.ilce3);
        bursa.add(value.ilce4);
        bursa.add(value.ilce5);
        bursa.add(value.ilce6);
        bursa.add(value.ilce7);
        bursa.add(value.ilce8);
    } else if (value.il.equals("Afyonkarahisar")) {
        list_parent.add(value.il);
        afyon_liste.add(value.ilce1);
        afyon_liste.add(value.ilce2);
        afyon_liste.add(value.ilce3);
        afyon_liste.add(value.ilce4);
        afyon_liste.add(value.ilce5);
        afyon_liste.add(value.ilce6);
        afyon_liste.add(value.ilce7);
        afyon_liste.add(value.ilce8);
    } else if (value.il.equals("Artvin")) {
        list_parent.add(value.il);
        artvin.add(value.ilce1);
        artvin.add(value.ilce2);
        artvin.add(value.ilce3);
        artvin.add(value.ilce4);
        artvin.add(value.ilce5);
        artvin.add(value.ilce6);
        artvin.add(value.ilce7);
        artvin.add(value.ilce8);
    } else if (value.il.equals("Aydın")) {
        list_parent.add(value.il);
        aydin_liste.add(value.ilce1);
        aydin_liste.add(value.ilce2);
        aydin_liste.add(value.ilce3);
        aydin_liste.add(value.ilce4);
        aydin_liste.add(value.ilce5);
        aydin_liste.add(value.ilce6);
        aydin_liste.add(value.ilce7);
        aydin_liste.add(value.ilce8);
    } else if (value.il.equals("Ankara")) {

        list_parent.add(value.il);
        ankara_liste.add(value.ilce1);
        ankara_liste.add(value.ilce2);
        ankara_liste.add(value.ilce3);
        ankara_liste.add(value.ilce4);
        ankara_liste.add(value.ilce5);
        ankara_liste.add(value.ilce6);
        ankara_liste.add(value.ilce7);
        ankara_liste.add(value.ilce8);

    } else if (value.il.equals("Antalya")) {

        list_parent.add(value.il);
        antalya_liste.add(value.ilce1);
        antalya_liste.add(value.ilce2);
        antalya_liste.add(value.ilce3);
        antalya_liste.add(value.ilce4);
        antalya_liste.add(value.ilce5);
        antalya_liste.add(value.ilce6);
        antalya_liste.add(value.ilce7);
        antalya_liste.add(value.ilce8);
    } else {
        Toast.makeText(il_liste.this, "hataaa", Toast.LENGTH_SHORT).show();

}} else {
                        Toast.makeText(il_liste.this, "Bos gelen veri", Toast.LENGTH_SHORT).show();}}
                list_child.put(list_parent.get(0), ankara_liste);
                list_child.put(list_parent.get(1), antalya_liste);
                list_child.put(list_parent.get(12), adiyaman);
                list_child.put(list_parent.get(4), afyon_liste);
                list_child.put(list_parent.get(3), artvin);
                list_child.put(list_parent.get(2), aydin_liste);
                list_child.put(list_parent.get(6), balikesir);
                list_child.put(list_parent.get(5), bursa);
                list_child.put(list_parent.get(7), bolu);
                list_child.put(list_parent.get(9), cankiri);
                list_child.put(list_parent.get(8), canakkale);
                list_child.put(list_parent.get(13), antep);
                list_child.put(list_parent.get(10), denizli);
                list_child.put(list_parent.get(14), urfa_list);
                list_child.put(list_parent.get(11), duzce);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }
}
