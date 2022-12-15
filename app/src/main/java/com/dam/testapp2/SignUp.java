package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {


    EditText eTemail, eTusername, eTpassword, eTrepassword;
    Button btnCreateaccount;
    TextView tVbtnlogin;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseAuth.getInstance();


        eTemail = findViewById(R.id.email);
        eTusername = findViewById(R.id.username);
        eTpassword = findViewById(R.id.password);
        eTrepassword = findViewById(R.id.repassword);
        btnCreateaccount = findViewById(R.id.signupbtn);
        tVbtnlogin = findViewById(R.id.tvloginbtn);
        progressBar = findViewById(R.id.progress_bar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        tVbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });

        btnCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {

        String email = eTemail.getText().toString();
        String password = eTpassword.getText().toString();
        String repassword = eTrepassword.getText().toString();


        boolean isValidated = validateData(email, password, repassword);
        if (!isValidated) {


            return;
        } else {

            createAccountInFirebase(email, password);

        }


    }

    void createAccountInFirebase(String email, String password) {
        changeInprogress(true);

//      UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//              .build();
//
//      firebaseUser.updateProfile(request);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInprogress(false);
                        if (task.isSuccessful()) {
                            Utility.showToast(SignUp.this,"Succesfully create account, check email");
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                            Intent intent = new Intent(SignUp.this, SignIn.class);
                            startActivity(intent);
                        } else {
                            Utility.showToast(SignUp.this, task.getException().getLocalizedMessage());
                        }
                    }
                }
        );

    }

    void changeInprogress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            btnCreateaccount.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.GONE);
            btnCreateaccount.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password, String repassword) {
        // confirmation de saisie


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eTemail.setError("Email is Invalid");
            return false;
        }
        if (password.length()<6){
            eTpassword.setError("Mot de passe trop court");
            return false;
        }
        if(!password.equals(repassword)){
            eTrepassword.setError("Password not matched");
            return false;

        }else {
            return true;
        }


    }







}