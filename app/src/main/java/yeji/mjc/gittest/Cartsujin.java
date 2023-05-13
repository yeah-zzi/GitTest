package yeji.mjc.gittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.cart.FoodAdapter;
import yeji.mjc.gittest.cart.FoodItem;

public class Cartsujin extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_cart;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartsujin, container, false);
        recyclerView = view.findViewById(R.id.FoodListRV);
        recyclerView.setHasFixedSize(true);
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