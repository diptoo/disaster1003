package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Profile_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

    }
    public void profile_2(View view){
        Intent intent = new Intent(this, Profile_Info2.class);
        startActivity(intent);
    }
}
