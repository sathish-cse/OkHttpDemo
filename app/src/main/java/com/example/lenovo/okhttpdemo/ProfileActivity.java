package com.example.lenovo.okhttpdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    EditText email,phone,gender;
    TextView name;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gender=(EditText)findViewById(R.id.input_id);
        email=(EditText)findViewById(R.id.input_email);
        phone=(EditText)findViewById(R.id.input_phone);
        name=(TextView)findViewById(R.id.input_name);
        profile=(ImageView)findViewById(R.id.imageView);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching Details");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Intent i=getIntent();
        gender.setText(i.getStringExtra("gender"));
        email.setText(i.getStringExtra("email"));
        phone.setText(i.getStringExtra("phone"));
        name.setText(i.getStringExtra("name"));
        Picasso.with(getApplicationContext()).load(i.getStringExtra("image")).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(profile);

        pd.dismiss();

    }
}
