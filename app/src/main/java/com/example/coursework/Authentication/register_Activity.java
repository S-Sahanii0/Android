package com.example.coursework.Authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursework.NavBar_Food;
import com.example.coursework.R;
import com.example.coursework.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class register_Activity extends AppCompatActivity {


    EditText Username, Fname, Email, Password, Number, Confirm_pass;
    TextView Log;
    Button Register;
    private Uri filePath;
    FirebaseStorage fire;
    CircleImageView Image;
    private FirebaseAuth mAuth;
    StorageReference mStorage;
    FirebaseDatabase db;
    DatabaseReference ref;
    private static final int PICK_GALLERY_IMAGE = 234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.editText);
        Fname = findViewById(R.id.sign_name);
        Email = findViewById(R.id.editText3);
        Password = findViewById(R.id.editText2);
        Number = findViewById(R.id.editText4);
        Register = findViewById(R.id.button3);
        Log = findViewById(R.id.textView2);
        Confirm_pass = findViewById(R.id.confirm_pass);
        Image = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();
        fire = FirebaseStorage.getInstance();
        mStorage = fire.getReference();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_Activity.this, LoginActivity.class));
            }
        });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });

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
            Picasso.with(this).load(filePath).into(Image);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handles the already logged in user
        }
    }

    public void signUp(View view) {
        //function to be called when clicked the signup button
        final String email = Email.getText().toString().trim(); //trim removes white space
        String password = Password.getText().toString().trim();
        String confirm_pass = Confirm_pass.getText().toString().trim();
        final String username = Username.getText().toString().trim();
        final String number = Number.getText().toString().trim();
        final String name = Fname.getText().toString().trim();
        final String key = ref.push().getKey();


        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(name) && TextUtils.isEmpty(username) && TextUtils.isEmpty(number) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirm_pass)){
            Toast.makeText(this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Username field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Number field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(confirm_pass)) {
            Toast.makeText(this, "Confirm password field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(confirm_pass)){
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mStorage.child("Profile").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        mStorage.child("Profile").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uriimage = String.valueOf(uri);
                                                ref.push().getKey();
                                                User user = new User(username, email, number, name, uriimage);
                                                FirebaseDatabase.getInstance().getReference("users")
                                                        .child(FirebaseAuth.getInstance().getUid())
                                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(register_Activity.this, "User Added!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(register_Activity.this, "Could not add user", Toast.LENGTH_SHORT).show();

                                                        }
                                                        Toast.makeText(register_Activity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(register_Activity.this, NavBar_Food.class));
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });
                                // Sign in success, execute if condition


                            }
                        }
                    });
        }
    }


}




