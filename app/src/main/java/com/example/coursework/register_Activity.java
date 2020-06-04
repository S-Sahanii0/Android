package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class register_Activity extends AppCompatActivity {


    EditText Username, Email, Password, Number, Confirm_pass;
    TextView Log;
    Button Register;
    ImageView Image;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.editText);
        Email = findViewById(R.id.editText3);
        Password = findViewById(R.id.editText2);
        Number = findViewById(R.id.editText4);
        Register = findViewById(R.id.button3);
        Log = findViewById(R.id.textView2);
        Confirm_pass = findViewById(R.id.confirm_pass);
        mAuth = FirebaseAuth.getInstance();

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_Activity.this, LoginActivity.class));
            }
        });
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


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
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

        }else if (password != confirm_pass){
            Toast.makeText(register_Activity.this, "Password do not match", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, execute if condition
                                Toast.makeText(register_Activity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register_Activity.this, NavBar_Food.class));
                                User user = new User(username, email, number);
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
                                    }
                                });

                            }
                        }
                    });
        }
    }


}




