package com.example.teka.icim_rahat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by teka on 25.7.2017.
 */

public class kullanici_adpter extends ArrayAdapter<icirahat_class> {

    public kullanici_adpter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<icirahat_class> objects) throws IOException {

        super(context, resource, objects);}

    int sayac=0;

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.icim_rahat_class, null);}

        icirahat_class hs = getItem(position);

        if (hs != null) {

            final ImageView txt = (ImageView) v.findViewById(R.id.txt);
            TextView yorum = (TextView) v.findViewById(R.id.yorum);
            final Button like = (Button) v.findViewById(R.id.like);
           txt.setImageResource(R.drawable.res);
            yorum.setText("cocuklarrrr");
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sayac++;
                    if (sayac%2==1){
                    like.setBackgroundResource(R.drawable.like);}
                else {
                like.setBackgroundResource(R.drawable.like_white);}
                }

            });}
return v;
    }
}
