package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Createprofile extends AppCompatActivity {

    EditText eTusername, eTmetier1, eTmetier2, eTbio, eTmail, eTsocial1, eTsocial2, eTsocial3, eTsocial4, eTsocial5;
    Button btnCreateprofile, btncancelprofile;
    ImageView createPpincv;
    Uri imageUri;
    UploadTask uploadTask;
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth  authProfile;
    Allusers member;
    String currentUserId;
    ProgressBar progressBar;



   private static final int PICK_IMAGE =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createprofile);


        eTusername = findViewById(R.id.createusername);
        eTmail = findViewById(R.id.createmail);
        eTmetier1 = findViewById(R.id.cmetier1);
        eTmetier2 = findViewById(R.id.cmetier2);
        eTbio = findViewById(R.id.cbio);
        eTsocial1 = findViewById(R.id.csocial1);
        eTsocial2 = findViewById(R.id.csocial2);
        eTsocial3 = findViewById(R.id.csocial3);
        eTsocial4 = findViewById(R.id.csocial4);
        eTsocial5 = findViewById(R.id.csocial5);
        progressBar = findViewById(R.id.createprogress);
        btnCreateprofile = findViewById(R.id.btncreateprofile);
        btncancelprofile = findViewById(R.id.btncancelcreate);
        createPpincv = findViewById(R.id.createppincv);
        member = new Allusers();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile image");
        databaseReference = firebaseDatabase.getReference("All Users");

        btncancelprofile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Createprofile.this, Profile.class));
            }
        }));

        btnCreateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

        createPpincv.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE);
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null){
                imageUri = data.getData();

                Glide.with(this).load(imageUri).into(createPpincv);
            }

        }catch (Exception e){
            Utility.showToast(this,"Error"+e);
        }


    }


    private String getFileExt (Uri uri){
        ContentResolver contentResolver =  getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    private void uploadData() {
        String username = eTusername.getText().toString();
        String metier1 = eTmetier1.getText().toString();
        String metier2 = eTmetier2.getText().toString();
        String mail = eTmail.getText().toString();
        String bio = eTbio.getText().toString();
        String social1 = eTsocial1.getText().toString();
        String social2 = eTsocial2.getText().toString();
        String social3 = eTsocial3.getText().toString();
        String social4 = eTsocial4.getText().toString();
        String social5 = eTsocial5.getText().toString();

        if (!TextUtils.isEmpty(username)  ||  !TextUtils.isEmpty(metier1)  ||  !TextUtils.isEmpty(metier2)  ||!TextUtils.isEmpty(mail)  ||
        !TextUtils.isEmpty(bio)  ||  !TextUtils.isEmpty(social1)  ||  !TextUtils.isEmpty(social2) ||  !TextUtils.isEmpty(social3)  ||
        !TextUtils.isEmpty(social4)  ||  !TextUtils.isEmpty(social5)  || imageUri != null){
            final  StorageReference reference = storageReference.child(System.currentTimeMillis() + "profile.jpg" + getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        Map<String, String> profile = new HashMap<>();
                        profile.put("username", username);
                        profile.put("metier1", metier1);
                        profile.put("metier2", metier2);
                        profile.put("mail", mail);
                        profile.put("bio", bio);
                        profile.put("social1", social1);
                        profile.put("social2", social2);
                        profile.put("social3", social3);
                        profile.put("social4", social4);
                        profile.put("social5", social5);
                        profile.put("profile image",downloadUri.toString());
                        profile.put("uid", currentUserId);


                        member.setName(username);
                        member.setMetier1(metier1);
                        member.setMetier2(metier2);
                        member.setMail(mail);
                        member.setBio(bio);
                        member.setSocial1(social1);
                        member.setSocial2(social2);
                        member.setSocial3(social3);
                        member.setSocial4(social4);
                        member.setSocial5(social5);
                        member.setUid((currentUserId));

                        databaseReference.child(currentUserId).setValue(member);

                        documentReference.set(profile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Utility.showToast(getApplicationContext(), "profile created");
                                        progressBar.setVisibility(View.INVISIBLE);

                                        Intent intent = new Intent(Createprofile.this, Profile.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Utility.showToast(Createprofile.this, "failed");
                                    }
                                });
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                    }else {
                        Utility.showToast(getApplicationContext(),"Please fill all Fields");
                    }



        }
    }
