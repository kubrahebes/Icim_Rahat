package com.example.teka.icim_rahat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by teka on 25.7.2017.
 */

public class profil_duzenle extends Activity  {
   EditText okuladi,okulil,okulilce;
   Button kaydet,cikis,kesfet,videoekle;
    String secilen,secilen2;

    private StorageReference mStorageRef;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth auth;

    private static final int IMAGE_PICKER_SELECT=3;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_duzenle);

        kaydet=(Button)findViewById(R.id.kaydet);
        videoekle=(Button)findViewById(R.id.Ttntvideo);
        kesfet=(Button)findViewById(R.id.kesfet);
        cikis=(Button)findViewById(R.id.cikis);
        okuladi=(EditText)findViewById(R.id.okulAdi);
        okulil=(EditText)findViewById(R.id.iladi);
        okulilce=(EditText)findViewById(R.id.ilceadi);

final String text1;

        Intent intent=getIntent();
        final String ad=intent.getStringExtra("isim");
        database = FirebaseDatabase.getInstance();
         myRef = database.getReference("okul_adi");
        auth = FirebaseAuth.getInstance();

        kesfet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent into=new Intent(profil_duzenle.this,il_liste.class);
                startActivity(into);}});


        videoekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_PICKER_SELECT);
            }

        });


        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                il_ilce_okul_adi_class kayit=new il_ilce_okul_adi_class(okulil.getText().
                        toString(),okulilce.getText().toString(),okuladi.getText().toString(),ad.toString());
                myRef.push().setValue(kayit);
            }
        });

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(profil_duzenle.this, MainActivity.class));
                finish();}
        });}

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_PICKER_SELECT&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            StorageReference reference=mStorageRef.child("res");
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    Toast.makeText(profil_duzenle.this, "kayıt tamammmm", Toast.LENGTH_SHORT).show();


                }
            });

}
    }
  /*  public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         secilen=parent.getItemAtPosition(pos).toString();
if (secilen.equals("Ankara")){

}

//Toast.makeText(this, secilen, Toast.LENGTH_SHORT).show();
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

*/

}