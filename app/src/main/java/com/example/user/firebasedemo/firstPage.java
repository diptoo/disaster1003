package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class firstPage extends AppCompatActivity implements View.OnClickListener{
    private Button collectButton;
    private Button donatetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        collectButton = (Button) findViewById(R.id.collect);
        donatetButton = (Button) findViewById(R.id.donate);

        collectButton.setOnClickListener(this);
        donatetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==collectButton) {

            startActivity(new Intent(firstPage.this, blog_app.class));
        }
        else if(view==donatetButton)
        {
            Toast.makeText(firstPage.this,"Donate home a dhukhse",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(firstPage.this,DonateHome.class));
        }
    }
}
