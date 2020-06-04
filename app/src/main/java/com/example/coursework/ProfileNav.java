package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileNav extends AppCompatActivity {
    TextView prof_username, prof_email, prof_number;
    Button prof_logout;
    DatabaseReference profileUser;
    FirebaseAuth mAuth;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUser = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        prof_username =findViewById(R.id.prof_username);
        //prof_name = findViewById(R.id.prof_name);
        prof_email = findViewById(R.id.prof_email);
        prof_number = findViewById(R.id.prof_numb);
        prof_logout= findViewById(R.id.prof_logout);

        profileUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String prof_username = dataSnapshot.child("username").getValue().toString();
                    //String prof_name = dataSnapshot.child("prof_name").getValue().toString();
                    String prof_email = dataSnapshot.child("email").getValue().toString();
                    String prof_numb = dataSnapshot.child("number").getValue().toString();


                    ((TextView)findViewById(R.id.prof_username)).setText("@"+prof_username);
                    ((TextView)findViewById(R.id.prof_email)).setText(prof_email);
                    ((TextView)findViewById(R.id.prof_numb)).setText("Phone Number"+prof_numb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
    }
}
