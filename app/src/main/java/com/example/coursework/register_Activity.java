package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.awt.font.NumericShaper;

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

        Username= findViewById(R.id.editText);
        Email= findViewById(R.id.editText3);
        Password=findViewById(R.id.editText2);
        Number= findViewById(R.id.editText4);
        Register= findViewById(R.id.button3);
        Log= findViewById(R.id.textView2);
        Confirm_pass= findViewById(R.id.confirm_pass);
        mAuth = FirebaseAuth.getInstance();

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_Activity.this, LoginActivity.class));
            }
        });
    }

    public void signUp(View view) {
        //function to be called when clicked the signup button
        String email= Email.getText().toString().trim(); //trim removes white space
        String password= Password.getText().toString().trim();
        String confirm_pass= Confirm_pass.getText().toString().trim();
        String username=Username .getText().toString().trim();
        String number=Number.getText().toString().trim();



        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Username field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Number field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(confirm_pass)){
            Toast.makeText(this, "Confirm password field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.length() <6){
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        }
        else if (password.equals(confirm_pass)){
            createAccount(email,password);
        }
        else {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String email, String password) {
        //firebase signup code
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, execute if condition
                            startActivity(new Intent(register_Activity.this, LoginActivity.class));
                            Toast.makeText(register_Activity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(register_Activity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
}
