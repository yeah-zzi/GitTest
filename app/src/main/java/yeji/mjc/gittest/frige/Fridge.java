package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.comunity.Fight_fragment;
import yeji.mjc.gittest.comunity.Life_Fragment;
import yeji.mjc.gittest.comunity.NewFight_fragment;
import yeji.mjc.gittest.comunity.Tip_fragment;
import yeji.mjc.gittest.mypage.Bellset;
import yeji.mjc.gittest.mypage.UserInfoChange;

public class Fridge extends Fragment{

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();


    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);

        adapter_refidge = new Fridge_Adapter(fridgeItems);

        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);

        View fridge_main = view.findViewById(R.id.fridge_main);
        View fridge_cold = view.findViewById(R.id.fridge_cold);
        View fridge_frozen = view.findViewById(R.id.fridge_frozen);

        View select_all = view.findViewById(R.id.select_all);
        View select_cold = view.findViewById(R.id.select_cold);
        View select_frozen = view.findViewById(R.id.select_frozen);

        fridge_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Fridge()).commit();
                select_all.setVisibility(View.VISIBLE);
                select_cold.setVisibility(View.GONE);
                select_frozen.setVisibility(View.GONE);
                return;
            }
        });

        fridge_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Cold_Fridge()).commit();
                select_all.setVisibility(View.GONE);
                select_cold.setVisibility(View.VISIBLE);
                select_frozen.setVisibility(View.GONE);
                return;
            }
        });

        fridge_frozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fridge_main_container, new Frozen_Fridge()).commit();
                select_all.setVisibility(View.GONE);
                select_cold.setVisibility(View.GONE);
                select_frozen.setVisibility(View.VISIBLE);
                return;
            }
        });

        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 정렬 기준에 따라 Fridge_Adapter의 setSortType 메서드를 호출
                switch (position) {
                    case 1: // 이름 오름차순
                        ((Fridge_Adapter) adapter_refidge).setSortType(Fridge_Adapter.SortType.NAME_ASCENDING);
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

        fridgeItems.add(new Fridge_Item(R.drawable.potato,"감자","5개","D-16"));
        fridgeItems.add(new Fridge_Item(R.drawable.fdsaf,"베이컨","2개","D-20"));
        fridgeItems.add(new Fridge_Item(R.drawable.chilli,"고추","10개","D-5"));
        fridgeItems.add(new Fridge_Item(R.drawable.carrot,"당근","6개","D-30"));
        fridgeItems.add(new Fridge_Item(R.drawable.gazi,"가지","1개","D-7"));
        fridgeItems.add(new Fridge_Item(R.drawable.godung,"고등어","2개","D-2"));
        fridgeItems.add(new Fridge_Item(R.drawable.corn,"옥수수","1개","D-21"));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        adapter_refidge = new Fridge_Adapter(fridgeItems); // 수정: adapter_refidge 초기화
        recyclerView.setAdapter(adapter_refidge);
    }
}