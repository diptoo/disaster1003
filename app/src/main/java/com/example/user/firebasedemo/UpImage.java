package com.example.user.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.user.firebasedemo.R.id.imageView;

public class UpImage extends AppCompatActivity {
  private Button mSelectImage;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog mProgressDialog;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_image);
        mStorage= FirebaseStorage.getInstance().getReference();
        mSelectImage=(Button) findViewById(R.id.button2);
        mProgressDialog= new ProgressDialog(this);
        mImageView=(ImageView) findViewById(R.id.imageView6) ;
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==GALLERY_INTENT && resultCode==RESULT_OK)
        {
            mProgressDialog.setMessage("Uploadin...");
            mProgressDialog.show();
             Uri uri=data.getData();
            StorageReference filepath= mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UpImage.this,"Upload done",Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
                    @SuppressWarnings("VisibleForTests")  Uri downloadUri=taskSnapshot.getDownloadUrl();
                    Picasso.with(UpImage.this).load(downloadUri).fit().centerCrop().into(mImageView);

                }
            });
        }
    }
}
