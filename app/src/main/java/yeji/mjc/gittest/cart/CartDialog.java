package yeji.mjc.gittest.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.comunity.Tip_comment_item;

public class CartDialog extends AppCompatActivity {

    private RecyclerView cartrecyclerView, frigerecyclerview;
    private CartAdapter cartAdapter;
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartdb, frigedb;
    String userid = "2810839655";

    ImageButton cancel, complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frige_plus_recycler);

        cartrecyclerView = findViewById(R.id.cartrecycler);
        frigerecyclerview = findViewById(R.id.fridgeRecyclerView);
        complete = findViewById(R.id.complete);
        cancel = findViewById(R.id.cancel);
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
                    FoodItem foodItem =
                            new FoodItem(cartItem.getFood_name(),cartItem.getFood_count(),cartItem.getFood_img(),false);
                    cartItems.add(cartItem);
                    foodItems.add(foodItem);
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
                            cartItems.add(cartItem);
                            frigedb = database.getReference().child("user").child(userid).child("fridge");
                            frigedb.setValue(foodItem);
                        }
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
}
