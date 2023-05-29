package yeji.mjc.gittest.FoodSearch;

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

import yeji.mjc.gittest.R;

public class FridgePlus extends AppCompatActivity {

    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName,foodCount,deadLine;
    ImageView foodImg;
    private DatabaseReference Barcodedb;

    TextView getFoodName;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference havefoodDB;
    String userid = "임시용 유저 아이디1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_plus);

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
        calendarBTN = findViewById(R.id.calendar);
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

        String productName = getIntent().getStringExtra("productName");
        foodName.setText(productName);




        //스캔하여 얻은 값
        String scannedBarcode = getIntent().getStringExtra("barcode");
        // 파이어베이스 데이터베이스의 "Product" 경로에서 바코드 값에 해당하는 이름을 가져옴
        Barcodedb = FirebaseDatabase.getInstance().getReference().child("Product");
        Barcodedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot barcode_date: snapshot.getChildren()){
                    Product product = barcode_date.getValue(Product.class);
                    if( product != null && equals(scannedBarcode)){
                        String name = product.getPRDT_NM()+" ";
                        foodName.setText((String)name);
                        String img = product.getImg();
                        Glide.with(getApplicationContext()).load(img).into(foodImg);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FridgePlus.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

//        // ValueEventListener를 사용하여 데이터 변경을 감지하고 이름을 가져옵니다.
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // 바코드 값에 해당하는 이름을 가져옴
//                    String Name = dataSnapshot.child("PRDT_NM").getValue(String.class);
//
//                    // 가져온 이름을 TextView에 설정
//                    foodName.setText(Name);
//                } else {
//                    // 파이어베이스에 해당 바코드가 없을 경우에 대한 처리
//                    Toast.makeText(FridgePlus.this, "스캔된 제품을 찾을 수 없습니다!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // 데이터 가져오기가 실패한 경우에 대한 처리
//                Toast.makeText(FridgePlus.this, "ERROR!", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        // ValueEventListener를 ValueEventListener를 통해 등록합니다.
//        Barcodedb.addValueEventListener(valueEventListener);
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
