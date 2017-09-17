package com.example.user.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonateInfo extends AppCompatActivity {
    private String mPost_key=null;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_info);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");

        mPost_key = getIntent().getExtras().getString("blog_id");

        Toast.makeText(DonateInfo.this,mPost_key,Toast.LENGTH_LONG).show();
    }
}
