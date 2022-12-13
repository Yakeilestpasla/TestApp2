package com.dam.testapp2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile extends AppCompatActivity {


    ImageView iVlogoutprofile, iVprofilepicture, iVbackgroundpicture, iVchangeprofilepicture, iVchangebackgroundpicture, iVupdateprofile;
    TextView tVprofileusername, tVmail,tVbio, tVmetier1, tVmetier2, tVsocial1, tVsocial2, tVsocial3, tVsocial4, tVsocial5;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri imageUri;
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Allusers member;
    String currentUserId;
    FirebaseUser user;
    UploadTask uploadTask;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_profile);

        documentReference = db.collection("user").document("profile");


        iVlogoutprofile = findViewById(R.id.logoutprofile);
        iVchangeprofilepicture = findViewById(R.id.editppp);
        iVchangebackgroundpicture = findViewById(R.id.editbp);
        iVupdateprofile = findViewById(R.id.updateprofile);
        tVsocial1 = findViewById(R.id.social1);
        tVsocial2 = findViewById(R.id.social2);
        tVsocial3 = findViewById(R.id.social3);
        tVsocial4 = findViewById(R.id.social4);
        tVsocial5 = findViewById(R.id.social5);
        tVprofileusername = findViewById(R.id.username);
        tVmetier1 = findViewById(R.id.metier1);
        tVmetier2 = findViewById(R.id.metier2);
        tVmail = findViewById(R.id.pmail);
        iVbackgroundpicture = findViewById(R.id.bp);
        iVprofilepicture = findViewById(R.id.ppincv);
        tVbio = findViewById(R.id.bio);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("user");



//        Query query = databaseReference.orderByChild("email").equalTo((user.getEmail()));
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    String username = "" + ds.child("name").getValue();
//                    String email = "" + ds.child("email").getValue();
//                    String social1 = "" + ds.child("social1").getValue();
//                    String image = "" + ds.child("image").getValue();
//
//                    tVprofileusername.setText(username);
//                    tVmail.setText(email);
//                    tVsocial1.setText(social1);
//                    try {
//                        Glide.with(getApplicationContext()).load(image).into(iVprofilepicture);
//
//                    } catch (Exception e) {
//
//                        Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_add_a_photo_24).into(iVprofilepicture);
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        iVchangebackgroundpicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Updateprofile.class));
            }
        }));

        iVupdateprofile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Createprofile.class));

            }
        }));

        iVchangeprofilepicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Editprofpicture.class));
            }
        }));

        iVlogoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showToast(Profile.this, "Logout");
                firebaseAuth.getCurrentUser();
                firebaseAuth.signOut();
                startActivity(new Intent(Profile.this, SignIn.class));
            }
        });


    }

    public void ShowProfile(View view) {
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            Intent intent = new Intent(Profile.this, Profile.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Profile.this, Createprofile.class);
                            startActivity(intent);
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();


        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){
                            String username_result = task.getResult().getString("username");
                            String metier1_result = task.getResult().getString("metier1");
                            String metier2_result = task.getResult().getString("metier2");
                            String mail_result = task.getResult().getString("mail");
                            String bio_result = task.getResult().getString("bio");
                            String social1_result = task.getResult().getString("social1");
                            String social2_result = task.getResult().getString("social2");
                            String social3_result = task.getResult().getString("social3");
                            String social4_result = task.getResult().getString("social4");
                            String social5_result = task.getResult().getString("social5");
                            String Url = task.getResult().getString("url");

                            Glide.with(getApplicationContext()).load(Url).into(iVprofilepicture);

                            tVprofileusername.setText(username_result);
                            tVmail.setText(mail_result);
                            tVmetier1.setText(metier1_result);
                            tVmetier2.setText(metier2_result);
                            tVbio.setText(bio_result);
                            tVsocial1.setText(social1_result);
                            tVsocial2.setText(social2_result);
                            tVsocial3.setText(social3_result);
                            tVsocial4.setText(social4_result);
                            tVsocial5.setText(social5_result);

                        }else {
                            Utility.showToast(Profile.this,"No profile found");                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
