package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class Updateprofile extends AppCompatActivity {

    EditText eTusername, eTmetier1, eTmetier2, eTbio, eTmail, eTsocial1, eTsocial2, eTsocial3, eTsocial4, eTsocial5;
    Button btnSaveprofile;
    ImageView editPpincv;

    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

//
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentuid = user.getUid();
//        documentReference = db.collection("user").document(currentuid);
//
//        eTusername = findViewById(R.id.updateusername);
//        eTmail = findViewById(R.id.updatemail);
//        eTmetier1 = findViewById(R.id.metier1);
//        eTmetier2 = findViewById(R.id.metier2);
//        eTbio = findViewById(R.id.bio);
//        eTsocial1 = findViewById(R.id.social1);
//        eTsocial2 = findViewById(R.id.social2);
//        eTsocial3 = findViewById(R.id.social3);
//        eTsocial4 = findViewById(R.id.social4);
//        eTsocial5 = findViewById(R.id.social5);
//        btnSaveprofile = findViewById(R.id.btnsaveprofile);
//        editPpincv = findViewById(R.id.updateppincv);
//
//
//        btnSaveprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateProfile();
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//        documentReference.get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//
//                        if (task.getResult().exists()) {
//
//                            String usernameResult = task.getResult().getString("username");
//                            String metier1Result = task.getResult().getString("metier1");
//                            String metier2Result = task.getResult().getString("metier2");
//                            String mailResult = task.getResult().getString("mail");
//                            String bioResult = task.getResult().getString("bio");
//                            String social1Result = task.getResult().getString("social1");
//                            String social2Result = task.getResult().getString("social2");
//                            String social3Result = task.getResult().getString("social3");
//                            String social4Result = task.getResult().getString("social4");
//                            String social5Result = task.getResult().getString("social5");
//
//                            eTusername.setText(usernameResult);
//                            eTmetier1.setText(metier1Result);
//                            eTmetier2.setText(metier2Result);
//                            eTmail.setText(mailResult);
//                            eTbio.setText(bioResult);
//                            eTsocial1.setText(social1Result);
//                            eTsocial2.setText(social2Result);
//                            eTsocial3.setText(social3Result);
//                            eTsocial4.setText(social4Result);
//                            eTsocial5.setText(social5Result);
//
//                        }else{
//                            Utility.showToast(Updateprofile.this, "No profile");
//                        }
//
//
//                    }
//                });
//    }
//
//    private void updateProfile() {
//
//        String username = eTusername.getText().toString();
//        String metier1 = eTmetier1.getText().toString();
//        String metier2 = eTmetier2.getText().toString();
//        String email = eTmail.getText().toString();
//        String bio = eTbio.getText().toString();
//        String social1 = eTsocial1.getText().toString();
//        String social2 = eTsocial2.getText().toString();
//        String social3 = eTsocial3.getText().toString();
//        String social4 = eTsocial4.getText().toString();
//        String social5 = eTsocial5.getText().toString();
//
//        final DocumentReference sDoc = db.collection("user").document();
//        db.runTransaction((new Transaction.Function<Void>() {
//            @Override
//            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
//                //DocumentSnapshot snapshot = transaction.get(sDoc);
//
//                transaction.update(sDoc, "username", username);
//                transaction.update(sDoc,"metier1", metier1);
//                transaction.update(sDoc,"metier2", metier2);
//                transaction.update(sDoc,"mail", email);
//                transaction.update(sDoc,"bio", bio);
//                transaction.update(sDoc,"social1",social1);
//                transaction.update(sDoc,"social2",social2);
//                transaction.update(sDoc,"social3",social3);
//                transaction.update(sDoc,"social4",social4);
//                transaction.update(sDoc,"social5",social5);
//
//
//                return null;
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                       Utility.showToast(Updateprofile.this,"updated");
//
//                    }
//
//        })
//                .addOnFailureListener(new OnFailureListener(){
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Utility.showToast(Updateprofile.this,"failed");
//                   }
//                }));
//
//
    }
}