package yeji.mjc.gittest.frige;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.FoodItem;
import yeji.mjc.gittest.FoodSearch.FoodSearchItem;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.SelectListener;

public class FoodSelect extends Activity implements View.OnClickListener, SelectListener {

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public ArrayList<FoodItem> items = new ArrayList<FoodItem>();
    public RecyclerView.Adapter foodSearchAdapter;
    private ChildEventListener mChild;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference havefoodDB;
    String userid = "임시용 유저 아이디1";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_img_search); //식재료 선택 화면 띄우기

        //리사이클러뷰에 매니저와 어댑터를 연결
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setAdapter(foodSearchAdapter);

        //파이어베이스에 저장되어 있는 보유식재료 정보를 유저아이디 정보를 통해 가져옴
        havefoodDB= database.getReference().child("user").child(userid).child("havefood");
        //파이어베이스에 저장되어 있는 보유식재료 정보가 변경되면 이벤트를 작동함
        havefoodDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                items.clear();   //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //보유식재료에 자식 key가 있다면 반복해서 보유식재료 정보를 받아오고 리사이클러뷰에 추가 시킨다
                    FoodItem foodItem = snapshot1.getValue(FoodItem.class);
                    items.add(foodItem);
                }
                foodSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClicked(FoodSearchItem myModel) {

    }
}
