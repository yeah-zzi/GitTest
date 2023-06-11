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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.FoodSearch.Product;
import yeji.mjc.gittest.MainActivity;
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

public class Fridge extends Fragment {

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();

    public FoodAdapter foodAdapter;


    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference fridgedb;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid, startDate, changeDate, fridge_type = "";


    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

    public void onStart() {
        super.onStart();

        userid = UserData.getInstance().getUserid();//

        fridgedb = database.getReference().child("user").child(userid).child("fridge");
        fridgedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fridgeItems.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String foodName = snapshot1.getKey();
                    String foodCount = snapshot1.child("food_count").getValue(String.class);
                    String foodImgPath = snapshot1.child("food_img").getValue(String.class);
                    String foodDate = snapshot1.child("food_date").getValue(String.class);
                    String fridgeType = snapshot1.child("fridge_type").getValue(String.class);

                    Fridge_Item item = new Fridge_Item(foodName, foodCount, foodImgPath, foodDate, fridgeType);
                    fridgeItems.add(item);
                }
                adapter_refidge.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter_refidge = new Fridge_Adapter(fridgeItems);
        recyclerView.setAdapter(adapter_refidge);


    }

}


