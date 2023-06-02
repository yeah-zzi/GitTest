package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Cold_Fridge extends Fragment{

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_cold, container, false);

        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);

        fridge_main = view.findViewById(R.id.fridge_main);
        fridge_cold = view.findViewById(R.id.fridge_cold);
        fridge_frozen = view.findViewById(R.id.fridge_frozen);

        return view;
    }



    public void onStart(){
        super.onStart();

        fridgeItems.add(new Fridge_Item(R.drawable.potato,"감자","5개","D-16"));
        fridgeItems.add(new Fridge_Item(R.drawable.gazi,"가지","1개","D-7"));
        fridgeItems.add(new Fridge_Item(R.drawable.godung,"고등어","2개","D-2"));
        fridgeItems.add(new Fridge_Item(R.drawable.corn,"옥수수","1개","D-21"));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(new Fridge_Adapter(fridgeItems));
    }
}