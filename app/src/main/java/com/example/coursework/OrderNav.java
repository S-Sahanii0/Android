package com.example.coursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderNav extends Fragment {
    RecyclerView recyclerView;
    FoodAdapter adapter;

    List<FoodList> foodlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_frag, container, false);

        foodlist = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                        "300",
                        R.drawable.pizza,
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
                        "300",
                        R.drawable.pizza,
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
                        "300",
                        R.drawable.pizza,
                        "Burger"

                ));
        foodlist.add(
                new FoodList(
                        "300",
                        R.drawable.pizza,
                        "Burger"

                ));

        FoodAdapter adapter = new FoodAdapter(getContext(), foodlist);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        return view;
    }
}
