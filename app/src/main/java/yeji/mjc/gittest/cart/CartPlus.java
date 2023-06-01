package yeji.mjc.gittest.cart;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.R;

public class CartPlus extends AppCompatActivity {

    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName,foodCount,deadLine;
    ImageView foodImg;
    private DatabaseReference Barcodedb;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();

    TextView getFoodName;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference havefoodDB;
    String userid = "임시용 유저 아이디1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_plus);

        havefoodDB=database.getReference().child("user").child(userid).child("fridge");
        havefoodDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //식재료들을 모두 가져오는 for문...
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    int img = snapshot1.child("food_img").getValue(Integer.class);
                    String name=snapshot1.child("food_name").getValue(String.class);

                    getFoodName.setText(name);
                    foodImg.setImageResource(img);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        //calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);
        getFoodName=findViewById(R.id.search_food_name);

        //취소버튼을 누르면 dialog창이 닫힌다
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //확인 버튼을 누르면 dialog창이 닫힌다
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //상품추가 버튼을 누르면 상품추가 dialog창이 뜬다
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
            }
        });


    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
