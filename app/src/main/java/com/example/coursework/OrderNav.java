package com.example.coursework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OrderNav extends Fragment {
    RecyclerView recyclerView;
    FoodAdapter adapter;
    Button order;
    ImageView image;
    TextView title, price;
    FirebaseUser user;
    DatabaseReference ref;
    StorageReference storageRef;
    FirebaseAuth auth;
    List<Menu> foodlist;
    ArrayList<String> pathArray;
    ProgressDialog mProgressDialog;
    int array_position;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_frag, container, false);
        ref= FirebaseDatabase.getInstance().getReference("cart");
        foodlist = new ArrayList<>();
        order = view.findViewById(R.id.orderButton);
        title = view.findViewById(R.id.textViewTitle);
        price = view.findViewById(R.id.textViewPrice);
        pathArray = new ArrayList<>();
        auth= FirebaseAuth.getInstance();
        storageRef= FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FoodAdapter adapter = new FoodAdapter(getContext(), foodlist);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

            return view;
        }
    }

