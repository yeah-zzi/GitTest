package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Frigesujin extends Fragment {

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);
        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void onStart(){
        super.onStart();

        items.add(new Item(R.drawable.potato,"감자","5개","D-16"));
        items.add(new Item(R.drawable.fdsaf,"베이컨","2개","D-20"));
        items.add(new Item(R.drawable.chilli,"고추","10개","D-5"));
        items.add(new Item(R.drawable.carrot,"당근","6개","D-30"));
        items.add(new Item(R.drawable.gazi,"가지","1개","D-7"));
        items.add(new Item(R.drawable.godung,"고등어","2개","D-2"));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(new FridgeAdapter(items));
    }
}