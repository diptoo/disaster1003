package com.example.user.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class register_activity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonContinue;
    private TextView textViewsignin;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;

    public register_activity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        firebaseAuth = FirebaseAuth.getInstance();//initialize
        ///same for mAuth
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(this);

        userName=(EditText) findViewById(R.id.editUser);
        editTextEmail = (EditText) findViewById(R.id.editemail);
        editTextPassword = (EditText) findViewById(R.id.editpass);
        buttonContinue = (Button) findViewById(R.id.btncnt);
        buttonRegister = (Button) findViewById(R.id.btnreg);
        textViewsignin=(TextView) findViewById(R.id.textsign);
        buttonContinue.setOnClickListener(this);
//        buttonRegister.setOnClickListener(this);
        //textViewsignin.setOnClickListener(this);
    }
    private void registerUser(){

        //getting email and password from edit texts
        final String name=userName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        //Toast.makeText(register_activity.this,"dhoke nai",Toast.LENGTH_LONG).show();
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(register_activity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            String user_id=firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db=mDatabase.child(user_id);
                            current_user_db.child("name").setValue(name);
                            current_user_db.child("image").setValue("default");
                        }else{
                            //display some message here
                            Toast.makeText(register_activity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                        Intent mainIntent=new Intent(register_activity.this,blog_app.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                });
      //  Intent intent = new Intent(register_activity.this,Profile_info.class);
        //startActivity(intent);

    }
    @Override
    public void onClick(View view) {
       if (view==buttonContinue)
       {
           registerUser();
       }
       
    }


}
