package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbgmLogin extends AppCompatActivity {

    private static  final int RC_SIGN_IN=0;
    private FirebaseAuth auth;
    private Button mlogout;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbgm_login);
        auth= FirebaseAuth.getInstance();//SAME LINE
        //firebaseAuth = FirebaseAuth.getInstance();//initialize
        //String user_id=auth.getCurrentUser().getUid();
       // Toast.makeText(FbgmLogin.this,user_id,Toast.LENGTH_LONG).show();
        ///same for mAuth
      //  mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mlogout=(Button)findViewById(R.id.logout);

        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.logout){
                    AuthUI.getInstance()
                            .signOut(FbgmLogin.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("AUTH","USER LOGGED OUT!");
                                    finish();
                                    System.out.println("Print a line");
                                    //Toast.makeText(FbgmLogin.this,"Hi",Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        if(auth.getCurrentUser()!=null){
            //already logged in
            Log.d("AUTH", auth.getCurrentUser().getEmail());
            //Toast.makeText(FbgmLogin.this,"dhukhse1",Toast.LENGTH_LONG).show();
        }
        else{
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER)

                    .build(),1);
            //String user_id=auth.getCurrentUser().getUid();
            //Toast.makeText(FbgmLogin.this,"dhukhse2",Toast.LENGTH_LONG).show();
            startActivity(new Intent(FbgmLogin.this,DonateHome.class));
        }


    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK) {

                Toast.makeText(FbgmLogin.this,"dhukhse3",Toast.LENGTH_LONG).show();
                //user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());
                //String user_id=auth.getCurrentUser().getUid();
                //Toast.makeText(FbgmLogin.this,user_id,Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("AUTH","NOT AUTHENTICATED");
            }
        }
    }

    /*public void onClick(View view){
        if(view.getId()==R.id.logout){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH","USER LOGGED OUT!");
                            finish();
                            System.out.println("Print a line");
                            Toast.makeText(FbgmLogin.this,"Hi",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    } */

}