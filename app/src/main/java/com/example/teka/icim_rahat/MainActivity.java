package com.example.teka.icim_rahat;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static android.R.attr.tag;
import static com.twitter.sdk.android.core.TwitterCore.TAG;


public class MainActivity extends AppCompatActivity {
    EditText kadi, sifre;
    Button gris, Kaydol;
    ImageView resim;
    SignInButton signInButton;
    final  int RC_SIGN_IN = 2;

    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;

    String sfre = null, kullaniciadi = null;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                //tanımlamalar

                kadi = (EditText) findViewById(R.id.kullanici);
                sifre = (EditText) findViewById(R.id.sifre);
                gris = (Button) findViewById(R.id.gris);
                Kaydol = (Button) findViewById(R.id.kaydol);
                resim = (ImageView) findViewById(R.id.images);
                signInButton = (SignInButton) findViewById(R.id.btnlogin);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("kullanicilar");


        mAuth = FirebaseAuth.getInstance();



                // griş buttonuna tıklanınca
                gris.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verigetir();

                    }
                });

                //Kaydol butonuna tıklanınca

                Kaydol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent into = new Intent(MainActivity.this, kaydol.class);
                        startActivity(into);
                    }
                });


        //singin butonuna tıklandıgında
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();}});
        //firebase
        // Write a message to the database
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null) {
                            startActivity(new Intent(MainActivity.this, Icim_Rahat.class));}}
                };
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(MainActivity.this, "hattaa!!!!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
            }

            // Configure Google Sign In


            private void signIn() {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }@Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = result.getSignInAccount();
                        firebaseAuthWithGoogle(account);
                    } else {
                        Toast.makeText(this, "hataa", Toast.LENGTH_SHORT).show();
                    }}}
            private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });


            }
            public void verigetir(){
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                            kullanici value = verigetir.getValue(kullanici.class);

                            if(value.sifre!=null){
                                if (value.sifre.equals(sifre.getText().toString()) && value.kullaniciadi.equals(kadi.getText().toString())) {

                                    if (value.tercih.equals("Is Sahibi")) {
                                        Intent into = new Intent(MainActivity.this, Icim_Rahat.class);
                                        into.putExtra("giden",value.kullaniciadi);
                                        into.putExtra("tercih",value.tercih);
                                        startActivity(into);
                                    } else {
                                        Intent into = new Intent(MainActivity.this, il_liste.class);
                                        into.putExtra("giden",value.kullaniciadi);
                                        into.putExtra("tercih",value.tercih);
                                        startActivity(into);
                                    }}
                            }}}
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });}}




