package yeji.mjc.gittest.cart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.FoodSearch.Food_expiry;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.Tip_comment_item;
import yeji.mjc.gittest.frige.Fridge_Item;

public class CartDialog extends AppCompatActivity implements CartDateListener {

    private RecyclerView cartrecyclerView, frigerecyclerview;
    private CartAdapter cartAdapter;
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartdb, frigedb;
    String userid="2830097009";

    String startDate,changeDate;
    Date endD;
    TextView deadLine;
    private String deadline;

    ImageButton cancel, complete;

    //문자열로 날짜를 초기화
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frige_plus_recycler);

        //뒤 배경을 반투명하게 하려고
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0,0);

        //로그인 시 아이디값 변수 받아오기
        //userid = UserData.getInstance().getUserid();

        Calendar now = Calendar.getInstance();

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        startDate = df.format(now.getTime());

        cartrecyclerView = findViewById(R.id.cartrecycler);
        frigerecyclerview = findViewById(R.id.fridgeRecyclerView);
        complete = findViewById(R.id.complete);
        cancel = findViewById(R.id.cancel);
        deadLine = findViewById(R.id.search_food_dead);
        cartAdapter = new CartAdapter(cartItems,this);

        //파이어베이스에 저장되어 있는 카트
        cartdb = database.getReference().child("user").child(userid).child("cart");
        cartdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                cartItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    CartItem cartItem= snapshot1.getValue(CartItem.class);
//                    FoodItem foodItem =
//                            new FoodItem(cartItem.getFood_name(),cartItem.getFood_count()+"개",cartItem.getFood_img(),false);
                    cartItems.add(cartItem);
                    //foodItems.add(foodItem);
                }
                cartrecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //파이어베이스에 저장되어 있는 카트
                cartdb = database.getReference().child("user").child(userid).child("cart");
                cartdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        cartItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            CartItem cartItem= snapshot1.getValue(CartItem.class);
                            FoodItem foodItem =
                                    new FoodItem(cartItem.getFood_name(),cartItem.getFood_count(),cartItem.getFood_img(),false);
                            frigedb = database.getReference().child("user").child(userid).child("fridge").child(cartItem.food_name);
                            frigedb.child("food_name").setValue(foodItem.getName());
                            frigedb.child("food_count").setValue(foodItem.getNum());
                            frigedb.child("fridge_type").setValue("cold");
                            frigedb.child("food_img").setValue(foodItem.getImg());
                            frigedb.child("food_date").setValue(changeDate);

                        }
                        // 확인 누르면 카트에 있는 아이템들 삭제 >> 이미 냉장고에 들어갔으니까
                        cartdb.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //디비를 가져오다 오류 발생시
                    }
                });

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        cartrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartrecyclerView.setAdapter(cartAdapter);

    }

    @Override
    public void onItemClicked(CartItem cartItem) {
        TextView deadLine =  findViewById(R.id.search_food_dead);

        // 날짜 선택 제약 조건 설정
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("유통기한을 선택해주세요").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build()).build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                endD = new Date(selection);

                changeDate = df.format(endD);

                deadLine.setText(datePicker.getHeaderText());
                //Toast.makeText(getApplicationContext(), changeDate, Toast.LENGTH_SHORT).show();


            }
        });

        datePicker.show(getSupportFragmentManager(),"Material_Date_Picker");

    }
}
