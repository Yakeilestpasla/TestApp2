package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    //Button button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new FragmentProfil()).commit();







        //button2.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
          //      startActivity(new Intent(MainActivity.this, SignIn.class));
            //    firebaseAuth.getCurrentUser();
              //  firebaseAuth.signOut();
                //finish();
            //}
        //});

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selected = null;
            switch (item.getItemId()){
                case R.id.bottombarmenu:
                    selected = new FragmentProfil();
                    break;

                case R.id.avoir1:
                    selected = new Fragment2();
                    break;

                case R.id.avoir2:
                    selected = new Fragment3();
                    break;

                case R.id.avoir3:
                    selected = new Fragment4();
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selected).commit();
            return true;
        }
    };
}

