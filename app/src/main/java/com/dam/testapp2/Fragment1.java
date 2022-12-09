package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Fragment1 extends Fragment {
    ImageView iVlogoutprofile, iVprofilepicture, iVbackgroundpicture, iVchangeprofilepicture, iVchangebackgroundpicture;
    TextView tVprofileusername, tVmetier1, tVmetier2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iVlogoutprofile = view.findViewById(R.id.logoutprofile);
        iVchangeprofilepicture = view.findViewById(R.id.editppp);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        iVchangeprofilepicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Editprofpicture.class));
            }
        }));

        iVlogoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showToast(getContext(), "Logout");
                firebaseAuth.getCurrentUser();
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });

    }
}
