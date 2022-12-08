package com.dam.testapp2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    ImageView iVlogoutprofile, iVprofilepicture, iVbackgroundpicture, iVchangeprofilepicture, iVchangebackgroundpicture;
    TextView tVprofileusername, tVmetier1, tVmetier2;



    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_profile);

        iVlogoutprofile = findViewById(R.id.logoutprofile);
        iVchangeprofilepicture = findViewById(R.id.editppp);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        iVchangeprofilepicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (Profile.this, Editprofpicture.class));
            }
        }));



        iVlogoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getCurrentUser();
                firebaseAuth.signOut();
                finish();
            }
        });



    }
}