package com.example.teka.icim_rahat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by teka on 5.8.2017.
 */

public class ogretmen_adapter <ogretmen_class>extends ArrayAdapter {

    public ogretmen_adapter(@NonNull Context context, @LayoutRes int resource,
                            @NonNull ArrayList<ogretmen_class> objects) {

        super(context, resource, objects);
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            final LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ogretmen_adapter,null);

            com.example.teka.icim_rahat.ogretmen_class hs=
                    (com.example.teka.icim_rahat.ogretmen_class) getItem(position);
            if (hs != null) {

                ImageView resim=(ImageView)v.findViewById(R.id.ogrtmnrsm);
                final TextView isim = (TextView) v.findViewById(R.id.isim);
                isim.setText(hs.ogretmenisim);
                resim.setImageResource(R.drawable.gulenguz);
   }
        }

            return v;
        }

    }
