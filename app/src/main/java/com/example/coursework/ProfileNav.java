package com.example.coursework;

import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileNav extends Fragment {
    EditText prof_username, prof_email, prof_number, prof_name;
    Button prof_update, prof_change;
    DatabaseReference ref;
    ImageView prof_logout;
    CircleImageView profile;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String currentUserId;
    String username, email, number, name, image;
    FirebaseAuth.AuthStateListener mauthAuthStateListener;
    private Uri filePath;
    FirebaseStorage fire;
    StorageReference mStorage;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    private static final int PICK_GALLERY_IMAGE = 234;


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

        prof_username = view.findViewById(R.id.prof_username);
        prof_name = view.findViewById(R.id.prof_name);
        prof_email = view.findViewById(R.id.prof_email);
        prof_number = view.findViewById(R.id.prof_numb);
        prof_logout = view.findViewById(R.id.prof_logout);
        prof_update = view.findViewById(R.id.prof_update);
        prof_change = view.findViewById(R.id.prof_change);

        profile = view.findViewById(R.id.profile_image);
        fire = FirebaseStorage.getInstance();
        mStorage = fire.getReference();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();

        prof_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        prof_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


        if (user != null) {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        username = dataSnapshot.child("username").getValue().toString();
                        name = dataSnapshot.child("name").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        number = dataSnapshot.child("number").getValue().toString();
                        image = dataSnapshot.child("image").getValue().toString();


                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Unable to extract Data", Toast.LENGTH_SHORT).show();
                    }

                    prof_username.setText(username);
                    prof_name.setText(name);
                    prof_email.setText(email);
                    prof_number.setText(number);
                    Picasso.with(getContext()).load(image).into(profile);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        }


        return view;
    }

    public void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.with(getContext()).load(filePath).into(profile);
        }
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

    public void updateProfile() {
        try {
            final String key = ref.push().getKey();
            mStorage.child("Profile").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorage.child("Profile").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);
                            databaseReference.push().getKey();

                            ref.child("username").setValue(prof_username.getText().toString());
                            ref.child("name").setValue(prof_name.getText().toString());
                            ref.child("email").setValue(prof_email.getText().toString());
                            ref.child("number").setValue(prof_number.getText().toString());
                            ref.child("image").setValue(uriimage);
                            Toast.makeText(getActivity(), "Successfully updated.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_SHORT).show();
        }
    }


}
