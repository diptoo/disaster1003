package com.example.user.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Input extends AppCompatActivity {
    private EditText mName;
    private EditText mUniversity;
    private EditText mDepartment;
    private EditText mContact;
    private EditText acc_name;
    private EditText acc_contact;
    private EditText jsc_name;
    private EditText jsc_contact;
    private Button mSubmit;
    private DatabaseReference mDatabase,newpost;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();//assign current user
        mName=(EditText) findViewById(R.id.editText);
        mUniversity=(EditText) findViewById(R.id.editText2);
        mDepartment=(EditText) findViewById(R.id.editText3);
        mContact=(EditText) findViewById(R.id.editcontact);
        acc_name=(EditText)findViewById(R.id.acn);
        acc_contact=(EditText)findViewById(R.id.acc);
        jsc_name=(EditText)findViewById(R.id.jcn);
        jsc_contact=(EditText)findViewById(R.id.jcc);
        mSubmit=(Button) findViewById(R.id.button5);
        mProgressDialog= new ProgressDialog(this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Info");


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name=mName.getText().toString();
                String varsity=mUniversity.getText().toString();
                String dept=mDepartment.getText().toString();
                String contact=mContact.getText().toString();
                String accname=acc_name.getText().toString();
                String accontact=acc_contact.getText().toString();
                String jscname=jsc_name.getText().toString();
                String jsccontact=jsc_contact.getText().toString();
                mProgressDialog.setMessage("posting to Info...");
                mProgressDialog.show();
                newpost=mDatabase.push();
                newpost.child("name").setValue(name);
                newpost.child("university").setValue(varsity);
                newpost.child("department").setValue(dept);
                newpost.child("contact").setValue(contact);
                newpost.child("accname").setValue(accname);
                newpost.child("accontact").setValue(accontact);
                newpost.child("jscname").setValue(jscname);
                newpost.child("jsccontact").setValue(jsccontact);
                newpost.child("uid").setValue(mCurrentUser.getUid());

                mProgressDialog.dismiss();

                startActivity(new Intent(Input.this,Check.class));
            }
        });


    }
}
