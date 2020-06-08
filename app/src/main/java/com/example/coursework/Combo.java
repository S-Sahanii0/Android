package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Combo extends AppCompatActivity {

    RecyclerView recyclerView;
    ComboAdapter adapter;


    List<FoodList> foodlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);


        foodlist = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.friecoke,
                        "Fries,Burger,Coke"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.500",
                        R.drawable.naan,
                        "Naan,Butter Chicken, Butter paneer"

                ));

        foodlist.add(
                new FoodList(
                        "Rs.400",
                        R.drawable.comm,
                        "Chicken,Pork,Buff"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.fish,
                        "Fish and chips"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.350",
                        R.drawable.biscuit,
                        "Chicken and Biscuit"

                ));
        foodlist.add(
                new FoodList(
                        "Rs.450",
                        R.drawable.dessert,
                        "Special Dessert"

                ));



        ComboAdapter adapter = new ComboAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }
    }

