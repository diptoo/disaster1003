package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogSingleActivity extends AppCompatActivity {

    private String mPost_key=null;
    private DatabaseReference mDatabase;

    private ImageView mBlogSingleImage;
    private TextView mBlogSingleTitle,mBlogSingleDesc;
    private Button mRemoveBtn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");

        mAuth=FirebaseAuth.getInstance();
         mPost_key = getIntent().getExtras().getString("blog_id");
       //layout
        mBlogSingleImage=(ImageView) findViewById(R.id.singleimage);
        mBlogSingleTitle=(TextView) findViewById(R.id.singletitle);
        mBlogSingleDesc=(TextView) findViewById(R.id.singledesc);
        mRemoveBtn=(Button) findViewById(R.id.button4);

        //Toast.makeText(BlogSingleActivity.this,post_key,Toast.LENGTH_LONG).show();
        //blog er key er vitor data save
        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           //we ccan get the image
                //data retrieve kora firebase theke
                String post_title=(String)dataSnapshot.child("title").getValue();
                String post_desc=(String)dataSnapshot.child("desc").getValue();
                String post_image=(String)dataSnapshot.child("image").getValue();
                String post_uid=(String) dataSnapshot.child("uid").getValue();
               /// data boshano layout a
                mBlogSingleTitle.setText(post_title);
                mBlogSingleDesc.setText(post_desc);
                ///picture layout a boshano
                Picasso.with(BlogSingleActivity.this).load(post_image).into(mBlogSingleImage);
                //jevabe current user id pabo
                //post uid er sathe match korlee sudhu matro remove button dekhte pabo and remove korte parbo
                if(mAuth.getCurrentUser().getUid().equals(post_uid))
                {
                    mRemoveBtn.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //remove post er function
        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //key dlt korle all dlt hoa jabe
                mDatabase.child(mPost_key).removeValue();
                Intent mainIntent=new Intent(BlogSingleActivity.this,blog_app.class);
                startActivity(mainIntent);
            }
        });
    }
}
