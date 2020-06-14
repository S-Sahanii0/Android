package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {

    EditText pass, newPass, rePass;
    private static final String TAG = "ChangePassword";
    Button change;
    FirebaseUser user;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        pass = findViewById(R.id.old_pass);
        newPass = findViewById(R.id.new_pass);
        rePass = findViewById(R.id.new_confirm);
        change = findViewById(R.id.change_btn);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        auth = FirebaseAuth.getInstance();
    }

    public void changePassword(View view) {
        String oldPassword = pass.getText().toString().trim();
        final String newPassword = newPass.getText().toString().trim();
        String confirmPass = rePass.getText().toString().trim();

        if (oldPassword.isEmpty() && newPassword.isEmpty() && confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            if (newPassword.equals(confirmPass)) {
                if (user != null) {
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), oldPassword);

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Password changed successfully.");
                                                            auth.signOut();
                                                            finish();
                                                        } else {
                                                            Toast.makeText(ChangePassword.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(ChangePassword.this, "Re-authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            } else {
                Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



