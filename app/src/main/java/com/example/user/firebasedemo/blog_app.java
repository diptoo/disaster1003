package com.example.user.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class blog_app extends AppCompatActivity {
//LAYOUT A TOOLS LINE MISSING
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers,mDatabaseLike;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean mProcessLike=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_app);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent loginIntent=new Intent(blog_app.this,Login.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
                else
                {
                    System.out.println("null apy ni");
                }
            }
        };

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseUsers=FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseUsers.keepSynced(true);
        mDatabaseLike.keepSynced(true);
        mDatabase.keepSynced(true);

        mBlogList=(RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        checkUserExist();
    }

    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                final String post_key=getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsername(model.getUsername());
                viewHolder.setLikeBtn(post_key);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(blog_app.this,"you clicked a view",Toast.LENGTH_LONG).show();
                        Intent SingleBlogIntent=new Intent(blog_app.this,BlogSingleActivity.class);
                        SingleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(SingleBlogIntent);


                    }
                });
                viewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mProcessLike=true;

                            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (mProcessLike) {
                                        Toast.makeText(blog_app.this,"dhukse",Toast.LENGTH_LONG).show();
                                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {

                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                            mProcessLike = false;
                                        } else {
                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("Randomvalue");
                                            mProcessLike = false;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    }
                });
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
    private void checkUserExist() {
        final String user_id=mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(Login.this,"pass wrong",Toast.LENGTH_LONG).show();
                if(!dataSnapshot.hasChild(user_id))
                {
                    Intent setupIntent=new Intent(blog_app.this,SetupActivity.class);
                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupIntent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageButton mLikebtn;

        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;

        TextView post_title;
        public BlogViewHolder(View itemView) {
            super(itemView);
           mView=itemView;
            mLikebtn=(ImageButton) mView.findViewById(R.id.like_btn);
           mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");
           mAuth=FirebaseAuth.getInstance();
            mDatabaseLike.keepSynced(true);
            post_title=(TextView) mView.findViewById(R.id.post_title);
            post_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.v("blog_app","Some text");
                }
            });
        }
        public void setLikeBtn(final String post_key)
        {
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid()))
               {
                   mLikebtn.setImageResource(R.mipmap.like_red);
               }
               else{
                   mLikebtn.setImageResource(R.mipmap.like_gray);
               }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        public void setTitle(String title)
        {

            post_title.setText(title);
        }
        public void setDesc(String desc)
        {
            TextView post_desc=(TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx,String image)
        {
            ImageView post_image=(ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
        public void setUsername(String username)
        {
            TextView post_username=(TextView) mView.findViewById(R.id.post_username);
            post_username.setText(username);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(blog_app.this,PostActivity.class));
        }
        if(item.getItemId()==R.id.action_logout)
        {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }
}
