package com.example.teka.icim_rahat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.teka.icim_rahat.R.id.image;

/**
 * Created by teka on 5.8.2017.
 */

public class duzeltme extends Fragment {

    View view;
    il_ilce_okul_adi_class deger;
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
    private static final int IMAGE_PICKER_SELECT = 3;

    private StorageReference mStorageRef;
    ArrayList<icirahat_class> arrayList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String ad;
    final File localfile = File.createTempFile("image", "jpeg");
    kullanici_adpter adpter;

    public duzeltme() throws IOException {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.duzeltme, container, false);


        //diger sayfadan gelen veriler
        Intent intent = getActivity().getIntent();
        ad = intent.getStringExtra("giden");
        String tercih = intent.getStringExtra("tercih");
        //  Toast.makeText(view.getContext(), ad.toString(), Toast.LENGTH_SHORT).show();

        Button resimekle = (Button) view.findViewById(R.id.btnekle);
        final ImageView image = (ImageView) view.findViewById(R.id.profil_resim);
        Button duzenle = (Button) view.findViewById(R.id.btnduzenle);
        ListView listView = (ListView) view.findViewById(R.id.resimlist);

        arrayList = new ArrayList<icirahat_class>();
        arrayList.add(new icirahat_class("Kübra"));
        arrayList.add(new icirahat_class("hebes"));
        arrayList.add(new icirahat_class("adana"));


        //  kullanici_adpter adapter= null;
        try {
            adpter = new kullanici_adpter(view.getContext(), R.layout.icim_rahat_class, arrayList);
            listView.setAdapter(adpter);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "hataaa adapter ayarı yapılamadı", Toast.LENGTH_SHORT).show();
        }


        if (tercih.equals("Is Sahibi")) {
            duzenle.setVisibility(View.VISIBLE);
            resimekle.setVisibility(View.VISIBLE);
        } else {
            duzenle.setVisibility(View.GONE);
            resimekle.setVisibility(View.GONE);
        }
//profil resmine tıklandıgında
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_PICKER_SELECT);
                return false;
            }
        });

//duzenle butonuna tıklandıgında
        duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view.getContext(), profil_duzenle.class);
                intent.putExtra("isim", ad);
                startActivity(intent);

            }
        });

        //Resim ekleme

        resimekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeri_int = new Intent();
                galeri_int.setType("image/*");

                galeri_int.setAction(Intent.ACTION_GET_CONTENT);
                galeri_int.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(galeri_int, 46);

                /*StorageReference indir=mStorageRef.child("resimler");
                indir.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap=BitmapFactory.decodeFile(localfile.getAbsolutePath());

                        image.setImageBitmap(bitmap);


                    }
                });*/


            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();
        //okul ismi ayarı
        final TextView okuladi = (TextView) view.findViewById(R.id.btnokuladi);
        //veritabanndan okul adını getirmek
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("okul_adi");
        auth = FirebaseAuth.getInstance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    deger = verigetir.getValue(il_ilce_okul_adi_class.class);
                    if (deger.kullaniciadi.equals(ad)) {
                        okuladi.setText(deger.okul_ad);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        return view;
    }
    // mTarifPhotosStorageReference=mFirebaseStorage.getReference().child(“resimler");

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.getActivity());
            if (path != null) {
                Uri uri = data.getData();
                StorageReference ref = mStorageRef.child("kubra");
                //image.setBackground(Drawable.createFromPath("false"));
                //image.setImageBitmap(data);
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //Resimler rsm=new Resim(ad,downloadUrl);
                        Toast.makeText(getActivity().getApplicationContext(), downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

}


