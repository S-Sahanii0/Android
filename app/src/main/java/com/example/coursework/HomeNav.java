package com.example.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coursework.Adapter.ImageSlider;
import com.example.coursework.Authentication.LoginActivity;
import com.example.coursework.Categories.Break;
import com.example.coursework.Categories.Chinese;
import com.example.coursework.Categories.Combo;
import com.example.coursework.Categories.Korean;
import com.example.coursework.model.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeNav extends Fragment {
    ImageView combo, korean, breakfast, chinese, logouu;
    SliderView sliderView;
    List<Slider> images;
    FirebaseAuth.AuthStateListener mauthAuthStateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);
        setupFirebaseListener();
        combo = view.findViewById(R.id.combo_id);
        korean = view.findViewById(R.id.korean_id);
        breakfast = view.findViewById(R.id.breakfast_id);
        chinese = view.findViewById(R.id.chinese_id);
        sliderView = view.findViewById(R.id.home_header_image);
        logouu=view.findViewById(R.id.logout_id);


        logouu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),  LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        combo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Combo.class));
            }
        });

        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Korean.class));
            }
        });

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Break.class));
            }
        });

        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Chinese.class));
            }
        });


        images = new ArrayList<Slider>();
        sliderView = view.findViewById(R.id.home_header_image);
        images.add(new Slider(R.drawable.piz));
        images.add(new Slider(R.drawable.home));
        images.add(new Slider(R.drawable.chick));

        sliderView.setSliderAdapter(new ImageSlider(getActivity(), images));
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(2);

        return view;

    }

    private void setupFirebaseListener() {
        mauthAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(),  LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        };
    }
}
