package com.example.coursework;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Adapter.FoodAdapter;
import com.example.coursework.model.Menu;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MenuNav extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    Button delete;
    ImageView image;
    TextView title, price;
    FirebaseUser user;
    DatabaseReference ref, refrence;
    List<Menu> foodlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_frag, container, false);
        ref = FirebaseDatabase.getInstance().getReference("Food");
        refrence = FirebaseDatabase.getInstance().getReference();
        searchView = view.findViewById(R.id.combo_search_id);
        foodlist = new ArrayList<>();
        title = view.findViewById(R.id.title_id);
        price = view.findViewById(R.id.price_id);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Menu menu = postSnapshot.getValue(Menu.class);
                    foodlist.add(menu);
                }
                adapter = new FoodAdapter(getContext(), foodlist);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFood(newText);
                return true;
            }
            private void searchFood(String a) {
                ArrayList<Menu> food = new ArrayList<>();
                for (Menu obj: foodlist){
                    if (obj.getTitle().toUpperCase().contains(a.toUpperCase())){
                        food.add(obj);
                    }
                }
                FoodAdapter filtered = new FoodAdapter(getActivity(), food);
                recyclerView.setAdapter(filtered);

            }
        });


        return view;
    }



    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteItem(viewHolder);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private Menu deletedItem = null;
    private void deleteItem(RecyclerView.ViewHolder viewHolder){
        final int position = viewHolder.getAdapterPosition();

        deletedItem = foodlist.get(position);
        foodlist.remove(position);
        adapter.notifyItemRemoved(position);


        Snackbar.make(recyclerView, deletedItem.getTitle(), Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        foodlist.add(position, deletedItem);
                        adapter.notifyItemInserted(position);
                    }
                }).show();
    }

}
