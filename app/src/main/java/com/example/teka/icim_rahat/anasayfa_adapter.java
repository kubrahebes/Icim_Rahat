package com.example.teka.icim_rahat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by teka on 26.7.2017.
 */

public class anasayfa_adapter extends ArrayAdapter<anasayfa_liste_class> {
    public anasayfa_adapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<anasayfa_liste_class> objects) {
        super(context, resource, objects);
    }
    private StorageReference mStorageRef;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("okul_adi");

     String kullaniciad;

    il_ilce_okul_adi_class value;
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {




        final int[] sayac = {0};
        View v = convertView;
        if (v == null) {
            final LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.anasayfa_list, null);
            final anasayfa_liste_class hs = getItem(position);
            if (hs != null) {

                final TextView kullaniciadi = (TextView) v.findViewById(R.id.kullaniciadi);
                kullaniciadi.setText(hs.isim);
               // Toast.makeText(vi.getContext(), hs.isim, Toast.LENGTH_SHORT).show();


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot verigetir : dataSnapshot.getChildren()) {

                            value = verigetir.getValue(il_ilce_okul_adi_class.class);

                                if (value.okul_ad.equals(hs.isim.toString())){

                                   kullaniciad=value.kullaniciadi.toString();

                                }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });

                //profil resmine tıklandıgında

                Button imageButton = (Button) v.findViewById(R.id.profil_resim);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(v.getContext(),Icim_Rahat.class);
                        intent.putExtra("tercih","veli");
                        intent.putExtra("giden",kullaniciad);
                        getContext().startActivity(intent);

                    }
                });

                TextView aciklama=(TextView)v.findViewById(R.id.yorum);
                final Button like = (Button) v.findViewById(R.id.like);
                ImageView resim = (ImageView) v.findViewById(R.id.txt);
                resim.setBackgroundResource(R.drawable.resim4);
                // kullaniciadi.setText(hs.isim);
                 aciklama.setText("cocuklarrrrr");


                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sayac[0]++;
                        if (sayac[0] %2==1){
                            //count.setText(sayac);
                            like.setBackgroundResource(R.drawable.like);
                        }
                        else {
                            like
                                    .setBackgroundResource(R.drawable.like_white);}
                    }

                });

            }



        }
        return v;


    }
}
