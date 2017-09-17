package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Check extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Query mCur;
    private TextView mName;
    private TextView mdept;
    private TextView mUni;
    private TextView mContact;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        mAuth=FirebaseAuth.getInstance();
        mName=(TextView) findViewById(R.id.textView9);
        mdept=(TextView) findViewById(R.id.textView11);
        mUni=(TextView) findViewById(R.id.textView13);
        mContact=(TextView) findViewById(R.id.textView15) ;
        String Cuid=mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Info");
        mCur=mDatabase.orderByChild("uid").equalTo(Cuid);

        mCur.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FireApp newPost = dataSnapshot.getValue(FireApp.class);
                System.out.println("name: " + newPost.name);
                System.out.println("department: " + newPost.department);
                System.out.println("university: " + newPost.university);
                System.out.println("contact: " + newPost.contact);
                mName.setText(newPost.name);
                mdept.setText(newPost.department);
                mUni.setText(newPost.university);
                mContact.setText(newPost.contact);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu resource file read kore
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(Check.this,PostActivity.class));
        }
        if(item.getItemId()==R.id.action_logout)
        {
            logout();
        }
        if(item.getItemId()==R.id.myPost)
        {
            startActivity(new Intent(Check.this,ProfilePage.class));
        }
        if(item.getItemId()==R.id.member_details)
        {
            startActivity(new Intent(Check.this,MemberDetails.class));
        }
        if(item.getItemId()==R.id.my_profile)
        {
            startActivity(new Intent(Check.this,Check.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        mAuth.signOut();
    }
}
