package com.dam.testapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.CDATASection;

public class FragmentProfil extends Fragment implements View.OnClickListener {
    ImageView iVlogoutprofile, iVprofilepicture, iVbackgroundpicture, iVchangeprofilepicture, iVchangebackgroundpicture, iVupdateprofile;
    TextView tVprofileusername, tVmail, tVmetier1, tVmetier2, tVsocial1, tVsocial2, tVsocial3, tVsocial4, tVsocial5;
    EditText eTbio;

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
        iVchangebackgroundpicture = view.findViewById(R.id.editbp);
        iVupdateprofile = view.findViewById(R.id.updateprofile);
        tVsocial1 = view.findViewById(R.id.social1);
        tVsocial2 = view.findViewById(R.id.social2);
        tVsocial3 = view.findViewById(R.id.social3);
        tVsocial4 = view.findViewById(R.id.social4);
        tVsocial5 = view.findViewById(R.id.social5);
        tVprofileusername = view.findViewById(R.id.username);
        tVmetier1 = view.findViewById(R.id.metier1);
        tVmetier2 = view.findViewById(R.id.metier2);
        tVmail = view.findViewById(R.id.pmail);
        iVbackgroundpicture = view.findViewById(R.id.bp);
        iVprofilepicture = view.findViewById(R.id.ppincv);
        eTbio = view.findViewById(R.id.bio);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Users");

        Query query = databaseReference.orderByChild("email").equalTo((user.getEmail()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String username = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String social1 = "" + ds.child("social1").getValue();
                    String image = "" + ds.child("image").getValue();

                    tVprofileusername.setText(username);
                    tVmail.setText(email);
                    tVsocial1.setText(social1);
                    try {
                        Glide.with(getContext()).load(image).into(iVprofilepicture);

                    } catch (Exception e) {

                        Glide.with(getActivity()).load(R.drawable.ic_baseline_add_a_photo_24).into(iVprofilepicture);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        iVchangebackgroundpicture.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Updateprofile.class));
            }
        }));

        iVupdateprofile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Createprofile.class));

            }
        }));

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

    @Override
    public void onClick(View view) {

    }

}
