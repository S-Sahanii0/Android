package com.example.coursework.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.coursework.Adapter.ChineseAdapter;
import com.example.coursework.Adapter.ComboAdapter;
import com.example.coursework.R;
import com.example.coursework.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chinese extends AppCompatActivity {

    RecyclerView recyclerView;
    ChineseAdapter adapter;
    DatabaseReference ref;
    List<Menu> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese);
        ref = FirebaseDatabase.getInstance().getReference("Chinese");
        foodlist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChineseAdapter adapter = new ChineseAdapter(this, foodlist);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Menu menu = postSnapshot.getValue(Menu.class);
                    foodlist.add(menu);
                }
                ComboAdapter adapter = new ComboAdapter(Chinese.this, foodlist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Chinese.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
