package com.example.teka.icim_rahat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class Icim_Rahat extends AppCompatActivity {
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
    private StorageReference mStorageRef;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    try {

                        fragment=new duzeltme();
                    } catch (IOException e) {
                        e.printStackTrace();}
                    break;

                case R.id.navigation_dashboard:
                    fragment=new ogretmen_liste();
                    break;

                case R.id.navigation_notifications:
                 fragment=new ogrenci_liste();
                    break;

                case R.id.iletisim:
                    fragment=new hakkimizda();
                    break;
            }


            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content,fragment).commit();
            return true;

        } };
    @Override

    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_sayfasi);




      //fragment ayarları
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager=getFragmentManager();
        try {fragment=new duzeltme();
        } catch (IOException e) {
            e.printStackTrace();}

        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment).commit();}



}
