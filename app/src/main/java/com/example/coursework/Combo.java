package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.Adapter.ComboAdapter;
import com.example.coursework.model.Combo_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Combo extends AppCompatActivity {

    RecyclerView recyclerView;
    ComboAdapter adapter;
    DatabaseReference ref;
    TextView title, price;
    List<Combo_model> Combolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);
        ref= FirebaseDatabase.getInstance().getReference("Combo");
        Combolist = new ArrayList<>();
        title = findViewById(R.id.title_id);
        price = findViewById(R.id.price_id);
        Combolist = new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Combo_model comboModel = postSnapshot.getValue(Combo_model.class);
                    Combolist.add(comboModel);
                }
                ComboAdapter adapter = new ComboAdapter(Combo.this, Combolist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Combo.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
    }

