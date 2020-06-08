package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Chinese extends AppCompatActivity {

    RecyclerView recyclerView;
    ChineseAdapter adapter;


    List<FoodList> foodlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese);

        foodlist = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.dimsums,
                        "Dim Sums"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.500",
                        R.drawable.chillichicken,
                        "Szechwan Chilli Chicken"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.300",
                        R.drawable.soup,
                        "Hot and Sour Soup"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.250",
                        R.drawable.springrolls,
                        "Spring Rolls"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.noodle,
                        "Chicken Noodle"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.glassnoodle,
                        "Shrimp glass noodle"

                ));



        ChineseAdapter adapter = new ChineseAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }
}
