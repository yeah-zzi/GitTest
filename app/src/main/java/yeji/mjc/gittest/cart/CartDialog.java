package yeji.mjc.gittest.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.Food_expiry;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.Tip_comment_item;

public class CartDialog extends AppCompatActivity {

    private RecyclerView cartrecyclerView, frigerecyclerview;
    private CartAdapter cartAdapter;
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartdb, frigedb;
    String userid;

    ImageButton cancel, complete, carlendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frige_plus_recycler);

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        cartrecyclerView = findViewById(R.id.cartrecycler);
        frigerecyclerview = findViewById(R.id.fridgeRecyclerView);
        complete = findViewById(R.id.complete);
        cancel = findViewById(R.id.cancel);
        carlendar = findViewById(R.id.calendar);
        cartAdapter = new CartAdapter(cartItems);


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
                            frigedb = database.getReference().child("user").child(userid).child("fridge");
                            // 파이어베이스에 해당 아이템 저장
                            String key = frigedb.push().getKey();  // 새로운 키 생성
                            frigedb.child(key).setValue(foodItem);
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

//        carlendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent foodexpiry = new Intent(getApplicationContext(), Food_expiry.class);
//                startActivity(foodexpiry);
//            }
//        });

        cartrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartrecyclerView.setAdapter(cartAdapter);

    }
}
