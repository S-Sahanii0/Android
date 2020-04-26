package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.awt.font.NumericShaper;

public class register_Activity extends AppCompatActivity {


    EditText Username, Email, Password, Number;
    TextView Log;
    Button Register;
    ImageView Image;

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

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_Activity.this, LoginActivity.class));
            }
        });
    }
}
