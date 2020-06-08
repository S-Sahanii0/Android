package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Korean extends AppCompatActivity {
    RecyclerView recyclerView;
    KoreanAdapter adapter;


    List<FoodList> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean);

        foodlist = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodlist.add(
                new FoodList(
                        "Rs.380",
                        R.drawable.jajang,
                        "Jajangmyeon"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.kimbap,
                        "Chicken Kimbap"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.150",
                        R.drawable.kimchi,
                        "Samgyeopsal"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.bibimbap,
                        "Bibimbap"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.ricecake,
                        "Spicy ricecake"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.250",
                        R.drawable.pat,
                        "Patbingsu"

                ));


        KoreanAdapter adapter = new KoreanAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }
}
