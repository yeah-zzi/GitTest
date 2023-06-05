package yeji.mjc.gittest.cart;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.FoodSearch.FoodSearchItem;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.SelectListener;

public class CartPlus extends AppCompatActivity {

    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName,foodCount,deadLine;
    ImageView foodImg;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    public RecyclerView.Adapter foodAdapter;
    RecyclerView foodrecyclerview;

    TextView getFoodName;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartDB;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid = "임시용 유저 아이디1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_plus);

        //로그인 시 아이디값 인텐트로 받아오기
        Intent intent = getIntent();
        userid = intent.getStringExtra("userId");


        //cartDB=database.getReference().child("user").child(userid).child("cart");

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        //calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);
       // getFoodName=findViewById(R.id.search_food_name);

        Intent cintent = getIntent();
        String name = cintent.getStringExtra("food_name");
        int Img = cintent.getIntExtra("food_img", 0);

        foodName.setText(name);
        foodImg.setImageResource(Img);

        //취소버튼을 누르면 dialog창이 닫힌다
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDB.child("food_name").removeValue(); // 해당 음식 데이터 삭제
                cartDB.child("food_img").removeValue(); // 해당 음식 데이터 삭제
                finish();
            }
        });

        //확인 버튼을 누르면 dialog창이 닫힌다
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodItems.add(new FoodItem(name, "1개", Img, false));
                finish();
            }
        });

        //상품추가 버튼을 누르면 상품추가 dialog창이 뜬다
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
                startActivityForResult(fridgeSearchIntent,3);
            }
        });

        //이미지를 누르면 카메라로 이동
        foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO밑에
                //checkCameraPermissionOpenCamera();
            }
        });


    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("이름");
                int img = data.getIntExtra("이미지", 0);

                foodName.setText(name);
                foodImg.setImageResource(img);
                }
            }
        }
    }




