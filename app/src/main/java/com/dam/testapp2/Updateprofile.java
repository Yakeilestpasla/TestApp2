package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Updateprofile extends AppCompatActivity {

    EditText eTusername, eTmetier1, eTmetier2, eTbio, eTmail, eTsocial1, eTsocial2, eTsocial3, eTsocial4, eTsocial5;
    Button btnUpdateprofile;
    ImageView updatePpincv;

    Uri imageUri;
    UploadTask uploadTask;
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth authProfile;
    Allusers member;
    String currentUserId;


    private static final int PICK_IMAGE =1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);


        eTusername = findViewById(R.id.updateusername);
        eTmail = findViewById(R.id.updatemail);
        eTmetier1 = findViewById(R.id.umetier1);
        eTmetier2 = findViewById(R.id.umetier2);
        eTbio = findViewById(R.id.ubio);
        eTsocial1 = findViewById(R.id.usocial1);
        eTsocial2 = findViewById(R.id.usocial2);
        eTsocial3 = findViewById(R.id.usocial3);
        eTsocial4 = findViewById(R.id.usocial4);
        eTsocial5 = findViewById(R.id.usocial5);
        btnUpdateprofile = findViewById(R.id.btnsaveprofile);

        updatePpincv = findViewById(R.id.updateppincv);
        member = new Allusers();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        documentReference = db.collection("user").document("profile");
        storageReference = firebaseStorage.getInstance().getReference("profile image");
        databaseReference = firebaseDatabase.getReference("All Users");




        btnUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

    updatePpincv.setOnClickListener((new View.OnClickListener() {
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

                Glide.with(this).load(imageUri).into(updatePpincv);
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



//    private void updateProfile() {
//        String username = eTusername.getText().toString();
//        String metier1 = eTmetier1.getText().toString();
//        String metier2 = eTmetier2.getText().toString();
//        String mail = eTmail.getText().toString();
//        String bio = eTbio.getText().toString();
//        String social1 = eTsocial1.getText().toString();
//        String social2 = eTsocial2.getText().toString();
//        String social3 = eTsocial3.getText().toString();
//        String social4 = eTsocial4.getText().toString();
//        String social5 = eTsocial5.getText().toString();
//
//        if (!TextUtils.isEmpty(username)  ||  !TextUtils.isEmpty(metier1)  ||  !TextUtils.isEmpty(metier2)  ||!TextUtils.isEmpty(mail)  ||
//                !TextUtils.isEmpty(bio)  ||  !TextUtils.isEmpty(social1)  ||  !TextUtils.isEmpty(social2) ||  !TextUtils.isEmpty(social3)  ||
//                !TextUtils.isEmpty(social4)  ||  !TextUtils.isEmpty(social5)  || imageUri != null){
//            final  StorageReference reference = storageReference.child(System.currentTimeMillis() + "profile.jpg" + getFileExt(imageUri));
//            uploadTask = reference.putFile(imageUri);
//
//            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()){
//                                throw task.getException();
//                            }
//                            return reference.getDownloadUrl();
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//
//                            if (task.isSuccessful()) {
//                                Uri downloadUri = task.getResult();
//
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//
//
//        }else {
//            Utility.showToast(getApplicationContext(),"Please fill all Fields");
//        }
//
//    }


    @Override
    protected void onStart() {
        super.onStart();


        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        if (task.getResult().exists() || imageUri != null) {

                            String usernameResult = task.getResult().getString("username");
                            String metier1Result = task.getResult().getString("metier1");
                            String metier2Result = task.getResult().getString("metier2");
                            String mailResult = task.getResult().getString("mail");
                            String bioResult = task.getResult().getString("bio");
                            String social1Result = task.getResult().getString("social1");
                            String social2Result = task.getResult().getString("social2");
                            String social3Result = task.getResult().getString("social3");
                            String social4Result = task.getResult().getString("social4");
                            String social5Result = task.getResult().getString("social5");

                            //Glide.with();

                            eTusername.setText(usernameResult);
                            eTmetier1.setText(metier1Result);
                            eTmetier2.setText(metier2Result);
                            eTmail.setText(mailResult);
                            eTbio.setText(bioResult);
                            eTsocial1.setText(social1Result);
                            eTsocial2.setText(social2Result);
                            eTsocial3.setText(social3Result);
                            eTsocial4.setText(social4Result);
                            eTsocial5.setText(social5Result);

                        }else{
                            Utility.showToast(Updateprofile.this, "No profile");
                        }


                    }
                });
    }

    private void updateProfile() {

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

        if (imageUri != null){
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

                                final DocumentReference sDoc = db.collection("user").document("profile");
                                db.runTransaction(new Transaction.Function<Void>() {
                                            @Override
                                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                                DocumentSnapshot snapshot = transaction.get(sDoc);



                                                transaction.update(sDoc, "username", username);
                                                transaction.update(sDoc,"metier1", metier1);
                                                transaction.update(sDoc,"metier2", metier2);
                                                transaction.update(sDoc,"mail", mail);
                                                transaction.update(sDoc,"bio", bio);
                                                transaction.update(sDoc,"social1",social1);
                                                transaction.update(sDoc,"social2",social2);
                                                transaction.update(sDoc,"social3",social3);
                                                transaction.update(sDoc,"social4",social4);
                                                transaction.update(sDoc,"social5",social5);
                                                transaction.update(sDoc, "profile image",downloadUri.toString());
                                                transaction.update(sDoc,"uid",currentUserId);


                                                return null;
                                            }
                                        })
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Utility.showToast(Updateprofile.this,"updated");
                                                Intent intent = new Intent(Updateprofile.this, Profile.class);
                                                startActivity(intent);

                                            }

                                        })
                                        .addOnFailureListener(new OnFailureListener(){
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Utility.showToast(Updateprofile.this,"failed");
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
            final DocumentReference sDoc = db.collection("user").document("profile");
            db.runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                            DocumentSnapshot snapshot = transaction.get(sDoc);



                            transaction.update(sDoc, "username", username);
                            transaction.update(sDoc,"metier1", metier1);
                            transaction.update(sDoc,"metier2", metier2);
                            transaction.update(sDoc,"mail", mail);
                            transaction.update(sDoc,"bio", bio);
                            transaction.update(sDoc,"social1",social1);
                            transaction.update(sDoc,"social2",social2);
                            transaction.update(sDoc,"social3",social3);
                            transaction.update(sDoc,"social4",social4);
                            transaction.update(sDoc,"social5",social5);
                           // transaction.update(sDoc, "profile image",downloadUri.toString());
                            transaction.update(sDoc,"uid",currentUserId);


                            return null;
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Utility.showToast(Updateprofile.this,"updated");
                            Intent intent = new Intent(Updateprofile.this, Profile.class);
                            startActivity(intent);

                        }

                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Utility.showToast(Updateprofile.this,"failed");
                        }
                    });
        }











    }
}