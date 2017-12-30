package com.example.rajk.raktdoot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class profile extends Snackbar {
Button editProfile;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    TextView me,name,contact,bloodgrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_profile, frame);
        editProfile = (Button)findViewById(R.id.edit_profile);
        me = (TextView)findViewById(R.id.me);
        name = (TextView)findViewById(R.id.name);
        contact = (TextView)findViewById(R.id.contact);
        bloodgrp = (TextView)findViewById(R.id.bloodgrp);
        sharedPreference = getSharedPreferences("UserRegistered", MODE_PRIVATE);
        editor = sharedPreference.edit();

        Snackbar.profile.setEnabled(false);
        Snackbar.notify.setEnabled(true);
        Snackbar.home.setEnabled(true);



        String c = sharedPreference.getString("UserRegistered", "");
        if(TextUtils.isEmpty(c)) {

        }
        else
        {
            String Name =sharedPreference.getString("Name","");
            char f =Name.charAt(0);
            char l =Name.charAt(Name.indexOf(" ")+1);
            me.setText(f+""+l);
            name.setText(Name);
            contact.setText(sharedPreference.getString("Contact",""));
            bloodgrp.setText(sharedPreference.getString("BloodG",""));
        }



            editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, com.example.rajk.raktdoot.editProfile.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile.this,Main2Activity.class));
    }
}
