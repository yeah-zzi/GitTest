package yeji.mjc.gittest.frige;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Frozen_Fridge extends Fragment{

    ImageButton search;
    EditText text_bar;

    //메뉴바 버튼 변수 선언
    public Button fridge_main; //종합
    public Button fridge_cold; //냉장
    public Button fridge_frozen; //냉동

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_refidge;
    public ArrayList<Fridge_Item> frozenFridgeItems = new ArrayList<Fridge_Item>();


    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference fridgedb;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid="2830097009",startDate,changeDate,fridge_type="";


    public int getDaysUntilFood(Date expirationDate) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = expirationDate.getTime();
        long diffInMillis = expirationTime - currentTime;
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        return (int) diffInDays;
    }

    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fridge_main, container, false);

        try {
            adapter_refidge = new Fridge_Adapter(frozenFridgeItems);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.fridgeRecyclerView);
        recyclerView.setHasFixedSize(true);

        View fridge_main = view.findViewById(R.id.fridge_main);
        View fridge_cold = view.findViewById(R.id.fridge_cold);
        View fridge_frozen = view.findViewById(R.id.fridge_frozen);

        ImageButton search=view.findViewById(R.id.search);
        EditText text_bar=view.findViewById(R.id.text_bar);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = text_bar.getText().toString().trim();
                ArrayList<Fridge_Item> filteredItems = new ArrayList<>();

                for (Fridge_Item item : frozenFridgeItems) {
                    if (item.getFood_name().equals(searchText)) {
                        filteredItems.add(item);
                    }
                }

                try {
                    adapter_refidge = new Fridge_Adapter(filteredItems);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(adapter_refidge);
            }
        });


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
                    case 0: //이름순
                        ((Fridge_Adapter) adapter_refidge).
                                setSortType(Fridge_Adapter.SortType.NAME_ASCENDING);
                        break;
                    case 1: //유통기한순
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


        //userid = UserData.getInstance().getUserid();
        fridgedb = database.getReference().child("user").child(userid).child("fridge");
        fridgedb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                frozenFridgeItems.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Fridge_Item item = snapshot1.getValue(Fridge_Item.class);

                        String foodCount = item.getFood_count();
                        item.setFood_count(foodCount + "개");

                        String foodDate = item.getFood_date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        try {
                            Date expirationDate = dateFormat.parse(foodDate);
                            int daysUntilFood = getDaysUntilFood(expirationDate)+1;
                            String dDay = "D-" + daysUntilFood;
                            item.setFood_date(dDay);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String foodType = item.getFridge_type();
                        if (foodType != null && foodType.equals("frozen")) {
                            frozenFridgeItems.add(item);
                        }
                    }
                    adapter_refidge.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return view;

    }


    public void onStart(){
        super.onStart();


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        try {
            adapter_refidge = new Fridge_Adapter(frozenFridgeItems);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter_refidge);
    }


}