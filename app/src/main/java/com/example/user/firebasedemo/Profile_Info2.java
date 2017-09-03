package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Profile_Info2 extends AppCompatActivity {
    EditText editTextName;
    Spinner spinjob;
    Button buttonAddJob;
    ListView listViewArtists;

    DatabaseReference databaseJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__info2);



        //getting the reference of artists node
        databaseJob = FirebaseDatabase.getInstance().getReference("jobs");

        //getting views
        //editTextName = (EditText) findViewById(R.id.editTextName);
        spinjob = (Spinner) findViewById(R.id.spinnerj);
        //listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        buttonAddJob = (Button) findViewById(R.id.btnreg);

        //list to store artists
        //artists = new ArrayList<>();

        buttonAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addjob();
            }
        });

    }
    public void sign_in(View view)
    {
        Intent intent = new Intent(Profile_Info2.this,Login.class);
        startActivity(intent);
    }

    public void addjob()
    {
        String job = spinjob.getSelectedItem().toString();

        if (!TextUtils.isEmpty(job)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseJob.push().getKey();

            //creating an Artist Object
            Job jobname = new Job(id,job);

            //Saving the Artist
            databaseJob.child(id).setValue(jobname);

            //setting edittext to blank again


            //displaying a success toast
            Toast.makeText(this, "job added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
    }

