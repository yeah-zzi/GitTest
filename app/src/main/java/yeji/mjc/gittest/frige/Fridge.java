package yeji.mjc.gittest.frige;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.Product;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.frige.Data_Item;
import yeji.mjc.gittest.frige.Fridge_Item;
import yeji.mjc.gittest.cart.CartAdapter;
import yeji.mjc.gittest.cart.CartItem;
import yeji.mjc.gittest.cart.FoodAdapter;
import yeji.mjc.gittest.cart.FoodItem;
import yeji.mjc.gittest.comunity.Fight_fragment;
import yeji.mjc.gittest.comunity.Life_Fragment;
import yeji.mjc.gittest.comunity.NewFight_fragment;
import yeji.mjc.gittest.comunity.Tip_fragment;
import yeji.mjc.gittest.mypage.Bellset;
import yeji.mjc.gittest.mypage.UserInfoChange;

public class Fridge extends Fragment{

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();

    public FoodAdapter foodAdapter;
    public CartAdapter cartAdapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference fridgedb;
    ArrayList<FoodItem> items;
    String userid;

    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);
        recyclerView = view.findViewById(R.id.fridgeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter_refidge = new Fridge_Adapter(fridgeItems);
        recyclerView.setAdapter(foodAdapter);

        recyclerView.setHasFixedSize(true);



        Button fridge_main = view.findViewById(R.id.fridge_main);
        Button fridge_cold = view.findViewById(R.id.fridge_cold);
        Button fridge_frozen = view.findViewById(R.id.fridge_frozen);

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

        /*
        // 로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        // 파이어베이스에 저장되어 있는 냉장고
        fridgedb = database.getReference().child("user").child(userid).child("fridge");
        fridgedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                fridgeItems.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Fridge_Item fridge_item = snapshot1.getValue(Fridge_Item.class);
                    if (fridge_item != null) {
                        // Data_Item의 값이 null이 아닌 경우에만 Fridge_Item 객체를 생성
                        Fridge_Item fridgeItem = new Fridge_Item(fridge_item.getFood_img(), fridge_item.getFood_name(), fridge_item.getFood_count() + "개", "D-" + fridge_item.getFood_date());
                        fridgeItems.add(fridgeItem);
                    }
                    else
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오다 오류 발생시
            }
        });
*/

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

        /*
        if (fridgeItems.isEmpty()) {
            // fridgeItems가 비어있으면 null_fridge.xml을 inflate하여 사용
            View nullView = inflater.inflate(R.layout.null_fridge_main, container, false);
            return nullView;
        } else {
            // fridgeItems가 비어있지 않으면 아이템을 RecyclerView에 표시
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            adapter_refidge = new Fridge_Adapter(fridgeItems);
            recyclerView.setAdapter(adapter_refidge);
            recyclerView.setHasFixedSize(true);

        }
        */


        return view;


    }

    public void onStart() {
        super.onStart();

        fridgeItems.add(new Fridge_Item(R.drawable.potato,"감자","5개","D-16",22));
        fridgeItems.add(new Fridge_Item(R.drawable.fdsaf,"베이컨","2개","D-20",76));
        fridgeItems.add(new Fridge_Item(R.drawable.chilli,"고추","10개","D-5",35));
        fridgeItems.add(new Fridge_Item(R.drawable.carrot,"당근","6개","D-30",56));
        fridgeItems.add(new Fridge_Item(R.drawable.gazi,"가지","1개","D-7", 24));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter_refidge = new Fridge_Adapter(fridgeItems);
        recyclerView.setAdapter(adapter_refidge);
    }


}