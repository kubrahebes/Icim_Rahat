package com.example.teka.icim_rahat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by teka on 8.8.2017.
 */

public class ogretmen_bilgisi_ekle extends Activity {

  EditText edtisim,edtokuladi,edttecrube,edtsinifi;
    Button ekle;


    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ogretmen_bilgisi_ekle);
        Intent intent=getIntent();
        final String   ad=intent.getStringExtra("giden");

edtisim=(EditText)findViewById(R.id.ogretmenadi);
        edtokuladi=(EditText)findViewById(R.id.mezunolduguokul);
        edtsinifi=(EditText)findViewById(R.id.danısman);
        edttecrube=(EditText)findViewById(R.id.istecrubesi);
      ekle=(Button)findViewById(R.id.kaydet);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ogretmen_bilgisi");
ekle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        ogretmen_class bilgiekle=new ogretmen_class(edtisim.getText().toString(),edtokuladi.getText().toString(),edttecrube.getText().toString(),edtsinifi.getText().toString(),ad.toString());
        myRef.push().setValue(bilgiekle);
      edtokuladi.setText("");
        edttecrube.setText("");
        edtsinifi.setText("");
        edtisim.setText("");
       // Intent into=new Intent(ogretmen_bilgisi_ekle.this,ogretmen_liste.TRIM_MEMORY_COMPLETE);
       // startActivity(into);


    }
});
    }
}
