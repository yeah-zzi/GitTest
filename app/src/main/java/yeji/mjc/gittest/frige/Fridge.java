package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Fridge extends Fragment implements View.OnClickListener{

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //FragmentActivity1 frameLayout1;
    //FragmentActivity2 frameLayout2;

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);
/*
        btn_goBack = view.findViewById(R.id.btn_goBack);
        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                //main_layout에 homeFragment로 transaction 한다.
                transaction.replace(R.id.main_layout, homeFragment);
                //꼭 commit을 해줘야 바뀐다.
                transaction.commit();
            }
        });*/


        /*
        fridge_cold = view.findViewById(R.id.fridge_cold);
        fridge_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                //main_layout에 homeFragment로 transaction 한다.
                transaction.replace(R.id.main_layout, homeFragment);
                //꼭 commit을 해줘야 바뀐다.
                transaction.commit();
            }
        });

        */

        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);

        fridge_main = view.findViewById(R.id.fridge_main);
        fridge_cold = view.findViewById(R.id.fridge_cold);
        fridge_frozen = view.findViewById(R.id.fridge_frozen);

        //ClickListener를 상속 받아 사용하기 위함
        fridge_main.setOnClickListener(this);
        fridge_cold.setOnClickListener(this);
        fridge_frozen.setOnClickListener(this);

        return view;
    }

    //메뉴바 버튼에 따른 페이지 이동
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //종합 버튼
            case R.id.fridge_main:
                break;
            //냉장 버튼
            case R.id.fridge_cold:
                //냉장 식재료 관련 로직
                Toast.makeText(getContext(),"냉장",Toast.LENGTH_SHORT).show();
                break;
            //냉동 버튼
            case R.id.fridge_frozen:
                //냉동 식재료 관련 로직
                Toast.makeText(getContext(),"냉동",Toast.LENGTH_SHORT).show();
                break;
        }
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
        recyclerView.setAdapter(new Fridge_Adapter(fridgeItems));
    }
}