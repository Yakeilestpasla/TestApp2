




package com.dam.testapp2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    EditText eTlogin, eTloginpassword;
    Button loginBtn;
    CheckBox cBshowpassword;
    ProgressBar progressBar;
    TextView tVsignup, tVforgotpassword;
    SignInButton mGoogleLoginbtn;






    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        eTlogin = findViewById(R.id.loginmail);
        eTloginpassword = findViewById(R.id.loginpassword);
        cBshowpassword = findViewById(R.id.showpassword);
        progressBar = findViewById(R.id.login_progressbar);
        tVsignup = findViewById(R.id.tvsignup);
        tVforgotpassword = findViewById(R.id.forgotpass);
        loginBtn = findViewById(R.id.loginbtn);
        mGoogleLoginbtn = findViewById(R.id.btngoogle);

        FirebaseAuth.getInstance();

        mGoogleLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });




        tVforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showToast(SignIn.this,"Reset your password");
                startActivity(new Intent(SignIn.this, Forgotpass.class));
            }
        });


        cBshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    eTloginpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    eTloginpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


        tVsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Utility.showToast(this,"Sign in Cancel");
        }
    }

    private void gotoProfile() {
        Intent intent=new Intent(SignIn.this, Profile.class);
        startActivity(intent);
    }


    void LoginUser() {
        String email = eTlogin.getText().toString();
        String password = eTloginpassword.getText().toString();

        boolean isValidated = validateData(email, password);
        if (!isValidated) {
            return;
        }

        loginAccountInFireBase(email,password);

    }

    void loginAccountInFireBase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                        startActivity(new Intent(SignIn.this, Profile.class));
                    }
                    else {
                        Utility.showToast(SignIn.this, "Email not verified");
                    }
                }
                else{
                    Utility.showToast(SignIn.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }




    void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }


    boolean validateData(String email, String password) {
        // confirmation de saisie


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eTlogin.setError("Email is Invalid");
            return false;
        }
        if (password.length() < 6) {
            eTloginpassword.setError("Mot de passe trop court");
            return false;

        } else {            return true;
        }

    }
}