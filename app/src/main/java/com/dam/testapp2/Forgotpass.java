package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpass extends AppCompatActivity {

    EditText eTsendmail;
    Button btnSendmail;
    TextView tVsignup;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        eTsendmail = findViewById(R.id.forgetpassmail);
        btnSendmail = findViewById(R.id.sendmail);
        tVsignup = findViewById(R.id.tvsignup);

        tVsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Forgotpass.this, SignUp.class));
            }
        });



        btnSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eTsendmail.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Utility.showToast(Forgotpass.this, "Please enter your registered mail");
                    eTsendmail.setError("Email is required");
                    eTsendmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Utility.showToast(Forgotpass.this, "Please enter valid mail");
                    eTsendmail.setError("Valid mail is required");
                    eTsendmail.requestFocus();
                } else {
                    resetPassword(email);
                }
            }
        });


    }



    private void resetPassword(String email) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Utility.showToast(Forgotpass.this, "PLease check your mail box for reset link");

                        Intent intent = new Intent(Forgotpass.this, SignIn.class);

                        //Pour eviter un retour sur la mm page en utilisant le bouton retour
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Utility.showToast(Forgotpass.this,"Something wen wrong");
                    }
                }
            });
        }
    }