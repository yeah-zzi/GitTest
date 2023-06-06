package yeji.mjc.gittest.cart;

import android.os.Bundle;

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

    private RecyclerView cartrecyclerView;
    private CartAdapter cartAdapter;
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartdb;
    String userid = "2810839655";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frige_plus_recycler);

        cartrecyclerView = findViewById(R.id.cartrecycler);
        cartAdapter = new CartAdapter(cartItems);

        cartItems.add(new CartItem("오이","1개","https://firebasestorage.googleapis.com/v0/b/foodiemate-1a25c.appspot.com/o/1686044877613.android.resource%3A%2Fyeji.mjc.gittest%2F2131231126?alt=media&token=08a72aba-bf2e-4fee-afe6-9d89844d13b5"));

//        //파이어베이스에 저장되어 있는 카트
//        cartdb = database.getReference().child("user").child(userid).child("cart");
//        cartdb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
//                cartItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
//                for (DataSnapshot snapshot1 : snapshot.getChildren()){
//                    CartItem cartItem= snapshot1.getValue(CartItem.class);
//                    cartItems.add(cartItem);
//                }
//                cartrecyclerView.getAdapter().notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //디비를 가져오다 오류 발생시
//            }
//        });

        cartrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartrecyclerView.setAdapter(cartAdapter);

    }
}
