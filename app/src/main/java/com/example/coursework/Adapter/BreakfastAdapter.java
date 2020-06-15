package com.example.coursework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.R;
import com.example.coursework.model.Menu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Menu> foodList;
    DatabaseReference databaseReference;
    FirebaseDatabase db;

    public BreakfastAdapter(Context mCtx, List<Menu> foodList) {
        this.mCtx = mCtx;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.breakfast, null);
        ProductViewHolder holder = new ProductViewHolder(view);
        db= FirebaseDatabase.getInstance();
        databaseReference=db.getReference();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    //Bind data
        final Menu foodlist = foodList.get(position);
        holder.textViewTitle.setText(foodlist.getTitle());
        holder.textViewPrice.setText(String.valueOf(foodlist.getPrice()));
        Picasso.with(mCtx).load(foodlist.getImage()).placeholder(R.drawable.burger).fit().centerCrop().into(holder.imageView);
        /*holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Breakfast").child(foodlist.getKey()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mCtx, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return foodList.size(); //size of the list
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView textViewTitle, textViewPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            textViewTitle=itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
