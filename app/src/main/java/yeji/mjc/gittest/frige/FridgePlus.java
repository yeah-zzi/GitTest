package yeji.mjc.gittest.frige;

import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.FoodSearch.FoodSearchItem;
import yeji.mjc.gittest.FoodSearch.Food_expiry;
import yeji.mjc.gittest.FoodSearch.Product;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class FridgePlus extends AppCompatActivity implements View.OnClickListener {



    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName; //식재료 이름
    ImageView foodImg; //식재료 이미지
    TextView foodCount; //식재료 수
    TextView deadLine; //유통기한

    Fridge_Adapter fridge_adapter;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference addfoodDB;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid,name,count;

    private DatabaseReference Barcodedb;

    // 냉장고 아이템
    public ArrayList<Fridge_Item> fridgeItems = new ArrayList<Fridge_Item>();
    // 선택한 아이템
    public ArrayList<FoodSearchItem> items = new ArrayList<FoodSearchItem>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_plus);


        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        Intent fbintent = getIntent();
        String name = fbintent.getStringExtra("food_name");
        int Img = fbintent.getIntExtra("food_img", 0);

        foodName.setText(name);
        foodImg.setImageResource(Img);

        //취소버튼을 누르면 dialog창이 닫힌다
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase 값에서 조회내역 삭제
                addfoodDB.removeValue();
                finish();
            }
        });

        //확인 버튼을 누르면 dialog창이 닫힌다
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase 값에서 조회내역 삭제
                //addfoodDB.removeValue();
                //TODO : 확인 버튼 누르면 냉장고에 들어가야함

                finish();
            }
        });

        //상품추가 버튼을 누르면 상품추가 dialog창이 뜬다
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch_Fridge.class);
                startActivity(fridgeSearchIntent);
            }
        });

        String productName = getIntent().getStringExtra("productName");
        foodName.setText(productName);

        calendarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foodexpiry = new Intent(getApplicationContext(), Food_expiry.class);
                startActivity(foodexpiry);
            }
        });


        //스캔하여 얻은 값
        String scannedBarcode = getIntent().getStringExtra("barcode");
        // 파이어베이스 데이터베이스의 "Product" 경로에서 바코드 값을 가져옴
        Barcodedb = FirebaseDatabase.getInstance().getReference().child("Product").child("barcode");
        // 스캔한 바코드 값과 파이어베이스 안에 있는 바코드 값이 같으면 불러냄
        Query query = Barcodedb.orderByChild("barcode").equalTo(scannedBarcode);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot barcode_date: snapshot.getChildren()){
                    Product product = barcode_date.getValue(Product.class);
                    if( product != null ){
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
//        //  ValueEventListener를 ValueEventListener를 통해 등록합니다.
//        Barcodedb.addValueEventListener(valueEventListener);
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View v) {

    }
}
