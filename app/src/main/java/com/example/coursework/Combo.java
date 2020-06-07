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
                        "300",
                        R.drawable.burger,
                        "Burger"

                ));

        foodlist.add(
                new FoodList(
                        "300",
                        R.drawable.momo,
                        "Burger"

                ));

        foodlist.add(
                new FoodList(
                        "300",
                        R.drawable.pizza,
                        "Burger"

                ));
        foodlist.add(
                new FoodList(
                        "2330",
                        R.drawable.pizza,
                        "Sausage"

                ));



        ComboAdapter adapter = new ComboAdapter(this, foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }
    }
