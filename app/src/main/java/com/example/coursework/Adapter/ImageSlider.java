package com.example.coursework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.coursework.R;
import com.example.coursework.model.Slider;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSlider extends SliderViewAdapter<ImageSlider.SliderViewHolder> {

    Context context;
    List<Slider> images;

    public ImageSlider(Context context, List<Slider> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider,parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        viewHolder.sliderImage.setImageResource(images.get(position).getImage());
    }

    @Override
    public int getCount() {
        return images.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView sliderImage;
        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.slider_image);

        }
    }
}
