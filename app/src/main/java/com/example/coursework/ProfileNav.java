package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileNav extends Fragment {
    TextView prof_username, prof_email, prof_number, prof_name;
    Button prof_logout;
    DatabaseReference ref;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String currentUserId;
    String username, email, number, name;
    FirebaseAuth.AuthStateListener mauthAuthStateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_frag, container, false);
        setupFirebaseListener();

        prof_logout = view.findViewById(R.id.prof_logout);
        //updateButton = view.findViewById(R.id.profile_update_button);
        prof_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        prof_username =view.findViewById(R.id.prof_username);
        prof_name = view.findViewById(R.id.prof_name);
        prof_email = view.findViewById(R.id.prof_email);
        prof_number = view.findViewById(R.id.prof_numb);
        prof_logout= view.findViewById(R.id.prof_logout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        if (user != null) {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        username = dataSnapshot.child("username").getValue().toString();
                        name= dataSnapshot.child("name").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        number = dataSnapshot.child("number").getValue().toString();

                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Unable to extract Data", Toast.LENGTH_SHORT).show();
                    }

                    prof_username.setText(username);
                    prof_name.setText(name);
                    prof_email.setText(email);
                    prof_number.setText(number);
                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
            return view;
    }

    private void setupFirebaseListener() {
        mauthAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mauthAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mauthAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mauthAuthStateListener);
        }
    }
}
