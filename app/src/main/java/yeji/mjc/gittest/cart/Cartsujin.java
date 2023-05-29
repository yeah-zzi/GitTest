package yeji.mjc.gittest.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.cart.FoodAdapter;
import yeji.mjc.gittest.cart.FoodItem;

public class Cartsujin extends Fragment {

    public RecyclerView recyclerView;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    ImageButton MartSearch;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_main, container, false);
        recyclerView = view.findViewById(R.id.FoodListRV);
        //recyclerView.setHasFixedSize(true);

        MartSearch = view.findViewById(R.id.MartSearch); // 마트 찾기 버튼

        MartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Cart_Map.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void onStart(){
        super.onStart();

        for(int i=0;i<9;i++){
            foodItems.add(new FoodItem("오징어", "3개",R.drawable.bread,true));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new FoodAdapter(foodItems));
    }
}