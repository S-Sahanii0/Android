package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Korean extends AppCompatActivity {
    RecyclerView recyclerView;
    KoreanAdapter adapter;
    Button order;
    TextView title, price;

    List<Menu> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean);
        order= findViewById(R.id.orderButton);
        title=findViewById(R.id.textViewTitle);
        price=findViewById(R.id.textViewPrice);

        foodlist = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        KoreanAdapter adapter = new KoreanAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);



    }
}
