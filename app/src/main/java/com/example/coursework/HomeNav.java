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
import com.example.coursework.model.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeNav extends Fragment {
    ImageView combo, korean, breakfast, chinese, logouu;
    SliderView sliderView;
    List<Slider> images;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);

        combo = view.findViewById(R.id.combo_id);
        korean = view.findViewById(R.id.korean_id);
        breakfast = view.findViewById(R.id.breakfast_id);
        chinese = view.findViewById(R.id.chinese_id);
        sliderView = view.findViewById(R.id.home_header_image);
        logouu=view.findViewById(R.id.logout_id);

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

       /* logouu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
*/

        images = new ArrayList<Slider>();
        sliderView = view.findViewById(R.id.home_header_image);
        images.add(new Slider(R.drawable.piz));
        images.add(new Slider(R.drawable.home));
        images.add(new Slider(R.drawable.chick));

        sliderView.setSliderAdapter(new ImageSlider(getActivity(), images));

        return view;

    }
}
