package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Frozen_Fridge extends Fragment{

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> frozenFridgeItems = new ArrayList<Fridge_Item>();



    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);

        adapter_refidge = new Fridge_Adapter(frozenFridgeItems);

        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);

        View fridge_main = view.findViewById(R.id.fridge_main);
        View fridge_cold = view.findViewById(R.id.fridge_cold);
        View fridge_frozen = view.findViewById(R.id.fridge_frozen);

        fridge_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Fridge()).commit();
                return;
            }
        });

        fridge_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Cold_Fridge()).commit();
                return;
            }
        });

        fridge_frozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Frozen_Fridge()).commit();
                return;
            }
        });


        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //추가순
                        ((Fridge_Adapter) adapter_refidge).
                                setSortType(Fridge_Adapter.SortType.ADD_ASCENDING);
                        break;
                    case 1: //이름순
                        ((Fridge_Adapter) adapter_refidge).
                                setSortType(Fridge_Adapter.SortType.NAME_ASCENDING);
                        break;
                    case 2: //유통기한순
                        ((Fridge_Adapter) adapter_refidge).
                                setSortType(Fridge_Adapter.SortType.DATE_ASCENDING);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });

        return view;

    }


    public void onStart(){
        super.onStart();

        frozenFridgeItems.add(new Fridge_Item(R.drawable.carrot,"당근","6개","D-31",50));
        frozenFridgeItems.add(new Fridge_Item(R.drawable.gazi,"가지","1개","D-6", 50));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        adapter_refidge = new Fridge_Adapter(frozenFridgeItems);
        recyclerView.setAdapter(adapter_refidge);
    }

    public ArrayList<Fridge_Item> getFrozenFridgeItems() {
        ArrayList<Fridge_Item> frozenItems = new ArrayList<>();
        return frozenItems;
    }

}