package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Fridge extends Fragment implements View.OnClickListener{

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //화면 이동을 위한 프레그먼트 변수 선언
    private final int Fragment_1 = 1;
    private final int Fragment_2 = 2;
    private final int Fragment_3 = 3;


    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);

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

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(new Fridge_Adapter(fridgeItems));
    }
}