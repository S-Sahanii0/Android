package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Break extends AppCompatActivity {
    RecyclerView recyclerView;
    BreakfastAdapter adapter;


    List<FoodList> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);

        foodlist = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.waffle,
                        "Fruit Waffle"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.300",
                        R.drawable.sandwitch,
                        "Sandwitch"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.eggs,
                        "Eggs Benedict"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.250",
                        R.drawable.pancake,
                        "Pancake"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.smoothie,
                        "Mix fruit smoothie"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.150",
                        R.drawable.donuts,
                        "Honey glazed donuts"

                ));


        BreakfastAdapter adapter = new BreakfastAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }
}
